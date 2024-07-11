package com.groupd.bms.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256Util
 * SHA-256 해시 알고리즘을 사용하여 문자열을 해싱하는 클래스
 * @version 1.0
 * @since 2024.04.26
 * @see com.groupd.bms.util.SHA256Util
 */
public class SHA256Util {
    
    /**
     * SHA-256 해시 알고리즘을 사용하여 문자열을 해싱
     * @param text
     * @return String
     */
    public static String hashWithSHA256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return StringUtil.objectToString(hexString.toString());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing SHA-256 algorithm", e);
        }
    }

}
