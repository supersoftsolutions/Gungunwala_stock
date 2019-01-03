import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import java.awt.event.ActionListener;

public class Add_country extends JInternalFrame {
	private JTextField country;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_country frame = new Add_country();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Add_country() {
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
	            int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION)
				{
					dispose();
				}
            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-120;
        int w = (int)(width);
        int h = (int)(height);
        
		setBounds((w-450)/2, (h-300)/2, 450, 300);
		setClosable(true);
		getContentPane().setLayout(null);
		
		final Connection connection1 = Databaseconnection.connection2();
		
		JLabel lblAddCountry = new JLabel("Country");
		lblAddCountry.setBounds(147, 28, 434, 25);
		lblAddCountry.setFont(new Font("Tahoma", Font.BOLD, 20));
		getContentPane().add(lblAddCountry);
		
		JLabel lblCountry = new JLabel(" Country Name:");
		lblCountry.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCountry.setBounds(38, 77, 142, 25);
		getContentPane().add(lblCountry);
		
		country = new JTextField();
		country.setColumns(10);
		country.setBounds(226, 81, 147, 28);
		getContentPane().add(country);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					if(country.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else 
                    {
                    	String query="insert into Country (Name) VALUES ('"+country.getText()+"')";
                        PreparedStatement pmt = connection1.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                      
                        
                        Add_country b = new Add_country();
		                JDesktopPane desktopPane = getDesktopPane();
		                desktopPane.add(b);
		                b.show();
		                dispose();
        		    
                    }
				
					if(country.getText().trim().equals(""))
					{
						JOptionPane.showMessageDialog(null, "please fill details");
					}
				
					else
					{
						Add_country b = new Add_country();
		                JDesktopPane desktopPane = getDesktopPane();
		                desktopPane.add(b);
		                b.show();
		                dispose();
					}
					
					
				}
					
				catch(Exception a)
				{
					a.printStackTrace();
				}
				
			
			
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(147, 132, 89, 23);
		getContentPane().add(button);

	}

}
