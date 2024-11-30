package com.teos.school.school_management.Service;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    public String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[96]; 
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
