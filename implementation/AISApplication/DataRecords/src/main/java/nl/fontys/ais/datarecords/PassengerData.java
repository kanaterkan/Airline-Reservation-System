package nl.fontys.ais.datarecords;

import java.time.LocalDate;

public record PassengerData (int passengerID, String passengerFName, String passengerLName, LocalDate dateOfBirth, String passengerEmail, SeatData seat, MenuData menu, Double luggage) {
    public PassengerData( int passengerID) {
        this(passengerID, null, null, null, null, null, null, null);
    }
    public PassengerData( String passengerFName, String passengerLName, LocalDate dateOfBirth, String passengerEmail, SeatData seat, MenuData menu, Double luggage) {
        this(1, passengerFName , passengerLName , dateOfBirth, passengerEmail, seat, menu, luggage);
    }

    @Override
    public String toString() {
        return "PassengerData{" +
                "passengerID=" + passengerID +
                ", passengerFName='" + passengerFName + '\'' +
                ", passengerLName='" + passengerLName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", passengerEmail='" + passengerEmail + '\'' +
                ", seat=" + seat +
                ", menu=" + menu +
                ", luggage=" + luggage +
                '}';
    }
}