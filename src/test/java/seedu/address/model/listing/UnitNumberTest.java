package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UnitNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnitNumber(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "123", "12-", "-12", "12-123I", "12-123O", 
            "A12-123", "C12-123", "12-0123", "02-123", "123-123", "B00-123", 
            "R12-0", "12-123-45", "12A-123", "B12-123456", "R12-123A1"})
    public void constructor_invalidUnitNumber_throwsIllegalArgumentException(String invalidUnitNumber) {
        assertThrows(IllegalArgumentException.class, () -> new UnitNumber(invalidUnitNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12-123", "12-1234", "12-12345", "B12-123", "R12-123", 
            "12-123A", "12-123Z", "B12-12345P", "R12-12H"})
    public void constructor_validUnitNumber_success(String validUnitNumber) {
        UnitNumber unitNumber = new UnitNumber(validUnitNumber);
        assertEquals(validUnitNumber, unitNumber.unitNumber);
    }

    @Test
    public void isValidUnitNumber() {
        // null unit number
        assertThrows(NullPointerException.class, () -> UnitNumber.isValidUnitNumber(null));

        // invalid unit numbers
        assertFalse(UnitNumber.isValidUnitNumber("")); // empty string
        assertFalse(UnitNumber.isValidUnitNumber(" ")); // spaces only
        assertFalse(UnitNumber.isValidUnitNumber("123")); // missing hyphen
        assertFalse(UnitNumber.isValidUnitNumber("12-")); // missing apartment number
        assertFalse(UnitNumber.isValidUnitNumber("-12")); // missing floor number
        assertFalse(UnitNumber.isValidUnitNumber("12-123I")); // contains I in subunit
        assertFalse(UnitNumber.isValidUnitNumber("12-123O")); // contains O in subunit
        assertFalse(UnitNumber.isValidUnitNumber("A12-123")); // invalid prefix
        assertFalse(UnitNumber.isValidUnitNumber("C12-123")); // invalid prefix
        assertFalse(UnitNumber.isValidUnitNumber("12-0123")); // apartment number starts with 0
        assertFalse(UnitNumber.isValidUnitNumber("02-123")); // floor number starts with 0
        assertFalse(UnitNumber.isValidUnitNumber("123-123")); // floor number more than 2 digits
        assertFalse(UnitNumber.isValidUnitNumber("B00-123")); // floor number starts with 0 (with prefix)
        assertFalse(UnitNumber.isValidUnitNumber("R12-0")); // apartment number starts with 0 and is only 1 digit
        assertFalse(UnitNumber.isValidUnitNumber("12-123-45")); // extra hyphen
        assertFalse(UnitNumber.isValidUnitNumber("12A-123")); // invalid floor format
        assertFalse(UnitNumber.isValidUnitNumber("B12-123456")); // apartment number too long
        assertFalse(UnitNumber.isValidUnitNumber("R12-123A1")); // invalid subunit format

        // valid unit numbers
        assertTrue(UnitNumber.isValidUnitNumber("12-123")); // standard format
        assertTrue(UnitNumber.isValidUnitNumber("12-1234")); // 4-digit apartment number
        assertTrue(UnitNumber.isValidUnitNumber("12-12345")); // 5-digit apartment number
        assertTrue(UnitNumber.isValidUnitNumber("B12-123")); // with basement prefix
        assertTrue(UnitNumber.isValidUnitNumber("R12-123")); // with roof prefix
        assertTrue(UnitNumber.isValidUnitNumber("12-123A")); // with subunit
        assertTrue(UnitNumber.isValidUnitNumber("12-123Z")); // with subunit Z
        assertTrue(UnitNumber.isValidUnitNumber("B12-12345P")); // with prefix, 5-digit apartment number, and subunit
        assertTrue(UnitNumber.isValidUnitNumber("R12-12H")); // with prefix, 2-digit apartment number, and subunit
    }

    @Test
    public void equals() {
        UnitNumber unitNumber1 = new UnitNumber("12-123");
        
        // same object -> returns true
        assertTrue(unitNumber1.equals(unitNumber1));
        
        // same values -> returns true
        UnitNumber unitNumber2 = new UnitNumber("12-123");
        assertTrue(unitNumber1.equals(unitNumber2));
        
        // null -> returns false
        assertFalse(unitNumber1.equals(null));
        
        // different type -> returns false
        assertFalse(unitNumber1.equals(5));
        
        // different unit number -> returns false
        UnitNumber unitNumber3 = new UnitNumber("13-456");
        assertFalse(unitNumber1.equals(unitNumber3));
    }

    @Test
    public void toString_returnsUnitNumber() {
        String validUnitNumber = "12-123";
        UnitNumber unitNumber = new UnitNumber(validUnitNumber);
        assertEquals(validUnitNumber, unitNumber.toString());
    }

    @Test
    public void hashCode_sameUnitNumber_sameHashCode() {
        UnitNumber unitNumber1 = new UnitNumber("12-123");
        UnitNumber unitNumber2 = new UnitNumber("12-123");
        assertEquals(unitNumber1.hashCode(), unitNumber2.hashCode());
    }
} 