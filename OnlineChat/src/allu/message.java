
package allu;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/message")
public class message extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   private String content;
  private Time time;
  private Date date;
   private String sender;
   private String reciever;
   private String chat_type;
   public message()
   {
	   
   }
   public message(String content,Time time,Date date,String sender,String reciever)
   {
	   this.content=content;
	   this.date=date;
	   this.sender=sender;
	   this.reciever=reciever;
	   this.time=time;
   }
   public message(String content,Time time,Date date,String sender,String reciever,String chat_type)
   {
	   this.content=content;
	   this.date=date;
	   this.sender=sender;
	   this.reciever=reciever;
	   this.time=time;
	   this.chat_type=chat_type;
   }
	
   
   public String get_content()
   {
	   return content;
   }
   
   public Time get_time()
   {
	   return time;
   }
   
   public Date get_date()
   {
	   return date;
   }
   public String get_sender()
   {
	   return sender;
   }
   public String get_reciever()
   {
	   return reciever;
   }
   
   public String get_chat_type()
   {
	   return chat_type;
   }
   
   public ArrayList<message> get_messages(int chat_id) throws Exception
   {
	   ArrayList<message> li = new ArrayList();
	   try {
		
		   Class.forName("com.mysql.jdbc.Driver");
	       Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");   
	   
	    PreparedStatement ps1=con.prepareStatement(  
				"select * from message where chat_id=?");  
	                 ps1.setInt(1, chat_id);
					ResultSet rs1=ps1.executeQuery();  
					while(rs1.next())  
						{                             
						li.add(new message(rs1.getString(2),rs1.getTime(3),rs1.getDate(4),rs1.getString(5),rs1.getString(6))); 

						} 
	    

	} catch (Exception e) {
		e.printStackTrace();
	}  
	   return li;
   }
   public ArrayList<message> get_messages(String user_name) throws Exception
   {
   
	   ArrayList<message> li = new ArrayList();
	   try {
		
		   Class.forName("com.mysql.jdbc.Driver");
	       Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");   
	   
	    PreparedStatement ps1=con.prepareStatement(  
				"select t1.chat_id,chat_type,message,sent_time,sent_date,sender,reciever from (select chat_id,chat_type from chat where user_name=?) as t1 inner join (select * from message order by sent_date desc,sent_time desc) as t2 on t1.chat_id=t2.chat_id group by t1.chat_id order by sent_date desc,sent_time desc;");  
        ps1.setString(1, user_name);
	    ResultSet rs1=ps1.executeQuery();  
					while(rs1.next())  
						{
						if(rs1.getString(2).equals("g"))
						{

						    try {
								 PreparedStatement ps2=con.prepareStatement(  
											"select * from group_chat where group_chat_id=?");  
								                ps2.setInt(1, rs1.getInt(1));
								                
												ResultSet rs2=ps2.executeQuery();  
												
												rs2.next();
																								
						                        li.add(new message(rs1.getString(3),rs1.getTime(4),rs1.getDate(5),rs1.getString(6),rs2.getString(3),rs1.getString(2))); 

							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						else
						{
							try {
							 PreparedStatement ps3=con.prepareStatement(  
										"select * from chat where chat_id=? and user_name<>?");  
							                ps3.setInt(1, rs1.getInt(1));
							                ps3.setString(2, rs1.getString(6));
											ResultSet rs3=ps3.executeQuery();  
											
											rs3.next();
											if(!rs3.getString(2).equals(user_name))
							li.add(new message(rs1.getString(3),rs1.getTime(4),rs1.getDate(5),rs1.getString(6),rs3.getString(2),rs1.getString(2))); 	
											else
											li.add(new message(rs1.getString(3),rs1.getTime(4),rs1.getDate(5),rs1.getString(6),rs1.getString(6),rs1.getString(2))); 	
							}catch(SQLException e)
							{
								e.printStackTrace();

							}
						}
						} 
	    

	} catch (Exception e) {
		e.printStackTrace();
	}  
	   return li;
   }
}
