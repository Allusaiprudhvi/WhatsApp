
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
import javax.servlet.http.HttpSession;

import allu.message;
@WebServlet("/group_chat")
public class group_chat extends chat {
	private static final long serialVersionUID = 1L;
    String group_name;        
    String admin;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 group_chat obj=new group_chat();
		try {
			   Class.forName("com.mysql.jdbc.Driver");  
			    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");   
			    
			      String val=request.getParameter("submit");
			      
			      if("Add To Group".equals(val))
			      {
			    	  obj.add_friend(request,response,con);
			      }
			      else if("delete".equals(val))
			      {
			    	  obj.delete_group(request,response,con);

			      }
			      else if("remove".equals(val))
			      {
			    	  obj.remove_friend(request,response,con);

			      }else if("leave".equals(val))
			      {
			    	  obj.leave_group(request,response,con);

			      } else if("delete_msg".equals(val))
			      {
			    	  obj.delete_msg(request,response,con);
			      }
			      
			      if(con!=null)
					  con.close();
			   }
			   catch(Exception e)
			   {
				   System.out.println(e);
			   }
			
		
		
	}
	
	public void delete_msg(HttpServletRequest request, HttpServletResponse response, Connection con) throws SQLException, IOException {
		

		PreparedStatement ps1=con.prepareStatement(  
				"delete from message where sent_time=? and sender=?");  
                    ps1.setString(1, request.getParameter("message"));

	                ps1.setString(2, request.getParameter("sender"));
					int i1=ps1.executeUpdate();
					response.sendRedirect("group_chat.jsp");
	}
	

	public void add_friend(HttpServletRequest request, HttpServletResponse response,Connection con) throws SQLException, ClassNotFoundException, IOException
	  {
		   int chat_id=0;
		   try {
		   PreparedStatement ps1=con.prepareStatement(  
					"select group_chat_id from group_chat where group_name=?");  
			ps1.setString(1,request.getParameter("group_name"));  

						ResultSet rs1=ps1.executeQuery();  
						while(rs1.next())  
							{
	                               chat_id=rs1.getInt(1);
							} 
						  PreparedStatement ps3=con.prepareStatement(  
									"insert into chat values(?,?,?,?)");  
									ps3.setInt(1,chat_id);  
									ps3.setString(2,request.getParameter("user_name"));  
									ps3.setString(3,"g");  
									ps3.setInt(4,0);  

									int i2=ps3.executeUpdate();  
									if(i2>0)
									{
										   HttpSession session=request.getSession();  
											 session.setAttribute("msg","joined group Successfully");  
										        response.sendRedirect("group_chat.jsp");  
									} 
		   }
		   catch(Exception e)
		   {
		        response.sendRedirect("group_chat.jsp");  
   
		   }
			  
									 
	  }
	public void delete_group(HttpServletRequest request, HttpServletResponse response,Connection con) throws ClassNotFoundException, IOException, SQLException
	{
	    			
	    			    PreparedStatement ps4=con.prepareStatement(  "select * from group_chat where group_name=?");  
	 				                ps4.setString(1, request.getParameter("group_name"));
	 				                
	 								ResultSet rs4=ps4.executeQuery();  
	 								
	 								while(rs4.next())  
	 									{
	 									chat_id=rs4.getInt(1);
	 									admin=rs4.getString(2);
	 									} 

	    				PreparedStatement ps1=con.prepareStatement(  
	    						"delete from group_chat where group_name=?");  
	    			                ps1.setString(1, request.getParameter("group_name"));
	    							int i1=ps1.executeUpdate();

						PreparedStatement ps2=con.prepareStatement(  
	    						"delete from message where chat_id=?");  
	    			                ps2.setInt(1, chat_id);
	    			                
						int i2=ps2.executeUpdate();

				        PreparedStatement ps3=con.prepareStatement(  
	    						"delete from chat where chat_id=?");  
	    			                ps3.setInt(1, chat_id);
	    			                
						int i3=ps3.executeUpdate();
						
							     HttpSession session=request.getSession();  
								 session.setAttribute("msg","joined group Successfully");  
					             response.sendRedirect("user_home.jsp");  
					             if(con!=null)
									  con.close();
					
	}
	public ArrayList<String> friends_list(String group_name) throws ClassNotFoundException
	{
        ArrayList<String> list=new ArrayList<String>();

		Class.forName("com.mysql.jdbc.Driver");  
		  int chat_id=0;
		  try {
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");
				 PreparedStatement ps1=con.prepareStatement(  
							"select * from group_chat where group_name=?");  
		         ps1.setString(1, group_name);
								ResultSet rs1=ps1.executeQuery();  
								while(rs1.next())  
									{
									chat_id=rs1.getInt(1);
									} 
								 PreparedStatement ps2=con.prepareStatement(  
											"select user.user_name,full_name,email_id,phone_number from user inner join chat on user.user_name=chat.user_name where chat_id=?");  
					                ps2.setInt(1,chat_id);
									ResultSet rs2=ps2.executeQuery();  
									while(rs2.next()){
				                          list.add(rs2.getString(1));

		                     }
									 if(con!=null)
										  con.close();
		  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
		  return list;
	}
	public void remove_friend(HttpServletRequest request, HttpServletResponse response,Connection con) throws SQLException, IOException
	{
		   PreparedStatement ps4=con.prepareStatement("select * from group_chat where group_name=?");  
            ps4.setString(1, request.getParameter("group_name"));
            
			ResultSet rs4=ps4.executeQuery();  
			
			while(rs4.next())  
				{
				chat_id=rs4.getInt(1);
				admin=rs4.getString(2);
				} 
			
			 PreparedStatement ps3=con.prepareStatement(  
						"delete from chat where chat_id=? and user_name=?");  
			                ps3.setInt(1, chat_id);
			                ps3.setString(2,request.getParameter("user_name") );

				int i3=ps3.executeUpdate();
				HttpSession session=request.getSession();  
				 session.setAttribute("msg","removed friend Successfully");  
	             response.sendRedirect("group_chat.jsp");  
	             if(con!=null)
					  con.close();
	}
	public void leave_group(HttpServletRequest request, HttpServletResponse response,Connection con) throws SQLException, IOException
	{
		   PreparedStatement ps4=con.prepareStatement("select * from group_chat where group_name=?");  
            ps4.setString(1, request.getParameter("group_name"));
            
			ResultSet rs4=ps4.executeQuery();  
			
			while(rs4.next())  
				{
				chat_id=rs4.getInt(1);
				admin=rs4.getString(2);
				} 
			
			 PreparedStatement ps3=con.prepareStatement(  
						"delete from chat where chat_id=? and user_name=?");  
			                ps3.setInt(1, chat_id);
			                ps3.setString(2,request.getParameter("user_name") );

				int i3=ps3.executeUpdate();
				HttpSession session=request.getSession();  
				 session.setAttribute("msg","removed friend Successfully");  
	             response.sendRedirect("user_home.jsp");  
	             if(con!=null)
					  con.close();
	}
	public String get_admin(String group_name) throws ClassNotFoundException, SQLException {

		 Class.forName("com.mysql.jdbc.Driver"); 
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");

		    try {
				 PreparedStatement ps1=con.prepareStatement(  
							"select * from group_chat where group_name=?");  
				                ps1.setString(1, group_name);
				                
								ResultSet rs1=ps1.executeQuery();  
								while(rs1.next())  
									{
									chat_id=rs1.getInt(1);
									admin=rs1.getString(2);
									} 
							
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				 if(con!=null)
				  con.close();
				}   
		    return admin;
	}
	
	public ArrayList<message> get_messages(String group_name) throws Exception
	{
		ArrayList <message> li=null;
		int chat_id=0;
		String admin="";
		message obj=new message();
		 Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");

		    try {
				 PreparedStatement ps1=con.prepareStatement(  
							"select * from group_chat where group_name=?");  
				                ps1.setString(1, group_name);
				                
								ResultSet rs1=ps1.executeQuery();  
								while(rs1.next())  
									{
									chat_id=rs1.getInt(1);
									admin=rs1.getString(2);
									} 
							li=obj.get_messages(chat_id);
							
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				 if(con!=null)
					  con.close();
					}    
		    return  li;

	}
	@Override
	void send_msg(int chat_id, String msg, String Sender) throws ClassNotFoundException, SQLException {
	    
		Class.forName("com.mysql.jdbc.Driver"); 
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");

	    try {
	   	 chat obj=new group_chat();
			PreparedStatement ps2=con.prepareStatement(  
					"insert into message(chat_id,message,sent_time,sent_date,sender) values(?,?,?,?,?)");  
			ps2.setInt(1,chat_id);
			ps2.setString(2, msg);
			ps2.setDate(4,obj.get_date());
			ps2.setTime(3,obj.get_time());
			ps2.setString(5, Sender);
			int i2=ps2.executeUpdate();  
			

			
		} catch (SQLException e) {
			e.printStackTrace();
		}   finally{
			 if(con!=null)
				  con.close();
				}  
		// TODO Auto-generated method stub
		
	}
}
