// CustomerManagementForm.java
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CustomerManagementForm extends JPanel 
{
    private HotelManagementSystem parent;
    private List<Customer> customerList;
    public CustomerDisplayPanel customerDisplayPanel;
    public CustomerFormPanel customerFormPanel;
    
    public CustomerManagementForm(HotelManagementSystem parent) {
        this.parent = parent;
        customerList = new ArrayList<>();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 250));

        loadData();

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 250));

        JPanel headerPanel=createHeaderPanel();

        customerFormPanel = new CustomerFormPanel(this);
        customerDisplayPanel = new CustomerDisplayPanel(this);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        splitPane.setDividerSize(3);
        splitPane.setLeftComponent(customerFormPanel);
        splitPane.setRightComponent(customerDisplayPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        refreshDisplay();
    }
    
    private JPanel createHeaderPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 245, 250));
        
        JLabel titleLabel = new JLabel("Manage Customers");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(45, 62, 80));
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.gray);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnRefresh.addActionListener(e -> refreshDisplay());
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(btnRefresh, BorderLayout.EAST);
        
        return panel;
    }

    public List<Customer> getCustomerList() 
    {
        return customerList;
    }
    public JPanel getCustomerDisplayPanel() 
    {
    return customerDisplayPanel;
    }
    public void refreshDisplay() 
    {
        customerDisplayPanel.refreshTable();
    }
    public void fillFormFromTable(int row) 
    {
        customerFormPanel.fillFormFromTable(row);
    }
    public void addCustomer(Customer customer) 
    {
        customerList.add(customer);
        saveData();
        refreshDisplay();
    }
    
    public void updateCustomer(Customer customer) 
    {
        for (int i = 0; i < customerList.size(); i++) 
            {
            if (customerList.get(i).getCustomerId().equals(customer.getCustomerId())) {
                customerList.set(i, customer);
                saveData();
                refreshDisplay();
                break;
            }
        }
    }
    
    public void deleteCustomer(String customerId) 
    {
        customerList.removeIf(customer -> customer.getCustomerId().equals(customerId));
        saveData();
        refreshDisplay();
    }
    
    public Customer getCustomerById(String customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
    
    public void loadData() 
    {
        try {
            List<Customer> loadedCustomers = FileHandler.loadCustomers();
            if (loadedCustomers != null) {
                customerList = loadedCustomers;
            }
        } catch (Exception e) {
            System.out.println("No customer data found or error loading: " + e.getMessage());
        }
    }
    
    public void saveData() 
    {
        try {
            FileHandler.saveCustomers(customerList);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving customer data: " + e.getMessage(), 
                "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}