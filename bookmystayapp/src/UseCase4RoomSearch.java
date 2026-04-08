/**
 * Use Case 4: Room Search & Availability Check
 * Single File Implementation
 */

import java.util.HashMap;
import java.util.Map;

/* ================= ABSTRACT CLASS ================= */
abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

/* ================= ROOM TYPES ================= */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

/* ================= INVENTORY CLASS ================= */
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 0); // Example: Suite unavailable
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

/* ================= SEARCH SERVICE ================= */
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory,
                                     Room single,
                                     Room doubleRoom,
                                     Room suite) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Available Rooms:\n");

        // Check Single Room
        if (availability.get("Single") > 0) {
            System.out.println("Single Room:");
            single.displayRoomDetails();
            System.out.println("Available: " + availability.get("Single") + "\n");
        }

        // Check Double Room
        if (availability.get("Double") > 0) {
            System.out.println("Double Room:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Double") + "\n");
        }

        // Check Suite Room
        if (availability.get("Suite") > 0) {
            System.out.println("Suite Room:");
            suite.displayRoomDetails();
            System.out.println("Available: " + availability.get("Suite") + "\n");
        }
    }
}

/* ================= MAIN CLASS ================= */
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Create objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Search Service
        RoomSearchService searchService = new RoomSearchService();

        // Perform search
        searchService.searchAvailableRooms(inventory, single, doubleRoom, suite);
    }
}
