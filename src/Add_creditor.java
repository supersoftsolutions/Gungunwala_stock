import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class Add_creditor extends JInternalFrame {
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField address3;
	private JTextField contact_person;
	private JTextField mobile;
	private JTextField balance;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_creditor frame = new Add_creditor();
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
	public Add_creditor() {
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
		setBounds((w-450)/2, (h-551)/2, 450, 551);
		getContentPane().setLayout(null);
		
		final Connection connection = Databaseconnection.connection2();


		
		JLabel label = new JLabel("Name:*");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(10, 69, 87, 25);
		getContentPane().add(label);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(135, 73, 269, 28);
		getContentPane().add(name);
		
		JLabel label_1 = new JLabel("Address1:*");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(10, 105, 87, 25);
		getContentPane().add(label_1);
		
		address1 = new JTextField();
		address1.setColumns(10);
		address1.setBounds(135, 109, 269, 28);
		getContentPane().add(address1);
		
		JLabel label_2 = new JLabel("Address2:*");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_2.setBounds(10, 141, 87, 25);
		getContentPane().add(label_2);
		
		address2 = new JTextField();
		address2.setColumns(10);
		address2.setBounds(135, 145, 269, 28);
		getContentPane().add(address2);
		
		JLabel label_3 = new JLabel("Address3:");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_3.setBounds(10, 177, 87, 25);
		getContentPane().add(label_3);
		
		address3 = new JTextField();
		address3.setColumns(10);
		address3.setBounds(135, 181, 269, 28);
		getContentPane().add(address3);
		
		JLabel label_4 = new JLabel("Country:*");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_4.setBounds(10, 214, 87, 20);
		getContentPane().add(label_4);
		
		JComboBox country = new JComboBox();
		country.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		country.setFont(new Font("Tahoma", Font.PLAIN, 16));
		country.setBounds(135, 213, 269, 25);
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
		label_5.setBounds(10, 245, 87, 20);
		getContentPane().add(label_5);
		
		JComboBox state = new JComboBox();
		state.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		state.setFont(new Font("Tahoma", Font.PLAIN, 16));
		state.setBounds(135, 245, 269, 25);
		getContentPane().add(state);
		
		JLabel label_6 = new JLabel("City:*");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_6.setBounds(10, 276, 87, 20);
		getContentPane().add(label_6);
		
		JComboBox city = new JComboBox();
		city.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		city.setFont(new Font("Tahoma", Font.PLAIN, 16));
		city.setBounds(135, 276, 269, 25);
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
		lblContactPerson.setBounds(10, 307, 133, 25);
		getContentPane().add(lblContactPerson);
		
		contact_person = new JTextField();
		contact_person.setColumns(10);
		contact_person.setBounds(134, 311, 269, 28);
		getContentPane().add(contact_person);
		
		JLabel lblMobile = new JLabel("Mobile:*");
		lblMobile.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMobile.setBounds(10, 343, 87, 35);
		getContentPane().add(lblMobile);
		
		mobile = new JTextField();
		mobile.setColumns(10);
		mobile.setBounds(135, 350, 269, 28);
		getContentPane().add(mobile);
		
		JLabel lblOpeningBalance = new JLabel("Opening Balance:");
		lblOpeningBalance.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblOpeningBalance.setBounds(10, 396, 133, 25);
		getContentPane().add(lblOpeningBalance);
		
		balance = new JTextField();
		balance.setColumns(10);
		balance.setBounds(135, 396, 269, 28);
		getContentPane().add(balance);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					if(name.getText().equals("")||address1.getText().equals("")||address2.getText().equals("")||city.getSelectedItem().toString().equals("SELECT")||mobile.getText().equals("")||contact_person.getText().equals("")||balance.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "please Fill Details");
					}
					else
					{
						String query="insert into Creditor (Name,Address1,Address2,Address3,Country,State,City,Mobile,Contact_person,Balance) VALUES ('"+name.getText()+"','"+address1.getText()+"','"+address2.getText()+"','"+address3.getText()+"','"+country.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+city.getSelectedItem().toString()+"','"+mobile.getText()+"','"+contact_person.getText()+"','"+balance.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        String query1="insert into Crediter_OpeningBalance (Name, Amount) VALUES ('"+name.getText()+"','"+balance.getText()+"')";
                        PreparedStatement pmt1 = connection.prepareStatement(query1);
                        pmt1.executeUpdate();
                        pmt1.close();
                        
                        Add_creditor b = new Add_creditor();
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
		button.setBounds(156, 451, 89, 28);
		getContentPane().add(button);
		
		JLabel label_10 = new JLabel("Creditor");
		label_10.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_10.setBounds(132, 21, 209, 25);
		getContentPane().add(label_10);
		
		
		balance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					if(name.getText().equals("")||address1.getText().equals("")||address2.getText().equals("")||city.getSelectedItem().toString().equals("SELECT")||mobile.getText().equals("")||contact_person.getText().equals("")||balance.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "please Fill Details");
					}
					else
					{
						String query="insert into Creditor (Name,Address1,Address2,Address3,Country,State,City,Mobile,Contact_person,Balance) VALUES ('"+name.getText()+"','"+address1.getText()+"','"+address2.getText()+"','"+address3.getText()+"','"+country.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+city.getSelectedItem().toString()+"','"+mobile.getText()+"','"+mobile.getText()+"','"+contact_person.getText()+"','"+balance.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        String query1="insert into Crediter_OpeningBalance (Name, Amount) VALUES ('"+name.getText()+"','"+balance.getText()+"')";
                        PreparedStatement pmt1 = connection.prepareStatement(query1);
                        pmt1.executeUpdate();
                        pmt1.close();
                        
                        Add_creditor b = new Add_creditor();
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

	}

}
