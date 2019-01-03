import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;

import appview.Databaseconnection;

public class Detail_bom extends JInternalFrame {
	public JLabel bill;
    public int id1; 
    public JTable table;
    public TableModel model;
    private int id;
    public JLabel name;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Detail_bom frame = new Detail_bom();
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
	public Detail_bom() {
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"Cancel"); //$NON-NLS-1$
		getRootPane().getActionMap().put("Cancel", new AbstractAction() { //$NON-NLS-1$
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
        double height = screenSize.getHeight()-120;
        int w = (int)(width);
        int h = (int)(height);
        setClosable(true);
        setBounds((w-774)/2, (h-400)/2, 774, 460);
        getContentPane().setLayout(null);
        JLabel lblBillNo = new JLabel("NO");
        lblBillNo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblBillNo.setBounds(10, 39, 88, 25);
        getContentPane().add(lblBillNo);
        
        bill = new JLabel();
        bill.setFont(new Font("Tahoma", Font.BOLD, 16));
        bill.setBounds(96, 39, 88, 25);
        getContentPane().add(bill);
        final Connection connection = Databaseconnection.connection2();
        
        JLabel label = new JLabel("Name");
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setBounds(205, 68, 88, 25);
        getContentPane().add(label);
        
        JLabel lblWholesaleBill = new JLabel("BOM Detail");
        lblWholesaleBill.setForeground(Color.BLACK);
        lblWholesaleBill.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblWholesaleBill.setBounds(288, 11, 206, 38);
        getContentPane().add(lblWholesaleBill);
        
        name = new JLabel();
        name.setFont(new Font("Tahoma", Font.BOLD, 16));
        name.setBounds(353, 62, 294, 31);
        getContentPane().add(name);
        
      
            
                    JScrollPane scrollPane = new JScrollPane();
                    scrollPane.setBounds(10, 104, 738, 315);
                    getContentPane().add(scrollPane);
                    
                    table = new JTable(model){

                        public boolean isCellEditable(int rowIndex, int colIndex) {
                        return false; //Disallow the editing of any cell
                        }
                        };
                    table.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent arg0) {
                            int r = table.getSelectedRow();
                            String i = (table.getModel().getValueAt(r,0).toString());
                            id = Integer.parseInt(i);
                        }
                    });
                    table.setFont(new Font("Tahoma", Font.PLAIN, 16));
                    table.getTableHeader().setReorderingAllowed(false);
                    scrollPane.setViewportView(table);
    }

}
