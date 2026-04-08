/**
 * Use Case 2: Basic Room Types & Static Availability
 * Single File Implementation
 */

abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    // Constructor
    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    // Method to display room details
    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

// Single Room Class
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

// Double Room Class
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

// Suite Room Class
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

// Main Class (ONLY this is public)
public class UseCase2RoomInitialization{

    public static void main(String[] args) {

        // Create objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Hotel Room Initialization\n");

        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}