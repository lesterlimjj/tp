package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.COMPARATOR_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;

import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class ListPersonCommand extends Command {

    public static final String COMMAND_WORD = "listPerson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all Persons\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Tag.setActiveSearchTags(new ArrayList<>());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateSortedFilteredPersonList(COMPARATOR_SHOW_ALL_PERSONS); // Trigger re-render
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
