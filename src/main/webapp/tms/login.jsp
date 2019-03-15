<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
   xmlns:th="http://www.thymeleaf.org">
   <head>
      <title>Login Page</title>
      <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/images/car_favicon.png"/>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
     <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" /> -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">  
      
      
      <script src="${pageContext.request.contextPath}/js/lib/Jquery.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/bootstrap.js"></script>
     
   
   <style>
   .btn{
   	margin:0px !important;
   }
   .login{
	/* background-image: url(/tmsApp/images/login_4.png); */
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center;
    background-attachment: fixed;
}
.login .footer a,.login .footer{
	color:white !important;
} 
.login_well{
/*  box-shadow: 0px 0px 20px 5px black !important;
 background-color:#ffffff !important; */
 background-color: #ffffffba !important;
} 
.foot{
padding-bottom:0px !important;
}

.log_cont .btn{
    color: #f1ecec !important;
    background-color: #377a99  !important;
}
.log_cont .btn:hover{
    color: #f1e8e8 !important;
    background-color: #03357c !important;
}

.quinnox_logo {
    padding: 0px 0px 0px 0px !important;
    left:0px;
    }
    .logo{
    text-align: center;
    margin: 7px 0px;
    }
   </style>
   </head>
  <body class="login">
  <!-- <div class="head" style="background-color:#fff;height:60px">
  <img  src="images/Q.png" class="quinnox_logo img-responsive"/> 
               </div> -->
      <div class="log_cont container col-sm-10 col-sm-offset-1  col-md-4 col-md-offset-1 ">
         <div class="well login_well col-sm-12 col-md-12 col-lg-12">
            <div class="logo inline-block">
          <!--  <h3>QCSS</h3>  -->
            <!--  <img width="190px" height="125px" src="images/Q.png" class="quinnox_logo img-responsive"/> -->
             <!-- <img src="images/QCSS logo-01.png" style="width:116px;height:40px" class="cab_logo img-responsive"/> -->
             <img src="images/QCSS logo-01.png" style="width:200px;height:70px" class="cab_logo"/>
            </div>
            <form action="login" method="POST" name="loginform">
               <div class="inputs">
                  <div class="form-group has-feedback">
                     <input type="text" name="email" class="form-control" placeholder="Username"  autofocus required />
                     <i class="form-control-feedback glyphicon glyphicon-user"></i>
                  </div>
                  <div class="form-group has-feedback">
                     <input type="password" name="password" class="form-control" placeholder="Password" autocomplete="off" ng-minlength="5"
                        ng-maxlength="10" required />
                     <i class="form-control-feedback glyphicon glyphicon-lock"></i>
                  </div>
               </div>
               <div class="foot">
                  <button type="submit" class="btn btn-md submit btn-block" value="Login" >Login</button>
                  <!-- <a id="register" href="registration" >New User? Register here!</a> -->
               </div>
               <div class="text-right">
               <label id="err" style="color:red;" ></label>
               </div>
            </form>
         </div>
      </div>
      <%-- <div class=" col-md-12 col-xs-12  col-sm-12 col-lg-8 col-lg-offset-3">
         <jsp:include page="footer.jsp" />
      </div> --%>
   </body>
<script>
$(document).ready(function(){
	var h = new Date().getHours();
	var img;
	 //h=23;
	  if(h>=5&&h<=9){
		  img="/tmsApp/images/login_1.png";
	  }
	  else if(h>=10&&h<=16){
		  img="/tmsApp/images/login_2.png";
	  }
	  else if(h>=17&&h<=19){
		  img="/tmsApp/images/login_3.png";
	  }
	  else{
		  img="/tmsApp/images/login_4.png";
	  }
	  $('.login').css('background-image','url(' + img + ')');

	if("${param.error}"=="true"){
		$('#err').html("<b>* Invalid UserName or Password</b>");
		$("#err").show();
		 $("#err").delay(4000).fadeOut();
	}
	console.log("${sessionScope.READ-ONLY}");
});
</script>
</html>