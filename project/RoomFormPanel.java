// RoomFormPanel.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RoomFormPanel extends JPanel {
    private RoomManagementForm roomManagementForm;

    private JTextField txtRoomId;
    private JComboBox<RoomType> cmbRoomType;
    private JComboBox<Room.RoomStatus> cmbStatus;
    private JTextField txtCapacity;
    private JTextField txtPrice;
    private JButton btnAdd, btnEdit, btnDelete, btnClear;
    
    public RoomFormPanel(RoomManagementForm roomManagementForm) {
        this.roomManagementForm = roomManagementForm;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 230, 240)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        initComponents();
        addActionListeners();
    }
    
    private void initComponents() {

        JLabel formTitle = new JLabel("Room Information");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        formTitle.setForeground(new Color(45, 62, 80));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(formTitle);
        add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel[] fieldPanels = new JPanel[5];
        for (int i = 0; i < fieldPanels.length; i++) {
            fieldPanels[i] = new JPanel(new BorderLayout(10, 0));
            fieldPanels[i].setBackground(Color.WHITE);
            fieldPanels[i].setMaximumSize(new Dimension(400, 40));
        }
        

        JLabel lblRoomId = new JLabel("Room ID:");
        lblRoomId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtRoomId = new JTextField();
        txtRoomId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[0].add(lblRoomId, BorderLayout.WEST);
        fieldPanels[0].add(txtRoomId, BorderLayout.CENTER);

        JLabel lblRoomType = new JLabel("Category:");
        lblRoomType.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cmbRoomType = new JComboBox<>(RoomType.values());
        cmbRoomType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[1].add(lblRoomType, BorderLayout.WEST);
        fieldPanels[1].add(cmbRoomType, BorderLayout.CENTER);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cmbStatus = new JComboBox<>(Room.RoomStatus.values());
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[2].add(lblStatus, BorderLayout.WEST);
        fieldPanels[2].add(cmbStatus, BorderLayout.CENTER);

        JLabel lblCapacity = new JLabel("Capacity:");
        lblCapacity.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtCapacity = new JTextField();
        txtCapacity.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[3].add(lblCapacity, BorderLayout.WEST);
        fieldPanels[3].add(txtCapacity, BorderLayout.CENTER);
        
        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtPrice = new JTextField();
        txtPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[4].add(lblPrice, BorderLayout.WEST);
        fieldPanels[4].add(txtPrice, BorderLayout.CENTER);
        
        for (JPanel fieldPanel : fieldPanels) {
            add(fieldPanel);
            add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(400, 45));
        
        btnAdd = createStyledButton("Add", new Color(46, 204, 113));
        btnEdit = createStyledButton("Edit", new Color(52, 152, 219));
        btnDelete = createStyledButton("Delete", new Color(231, 76, 60));
        btnClear = createStyledButton("Clear", new Color(149, 165, 166));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        
        add(buttonPanel);
    }
    
    private JButton createStyledButton(String text, Color color) 
    {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.GRAY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void addActionListeners() {
        btnAdd.addActionListener(e -> addRoom());
        btnEdit.addActionListener(e -> editRoom());
        btnDelete.addActionListener(e -> deleteRoom());
        btnClear.addActionListener(e -> clearForm());
    }
    
    private void addRoom() {
        try {
            if (txtRoomId.getText().trim().isEmpty() || 
                txtCapacity.getText().trim().isEmpty() || txtPrice.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            for (Room room : roomManagementForm.getRoomList()) {
                if (room.getId().equals(txtRoomId.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "Room ID already exists!", 
                        "Duplicate Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            Room room = new Room(
                txtRoomId.getText().trim(),
                (RoomType)cmbRoomType.getSelectedItem(),
                (Room.RoomStatus)cmbStatus.getSelectedItem(),
                Integer.parseInt(txtCapacity.getText().trim()),
                Double.parseDouble(txtPrice.getText().trim())
            );
            
            roomManagementForm.addRoom(room);
            clearForm();
            
            JOptionPane.showMessageDialog(this, "Room added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for capacity and price!", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editRoom() 
    {
        if (txtRoomId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a room to edit!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String roomId = txtRoomId.getText().trim();
            Room existingRoom = roomManagementForm.getRoomById(roomId);
            
            if (existingRoom == null) {
                JOptionPane.showMessageDialog(this, "Room not found!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Room updatedRoom = new Room(roomId,
            (RoomType) cmbRoomType.getSelectedItem(), (Room.RoomStatus) cmbStatus.getSelectedItem(),
             Integer.parseInt(txtCapacity.getText().trim()), Double.parseDouble(txtPrice.getText().trim()));

            
            roomManagementForm.updateRoom(updatedRoom);
            
            JOptionPane.showMessageDialog(this, "Room updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for capacity and price!", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteRoom() {
        if (txtRoomId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a room to delete!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String roomId = txtRoomId.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete room " + roomId + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            roomManagementForm.deleteRoom(roomId);
            clearForm();
            
            JOptionPane.showMessageDialog(this, "Room deleted successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void clearForm() {
        txtRoomId.setText("");
        cmbRoomType.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
        txtCapacity.setText("");
        txtPrice.setText("");
    }
    
    public void fillFormFromTable(int row) 
    {
        RoomDisplayPanel displayPanel = roomManagementForm.roomDisplayPanel;
        javax.swing.table.DefaultTableModel model = displayPanel.getTableModel();
        
        txtRoomId.setText(model.getValueAt(row, 0).toString());
      
        cmbRoomType.setSelectedItem(RoomType.valueOf(model.getValueAt(row, 1).toString()));
        cmbStatus.setSelectedItem(Room.RoomStatus.valueOf(model.getValueAt(row, 2).toString()));
        txtCapacity.setText(model.getValueAt(row, 3).toString());

        String priceStr = model.getValueAt(row, 4).toString().replace("$", "");
        txtPrice.setText(priceStr);
    }
}