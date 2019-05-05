<%--Iterator.jsp --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
#button {
  border-radius: 4px;
  background-color: #f4511e;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 10px;
  padding: 20px;
  width: 100px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
}

#button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

#button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

#button:hover span {
  padding-right: 25px;
}

#button:hover span:after {
  opacity: 1;
  right: 0;
}
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
}</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="style.css">

<title>Insert title here</title>
</head>
<body>
<%@page import="java.util.ArrayList"%>      <%--Importing all the dependent classes--%>
<%@page import="allu.individual_chat"%>
<%@page import="allu.user"%>
<%@page import="allu.message"%>
<%@page import ="java.sql.*"%>
<%@page import="java.util.Iterator"%> 
 <%@page import ="java.io.*" %>
  <%@page import ="javax.servlet.*" %>
 
<% individual_chat obj=new individual_chat();
int i=(Integer)session.getAttribute("chat_id");  


ArrayList <message> li = (ArrayList) obj.get_messages(i); %> <%--Assigning ArrayList object containing Employee data to the local object --%>
 

 <div class="menu">
            <div class="name"><%=session.getAttribute("reciever") %>    
                              
            </div>
            
            <div style="right:0%">                    <a style="color:white" href="user_home.jsp">Home</a>
            
            </div>
            <div style="margin-right:90%;">
            <form action="individual_chat" method="post">
            <input type="text" name="sender" value="<%=session.getAttribute("sender")%>" style="display:none">
             <input type="text" name="reciever" value="<%=session.getAttribute("reciever")%>" style="display:none">
              <input id="button" type="submit" name="submit" value="block">
            </form>
            </div>
        </div>
    <ol class="chat">
  <%  Iterator<message> iterator = li.iterator();  // Iterator interface

  while(iterator.hasNext()) 
      {
          message msg = iterator.next(); 
 
  if(msg.get_sender().equals(session.getAttribute("sender") ))
    {%>
     <li class="self">
     <div class="msg" >
     <div style="margin-left:50%">
      <form action="individual_chat" method="post">
     <input type="text" style="display:none" name="sender" value="<%=msg.get_sender()%>">
     <input type="text" style="display:none" name="message"  value="<%=msg.get_time()%>" style="display:none">
     <button type="submit" value="delete_msg" name="submit" class="btn"><i class="fa fa-trash"></i></button>
     </form></div>
        <b><%=msg.get_content()%></b>
        <time><%=msg.get_time()%></time> 
      </div>
    </li>
    
    <%}else{ %>
    
    <li class="other">
      <div class="msg">
        <b><%=msg.get_content()%></b>
        
        <time><%=msg.get_time()%></time>
      </div>
    </li>
    <%
    }}
    %>
   
    </ol>
    
    <div id="g"></div>
    <%      individual_chat obj4=new individual_chat();
    
    
      if(obj4.is_blocked((String)session.getAttribute("reciever"),(String)session.getAttribute("sender"))==0)
      {
    %>
    <form action="home" method="post">
    <input class="textarea" type="text" id="m" name="message" placeholder="Type here!"/><div class="emojis"><input type="submit" style=" position: fixed;
    display: block;
    bottom: 0px;
    width: 134px;
    height: 50px;
        border: 2px solid #FFFFFF;
    background-repeat: no-repeat;
    background-size: cover;
	background: rgba(0, 0, 0, 0.78);
    z-index: 100;
    cursor: pointer;" name="submit" value="send" ></div>
    <input type="text" name="sender" value="<%=session.getAttribute("sender") %>" style="display:none">
    <input type="text" name="reciever" value="<%=session.getAttribute("reciever") %>" style="display:none" >
    <input type="text" name="chat_id" value="<%=i%>" style="display:none">
    
    </form>
    <%} else{%>
     <h1>U can't send message to this person</h1>
     <%} %>
</body>
</html>
<script>
window.location.href("#g");
</script>
