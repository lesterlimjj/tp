package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.price.PriceRange;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person's property preference in the real estate system.
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
     */
    public PropertyPreference(PriceRange priceRange, Set<Tag> tags, Person person) {
        requireAllNonNull(priceRange, tags, person);
        this.priceRange = priceRange;
        this.tags.addAll(tags);
        this.person = person;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void addTag(Tag toAdd) {
        this.tags.add(toAdd);
    }

    public void removeTag(Tag toDelete) {
        this.tags.remove(toDelete);
    }

    /**
     * Checks whether this {@code PropertyPreference} is to be shown in the UI.
     *
     * @return True if preference contains all the specified search tags (case-insensitive), or if searchTags is empty.
     */
    public boolean isActive() {

        boolean tagMatches = tags.stream().anyMatch(tag -> Tag
                .getActiveSearchTags()
                .containsKey(tag.tagName));

        return tagMatches || Tag.getActiveSearchTags().isEmpty();
    }

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
