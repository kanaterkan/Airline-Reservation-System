package nl.fontys.ais.businesslogic.objects;

import nl.fontys.ais.datarecords.BookingData;

public class Booking {
    private final BookingData bd;


    
    public Booking(BookingData bd){
        this.bd = bd;
    }

    public BookingData getBd() {
        return bd;
    }


}
