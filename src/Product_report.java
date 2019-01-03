import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import com.toedter.calendar.JYearChooser;

import net.proteanit.sql.DbUtils;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;

public class Product_report extends JInternalFrame {
	 private JTable table;
	    private TableModel model;
	    public int id;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Product_report frame = new Product_report();
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
	public Product_report() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-120;
        int w = (int)(width);
        int h = (int)(height);
        setClosable(true);
        setBounds((w-774)/2, (h-461)/2, 774, 460);
        getContentPane().setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 91, 758, 340);
        getContentPane().add(scrollPane);
        
        table = new JTable(model){

            public boolean isCellEditable(int rowIndex, int colIndex) {
            return false; //Disallow the editing of any cell
            }
            };
           
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane.setViewportView(table);

        final Connection connection = Databaseconnection.connection2();
        
        JLabel lblFrom = new JLabel("Month");
        lblFrom.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblFrom.setBounds(10, 51, 115, 29);
        getContentPane().add(lblFrom);
        
        try{
            
            String querydetais="delete from Temp_Product";
            PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
            pmtdetails.executeUpdate();
            pmtdetails.close();
            
       
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        final JComboBox month = new JComboBox();
        month.setModel(new DefaultComboBoxModel(new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}));
        month.setFont(new Font("Calibri", Font.PLAIN, 25));
        month.setBounds(182, 50, 137, 25);
        getContentPane().add(month);
        
        final JYearChooser yearChooser = new JYearChooser();
        yearChooser.setBounds(349, 51, 102, 25);
        getContentPane().add(yearChooser);
        
    
        
        JButton btnView = new JButton("View");
        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    String querydetais="delete from Temp_Product";
                    PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
                    pmtdetails.executeUpdate();
                    pmtdetails.close();
                    
                    String query1 = "INSERT INTO Temp_Product(Product, Opening_Stock, IN_Stock, OUT_Stock, Closing_Stock) select Product,Stock,'0','0',Stock from Opening_Stock";
                    PreparedStatement pmt1 = connection.prepareStatement(query1);
                    pmt1.executeUpdate();
                    pmt1.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                
                String m=null;
                
                if(month.getSelectedIndex() == 0)
                {
                    m="01";
                }
                else if(month.getSelectedIndex() == 1)
                {
                    m="02";
                }
                else if(month.getSelectedIndex() == 2)
                {
                    m="03";
                }
                else if(month.getSelectedIndex() == 3)
                {
                    m="04";
                }
                else if(month.getSelectedIndex() == 4)
                {
                    m="05";
                }
                else if(month.getSelectedIndex() == 5)
                {
                    m="06";
                }
                else if(month.getSelectedIndex() == 6)
                {
                    m="07";
                }
                else if(month.getSelectedIndex() == 7)
                {
                    m="08";
                }
                else if(month.getSelectedIndex() == 8)
                {
                    m="09";
                }
                else if(month.getSelectedIndex() == 9)
                {
                    m="10";
                }
                else if(month.getSelectedIndex() == 10)
                {
                    m="11";
                }
                else if(month.getSelectedIndex() == 11)
                {
                    m="12";
                }
                
                
                String date1 = yearChooser.getYear()+"-"+m+"-01";
                String date2 = yearChooser.getYear()+"-"+m+"-31";
                
                try {
                    // pull data from the database 
                    ArrayList<Integer> id = new ArrayList<Integer>();
                    String query0 = "select * from Purchase where Date >= '"+date1+"' and Date <= '"+date2+"'";
                    PreparedStatement pmt0 = connection.prepareStatement(query0);
                    ResultSet rs0 = pmt0.executeQuery();
                    while(rs0.next())
                    {
                        id.add(rs0.getInt("ID"));
                    }
                    pmt0.close();
                    rs0.close();
                    
                    
                    for(int i = 0;i<id.size();i++)
                    {
                        ArrayList<String> product = new ArrayList<String>();
                        ArrayList<Float> qty = new ArrayList<Float>();
                        String query = "select * from Purchase_detail where S_id='"+id.get(i)+"'";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        ResultSet rs = pmt.executeQuery();
                        while(rs.next())
                        {
                            product.add(rs.getString("Product"));
                            qty.add(rs.getFloat("Qty"));
                        }
                        pmt.close();
                        rs.close();
                        
                        for(int j=0;j<qty.size();j++)
                        {
                            String query3 = "select * from Temp_Product where Product='"+product.get(j)+"'";
                            PreparedStatement pmt3 = connection.prepareStatement(query3);
                            ResultSet rs3 = pmt3.executeQuery();
                            float t2 = 0,t1=0;
                            while(rs3.next())
                            {
                                 t2 = rs3.getFloat("IN_Stock");
                                 t1 = rs3.getFloat("Closing_Stock");
                            }
                            pmt3.close();
                            rs3.close();
                            
                            double t4 = t2 + qty.get(j);
                            double t3 = t1 + qty.get(j);
                           
                            String query4 = "UPDATE Temp_Product set IN_Stock ="+t4+",Closing_Stock ="+t3+" where Product='"+product.get(j)+"'";
                            PreparedStatement pmt4 = connection.prepareStatement(query4);
                            pmt4.executeUpdate();
                            pmt4.close();
                          
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    // pull data from the database 
                    ArrayList<String> product = new ArrayList<String>();
                    ArrayList<Float> qty = new ArrayList<Float>();
                    ArrayList<Integer> id = new ArrayList<Integer>();
                    String query0 = "select * from Receive where Date >= '"+date1+"' and Date <= '"+date2+"'";
                    PreparedStatement pmt0 = connection.prepareStatement(query0);
                    ResultSet rs0 = pmt0.executeQuery();
                    while(rs0.next())
                    {
                        id.add(rs0.getInt("ID"));
                        product.add(rs0.getString("Product"));
                        qty.add(rs0.getFloat("Qty"));
                    }
                    pmt0.close();
                    rs0.close();                   
                        
                        for(int j=0;j<qty.size();j++)
                        {
                            String query3 = "select * from Temp_Product where Product='"+product.get(j)+"'";
                            PreparedStatement pmt3 = connection.prepareStatement(query3);
                            ResultSet rs3 = pmt3.executeQuery();
                            float t2 = 0,t1=0;
                            while(rs3.next())
                            {
                                 t2 = rs3.getFloat("IN_Stock");
                                 t1 = rs3.getFloat("Closing_Stock");
                            }
                            pmt3.close();
                            rs3.close();
                            
                            double t4 = t2 + qty.get(j);
                            double t3 = t1 + qty.get(j);
                           
                            String query4 = "UPDATE Temp_Product set IN_Stock ="+t4+",Closing_Stock ="+t3+" where Product='"+product.get(j)+"'";
                            PreparedStatement pmt4 = connection.prepareStatement(query4);
                            pmt4.executeUpdate();
                            pmt4.close();
                          
                        }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
                try {
                    // pull data from the database 
                    ArrayList<String> product = new ArrayList<String>();
                    ArrayList<Float> qty = new ArrayList<Float>();
                    ArrayList<Integer> id = new ArrayList<Integer>();
                    String query0 = "select * from Waste where Date >= '"+date1+"' and Date <= '"+date2+"'";
                    PreparedStatement pmt0 = connection.prepareStatement(query0);
                    ResultSet rs0 = pmt0.executeQuery();
                    while(rs0.next())
                    {
                        id.add(rs0.getInt("ID"));
                        product.add(rs0.getString("Product"));
                        qty.add(rs0.getFloat("Qty"));
                    }
                    pmt0.close();
                    rs0.close();                   
                        
                        for(int j=0;j<qty.size();j++)
                        {
                            String query3 = "select * from Temp_Product where Product='"+product.get(j)+"'";
                            PreparedStatement pmt3 = connection.prepareStatement(query3);
                            ResultSet rs3 = pmt3.executeQuery();
                            float t2 = 0,t1=0;
                            while(rs3.next())
                            {
                                 t2 = rs3.getFloat("IN_Stock");
                                 t1 = rs3.getFloat("Closing_Stock");
                            }
                            pmt3.close();
                            rs3.close();
                            
                            double t4 = t2 - qty.get(j);
                            double t3 = t1 - qty.get(j);
                           
                            String query4 = "UPDATE Temp_Product set IN_Stock ="+t4+",Closing_Stock ="+t3+" where Product='"+product.get(j)+"'";
                            PreparedStatement pmt4 = connection.prepareStatement(query4);
                            pmt4.executeUpdate();
                            pmt4.close();
                          
                        }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
                
                
                
                try {
                    // pull data from the database 
                    ArrayList<Integer> id = new ArrayList<Integer>();
                    String query0 = "select * from Staff_issue where Date >= '"+date1+"' and Date <= '"+date2+"'";
                    PreparedStatement pmt0 = connection.prepareStatement(query0);
                    ResultSet rs0 = pmt0.executeQuery();
                    while(rs0.next())
                    {
                        id.add(rs0.getInt("ID"));
                    }
                    pmt0.close();
                    rs0.close();
                    
                    
                    for(int i = 0;i<id.size();i++)
                    {
                        ArrayList<String> product = new ArrayList<String>();
                        ArrayList<Float> qty = new ArrayList<Float>();
                        String query = "select * from Staff_issue_detail where S_id='"+id.get(i)+"'";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        ResultSet rs = pmt.executeQuery();
                        while(rs.next())
                        {
                            product.add(rs.getString("Product"));
                            qty.add(rs.getFloat("Qty"));
                        }
                        pmt.close();
                        rs.close();
                        
                        for(int j=0;j<qty.size();j++)
                        {
                            String query3 = "select * from Temp_Product where Product='"+product.get(j)+"'";
                            PreparedStatement pmt3 = connection.prepareStatement(query3);
                            ResultSet rs3 = pmt3.executeQuery();
                            float t2 = 0,t1=0;
                            while(rs3.next())
                            {
                                 t2 = rs3.getFloat("OUT_Stock");
                                 t1 = rs3.getFloat("Closing_Stock");
                            }
                            pmt3.close();
                            rs3.close();
                            
                            double t4 = t2 + qty.get(j);
                            double t3 = t1 - qty.get(j);
                           
                            String query4 = "UPDATE Temp_Product set OUT_Stock ="+t4+",Closing_Stock ="+t3+" where Product='"+product.get(j)+"'";
                            PreparedStatement pmt4 = connection.prepareStatement(query4);
                            pmt4.executeUpdate();
                            pmt4.close();
                          
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
                try {
                    // pull data from the database 
                    ArrayList<Integer> id = new ArrayList<Integer>();
                    String query0 = "select * from Material_issue where Date >= '"+date1+"' and Date <= '"+date2+"'";
                    PreparedStatement pmt0 = connection.prepareStatement(query0);
                    ResultSet rs0 = pmt0.executeQuery();
                    while(rs0.next())
                    {
                        id.add(rs0.getInt("ID"));
                    }
                    pmt0.close();
                    rs0.close();
                    
                    
                    for(int i = 0;i<id.size();i++)
                    {
                        ArrayList<String> product = new ArrayList<String>();
                        ArrayList<Float> qty = new ArrayList<Float>();
                        String query = "select * from Material_Issue_Details where M_id='"+id.get(i)+"'";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        ResultSet rs = pmt.executeQuery();
                        while(rs.next())
                        {
                            product.add(rs.getString("Product"));
                            qty.add(rs.getFloat("Qty"));
                        }
                        pmt.close();
                        rs.close();
                        
                        for(int j=0;j<qty.size();j++)
                        {
                            String query3 = "select * from Temp_Product where Product='"+product.get(j)+"'";
                            PreparedStatement pmt3 = connection.prepareStatement(query3);
                            ResultSet rs3 = pmt3.executeQuery();
                            float t2 = 0,t1=0;
                            while(rs3.next())
                            {
                                 t2 = rs3.getFloat("OUT_Stock");
                                 t1 = rs3.getFloat("Closing_Stock");
                            }
                            pmt3.close();
                            rs3.close();
                            
                            double t4 = t2 + qty.get(j);
                            double t3 = t1 - qty.get(j);
                           
                            String query4 = "UPDATE Temp_Product set OUT_Stock ="+t4+",Closing_Stock ="+t3+" where Product='"+product.get(j)+"'";
                            PreparedStatement pmt4 = connection.prepareStatement(query4);
                            pmt4.executeUpdate();
                            pmt4.close();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }              
                try {
                                     
                    String query = "SELECT * FROM Temp_Product";
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
        btnView.setMnemonic(KeyEvent.VK_END);
        btnView.setForeground(Color.BLACK);
        btnView.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnView.setBounds(488, 50, 115, 30);
        getContentPane().add(btnView);
        
        JButton btnPrint = new JButton("Print");
        btnPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                
                try{
                    
                    Document doc = new Document(PageSize.A4,40,40,40,40);
                    PdfWriter writer = PdfWriter.getInstance(doc,new FileOutputStream("productreport.pdf"));
                    doc.open();
                   
                    PdfPCell cell;
                    PdfPCell blankcell = new PdfPCell(new Phrase(" "));
                    blankcell.setBorder(Rectangle.NO_BORDER);
                    Paragraph p;
                   
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
                   
                    p =new Paragraph("6-B,Aashutosh Industrial Estate,ZAK,G.I.D.C,Naroda-Dahegam Road,Ahmedabad.",FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setColspan(3);
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
                    cell.setColspan(3);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0);
                    tableh.addCell(cell);
                   doc.add(tableh);
                    
                    
                   
                  
                   PdfPTable table1 = new PdfPTable(1);
                   table1.setWidthPercentage(100);
               
                   p =new Paragraph("Product Report",FontFactory.getFont(FontFactory.HELVETICA,20,Font.PLAIN,BaseColor.BLACK));
                   cell = new PdfPCell(p);
                   cell.setUseBorderPadding(true);
                   cell.setVerticalAlignment(Element.ALIGN_CENTER);
                   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                   cell.setBorderWidthTop(0);
                   cell.setBorderWidthRight(0f);
                   cell.setBorderWidthLeft(0f);
                   cell.setBorderWidthBottom(0f);
                   table1.addCell(cell);
                   p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,20,Font.PLAIN,BaseColor.BLACK));
                   cell = new PdfPCell(p);
                   cell.setUseBorderPadding(true);
                   cell.setVerticalAlignment(Element.ALIGN_CENTER);
                   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                   cell.setBorderWidthTop(0);
                   cell.setBorderWidthRight(0f);
                   cell.setBorderWidthLeft(0f);
                   cell.setBorderWidthBottom(0f);
                   table1.addCell(cell);
                   doc.add(table1);
                    
                    PdfPTable table2 = new PdfPTable(5);
                    table2.setWidthPercentage(100);
                    
                    table2.setWidths(new int[]{ 4, 2, 2, 2, 2});
                    p =new Paragraph("Product",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table2.addCell(cell);
                    p =new Paragraph("Opening Stock",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table2.addCell(cell);
                    p =new Paragraph("In Stock",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table2.addCell(cell);
                    p =new Paragraph("Out Stock",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table2.addCell(cell);
                    p =new Paragraph("Closing Stock",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
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
                    
                   
                    ArrayList<Float> in = new ArrayList<Float>();
                    ArrayList<Float> out = new ArrayList<Float>();
                    ArrayList<String> open = new ArrayList<String>();
                    ArrayList<String> close = new ArrayList<String>();
                    ArrayList<String> pro = new ArrayList<String>();
                    String query0 = "select * from Temp_Product";
                    PreparedStatement pmt0 = connection.prepareStatement(query0);
                    ResultSet rs0 = pmt0.executeQuery();
                    while(rs0.next())
                    {
                        in.add(rs0.getFloat("IN_Stock"));
                        out.add(rs0.getFloat("OUT_Stock"));
                        open.add(rs0.getString("Opening_Stock"));
                        close.add(rs0.getString("Closing_Stock"));
                        pro.add(rs0.getString("Product"));
                    }
                    pmt0.close();
                    rs0.close();
                                       
                    for(int i = 0;i<pro.size();i++)
                    {
                        p =new Paragraph(""+pro.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setFixedHeight(20f);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(0f);
                        table2.addCell(cell);
                        p =new Paragraph(open.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(0f);
                        table2.addCell(cell);
                        p =new Paragraph(""+in.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(0f);
                        table2.addCell(cell);
                        p =new Paragraph(""+out.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(0f);
                        table2.addCell(cell);
                        p =new Paragraph(""+close.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(1f);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(0f);
                        table2.addCell(cell);  
                    }
                    
                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
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
                    cell.setBorderWidthBottom(1f);
                    table2.addCell(cell);
                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table2.addCell(cell);  
                    
                    doc.add(table2);
                    
                    doc.close();
                    
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                try{
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"productreport.pdf");
                    
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        btnPrint.setMnemonic(KeyEvent.VK_END);
        btnPrint.setForeground(Color.BLACK);
        btnPrint.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnPrint.setBounds(633, 50, 115, 30);
        getContentPane().add(btnPrint);
        
        JTextField textField = new JTextField("");
        textField.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent arg0) {
        		try
				{
					String query = "select * from Temp_Product where Product like '%"+textField.getText()+"%' or Opening_Stock like '%"+textField.getText()+"%' or IN_Stock like '%"+textField.getText()+"%' or OUT_Stock like '%"+textField.getText()+"%'  or Closing_Stock like '%"+textField.getText()+"%'";
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
        textField.setBounds(10, 11, 125, 25);
        getContentPane().add(textField);
        
        JLabel lblProductReport = new JLabel("Product Report");
        lblProductReport.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblProductReport.setBounds(299, 11, 200, 25);
        getContentPane().add(lblProductReport);
        
       
    
    }

}
