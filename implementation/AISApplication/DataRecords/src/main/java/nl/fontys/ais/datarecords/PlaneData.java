package nl.fontys.ais.datarecords;

public record PlaneData(int ID, int numberOfSeats, double capacity) {
    public PlaneData(int numberOfSeats, double capacity){
        this(1, numberOfSeats, capacity);
    }

    @Override
    public String toString() {
        return "PNO {" + ID + "}, NO seat {" + numberOfSeats + "}, cap {" + capacity + "}";
    }

}
