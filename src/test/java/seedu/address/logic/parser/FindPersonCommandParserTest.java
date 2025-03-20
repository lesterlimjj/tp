package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.exceptions.CommandException;

public class FindPersonCommandParserTest {

    private FindPersonCommandParser parser = new FindPersonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        String expectedMessage = "ERROR: Missing parameters. You must provide at least one keyword.";
        assertParseFailure(parser, "     ", expectedMessage);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws CommandException {
        // no leading and trailing whitespaces
        List<String> expectedKeywords = Arrays.asList("Alice", "Bob");
        FindPersonCommand expectedFindPersonCommand = new FindPersonCommand(expectedKeywords);
        assertParseSuccess(parser, "Alice Bob", expectedFindPersonCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindPersonCommand);
    }
}
