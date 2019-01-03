import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;


import javax.swing.JMenuItem;
import java.awt.event.ActionListener;

public class Home extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
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
	public Home() {
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
		double height = screenSize.getHeight();
		int w = (int) (width);
		int h = (int) (height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, w, h);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		final JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		desktopPane.setBounds(0, 0, w, h - 120);
		contentPane.add(desktopPane);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 10, 10);
		desktopPane.add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 10, 10);
		desktopPane.add(panel_1);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		setJMenuBar(menuBar);

		JMenu mnMaster = new JMenu("Master");
		mnMaster.setFont(new Font("Calibri", Font.BOLD, 25));
		menuBar.add(mnMaster);
		
		JMenu mnCategory = new JMenu("Category");
		mnCategory.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaster.add(mnCategory);
		
		JMenuItem mntmAddCategory = new JMenuItem("Add Category");
		mntmAddCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_Category c = new Add_Category();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddCategory.setFont(new Font("Calibri", Font.BOLD, 20));
		mnCategory.add(mntmAddCategory);
		
		JMenuItem mntmViewCategory = new JMenuItem("View Category");
		mntmViewCategory.setFont(new Font("Calibri", Font.BOLD, 20));
		mntmViewCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View_Category c = new View_Category();
				desktopPane.add(c);
				c.show();
			}
		});
		mnCategory.add(mntmViewCategory);
		
		JMenu mnUnit = new JMenu("Unit");
		mnUnit.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaster.add(mnUnit);
		
		JMenuItem mntmAddUnit = new JMenuItem("Add Unit");
		mntmAddUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_unit c = new Add_unit();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddUnit.setFont(new Font("Calibri", Font.BOLD, 20));
		mnUnit.add(mntmAddUnit);
		
		JMenuItem mntmViewUnit = new JMenuItem("View Unit");
		mntmViewUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View_unit c = new View_unit();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewUnit.setFont(new Font("Calibri", Font.BOLD, 20));
		mnUnit.add(mntmViewUnit);
		
		JMenu mnProduct = new JMenu("Product");
		mnProduct.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaster.add(mnProduct);
		
		JMenuItem mntmAddProduct = new JMenuItem("Add Product");
		mntmAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add_product c = new Add_product();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddProduct.setFont(new Font("Calibri", Font.BOLD, 20));
		mnProduct.add(mntmAddProduct);
		
		JMenuItem mntmViewProduct = new JMenuItem("View Product");
		mntmViewProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View_product c = new View_product();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewProduct.setFont(new Font("Calibri", Font.BOLD, 20));
		mnProduct.add(mntmViewProduct);
		
		JMenu mnCountry = new JMenu("Country");
		mnCountry.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaster.add(mnCountry);
		
		JMenuItem mntmAddCountry = new JMenuItem("Add Country");
		mntmAddCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add_country c = new Add_country();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddCountry.setFont(new Font("Calibri", Font.BOLD, 20));
		mnCountry.add(mntmAddCountry);
		
		JMenuItem mntmViewCountry = new JMenuItem("View Country");
		mntmViewCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View_country c = new View_country();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewCountry.setFont(new Font("Calibri", Font.BOLD, 20));
		mnCountry.add(mntmViewCountry);
		
		JMenu mnState = new JMenu("State");
		
		mnState.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaster.add(mnState);
		
		JMenuItem mntmAddState = new JMenuItem("Add State");
		mntmAddState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add_State c = new Add_State();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddState.setFont(new Font("Calibri", Font.BOLD, 20));
		mnState.add(mntmAddState);
		
		JMenuItem mntmViewState = new JMenuItem("View State");
		mntmViewState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View_State c = new View_State();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewState.setFont(new Font("Calibri", Font.BOLD, 20));
		mnState.add(mntmViewState);
		
		JMenu mnCity = new JMenu("City");
		mnCity.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaster.add(mnCity);
		
		JMenuItem mntmAddCity = new JMenuItem("Add City");
		mntmAddCity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add_city c = new Add_city();
				desktopPane.add(c);
				c.show();
			}
		});
		
		mntmAddCity.setFont(new Font("Calibri", Font.BOLD, 20));
		mnCity.add(mntmAddCity);
		
		JMenuItem mntmViewCity = new JMenuItem("View City");
		mntmViewCity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View_city c = new View_city();
				desktopPane.add(c);
				c.show();
			}
		});
		
		mntmViewCity.setFont(new Font("Calibri", Font.BOLD, 20));
		mnCity.add(mntmViewCity);
		
		JMenu mnCreditor = new JMenu("Creditor");
		mnCreditor.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaster.add(mnCreditor);
		
		JMenuItem mntmAddCreditor = new JMenuItem("Add Creditor");
		mntmAddCreditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add_creditor c = new Add_creditor();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddCreditor.setFont(new Font("Calibri", Font.BOLD, 20));
		mnCreditor.add(mntmAddCreditor);
		
		JMenuItem mntmViewCreditor = new JMenuItem("View Creditor");
		mntmViewCreditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View_creditor c = new View_creditor();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewCreditor.setFont(new Font("Calibri", Font.BOLD, 20));
		mnCreditor.add(mntmViewCreditor);
		
		JMenu mnClient = new JMenu("Client");
		mnClient.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaster.add(mnClient);
		
		JMenuItem mntmAddClient = new JMenuItem("Add Client");
		mntmAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_client c = new Add_client();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddClient.setFont(new Font("Calibri", Font.BOLD, 20));
		mnClient.add(mntmAddClient);
		
		JMenuItem mntmViewClient = new JMenuItem("View Client");
		mntmViewClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_client c = new View_client();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewClient.setFont(new Font("Calibri", Font.BOLD, 20));
		mnClient.add(mntmViewClient);
		
		JMenu mnMaterialReceive = new JMenu("Material Receive");
		mnMaterialReceive.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaster.add(mnMaterialReceive);
		
		JMenuItem mntmAddReceive = new JMenuItem("Add Receive");
		mntmAddReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_material_receive c = new Add_material_receive();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddReceive.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaterialReceive.add(mntmAddReceive);
		
		JMenuItem mntmViewReceive = new JMenuItem("View Receive");
		mntmViewReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_material_receive c = new View_material_receive();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewReceive.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaterialReceive.add(mntmViewReceive);
		
		JMenu mnMaterialWaste = new JMenu("Material Waste");
		mnMaterialWaste.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaster.add(mnMaterialWaste);
		
		JMenuItem mntmAddWaste = new JMenuItem("Add Waste");
		mntmAddWaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_material_waste c = new Add_material_waste();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddWaste.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaterialWaste.add(mntmAddWaste);
		
		JMenuItem mntmViewWaste = new JMenuItem("View Waste");
		mntmViewWaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_material_waste c = new View_material_waste();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewWaste.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaterialWaste.add(mntmViewWaste);
		
		JMenu mnTransaction = new JMenu("Transaction");
		mnTransaction.setFont(new Font("Calibri", Font.BOLD, 25));
		menuBar.add(mnTransaction);
		
		JMenu mnPo = new JMenu("PO");
		mnPo.setFont(new Font("Calibri", Font.BOLD, 20));
		mnTransaction.add(mnPo);
		
		JMenuItem mntmAddPo = new JMenuItem("Add PO");
		mntmAddPo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_po c = new Add_po();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddPo.setFont(new Font("Calibri", Font.BOLD, 20));
		mnPo.add(mntmAddPo);
		
		JMenuItem mntmViewPo = new JMenuItem("View PO");
		mntmViewPo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_po c = new View_po();
				desktopPane.add(c);
				c.show();
			}
		});
		
		mntmViewPo.setFont(new Font("Calibri", Font.BOLD, 20));
		mnPo.add(mntmViewPo);
		
		JMenu mnPurchase = new JMenu("Purchase");
		mnPurchase.setFont(new Font("Calibri", Font.BOLD, 20));
		mnTransaction.add(mnPurchase);
		
		JMenuItem mntmAddPurchase = new JMenuItem("Add Purchase");
		mntmAddPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_purchase c = new Add_purchase();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddPurchase.setFont(new Font("Calibri", Font.BOLD, 20));
		mnPurchase.add(mntmAddPurchase);
		
		JMenuItem mntmViewPurchase = new JMenuItem("View Purchase");
		mntmViewPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_purchase c = new View_purchase();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewPurchase.setFont(new Font("Calibri", Font.BOLD, 20));
		mnPurchase.add(mntmViewPurchase);
		
		JMenu mnMaterialIssue = new JMenu("Service Material");
		mnMaterialIssue.setFont(new Font("Calibri", Font.BOLD, 20));
		mnTransaction.add(mnMaterialIssue);
		
		JMenuItem mntmAddMaterialIssue = new JMenuItem("Add Service Material");
		mntmAddMaterialIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_Materialissue c = new Add_Materialissue();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddMaterialIssue.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaterialIssue.add(mntmAddMaterialIssue);
		
		JMenuItem mntmViewMaterialIssue = new JMenuItem("View Service Material");
		mntmViewMaterialIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_Materialissue c = new View_Materialissue();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewMaterialIssue.setFont(new Font("Calibri", Font.BOLD, 20));
		mnMaterialIssue.add(mntmViewMaterialIssue);
		
		JMenu mnReturnableMaterial = new JMenu("Returnable Material");
		mnReturnableMaterial.setFont(new Font("Calibri", Font.BOLD, 20));
		mnTransaction.add(mnReturnableMaterial);
		
		JMenuItem mntmAddReturnableMaterial = new JMenuItem("Add Returnable Material");
		mntmAddReturnableMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_returnable_material c = new Add_returnable_material();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddReturnableMaterial.setFont(new Font("Calibri", Font.BOLD, 20));
		mnReturnableMaterial.add(mntmAddReturnableMaterial);
		
		JMenuItem mntmViewReturnableMaterial = new JMenuItem("View Returnable Material");
		mntmViewReturnableMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_returnable_material c = new View_returnable_material();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewReturnableMaterial.setFont(new Font("Calibri", Font.BOLD, 20));
		mnReturnableMaterial.add(mntmViewReturnableMaterial);
		
		JMenu mnStaffIssue = new JMenu("Staff Issue");
		mnStaffIssue.setFont(new Font("Calibri", Font.BOLD, 20));
		mnTransaction.add(mnStaffIssue);
		
		JMenuItem mntmAddStaffIssue = new JMenuItem("Add Staff Issue");
		mntmAddStaffIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_staff_issue c = new Add_staff_issue();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddStaffIssue.setFont(new Font("Calibri", Font.BOLD, 20));
		mnStaffIssue.add(mntmAddStaffIssue);
		
		JMenuItem mntmViewStaffIssue = new JMenuItem("View Staff Issue");
		mntmViewStaffIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_staff_issue c = new View_staff_issue();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewStaffIssue.setFont(new Font("Calibri", Font.BOLD, 20));
		mnStaffIssue.add(mntmViewStaffIssue);
		
		JMenu mnPayment = new JMenu("Payment");
		mnPayment.setFont(new Font("Calibri", Font.BOLD, 20));
		mnTransaction.add(mnPayment);
		
		JMenuItem mntmAddPayment = new JMenuItem("Add Payment");
		mntmAddPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_payment c = new Add_payment();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddPayment.setFont(new Font("Calibri", Font.BOLD, 20));
		mnPayment.add(mntmAddPayment);
		
		JMenuItem mntmViewPayment = new JMenuItem("View Payment");
		mntmViewPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_payment c = new View_payment();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewPayment.setFont(new Font("Calibri", Font.BOLD, 20));
		mnPayment.add(mntmViewPayment);
		
		JMenu mnBom = new JMenu("BOM");
		mnBom.setFont(new Font("Calibri", Font.BOLD, 25));
		menuBar.add(mnBom);
		
		JMenuItem mntmAddBom = new JMenuItem("Add BOM");
		mntmAddBom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_bom c = new Add_bom();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmAddBom.setFont(new Font("Calibri", Font.BOLD, 20));
		mnBom.add(mntmAddBom);
		
		JMenuItem mntmViewBom = new JMenuItem("View BOM");
		mntmViewBom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_bom c = new View_bom();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewBom.setFont(new Font("Calibri", Font.BOLD, 20));
		mnBom.add(mntmViewBom);
		
		JMenuItem mntmCost = new JMenuItem("Cost");
		mntmCost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cost_bom c = new Cost_bom();
				desktopPane.add(c);
				c.show();
			}
		});
		
		mntmCost.setFont(new Font("Calibri", Font.BOLD, 20));
		mnBom.add(mntmCost);
		
		JMenu mnStock = new JMenu("Stock");
		mnStock.setFont(new Font("Calibri", Font.BOLD, 25));
		menuBar.add(mnStock);
		
		JMenuItem mntmViewStock = new JMenuItem("View Stock");
		mntmViewStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				View_stock c = new View_stock();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmViewStock.setFont(new Font("Calibri", Font.BOLD, 20));
		mnStock.add(mntmViewStock);
		
		JMenu mnReport = new JMenu("Report");
		mnReport.setFont(new Font("Calibri", Font.BOLD, 25));
		menuBar.add(mnReport);
		
		JMenuItem mntmStaffIssue = new JMenuItem("Staff Issue");
		mntmStaffIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Staff_issue c = new Staff_issue();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmStaffIssue.setFont(new Font("Calibri", Font.BOLD, 20));
		mnReport.add(mntmStaffIssue);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Client Issue");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client_issue c = new Client_issue();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmNewMenuItem.setFont(new Font("Calibri", Font.BOLD, 20));
		mnReport.add(mntmNewMenuItem);
		
		JMenuItem mntmProductReport = new JMenuItem("Product Report");
		mntmProductReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Product_report c = new Product_report();
				desktopPane.add(c);
				c.show();
			}
		});
		mntmProductReport.setFont(new Font("Calibri", Font.BOLD, 20));
		mnReport.add(mntmProductReport);
	}

}
