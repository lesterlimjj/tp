package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @ParameterizedTest
    @ValueSource(strings = {"911", "93121534", "124293842033123"})
    public void constructor_validPhone_success(String validPhone) {
        Phone phone = new Phone(validPhone);
        assertEquals(validPhone, phone.value);
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone(" 93121534")); // leading space
        assertFalse(Phone.isValidPhone("93121534 ")); // trailing space
        assertFalse(Phone.isValidPhone("+93121534")); // special characters
        assertFalse(Phone.isValidPhone("(93)121534")); // special characters
        assertFalse(Phone.isValidPhone("93-121-534")); // hyphens

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
        assertTrue(Phone.isValidPhone("999")); // short phone number
        assertTrue(Phone.isValidPhone("123456789")); // typical phone number
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }

    @Test
    public void toString_returnsPhoneValue() {
        String validPhone = "123456789";
        Phone phone = new Phone(validPhone);
        assertEquals(validPhone, phone.toString());
    }

    @Test
    public void hashCode_samePhone_sameHashCode() {
        Phone phone1 = new Phone("123456789");
        Phone phone2 = new Phone("123456789");
        assertEquals(phone1.hashCode(), phone2.hashCode());
    }
}
