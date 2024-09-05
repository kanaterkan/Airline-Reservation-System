package nl.fontys.ais.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class SearchFlightController implements Initializable {

    @FXML
    public TableView<FlightRoute> flightTable = new TableView<>();

    @FXML
    public TableColumn<FlightRoute, String> estDuration;
    @FXML
    public TableColumn<FlightRoute, String> departureTimeZone;
    @FXML
    public TableColumn<FlightRoute, String> arrivalTimeZone;

    @FXML
    TableColumn<FlightRoute, String> ID = new TableColumn<>();

    @FXML
    TableColumn<FlightRoute, String> cost = new TableColumn<>();

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
    TextField searchFlightTxt = new TextField();
    @FXML
    Text label;

    Supplier<SceneManager> sceneManagerSupplier;
    FlightManager flightManager;
    FlightData fd = new FlightData(LocalDate.now(),
            new ArrayList<>(
                    List.of(
                            new RoutePlaneData(
                                    new RouteData(100, LocalTime.now(), "AMS",0, "DUS",1),
                                    new PlaneData(600, 6, 6.0),
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

    public SearchFlightController(Supplier<SceneManager> sceneManagerSupplier, FlightManager flightManager) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.flightManager = flightManager;
    }

    /**
     * Method that initializes flightTable
     *
     * @param url
     * @param resourceBundle
     */
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

    /**
     * Method that changes the current scene to the previous view
     *
     * @author=ks
     */
    @FXML
    public void goBack() {
        sceneManagerSupplier.get().changeScene("login");
    }

    /**
     * Method that changes the current scene to the createFlightView
     *
     * @author=ks
     */
    @FXML
    public void createFlight() {
        sceneManagerSupplier.get().changeScene("createFlight");
    }

    /**
     * Method that gets the current selected row and
     * passes it through the delete method on flightManager (Business Logic)
     *
     * @author=ks
     */
    @FXML
    public void deleteFlight() {
        ObservableList<FlightRoute> frdList;
        frdList = flightTable.getSelectionModel().getSelectedItems();
        if (frdList.size() == 1) {
            try {
                flightManager.delete(frdList.get(0).getID());
                refresh();
            } catch (PersistenceException e) {
                label.setText(e.getMessage());
            }
        } else {
            label.setText("No flight was selected");
        }
    }

    /**
     * Helper method that refreshes the flightTable
     *
     * @throws PersistenceException
     * @author=ks
     */
    public void refresh() throws PersistenceException {
        flights = FXCollections.observableArrayList(flightManager.getFlights());
        flightTable.getItems().clear();
        flightTable.getItems().addAll(flights);
    }

    /**
     * Method that filters flights based on fields in FlightRouteData
     *
     * @author=ks
     */
    @FXML
    public void searchFlight() {
        flightTable.getItems().clear();
        if (searchFlightTxt.getText().isEmpty()) {
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
