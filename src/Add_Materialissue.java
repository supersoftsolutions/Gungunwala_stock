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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import java.awt.Font;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;

public class Add_Materialissue extends JInternalFrame {
	private JTextField client_name;
	private JTextField sleep_no;
	private JTextField name;
	private JTextField type;
	private JTextField code;
	private JTextField qty;
	private JTextField rate;
	private JTable table;
	private static JTextField total;
    private String id;
	int flag=0;
    int c=0;
    private JComboBox product;





	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_Materialissue frame = new Add_Materialissue();
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
	
private static void gettottal(){
        
        final Connection connection = Databaseconnection.connection2();
        
      
        try
        {
            int total1 =0;
            String query3="select * from Material_temp";
            PreparedStatement pmt3 = connection.prepareStatement(query3);
            ResultSet rs3 = pmt3.executeQuery();
            while(rs3.next())
            {
                total1 += rs3.getInt("Total");
            }
            rs3.close();
            pmt3.close(); 
            
         
           
            
            total.setText(Float.toString((total1)));
            
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
}
	public Add_Materialissue() {
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

			
			JLabel lblMaterialIssue = new JLabel("Material Issue");
			lblMaterialIssue.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblMaterialIssue.setBounds(252, 21, 160, 25);
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
		                	String query = "select Rate from Purchase_detail Where Product = '"+product.getSelectedItem().toString()+"'";
	                        PreparedStatement pmt = connection.prepareStatement(query);
	                        ResultSet rs = pmt.executeQuery();
	                        String n = null,u=null;
	                        while (rs.next())
	                       {
	                         rate.setText(rs.getString("Rate"));
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
			dateChooser_1.setBounds(552, 215, 112, 28);
			getContentPane().add(dateChooser_1);
			
			JLabel label_3 = new JLabel("Rate:");
			label_3.setFont(new Font("Tahoma", Font.BOLD, 16));
			label_3.setBounds(360, 215, 61, 27);
			getContentPane().add(label_3);
			
			rate = new JTextField();
			rate.setColumns(10);
			rate.setBounds(433, 217, 94, 28);
			getContentPane().add(rate);
			
			JButton button = new JButton("Add");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					 try
	                    {
						 float total99 = Float.parseFloat(rate.getText())*Float.parseFloat(qty.getText());
	                        if(product.getSelectedItem().toString()=="Select")
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
	                                String query2="select * from Material_temp";
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
	                                    String query3="select * from Material_temp";
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
	                                
	                               
	                                String query1="insert into Material_temp (ID,Product, Qty, Rate, Total,Return_Date,Type) VALUES ('"+i+"','"+product.getSelectedItem().toString()+"',"+qty.getText()+",'"+rate.getText()+"','"+total99+"','"+((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText()+"','"+type.getText()+"')";
	                                PreparedStatement pmt1 = connection.prepareStatement(query1);
	                                pmt1.executeUpdate();
	                                pmt1.close();
	                                
	                                
	                                String query = "select * from Material_temp";
	                                PreparedStatement pmt = connection.prepareStatement(query);
	                                ResultSet rs = pmt.executeQuery();
	                                table.setModel(DbUtils.resultSetToTableModel(rs));
	                                pmt.close();
	                                rs.close();
	                            }
	                            else
	                            {
	                               
	                                
	                                String query1="update Material_temp set Qty='"+qty.getText()+"', Rate='"+rate.getText()+"',Type='"+type.getText()+"',Total='"+total99+"' where ID = '"+id+"'";
	                                PreparedStatement pmt1 = connection.prepareStatement(query1);
	                                pmt1.executeUpdate();
	                                pmt1.close();
	                                
	                                
	                                String query = "select * from Material_temp";
	                                PreparedStatement pmt = connection.prepareStatement(query);
	                                ResultSet rs = pmt.executeQuery();
	                                table.setModel(DbUtils.resultSetToTableModel(rs));
	                                pmt.close();
	                                rs.close();
	                            }
	                      		total.setText(Double.toString(total99));

	                            code.setEnabled(true);
	                            code.setText("");
	                            type.setText("");
	                            product.setSelectedIndex(0);
	                            unit.setText("");
	                            qty.setText("");
	                            code.requestFocus();
	                            rate.setText("");
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
                        
                        
                        String querydetais="delete from Material_temp where ID='"+id+"'";
                        PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                        pmtdetails.executeUpdate();
                        pmtdetails.close();
                        
                        
                        String querytable = "select * from Material_temp";
                        PreparedStatement pmttable = connection.prepareStatement(querytable);
                        ResultSet rstable = pmttable.executeQuery();
                        table.setModel(DbUtils.resultSetToTableModel(rstable));
                        pmttable.close();
                        rstable.close();
                        
                        code.setEnabled(true);
                        code.setText("");
                        product.setSelectedIndex(0);
                        rate.setText("");
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
			
			
			total = new JTextField();
			total.setFont(new Font("Tahoma", Font.BOLD, 16));
			total.setText("0");
			total.setEditable(false);
			total.setColumns(10);
			total.setBounds(474, 514, 116, 28);
			getContentPane().add(total);
			
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
		            	String query13 = "select * from Material_temp where ID='"+id+"'";
						  PreparedStatement pmt13 =  connection.prepareStatement(query13);
						  ResultSet rs13 = pmt13.executeQuery();
						  while(rs13.next())
						  {
							  product.setSelectedItem(rs13.getString("Product")); 
		                       qty.setText(rs13.getString("Qty"));
		                       rate.setText(rs13.getString("Rate"));
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
		                ArrayList<Float> rate1 = new ArrayList<Float>();
		                ArrayList<Float> total1 = new ArrayList<Float>();
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
		                       String query0 = "select * from Material_temp";
		                       PreparedStatement pmt0 = connection.prepareStatement(query0);
		                       float t = 0;
		                       ResultSet rs0 = pmt0.executeQuery();
		                       while(rs0.next())
		                       {
		                           product.add(rs0.getString("Product"));
		                           qty.add(rs0.getFloat("Qty"));
		                           rate1.add(rs0.getFloat("Rate"));
		                           total1.add(rs0.getFloat("Total"));
		                           type.add(rs0.getString("Type"));
		                           dateChooser_1.add(rs0.getString("Return_Date"));
		                       }
		                       pmt0.close();
		                       rs0.close();
		                    
		                       
		                       String query1="insert into Material_issue (Client_name, Name,Date,Total,Sleep_no) VALUES ('"+client_name.getText()+"','"+name.getText()+"','"+((JTextField)dateChooser.getDateEditor().getUiComponent()).getText()+"','"+total.getText()+"','"+sleep_no.getText()+"')";
		                       PreparedStatement pmt1 = connection.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
		                       pmt1.executeUpdate();
		                       pmt1.close();
		                       
		                       int id=0;
		                       
		                       ResultSet rsid = pmt1.getGeneratedKeys();
		                       id=rsid.getInt(1);
		                       rsid.close();
		                   
		                       for(int i=0; i<product.size();i++)
		                       {
		                           String querydetais="insert into Material_Issue_Details (M_id, Product, Qty, Rate, Total,Return_Date,Type) VALUES ('"+id+"','"+product.get(i)+"',"+qty.get(i)+",'"+rate1.get(i)+"','"+total1.get(i)+"','"+dateChooser_1.get(i)+"','"+type.get(i)+"')";
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
		                       
		                       String querydetais="delete from Material_temp";
		                       PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
		                       pmtdetails.executeUpdate();
		                       pmtdetails.close();
		                       
		                       String querytable = "select * from Material_temp";
		                       PreparedStatement pmttable = connection.prepareStatement(querytable);
		                       ResultSet rstable = pmttable.executeQuery();
		                       table.setModel(DbUtils.resultSetToTableModel(rstable));
		                       pmttable.close();
		                       rstable.close();

		                      
		                     
		                       code.setText("");
		                       sleep_no.setText("");
		                       client_name.setText("");
		                       name.setText("");
		                       rate.setText("");
		                       Add_Materialissue.this.type.setText("");
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

					String query1 = "select * from Material_issue";
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
		                String querydetais="delete from Material_temp";
		                PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
		                pmtdetails.executeUpdate();
		                pmtdetails.close();
		                
		                String querytable = "select * from Material_temp";
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
