// ReservationDisplayPanel.java
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ReservationDisplayPanel extends JPanel {
    private ReservationManagementForm reservationManagementForm;
    private JTable reservationTable;
    private DefaultTableModel tableModel;
    
    public ReservationDisplayPanel(ReservationManagementForm reservationManagementForm) {
        this.reservationManagementForm = reservationManagementForm;
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 230, 240)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        initComponents();
    }
    
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    
    private void initComponents() {
        // Table title
        JLabel tableTitle = new JLabel("Reservation List");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tableTitle.setForeground(new Color(45, 62, 80));
        add(tableTitle, BorderLayout.NORTH);
        
        // Create table
        String[] columns = {"Reservation ID", "Customer", "Room", "Check-in", "Check-out", "Nights", "Guests", "Status", "Total Price"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        reservationTable = new JTable(tableModel);
        reservationTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        reservationTable.setRowHeight(30);
        reservationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reservationTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        reservationTable.getTableHeader().setBackground(new Color(52, 73, 94));
        reservationTable.getTableHeader().setForeground(Color.DARK_GRAY);
        reservationTable.getTableHeader().setReorderingAllowed(false);
        

        reservationTable.getColumnModel().getColumn(7).setCellRenderer(new StatusRenderer());
        
        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        add(scrollPane, BorderLayout.CENTER);
        
        reservationTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && reservationManagementForm != null) {
                int selectedRow = reservationTable.getSelectedRow();
                if (selectedRow != -1) {
                    reservationManagementForm.fillFormFromTable(selectedRow);
                }
            }
        });
    }
    
    public void refreshTable() {
        if (reservationManagementForm == null) return;
        
        tableModel.setRowCount(0);
        java.util.List<Reservation> reservations = reservationManagementForm.getReservationList();
        for (Reservation reservation : reservations) {
            tableModel.addRow(new Object[]{
                reservation.getReservationId(),
                reservation.getCustomer().getName(),
                reservation.getRoom().getId(),
                reservation.getCheckInDate().toString(),
                reservation.getCheckOutDate().toString(),
                reservation.getNumberOfNights(),
                reservation.getNumberOfGuests(),
                reservation.getStatus().toString(),
                String.format("$%.2f", reservation.getTotalPrice())
            });
        }
    }
    

    private class StatusRenderer extends DefaultTableCellRenderer 
    {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) 
        {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (value != null) {
                String status = value.toString();
                if (status.equals("CONFIRMED")) {
                    c.setBackground(new Color(220, 255, 220));
                    c.setForeground(new Color(0, 128, 0));
                } else if (status.equals("CANCELLED")) {
                    c.setBackground(new Color(255, 220, 220));
                    c.setForeground(new Color(255, 0, 0));
                } else if (status.equals("COMPLETED")) {
                    c.setBackground(new Color(220, 240, 255));
                    c.setForeground(new Color(0, 0, 255));
                }
                
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }
            }
            
            setHorizontalAlignment(SwingConstants.CENTER);
            return c;
        }
    }
}