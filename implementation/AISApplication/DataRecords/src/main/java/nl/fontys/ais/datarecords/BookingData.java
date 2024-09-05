package nl.fontys.ais.datarecords;

import java.util.List;

public record BookingData(int ID, int countOfSeats, double discount, int customerId ,int flightId, double price, List<PassengerData> passengers) {

    public BookingData(int ID,int countOfSeats, double discount, int customerId, int flightId,double price){
        this(ID, countOfSeats,discount, customerId,flightId,price, null);
    }
    public List<PassengerData> getPassengers(){
        return passengers;
    }


}


//, int passengerId, int menuId, int seatsId,
