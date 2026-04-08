import java.io.*;
import java.util.*;

// Main Class
public class UseCase12Persistence {

    // Reservation Class (Serializable)
    static class Reservation implements Serializable {
        private static final long serialVersionUID = 1L;

        private String guestName;
        private String roomId;

        public Reservation(String guestName, String roomId) {
            this.guestName = guestName;
            this.roomId = roomId;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomId() {
            return roomId;
        }
    }

    // System State (Serializable)
    static class SystemState implements Serializable {
        private static final long serialVersionUID = 1L;

        List<Reservation> bookings;
        Map<String, Integer> inventory;

        public SystemState(List<Reservation> bookings,
                           Map<String, Integer> inventory) {
            this.bookings = bookings;
            this.inventory = inventory;
        }
    }

    // Persistence Service
    static class PersistenceService {

        private static final String FILE_NAME = "hotel_state.dat";

        // SAVE state
        public static void save(SystemState state) {
            try (ObjectOutputStream oos =
                         new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

                oos.writeObject(state);
                System.out.println("System state saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving state: " + e.getMessage());
            }
        }

        // LOAD state
        public static SystemState load() {
            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(FILE_NAME))) {

                System.out.println("System state loaded successfully.");
                return (SystemState) ois.readObject();

            } catch (Exception e) {
                System.out.println("No previous data found. Starting fresh.");
                return null;
            }
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("Data Persistence & Recovery");

        // Try loading previous state
        SystemState state = PersistenceService.load();

        List<Reservation> bookings;
        Map<String, Integer> inventory;

        if (state != null) {
            // Restore state
            bookings = state.bookings;
            inventory = state.inventory;
        } else {
            // Fresh system
            bookings = new ArrayList<>();
            inventory = new HashMap<>();

            inventory.put("Single", 2);
            inventory.put("Double", 2);
        }

        // Simulate new booking
        Reservation r1 = new Reservation("Abhi", "Single-1");
        bookings.add(r1);
        inventory.put("Single", inventory.get("Single") - 1);

        // Display current state
        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings) {
            System.out.println(r.getGuestName() + " → " + r.getRoomId());
        }

        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }

        // Save state before shutdown
        PersistenceService.save(new SystemState(bookings, inventory));
    }
}
