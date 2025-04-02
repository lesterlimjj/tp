package seedu.address.model.tag.exceptions;

/**
 * Signals that the operation will result in duplicate {@code Tag}s, which are considered duplicates if they have the
 * same case-insensitive names.
 */
public class DuplicateTagException extends RuntimeException {
    public DuplicateTagException() {
        super("Operation would result in duplicate tags");
    }
}
