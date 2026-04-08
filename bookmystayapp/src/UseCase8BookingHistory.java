import java.util.*;

// Main Class
public class UseCase8BookingHistory {

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

    // Booking History (Stores confirmed bookings)
    static class BookingHistory {
        private List<Reservation> history;

        public BookingHistory() {
            history = new ArrayList<>();
        }

        // Add confirmed booking
        public void addBooking(Reservation reservation) {
            history.add(reservation);
        }

        // Get all bookings
        public List<Reservation> getAllBookings() {
            return history;
        }
    }

    // Reporting Service
    static class BookingReportService {

        // Display all bookings
        public void displayAllBookings(List<Reservation> bookings) {
            System.out.println("\nBooking History:");

            for (Reservation r : bookings) {
                System.out.println("Guest: " + r.getGuestName()
                        + ", Room ID: " + r.getRoomId());
            }
        }

        // Summary report
        public void generateSummary(List<Reservation> bookings) {
            System.out.println("\nTotal Bookings: " + bookings.size());
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("Booking History & Reporting");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings (from Use Case 6)
        history.addBooking(new Reservation("Abhi", "Single-1"));
        history.addBooking(new Reservation("Subha", "Single-2"));
        history.addBooking(new Reservation("Vanmathi", "Suite-1"));

        // Display all bookings
        reportService.displayAllBookings(history.getAllBookings());

        // Generate summary
        reportService.generateSummary(history.getAllBookings());
    }
}
