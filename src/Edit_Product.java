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

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;

public class Edit_Product extends JInternalFrame {
	private JTextField product;
	private JTextField code;
	private JTextField stock;
	private JTextField product_name;
	private JTextField category;
	String id=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit_Product frame = new Edit_Product();
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
	public Edit_Product() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"Cancel"); //$NON-NLS-1$
		getRootPane().getActionMap().put("Cancel", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are You Sure?", "Warning", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight() - 120;
		int w = (int) (width);
		int h = (int) (height);
		
		final Connection connection = Databaseconnection.connection2();   


		setBounds((w - 411) / 2, (h - 519) / 2, 411, 519);
		setClosable(true);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Product");
		label.setFont(new Font("Calibri", Font.BOLD, 25));
		label.setBounds(114, 11, 114, 24);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Category:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(10, 107, 120, 28);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Product:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_2.setBounds(10, 167, 110, 28);
		getContentPane().add(label_2);
		
		product = new JTextField();
		product.setFont(new Font("Tahoma", Font.PLAIN, 16));
		product.setColumns(10);
		product.setBounds(130, 167, 202, 28);
		getContentPane().add(product);
		
		JLabel label_3 = new JLabel("Short Code:");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_3.setBounds(10, 222, 110, 28);
		getContentPane().add(label_3);
		
		code = new JTextField();
		code.setEditable(false);
		code.setFont(new Font("Tahoma", Font.PLAIN, 16));
		code.setColumns(10);
		code.setBounds(130, 222, 202, 28);
		getContentPane().add(code);
		
		JLabel label_4 = new JLabel("Unit:");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_4.setBounds(10, 281, 120, 28);
		getContentPane().add(label_4);
		
		JComboBox unit = new JComboBox();
		unit.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		try
		{
			String query5 = "select * from Unit";
            PreparedStatement pmt5 = connection.prepareStatement(query5);
            ResultSet rs5 = pmt5.executeQuery();
		    while(rs5.next())
		    {
		    	unit.addItem(rs5.getString("Unit"));
            }
		    rs5.close();
		    pmt5.close();
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		unit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		unit.setBounds(130, 279, 202, 28);
		getContentPane().add(unit);
		
		JLabel label_5 = new JLabel("Stock:");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_5.setBounds(10, 335, 110, 28);
		getContentPane().add(label_5);
		
		stock = new JTextField();
		stock.setEditable(false);
		stock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		stock.setColumns(10);
		stock.setBounds(130, 335, 202, 28);
		getContentPane().add(stock);
		
		JLabel label_6 = new JLabel("Product:");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_6.setBounds(10, 59, 110, 28);
		getContentPane().add(label_6);
		
		product_name = new JTextField();
		product_name.setEditable(false);
		product_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		product_name.setColumns(10);
		product_name.setBounds(130, 59, 202, 28);
		getContentPane().add(product_name);
		
		category = new JTextField();
		category.setFont(new Font("Tahoma", Font.PLAIN, 16));
		category.setEditable(false);
		category.setColumns(10);
		category.setBounds(130, 113, 202, 28);
		getContentPane().add(category);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					if(product.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else 
                    {
                    	try
						{
							String query1="update Receive set Product='"+product.getText()+"' where Product='"+product_name.getText()+"'";
	                        PreparedStatement pmt1 = connection.prepareStatement(query1);
	                        pmt1.executeUpdate();
	                        pmt1.close();	
						}
						catch(Exception ae)
						{
							ae.printStackTrace();
						}
                    	
                    	try
						{
							String query1="update Returnable_Details set Product='"+product.getText()+"' where Product='"+product_name.getText()+"'";
	                        PreparedStatement pmt1 = connection.prepareStatement(query1);
	                        pmt1.executeUpdate();
	                        pmt1.close();	
						}
						catch(Exception ae)
						{
							ae.printStackTrace();
						}
                    	
                    	try
						{
							String query1="update Waste set Product='"+product.getText()+"' where Product='"+product_name.getText()+"'";
	                        PreparedStatement pmt1 = connection.prepareStatement(query1);
	                        pmt1.executeUpdate();
	                        pmt1.close();	
						}
						catch(Exception ae)
						{
							ae.printStackTrace();
						}
                    	
                    	try
						{
							String query1="update BOM_Detail set Product='"+product.getText()+"' where Product='"+product_name.getText()+"'";
	                        PreparedStatement pmt1 = connection.prepareStatement(query1);
	                        pmt1.executeUpdate();
	                        pmt1.close();	
						}
						catch(Exception ae)
						{
							ae.printStackTrace();
						}
                    	
                    	try
						{
							String query1="update PO_Detail set Product='"+product.getText()+"' where Product='"+product_name.getText()+"'";
	                        PreparedStatement pmt1 = connection.prepareStatement(query1);
	                        pmt1.executeUpdate();
	                        pmt1.close();	
						}
						catch(Exception ae)
						{
							ae.printStackTrace();
						}
                    	
                    	try
						{
							String query1="update Purchase_detail set Product='"+product.getText()+"' where Product='"+product_name.getText()+"'";
	                        PreparedStatement pmt1 = connection.prepareStatement(query1);
	                        pmt1.executeUpdate();
	                        pmt1.close();	
						}
						catch(Exception ae)
						{
							ae.printStackTrace();
						}
                    	
                    	try
						{
							String query1="update Material_Issue_Details set Product='"+product.getText()+"' where Product='"+product_name.getText()+"'";
	                        PreparedStatement pmt1 = connection.prepareStatement(query1);
	                        pmt1.executeUpdate();
	                        pmt1.close();	
						}
						catch(Exception ae)
						{
							ae.printStackTrace();
						}
                    	
                    	
                    	try
						{
							String query1="update Staff_issue_detail set Product='"+product.getText()+"' where Product='"+product_name.getText()+"'";
	                        PreparedStatement pmt1 = connection.prepareStatement(query1);
	                        pmt1.executeUpdate();
	                        pmt1.close();	
						}
						catch(Exception ae)
						{
							ae.printStackTrace();
						}
                    	String query="update Product set Product='"+product.getText()+"',Unit='"+unit.getSelectedItem().toString()+"' where ID='"+id+"'";
		            	PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                      
                        
                        View_product b = new View_product();
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
		button.setBounds(123, 388, 89, 28);
		getContentPane().add(button);
		
		
		try
		{
			String query5 = "select * from Product_id";
            PreparedStatement pmt5 = connection.prepareStatement(query5);
            ResultSet rs5 = pmt5.executeQuery();
		    while(rs5.next())
		    {
		    	id = rs5.getString("ID");
		    }
		    rs5.close();
		    pmt5.close();
		    
		    

		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		
		try
			{	
		  
        
        String query = "select * from Product where ID='"+id+"'";
        java.sql.PreparedStatement pmt = connection.prepareStatement(query);
        ResultSet rs = pmt.executeQuery();
        while(rs.next())
        {
        	product_name.setText(rs.getString("Product"));
        	category.setText(rs.getString("Category"));
        	product.setText(rs.getString("Product"));
        	code.setText(rs.getString("Short_Code"));
        	unit.setSelectedItem(rs.getString("Unit"));
        	stock.setText(rs.getString("Stock"));
        	
        }
        rs.close();
        pmt.close();
        
       
      
  
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
	

	}

}
