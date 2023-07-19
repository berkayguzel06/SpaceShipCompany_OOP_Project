
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
public class company{
    Stack spaceStations = new Stack(20);
    private String[] locations;              // contains the locations of planets
    private String[] Validlocations;         // contains the locations of planets that has not space station
    private String[] passengerPool=new String[205];;          // contains all the passengers
    private String[] todayPassengers;        // contains the passengers that will travel today
    private double fame = 0;                 // fame of the company
    private int money = 21000;             // money of the company     (deneme için yüksek değişecek unutma)
    private int currentDay = 1;              // current day
    private double A_shipTicketPrice = 100;  // ticket price of A type ship
    private double B_shipTicketPrice = 50;  // ticket price of B type ship
    private double C_shipTicketPrice = 25;   // ticket price of C type ship
    private int spaceShipsNumber = 0;        // number of space ships in company
    private int spaceStationsNumber = 0;     // number of space stations in company
    private int todayPassengerNumber = 10;   // number of passengers that will travel today
    
    private static company instance = null;
    private company(){ // run this constructor if manager registers for the first time
        try {
            getPassenger(passengerPool);
			// getting passengers according to number of toay passengers
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			this.locations = getLocations(locations); // gets planet locaitons
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			this.Validlocations = getLocations(Validlocations);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        purchaseSpaceStation("Zerthor"); // default start point
        purchaseSpaceShip("Zerthor","C"); // default start point
        getPassenger(passengerPool,todayPassengerNumber);
    }
    public static company getInstance(){ // get instance of company
        if(instance==null){
            instance = new company();
        }
        return instance;
    }
    private void getPassenger(String[] pool, int todayPassengerNumber){ // gets daily passengers from pool
        todayPassengers = new String[todayPassengerNumber];
        Random r = new Random(); 
        for (int i = 0; i < todayPassengerNumber; i++) {
            String sp = pool[r.nextInt(pool.length-1)]; // get random passenger from pool
            todayPassengers[i] = sp;
        }
    }
    private void getPassenger(String[] pool) throws FileNotFoundException{ // gets passengers from file
        File file = new File("passengers.txt");
        Scanner sc = new Scanner(file);
        int index = 0;
        while(sc.hasNextLine()){
            pool[index] = sc.nextLine();
            index++;
        }
        sc.close();
    }
    public String[] getlistpassenger(){
        return todayPassengers;
    }
    private String[] getLocations(String[] locations) throws FileNotFoundException{ // gets locations from file
        File file = new File("planets.txt");
        Scanner sc = new Scanner(file);
        locations = new String[20];
        int index = 0;
        while(sc.hasNextLine()){
            locations[index] = sc.nextLine();
            index++;
        }
        sc.close();
        Validlocations = locations;
        return locations;
    }
    
    public void dayCycle() throws IOException{ // change day
        Random r = new Random();
        currentDay++;
        fame += 0.5; // daily fame increase
        double expanse = getExpanse();
        double income = getIncome();
        double profit = income-expanse;
        money+=profit; // update money
        double numberOfPassengers = 0;
    	numberOfPassengers = (fame*10)/Math.log(A_shipTicketPrice+B_shipTicketPrice+C_shipTicketPrice); // calculate number of passengers
        todayPassengerNumber = (int)numberOfPassengers;
        int randomize = r.nextInt(10);
        if(randomize<7 && randomize>5){ // random passenger increase/decrease to gain dynamism
            todayPassengerNumber=todayPassengerNumber*2;
        }else if(randomize>3&&randomize<5){
            todayPassengerNumber=todayPassengerNumber/2;
        }
        int passengerSeats = 0;
        for (int i = 0; i < locations.length; i++) { // calculate the number of seats of space shis
            Stack ships = getSpaceShip(locations[i]);
            if(ships!=null){
                while(!ships.isEmpty()){
                    Ships ship = (Ships)ships.pop();
                    passengerSeats += ship.getNumberOfSeats();
                }
            }
        }
        if(passengerSeats<todayPassengerNumber){ // if seats smaller than passengers
            todayPassengerNumber=passengerSeats;
        }
        getPassenger(passengerPool,todayPassengerNumber);// calculate daily passengers
    }
    private int calcExpanse(){ // calculate expanse
        spaceStation spaceStation = (spaceStation)spaceStations.peek(); // take spacestation
        int expanse = 0;
        for (int i = 0; i < locations.length; i++) { // take space ships
            Stack ships = getSpaceShip(locations[i]);
            if(ships!=null){
                while(!ships.isEmpty()){
                    Ships ship = (Ships)ships.pop();
                    expanse += ship.getExpanse(); // add every spaceship expanse to the total expanse
                }
            }
        }
        Random r = new Random();
        int randomize = r.nextInt(10); // random expanse increase to gain dynamism
        if(randomize<7 && randomize>5){
            expanse=expanse*2;
        }
        expanse += spaceStationsNumber*spaceStation.getExpanse(); // add space station expanses to total expanse
        return expanse;
    }
    private int clacIncome(){ //calculate income
        int temp = todayPassengerNumber;
        int income = 0;
        for (int i = 0; i < locations.length; i++) { // to gets every location
            Stack ships = getSpaceShip(locations[i]); // gets in location space ships if there is a space station
            if(ships!=null){
                while(!ships.isEmpty()){
                    Ships ship = (Ships)ships.pop(); // take the every ship inside stack
                    if(temp<ship.getNumberOfSeats()){ // if passenger number is smaller than seat number
                        if(ship.getShipType().compareTo("A")==0){
                            income += temp*A_shipTicketPrice;
                        }else if(ship.getShipType().compareTo("B")==0){
                            income += temp*B_shipTicketPrice;
                        }else if(ship.getShipType().compareTo("C")==0){
                            income += temp*C_shipTicketPrice;
                        }
                        break;
                    }else{ // if passenger number is higher than seat number
                        if(ship.getShipType().compareTo("A")==0){
                            income += ship.getNumberOfSeats()*A_shipTicketPrice;
                        }else if(ship.getShipType().compareTo("B")==0){
                            income += ship.getNumberOfSeats()*B_shipTicketPrice;
                        }else if(ship.getShipType().compareTo("C")==0){
                            income += ship.getNumberOfSeats()*C_shipTicketPrice;
                        }
                        temp -= ship.getNumberOfSeats(); // decrase passengers that takes a seat in spaceship
                    }
                }
            }
        }
        return income;
    }
    public double getExpanse(){ // gets expanse
        return calcExpanse();
    }
    public double getIncome(){ // gets income
        return clacIncome();
    }
    public int getDay(){ // gets day
        return currentDay;
    }
    public int getTodayPassengers(){ // get daily player
        return todayPassengerNumber;
    }
    public int getTotalSpaceShip(){ // gets total space ship
        return spaceShipsNumber;
    }
    public int getTotalSpaceStation(){ // gets total space station
        return spaceStationsNumber;
    }
    public double getFame(){ // gets fame
        return fame;
    }
    public int getMoney(){ // gets money
        return money;
    }
    public double getA_ShipPrice(){ // gets class a price
        return A_shipTicketPrice;
    }
    public double getB_ShipPrice(){ // gets class b price
        return B_shipTicketPrice;
    }
    public double getC_ShipPrice(){ // gets class c price
        return C_shipTicketPrice;
    }
    public void setA_ShipPrice(double price){ // sets class a price
        A_shipTicketPrice = price;
    }
    public void setB_ShipPrice(double price){ // sets class b price
        B_shipTicketPrice = price;
    }
    public void setC_ShipPrice(double price){ // sets class c price
        C_shipTicketPrice = price;
    }
    public void purchaseSpaceStation(String location){// used when player purchase space station
        if(spaceStations.isFull()){ // check if space station is empty
            return;
        }else if(validateLocation(location)==false){ // check the validate location
            return;
        }else{ // purchase space station
            spaceStation station = new spaceStation(location);
            if(money>=station.getPrice()){
                spaceStations.push(station);
                System.out.println("SpaceStation purchased!");
                money-=station.getPrice();
                spaceStationsNumber++;
                for (int i = 0; i < Validlocations.length; i++) { // remove location from valid locations
                    if(Validlocations[i]!=null&&Validlocations[i].compareTo(location)==0){
                        Validlocations[i] = null;
                    }
                }
            }else{
                System.out.println("You don't have enough money!");
            }
        }  
    }
    private boolean validateLocation(String location){ // control validation of location (if in a location has not space station)
        for (int i = 0; i < Validlocations.length; i++) {
            if(Validlocations[i]!=null&&Validlocations[i].compareTo(location)==0){
                return true;
            }
        } 
        return false;
    }
    public void purchaseSpaceShip(String location, String type){// get space ships according to location
        spaceStation station = null;
        Stack temp = new Stack(spaceStations.size());
        if(validateLocation(location)==true){ // check the validate location
            return;
        }else{ // return space station
            int size = spaceStations.size();
            for (int i = 0; i < size; i++) { // remove location from valid locations
                spaceStation current = (spaceStation)spaceStations.pop();
                if(current.getLocation().compareTo(location)==0){
                    station = current;
                }else{
                    temp.push(current);
                }
            } 
        } 
        int size = temp.size();
        for (int i = 0; i < size; i++) { // reload space stations
            spaceStations.push(temp.pop());
        }  
        if(station.addShip(type)){ // add ship to space station and check
            System.out.println("SpaceShip purchased!");
            if(type.compareTo("A")==0){
                money-= 4000;
            }else if(type.compareTo("B")==0){
                money-= 2000;
            }else if(type.compareTo("C")==0){
                money-= 1000;
            }
            spaceShipsNumber++;
        }
        spaceStations.push(station); // add current station to stack
    }
    public spaceStation getSpaceStation(String location){// get space station according to location
        spaceStation station = null;
        Stack temp = new Stack(spaceStations.size());
        if(validateLocation(location)==true){ // check the validate location
            return null;
        }else{ // return space station
            int size = spaceStations.size();
            for (int i = 0; i < size; i++) { // remove location from valid locations
                spaceStation current = (spaceStation)spaceStations.pop();
                if(current.getLocation().compareTo(location)==0){
                    station = current;
                }
                temp.push(current);
            } 
        } 
        int size = temp.size();
        for (int i = 0; i < size; i++) { // reload space stations
            spaceStations.push(temp.pop());
        }  
        return station;// if null  there is no space ship in this location
    }
    public Stack getSpaceShip(String location){// get space ships according to location
        spaceStation station = null;
        Stack temp = new Stack(spaceStations.size());
        if(validateLocation(location)==true){ // check the validate location
            return null;
        }else{ // return space station
            int size = spaceStations.size();
            for (int i = 0; i < size; i++) { // remove location from valid locations
                spaceStation current = (spaceStation)spaceStations.pop();
                if(current.getLocation().compareTo(location)==0){
                    station = current;
                }
                temp.push(current);
            } 
        } 
        int size = temp.size();
        for (int i = 0; i < size; i++) { // reload space stations
            spaceStations.push(temp.pop());
        }  
        Stack spaceShips = station.getSpaceShip(); // returns stack of ship
        return spaceShips;// returns the stack of space ships in this location
    }
}