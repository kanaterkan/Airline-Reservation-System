package nl.fontys.ais.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import nl.fontys.ais.datarecords.RouteData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class RouteStorageServiceImpl implements RouteStorageService {

    @Override
    public List<RouteData> getMatchingRoutesByStartOrEndAirport(String pattern) throws PersistenceException {
        DataSource db = UtilityClass.getDataSource();
        String query = """
                       select *
                       from prj2schema.route r 
                       where r.startairport like ?
                       or r.endairport like ?;""";
        List<RouteData> rd = new ArrayList<>();

        try (Connection con = db.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            // check for pattern
            ps.setString(1, "%" + pattern.toUpperCase() + "%");
            ps.setString(2, "%" + pattern.toUpperCase() + "%");
            //store results of the query
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //convert values
                int id = rs.getInt(1);
                Time dur = rs.getTime("estduration");
                LocalTime durt = dur.toLocalTime();
                String start = rs.getString("startairport");
                String end = rs.getString("endairport");

                rd.add(new RouteData(id, durt, start, end));
            }

            if (!rd.isEmpty()) {
                System.out.println("Routes returned: " + rd.size());
                return rd;
            } else {
                System.err.println("Such route doesn't exist");
                return new ArrayList<>();
            }

        } catch (SQLException e) {
            throw new PersistenceException("dog say woof" + e, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RouteData> getAll() throws PersistenceException {
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

    @Override
    public int getFirstClassPassengers(RouteData rd) throws PersistenceException {
        DataSource db = UtilityClass.getDataSource();
        String countQuery = """
                            select count(p.id) as firstclasspassengers
                            from prj2schema.booking b 
                            join prj2schema.passenger p on p.bookingid = b.id
                            join prj2schema.flight f on b.flightid = f.id
                            join prj2schema.flights_routes fr on f.id = fr.flightid 
                            join prj2schema.route r on fr.routeid = r.id 
                            where p.seat like '%A%' and r.id = ?;""";

        int returnedCount = 0;

        try (Connection con = db.getConnection(); PreparedStatement ps = con.prepareStatement(countQuery)) {
            //store results of the query
            ps.setInt(1, rd.ID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //convert values
                int count = rs.getInt(1);
                returnedCount = count;
            }
            return returnedCount;

        } catch (SQLException e) {
            throw new PersistenceException("Unable to get first class passengers", e);
        }
    }

    @Override
    public int getEconomyPlusClassPassengers(RouteData rd) throws PersistenceException {
        DataSource dSou = UtilityClass.getDataSource();
        String countQuery = """
                            select count(p.id) as economypluspassengers
                            from prj2schema.booking b
                            join prj2schema.passenger p on p.bookingid = b.id 
                            join prj2schema.flight f on b.flightid = f.id
                            join prj2schema.flights_routes fr on f.id = fr.flightid 
                            join prj2schema.route r on fr.routeid = r.id 
                            where p.seat like '%B%' and r.id = ?;""";
        int returnedCount = 0;

        try (Connection conn = dSou.getConnection(); PreparedStatement ps = conn.prepareStatement(countQuery);) {
            ps.setInt(1, rd.ID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                returnedCount = count;
            }
            return returnedCount;

        } catch (SQLException e) {
            throw new PersistenceException("Unable to get economy plus class passengers", e);
        }
    }

    @Override
    public int getEconomyClassPassengers(RouteData rd) throws PersistenceException {
        String query = """
                       select count(p.id) as economyclasspassengers
                       from prj2schema.booking b
                       join prj2schema.passenger p on p.bookingid = b.id
                       join prj2schema.flight f on b.flightid = f.id
                       join prj2schema.flights_routes fr on f.id = fr.flightid 
                       join prj2schema.route r on fr.routeid = r.id 
                       where p.seat like '%C%' and r.id = ?;""";
        DataSource ds = UtilityClass.getDataSource();
        int returnedCount = 0;

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, rd.ID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                returnedCount = count;
            }

            return returnedCount;
        } catch (SQLException e) {
            throw new PersistenceException("unable to get economy class passengers", e);
        }
    }

    @Override
    public int getExtraOptionPassengers(RouteData rd) throws PersistenceException {
        String query = """
                       select count(distinct p.id) as extraoptionpassengers
                       from prj2schema.booking b
                       join prj2schema.passenger p on p.bookingid = b.id
                       join prj2schema.flight f on b.flightid = f.id
                       join prj2schema.flights_routes fr on f.id = fr.flightid
                       join prj2schema.route r on fr.routeid = r.id
                       where (    p.seat like '%A%'
                       or p.seat like '%B%'
                       or p.numberofmenus != 0
                       or p.luggage != 0)
                       and r.id = ?;""";
        DataSource ds = UtilityClass.getDataSource();
        int returnedCount = 0;

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, rd.ID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                returnedCount = count;
            }

            return returnedCount;
        } catch (SQLException e) {
            throw new PersistenceException("unable to get extra option passengers", e);
        }
    }

    @Override
    public int getExtraLegroomPassengers(RouteData rd) throws PersistenceException {
        String query = """
                       select count(distinct p.id) as extralegroompassengers
                       from prj2schema.booking b
                       join prj2schema.passenger p on p.bookingid = b.id
                       join prj2schema.flight f on b.flightid = f.id
                       join prj2schema.flights_routes fr on f.id = fr.flightid
                       join prj2schema.route r on fr.routeid = r.id
                       where (p.seat like '%A%' or p.seat like '%B%')
                       and r.id = ?;""";
        DataSource ds = UtilityClass.getDataSource();
        int returnedCount = 0;

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, rd.ID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                returnedCount = count;
            }

            return returnedCount;
        } catch (SQLException e) {
            throw new PersistenceException("unable to get extra leg room passengers", e);
        }
    }

    @Override
    public int getExtraFoodPassengers(RouteData rd) throws PersistenceException {
        String query = """
                       select
                       count(distinct p.id) as extrafoodpassengers
                       from
                       prj2schema.booking b
                       join prj2schema.passenger p on
                       p.bookingid = b.id
                       join prj2schema.flight f on
                       b.flightid = f.id
                       join prj2schema.flights_routes fr on
                       f.id = fr.flightid
                       join prj2schema.route r on
                       fr.routeid = r.id
                       where
                       p.numberofmenus != 0
                       and r.id = ?;""";
        DataSource ds = UtilityClass.getDataSource();
        int returnedCount = 0;

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, rd.ID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                returnedCount = count;
            }

            return returnedCount;
        } catch (SQLException e) {
            throw new PersistenceException("unable to get extra food passengers", e);
        }
    }

    @Override
    public int getExtraLuggagePassengers(RouteData rd) throws PersistenceException {
        String query = """
                       select
                       count(distinct p.id) as extraluggagepassengers
                       from
                       prj2schema.booking b
                       join prj2schema.passenger p on
                       p.bookingid = b.id
                       join prj2schema.flight f on
                       b.flightid = f.id
                       join prj2schema.flights_routes fr on
                       f.id = fr.flightid
                       join prj2schema.route r on
                       fr.routeid = r.id
                       where
                       p.luggage >= 20
                       and r.id = ?;""";
        DataSource ds = UtilityClass.getDataSource();
        int returnedCount = 0;

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, rd.ID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                returnedCount = count;
            }

            return returnedCount;
        } catch (SQLException e) {
            throw new PersistenceException("unable to get extra luggage passengers", e);
        }
    }

    @Override
    public double getBookingAndFlightPriceDifference(RouteData rd) throws PersistenceException {
        // get difference between flights base price and bookings price
        String query = """
                       select b.price - f.baseprice
                       from prj2schema.booking b
                       join prj2schema.flight f on b.flightid = f.id
                       join prj2schema.flights_routes fr on f.id = fr.flightid
                       join prj2schema.route r on fr.routeid = r.id
                       where r.id = ?;""";
        DataSource ds = UtilityClass.getDataSource();
        double returnedDifference = 0;

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, rd.ID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double count = rs.getDouble(1);
                returnedDifference = count;
            }

            return returnedDifference;
        } catch (SQLException e) {
            throw new PersistenceException("unable to get booking - flight price difference", e);
        }
    }

    @Override
    public int getNumberOfRoutesAssociatedOnSameFlightAsRoute(RouteData rd) throws PersistenceException {
        //get number of routes associated with the same flight as specified route
        String query = """
                       select count(fr.routeid)
                       from prj2schema.flights_routes fr
                       where fr.flightid in (select flightid from prj2schema.flights_routes where routeid = ?);""";
        DataSource ds = UtilityClass.getDataSource();
        int returnedCount = 0;

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, rd.ID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                returnedCount = count;
            }

            return returnedCount;
        } catch (SQLException e) {
            throw new PersistenceException("unable to get any routes associated with the same flight", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RouteData add(RouteData data) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(RouteData data) {
    }

}
