

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
import javax.swing.JLabel;
import java.awt.Color;

public class View_staff_issue extends JInternalFrame {
	private JTable table;
    private TableModel model;
    public int id;
    private JTextField textField;
    
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
					View_staff_issue frame = new View_staff_issue();
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
	public View_staff_issue() {
		
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
        scrollPane.setBounds(0, 107, 758, 324);
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

        final Connection connection = Databaseconnection.connection2();
        
        try {
            // pull data from the database 
            
            String query = "select * from Staff_issue";
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
                        		ArrayList<String> product = new ArrayList<String>();
                                ArrayList<Float> qty = new ArrayList<Float>(); 
                                
                                String query5 = "Select * from Staff_issue_detail where S_id="+id;
                                PreparedStatement pmt5 = connection.prepareStatement(query5);
                                ResultSet rs5 = pmt5.executeQuery();
                                while(rs5.next())
                                {
                                    product.add(rs5.getString("Product"));
                                    qty.add(rs5.getFloat("Qty"));
                                }
                                
                                for (int i=0;i<product.size();i++)
                                {
                            		String query4 = "UPDATE Stock set Stock =Stock+"+qty.get(i)+" where product='"+product.get(i)+"'";
                                    PreparedStatement pmt4 = connection.prepareStatement(query4);
                                    pmt4.executeUpdate();
                                    pmt4.close();
                                    
                                    String query41 = "UPDATE Product set Stock =Stock+"+qty.get(i)+" where product='"+product.get(i)+"'";
                                    PreparedStatement pmt41 = connection.prepareStatement(query41);
                                    pmt41.executeUpdate();
                                    pmt41.close();
                                }
                                
                                
                               String query4 = "delete from Staff_issue_detail where S_id="+id;
                               PreparedStatement pmt4 = connection.prepareStatement(query4);
                               pmt4.executeUpdate();
                               pmt4.close(); 
                               
                               String query = "delete from Staff_issue where ID="+id;
                               PreparedStatement pmt = connection.prepareStatement(query);
                               pmt.executeUpdate();
                               pmt.close();
                           
                           
                        }
                        catch (SQLException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        View_staff_issue b = new View_staff_issue();
                        JDesktopPane desktopPane = getDesktopPane();
                        desktopPane.add(b);
                        
                        b.show();
                        dispose();
            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDelete.setBounds(10, 56, 122, 39);
        getContentPane().add(btnDelete);
        
        JButton btnBillDetails = new JButton("Detail");
        btnBillDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try
                {
                    String t3=null;
                    String n = null;
                    String date =null;
                    String query3 = "select * from Staff_issue where ID ='"+id+"'";
                    
                    PreparedStatement pmt3 = connection.prepareStatement(query3);
                   
                    ResultSet rs3 = pmt3.executeQuery();
                    while(rs3.next())
                    {
                         n = rs3.getString("Project_name");                                            
                         date = rs3.getString("Date");
                    }
                    rs3.close();
                    Detail_staff_issue s = new Detail_staff_issue();
                    
                    s.bill.setText(Integer.toString(id));
                    s.name.setText(n);
                    
                    String query = "select ID,Job_no,Product,Qty,Person,Remark from Staff_issue_detail where S_id ="+id+"";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    s.table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                    JDesktopPane desktopPane = getDesktopPane();
                    desktopPane.add(s);
                    s.show();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                
            }
        });
        btnBillDetails.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnBillDetails.setBounds(575, 55, 125, 40);
        getContentPane().add(btnBillDetails);
        
