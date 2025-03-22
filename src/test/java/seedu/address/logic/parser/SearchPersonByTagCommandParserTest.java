package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SearchPersonByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SearchPersonByTagCommandParserTest {

    private final SearchPersonByTagCommandParser parser = new SearchPersonByTagCommandParser();

    @Test
    public void parse_validTags_success() throws Exception {
        SearchPersonByTagCommand command = parser.parse(" t/gym t/pool");
        assertEquals(new SearchPersonByTagCommand(Set.of("gym", "pool")), command);
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_missingTagValue_throwsParseException() {
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(" t/"));
        assertEquals(Messages.MESSAGE_SEARCH_PERSON_TAG_PREFIX_EMPTY, thrown.getMessage());
    }

    @Test
    public void parse_tagsWithSpaces_trimmedAndParsed() throws Exception {
        SearchPersonByTagCommand command = parser.parse(" t/ gym t/ pool ");
        assertEquals(new SearchPersonByTagCommand(Set.of("gym", "pool")), command);
    }
}
