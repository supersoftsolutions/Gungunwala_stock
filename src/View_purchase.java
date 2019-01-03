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
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.proteanit.sql.DbUtils;

public class View_purchase extends JInternalFrame {
	private JTable table;
    private TableModel model;
    private JTextField textField;
    String id=null;
    
    public static boolean empty( final String s ) 
  	{
  		return s == null || s.trim().isEmpty();
  	}
	/**

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_purchase frame = new View_purchase();
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
	public View_purchase() {
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
        setBounds((w-774)/2, (h-461)/2, 774, 461);
        getContentPane().setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 88, 738, 332);
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
                    id=i;
                }
            });
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN,16));
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(table);

        final Connection connection = Databaseconnection.connection2();
        
        try {
            // pull data from the database 
            
            String query = "select ID,Po_no,Creditor,Date,Total from Purchase";
            PreparedStatement pmt = connection.prepareStatement(query);
            ResultSet rs = pmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            pmt.close();
            rs.close();
            
            
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try
                {
                	
                	int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){

                        float t = 0;
                        String n = null;
                        String date =null;
                        String name = null;
                        String query2 = "Select * from Purchase where ID='"+id+"'";
                        PreparedStatement pmt2 = connection.prepareStatement(query2);
                        ResultSet rs2 = pmt2.executeQuery();
                        
                        while(rs2.next())
                        {
                            name = rs2.getString("Creditor");
                            t = rs2.getFloat("Total");
                        }
                        rs2.close();pmt2.close();
                       
                        String query = "delete from Purchase where ID='"+id+"'";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        String query1 = "select * from Creditor where Name='"+name+"'";
                        PreparedStatement pmt1 = connection.prepareStatement(query1);
                        ResultSet rs1 = pmt1.executeQuery();
                        float t2 = 0;
                        while(rs1.next())
                        {
                             t2 = rs1.getFloat("Balance");
                        }
                        pmt1.close();
                        rs1.close();
                        
                        double t4 = t2 - t;
                        
                        String query5 = "UPDATE Creditor set Balance ="+t4+" where Name='"+name+"'";
                        PreparedStatement pmt5 = connection.prepareStatement(query5);
                        pmt5.executeUpdate();
                        pmt5.close();
                       
                        ArrayList<String> product = new ArrayList<String>();
                        ArrayList<Float> qty = new ArrayList<Float>(); 
                        
                        String query7 = "Select * from Purchase_detail where S_id='"+id+"'";
                        PreparedStatement pmt7 = connection.prepareStatement(query7);
                        ResultSet rs7 = pmt7.executeQuery();
                        
                        while(rs7.next())
                        {
                            product.add(rs7.getString("Product"));
                            qty.add(rs7.getFloat("Qty"));
                        }
                        
                        for (int i=0;i<product.size();i++)
                        {
                                
                               
                               
                                String query4 = "UPDATE Stock set Stock =Stock-"+qty.get(i)+" where product='"+product.get(i)+"'";
                                PreparedStatement pmt4 = connection.prepareStatement(query4);
                                pmt4.executeUpdate();
                                pmt4.close();
                                
                                String query41 = "UPDATE Product set Stock =Stock-"+qty.get(i)+" where Product='"+product.get(i)+"'";
	                               PreparedStatement pmt41 = connection.prepareStatement(query41);
	                               pmt41.executeUpdate();
	                               pmt41.close();
                        }
                        
                       String query4 = "delete from Purchase_detail where S_id='"+id+"'";
                       PreparedStatement pmt4 = connection.prepareStatement(query4);
                       pmt4.executeUpdate();
                       pmt4.close();
                       
                    }
                }
                    catch (SQLException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    View_purchase b = new View_purchase();
                    JDesktopPane desktopPane = getDesktopPane();
                    desktopPane.add(b);
                    
                    b.show();
                    dispose();
					
                    
                }
              
        });
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDelete.setBounds(10, 45, 117, 28);
        getContentPane().add(btnDelete);
        
        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                try {
                    
                    // pull data from the database 
                    String query = "select  ID,Po_no,Creditor,Date,Total from Purchase where Po_no like '%"+textField.getText()+"%' or Creditor like '%"+textField.getText()+"%' or Date like '%"+textField.getText()+"%'";
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
        textField.setBounds(331, 52, 135, 25);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JLabel lblMaterialReceive = new JLabel("View Purchase");
        lblMaterialReceive.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblMaterialReceive.setBounds(319, 15, 175, 24);
        getContentPane().add(lblMaterialReceive);
        
        JButton btnDetail = new JButton("Detail");
        btnDetail.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try
                {
                    float t = 0,t2 =0 ;
                    String n = null;
                    Detail_purchase s = new Detail_purchase();
                    
                    String query3 = "select * from Purchase where ID ='"+id+"'";
                    java.sql.PreparedStatement pmt3 = connection.prepareStatement(query3);
                    ResultSet rs3 = pmt3.executeQuery();
                    while(rs3.next())
                    {
                         t2 = rs3.getFloat("Total");
                         n = rs3.getString("Creditor");
                    }
                    pmt3.close();
                    rs3.close();
                    
                   
                    s.no.setText(id);
                    s.name.setText(n);
                    s.total.setText(Float.toString(t2));
                    String query = "select * from Purchase_detail where S_id ="+id+"";
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    s.table.setModel(DbUtils.resultSetToTableModel(rs));

                    
                    JDesktopPane desktopPane = getDesktopPane();
                    desktopPane.add(s);
                    s.show();
                    pmt.close();
                    rs.close();
                }
				catch(Exception ae)
				{
					ae.printStackTrace();
				}

        	}
        });
        btnDetail.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDetail.setBounds(537, 45, 117, 28);
        getContentPane().add(btnDetail);
        
        JButton btnPrint = new JButton("Print");
        btnPrint.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		  try{
                      double t2 =0 ;
                      double total99=0;
                      String n = null,date=null,po=null;
                      String query1 = "select * from Purchase where ID ='"+id+"'";
                      PreparedStatement pmt1 = connection.prepareStatement(query1);
                      ResultSet rs1 = pmt1.executeQuery();
                      while(rs1.next())
                      {
                           t2 = rs1.getDouble("Total");
                           date = rs1.getString("Date"); 
                           po = rs1.getString("Po_no"); 
                           n = rs1.getString("Creditor");
                      }
                      rs1.close();
                      pmt1.close();
                      
                      String add1 =null,add2=null,add3=null,state=null,city=null;
                      
                      
                      String query3 = "select * from Creditor where Name ='"+n+"'";
                      PreparedStatement pmt3 = connection.prepareStatement(query3);
                      ResultSet rs3 = pmt3.executeQuery();
                      while(rs3.next())
                      {
                           add1 = rs3.getString("Address1");
                           add2 = rs3.getString("Address2");
                           add3 = rs3.getString("Address3");
                           state = rs3.getString("State");
                           city = rs3.getString("City");
                      }
                      rs3.close();
                      pmt3.close();
                      Document doc = new Document(PageSize.A4,40,40,40,40);
                      PdfWriter writer = PdfWriter.getInstance(doc,new FileOutputStream("Purchase.pdf"));
                      doc.open();
                     
                      PdfPCell cell;
                      PdfPCell blankcell = new PdfPCell(new Phrase(" "));
                      blankcell.setBorder(Rectangle.NO_BORDER);
                      Paragraph p;
                      // row 1, cell 1
                     
                     
                      PdfPTable tableh = new PdfPTable(1);
                      tableh.setWidthPercentage(100);
                     
                      p =new Paragraph("GUNGUNWALA FOOD EQUIPMENT",FontFactory.getFont(FontFactory.HELVETICA,30,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(0f);
                      cell.setBorderWidthBottom(0);
                      tableh.addCell(cell);
                     
                      p =new Paragraph("6-B,Aashutosh Industrial Estate,ZAK,G.I.D.C,Naroda-Dahegam Road,Ahmedabad.",FontFactory.getFont(FontFactory.HELVETICA,10,Font.PLAIN,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(0f);
                      cell.setBorderWidthBottom(0);
                      tableh.addCell(cell);
                      p =new Paragraph("PH: 079-22870786",FontFactory.getFont(FontFactory.HELVETICA,10,Font.PLAIN,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(0f);
                      cell.setBorderWidthBottom(0);
                      tableh.addCell(cell);
                      
                      p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(0f);
                      cell.setBorderWidthBottom(0);
                      tableh.addCell(cell);
                     doc.add(tableh);
                      
                      
                     
                    
                     PdfPTable table1 = new PdfPTable(2);
                     table1.setWidthPercentage(100);
                     table1.setWidths(new int[]{ 5,2});
                      
                     p =new Paragraph("Company Name: "+n,FontFactory.getFont(FontFactory.HELVETICA,16,Font.PLAIN,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(0f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table1.addCell(cell);
                     
                     
                     String strDate1 = null;
                     if(empty(date))
                     {
                     	p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     }
                     else
                     {
                    	 Date startDate1 = null;
                    	
                         
                         try {
                         	SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                             startDate1 = sm.parse(date);
                 		
  
                         }
                         catch (ParseException ae) {
                             ae.printStackTrace();
                         }
                         
                         SimpleDateFormat sm2 = new SimpleDateFormat("dd-MM-yyyy");
                          strDate1 = sm2.format(startDate1);
                          p =new Paragraph("Date: "+strDate1,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     }
                     
                     
                     
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(1f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table1.addCell(cell);
                     
                     p =new Paragraph("Address :- "+add1,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(0f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table1.addCell(cell);
                     
                     p =new Paragraph("PO No.: "+id,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(1f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table1.addCell(cell);
                     
                     
                     p =new Paragraph("                 "+add2,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(0f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table1.addCell(cell);
                     
                     p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(1f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table1.addCell(cell);
                     
                     //p =new Paragraph("      "+add3+","+city+","+state,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     p =new Paragraph("                 "+add3+","+city+","+state,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(0f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table1.addCell(cell);
                     
                     p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(1f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table1.addCell(cell);
                    
                     doc.add(table1);
                      
                      
                      PdfPTable table2 = new PdfPTable(5);
                      table2.setWidthPercentage(100);
                      
                      table2.setWidths(new int[]{ 1, 8, 2, 2, 2});
                      p =new Paragraph("No",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(1f);
                      table2.addCell(cell);
                      p =new Paragraph("Description",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(1f);
                      table2.addCell(cell);
                      p =new Paragraph("Qty.",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(1f);
                      table2.addCell(cell);
                      p =new Paragraph("Rate",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(1f);
                      table2.addCell(cell);
                      p =new Paragraph("Amount",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthRight(1f);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(1f);
                      table2.addCell(cell);
                      
                      p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(0f);
                      table2.addCell(cell);
                      p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(0f);
                      table2.addCell(cell);
                      p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(0f);
                      table2.addCell(cell);
                      p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(0f);
                      table2.addCell(cell);
                      p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(1f);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(0f);
                      table2.addCell(cell);  
                      
                     
                      ArrayList<Float> qty = new ArrayList<Float>();
                      ArrayList<Float> rate = new ArrayList<Float>();
                      ArrayList<Float> to = new ArrayList<Float>();
                      ArrayList<String> pro = new ArrayList<String>();
                      ArrayList<String> uni = new ArrayList<String>();
                      String query0 = "select * from Purchase_detail where S_id='"+id+"'";
                      PreparedStatement pmt0 = connection.prepareStatement(query0);
                      ResultSet rs0 = pmt0.executeQuery();
                      while(rs0.next())
                      {
                          qty.add(rs0.getFloat("Qty"));
                          rate.add(rs0.getFloat("Rate"));
                          to.add(rs0.getFloat("Total"));
                          pro.add(rs0.getString("Product"));
                          total99 +=rs0.getDouble("Total");
                      }
                      pmt0.close();
                      rs0.close();
                      int total1 = 0;
                      
                      for(int i = 0;i<pro.size();i++)
                      {
                          String query = "select * from Product where Product='"+pro.get(i)+"'";
                          PreparedStatement pmt = connection.prepareStatement(query);
                          ResultSet rs = pmt.executeQuery();
                          while(rs.next())
                          {
                              uni.add(rs.getString("Unit"));
                          }
                          pmt.close();
                          rs.close();
                      }
                      for(int i = 0;i<pro.size();i++)
                      {
                          p =new Paragraph(""+(i+1),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setFixedHeight(20f);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(0f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table2.addCell(cell);
                          p =new Paragraph(pro.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                          cell.setBorderWidthTop(0f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table2.addCell(cell);
                          p =new Paragraph(""+qty.get(i)+" "+uni.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(0f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table2.addCell(cell);
                          p =new Paragraph(""+rate.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(0f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table2.addCell(cell);
                          p =new Paragraph(""+to.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(0f);
                          cell.setBorderWidthRight(1f);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table2.addCell(cell);  
                          total1 += to.get(i);
                      }
                      for(int i = 0;i<15-pro.size();i++)
                      {
                          p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setFixedHeight(20f);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(0f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table2.addCell(cell);
                          p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(0f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table2.addCell(cell);
                          p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(0f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table2.addCell(cell);
                          p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(0f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table2.addCell(cell);
                          p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(0f);
                          cell.setBorderWidthRight(1f);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table2.addCell(cell);  
                      }
                      doc.add(table2);
                      
                      DecimalFormat df = new DecimalFormat("#0.00");
                      
                      double tax28=0,tax18=0,tax5=0;

                      String query2 = "select * from Purchase where ID='"+id+"'";
                      PreparedStatement pmt2 = connection.prepareStatement(query2);
                      ResultSet rs2 = pmt2.executeQuery();
                      while(rs2.next())
                      {
                          /*tax.add(rs2.getString("tax18"));
                          tax.add(rs2.getString("tax28"));
                          tax.add(rs2.getString("tax5"));*/
                    	  tax28=rs2.getDouble("tax28");
                    	  tax18=rs2.getDouble("tax18");
                    	  tax5=rs2.getDouble("tax5");
                      }
                      pmt2.close();
                      rs2.close();
                      
                      PdfPTable table3 = new PdfPTable(3);
                      table3.setWidthPercentage(100);
                      PdfPCell cell1;
                       table3.setWidths(new int[]{ 6, 2, 2});
                      
                      /*for(int i=0;i<tax.size();i++)
                      {
                          p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                          p =new Paragraph(""+tax.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                          
                          double t=0;
                          if(tax.get(i).equals("tax 18"))
                          {
                              t = total1 * 18.0 / 100;
                          }
                          if(tax.get(i).equals("tax 28"))
                          {
                              t = total1 * 28.0 / 100;
                          }
                          if(tax.get(i).equals("tax 5"))
                          {
                              t = total1 * 5 / 100;
                          }
                       
                          p =new Paragraph(""+df.format(t),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthRight(1f);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                      }*/
                      
                      p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthBottom(0f);
                      table3.addCell(cell);
                      p =new Paragraph("Total",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(0f);
                      table3.addCell(cell);
                      p =new Paragraph(""+df.format(total99),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthRight(1f);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(0f);
                      table3.addCell(cell);
                      
                      if(tax5==0)
                      {
                    	  
                      }
                      else
                      {
                    	  p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                          p =new Paragraph("Tax 5%",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                          p =new Paragraph(""+df.format(total99*0.05),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthRight(1f);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                      }
                      
                      if(tax18==0)
                      {
                    	  
                      }
                      else
                      {
                    	  p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                          p =new Paragraph("Tax 18%",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                          p =new Paragraph(""+df.format(total99*0.18),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthRight(1f);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                      }
                      
                      if(tax28==0)
                      {
                    	  
                      }
                      else
                      {
                    	  p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                          p =new Paragraph("Tax 28%",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthRight(0);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                          p =new Paragraph(""+df.format(total99*0.28),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                          cell = new PdfPCell(p);
                          cell.setUseBorderPadding(true);
                          cell.setVerticalAlignment(Element.ALIGN_CENTER);
                          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                          cell.setBorderWidthTop(1f);
                          cell.setBorderWidthRight(1f);
                          cell.setBorderWidthLeft(1f);
                          cell.setBorderWidthBottom(0f);
                          table3.addCell(cell);
                      }
                     
                      
                      p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthBottom(0f);
                      table3.addCell(cell);
                      p =new Paragraph("Grand Total",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(0f);
                      table3.addCell(cell);
                      p =new Paragraph(""+df.format(t2),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthRight(1f);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(0f);
                      table3.addCell(cell);
                      doc.add(table3);
                      
                      
                      PdfPTable table5 = new PdfPTable(1);
                      table5.setWidthPercentage(100);
                      p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.PLAIN,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                      cell.setBorder(Rectangle.NO_BORDER);
                      table5.addCell(cell);
                      p =new Paragraph("Email:- fryersgungunwala@gmail.com,enquirygungunwalafood@yahoo.com",FontFactory.getFont(FontFactory.HELVETICA,10,Font.PLAIN,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                      cell.setBorder(Rectangle.NO_BORDER);
                      table5.addCell(cell);
                      p =new Paragraph("Website:- www.namkeenfryer.com",FontFactory.getFont(FontFactory.HELVETICA,10,Font.PLAIN,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                      cell.setBorder(Rectangle.NO_BORDER);
                      table5.addCell(cell);
                     
                      
                      PdfPTable table4 = new PdfPTable(2);
                      table4.setWidths(new int[]{ 7, 3});
                      table4.setWidthPercentage(100);
                      p =new Paragraph("'Use Technology Save Money'",FontFactory.getFont(FontFactory.HELVETICA,20,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setColspan(2);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(1f);
                      cell.setBorderWidthRight(1f);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(1f);
                      table4.addCell(cell);
                      cell = new PdfPCell(p);
                      cell.addElement(table5);
                      cell.setUseBorderPadding(true);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(0);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(1f);
                      table4.addCell(cell);
                      p =new Paragraph("Autorised Signature",FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,BaseColor.BLACK));
                      cell = new PdfPCell(p);
                      cell.setUseBorderPadding(true);
                      cell.setVerticalAlignment(Element.ALIGN_CENTER);
                      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                      cell.setBorderWidthTop(0f);
                      cell.setBorderWidthRight(1f);
                      cell.setBorderWidthLeft(1f);
                      cell.setBorderWidthBottom(1f);
                      table4.addCell(cell);
                      doc.add(table4);
                      
                      blankcell.setBorder(Rectangle.NO_BORDER);
                      
                      doc.close();
                      
                  }
                  
                  catch (Exception e) {
                      e.printStackTrace();
                  }
                  
                  try{
                      Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"Purchase.pdf");
                      
                  }catch(Exception e){
                      e.printStackTrace();
                  }
        	}
        });
        btnPrint.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnPrint.setBounds(158, 45, 117, 28);
        getContentPane().add(btnPrint);
        
        }
	}


