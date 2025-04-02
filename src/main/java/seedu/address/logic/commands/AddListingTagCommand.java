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
 * Adds {@code Tag} to a {@code Listing} in the address book.
 * The {@code Listing} is identified using it's displayed index.
 */
public class AddListingTagCommand extends Command {
    public static final String COMMAND_WORD = "addListingTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tags to listing "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_NEW_TAG + "NEW_TAG]...\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_TAG + "quiet "
            + PREFIX_TAG + "pet-friendly "
            + PREFIX_NEW_TAG + "family-friendly "
            + PREFIX_NEW_TAG + "spacious";

    public static final String MESSAGE_SUCCESS = "Adds tags to listing %1$s";
    public static final String MESSAGE_DUPLICATE_TAGS = "At least one of the new tags given already exist.\n%1$s";
    public static final String MESSAGE_INVALID_TAGS = "At least one of the tags given does not exist.\n%1$s";
    public static final String MESSAGE_DUPLICATE_TAGS_IN_LISTING = "At least one of the "
            + "tags given already exist in the listing.\n%1$s";

    private final Index targetListingIndex;
    private final Set<String> tagSet;
    private final Set<String> newTagSet;

    /**
     * Creates an {@code AddListingTagCommand} to add the specified {@code Tag}(s) to the specified {@code Listing}.
     *
     * @param targetListingIndex The index of the listing in the filtered listing list to add tags to.
     * @param tagSet The set of existing tags to be added to the listing.
     * @param newTagSet The set of tags to be added to the listing and to the unique tag map.
     */
    public AddListingTagCommand(Index targetListingIndex, Set<String> tagSet, Set<String> newTagSet) {
        requireAllNonNull(targetListingIndex, tagSet, newTagSet);
        this.targetListingIndex = targetListingIndex;
        this.tagSet = tagSet;
        this.newTagSet = newTagSet;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTags(tagSet)) {
            throw new CommandException(String.format(MESSAGE_INVALID_TAGS, MESSAGE_USAGE));
        }

        if (model.hasNewTags(newTagSet)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAGS, MESSAGE_USAGE));
        }
        model.addTags(newTagSet);
        Listing listingToAddTags = CommandUtil.getValidatedListing(model, targetListingIndex, MESSAGE_USAGE);
        Set<String> tagNames = new HashSet<>(tagSet);
        Set<Tag> tags = new HashSet<>();
        tagNames.addAll(newTagSet);

        for (String tagName : tagNames) {
            Tag tag = model.getTag(tagName);
            if (listingToAddTags.getTags().contains(tag)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_TAGS_IN_LISTING, MESSAGE_USAGE));
            }
            tags.add(tag);
        }

        for (Tag tag : tags) {
            tag.addListing(listingToAddTags);
            model.setTag(tag, tag);
            listingToAddTags.addTag(tag);
        }

        model.setListing(listingToAddTags, listingToAddTags);
        model.resetAllLists();

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(listingToAddTags.getTags(), listingToAddTags)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddListingTagCommand)) {
            return false;
        }

        AddListingTagCommand otherAddListingTagCommand = (AddListingTagCommand) other;
        return targetListingIndex.equals(otherAddListingTagCommand.targetListingIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetListingIndex", targetListingIndex)
                .toString();
    }

}
