//package nl.fontys.ais.gui;
//
//import javafx.scene.control.DatePicker;
//import javafx.scene.input.KeyCode;
//import javafx.stage.Stage;
//import nl.fontys.ais.businesslogic.BookingManager;
//import nl.fontys.ais.businesslogic.BusinessLogicAPI;
//import nl.fontys.ais.businesslogic.FlightManager;
//import nl.fontys.ais.businesslogic.objects.Plane;
//import nl.fontys.ais.businesslogic.objects.Route;
//import nl.fontys.ais.datarecords.FlightData;
//import nl.fontys.ais.datarecords.PlaneData;
//import nl.fontys.ais.datarecords.RouteData;
//import nl.fontys.ais.persistence.PersistenceException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.testfx.api.FxRobot;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.framework.junit5.Start;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.Arrays;
//
//import nl.fontys.ais.businesslogic.StatisticsCalculator;
//
//import static org.assertj.core.api.SoftAssertions.assertSoftly;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(ApplicationExtension.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class CreateflightControllerTest {
//
//    static {
//        if (Boolean.getBoolean("SERVER")) {
//            System.setProperty("java.awt.headless", "true");
//            System.setProperty("testfx.robot", "glass");
//            System.setProperty("testfx.headless", "true");
//            System.setProperty("prism.order", "sw");
//            System.setProperty("prism.text", "t2k");
//            System.setProperty("glass.platform", "Monocle");
//            System.setProperty("monocle.platform", "Headless");
//        }
//    }
//
//    Route r1 = new Route(new RouteData(LocalTime.of(1, 30), "DUS", "AMS"));
//    Route r2 = new Route(new RouteData(LocalTime.of(1, 30), "AMS", "VLN"));
//    Route r3 = new Route(new RouteData(LocalTime.of(1, 30), "VLN", "DUB"));
//    Route r4 = new Route(new RouteData(LocalTime.of(1, 30), "DUB", "MOS"));
//    Route r5 = new Route(new RouteData(LocalTime.of(1, 30), "MOS", "TOK"));
//
//
//    Plane p1 = new Plane(new PlaneData(5, 50, 500));
//    Plane p2 = new Plane(new PlaneData(7, 53, 530));
//
//    FlightData fd;
//
//    // the mock
//    FlightManager flightManager;
//
//    @BeforeEach
//    void setup() throws PersistenceException {
//        when(flightManager.getPlanes()).thenReturn(Arrays.asList(p1, p2));
//        when(flightManager.getRoutes()).thenReturn(Arrays.asList(r1, r2, r3, r4, r5));
//    }
//
//    @Start
//    void start(Stage stage) throws IOException {
//        flightManager = mock(FlightManager.class);
//        BusinessLogicAPI businessLogicAPI = new BusinessLogicAPI() {
//            @Override
//            public FlightManager getFlightManager() {
//                return flightManager;
//            }
//
//            @Override
//            public BookingManager getBookingManager() {
//                return null;
//            }
//
//            @Override
//            public StatisticsCalculator getStatisticsCalculator() {
//                return null;
//            }
//        };
//        new GUIApp(businessLogicAPI).init(false).start(stage);
//    }
//
//    @Test
//    void testCreateFlight(FxRobot fxRobot) throws PersistenceException {
//        fxRobot
//                .clickOn("#salesOfficer");
//        fxRobot.clickOn("#createFlightBtn");
//        fxRobot.lookup("#departureDate").queryAs(DatePicker.class).setValue(LocalDate.now());
//
//        when(flightManager.add(any())).thenReturn(fd);
//        when(flightManager.getPlanes()).thenReturn(Arrays.asList(p1, p2));
//        when(flightManager.getRoutes()).thenReturn(Arrays.asList(r1, r2, r3, r4, r5));
//
//
//        ArgumentCaptor<FlightData> flightDataArgumentCaptor = ArgumentCaptor.forClass(FlightData.class);
//
//        fxRobot
//                .clickOn("#departureDate")
//                .clickOn("#departureTime")
//                .write("18:40")
//                .clickOn("#routeChoiceBox")
//                .type(KeyCode.ENTER)
//                .clickOn("#planeChoiceBox")
//                .type(KeyCode.ENTER)
//                .clickOn("#btnAddRoute")
//                .clickOn("#routeChoiceBox")
//                .type(KeyCode.DOWN)
//                .type(KeyCode.ENTER)
//                .clickOn("#btnAddRoute")
//                .clickOn("#routeChoiceBox")
//                .type(KeyCode.DOWN)
//                .type(KeyCode.ENTER)
//                .clickOn("#btnAddRoute")
//                .clickOn("#routeChoiceBox")
//                .type(KeyCode.DOWN)
//                .type(KeyCode.ENTER)
//                .clickOn("#planeChoiceBox")
//                .type(KeyCode.DOWN)
//                .type(KeyCode.ENTER)
//                .clickOn("#btnAddRoute")
//                .clickOn("#routeChoiceBox")
//                .type(KeyCode.DOWN)
//                .type(KeyCode.ENTER)
//                .clickOn("#btnAddRoute")
//                .clickOn("#routeTable")
//                .type(KeyCode.UP)
//                .type(KeyCode.UP)
//                .type(KeyCode.UP)
//                .type(KeyCode.UP)
//                .clickOn("#btnRemoveRoute")
//                .clickOn("#routeTable")
//                .type(KeyCode.DOWN)
//                .type(KeyCode.DOWN)
//                .type(KeyCode.DOWN)
//                .clickOn("#btnRemoveRoute")
//                .clickOn("#routeTable")
//                .type(KeyCode.UP)
//                .clickOn("#btnRemoveRoute")
//                .clickOn("#create");
//
//        verify(flightManager).add(flightDataArgumentCaptor.capture());
//
//
//        assertSoftly(softly -> {
//            softly.assertThat(flightDataArgumentCaptor.getValue().routes().get(0).planeData().ID()).isEqualTo(p1.getData().ID());
//        });
//
//
//    }
//}
