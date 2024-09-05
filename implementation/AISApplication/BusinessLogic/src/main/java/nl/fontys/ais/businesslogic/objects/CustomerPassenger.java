package nl.fontys.ais.businesslogic.objects;

import nl.fontys.ais.datarecords.*;

public class CustomerPassenger {
PassengerMenu passengerMenu;

public CustomerPassenger(PassengerMenu passengerMenu) {
    this.passengerMenu = passengerMenu;
}



    @Override
    public String toString() {
        return "BookingPassenger{" +
                "bookingPassengerData=" + passengerMenu +
                '}';
    }
}
