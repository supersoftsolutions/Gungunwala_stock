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

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;
import javax.swing.JLabel;
import java.awt.Color;

public class View_bom extends JInternalFrame {
	private JTable table;
    private TableModel model;
    public int id;
    private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_bom frame = new View_bom();
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
	public View_bom() {
		
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
		
		final Connection connection = Databaseconnection.connection2();
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-120;
        int w = (int)(width);
        int h = (int)(height);
        setClosable(true);
        setBounds((w-774)/2, (h-461)/2, 774, 461);
        getContentPane().setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 102, 758, 329);
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
                    
                    try
                    {
                    	String query = "delete from B_id";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        String query1 = "insert into B_id(ID) values('"+id+"')";
                        PreparedStatement pmt1 = connection.prepareStatement(query1);
                        pmt1.executeUpdate();
                        pmt1.close();
                    }
                    catch(Exception ae)
                    {
                    	ae.printStackTrace();
                    }
                }
            });
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(table);

        
        
        try {
            // pull data from the database 
            
            String query = "select * from BOM";
            PreparedStatement pmt = connection.prepareStatement(query);
            ResultSet rs = pmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            pmt.close();
            rs.close();
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               
                        try
                        {
                          
                                    String query = "delete from BOM where ID="+id;
                                    PreparedStatement pmt = connection.prepareStatement(query);
                                    pmt.executeUpdate();
                                    pmt.close();
                                    
                                   String query4 = "delete from BOM_Detail where S_id="+id;
                                   PreparedStatement pmt4 = connection.prepareStatement(query4);
                                   pmt4.executeUpdate();
                                   pmt4.close();
                               
                           
                        }
                        catch (SQLException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        View_bom b = new View_bom();
                        JDesktopPane desktopPane = getDesktopPane();
                        desktopPane.add(b);
                        b.show();
                        dispose();
            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDelete.setBounds(0, 41, 135, 25);
        getContentPane().add(btnDelete);
        
        JButton btnBillDetails = new JButton("Detail");
        btnBillDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try
                {
                    String n = null;
                    String query3 = "select * from BOM where ID ='"+id+"'";
                    PreparedStatement pmt3 = connection.prepareStatement(query3);
                    ResultSet rs3 = pmt3.executeQuery();
                    while(rs3.next())
                    {
                         n = rs3.getString("Name");                                            
                    }
                    rs3.close();
                    
                    Detail_bom s = new Detail_bom();
                    s.bill.setText(Integer.toString(id));
                    s.name.setText(n);
                    
                    String query = "select * from BOM_Detail where S_id ="+id+"";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    s.table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                    JDesktopPane desktopPane = getDesktopPane();
                    desktopPane.add(s);
                    s.show();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        btnBillDetails.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnBillDetails.setBounds(555, 40, 125, 25);
        getContentPane().add(btnBillDetails);
        
        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                
                try
                {
                   Edit_bom s = new Edit_bom();
                    JDesktopPane desktopPane = getDesktopPane();
                    desktopPane.add(s);
                    s.show();
                    dispose();
                    
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        btnEdit.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnEdit.setBounds(177, 41, 122, 25);
        getContentPane().add(btnEdit);
        
        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                try {
                    // pull data from the database 
                    String query = "select * from BOM where Name like '%"+textField.getText()+"%' or ID like '%"+textField.getText()+"%'";
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
        textField.setBounds(343, 66, 135, 25);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JLabel lblViewBom = new JLabel("View BOM");
        lblViewBom.setForeground(Color.BLACK);
        lblViewBom.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblViewBom.setBounds(343, 11, 157, 38);
        getContentPane().add(lblViewBom);
        
    }

}
