import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

public class Add_material_waste extends JInternalFrame {
	private TableModel model;
    private int id;
    private JComboBox product;
    private JTextField qty;
    private JTextField code;
    int c=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_material_waste frame = new Add_material_waste();
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
	public Add_material_waste() {
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"Cancel"); //$NON-NLS-1$
		getRootPane().getActionMap().put("Cancel", new AbstractAction() { //$NON-NLS-1$
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
        double height = screenSize.getHeight()-120;
        int w = (int)(width);
        int h = (int)(height);
        setClosable(true);
        setBounds((w-583)/2, (h-334)/2, 583, 334);
        getContentPane().setLayout(null);
        
        final Connection connection = Databaseconnection.connection2();
        
        JLabel lblWholesaleBill = new JLabel("Add Waste");
        lblWholesaleBill.setForeground(Color.BLACK);
        lblWholesaleBill.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWholesaleBill.setBounds(197, 19, 179, 38);
        getContentPane().add(lblWholesaleBill);
        
        final JLabel unit = new JLabel("");
        unit.setFont(new Font("Calibri", Font.BOLD, 25));
        unit.setBounds(323, 176, 131, 25);
        getContentPane().add(unit);
        
        
        JLabel lblProduct = new JLabel("Product");
        lblProduct.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblProduct.setBounds(27, 81, 80, 25);
        getContentPane().add(lblProduct);
        
        product = new JComboBox();
        product.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    // pull data from the database 
                    /*int c=0;
                    
                    String query = "select * from Product Where Product = '"+product.getSelectedItem().toString()+"'";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    String n = null,u=null;
                    while (rs.next())
                   {
                     u = rs.getString("Unit");
                     c++;
                   }
                    pmt.close();
                    rs.close();
                    if(c==0)
                    {
                        code.setText("");
                        code.requestFocus();
                        JOptionPane.showMessageDialog(null,"Invalid Product Code");
                    }
                    else
                    {
                        unit.setText(u);
                    }*/
                	unit.setText("");
                	if(c!=0)
                	{
                		
                	}
                	else
                	{
                		String query = "select * from Product Where Product = '"+product.getSelectedItem().toString()+"'";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        ResultSet rs = pmt.executeQuery();
                        String n = null,u=null;
                        while (rs.next())
                       {
                         unit.setText(rs.getString("Unit"));
                       }
                	}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        product.setModel(new DefaultComboBoxModel(new String[] {"Select"}));
        
        product.setFont(new Font("Tahoma", Font.PLAIN, 16));
        product.setBounds(148, 127, 368, 25);
        getContentPane().add(product);
        
        JLabel lblQuantity = new JLabel("Qty");
        lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblQuantity.setBounds(27, 176, 97, 25);
        getContentPane().add(lblQuantity);
        
        qty = new JTextField();
        qty.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                char c = arg0.getKeyChar();
                if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c=='.'))){
                    getToolkit().beep();
                    arg0.consume();
                }
            }
        });
        qty.setFont(new Font("Tahoma", Font.PLAIN, 16));
        qty.setColumns(10);
        qty.setBounds(148, 176, 150, 25);
        getContentPane().add(qty);
        
        final JDateChooser date = new JDateChooser();
        date.setDateFormatString("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date d = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s = dateFormat.format(cal.getTime());
        DateFormat dateFormat1 = new SimpleDateFormat("dd");
        String s1 = dateFormat1.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String s2 = dateFormat.format(cal.getTime());
        try
        {
            d = (Date) dateFormat.parse(s);
            date.setDate(d);
            d = (Date) dateFormat.parse(s2);
            
            //date.setMaxSelectableDate(d);
            date.setMinSelectableDate(d);
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        date.setBounds(420, 19, 125, 30);
        getContentPane().add(date);
        
        code = new JTextField();
        code.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    // pull data from the database 
                    c=0;
                    String query = "select * from Product Where Short_Code = '"+code.getText()+"'";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    
                    while (rs.next())
                   {
                    	product.setSelectedItem(rs.getString("Product"));
                    	unit.setText(rs.getString("Unit"));
                    	c++;
                   }
                    pmt.close();
                    rs.close();
                    
                    if(c==0)
                    {
                        code.setText("");
                        code.requestFocus();
                        JOptionPane.showMessageDialog(null,"Invalid Product Code");
                    }
                    
                    
                   
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
       
        code.setFont(new Font("Tahoma", Font.PLAIN, 16));
        code.setBounds(148, 81, 167, 25);
        getContentPane().add(code);
        
        JLabel gno = new JLabel();
        gno.setFont(new Font("Calibri", Font.PLAIN, 25));
        gno.setBounds(352, 81, 332, 25);
        getContentPane().add(gno);
        
        JButton button = new JButton("Submit");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               
                try
                {
                    if(qty.getText() == ""||product.getSelectedItem().toString().equals("SELECT"))
                    {
                       JOptionPane.showMessageDialog(null,"Please Fill Details");
                    }
                    else
                    {
                      
                       String query1="insert into Waste ( Date, Product, Qty) VALUES ('"+((JTextField)date.getDateEditor().getUiComponent()).getText()+"','"+product.getSelectedItem().toString()+"','"+qty.getText()+"')";
                       PreparedStatement pmt1 = connection.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
                       pmt1.executeUpdate();
                       pmt1.close();
                       
                      String query4 = "UPDATE Stock set Stock =Stock-'"+qty.getText()+"' where Product='"+product.getSelectedItem().toString()+"'";
                      PreparedStatement pmt4 = connection.prepareStatement(query4);
                      pmt4.executeUpdate();
                      pmt4.close();
                      
                      String query41 = "UPDATE Product set Stock =Stock-'"+qty.getText()+"' where Product='"+product.getSelectedItem().toString()+"'";
                      PreparedStatement pmt41 = connection.prepareStatement(query41);
                      pmt41.executeUpdate();
                      pmt41.close();
                     
                      Add_material_waste b = new Add_material_waste();
                       JDesktopPane desktopPane = getDesktopPane();
                       desktopPane.add(b);
                       b.show();
                       dispose();
                   }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBounds(219, 252, 150, 30);
        getContentPane().add(button);
        
        
        qty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               try
                {
                    if(qty.getText() == ""||product.getSelectedItem().toString().equals("SELECT"))
                    {
                       JOptionPane.showMessageDialog(null,"Please Fill Details");
                    }
                    else
                    {
                      
                       String query1="insert into Waste ( Date, Product, Qty) VALUES ('"+((JTextField)date.getDateEditor().getUiComponent()).getText()+"','"+product.getSelectedItem().toString()+"','"+qty.getText()+"')";
                       PreparedStatement pmt1 = connection.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
                       pmt1.executeUpdate();
                       pmt1.close();
                       
                      String query4 = "UPDATE Stock set Stock =Stock-'"+qty.getText()+"' where Product='"+product.getSelectedItem().toString()+"'";
                      PreparedStatement pmt4 = connection.prepareStatement(query4);
                      pmt4.executeUpdate();
                      pmt4.close();
                      
                      String query41 = "UPDATE Product set Stock =Stock-'"+qty.getText()+"' where Product='"+product.getSelectedItem().toString()+"'";
                      PreparedStatement pmt41 = connection.prepareStatement(query41);
                      pmt41.executeUpdate();
                      pmt41.close();
                     
                      Add_material_waste b = new Add_material_waste();
                       JDesktopPane desktopPane = getDesktopPane();
                       desktopPane.add(b);
                       b.show();
                       dispose();
                   }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
       
        try {
            // pull data from the database 
            String query = "select * from Product";
            PreparedStatement pmt = connection.prepareStatement(query);
            ResultSet rs = pmt.executeQuery();
            
            while (rs.next())
           {
             String n = rs.getString("Product");
             product.addItem(n);
           }
            pmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
