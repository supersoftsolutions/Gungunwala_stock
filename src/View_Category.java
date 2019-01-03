import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;


public class View_Category extends JInternalFrame {
	private JTextField textField;
	private JTable table;
	String id=null,category=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_Category frame = new View_Category();
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
	public View_Category() {
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
        setClosable(true);
        setBounds((w-455)/2, (h-489)/2, 455, 489);
		getContentPane().setLayout(null);
		
		final java.sql.Connection connection = Databaseconnection.connection2();

		
		JLabel lblViewCategory = new JLabel("View Category");
		lblViewCategory.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblViewCategory.setBounds(118, 11, 219, 24);
		getContentPane().add(lblViewCategory);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				try
				{
					String query = "select * from Category where ID like '%"+textField.getText()+"%' or Name like '%"+textField.getText()+"%'";
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(128, 50, 143, 28);
		getContentPane().add(textField);
		
		JButton button = new JButton("Delete");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
						int i=0;
						
						String query5 = "select * from Product where Category='"+category+"'";
			            PreparedStatement pmt5 = connection.prepareStatement(query5);
			            ResultSet rs5 = pmt5.executeQuery();
					    while(rs5.next())
					    {
					    	i++;
					    }
					    rs5.close();
					    pmt5.close();
					    
					    if(i==0)
					    {
					    	String query = "delete from Category where ID='"+id+"'";
		                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
		                    pmt.executeUpdate();
		                    pmt.close();
		                    
		                    String query1 = "select * from Category";
		        		    java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
		        		    ResultSet rs = pmt1.executeQuery();
		        		    table.setModel(DbUtils.resultSetToTableModel(rs));
					    }
					    else
					    {
					    	JOptionPane.showMessageDialog(null, "You cannot delete this category");
					    }
					
					
                 
				}
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(311, 50, 89, 28);
		getContentPane().add(button);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Edit_Category b = new Edit_Category();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
                dispose();
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnEdit.setBounds(10, 55, 89, 28);
		getContentPane().add(btnEdit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 100, 419, 348);
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
	            int col1 = table.getColumnModel().getColumnIndex("Name");
	            id =table.getModel().getValueAt(row, col).toString();
	            category=table.getModel().getValueAt(row, col1).toString();
	            
	          try{  
	            String query = "delete from Category_id";
                java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                pmt.executeUpdate();
                pmt.close();
                
                String query1 = "insert into Category_id(ID) values ('"+id+"')";
    		    java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
    		    pmt1.executeUpdate();
	            pmt1.close();
	          }
	          catch(Exception ae)
	          {
	        	  ae.printStackTrace();
	          }
			}
		});

		scrollPane.setViewportView(table);
		
		try
		
		{
			 String query = "select * from Category";
			    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
			    ResultSet rs = pmt.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			 
		}
		
       catch(Exception e){
    	   
       }
		
		
		
		
	}
}
