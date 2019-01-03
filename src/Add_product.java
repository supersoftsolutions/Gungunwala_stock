import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;


public class Add_product extends JInternalFrame {
	private JTextField product;
	private JTextField code;
	private JTextField stock;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_product frame = new Add_product();
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
	public Add_product() {
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
		
		JLabel lblProduct_2 = new JLabel("Product");
		lblProduct_2.setFont(new Font("Calibri", Font.BOLD, 25));
		lblProduct_2.setBounds(140, 26, 114, 24);
		getContentPane().add(lblProduct_2);
		
		JLabel lblProduct = new JLabel("Category:");
		lblProduct.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProduct.setBounds(36, 76, 120, 28);
		getContentPane().add(lblProduct);
		
		JComboBox category = new JComboBox();
		category.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		
		try
		{
			String query5 = "select * from Category";
            PreparedStatement pmt5 = connection.prepareStatement(query5);
            ResultSet rs5 = pmt5.executeQuery();
		    while(rs5.next())
		    {
		    	category.addItem(rs5.getString("Name"));
            }
		    rs5.close();
		    pmt5.close();
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		category.setFont(new Font("Tahoma", Font.PLAIN, 16));
		category.setBounds(156, 74, 202, 28);
		getContentPane().add(category);
		
		JLabel lblProduct_1 = new JLabel("Product:");
		lblProduct_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProduct_1.setBounds(36, 129, 110, 28);
		getContentPane().add(lblProduct_1);
		
		product = new JTextField();
		product.setFont(new Font("Tahoma", Font.PLAIN, 16));
		product.setColumns(10);
		product.setBounds(156, 129, 202, 28);
		getContentPane().add(product);
		
		code = new JTextField();
		code.setFont(new Font("Tahoma", Font.PLAIN, 16));
		code.setColumns(10);
		code.setBounds(156, 179, 202, 28);
		getContentPane().add(code);
		
		JLabel lblShortCode = new JLabel("Short Code:");
		lblShortCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblShortCode.setBounds(36, 179, 110, 28);
		getContentPane().add(lblShortCode);
		
		JLabel lblUnit = new JLabel("Unit:");
		lblUnit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUnit.setBounds(36, 232, 120, 28);
		getContentPane().add(lblUnit);
		
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
		unit.setBounds(156, 230, 202, 28);
		getContentPane().add(unit);
		
		stock = new JTextField();
		stock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		stock.setColumns(10);
		stock.setBounds(156, 281, 202, 28);
		getContentPane().add(stock);
		
		JLabel lblStock = new JLabel("Stock:");
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStock.setBounds(36, 281, 110, 28);
		getContentPane().add(lblStock);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try
	                {
	                    if(product.getText() == ""||category.getSelectedItem().toString().equals("SELECT")||unit.getSelectedItem().toString().equals("SELECT")||stock.getText().equals(""))
	                    {
	                        JOptionPane.showMessageDialog(null,"Please Enter Product.");
	                    }
	                   
	                    else
	                    {
	                        int c=0;
	                        String query = "select * from Product where Short_code='"+code.getText()+"'";
	                        PreparedStatement pmt = connection.prepareStatement(query);
	                        ResultSet rs = pmt.executeQuery();
	                        while(rs.next())
	                        {
	                         c++;   
	                        }
	                        rs.close();
	                        pmt.close();
	                        
	                        if(c==0)
	                        {
	                            int c1=0;
	                            String query4 = "select * from Product where Product='"+product.getText()+"'";
	                            PreparedStatement pmt4 = connection.prepareStatement(query4);
	                            ResultSet rs4 = pmt4.executeQuery();
	                            while(rs4.next())
	                            {
	                             c1++;   
	                            }
	                            rs4.close();
	                            pmt4.close();
	                            if(c1==0){
	                                String query5="insert into Opening_Stock (Product, Stock) VALUES ('"+product.getText()+"','"+stock.getText()+"')";
	                                PreparedStatement pmt5 = connection.prepareStatement(query5);
	                                pmt5.executeUpdate();
	                                pmt5.close();
	                                
	                                String query3="insert into Stock (Product, Stock) VALUES ('"+product.getText()+"','"+stock.getText()+"')";
	                                PreparedStatement pmt3 = connection.prepareStatement(query3);
	                                pmt3.executeUpdate();
	                                pmt3.close();
	                                
	                                String query1="insert into Product (Category,Product, Short_Code, Unit,Stock) VALUES ('"+category.getSelectedItem().toString()+"','"+product.getText()+"','"+code.getText()+"','"+unit.getSelectedItem().toString()+"','"+stock.getText()+"')";
	                                PreparedStatement pmt1 = connection.prepareStatement(query1);
	                                pmt1.executeUpdate();
	                                pmt1.close();
	                            
	                            category.setSelectedIndex(0);
	                            product.setText("");
	                            code.setText("");
	                            stock.setText("0");
	                            }
	                            else {
	                                JOptionPane.showMessageDialog(null,"Product already Exist");
	                            }
	                        }
	                        else
	                        {
	                            JOptionPane.showMessageDialog(null,"Short code already Exist");
	                        }
	                    }
	                    
	                }
	               catch (Exception ae) {
	                   ae.printStackTrace();
	               }
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(140, 339, 89, 28);
		getContentPane().add(button);
		
		
		
	}
}
