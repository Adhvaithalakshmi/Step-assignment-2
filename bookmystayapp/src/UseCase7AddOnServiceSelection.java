import java.util.*;

// Main Class
public class UseCase7AddOnServiceSelection {

    // Reservation Class
    static class Reservation {
        private String reservationId;

        public Reservation(String reservationId) {
            this.reservationId = reservationId;
        }

        public String getReservationId() {
            return reservationId;
        }
    }

    // Service Class
    static class Service {
        private String serviceName;
        private double cost;

        public Service(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public double getCost() {
            return cost;
        }

        public String getServiceName() {
            return serviceName;
        }
    }

    // Add-On Service Manager
    static class AddOnServiceManager {

        // Map<ReservationID, List of Services>
        private Map<String, List<Service>> servicesByReservation;

        public AddOnServiceManager() {
            servicesByReservation = new HashMap<>();
        }

        // Add service to reservation
        public void addService(String reservationId, Service service) {
            servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
            servicesByReservation.get(reservationId).add(service);
        }

        // Calculate total cost
        public double calculateTotalServiceCost(String reservationId) {
            double total = 0;

            List<Service> services = servicesByReservation.get(reservationId);

            if (services != null) {
                for (Service s : services) {
                    total += s.getCost();
                }
            }

            return total;
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        // Example reservation (from previous use case)
        Reservation reservation = new Reservation("Single-1");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(reservation.getReservationId(), new Service("Breakfast", 500));
        manager.addService(reservation.getReservationId(), new Service("WiFi", 300));
        manager.addService(reservation.getReservationId(), new Service("Airport Pickup", 700));

        // Calculate total cost
        double totalCost = manager.calculateTotalServiceCost(reservation.getReservationId());

        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}
