package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.listing.Listing;
import seedu.address.model.price.PriceRange;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Name validName = new Name("Valid Name");
        Phone validPhone = new Phone("12345678");
        Email validEmail = new Email("test@example.com");
        List<PropertyPreference> preferences = new ArrayList<>();
        List<Listing> listings = new ArrayList<>();

        assertThrows(NullPointerException.class, () -> 
                new Person(null, validPhone, validEmail, preferences, listings));
        assertThrows(NullPointerException.class, () -> 
                new Person(validName, null, validEmail, preferences, listings));
        assertThrows(NullPointerException.class, () -> 
                new Person(validName, validPhone, null, preferences, listings));
        assertThrows(NullPointerException.class, () -> 
                new Person(validName, validPhone, validEmail, null, listings));
        assertThrows(NullPointerException.class, () -> 
                new Person(validName, validPhone, validEmail, preferences, null));
    }

    @Test
    public void constructor_validData_success() {
        Name validName = new Name("Valid Name");
        Phone validPhone = new Phone("12345678");
        Email validEmail = new Email("test@example.com");
        List<PropertyPreference> preferences = new ArrayList<>();
        List<Listing> listings = new ArrayList<>();

        Person person = new Person(validName, validPhone, validEmail, preferences, listings);
        
        assertEquals(validName, person.getName());
        assertEquals(validPhone, person.getPhone());
        assertEquals(validEmail, person.getEmail());
        assertEquals(preferences, person.getPropertyPreferences());
        assertEquals(listings, person.getListings());
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // different phone -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name -> returns true (phone is the identity field)
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different email -> returns true (phone is the identity field)
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void propertyPreferences_modifyReturnedList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> 
                ALICE.getPropertyPreferences().add(new PropertyPreference(
                        new PriceRange("100000-200000"), Collections.emptySet(), ALICE)));
    }

    @Test
    public void addPropertyPreference_validPreference_success() {
        Person person = new PersonBuilder().build();
        PropertyPreference preference = new PropertyPreference(
                new PriceRange("100000-200000"), Collections.emptySet(), person);
        
        person.addPropertyPreference(preference);
        
        assertTrue(person.getPropertyPreferences().contains(preference));
    }

    @Test
    public void removePropertyPreference_existingPreference_success() {
        Person person = new PersonBuilder().build();
        PropertyPreference preference = new PropertyPreference(
                new PriceRange("100000-200000"), Collections.emptySet(), person);
        
        person.addPropertyPreference(preference);
        person.removePropertyPreference(preference);
        
        assertFalse(person.getPropertyPreferences().contains(preference));
    }

    @Test
    public void listings_modifyReturnedList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> 
                ALICE.getListings().add(null));
    }

    @Test
    public void addListing_validListing_success() {
        Person person = new PersonBuilder().build();
        Listing listing = BOB.getListings().isEmpty() ? null : BOB.getListings().get(0);
        
        if (listing != null) {
            person.addListing(listing);
            assertTrue(person.getListings().contains(listing));
        }
    }

    @Test
    public void removeListing_existingListing_success() {
        Person person = new PersonBuilder().build();
        Listing listing = BOB.getListings().isEmpty() ? null : BOB.getListings().get(0);
        
        if (listing != null) {
            person.addListing(listing);
            person.removeListing(listing);
            assertFalse(person.getListings().contains(listing));
        }
    }

    @Test
    public void hashCode_sameData_sameHashCode() {
        Person person1 = new PersonBuilder().withName("Alice Pauline")
                .withEmail("alice@example.com").withPhone("12345678").build();
        Person person2 = new PersonBuilder().withName("Alice Pauline")
                .withEmail("alice@example.com").withPhone("12345678").build();
                
        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void hashCode_differentData_differentHashCode() {
        Person person1 = new PersonBuilder().withName("Alice Pauline").build();
        Person person2 = new PersonBuilder().withName("Bob Choo").build();
                
        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void toString_containsAllFields() {
        String personString = ALICE.toString();
        
        assertTrue(personString.contains(ALICE.getName().toString()));
        assertTrue(personString.contains(ALICE.getPhone().toString()));
        assertTrue(personString.contains(ALICE.getEmail().toString()));
    }
}
