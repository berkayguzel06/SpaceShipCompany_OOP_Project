
public class spaceStation {
    String location;
    int price = 10000;
    int expanse = 1000; 
    int parkPlace = 3; 
    Stack ships = new Stack(parkPlace);
    public spaceStation(String location){
        this.location = location;
    }
    public int getExpanse() {
        return expanse;
    }
    public int getParkPlace() {
        return parkPlace;
    }
    public String getLocation() {
        return location;
    }
    public Stack getSpaceShip() {
        Stack temp = new Stack(3);
        Stack temp2 = new Stack(3);
        while(ships.isEmpty() == false){
            Ships ship = (Ships)ships.pop();
            temp.push(ship);
            temp2.push(ship);
        }
        while(temp.isEmpty() == false){
            Ships ship = (Ships)temp.pop();
            ships.push(ship);
        }
        return temp2;
    }
    public int getPrice() {
        return price;
    }
    public boolean addShip(String type) { // add this type of ship to the space station
        Ships ship = null;
        if(ships.isFull()){
            System.out.println("Station is full!");
            return false;
        }else{
            if(type.compareTo("A")==0){
                ship = new classA_ship("A",800, 25, 0, location, 4000);
            }else if(type.compareTo("B")==0){
                ship = new classB_ship("B",400, 20, 0, location, 2000);
            }else if(type.compareTo("C")==0){
                ship = new classC_ship("C",200, 15, 0, location, 1000);
            }else{
                System.out.println("Invalid type!");
                return false;
            }
            ships.push(ship);
            return true;
        }
    }
}
