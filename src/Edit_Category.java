import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Edit_Category extends JInternalFrame {
	private JTextField category;
	String id=null;
	private JTextField name;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit_Category frame = new Edit_Category();
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
	public Edit_Category() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"Cancel"); //$NON-NLS-1$
		getRootPane().getActionMap().put("Cancel", new AbstractAction() {
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
		double height = screenSize.getHeight() - 120;
		int w = (int) (width);
		int h = (int) (height);

		setBounds((w - 417) / 2, (h - 300) / 2, 417, 300);
		setClosable(true);
		getContentPane().setLayout(null);
		
		final Connection connection = Databaseconnection.connection2();
		
		JLabel lblCategory = new JLabel("Edit Category");
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCategory.setBounds(121, 26, 154, 25);
		getContentPane().add(lblCategory);
		
		JLabel lblCategory_1 = new JLabel("Category:");
		lblCategory_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCategory_1.setBounds(71, 111, 102, 28);
		getContentPane().add(lblCategory_1);
		
		category = new JTextField();
		category.setColumns(10);
		category.setBounds(196, 108, 147, 28);
		getContentPane().add(category);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					if(category.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else 
                    {
                    	//String query="insert into Category(Name) VALUES ('"+category.getText()+"')";
                    	String query1="update Product set Category='"+category.getText()+"' where Category='"+name.getText()+"'";
		            	PreparedStatement pmt1 = connection.prepareStatement(query1);
                        pmt1.executeUpdate();
                        pmt1.close();
                        
                        
                    	
                    	String query="update Category set Name='"+category.getText()+"' where ID='"+id+"'";
		            	PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                      
                        
                        View_Category b = new View_Category();
		                JDesktopPane desktopPane = getDesktopPane();
		                desktopPane.add(b);
		                b.show();
		                dispose();
        		    
                    }
				
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(149, 172, 89, 23);
		getContentPane().add(button);
		
		name = new JTextField();
		name.setEditable(false);
		name.setColumns(10);
		name.setBounds(196, 62, 147, 28);
		getContentPane().add(name);
		
		JLabel label = new JLabel("Category:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(71, 65, 102, 28);
		getContentPane().add(label);
		
		
		try
		{
			String query5 = "select * from Category_id";
            PreparedStatement pmt5 = connection.prepareStatement(query5);
            ResultSet rs5 = pmt5.executeQuery();
		    while(rs5.next())
		    {
		    	id = rs5.getString("ID");
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
		  
        
        String query = "select * from Category where ID='"+id+"'";
        java.sql.PreparedStatement pmt = connection.prepareStatement(query);
        ResultSet rs = pmt.executeQuery();
        while(rs.next())
        {
        	name.setText(rs.getString("Name"));

        	category.setText(rs.getString("Name"));
        	
        }
        rs.close();
        pmt.close();
        
       
      
  
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		
	}

}
