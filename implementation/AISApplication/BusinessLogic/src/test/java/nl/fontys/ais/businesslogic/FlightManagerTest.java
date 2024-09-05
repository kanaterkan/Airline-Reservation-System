package nl.fontys.ais.businesslogic;

import nl.fontys.ais.datarecords.FlightData;
import nl.fontys.ais.datarecords.PlaneData;
import nl.fontys.ais.datarecords.RouteData;
import nl.fontys.ais.datarecords.RoutePlaneData;
import nl.fontys.ais.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class FlightManagerTest {


    FlightManager flightManager;

    @Mock
    FlightStorageService flightStorageService;
    @Mock
    FlightRouteStorageService flightRouteStorageService;
    @Mock
    PlaneStorageService planeStorageService;
    @Mock
    RouteStorageService routeStorageService;


    PlaneData pd;
    RouteData rd;
    /**
     * Verify that addFlight on flightStorageService is invoked once
     */

    FlightData fd = new FlightData(LocalDate.now(),
            new ArrayList<>(
                    List.of(
                            new RoutePlaneData(
                                    new RouteData(LocalTime.now(), "AMS", "DUS"),
                                    new PlaneData(600, 6, 6.0),
                                    1,
                                    LocalDateTime.now(),
                                    2,
                                    LocalDateTime.now().plusDays(2)
                            )
                    )
            )
    );
    FlightData faultyFd = new FlightData(LocalDate.now(),
            new ArrayList<>(
                    List.of(
                            new RoutePlaneData(
                                    new RouteData(LocalTime.now(), "AMS", "DUS"),
                                    new PlaneData(600, 6, 6.0),
                                    1,
                                    LocalDateTime.now().minusDays(1),
                                    1,
                                    LocalDateTime.now().plusDays(2)
                            )
                    )
            )
    );

    @BeforeEach
    void setup() throws PersistenceException {
        flightStorageService = mock(FlightStorageService.class);
        flightRouteStorageService = mock(FlightRouteStorageService.class);
        routeStorageService = mock(RouteStorageService.class);
        planeStorageService = mock(PlaneStorageService.class);
        flightManager = new FlightManager(flightStorageService,routeStorageService, planeStorageService, flightRouteStorageService);
        pd = new PlaneData(10, 200.0);
        rd = new RouteData(LocalTime.now(), "AMS", "DUS");

        when(flightStorageService.add(fd)).thenReturn(fd);
    }

    @Test
    @Order(1)
    @DisplayName("testAddFlightIsInvoked")
    void testAddFlightIsInvoked(){
        try{
            flightManager.add(fd);
            verify(flightStorageService, times(1)).add(any());
        } catch (PersistenceException e){
            assertThat(false);
        }

    }
    @Test
    @Order(2)
    @DisplayName("testDeleteFlight")
    void testDeleteFlight(){
        try{
            flightManager.delete(fd.ID());
            verify(flightStorageService, times(1)).delete(any());
        } catch (PersistenceException e){
            assertThat(false);
        }
    }
    
    @Test
    @Order(3)
    @DisplayName("testGetRoutes")
    void testGetRoutes(){

        try{
            flightManager.getRoutes();
            verify(routeStorageService, times(1)).getAll();
        } catch (PersistenceException e){
            assertThat(false);
        }
    }

    @Test
    @Order(4)
    @DisplayName("testGetPlanes")
    void testGetPlanes(){
        try{
            flightManager.getPlanes();
            verify(planeStorageService, times(1)).getAll();
        } catch (PersistenceException e){
            assertThat(false);
        }
        //TODO
    }

    @Test
    @Order(5)
    @DisplayName("testGetFlights")
    void testGetFlights(){
        try{
            flightManager.getFlights();
            verify(flightRouteStorageService, times(1)).getAll();
        } catch (PersistenceException e){
            assertThat(false);
        }
    }

    @Test
    @Order(6)
    @DisplayName("testAddFaultyFlight")
    void testAddFaultyFlight(){
        assertThatThrownBy(() -> flightManager.add(faultyFd)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Order(7)
    @DisplayName("testAddFlight")
    void testAddFlight(){
        try{
            assertThat(flightManager.add(fd)).isEqualTo(fd);
        } catch (PersistenceException e){
            assertThat(false);
        }
    }
}
