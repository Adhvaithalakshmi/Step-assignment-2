import java.util.*;

// Main Class
public class UseCase6RoomAllocation {

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

    // Booking Request Queue (FIFO)
    static class BookingRequestQueue {
        private Queue<Reservation> requestQueue = new LinkedList<>();

        public void addRequest(Reservation r) {
            requestQueue.offer(r);
        }

        public Reservation getNextRequest() {
            return requestQueue.poll();
        }

        public boolean hasPendingRequests() {
            return !requestQueue.isEmpty();
        }
    }

    // Room Inventory
    static class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();

        public RoomInventory() {
            inventory.put("Single", 2);
            inventory.put("Double", 2);
            inventory.put("Suite", 2);
        }

        public boolean isAvailable(String type) {
            return inventory.getOrDefault(type, 0) > 0;
        }

        public void decrement(String type) {
            inventory.put(type, inventory.get(type) - 1);
        }
    }

    // Room Allocation Service
    static class RoomAllocationService {

        // To prevent duplicate room IDs
        private Set<String> allocatedRoomIds = new HashSet<>();

        // Track rooms by type
        private Map<String, Set<String>> assignedRoomsByType = new HashMap<>();

        public void allocateRoom(Reservation reservation, RoomInventory inventory) {

            String type = reservation.getRoomType();

            if (!inventory.isAvailable(type)) {
                System.out.println("No rooms available for " + type);
                return;
            }

            String roomId = generateRoomId(type);

            // Store allocated ID
            allocatedRoomIds.add(roomId);

            assignedRoomsByType.putIfAbsent(type, new HashSet<>());
            assignedRoomsByType.get(type).add(roomId);

            // Update inventory
            inventory.decrement(type);

            // Confirm booking
            System.out.println("Booking confirmed for Guest: "
                    + reservation.getGuestName()
                    + ", Room ID: " + roomId);
        }

        // Generate unique Room ID
        private String generateRoomId(String type) {
            int count = assignedRoomsByType.getOrDefault(type, new HashSet<>()).size() + 1;
            return type + "-" + count;
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocator = new RoomAllocationService();

        // Create requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Process FIFO
        while (queue.hasPendingRequests()) {
            Reservation r = queue.getNextRequest();
            allocator.allocateRoom(r, inventory);
        }
    }
}
