package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.listing.HouseNumber;
import seedu.address.model.listing.PostalCode;
import seedu.address.model.listing.PropertyName;
import seedu.address.model.listing.UnitNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.price.Price;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String postalCode} into a {@code PostalCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PostalCode} is invalid.
     */
    public static PostalCode parsePostalCode(String postalCode) throws ParseException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!PostalCode.isValidPostalCode(trimmedPostalCode)) {
            throw new ParseException(PostalCode.MESSAGE_CONSTRAINTS);
        }
        return new PostalCode(trimmedPostalCode);
    }

    /**
     * Parses a {@code String unitNumber} into a {@code UnitNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code UnitNumber} is invalid.
     */
    public static UnitNumber parseUnitNumber(String unitNumber) throws ParseException {
        if (unitNumber == null) {
            return null;
        }

        String trimmedUnitNumber = unitNumber.trim();
        if (!UnitNumber.isValidUnitNumber(trimmedUnitNumber)) {
            throw new ParseException(UnitNumber.MESSAGE_CONSTRAINTS);
        }
        return new UnitNumber(trimmedUnitNumber);
    }

    /**
     * Parses a {@code String houseNumber} into a {@code HouseNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code HouseNumber} is invalid.
     */
    public static HouseNumber parseHouseNumber(String houseNumber) throws ParseException {
        if (houseNumber == null) {
            return null;
        }

        String trimmedHouseNumber = houseNumber.trim();
        if (!HouseNumber.isValidHouseNumber(trimmedHouseNumber)) {
            throw new ParseException(HouseNumber.MESSAGE_CONSTRAINTS);
        }
        return new HouseNumber(trimmedHouseNumber);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PropertyName} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        if (price == null) {
            return null;
        }

        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code String propertyName} into a {@code PropertyName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PropertyName} is invalid.
     */
    public static PropertyName parsePropertyName(String propertyName) throws ParseException {
        if (propertyName == null) {
            return null;
        }

        requireNonNull(propertyName);
        String trimmedPropertyName = propertyName.trim();
        if (!PropertyName.isValidPropertyName(trimmedPropertyName)) {
            throw new ParseException(PropertyName.MESSAGE_CONSTRAINTS);
        }
        return new PropertyName(trimmedPropertyName);
    }

    /**
     * Parses a {@code String tag}. Creation of tag is handled later by tagRegistry when executing.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static String parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }

        return trimmedTag;
    }

    /**
     * Parses {@code Collection<String> tags}.
     */
    public static Set<String> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        Set<String> trimmedTagSet = new HashSet<>();
        for (String tagName : tags) {
            trimmedTagSet.add(parseTag(tagName));
        }
        return trimmedTagSet;
    }

}
