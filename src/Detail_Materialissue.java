import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

public class Detail_Materialissue extends JInternalFrame {
	public JTable table;
	public JLabel no,name,total;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Detail_purchase frame = new Detail_purchase();
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
	public Detail_Materialissue() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
	        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
	            public void actionPerformed(ActionEvent e)
	            {
	            	
	            	int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
	            	
	            	dispose();
					}
	            }
	        });
	        
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        double width = screenSize.getWidth();
	        double height = screenSize.getHeight()-120;
	        int w = (int)(width);
	        int h = (int)(height);
	        
			setBounds((w-930)/2, (h-444)/2,930, 444);
			getContentPane().setLayout(null);
			setClosable(true);
		JLabel lblMaterialIssue = new JLabel("Service Material");
		lblMaterialIssue.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMaterialIssue.setBounds(362, 11, 200, 25);
		getContentPane().add(lblMaterialIssue);
		
		
		
		JLabel label_2 = new JLabel("Name");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_2.setBounds(169, 71, 65, 14);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("Total");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_3.setBounds(623, 71, 110, 14);
		getContentPane().add(label_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 119, 894, 258);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNo = new JLabel("No:");
		lblNo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNo.setBounds(10, 71, 56, 14);
		getContentPane().add(lblNo);
		
		 no = new JLabel("");
		no.setFont(new Font("Tahoma", Font.BOLD, 16));
		no.setBounds(59, 71, 100, 14);
		getContentPane().add(no);
		
		 name = new JLabel("");
		name.setFont(new Font("Tahoma", Font.BOLD, 16));
		name.setBounds(244, 71, 318, 14);
		getContentPane().add(name);
		
		
		
		 total = new JLabel("");
		total.setFont(new Font("Tahoma", Font.BOLD, 16));
		total.setBounds(754, 71, 150, 14);
		getContentPane().add(total);

	}

}
