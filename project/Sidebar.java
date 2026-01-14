import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Sidebar extends JPanel 
{
    private JLabel selectedMenuItem;
    private HotelManagementSystem parent;

    private static final Color BACKGROUND = new Color(45, 62, 80);
    private static final Color HEADER_BG = new Color(39, 55, 70);
    private static final Color GOLD = new Color(255, 215, 0);
    private static final Color WHITE = Color.WHITE;
    private static final Color HOVER = new Color(52, 73, 94);
    private static final Color SELECTED = new Color(41, 128, 185);
    private static final Color VERSION = new Color(200, 200, 200);
    
    public Sidebar(HotelManagementSystem parent) 
    {
        this.parent = parent;
        setupSidebar();
    }
    
    private void setupSidebar() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        setPreferredSize(new Dimension(220, 0));

        JPanel header = createHeader();
        add(header, BorderLayout.NORTH);
        

        JPanel menu = createMenu();
        add(menu, BorderLayout.CENTER);
        
        JPanel footer = createFooter();
        add(footer, BorderLayout.SOUTH);
    }
    
    private JPanel createHeader() 
    {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(HEADER_BG);
        headerPanel.setPreferredSize(new Dimension(220, 120));
        
        headerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
   
        JLabel hotelName = new JLabel("LUXURY HOTEL");
        hotelName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hotelName.setForeground(GOLD);
        
        gbc.gridy = 1;
        headerPanel.add(hotelName, gbc);
        
        return headerPanel;
    }
    
    private JPanel createMenu() 
    {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        String[] icons = {"🏠", "👥", "📅"};
        String[] texts = {"Manage Rooms", "Customers", "Bookings"};
        String[] actions = {"ROOMS", "CUSTOMERS", "RESERVATIONS"};
        
        for (int i = 0; i < icons.length; i++) {
            JPanel menuItem = createMenuItem(icons[i], texts[i], actions[i]);
            menuPanel.add(menuItem);
            if (i < icons.length - 1) {
                menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        return menuPanel;
    }
    
    private JPanel createMenuItem(String icon, String text, String action) {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        iconLabel.setForeground(WHITE);
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textLabel.setForeground(WHITE);
        
        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textLabel, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                selectMenu(panel, textLabel);
                performAction(action);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                if (selectedMenuItem != textLabel) 
                {
                    panel.setBackground(HOVER);
                    panel.setOpaque(true);
                    panel.repaint();
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                if (selectedMenuItem != textLabel) 
                {
                    panel.setOpaque(false);
                    panel.repaint();
                }
            }
        });
        
        return panel;
    }
    
    private void selectMenu(JPanel panel, JLabel label) 
    {
        if (selectedMenuItem!=null) 
        {
            Container parent=selectedMenuItem.getParent();
            if (parent != null) 
            {
                JPanel previousPanel = (JPanel) parent;
                previousPanel.setOpaque(false);
                previousPanel.setBackground(null);
                previousPanel.repaint();
            }

        }
        selectedMenuItem = label;
        panel.setBackground(SELECTED);
        panel.setOpaque(true);
        panel.repaint();
    }
    
    private void performAction(String action) {
        if (parent == null) return;
        
        switch (action) {
            case "ROOMS":
                parent.showForm("ROOMS");
                break;
            case "CUSTOMERS":
                parent.showForm("CUSTOMERS");
                break;
            case "RESERVATIONS":
                parent.showForm("RESERVATIONS");
                break;
        }
    }
    
    private JPanel createFooter() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        JLabel version = new JLabel("Version 1.0.0");
        version.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        version.setForeground(VERSION);
        version.setHorizontalAlignment(SwingConstants.CENTER);
        
        footerPanel.add(version, BorderLayout.CENTER);
        return footerPanel;
    }
}