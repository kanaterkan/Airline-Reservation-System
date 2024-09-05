//package nl.fontys.ais.persistence;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import nl.fontys.ais.datarecords.AirportData;
//import nl.fontys.ais.datarecords.FlightData;
//import nl.fontys.ais.datarecords.PlaneData;
//import nl.fontys.ais.datarecords.RouteData;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Date;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class UtilityClassTest {
//
//    AirportData schiphol;
//    AirportData dusseldorf;
//    AirportData barcelona;
//    FlightData fd;
//    RouteData rd;
//    RouteData rd2;
//    PlaneData pd;
//
//    AirportData ad;
//
//    @BeforeEach
//    public void setup() {
//
//        //schiphol = new AirportData("AMS", "Amsterdam", "Schiphol");
//        //dusseldorf = new AirportData("DUS", "Dusseldorf", "Flughafen Düsseldorf");
//        //rd = new RouteData(5, LocalTime.now(), schiphol.IATA(), dusseldorf.IATA());
//        //pd = new PlaneData(5, 50, 100);
//        //fd = new FlightData(Integer.valueOf(5), new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()), rd, pd);
//        schiphol = new AirportData("AMS", "Amsterdam", "Schiphol");
//        dusseldorf = new AirportData("DUS", "Dusseldorf", "Flughafen Düsseldorf");
//        barcelona = new AirportData("BAR", "Barcelona", "Spain");
//
//        rd = new RouteData(5, LocalTime.now(), schiphol.IATA(), dusseldorf.IATA());
//        pd = new PlaneData(5, 50, 100);
//
//        ad = new AirportData("TOP", "OkK", "OKK");
//        rd = new RouteData(LocalTime.now(), schiphol.IATA(), dusseldorf.IATA());
//        rd2 = new RouteData(LocalTime.now(), dusseldorf.IATA(), barcelona.IATA());
//        pd = new PlaneData(50, 100);
//        fd = new FlightData(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), rd, pd);
//    }
//
//    //Assert that dummy planes record exists in db
//    //@Test
//    void getPlanes() {
//        //Test UtilityClass methods, not storage service
//        /*List<PlaneData> pList = new ArrayList<PlaneData>();
//        UtilityClass.addPlane(pd);
//        pList = UtilityClass.getPlanes();
//        assertThat(pList).contains(pd);*/
//        //writing tests like this won't work, cause "PlaneData" is pre-defined.
//    }
//
//    //Assert that dummy airport record exists in db
//    //@Test
//    void getAirports() {
//        //Test UtilityClass methods, not storage service
//    }
//
//    //Assert that dummy flight route exists in db
//    //@Test
//    void getRoutes() {
//        //Test UtilityClass methods, not storage service
//    }
//
//    //@Test
//    void addFlight() {
//        
//        //arrange
//        //String expected;
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        
//        //act
//        UtilityClass.addFlight(fd); // fails test if the flight already exists
//        UtilityClass.getFlights();
//        
//        //assert
//        assertThat(outContent.toString())
//                .contains("connected to db")
//                .contains("Flights\t\tCreation date\tDeparture date\tRoute\tPlane")
//                .contains("Flights\t\t" + fd.creationDate() + "\t\t" + fd.departureDate() 
//                        + "\t\t" + fd.route().ID() + "\t\t" + fd.planes().ID());
//        //DELETE THE ADDED FLIGHT SO THE TEST CAN BE RUN OVER AND OVER AGAIN
//        UtilityClass.deleteFlight(fd.departureDate(), rd.ID(), pd.ID());
//
//    }
//}
