package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagRegistry;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_LISTING = "Listings list contains duplicate listing(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedListing> listings = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();


    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("listings") List<JsonAdaptedListing> listings,
                                       @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.persons.addAll(persons);
        this.listings.addAll(listings);
        this.tags.addAll(tags);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).toList());
        listings.addAll(source.getListingList().stream().map(JsonAdaptedListing::new).toList());
        tags.addAll(TagRegistry.of().asUnmodifiableObservableMap().keySet().stream().map(JsonAdaptedTag::new).toList());
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        ArrayList<Person> tempPersonList = getTempPersonList();

        addListings(addressBook, tempPersonList);
        updatePersonsAndListings(addressBook, tempPersonList);

        addTags(addressBook);

        return addressBook;
    }

    /**
     * Creates and returns a list of Person objects from the JSON-adapted persons.
     *
     * @return A list of Person objects.
     * @throws IllegalValueException If there are duplicate persons.
     */
    private ArrayList<Person> getTempPersonList() throws IllegalValueException {
        ArrayList<Person> tempPersonList = new ArrayList<>();

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();

            if (tempPersonList.contains(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }

            tempPersonList.add(person);
        }

        return tempPersonList;
    }

    /**
     * Creates Listing objects from the JSON-adapted listings and adds them to the AddressBook.
     *
     * @param addressBook The AddressBook to add the listings to.
     * @param tempPersonList The list of Person objects to link to the listings.
     * @throws IllegalValueException If there are duplicate listings.
     */
    private void addListings(AddressBook addressBook, ArrayList<Person> tempPersonList)
            throws IllegalValueException {
        for (JsonAdaptedListing jsonAdaptedListing : listings) {
            Listing listing = jsonAdaptedListing.toModelType(tempPersonList);
            if (addressBook.hasListing(listing)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LISTING);
            }
            addressBook.addListing(listing);
        }
    }

    /**
     * Updates the Person objects with the Listings they own and updates the Listing objects
     * to reference the updated Person objects.
     *
     * @param addressBook The AddressBook containing the listings.
     * @param tempPersonList The list of Person objects to update.
     * @throws IllegalValueException If there are duplicate persons.
     */
    private void updatePersonsAndListings(AddressBook addressBook, ArrayList<Person> tempPersonList)
            throws IllegalValueException {
        for (Person tempPerson : tempPersonList) {
            ArrayList<Listing> ownedListings = getOwnedListings(addressBook, tempPerson);

            Person updatedPerson = new Person(tempPerson.getName(), tempPerson.getPhone(), tempPerson.getEmail(),
                    tempPerson.getPropertyPreferences(), ownedListings);

            if (addressBook.hasPerson(updatedPerson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }

            addressBook.addPerson(updatedPerson);
            updateListingsWithNewPerson(addressBook, tempPerson, updatedPerson);
        }
    }

    /**
     * Returns a list of Listings owned by the given Person.
     *
     * @param addressBook The AddressBook containing the listings.
     * @param person The Person to check for owned listings.
     * @return A list of Listings owned by the Person.
     */
    private ArrayList<Listing> getOwnedListings(AddressBook addressBook, Person person) {
        ArrayList<Listing> ownedListings = new ArrayList<>();
        for (Listing listing : addressBook.getListingList()) {
            if (listing.getOwners().contains(person)) {
                ownedListings.add(listing);
            }
        }
        return ownedListings;
    }

    /**
     * Updates the Listing objects to reference the updated Person objects.
     *
     * @param addressBook The AddressBook containing the listings.
     * @param oldPerson The original Person object to replace.
     * @param newPerson The updated Person object to use.
     */
    private void updateListingsWithNewPerson(AddressBook addressBook, Person oldPerson, Person newPerson) {
        for (Listing listing : addressBook.getListingList()) {
            if (listing.getOwners().contains(oldPerson)) {
                List<Person> updatedOwners = new ArrayList<>(listing.getOwners());
                updatedOwners.remove(oldPerson);
                updatedOwners.add(newPerson);

                Listing updatedListing = Listing.of(listing.getPostalCode(), listing.getUnitNumber(),
                        listing.getHouseNumber(), listing.getPriceRange(), listing.getPropertyName(),
                        listing.getTags(), updatedOwners);

                addressBook.setListing(listing, updatedListing);
            }
        }
    }

    /**
     * Adds tags from the JSON-adapted tags to the TagRegistry.
     */
    private void addTags(AddressBook addressBook) throws IllegalValueException {
        for (JsonAdaptedTag jsonAdaptedTag : tags) {
            Tag tag = jsonAdaptedTag.toModelType();
            if (!TagRegistry.of().contains(tag)) {
                TagRegistry.of().add(tag);
            }
        }


        for (Person person: addressBook.getPersonList()){
            for (PropertyPreference propertyPreference: person.getPropertyPreferences()){
                for (Tag tag: propertyPreference.getTags()){
                    TagRegistry.of().addPropertyPreferenceToTag(tag.getTagName(), propertyPreference);
                }
            }
        }

        for (Listing listing: addressBook.getListingList()){
            for (Tag tag: listing.getTags()){
                TagRegistry.of().addListingToTag(tag.getTagName(), listing);
            }
        }

    }

}
