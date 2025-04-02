package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.search.SearchType;
import seedu.address.model.search.predicates.PersonPropertyPreferencesContainAllTagsPredicate;
import seedu.address.model.search.predicates.PropertyPreferencesContainAllActiveSearchTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Lists all {@code Person}(s) with {@code PropertyPreference}(s) that contains all
 * the specified {@code Tag}(s).
 * List excludes all {@code PropertyPreference}(s) that do not contain the specified {@code Tag}(s).
 */
public class SearchPersonByTagCommand extends Command {
    public static final String COMMAND_WORD = "searchPersonTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons with property preferences "
            + "containing all specified tags.\n"
            + "Parameters: " + PREFIX_TAG + "TAG [" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " t/gym t/pet-friendly";

    private final Set<String> tagsToSearch;

    /**
     * Constructs a {@code SearchListingByTagCommand} to list the {@code Person}(s) with {@code PropertyPreference}(s)
     * that contains all the specified {@code Tag}(s).
     *
     * @param tagsToSearch The tags to search by.
     */
    public SearchPersonByTagCommand(Set<String> tagsToSearch) {
        requireNonNull(tagsToSearch);
        this.tagsToSearch = tagsToSearch;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (tagsToSearch.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_SEARCH_PERSON_TAG_MISSING_PARAMS, MESSAGE_USAGE));
        }

        // Validate each tag exists
        for (String tagName : tagsToSearch) {
            if (!model.hasTag(tagName)) {
                throw new CommandException(String
                        .format(Messages.MESSAGE_TAG_DOES_NOT_EXIST, tagName, MESSAGE_USAGE));
            }
        }

        Set<Tag> activeTags = new HashSet<>();
        for (String tagName : tagsToSearch) {
            activeTags.add(model.getTag(tagName));
        }

        model.resetAllLists();
        model.setSearch(activeTags,
                null,
                SearchType.PERSON,
                new PropertyPreferencesContainAllActiveSearchTagsPredicate(activeTags));

        model.updateFilteredPersonList(new PersonPropertyPreferencesContainAllTagsPredicate(tagsToSearch));
        List<Person> filteredPersons = model.getSortedFilteredPersonList();

        if (filteredPersons.isEmpty()) {
            return new CommandResult(Messages.MESSAGE_SEARCH_PERSON_TAGS_NO_MATCH);
        } else {
            return new CommandResult(String.format(Messages.MESSAGE_SEARCH_PERSON_TAGS_SUCCESS,
                    filteredPersons.size()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SearchPersonByTagCommand
                && tagsToSearch.equals(((SearchPersonByTagCommand) other).tagsToSearch));
    }
}
