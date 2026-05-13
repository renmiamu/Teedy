package com.sismics.docs.rest.util;

import com.sismics.rest.exception.ClientException;
import com.sismics.rest.util.ValidationUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the validations.
 *
 * @author jtremeaux 
 */
public class TestValidationUtil {
    @Test
    public void testValidateHttpUrlFail() throws Exception {
        ValidationUtil.validateHttpUrl("http://www.google.com", "url");
        ValidationUtil.validateHttpUrl("https://www.google.com", "url");
        Assert.assertEquals("https://www.google.com", ValidationUtil.validateHttpUrl(" https://www.google.com ", "url"));
        try {
            ValidationUtil.validateHttpUrl("ftp://www.google.com", "url");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateHttpUrl("http://", "url");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
    }

    @Test
    public void testValidateLengthAndRequired() throws Exception {
        Assert.assertEquals("abc", ValidationUtil.validateLength(" abc ", "name", 1, 5, false));
        Assert.assertEquals("", ValidationUtil.validateLength("  ", "optional", 1, 5, true));
        Assert.assertNull(ValidationUtil.validateLength(null, "optional", 1, 5, true));
        Assert.assertEquals("abc", ValidationUtil.validateLength("abc", "name", 2, null, false));
        Assert.assertEquals("abc", ValidationUtil.validateLength("abc", "name", null, 3, false));
        try {
            ValidationUtil.validateLength(null, "name", 1, 5, false);
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateLength("ab", "name", 3, 5, false);
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateLength("abcdef", "name", 1, 5, false);
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateLength("abcd", "name", null, 3, false);
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        ValidationUtil.validateRequired("value", "required");
        try {
            ValidationUtil.validateRequired(null, "required");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
    }

    @Test
    public void testValidatePatterns() throws Exception {
        ValidationUtil.validateEmail("a@b", "email");
        ValidationUtil.validateStringNotBlank(" abc ", "name");
        try {
            ValidationUtil.validateEmail("ab", "email");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateStringNotBlank("   ", "name");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }

        ValidationUtil.validateHexColor("#a1b2c3", "color", false);
        Assert.assertEquals("", ValidationUtil.validateLength("  ", "color", 7, 7, true));
        ValidationUtil.validateHexColor(null, "color", true);
        try {
            ValidationUtil.validateHexColor("#fff", "color", false);
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }

        ValidationUtil.validateTagName("tag-name");
        try {
            ValidationUtil.validateTagName("bad tag");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateTagName("bad:tag");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }

        ValidationUtil.validateAlphanumeric("abc_123", "alnum");
        try {
            ValidationUtil.validateAlphanumeric("abc-123", "alnum");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateAlphanumeric("", "alnum");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }

        ValidationUtil.validateUsername("user.name-1", "user");
        ValidationUtil.validateUsername("user@name", "user");
        try {
            ValidationUtil.validateUsername("user name", "user");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }

        ValidationUtil.validateRegex("abc123", "regex", "[a-z0-9]+");
        try {
            ValidationUtil.validateRegex("ABC", "regex", "[a-z]+");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
    }

    @Test
    public void testValidateNumbersAndDate() throws Exception {
        Assert.assertEquals(Integer.valueOf(42), ValidationUtil.validateInteger("42", "int"));
        Assert.assertEquals(Integer.valueOf(-1), ValidationUtil.validateInteger("-1", "int"));
        try {
            ValidationUtil.validateInteger("x", "int");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateInteger(" 42 ", "int");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }

        Assert.assertEquals(Long.valueOf(123456789L), ValidationUtil.validateLong("123456789", "long"));
        Assert.assertEquals(Long.valueOf(-123L), ValidationUtil.validateLong("-123", "long"));
        try {
            ValidationUtil.validateLong("x", "long");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateLong(" 123 ", "long");
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }

        Assert.assertNotNull(ValidationUtil.validateDate("0", "date", false));
        Assert.assertNull(ValidationUtil.validateDate("", "date", true));
        Assert.assertNull(ValidationUtil.validateDate(null, "date", true));
        try {
            ValidationUtil.validateDate(null, "date", false);
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateDate("", "date", false);
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
        try {
            ValidationUtil.validateDate("abc", "date", false);
            Assert.fail();
        } catch (ClientException e) {
            // NOP
        }
    }
}
