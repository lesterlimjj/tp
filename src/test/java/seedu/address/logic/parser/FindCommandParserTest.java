package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.exceptions.CommandException;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        String expectedMessage = "ERROR: Missing parameters. You must provide at least one keyword.";
        assertParseFailure(parser, "     ", expectedMessage);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws CommandException {
        // no leading and trailing whitespaces
        List<String> expectedKeywords = Arrays.asList("Alice", "Bob");
        FindCommand expectedFindCommand = new FindCommand(expectedKeywords);
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }
}
