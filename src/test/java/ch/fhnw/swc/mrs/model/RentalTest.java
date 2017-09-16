/**
 * 
 */
package ch.fhnw.swc.mrs.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author christoph.denzler
 * 
 */
public class RentalTest {
    /** Exception message. */
    private static final String USERMESSAGE = "user must not be null.";
    /** Exception message. */
    private static final String MOVIEMESSAGE = "movie must not be null or is already rented.";
    /** Exception message. */
    private static final String RENTALMESSAGE = "Rental date must not be null or in the future.";

    /** users to use in tests. */
    private User u1, u2;

    /** movies to use in tests. */
    private Movie m1, m2;

    /** the release date used for the movies m1 and m2. */
    private LocalDate d = LocalDate.now();

    /** the price category used for the movies m1 and m2. */
    private PriceCategory pc = RegularPriceCategory.getInstance();
    
    private LocalDate today = LocalDate.now();

    /**
     * @throws java.lang.Exception should not be thrown
     */
    @Before
    public void setUp() throws Exception {
        u1 = new User("Mouse", "Mickey");
        u2 = new User("Duck", "Donald");
        m1 = new Movie("The Kid", d, pc);
        m2 = new Movie("Goldrush", d, pc);
    }
    
    /**
     * Test if Rental object gets initialized correctly with 3 argument constructor.
     */
    @Test
    public void testRentalCtorWithRentalDate() {
        Rental r = new Rental(u1, m1, today.minusDays(6));
        doAssertionsForTestRental(6, r);
    }
    
    
    private void doAssertionsForTestRental(int expected, Rental r) {
      // is the rental registered with the user?
      assertTrue(u1.getRentals().contains(r));
      // has the movie's rented state been set to rented?
      assertTrue(m1.isRented());
      // is the number of rental days set correctly?
      assertEquals(expected, r.getRentalDays());
      // has the rental date been set?
      assertNotNull(r.getRentalDate());
      // do we get what we set?
      assertEquals(u1, r.getUser());
      assertEquals(m1, r.getMovie());      
    }

    @Test
    public void testRentalCtorWithNullUser() {
        try {
            new Rental(null, m1, today);
            fail();
        } catch (MovieRentalException e) {
            assertEquals(USERMESSAGE, e.getMessage());
        }
    }
    
    @Test
    public void testRentalCtorWithNullMovie() {
        try {
            new Rental(u1, null, today);
            fail();
        } catch (MovieRentalException e) {
            assertEquals(MOVIEMESSAGE, e.getMessage());
        }
    }
    
    @Test
    public void testRentalCtorWithNullDate() {
        try {
            new Rental(u1, m1, null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(RENTALMESSAGE, e.getMessage());
        }
    }
    
    @Test
    public void testRentalCtorWithFutureDate() {
        try {
            new Rental(u1, m1, today.plusDays(1));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(RENTALMESSAGE, e.getMessage());
        }
    }
    
     /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Rental#calcRemainingDaysOfRental(java.util.Date)}.
     */
    @Test
    public void testCalcDaysOfRental() {
        LocalDate rentalDate = LocalDate.now().minusDays(6);

        Rental r = new Rental(u1, m1, rentalDate);
        
        long days = r.getRentalDays();
        assertEquals(6L, days);
    }

    @Test
    public void testSetterGetterId() {
        Rental r = new Rental(u1, m1, today);
        r.setId(11);
        assertEquals(11, r.getId());

        try { // setting id a 2nd time
            r.setId(47);
            fail();
        } catch (MovieRentalException mre) {
            assertEquals("illegal change of rental's id", mre.getMessage());
        }
        assertEquals(11, r.getId());
    }

    @Test
    public void testSetterGetterMovie() {
        Rental r = new Rental(u1, m1, today);
        r.setMovie(m2);
        assertEquals(m2, r.getMovie());

        try {
            r.setMovie(null);
            fail();
        } catch (MovieRentalException e) {
            assertEquals(MOVIEMESSAGE, e.getMessage());
        }
        assertEquals(m2, r.getMovie());
    }

    @Test
    public void testSetterGetterUser() {
        Rental r = new Rental(u1, m1, today);
        r.setUser(u2);
        assertEquals(u2, r.getUser());

        try {
            r.setUser(null);
            fail();
        } catch (MovieRentalException e) {
            assertEquals(USERMESSAGE, e.getMessage());
        }
        assertEquals(u2, r.getUser());
    }

    @Test
    @Ignore
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
        Rental x = new Rental(u1, m1, today);
        m1.setRented(false);
        Rental y = new Rental(u1, m1, today);

        assertEquals(x.hashCode(), y.hashCode());

        x.setId(42);
        assertTrue(x.hashCode() != y.hashCode());
        y.setId(42);
        assertEquals(x.hashCode(), y.hashCode());

        x.setMovie(m2);
        assertTrue(x.hashCode() != y.hashCode());
        y.setMovie(m2);
        assertEquals(x.hashCode(), y.hashCode());

        x.setUser(u2);
        assertTrue(x.hashCode() != y.hashCode());
        y.setUser(u2);
        assertEquals(x.hashCode(), y.hashCode());
    }
    
}