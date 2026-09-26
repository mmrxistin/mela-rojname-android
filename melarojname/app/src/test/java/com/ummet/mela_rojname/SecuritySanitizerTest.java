// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.ummet.mela_rojname.security.SecuritySanitizer;

import org.junit.Test;

public class SecuritySanitizerTest {

    @Test
    public void sanitizeInput_removesHtmlTags() {
        String dirtyText = "<script>alert('hack');</script><b>Selam</b>";
        String cleanText = SecuritySanitizer.sanitizeInput(dirtyText);
        assertFalse(cleanText.contains("<script>"));
        assertFalse(cleanText.contains("<b>"));
        assertTrue(cleanText.contains("Selam"));
    }

    @Test
    public void isValidEmail_validatesCorrectFormat() {
        assertTrue(SecuritySanitizer.isValidEmail("user@example.com"));
        assertFalse(SecuritySanitizer.isValidEmail("invalid_email_format"));
    }
}
