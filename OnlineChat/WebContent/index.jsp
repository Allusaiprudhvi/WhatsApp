<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="main_css/style.css" rel="stylesheet" type="text/css" media="all" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="main_css/font-awesome.css" rel="stylesheet"> 
<!-- //font-awesome icons -->
<!-- web font -->
<link href="//fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i" rel="stylesheet">
<!-- //web font -->

<title>Login</title>
</head>
<body>


		
	<div class="main-agile">
            <div style="width:15%;margin: 0 auto;margin-top: 1%;">  </div> 

		<div class="content1">
			<div class="top-grids">
						
					<div class="signin-form-grid">
						<div class="signin-form">
							<h2>Messenger Log-In</h2>
                     
                                                          
							<form id="signin" action="home" method="post">
                               <%if(session.getAttribute("error")!=null){ %>   <p style="color:red"><%=session.getAttribute("error") %><%session.setAttribute("error",null);} %></p>
                               <%if(session.getAttribute("msg")!=null){ %>   <p style="color:red"><%=session.getAttribute("msg") %><%session.setAttribute("msg",null);} %></p>
                            
								<input type="text" name="user_name" placeholder="User Name" required="">
								<input type="password" name="password" placeholder="Password" required="">	 
								<input type="checkbox" id="brand" value="">
								<label for="brand"><span></span> Remember me ?</label> 
								<input type="submit" name="submit" value="Login">
								<div class="signin-agileits-bottom"> 
									<p><a href="registration.jsp">Create an Account?</a></p>    
								</div> 
							</form>
						</div>
					</div>
				
			</div>
		</div>
		
	</div>	
</body>

</html>

