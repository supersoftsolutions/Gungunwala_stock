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

public class View_creditor extends JInternalFrame {
	private JTextField textField;
	private JTable table;
	String id=null;
	String creditor_name=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_creditor frame = new View_creditor();
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
	public View_creditor() {
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

		
		JLabel lblViewProduct = new JLabel("View Creditor");
		lblViewProduct.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblViewProduct.setBounds(298, 11, 177, 24);
		getContentPane().add(lblViewProduct);
		
		JButton button = new JButton("Edit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Edit_creditor b = new Edit_creditor();
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
	                    
	                    String query = "select ID,Name,Balance from Creditor where ID like '%"+textField.getText()+"%' or Name like '%"+textField.getText()+"%' or Balance like '%"+textField.getText()+"%' ";
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
						int c=0;
	                    
						try
	                    {
	                    	String query5 = "select * from PO where Name='"+creditor_name+"'";
				            PreparedStatement pmt5 = connection.prepareStatement(query5);
				            ResultSet rs5 = pmt5.executeQuery();
						    while(rs5.next())
						    {
						    	c++;
						    }
						    rs5.close();
						    pmt5.close();
	                    }
	                    catch(Exception ae)
	                    {
	                    	ae.printStackTrace();
	                    }
						
						try
	                    {
	                    	String query5 = "select * from Payment where Name='"+creditor_name+"'";
				            PreparedStatement pmt5 = connection.prepareStatement(query5);
				            ResultSet rs5 = pmt5.executeQuery();
						    while(rs5.next())
						    {
						    	c++;
						    }
						    rs5.close();
						    pmt5.close();
	                    }
	                    catch(Exception ae)
	                    {
	                    	ae.printStackTrace();
	                    }
						
						try
	                    {
	                    	String query5 = "select * from Purchase where Creditor='"+creditor_name+"'";
				            PreparedStatement pmt5 = connection.prepareStatement(query5);
				            ResultSet rs5 = pmt5.executeQuery();
						    while(rs5.next())
						    {
						    	c++;
						    }
						    rs5.close();
						    pmt5.close();
	                    }
	                    catch(Exception ae)
	                    {
	                    	ae.printStackTrace();
	                    }	
						
	                   
	                    if(c==0)
	                    {
	                       String query = "delete from Creditor where ID="+id;
	                       PreparedStatement pmt = connection.prepareStatement(query);
	                       pmt.executeUpdate();
	                       pmt.close();
	                       
	                       String query5 = "delete from Crediter_OpeningBalance where Name='"+creditor_name+"'";
	                       PreparedStatement pmt5 = connection.prepareStatement(query5);
	                       pmt5.executeUpdate();
	                       pmt5.close();
	                    }
	                    else
	                    {
	                        JOptionPane.showMessageDialog(null,"Creditor Can not be deleted.");
	                    }
					}
                    
                   
                }
                catch (SQLException ae)
                {
                    // TODO Auto-generated catch block
                    ae.printStackTrace();
                }
                View_creditor b = new View_creditor();
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
	            int col1 = table.getColumnModel().getColumnIndex("Name");
	            creditor_name=table.getModel().getValueAt(row, col1).toString();
	            id =table.getModel().getValueAt(row, col).toString();
	          try{  
	            String query = "delete from Creditor_id";
                java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                pmt.executeUpdate();
                pmt.close();
                
                String query1 = "insert into Creditor_id(ID) values ('"+id+"')";
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
			 String query = "select ID,Name,Balance from Creditor";
			    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
			    ResultSet rs = pmt.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			    
			    
			 
		}
		
       catch(Exception e){
    	   
       }
		
		JButton btnDetail = new JButton("Detail");
	    btnDetail.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		Detail_creditor b = new Detail_creditor();
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
