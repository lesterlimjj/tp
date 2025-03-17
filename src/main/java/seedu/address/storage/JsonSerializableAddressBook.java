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
        ArrayList<Person> tempPersonList = new ArrayList<>();

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();

            if (tempPersonList.contains(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }

            tempPersonList.add(person);
        }

        for (JsonAdaptedListing jsonAdaptedListing: listings) {
            Listing listing = jsonAdaptedListing.toModelType(tempPersonList);
            if (addressBook.hasListing(listing)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LISTING);
            }
            addressBook.addListing(listing);
        }

        for (Person tempPerson: tempPersonList) {
            ArrayList<Listing> listings = new ArrayList<>();
            for (Listing listing: addressBook.getListingList()) {
                if (listing.getOwners().contains(tempPerson)) {
                    listings.add(listing);
                }
            }
            Person person = new Person(
                    tempPerson.getName(),
                    tempPerson.getPhone(),
                    tempPerson.getEmail(),
                    tempPerson.getPropertyPreferences(),
                    listings);

            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }

            addressBook.addPerson(person);
        }

        for (JsonAdaptedTag jsonAdaptedTag: tags) {
            Tag tag = jsonAdaptedTag.toModelType();
            if (!TagRegistry.of().contains(tag)) {
                TagRegistry.of().add(tag);
            }
        }

        return addressBook;
    }

}
