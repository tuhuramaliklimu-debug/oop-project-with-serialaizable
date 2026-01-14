// RoomDisplayPanel.java
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class RoomDisplayPanel extends JPanel 
{
    private RoomManagementForm roomManagementForm;
    private JTable roomTable;
    private DefaultTableModel tableModel;
    
    public RoomDisplayPanel(RoomManagementForm roomManagementForm) 
    {
        this.roomManagementForm = roomManagementForm;
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 230, 240)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        initComponents();
    }
    
    public DefaultTableModel getTableModel() 
    {
        return tableModel;
    }
    
    private void initComponents()
    {
        JLabel tableTitle = new JLabel("Room List");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tableTitle.setForeground(new Color(45, 62, 80));
        add(tableTitle, BorderLayout.NORTH);

        String[] columns = {"Room ID", "Type", "Status", "Capacity", "Price"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        roomTable = new JTable(tableModel);
        roomTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        roomTable.setRowHeight(30);
        roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        roomTable.getTableHeader().setBackground(new Color(45, 62, 80));
        roomTable.getTableHeader().setForeground(Color.DARK_GRAY);
        roomTable.getTableHeader().setReorderingAllowed(false);
        

        roomTable.getColumnModel().getColumn(3).setCellRenderer(new StatusRenderer());
        
        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        add(scrollPane, BorderLayout.CENTER);
        
        roomTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && roomManagementForm != null) {
                int selectedRow = roomTable.getSelectedRow();
                if (selectedRow != -1) {
                    roomManagementForm.fillFormFromTable(selectedRow);
                }
            }
        });
    }
    
    public void refreshTable() {
        if (roomManagementForm == null) return;
        
        tableModel.setRowCount(0);
        java.util.List<Room> rooms = roomManagementForm.getRoomList();
        for (Room room : rooms) {
            tableModel.addRow(new Object[]{
                room.getId(),
                room.getType().toString(),
                room.getStatus().toString(),
                room.getCapacity(),
                String.format("$%.2f", room.getPrice())
            });
        }
    }
    
   
    private class StatusRenderer extends DefaultTableCellRenderer 
    {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) 
        {
            Component c = super.getTableCellRendererComponent(table, value,  isSelected, hasFocus, row, column);
            
            if (value != null) {
                String status = value.toString();
                if (status.equals("FREE")) {
                    c.setBackground(new Color(220, 255, 220));
                    c.setForeground(new Color(0, 128, 0));
                } else if (status.equals("BOOKED")) {
                    c.setBackground(new Color(255, 220, 220));
                    c.setForeground(new Color(255, 0, 0));
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