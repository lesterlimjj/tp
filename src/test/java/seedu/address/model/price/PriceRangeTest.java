package seedu.address.model.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PriceRangeTest {

    @Test
    public void constructor_nullBoundary_success() {
        // Test no-arg constructor (unbounded)
        PriceRange priceRange = new PriceRange();
        assertNull(priceRange.lowerBoundPrice);
        assertNull(priceRange.upperBoundPrice);
    }

    @Test
    public void constructor_singleBoundary_null_throwsNullPointerException() {
        // Test single boundary constructor with null price
        Price nullPrice = null;
        assertThrows(NullPointerException.class, () -> new PriceRange(nullPrice, true));
        assertThrows(NullPointerException.class, () -> new PriceRange(nullPrice, false));
    }

    @Test
    public void constructor_singleBoundary_success() {
        // Test single boundary constructor with upper bound
        Price price = new Price("100");
        PriceRange priceRangeUpperBound = new PriceRange(price, true);
        assertNull(priceRangeUpperBound.lowerBoundPrice);
        assertEquals(price, priceRangeUpperBound.upperBoundPrice);

        // Test single boundary constructor with lower bound
        PriceRange priceRangeLowerBound = new PriceRange(price, false);
        assertEquals(price, priceRangeLowerBound.lowerBoundPrice);
        assertNull(priceRangeLowerBound.upperBoundPrice);
    }

    @Test
    public void constructor_doubleBoundary_nullLowerBound_throwsNullPointerException() {
        Price nullPrice = null;
        Price validPrice = new Price("100");
        assertThrows(NullPointerException.class, () -> new PriceRange(nullPrice, validPrice));
    }

    @Test
    public void constructor_doubleBoundary_nullUpperBound_throwsNullPointerException() {
        Price validPrice = new Price("100");
        Price nullPrice = null;
        assertThrows(NullPointerException.class, () -> new PriceRange(validPrice, nullPrice));
    }

    @Test
    public void constructor_doubleBoundary_lowerGreaterThanUpper_throwsIllegalArgumentException() {
        Price lowerPrice = new Price("200");
        Price upperPrice = new Price("100");
        assertThrows(IllegalArgumentException.class, () -> new PriceRange(lowerPrice, upperPrice));
    }

    @Test
    public void constructor_doubleBoundary_success() {
        Price lowerPrice = new Price("100");
        Price upperPrice = new Price("200");
        PriceRange priceRange = new PriceRange(lowerPrice, upperPrice);
        assertEquals(lowerPrice, priceRange.lowerBoundPrice);
        assertEquals(upperPrice, priceRange.upperBoundPrice);

        // Equal bounds should be valid
        Price samePrice = new Price("100");
        PriceRange samePriceRange = new PriceRange(samePrice, samePrice);
        assertEquals(samePrice, samePriceRange.lowerBoundPrice);
        assertEquals(samePrice, samePriceRange.upperBoundPrice);
    }

    @Test
    public void isPriceWithinRange_nullPrice_throwsNullPointerException() {
        PriceRange priceRange = new PriceRange();
        Price nullPrice = null;
        assertThrows(NullPointerException.class, () -> priceRange.isPriceWithinRange(nullPrice));
    }

    @Test
    public void isPriceWithinRange_unboundedRange_returnsTrue() {
        // Unbounded range accepts any price
        PriceRange unboundedRange = new PriceRange();
        Price anyPrice = new Price("100");
        assertTrue(unboundedRange.isPriceWithinRange(anyPrice));

        // Should also work with very high or low prices
        Price veryHighPrice = new Price("999999");
        Price veryLowPrice = new Price("0");
        assertTrue(unboundedRange.isPriceWithinRange(veryHighPrice));
        assertTrue(unboundedRange.isPriceWithinRange(veryLowPrice));
    }

    @Test
    public void isPriceWithinRange_upperBoundedOnly_success() {
        Price upperBoundPrice = new Price("100");
        PriceRange upperBoundedRange = new PriceRange(upperBoundPrice, true);

        // Price below upper bound should be within range
        Price belowUpperPrice = new Price("99.99");
        assertTrue(upperBoundedRange.isPriceWithinRange(belowUpperPrice));

        // Price equal to upper bound should be within range
        Price equalToUpperPrice = new Price("100");
        assertTrue(upperBoundedRange.isPriceWithinRange(equalToUpperPrice));

        // Price above upper bound should not be within range
        Price aboveUpperPrice = new Price("100.01");
        assertFalse(upperBoundedRange.isPriceWithinRange(aboveUpperPrice));
    }

    @Test
    public void isPriceWithinRange_lowerBoundedOnly_success() {
        Price lowerBoundPrice = new Price("100");
        PriceRange lowerBoundedRange = new PriceRange(lowerBoundPrice, false);

        // Price above lower bound should be within range
        Price aboveLowerPrice = new Price("100.01");
        assertTrue(lowerBoundedRange.isPriceWithinRange(aboveLowerPrice));

        // Price equal to lower bound should be within range
        Price equalToLowerPrice = new Price("100");
        assertTrue(lowerBoundedRange.isPriceWithinRange(equalToLowerPrice));

        // Price below lower bound should not be within range
        Price belowLowerPrice = new Price("99.99");
        assertFalse(lowerBoundedRange.isPriceWithinRange(belowLowerPrice));
    }

    @Test
    public void isPriceWithinRange_doubleBounded_success() {
        Price lowerBoundPrice = new Price("100");
        Price upperBoundPrice = new Price("200");
        PriceRange boundedRange = new PriceRange(lowerBoundPrice, upperBoundPrice);

        // Price within bounds should be within range
        Price withinPrice = new Price("150");
        assertTrue(boundedRange.isPriceWithinRange(withinPrice));

        // Price equal to lower bound should be within range
        Price equalToLowerPrice = new Price("100");
        assertTrue(boundedRange.isPriceWithinRange(equalToLowerPrice));

        // Price equal to upper bound should be within range
        Price equalToUpperPrice = new Price("200");
        assertTrue(boundedRange.isPriceWithinRange(equalToUpperPrice));

        // Price below lower bound should not be within range
        Price belowLowerPrice = new Price("99.99");
        assertFalse(boundedRange.isPriceWithinRange(belowLowerPrice));

        // Price above upper bound should not be within range
        Price aboveUpperPrice = new Price("200.01");
        assertFalse(boundedRange.isPriceWithinRange(aboveUpperPrice));
    }

    @Test
    public void doPriceRangeOverlap_nullPriceRange_throwsNullPointerException() {
        PriceRange priceRange = new PriceRange();
        PriceRange nullPriceRange = null;
        assertThrows(NullPointerException.class, () -> priceRange.doPriceRangeOverlap(nullPriceRange));
    }

    @Test
    public void doPriceRangeOverlap_unboundedFirstRange_returnsTrue() {
        // Unbounded range overlaps with any other range
        PriceRange unboundedRange = new PriceRange();
        
        // Other range is also unbounded
        PriceRange otherUnboundedRange = new PriceRange();
        assertTrue(unboundedRange.doPriceRangeOverlap(otherUnboundedRange));
        
        // Other range is bounded 
        Price lowerPrice = new Price("100");
        Price upperPrice = new Price("200");
        PriceRange boundedRange = new PriceRange(lowerPrice, upperPrice);
        assertTrue(unboundedRange.doPriceRangeOverlap(boundedRange));
        
        // Other range is single-bounded
        PriceRange upperBoundedRange = new PriceRange(upperPrice, true);
        assertTrue(unboundedRange.doPriceRangeOverlap(upperBoundedRange));
        
        PriceRange lowerBoundedRange = new PriceRange(lowerPrice, false);
        assertTrue(unboundedRange.doPriceRangeOverlap(lowerBoundedRange));
    }

    @Test
    public void doPriceRangeOverlap_upperBoundedFirstRange_success() {
        Price upperPrice = new Price("200");
        PriceRange upperBoundedRange = new PriceRange(upperPrice, true);
        
        // Second range is below upper bound
        Price lowerPrice1 = new Price("100");
        Price upperPrice1 = new Price("150");
        PriceRange belowRange = new PriceRange(lowerPrice1, upperPrice1);
        assertTrue(upperBoundedRange.doPriceRangeOverlap(belowRange));
        
        // Second range straddles upper bound
        Price lowerPrice2 = new Price("150");
        Price upperPrice2 = new Price("250");
        PriceRange straddlingRange = new PriceRange(lowerPrice2, upperPrice2);
        assertTrue(upperBoundedRange.doPriceRangeOverlap(straddlingRange));
        
        // Second range is entirely above upper bound
        Price lowerPrice3 = new Price("250");
        Price upperPrice3 = new Price("300");
        PriceRange aboveRange = new PriceRange(lowerPrice3, upperPrice3);
        assertFalse(upperBoundedRange.doPriceRangeOverlap(aboveRange));
    }

    @Test
    public void doPriceRangeOverlap_lowerBoundedFirstRange_success() {
        Price lowerPrice = new Price("200");
        PriceRange lowerBoundedRange = new PriceRange(lowerPrice, false);
        
        // Second range is entirely below lower bound
        Price lowerPrice1 = new Price("100");
        Price upperPrice1 = new Price("150");
        PriceRange belowRange = new PriceRange(lowerPrice1, upperPrice1);
        assertFalse(lowerBoundedRange.doPriceRangeOverlap(belowRange));
        
        // Second range straddles lower bound
        Price lowerPrice2 = new Price("150");
        Price upperPrice2 = new Price("250");
        PriceRange straddlingRange = new PriceRange(lowerPrice2, upperPrice2);
        assertTrue(lowerBoundedRange.doPriceRangeOverlap(straddlingRange));
        
        // Second range is entirely above lower bound
        Price lowerPrice3 = new Price("250");
        Price upperPrice3 = new Price("300");
        PriceRange aboveRange = new PriceRange(lowerPrice3, upperPrice3);
        assertTrue(lowerBoundedRange.doPriceRangeOverlap(aboveRange));
    }

    @Test
    public void doPriceRangeOverlap_doubleBoundedFirstRange_success() {
        Price lowerPrice = new Price("200");
        Price upperPrice = new Price("400");
        PriceRange boundedRange = new PriceRange(lowerPrice, upperPrice);
        
        // Second range is entirely below bounded range
        Price lowerPrice1 = new Price("100");
        Price upperPrice1 = new Price("150");
        PriceRange belowRange = new PriceRange(lowerPrice1, upperPrice1);
        assertFalse(boundedRange.doPriceRangeOverlap(belowRange));
        
        // Second range overlaps at lower boundary
        Price lowerPrice2 = new Price("150");
        Price upperPrice2 = new Price("250");
        PriceRange lowerOverlapRange = new PriceRange(lowerPrice2, upperPrice2);
        assertTrue(boundedRange.doPriceRangeOverlap(lowerOverlapRange));
        
        // Second range is entirely within bounded range
        Price lowerPrice3 = new Price("250");
        Price upperPrice3 = new Price("350");
        PriceRange withinRange = new PriceRange(lowerPrice3, upperPrice3);
        assertTrue(boundedRange.doPriceRangeOverlap(withinRange));
        
        // Second range overlaps at upper boundary
        Price lowerPrice4 = new Price("350");
        Price upperPrice4 = new Price("450");
        PriceRange upperOverlapRange = new PriceRange(lowerPrice4, upperPrice4);
        assertTrue(boundedRange.doPriceRangeOverlap(upperOverlapRange));
        
        // Second range is entirely above bounded range
        Price lowerPrice5 = new Price("450");
        Price upperPrice5 = new Price("500");
        PriceRange aboveRange = new PriceRange(lowerPrice5, upperPrice5);
        assertFalse(boundedRange.doPriceRangeOverlap(aboveRange));
        
        // Second range entirely contains bounded range
        Price lowerPrice6 = new Price("100");
        Price upperPrice6 = new Price("500");
        PriceRange containingRange = new PriceRange(lowerPrice6, upperPrice6);
        assertTrue(boundedRange.doPriceRangeOverlap(containingRange));
    }

    @Test
    public void toString_unboundedRange_returnsAnyPrice() {
        PriceRange unboundedRange = new PriceRange();
        assertEquals("Any Price", unboundedRange.toString());
    }

    @Test
    public void toString_upperBoundedRange_returnsUpTo() {
        Price upperPrice = new Price("200");
        PriceRange upperBoundedRange = new PriceRange(upperPrice, true);
        assertEquals("Up to $200.0", upperBoundedRange.toString());
    }

    @Test
    public void toString_lowerBoundedRange_returnsFrom() {
        Price lowerPrice = new Price("200");
        PriceRange lowerBoundedRange = new PriceRange(lowerPrice, false);
        assertEquals("From $200.0", lowerBoundedRange.toString());
    }

    @Test
    public void toString_doubleBoundedRange_returnsFromTo() {
        Price lowerPrice = new Price("200");
        Price upperPrice = new Price("400");
        PriceRange boundedRange = new PriceRange(lowerPrice, upperPrice);
        assertEquals("$200.0 to $400.0", boundedRange.toString());
    }

    @Test
    public void equals() {
        // Create price ranges for testing
        PriceRange unboundedRange = new PriceRange();
        Price price100 = new Price("100");
        Price price200 = new Price("200");
        Price price100Copy = new Price("100");
        Price price200Copy = new Price("200");
        
        PriceRange upperBoundedRange = new PriceRange(price200, true);
        PriceRange upperBoundedRangeCopy = new PriceRange(price200Copy, true);
        PriceRange lowerBoundedRange = new PriceRange(price100, false);
        PriceRange boundedRange = new PriceRange(price100, price200);
        PriceRange boundedRangeCopy = new PriceRange(price100Copy, price200Copy);
        
        // same object -> returns true
        assertTrue(unboundedRange.equals(unboundedRange));
        
        // same values -> returns true
        assertTrue(upperBoundedRange.equals(upperBoundedRangeCopy));
        assertTrue(boundedRange.equals(boundedRangeCopy));
        
        // null -> returns false
        assertFalse(unboundedRange.equals(null));
        
        // different type -> returns false
        assertFalse(unboundedRange.equals("Any Price"));
        
        // different configurations -> returns false
        assertFalse(unboundedRange.equals(upperBoundedRange));
        assertFalse(upperBoundedRange.equals(lowerBoundedRange));
        assertFalse(lowerBoundedRange.equals(boundedRange));
        
        // different values in the same configuration -> returns false
        PriceRange differentUpperBound = new PriceRange(new Price("300"), true);
        assertFalse(upperBoundedRange.equals(differentUpperBound));
    }

    @Test
    public void hashCode_success() {
        // Create price ranges for testing
        Price price100 = new Price("100");
        Price price200 = new Price("200");
        Price price100Copy = new Price("100");
        Price price200Copy = new Price("200");
        
        PriceRange unboundedRange1 = new PriceRange();
        PriceRange unboundedRange2 = new PriceRange();
        PriceRange boundedRange1 = new PriceRange(price100, price200);
        PriceRange boundedRange2 = new PriceRange(price100Copy, price200Copy);
        
        // same values -> same hash code
        assertEquals(unboundedRange1.hashCode(), unboundedRange2.hashCode());
        assertEquals(boundedRange1.hashCode(), boundedRange2.hashCode());
        
        // different values -> different hash code
        PriceRange differentBoundedRange = new PriceRange(price100, new Price("300"));
        assertNotEquals(boundedRange1.hashCode(), differentBoundedRange.hashCode());
    }
} 