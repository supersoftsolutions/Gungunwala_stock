import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Edit_client extends JInternalFrame {
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField address3;
	private JTextField contact_person;
	private JTextField mobile;
	private JTextField balance;
	private JTextField client;
	int id=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit_client frame = new Edit_client();
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
	public Edit_client() {
		getContentPane().setLayout(null);getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
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
        setClosable(true);
		setBounds((w-450)/2, (h-506)/2, 450, 506);
		getContentPane().setLayout(null);
		
		final Connection connection = Databaseconnection.connection2();

		JLabel label = new JLabel("Name:*");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(10, 96, 87, 25);
		getContentPane().add(label);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(135, 100, 269, 28);
		getContentPane().add(name);
		
		JLabel label_1 = new JLabel("Address1:*");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(10, 132, 87, 25);
		getContentPane().add(label_1);
		
		address1 = new JTextField();
		address1.setColumns(10);
		address1.setBounds(135, 136, 269, 28);
		getContentPane().add(address1);
		
		JLabel label_2 = new JLabel("Address2:*");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_2.setBounds(10, 168, 87, 25);
		getContentPane().add(label_2);
		
		address2 = new JTextField();
		address2.setColumns(10);
		address2.setBounds(135, 172, 269, 28);
		getContentPane().add(address2);
		
		JLabel label_3 = new JLabel("Address3:");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_3.setBounds(10, 204, 87, 25);
		getContentPane().add(label_3);
		
		address3 = new JTextField();
		address3.setColumns(10);
		address3.setBounds(135, 208, 269, 28);
		getContentPane().add(address3);
		
		JLabel label_4 = new JLabel("Country:*");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_4.setBounds(10, 241, 87, 20);
		getContentPane().add(label_4);
		
		JComboBox country = new JComboBox();
		country.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		country.setFont(new Font("Tahoma", Font.PLAIN, 16));
		country.setBounds(135, 240, 269, 25);
		getContentPane().add(country);
		
		try
		{
			String query5 = "select * from Country";
            PreparedStatement pmt5 = connection.prepareStatement(query5);
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
		
		JLabel label_5 = new JLabel("State:*");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_5.setBounds(10, 272, 87, 20);
		getContentPane().add(label_5);
		
		JComboBox state = new JComboBox();
		state.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		state.setFont(new Font("Tahoma", Font.PLAIN, 16));
		state.setBounds(135, 272, 269, 25);
		getContentPane().add(state);
		
		JLabel label_6 = new JLabel("City:*");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_6.setBounds(10, 303, 87, 20);
		getContentPane().add(label_6);
		
		JComboBox city = new JComboBox();
		city.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		city.setFont(new Font("Tahoma", Font.PLAIN, 16));
		city.setBounds(135, 303, 269, 25);
		getContentPane().add(city);
		
		country.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					state.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
					city.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
					
					String query5 = "select * from State where Country_name='"+country.getSelectedItem().toString()+"'";
		            PreparedStatement pmt5 = connection.prepareStatement(query5);
		            ResultSet rs5 = pmt5.executeQuery();
				    while(rs5.next())
				    {
				    	state.addItem(rs5.getString("State_name"));
		            }
				    rs5.close();
				    pmt5.close();
				    
				}
				catch(Exception a)
				{
					a.printStackTrace();
				}
			}
		});
		
		state.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					
					city.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
					
					String query5 = "select * from City where State_name='"+state.getSelectedItem().toString()+"'";
		            PreparedStatement pmt5 = connection.prepareStatement(query5);
		            ResultSet rs5 = pmt5.executeQuery();
				    while(rs5.next())
				    {
				    	city.addItem(rs5.getString("City_name"));
		            }
				    rs5.close();
				    pmt5.close();
				    
				}
				catch(Exception a)
				{
					a.printStackTrace();
				}
			}
		});
		
		JLabel lblContactPerson = new JLabel("Contact Person:*");
		lblContactPerson.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblContactPerson.setBounds(10, 334, 133, 25);
		getContentPane().add(lblContactPerson);
		
		contact_person = new JTextField();
		contact_person.setColumns(10);
		contact_person.setBounds(134, 338, 269, 28);
		getContentPane().add(contact_person);
		
		JLabel lblMobile = new JLabel("Mobile:*");
		lblMobile.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMobile.setBounds(10, 370, 87, 35);
		getContentPane().add(lblMobile);
		
		mobile = new JTextField();
		mobile.setColumns(10);
		mobile.setBounds(135, 377, 269, 28);
		getContentPane().add(mobile);
		
	
		
		JLabel lblEditCreditor = new JLabel("Edit Client");
		lblEditCreditor.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEditCreditor.setBounds(135, 11, 158, 25);
		getContentPane().add(lblEditCreditor);
		
		JLabel label_8 = new JLabel("Name:*");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_8.setBounds(10, 53, 87, 25);
		getContentPane().add(label_8);
		
		client = new JTextField();
		client.setEditable(false);
		client.setColumns(10);
		client.setBounds(135, 57, 269, 28);
		getContentPane().add(client);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					if(name.getText().equals("")||address1.getText().equals("")||address2.getText().equals("")||city.getSelectedItem().toString().equals("SELECT")||mobile.getText().equals("")||contact_person.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "please Fill Details");
					}
					else
					{
						
		            	String query="update Client set Name='"+name.getText()+"',Address1='"+address1.getText()+"',Address2='"+address2.getText()+"',Address3='"+address3.getText()+"',Country='"+country.getSelectedItem().toString()+"',State='"+state.getSelectedItem().toString()+"',City='"+city.getSelectedItem().toString()+"',Mobile='"+mobile.getText()+"',Contact_person='"+contact_person.getText()+"' where ID='"+id+"'";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
		            	
                        View_client b = new View_client();
		                JDesktopPane desktopPane = getDesktopPane();
		                desktopPane.add(b);
		                b.show();
		                dispose();
					}
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(138, 429, 89, 28);
		getContentPane().add(button);
		
		
		try
		{
			String query5 = "select * from Client_id";
            PreparedStatement pmt5 = connection.prepareStatement(query5);
            ResultSet rs5 = pmt5.executeQuery();
		    while(rs5.next())
		    {
		    	id=rs5.getInt("ID");
            }
		    rs5.close();
		    pmt5.close();
		    
		    
		    String query51 = "select * from Client where ID='"+id+"'";
            PreparedStatement pmt51 = connection.prepareStatement(query51);
            ResultSet rs51 = pmt51.executeQuery();
		    while(rs51.next())
		    {
		    	client.setText(rs51.getString("Name"));
		    	name.setText(rs51.getString("Name"));
		    	address1.setText(rs51.getString("Address1"));
		    	address2.setText(rs51.getString("Address2"));
		    	address3.setText(rs51.getString("Address3"));
		    	country.setSelectedItem(rs51.getString("Country"));
		    	state.setSelectedItem(rs51.getString("State"));
		    	city.setSelectedItem(rs51.getString("City"));
		    	mobile.setText(rs51.getString("Mobile"));
		    	contact_person.setText(rs51.getString("Contact_person"));
            }
		    rs51.close();
		    pmt51.close();
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
	}

}
