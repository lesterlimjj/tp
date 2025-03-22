package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalListings.CONDO_A;
import static seedu.address.testutil.TypicalListings.CONDO_B;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.listing.exceptions.DuplicateListingException;
import seedu.address.model.listing.exceptions.ListingNotFoundException;
import seedu.address.testutil.ListingBuilder;

public class UniqueListingListTest {

    private final UniqueListingList uniqueListingList = new UniqueListingList();

    @Test
    public void contains_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.contains(null));
    }

    @Test
    public void contains_listingNotInList_returnsFalse() {
        assertFalse(uniqueListingList.contains(CONDO_A));
    }

    @Test
    public void contains_listingInList_returnsTrue() {
        uniqueListingList.add(CONDO_A);
        assertTrue(uniqueListingList.contains(CONDO_A));
    }

    @Test
    public void contains_listingWithSameIdentityFieldsInList_returnsTrue() {
        uniqueListingList.add(CONDO_A);
        Listing editedCondoA = new ListingBuilder(CONDO_A)
                .withPropertyName("The Alpine")
                .withPropertyType("Condo")
                .build();
        assertTrue(uniqueListingList.contains(editedCondoA));
    }

    @Test
    public void add_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.add(null));
    }

    @Test
    public void add_duplicateListing_throwsDuplicateListingException() {
        uniqueListingList.add(CONDO_A);
        assertThrows(DuplicateListingException.class, () -> uniqueListingList.add(CONDO_A));
    }

    @Test
    public void add_listingWithSameIdentityFields_throwsDuplicateListingException() {
        uniqueListingList.add(CONDO_A);
        Listing editedCondoA = new ListingBuilder(CONDO_A)
                .withPropertyName("The Alpine")
                .withPropertyType("Condo")
                .build();
        assertThrows(DuplicateListingException.class, () -> uniqueListingList.add(editedCondoA));
    }

    @Test
    public void setListing_nullTargetListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.setListing(null, CONDO_A));
    }

    @Test
    public void setListing_nullEditedListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.setListing(CONDO_A, null));
    }

    @Test
    public void setListing_targetListingNotInList_throwsListingNotFoundException() {
        assertThrows(ListingNotFoundException.class, () -> uniqueListingList.setListing(CONDO_A, CONDO_A));
    }

    @Test
    public void setListing_editedListingIsSameListing_success() {
        uniqueListingList.add(CONDO_A);
        uniqueListingList.setListing(CONDO_A, CONDO_A);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(CONDO_A);
        assertEquals(expectedUniqueListingList, uniqueListingList);
    }

    @Test
    public void setListing_editedListingHasSameIdentity_success() {
        uniqueListingList.add(CONDO_A);
        Listing editedCondoA = new ListingBuilder(CONDO_A)
                .withPropertyName("The Alpine")
                .withPropertyType("Condo")
                .build();
        uniqueListingList.setListing(CONDO_A, editedCondoA);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(editedCondoA);
        assertEquals(expectedUniqueListingList, uniqueListingList);
    }

    @Test
    public void setListing_editedListingHasDifferentIdentity_success() {
        uniqueListingList.add(CONDO_A);
        uniqueListingList.setListing(CONDO_A, CONDO_B);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(CONDO_B);
        assertEquals(expectedUniqueListingList, uniqueListingList);
    }

    @Test
    public void setListing_editedListingHasNonUniqueIdentity_throwsDuplicateListingException() {
        uniqueListingList.add(CONDO_A);
        uniqueListingList.add(CONDO_B);
        assertThrows(DuplicateListingException.class, () -> uniqueListingList.setListing(CONDO_A, CONDO_B));
    }

    @Test
    public void remove_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.remove(null));
    }

    @Test
    public void remove_listingDoesNotExist_throwsListingNotFoundException() {
        assertThrows(ListingNotFoundException.class, () -> uniqueListingList.remove(CONDO_A));
    }

    @Test
    public void remove_existingListing_removesListing() {
        uniqueListingList.add(CONDO_A);
        uniqueListingList.remove(CONDO_A);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        assertEquals(expectedUniqueListingList, uniqueListingList);
    }

    @Test
    public void setListings_nullUniqueListingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.setListings((UniqueListingList) null));
    }

    @Test
    public void setListings_uniqueListingList_replacesOwnListWithProvidedUniqueListingList() {
        uniqueListingList.add(CONDO_A);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(CONDO_B);
        uniqueListingList.setListings(expectedUniqueListingList);
        assertEquals(expectedUniqueListingList, uniqueListingList);
    }

    @Test
    public void setListings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueListingList.setListings((List<Listing>) null));
    }

    @Test
    public void setListings_list_replacesOwnListWithProvidedList() {
        uniqueListingList.add(CONDO_A);
        List<Listing> listingList = Collections.singletonList(CONDO_B);
        uniqueListingList.setListings(listingList);
        UniqueListingList expectedUniqueListingList = new UniqueListingList();
        expectedUniqueListingList.add(CONDO_B);
        assertEquals(expectedUniqueListingList, uniqueListingList);
    }

    @Test
    public void setListings_listWithDuplicateListings_throwsDuplicateListingException() {
        List<Listing> listWithDuplicateListings = Arrays.asList(CONDO_A, CONDO_A);
        assertThrows(DuplicateListingException.class, () -> uniqueListingList.setListings(listWithDuplicateListings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> 
                uniqueListingList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_hasNextAndNext_returnsCorrectElement() {
        uniqueListingList.add(CONDO_A);
        assertTrue(uniqueListingList.iterator().hasNext());
        assertEquals(CONDO_A, uniqueListingList.iterator().next());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(uniqueListingList.equals(uniqueListingList));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(uniqueListingList.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(uniqueListingList.equals(5));
    }

    @Test
    public void equals_differentListings_returnsFalse() {
        uniqueListingList.add(CONDO_A);
        UniqueListingList otherList = new UniqueListingList();
        otherList.add(CONDO_B);
        assertFalse(uniqueListingList.equals(otherList));
    }

    @Test
    public void equals_sameListings_returnsTrue() {
        uniqueListingList.add(CONDO_A);
        UniqueListingList otherList = new UniqueListingList();
        otherList.add(CONDO_A);
        assertTrue(uniqueListingList.equals(otherList));
    }

    @Test
    public void hashCode_sameListings_sameHashCode() {
        uniqueListingList.add(CONDO_A);
        UniqueListingList otherList = new UniqueListingList();
        otherList.add(CONDO_A);
        assertEquals(uniqueListingList.hashCode(), otherList.hashCode());
    }

    @Test
    public void toString_nonEmptyList_returnsString() {
        uniqueListingList.add(CONDO_A);
        String result = uniqueListingList.toString();
        assertTrue(result.contains(CONDO_A.toString()));
    }
} 