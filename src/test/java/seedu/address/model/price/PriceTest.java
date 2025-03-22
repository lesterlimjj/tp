package seedu.address.model.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        String nullPrice = null;
        // Price constructor includes a special case for null
        // where it tries to parse null as a number, resulting in NPE
        assertThrows(NullPointerException.class, () -> new Price(nullPrice));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "abc", "-100", "100.123", "100,000", "1 000", "+200", "$400"
    })
    public void constructor_invalidPrice_throwsIllegalArgumentException(String invalidPrice) {
        assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "100", "0", "1", "999999", "10.50", "0.99", "123456.78"
    })
    public void constructor_validPrice_success(String validPrice) {
        Price price = new Price(validPrice);
        assertEquals(Float.parseFloat(validPrice), price.price);
    }

    @Test
    public void isValidPrice() {
        // null price
        // Note that isValidPrice treats null as valid for special reasons in the app context
        assertTrue(Price.isValidPrice(null)); 

        // invalid price
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("price")); // non-numeric
        assertFalse(Price.isValidPrice("9p")); // alphabets within digits
        assertFalse(Price.isValidPrice("9 12")); // spaces within digits
        assertFalse(Price.isValidPrice("-10")); // negative number
        assertFalse(Price.isValidPrice("+10")); // explicit positive sign
        assertFalse(Price.isValidPrice("$10")); // currency symbol
        assertFalse(Price.isValidPrice("10.123")); // more than 2 decimal places
        assertFalse(Price.isValidPrice("10,000")); // commas in number
        assertFalse(Price.isValidPrice(".50")); // no whole number part

        // valid price
        assertTrue(Price.isValidPrice("1")); // minimal example
        assertTrue(Price.isValidPrice("0")); // zero
        assertTrue(Price.isValidPrice("123456789")); // long number
        assertTrue(Price.isValidPrice("10.5")); // 1 decimal place
        assertTrue(Price.isValidPrice("10.50")); // 2 decimal places
    }

    @Test
    public void compare_success() {
        Price price1 = new Price("100");
        Price price2 = new Price("200");
        Price price3 = new Price("100");
        Price price4 = new Price("99.99");
        Price price5 = new Price("100.01");
        
        // equal
        assertEquals(0, price1.compare(price3));
        
        // less than
        assertEquals(-1, price1.compare(price2));
        assertEquals(-1, price4.compare(price1));
        
        // greater than
        assertEquals(1, price2.compare(price1));
        assertEquals(1, price5.compare(price1));
    }

    @Test
    public void equals() {
        Price price = new Price("100");

        // same object -> returns true
        assertTrue(price.equals(price));

        // same values -> returns true
        Price priceCopy = new Price("100");
        assertTrue(price.equals(priceCopy));
        
        // different types -> returns false
        assertFalse(price.equals(100f));
        
        // null -> returns false
        assertFalse(price.equals(null));
        
        // different price -> returns false
        Price differentPrice = new Price("200");
        assertFalse(price.equals(differentPrice));
    }

    @Test
    public void toString_success() {
        Price price = new Price("100");
        assertEquals("$100.0", price.toString());
        
        Price priceWithDecimal = new Price("99.99");
        assertEquals("$99.99", priceWithDecimal.toString());
    }

    @Test
    public void hashCode_success() {
        Price price1 = new Price("100");
        Price price2 = new Price("100");
        Price price3 = new Price("200");
        
        // same value -> same hash code
        assertEquals(price1.hashCode(), price2.hashCode());
        
        // different value -> different hash code
        assertNotEquals(price1.hashCode(), price3.hashCode());
    }
} 