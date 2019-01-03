import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;

public class Add_State extends JInternalFrame {
	private JTextField state;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_State frame = new Add_State();
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
	public Add_State() {
		
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
		JLabel label = new JLabel("Add State");
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setBounds(177, 25, 146, 24);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Country Name:*");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(54, 88, 125, 14);
		getContentPane().add(label_1);
		
		JComboBox country = new JComboBox();
		country.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		try
		{
			String query5 = "select * from Country";
            PreparedStatement pmt5 = connection1.prepareStatement(query5);
            ResultSet rs5 = pmt5.executeQuery();
		    while(rs5.next())
		    {
		    	country.addItem(rs5.getString("Name"));
            }
		    rs5.close();
		    pmt5.close();
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		country.setFont(new Font("Tahoma", Font.PLAIN, 16));
		country.setBounds(214, 87, 138, 20);
		getContentPane().add(country);
		
		JLabel label_2 = new JLabel("State Name:*");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_2.setBounds(54, 133, 125, 24);
		getContentPane().add(label_2);
		
		state = new JTextField();
		state.setFont(new Font("Tahoma", Font.PLAIN, 16));
		state.setColumns(10);
		state.setBounds(216, 137, 136, 28);
		getContentPane().add(state);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					
                    if(country.getSelectedItem().toString().equals("SELECT")||state.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    
                    else {
                        String query="insert into State (Country_name,State_name) VALUES ('"+country.getSelectedItem().toString()+"','"+state.getText()+"')";
                        PreparedStatement pmt = connection1.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                    }
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
				
				
				country.setSelectedIndex(0);
				state.setText("");
			}
		});
			
	
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(177, 202, 89, 23);
		getContentPane().add(button);
		
		state.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					
                    if(country.getSelectedItem().toString().equals("SELECT")||state.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    
                    else {
                        String query="insert into State (Country_name,State_name) VALUES ('"+country.getSelectedItem().toString()+"','"+state.getText()+"')";
                        PreparedStatement pmt = connection1.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                    }
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
				
				
				country.setSelectedIndex(0);
				state.setText("");
			}
		});

	}

}
