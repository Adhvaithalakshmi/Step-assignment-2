import java.util.*;

// Main Class
public class UseCase9ErrorHandling {

    // Custom Exception
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // Reservation Class
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // Inventory Class
    static class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();

        public RoomInventory() {
            inventory.put("Single", 1);
            inventory.put("Double", 1);
            inventory.put("Suite", 1);
        }

        public boolean isValidRoomType(String type) {
            return inventory.containsKey(type);
        }

        public boolean isAvailable(String type) {
            return inventory.get(type) > 0;
        }

        public void decrement(String type) {
            inventory.put(type, inventory.get(type) - 1);
        }
    }

    // Validator Class
    static class BookingValidator {

        public static void validate(Reservation r, RoomInventory inventory)
                throws InvalidBookingException {

            if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty");
            }

            if (!inventory.isValidRoomType(r.getRoomType())) {
                throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
            }

            if (!inventory.isAvailable(r.getRoomType())) {
                throw new InvalidBookingException("No rooms available for: " + r.getRoomType());
            }
        }
    }

    // Allocation Service
    static class RoomAllocationService {
        private Set<String> allocatedRoomIds = new HashSet<>();
        private Map<String, Integer> counters = new HashMap<>();

        public String allocate(Reservation r, RoomInventory inventory)
                throws InvalidBookingException {

            // Validate first (Fail-Fast)
            BookingValidator.validate(r, inventory);

            // Generate room ID
            int count = counters.getOrDefault(r.getRoomType(), 0) + 1;
            counters.put(r.getRoomType(), count);

            String roomId = r.getRoomType() + "-" + count;

            // Ensure uniqueness
            if (allocatedRoomIds.contains(roomId)) {
                throw new InvalidBookingException("Duplicate room ID generated!");
            }

            allocatedRoomIds.add(roomId);

            // Update inventory safely
            inventory.decrement(r.getRoomType());

            return roomId;
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("Booking System with Error Handling");

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Test cases (valid + invalid)
        Reservation[] requests = {
                new Reservation("Abhi", "Single"),
                new Reservation("", "Double"),           // Invalid name
                new Reservation("Subha", "Triple"),      // Invalid room type
                new Reservation("Vanmathi", "Single")    // No availability
        };

        for (Reservation r : requests) {
            try {
                String roomId = service.allocate(r, inventory);
                System.out.println("Booking confirmed for "
                        + r.getGuestName() + ", Room ID: " + roomId);

            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        }
    }
}
