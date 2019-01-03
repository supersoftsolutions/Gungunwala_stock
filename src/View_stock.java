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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;


import net.proteanit.sql.DbUtils;

public class View_stock extends JInternalFrame {
	private JTable table;
    private TableModel model;
    private int id;
    private JTextField textField;
    private JTextField stock;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_stock frame = new View_stock();
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
	public View_stock() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        int w = (int)(width);
        int h = (int)(height)-120;
        setClosable(true);
        setBounds((w-674)/2, (h-530)/2, 674, 530);
        getContentPane().setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 107, 658, 393);
        getContentPane().add(scrollPane);
        
        table = new JTable(model){

            public boolean isCellEditable(int rowIndex, int colIndex) {
            return false; //Disallow the editing of any cell
            }
            };
        
        final Connection connection = Databaseconnection.connection2();
        
        try {
            // pull data from the database 
            Calendar cal= Calendar.getInstance();
            SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
            String date = d.format(cal.getTime());
          
            String query = "select * from Stock";
            PreparedStatement pmt = connection.prepareStatement(query);
            ResultSet rs = pmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            pmt.close();
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                try {
                    
                    String query = "select * from Stock where ID like '%"+textField.getText()+"%' or Product like '%"+textField.getText()+"%'";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } 
            }
        });
        textField.setColumns(10);
        textField.setBounds(226, 11, 149, 25);
        getContentPane().add(textField);
        
        final JLabel pro = new JLabel("");
        pro.setFont(new Font("Tahoma", Font.BOLD, 16));
        pro.setBounds(10, 63, 345, 29);
        getContentPane().add(pro);
        
        stock = new JTextField();
        stock.setFont(new Font("Tahoma", Font.PLAIN, 16));
        stock.setColumns(10);
        stock.setBounds(382, 63, 97, 29);
        getContentPane().add(stock);
     
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                int r = table.getSelectedRow();
                String i = (table.getModel().getValueAt(r,0).toString());
                id = Integer.parseInt(i);
                try {
                    String c=null;
                    float m=0,s=0;
                    String query = "select * from Stock where ID='"+id+"'";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    while(rs.next())
                    {
                        pro.setText(rs.getString("Product"));
                        m = rs.getFloat("Stock");
                    }
                    pmt.close();
                    rs.close();
                    stock.setText(Float.toString(m));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(table);
        
    
        
        JButton button = new JButton("Update");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try
                {
                    String query = "UPDATE Stock SET Stock = '"+stock.getText()+"'  WHERE ID ='"+id+"'";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    pmt.executeUpdate();
                    pmt.close();
                    
                    String query1 = "UPDATE Product SET Stock = '"+stock.getText()+"'  WHERE Product ='"+pro.getText()+"'";
                    PreparedStatement pmt1 = connection.prepareStatement(query1);
                    pmt1.executeUpdate();
                    pmt1.close();
                    
                }
                catch (SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                pro.setText("");
                stock.setText("");
                try {
                    // pull data from the database 
                    Calendar cal= Calendar.getInstance();
                    SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
                    String date = d.format(cal.getTime());
                  
                    String query = "select * from Stock";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
               
            }
        });
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBounds(517, 70, 108, 26);
        getContentPane().add(button);
    }

}
