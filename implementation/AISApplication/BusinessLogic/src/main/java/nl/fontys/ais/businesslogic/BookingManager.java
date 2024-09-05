package nl.fontys.ais.businesslogic;

import nl.fontys.ais.businesslogic.objects.*;
import nl.fontys.ais.datarecords.*;
import nl.fontys.ais.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingManager {

    private final BookingStorageService bookingStorageService;
    private final CustomerStorageService customerStorageService;
    private final PassengerStorageService passengerStorageService;
    private final MenuStorageService menuStorageService;
    private final SeatStorageService seatStorageService;

    private final List<SeatData> seatList = new ArrayList<>();

    public BookingManager(BookingStorageService bookingStorageServ, CustomerStorageService customerStorageServ, PassengerStorageService passengerStorageServ, MenuStorageService menuStorageService, SeatStorageService seatStorageServ) {

        this.bookingStorageService = bookingStorageServ;
        this.customerStorageService = customerStorageServ;
        this.passengerStorageService = passengerStorageServ;
        this.menuStorageService = menuStorageService;
        this.seatStorageService = seatStorageServ;
    }

    public List<MenuData> getMenus() {
        try {
            return menuStorageService.getAll();
        } catch (PersistenceException ex) {
            Logger.getLogger(BookingManager.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public SeatData add(SeatData seatData) throws PersistenceException {
        return seatStorageService.add(seatData);
    }

    public BookingData add(BookingData bookingData, CustomerData customerData) throws PersistenceException, IllegalArgumentException, EmptyException {

        if (customerData.getFirstName().isEmpty()) {
            throw new EmptyException("Customer First Name can't be empty");
        }
        if (customerData.getLastName().isEmpty()) {
            throw new EmptyException("Customer Last Name can't be empty");
        }
        if (customerData.getDOB() == null || customerData.getDOB().isAfter(LocalDate.now())) {
            throw new EmptyException("Customer DOB cant be empty or in future");
        }
        if (customerData.getEmail().isEmpty()) {
            throw new EmptyException("Customer mail has to be filled");
        }
        if (customerData.getAddress().isEmpty()) {
            throw new EmptyException("Customer address has to be filled");
        }
        if (customerData.getIBAN().isEmpty()) {
            throw new EmptyException("Customer IBAN has to be filled");
        }

        CustomerData c = customerStorageService.add(customerData);
        BookingData bookingData1
                = new BookingData(c.ID(), bookingData.countOfSeats(), bookingData.discount(), c.ID(), bookingData.flightId(),
                        bookingData.price(), bookingData.passengers());
        BookingData b = bookingStorageService.add(bookingData1);
        return b;

    }

    public List<SeatData> getSeats(FlightRoute selectedFlight) throws PersistenceException {
        this.seatList.clear();
        seatStorageService.getAll().forEach(seatData -> {
            SeatData seat = new SeatData(seatData.seatName(), seatData.price(), seatData.legroom());
            seatList.add(seat);
        });
        return seatList;
    }

    public List<PassengerData> getBookingsPassengers(int bookId) throws PersistenceException {
        try {
            return passengerStorageService.getPassengersByBookingId(bookId);
        } catch (PersistenceException ex) {
            throw new PersistenceException("booking doesn't exist", ex);
        }
    }

    public List<BookingData> getBookings() throws PersistenceException {
        return bookingStorageService.getAll();
    }

    public List<BookingData> getBookings(int cusId) throws PersistenceException {
        if (cusId > 0) {
            return bookingStorageService.getBookingsById(cusId);
        } else {
            return new ArrayList<>();
        }
    }

    public boolean changeSeat(String seatNumber, PassengerData pd) throws PersistenceException {
        // method to change seat in modify booking
        return passengerStorageService.setSeatNumber(seatNumber, pd);
    }

    public boolean changeMenu(String itemName, PassengerData pd) throws PersistenceException {
        // method to change menu in modify booking
        return passengerStorageService.setMenuItem(itemName, pd);
    }

    public boolean changeLuggage(double luggage, PassengerData pd) throws PersistenceException {
        // method to change luggage weight in modify booking
        return passengerStorageService.setLuggageWeight(luggage, pd);
    }

    public double calculateTicketPrice(SeatData seatData, MenuData menuData, double luggage, FlightRoute selectedFlight) {
        double menuPrice = menuData.price() * menuData.amount();
        double luggagePrice = 0.0;

        luggagePrice = (luggage - 8) * 5;

        if (luggagePrice < 0) {
            luggagePrice = 0;
        }

        double seatPrice = seatData.price();
        double flightPrice = selectedFlight.getCost();
        double priceForPassenger = menuPrice + luggagePrice + flightPrice + seatPrice;
        return priceForPassenger;
    }

    public double calculateTotal(List<Double> prices) {
        double total = 0.0;
        for (int i = 0; i < prices.size(); i++) {
            total = total + prices.get(i);
        }
        return total;
    }

}
