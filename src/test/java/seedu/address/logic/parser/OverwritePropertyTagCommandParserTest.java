package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_ARGUMENTS_EMPTY;
import static seedu.address.logic.Messages.MESSAGE_TAG_OR_NEW_TAG_REQUIRED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LISTING;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.OverwritePropertyTagCommand;

public class OverwritePropertyTagCommandParserTest {

    private static final String TAG_EMPTY = " ";
    private static final String VALID_TAG = "t/modern";
    private static final String VALID_NEW_TAG = "nt/beachfront";
    private static final String INVALID_TAG = "t/-invalid";
    private static final String INVALID_NEW_TAG = "nt/!invalid!";

    private OverwritePropertyTagCommandParser parser = new OverwritePropertyTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, TAG_EMPTY, 
                String.format(MESSAGE_ARGUMENTS_EMPTY, OverwritePropertyTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noTags_throwsParseException() {
        assertParseFailure(parser, "1", 
                String.format(MESSAGE_TAG_OR_NEW_TAG_REQUIRED, OverwritePropertyTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPreamble_throwsParseException() {
        assertParseFailure(parser, VALID_TAG, 
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OverwritePropertyTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValueFormat_throwsParseException() {
        // Invalid tag format
        assertParseFailure(parser, "1 " + INVALID_TAG, 
                String.format(Tag.MESSAGE_CONSTRAINTS));
        
        // Invalid new tag format
        assertParseFailure(parser, "1 " + INVALID_NEW_TAG, 
                String.format(Tag.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_validArgs_returnsOverwritePropertyTagCommand() {
        // Only existing tag
        Index targetIndex = INDEX_FIRST_LISTING;
        Set<String> tagSet = Collections.singleton("modern");
        Set<String> emptySet = new HashSet<>();
        
        OverwritePropertyTagCommand expectedCommand = 
                new OverwritePropertyTagCommand(targetIndex, tagSet, emptySet);
        assertParseSuccess(parser, "1 " + VALID_TAG, expectedCommand);
        
        // Only new tag
        Set<String> newTagSet = Collections.singleton("beachfront");
        
        OverwritePropertyTagCommand expectedCommand2 = 
                new OverwritePropertyTagCommand(targetIndex, emptySet, newTagSet);
        assertParseSuccess(parser, "1 " + VALID_NEW_TAG, expectedCommand2);
        
        // Both tags
        OverwritePropertyTagCommand expectedCommand3 = 
                new OverwritePropertyTagCommand(targetIndex, tagSet, newTagSet);
        assertParseSuccess(parser, "1 " + VALID_TAG + " " + VALID_NEW_TAG, expectedCommand3);
    }
} 