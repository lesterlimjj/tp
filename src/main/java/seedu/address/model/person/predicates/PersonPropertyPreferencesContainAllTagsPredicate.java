package seedu.address.model.person.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

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
        return person.getPropertyPreferences().stream()
                .anyMatch(pref -> pref.getTags().stream()
                        .anyMatch(tag -> tagsToMatch.contains(tag.getTagName().toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PersonPropertyPreferencesContainAllTagsPredicate
                && tagsToMatch.equals(((PersonPropertyPreferencesContainAllTagsPredicate) other).tagsToMatch));
    }
}
