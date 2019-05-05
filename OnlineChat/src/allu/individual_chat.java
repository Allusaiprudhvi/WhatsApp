

package allu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allu.group_chat;
import allu.message;

/**
 * Servlet implementation class individual_chat
 */
@WebServlet("/individual_chat")
public class individual_chat extends allu.chat {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			   Class.forName("com.mysql.jdbc.Driver");  
			    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");   
			    
			     String val=request.getParameter("submit");
				 chat obj=new individual_chat();

			  if("send".equals(val))
			  {
				  int i=Integer.parseInt(request.getParameter("chat_id"));
				  obj.send_msg(i,request.getParameter("message"), request.getParameter("sender"));
				  request.setAttribute("chat_id",i);  

		  	         request.getRequestDispatcher("chat.jsp").forward(request, response);  
			  }
			  else if("delete_msg".equals(val))
		      {
				  individual_chat obj1=new individual_chat();
		    	  obj1.delete_msg(request,response,con);
		      }
			  else if("block".equals(val))
		      {
				  individual_chat obj1=new individual_chat();
		    	  obj1.block_user(request,response,con);
		      }
			     
			     
			   }
			   catch(Exception e)
			   {
				   System.out.println(e);
			   }
		
		
		
	}
	
public void block_user(HttpServletRequest request, HttpServletResponse response, Connection con) throws SQLException, IOException {
		
	
	PreparedStatement ps=con.prepareStatement(  
			"select t1.chat_id from (select chat_id from chat where user_name=? and chat_type=?) as t1 inner join (select chat_id from chat where user_name=? and chat_type=?) as t2 on t1.chat_id=t2.chat_id");  
		ps.setString(1,request.getParameter("sender"));  
		ps.setString(2,"i");  
		ps.setString(3,request.getParameter("reciever")); 
		ps.setString(4,"i");                    // checking whether chat_id exits or not

         ResultSet rs=ps.executeQuery();
        rs.next();

		PreparedStatement ps1=con.prepareStatement(  
				"update chat set blocked=? where chat_id =?");  
		            ps1.setInt(1, 1);
                    ps1.setInt(2,rs.getInt(1));
					int i1=ps1.executeUpdate();
					response.sendRedirect("chat.jsp");
	}
	
public int is_blocked(String sender,String reciever) throws ClassNotFoundException, SQLException
{	Class.forName("com.mysql.jdbc.Driver");
Connection con;
	con = DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");

	PreparedStatement ps=con.prepareStatement(  
			"select t1.chat_id from (select chat_id from chat where user_name=? and chat_type=?) as t1 inner join (select chat_id from chat where user_name=? and chat_type=?) as t2 on t1.chat_id=t2.chat_id");  
		ps.setString(1,sender);  
		ps.setString(2,"i");  
		ps.setString(3,reciever); 
		ps.setString(4,"i");                    // checking whether chat_id exits or not

         ResultSet rs=ps.executeQuery();
        rs.next();

	
	try {
		
		PreparedStatement ps1=con.prepareStatement(  
				"select blocked from chat where chat_id=?");  
	                 ps1.setInt(1, rs.getInt(1));
					ResultSet rs1=ps1.executeQuery();  
					rs1.next();

			return	rs1.getInt(1);
					
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 0;

	}

}
public void delete_msg(HttpServletRequest request, HttpServletResponse response, Connection con) throws SQLException, IOException {
		

		PreparedStatement ps1=con.prepareStatement(  
				"delete from message where sent_time=? and sender=?");  
                    ps1.setString(1, request.getParameter("message"));

	                ps1.setString(2, request.getParameter("sender"));
					int i1=ps1.executeUpdate();
					response.sendRedirect("chat.jsp");
	}
	
	
	public ArrayList<message> get_messages(int chat_id) throws Exception
	{

		ArrayList <message> li;
		message obj1=new message();
		    
							li=obj1.get_messages(chat_id);

		
		    return  li;

	}



	//@Override
   public void send_msg(int  chat_id, String msg,String sender) throws ClassNotFoundException, SQLException {
		
		  Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");

		    try {
		   	 chat obj=new individual_chat();
				PreparedStatement ps2=con.prepareStatement(  
						"insert into message(chat_id,message,sent_time,sent_date,sender) values(?,?,?,?,?)");  
				ps2.setInt(1,chat_id);
				ps2.setString(2, msg);
				ps2.setDate(4,obj.get_date());
				ps2.setTime(3,obj.get_time());
				ps2.setString(5, sender);
				int i2=ps2.executeUpdate();  

				
			} catch (SQLException e) {
				e.printStackTrace();
			}  finally{
	  			 if(con!=null)
					  con.close();
					}   
	}




}
