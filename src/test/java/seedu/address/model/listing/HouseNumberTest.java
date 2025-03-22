package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class HouseNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HouseNumber(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "1234", "A123", "123I", "123O", "I", "O", "#123", "12-3", "12/3", "12 3"})
    public void constructor_invalidHouseNumber_throwsIllegalArgumentException(String invalidHouseNumber) {
        assertThrows(IllegalArgumentException.class, () -> new HouseNumber(invalidHouseNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "A1", "1A", "12A", "AB1", "12Z", "123", "12N", "12P"})
    public void constructor_validHouseNumber_success(String validHouseNumber) {
        HouseNumber houseNumber = new HouseNumber(validHouseNumber);
        assertEquals(validHouseNumber, houseNumber.houseNumber);
    }

    @Test
    public void isValidHouseNumber() {
        // null house number
        assertThrows(NullPointerException.class, () -> HouseNumber.isValidHouseNumber(null));

        // invalid house numbers
        assertFalse(HouseNumber.isValidHouseNumber("")); // empty string
        assertFalse(HouseNumber.isValidHouseNumber(" ")); // spaces only
        assertFalse(HouseNumber.isValidHouseNumber("1234")); // too long
        assertFalse(HouseNumber.isValidHouseNumber("A123")); // too long
        assertFalse(HouseNumber.isValidHouseNumber("123I")); // contains I
        assertFalse(HouseNumber.isValidHouseNumber("123O")); // contains O
        assertFalse(HouseNumber.isValidHouseNumber("#123")); // contains special characters
        assertFalse(HouseNumber.isValidHouseNumber("12-3")); // contains special characters
        assertFalse(HouseNumber.isValidHouseNumber("12/3")); // contains special characters
        assertFalse(HouseNumber.isValidHouseNumber("12 3")); // contains spaces

        // valid house numbers
        assertTrue(HouseNumber.isValidHouseNumber("1")); // single digit
        assertTrue(HouseNumber.isValidHouseNumber("123")); // three digits
        assertTrue(HouseNumber.isValidHouseNumber("A1")); // letter and digit
        assertTrue(HouseNumber.isValidHouseNumber("1A")); // digit and letter
        assertTrue(HouseNumber.isValidHouseNumber("12A")); // two digits and letter
        assertTrue(HouseNumber.isValidHouseNumber("AB1")); // two letters and digit
        assertTrue(HouseNumber.isValidHouseNumber("12Z")); // two digits and Z
        assertTrue(HouseNumber.isValidHouseNumber("12N")); // two digits and N
        assertTrue(HouseNumber.isValidHouseNumber("12P")); // two digits and P
    }

    @Test
    public void equals() {
        HouseNumber houseNumber1 = new HouseNumber("123");
        
        // same object -> returns true
        assertTrue(houseNumber1.equals(houseNumber1));
        
        // same values -> returns true
        HouseNumber houseNumber2 = new HouseNumber("123");
        assertTrue(houseNumber1.equals(houseNumber2));
        
        // null -> returns false
        assertFalse(houseNumber1.equals(null));
        
        // different type -> returns false
        assertFalse(houseNumber1.equals(5));
        
        // different house number -> returns false
        HouseNumber houseNumber3 = new HouseNumber("456");
        assertFalse(houseNumber1.equals(houseNumber3));
    }

    @Test
    public void toString_returnsHouseNumber() {
        String validHouseNumber = "123";
        HouseNumber houseNumber = new HouseNumber(validHouseNumber);
        assertEquals(validHouseNumber, houseNumber.toString());
    }

    @Test
    public void hashCode_sameHouseNumber_sameHashCode() {
        HouseNumber houseNumber1 = new HouseNumber("123");
        HouseNumber houseNumber2 = new HouseNumber("123");
        assertEquals(houseNumber1.hashCode(), houseNumber2.hashCode());
    }
} 