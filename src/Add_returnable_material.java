import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

public class Add_returnable_material extends JInternalFrame {
	private JTextField client_name;
	private JTextField sleep_no;
	private JTextField name;
	private JTextField type;
	private JTextField code;
	private JTextField qty;
	private JTable table;
    private String id;
	int flag=0;
    int c=0;
    private JComboBox product;
    private JTextField amount;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_returnable_material frame = new Add_returnable_material();
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
	public Add_returnable_material() {
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
			double height = screenSize.getHeight() - 120;
			int w = (int) (width);
			int h = (int) (height);
			setBounds((w - 690) / 2, (h - 592) / 2, 690, 592);
			setClosable(true);
			getContentPane().setLayout(null);
			
			final Connection connection = Databaseconnection.connection2();
			
			JLabel lblAmount = new JLabel("Amount:");
            lblAmount.setFont(new Font("Tahoma", Font.BOLD, 16));
            lblAmount.setBounds(20, 512, 79, 28);
            getContentPane().add(lblAmount);
            
            amount = new JTextField();
            amount.setText("0");
            amount.setColumns(10);
            amount.setBounds(108, 513, 94, 28);
            getContentPane().add(amount);

			
			JLabel lblMaterialIssue = new JLabel("Returnable Material");
			lblMaterialIssue.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblMaterialIssue.setBounds(252, 21, 208, 25);
			getContentPane().add(lblMaterialIssue);
			
			JDateChooser dateChooser = new JDateChooser();
			dateChooser.setDateFormatString("yyyy-MM-dd");
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
		            dateChooser.setDate(d);
		            d = (Date) dateFormat.parse(s2);
		            
		            //date.setMaxSelectableDate(d);
		            dateChooser.setMinSelectableDate(d);
		        }
		        catch (ParseException e1)
		        {
		            e1.printStackTrace();
		        }
			dateChooser.setDateFormatString("yyyy-MM-dd");
			dateChooser.setBounds(470, 21, 146, 28);
			getContentPane().add(dateChooser);
			
			JLabel lblClientName = new JLabel("Client Name:");
			lblClientName.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblClientName.setBounds(20, 60, 128, 28);
			getContentPane().add(lblClientName);
			
			client_name = new JTextField();
			client_name.setColumns(10);
			client_name.setBounds(133, 60, 424, 28);
			getContentPane().add(client_name);
			
			sleep_no = new JTextField();
			sleep_no.setText("0");
			sleep_no.setColumns(10);
			sleep_no.setBounds(97, 23, 128, 28);
			getContentPane().add(sleep_no);
			
			name = new JTextField();
			name.setColumns(10);
			name.setBounds(133, 99, 424, 28);
			getContentPane().add(name);
			
			JLabel lblPersonName = new JLabel("Person Name:");
			lblPersonName.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblPersonName.setBounds(20, 99, 128, 28);
			getContentPane().add(lblPersonName);
			
			type = new JTextField();
			type.setColumns(10);
			type.setBounds(133, 138, 424, 28);
			getContentPane().add(type);
			
			JLabel lblType = new JLabel("Type:");
			lblType.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblType.setBounds(20, 138, 128, 28);
			getContentPane().add(lblType);
			
			JLabel label = new JLabel("Product:");
			label.setFont(new Font("Tahoma", Font.BOLD, 16));
			label.setBounds(20, 177, 79, 27);
			getContentPane().add(label);
			
			JLabel unit = new JLabel("");
			unit.setBounds(226, 217, 102, 25);
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
		       
			code.setColumns(10);
			code.setBounds(108, 178, 94, 28);
			getContentPane().add(code);
			
