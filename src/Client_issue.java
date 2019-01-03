import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.event.ActionListener;

public class Client_issue extends JInternalFrame {
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_issue frame = new Client_issue();
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
	public Client_issue() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
	        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
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
        setBounds((w-726)/2, (h-592)/2, 749, 491);
        setClosable(true);
        getContentPane().setLayout(null);
        
		final java.sql.Connection connection = Databaseconnection.connection2();

        
        JLabel lblClientIssueReport = new JLabel("Client Issue Report");
        lblClientIssueReport.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblClientIssueReport.setBounds(264, 23, 202, 25);
        getContentPane().add(lblClientIssueReport);
        
        JLabel label_1 = new JLabel("Product Name:");
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label_1.setBounds(175, 68, 120, 25);
        getContentPane().add(label_1);
        
        JComboBox product = new JComboBox();
        product.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
        try {
            // pull data from the database 
            
            String query = "select * from Product";
            
            PreparedStatement pmt = connection.prepareStatement(query);
            
            ResultSet rs = pmt.executeQuery();
            
            while (rs.next())
           {
             String n = rs.getString("Product");
               product.addItem(n);
           }
            pmt.close();
            rs.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        product.setFont(new Font("Tahoma", Font.PLAIN, 16));
        product.setBounds(322, 68, 230, 25);
        getContentPane().add(product);
        
        JLabel label_2 = new JLabel("From:");
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label_2.setBounds(175, 103, 55, 25);
        getContentPane().add(label_2);
        
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setBounds(229, 103, 125, 25);
        getContentPane().add(dateChooser);
        
        JLabel label_3 = new JLabel("To:");
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label_3.setBounds(376, 103, 55, 25);
        getContentPane().add(label_3);
        
        JDateChooser dateChooser_1 = new JDateChooser();
        dateChooser_1.setDateFormatString("yyyy-MM-dd");
        dateChooser_1.setBounds(427, 103, 125, 25);
        getContentPane().add(dateChooser_1);
        
        JButton button = new JButton("View");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try
				{
						if(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText().equals("")||((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText().equals("")||product.getSelectedItem().toString().equals("SELECT"))
	                    {
	                        JOptionPane.showMessageDialog(null,"Please Fillup Details.");
	                    }
						else
						{
							String query1 = "SELECT Material_issue.ID,Material_issue.Sleep_no,Material_issue.Client_name,Material_issue.Date,Material_Issue_Details.Product,Material_Issue_Details.Qty FROM Material_issue JOIN Material_Issue_Details ON Material_issue.ID=M_id where ('"+((JTextField)dateChooser.getDateEditor().getUiComponent()).getText()+"'<= Date and Date<='"+((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText()+"') and Material_Issue_Details.Product='"+product.getSelectedItem().toString()+"'";
							PreparedStatement pmt1 = connection.prepareStatement(query1);
							ResultSet rs1 = pmt1.executeQuery();
							table.setModel(DbUtils.resultSetToTableModel(rs1));
							pmt1.close();
							rs1.close();
							
							
						}
					}
					    
				
				catch (SQLException ae) {
		               ae.printStackTrace();
		           }
        	}
        });
        button.setFont(new Font("Tahoma", Font.PLAIN, 16));
        button.setBounds(311, 147, 89, 23);
        getContentPane().add(button);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 192, 707, 256);
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
            
             table.getTableHeader().setReorderingAllowed(false);

     		scrollPane.setViewportView(table);
     		
        

	}

}
