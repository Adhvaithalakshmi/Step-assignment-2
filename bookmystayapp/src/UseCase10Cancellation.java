import java.util.*;

// Main Class
public class UseCase10Cancellation {

    // Reservation Class
    static class Reservation {
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

    // Inventory Class
    static class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();

        public RoomInventory() {
            inventory.put("Single", 1);
            inventory.put("Double", 1);
            inventory.put("Suite", 1);
        }

        public void increment(String type) {
            inventory.put(type, inventory.get(type) + 1);
        }

        public void decrement(String type) {
            inventory.put(type, inventory.get(type) - 1);
        }

        public int getAvailable(String type) {
            return inventory.getOrDefault(type, 0);
        }
    }

    // Booking History
    static class BookingHistory {
        private List<Reservation> confirmedBookings = new ArrayList<>();

        public void add(Reservation r) {
            confirmedBookings.add(r);
        }

        public boolean exists(String roomId) {
            for (Reservation r : confirmedBookings) {
                if (r.getRoomId().equals(roomId)) return true;
            }
            return false;
        }

        public void remove(String roomId) {
            confirmedBookings.removeIf(r -> r.getRoomId().equals(roomId));
        }
    }

    // Cancellation Service
    static class CancellationService {

        // Stack for rollback (LIFO)
        private Stack<String> rollbackStack = new Stack<>();

        public void cancelBooking(String roomId,
                                  BookingHistory history,
                                  RoomInventory inventory) {

            // Validate booking exists
            if (!history.exists(roomId)) {
                System.out.println("Cancellation failed: Booking not found for " + roomId);
                return;
            }

            // Push to stack (rollback tracking)
            rollbackStack.push(roomId);

            // Extract room type (Single-1 → Single)
            String roomType = roomId.split("-")[0];

            // Restore inventory
            inventory.increment(roomType);

            // Remove from history
            history.remove(roomId);

            System.out.println("Booking cancelled for Room ID: " + roomId);
        }

        // Show rollback history
        public void showRollbackStack() {
            System.out.println("Rollback Stack: " + rollbackStack);
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("Booking Cancellation & Rollback");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService();

        // Simulate confirmed bookings (from previous use case)
        history.add(new Reservation("Abhi", "Single-1"));
        history.add(new Reservation("Subha", "Double-1"));

        // Perform cancellations
        cancelService.cancelBooking("Single-1", history, inventory); // valid
        cancelService.cancelBooking("Single-1", history, inventory); // already cancelled
        cancelService.cancelBooking("Suite-1", history, inventory); // not exists

        // Show rollback order
        cancelService.showRollbackStack();

        // Show inventory after rollback
        System.out.println("Available Single Rooms: " + inventory.getAvailable("Single"));
    }
}
