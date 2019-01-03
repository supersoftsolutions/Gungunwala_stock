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
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

public class Cost_bom extends JInternalFrame {
	private JTable table;
    private TableModel model;
    public int id;
    private JComboBox product;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cost_bom frame = new Cost_bom();
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
	public Cost_bom() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-120;
        int w = (int)(width);
        int h = (int)(height);
        setClosable(true);
        setBounds((w-774)/2, (h-461)/2, 774, 531);
        getContentPane().setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 51, 758, 380);
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
                    
                    
                }
            });
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(table);

        final Connection connection = Databaseconnection.connection2();
        
       
        
        JLabel btnDelete = new JLabel("Product");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDelete.setBounds(10, 5, 93, 39);
        getContentPane().add(btnDelete);
        
        JLabel btnEdit = new JLabel("Total");
        btnEdit.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnEdit.setBounds(393, 442, 122, 39);
        getContentPane().add(btnEdit);
        
        product = new JComboBox();
       
        product.setFont(new Font("Tahoma", Font.PLAIN, 16));
        product.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
        product.setBounds(113, 5, 218, 35);
        getContentPane().add(product);
        
        final JLabel Total = new JLabel("");
        Total.setFont(new Font("Tahoma", Font.BOLD, 16));
        Total.setBounds(548, 442, 122, 39);
        getContentPane().add(Total);
        
        JLabel lblQty = new JLabel("Qty");
        lblQty.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblQty.setBounds(359, 5, 51, 39);
        getContentPane().add(lblQty);
        
        final JTextField qt = new JTextField();
        qt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                char c = arg0.getKeyChar();
                if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c=='.'))){
                    getToolkit().beep();
                    arg0.consume();
                }
            }
          
        });

        qt.setFont(new Font("Tahoma", Font.PLAIN, 16));
        qt.setBounds(420, 5, 95, 35);
        getContentPane().add(qt);
        
        JButton label = new JButton("View");
        label.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                
                try {
                    // pull data from the database 
                    String queryd = "delete from Temp_Cost";
                    PreparedStatement pmtd = connection.prepareStatement(queryd);
                    pmtd.executeUpdate();
                    pmtd.close();
                    
                    int no=0;
                    String query = "select * from BOM where Name='"+product.getSelectedItem().toString()+"'";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    while (rs.next())
                    {
                    	no = rs.getInt("ID");
                    }
                    pmt.close();
                    rs.close();
                    
                    ArrayList<String> pro = new ArrayList<String>();
                    ArrayList<Integer> qty = new ArrayList<Integer>();
                    
                    String query1 = "select * from BOM_Detail where S_id='"+no+"'";
                    PreparedStatement pmt1 = connection.prepareStatement(query1);
                    ResultSet rs1 = pmt1.executeQuery();
                    while (rs1.next())
                    {
                    	qty.add(rs1.getInt("Qty"));
                    	pro.add(rs1.getString("Product"));
                    }
                    pmt1.close();
                    rs1.close();
                    
                    for(int i=0;i<pro.size();i++)
                    {
                        float rate=0,stock=0;
                        String query3 = "select * from Purchase_detail where Product='"+pro.get(i)+"'";
                        PreparedStatement pmt3 = connection.prepareStatement(query3);
                        ResultSet rs3 = pmt3.executeQuery();
                        while (rs3.next())
                        {
                         rate = rs3.getFloat("Rate");
                        }
                        pmt3.close();
                        rs3.close();
                        
                        String query7 = "select * from Stock where Product='"+pro.get(i)+"'";
                        PreparedStatement pmt7 = connection.prepareStatement(query7);
                        ResultSet rs7 = pmt7.executeQuery();
                        while (rs7.next())
                        {
                        	stock = rs7.getFloat("Stock");
                        }
                        pmt7.close();
                        rs7.close();
                        
                        String query4 = "insert into Temp_Cost(ID,Product,Qty,Cost,Available_Qty) values ('"+(i+1)+"','"+pro.get(i)+"','"+(qty.get(i)*Integer.parseInt(qt.getText()))+"','"+(rate*qty.get(i)*Integer.parseInt(qt.getText()))+"','"+stock+"')";
                        PreparedStatement pmt4 = connection.prepareStatement(query4);
                        pmt4.executeUpdate();
                        pmt4.close();
                    }
                    
                    String query5 = "select * from Temp_Cost";
                    PreparedStatement pmt5 = connection.prepareStatement(query5);
                    ResultSet rs5 = pmt5.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs5));
                    ResultSet rs6 = pmt5.executeQuery();
                    float tot=0;
                    while(rs6.next())
                    {
                        tot  += rs6.getFloat("Cost");
                    }
                    pmt5.close();
                    rs5.close();
                    Total.setText(Float.toString(tot));
                   
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setBounds(605, 5, 122, 39);
        getContentPane().add(label);
        

        try {
            // pull data from the database 
            String query = "select * from BOM";
            
            PreparedStatement pmt = connection.prepareStatement(query);
            
            ResultSet rs = pmt.executeQuery();
            
            while (rs.next())
           {
             String n = rs.getString("Name");
               product.addItem(n);
           }
            pmt.close();
            rs.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
