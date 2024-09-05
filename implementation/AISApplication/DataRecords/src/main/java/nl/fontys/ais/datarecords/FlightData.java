package nl.fontys.ais.datarecords;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public record FlightData(int ID, LocalDate creationDate,List<RoutePlaneData> routes) {
    public FlightData(LocalDate creationDate, List<RoutePlaneData> routes){
        this(1, creationDate,  routes);
    }

    public FlightData(int ID){
        this(ID,null,null);
    }

    public List<RoutePlaneData> getRoutePlanes(){
        return routes;
    }

    @Override
    public String toString() {
        return "FlightData{" +
                "ID=" + ID +
                ", routes=" + routes +
                '}';
    }
}
