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

<title>Create Account</title>
</head>
<body>


		
	<div class="main-agile">
            <div style="width:15%;margin: 0 auto;margin-top: 1%;">  </div> 

		<div class="content1">
			<div class="top-grids">
						
					<div class="signin-form signup-form">
						<h3>Sign Up Form</h3>
						<form id="signin" action="home" method="post" onsubmit="return password_validate()">
							<input type="text" name="user_name" placeholder="Nick Name" required="">
					    	<input type="text" name="full_name" placeholder="Full Name" required="">
							<input type="email" name="email" placeholder="Your Email" required="">
                            <input  type="password" placeholder="password" id="pwd1"  name="password1" data-type="password" required="">
						    <input  type="password"  id="pwd2" placeholder="retype  password"   name="password2" data-type="password" required="">
						    <input type="text" name="phone_number" placeholder="Enter Phone Number" required="">
						    						    <input type="text" name="age" placeholder="Enter Age" required="">
						    
							<input type="checkbox" id="brand1" value="">
							<label for="brand1"><span></span>I accept the terms of use</label>
							<input type="submit" name="submit" value="Signup">
						</form>
					</div>
				
			</div>
		</div>
		
	</div>	
</body>

<script>
    function password_validate()
    {
        if(document.getElementById('pwd1').value!=document.getElementById('pwd2').value)
        {
               alert("Passwords does not match");
            return false;
        }
        
        return true;
    }
    
     
    </script>
</html>
