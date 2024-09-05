/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nl.fontys.ais.gui;

//<editor-fold defaultstate="collapsed" desc="IMPORTS">
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import nl.fontys.ais.businesslogic.StatisticsCalculator;

import nl.fontys.ais.datarecords.RouteData;
import nl.fontys.ais.persistence.PersistenceException;
//</editor-fold>

//REMINDER
/**
 * FXML Controller class. The controller class contains GUI-logic (no business
 * logic!). It reacts on GUI events like button clicks. It triggers the
 * BusinessLogic layer to do the real work. Furthermore the controller will
 * trigger navigation and update the GUI.
 *
 * @author Informatics Fontys Venlo
 */
/**
 *
 * @author nedus
 */
public class ManagementDashboardController implements Initializable {

    private final StatisticsCalculator statCalc;

    private ObservableList<RouteData> observableRoutes;

    private List<RouteData> routes;

    public ManagementDashboardController(StatisticsCalculator sc) {
        this.statCalc = sc;
    }

    //<editor-fold defaultstate="collapsed" desc="Initialize">
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing ManagementDashboardController...");

        try {
            //GETTING LIST OF ROUTES
            routes = statCalc.getRoutes();
        } catch (PersistenceException ex) {
            errorLabel.setText(ex.getMessage());
        }

        List<RouteData> initialRoutes = routes.subList(0, Math.min(28, routes.size()));

        // initial routes
        observableRoutes = FXCollections.observableArrayList(initialRoutes);

        routeListView.setItems(observableRoutes);

        // update values in the XYChart and both StackPanes based on a selected Route
        routeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updateRouteInfo(newValue);
                updateChartData(newValue);
                errorLabel.setText("");
            } catch (PersistenceException ex) {
                errorLabel.setText(ex.getMessage());
                revenueLabel.setText("Total revenue: 0.0");
                barChart.getData().clear();
            }
        });
        System.out.println("Finished initializing ManagementDashboardController.");

        // method to set custom text on routes
        setRoutesText(routeListView);
        setupRouteSearch();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="search related methods">
    public void searchRoute(String pattern) throws PersistenceException {
        // original list of routes
        List<RouteData> rd;
        // limited size list
        List<RouteData> limited;

        //if statement, check if "pattern" is empty, if it is, do rd.getAll();
        if (pattern.isBlank()) {
            rd = statCalc.getRoutes();
            // limit size by 28 cells
            limited = rd.subList(0, Math.min(28, routes.size()));
        } else {
            limited = statCalc.getRoutes(pattern);
        }

        observableRoutes.clear();
        // add limited, not rd!
        observableRoutes.addAll(limited);
    }

    public void setupRouteSearch() {
        //IMPLEMENT ROUTE SEARCH METHOD
        routeSearch.setOnAction(eh -> {
            try {
                searchRoute(routeSearch.getText());
            } catch (PersistenceException ex) {
                Logger.getLogger(ManagementDashboardController.class.getName()).log(Level.INFO, null, ex);
            }
        });
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update/set methods">
    private void setRoutesText(ListView<RouteData> rlv) {
        rlv.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(RouteData rd, boolean empty) {
                super.updateItem(rd, empty);
                if (empty || rd == null) {
                    // null is better, leaves clean cells
                    setText(null);
                } else {
                    setText("Route: " + rd);
                }
            }
        });
    }

    private void updateRouteInfo(RouteData route) throws PersistenceException {
        if (route != null) {
            Double totalRevenue = statCalc.getTotalRevenueOfRoute(route);
            String routeDA = route.departureAirport() + "-" + route.arrivalAirport();
            
            routeLabel.setText("Route: " + routeDA);
            revenueLabel.setText("Total revenue: " + totalRevenue);
        }
    }

    private void updateChartData(RouteData selectedRoute) throws PersistenceException {
        // inside this method, get data from the selected route
        if (selectedRoute != null) {
            // retrieve actual data from the databse and replace 'int's,
            // with the data values retrived from db.
            // number of passengers with extra options
            String fanta = "Extra option passengers";
            int extraOptionPass = statCalc.extraOptionPassengers(selectedRoute);

            // Legroom category stats
            String legroomCat = "Extra legroom passengers";
            int numLegroomPass = statCalc.extraLegroomPassengers(selectedRoute);
            // Food category stats
            String foodCat = "On flight food passengers";
            int numFoodPass = statCalc.extraFoodPassengers(selectedRoute);
            // Luggage category stats
            String luggageCat = "Additional luggage passengers";
            int numLuggagePass = statCalc.extraLuggagePassengers(selectedRoute);

            // Front row passenger stats
            String frontCat = "First Class";
            int numFrontPass = statCalc.firstPassengers(selectedRoute);
            // Middle row passenger stats
            String midCat = "Economy+ Class";
            int numMidPass = statCalc.economyPlusPassengers(selectedRoute);
            // Back row passenger stats
            String backCat = "Economy Class";
            int numBackPass = statCalc.economyPassengers(selectedRoute);

            // Create a new data series for the chart
            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            // Add data series
            series.getData().add(new XYChart.Data<>(fanta, extraOptionPass));
            series.getData().add(new XYChart.Data<>(legroomCat, numLegroomPass));
            series.getData().add(new XYChart.Data<>(foodCat, numFoodPass));
            series.getData().add(new XYChart.Data<>(luggageCat, numLuggagePass));
            series.getData().add(new XYChart.Data<>(frontCat, numFrontPass));
            series.getData().add(new XYChart.Data<>(midCat, numMidPass));
            series.getData().add(new XYChart.Data<>(backCat, numBackPass));

            if (extraOptionPass == 0
                    && numLegroomPass == 0
                    && numFoodPass == 0
                    && numLuggagePass == 0
                    && numFrontPass == 0
                    && numMidPass == 0
                    && numBackPass == 0) {
                // if they ask why i chose "no data" instead of anything specific
                // it's because total revenue per route is directly related
                // to how many passengers chose extra options
                throw new PersistenceException("No data available for this route");
            }
            // Update the chart with the new data
            barChart.getData().setAll(series);

            // Disable annoying cube
            barChart.setLegendVisible(false);

        }
    }
//</editor-fold>

    @FXML
    private ListView<RouteData> routeListView;

    @FXML
    private Label routeLabel;

    @FXML
    private Label revenueLabel;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private TextField routeSearch;

    @FXML
    private Label errorLabel;
}
