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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
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

public class View_client extends JInternalFrame {
	private JTextField textField;
	private JTable table;
	String id=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_client frame = new View_client();
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
	public View_client() {
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
        setBounds((w-800)/2, (h-485)/2, 800, 485);
		getContentPane().setLayout(null);
		
		final java.sql.Connection connection = Databaseconnection.connection2();

		
		JLabel lblViewProduct = new JLabel("View Client");
		lblViewProduct.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblViewProduct.setBounds(298, 11, 177, 24);
		getContentPane().add(lblViewProduct);
		
		JButton button = new JButton("Edit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Edit_client b = new Edit_client();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
                dispose();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(128, 53, 89, 28);
		getContentPane().add(button);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 textField.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyTyped(KeyEvent arg0) {
	                try {
	                    
	                    String query = "select ID,Name from Client where ID like '%"+textField.getText()+"%' or Name like '%"+textField.getText()+"%'";
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
		textField.setBounds(308, 53, 143, 28);
		getContentPane().add(textField);
		
		JButton button_1 = new JButton("Delete");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    try
                {
			    	int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
						String cat = null,bill=null;
	                    
	                    String query1 = "Select * from Client where ID="+id;
	                    PreparedStatement pmt1 = connection.prepareStatement(query1);
	                    ResultSet rs1 = pmt1.executeQuery();
	                    
	                    while(rs1.next())
	                    {
	                        cat = rs1.getString("Name");
	                    }
	                    rs1.close();
	                    pmt1.close();
	                    int c=0;
	                    
	                   
	                    if(c==0)
	                    {
	                       String query = "delete from Client where ID="+id;
	                       PreparedStatement pmt = connection.prepareStatement(query);
	                       pmt.executeUpdate();
	                       pmt.close();
	                    }
	                    else
	                    {
	                        JOptionPane.showMessageDialog(null,"Client Can not be deleted.");
	                    }
					}
                    
                   
                }
                catch (SQLException ae)
                {
                    // TODO Auto-generated catch block
                    ae.printStackTrace();
                }
                View_client b = new View_client();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                
                b.show();
                dispose();
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button_1.setBounds(538, 53, 89, 28);
		getContentPane().add(button_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 100, 764, 344);
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
	          try{  
	            String query = "delete from Client_id";
                java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                pmt.executeUpdate();
                pmt.close();
                
                String query1 = "insert into Client_id(ID) values ('"+id+"')";
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
			 String query = "select ID,Name from Client";
			    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
			    ResultSet rs = pmt.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			    
			    
			 
		}
		
       catch(Exception e){
    	   
       }
		
		JButton btnDetail = new JButton("Detail");
	    btnDetail.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		Detail_client b = new Detail_client();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
            }
	    });
	    btnDetail.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    btnDetail.setBounds(29, 58, 89, 23);
	    getContentPane().add(btnDetail);

	}

}
