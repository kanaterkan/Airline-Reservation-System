package nl.fontys.ais.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import nl.fontys.ais.businesslogic.FlightManager;
import nl.fontys.ais.businesslogic.objects.FlightRoute;
import nl.fontys.ais.datarecords.*;
import nl.fontys.ais.persistence.PersistenceException;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class SearchFlightBooking implements Initializable {


    @FXML
    Button goBackBtn;
    @FXML
    Button createFlightBtn;
    @FXML
    Button deleteFlightBtn;
    @FXML
    Button editFlightBtn;
    
    @FXML
    Button modifyBookingButton;
    
    private CreateBookingController createBookingController;


    @FXML
    public TableView<FlightRoute> flightTable = new TableView<>();

    @FXML
    TableColumn<FlightRoute, String> ID = new TableColumn<>();
    @FXML
    TableColumn<FlightRoute, String> creationDate = new TableColumn<>();
    @FXML
    TableColumn<FlightRoute, String> startAirport = new TableColumn<>();
    @FXML
    TableColumn<FlightRoute, String> endAirport = new TableColumn<>();
    @FXML
    TableColumn<FlightRoute, String> departureDate = new TableColumn<>();
    @FXML
    TableColumn<FlightRoute, String> departureTime = new TableColumn<>();
    @FXML
    TableColumn<FlightRoute, String> arrivalDate = new TableColumn<>();
    @FXML
    TableColumn<FlightRoute, String> arrivalTime = new TableColumn<>();
    @FXML
    TableColumn<FlightRoute, String> plane = new TableColumn<>();
    @FXML
    TableColumn<FlightRoute, String> cost = new TableColumn<>();

    @FXML
    public TableColumn<FlightRoute, String> estDuration;
    @FXML
    public TableColumn<FlightRoute, String> departureTimeZone;
    @FXML
    public TableColumn<FlightRoute, String> arrivalTimeZone;
    @FXML
    TextField searchFlightTxt = new TextField();
    @FXML
    Text label;

    Supplier<SceneManager> sceneManagerSupplier;
    FlightManager flightManager;
    FlightData fd = new FlightData(LocalDate.now(),
            new ArrayList<>(
                    List.of(
                            new RoutePlaneData(
                                    new RouteData(100, 1, LocalTime.now(), "AMS",0, "DUS", 1),
                                    new PlaneData(600,6,6.0),
                                    1,
                                    LocalDateTime.now(),
                                    1,
                                    LocalDateTime.now().plusDays(2)
                            )
                    )
            )
    );
    ObservableList<FlightRoute> flights = javafx.collections.FXCollections.observableArrayList(
            new FlightRoute(
                    new FlightRouteData(
                            fd.ID(),
                            fd.creationDate(),
                            fd.routes().get(0).routeData().estDuration(),
                            fd.routes().get(0).routeData().departureAirport(),
                            fd.routes().get(0).routeData().departureTimezone(),
                            fd.routes().get(0).routeData().arrivalAirport(),
                            fd.routes().get(0).routeData().arrivalTimezone(),
                            fd.routes().get(0).routeData().cost(),
                            fd.routes().get(0).getDepartureDate(),
                            fd.routes().get(0).getDepartureTime(),
                            fd.routes().get(0).getArrivalDate(),
                            fd.routes().get(0).getArrivalTime(),
                            fd.routes().get(0).planeData()
                    )
            )
    );
    public SearchFlightBooking(Supplier<SceneManager> sceneManagerSupplier, FlightManager flightManager){
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.flightManager = flightManager;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        startAirport.setCellValueFactory(new PropertyValueFactory<>("departureAirport"));
        endAirport.setCellValueFactory(new PropertyValueFactory<>("arrivalAirport"));
        departureDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        departureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalDate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        arrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        plane.setCellValueFactory(new PropertyValueFactory<>("plane"));
        estDuration.setCellValueFactory(new PropertyValueFactory<>("estDuration"));
        departureTimeZone.setCellValueFactory(new PropertyValueFactory<>("departureTimezone"));
        arrivalTimeZone.setCellValueFactory(new PropertyValueFactory<>("arrivalTimezone"));
        try {
            refresh();
        } catch (PersistenceException e) {
            label.setText(e.getMessage());
        }
    }

    public void setCreateBookingController(CreateBookingController createBookingController){
        this.createBookingController = createBookingController;
    }

    @FXML
    public void goBack(){
        sceneManagerSupplier.get().changeScene("login");
    }
    
    @FXML
    public void modifyBooking() {
        sceneManagerSupplier.get().changeScene("modifyBooking");
    }

    @FXML
    public void createNewBooking(){
        FlightRoute selectedFlight = flightTable.getSelectionModel().getSelectedItem();
        if(selectedFlight == null){
            return;
        }
        CreateBookingController.setSelectedFlight(selectedFlight);
        sceneManagerSupplier.get().changeScene("createBookingCustomer");
    }



    public void refresh() throws PersistenceException {
        flights = FXCollections.observableArrayList(flightManager.getFlights());
        flightTable.getItems().clear();
        flightTable.getItems().addAll(flights);
    }
    @FXML
    public void searchFlight(){
        flightTable.getItems().clear();
        if(searchFlightTxt.getText().isEmpty()){
            flightTable.getItems().addAll(flights);
            return;
        }
        flightTable.getItems().addAll(flights.filtered(
                f ->

                        f.getID().toString().equals(searchFlightTxt.getText()) ||
                                f.getDepartureAirport().equalsIgnoreCase(searchFlightTxt.getText())
                                || f.getArrivalAirport().equalsIgnoreCase(searchFlightTxt.getText())
                                || f.getArrivalTime().toString().equalsIgnoreCase(searchFlightTxt.getText())
                                || f.getArrivalDate().toString().equalsIgnoreCase(searchFlightTxt.getText())
                                || f.getCreationDate().toString().equalsIgnoreCase(searchFlightTxt.getText())
                                || f.getDepartureDate().toString().equalsIgnoreCase(searchFlightTxt.getText())
                                || f.getDepartureTime().toString().equalsIgnoreCase(searchFlightTxt.getText())
        ));
    }


}