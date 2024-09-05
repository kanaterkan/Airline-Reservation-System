/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package nl.fontys.ais.gui;

//<editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.fontys.ais.businesslogic.BookingManager;
import nl.fontys.ais.datarecords.BookingData;
import nl.fontys.ais.datarecords.MenuData;
import nl.fontys.ais.datarecords.PassengerData;
import nl.fontys.ais.datarecords.SeatData;
import nl.fontys.ais.persistence.PersistenceException;
//</editor-fold>

/**
 * FXML Controller class
 *
 * The controller class contains GUI-logic (no business logic!). It reacts on
 * GUI events like button clicks. It triggers the BusinessLogic layer to do the
 * real work. Furthermore the controller will trigger navigation and update the
 * GUI.
 *
 * @author nedus
 */
public class ModifyBookingController implements Initializable {

    private final Supplier<SceneManager> sceneManagior;
    private final BookingManager bookingManager;

    private final ObservableList<BookingData> obvList = FXCollections.observableArrayList();
    private final ObservableList<PassengerData> obvListPass = FXCollections.observableArrayList();

    //<editor-fold defaultstate="collapsed" desc="Table Columns">
    //BOOKING TABLE COLUMNS
    private TableColumn<BookingData, Integer> bookingIdColumn;
    private TableColumn<BookingData, Integer> countOfSeatsColumn;
    private TableColumn<BookingData, Double> discountColumn;
    private TableColumn<BookingData, Integer> customerIdColumn;
    private TableColumn<BookingData, Integer> flightIdColumn;

    //PASSENGER TABLE COLUMNS
    private TableColumn<PassengerData, Integer> passengerId;
    private TableColumn<PassengerData, String> firstName;
    private TableColumn<PassengerData, String> lastName;
    private TableColumn<PassengerData, LocalDate> dob;
    private TableColumn<PassengerData, SeatData> seat;
    private TableColumn<PassengerData, String> email;
    private TableColumn<PassengerData, MenuData> menu;
    private TableColumn<PassengerData, Double> luggage;
    //</editor-fold>

    public ModifyBookingController(Supplier<SceneManager> sm, BookingManager bm) {
        this.sceneManagior = sm;
        this.bookingManager = bm;
    }

    /**
     * Initializes the controller class.
     */
    //<editor-fold defaultstate="collapsed" desc="initalize">
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //initiallize booking table columns
        bookingIdColumn = new TableColumn<>("ID");
        countOfSeatsColumn = new TableColumn<>("Booked seats");
        discountColumn = new TableColumn<>("Discount");
        customerIdColumn = new TableColumn<>("Customer ID");
        flightIdColumn = new TableColumn<>("Flight ID");

        // initialize passenger table columns
        passengerId = new TableColumn<>("Passenger ID");
        firstName = new TableColumn<>("First name");
        lastName = new TableColumn<>("Last name");
        dob = new TableColumn<>("Date of birth");
        seat = new TableColumn("Seat");
        email = new TableColumn<>("Email");
        menu = new TableColumn<>("Menu item");
        luggage = new TableColumn<>("Luggage weight");

