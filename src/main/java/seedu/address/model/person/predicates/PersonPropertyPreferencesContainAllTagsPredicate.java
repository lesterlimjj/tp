package seedu.address.model.person.predicates;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s property preferences contain all the specified tags.
 */
public class PersonPropertyPreferencesContainAllTagsPredicate implements Predicate<Person> {
    private final Set<String> tagsToMatch;

    public PersonPropertyPreferencesContainAllTagsPredicate(Set<String> tagsToMatch) {
        this.tagsToMatch = tagsToMatch;
    }

    @Override
    public boolean test(Person person) {
        Set<String> preferenceTags = person.getPropertyPreferences().stream()
                .flatMap(listing -> listing.getTags().stream())
                .map(Tag::getTagName)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return preferenceTags.containsAll(tagsToMatch.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PersonPropertyPreferencesContainAllTagsPredicate
                && tagsToMatch.equals(((PersonPropertyPreferencesContainAllTagsPredicate) other).tagsToMatch));
    }
}