        JButton button = new JButton("Print");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               
                try{
                    String issue_no=null,project_name=null,date=null,supervisor_name=null;
                    String query1 = "select * from Staff_issue where ID ='"+id+"'";
                    PreparedStatement pmt1 = connection.prepareStatement(query1);
                    ResultSet rs1 = pmt1.executeQuery();
                    while(rs1.next())
                    {
                         date = rs1.getString("Date"); 
                         issue_no= rs1.getString("Issue_no");
                         project_name = rs1.getString("Project_name");
                         supervisor_name= rs1.getString("Supervisor_name");
                    }
                    rs1.close();
                    pmt1.close();
                    
                    String add1 =null,add2=null,add3=null,state=null,city=null;
                   
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
                    
                   p =new Paragraph("Project Name: "+project_name,FontFactory.getFont(FontFactory.HELVETICA,16,Font.PLAIN,BaseColor.BLACK));
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
                   cell.setBorderWidthBottom(1f);
                   table1.addCell(cell);
                   
                   p =new Paragraph("Sleep No.: "+issue_no,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                   cell = new PdfPCell(p);
                   cell.setUseBorderPadding(true);
                   cell.setVerticalAlignment(Element.ALIGN_CENTER);
                   cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                   cell.setBorderWidthTop(0f);
                   cell.setBorderWidthRight(1f);
                   cell.setBorderWidthLeft(1f);
                   cell.setBorderWidthBottom(1f);
                   table1.addCell(cell);
                  
                   doc.add(table1);
                    
                    PdfPTable table2 = new PdfPTable(6);
                    table2.setWidthPercentage(100);
                    
                    table2.setWidths(new int[]{ 1, 2, 2, 2,4,2});
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
                    p =new Paragraph("Job No.",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table2.addCell(cell);
                    p =new Paragraph("Person",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table2.addCell(cell);
                    
                    p =new Paragraph("Remark",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table2.addCell(cell);
                    
                    p =new Paragraph("Material Name",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table2.addCell(cell);
                    p =new Paragraph("Qty",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
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
                    ArrayList<String> remark = new ArrayList<String>();
                    ArrayList<String> job_no = new ArrayList<String>();
                    ArrayList<String> product = new ArrayList<String>();
                    ArrayList<String> uni = new ArrayList<String>();
                    ArrayList<String> person = new ArrayList<String>();
                    String query0 = "select * from Staff_issue_detail where S_id='"+id+"'";
                    PreparedStatement pmt0 = connection.prepareStatement(query0);
                    ResultSet rs0 = pmt0.executeQuery();
                    while(rs0.next())
                    {
                        qty.add(rs0.getFloat("Qty"));
                        remark.add(rs0.getString("Remark"));
                        product.add(rs0.getString("Product"));
                        job_no.add(rs0.getString("Job_no"));
                        person.add(rs0.getString("Person"));
                    }
                    pmt0.close();
                    rs0.close();
                    int total1 =0;
                    
                    for(int i = 0;i<product.size();i++)
                    {
                        String query = "select * from Product where Product='"+product.get(i)+"'";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        ResultSet rs = pmt.executeQuery();
                        while(rs.next())
                        {
                            uni.add(rs.getString("Unit"));
                        }
                        pmt.close();
                        rs.close();
                    }
                    for(int i = 0;i<product.size();i++)
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
                        p =new Paragraph(job_no.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(0f);
                        table2.addCell(cell);
                        
                        p =new Paragraph(person.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(0f);
                        table2.addCell(cell);
                        
                        p =new Paragraph(remark.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(0f);
                        table2.addCell(cell);
                        p =new Paragraph(""+product.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(0f);
                        table2.addCell(cell);
                        p =new Paragraph(""+qty.get(i)+" "+uni.get(i),FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
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
                    for(int i = 0;i<22-product.size();i++)
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
                        cell.setBorderWidthRight(1f);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(0f);
                        table2.addCell(cell);
                       
                    }
                    doc.add(table2);
                    
                    DecimalFormat df = new DecimalFormat("#0.00");
                    
                    PdfPTable tablep = new PdfPTable(2);
                    tablep.setWidthPercentage(100);
                    tablep.setWidths(new int[]{ 6, 4});
                    
                    p =new Paragraph(" Supervisor Name: "+supervisor_name,FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(0f);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(0f);
                    tablep.addCell(cell);
                    
                    p =new Paragraph("Signature :",FontFactory.getFont(FontFactory.HELVETICA,14,Font.PLAIN,BaseColor.BLACK));
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
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBounds(142, 56, 135, 39);
        getContentPane().add(button);
        
        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                try {
                    
                    // pull data from the database 
                    
                    String query = "select * from Staff_issue where Issue_no like '%"+textField.getText()+"%' or Project_name like '%"+textField.getText()+"%' or Date like '%"+textField.getText()+"%' or Supervisor_name like '%"+textField.getText()+"%'";
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
        textField.setBounds(287, 61, 135, 35);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JLabel label = new JLabel("Staff Issue");
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Tahoma", Font.BOLD, 20));
        label.setBounds(286, 12, 125, 38);
        getContentPane().add(label);
    }

}
