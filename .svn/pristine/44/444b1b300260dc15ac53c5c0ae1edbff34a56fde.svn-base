<html xmlns="http://www.w3.org/1999/xhtml"
   xmlns:th="http://www.thymeleaf.org">
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   <head >
      <title>Action Page</title>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" type="text/css" href="/tmsApp/css/login.css" />
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   </head>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
   <script src="/tmsApp/js/lib/angular.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.7/angular-route.min.js"></script>
   <script src="/tmsApp/js/lib/angular-ui-router.min.js"></script>
   <script src="/tmsApp/js/lib/localforage.min.js"></script>
   <script src="/tmsApp/js/lib/ngStorage.min.js"></script>
   <script src="/tmsApp/js/app/app.js"></script>
   <script src="/tmsApp/js/app/actionController.js"></script>
   <style>
      .btn {
      margin: 0px 0px;
      }
      label{
      text-align:right;
      }
   </style>
   </script>			
   <script>
   $(document).ready(function(){
	   /* if("${isRecordFound}"=="false"){ 
		   $('.reqDetail').remove();
		   $('.action .panel-heading').html("Alert");
		   $('.action .panel-body').html("Action Performed Already !!");
	   }*/
	    /* $('input[name="Action"]').off().on('change',(function(){
	    	var val=$(this).val();
	    	if(val=="Rejected"){
	    		$('textarea').attr('required',true);
	    	}
	    	else{
	    		$('textarea').attr('required',false);
	    	}
	    })); */
 
	   
		   if("${isRecordFound}"=="false"){ 
			   $('.reqDetail').remove();
			   $('.action .panel-heading').html("Alert");
			   $('.action .panel-body').html("Request has cancelled by user");
			   } 
 
		   if("${isActionPerformed}"=="true"){ 
			   $('.reqDetail').remove();
			   $('.action .panel-heading').html("Alert");
			   $('.action .panel-body').html("Action has already been taken");
			   }
 
	  /*  else{
	   $('.reqDetail').remove();
	   $('.action .panel-heading').html("Alert");
	   $('.action .panel-body').html("Request has cancelled by user");
	   }   */
   });
   </script>
   <body ng-app="tmsApp" ng-controller="actionController">
      <div class="panel action col-lg-offset-2 col-lg-8 col-sm-offset-1 col-sm-10 col-md-10 col-md-offset-1 col-xs-10 col-xs-offset-1" style="padding:0px 0px;margin-top:10px"   >
         <div class="panel-heading " style="background-color:#1a3a4a;color:white">Aprroval Details</div>
         <div class="panel-body" style="background-color:#d4d8da;">
         <form ng-submit="flmAction(${NewAddress.empAddressBeanId})">
            <div class="form-group row col-lg-12 col-sm-12 col-md-12">
               <label  class="col-lg-3 col-sm-5 col-xs-4 col-form-label text-right">Action : </label>
               <div class="col-lg-9 col-sm-6 col-xs-8 ">
                  <input name="Action" id="Approve" type="radio" value="Approved" required  ng-model="flmActions.action" > <label for="Approve"> Approve </label>&nbsp;&nbsp;
                  <input name="Action" id="Reject" type="radio" value="Rejected" required ng-model="flmActions.action"> <label for="Reject"> Reject </label>
               </div>
            </div>
            <div class="form-group row col-lg-12 col-sm-12 col-md-12">
               <label  class="col-lg-3 col-sm-5 col-xs-4 col-form-label text-right">Comments : </label>
               <div class="col-lg-9 col-sm-7 col-xs-8 ">
               
                  <textarea class="form-control" rows="3" cols="25" ng-model="flmActions.comment"></textarea>	
                  <!-- <div class="text-center">
                   <input type="submit" class="btn btn-sm " value="Submit" />
                  </div>	 -->
               </div>
            </div>
             <div class="btns text-center">
                   <input type="submit" class="btn btn-sm " value="Submit" />
                  </div>
            </form>
         </div>
      </div>
      <div class="panel reqDetail col-lg-offset-2 col-lg-8 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-xs-offset-1 col-xs-10" style="padding:0px 0px;"  >
         <div class="panel-heading " style="background-color:#1a3a4a;color:white">Request Details</div>
         <div class="panel-body text-left" id="employee_detail" style="background-color:#d4d8da;">
         <div class="col-lg-offset-1">
            <div class="form-group row col-lg-6 col-sm-12 col-md-6">
               <label  class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Employee Name :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8 ">
                  <label> ${EmployeeDetails.name}</label>
               </div>
            </div>
            
            <div class="form-group row col-lg-6 col-sm-12 col-md-6">
               <label  class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Employee ID :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8 ">
                  <label>${EmployeeDetails.empcode}</label>
               </div>
            </div>
        
            <div class="form-group row col-lg-6 col-sm-12 col-md-6">
               <label  class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Email :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8 " style="word-break: break-all;">
                  <label >${EmployeeDetails.email}</label>
               </div>
            </div>
           <%--   <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-5  col-sm-5 col-xs-5 col-form-label ">Project </label>
               <div class="col-lg-7 col-sm-7 col-xs-7">
                  <label>: ${EmployeeDetails.project.projectName}</label>
               </div>
            </div> --%>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-5  col-sm-4 col-xs-4 col-form-label ">Address Alias :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8">
                  <label>${NewAddress.alias}</label>
               </div>
            </div>
            <%--  <div class="form-group row col-lg-6 col-sm-12 col-md-6">
               <label  class="col-lg-5 col-sm-5 col-xs-5 col-form-label ">Mobile Number </label>
               <div class="col-lg-7 col-sm-7 col-xs-7 ">
                  <label>: ${EmployeeDetails.phoneNumber}</label>
               </div>
            </div> --%>
<!--             <div class="form-group row col-lg-6 col-sm-12 col-md-6"> -->
<!--                <label  class="col-lg-5 col-sm-5 col-xs-5 col-form-label ">Current Address </label> -->
<!--                <div class="col-lg-7 col-sm-7 col-xs-7 "> -->
<%--                   <label>: ${OldAddress.address},${OldAddress.location},${OldAddress.landmark},${OldAddress.city},${OldAddress.pincode}</label> --%>
<!--                </div> -->
<!--             </div> -->
            <div class="form-group row col-lg-6 col-sm-12 col-md-6">
               <label  class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Updated Address  :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8 ">
                  <label class="text-left">${NewAddress.address}, ${NewAddress.location}, ${NewAddress.landmark}, ${NewAddress.city}, ${NewAddress.pincode}</label>
               </div>
            </div>
            </div>
         </div>
      </div>
   </body>
</html>