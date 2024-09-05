package nl.fontys.ais.businesslogic.objects;

import nl.fontys.ais.datarecords.PassengerData;

public class Passenger {

    private final PassengerData pd;
    
    public Passenger(PassengerData pd){
        this.pd = pd;
    }

    public  Integer getID(){
        return this.pd.passengerID();
    }

}
