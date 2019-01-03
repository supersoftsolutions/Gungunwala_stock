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
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;

public class Staff_issue extends JInternalFrame {
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Staff_issue frame = new Staff_issue();
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
	public Staff_issue() {
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
        setBounds((w-690)/2, (h-592)/2, 690, 592);

        getContentPane().setLayout(null);
        
		final java.sql.Connection connection = Databaseconnection.connection2();

        
        JLabel label = new JLabel("Staff Issue Report");
        label.setFont(new Font("Tahoma", Font.BOLD, 20));
        label.setBounds(264, 11, 202, 25);
        getContentPane().add(label);
        
        JLabel lblProjectName = new JLabel("Project Name:");
        lblProjectName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblProjectName.setBounds(178, 56, 120, 25);
        getContentPane().add(lblProjectName);
        
        JComboBox project = new JComboBox();
        project.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
        try {
            // pull data from the database 
            
            String query = "select * from Staff_issue";
            
            PreparedStatement pmt = connection.prepareStatement(query);
            
            ResultSet rs = pmt.executeQuery();
            
            while (rs.next())
           {
             String n = rs.getString("Project_name");
             project.addItem(n);
           }
            pmt.close();
            rs.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        project.setFont(new Font("Tahoma", Font.PLAIN, 16));
        project.setBounds(325, 56, 230, 25);
        getContentPane().add(project);
        
        
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
        product.setBounds(325, 92, 230, 25);
        getContentPane().add(product);
		setBounds((w-743)/2, (h-589)/2, 743, 505);
		setClosable(true);
        
        JLabel label_2 = new JLabel("From:");
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label_2.setBounds(178, 127, 55, 25);
        getContentPane().add(label_2);
        
        JLabel label_3 = new JLabel("To:");
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label_3.setBounds(379, 127, 55, 25);
        getContentPane().add(label_3);
        
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setBounds(232, 127, 125, 25);
        getContentPane().add(dateChooser);
        
        JDateChooser dateChooser_1 = new JDateChooser();
        dateChooser_1.setDateFormatString("yyyy-MM-dd");
        dateChooser_1.setBounds(430, 127, 125, 25);
        getContentPane().add(dateChooser_1);
        
        JLabel lblProductName = new JLabel("Product Name:");
        lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblProductName.setBounds(178, 92, 120, 25);
        getContentPane().add(lblProductName);
        
        JButton button = new JButton("View");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try
				{
						if(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText().equals("")||((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText().equals("")||project.getSelectedItem().toString().equals("SELECT")||product.getSelectedItem().toString().equals("SELECT"))
	                    {
	                        JOptionPane.showMessageDialog(null,"Please Fillup Details.");
	                    }
						else
						{
							String query1 = "SELECT Staff_issue.ID,Staff_issue_detail.Job_no,Staff_issue.Project_name,Staff_issue.Date,Staff_issue_detail.Product,Staff_issue_detail.Qty FROM Staff_issue JOIN Staff_issue_detail ON Staff_issue.ID=S_id where ('"+((JTextField)dateChooser.getDateEditor().getUiComponent()).getText()+"'<= Date and Date<='"+((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText()+"') and Staff_issue_detail.Product='"+product.getSelectedItem().toString()+"' and Staff_issue.Project_name='"+project.getSelectedItem().toString()+"'";
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
        button.setBounds(314, 171, 89, 23);
        getContentPane().add(button);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 205, 707, 256);
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
