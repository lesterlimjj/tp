package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"peter jack", "12345", "peter the 2nd", "Capital Tan", "David Roger Jackson Ray Jr 2nd"})
    public void constructor_validName_success(String validName) {
        Name name = new Name(validName);
        assertEquals(validName, name.fullName);
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName(" peter")); // starts with space
        assertFalse(Name.isValidName("peter ")); // ends with space
        assertFalse(Name.isValidName("peter@jack")); // contains @ symbol
        assertFalse(Name.isValidName("peter#jack")); // contains # symbol
        assertFalse(Name.isValidName("peter$jack")); // contains $ symbol

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("a")); // single character
        assertTrue(Name.isValidName("A")); // single capital letter
        assertTrue(Name.isValidName("1")); // single digit
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }

    @Test
    public void toString_returnsFullName() {
        String validName = "Valid Name";
        Name name = new Name(validName);
        assertEquals(validName, name.toString());
    }

    @Test
    public void hashCode_sameName_sameHashCode() {
        Name name1 = new Name("Valid Name");
        Name name2 = new Name("Valid Name");
        assertEquals(name1.hashCode(), name2.hashCode());
    }
}
