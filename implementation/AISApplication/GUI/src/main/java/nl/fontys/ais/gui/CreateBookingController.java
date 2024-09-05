
package nl.fontys.ais.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import nl.fontys.ais.businesslogic.BookingManager;
import nl.fontys.ais.businesslogic.objects.Menu;
import nl.fontys.ais.businesslogic.objects.Passenger;
import nl.fontys.ais.datarecords.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import nl.fontys.ais.businesslogic.objects.FlightRoute;
import nl.fontys.ais.persistence.EmptyException;
import nl.fontys.ais.persistence.LuggageException;
import nl.fontys.ais.persistence.PersistenceException;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

import static javafx.application.Application.launch;

public class CreateBookingController implements Initializable {
    //-----------------------------customer information-----------------------------------------------------------------
  @FXML
  private TextField customerFName;
  @FXML
  private TextField customerLName;
  @FXML
  private TextField customerMobile;
  @FXML
  private DatePicker customerDOB;
  @FXML
  private TextField customerAddress;
  @FXML
  private TextField customerEmail;
  @FXML
  private TextField customerIBAN;
    @FXML
    public TableView<FlightRoute> flightRouteTableView = new TableView<>();
    @FXML
    TableColumn<FlightRoute, String> departureDateCol = new TableColumn<>("departureTime");
    @FXML
    TableColumn<FlightRoute, String> departureTimeCol = new TableColumn<>("departureTime");
    @FXML
    TableColumn<FlightRoute, String> departureCol = new TableColumn<>("departureAirport");
    @FXML
    TableColumn<FlightRoute, String> arrivalCol = new TableColumn<>("arrivalAirport");
    @FXML
    TableColumn<FlightRoute, String> arrivalTimeCol = new TableColumn<>("arrivalTime");
  //----------------------------------passenger information-------------------------------------------------------------
    @FXML
    private TextField passengerFName;
    @FXML
    private TextField passengerLName;
    @FXML
    private DatePicker passengerDOB;
    @FXML
    private TextField passengerEmail;
    @FXML
    private ChoiceBox<MenuData> menuChoiceBox; //menu choicebox
    @FXML
    private TextField passengerLuggage;
    @FXML
    private TextField amount;
    @FXML
    private ChoiceBox<SeatData> seatChoiceBox;
    @FXML
    public TableView<PassengerData> passengerTableView = new TableView<>();
    @FXML
    TableColumn<PassengerData, String> firstNameCol = new TableColumn<>();
    @FXML
    TableColumn<PassengerData, String> lastNameCol = new TableColumn<>();
    @FXML
    TableColumn<PassengerData, String> dateOfBitrthCol = new TableColumn<>();
    @FXML
    TableColumn<PassengerData, String> emailCol = new TableColumn<>();
    @FXML
    TableColumn<PassengerData, String> menuNameCol = new TableColumn<>();
    @FXML
    TableColumn<PassengerData, String> nrMenuCol = new TableColumn<>();
    @FXML
    TableColumn<PassengerData, String> luggageCol = new TableColumn<>();
    @FXML
    TableColumn<PassengerData, String> seatCol = new TableColumn<>();
    @FXML
    TableColumn<PassengerData, String> priceCol = new TableColumn<>();

    @FXML
    public TableView<PassengerData> overviewTable = new TableView<>();
    @FXML
    TableColumn<PassengerData, String > totalCostCol = new TableColumn<>();

    @FXML
    private Text label;
    @FXML
    private Text labelPassenger;

    private final Supplier<SceneManager> sceneManagerSupplier;
    private final BookingManager bookingManager;

    private final List<MenuData> menus;
    private final List<SeatData> seats;
    private List<Double> prices = new ArrayList<>();





    static FlightRoute selectedFlight;


    @FXML
    TextField searchFlightTxt = new TextField();

    private ObservableList<PassengerData> passengerList = FXCollections.observableArrayList();

