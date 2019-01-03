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

public class View_material_waste extends JInternalFrame {
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
					View_material_waste frame = new View_material_waste();
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
	public View_material_waste() {
		
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
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 85, 758, 346);
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
        table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN,16));
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(table);

        final Connection connection = Databaseconnection.connection2();
        
        try {
            // pull data from the database 
            
            String query = "select * from Waste";
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
                	
                	int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
						float t = 0,t2=0;
	                    String n = null;
	                    String date =null;
	                    String name = null;
	                    String query2 = "Select * from Waste where ID="+id;
	                    PreparedStatement pmt2 = connection.prepareStatement(query2);
	                    ResultSet rs2 = pmt2.executeQuery();
	                    
	                    while(rs2.next())
	                    {
	                        name = rs2.getString("Product");
	                        t = rs2.getFloat("Qty");
	                    }
	                    rs2.close();pmt2.close();
	                   
	                    String query = "delete from Waste where ID="+id;
	                    PreparedStatement pmt = connection.prepareStatement(query);
	                    pmt.executeUpdate();
	                    pmt.close();
	                    
	                                               
	            		String query4 = "UPDATE Waste set Stock =Stock+"+t+" where product='"+name+"'";
	                    PreparedStatement pmt4 = connection.prepareStatement(query4);
	                    pmt4.executeUpdate();
	                    pmt4.close();
	                    
	                    String query41 = "UPDATE Waste set Stock =Stock+"+t+" where product='"+name+"'";
	                    PreparedStatement pmt41 = connection.prepareStatement(query41);
	                    pmt41.executeUpdate();
	                    pmt41.close();
					}
                    
                }
                catch (SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                View_material_waste b = new View_material_waste();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
                dispose();
            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDelete.setBounds(30, 15, 117, 25);
        getContentPane().add(btnDelete);
        
        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                try {
                    
                    // pull data from the database 
                    String query = "select * from Waste where Product like '%"+textField.getText()+"%' or ID like '%"+textField.getText()+"%' or Date like '%"+textField.getText()+"%' or Qty like '%"+textField.getText()+"%'";
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
        textField.setBounds(328, 49, 135, 25);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JLabel lblMaterialWaste = new JLabel("Material Waste");
        lblMaterialWaste.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblMaterialWaste.setBounds(303, 15, 175, 24);
        getContentPane().add(lblMaterialWaste);
        }

}
