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
import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;

public class Add_city extends JInternalFrame {
	private JTextField city;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_city frame = new Add_city();
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
	public Add_city() {
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
		
		final Connection connection = Databaseconnection.connection2();
		
		JLabel label = new JLabel("Add City");
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setBounds(139, 38, 146, 24);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Country Name:*");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(24, 91, 125, 25);
		getContentPane().add(label_1);
		
		JComboBox country = new JComboBox();
		country.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
			try{
        	
            String query1 = "select * from Country";
		    PreparedStatement pmt1 = connection.prepareStatement(query1);
		    ResultSet rs = pmt1.executeQuery();
		   
		    while(rs.next())
		    {
		    	country.addItem(rs.getString("Name"));
		    }
		    rs.close();
		}
        catch (SQLException ae) {
		    ae.printStackTrace();
		}
		country.setFont(new Font("Tahoma", Font.PLAIN, 16));
		country.setBounds(184, 90, 191, 28);
		getContentPane().add(country);
		
		JLabel label_2 = new JLabel("State Name:*");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_2.setBounds(24, 128, 125, 25);
		getContentPane().add(label_2);
		
		JComboBox state = new JComboBox();
		state.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		country.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
				try{
	        	
	            String query1 = "select * from State where Country_name='"+country.getSelectedItem().toString()+"'";
			    PreparedStatement pmt1 = connection.prepareStatement(query1);
			    ResultSet rs = pmt1.executeQuery();
			   
			    while(rs.next())
			    {
			    	state.addItem(rs.getString("State_name"));
			    }
			    rs.close();
			}
	        catch (SQLException ae) {
			    ae.printStackTrace();
			}
			}
		});
				
		state.setFont(new Font("Tahoma", Font.PLAIN, 16));
		state.setBounds(184, 127, 191, 28);
		getContentPane().add(state);
		
		JLabel label_3 = new JLabel("City Name:*");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_3.setBounds(24, 164, 125, 25);
		getContentPane().add(label_3);
		
		city = new JTextField();
		city.setFont(new Font("Tahoma", Font.PLAIN, 16));
		city.setColumns(10);
		city.setBounds(186, 168, 189, 28);
		getContentPane().add(city);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					if(country.getSelectedItem().toString().equals("SELECT")||city.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else {
                        String query="insert into City (Country_name,State_name,City_name) VALUES ('"+country.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+city.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                    }
                }
				
				catch(Exception ae)
				
				{
					ae.printStackTrace();
				}
				
				country.setSelectedIndex(0);
				state.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
				city.setText("");
				
		
		}
	});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(148, 236, 89, 23);
		getContentPane().add(button);

	}

		}
		