    public CreateBookingController(Supplier<SceneManager> sceneManagerSupplier, BookingManager bookingManager) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.bookingManager = bookingManager;
        this.menuChoiceBox = new ChoiceBox<>();
        this.seatChoiceBox = new ChoiceBox<>();
        menus = new ArrayList<>();
        seats = new ArrayList<>();
        //passengerList = new ArrayList<>();
        System.out.println(selectedFlight);
    }



    //to show the information in the TV /Customer
    public void setFlightRoutes(List<FlightRoute> flightRoutes) {
        flightRouteTableView.getItems().addAll(flightRoutes);
    }

    public static void setSelectedFlight(FlightRoute selectedFlight) {
        CreateBookingController.selectedFlight = selectedFlight;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        menuChoiceBox.getItems().addAll(bookingManager.getMenus());
        try {
            seatChoiceBox.getItems().addAll(bookingManager.getSeats(selectedFlight));
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }

        departureDateCol.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        departureTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        departureCol.setCellValueFactory(new PropertyValueFactory<>("departureAirport"));
        arrivalCol.setCellValueFactory(new PropertyValueFactory<>("arrivalAirport"));
        arrivalTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        flightRouteTableView.getItems().addAll(selectedFlight);
        //reflection -> call getters



        //firstNameCol.setCellValueFactory(new PropertyValueFactory<>("passengerData.passengerFName"));
        firstNameCol.setCellValueFactory(cellData -> {

            PassengerData passengerData = cellData.getValue();
            return new SimpleStringProperty(passengerData.passengerFName());
        });

        //lastNameCol.setCellValueFactory(new PropertyValueFactory<>("passengerLName"));
        lastNameCol.setCellValueFactory(cellData ->{

            PassengerData passengerData = cellData.getValue();
            return new SimpleStringProperty(passengerData.passengerLName());
        });


        //dateOfBitrthCol.setCellValueFactory(new PropertyValueFactory<>("passengerDOB"));
        dateOfBitrthCol.setCellValueFactory(cellData ->{

            PassengerData passengerData = cellData.getValue();
            return new SimpleStringProperty(String.valueOf(passengerData.dateOfBirth()));
        });
        //emailCol.setCellValueFactory(new PropertyValueFactory<>("passengerEmail"));
        emailCol.setCellValueFactory(cellData ->{

            PassengerData passengerData = cellData.getValue();
            return new SimpleStringProperty(passengerData.passengerEmail());
        });
        //menuNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        menuNameCol.setCellValueFactory(cellData ->{

            PassengerData menu = cellData.getValue();
            return new SimpleStringProperty(menu.menu().name());
        });
        //nrMenuCol.setCellValueFactory(new PropertyValueFactory<>("nrOfMenus"));
        nrMenuCol.setCellValueFactory(cellData ->{

           PassengerData nrmenu = cellData.getValue();
            return new SimpleStringProperty(String.valueOf(nrmenu.menu().amount()));
        });
        //luggageCol.setCellValueFactory(new PropertyValueFactory<>("passengerLuggage"));
        luggageCol.setCellValueFactory(cellData ->{

            PassengerData passengerData = cellData.getValue();
            return new SimpleStringProperty(String.valueOf(passengerData.luggage()));
        });

        //seatCol.setCellValueFactory(new PropertyValueFactory<>("seat"));
        seatCol.setCellValueFactory(cellData ->{
            PassengerData seat = cellData.getValue();
            return new SimpleStringProperty(String.valueOf(seat.seat().seatName()));
        });


        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));




        passengerTableView.setItems(passengerList);

    }


    @FXML
    private void createBooking() {

        String fName = customerFName.getText();
        String lName = customerLName.getText();
        String mobile = customerMobile.getText();
        LocalDate dob = customerDOB.getValue();
        String addr = customerAddress.getText();
        String email = customerEmail.getText();
        String IBAN = customerIBAN.getText();
        CustomerData c = new CustomerData(fName, lName, dob, mobile, addr, email, IBAN);
        passengerList = passengerTableView.getItems();
        int count = passengerList.size();
        double total = bookingManager.calculateTotal(prices);
        double discount = 0;
        BookingData b = new BookingData(1,count, discount, c.ID(), selectedFlight.getID(),total ,passengerList);
        try{
            bookingManager.add(b, c);
            label.setText("Booking created  " + b );
        }
        catch (EmptyException e){
            label.setText(e.getMessage());
        }
        catch (PersistenceException e){
            label.setText(e.getMessage());
        }
    }

    //Method that removes the passenger from the passengerTableView
    @FXML
    public void deletePassenger() {
        var selectionModel = passengerTableView.getSelectionModel();
        if(selectionModel.getSelectedCells().size() == 1){
            passengerTableView.getItems().remove(selectionModel.getSelectedItem());
            passengerTableView.refresh();
        } else {
            // label.setText("Could not remove passenger");
            System.out.println("cant");
        }
    }




    //method to add the passengers into the view
    @FXML
    public void addPassenger() throws LuggageException {


            //use a luggageException throw it in the business logic

            String fn = passengerFName.getText();
            String ln = passengerLName.getText();
            LocalDate dob = passengerDOB.getValue();
            String email = passengerEmail.getText();
            MenuData menuData = menuChoiceBox.getValue();
            int nr = Integer.parseInt(amount.getText());
            menuData = menuData.setAmount(nr);
            MenuData finalMenuData = menuData;
            Double luggage = Double.parseDouble(passengerLuggage.getText());
            SeatData seatData = seatChoiceBox.getValue();


            if(passengerFName.getText().isEmpty()){label.setText("Please fill in the passenger first name");}
            if(passengerLName.getText().isEmpty()){label.setText("Please fill in the passenger last name");}
            if(passengerDOB.getValue()==null){label.setText("Please fill in the passenger date of birth");}
        if (passengerDOB.getValue().isAfter(LocalDate.now())) {
            label.setText("Cannot be in the future");
        }
            if(passengerEmail.getText().isEmpty()){label.setText("Please fill in the passenger mail");}

        PassengerData passengerData = new PassengerData(1, fn, ln, dob, email, seatData, finalMenuData, luggage);


        priceCol.setCellValueFactory(cellData -> {

                double price = bookingManager.calculateTicketPrice(seatData, finalMenuData, luggage, selectedFlight);
              prices.clear();
               for(int i = 0; i < passengerList.size(); i++){
                   prices.add(price);
               }
                    return new SimpleStringProperty(String.valueOf(price));
            });

                passengerList.add(passengerData);
                passengerTableView.setItems(passengerList);
                overviewTable.setItems(passengerList);

        totalCostCol.setCellValueFactory(cellData -> {
            double totalCost = bookingManager.calculateTotal(prices);
            String totalCostString = String.valueOf(totalCost);
            return new SimpleStringProperty(totalCostString);
        });
        overviewTable.setItems(passengerList);


        }






    @FXML
    private void searchFlight() {
        this.sceneManagerSupplier.get().changeScene("searchFlightEmployeeView");
    }


    public void cancelBooking() {
        this.sceneManagerSupplier.get().changeScene("login");
    }
}

