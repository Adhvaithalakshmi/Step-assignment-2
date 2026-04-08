import java.util.*;

// Main Class
public class UseCase11ConcurrentBookingUs {

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

    // Shared Booking Queue
    static class BookingQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public synchronized void addRequest(Reservation r) {
            queue.offer(r);
        }

        public synchronized Reservation getRequest() {
            return queue.poll();
        }

        public synchronized boolean hasRequests() {
            return !queue.isEmpty();
        }
    }

    // Shared Inventory
    static class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();

        public RoomInventory() {
            inventory.put("Single", 1);
            inventory.put("Double", 1);
        }

        // Critical Section (Thread Safe)
        public synchronized boolean allocateRoom(String type) {
            int available = inventory.getOrDefault(type, 0);

            if (available > 0) {
                inventory.put(type, available - 1);
                return true;
            }
            return false;
        }
    }

    // Booking Processor (Thread)
    static class BookingProcessor extends Thread {
        private BookingQueue queue;
        private RoomInventory inventory;

        public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
            this.queue = queue;
            this.inventory = inventory;
        }

        @Override
        public void run() {
            while (true) {
                Reservation r;

                // Synchronize queue access
                synchronized (queue) {
                    if (!queue.hasRequests()) break;
                    r = queue.getRequest();
                }

                if (r != null) {
                    boolean success = inventory.allocateRoom(r.getRoomType());

                    if (success) {
                        System.out.println(Thread.currentThread().getName()
                                + " → Booking confirmed for "
                                + r.getGuestName()
                                + " (" + r.getRoomType() + ")");
                    } else {
                        System.out.println(Thread.currentThread().getName()
                                + " → Booking failed for "
                                + r.getGuestName()
                                + " (No rooms)");
                    }
                }
            }
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        // Add requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Ram", "Double"));
        queue.addRequest(new Reservation("Priya", "Double"));

        // Create multiple threads
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        // Start threads
        t1.start();
        t2.start();
    }
}
