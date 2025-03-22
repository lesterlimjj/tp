package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class ListingTest {

    private static final PostalCode VALID_POSTAL_CODE = new PostalCode("123456");
    private static final UnitNumber VALID_UNIT_NUMBER = new UnitNumber("01-01");
    private static final HouseNumber VALID_HOUSE_NUMBER = new HouseNumber("123");
    private static final PriceRange VALID_PRICE_RANGE = new PriceRange("1000000-2000000");
    private static final PropertyName VALID_PROPERTY_NAME = new PropertyName("Test Property");
    private static final Set<Tag> VALID_TAGS = Collections.singleton(new Tag("test"));

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        // Unit number constructor
        assertThrows(NullPointerException.class, () -> new Listing(null, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Listing(VALID_POSTAL_CODE, null, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, null, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                null, VALID_TAGS, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, null, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, null));

        // House number constructor
        assertThrows(NullPointerException.class, () -> new Listing(null, VALID_HOUSE_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Listing(VALID_POSTAL_CODE, null, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Listing(VALID_POSTAL_CODE, VALID_HOUSE_NUMBER, null, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Listing(VALID_POSTAL_CODE, VALID_HOUSE_NUMBER, VALID_PRICE_RANGE, 
                null, VALID_TAGS, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Listing(VALID_POSTAL_CODE, VALID_HOUSE_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, null, Collections.emptyList()));
        assertThrows(NullPointerException.class, () -> new Listing(VALID_POSTAL_CODE, VALID_HOUSE_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, null));
    }

    @Test
    public void constructor_validUnit_success() {
        Listing listing = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        
        assertEquals(VALID_POSTAL_CODE, listing.getPostalCode());
        assertEquals(VALID_UNIT_NUMBER, listing.getUnitNumber());
        assertEquals(VALID_PRICE_RANGE, listing.getPriceRange());
        assertEquals(VALID_PROPERTY_NAME, listing.getPropertyName());
        assertEquals(VALID_TAGS, listing.getTags());
        assertTrue(listing.getOwners().isEmpty());
    }

    @Test
    public void constructor_validHouse_success() {
        Listing listing = new Listing(VALID_POSTAL_CODE, VALID_HOUSE_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        
        assertEquals(VALID_POSTAL_CODE, listing.getPostalCode());
        assertEquals(VALID_HOUSE_NUMBER, listing.getHouseNumber());
        assertEquals(VALID_PRICE_RANGE, listing.getPriceRange());
        assertEquals(VALID_PROPERTY_NAME, listing.getPropertyName());
        assertEquals(VALID_TAGS, listing.getTags());
        assertTrue(listing.getOwners().isEmpty());
    }

    @Test
    public void of_unitNumber_callsUnitConstructor() {
        Listing listing = Listing.of(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, null, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        
        assertEquals(VALID_UNIT_NUMBER, listing.getUnitNumber());
        assertFalse(listing.getHouseNumber().isPresent());
    }

    @Test
    public void of_houseNumber_callsHouseConstructor() {
        Listing listing = Listing.of(VALID_POSTAL_CODE, null, VALID_HOUSE_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        
        assertEquals(VALID_HOUSE_NUMBER, listing.getHouseNumber().get());
        assertFalse(listing.getUnitNumber().isPresent());
    }

    @Test
    public void of_bothNumbers_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> 
                Listing.of(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_HOUSE_NUMBER, VALID_PRICE_RANGE, 
                        VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList()));
    }

    @Test
    public void of_neitherNumbers_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> 
                Listing.of(VALID_POSTAL_CODE, null, null, VALID_PRICE_RANGE, 
                        VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList()));
    }

    @Test
    public void isSameListing() {
        // Same postal code and unit number
        Listing listing1 = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        Listing listing2 = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, new PriceRange("2000000-3000000"), 
                new PropertyName("Different Property"), new HashSet<>(), Collections.emptyList());
        
        assertTrue(listing1.isSameListing(listing2));
        
        // Different postal code
        Listing listing3 = new Listing(new PostalCode("654321"), VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        
        assertFalse(listing1.isSameListing(listing3));
        
        // Different unit number
        Listing listing4 = new Listing(VALID_POSTAL_CODE, new UnitNumber("02-02"), VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        
        assertFalse(listing1.isSameListing(listing4));
        
        // Same postal code and house number
        Listing listing5 = new Listing(VALID_POSTAL_CODE, VALID_HOUSE_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        Listing listing6 = new Listing(VALID_POSTAL_CODE, VALID_HOUSE_NUMBER, new PriceRange("2000000-3000000"), 
                new PropertyName("Different Property"), new HashSet<>(), Collections.emptyList());
        
        assertTrue(listing5.isSameListing(listing6));
        
        // Different house number
        Listing listing7 = new Listing(VALID_POSTAL_CODE, new HouseNumber("456"), VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        
        assertFalse(listing5.isSameListing(listing7));
        
        // Same object
        assertTrue(listing1.isSameListing(listing1));
        
        // Null
        assertFalse(listing1.isSameListing(null));
        
        // Different types (unit vs house)
        assertFalse(listing1.isSameListing(listing5));
    }

    @Test
    public void equals() {
        // Same postal code, unit number, and all other fields
        Listing listing1 = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        Listing listing2 = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        
        assertTrue(listing1.equals(listing2));
        
        // Same object
        assertTrue(listing1.equals(listing1));
        
        // Null
        assertFalse(listing1.equals(null));
        
        // Different type
        assertFalse(listing1.equals(5));
        
        // Different price range
        Listing listing3 = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, new PriceRange("2000000-3000000"), 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        
        assertFalse(listing1.equals(listing3));
        
        // Different property name
        Listing listing4 = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                new PropertyName("Different Property"), VALID_TAGS, Collections.emptyList());
        
        assertFalse(listing1.equals(listing4));
        
        // Different tags
        Set<Tag> differentTags = new HashSet<>();
        differentTags.add(new Tag("different"));
        Listing listing5 = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, differentTags, Collections.emptyList());
        
        assertFalse(listing1.equals(listing5));
    }

    @Test
    public void addOwner_validOwner_success() {
        Listing listing = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        Person owner = new Person(null, null, null, Collections.emptyList(), Collections.emptyList());
        
        int initialSize = listing.getOwners().size();
        listing.addOwner(owner);
        
        assertEquals(initialSize + 1, listing.getOwners().size());
        assertTrue(listing.getOwners().contains(owner));
    }

    @Test
    public void removeOwner_existingOwner_success() {
        Listing listing = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        Person owner = new Person(null, null, null, Collections.emptyList(), Collections.emptyList());
        
        listing.addOwner(owner);
        int initialSize = listing.getOwners().size();
        
        listing.removeOwner(owner);
        
        assertEquals(initialSize - 1, listing.getOwners().size());
        assertFalse(listing.getOwners().contains(owner));
    }

    @Test
    public void toString_containsAllFields() {
        Listing listing = new Listing(VALID_POSTAL_CODE, VALID_UNIT_NUMBER, VALID_PRICE_RANGE, 
                VALID_PROPERTY_NAME, VALID_TAGS, Collections.emptyList());
        
        String listingString = listing.toString();
        
        assertTrue(listingString.contains(VALID_POSTAL_CODE.toString()));
        assertTrue(listingString.contains(VALID_UNIT_NUMBER.toString()));
        assertTrue(listingString.contains(VALID_PRICE_RANGE.toString()));
        assertTrue(listingString.contains(VALID_PROPERTY_NAME.toString()));
    }
} 