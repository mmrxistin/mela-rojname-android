// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.security;

public class SecuritySanitizer {

    /**
     * Sanitizes user text input to prevent XSS, HTML tag injection, and malicious scripts.
     */
    public static String sanitizeInput(String input) {
        if (input == null) return "";

        String sanitized = input.trim();
        // Remove HTML/Script tags
        sanitized = sanitized.replaceAll("<[^>]*>", "");
        // Escape risky characters
        sanitized = sanitized.replace("'", "''")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");

        return sanitized;
    }

    /**
     * Validates if email format is clean and safe.
     */
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String cleanEmail = email.trim();
        return cleanEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }
}