        //add booking colums
        bookingTableView.getColumns().addAll(flightIdColumn, customerIdColumn, countOfSeatsColumn, discountColumn, bookingIdColumn);
        setupModifyBooking();
        setupSearchBooking();
        // add passenger columns
        passengerTableView.getColumns().addAll(firstName, lastName, seat, menu, luggage, email, dob, passengerId);
        updatePassengerTable(bookingTableView);
        updateBookingLabel(bookingTableView);
        updateOptionFields(passengerTableView);

    }
    //</editor-fold>

    /**
     * All methods related to booking table manipulation using BookingManager
     */
    //<editor-fold defaultstate="collapsed" desc="Booking manipulation reltade methods">
    private void updatePassengerTable(TableView<BookingData> btv) {
        //update passenger table view based on the selected booking
        btv.getSelectionModel().selectedItemProperty().addListener((obv, ov, nv) -> {
            if (nv != null) {
                getPassengerData(nv);
                updateBookingLabel(nv);
            }
        });
    }

    private void updateBookingLabel(TableView<BookingData> btv) {
        btv.getSelectionModel().selectedItemProperty().addListener((obv, ov, nv) -> {
            if (nv != null) {
                updateBookingLabel(nv);
            }
        });
    }

    private void updateBookingLabel(BookingData bd) {
        if (bd != null) {
            String i = Integer.toString(bd.ID());
            selectedBooking.setText("Selected booking ID: " + i);
        }
    }

    private void setupModifyBooking() {
        // method for setting up the booking scene
        // dont like too much code in the initalize mehtod

        // conv values
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        bookingIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().ID()).asObject());

        countOfSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("countOfSeats"));
        countOfSeatsColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().countOfSeats()).asObject());

        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        discountColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().discount()).asObject());

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().customerId()).asObject());

        flightIdColumn.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        flightIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().flightId()).asObject());

        // retv bookings from the daabase
        List<BookingData> bookingData = null;
        try {
            bookingData = bookingManager.getBookings();
        } catch (PersistenceException ex) {
            selectedBooking.setText(ex.getMessage());
        }

        // add bookings to the table
            obvList.setAll(bookingData);

        bookingTableView.setItems(obvList);

    }

    private void searchBooking(int cusId) {
        List<BookingData> bd = null;

        if (cusId == 411) {
            try {
                // 411 stands for All
                bd = bookingManager.getBookings();
            } catch (PersistenceException ex) {
                selectedBooking.setText(ex.getMessage());
            }
        } else {
            try {
                bd = bookingManager.getBookings(cusId);
            } catch (PersistenceException ex) {
                selectedBooking.setText(ex.getMessage());
            }
        }

        obvList.clear();
        obvList.setAll(bd);
        bookingTableView.setItems(obvList);
    }

    private void setupSearchBooking() {
        searchBookingBar.setOnAction(eh -> {
            searchBooking(Integer.parseInt(searchBookingBar.getText()));
        });
    }
    //</editor-fold>

    /**
     * All methods related to passenger table manipulation using BookingManager
     */
    //<editor-fold defaultstate="collapsed" desc="Passenger manipulation related methods">
    public void getPassengerData(BookingData bd) {
        // passengerID
        passengerId.setCellValueFactory(new PropertyValueFactory<>("passengerId"));
        passengerId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().passengerID()).asObject());
        // firstName
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstName.setCellValueFactory(fn -> new SimpleStringProperty(fn.getValue().passengerFName()));
        // lastName
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastName.setCellValueFactory(ln -> new SimpleStringProperty(ln.getValue().passengerLName()));
        // dob
        dob.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        dob.setCellValueFactory(d -> {
            LocalDate date = d.getValue().dateOfBirth();
            return new SimpleObjectProperty<>(date);
        });

        seat.setCellValueFactory(new PropertyValueFactory<>("seat"));
        seat.setCellValueFactory(s -> {
            SeatData sd = s.getValue().seat();
            return new SimpleObjectProperty<>(sd);
        });

        // email
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.setCellValueFactory(em -> new SimpleStringProperty(em.getValue().passengerEmail()));

        menu.setCellValueFactory(new PropertyValueFactory<>("menu"));
        menu.setCellValueFactory(m -> {
            MenuData md = m.getValue().menu();
            return new SimpleObjectProperty<>(md);
        });

        luggage.setCellValueFactory(new PropertyValueFactory<>("luggage"));
        luggage.setCellValueFactory(lug -> new SimpleDoubleProperty(lug.getValue().luggage()).asObject());

        // ger passenger data related to a booking. Works thru booking_passenger table
        List<PassengerData> passengerData = null;
        try {
            passengerData = bookingManager.getBookingsPassengers(bd.ID());
        } catch (PersistenceException ex) {
            selectedBooking.setText(ex.getMessage());
        }
        // create passenger table viw
        obvListPass.setAll(passengerData);
        passengerTableView.setItems(obvListPass);

    }

    void updateOptionFields(TableView<PassengerData> ptv) {
        ptv.getSelectionModel().selectedItemProperty().addListener((obv, ov, nv) -> {
            if (nv != null) {
                initialOptionFields(nv);
            }
        });
    }

    void initialOptionFields(PassengerData pd) {

        seatTextField.setText(pd.seat().seatName());
        menuTextField.setText(pd.menu().name());
        luggageTextField.setText(pd.luggage().toString());
    }
    //</editor-fold>

    //cancelButton
    @FXML
    private void cancel() {
        sceneManagior.get().changeScene("login");
    }

    //updateButton
    @FXML
    private void update() {
        // EXECUTE THE UPDATE QUERY
        PassengerData selectedBoy = passengerTableView.getSelectionModel().getSelectedItem();
        String newSeat = seatTextField.getText();
        String newMenuItem = menuTextField.getText();
        String newLuggageWeight = luggageTextField.getText();

        boolean everythingExecuted = true;

        if (!newSeat.isEmpty()) {
            try {
                bookingManager.changeSeat(newSeat, selectedBoy);
            } catch (PersistenceException ex) {
                messageLabel.setText(ex.getMessage());
                everythingExecuted = false;
            }
        }
        if (!newMenuItem.isEmpty()) {
            try {
                bookingManager.changeMenu(newMenuItem, selectedBoy);
            } catch (PersistenceException ex) {
                messageLabel.setText(ex.getMessage());
                everythingExecuted = false;
            }
        }
        if (!newLuggageWeight.isEmpty()) {
            try {
                bookingManager.changeLuggage(Double.parseDouble(newLuggageWeight), selectedBoy);
            } catch (PersistenceException ex) {
                messageLabel.setText(ex.getMessage());
                everythingExecuted = false;
            }
        }

        // and maybe refresh booking table view upon a successful update
        if (everythingExecuted) {
            BookingData selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();
            if (selectedBooking != null) {
                refreshPassengerTable(selectedBooking.ID());
            }
        }
    }

    void refreshPassengerTable(int bookId) {
        List<PassengerData> pd = null;
        try {
            pd = bookingManager.getBookingsPassengers(bookId);
        } catch (PersistenceException pe) {
            messageLabel.setText(pe.getMessage());
        }

        obvListPass.setAll(pd);
        passengerTableView.setItems(obvListPass);
        messageLabel.setText(null);
    }

    // FXML FIELDS
    @FXML
    private TextField searchBookingBar;

    @FXML
    private Label selectedBooking;

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<BookingData> bookingTableView;

    @FXML
    private TableView<PassengerData> passengerTableView;

    @FXML
    private TextField luggageTextField;

    @FXML
    private TextField seatTextField;

    @FXML
    private TextField menuTextField;

}
