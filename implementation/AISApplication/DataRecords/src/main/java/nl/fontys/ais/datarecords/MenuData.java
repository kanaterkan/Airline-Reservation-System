package nl.fontys.ais.datarecords;

public record MenuData(int ID, String name, double price, int amount) {

    public MenuData(String name, double price, int amount){
        this(1, name, price, amount);
        System.out.println("MenuData constructor is called");
    }

    public MenuData setAmount(int amount){
        return new MenuData(this.ID(), this.name(), this.price(), amount);
    }


    @Override
    public String toString() {
        return name() + "-" + price();
    }

}
