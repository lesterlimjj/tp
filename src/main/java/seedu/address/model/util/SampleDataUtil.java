package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.listing.HouseNumber;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.PostalCode;
import seedu.address.model.listing.PropertyName;
import seedu.address.model.listing.UnitNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.price.Price;
import seedu.address.model.price.PriceRange;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Set<String> getSampleTagStrings() {
        Set<String> tags = new HashSet<>();
        tags.add("hdb");
        tags.add("condo");
        tags.add("bungalow");
        return tags;
    }

    public static List<Person> getSamplePersons(AddressBook addressBook) {
        List<Person> persons = new ArrayList<>();
        Tag hdbTag = addressBook.getTagMap().get("HDB");

        Person alexYeoh = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new ArrayList<>(), new ArrayList<>());

        PropertyPreference alexYeohPreference = new PropertyPreference(
                new PriceRange(new Price("100000"), new Price("200000")),
                Set.of(hdbTag),
                alexYeoh);

        alexYeoh.addPropertyPreference(alexYeohPreference);
        hdbTag.addPropertyPreference(alexYeohPreference);
        persons.add(alexYeoh);

        Person berniceYu = new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new ArrayList<>(), new ArrayList<>());
        persons.add(berniceYu);

        Person charlotteOliveiro = new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"), new ArrayList<>(), new ArrayList<>());
        persons.add(charlotteOliveiro);

        Person davidLi = new Person(new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new ArrayList<>(), new ArrayList<>());
        persons.add(davidLi);

        Person irfanIbrahim = new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new ArrayList<>(), new ArrayList<>());
        persons.add(irfanIbrahim);

        Person royBalakrishnan = new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"),
                new ArrayList<>(), new ArrayList<>());
        persons.add(royBalakrishnan);

        return persons;
    }

    public static List<Listing> getSampleListings(AddressBook addressBook) throws IllegalValueException {
        List<Listing> listings = new ArrayList<>();

        Tag condoTag = addressBook.getTagMap().get("CONDO");
        Tag hdbTag = addressBook.getTagMap().get("HDB");
        Tag bungalowTag = addressBook.getTagMap().get("BUNGALOW");

        HashSet<Tag> condoTagSet = new HashSet<>();
        condoTagSet.add(condoTag);

        HashSet<Tag> hdbTagSet = new HashSet<>();
        hdbTagSet.add(hdbTag);

        HashSet<Tag> bungalowTagSet = new HashSet<>();
        bungalowTagSet.add(bungalowTag);

        List<Person> owners = addressBook.getPersonList();

        Listing sunnyResidences = Listing.of(
                new PostalCode("123456"),
                new UnitNumber("10-15"),
                null,
                new PriceRange(new Price("1000000"), new Price("2000000")),
                new PropertyName("Sunny Residences"),
                condoTagSet,
                List.of(owners.get(1)),
                true
        );
        condoTag.addListing(sunnyResidences);
        listings.add(sunnyResidences);

        Listing hdbFlat = Listing.of(
                new PostalCode("238877"),
                null,
                new HouseNumber("42B"),
                new PriceRange(new Price("100000"), new Price("200000")),
                null,
                hdbTagSet,
                new ArrayList<>(),
                true
        );
        hdbTag.addListing(hdbFlat);
        listings.add(hdbFlat);

        Listing marinaBaySuites = Listing.of(
                new PostalCode("543210"),
                new UnitNumber("35-01"),
                null,
                new PriceRange(new Price("2000000"), new Price("5000000")),
                new PropertyName("Marina Bay Suites"),
                condoTagSet,
                new ArrayList<>(),
                false
        );
        condoTag.addListing(marinaBaySuites);
        listings.add(marinaBaySuites);

        Listing gardenVillas = Listing.of(
                new PostalCode("159753"),
                null,
                new HouseNumber("12"),
                new PriceRange(new Price("500000"), new Price("600000")),
                new PropertyName("Garden Villas"),
                bungalowTagSet,
                new ArrayList<>(),
                true
        );
        bungalowTag.addListing(gardenVillas);
        listings.add(gardenVillas);

        Listing condoUnit = Listing.of(
                new PostalCode("987654"),
                new UnitNumber("05-22"),
                null,
                new PriceRange(new Price("400000"), new Price("800000")),
                null,
                condoTagSet,
                List.of(owners.get(2), owners.get(3)),
                true
        );
        condoTag.addListing(condoUnit);
        listings.add(condoUnit);

        addressBook.setTag(condoTag, condoTag);
        addressBook.setTag(hdbTag, hdbTag);
        addressBook.setTag(bungalowTag, bungalowTag);

        for (Person owner : owners) {
            for (Listing listing : listings) {
                if (listing.getOwners().contains(owner)) {
                    owner.addListing(listing);
                }
            }
            addressBook.setPerson(owner, owner);
        }

        return listings;
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();

        sampleAb.addTags(getSampleTagStrings());

        for (Person samplePerson : getSamplePersons(sampleAb)) {
            sampleAb.addPerson(samplePerson);
        }

        try {
            for (Listing sampleListing : getSampleListings(sampleAb)) {
                sampleAb.addListing(sampleListing);
            }
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }

        return sampleAb;
    }
}
