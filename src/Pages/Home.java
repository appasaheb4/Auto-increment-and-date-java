package Pages;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import Pages.DBConnection;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Home extends JFrame {

	private JPanel contentPane;
	private JDateChooser txtFormDate;
	private JDateChooser txtToDate;
	private JTextField txtSrNo;
	
	
	//Database conneciton
	
	Connection conn;
	Statement st;
	ResultSet rs;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sr No :");
		lblNewLabel.setBounds(6, 22, 42, 16);
		contentPane.add(lblNewLabel);
		
		txtSrNo = new JTextField();
		txtSrNo.setEnabled(false);
		txtSrNo.setBounds(60, 17, 130, 26);
		contentPane.add(txtSrNo);
		txtSrNo.setColumns(10);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Form Date :");
		lblNewLabel_1.setBounds(213, 22, 73, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("To Date :");
		lblNewLabel_2.setBounds(516, 22, 57, 16);
		contentPane.add(lblNewLabel_2);
		
		txtToDate = new JDateChooser();
		txtToDate.setDateFormatString("dd/MM/yyyy");
		txtToDate.setEnabled(false);
		txtToDate.setBounds(585, 17, 206, 26);
		
		//To Date set 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); 
		c.add(Calendar.DATE, 100); 
		String output = sdf.format(c.getTime());
		 try {
			Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(output);
			txtToDate.setDate(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		//txtToDate.setDate(output);
		contentPane.add(txtToDate);
		
		txtFormDate = new JDateChooser();
		txtFormDate.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
				String dt = formatter.format(txtFormDate.getDate()); 
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(sdf.parse(dt));
					c.add(Calendar.DATE, 100);  // number of days to add
					dt = sdf.format(c.getTime());  // dt is now the new date
					Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(dt);
					txtToDate.setDate(date2);  
					System.out.println(dt.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		txtFormDate.setDateFormatString("dd/MM/yyyy");
		txtFormDate.setBounds(298, 17, 206, 26);
		Date date = new Date();
		txtFormDate.setDate(date);
		contentPane.add(txtFormDate);
		
		
		
		
		//Srno show  
		txtSrNo.setText(getAutoNo());
		
		
	}
	
	public String  getAutoNo() {
		String autoValue = null;
		try {
			conn = DBConnection.getConnection();     
			st = (Statement) conn.createStatement();
			rs = st.executeQuery("SELECT AUTO_INCREMENT as autoValue FROM INFORMATION_SCHEMA.TABLES\n" + 
					"WHERE TABLE_SCHEMA = 'FinanceSoftware' AND TABLE_NAME = 'tblLoanEntryCash'");
			if (rs.next()) {
				autoValue =  rs.getString("autoValue");  
			}  else {  
//				sd.setBounds(470, 110, 360, 210);
//				sd.lblTitle.setText("Fail!");
//				sd.lblMessage.setText(cons.kmsgEnterCorrectPassword);
//				sd.setUndecorated(true);
//				sd.setModal(true);
//				sd.setVisible(true);
			}
		} catch (Exception ee) {
//			sd.setBounds(470, 110, 360, 210);
//			sd.lblTitle.setText("Fail!");
//			sd.lblMessage.setText("Please start mysql database.");
//			sd.setUndecorated(true);
//			sd.setModal(true);
//			sd.setVisible(true);
			System.out.println(ee.getMessage());
		}
		return  autoValue;
	}
}
