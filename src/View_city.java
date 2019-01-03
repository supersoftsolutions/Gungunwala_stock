import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;

public class View_city extends JInternalFrame {
	private JTextField city;
	private JTable table;
	String id=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_city frame = new View_city();
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
	public View_city() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {
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
        
        setBounds((w-592)/2, (h-496)/2,592, 496);
		getContentPane().setLayout(null);
		setClosable(true);
		final Connection connection1 = Databaseconnection.connection2();
		
		JLabel label = new JLabel("View City");
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setBounds(124, 26, 156, 24);
		getContentPane().add(label);
		
		
		
		city = new JTextField();
		city.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
try {
                    
                    // pull data from the database 
                    
                    String query = "select * from City where ID like '%"+city.getText()+"%' or Country_name like '%"+city.getText()+"%' or State_name like '%"+city.getText()+"%' or City_name like '%"+city.getText()+"%'";
                    java.sql.PreparedStatement pmt = connection1.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                    
                   
                } catch (SQLException ae) {
                    ae.printStackTrace();
                }
			}
		});
		city.setFont(new Font("Tahoma", Font.PLAIN, 16));
		city.setColumns(10);
		city.setBounds(124, 73, 124, 20);
		getContentPane().add(city);
		
		JButton button = new JButton("Delete");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					
					String query = "delete from City where ID="+id;
                    java.sql.PreparedStatement pmt = connection1.prepareStatement(query);
                    pmt.executeUpdate();
                    pmt.close();
                    
                    String query1 = "select * from City";
        		    java.sql.PreparedStatement pmt1 = connection1.prepareStatement(query1);
        		    ResultSet rs = pmt1.executeQuery();
        		    table.setModel(DbUtils.resultSetToTableModel(rs));
                 
				}
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(291, 72, 89, 23);
		getContentPane().add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 116, 556, 339);
		getContentPane().add(scrollPane);
		
		TableModel model = null;
		table = new JTable(model){

	            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int colIndex) {
	            return false; //Disallow the editing of any cell
	            }
	            };
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row =  table.convertRowIndexToModel(table.getSelectedRow());
	            int col = table.getColumnModel().getColumnIndex("ID");
	            id =table.getModel().getValueAt(row, col).toString();
			}
		});

		scrollPane.setViewportView(table);
		
		try
		
		{
			 String query = "select * from City";
			    java.sql.PreparedStatement pmt = connection1.prepareStatement(query);
			    ResultSet rs = pmt.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		
       catch(Exception e){
    	   
       }
	}
}