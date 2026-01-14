// RoomManagementForm.java
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class RoomManagementForm extends JPanel {
    private HotelManagementSystem parent;
    private List<Room> roomList;
    public RoomDisplayPanel roomDisplayPanel;
    public RoomFormPanel roomFormPanel;
    
    public RoomManagementForm(HotelManagementSystem parent) {
        this.parent = parent;
        roomList = new ArrayList<>();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 250));
        
        loadData();
        
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 250));
        
        JPanel headerPanel = createHeaderPanel();
        
        roomFormPanel = new RoomFormPanel(this);
        roomDisplayPanel = new RoomDisplayPanel(this);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        splitPane.setDividerSize(3);
        splitPane.setLeftComponent(roomFormPanel);
        splitPane.setRightComponent(roomDisplayPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);

        refreshDisplay();
    }
    
    private JPanel createHeaderPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 245, 250));
        
        JLabel titleLabel = new JLabel("Manage Rooms");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(45, 62, 80));

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setBackground(new Color(45, 62, 80));
        btnRefresh.setForeground(Color.gray);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnRefresh.addActionListener(e -> refreshDisplay());
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(btnRefresh, BorderLayout.EAST);
        
        return panel;
    }

    public List<Room> getRoomList() {
        return roomList;
    }
    
    public void refreshDisplay() {
        roomDisplayPanel.refreshTable();
    }
    
    public void fillFormFromTable(int row) {
        roomFormPanel.fillFormFromTable(row);
    }
    
    public void addRoom(Room room) {
        roomList.add(room);
        saveData();
        refreshDisplay();
    }
    
    public void updateRoom(Room room) 
    {
        for (int i = 0; i < roomList.size(); i++) 
        {
            if (roomList.get(i).getId().equals(room.getId())) 
            {
                roomList.set(i, room);
                saveData();
                refreshDisplay();
                break;
            }
        }
    }
    
    public void deleteRoom(String roomId) {
        roomList.removeIf(room -> room.getId().equals(roomId));
        saveData();
        refreshDisplay();
    }
    
    public Room getRoomById(String roomId) {
        for (Room room : roomList) {
            if (room.getId().equals(roomId)) {
                return room;
            }
        }
        return null;
    }
    
    public void loadData() 
    {
        try 
        {
            List<Room> loadedRooms = FileHandler.loadRooms();
            if (loadedRooms != null) {
                roomList = loadedRooms;
            }
        } 
        catch (Exception e) 
        {
            System.out.println("No existing room data found or error loading: " + e.getMessage());
        }
    }
    
    public void saveData()
    {
        try {
            FileHandler.saveRooms(roomList);
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error saving room data: " + e.getMessage(), 
                "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}