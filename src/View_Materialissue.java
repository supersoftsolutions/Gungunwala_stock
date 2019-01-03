import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;

public class View_Materialissue extends JInternalFrame {
	private JTextField textField;
	private JTable table;
    private TableModel model;

    String id=null;
    
    public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_Materialissue frame = new View_Materialissue();
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
	public View_Materialissue() {
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
        
        final Connection connection = Databaseconnection.connection2();
        
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
                    try
                    {
                    	String query = "delete from Material_issue_id";
                        java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        String query1 = "insert into Material_issue_id(ID) values ('"+id+"')";
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
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(table);

        
        
        try {
            // pull data from the database 
            
            String query = "select ID,Sleep_no,Client_name,Name as Person_name,Date,Total from Material_issue";
            PreparedStatement pmt = connection.prepareStatement(query);
            ResultSet rs = pmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            pmt.close();
            rs.close();
            
            
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JLabel lblViewMaterialIssue = new JLabel("View Service Material");
        lblViewMaterialIssue.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblViewMaterialIssue.setBounds(319, 13, 220, 24);
        getContentPane().add(lblViewMaterialIssue);
        
        JButton button = new JButton("Delete");
        button.addActionListener(new ActionListener() {
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
                         String query2 = "Select * from Material_issue where ID='"+id+"'";
                         PreparedStatement pmt2 = connection.prepareStatement(query2);
                         ResultSet rs2 = pmt2.executeQuery();
                         
                         while(rs2.next())
                         {
                             name = rs2.getString("Client_name");
                             t = rs2.getFloat("Total");
                         }
                         rs2.close();pmt2.close();
                        
                         String query = "delete from Material_issue where ID='"+id+"'";
                         PreparedStatement pmt = connection.prepareStatement(query);
                         pmt.executeUpdate();
                         pmt.close();
                       
                         ArrayList<String> product = new ArrayList<String>();
                         ArrayList<Float> qty = new ArrayList<Float>(); 
                         
                         String query7 = "Select * from Material_Issue_Details where M_id='"+id+"'";
                         PreparedStatement pmt7 = connection.prepareStatement(query7);
                         ResultSet rs7 = pmt7.executeQuery();
                         
                         while(rs7.next())
                         {
                             product.add(rs7.getString("Product"));
                             qty.add(rs7.getFloat("Qty"));
                         }
                         
                         for (int i=0;i<product.size();i++)
                         {
                        	 float t2=0;
                                 String query6 = "select * from Stock where product='"+product.get(i)+"'";
                                 PreparedStatement pmt6 = connection.prepareStatement(query6);
                                 ResultSet rs6 = pmt6.executeQuery();
                                 while(rs6.next())
                                 {
                                      t2 = rs6.getFloat("Stock");
                                 }
                                 pmt6.close();
                                 rs6.close();
                                 
                                 String query61 = "select * from Product where product='"+product.get(i)+"'";
                                 PreparedStatement pmt61 = connection.prepareStatement(query61);
                                 ResultSet rs61 = pmt61.executeQuery();
                                 while(rs61.next())
                                 {
                                      t2 = rs61.getFloat("Stock");
                                 }
                                 pmt61.close();
                                 rs61.close();
                                
                                
                                 String query4 = "UPDATE Stock set Stock =Stock-"+qty.get(i)+" where product='"+product.get(i)+"'";
                                 PreparedStatement pmt4 = connection.prepareStatement(query4);
                                 pmt4.executeUpdate();
                                 pmt4.close();
                                 
                                 String query41 = "UPDATE Product set Stock =Stock-"+qty.get(i)+" where Product='"+product.get(i)+"'";
 	                               PreparedStatement pmt41 = connection.prepareStatement(query41);
 	                               pmt41.executeUpdate();
 	                               pmt41.close();
                         }
                         
                        String query4 = "delete from Material_Issue_Details where M_id='"+id+"'";
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
                     View_Materialissue b = new View_Materialissue();
                     JDesktopPane desktopPane = getDesktopPane();
                     desktopPane.add(b);
                     
                     b.show();
                     dispose();
 					
                     
        	}
        });
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBounds(28, 41, 117, 28);
        getContentPane().add(button);
        
        JButton button_1 = new JButton("Print");
        button_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		 try{
                     String n = null,cn=null,date=null,total=null,sleep_no=null;
                     String query1 = "select * from Material_issue where ID ='"+id+"'";
                     PreparedStatement pmt1 = connection.prepareStatement(query1);
                     ResultSet rs1 = pmt1.executeQuery();
                     while(rs1.next())
                     {
                          date = rs1.getString("Date"); 
                          n = rs1.getString("Name");
                          cn = rs1.getString("Client_Name");
                          total = rs1.getString("Total");
                          sleep_no = rs1.getString("Sleep_no");
                     }
                     rs1.close();
                     pmt1.close();
                     
                     
                     
                     
                     
                     Document doc = new Document(PageSize.A4,40,40,40,40);
                     PdfWriter writer = PdfWriter.getInstance(doc,new FileOutputStream("Issue.pdf"));
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
                     
                    p =new Paragraph("Company Name: "+cn,FontFactory.getFont(FontFactory.HELVETICA,16,Font.PLAIN,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(1f);
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
                    cell.setBorderWidthLeft(0f);
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
                    
                    p =new Paragraph("SR. No.: "+sleep_no,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                    p =new Paragraph("                 ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                    p =new Paragraph("Person Name:- "+n,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                  
                    
                 /*   p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);*/
                    
                    //p =new Paragraph("      "+add3+","+city+","+state,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                    
                   
                   
                    doc.add(table1);
                     
                     PdfPTable table2 = new PdfPTable(7);
                     table2.setWidthPercentage(100);
                     
                     table2.setWidths(new int[]{ 1, 6, 3, 3, 4, 2, 2});
                     p =new Paragraph("No",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(1f);
                     table2.addCell(cell);
                     p =new Paragraph("Description",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(1f);
                     table2.addCell(cell);
                     p =new Paragraph("Purpose",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(1f);
                     table2.addCell(cell);
                     p =new Paragraph("Return Date",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(1f);
                     table2.addCell(cell);
                     p =new Paragraph("Qty",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(1f);
                     table2.addCell(cell);
                     p =new Paragraph("Rate",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(0f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(1f);
                     table2.addCell(cell);
                     p =new Paragraph("Total",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(1f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(1f);
                     table2.addCell(cell);
                    
                     p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table2.addCell(cell);
                     p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table2.addCell(cell);
                     p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table2.addCell(cell);
                     p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table2.addCell(cell);
                     p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table2.addCell(cell);
                     p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(0f);
                     cell.setBorderWidthRight(0);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table2.addCell(cell);
                     p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
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
                     ArrayList<String> rate = new ArrayList<String>();
                     ArrayList<String> tot = new ArrayList<String>();
                     ArrayList<String> pro = new ArrayList<String>();
                     ArrayList<String> uni = new ArrayList<String>();
                     ArrayList<String> type = new ArrayList<String>();
                     ArrayList<String> date1 = new ArrayList<String>();

                     String query0 = "select * from Material_Issue_Details where M_id='"+id+"'";
                     PreparedStatement pmt0 = connection.prepareStatement(query0);
                     ResultSet rs0 = pmt0.executeQuery();
                     while(rs0.next())
                     {
                         qty.add(rs0.getFloat("Qty"));
                         rate.add(rs0.getString("Rate"));
                         tot.add(rs0.getString("Total"));
                         pro.add(rs0.getString("Product"));
                         type.add(rs0.getString("Type"));
                         date1.add(rs0.getString("Return_Date"));
                     }
                     pmt0.close();
                     rs0.close();
                     int total1 =0;
                     
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
                         p =new Paragraph(""+(i+1),FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
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
                         p =new Paragraph(pro.get(i),FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                         cell = new PdfPCell(p);
                         cell.setUseBorderPadding(true);
                         cell.setVerticalAlignment(Element.ALIGN_CENTER);
                         cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                         cell.setBorderWidthTop(0f);
                         cell.setBorderWidthRight(0);
                         cell.setBorderWidthLeft(1f);
                         cell.setBorderWidthBottom(0f);
                         table2.addCell(cell);
                         p =new Paragraph(""+type.get(i),FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                         cell = new PdfPCell(p);
                         cell.setUseBorderPadding(true);
                         cell.setVerticalAlignment(Element.ALIGN_CENTER);
                         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cell.setBorderWidthTop(0f);
                         cell.setBorderWidthRight(0);
                         cell.setBorderWidthLeft(1f);
                         cell.setBorderWidthBottom(0f);
                         table2.addCell(cell);
                         
                         if(empty(date1.get(i)))
                         {
                        	 p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                         }
                         else
                         {
                        	 Date startDate = null;
                             
                             try {
                             	SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                                 startDate = sm.parse(date1.get(i));
                     		
      
                             }
                             catch (ParseException ae) {
                                 ae.printStackTrace();
                             }
                             
                             SimpleDateFormat sm2 = new SimpleDateFormat("dd-MM-yyyy");
                             String strDate = sm2.format(startDate);
                             p =new Paragraph(""+strDate,FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                         }
                         
                      
                         
                         
                         
                         cell = new PdfPCell(p);
                         cell.setUseBorderPadding(true);
                         cell.setVerticalAlignment(Element.ALIGN_CENTER);
                         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cell.setBorderWidthTop(0f);
                         cell.setBorderWidthRight(0);
                         cell.setBorderWidthLeft(1f);
                         cell.setBorderWidthBottom(0f);
                         table2.addCell(cell);
                         p =new Paragraph(""+qty.get(i)+" "+uni.get(i),FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                         cell = new PdfPCell(p);
                         cell.setUseBorderPadding(true);
                         cell.setVerticalAlignment(Element.ALIGN_CENTER);
                         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cell.setBorderWidthTop(0f);
                         cell.setBorderWidthRight(0);
                         cell.setBorderWidthLeft(1f);
                         cell.setBorderWidthBottom(0f);
                         table2.addCell(cell);
                         p =new Paragraph(""+rate.get(i),FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                         cell = new PdfPCell(p);
                         cell.setUseBorderPadding(true);
                         cell.setVerticalAlignment(Element.ALIGN_CENTER);
                         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                         cell.setBorderWidthTop(0f);
                         cell.setBorderWidthRight(0f);
                         cell.setBorderWidthLeft(1f);
                         cell.setBorderWidthBottom(0f);
                         table2.addCell(cell);
                         p =new Paragraph(""+tot.get(i),FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
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
                         cell.setBorderWidthRight(0f);
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
                     
                     
                     DecimalFormat df = new DecimalFormat("#0.00");
                     
                     
                     
                     p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setColspan(5);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(0f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     table2.addCell(cell);
                     
                     p =new Paragraph("Total ",FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(1f);
                     cell.setBorderWidthLeft(0f);
                     cell.setBorderWidthBottom(0f);
                     table2.addCell(cell);
                     
                     p =new Paragraph(""+total,FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(1f);
                     cell.setBorderWidthLeft(0f);
                     cell.setBorderWidthBottom(0f);
                     table2.addCell(cell);
                     doc.add(table2);
                     
                     PdfPTable tablep = new PdfPTable(2);
                     tablep.setWidthPercentage(100);
                     tablep.setWidths(new int[]{ 6, 4});
                     
                     p =new Paragraph("Receiver stmp & Sign:",FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(0f);
                     cell.setBorderWidthLeft(1f);
                     cell.setBorderWidthBottom(0f);
                     tablep.addCell(cell);
                     
                     p =new Paragraph("Signature:",FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                     cell = new PdfPCell(p);
                     cell.setUseBorderPadding(true);
                     cell.setVerticalAlignment(Element.ALIGN_CENTER);
                     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                     cell.setBorderWidthTop(1f);
                     cell.setBorderWidthRight(1f);
                     cell.setBorderWidthLeft(0f);
                     cell.setBorderWidthBottom(0f);
                     tablep.addCell(cell);
                     doc.add(tablep);
                     
                     PdfPTable table5 = new PdfPTable(1);
                     table5.setWidthPercentage(100);
                     
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
                     
                     PdfPTable table51 = new PdfPTable(1);
                     table51.setWidthPercentage(100);
                    
                     blankcell.setBorder(Rectangle.NO_BORDER);
                     
                     doc.close();
                     
                     
                     
                 }
                 
                 catch (Exception e) {
                     e.printStackTrace();
                 }
                 
                 try{
                     Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"Issue.pdf");
                     
                 }catch(Exception e){
                     e.printStackTrace();
                 }
        	}
        });
        button_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        button_1.setBounds(176, 41, 117, 28);
        getContentPane().add(button_1);
        
        textField = new JTextField();
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                try {
                    
                    // pull data from the database 
                    String query = "select ID,Sleep_no,Client_name,Name as Person_name,Date,Total from Material_issue where Sleep_no like '%"+textField.getText()+"%' or Client_name like '%"+textField.getText()+"%' or Name like '%"+textField.getText()+"%' or  Date like '%"+textField.getText()+"%'";
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
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField.setColumns(10);
        textField.setBounds(349, 48, 135, 25);
        getContentPane().add(textField);
        
        JButton button_2 = new JButton("Detail");
        button_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try
                {
                    float t = 0,t2 =0 ;
                    String n = null;
                    Detail_Materialissue s = new Detail_Materialissue();
                    
                    String query3 = "select * from Material_issue where ID ='"+id+"'";
                    java.sql.PreparedStatement pmt3 = connection.prepareStatement(query3);
                    ResultSet rs3 = pmt3.executeQuery();
                    while(rs3.next())
                    {
                         t2 = rs3.getFloat("Total");
                         n = rs3.getString("Client_name");
                    }
                    pmt3.close();
                    rs3.close();
                    
                   
                    s.no.setText(id);
                    s.name.setText(n);
                    s.total.setText(Float.toString(t2));
                    String query = "select * from Material_Issue_Details where M_id ="+id+"";
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
        button_2.setFont(new Font("Tahoma", Font.BOLD, 16));
        button_2.setBounds(524, 41, 100, 28);
        getContentPane().add(button_2);
        
        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Edit_material_issue s = new Edit_material_issue();
        		JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(s);
                s.show();
                dispose();
        	}
        });
        btnEdit.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnEdit.setBounds(631, 41, 117, 28);
        getContentPane().add(btnEdit);
        
	}
}
