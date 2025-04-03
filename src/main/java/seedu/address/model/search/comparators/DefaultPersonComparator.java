package seedu.address.model.search.comparators;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Compares two {@code Person}s based on the following criteria:
 * 1. Name (primary)
 * 2. Phone Number (secondary)
 * If two {@code Person}s have the same postal code, they will then be ordered by their phone number.
 */
public class DefaultPersonComparator implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {
        requireAllNonNull(person1, person1);

        int nameCompare = person1.getName().fullName.compareTo(person2.getName().fullName);

        if (nameCompare != 0) {
            return nameCompare;
        }

        return person1.getPhone().value.compareTo(person2.getPhone().value);
    }
}
