import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

public class Edit_bom extends JInternalFrame {
	private JTable table;
    private TableModel model;
    private int id;
    private JComboBox pro;
    private JTextField quantity;
    private JTextField code;
    int c=0,flag=0,id99=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit_bom frame = new Edit_bom();
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
	public Edit_bom() {
		
		
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
        setBounds((w-774)/2, (h-461)/2, 774, 461);
        getContentPane().setLayout(null);
        
        final Connection connection = Databaseconnection.connection2();
        
        JLabel lblCreditorName = new JLabel("Product Name");
        lblCreditorName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblCreditorName.setBounds(43, 81, 172, 25);
        getContentPane().add(lblCreditorName);
        
        JLabel lblWholesaleBill = new JLabel("Edit BOM");
        lblWholesaleBill.setForeground(Color.BLACK);
        lblWholesaleBill.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWholesaleBill.setBounds(350, 11, 123, 38);
        getContentPane().add(lblWholesaleBill);
        
        JLabel unit = new JLabel("");
        unit.setFont(new Font("Tahoma", Font.BOLD, 16));
        unit.setBounds(660, 129, 88, 25);
        getContentPane().add(unit);
        
        final JTextField name = new JTextField();
        name.setForeground(Color.RED);
        name.setEditable(false);
        name.setFont(new Font("Tahoma", Font.PLAIN, 16));
        name.setBounds(225, 81, 268, 25);
        getContentPane().add(name);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 210, 738, 165);
        getContentPane().add(scrollPane);
        
       
        
