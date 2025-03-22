package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.price.PriceRange;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PropertyPreferenceTest {

    private static final PriceRange VALID_PRICE_RANGE = new PriceRange("1000000-2000000");
    private static final Set<Tag> VALID_TAGS = Collections.singleton(new Tag("test"));
    private Person VALID_PERSON;

    @BeforeEach
    public void setUp() {
        VALID_PERSON = new PersonBuilder().build();
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PropertyPreference(null, VALID_TAGS, VALID_PERSON));
        assertThrows(NullPointerException.class, () -> new PropertyPreference(VALID_PRICE_RANGE, null, VALID_PERSON));
        assertThrows(NullPointerException.class, () -> new PropertyPreference(VALID_PRICE_RANGE, VALID_TAGS, null));
    }

    @Test
    public void constructor_validData_success() {
        PropertyPreference preference = new PropertyPreference(VALID_PRICE_RANGE, VALID_TAGS, VALID_PERSON);
        
        assertEquals(VALID_PRICE_RANGE, preference.getPriceRange());
        assertEquals(VALID_TAGS, preference.getTags());
        assertEquals(VALID_PERSON, preference.getPerson());
    }

    @Test
    public void getTags_modifyReturnedSet_throwsUnsupportedOperationException() {
        PropertyPreference preference = new PropertyPreference(VALID_PRICE_RANGE, VALID_TAGS, VALID_PERSON);
        assertThrows(UnsupportedOperationException.class, () -> preference.getTags().add(new Tag("invalid")));
    }

    @Test
    public void addTag_validTag_success() {
        PropertyPreference preference = new PropertyPreference(VALID_PRICE_RANGE, Collections.emptySet(), VALID_PERSON);
        Tag tag = new Tag("valid");
        
        preference.addTag(tag);
        
        assertTrue(preference.getTags().contains(tag));
    }

    @Test
    public void removeTag_existingTag_success() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag("toRemove");
        tags.add(tag);
        
        PropertyPreference preference = new PropertyPreference(VALID_PRICE_RANGE, tags, VALID_PERSON);
        
        preference.removeTag(tag);
        
        assertFalse(preference.getTags().contains(tag));
    }

    @Test
    public void removeTag_nonExistingTag_noEffect() {
        PropertyPreference preference = new PropertyPreference(VALID_PRICE_RANGE, Collections.emptySet(), VALID_PERSON);
        Tag tag = new Tag("nonExistent");
        
        preference.removeTag(tag);
        
        assertFalse(preference.getTags().contains(tag));
    }

    @Test
    public void hashCode_sameData_sameHashCode() {
        PropertyPreference preference1 = new PropertyPreference(VALID_PRICE_RANGE, VALID_TAGS, VALID_PERSON);
        PropertyPreference preference2 = new PropertyPreference(VALID_PRICE_RANGE, VALID_TAGS, VALID_PERSON);
        
        assertEquals(preference1.hashCode(), preference2.hashCode());
    }

    @Test
    public void toString_containsRelevantInformation() {
        PropertyPreference preference = new PropertyPreference(VALID_PRICE_RANGE, VALID_TAGS, VALID_PERSON);
        String preferenceString = preference.toString();
        
        assertTrue(preferenceString.contains(VALID_PRICE_RANGE.toString()));
        assertTrue(preferenceString.contains("tags"));
    }
    
    @Test
    public void multipleTags_addedCorrectly() {
        Set<Tag> multiTags = new HashSet<>();
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        multiTags.add(tag1);
        
        PropertyPreference preference = new PropertyPreference(VALID_PRICE_RANGE, multiTags, VALID_PERSON);
        
        // Add more tags after construction
        preference.addTag(tag2);
        preference.addTag(tag3);
        
        assertTrue(preference.getTags().contains(tag1));
        assertTrue(preference.getTags().contains(tag2));
        assertTrue(preference.getTags().contains(tag3));
        assertEquals(3, preference.getTags().size());
    }
} 