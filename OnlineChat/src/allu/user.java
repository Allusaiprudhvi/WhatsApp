package allu;
import java.sql.*;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/home")
public class user extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public user() 
    {
        super();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException  {
    	
      String val=req.getParameter("submit");
      try {
		Class.forName("com.mysql.jdbc.Driver");
		 Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");   
		 
	      if("logout".equals(val)) //logout
	      {
	    	 res.sendRedirect("index.jsp");
	      }
	      else  //redirecting to group_chat.jsp
	      {
	    	  HttpSession session=req.getSession();  
			  session.setAttribute("group_name",req.getParameter("group_name"));  

			  session.setAttribute("sender",req.getParameter("sender"));  
		        res.sendRedirect("group_chat.jsp");  
	      }
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}  
	   
      
    	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		try {
			 Class.forName("com.mysql.jdbc.Driver");  
			    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");   
			user obj=new user();
			res.setContentType("text/html");
		     PrintWriter pw=res.getWriter();
		   
		    
		   
		    String val=req.getParameter("submit");
		    
			if("Login".equals(val)) // goes to login method
			{
				obj.login(req.getParameter("user_name"),req.getParameter("password"),pw,con,req,res);
			}
			else if("Signup".equals(val)) // goes to signup method for registration
			{
				obj.create_account(req.getParameter("user_name"),req.getParameter("full_name"),req.getParameter("email"),req.getParameter("password1"),req.getParameter("phone_number"),req.getParameter("age"),pw,con,req,res);
			}
			else if("create".equals(val)) // goes to create method for creating new group
			{
				obj.create_group(req,res,con);
			}
			else if("join".equals(val))   // goes to join method for joining group
			{
				obj.join_group(req,res,con);
			}
			else  if("send".equals(val))    // goes to login method
			  {
				  int i=Integer.parseInt(req.getParameter("chat_id"));
				  obj.send_msg(i,req.getParameter("message"), req.getParameter("sender"));
			 	   HttpSession session=req.getSession();  
				  session.setAttribute("chat_id",i);  
			        res.sendRedirect("chat.jsp");  
			  }
			else if("send1".equals(val))
			{
				HttpSession session=req.getSession();  
				  session.setAttribute("group_name",req.getParameter("group_name"));  
				obj.send_msg(req.getParameter("group_name"),req.getParameter("message"), req.getParameter("sender"),con);
		        res.sendRedirect("group_chat.jsp");  

			}
			else if("search".equals(val))
			{
				obj.chat(req,res,pw,con);


			}
				else
			{
				obj.chat(req,res,pw,con);
		        res.sendRedirect("chat.jsp");  

			}
			 if(con!=null)
				  con.close();
			

		    }
	  	catch(Exception e)
		   {
			System.out.println(e);
		   }
		}
	
	public void send_msg(int  chat_id, String msg,String sender) throws ClassNotFoundException, SQLException {
		allu.individual_chat obj1=new allu.individual_chat();
		obj1.send_msg(chat_id,msg,sender);
			
		}
	public void send_msg(String group_name,String msg,String sender,Connection con) throws SQLException {
		PreparedStatement ps=con.prepareStatement("select group_chat_id from group_chat where group_name=?");
		ps.setString(1, group_name);
		ResultSet rs=ps.executeQuery();
		rs.next();
		allu.group_chat obj=new allu.group_chat();
		try {
			obj.send_msg(rs.getInt(1), msg, sender);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
 			 if(con!=null)
				  con.close();
				}  
		
	}
	
	
	public void chat(HttpServletRequest req, HttpServletResponse res,PrintWriter pw,Connection con)throws SQLException  // displays individual chat
	{
		
 	   HttpSession session=req.getSession();  

		int max_chat_id=0,chat_id=0;
		try {
		PreparedStatement ps=con.prepareStatement(  
				"select t1.chat_id from (select chat_id from chat where user_name=? and chat_type=?) as t1 inner join (select chat_id from chat where user_name=? and chat_type=?) as t2 on t1.chat_id=t2.chat_id");  
			ps.setString(1,req.getParameter("sender"));  
			ps.setString(2,"i");  
			ps.setString(3,req.getParameter("reciever")); 
			ps.setString(4,"i");                    // checking whether chat_id exits or not

	         ResultSet rs=ps.executeQuery();
             session.setAttribute("sender",req.getParameter("sender"));
             session.setAttribute("reciever",req.getParameter("reciever"));

	         if(rs.next())
             {
	               session.setAttribute("chat_id",rs.getInt(1));

             }
	         else
	         {

	        	 PreparedStatement ps1=con.prepareStatement(  
	        				"select max(chat_id) from chat");  
	        					ResultSet rs1=ps1.executeQuery();  
	        					while(rs1.next())  
	        						{
	        						max_chat_id=rs1.getInt(1);	        						
	        						} 
                    chat_id=max_chat_id+1;
                    System.out.println(chat_id);
	        			    	PreparedStatement ps2=con.prepareStatement(  
	        						"insert into chat values(?,?,?,?)");  
	        						ps2.setInt(1,chat_id);                             //if chat_id does not exist ,creates a new chat-id and redirects to home page
	        						ps2.setString(2,req.getParameter("sender"));  
	        						ps2.setString(3,"i");  
	        						ps2.setInt(4, 0);

	        						int i1=ps2.executeUpdate();  

	        						PreparedStatement ps3=con.prepareStatement(  
	    	        						"insert into chat values(?,?,?,?)");  
	    	        						ps3.setInt(1,chat_id);  
	    	        						ps3.setString(2,req.getParameter("reciever"));  
	    	        						ps3.setString(3,"i");  
	    	        						ps3.setInt(4, 0);
	    	        						int i2=ps3.executeUpdate();  
	  
	    	        						session.setAttribute("chat_id",rs.getInt(1));
	         }

		}
		catch(Exception e)
		{
			
		}
		finally{
 			 if(con!=null)
				  con.close();
				}  

	}

	
	public int login(String uname,String pwd, PrintWriter pw,Connection con,HttpServletRequest req, HttpServletResponse res) throws SQLException
	{
		
		try
		{
		String user_name="";
		String password="";
		//HttpSession session=req.getSession();  
//System.out.println(uname+pwd);
	       HttpSession session=req.getSession();  

PreparedStatement ps=con.prepareStatement(  
	"select * from user where user_name=?");  
ps.setString(1,uname);  

		ResultSet rs=ps.executeQuery();  
		while(rs.next())  
			{
			user_name=rs.getString(1);
			 password=rs.getString(3);  
			
			}

		if(user_name.equals(uname))
		{
	        
	        if(password.equals(pwd))
	        {
		     session.setAttribute("user_name",user_name);  	//redirects to home page if user_name and password are correct else goes to login page
	    		con.close(); 

		        res.sendRedirect("user_home.jsp");   

	        	return 1;
	        }
	        else
	        {
	        	
				 session.setAttribute("error","Password Incorrect");
		    		con.close(); 

			        res.sendRedirect("index.jsp");   

                return 0;
	        }
		}
		else
		{
		pw.println("<html><p style='red'>User Does Not Exist</p></html>");
	     req.getRequestDispatcher("index.jsp").include(req, res);  
	     session.setAttribute("error","Username Incorrect"); 
			con.close(); 

	        res.sendRedirect("index.jsp");   

              return 2;
              
		}
				
		}
		catch(Exception e)
		{
			pw.println(e);
			return 4;

		}finally{
 			 if(con!=null)
				  con.close();
				}  
	}
	
	
	public void create_account(String uname,String fname,String email,String pwd,String phno,String age, PrintWriter pw,Connection con,HttpServletRequest req, HttpServletResponse res) throws SQLException
	{
		try
		{
	    	PreparedStatement ps=con.prepareStatement(  
				"insert into user values(?,?,?,?,?,?,?)");  
				ps.setString(1,uname);  
				ps.setString(2,fname);  
				ps.setString(3,pwd);  
				ps.setString(4,email);  
				ps.setString(5,phno);  
                ps.setString(6, age);
                ps.setBoolean(7, false);
				int i=ps.executeUpdate();  

				if(i>0)  	
				{
					pw.println("<html><p style='red'>Account created Successfully</p></html>");
					HttpSession session=req.getSession();  
					session.setAttribute("msg","Account Created Successfully");  
					con.close(); 

			        res.sendRedirect("index.jsp");   
				}
				
		}
		catch(Exception e)
		{
			pw.println(e);
		}finally{
 			 if(con!=null)
				  con.close();
				}  
		
	}
	
	
  public ArrayList<String> dataList() throws ClassNotFoundException, SQLException{
	  Class.forName("com.mysql.jdbc.Driver");  
		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");   
           ArrayList<String> list=new ArrayList<String>();
           try{
        	   
   		 Statement st=con.createStatement();
         ResultSet rs=st.executeQuery("select * from user");          // list of active users
                      while(rs.next()){
                          list.add(rs.getString(1));
                          

           }
           } 
           catch(Exception e){}finally{
  			 if(con!=null)
				  con.close();
				}  
           return   list;

           }
  
  public ArrayList<String> GroupList(String user_name) throws SQLException, ClassNotFoundException{
      ArrayList<String> list=new ArrayList<String>();
      Class.forName("com.mysql.jdbc.Driver");  
	    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");   
      try{
   	  
    PreparedStatement ps=con.prepareStatement("select * from (select chat_id,chat_type,user.user_name from user inner join chat on user.user_name=chat.user_name having chat.chat_type=? and user.user_name=?) as t1 inner join group_chat on t1.chat_id=group_chat.group_chat_id;");
 	ps.setString(1,"g");
 	ps.setString(2,user_name);
                                                         //list of groups
     ResultSet rs=ps.executeQuery();

    while(rs.next()){
                     list.add(rs.getString(6));
                     

      }
      } 
      catch(Exception e){}finally{
			 if(con!=null)
				  con.close();
				}  
      return   list;

      }

  public ArrayList <String> my_profile(String user_name) throws ClassNotFoundException, SQLException{
	  Class.forName("com.mysql.jdbc.Driver");  
	    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");   
	  ArrayList<String> list=new ArrayList<String>();
      try{
   	        
                 
                 PreparedStatement ps=con.prepareStatement("select * from user where user_name=?");
     	    	ps.setString(1,user_name);
     	         ResultSet rs=ps.executeQuery();

                 while(rs.next()){
                     list.add(rs.getString(1));                    //for personal information
                     list.add(rs.getString(2));
                     list.add(rs.getString(4));
                     list.add(rs.getString(5));
                     list.add(rs.getString(6));
                     list.add(rs.getString(7));
                                  }
      } 
      catch(Exception e){}finally{
			 if(con!=null)
				  con.close();
				}  
      return   list;

      }
  

  public void create_group(HttpServletRequest request, HttpServletResponse response,Connection con) throws SQLException, ClassNotFoundException, IOException
  {
	   int max_chat_id=0,chat_id;
	   
	   PreparedStatement ps1=con.prepareStatement(  
				"select max(chat_id) from chat");  
					ResultSet rs1=ps1.executeQuery();  
					while(rs1.next())  
						{
						max_chat_id=rs1.getInt(1);	        						
						} 
      chat_id=max_chat_id+1;
	   
	   PreparedStatement ps2=con.prepareStatement(  
				"insert into chat values(?,?,?,?)");  
				ps2.setInt(1,chat_id);  
				ps2.setString(2,request.getParameter("user_name"));  
				ps2.setString(3,"g");
				ps2.setInt(4, 0);

				int i1=ps2.executeUpdate();  
				
				
				   PreparedStatement ps3=con.prepareStatement(  
							"insert into group_chat values(?,?,?)");  
							ps3.setInt(1,chat_id);  
							ps3.setString(2,request.getParameter("user_name"));  
							ps3.setString(3,request.getParameter("group_name"));  
							int i2=ps3.executeUpdate();  
							
							if(i1>0 && i2>0)
							{
					        HttpSession session=request.getSession();  
							 session.setAttribute("msg","Group Created Successfully");  
								con.close(); 

						        response.sendRedirect("user_home.jsp");  
							}
						        }
  
  
  public void join_group(HttpServletRequest request, HttpServletResponse response,Connection con) throws SQLException, ClassNotFoundException, IOException
  {
	   int chat_id=0;
	   PreparedStatement ps1=con.prepareStatement(  
				"select group_chat_id from group_chat where group_name=?");  
		ps1.setString(1,request.getParameter("group_name"));  

					ResultSet rs1=ps1.executeQuery();  
					while(rs1.next())  
						{
                               chat_id=rs1.getInt(1);
						} 
					  PreparedStatement ps3=con.prepareStatement(  
								"insert into chat() values(?,?,?,?)");  
								ps3.setInt(1,chat_id);  
								ps3.setString(2,request.getParameter("user_name"));  
								ps3.setString(3,"g");  
								ps3.setInt(4, 0);
								int i2=ps3.executeUpdate();  
								if(i2>0)
								{
									   HttpSession session=request.getSession();  
										 session.setAttribute("msg","joined group Successfully");  
											con.close(); 

									        response.sendRedirect("user_home.jsp");  
								}
		  
		   
  }
	
	  
  
}
