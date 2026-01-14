// CustomerFormPanel.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomerFormPanel extends JPanel 
{
    private CustomerManagementForm customerManagementForm;
    private JTextField txtCustomerId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtAddress;
    private JButton btnAdd, btnEdit, btnDelete, btnClear;
    
    public CustomerFormPanel(CustomerManagementForm customerManagementForm) {
        this.customerManagementForm = customerManagementForm;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 230, 240)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        initComponents();
        addActionListeners();
    }
    
    private void initComponents() 
    {
        JLabel formTitle = new JLabel("Customer Information");
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

        JLabel lblCustomerId = new JLabel("Customer ID:");
        lblCustomerId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtCustomerId = new JTextField();
        txtCustomerId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[0].add(lblCustomerId, BorderLayout.WEST);
        fieldPanels[0].add(txtCustomerId, BorderLayout.CENTER);
        
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[1].add(lblName, BorderLayout.WEST);
        fieldPanels[1].add(txtName, BorderLayout.CENTER);
        
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[2].add(lblEmail, BorderLayout.WEST);
        fieldPanels[2].add(txtEmail, BorderLayout.CENTER);
        
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtPhone = new JTextField();
        txtPhone.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[3].add(lblPhone, BorderLayout.WEST);
        fieldPanels[3].add(txtPhone, BorderLayout.CENTER);
        
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtAddress = new JTextField();
        txtAddress.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanels[4].add(lblAddress, BorderLayout.WEST);
        fieldPanels[4].add(txtAddress, BorderLayout.CENTER);
        
        for (JPanel fieldPanel : fieldPanels) 
        {
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
        btnAdd.addActionListener(e -> addCustomer());
        btnEdit.addActionListener(e -> editCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnClear.addActionListener(e -> clearForm());
    }
    
    private void addCustomer() 
    {
        try {
            if (txtCustomerId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty() ||
                txtAddress.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            for (Customer customer : customerManagementForm.getCustomerList()) {
                if (customer.getCustomerId().equals(txtCustomerId.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "Customer ID already exists!", 
                        "Duplicate Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
       
            String email = txtEmail.getText().trim();
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address!", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Customer customer = new Customer(
                txtCustomerId.getText().trim(),
                txtName.getText().trim(),
                email,
                txtPhone.getText().trim(),
                txtAddress.getText().trim()
            );
            
            customerManagementForm.addCustomer(customer);
            clearForm();
            
            JOptionPane.showMessageDialog(this, "Customer added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editCustomer() {
        if (txtCustomerId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a customer to edit!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String customerId = txtCustomerId.getText().trim();
            Customer existingCustomer = customerManagementForm.getCustomerById(customerId);
            
            if (existingCustomer == null) {
                JOptionPane.showMessageDialog(this, "Customer not found!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String email = txtEmail.getText().trim();
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address!", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Customer updatedCustomer = new Customer(
                customerId,
                txtName.getText().trim(),
                email,
                txtPhone.getText().trim(),
                txtAddress.getText().trim()
            );
            
            customerManagementForm.updateCustomer(updatedCustomer);
            
            JOptionPane.showMessageDialog(this, "Customer updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteCustomer() {
        if (txtCustomerId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String customerId = txtCustomerId.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete customer " + customerId + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            customerManagementForm.deleteCustomer(customerId);
            clearForm();
            
            JOptionPane.showMessageDialog(this, "Customer deleted successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void clearForm() {
        txtCustomerId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
    }
    
    public void fillFormFromTable(int row) {
        CustomerDisplayPanel displayPanel = customerManagementForm.customerDisplayPanel;
        javax.swing.table.DefaultTableModel model = displayPanel.getTableModel();
        
        txtCustomerId.setText(model.getValueAt(row, 0).toString());
        txtName.setText(model.getValueAt(row, 1).toString());
        txtEmail.setText(model.getValueAt(row, 2).toString());
        txtPhone.setText(model.getValueAt(row, 3).toString());
        txtAddress.setText(model.getValueAt(row, 4).toString());
    }
}