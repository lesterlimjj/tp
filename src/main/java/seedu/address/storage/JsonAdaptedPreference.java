package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagRegistry;

/**
 * Jackson-friendly version of a preference.
 */
class JsonAdaptedPreference {

    private final JsonAdaptedPriceRange priceRange;
    private final List<JsonAdaptedTag> tags;

    /**
     * Constructs a {@code JsonAdaptedPreference} with the given details.
     */
    @JsonCreator
    public JsonAdaptedPreference(@JsonProperty("priceRange") JsonAdaptedPriceRange priceRange,
                                 @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.priceRange = priceRange;
        this.tags = (tags != null) ? tags : new ArrayList<>();
    }

    /**
     * Converts a given {@code PriceRange} into this class for Jackson use.
     */
    public JsonAdaptedPreference(PropertyPreference source) {
        this.priceRange = new JsonAdaptedPriceRange(source.getPriceRange());
        this.tags = source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted preference object into the model's {@code PriceRange} object.
     */
    public PropertyPreference toModelType() throws IllegalValueException {
        if (priceRange == null) {
            throw new IllegalValueException("PropertyPreference's priceRange cannot be null.");
        }

        if (tags == null) {
            throw new IllegalValueException("PropertyPreference's tags cannot be null.");
        }

        return new PropertyPreference(priceRange.toModelType(), getModelTags());
    }

    private Set<Tag> getModelTags() throws IllegalValueException {
        Set<Tag> tagSet = new HashSet<>();
        for (JsonAdaptedTag jsonAdaptedTag : tags) {
            Tag tag = jsonAdaptedTag.toModelType();
            if (!TagRegistry.of().contains(tag)) {
                TagRegistry.of().add(tag);
            }
            tagSet.add(tag);
        }
        return tagSet;
    }
}
