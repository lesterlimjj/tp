package seedu.address.model.listing;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.price.PriceRange;

/**
 * Represents a Listing in the real estate system.
 * Guarantees: either unit number or house number is present and not null but not both,
 * rest of details are present and not null, field values are validated, immutable.
 */
public class Listing {

    // Identity fields
    private final PostalCode postalCode;
    private final UnitNumber unitNumber;
    private final HouseNumber houseNumber;

    // Data fields
    private final PriceRange priceRange;
    private final PropertyName propertyName;

    /**
     * Constructs an {@code Listing}.
     * Every field except for house number must be present and not null.
     * House number must be null.
     *
     * @param postalCode A valid postal code.
     * @param unitNumber A valid unit number.
     * @param priceRange A valid price range.
     */
    public Listing(PostalCode postalCode, UnitNumber unitNumber, PriceRange priceRange) {
        requireAllNonNull(postalCode, unitNumber, priceRange);
        this.postalCode = postalCode;
        this.unitNumber = unitNumber;
        this.houseNumber = null;
        this.priceRange = priceRange;
        this.propertyName = null;
    }

    /**
     * Constructs an {@code Listing}.
     * Every field except for house number must be present and not null.
     * House number must be null.
     *
     * @param postalCode A valid postal code.
     * @param houseNumber A valid house number.
     * @param priceRange A valid price range.
     */
    public Listing(PostalCode postalCode, HouseNumber houseNumber, PriceRange priceRange) {
        requireAllNonNull(postalCode, houseNumber, priceRange);
        this.postalCode = postalCode;
        this.unitNumber = null;
        this.houseNumber = houseNumber;
        this.priceRange = priceRange;
        this.propertyName = null;
    }

    /**
     * Constructs an {@code Listing}.
     * Every field except for house number must be present and not null.
     * House number must be null.
     *
     * @param postalCode A valid postal code.
     * @param unitNumber A valid unit number.
     * @param priceRange A valid price range.
     * @param propertyName A valid property name.
     */
    public Listing(PostalCode postalCode, UnitNumber unitNumber, PriceRange priceRange, PropertyName propertyName) {
        requireAllNonNull(postalCode, unitNumber, priceRange, propertyName);
        this.postalCode = postalCode;
        this.unitNumber = unitNumber;
        this.houseNumber = null;
        this.priceRange = priceRange;
        this.propertyName = propertyName;
    }

    /**
     * Constructs an {@code Listing}.
     * Every field except for house number must be present and not null.
     * House number must be null.
     *
     * @param postalCode A valid postal code.
     * @param houseNumber A valid house number.
     * @param priceRange A valid price range.
     * @param propertyName A valid property name.
     */
    public Listing(PostalCode postalCode, HouseNumber houseNumber, PriceRange priceRange, PropertyName propertyName) {
        requireAllNonNull(postalCode, houseNumber, priceRange, propertyName);
        this.postalCode = postalCode;
        this.unitNumber = null;
        this.houseNumber = houseNumber;
        this.priceRange = priceRange;
        this.propertyName = propertyName;
    }



    public PostalCode getPostalCode() {
        return postalCode;
    }

    public UnitNumber getUnitNumber() {
        return unitNumber;
    }

    public HouseNumber getHouseNumber() {
        return houseNumber;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public PropertyName getPropertyName() {
        return propertyName;
    }

    /**
     * Checks if two listings have the same unique identifiers.
     * This defines a weaker notion of equality between two listings.
     *
     * @param otherListing Listing to be compared with.
     * @return true if both listings have the same postal code, unit number and house number. false otherwise.
     */
    public boolean isSameListing(Listing otherListing) {
        if (otherListing == this) {
            return true;
        }

        return otherListing != null
                && otherListing.postalCode.equals(postalCode)
                && Objects.equals(unitNumber, otherListing.unitNumber)
                && Objects.equals(houseNumber, otherListing.houseNumber);
    }

    /**
     * Checks if two listings have the same identity and data fields and associations.
     * This defines a stronger notion of equality between two listings.
     *
     * @param other Object to be compared with.
     * @return true if both listings have the same identity and data fields and associations. false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Listing)) {
            return false;
        }

        Listing otherListing = (Listing) other;
        return postalCode.equals(otherListing.postalCode)
                && Objects.equals(unitNumber, otherListing.unitNumber)
                && Objects.equals(houseNumber, otherListing.houseNumber)
                && priceRange.equals(otherListing.priceRange)
                && propertyName.equals(otherListing.propertyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, unitNumber, houseNumber, priceRange, propertyName);
    }

    @Override
    public String toString() {

        ToStringBuilder stringBuilder = new ToStringBuilder(this)
                .add("postal code", postalCode);

        // Guarantee ensures that one of them is null, include the non-null field
        if (houseNumber == null) {

            stringBuilder.add("unit number", unitNumber);

        } else if (unitNumber == null) {

            stringBuilder.add("house number", houseNumber);

        }

        stringBuilder.add("price range", priceRange)
                .add("property name", propertyName);

        return stringBuilder.toString();
    }

}
