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
import seedu.address.model.person.Person;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;

/**
 * Overwrites all {@code Tag}(s) in a {@code PropertyPreference} of a {@code Person} with the specified
 * {@code Tag}(s).
 * The {@code PropertyPreference} is identified using it's displayed index within the {@code Person}'s preferences,
 * and the {@code Person} is identified using it's displayed index.
 */
public class OverwritePreferenceTagCommand extends Command {

    public static final String COMMAND_WORD = "overwritePreferenceTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Replaces all tags in an existing preference."
            + "\nParameters: "
            + "PERSON_INDEX (must be a positive integer) "
            + "PREFERENCE_INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]{1}... "
            + "[" + PREFIX_NEW_TAG + "NEW_TAG]{1}..."
            + "\nExample: "
            + COMMAND_WORD + " 3 3 " + PREFIX_TAG + "4-bedrooms " + PREFIX_TAG
            + "2-toilets " + PREFIX_NEW_TAG + "seaside view " + PREFIX_NEW_TAG + "beach";

    public static final String MESSAGE_SUCCESS = "Tag in preference overwritten with: %s";
    public static final String MESSAGE_INVALID_TAGS = "At least one of the tags given does not exist.\n%s";
    public static final String MESSAGE_DUPLICATE_TAGS = "At least one of the new tags given already exist.\n%s";

    private final Index targetPersonIndex;
    private final Index targetPreferenceIndex;
    private final Set<String> tagSet;
    private final Set<String> newTagSet;

    /**
     * Creates an @{code OverwritePreferenceTagCommand} to replace all {@code Tag})(s) in in the specified
     * {@code PropertyPreference} with the specified {@code Tag}(s).
     *
     * @param targetPersonIndex The index of the person in the filtered person list that the preference is located in.
     * @param targetPreferenceIndex The index of the preference to overwrite the tags of.
     * @param tagSet The set of existing tags to be added in the preference.
     * @param newTagSet The set of new tags to be added to the preference and to the unique tag map.
     */
    public OverwritePreferenceTagCommand(Index targetPersonIndex, Index targetPreferenceIndex, Set<String> tagSet,
                                      Set<String> newTagSet) {
        requireAllNonNull(targetPersonIndex, targetPreferenceIndex, tagSet, newTagSet);

        this.targetPersonIndex = targetPersonIndex;
        this.targetPreferenceIndex = targetPreferenceIndex;
        this.tagSet = tagSet;
        this.newTagSet = newTagSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person targetPerson = CommandUtil.getValidatedPerson(model, targetPersonIndex, MESSAGE_USAGE);
        PropertyPreference targetPreference = CommandUtil.getValidatedPreference(model, targetPerson,
                targetPreferenceIndex, MESSAGE_USAGE, false);
        CommandUtil.validateTags(model, tagSet, newTagSet, MESSAGE_USAGE,
                MESSAGE_INVALID_TAGS, MESSAGE_DUPLICATE_TAGS);
        updatePreferenceTags(model, targetPreference, targetPerson);
        return generateCommandResult(targetPreference);
    }

    /**
     * Updates the preference's tags with the new set of tags.
     */
    private void updatePreferenceTags(Model model, PropertyPreference preference, Person targetPerson) {
        model.addTags(newTagSet);
        Set<Tag> oldTags = new HashSet<>(preference.getTags());
        Set<Tag> newTags = new HashSet<>();

        // Remove all existing tags
        for (Tag tag : oldTags) {
            tag.removePropertyPreference(preference);
            model.setTag(tag, tag);
            preference.removeTag(tag);
        }

        // Add new tags
        for (String tagName : tagSet) {
            Tag tag = model.getTag(tagName);
            tag.addPropertyPreference(preference);
            model.setTag(tag, tag);
            preference.addTag(tag);
            newTags.add(tag);
        }

        for (String tagName : newTagSet) {
            Tag tag = model.getTag(tagName);
            tag.addPropertyPreference(preference);
            model.setTag(tag, tag);
            preference.addTag(tag);
            newTags.add(tag);
        }

        model.setPerson(targetPerson, targetPerson);
        model.resetAllLists();
    }

    private CommandResult generateCommandResult(PropertyPreference preference) {
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.formatTagsOnly(preference.getTags())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof OverwritePreferenceTagCommand
                && targetPersonIndex.equals(((OverwritePreferenceTagCommand) other).targetPersonIndex)
                && targetPreferenceIndex.equals(((OverwritePreferenceTagCommand) other).targetPreferenceIndex)
                && tagSet.equals(((OverwritePreferenceTagCommand) other).tagSet))
                && newTagSet.equals(((OverwritePreferenceTagCommand) other).newTagSet);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonIndex", targetPersonIndex)
                .add("targetPreferenceIndex", targetPreferenceIndex)
                .add("tagSet", tagSet)
                .add("newTagSet", newTagSet)
                .toString();
    }
}
