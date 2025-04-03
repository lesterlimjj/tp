package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.search.SearchType;
import seedu.address.model.search.predicates.ListingContainsAllTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Lists all {@code Listing}(s) that contains all the specified {@code Tag}(s).
 */
public class SearchListingByTagCommand extends Command {
    public static final String COMMAND_WORD = "searchListingTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds listings with all specified tags."
            + "\nParameters: "
            + PREFIX_TAG
            + "TAG..."
            + "\nExample: "
            + COMMAND_WORD + " t/pet-friendly t/pool";
    private static final Logger logger = LogsCenter.getLogger(SearchListingByTagCommand.class);

    private final Set<String> tagsToSearch;

    /**
     * Constructs a {@code SearchListingByTagCommand} to list the {@code Listing}(s) that contains all the specified
     * {@code Tag}(s).
     *
     * @param tagsToSearch The tags to search by.
     */
    public SearchListingByTagCommand(Set<String> tagsToSearch) {
        requireAllNonNull(tagsToSearch);
        this.tagsToSearch = tagsToSearch;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing SearchListingByTagCommand with tags: " + tagsToSearch);

        if (tagsToSearch.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_SEARCH_PROPERTY_TAG_MISSING_PARAMS,
                    MESSAGE_USAGE));
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
                SearchType.LISTING,
                Model.PREDICATE_SHOW_ALL_PROPERTY_PREFERENCES);

        ListingContainsAllTagsPredicate predicate = new ListingContainsAllTagsPredicate(tagsToSearch);
        model.updateFilteredListingList(predicate);

        List<Listing> filteredListings = model.getSortedFilteredListingList();

        if (filteredListings.isEmpty()) {
            return new CommandResult(Messages.MESSAGE_SEARCH_PROPERTY_TAGS_NO_MATCH);
        } else {
            return new CommandResult(String.format(Messages.MESSAGE_SEARCH_PROPERTY_TAGS_SUCCESS,
                    filteredListings.size()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SearchListingByTagCommand
                && tagsToSearch.equals(((SearchListingByTagCommand) other).tagsToSearch));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tagsToSearch", tagsToSearch)
                .toString();
    }
}
