import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler
{
    private static final File DATA_DIR = new File("data");

    private static final File ROOMS_FILE = new File(DATA_DIR, "rooms.dat");
    private static final File CUSTOMERS_FILE = new File(DATA_DIR, "customers.dat");
    private static final File RESERVATIONS_FILE = new File(DATA_DIR, "reservations.dat");

    static {
        if (!DATA_DIR.exists()) {
            DATA_DIR.mkdirs();
        }
    }

    public static void saveRooms(List<Room> rooms) throws IOException
    {
        try (ObjectOutputStream oos =
                 new ObjectOutputStream(new FileOutputStream(ROOMS_FILE)))
        {
            oos.writeObject(rooms);
        }
    }

    public static void saveCustomers(List<Customer> customers) throws IOException
    {
        try (ObjectOutputStream oos =
                 new ObjectOutputStream(new FileOutputStream(CUSTOMERS_FILE)))
        {
            oos.writeObject(customers);
        }
    }

    public static void saveReservations(List<Reservation> reservations) throws IOException
    {
        try (ObjectOutputStream oos =
                 new ObjectOutputStream(new FileOutputStream(RESERVATIONS_FILE)))
        {
            oos.writeObject(reservations);
        }
    }

    public static List<Room> loadRooms() throws IOException, ClassNotFoundException
    {
        if (!ROOMS_FILE.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois =
                 new ObjectInputStream(new FileInputStream(ROOMS_FILE)))
        {
            return (List<Room>) ois.readObject();
        }
    }

    public static List<Customer> loadCustomers() throws IOException, ClassNotFoundException
    {
        if (!CUSTOMERS_FILE.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois =
                 new ObjectInputStream(new FileInputStream(CUSTOMERS_FILE)))
        {
            return (List<Customer>) ois.readObject();
        }
    }

    public static List<Reservation> loadReservations() throws IOException, ClassNotFoundException
    {
        if (!RESERVATIONS_FILE.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois =
                 new ObjectInputStream(new FileInputStream(RESERVATIONS_FILE)))
        {
            return (List<Reservation>) ois.readObject();
        }
    }

    public static void addRoom(Room room) throws IOException, ClassNotFoundException
    {
        List<Room> rooms = loadRooms();
        rooms.add(room);
        saveRooms(rooms);
    }

    public static void addCustomer(Customer customer) throws IOException, ClassNotFoundException
    {
        List<Customer> customers = loadCustomers();
        customers.add(customer);
        saveCustomers(customers);
    }

    public static void addReservation(Reservation reservation) throws IOException, ClassNotFoundException
    {
        List<Reservation> reservations = loadReservations();
        reservations.add(reservation);
        saveReservations(reservations);
    }

    public static void updateRoom(Room updatedRoom) throws IOException, ClassNotFoundException
    {
        List<Room> rooms = loadRooms();
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId().equals(updatedRoom.getId())) {
                rooms.set(i, updatedRoom);
                break;
            }
        }
        saveRooms(rooms);
    }

    public static void updateCustomer(Customer updatedCustomer) throws IOException, ClassNotFoundException
    {
        List<Customer> customers = loadCustomers();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId().equals(updatedCustomer.getCustomerId())) {
                customers.set(i, updatedCustomer);
                break;
            }
        }
        saveCustomers(customers);
    }

    public static void updateReservation(Reservation updatedReservation) throws IOException, ClassNotFoundException
    {
        List<Reservation> reservations = loadReservations();
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId().equals(updatedReservation.getReservationId())) {
                reservations.set(i, updatedReservation);
                break;
            }
        }
        saveReservations(reservations);
    }

    public static void deleteRoom(String roomId) throws IOException, ClassNotFoundException
    {
        List<Room> rooms = loadRooms();
        rooms.removeIf(room -> room.getId().equals(roomId));
        saveRooms(rooms);
    }

    public static void deleteCustomer(String customerId) throws IOException, ClassNotFoundException
    {
        List<Customer> customers = loadCustomers();
        customers.removeIf(customer -> customer.getCustomerId().equals(customerId));
        saveCustomers(customers);
    }

    public static void deleteReservation(String reservationId) throws IOException, ClassNotFoundException
    {
        List<Reservation> reservations = loadReservations();
        reservations.removeIf(reservation -> reservation.getReservationId().equals(reservationId));
        saveReservations(reservations);
    }

    public static Room findRoomById(String id) throws IOException, ClassNotFoundException
    {
        for (Room room : loadRooms()) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    public static Customer findCustomerById(String id) throws IOException, ClassNotFoundException
    {
        for (Customer customer : loadCustomers()) 
        {
            if (customer.getCustomerId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public static Reservation findReservationById(String id) throws IOException, ClassNotFoundException
    {
        for (Reservation reservation : loadReservations()) 
        {
            if (reservation.getReservationId().equals(id)) {
                return reservation;
            }
        }
        return null;
    }
}
