package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.price.PriceRange;
import seedu.address.model.tag.Tag;

/**
 * Represents a {@code Person}'s property preference in the real estate system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * Associations are mutable.
 */
public class PropertyPreference {

    // Data fields
    private final PriceRange priceRange;

    // Associations
    private final Set<Tag> tags = new HashSet<>();
    private Person person;


    /**
     * Constructs a {@code PropertyPreference}.
     * Every field must be present and not null.
     *
     * @param priceRange A valid price range.
     * @param tags A valid set of tags.
     * @param person A valid person.
     */
    public PropertyPreference(PriceRange priceRange, Set<Tag> tags, Person person) {
        requireAllNonNull(priceRange, tags, person);
        this.priceRange = priceRange;
        this.tags.addAll(tags);
        this.person = person;
    }

    // Getters
    public PriceRange getPriceRange() {
        return priceRange;
    }

    public Person getPerson() {
        return person;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    // Setters for associations
    /**
     * Sets the person associated with this property preference.
     * Can be updated due to immutable updating of data fields creating a new person.
     *
     * @param person The person to associate with this property preference.
     */
    public void setPerson(Person person) {
        requireNonNull(person);

        this.person = person;
    }

    /**
     * Adds a tag to the property preference.
     *
     * @param toAdd A valid tag.
     */
    public void addTag(Tag toAdd) {
        requireNonNull(toAdd);

        this.tags.add(toAdd);
    }

    /**
     * Removes a tag from the property preference.
     *
     * @param toDelete A valid tag.
     */
    public void removeTag(Tag toDelete) {
        requireNonNull(toDelete);

        this.tags.remove(toDelete);
    }

    // Miscellaneous
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(priceRange);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("price range", priceRange)
                .add("tags", tags)
                .toString();
    }
}
