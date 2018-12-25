package dev.manifest.bccheck.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrefsValidatorTest {

    @Test
    public void isIPV4emptyString() {
        assertFalse("IP empty String should be invalid", PrefsValidator.isIPV4(""));
    }

    @Test
    public void isIPV4NegativeNumber() {
        assertFalse("IP -127.0.0.1 should be invalid", PrefsValidator.isIPV4("-127.0.0.1"));
        assertFalse("IP 127.-10.0.1 should be invalid", PrefsValidator.isIPV4("127.-10.0.1"));
        assertFalse("IP 127.0.-120.1 should be invalid", PrefsValidator.isIPV4("127.0.-120.1"));
        assertFalse("IP 127.0.0.-251 should be invalid", PrefsValidator.isIPV4("127.0.0.-251"));
    }

    @Test
    public void isIPV4alphabetic() {
        assertFalse("IP \"abc\" should be invalid", PrefsValidator.isIPV4("abc"));
    }

    @Test
    public void isIPV4lessThenFourNumbers() {
        assertFalse("IP 112 should be invalid", PrefsValidator.isIPV4("112"));
        assertFalse("IP 112.101 should be invalid", PrefsValidator.isIPV4("112.101"));
        assertFalse("IP 112.101.102 should be invalid", PrefsValidator.isIPV4("112.101.102"));
    }

    @Test
    public void isIPV4oneNumberIsMoreThen255() {
        assertFalse("IP 256.112.101.102 should be invalid", PrefsValidator.isIPV4("256.112.101.102"));
        assertFalse("IP 112.303.101.102 should be invalid", PrefsValidator.isIPV4("112.303.101.102"));
        assertFalse("IP 112.101.999.102 should be invalid", PrefsValidator.isIPV4("112.101.999.102"));
        assertFalse("IP 112.101.102.256 should be invalid", PrefsValidator.isIPV4("112.101.102.256"));
    }

    @Test
    public void isIPV4correct() {
        assertTrue("IP 127.0.0.1 should be valid", PrefsValidator.isIPV4("127.0.0.1"));
        assertTrue("IP 255.255.255.255 should be valid", PrefsValidator.isIPV4("255.255.255.255"));
    }

    @Test
    public void isPortValidNotNumber() {
        assertFalse(PrefsValidator.isPortValid("zero"));
    }

    @Test
    public void isPortValidEmpty() {
        assertFalse(PrefsValidator.isPortValid(""));
    }

    @Test
    public void isPortValidZero() {
        assertFalse(PrefsValidator.isPortValid("0"));
    }

    @Test
    public void isPortValidNegative() {
        assertFalse(PrefsValidator.isPortValid("-100"));
    }

    @Test
    public void isPortValidTooBig() {
        assertFalse(PrefsValidator.isPortValid("65536"));
    }

    @Test
    public void isPortValidValidNumber() {
        assertTrue(PrefsValidator.isPortValid("8080"));
    }

    @Test
    public void isObjectValidNotNumber() {
        assertFalse(PrefsValidator.isObjectValid("zero"));
    }

    @Test
    public void isObjectValidEmpty() {
        assertFalse(PrefsValidator.isObjectValid(""));
    }

    @Test
    public void isObjectValidZero() {
        assertTrue(PrefsValidator.isObjectValid("0"));
    }

    @Test
    public void isObjectValidNegative() {
        assertFalse(PrefsValidator.isObjectValid("-100"));
    }

    @Test
    public void isObjectValidTooBig() {
        assertFalse(PrefsValidator.isObjectValid("100000"));
    }

    @Test
    public void isObjectValidValidNumber() {
        assertTrue(PrefsValidator.isObjectValid("119"));
    }
}