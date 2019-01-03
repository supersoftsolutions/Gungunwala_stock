import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
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
import javax.swing.JButton;
import java.awt.event.ActionListener;

public class View_payment extends JInternalFrame {
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
					View_payment frame = new View_payment();
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
	public View_payment() {
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
        setBounds((w-815)/2, (h-408)/2, 815, 408);
		getContentPane().setLayout(null);
		
		JLabel lblViewUnit = new JLabel("View Payment");
		lblViewUnit.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblViewUnit.setBounds(318, 11, 147, 24);
		getContentPane().add(lblViewUnit);
		
		final java.sql.Connection connection = Databaseconnection.connection2();
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				try
				{
                    String query = "select * from Payment where ID like '%"+textField.getText()+"%' or Name like '%"+textField.getText()+"%' or Date like '%"+textField.getText()+"%' or Amount like '%"+textField.getText()+"%' or Cheque like '%"+textField.getText()+"%'";
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
		textField.setBounds(318, 46, 143, 28);
		getContentPane().add(textField);
		
		  JButton button = new JButton("Delete");
		    button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		  try
                      {
		    			  int dialogButton = JOptionPane.YES_NO_OPTION;
							int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
							if(dialogResult == JOptionPane.YES_OPTION)
							{
                          float amt = 0;
                          String n = null;
                        
                          String query2 = "Select * from Payment where ID='"+id+"'";
                          PreparedStatement pmt2 = connection.prepareStatement(query2);
                          ResultSet rs2 = pmt2.executeQuery();
                          
                          while(rs2.next())
                          {
                              n = rs2.getString("Name");
                              amt = rs2.getFloat("Amount");
                          }
                          rs2.close();pmt2.close();
                          
                              float cb1 = 0;
                              String query3="select * from Creditor where Name = '"+n+"'";
                              PreparedStatement pmt3 = connection.prepareStatement(query3);
                              ResultSet rs3 = pmt3.executeQuery();
                              while(rs3.next())
                              {
                                  cb1 = rs3.getFloat("Balance");
                              }
                              pmt3.close();
                              rs3.close();
                              
                              cb1 += amt;
                              
                              String query4="update Creditor set Balance='"+cb1+"' where Name = '"+n+"'";
                              PreparedStatement pmt4 = connection.prepareStatement(query4);
                              pmt4.executeUpdate();
                              pmt4.close();
                              
                              String query = "delete from Payment where ID='"+id+"'";
                              PreparedStatement pmt = connection.prepareStatement(query);
                              pmt.executeUpdate();
                              pmt.close();
							}
                          
                      }
                      catch (SQLException ae)
                      {
                          // TODO Auto-generated catch block
                          ae.printStackTrace();
                      }
                      View_payment b = new View_payment();
                      JDesktopPane desktopPane = getDesktopPane();
                      desktopPane.add(b);
                      
                      b.show();
                      dispose();
                      
		    	}
		    });
		    button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		    button.setBounds(573, 46, 89, 28);
		    getContentPane().add(button);
		    
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 100, 779, 267);
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
			 String query = "select * from Payment";
			    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
			    ResultSet rs = pmt.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			
		}
		
       catch(Exception e){
    	   
       }
	}

}
