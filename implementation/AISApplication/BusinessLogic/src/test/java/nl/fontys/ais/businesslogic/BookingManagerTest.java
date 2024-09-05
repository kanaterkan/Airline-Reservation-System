package nl.fontys.ais.businesslogic;

//import java.awt.print.Book;
import java.time.LocalDate;
import java.util.ArrayList;

import nl.fontys.ais.datarecords.*;
import nl.fontys.ais.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class BookingManagerTest {

    @Mock
    BookingStorageService bookingStorageService;

    @Mock
    PassengerStorageService passengerStorageService;


    @BeforeEach
    void setup() throws PersistenceException, EmptyException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetBookings() {
        // getBookings
        BookingManager bm = new BookingManager(bookingStorageService, null, passengerStorageService, null, null);
        List<BookingData> bookingList = new ArrayList<>();
        var testBooking = new BookingData(1, 1, 2, 10, 2, 259);
        //var testBooking2 = new BookingData(2, 1, 0.0, 1, 259);
        bookingList.add(testBooking);
        //bookingList.add(testBooking2);
        try {
            when(bookingStorageService.getAll()).thenReturn(bookingList);
            var retrievedBookings = bm.getBookings();
            assertThat(retrievedBookings.contains(testBooking)).isTrue();
        } catch (PersistenceException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    void testGetBookingsByCustomerId() {
        // getBookings(cusId) method
        BookingManager bm = new BookingManager(bookingStorageService, null, null, null, null);
        List<BookingData> bookingList = new ArrayList<>();
        var testBooking = new BookingData(1, 2, 1, 10, 1, 212);
        bookingList.add(testBooking);

        try {
            when(bookingStorageService.getBookingsById(testBooking.customerId())).thenReturn(bookingList);
            var retrievedBooking = bm.getBookings(10);
            assertThat(retrievedBooking.contains(testBooking)).isTrue();
        } catch (PersistenceException ex) {
            System.err.println(ex);
        }
    }

    @Test
    void testGetPassengersByBookingId() {
        // getBookingPassengers(bookId) mehtod
        BookingManager bm = new BookingManager(null, null, passengerStorageService, null, null);
        List<PassengerData> passengerList = new ArrayList<>();
        var testPassenger = new PassengerData(11, "Nedas", "Adamavicius", LocalDate.of(2000, 12, 30), "real@emai.com", null, null, 20.0);
        try {
            when(passengerStorageService.getPassengersByBookingId(11)).thenReturn(passengerList);
            passengerList.add(testPassenger);
            var retvList = bm.getBookingsPassengers(11);
            assertThat(retvList.contains(testPassenger)).isTrue();
        } catch (PersistenceException ex) {
            System.err.println(ex);
        }
    }

    @Test
    void testChangeSeat() {
        // changeSeat method
        BookingManager bm = new BookingManager(null, null, passengerStorageService, null, null);

        var originalSeat = new SeatData("A1", 125.5, true);
        var newSeat = new SeatData("C1", 12.5, false);

        var passenger = new PassengerData(11, "Nedas", "Adamavicius", LocalDate.of(2000, 12, 30), "real@emai.com", originalSeat, null, 20.0);

        try {
            when(passengerStorageService.setSeatNumber(newSeat.seatName(), passenger)).thenReturn(true);
            boolean didItChange = bm.changeSeat(newSeat.seatName(), passenger);
            assertThat(didItChange).isTrue();
        } catch (PersistenceException ex) {
            System.err.println(ex);
        }
    }

    @Test
    void testChangeMenu() {
        // changeMenu method
        BookingManager bm = new BookingManager(null, null, passengerStorageService, null, null);

        var originalMenu = new MenuData("pie", 10.2, 1);
        var newMenu = new MenuData("kiwi", 12.5, 1);

        var passenger = new PassengerData(11, "Nedas", "Adamavicius", LocalDate.of(2000, 12, 30), "real@emai.com", null, originalMenu, 20.0);

        try {
            when(passengerStorageService.setMenuItem(newMenu.name(), passenger)).thenReturn(true);
            boolean didItChange = bm.changeMenu(newMenu.name(), passenger);
            assertThat(didItChange).isTrue();
        } catch (PersistenceException ex) {
            System.err.println(ex);
        }
    }

    @Test
    void testChangeLuggage() {
        // changeLuggage method
        BookingManager bm = new BookingManager(null, null, passengerStorageService, null, null);

        var originalLuggage = 20.0;
        var newLuggage = 20.5;

        var passenger = new PassengerData(11, "Nedas", "Adamavicius", LocalDate.of(2000, 12, 30), "real@emai.com", null, null, originalLuggage);

        try {
            when(passengerStorageService.setLuggageWeight(newLuggage, passenger)).thenReturn(true);
            boolean didItChange = bm.changeLuggage(newLuggage, passenger);
            assertThat(didItChange).isTrue();
        } catch (PersistenceException ex) {
            System.err.println(ex);
        }
    }

    @Test
    void testGetBookingsPassengersReturnEmptyList() {
        BookingManager bm = new BookingManager(null, null, passengerStorageService, null, null);
        List<PassengerData> passengerList = new ArrayList<>();
        var testPassenger = new PassengerData(11, "Nedas", "Adamavicius", LocalDate.of(2000, 12, 30), "real@emai.com", null, null, 20.0);
        try {
            when(passengerStorageService.getPassengersByBookingId(11)).thenReturn(passengerList);
            passengerList.add(testPassenger);
            var retvList = bm.getBookingsPassengers(0);
            assertThat(retvList.contains(testPassenger)).isFalse();
        } catch (PersistenceException ex) {
            System.err.println(ex);
        }
    }

    @Test
    void testGetBookingsPassengersThrowsException() {
        BookingManager bm = new BookingManager(null, null, passengerStorageService, null, null);

        try {
            when(passengerStorageService.getPassengersByBookingId(0)).thenThrow(PersistenceException.class);
        } catch (PersistenceException ex) {
            System.err.println(ex);
        }

        assertThatThrownBy(() -> {
            bm.getBookingsPassengers(0);
        });
    }

    @Test
    void getBookingsByNegativeIdReturnsEmptyList() {
        // getBookings(cusId) method
        BookingManager bm = new BookingManager(bookingStorageService, null, passengerStorageService, null, null);
        List<BookingData> bookingList = new ArrayList<>();
        var testBooking = new BookingData(1, 2, 1, 10, 1, 212);
        bookingList.add(testBooking);

        try {
            when(bookingStorageService.getBookingsById(testBooking.ID())).thenReturn(bookingList);
            var retrievedBooking = bm.getBookings(0);
            assertThat(retrievedBooking.contains(testBooking)).isFalse();
        } catch (PersistenceException ex) {
            System.err.println(ex);
        }
    }

    @Test
    void testChangeSeatReturnsFalse() {
        // changeSeat method
        BookingManager bm = new BookingManager(null, null, passengerStorageService, null, null);

        var originalSeat = new SeatData("A1", 125.5, true);
        var newSeat = new SeatData("DogFood", 12.5, false);

        var passenger = new PassengerData(11, "Nedas", "Adamavicius", LocalDate.of(2000, 12, 30), "real@emai.com", originalSeat, null, 20.0);

        try {
            when(passengerStorageService.setSeatNumber(newSeat.seatName(), passenger)).thenReturn(false);
            boolean didItChange = bm.changeSeat(newSeat.seatName(), passenger);
            assertThat(didItChange).isFalse();
        } catch (PersistenceException ex) {
            System.err.println(ex);
        }
    }

}