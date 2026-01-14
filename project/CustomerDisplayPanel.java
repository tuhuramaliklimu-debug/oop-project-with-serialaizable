// CustomerDisplayPanel.java
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class CustomerDisplayPanel extends JPanel 
{
    private CustomerManagementForm customerManagementForm;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    
    public CustomerDisplayPanel(CustomerManagementForm customerManagementForm) 
    {
        this.customerManagementForm = customerManagementForm;
        
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
    
    private void initComponents() 
    {
        JLabel tableTitle = new JLabel("Customer List");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tableTitle.setForeground(new Color(45, 62, 80));
        add(tableTitle, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Email", "Phone", "Address"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        customerTable = new JTable(tableModel);
        customerTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        customerTable.setRowHeight(30);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        customerTable.getTableHeader().setBackground(new Color(52, 73, 94));
        customerTable.getTableHeader().setForeground(Color.DARK_GRAY);
        customerTable.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        add(scrollPane, BorderLayout.CENTER);
      
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && customerManagementForm != null) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    customerManagementForm.fillFormFromTable(selectedRow);
                }
            }
        });
    }
    
    public void refreshTable() {
        if (customerManagementForm == null) return;
        
        tableModel.setRowCount(0);
        java.util.List<Customer> customers = customerManagementForm.getCustomerList();
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress()
            });
        }
    }
}