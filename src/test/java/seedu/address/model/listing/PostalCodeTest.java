package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PostalCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PostalCode(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "12345", "1234567", "12345a", "a12345", "12 345", "12-345", "12.345", "ABCDEF"})
    public void constructor_invalidPostalCode_throwsIllegalArgumentException(String invalidPostalCode) {
        assertThrows(IllegalArgumentException.class, () -> new PostalCode(invalidPostalCode));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456", "000000", "999999", "120456", "098765"})
    public void constructor_validPostalCode_success(String validPostalCode) {
        PostalCode postalCode = new PostalCode(validPostalCode);
        assertEquals(validPostalCode, postalCode.postalCode);
    }

    @Test
    public void isValidPostalCode() {
        // null postal code
        assertThrows(NullPointerException.class, () -> PostalCode.isValidPostalCode(null));

        // invalid postal codes
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("12345")); // not enough digits
        assertFalse(PostalCode.isValidPostalCode("1234567")); // too many digits
        assertFalse(PostalCode.isValidPostalCode("12345a")); // contains non-digits
        assertFalse(PostalCode.isValidPostalCode("a12345")); // contains non-digits
        assertFalse(PostalCode.isValidPostalCode("12 345")); // contains spaces
        assertFalse(PostalCode.isValidPostalCode("12-345")); // contains hyphens
        assertFalse(PostalCode.isValidPostalCode("12.345")); // contains periods
        assertFalse(PostalCode.isValidPostalCode("ABCDEF")); // contains letters

        // valid postal codes
        assertTrue(PostalCode.isValidPostalCode("123456")); // standard 6-digit postal code
        assertTrue(PostalCode.isValidPostalCode("000000")); // all zeros
        assertTrue(PostalCode.isValidPostalCode("999999")); // all nines
        assertTrue(PostalCode.isValidPostalCode("120456")); // mixed digits
        assertTrue(PostalCode.isValidPostalCode("098765")); // leading zero
    }

    @Test
    public void equals() {
        PostalCode postalCode1 = new PostalCode("123456");
        
        // same object -> returns true
        assertTrue(postalCode1.equals(postalCode1));
        
        // same values -> returns true
        PostalCode postalCode2 = new PostalCode("123456");
        assertTrue(postalCode1.equals(postalCode2));
        
        // null -> returns false
        assertFalse(postalCode1.equals(null));
        
        // different type -> returns false
        assertFalse(postalCode1.equals(5));
        
        // different postal code -> returns false
        PostalCode postalCode3 = new PostalCode("654321");
        assertFalse(postalCode1.equals(postalCode3));
    }

    @Test
    public void toString_returnsPostalCode() {
        String validPostalCode = "123456";
        PostalCode postalCode = new PostalCode(validPostalCode);
        assertEquals(validPostalCode, postalCode.toString());
    }

    @Test
    public void hashCode_samePostalCode_sameHashCode() {
        PostalCode postalCode1 = new PostalCode("123456");
        PostalCode postalCode2 = new PostalCode("123456");
        assertEquals(postalCode1.hashCode(), postalCode2.hashCode());
    }
} 