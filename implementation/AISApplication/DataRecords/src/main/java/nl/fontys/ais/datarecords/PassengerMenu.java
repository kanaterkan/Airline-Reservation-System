package nl.fontys.ais.datarecords;

public record PassengerMenu(PassengerData passengerData,SeatData seatData, MenuData menuData) {

    @Override
    public MenuData menuData() {
        return menuData;
    }

    @Override
    public PassengerData passengerData() {
        return passengerData;
    }

    public SeatData seatData(){
        return seatData;
    }



    @Override
    public String toString() {
        return "PassengerMenu{" +
                "passengerData=" + passengerData +
                ", seatData=" + seatData +
                ", menuData=" + menuData +
                '}';
    }
}
