// ReservationManagementForm.java
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ReservationManagementForm extends JPanel 
{
    private HotelManagementSystem parent;
    private List<Reservation> reservationList;
    private List<Room> roomList;
    private List<Customer> customerList;
    public ReservationDisplayPanel reservationDisplayPanel;
    public ReservationFormPanel reservationFormPanel;
    
    public ReservationManagementForm(HotelManagementSystem parent) {
        this.parent = parent;
        reservationList = new ArrayList<>();
        roomList = new ArrayList<>();
        customerList = new ArrayList<>();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 250));
        
        loadData();

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 250));
        JPanel headerPanel = createHeaderPanel();
        reservationFormPanel = new ReservationFormPanel(this);
        reservationDisplayPanel = new ReservationDisplayPanel(this);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(450);
        splitPane.setDividerSize(3);
        splitPane.setLeftComponent(reservationFormPanel);
        splitPane.setRightComponent(reservationDisplayPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        refreshDisplay();
        reservationFormPanel.loadComboBoxData();
    }
    
    private JPanel createHeaderPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 245, 250));
        
        JLabel titleLabel = new JLabel("Manage Reservations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(new Color(45, 62, 80));

        JButton btnRefresh = new JButton("REFRESH");
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 14));
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.GRAY);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setVisible(true);
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnRefresh.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshDisplay();
            }
        });
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(btnRefresh, BorderLayout.EAST);
        
        return panel;
    }
    
    public List<Reservation> getReservationList() 
    {
        return reservationList;
    }
    public List<Room> getRoomList() 
    {
        return roomList;
    }
    
    public List<Customer> getCustomerList() 
    {
        return customerList;
    }
    public void refreshDisplay() 
    {
        reservationDisplayPanel.refreshTable();
    }
    
    public void fillFormFromTable(int row) 
    {
        reservationFormPanel.fillFormFromTable(row);
    }
    
    public void addReservation(Reservation reservation) 
    {
        reservationList.add(reservation);
        saveData();
        refreshDisplay();
        if (reservation.getStatus()==Reservation.Status.CONFIRMED) 
        {
            updateRoomStatus(reservation.getRoom(),Room.RoomStatus.BOOKED);
        }
    }
    public void updateReservation(Reservation reservation)
    {
        for (int i = 0; i < reservationList.size(); i++) 
        {
            if (reservationList.get(i).getReservationId().equals(reservation.getReservationId())) 
            {
                Reservation oldReservation = reservationList.get(i);

                if (oldReservation.getStatus() == Reservation.Status.CONFIRMED && reservation.getStatus() != Reservation.Status.CONFIRMED) 
                {
                    updateRoomStatus(oldReservation.getRoom(), Room.RoomStatus.FREE);

                } else if (oldReservation.getStatus() != Reservation.Status.CONFIRMED && reservation.getStatus() == Reservation.Status.CONFIRMED) 
                {
                    updateRoomStatus(reservation.getRoom(), Room.RoomStatus.BOOKED);
                }
                
                reservationList.set(i, reservation);
                saveData();
                refreshDisplay();
                break;
            }
        }
    }
    
    public void deleteReservation(String reservationId) 
    {
        Reservation reservationToDelete = null;
        for (Reservation reservation : reservationList) {
            if (reservation.getReservationId().equals(reservationId)) 
            {
                reservationToDelete = reservation;
                break;
            }
        }
        
        if (reservationToDelete != null) 
        {
            if (reservationToDelete.getStatus()==Reservation.Status.CONFIRMED) 
            {
                updateRoomStatus(reservationToDelete.getRoom(),Room.RoomStatus.FREE);
            }
            reservationList.remove(reservationToDelete);
            saveData();
            refreshDisplay();
        }
    }
    public Reservation getReservationById(String reservationId) 
    {
        for (Reservation reservation : reservationList) {
            if (reservation.getReservationId().equals(reservationId)) {
                return reservation;
            }
        }
        return null;
    }
    
    public void updateRoomStatus(Room room, Room.RoomStatus status) 
    {
        room.setStatus(status);
        try {
            FileHandler.updateRoom(room);

            roomList = FileHandler.loadRooms();
            reservationFormPanel.loadComboBoxData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public MyDate parseDate(String dateStr) 
    {
        String[] parts = dateStr.split("/");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid date format");
        }
        
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        
        return new MyDate(month, day, year);
    }
    
    public void loadData() 
    {
        try {
            List<Reservation> loadedReservations = FileHandler.loadReservations();
            if (loadedReservations != null) 
            {
                reservationList=loadedReservations;
            }
            List<Room> loadedRooms = FileHandler.loadRooms();
            if (loadedRooms!=null) 
            {
                roomList =loadedRooms;
            }
            List<Customer> loadedCustomers = FileHandler.loadCustomers();
            if (loadedCustomers != null) {
                customerList = loadedCustomers;
            }
            
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    
    public void saveData() {
        try {
            FileHandler.saveReservations(reservationList);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error saving reservation data: " + e.getMessage(), 
                "Save Error ??", JOptionPane.ERROR_MESSAGE);
        }
    }
}