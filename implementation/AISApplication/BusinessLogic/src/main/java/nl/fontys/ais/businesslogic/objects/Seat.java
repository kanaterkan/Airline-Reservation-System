package nl.fontys.ais.businesslogic.objects;

import nl.fontys.ais.datarecords.SeatData;

public class Seat {
    private final SeatData seatData;

    public Seat(SeatData seatData){
        this.seatData = seatData;
    }

    public SeatData getData(){
        return seatData;
    }

    public String toString(){
        return "Seat " + this.seatData.seatName() + " costs " + this.seatData.price() ;
    }
}
