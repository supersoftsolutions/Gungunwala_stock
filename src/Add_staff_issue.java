

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

import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

public class Add_staff_issue extends JInternalFrame {
	public JTable table;
    private TableModel model;
    private int id;
    private JComboBox pro;
    private JTextField quantity;
    private JTextField code;
    
    private JTextField remark;
    private JComboBox proname;
    private JTextField issue_no;
    int c=0,flag=0;
    private JTextField job_no;
    private JTextField person_name;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_staff_issue frame = new Add_staff_issue();
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
	public Add_staff_issue() {
		
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
        setBounds((w-774)/2, (h-549)/2, 774, 549);
        getContentPane().setLayout(null);
        
        final Connection connection = Databaseconnection.connection2();
        
        proname = new JComboBox();
        proname.addItem("SELECT");
        proname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        proname.setBounds(231, 67, 268, 25);
        getContentPane().add(proname);
        
        
        try
        {
        	String querytable = "select * from BOM";
            PreparedStatement pmttable = connection.prepareStatement(querytable);
            ResultSet rstable = pmttable.executeQuery();
            while(rstable.next())
            {
               proname.addItem(rstable.getString("Name"));
            }
            pmttable.close();
            rstable.close();
        	
        }
        catch(Exception ae)
        {
        	ae.printStackTrace();
        }
        
        JLabel lblCreditorName = new JLabel("Supervisor Name");
        lblCreditorName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblCreditorName.setBounds(49, 111, 190, 25);
        getContentPane().add(lblCreditorName);
        
        final JLabel unit = new JLabel("");
        unit.setFont(new Font("Calibri", Font.BOLD, 25));
        unit.setBounds(711, 199, 47, 25);
        getContentPane().add(unit);
        
        issue_no = new JTextField();
        issue_no.setFont(new Font("Tahoma", Font.PLAIN, 16));
        issue_no.setBounds(97, 11, 124, 25);
        getContentPane().add(issue_no);
        
        try
        {
            ArrayList<Integer> id = new ArrayList<Integer>();

            String query1 = "select * from Staff_issue";
            java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
            ResultSet rs1 = pmt1.executeQuery();
            while(rs1.next())
            {
                id.add(rs1.getInt("Issue_no"));
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
                    issue_no.setText(Integer.toString(j1));
                    break;
                }
            }
        }
        catch(Exception ae)
        {
            ae.printStackTrace();
        }
        
        JLabel lblWholesaleBill = new JLabel("Staff Issue");
        lblWholesaleBill.setForeground(Color.BLACK);
        lblWholesaleBill.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWholesaleBill.setBounds(335, 11, 125, 38);
        getContentPane().add(lblWholesaleBill);
        
