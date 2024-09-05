/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nl.fontys.ais.persistence;

import nl.fontys.ais.datarecords.*;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nedus
 */
public class UtilityClass {

    // --------------- DATABASE CONNECTION RELATED FIELDS ----------------
    private static final Map<String, DataSource> datasourceByName = new HashMap<>();

    //Change to dev if local database is desired
    private static final String sourceName = "jdbc.pg.prod";

    static DataSource getDataSource() {
        // dataSourceByName is a map, serving as a cache.
        return datasourceByName.computeIfAbsent(UtilityClass.sourceName, (s) -> {
            Properties props = properties();

            PGSimpleDataSource source = new PGSimpleDataSource();

            String prefix = UtilityClass.sourceName + ".";
            String url = props.getProperty(prefix + "url");
            source.setUrl(url);

            String user = props.getProperty(prefix + "username");
            source.setUser(user);

            source.setPassword(props.getProperty(prefix + "password"));
            String pingQuery = "SELECT current_database(), now()::TIMESTAMP as now;";
            try (Connection con = source.getConnection(); // ping the database for success.
                     PreparedStatement pst = con.prepareStatement(pingQuery)) {
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        Object db = rs.getObject("current_database");
                        Object now = rs.getObject("now");
                        System.out.println("connected to db " + db.toString() + ", date/time is " + now.toString());
                        con.close();
                    }
                }

            } catch (Exception ex) {
                throw new RuntimeException("Something went wrong in the datasource ... ", ex);
            }
            return source;
        });
    }

    // read properties
    static Properties properties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("../Persistence/src/main/resources/application.properties")) {
            properties.load(fis);
        } catch (IOException ignored) {
            System.out.println("attempt to read file from well known location failed'");
        }
        return properties;
    }

    // --------------- ADD & GETTER METHODS -----------------------------------
    /**
     * method for retrieving planes data from the database, adding the data to a
     * list and returning the list.
     * <p>
     *
     */
    static List<PlaneData> getPlanes() throws PersistenceException {
        DataSource db = UtilityClass.getDataSource();
        String query = "select * from prj2schema.plane order by id";

        try (Connection con = db.getConnection(); PreparedStatement pstm = con.prepareStatement(query)) {
            ResultSet result = pstm.executeQuery();

            //System.out.println("|Planes|ID|Number of seats|Total capacity|");
            List<PlaneData> planes = new ArrayList<>();

            while (result.next()) {
                int id = result.getInt(1);
                int nrSeats = result.getInt("nrOfSeats");
                int capacity = result.getInt("totalCapacity");

                //System.out.println(" Plane: " + id + ",  " + nrSeats + ",            " + capacity + ".");
                planes.add(new PlaneData(id, nrSeats, capacity));
            }
            return planes;

        } catch (Exception ex) {
            throw new PersistenceException("Something went wrong with executing getPlanes query ... ", ex);
        }
    }

    /**
     * method for retrieving route data from the database, adding the data to a
     * list and returning the list.
     * <p>
     *
     */
    static List<RouteData> getRoutes() throws PersistenceException {
        //Logging to check performance
        Logger logger = Logger.getLogger("getRoutes");
        logger.log(Level.INFO, "Start retrieving routes from database");
        //Time indicator
        long startTime = System.currentTimeMillis();
        //The main code of the getRoutes method
        DataSource db = UtilityClass.getDataSource();
        String query = """
                   select r.id, r.cost, r.estduration, r.startairport, a.timezone, r.endairport, a2.timezone
                   from prj2schema.route r
                   inner join prj2schema.airport a on a.iata = r.startairport
                   inner join prj2schema.airport a2 on a2.iata = r.endairport
                   order by id;
                   """;

        try (Connection con = db.getConnection(); PreparedStatement pstm = con.prepareStatement(query)) {
            ResultSet result = pstm.executeQuery();

            List<RouteData> routes = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                Time estDuration = result.getTime("estduration");
                String startAirport = result.getString("startairport");
                int startTimezone = result.getInt(5);
                String endAirport = result.getString("endairport");
                int endTimezone = result.getInt(7);
                int cost = result.getInt("cost");

                routes.add(new RouteData(id, cost, estDuration.toLocalTime(), startAirport, startTimezone, endAirport, endTimezone));
            }
            logger.log(Level.INFO, "Successfully retrieved " + routes.size() + " routes from database");
            long endTime = System.currentTimeMillis(); // end time
            long totalTime = endTime - startTime; // total time
            System.out.println("Total time to get all flights: " + totalTime + "ms");
            return routes;

        } catch (Exception ex) {
            throw new PersistenceException("Something went wrong with executing getRoutes query ... " + ex, ex);
        }
    }

    /**
     * method retrieves all airport data from the database
     * <p>
     *
     */
    static List<AirportData> getAirports() throws PersistenceException {
        DataSource db = UtilityClass.getDataSource();
        String query = "select * from prj2schema.airport";
        List<AirportData> ad = new ArrayList<>();

        try (Connection con = db.getConnection(); PreparedStatement pstm = con.prepareStatement(query)) {
            ResultSet result = pstm.executeQuery();

            //System.out.println("|Airports|IATA|------Name------|Location|");
            while (result.next()) {
                String iata = result.getString("iata");
                String name = result.getString("name");
                String location = result.getString("location");
                //System.out.println(" Airport: " + iata + ",   " + name + "," + location);
                ad.add(new AirportData(iata, name, location));
            }
            return ad;
        } catch (Exception ex) {
            throw new PersistenceException("Something went wrong with executing getAirports query ... ", ex);
        }
    }

    /**
     * method retrieves all airport data from the database, based on the IATA
     * code provided.
     *
     * @param IATA - String reference
     *
     */
    static AirportData getAirportByIATA(String IATA) throws PersistenceException {
        DataSource db = UtilityClass.getDataSource();
        String query = "select * from prj2schema.airport WHERE iata=?";

        try (Connection con = db.getConnection(); PreparedStatement pstm = con.prepareStatement(query)) {
            pstm.setString(1, IATA);
            ResultSet result = pstm.executeQuery();

            if (result.next()) {
                String iata = result.getString("iata");
                String name = result.getString("name");
                String location = result.getString("location");
                return new AirportData(iata, name, location);
            }
        } catch (Exception ex) {
            throw new PersistenceException("Something went wrong with executing getAirportByIATA query ... ", ex);
        }
        return null;
    }

    static CustomerData addCustomer(CustomerData customerData) throws PersistenceException {
        Connection conn;
        PreparedStatement ps;
        DataSource db = UtilityClass.getDataSource();
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("insert into prj2schema.customer (firstname, lastname, dateofbirth, mobile, address, email, iban) values (?,?,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerData.firstName());
            ps.setString(2, customerData.lastName());
            ps.setDate(3, Date.valueOf(customerData.dateOfBirth()));
            ps.setString(4, customerData.phoneNumber());
            ps.setString(5, customerData.address());
            ps.setString(6, customerData.email());
            ps.setString(7, customerData.IBAN());

            int out = ps.executeUpdate();
            if (out == 0) {
                throw new SQLException("Failed to insert customer record");
            }
            conn.commit();

            // Get ID from resultset
            ResultSet genID = ps.getGeneratedKeys();
            int cusID = 0;
            if(genID.next()) {
                cusID = genID.getInt(1);
            }
            CustomerData cd = new CustomerData(cusID, customerData.firstName(), customerData.lastName(), customerData.dateOfBirth(), customerData.phoneNumber(), customerData.address(), customerData.email(), customerData.IBAN());
            //Create new customerdata obj with ID
            return cd;
            //return customerdata

        } catch (Exception e) {
            throw new PersistenceException("Something went wrong with executing addCustomer query ..." + e, e);
        }
    }
    static void addBooking(BookingData bookingData) throws PersistenceException{
        DataSource ds = getDataSource();
        String query = "insert into prj2schema.booking (countofseats, discount, customerid, flightid, price) values (?, ?, ?, ?, ?);";

        try {
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bookingData.countOfSeats());
            ps.setDouble(2, bookingData.discount());
            ps.setInt(3, bookingData.customerId());
            ps.setInt(4, bookingData.flightId());
            ps.setDouble(5, bookingData.price());

            int num = ps.executeUpdate();
            if (num == 0) {
                throw new SQLException("Failed to insert flight record");
            }

            ResultSet genId = ps.getGeneratedKeys();
            int bookingID = 0;
            while (genId.next()){
                bookingID = genId.getInt(1);
            }

            // iterate over each passenger .. exec insert statement ...
            for (PassengerData psd : bookingData.getPassengers()) {
                String pQuery = "insert into prj2schema.passenger " +
                        "(firstname, lastname, dateofbirth, email, seat, numberofmenus, luggage, menuid, bookingid)" +
                        " values(?,?,?,?,?,?,?,?,?);";
                PreparedStatement psR = con.prepareStatement(pQuery);
                if (psd.passengerID() == 0 || bookingID == 0) {
                    throw new SQLException("Cannot add booking with invalid passenger or booking ID.");
                }
                psR.setString(1, psd.passengerFName());
                psR.setString(2, psd.passengerLName());
                psR.setDate(3, Date.valueOf(psd.dateOfBirth()));
                psR.setString(4, psd.passengerEmail());
                psR.setString(5, psd.seat().seatName());
                psR.setInt(6, psd.menu().amount());
                psR.setDouble(7, psd.luggage());
                psR.setInt(8, psd.menu().ID());
                psR.setInt(9, bookingID);
                psR.executeUpdate();



            }
        } catch (SQLException se) {
            throw new PersistenceException("couldnt add booking. " + se, se);
        }
    }


    static void addFlight(FlightData fd) throws PersistenceException {
        Connection conn;
        PreparedStatement ps;
        DataSource db = UtilityClass.getDataSource();

        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("insert into prj2schema.flight (creationdate, baseprice) values(?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ps.setDouble(2, 300.0);

            int num = ps.executeUpdate();
            if (num == 0) {
                throw new SQLException("Failed to insert flight record");
            }
            //...

            ResultSet genId = ps.getGeneratedKeys();
            int flightId;
            if (genId.next()) {
                flightId = genId.getInt(1);

                for (RoutePlaneData rpd : fd.getRoutePlanes()) {
                    PreparedStatement psR = conn.prepareStatement(
                            "insert into prj2schema.flights_routes (flightId, routeId, planeId, departuredate, departuretime, arrivaldate, arrivaltime) values (?, ?, ?, ?, ?, ?, ?)");
                    if (rpd.routeData().ID() == 0 || rpd.getPlaneData().ID() == 0) {
                        throw new SQLException("Cannot add flight with invalid route / plane ID.");
                    }
                    psR.setInt(1, flightId);
                    psR.setInt(2, rpd.routeData().ID());
                    psR.setInt(3, rpd.planeData().ID());
                    psR.setDate(4, java.sql.Date.valueOf(rpd.getDepartureDate()));
                    psR.setTime(5, Time.valueOf(rpd.getDepartureTime()));
                    psR.setDate(6, java.sql.Date.valueOf(rpd.getArrivalDate()));
                    psR.setTime(7, Time.valueOf(rpd.getArrivalTime()));
                    psR.executeUpdate();
                }
            }
            conn.commit();
        } catch (Exception ex) {
            throw new PersistenceException("Something went wrong with executing addFlight query ...", ex);
        }
    }

    /**
     * method for retrieving flight data from the database
     * <p>
     *
     */
    static List<FlightRouteData> getFlights() throws PersistenceException {
        DataSource db = UtilityClass.getDataSource();
        String query = """
                 select f.id, f.creationdate, r.estduration, r.startairport, r.endairport, a1.timezone, a2.timezone, r.cost, frs.departuredate, frs.departuretime, frs.arrivaldate, frs.arrivaltime, frs.planeid, p.nrofseats, p.totalcapacity
                   from prj2schema.flights_routes frs
                   join prj2schema.flight f on frs.flightid = f.id
                   join prj2schema.route r on frs.routeid = r.id
                   join prj2schema.plane p on frs.planeid = p.id
                   join prj2schema.airport a1 on a1.iata = r.startairport
                   join prj2schema.airport a2 on a2.iata = r.endairport
                """;
        //initialize empty list of flightData
        List<FlightRouteData> flights = new ArrayList<>();

        try (Connection con = db.getConnection(); PreparedStatement pstm = con.prepareStatement(query)) {
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                //Flight data fields
                int id = result.getInt("id");
                int cost = result.getInt("cost");
                Date cd = result.getDate("creationdate");
                String sa = result.getString("startairport");
                String ea = result.getString("endairport");
                Date dd = result.getDate("departuredate");
                Time dt = result.getTime("departuretime");
                Date ad = result.getDate("arrivaldate");
                Time at = result.getTime("arrivaltime");
                Time estduration = result.getTime("estduration");
                int a1 = result.getInt(6);
                int a2 = result.getInt(7);
                PlaneData pd = new PlaneData(result.getInt("planeid"), result.getInt("nrofseats"), result.getInt("totalcapacity"));
                flights.add(new FlightRouteData(id, cd.toLocalDate(), estduration.toLocalTime(), sa, a1, ea, a2, cost, dd.toLocalDate(), dt.toLocalTime(), ad.toLocalDate(), at.toLocalTime(), pd));
            }
            return flights;

        } catch (Exception ex) {

            throw new PersistenceException("Something went wrong with executing getflights query ... " + ex, ex);

        }
    }

    static void deleteFlight(int flightID) {
        DataSource db = getDataSource();
        String sql = "delete from prj2schema.flight where id=?";
        try (Connection conn = db.getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, flightID);
            p.executeUpdate();
        } catch (SQLException ex) {
            try {
                throw new PersistenceException("Something went wrong with executing deleteFlight query ... ", ex);
            } catch (PersistenceException ex1) {
                Logger.getLogger(UtilityClass.class.getName()).log(Level.INFO, null, ex1);
            }
        }
    }

    //method for retrieving the Seats
    // static void retrieveMenus(){
    //  Logger logger = Logger.getLogger("getMenus");
    //   logger.log(Level.INFO,"Start retrieving the menus from the database");
    //Time indicator
    // long startTime = System.currentTimeMillis();
    //main code
    //  DataSource db = UtilityClass.getDataSource();
    // String query = """
    //  select *
    //   from prj2schema.menuList;
    //     """;
    //  try(Connection con = db.getConnection(); PreparedStatement pstm = con.prepareStatement(query)){
    //
    //  List<Menu> menus = new ArrayList<>();
    //   while(result.next()){
    //    int id = result.getInt(1);
    //     String menu = result.getMenu("menuName");
    //   menus.add(new MenuData(menu));
    //   return menus;
    //  } catch(Exception e){
    //  throw new PersistenceException("Something went wrong with executing getMenus query...",ex);
    // }
    // }
    // }
    //method for retrieving the menus
    static List<MenuData> getMenus() throws PersistenceException {
        Logger logger = Logger.getLogger("getMenus");
        logger.log(Level.INFO, "Start retrieving the menus from the db");
        long startTime = System.currentTimeMillis();
        DataSource db = UtilityClass.getDataSource();
        String query = "select * from prj2schema.menu order by id";
        try (Connection connection = db.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<MenuData> menuList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                double price = resultSet.getDouble(3);
                int amount = resultSet.getInt(4);
                menuList.add(new MenuData(id, name, price, amount));

            }

            logger.log(Level.INFO, "successfully retrieved " + menuList.size() + " menus");
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Total time to get all menus: " + totalTime + "ms");
            return menuList;

        } catch (Exception e) {
            throw new PersistenceException("Something went wrong with the executing getMenus query:", e);
        }
    }



    static List<SeatData> getSeats() throws PersistenceException {
        DataSource db = getDataSource();
        String query = "select * from prj2schema.seat";
        try (Connection connection = db.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<SeatData> seats = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                double price = resultSet.getDouble(2);
                boolean legroom = resultSet.getBoolean(3);
                seats.add(new SeatData(name, price, legroom));

            }
            return seats;

        } catch (Exception e) {
            throw new PersistenceException("Unable to getSeats()", e);
        }
    }

    static List<BookingData> getBookings() throws PersistenceException {
        DataSource db = getDataSource();
        String query = "select * from prj2schema.booking";
        List<BookingData> bookingData = new ArrayList<>();

        try (Connection con = db.getConnection(); PreparedStatement pstm = con.prepareStatement(query)) {
            ResultSet result = pstm.executeQuery();

            while (result.next()) {
                int id = result.getInt(1);
                int countSeats = result.getInt(2);
                double disc = result.getDouble(3);
                int cusId = result.getInt(4);
                int flightId = result.getInt(5);
                double price = result.getDouble(6);
                List<PassengerData> pd = getPassengersRelatedToBooking(id);
                bookingData.add(new BookingData(id, countSeats, disc, cusId, flightId, price, pd));
            }
            //System.out.println("Bookings returned " + bookingData.size());
            return bookingData;
        } catch (Exception ex) {
            throw new PersistenceException("Unable to getBookings() ... ", ex);
        }
    }

    static List<BookingData> getBookings(int customerId) throws PersistenceException {
        DataSource db = getDataSource();
        String query = "select * from prj2schema.booking b where b.customerid=?";
        List<BookingData> bookingData = new ArrayList<>();

        try (Connection con = db.getConnection(); PreparedStatement pstm = con.prepareStatement(query)) {
            pstm.setInt(1, customerId);
            ResultSet result = pstm.executeQuery();

            while (result.next()) {
                int id = result.getInt(1);
                int countSeats = result.getInt(2);
                double disc = result.getDouble(3);
                int cusId = result.getInt(4);
                int flightId = result.getInt(5);
                double price = result.getDouble(6);
                List<PassengerData> pd = getPassengersRelatedToBooking(id);
                bookingData.add(new BookingData(id, countSeats, disc, cusId, flightId, price, pd));
            }
            //System.out.println("Bookings returned " + bookingData.size());
            if (bookingData.isEmpty()) {
                System.out.println("no bookings returned");
                return new ArrayList<>();
            } else {
                return bookingData;
            }
        } catch (SQLException ex) {
            throw new PersistenceException("Unable to getBookings() ... ", ex);
        }
    }

    static List<PassengerData> getPassengersRelatedToBooking(int bookingId) throws PersistenceException {
        String query = """
                       select
                       p.id,
                       p.firstname,
                       p.lastname,
                       p.dateofbirth,
                       p.email,
                       p.seat,
                       p.luggage,
                       m.id as menuid,
                       m.name as menuitemname,
                       m.price as menuitemprice,
                       m.amount as itemamount,
                       s.seatname,
                       s.price as seatprice,
                       s.legroom as seatlegroom
                       from
                       prj2schema.passenger p
                       join prj2schema.booking b on
                       p.bookingid = b.id
                       join prj2schema.menu m on
                       p.menuid = m.id
                       join prj2schema.seat s on
                       p.seat = s.seatname
                       where
                       b.id = ?;""";
        DataSource ds = getDataSource();
        List<PassengerData> pd = new ArrayList<>();

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, bookingId);
            ResultSet rezSet = ps.executeQuery();

            while (rezSet.next()) {
                // passenger related
                int id = rezSet.getInt(1);
                String first = rezSet.getString(2);
                String last = rezSet.getString(3);
                Date dob = rezSet.getDate(4);
                LocalDate dobLocal = dob.toLocalDate();
                String email = rezSet.getString(5);
                //String seat = rezSet.getString(6);
                double luggage = rezSet.getDouble(7);
                // menu related
                int menuid = rezSet.getInt(8);
                String menuItemName = rezSet.getString(9);
                double menuPrice = rezSet.getDouble(10);
                int menuAmount = rezSet.getInt(11);

                // seat related
                String seatNumber = rezSet.getString(12);
                double seatPrice = rezSet.getDouble(13);
                boolean seatLegroom = rezSet.getBoolean(14);

                MenuData menuData = new MenuData(menuid, menuItemName, menuPrice, menuAmount);
                SeatData seatData = new SeatData(seatNumber, seatPrice, seatLegroom);
                PassengerData passengerData = new PassengerData(id, first, last, dobLocal, email, seatData, menuData, luggage);

                pd.add(passengerData);

            }
            
            return pd;

        } catch (SQLException se) {
            throw new PersistenceException("Unable to getPassengersRelatedToBooking", se);
        }

    }

    static boolean setSeatNumber(String seatNumber, PassengerData pd) throws PersistenceException {
        DataSource ds = getDataSource();
        String query = "update prj2schema.passenger set seat = ? where id = ?;";

        boolean seatChanged;

        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, seatNumber);
            ps.setInt(2, pd.passengerID());

            ps.executeUpdate();

            seatChanged = true;
        } catch (SQLException ex) {
            seatChanged = false;
            throw new PersistenceException("failed to update seat number", ex);
        }
        return seatChanged;
    }

    static int getMenuId(String itemName) throws PersistenceException {
        DataSource ds = getDataSource();
        String query = """
                       select id
                       from prj2schema.menu
                       where "name" = ?;""";
        int returnId = 0;

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, itemName);
            ResultSet rezSe = ps.executeQuery();

            if (rezSe.next()) {
                int receivedId = rezSe.getInt(1);
                returnId = receivedId;
            }
            return returnId;
        } catch (SQLException ex) {
            throw new PersistenceException("failed to get menu id", ex);
        }

    }

    static boolean setMenuItem(String itemName, PassengerData pd) throws PersistenceException {
        DataSource ds = getDataSource();
        String query = "update prj2schema.passenger set menuid = ? where id = ?;";
        int itemId = getMenuId(itemName);

        boolean itemChanged = false;

        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, itemId);
            ps.setInt(2, pd.passengerID());

            ps.executeUpdate();

            itemChanged = true;
        } catch (SQLException ex) {
            itemChanged = false;
            throw new PersistenceException("failed to update menu item", ex);
        }
        return itemChanged;
    }

    static boolean setLuggageWeight(double weight, PassengerData pd) throws PersistenceException {
        DataSource ds = getDataSource();
        String query = "update prj2schema.passenger set luggage = ? where id = ?;";

        boolean weightChanged;

        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, weight);
            ps.setInt(2, pd.passengerID());

            ps.executeUpdate();

            weightChanged = true;
        } catch (SQLException ex) {
            weightChanged = false;
            throw new PersistenceException("failed to update luggage weight", ex);
        }
        return weightChanged;
    }
    


}
