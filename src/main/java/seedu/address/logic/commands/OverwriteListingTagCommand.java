package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CommandUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.tag.Tag;

/**
 * Overwrites all {@code Tag}s in a {@code Listing} identified using it's displayed index in the address book.
 */
public class OverwriteListingTagCommand extends Command {

    public static final String COMMAND_WORD = "overwriteListingTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Replaces all tags in an existing listing "
            + "identified by the index number used in the displayed listing list.\n"
            + "Parameters: LISTING_INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_NEW_TAG + "NEW_TAG]...\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_TAG + "2-bedrooms\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_NEW_TAG + "2-toilets\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_NEW_TAG + "pet-friendly "
            + PREFIX_NEW_TAG + "integrated cooling\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_TAG + "4-bedrooms " + PREFIX_TAG
            + "2-toilets " + PREFIX_NEW_TAG + "seaside view " + PREFIX_NEW_TAG + "beach";

    public static final String MESSAGE_SUCCESS = "Tag in listing%s overwritten with: %s";
    public static final String MESSAGE_INVALID_TAGS = "At least one of the tags given does not exist.\n%s";
    public static final String MESSAGE_DUPLICATE_TAGS = "At least one of the new tags given already exist.\n%s";

    private final Index listingIndex;
    private final Set<String> tagSet;
    private final Set<String> newTagSet;

    /**
     * Creates an @{code OverwriteListingTagCommand} to replace all {@code Tag}s in {@code Listing}.
     *
     * @param listingIndex The index of the listing in which tags will be overwritten.
     * @param tagSet The set of existing tags to be used in the listing.
     * @param newTagSet The set of new tags to be created and used in the listing.
     */
    public OverwriteListingTagCommand(Index listingIndex, Set<String> tagSet, Set<String> newTagSet) {
        requireAllNonNull(listingIndex, tagSet, newTagSet);

        this.listingIndex = listingIndex;
        this.tagSet = tagSet;
        this.newTagSet = newTagSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Listing listing = CommandUtil.getValidatedListing(model, listingIndex, MESSAGE_USAGE);
        validateTags(model);
        updateListingTags(model, listing);
        return generateCommandResult(listing);
    }


    /**
     * Validates that all existing tags exist and new tags don't exist.
     */
    private void validateTags(Model model) throws CommandException {
        if (!model.hasTags(tagSet)) {
            throw new CommandException(String.format(MESSAGE_INVALID_TAGS, MESSAGE_USAGE));
        }
        if (model.hasNewTags(newTagSet)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAGS, MESSAGE_USAGE));
        }
    }

    /**
     * Updates the listing's tags by removing existing ones and adding new ones.
     */
    private void updateListingTags(Model model, Listing listing) {
        // Create new tags
        model.addTags(newTagSet);

        Set<String> tagNames = new HashSet<>(tagSet);
        Set<Tag> newTags = prepareNewTags(model, tagNames);

        removeExistingTags(model, listing);
        addNewTags(model, listing, newTags);

        model.setListing(listing, listing);
        model.resetAllLists();
    }

    /**
     * Prepares the set of new tags to be added to the listing.
     */
    private Set<Tag> prepareNewTags(Model model, Set<String> tagNames) {
        tagNames.addAll(newTagSet);
        Set<Tag> newTags = new HashSet<>();
        for (String tagName : tagNames) {
            Tag tag = model.getTag(tagName);
            newTags.add(tag);
        }
        return newTags;
    }

    /**
     * Removes all existing tags from the listing.
     */
    private void removeExistingTags(Model model, Listing listing) {
        Set<Tag> existingTags = new HashSet<>(listing.getTags());
        for (Tag tag : existingTags) {
            tag.removeListing(listing);
            model.setTag(tag, tag);
            listing.removeTag(tag);
        }
    }

    /**
     * Adds new tags to the listing.
     */
    private void addNewTags(Model model, Listing listing, Set<Tag> newTags) {
        for (Tag tag : newTags) {
            tag.addListing(listing);
            model.setTag(tag, tag);
            listing.addTag(tag);
        }
    }

    /**
     * Generates the command result with formatted listing details.
     */
    private CommandResult generateCommandResult(Listing listing) {
        String listingDetails = Messages.format(listing);
        Set<Tag> newTags = listing.getTags();
        return new CommandResult(String.format(MESSAGE_SUCCESS, listingDetails, Messages.formatTagsOnly(newTags)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof OverwriteListingTagCommand
                && listingIndex.equals(((OverwriteListingTagCommand) other).listingIndex)
                && tagSet.equals(((OverwriteListingTagCommand) other).tagSet))
                && newTagSet.equals(((OverwriteListingTagCommand) other).newTagSet);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("listingIndex", listingIndex)
                .add("tags", tagSet)
                .add("newTags", newTagSet)
                .toString();
    }
}
