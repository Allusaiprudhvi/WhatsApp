package allu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class chat
 */
@WebServlet("/chat")
public abstract class chat extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String chat_type;
	int chat_id;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 group_chat obj=new group_chat();
		try {
			   Class.forName("com.mysql.jdbc.Driver");  
			    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3300/OnlineChat","root","omshiridisai");   
			    
			      String val=request.getParameter("submit");
			      
			     
		}
		 catch(Exception e)
		   {
			   System.out.println(e);
		   }
	}

	
	public String get_chat_type()
	{
		return chat_type;
	}
	
	public void set_chat_type(String chat_type)
	{
		this.chat_type=chat_type;
	}

	 java.util.Date date = new java.util.Date();
     long t = date.getTime();
     java.sql.Date sqlDate 	= new java.sql.Date(t);
     java.sql.Time sqlTime = new java.sql.Time(t);
	
     public java.sql.Date get_date()
     {
    	 java.util.Date date = new java.util.Date();
         long t = date.getTime();
         java.sql.Date sqlDate = new java.sql.Date(t);
         return sqlDate;
     }
     
     public java.sql.Time get_time()
     {

    	 java.util.Date date = new java.util.Date();
         long t = date.getTime();
         java.sql.Time sqlTime = new java.sql.Time(t);
         return sqlTime;
     }
	abstract void send_msg(int chat_id,String msg,String Sender) throws ClassNotFoundException, SQLException;

	
	
	
}
