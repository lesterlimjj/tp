package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.address.model.listing.Listing;
import seedu.address.model.person.PropertyPreference;

public class TagTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null, Collections.emptyList(), Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Tag("ValidTag", null, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Tag("ValidTag", Collections.emptyList(), null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "", "   ", "Tag*", "Tag@", "Tag!", "Tag#", "Tag$", "Tag%", "Tag^", 
            "VeryLongTagNameThatExceedsThirtyCharacters"})
    public void constructor_invalidTagName_throwsIllegalArgumentException(String invalidTagName) {
        assertThrows(IllegalArgumentException.class, () -> 
                new Tag(invalidTagName, Collections.emptyList(), Collections.emptyList()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Valid", "valid", "VALID", "Valid123", "123Valid", "Valid-Tag", "Valid_Tag", 
            "Valid.Tag", "Valid+Tag", "Valid&Tag", "Valid'Tag", "Valid Tag"})
    public void constructor_validTagName_success(String validTagName) {
        Tag tag = new Tag(validTagName, Collections.emptyList(), Collections.emptyList());
        assertEquals(validTagName.toUpperCase(), tag.tagName);
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName(" ")); // spaces only
        assertFalse(Tag.isValidTagName("a")); // single character
        assertFalse(Tag.isValidTagName("tag*")); // contains invalid character
        assertFalse(Tag.isValidTagName("VeryLongTagNameThatExceedsThirtyCharacters")); // too long

        // valid tag names
        assertTrue(Tag.isValidTagName("Valid"));
        assertTrue(Tag.isValidTagName("valid"));
        assertTrue(Tag.isValidTagName("VALID"));
        assertTrue(Tag.isValidTagName("Valid123"));
        assertTrue(Tag.isValidTagName("123Valid"));
        assertTrue(Tag.isValidTagName("Valid-Tag"));
        assertTrue(Tag.isValidTagName("Valid_Tag"));
        assertTrue(Tag.isValidTagName("Valid.Tag"));
        assertTrue(Tag.isValidTagName("Valid+Tag"));
        assertTrue(Tag.isValidTagName("Valid&Tag"));
        assertTrue(Tag.isValidTagName("Valid'Tag"));
        assertTrue(Tag.isValidTagName("Valid Tag"));
    }

    @Test
    public void getTagName() {
        Tag tag = new Tag("TestTag", Collections.emptyList(), Collections.emptyList());
        assertEquals("TESTTAG", tag.getTagName());
    }

    @Test
    public void propertyPreferences_modifyReturnedList_throwsUnsupportedOperationException() {
        Tag tag = new Tag("TestTag", Collections.emptyList(), Collections.emptyList());
        assertThrows(UnsupportedOperationException.class, () -> tag.getPropertyPreferences().add(null));
    }

    @Test
    public void listings_modifyReturnedList_throwsUnsupportedOperationException() {
        Tag tag = new Tag("TestTag", Collections.emptyList(), Collections.emptyList());
        assertThrows(UnsupportedOperationException.class, () -> tag.getListings().add(null));
    }

    @Test
    public void addPropertyPreference_validPreference_success() {
        Tag tag = new Tag("TestTag", Collections.emptyList(), Collections.emptyList());
        PropertyPreference preference = null; // Just for testing, not actually using the preference
        
        int initialSize = tag.getPropertyPreferences().size();
        tag.addPropertyPreference(preference);
        
        assertEquals(initialSize + 1, tag.getPropertyPreferences().size());
        assertTrue(tag.getPropertyPreferences().contains(preference));
    }

    @Test
    public void removePropertyPreference_existingPreference_success() {
        List<PropertyPreference> preferences = new ArrayList<>();
        PropertyPreference preference = null; // Just for testing, not actually using the preference
        preferences.add(preference);
        
        Tag tag = new Tag("TestTag", preferences, Collections.emptyList());
        
        int initialSize = tag.getPropertyPreferences().size();
        tag.removePropertyPreference(preference);
        
        assertEquals(initialSize - 1, tag.getPropertyPreferences().size());
        assertFalse(tag.getPropertyPreferences().contains(preference));
    }

    @Test
    public void addListing_validListing_success() {
        Tag tag = new Tag("TestTag", Collections.emptyList(), Collections.emptyList());
        Listing listing = null; // Just for testing, not actually using the listing
        
        int initialSize = tag.getListings().size();
        tag.addListing(listing);
        
        assertEquals(initialSize + 1, tag.getListings().size());
        assertTrue(tag.getListings().contains(listing));
    }

    @Test
    public void removeListing_existingListing_success() {
        List<Listing> listings = new ArrayList<>();
        Listing listing = null; // Just for testing, not actually using the listing
        listings.add(listing);
        
        Tag tag = new Tag("TestTag", Collections.emptyList(), listings);
        
        int initialSize = tag.getListings().size();
        tag.removeListing(listing);
        
        assertEquals(initialSize - 1, tag.getListings().size());
        assertFalse(tag.getListings().contains(listing));
    }

    @Test
    public void getNumPropertyPreferences() {
        List<PropertyPreference> preferences = new ArrayList<>();
        preferences.add(null); // Just for testing, not actually using the preference
        preferences.add(null);
        
        Tag tag = new Tag("TestTag", preferences, Collections.emptyList());
        
        assertEquals(2, tag.getNumPropertyPreferences());
    }

    @Test
    public void getNumListings() {
        List<Listing> listings = new ArrayList<>();
        listings.add(null); // Just for testing, not actually using the listing
        listings.add(null);
        listings.add(null);
        
        Tag tag = new Tag("TestTag", Collections.emptyList(), listings);
        
        assertEquals(3, tag.getNumListings());
    }

    @Test
    public void getNumUsage() {
        List<PropertyPreference> preferences = new ArrayList<>();
        preferences.add(null); // Just for testing, not actually using the preference
        preferences.add(null);
        
        List<Listing> listings = new ArrayList<>();
        listings.add(null); // Just for testing, not actually using the listing
        listings.add(null);
        listings.add(null);
        
        Tag tag = new Tag("TestTag", preferences, listings);
        
        assertEquals(5, tag.getNumUsage());
    }

    @Test
    public void equals() {
        Tag tag1 = new Tag("TestTag", Collections.emptyList(), Collections.emptyList());
        
        // Same object -> returns true
        assertTrue(tag1.equals(tag1));
        
        // Null -> returns false
        assertFalse(tag1.equals(null));
        
        // Different type -> returns false
        assertFalse(tag1.equals(5));
        
        // Same tag name, different case -> returns true (tag names are stored uppercase)
        Tag tag2 = new Tag("testtag", Collections.emptyList(), Collections.emptyList());
        assertTrue(tag1.equals(tag2));
        
        // Different tag name -> returns false
        Tag tag3 = new Tag("OtherTag", Collections.emptyList(), Collections.emptyList());
        assertFalse(tag1.equals(tag3));
    }

    @Test
    public void hashCode_sameTagName_sameHashCode() {
        Tag tag1 = new Tag("TestTag", Collections.emptyList(), Collections.emptyList());
        Tag tag2 = new Tag("TestTag", Collections.emptyList(), Collections.emptyList());
        
        assertEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void toString_containsTagName() {
        Tag tag = new Tag("TestTag", Collections.emptyList(), Collections.emptyList());
        
        String tagString = tag.toString();
        
        assertTrue(tagString.contains("TESTTAG"));
    }
}