			 product = new JComboBox();
			 product.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent arg0) {
		                try {
		                  
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
			product.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
			product.setFont(new Font("Tahoma", Font.PLAIN, 16));
			product.setBounds(226, 177, 350, 28);
			getContentPane().add(product);
			
			JLabel label_1 = new JLabel("Qty:");
			label_1.setFont(new Font("Tahoma", Font.BOLD, 16));
			label_1.setBounds(20, 215, 79, 28);
			getContentPane().add(label_1);
			
			qty = new JTextField();
			qty.setColumns(10);
			qty.setBounds(108, 216, 94, 28);
			getContentPane().add(qty);
			
			  JLabel lblSleepNo = new JLabel("Sleep No.:");
              lblSleepNo.setFont(new Font("Tahoma", Font.BOLD, 16));
              lblSleepNo.setBounds(10, 21, 89, 28);
              getContentPane().add(lblSleepNo);
			
			JDateChooser dateChooser_1 = new JDateChooser();
			dateChooser_1.setDateFormatString("yyyy-MM-dd");
			dateChooser_1.setBounds(464, 217, 112, 28);
			getContentPane().add(dateChooser_1);
			
			JButton button = new JButton("Add");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					 try
	                    {
						 
	                        if(product.getSelectedItem().toString()=="Select")
	                        {
	                            JOptionPane.showMessageDialog(null,"Enter Product");
	                            code.requestFocus();
	                        }
	                        else if (qty.getText().isEmpty()) {
	                            JOptionPane.showMessageDialog(null,"Enter Quantity");
	                            qty.requestFocus();
	                        }
	                        else
	                        {
	                            if(flag==0)
	                            {
	                                int c=0;
	                                String query2="select * from Returnable_temp";
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
	                                    String query3="select * from Returnable_temp";
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
	                                
	                               
	                                String query1="insert into Returnable_temp (ID,Product, Qty,Return_Date,Type) VALUES ('"+i+"','"+product.getSelectedItem().toString()+"',"+qty.getText()+",'"+((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText()+"','"+type.getText()+"')";
	                                PreparedStatement pmt1 = connection.prepareStatement(query1);
	                                pmt1.executeUpdate();
	                                pmt1.close();
	                                
	                                
	                                String query = "select * from Returnable_temp";
	                                PreparedStatement pmt = connection.prepareStatement(query);
	                                ResultSet rs = pmt.executeQuery();
	                                table.setModel(DbUtils.resultSetToTableModel(rs));
	                                pmt.close();
	                                rs.close();
	                            }
	                            else
	                            {
	                               
	                                
	                                String query1="update Returnable_temp set Qty='"+qty.getText()+"',Type='"+type.getText()+"' where ID = '"+id+"'";
	                                PreparedStatement pmt1 = connection.prepareStatement(query1);
	                                pmt1.executeUpdate();
	                                pmt1.close();
	                                
	                                
	                                String query = "select * from Returnable_temp";
	                                PreparedStatement pmt = connection.prepareStatement(query);
	                                ResultSet rs = pmt.executeQuery();
	                                table.setModel(DbUtils.resultSetToTableModel(rs));
	                                pmt.close();
	                                rs.close();
	                            }
	                      		
	                            code.setEnabled(true);
	                            code.setText("");
	                            type.setText("");
	                            product.setSelectedIndex(0);
	                            unit.setText("");
	                            qty.setText("");
	                            code.requestFocus();
	                            ((JTextField)dateChooser_1.getDateEditor().getUiComponent()).setText("");
	                        }
	                        
	                    }
	                   catch (Exception e) {
	                       e.printStackTrace();
	                   }
				}
			});
			button.setFont(new Font("Tahoma", Font.PLAIN, 16));
			button.setBounds(120, 266, 89, 23);
			getContentPane().add(button);
			
			JButton button_1 = new JButton("Delete");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try{
                        
                        
                        String querydetais="delete from Returnable_temp where ID='"+id+"'";
                        PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                        pmtdetails.executeUpdate();
                        pmtdetails.close();
                        
                        
                        String querytable = "select * from Returnable_temp";
                        PreparedStatement pmttable = connection.prepareStatement(querytable);
                        ResultSet rstable = pmttable.executeQuery();
                        table.setModel(DbUtils.resultSetToTableModel(rstable));
                        pmttable.close();
                        rstable.close();
                        
                        code.setEnabled(true);
                        code.setText("");
                        product.setSelectedIndex(0);
                        qty.setText("");
                        type.setText("");
                        code.requestFocus();
                        ((JTextField)dateChooser_1.getDateEditor().getUiComponent()).setText("");
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    } 
				}
			});
			button_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
			button_1.setBounds(413, 266, 89, 23);
			getContentPane().add(button_1);
			
			
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 310, 654, 178);
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
		            	String query13 = "select * from Returnable_temp where ID='"+id+"'";
						  PreparedStatement pmt13 =  connection.prepareStatement(query13);
						  ResultSet rs13 = pmt13.executeQuery();
						  while(rs13.next())
						  {
							  product.setSelectedItem(rs13.getString("Product")); 
		                       qty.setText(rs13.getString("Qty"));
		                       type.setText(rs13.getString("Type"));
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

			
			JButton button_2 = new JButton("Submit");
			button_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					
					   ArrayList<String> product = new ArrayList<String>();
		                ArrayList<Float> qty = new ArrayList<Float>();
		                ArrayList<String> type = new ArrayList<String>();
		                ArrayList<String> dateChooser_1 = new ArrayList<String>();

		                try
		                {
		                    if(sleep_no.getText() == "")
		                    {
		                       JOptionPane.showMessageDialog(null,"Enter P.O.No");
		                    }
		                    else if(client_name.getText().equals(""))
		                    {
		                       JOptionPane.showMessageDialog(null,"Select Crediter Name");
		                    }
		                   else
		                   {
		                       String query0 = "select * from Returnable_temp";
		                       PreparedStatement pmt0 = connection.prepareStatement(query0);
		                       float t = 0;
		                       ResultSet rs0 = pmt0.executeQuery();
		                       while(rs0.next())
		                       {
		                           product.add(rs0.getString("Product"));
		                           qty.add(rs0.getFloat("Qty"));
		                           type.add(rs0.getString("Type"));
		                           dateChooser_1.add(rs0.getString("Return_Date"));
		                       }
		                       pmt0.close();
		                       rs0.close();
		                    
		                       
		                       String query1="insert into Returnable (Client_name, Name,Date,Sleep_no,Amount) VALUES ('"+client_name.getText()+"','"+name.getText()+"','"+((JTextField)dateChooser.getDateEditor().getUiComponent()).getText()+"','"+sleep_no.getText()+"','"+amount.getText()+"')";
		                       PreparedStatement pmt1 = connection.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
		                       pmt1.executeUpdate();
		                       pmt1.close();
		                       
		                       int id=0;
		                       
		                       ResultSet rsid = pmt1.getGeneratedKeys();
		                       id=rsid.getInt(1);
		                       rsid.close();
		                   
		                       for(int i=0; i<product.size();i++)
		                       {
		                           String querydetais="insert into Returnable_Details (S_id, Product, Qty,Return_Date,Type) VALUES ('"+id+"','"+product.get(i)+"',"+qty.get(i)+",'"+dateChooser_1.get(i)+"','"+type.get(i)+"')";
		                           PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
		                           pmtdetails.executeUpdate();
		                           pmtdetails.close();
	                           }
		                       
		                       String querydetais="delete from Returnable_temp";
		                       PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
		                       pmtdetails.executeUpdate();
		                       pmtdetails.close();
		                       
		                       String querytable = "select * from Returnable_temp";
		                       PreparedStatement pmttable = connection.prepareStatement(querytable);
		                       ResultSet rstable = pmttable.executeQuery();
		                       table.setModel(DbUtils.resultSetToTableModel(rstable));
		                       pmttable.close();
		                       rstable.close();

		                      
		                     
		                       code.setText("");
		                       sleep_no.setText("");
		                       client_name.setText("");
		                       name.setText("");
		                       Add_returnable_material.this.type.setText("");
		                       amount.setText("0");
		                       ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();		                   }
		                }
		                    catch(Exception ae)
		                    {
		                    	ae.printStackTrace();
		                    }
				}
			});
			button_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
			button_2.setBounds(266, 512, 89, 28);
			getContentPane().add(button_2);
		
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
			
				try
				{
					ArrayList<Integer> id = new ArrayList<Integer>();

					String query1 = "select * from Returnable";
		            java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
		            ResultSet rs1 = pmt1.executeQuery();
		            while(rs1.next())
		            {
		            	id.add(rs1.getInt("Sleep_no"));
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
		                	sleep_no.setText(Integer.toString(j1));
		                	break;
		                }
		            }
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
				
				 try{
		                String querydetais="delete from Returnable_temp";
		                PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
		                pmtdetails.executeUpdate();
		                pmtdetails.close();
		                
		                String querytable = "select * from Returnable_temp";
		                PreparedStatement pmttable = connection.prepareStatement(querytable);
		                ResultSet rstable = pmttable.executeQuery();
		                table.setModel(DbUtils.resultSetToTableModel(rstable));
		                
		                JLabel lblReturnDate = new JLabel("Return Date:");
		                lblReturnDate.setFont(new Font("Tahoma", Font.BOLD, 16));
		                lblReturnDate.setBounds(325, 219, 129, 28);
		                getContentPane().add(lblReturnDate);
		                
		                
		                
		              
		                pmttable.close();
		                rstable.close();
		            }catch(Exception e)
		            {
		                e.printStackTrace();
		            }
		        
	}
}
