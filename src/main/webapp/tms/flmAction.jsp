<html xmlns="http://www.w3.org/1999/xhtml"
   xmlns:th="http://www.thymeleaf.org">
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   <head >
      <title>Action Page</title>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" type="text/css" href="/css/login.css" />
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
   <body ng-app="tmsApp" ng-controller="actionController">
      <div class="panel col-lg-offset-2 col-lg-8 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10  col-xs-12" style="padding:0px 0px;margin-top:10px"   >
         <div class="panel-heading " style="background-color:#1a3a4a;color:white">Aprroval Details</div>
         <div class="panel-body" style="background-color:#d4d8da;">
            <div class="form-group row col-lg-12 col-sm-12 col-md-12">
               <label  class="col-lg-3 col-sm-5 col-xs-4 col-form-label text-right">Action : </label>
               <div class="col-lg-9 col-sm-6 col-xs-8 ">
                  <input name="Action" id="Approve" type="radio" ng-value="true" ng-model="flmActions.status" > <label for="Approve"> Approve </label>&nbsp;&nbsp;
                  <input name="Action" id="Reject" type="radio" ng-value=false ng-model="flmActions.status"> <label for="Reject"> Reject </label>
               </div>
            </div>
           
            <div class="btns col-lg-offset-3 col-lg-9 col-xs-offset-4 col-xs-8 col-sm-offset-5 col-sm-7 col-md-12">
               <input type="submit" class="btn btn-sm " value="Submit" ng-click="flmAction(${employeeAddressObject.empAddressBeanId})"/>
            </div>
         </div>
      </div>
      <div class="panel col-lg-offset-2 col-lg-8 col-sm-offset-1 col-sm-10 col-md-offset-2 col-md-10 col-xs-offset-1 col-xs-10" style="padding:0px 0px;"  >
         <div class="panel-heading " style="background-color:#1a3a4a;color:white">Request Details</div>
         <div class="panel-body text-left" id="employee_detail" style="background-color:#d4d8da;">
         <div class="col-lg-offset-1">
           <c:if test="${employeeAddressObject.location ne null}">
            <div class="form-group row col-lg-6 col-sm-12 col-md-6">
               <label  class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Location :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8 ">
                  <label>${employeeAddressObject.location}</label>
               </div>
            </div>
            </c:if>
              <c:if test="${employeeAddressObject.pincode ne null}">
               <div class="form-group row col-lg-6 col-sm-12 col-md-6">
               <label  class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Pincode :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8 ">
                  <label>${employeeAddressObject.pincode}</label>
               </div>
            </div>
            </c:if>
              <c:if test="${employeeAddressObject.landmark ne null}">
            <div class="form-group row col-lg-6 col-sm-12 col-md-6">
               <label  class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Landmark :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8 ">
                  <label>${employeeAddressObject.landmark}</label>
               </div>
            </div>
            </c:if>
             <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-5  col-sm-4 col-xs-4 col-form-label ">City :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8">
                  <label>${employeeAddressObject.city}</label>
               </div>
            </div>
              <div class="form-group row col-sm-12 col-lg-6 col-md-6 ">
               <label  class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Alias :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8">
                  <label>${employeeAddressObject.alias}</label>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Address :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8">
                  <label>${employeeAddressObject.address}</label>
               </div>
            </div>
             <div class="form-group row col-lg-6 col-sm-12 col-md-6">
               <label  class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">EmployeeId :</label>
               <div class="col-lg-7 col-sm-8 col-xs-8">
                  <label>${employeeAddressObject.employeeId}</label>
               </div>
            </div>
          </div>
  
         </div>
      </div>
   </body>
</html>