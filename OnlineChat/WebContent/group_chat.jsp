<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Add icon library -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.btn {
  background-color:white;
  border: none;
  color:  white;
  padding: 12px 16px;
  font-size: 16px;
  cursor: pointer;
}
.btn:hover {
  background-color: RoyalBlue;
}
table {
  width:100%;
}
table, th, td {
text-align:left;

  border: 1px solid black;
  border-collapse: collapse;
}
th, td {
text-align:left;
  padding: 15px;
}
table#t01 tr:nth-child(even) {
  background-color: #eee;
}
table#t01 tr:nth-child(odd) {
 background-color: #fff;
}
table#t01 th {
  background-color: black;
  color: white;
}
</style>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="style.css">
<link href="main_css/style.css" rel="stylesheet" type="text/css" media="all" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="main_css/font-awesome.css" rel="stylesheet"> 
<title>Online Chat Application</title>
</head>
<body>
<%@page import="java.util.ArrayList"%>      <%--Importing all the dependent classes--%>
<%@page import="allu.individual_chat"%>
<%@page import="allu.user"%>
<%@page import="allu.*"%>
<%@page import ="java.sql.*"%>
<%@page import="java.util.Iterator"%> 
 <%@page import ="java.io.*" %>
  <%@page import ="javax.servlet.*" %>

 <%--Assigning ArrayList object containing Employee data to the local object --%>
   <div class="menu">
             <header class="header">
      <ul class="main-nav">
          <li><a href="javascript:fun1()">Chat</a></li>
          <li><a href="javascript:fun2()">Group Profile</a></li>
          <%
          group_chat obj1=new group_chat();

          if(obj1.get_admin((String)session.getAttribute("group_name")).equals(session.getAttribute("sender"))){
        	  
        	 %>
          <li><a href="javascript:fun3()">Delete Group</a></li><%} %>
          <li><a href="javascript:fun4()">Add Friend</a></li>
                    <li><a href="javascript:fun5()">Leave Group</a></li>
          
          <li>          <a href="user_home.jsp" class="btn"><i class="fa fa-home"></i> Home</a></li>
         <li style="margin-left:300px;font-size:20px;color:white"> <b> <%=session.getAttribute("group_name")%></b>
      </li>
      </ul>
      
	</header> 
	</div>
	
 <section id="a">
         
        
    <ol class="chat">
  <%
   group_chat obj=new group_chat();

