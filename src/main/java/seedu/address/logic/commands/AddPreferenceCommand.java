package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOWER_BOUND_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UPPER_BOUND_PRICE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.price.PriceRange;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagRegistry;

/**
 * Adds a {@code PropertyPreference} with the specified tags and new tags.
 */
public class AddPreferenceCommand extends Command {
    public static final String COMMAND_WORD = "addPreference";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new property preference with "
            + "specified tags to a person."
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LOWER_BOUND_PRICE + "NAME "
            + PREFIX_UPPER_BOUND_PRICE + "PHONE "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_NEW_TAG + "NEW_TAG]...\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_LOWER_BOUND_PRICE + "300000 "
            + PREFIX_UPPER_BOUND_PRICE + "600000 "
            + PREFIX_TAG + "quiet "
            + PREFIX_TAG + "pet-friendly "
            + PREFIX_NEW_TAG + "family-friendly "
            + PREFIX_NEW_TAG + "spacious";

    public static final String MESSAGE_SUCCESS = "New property preference added to %1$s";
    public static final String MESSAGE_DUPLICATE_TAGS = "At least one of the new tags given already exist.";
    public static final String MESSAGE_INVALID_TAGS = "At least one of the tags given does not exist.";

    private final Index index;
    private final PriceRange priceRange;
    private final Set<String> tagSet;
    private final Set<String> newTagSet;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AddPreferenceCommand(Index index, PriceRange priceRange, Set<String> tags,
                                Set<String> newTags) {
        requireNonNull(priceRange);
        this.index = index;
        this.priceRange = priceRange;
        tagSet = tags;
        newTagSet = newTags;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTags(tagSet)) {
            throw new CommandException(MESSAGE_INVALID_TAGS);
        }

        if (model.hasNewTags(newTagSet)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAGS);
        }
        model.addTags(newTagSet);

        PropertyPreference preference = new PropertyPreference(priceRange, new HashSet<>());
        PropertyPreference preferenceWithTags = createPreferenceWithTags(preference, tagSet, newTagSet, model);

        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddPreference = lastShownList.get(index.getZeroBased());
        PropertyPreference preferenceWithPerson = createPreferenceWithPerson(preferenceWithTags, personToAddPreference);
        Person personWithPreferenceAdded = createPersonWithAddedPreference(personToAddPreference, preferenceWithPerson);


        model.setPerson(personToAddPreference, personWithPreferenceAdded);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
            Messages.format(personWithPreferenceAdded, preferenceWithTags)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPreferenceCommand)) {
            return false;
        }

        AddPreferenceCommand otherAddPreferenceCommand = (AddPreferenceCommand) other;
        return priceRange.equals(otherAddPreferenceCommand.priceRange);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("priceRange", priceRange)
                .toString();
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private Person createPersonWithAddedPreference(Person person, PropertyPreference preference) {
        assert person != null;

        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        List<PropertyPreference> propertyPreferences = new ArrayList<>(person.getPropertyPreferences());
        propertyPreferences.add(preference);
        List<Listing> listings = new ArrayList<>(person.getListings());

        return new Person(name, phone, email, propertyPreferences, listings);
    }

    /**
     * Creates a new {@code PropertyPreference} with the specified tags and new tags.
     * The method combines the existing and new tags, creates {@code Tag} objects from the combined tags,
     * and associates them with a new {@code PropertyPreference}. The preference is then added to the model's
     * tag registry.
     */
    private PropertyPreference createPreferenceWithTags(PropertyPreference preference, Set<String> tagSet,
                                                       Set<String> newTagSet, Model model) {
        Set<String> combinedTags = new HashSet<>(tagSet);
        combinedTags.addAll(newTagSet);
        Set<Tag> tagList = new HashSet<>();
        TagRegistry tagRegistry = TagRegistry.of();

        for (String tag : combinedTags) {
            List<PropertyPreference> tagPropertyPreferences = new ArrayList<>();
            tagPropertyPreferences.add(preference);
            Tag tagToAdd = new Tag(tag, tagPropertyPreferences, new ArrayList<>());
            tagRegistry.setTag(tagToAdd, tagToAdd);

            tagList.add(tagToAdd);
        }

        PriceRange priceRange = preference.getPriceRange();
        PropertyPreference newPreference = new PropertyPreference(priceRange, tagList);
        model.addPreferenceToTags(combinedTags, newPreference);

        return newPreference;
    }

    /**
     * Creates a new {@code PropertyPreference} with the specified tags and new tags.
     * The method combines the existing and new tags, creates {@code Tag} objects from the combined tags,
     * and associates them with a new {@code PropertyPreference}. The preference is then added to the model's
     * tag registry.
     */
    private PropertyPreference createPreferenceWithPerson(PropertyPreference preference, Person person) {
        Set<Tag> tagList = new HashSet<>(preference.getTags());
        PriceRange priceRange = preference.getPriceRange();
        PropertyPreference newPreference = new PropertyPreference(priceRange, tagList, person);

        return newPreference;
    }
}
