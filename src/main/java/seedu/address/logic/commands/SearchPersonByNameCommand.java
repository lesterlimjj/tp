package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Represents a command to that lists all {@code Person}(s) whose names match any of the given keyword(s).
 * Keyword matching is case-insensitive and must adhere to a valid name format.
 */
public class SearchPersonByNameCommand extends Command {

    public static final String COMMAND_WORD = "searchPersonName";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons whose names match the given keyword(s). "
            + "Keywords must contain only letters, spaces, hyphens, full stops, or apostrophes.\n"
            + "Every keyword can only start with a letter.\n"
            + "Parameters: " + COMMAND_WORD + " KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Alex Yeoh";

    public static final String VALID_NAME_REGEX = "^[A-Za-z][A-Za-z' .-]{0,49}$";
    public static final Pattern VALID_NAME_PATTERN = Pattern.compile(VALID_NAME_REGEX);

    private final List<String> keywords;

    /**
     * Constructs a @{code SearchPersonByNameCommand} to find {@code Person}(s) with the given keywords.
     *
     * @param keywords The list of keywords to match against names.
     */
    public SearchPersonByNameCommand(List<String> keywords) {
        requireAllNonNull(keywords);
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Person> personPredicate = person -> keywords.stream()
                .anyMatch(keyword -> person.getName().fullName.toLowerCase().contains(keyword.toLowerCase()));

        model.resetAllLists();
        model.updateFilteredPersonList(personPredicate);

        int count = model.getSortedFilteredPersonList().size();

        if (count == 0) {
            return new CommandResult("No persons found matching the keywords.");
        }
        return new CommandResult(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, count));
    }

    /**
     * Checks if another object is equal to this SearchPersonByNameCommand.
     *
     * @param other Object to compare with.
     * @return true if the other object is a SearchPersonByNameCommand with the same keywords, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SearchPersonByNameCommand)) {
            return false;
        }

        SearchPersonByNameCommand otherSearchPersonByNameCommand = (SearchPersonByNameCommand) other;
        return keywords.equals(otherSearchPersonByNameCommand.keywords);
    }

    /**
     * Returns a string representation of the SearchPersonByNameCommand.
     *
     * @return String representation of the command with keywords.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
