
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class HotelManagementSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    public HotelManagementSystem() 
    {
        setTitle("Hotel Reservation System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        

        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        Font customFont = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", customFont);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("TextField.font", customFont);
        UIManager.put("ComboBox.font", customFont);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        

        RoomManagementForm roomManagementForm = new RoomManagementForm(this);
        CustomerManagementForm customerManagementForm = new CustomerManagementForm(this);
        ReservationManagementForm reservationManagementForm = new ReservationManagementForm(this);
        
        mainPanel.add(roomManagementForm, "ROOMS");
        mainPanel.add(customerManagementForm, "CUSTOMERS");
        mainPanel.add(reservationManagementForm, "RESERVATIONS");

        Sidebar sidebar = new Sidebar(this);
        
        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                int choice = JOptionPane.showConfirmDialog(
                    HotelManagementSystem.this,
                    "Save all data before exiting?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (choice == JOptionPane.YES_OPTION) 
                {
                    try 
                    {
                        roomManagementForm.saveData();
                        customerManagementForm.saveData();
                        reservationManagementForm.saveData();
                        JOptionPane.showMessageDialog(
                            HotelManagementSystem.this,
                            "All data saved successfully!",
                            "Data Saved",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } 
                    catch (Exception ex) 
                    {
                        JOptionPane.showMessageDialog(
                            HotelManagementSystem.this,
                            "Error saving data: " + ex.getMessage(),
                            "Save Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
    }
    
    public void showForm(String formName) {
        cardLayout.show(mainPanel, formName);
    }
    
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> { HotelManagementSystem app = new HotelManagementSystem();
            app.setVisible(true);
        });
    }
}