        table = new JTable(model){

            public boolean isCellEditable(int rowIndex, int colIndex) {
            return false; //Disallow the editing of any cell
            }
            };
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                if(arg0.getKeyCode()==KeyEvent.VK_DELETE)
                {
                    try
                    {
                        String querydetais="delete from B_temp1 where ID='"+id+"'";
                        PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                        pmtdetails.executeUpdate();
                        pmtdetails.close();
                        
                        String querytable = "select * from B_temp1";
                        PreparedStatement pmttable = connection.prepareStatement(querytable);
                        ResultSet rstable = pmttable.executeQuery();
                        table.setModel(DbUtils.resultSetToTableModel(rstable));
                        pmttable.close();
                        rstable.close();
                        
                        flag=0;
                        code.setEnabled(true);
                        code.setText("");
                        pro.setSelectedIndex(0);
                        quantity.setText("");
                        code.requestFocus();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    } 
                }
            }
        });
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                int r = table.getSelectedRow();
                String i = (table.getModel().getValueAt(r,0).toString());
                id = Integer.parseInt(i);
                try{
                    String querytable = "select * from B_temp1 where ID='"+id+"'";
                    PreparedStatement pmttable = connection.prepareStatement(querytable);
                    ResultSet rstable = pmttable.executeQuery();
                    while(rstable.next())
                    {
                       pro.setSelectedItem(rstable.getString("Product")); 
                       quantity.setText(rstable.getString("Qty"));
                    }
                    pmttable.close();
                    rstable.close();
                     code.setEnabled(false);
                     flag=1;
                     
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                } 
            }
        });
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(table);
        
        JLabel lblProduct = new JLabel("Product");
        lblProduct.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblProduct.setBounds(43, 129, 80, 25);
        getContentPane().add(lblProduct);
        
        pro = new JComboBox();
        pro.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try
        		{
        			unit.setText("");
                	if(c!=0)
                	{
                		
                	}
                	else
                	{
                		String query = "select * from Product Where Product = '"+pro.getSelectedItem().toString()+"'";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        ResultSet rs = pmt.executeQuery();
                        String n = null,u=null;
                        while (rs.next())
                       {
                         unit.setText(rs.getString("Unit"));
                       }
                	}
        		}
        		catch(Exception ae)
        		{
        			ae.printStackTrace();
        		}
        	}
        });
        pro.setModel(new DefaultComboBoxModel(new String[] {"Select"}));
        pro.setFont(new Font("Tahoma", Font.PLAIN, 16));
        pro.setBounds(225, 129, 268, 25);
        getContentPane().add(pro);
        
        JLabel lblQuantity = new JLabel("Qty");
        lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblQuantity.setBounds(503, 129, 47, 25);
        getContentPane().add(lblQuantity);
        
            try{
                String querydetais="delete from B_temp1";
                PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                pmtdetails.executeUpdate();
                pmtdetails.close();
                
                String querytable = "select * from B_temp1";
                PreparedStatement pmttable = connection.prepareStatement(querytable);
                ResultSet rstable = pmttable.executeQuery();
                table.setModel(DbUtils.resultSetToTableModel(rstable));
                pmttable.close();
                rstable.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        
        quantity = new JTextField();
        quantity.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                char c = arg0.getKeyChar();
                if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c=='.'))){
                    getToolkit().beep();
                    arg0.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent arg0) {
                if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
                    
                    try
                    {
                        if(pro.getSelectedItem().toString()=="Select"||quantity.getText().equals(""))
                        {
                            JOptionPane.showMessageDialog(null,"Enter Product");
                        }
                        else
                        {
                            if(flag==0)
                            {
                                int c=0;
                                String query2="select * from B_temp1";
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
                                    String query3="select * from B_temp1";
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
                                    
                                
                                String query1="insert into B_temp1 (ID,Product, Qty) VALUES ('"+i+"','"+pro.getSelectedItem().toString()+"','"+quantity.getText()+"')";
                                PreparedStatement pmt1 = connection.prepareStatement(query1);
                                pmt1.executeUpdate();
                                pmt1.close();
                                
                                String query = "select * from B_temp1";
                                PreparedStatement pmt = connection.prepareStatement(query);
                                ResultSet rs = pmt.executeQuery();
                                table.setModel(DbUtils.resultSetToTableModel(rs));
                                pmt.close();
                                rs.close();
                            }
                            else {
                                String query1="update B_temp1 set Qty = '"+quantity.getText()+"' where ID = '"+id+"'";
                                PreparedStatement pmt1 = connection.prepareStatement(query1);
                                pmt1.executeUpdate();
                                pmt1.close();
                                
                                String query = "select * from B_temp1";
                                PreparedStatement pmt = connection.prepareStatement(query);
                                ResultSet rs = pmt.executeQuery();
                                table.setModel(DbUtils.resultSetToTableModel(rs));
                                pmt.close();
                                rs.close();
                            }
                           
                            flag=0;
                            code.setEnabled(true);
                            code.setText("");
                            pro.setSelectedIndex(0);
                            quantity.setText("");
                            code.requestFocus();
                        }
                    }
                   catch (Exception e) {
                       e.printStackTrace();
                   }
                }
            }
        });
        quantity.setFont(new Font("Tahoma", Font.PLAIN, 16));
        quantity.setColumns(10);
        quantity.setBounds(560, 129, 92, 25);
        getContentPane().add(quantity);
        Calendar cal = Calendar.getInstance();
        Date d = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s = dateFormat.format(cal.getTime());
        try
        {
            d = (Date) dateFormat.parse(s);
            //date.setMaxSelectableDate(d);
           // dateChooser.setMinSelectableDate(d);
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        
        JButton button = new JButton("Submit");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ArrayList<String> product = new ArrayList<String>();
                ArrayList<Float> qty = new ArrayList<Float>();
                try
                {
                  if(name.getText() == "")
                  {
                     JOptionPane.showMessageDialog(null,"Enter Product Name");
                  }
                 else
                 {
                     String query0 = "select * from B_temp1";
                     PreparedStatement pmt0 = connection.prepareStatement(query0);
                     float t = 0;
                     ResultSet rs0 = pmt0.executeQuery();
                     while(rs0.next())
                     {
                         product.add(rs0.getString("Product"));
                         qty.add(rs0.getFloat("Qty"));
                     }
                     pmt0.close();
                     rs0.close();
                     
                     String querydetais1="delete from BOM_Detail where S_id='"+id99+"'";
                     PreparedStatement pmtdetails1 = connection.prepareStatement(querydetais1);
                     pmtdetails1.executeUpdate();
                     pmtdetails1.close();
                     
                     
                     for(int i=0; i<product.size();i++)
                     {
                         String querydetais="insert into BOM_Detail (S_id, Product, Qty) VALUES ('"+id99+"','"+product.get(i)+"',"+qty.get(i)+")";
                         PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                         pmtdetails.executeUpdate();
                         pmtdetails.close();
                     }
                     
                     View_bom b = new View_bom();
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
        button.setBounds(274, 386, 150, 30);
        getContentPane().add(button);
        button.setMnemonic(KeyEvent.VK_END);
        
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
                    	pro.setSelectedItem(rs.getString("Product"));
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
        code.setBounds(144, 129, 63, 25);
        getContentPane().add(code);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {

                
                try
                {
                    if(pro.getSelectedItem().toString()=="Select"||quantity.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Enter Product");
                    }
                    else
                    {
                        if(flag==0)
                        {
                            int c=0;
                            String query2="select * from B_temp1";
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
                                String query3="select * from B_temp1";
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
                                
                            
                            String query1="insert into B_temp1 (ID,Product, Qty) VALUES ('"+i+"','"+pro.getSelectedItem().toString()+"','"+quantity.getText()+"')";
                            PreparedStatement pmt1 = connection.prepareStatement(query1);
                            pmt1.executeUpdate();
                            pmt1.close();
                            
                            String query = "select * from B_temp1";
                            PreparedStatement pmt = connection.prepareStatement(query);
                            ResultSet rs = pmt.executeQuery();
                            table.setModel(DbUtils.resultSetToTableModel(rs));
                            pmt.close();
                            rs.close();
                        }
                        else {
                            String query1="update B_temp1 set Qty = '"+quantity.getText()+"' where ID = '"+id+"'";
                            PreparedStatement pmt1 = connection.prepareStatement(query1);
                            pmt1.executeUpdate();
                            pmt1.close();
                            
                            String query = "select * from B_temp1";
                            PreparedStatement pmt = connection.prepareStatement(query);
                            ResultSet rs = pmt.executeQuery();
                            table.setModel(DbUtils.resultSetToTableModel(rs));
                            pmt.close();
                            rs.close();
                        }
                       
                        flag=0;
                        code.setEnabled(true);
                        code.setText("");
                        pro.setSelectedIndex(0);
                        quantity.setText("");
                        code.requestFocus();
                    }
                }
               catch (Exception e) {
                   e.printStackTrace();
               }
            
        	}
        });
        btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnAdd.setBounds(230, 176, 89, 23);
        getContentPane().add(btnAdd);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {

                try
                {
                    String querydetais="delete from B_temp1 where ID='"+id+"'";
                    PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                    pmtdetails.executeUpdate();
                    pmtdetails.close();
                    
                    String querytable = "select * from B_temp1";
                    PreparedStatement pmttable = connection.prepareStatement(querytable);
                    ResultSet rstable = pmttable.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rstable));
                    pmttable.close();
                    rstable.close();
                    
                    flag=0;
                    code.setEnabled(true);
                    code.setText("");
                    pro.setSelectedIndex(0);
                    quantity.setText("");
                    code.requestFocus();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                } 
            
        	}
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnDelete.setBounds(404, 176, 89, 23);
        getContentPane().add(btnDelete);
        
        
        
        try {
            // pull data from the database 
            String query = "select * from Product";
            PreparedStatement pmt = connection.prepareStatement(query);
            ResultSet rs = pmt.executeQuery();
            
            while (rs.next())
           {
             String n = rs.getString("Product");
             pro.addItem(n);
           }
            pmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try
        {
        	String query = "select * from B_id";
            PreparedStatement pmt = connection.prepareStatement(query);
            ResultSet rs = pmt.executeQuery();
            while (rs.next())
            {
            	id99=rs.getInt("ID");
            }
            pmt.close();
            rs.close();
            
            String query1 = "select * from BOM where ID='"+id99+"'";
            PreparedStatement pmt1 = connection.prepareStatement(query1);
            ResultSet rs1 = pmt1.executeQuery();
            while (rs1.next())
            {
            	name.setText(rs1.getString("Name"));
            }
            pmt1.close();
            rs1.close();
            
            ArrayList<String> product = new ArrayList<String>();
            ArrayList<Float> qty = new ArrayList<Float>();
            
            String query0 = "select * from BOM_Detail where S_id='"+id99+"'";
            PreparedStatement pmt0 = connection.prepareStatement(query0);
            ResultSet rs0 = pmt0.executeQuery();
            while(rs0.next())
            {
                product.add(rs0.getString("Product"));
                qty.add(rs0.getFloat("Qty"));
            }
            pmt0.close();
            rs0.close();
            
            String query12="delete from B_temp1";
            PreparedStatement pmt12 = connection.prepareStatement(query12);
            pmt12.executeUpdate();
            pmt12.close();
            
            for(int i=0; i<product.size();i++)
            {
                String querydetais="insert into B_temp1 (ID, Product, Qty) VALUES ('"+(i+1)+"','"+product.get(i)+"',"+qty.get(i)+")";
                PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                pmtdetails.executeUpdate();
                pmtdetails.close();
            }
            
            String query2 = "select * from B_temp1";
            PreparedStatement pmt2 = connection.prepareStatement(query2);
            ResultSet rs2 = pmt2.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs2));
            pmt2.close();
            rs2.close();
        }
        catch(Exception ae)
        {
        	ae.printStackTrace();
        }
      
    }

}
