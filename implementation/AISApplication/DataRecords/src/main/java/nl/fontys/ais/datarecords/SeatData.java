package nl.fontys.ais.datarecords;





public record SeatData(String seatName, double price, boolean legroom) {

    @Override
    public String toString(){
        return seatName() + " | price: "+  price() + " | extra legroom: " +legroom();
    }

}
