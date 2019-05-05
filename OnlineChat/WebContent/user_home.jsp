<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="main_css/style.css" rel="stylesheet" type="text/css" media="all" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="main_css/font-awesome.css" rel="stylesheet"> 
<!-- //font-awesome icons -->
<!-- web font -->
<link href="//fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i" rel="stylesheet">
<!-- //web font -->
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

</head>

<body style="background-image:url(images/1.jpg)">
	<header class="header">
<h1>Welcome <%=session.getAttribute("user_name") %></h1>
      <ul class="main-nav">
                <li><a href="javascript:fun5()">chat</a></li>
      
          <li><a href="javascript:fun1()">Search</a></li>
          <li><a href="javascript:fun2()">Create Group</a></li>
          <li><a href="javascript:fun3()">Join Group</a></li>
          <li><a href="javascript:fun4()">Profile</a></li>
          
          <li><a href="home?submit=logout">Logout</a></li>
      </ul>
	</header> 


<%@page language="java" import="java.util.*" %>
<%@page import="java.util.ArrayList"%> 
<%@page import="allu.user"%>
<%@page import="allu.message"%>
<%@page import="java.util.Iterator"%> 

      <div class="w3-sidebar w3-bar-block w3-card" style="background-image:url(images/1.jpg);width:15%;text-align:center">
     <div style="color:white"> <h3 style="color:white">Active Users</h3></div>
  <%
      user obj= new user();
      ArrayList<String> li = (ArrayList)obj.dataList();
      Iterator<String> iterator=li.iterator(); 
      while(iterator.hasNext())  // iterate through all the data until the last record
         {
            String users = iterator.next(); //assign individual employee record to the employee class object
%>
<% if (!users.equals(session.getAttribute("user_name")))
{%>

<form action="home" method="post">
<input type="text" name="reciever" value=<%=users%> style="display:none">
<input type="text" name="sender" value=<%=session.getAttribute("user_name")%> style="display:none">

<input type="submit" name="submit" class="w3-bar-item w3-button" value=<%=users%>>
</form><br>

<%}} %>


     <div style="color:white"> <h3  style="color:white">Groups</h3></div>
  <%
      ArrayList<String> li1 = (ArrayList)obj.GroupList((String)session.getAttribute("user_name"));
      Iterator<String> iterator1=li1.iterator(); 
      while(iterator1.hasNext())  // iterate through all the data until the last record
         {
            String groups = iterator1.next(); //assign individual employee record to the employee class object
%>

<form action="home" method="get">
<input type="text" name="group_name" value=<%=groups%> style="display:none">
<input type="text" name="sender" value=<%=session.getAttribute("user_name")%> style="display:none">
<input type="submit"  name="submit" class="w3-bar-item w3-button" value=<%=groups%>>
</form><br>

<% }%>
</div>

<div style="margin-left:15%">
<section id="e">
 <%
   message obj3=new message();

  ArrayList <message> li3 = (ArrayList) obj3.get_messages((String)session.getAttribute("user_name"));  
  Iterator<message> iterator3 = li3.iterator();  // Iterator interface

  while(iterator3.hasNext()) 
      {
          message msg3 = iterator3.next(); 
 
  if(msg3.get_chat_type().equals("i"))
    {%>
     <li class="self">
     <div class="msg" >
         <form action="home" method="post">
         <input type="text" name="reciever" value=<%=msg3.get_reciever()%> style="display:none">
         <input type="text" name="sender" value=<%=session.getAttribute("user_name")%> style="display:none">
<b><%=msg3.get_reciever()%></b>  <time ><%=msg3.get_time()%></time><br>
<input type="submit" name="submit" style="font-size:10px" class="w3-bar-item w3-button" value="<%=msg3.get_sender()%> : <%=msg3.get_content() %>">

        
     
</form><br>
         
       
        
        
      </div>
    </li>
    <%}else{ %>
      
    <li class="self">
      <div class="msg">
      
      <form action="home" method="get">
<input type="text" name="group_name" value=<%=msg3.get_reciever()%> style="display:none">

<input type="text" name="sender" value=<%=session.getAttribute("user_name")%> style="display:none">
              <b><%=msg3.get_reciever()%></b>        <time><%=msg3.get_time()%></time><br>

<input type="submit" style="font-size:10px"  name="submit" class="w3-bar-item w3-button" value= "<%=msg3.get_sender()%> : <%=msg3.get_content()%>">
</form>
        
     <p> </p>
        
      </div>
    </li>
    <%
    }}
    %>
    </ol>
</section>
<section id="a" style="display:none">
   <div class="main-agile">
		<div class="content">
			<div class="top-grids">
					
			                <div class="signin-form subscribe">	
						<h3>Search</h3>


							<form action="home" method="post">
                            <input type="text" name="reciever" placeholder="enter friend's name" >
                            <input type="text" name="sender" value=<%=session.getAttribute("user_name")%> style="display:none">
                            <input type="submit" name="submit" class="w3-bar-item w3-button" value="Search">
                        </form><br>
							
						</form>
					</div>
			</div>
		</div>
		
	</div>	
        
</section>

<section id="b" style="display:none">
<div class="content">
			<div class="top-grids">
					
			                <div class="signin-form subscribe">	
						<h3>Create Group With Friends</h3>
						<form action="home" method="post">
                                                        <input type="text" name="group_name"  placeholder="Enter Group Name" required>
                                                    Admin    <input type="text" name="user_name" value=<%=session.getAttribute("user_name") %>>
							<input type="submit" name="submit" value="create">
						</form>
					</div>
			</div>
		</div>
</section>

<section id="c" style="display:none">
<div class="content">
			<div class="top-grids">
					
			                <div class="signin-form subscribe">	
						<h3>Join Group</h3>
						<form action="home" method="post">
                            <input type="text" name="group_name"  placeholder="Enter Group Name" required>
                            <input type="text" name="user_name" style="display:none" value=<%=session.getAttribute("user_name") %>>
                            
							<input type="submit" class="w3-bar-item w3-button" name="submit" value="join">
						</form>
					</div>
			</div>
		</div>
</section>

<section id="d" style="display:none">
<div class="content">
			<div class="top-grids">
					
			                <div class="signin-form subscribe">	
						<h3>My Profile</h3>
						<%
						String user_name=(String)session.getAttribute("user_name");
						ArrayList<String> pro=obj.my_profile(user_name); %>
						<table>
						<tr><td> User Name</td> <td> <%=pro.get(0) %></td></tr>
						<tr><td> Full Name</td> <td>  <%=pro.get(1) %></td></tr>
						<tr><td> Email Id</td> <td>  <%=pro.get(2) %></td></tr>
						<tr><td> Phone Number</td> <td>  <%=pro.get(3) %></td></tr>
						<tr><td> Age</td> <td>  <%=pro.get(4) %></td></tr>
						<tr><td> Status</td> <td> <%=pro.get(5) %> </td></tr>
						
						</table>
					</div>
			</div>
		</div>
</section>

<section id="e" style="display:none">
</section>
</div>         
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