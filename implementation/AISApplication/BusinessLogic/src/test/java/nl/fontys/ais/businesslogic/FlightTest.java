package nl.fontys.ais.businesslogic;

import nl.fontys.ais.businesslogic.objects.Airport;
import nl.fontys.ais.businesslogic.objects.Flight;
import nl.fontys.ais.businesslogic.objects.Plane;
import nl.fontys.ais.businesslogic.objects.Route;
import nl.fontys.ais.datarecords.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FlightTest {

    Airport schiphol = new Airport(new AirportData("AMS", "Amsterdam", "Schiphol"));
    Airport dusseldorf = new Airport(new AirportData("DUS", "Dusseldorf", "Flughafen Düsseldorf"));

    Route schip_dussel = new Route(new RouteData(LocalTime.of(1,30), schiphol.getData().IATA(), dusseldorf.getData().IATA()));
    List<RouteData> routes = new ArrayList<>();
    Plane KLM = new Plane(new PlaneData(911, 50, 500));

    @Test
    void testFlightCreated() {
        Flight flight = new Flight(new FlightData(LocalDate.now(),
                new ArrayList<>(
                        List.of(
                                new RoutePlaneData(
                                        schip_dussel.getData(),
                                        KLM.getData(),
                                        1,
                                        LocalDateTime.now(),
                                        1,
                                        LocalDateTime.now().plusDays(2)
                                )
                        )
                )));
        assertThat(flight.toString()).contains("Flight{");

    }

}