package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PropertyNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PropertyName(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "A", "$Invalid", "With@Symbol", "~Special&Chars", 
            "This property name is way too long exceeding the limit of one hundred characters which is not allowed according to the validation regex set in the PropertyName class rules",
            "#Invalid", "//Invalid"})
    public void constructor_invalidPropertyName_throwsIllegalArgumentException(String invalidPropertyName) {
        assertThrows(IllegalArgumentException.class, () -> new PropertyName(invalidPropertyName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Sunshine Towers", "The Peak", "Marina Bay Residences", "12 Holland Village", 
            "St. Patrick's Garden", "Newton-Novena Complex", "East Coast Park's Villa", "The Grand 123", 
            "Century 21", "Marina One Residences"})
    public void constructor_validPropertyName_success(String validPropertyName) {
        PropertyName propertyName = new PropertyName(validPropertyName);
        assertEquals(validPropertyName, propertyName.propertyName);
    }

    @Test
    public void isValidPropertyName() {
        // null property name
        assertThrows(NullPointerException.class, () -> PropertyName.isValidPropertyName(null));

        // invalid property names
        assertFalse(PropertyName.isValidPropertyName("")); // empty string
        assertFalse(PropertyName.isValidPropertyName(" ")); // single space
        assertFalse(PropertyName.isValidPropertyName("A")); // too short
        assertFalse(PropertyName.isValidPropertyName("@Invalid")); // contains '@' symbol
        assertFalse(PropertyName.isValidPropertyName("With$Symbol")); // contains '$' symbol
        assertFalse(PropertyName.isValidPropertyName("#Invalid")); // contains '#' symbol
        assertFalse(PropertyName.isValidPropertyName("//Invalid")); // contains '/' symbols
        assertFalse(PropertyName.isValidPropertyName("This property name is way too long exceeding the limit of one hundred characters which is not allowed according to the validation regex set in the PropertyName class rules")); // too long

        // valid property names
        assertTrue(PropertyName.isValidPropertyName("Sunshine Towers")); // standard property name with space
        assertTrue(PropertyName.isValidPropertyName("The Peak")); // simple name with space
        assertTrue(PropertyName.isValidPropertyName("Marina Bay Residences")); // multiple words
        assertTrue(PropertyName.isValidPropertyName("12 Holland Village")); // numbers and letters
        assertTrue(PropertyName.isValidPropertyName("St. Patrick's Garden")); // with period and apostrophe
        assertTrue(PropertyName.isValidPropertyName("Newton-Novena Complex")); // with hyphen
        assertTrue(PropertyName.isValidPropertyName("East Coast Park's Villa")); // with apostrophe
        assertTrue(PropertyName.isValidPropertyName("The Grand 123")); // with numbers
        assertTrue(PropertyName.isValidPropertyName("Century 21")); // with numbers
        assertTrue(PropertyName.isValidPropertyName("AA")); // exactly 2 characters
    }

    @Test
    public void equals() {
        PropertyName propertyName1 = new PropertyName("Marina Bay Residences");
        
        // same object -> returns true
        assertTrue(propertyName1.equals(propertyName1));
        
        // same values -> returns true
        PropertyName propertyName2 = new PropertyName("Marina Bay Residences");
        assertTrue(propertyName1.equals(propertyName2));
        
        // null -> returns false
        assertFalse(propertyName1.equals(null));
        
        // different type -> returns false
        assertFalse(propertyName1.equals(5));
        
        // different property name -> returns false
        PropertyName propertyName3 = new PropertyName("The Peak");
        assertFalse(propertyName1.equals(propertyName3));
    }

    @Test
    public void toString_returnsPropertyName() {
        String validPropertyName = "Marina Bay Residences";
        PropertyName propertyName = new PropertyName(validPropertyName);
        assertEquals(validPropertyName, propertyName.toString());
    }

    @Test
    public void hashCode_samePropertyName_sameHashCode() {
        PropertyName propertyName1 = new PropertyName("Marina Bay Residences");
        PropertyName propertyName2 = new PropertyName("Marina Bay Residences");
        assertEquals(propertyName1.hashCode(), propertyName2.hashCode());
    }
} 