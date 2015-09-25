/*
 * CustomAuthenticationProvider.java
 *
 * Copyright 2013 Fundación Indesoft indesoft.org.ve.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stricore.as400.security;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Service;

/**
 * @date 04/08/2013
 *
 * @version 1.0
 * @author Jose Luis Estevez jose.estevez.prieto@gmail.com
 * jestevez@indesoft.org.ve
 */
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger Log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    private static final String NONCE_FIELD_SEPARATOR = ":";
    private String key = "KEY";
    private long nonceValiditySeconds = 10;
    protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public final Authentication authenticate(Authentication authentication) {
        final UsernamePasswordWithTimeoutAuthenticationToken authenticationToken = (UsernamePasswordWithTimeoutAuthenticationToken) authentication;
        validateTimeout(authenticationToken);

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (isPasswordCorrect(authentication)) {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            grantedAuths.add(new SimpleGrantedAuthority("superuser"));
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
            return auth;
        } else {
            return null;
        }
    }

    protected boolean isPasswordCorrect(Authentication authentication) {        
        String systemName = "PUB1.RZKH.DE";
        String userName = authentication.getName();
        String userPwd = authentication.getCredentials().toString();
        AS400 myAS400 = new AS400(systemName);
        String msg = "authentification failed on " + systemName + " for user" + userName;
        try {
            myAS400.validateSignon(userName, userPwd);
        } catch (AS400SecurityException e) {
            translateSecurityExceptionAndRethrow(e, userName, systemName);
            throw new BadCredentialsException(msg, e);
        } catch (IOException e) {
            throw new AuthenticationServiceException(msg, e);
        }
        return true;
    }

    private void translateSecurityExceptionAndRethrow(AS400SecurityException e,
            String userName, String systemName) {
        int code = e.getReturnCode();
        String msg = "authentification failed on " + systemName + " for user" + userName;
        if (code == AS400SecurityException.PASSWORD_INCORRECT) {
            throw new BadCredentialsException(msg, e);
        } else if (code == AS400SecurityException.USERID_UNKNOWN) {
            throw new UsernameNotFoundException(msg, e);
        } else if (code == AS400SecurityException.PASSWORD_EXPIRED) {
            throw new CredentialsExpiredException(msg, e);
        } else if (code == AS400SecurityException.PASSWORD_INCORRECT_USERID_DISABLE) {
            throw new CredentialsExpiredException(msg, e);
        } else if (code == AS400SecurityException.USERID_DISABLE) {
            throw new CredentialsExpiredException(msg, e);
        } else {
            throw new BadCredentialsException(msg, e);
        }
    }

    @Override
    public final boolean supports(Class<?> authentication) {
        return UsernamePasswordWithTimeoutAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public long getNonceValiditySeconds() {
        return nonceValiditySeconds;
    }

    public void setNonceValiditySeconds(long nonceValiditySeconds) {
        this.nonceValiditySeconds = nonceValiditySeconds;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private void validateTimeout(
            UsernamePasswordWithTimeoutAuthenticationToken authenticationToken) {
        if (StringUtils.isEmpty(authenticationToken.getTimeout())) {
            final String msg = "Timeout signature not present.";
            Log.error(msg);
            throw new BadCredentialsException(msg);
        }
        final long timeOutTime = extractNonceValue(authenticationToken.getTimeout());

        if (isNonceExpired(timeOutTime) && false) {
            final String msg = "Login timeout";
            Log.error(msg);
            throw new NonceExpiredException(msg);
        }
    }

    boolean isNonceExpired(final long timeoutTime) {
        final long now = System.currentTimeMillis();
        return timeoutTime < now;
    }

    private long extractNonceValue(final String nonce) {
        // Check nonce was Base64 encoded (as sent by the filter)
        if (!Base64.isBase64(nonce.getBytes())) {
            throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceEncoding",
                    new Object[]{nonce}, "Nonce is not encoded in Base64; received nonce {0}"));
        }

        // Decode nonce from Base64
        // format of nonce is:
        // base64(expirationTime + ":" + md5Hex(expirationTime + ":" + key))
        final String nonceAsPlainText = new String(Base64.decode(nonce.getBytes()));
        final String[] nonceTokens = org.springframework.util.StringUtils.delimitedListToStringArray(nonceAsPlainText, NONCE_FIELD_SEPARATOR);

        if (nonceTokens.length != 2) {
            throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceNotTwoTokens",
                    new Object[]{nonceAsPlainText}, "Nonce should have yielded two tokens but was {0}"));
        }

        // Extract expiry time from nonce
        long nonceExpiryTime;
        try {
            nonceExpiryTime = Long.valueOf(nonceTokens[0]);
        } catch (NumberFormatException nfe) {
            throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceNotNumeric",
                    new Object[]{nonceAsPlainText},
                    "Nonce token should have yielded a numeric first token, but was {0}"), nfe);
        }

        // Check signature of nonce matches this expiry time
        final String expectedNonceSignature = md5Hex(nonceExpiryTime + NONCE_FIELD_SEPARATOR + key);

        if (!expectedNonceSignature.equals(nonceTokens[1])) {
            throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceCompromised",
                    new Object[]{nonceAsPlainText}, "Nonce token compromised {0}"));
        }

        return nonceExpiryTime;
    }

    private Authentication createSuccessAuthentication(UsernamePasswordAuthenticationToken authenticationToken) {
        final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        //lógica de asignación de roles en authorities
        return new UsernamePasswordAuthenticationToken(authenticationToken.getPrincipal(),
                authenticationToken.getCredentials(), authorities);
    }

    public String calculateNonce() {
        final long expiryTime = System.currentTimeMillis()
                + (nonceValiditySeconds * 1000);
        final String signatureValue = md5Hex(new StringBuilder().append(expiryTime).append(NONCE_FIELD_SEPARATOR).append(key).toString());
        final String nonceValue = new StringBuilder().append(expiryTime).append(NONCE_FIELD_SEPARATOR).append(signatureValue).toString();
        return new String(Base64.encode(nonceValue.getBytes()));
    }

    public static String md5Hex(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return new String(Hex.encode(digest.digest(data.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

    }
}
