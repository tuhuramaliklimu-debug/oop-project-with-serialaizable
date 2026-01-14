// ReservationFormPanel.java
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

public class ReservationFormPanel extends JPanel {
    private ReservationManagementForm reservationManagementForm;
    private JTextField txtReservationId;
    private JComboBox<Customer> cmbCustomer;
    private JComboBox<Room> cmbRoom;
    private JTextField txtCheckInDate;
    private JTextField txtCheckOutDate;
    private JSpinner spnNumberOfNights;
    private JSpinner spnNumberOfGuests;
    private JComboBox<Reservation.Status> cmbStatus;
    private JTextField txtTotalPrice;
    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnCalculate;
    
    public ReservationFormPanel(ReservationManagementForm reservationManagementForm) 
    {
        this.reservationManagementForm = reservationManagementForm;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 230, 240)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        initComponents();
        addActionListeners();
    }
    
    public void loadComboBoxData() 
    {
        cmbCustomer.removeAllItems();
        for (Customer customer : reservationManagementForm.getCustomerList()) {
            cmbCustomer.addItem(customer);
        }
        cmbRoom.removeAllItems();
        for (Room room : reservationManagementForm.getRoomList()) {
            cmbRoom.addItem(room);
        }
    }
    
    private void initComponents() {
       
        JLabel formTitle = new JLabel("Reservation Information");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        formTitle.setForeground(new Color(45, 62, 80));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(formTitle);
        add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel[] fieldPanels = new JPanel[9];
        for (int i = 0; i < fieldPanels.length; i++) 
        {
            fieldPanels[i]=new JPanel(new BorderLayout(10, 0));
            fieldPanels[i].setBackground(Color.WHITE);
            fieldPanels[i].setMaximumSize(new Dimension(400, 40));
        }

        JLabel lblReservationId = new JLabel("Reservation ID:");
        lblReservationId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtReservationId = new JTextField();
        txtReservationId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[0].add(lblReservationId, BorderLayout.WEST);
        fieldPanels[0].add(txtReservationId, BorderLayout.CENTER);

        JLabel lblCustomer = new JLabel("Customer:");
        lblCustomer.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cmbCustomer = new JComboBox<>();
        cmbCustomer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[1].add(lblCustomer, BorderLayout.WEST);
        fieldPanels[1].add(cmbCustomer, BorderLayout.CENTER);

        JLabel lblRoom = new JLabel("Room:");
        lblRoom.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cmbRoom = new JComboBox<>();
        cmbRoom.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[2].add(lblRoom, BorderLayout.WEST);
        fieldPanels[2].add(cmbRoom, BorderLayout.CENTER);
        
  
        JLabel lblCheckIn = new JLabel("Check-in Date:");
        lblCheckIn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtCheckInDate = new JTextField(getCurrentDate());
        txtCheckInDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[3].add(lblCheckIn, BorderLayout.WEST);
        fieldPanels[3].add(txtCheckInDate, BorderLayout.CENTER);
        
        JLabel lblCheckOut = new JLabel("Check-out Date:");
        lblCheckOut.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtCheckOutDate = new JTextField(getTomorrowDate());
        txtCheckOutDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[4].add(lblCheckOut, BorderLayout.WEST);
        fieldPanels[4].add(txtCheckOutDate, BorderLayout.CENTER);
        

        JLabel lblNights = new JLabel("Number of Nights:");
        lblNights.setFont(new Font("Segoe UI", Font.BOLD, 14));
        spnNumberOfNights = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        spnNumberOfNights.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[5].add(lblNights, BorderLayout.WEST);
        fieldPanels[5].add(spnNumberOfNights, BorderLayout.CENTER);
        
   
        JLabel lblGuests = new JLabel("Number of Guests:");
        lblGuests.setFont(new Font("Segoe UI", Font.BOLD, 14));
        spnNumberOfGuests = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spnNumberOfGuests.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[6].add(lblGuests, BorderLayout.WEST);
        fieldPanels[6].add(spnNumberOfGuests, BorderLayout.CENTER);
        
  
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cmbStatus = new JComboBox<>(Reservation.Status.values());
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[7].add(lblStatus, BorderLayout.WEST);
        fieldPanels[7].add(cmbStatus, BorderLayout.CENTER);
        

        JLabel lblTotalPrice = new JLabel("Total Price:");
        lblTotalPrice.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtTotalPrice = new JTextField("0.00");
        txtTotalPrice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTotalPrice.setEditable(false);
        fieldPanels[8].add(lblTotalPrice, BorderLayout.WEST);
        fieldPanels[8].add(txtTotalPrice, BorderLayout.CENTER);
        
     
        for (JPanel fieldPanel : fieldPanels) 
        {
            add(fieldPanel);
            add(Box.createRigidArea(new Dimension(0, 15)));
        }
        

        btnCalculate = new JButton("Calculate TotalPrice");
        btnCalculate.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCalculate.setBackground(new Color(155, 89, 182));
        btnCalculate.setFocusPainted(false);
        btnCalculate.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCalculate.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCalculate.setMaximumSize(new Dimension(400, 40));
        btnCalculate.addActionListener(e -> calculatePrice());
        btnCalculate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        add(btnCalculate);
        add(Box.createRigidArea(new Dimension(0, 20)));

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
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.gray);
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
        btnAdd.addActionListener(e -> addReservation());
        btnEdit.addActionListener(e -> editReservation());
        btnDelete.addActionListener(e -> deleteReservation());
        btnClear.addActionListener(e -> clearForm());
        
        cmbRoom.addActionListener(e -> validateGuestCapacity());
    }
    
    private void addReservation() {
        try {
    
            if (txtReservationId.getText().trim().isEmpty() || cmbCustomer.getSelectedItem() == null ||
                cmbRoom.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields!", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
     
            for (Reservation reservation : reservationManagementForm.getReservationList()) {
                if (reservation.getReservationId().equals(txtReservationId.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "Reservation ID already exists!", 
                        "Duplicate Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            Customer selectedCustomer = (Customer) cmbCustomer.getSelectedItem();
            Room selectedRoom = (Room) cmbRoom.getSelectedItem();
            
            if (selectedRoom.getStatus() == Room.RoomStatus.BOOKED) {
                JOptionPane.showMessageDialog(this, "Selected room is already booked!", 
                    "Room Not Available", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int numberOfGuests = (int) spnNumberOfGuests.getValue();
            if (numberOfGuests > selectedRoom.getCapacity()) {
                JOptionPane.showMessageDialog(this, 
                    "Number of guests exceeds room capacity!\nRoom capacity: " + selectedRoom.getCapacity(), 
                    "Capacity Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            MyDate checkInDate = reservationManagementForm.parseDate(txtCheckInDate.getText().trim());
            MyDate checkOutDate = reservationManagementForm.parseDate(txtCheckOutDate.getText().trim());
            
            Reservation reservation = new Reservation(
                txtReservationId.getText().trim(),
                selectedCustomer,
                selectedRoom,
                checkInDate,
                checkOutDate,
                (int) spnNumberOfNights.getValue(),
                numberOfGuests
            );

            reservation.setStatus((Reservation.Status) cmbStatus.getSelectedItem());
            
            reservationManagementForm.addReservation(reservation);
            clearForm();
            
            JOptionPane.showMessageDialog(this, "Reservation added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);

                
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format! use the format MM/DD/YYYY", 
                "Date Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editReservation() 
    {
        if (txtReservationId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "select a reservation to edit!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String reservationId = txtReservationId.getText().trim();
            Reservation existingReservation = reservationManagementForm.getReservationById(reservationId);
            
            if (existingReservation == null) 
            {
                JOptionPane.showMessageDialog(this, "Reservation not found!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Customer selectedCustomer = (Customer) cmbCustomer.getSelectedItem();
            Room selectedRoom = (Room) cmbRoom.getSelectedItem();

            int numberOfGuests = (int) spnNumberOfGuests.getValue();
            if (numberOfGuests > selectedRoom.getCapacity()) {
                JOptionPane.showMessageDialog(this, 
                    "Number of guests exceeds room capacity!\nRoom capacity: " + selectedRoom.getCapacity(), 
                    "Capacity Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            MyDate checkInDate = reservationManagementForm.parseDate(txtCheckInDate.getText().trim());
            MyDate checkOutDate = reservationManagementForm.parseDate(txtCheckOutDate.getText().trim());

            Reservation updatedReservation = new Reservation(
                reservationId,
                selectedCustomer,
                selectedRoom,
                checkInDate,
                checkOutDate,
                (int) spnNumberOfNights.getValue(),
                numberOfGuests
            );
            
            updatedReservation.setStatus((Reservation.Status) cmbStatus.getSelectedItem());
            
            reservationManagementForm.updateReservation(updatedReservation);
            
            JOptionPane.showMessageDialog(this, "Reservation updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Please use MM/DD/YYYY", 
                "Date Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteReservation() {
        if (txtReservationId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "select a reservation to delete!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String reservationId = txtReservationId.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete reservation " + reservationId + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            reservationManagementForm.deleteReservation(reservationId);
            clearForm();
            
            JOptionPane.showMessageDialog(this, "Reservation deleted successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void clearForm() {
        txtReservationId.setText("");
        if (cmbCustomer.getItemCount() > 0) cmbCustomer.setSelectedIndex(0);
        if (cmbRoom.getItemCount() > 0) cmbRoom.setSelectedIndex(0);
        txtCheckInDate.setText(getCurrentDate());
        txtCheckOutDate.setText(getTomorrowDate());
        spnNumberOfNights.setValue(1);
        spnNumberOfGuests.setValue(1);
        cmbStatus.setSelectedIndex(0);
        txtTotalPrice.setText("0.00");
    }
    
    public void fillFormFromTable(int row) 
    {
        ReservationDisplayPanel displayPanel = reservationManagementForm.reservationDisplayPanel;
        javax.swing.table.DefaultTableModel model = displayPanel.getTableModel();
        
        txtReservationId.setText(model.getValueAt(row, 0).toString());

        String customerName = model.getValueAt(row, 1).toString();
        for (int i = 0; i < cmbCustomer.getItemCount(); i++) {
            if (cmbCustomer.getItemAt(i).getName().equals(customerName)) {
                cmbCustomer.setSelectedIndex(i);
                break;
            }
        }

        String roomId = model.getValueAt(row, 2).toString();
        for (int i = 0; i < cmbRoom.getItemCount(); i++) {
            if (cmbRoom.getItemAt(i).getId().equals(roomId)) {
                cmbRoom.setSelectedIndex(i);
                break;
            }
        }
        
        txtCheckInDate.setText(model.getValueAt(row, 3).toString());
        txtCheckOutDate.setText(model.getValueAt(row, 4).toString());
        spnNumberOfNights.setValue(Integer.parseInt(model.getValueAt(row, 5).toString()));
        spnNumberOfGuests.setValue(Integer.parseInt(model.getValueAt(row, 6).toString()));
        cmbStatus.setSelectedItem(Reservation.Status.valueOf(model.getValueAt(row, 7).toString()));
        
        String priceStr = model.getValueAt(row, 8).toString().replace("$", "");
        txtTotalPrice.setText(priceStr);
    }
    
    private void calculatePrice() 
    {
        try {
            Room selectedRoom = (Room) cmbRoom.getSelectedItem();
            if (selectedRoom != null) {
                int numberOfNights = (int) spnNumberOfNights.getValue();
                double totalPrice = selectedRoom.getPrice() * numberOfNights;
                txtTotalPrice.setText(String.format("%.2f", totalPrice));
            }
        } catch (Exception ex) {
            txtTotalPrice.setText("0.00");
        }
    }
    
    private void validateGuestCapacity() 
    {
        Room selectedRoom = (Room) cmbRoom.getSelectedItem();
        if (selectedRoom != null) {
            int maxGuests = selectedRoom.getCapacity();
            spnNumberOfGuests.setModel(new SpinnerNumberModel(1, 1, maxGuests, 1));
        }
    }
    
    private String getCurrentDate() 
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(new Date());
    }
    
    private String getTomorrowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return sdf.format(calendar.getTime());
    }
}