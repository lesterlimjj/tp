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

    public static void addSampleTags(AddressBook addressBook) {
        addressBook.addTag("hdb");
        addressBook.addTag("condo");
        addressBook.addTag("bungalow");
    }

    public static void addSamplePersons(AddressBook addressBook) {
        Tag hdbTag = addressBook.getTagMap().get("HDB");

        Person alexYeoh = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new ArrayList<>(), new ArrayList<>());

        PropertyPreference alexYeohPreference = new PropertyPreference(
                new PriceRange(new Price("100000"), new Price("200000")),
                Set.of(hdbTag),
                alexYeoh);

        alexYeoh.addPropertyPreference(alexYeohPreference);
        hdbTag.addPropertyPreference(alexYeohPreference);
        addressBook.addPerson(alexYeoh);

        Person berniceYu = new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new ArrayList<>(), new ArrayList<>());
        addressBook.addPerson(berniceYu);

        Person charlotteOliveiro = new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"), new ArrayList<>(), new ArrayList<>());
        addressBook.addPerson(charlotteOliveiro);

        Person davidLi = new Person(new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new ArrayList<>(), new ArrayList<>());
        addressBook.addPerson(davidLi);

        Person irfanIbrahim = new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new ArrayList<>(), new ArrayList<>());
        addressBook.addPerson(irfanIbrahim);

        Person royBalakrishnan = new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"),
                new ArrayList<>(), new ArrayList<>());
        addressBook.addPerson(royBalakrishnan);

    }

    public static void addSampleListings(AddressBook addressBook) {
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

        Listing sunnyResidences = new Listing(
                new PostalCode("123456"),
                new UnitNumber("10-15"),
                new PriceRange(new Price("1000000"), new Price("2000000")),
                new PropertyName("Sunny Residences"),
                condoTagSet,
                List.of(owners.get(1)),
                true
        );
        condoTag.addListing(sunnyResidences);
        addressBook.addListing(sunnyResidences);
        owners.get(1).addListing(sunnyResidences);
        addressBook.setPerson(owners.get(1), owners.get(1));

        Listing hdbFlat = new Listing(
                new PostalCode("238877"),
                new HouseNumber("42B"),
                new PriceRange(new Price("100000"), new Price("200000")),
                null,
                hdbTagSet,
                new ArrayList<>(),
                true
        );
        hdbTag.addListing(hdbFlat);
        addressBook.addListing(hdbFlat);

        Listing marinaBaySuites = new Listing(
                new PostalCode("543210"),
                new UnitNumber("35-01"),
                new PriceRange(new Price("2000000"), new Price("5000000")),
                new PropertyName("Marina Bay Suites"),
                condoTagSet,
                new ArrayList<>(),
                false
        );
        condoTag.addListing(marinaBaySuites);
        addressBook.addListing(marinaBaySuites);

        Listing gardenVillas = new Listing(
                new PostalCode("159753"),
                new HouseNumber("12"),
                new PriceRange(new Price("500000"), new Price("600000")),
                new PropertyName("Garden Villas"),
                bungalowTagSet,
                new ArrayList<>(),
                true
        );
        bungalowTag.addListing(gardenVillas);
        addressBook.addListing(gardenVillas);

        Listing condoUnit = new Listing(
                new PostalCode("987654"),
                new UnitNumber("05-22"),
                new PriceRange(new Price("400000"), new Price("800000")),
                null,
                condoTagSet,
                List.of(owners.get(2), owners.get(3)),
                true
        );

        condoTag.addListing(condoUnit);
        addressBook.addListing(condoUnit);
        owners.get(2).addListing(condoUnit);
        addressBook.setPerson(owners.get(2), owners.get(2));
        owners.get(3).addListing(condoUnit);
        addressBook.setPerson(owners.get(3), owners.get(3));


        addressBook.setTag(condoTag, condoTag);
        addressBook.setTag(hdbTag, hdbTag);
        addressBook.setTag(bungalowTag, bungalowTag);
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();

        addSampleTags(sampleAb);
        addSamplePersons(sampleAb);
        addSampleListings(sampleAb);

        return sampleAb;
    }
}
