import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;

public class Add_payment extends JInternalFrame {
	private JTextField amount;
	private JTextField cheque;
    private JComboBox type;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_payment frame = new Add_payment();
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
	public Add_payment() {
		getContentPane().setLayout(null);getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
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
		setBounds((w-450)/2, (h-466)/2, 450, 388);
		getContentPane().setLayout(null);
		
		final Connection connection = Databaseconnection.connection2();

		
		JLabel lblPayment = new JLabel("Payment");
		lblPayment.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPayment.setBounds(132, 11, 209, 25);
		getContentPane().add(lblPayment);
		
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAmount.setBounds(10, 153, 87, 25);
		getContentPane().add(lblAmount);
		
		amount = new JTextField();
		amount.setColumns(10);
		amount.setBounds(135, 153, 269, 28);
		getContentPane().add(amount);
		
		JLabel lblCreditorName = new JLabel("Crediter Name:");
		lblCreditorName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCreditorName.setBounds(10, 64, 115, 20);
		getContentPane().add(lblCreditorName);
		
		JComboBox name = new JComboBox();
		name.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
		try
		{
			String query5 = "select * from Creditor";
            PreparedStatement pmt5 = connection.prepareStatement(query5);
            ResultSet rs5 = pmt5.executeQuery();
		    while(rs5.next())
		    {
		    	name.addItem(rs5.getString("Name"));
            }
		    rs5.close();
		    pmt5.close();
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		name.setBounds(135, 63, 269, 28);
		getContentPane().add(name);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDate.setBounds(10, 108, 87, 25);
		getContentPane().add(lblDate);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(132, 108, 272, 28);
		getContentPane().add(dateChooser);
		dateChooser.setDateFormatString("yyyy-MM-dd");
	        Calendar cal = Calendar.getInstance();
	        Date d = null;
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        String s = dateFormat.format(cal.getTime());
	        try
	        {
	            d = (Date) dateFormat.parse(s);
	            dateChooser.setDate(d);
	            //date.setMaxSelectableDate(d);
	           // dateChooser.setMinSelectableDate(d);
	        }
	        catch (ParseException e1)
	        {
	            e1.printStackTrace();
	        }
		
		JLabel lblType = new JLabel("Type:");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblType.setBounds(10, 203, 115, 20);
		getContentPane().add(lblType);
		
		
		 type = new JComboBox();
		type.setModel(new DefaultComboBoxModel(new String[] {"Cash", "Cheque"}));
		type.setFont(new Font("Tahoma", Font.PLAIN, 16));
		type.setBounds(135, 202, 269, 28);
		getContentPane().add(type);
		
		
		
		JLabel lblChequeNo = new JLabel("Cheque No:");
		lblChequeNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChequeNo.setBounds(10, 257, 115, 20);
		getContentPane().add(lblChequeNo);
		
		cheque = new JTextField();
		cheque.setEnabled(false);
		cheque.setColumns(10);
		cheque.setBounds(135, 255, 269, 28);
		getContentPane().add(cheque);
		
	        type.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	                if(type.getSelectedItem().toString().equals("Cash"))
	                {
	                    cheque.setEnabled(false);
	                }
	                else
	                {
	                    cheque.setEnabled(true);
	                }
	            }
	        });
		
		

		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 try
	                {
	                    if(type.getSelectedItem().toString().equals("Cash"))
	                    {
	                        float cb1 = 0;
	                        String query3="select * from Creditor where Name = '"+name.getSelectedItem().toString()+"'";
	                        PreparedStatement pmt3 = connection.prepareStatement(query3);
	                        ResultSet rs3 = pmt3.executeQuery();
	                        while(rs3.next())
	                        {
	                            cb1 = rs3.getFloat("Balance");
	                        }
	                        pmt3.close();
	                        rs3.close();
	                        
	                        cb1 -= (Integer.parseInt(amount.getText()));
	                        
	                        String query4="update Creditor set Balance='"+cb1+"' where Name = '"+name.getSelectedItem().toString()+"'";
	                        PreparedStatement pmt4 = connection.prepareStatement(query4);
	                        pmt4.executeUpdate();
	                        pmt4.close();
	                        
	                        String query5="insert into Payment (Name, Date, Amount, Type) VALUES ('"+name.getSelectedItem().toString()+"','"+((JTextField)dateChooser.getDateEditor().getUiComponent()).getText()+"','"+amount.getText()+"','"+type.getSelectedItem().toString()+"')";
	                        PreparedStatement pmt5 = connection.prepareStatement(query5);
	                        pmt5.executeUpdate();
	                        pmt5.close();
	                    }
	                    else {
	                        float cb1 = 0;
	                        String query3="select * from Creditor where Name = '"+name.getSelectedItem().toString()+"'";
	                        PreparedStatement pmt3 = connection.prepareStatement(query3);
	                        ResultSet rs3 = pmt3.executeQuery();
	                        while(rs3.next())
	                        {
	                            cb1 = rs3.getFloat("Balance");
	                        }
	                        pmt3.close();
	                        rs3.close();
	                        
	                        cb1 -= (Integer.parseInt(amount.getText()));
	                        
	                        String query4="update Creditor set Balance='"+cb1+"' where Name = '"+name.getSelectedItem().toString()+"'";
	                        PreparedStatement pmt4 = connection.prepareStatement(query4);
	                        pmt4.executeUpdate();
	                        pmt4.close();
	                        
	                        String query5="insert into Payment (Name, Date, Amount, Type, Cheque) VALUES ('"+name.getSelectedItem().toString()+"','"+((JTextField)dateChooser.getDateEditor().getUiComponent()).getText()+"','"+amount.getText()+"','"+type.getSelectedItem().toString()+"','"+cheque.getText()+"')";
	                        PreparedStatement pmt5 = connection.prepareStatement(query5);
	                        pmt5.executeUpdate();
	                        pmt5.close();
	                    }
	                    
	                }
	               catch (Exception e) {
	                   e.printStackTrace();
	               }
	                amount.setText("");
	                cheque.setText("");
	                type.setSelectedIndex(0);
	                name.setSelectedIndex(0);
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(132, 309, 89, 28);
		getContentPane().add(button);
		
		


	}
}