session.setAttribute("user_name",session.getAttribute("sender"));
  ArrayList <message> li = (ArrayList) obj.get_messages((String)session.getAttribute("group_name"));  
  Iterator<message> iterator = li.iterator();  // Iterator interface

  while(iterator.hasNext()) 
      {
          message msg = iterator.next(); 
 
  if(msg.get_sender().equals(session.getAttribute("sender")))
    {%>
     <li class="self">
     <div class="msg" >
     <div style="margin-left:50%">
      <form action="group_chat" method="post">
     <input type="text" style="display:none" name="sender" value="<%=msg.get_sender()%>">
     <input type="text" style="display:none" name="message"  value="<%=msg.get_time()%>" style="display:none">
     <button type="submit" value="delete_msg" name="submit" class="btn"><i class="fa fa-trash"></i></button>
     </form></div>
     
    
        <b><%=msg.get_content()%></b>
                   <div style="padding:5px">  <time><%=msg.get_time()%></time> </div>
        
      </div>
    </li>
    <%}else{ %>
    
    <li class="other">
     <div class="msg">
      <div style="margin-left:50%">

      <form action="group_chat" method="post">
     <input type="text" name="sender" value="<%=msg.get_sender()%>" style="display:none">
     <input type="time" name="message"  value="<%=msg.get_time()%>" style="display:none">
     <button type="submit" value="delete_msg" name="submit" class="btn"><i class="fa fa-trash"></i></button>
     </form></div>
                                  <p style="margin-left:50%">--<%=msg.get_sender()%></p>
                                          <b><%=msg.get_content()%></b>
                                  
                   <div style="padding:5px">   <time ><%=msg.get_time()%></time> </div>
      
      
      
        
      </div>
     
    </li>
    
    <%
    }}
    %>
    </ol>
    <div id="g"></div>
    <form action="home" method="post">
    <input class="textarea" type="text" id="m" name="message" placeholder="Type here!"/><div class="emojis"  ><input type="submit" style=" position: fixed;
    display: block;
    bottom: 0px;
    width: 134px;
    height: 50px;
    background-repeat: no-repeat;
    background-size: cover;
    z-index: 100;
    cursor: pointer;" name="submit" value="send1" ></div>
    <input type="text" name="sender" value="<%=session.getAttribute("sender")%>" style="display:none">
    <input type="text" name="group_name" value="<%=session.getAttribute("group_name")%>" style="display:none">
    </form>
    </section>
    <section id="b" style="display:none;margin-top:10%;text-align">
  
  <div style="text-align:center"> <h1 style="font-size:50px">Group Members</h1></div>
  <%      String admin=obj1.get_admin((String)session.getAttribute("group_name"));
 %>
        Admin <p><%=admin %></p>
  
  <table id="t01">
  <tr><th>Users</th><th>Only Admin can remove users?</th></tr>
  <tr>
  <%
      group_chat obj2= new group_chat();
      ArrayList<String> li2 = (ArrayList)obj2.friends_list((String)session.getAttribute("group_name"));
      Iterator<String> iterator2=li2.iterator(); 

      while(iterator2.hasNext())  // iterate through all the data until the last record
         {
    	  
            String users = iterator2.next(); //assign individual employee record to the employee class object
%>

<form action="group_chat" method="post">
<input type="text" name="user_name" value=<%=users%> style="display:none">
<input type="text" name="group_name" value=<%=session.getAttribute("group_name")%> style="display:none">
<td><%=users%></td>
<% 
   if(obj1.get_admin((String)session.getAttribute("group_name")).equals(session.getAttribute("sender"))&&!obj1.get_admin((String)session.getAttribute("group_name")).equals(users)){%>

<td><input type="submit" name="submit" class="w3-bar-item w3-button" value="remove"></td>
<%} %>
</form><br>
</tr>

<%} %>
</table>
    </section>
    <section id="c" style="display:none">
     
     <div class="main-agile">
		<div class="content">
			<div class="top-grids">
					<h1>Do you Really want to delete the group?</h1>
                       <p style="text-align:center">once deleted can't be undone</p>
			           <form action="group_chat" method="post" >
			           <input type="text" name="sender" value="<%=session.getAttribute("sender")%>" style="display:none">
			           <input type="text" name="group_name" value="<%=session.getAttribute("group_name")%>" style="display:none">
			           <input type="submit" name="submit" value="delete">
			           </form>
			</div>
		</div>
		
	</div>	
    </section>
    <section id="d" style="display:none">
    <div class="main-agile">
		<div class="content">
			<div class="top-grids">
					
			                <div class="signin-form subscribe">	
						<h3>Add Friend</h3>
						
						<form action="group_chat" method="post" >
                             <input type="text" name="user_name"  placeholder="Enter Friend Name" required>
                             <input type="text" name="group_name" value="<%=session.getAttribute("group_name")%>" style="display:none">
                             <input type="submit" name="submit" value="Add To Group">
						</form>
						
					</div>
			</div>
		</div>
		
	</div>	
    </section>
        <section id="e" style="display:none">
         <div class="main-agile">
		<div class="content">
			<div class="top-grids">
					<h1>Do you Really want to leave the group?</h1>
                       <p style="text-align:center">U can't see the conversation also..</p>
			           <form action="group_chat" method="post" >
			           <input type="text" name="user_name" value="<%=session.getAttribute("sender")%>" style="display:none">
			           <input type="text" name="group_name" value="<%=session.getAttribute("group_name")%>" style="display:none">
			           <input type="submit" name="submit" value="leave">
			           </form>
			</div>
		</div>
        </section>
    
</body>
</html>
<script>
function fun1()
{
    document.getElementById('a').style.display="block";
    document.getElementById('b').style.display="none";
    document.getElementById('c').style.display="none";
    document.getElementById('d').style.display="none";
    document.getElementById('e').style.display="none";


}
 function fun2()
{
    
	     document.getElementById('a').style.display="none";
	     document.getElementById('b').style.display="block";
	     document.getElementById('c').style.display="none";
	     document.getElementById('d').style.display="none";
	     document.getElementById('e').style.display="none";

}
 function fun3()
{
	     document.getElementById('a').style.display="none";
	     document.getElementById('b').style.display="none";
	     document.getElementById('c').style.display="block";
	     document.getElementById('d').style.display="none";
	     document.getElementById('e').style.display="none";


}
 function fun4()
 {
	     document.getElementById('a').style.display="none";
	     document.getElementById('b').style.display="none";
	     document.getElementById('c').style.display="none";
	     document.getElementById('d').style.display="block";
	     document.getElementById('e').style.display="none";


 }
 function fun5()
 {
	     document.getElementById('a').style.display="none";
	     document.getElementById('b').style.display="none";
	     document.getElementById('c').style.display="none";
	     document.getElementById('d').style.display="none";
	     document.getElementById('e').style.display="block";


 }
</script>
<script>
window.location.href("#g");
</script>
