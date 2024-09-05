/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package nl.fontys.ais.gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import nl.fontys.ais.businesslogic.FlightManager;
import nl.fontys.ais.businesslogic.objects.Plane;
import nl.fontys.ais.businesslogic.objects.Route;
import nl.fontys.ais.datarecords.*;
import nl.fontys.ais.persistence.PersistenceException;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Supplier;

/**
 * FXML Controller class
 *
 * @author Danylo
 */
public class CreateFlightController implements Initializable {



    @FXML
    private DatePicker departureDate;
    @FXML
    private TextField departureTime;
    @FXML
    private ChoiceBox<nl.fontys.ais.businesslogic.objects.Route> routeChoiceBox;
    @FXML
    private ChoiceBox<nl.fontys.ais.businesslogic.objects.Plane> planeChoiceBox;
    @FXML
    private TableView<RoutePlaneData> routeTable = new TableView<>();
    @FXML
    TableColumn<RoutePlaneData, String> planeCol = new TableColumn<>();
    @FXML
    TableColumn<RoutePlaneData, String> routeCol = new TableColumn<>();

    @FXML
    public TableColumn depDateCol;
    @FXML
    public TableColumn depTimeCol;
    @FXML
    public TableColumn arrDateCol;
    @FXML
    public TableColumn arrTimeCol;
    @FXML
    public TableColumn depTimezoneCol;
    @FXML
    public TableColumn arrTimezoneCol;


    @FXML
    private Text label;

    private final Supplier<SceneManager> sceneManagerSupplier;
    private final FlightManager flightManager;

    Route route;
    Plane plane;
    public CreateFlightController(Supplier<SceneManager> sceneManagerSupplier, FlightManager flightManager) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.flightManager = flightManager;
        this.routeChoiceBox = new ChoiceBox<>();
        this.planeChoiceBox = new ChoiceBox<>();
    }

    /**
     * Method to initialize routeTable, routeChoiceBox and planeChoiceBox
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        routeCol.setCellValueFactory(new PropertyValueFactory<>("routeData"));
        planeCol.setCellValueFactory(new PropertyValueFactory<>("planeData"));
        depDateCol.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        depTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrDateCol.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        arrTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        arrTimezoneCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTimezone"));
        depTimezoneCol.setCellValueFactory(new PropertyValueFactory<>("departureTimezone"));

        try {
            routeChoiceBox.getItems().addAll(flightManager.getRoutes());
            planeChoiceBox.getItems().addAll(flightManager.getPlanes());
        } catch (PersistenceException e) {
            label.setText(e.getMessage());
        }
    }

    /**
     * Method that retrieves data from controls,
     * initializes a new FlightData object and passes it through the FlightManager (Business Logic)
     *
     * @author=ks
     */
    @FXML
    private void createFlight() {
        FlightData flightData = new FlightData(LocalDate.now(), routeTable.getItems());
        try {
            flightManager.add(flightData);
        } catch (IllegalArgumentException | PersistenceException e) {
            label.setText(e.getMessage());
            return;
        }
        label.setText("Flight created: " + flightData);
    }

    /**
     * Method that changes the current scene to the previous view
     *
     * @author=ks
     */
    @FXML
    private void goBack(){
        this.sceneManagerSupplier.get().changeScene("searchFlight");
    }

    /**
     * Method that adds route to the routeTable.
     *
     * @author=ks
     */
    @FXML
    public void addRoute(){

        if(routeChoiceBox.getValue() == null || planeChoiceBox.getValue() == null){
            label.setText("Route and planes choicebox can not be null");
            return;
        }

        Optional<LocalDateTime> departure = Optional.empty();
        LocalDateTime arrival;
        route = routeChoiceBox.getValue();

        RouteData routeData = route.getData();


        plane = planeChoiceBox.getValue();
        PlaneData planeData = new PlaneData(plane.getData().ID(), plane.getData().numberOfSeats(), plane.getData().capacity());
        RoutePlaneData r;

        ObservableList<RoutePlaneData> routes = routeTable.getItems();
        if(routes.size() >= 1){
            r = routes.get(routes.size() - 1);
        } else {
            try {
                departure = Optional.of(LocalDateTime.parse(departureDate.getValue() + " " + departureTime.getText() + ":00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                arrival = departure.get()
                        .plusMinutes(routeData.estDuration().getMinute())
                        .plusHours(routeData.estDuration().getHour());

                int offset = routeData.departureTimezone() - routeData.arrivalTimezone();
                if(offset >= 0){
                    arrival = arrival.minusHours(offset);
                }else{
                    arrival = arrival.plusHours(Math.abs(offset));
                }
            }
            catch(DateTimeParseException dt){
                label.setText("Departure not in correct format");
                return;
            }

            routes.add(new RoutePlaneData(routeData, planeData, routeData.departureTimezone(), departure.get(), routeData.arrivalTimezone(), arrival));
            departureTime.setText(arrival.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            return;
        }
        if(r.routeData().arrivalAirport().equals(routeData.departureAirport())){
            LocalDateTime dep = r.arrival();
            if(!departureTime.getText().isEmpty() && departureDate.getValue() != null){
                departure = Optional.of(LocalDateTime.parse(departureDate.getValue() + " " + departureTime.getText() + ":00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            try {
                if(departure.isPresent() && departure.get().isAfter(r.arrival())){
                    dep = departure.get();
                }
                arrival = dep
                        .plusMinutes(routeData.estDuration().getMinute())
                        .plusHours(routeData.estDuration().getHour());
            }
            catch(DateTimeParseException dt){
                label.setText("Departure time not in correct format");
                return;
            }

            int offset = routeData.departureTimezone() - routeData.arrivalTimezone();
            if(offset >= 0){
                arrival = arrival.minusHours(offset);
            }else{
                arrival = arrival.plusHours(Math.abs(offset));
            }

            routes.add(new RoutePlaneData(routeData, planeData, routeData.departureTimezone(), dep,routeData.arrivalTimezone(), arrival));
            departureTime.setText(arrival.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        } else {
            label.setText("Destination airport has to be the same as departure airport");
        }
    }



    /**
     * Method that removes route from the routeTable
     *
     * @author=ks
     */
    @FXML
    public void removeRoute(){
        var selectionModel = routeTable.getSelectionModel();
        if(selectionModel.getSelectedItems().size() == 1 &&
            selectionModel.getSelectedIndex() == 0 ||
            selectionModel.getSelectedIndex() == routeTable.getItems().size() - 1
        ){
            routeTable.getItems().remove(selectionModel.getSelectedItem());
            routeTable.refresh();
        }else{
            label.setText("Could not remove route");
        }
    }
}
