import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

public class Add_purchase extends JInternalFrame {
	private JTextField purchase;
	private JTextField code;
	private JTextField qty;
	private JTextField rate;
	private JTable table;
	private static JTextField total;
    private String id;
    private TableModel model;

    private JComboBox product_name;
    public static JCheckBox vat18;
    public static JCheckBox vat28;
    public static JCheckBox vat5;
    int c=0;
	int flag=0;




	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_purchase frame = new Add_purchase();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
private static void gettottal(){
        
        final Connection connection = Databaseconnection.connection2();
        
      
        try
        {
            int total1 =0;
            String query3="select * from Purchase_temp";
            PreparedStatement pmt3 = connection.prepareStatement(query3);
            ResultSet rs3 = pmt3.executeQuery();
            while(rs3.next())
            {
                total1 += rs3.getInt("Total");
            }
            rs3.close();
            pmt3.close(); 
            
            float tax=0;
            
            if(vat18.isSelected()){
                tax += (total1*18.0/100);
            }
            if(vat28.isSelected()){
                tax += (total1*28.0/100);
            }
            if(vat5.isSelected()){
                tax += (total1*5/100);
            }
         
           
            
            total.setText(Float.toString((tax+total1)));
            
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
	public Add_purchase() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
	        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
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
	        setBounds((w-690)/2, (h-600)/2, 690, 548);
			getContentPane().setLayout(null);
			
			final Connection connection = Databaseconnection.connection2();

			
			JLabel label = new JLabel("Purchase");
			label.setFont(new Font("Tahoma", Font.BOLD, 20));
			label.setBounds(278, 17, 102, 25);
			getContentPane().add(label);
			
			purchase = new JTextField();
			purchase.setText("0");
			purchase.setColumns(10);
			purchase.setBounds(518, 74, 146, 28);
			getContentPane().add(purchase);
			
			JDateChooser dateChooser = new JDateChooser();
			dateChooser.setDateFormatString("yyyy-MM-dd");
			dateChooser.setBounds(480, 17, 146, 28);
			getContentPane().add(dateChooser);
			
			JLabel label_1 = new JLabel("Crediter Name:");
			label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
			label_1.setBounds(23, 76, 128, 20);
			getContentPane().add(label_1);
			
			JComboBox creditor = new JComboBox();
			creditor.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
			try
			{
				String query5 = "select * from Creditor";
	            PreparedStatement pmt5 = connection.prepareStatement(query5);
	            ResultSet rs5 = pmt5.executeQuery();
			    while(rs5.next())
			    {
			    	creditor.addItem(rs5.getString("Name"));
	            }
			    rs5.close();
			    pmt5.close();
			    
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			creditor.setFont(new Font("Tahoma", Font.PLAIN, 16));
			creditor.setBounds(161, 72, 217, 28);
			getContentPane().add(creditor);
			
			JLabel lblProduct = new JLabel("Product:");
			lblProduct.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblProduct.setBounds(23, 119, 79, 27);
			getContentPane().add(lblProduct);
			
			JLabel unit = new JLabel("");
			unit.setBounds(229, 167, 102, 25);
			getContentPane().add(unit);
			
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
		                    	product_name.setSelectedItem(rs.getString("Product"));
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
		       
			code.setColumns(10);
			code.setBounds(111, 120, 94, 28);
			getContentPane().add(code);
			
			 product_name = new JComboBox();
			 product_name.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent arg0) {
		                try {
		                  
		                	unit.setText("");
		                	if(c!=0)
		                	{
		                		
		                	}
		                	else
		                	{
		                		String query = "select * from Product Where Product = '"+product_name.getSelectedItem().toString()+"'";
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
			product_name.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
			product_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
			product_name.setBounds(229, 119, 350, 28);
			getContentPane().add(product_name);
			
			JLabel lblQty = new JLabel("Qty:");
			lblQty.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblQty.setBounds(23, 165, 79, 28);
			getContentPane().add(lblQty);
			
			qty = new JTextField();
			qty.setColumns(10);
			qty.setBounds(111, 166, 94, 28);
			getContentPane().add(qty);
			
			
			
			rate = new JTextField();
			rate.setColumns(10);
			rate.setBounds(451, 166, 94, 28);
			getContentPane().add(rate);
			
			JLabel lblRate = new JLabel("Rate:");
			lblRate.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblRate.setBounds(363, 165, 79, 27);
			getContentPane().add(lblRate);
			
			total = new JTextField();
			total.setText("0");
			total.setEditable(false);
			total.setColumns(10);
			total.setBounds(468, 455, 116, 28);
			getContentPane().add(total);
			
			 vat18 = new JCheckBox("18%");
			vat18.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent arg0) {
	                 gettottal();
	             }
	         });
			vat18.setBounds(23, 458, 49, 23);
			getContentPane().add(vat18);
			
			 vat28 = new JCheckBox("28%");
			vat28.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent arg0) {
	                 gettottal();
	             }
	         });
			vat28.setBounds(74, 458, 56, 23);
			getContentPane().add(vat28);
			
			 vat5 = new JCheckBox("5%");
			vat5.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent arg0) {
	                 gettottal();
	             }
	         });
			vat5.setBounds(131, 458, 49, 23);
			getContentPane().add(vat5);
			setClosable(true);
			
			JLabel lblPoNo = new JLabel("PO No.:");
			lblPoNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblPoNo.setBounds(384, 74, 128, 20);
			getContentPane().add(lblPoNo);
			
			
		
			
			JButton button = new JButton("Add");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					 try
	                    {
	                        if(product_name.getSelectedItem().toString()=="Select")
	                        {
	                            JOptionPane.showMessageDialog(null,"Enter Product");
	                            code.requestFocus();
	                        }
	                        else if (qty.getText().isEmpty()) {
	                            JOptionPane.showMessageDialog(null,"Enter Quantity");
	                            qty.requestFocus();
	                        }
	                        else if (rate.getText().isEmpty()) {
	                            JOptionPane.showMessageDialog(null,"Enter Rate");
	                            rate.requestFocus();
	                        }
	                        else
	                        {
	                            if(flag==0)
	                            {
	                                int c=0;
	                                String query2="select * from Purchase_temp";
	                                PreparedStatement pmt2 = connection.prepareStatement(query2);
	                                ResultSet rs2 = pmt2.executeQuery();
	                                while(rs2.next())
	                                {
	                                   c++;
	                                }
	                                rs2.close();
	                                pmt2.close();
	                                
	                                int i=0;
	                                if(c==0)
	                                {
	                                    i=1;
	                                }
	                                else
	                                {
	                                    int id=0;
	                                    String query3="select * from Purchase_temp";
	                                    PreparedStatement pmt3 = connection.prepareStatement(query3);
	                                    ResultSet rs3 = pmt3.executeQuery();
	                                    while(rs3.next())
	                                    {
	                                       id = rs3.getInt("ID");
	                                    }
	                                    rs3.close();
	                                    pmt3.close(); 
	                                    i = id +1;
	                                }
	                                
	                                float total99 = Float.parseFloat(rate.getText())*Float.parseFloat(qty.getText());
	                                String query1="insert into Purchase_temp (ID,Product, Qty, Rate, Total) VALUES ('"+i+"','"+product_name.getSelectedItem().toString()+"',"+qty.getText()+",'"+rate.getText()+"','"+total99+"')";
	                                PreparedStatement pmt1 = connection.prepareStatement(query1);
	                                pmt1.executeUpdate();
	                                pmt1.close();
	                                
	                               gettottal();
	                                
	                                String query = "select * from Purchase_temp";
	                                PreparedStatement pmt = connection.prepareStatement(query);
	                                ResultSet rs = pmt.executeQuery();
	                                table.setModel(DbUtils.resultSetToTableModel(rs));
	                                pmt.close();
	                                rs.close();
	                            }
	                            else
	                            {
	                               
	                                float total99 = Float.parseFloat(rate.getText())*Float.parseFloat(qty.getText());
	                                String query1="update Purchase_temp set Qty='"+qty.getText()+"', Rate='"+rate.getText()+"',Total='"+total99+"' where ID = '"+id+"'";
	                                PreparedStatement pmt1 = connection.prepareStatement(query1);
	                                pmt1.executeUpdate();
	                                pmt1.close();
	                                
	                                gettottal();
	                                
	                                String query = "select * from Purchase_temp";
	                                PreparedStatement pmt = connection.prepareStatement(query);
	                                ResultSet rs = pmt.executeQuery();
	                                table.setModel(DbUtils.resultSetToTableModel(rs));
	                                pmt.close();
	                                rs.close();
	                            }
	                            
	                            code.setEnabled(true);
	                            code.setText("");
	                            product_name.setSelectedIndex(0);
	                            unit.setText("");
	                            qty.setText("");
	                            code.requestFocus();
	                            rate.setText("");
	                        }
	                        
	                    }
	                   catch (Exception e) {
	                       e.printStackTrace();
	                   }
					
				}
			});
			button.setFont(new Font("Tahoma", Font.PLAIN, 16));
			button.setBounds(130, 222, 89, 23);
			getContentPane().add(button);
			
			JButton button_1 = new JButton("Delete");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try
					{
                        String querydetais="delete from Purchase_temp where ID='"+id+"'";
                        PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                        pmtdetails.executeUpdate();
                        pmtdetails.close();
                        
                        gettottal();
                        
                        String querytable = "select * from Purchase_temp";
                        PreparedStatement pmttable = connection.prepareStatement(querytable);
                        ResultSet rstable = pmttable.executeQuery();
                        table.setModel(DbUtils.resultSetToTableModel(rstable));
                        pmttable.close();
                        rstable.close();
                        
                        code.setEnabled(true);
                        code.setText("");
                        product_name.setSelectedIndex(0);
                        rate.setText("");
                        qty.setText("");
                        code.requestFocus();
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    } 
				}
			});
			button_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
			button_1.setBounds(423, 222, 89, 23);
			getContentPane().add(button_1);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 256, 654, 178);
			getContentPane().add(scrollPane);
			
			TableModel model = null;
			table = new JTable(model){

				private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int rowIndex, int colIndex) {
		            return false; //Disallow the editing of any cell
		            }
		            };

			scrollPane.setViewportView(table);
			table.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent arg0) {
	            	int row =  table.convertRowIndexToModel(table.getSelectedRow());
		            int col = table.getColumnModel().getColumnIndex("ID");

		            id =table.getModel().getValueAt(row, col).toString();
		            
		            try
		            {
		            	String query13 = "select * from Purchase_temp where ID='"+id+"'";
						  PreparedStatement pmt13 =  connection.prepareStatement(query13);
						  ResultSet rs13 = pmt13.executeQuery();
						  while(rs13.next())
						  {
							  product_name.setSelectedItem(rs13.getString("Product")); 
		                       qty.setText(rs13.getString("Qty"));
		                       rate.setText(rs13.getString("Rate"));
						}
						  rs13.close();
						  flag=1;
		            }
		            catch(Exception ae)
		            {
		            	ae.printStackTrace();
		            }
	            }
	        });

			try
			{
				 String query11="delete from Purchase_temp";
	             PreparedStatement pmt11 = connection.prepareStatement(query11);
	             pmt11.executeUpdate();
	             pmt11.close();

	             String query12 = "select * from Purchase_temp";
	             PreparedStatement pmt12 = connection.prepareStatement(query12);
	             ResultSet rs12 = pmt12.executeQuery();
	             table.setModel(DbUtils.resultSetToTableModel(rs12));
	             pmt12.close();
	             rs12.close();
			}
			catch(Exception ae)
			{
				ae.printStackTrace();
			}
			
			JButton button_2 = new JButton("Submit");
			button_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					   ArrayList<String> product = new ArrayList<String>();
		                ArrayList<Float> qty = new ArrayList<Float>();
		                ArrayList<Float> rate1 = new ArrayList<Float>();
		                ArrayList<Float> total1 = new ArrayList<Float>();
		                try
		                {
		                    if(purchase.getText() == "")
		                    {
		                       JOptionPane.showMessageDialog(null,"Enter P.O.No");
		                    }
		                    else if(creditor.getSelectedItem().toString().equals("select"))
		                    {
		                       JOptionPane.showMessageDialog(null,"Select Crediter Name");
		                    }
		                   else
		                   {
		                       String query0 = "select * from Purchase_temp";
		                       PreparedStatement pmt0 = connection.prepareStatement(query0);
		                       ResultSet rs0 = pmt0.executeQuery();
		                       while(rs0.next())
		                       {
		                           product.add(rs0.getString("Product"));
		                           qty.add(rs0.getFloat("Qty"));
		                           rate1.add(rs0.getFloat("Rate"));
		                           total1.add(rs0.getFloat("Total"));
		                       }
		                       pmt0.close();
		                       rs0.close();
		                       String v18=null;
		                       if(vat18.isSelected()){
		                    	   v18="18";
		                       }
		                       else
		                       {
		                    	   v18="0";
		                       }
		                       
		                       String v28=null;
		                       if(vat28.isSelected()){
		                    	   v28="28";
		                       }
		                       else
		                       {
		                    	   v28="0";
		                       }
		                       String v5=null;
		                       if(vat5.isSelected()){
		                    	   v5="5";
		                       }
		                       else
		                       {
		                    	   v5="0";
		                       }
		                       
		                       String query1="insert into Purchase (Creditor, Po_no, Date, Total,tax18,tax28,tax5) VALUES ('"+creditor.getSelectedItem().toString()+"','"+purchase.getText()+"','"+((JTextField)dateChooser.getDateEditor().getUiComponent()).getText()+"','"+total.getText()+"','"+v18+"','"+v28+"','"+v5+"')";
		                       PreparedStatement pmt1 = connection.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
		                       pmt1.executeUpdate();
		                       pmt1.close();
		                       
		                     
		                       
		                       String query6 = "select * from Creditor where Name='"+creditor.getSelectedItem().toString()+"'";
		                       PreparedStatement pmt6 = connection.prepareStatement(query6);
		                       ResultSet rs6 = pmt6.executeQuery();
		                       float t3 = 0;
		                       while(rs6.next())
		                       {
		                            t3 = rs6.getFloat("Balance");
		                       }
		                       pmt6.close();
		                       rs6.close();
		                       
		                       double t5 = t3 + Float.parseFloat(total.getText());
		                      
		                       String query5 = "UPDATE Creditor set Balance ="+t5+" where Name='"+creditor.getSelectedItem().toString()+"'";
		                       PreparedStatement pmt5 = connection.prepareStatement(query5);
		                       pmt5.executeUpdate();
		                       pmt5.close();
		                       int id=0;
		                       
		                       ResultSet rsid = pmt1.getGeneratedKeys();
		                       id=rsid.getInt(1);
		                       rsid.close();
		                       
		                       for(int i=0; i<product.size();i++)
		                       {
		                           String querydetais="insert into Purchase_detail (S_id, Product, Qty, Rate, Total) VALUES ('"+id+"','"+product.get(i)+"',"+qty.get(i)+",'"+rate1.get(i)+"','"+total1.get(i)+"')";
		                           PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
		                           pmtdetails.executeUpdate();
		                           pmtdetails.close();
	                              
		                           String query4 = "UPDATE Stock set Stock =Stock+"+qty.get(i)+" where Product='"+product.get(i)+"'";
	                               PreparedStatement pmt4 = connection.prepareStatement(query4);
	                               pmt4.executeUpdate();
	                               pmt4.close();
	                               
	                               String query41 = "UPDATE Product set Stock =Stock+"+qty.get(i)+" where Product='"+product.get(i)+"'";
	                               PreparedStatement pmt41 = connection.prepareStatement(query41);
	                               pmt41.executeUpdate();
	                               pmt41.close();
		                       }
		                       
		                       String querydetais="delete from Purchase_temp";
		                       PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
		                       pmtdetails.executeUpdate();
		                       pmtdetails.close();
		                       
		                       String querytable = "select * from Purchase_temp";
		                       PreparedStatement pmttable = connection.prepareStatement(querytable);
		                       ResultSet rstable = pmttable.executeQuery();
		                       table.setModel(DbUtils.resultSetToTableModel(rstable));
		                       pmttable.close();
		                       rstable.close();

		                      
		                       vat18.setSelected(false);
		                       vat28.setSelected(false);
		                       vat5.setSelected(false);
		                       code.setText("");
		                       product_name.setSelectedIndex(0);
		                       purchase.setText("");
		                       creditor.setSelectedIndex(0);
		                       rate.setText("");
		                       total.setText("0");
		                       ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();		                   }
		                }
		                    catch(Exception ae)
		                    {
		                    	ae.printStackTrace();
		                    }
				}
			});
			button_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
			button_2.setBounds(260, 453, 89, 28);
			getContentPane().add(button_2);
			
		
			 try {
		            // pull data from the database 
		            String query = "select * from Product";
		            PreparedStatement pmt = connection.prepareStatement(query);
		            ResultSet rs = pmt.executeQuery();
		            
		            while (rs.next())
		           {
		             String n = rs.getString("Product");
		             product_name.addItem(n);
		           }
		            pmt.close();
		            rs.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			 
				try
				{
					ArrayList<Integer> id = new ArrayList<Integer>();

					String query1 = "select * from Purchase";
		            java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
		            ResultSet rs1 = pmt1.executeQuery();
		            while(rs1.next())
		            {
		            	id.add(rs1.getInt("Po_no"));
		            }
		            rs1.close();
		            pmt1.close();

		            int j1=1;

		            for(int j = 0;j<1;)
		            {
		                if(id.contains(j1))
		                {
		                	j1++;
		                }
		                else
		                {
		                	purchase.setText(Integer.toString(j1));
		                	break;
		                }
		            }
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
				
				 try{
		                String querydetais="delete from Purchase_temp";
		                PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
		                pmtdetails.executeUpdate();
		                pmtdetails.close();
		                
		                String querytable = "select * from Purchase_temp";
		                PreparedStatement pmttable = connection.prepareStatement(querytable);
		                ResultSet rstable = pmttable.executeQuery();
		                table.setModel(DbUtils.resultSetToTableModel(rstable));
		                pmttable.close();
		                rstable.close();
		            }catch(Exception e)
		            {
		                e.printStackTrace();
		            }
		        
				
	}
}
