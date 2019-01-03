
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	int hj=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{   
					Login frame = new Login();
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
	public Login() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-120;
        int w = (int)(width);
        int h = (int)(height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((w-450)/2, (h-300)/2, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 414, 240);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Calibri", Font.BOLD, 25));
		lblLogin.setBounds(170, 11, 96, 24);
		panel.add(lblLogin);
		
		JLabel lblUser = new JLabel("User Name:");
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUser.setBounds(51, 57, 110, 24);
		panel.add(lblUser);
		
		textField = new JTextField();
		textField.setBounds(197, 57, 150, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(61, 102, 100, 24);
		panel.add(lblPassword);
		
		textField_1 = new JPasswordField();
		textField_1.setColumns(10);
		textField_1.setBounds(197, 102, 150, 28);
		panel.add(textField_1);
		
		
		 
		final Connection connection1 = Databaseconnection.connection2();
		

		
		JButton btnLogin = new JButton("Login");
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name=null,pd=null;
				
		        
		       
				try
    			{
    				String query5 = "select * from Login";
                    PreparedStatement pmt5 = connection1.prepareStatement(query5);
                    ResultSet rs5 = pmt5.executeQuery();
        		    while(rs5.next())
        		    {
        		    	name = rs5.getString("username");                                            
                        pd = rs5.getString("password");
                        
        		    }
        		    
        		    
        		    
        		    rs5.close();
        		    pmt5.close();
        		   
        		   
        		    
    			}
    			
    			catch (SQLException ae) {
        		    ae.printStackTrace();
        		}
				
				
				
				
				if(textField.getText().equals("") || textField_1.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter details");
				}
				
				else if (textField.getText().equals(name) && textField_1.getText().equals(pd)) {
					Home c=new Home();
	        		c.show();
	                dispose();
				
				}
					
				
				else
				{
					JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
				}
				
				
				
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name=null,pd=null;
				
		        
		       
				try
    			{
    				String query5 = "select * from Login";
                    PreparedStatement pmt5 = connection1.prepareStatement(query5);
                    ResultSet rs5 = pmt5.executeQuery();
        		    while(rs5.next())
        		    {
        		    	name = rs5.getString("username");                                            
                        pd = rs5.getString("password");
                        
        		    }
        		    
        		    
        		    
        		    rs5.close();
        		    pmt5.close();
        		   
        		    
    			}
    			
    			catch (SQLException ae) {
        		    ae.printStackTrace();
        		}
				
				
				
				
				if(textField.getText().equals("") || textField_1.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter details");
				}
				else if (textField.getText().equals(name) && textField_1.getText().equals(pd)) {
					Home c=new Home();
	        		c.show();
	                dispose();
				
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
				}
				
				
				
			}
		});
		
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setBounds(156, 157, 89, 23);
		panel.add(btnLogin);
	}
}