        final JTextField supervisor_name = new JTextField();
        supervisor_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
        supervisor_name.setBounds(231, 111, 268, 25);
        getContentPane().add(supervisor_name);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 278, 738, 193);
        getContentPane().add(scrollPane);
        
        
        
        
        
        table = new JTable(model){

            public boolean isCellEditable(int rowIndex, int colIndex) {
            return false; //Disallow the editing of any cell
            }
            };
            
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                int r = table.getSelectedRow();
                String i = (table.getModel().getValueAt(r,0).toString());
                id = Integer.parseInt(i);
                try{
                    String querytable = "select * from Staff_issue_temp where ID='"+id+"'";
                    PreparedStatement pmttable = connection.prepareStatement(querytable);
                    ResultSet rstable = pmttable.executeQuery();
                    while(rstable.next())
                    {
                       pro.setSelectedItem(rstable.getString("Product")); 
                       quantity.setText(rstable.getString("Qty"));
                       job_no.setText(rstable.getString("Job_no"));
                       person_name.setText(rstable.getString("Person"));
                       remark.setText(rstable.getString("Remark"));
                    }
                    pmttable.close();
                    rstable.close();
                     code.setEnabled(false);
                     flag=1;
                    
                }catch(Exception e)
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
        lblProduct.setBounds(49, 199, 80, 25);
        getContentPane().add(lblProduct);
        
        pro = new JComboBox();
        pro.setModel(new DefaultComboBoxModel(new String[] {"Select"}));
        pro.addActionListener(new ActionListener() {
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
                		String query = "select * from Product Where Product = '"+pro.getSelectedItem().toString()+"'";
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
        
        pro.setFont(new Font("Tahoma", Font.PLAIN, 16));
        pro.setBounds(223, 199, 318, 25);
        getContentPane().add(pro);
        
        JLabel lblQuantity = new JLabel("Qty");
        lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblQuantity.setBounds(551, 199, 47, 25);
        getContentPane().add(lblQuantity);
        
            try{
                
                String querydetais="delete from Staff_issue_temp";
                PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                pmtdetails.executeUpdate();
                pmtdetails.close();
                
                String querytable = "select * from Staff_issue_temp";
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
                        
                        if(pro.getSelectedItem().toString()=="Select")
                        {
                            JOptionPane.showMessageDialog(null,"Enter Product");
                        }
                        else
                        {
                            if(flag==0)
                            {
                                int c=0;
                                String query2="select * from S_temp";
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
                                    String query3="select * from S_temp";
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
                                
                                String query1="insert into S_temp (ID, Product, Quantity, Remark) VALUES ('"+i+"','"+pro.getSelectedItem().toString()+"',"+quantity.getText()+",'"+remark.getText()+"')";
                                PreparedStatement pmt1 = connection.prepareStatement(query1);
                                pmt1.executeUpdate();
                                pmt1.close();
                                
                                String query = "select ID,Product,Quantity,Remark from S_temp";
                                PreparedStatement pmt = connection.prepareStatement(query);
                                ResultSet rs = pmt.executeQuery();
                                table.setModel(DbUtils.resultSetToTableModel(rs));
                                pmt.close();
                                rs.close();
                                
                               
                            }
                            else {
                                String query1="update S_temp set Quantity = '"+quantity.getText()+"',Remark='"+remark.getText()+"' where ID = '"+id+"'";
                                PreparedStatement pmt1 = connection.prepareStatement(query1);
                                pmt1.executeUpdate();
                                pmt1.close();
                                
                                String query = "select ID,Product,Quantity,Remark from S_temp";
                                PreparedStatement pmt = connection.prepareStatement(query);
                                ResultSet rs = pmt.executeQuery();
                                table.setModel(DbUtils.resultSetToTableModel(rs));
                                pmt.close();
                                rs.close();
                            }
                            
                            code.setEnabled(true);
                            code.setText("");
                            pro.setSelectedItem(0);
                            unit.setText("");
                            remark.setText("");
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
        
        JLabel lblRemark = new JLabel("Remark");
        lblRemark.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblRemark.setBounds(49, 242, 91, 25);
        getContentPane().add(lblRemark);
        
        remark = new JTextField();
        remark.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
                    try
                    {
                        if(pro.getSelectedItem().toString()=="Select")
                        {
                            JOptionPane.showMessageDialog(null,"Enter Product");
                        }
                        else
                        {
                            if(flag==0)
                            {
                                int c=0;
                                String query2="select * from Staff_issue_temp";
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
                                    String query3="select * from Staff_issue_temp";
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
                                
                                String query1="insert into Staff_issue_temp (ID,Job_no, Product, Qty,Person, Remark) VALUES ('"+i+"','"+job_no.getText()+"','"+pro.getSelectedItem().toString()+"',"+quantity.getText()+",'"+person_name.getText()+"','"+remark.getText()+"')";
                                PreparedStatement pmt1 = connection.prepareStatement(query1);
                                pmt1.executeUpdate();
                                pmt1.close();
                                
                                String query = "select * from Staff_issue_temp";
                                PreparedStatement pmt = connection.prepareStatement(query);
                                ResultSet rs = pmt.executeQuery();
                                table.setModel(DbUtils.resultSetToTableModel(rs));
                                pmt.close();
                                rs.close();
                                
                               
                            }
                            else {
                                String query1="update Staff_issue_temp set Qty = '"+quantity.getText()+"',Remark='"+remark.getText()+"',Person='"+person_name.getText()+"',Job_no='"+job_no.getText()+"' where ID = '"+id+"'";
                                PreparedStatement pmt1 = connection.prepareStatement(query1);
                                pmt1.executeUpdate();
                                pmt1.close();
                                
                                String query = "select * from Staff_issue_temp";
                                PreparedStatement pmt = connection.prepareStatement(query);
                                ResultSet rs = pmt.executeQuery();
                                table.setModel(DbUtils.resultSetToTableModel(rs));
                                pmt.close();
                                rs.close();
                            }
                            flag=0;
                            job_no.setText("");
                            person_name.setText("");
                            code.setEnabled(true);
                            code.setText("");
                            pro.setSelectedIndex(0);
                            unit.setText("");
                            remark.setText("");
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
        remark.setFont(new Font("Tahoma", Font.PLAIN, 16));
        remark.setBounds(144, 242, 208, 25);
        getContentPane().add(remark);
        quantity.setFont(new Font("Tahoma", Font.PLAIN, 16));
        quantity.setColumns(10);
        quantity.setBounds(608, 199, 92, 25);
        getContentPane().add(quantity);
        
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
       
        date.setBounds(601, 11, 125, 30);
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
        code.setBounds(150, 199, 63, 25);
        getContentPane().add(code);
        
        
        JLabel lblClientName = new JLabel("Project Name");
        lblClientName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblClientName.setBounds(49, 67, 172, 25);
        getContentPane().add(lblClientName);
        
        JButton button = new JButton("Submit");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ArrayList<String> product = new ArrayList<String>();
                ArrayList<String> job_no = new ArrayList<String>();
                ArrayList<Float> qty = new ArrayList<Float>();
                ArrayList<String> remark = new ArrayList<String>();
                ArrayList<String> person = new ArrayList<String>();
                try
                {
                   
                        if(supervisor_name.getText() == "")
                        {
                            JOptionPane.showMessageDialog(null,"Enter Person Name");
                        }
                        else
                        {
                            String query0 = "select * from Staff_issue_temp";
                            PreparedStatement pmt0 = connection.prepareStatement(query0);
                            ResultSet rs0 = pmt0.executeQuery();
                            while(rs0.next())
                            {
                                product.add(rs0.getString("Product"));
                                remark.add(rs0.getString("Remark"));
                                job_no.add(rs0.getString("Job_no"));
                                person.add(rs0.getString("Person"));
                                qty.add(rs0.getFloat("Qty"));
                            }
                            pmt0.close();
                            rs0.close();
                            
                            String query1="insert into Staff_issue (Issue_no,Project_name,Date,Supervisor_name) VALUES ('"+issue_no.getText()+"','"+proname.getSelectedItem().toString()+"','"+((JTextField)date.getDateEditor().getUiComponent()).getText()+"','"+supervisor_name.getText()+"')";
                            PreparedStatement pmt1 = connection.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
                            pmt1.executeUpdate();
                            pmt1.close();
                           
                            int id=0;
                            
                            ResultSet rsid = pmt1.getGeneratedKeys();
                            id=rsid.getInt(1);
                            rsid.close();
                            
                            for(int i=0; i<product.size();i++)
                            {
                            	String querydetais="insert into Staff_issue_detail (S_id,Job_no, Product, Qty,Person, Remark) VALUES ('"+id+"','"+job_no.get(i)+"','"+product.get(i)+"',"+qty.get(i)+",'"+person.get(i)+"','"+remark.get(i)+"')";
                                PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                                pmtdetails.executeUpdate();
                                pmtdetails.close();
                            
                                String query4 = "UPDATE Stock set Stock =Stock-"+qty.get(i)+" where Product='"+product.get(i)+"'";
                                PreparedStatement pmt4 = connection.prepareStatement(query4);
                                pmt4.executeUpdate();
                                pmt4.close();
                                
                                String query41 = "UPDATE Product set Stock =Stock-"+qty.get(i)+" where Product='"+product.get(i)+"'";
                                PreparedStatement pmt41 = connection.prepareStatement(query41);
                                pmt41.executeUpdate();
                                pmt41.close();
                            }

                            String querydetais="delete from Staff_issue_temp";
                            PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                            pmtdetails.executeUpdate();
                            pmtdetails.close();

                            String querytable = "select * from Staff_issue_temp";
                            PreparedStatement pmttable = connection.prepareStatement(querytable);
                            ResultSet rstable = pmttable.executeQuery();
                            table.setModel(DbUtils.resultSetToTableModel(rstable));
                            pmttable.close();
                            rstable.close();

                            Add_staff_issue b = new Add_staff_issue();
                            JDesktopPane desktopPane = getDesktopPane();
                            desktopPane.add(b);
                            b.show();
                            dispose();
                        }
                        
                	}
                	catch (Exception e) 
                	{
                		e.printStackTrace();
                	}
            }
        });
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBounds(301, 482, 150, 30);
        getContentPane().add(button);
        button.setMnemonic(KeyEvent.VK_END);
        
        JLabel lblJobNo = new JLabel("Job No.");
        lblJobNo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblJobNo.setBounds(49, 158, 80, 25);
        getContentPane().add(lblJobNo);
        
        job_no = new JTextField();
        job_no.setFont(new Font("Tahoma", Font.PLAIN, 16));
        job_no.setBounds(150, 158, 202, 25);
        getContentPane().add(job_no);
        
        person_name = new JTextField();
        person_name.setFont(new Font("Tahoma", Font.PLAIN, 16));
        person_name.setBounds(498, 158, 202, 25);
        getContentPane().add(person_name);
        
        JLabel lblPersonName = new JLabel("Person Name");
        lblPersonName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPersonName.setBounds(362, 158, 115, 25);
        getContentPane().add(lblPersonName);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try
        		{
                    if(pro.getSelectedItem().toString()=="Select")
                    {
                        JOptionPane.showMessageDialog(null,"Enter Product");
                    }
                    else
                    {
                        if(flag==0)
                        {
                            int c=0;
                            String query2="select * from Staff_issue_temp";
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
                                String query3="select * from Staff_issue_temp";
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
                            
                            String query1="insert into Staff_issue_temp (ID,Job_no, Product, Qty,Person, Remark) VALUES ('"+i+"','"+job_no.getText()+"','"+pro.getSelectedItem().toString()+"',"+quantity.getText()+",'"+person_name.getText()+"','"+remark.getText()+"')";
                            PreparedStatement pmt1 = connection.prepareStatement(query1);
                            pmt1.executeUpdate();
                            pmt1.close();
                            
                            String query = "select * from Staff_issue_temp";
                            PreparedStatement pmt = connection.prepareStatement(query);
                            ResultSet rs = pmt.executeQuery();
                            table.setModel(DbUtils.resultSetToTableModel(rs));
                            pmt.close();
                            rs.close();
                            
                           
                        }
                        else {
                            String query1="update Staff_issue_temp set Qty = '"+quantity.getText()+"',Remark='"+remark.getText()+"',Person='"+person_name.getText()+"',Job_no='"+job_no.getText()+"' where ID = '"+id+"'";
                            PreparedStatement pmt1 = connection.prepareStatement(query1);
                            pmt1.executeUpdate();
                            pmt1.close();
                            
                            String query = "select * from Staff_issue_temp";
                            PreparedStatement pmt = connection.prepareStatement(query);
                            ResultSet rs = pmt.executeQuery();
                            table.setModel(DbUtils.resultSetToTableModel(rs));
                            pmt.close();
                            rs.close();
                        }
                        flag=0;
                        job_no.setText("");
                        person_name.setText("");
                        code.setEnabled(true);
                        code.setText("");
                        pro.setSelectedIndex(0);
                        unit.setText("");
                        remark.setText("");
                        quantity.setText("");
                        code.requestFocus();
                    }
                }
        		catch(Exception ae)
        		{
        			ae.printStackTrace();
        		}
        	}
        });
        btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnAdd.setBounds(410, 244, 89, 23);
        getContentPane().add(btnAdd);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try
        		{

                    String querydetais="delete from Staff_issue_temp where ID='"+id+"'";
                    PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                    pmtdetails.executeUpdate();
                    pmtdetails.close();
                    
                    String querytable = "select * from Staff_issue_temp";
                    PreparedStatement pmttable = connection.prepareStatement(querytable);
                    ResultSet rstable = pmttable.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rstable));
                    pmttable.close();
                    rstable.close();
                    
                    code.setEnabled(true);
                    code.setText("");
                    pro.setSelectedIndex(0);
                    quantity.setText("");
                    code.requestFocus();
                    flag=0;
                    job_no.setText("");
                    person_name.setText("");
                remark.setText("");
        		}
        		catch(Exception ae)
        		{
        			ae.printStackTrace();
        		}
        	}
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnDelete.setBounds(561, 244, 89, 23);
        getContentPane().add(btnDelete);
        
        JLabel label = new JLabel("Sleep No.:");
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setBounds(10, 11, 89, 28);
        getContentPane().add(label);
        
        
        
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
      
      
    }
}
