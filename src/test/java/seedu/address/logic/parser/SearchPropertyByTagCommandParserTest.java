package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_SEARCH_PROPERTY_TAG_PREFIX_EMPTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchPropertyByTagCommand;

public class SearchPropertyByTagCommandParserTest {

    private final SearchPropertyByTagCommandParser parser = new SearchPropertyByTagCommandParser();

    @Test
    public void parse_validInput_success() {
        Set<String> expectedTags = Set.of("pet-friendly", "pool");
        String userInput = " t/pool t/pet-friendly";
        SearchPropertyByTagCommand expectedCommand = new SearchPropertyByTagCommand(expectedTags);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String userInput = "random words without prefix";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchPropertyByTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankTag_failure() {
        String userInput = " t/";
        assertParseFailure(parser, userInput, MESSAGE_SEARCH_PROPERTY_TAG_PREFIX_EMPTY);
    }

    @Test
    public void parse_emptyArgs_failure() {
        String userInput = " ";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchPropertyByTagCommand.MESSAGE_USAGE));
    }
}
