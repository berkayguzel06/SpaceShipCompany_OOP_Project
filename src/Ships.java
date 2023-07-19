
public abstract class Ships {
    protected String shipType;
    protected double expanse;
    protected int numberOfSeats;
    protected int numberOfPassengers;
    protected String destination;
    protected int price;

    public Ships(String shipType, double expanse, int numberOfSeats, int numberOfPassengers, String destination, int price) {
        this.shipType = shipType;
        this.expanse = expanse;
        this.numberOfSeats = numberOfSeats;
        this.numberOfPassengers = numberOfPassengers;
        this.destination = destination;
        this.price = price;
    }

    public abstract void fly();


    public String getShipType() {
        return shipType;
    }

    public int getPrice() {
        return price;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public double getExpanse() {
        return expanse;
    }

    public void setExpanse(double expanse) {
        this.expanse = expanse;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
