<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<title>Action Page</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/tmsApp/css/login.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="/tmsApp/js/lib/angular.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.7/angular-route.min.js"></script>
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
      text-align:left;
      }
</style>
<script>
   $(document).ready(function(){
	   if("${noActiveRequest}"=="true"){
		   $('.reqDetail').remove();
		   $('.action .panel-heading').html("Alert");
		   $('.action .panel-body').html("This request is no more Active for any action to be taken.");
	   }
	   else{
	   if("${isRecordFound}"=="true"){ 
	   if("${isActionPerformed}"=="true"){ 
	  // $('.reqDetail').remove();
	   $('.action .panel-heading').html("Alert");
	   $('.action .panel-body').html("Action has already been taken.");
	   }
	   }
	   else{
	   $('.reqDetail').remove();
	   $('.action .panel-heading').html("Alert");
	   $('.action .panel-body').html("Request was cancelled by user.");
	   }
	   }
	   if("${isOldRequest}"=="true"){
		   $('.reqDetail').remove();
		   $('.action .panel-heading').html("Alert");
		   $('.action .panel-body').html("This request has been expired.");
	   }
	   });

   </script>
<body ng-app="tmsApp" ng-controller="actionController">
	<div
		class="panel action col-lg-offset-2 col-lg-8 col-sm-offset-1 col-sm-10 col-md-9 col-md-offset-3  col-xs-10 col-xs-offset-1"
		style="padding: 0px 0px; margin-top: 10px">
		<div class="panel-heading "
			style="background-color: #1a3a4a; color: white">Approval
			Details</div>
		<div class="panel-body" style="background-color: #d4d8da;">
			<form ng-submit="action(${CabRequestDetails.id})">
				<div class="form-group row col-lg-12 col-sm-12 col-md-12">
					<label class="col-lg-3 col-sm-5 col-xs-4 col-form-label text-right">Action :
					</label>
					<div class="col-lg-9 col-sm-6 col-xs-8 ">
						<input name="Action" id="Approve" type="radio" value="Approved"
							required ng-model="actions.action"> <label for="Approve">
							Approve </label>&nbsp;&nbsp; <input name="Action" id="Reject"
							type="radio" value="Rejected" required ng-model="actions.action">
						<label for="Reject"> Reject </label>
					</div>
				</div>
				<div class="form-group row col-lg-12 col-sm-12 col-md-12">
					<label class="col-lg-3 col-sm-5 col-xs-4 col-form-label text-right">Comments :
					</label>
					<div class="col-lg-9 col-sm-7 col-xs-8 ">

						<textarea class="form-control" rows="3" cols="25"
							ng-model="actions.comment" required></textarea>
					</div>
				</div>
				<div
					class="btns text-center">
					<input type="submit" class="btn btn-sm " value="Submit" />
				</div>
			</form>
		</div>
	</div>
	<div
		class="panel reqDetail col-lg-offset-2 col-lg-8 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-xs-offset-1 col-xs-10"
		style="padding: 0px 0px;">
		<div class="panel-heading "
			style="background-color: #1a3a4a; color: white">Request
			Details</div>
		<div class="panel-body text-left" id="employee_detail"
			style="background-color: #d4d8da;">
			<div class="col-lg-offset-1">
			<c:if test="${CabRequestDetails.empName ne null}">
				<div class="form-group row col-lg-6 col-sm-12 col-md-6">
					<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Employee 
						Name </label>
					<div class="col-lg-7 col-sm-8 col-xs-8 ">
						<label>: ${CabRequestDetails.empName}</label>
					</div>
				</div>
			</c:if>
			<c:if test="${CabRequestDetails.guestUserName ne null}">
				<div class="form-group row col-lg-6 col-sm-12 col-md-6">
					<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Guest 
						Name</label>
					<div class="col-lg-7 col-sm-8 col-xs-8 ">
						<label>: ${CabRequestDetails.guestUserName}</label>
					</div>
				</div>
			</c:if>
			<c:if test="${CabRequestDetails.empMailId ne null}">
				<div class="form-group row col-lg-6 col-sm-12 col-md-6">
					<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Email 
					</label>
					<div class="col-lg-7 col-sm-8 col-xs-8 " style="word-break: break-all;">
						<label>: ${CabRequestDetails.empMailId}</label>
					</div>
				</div>
			</c:if>
			<div class="form-group row col-sm-12 col-lg-6 col-md-6">
				<label class="col-lg-5  col-sm-4 col-xs-4 col-form-label ">Project 
				</label>
				<div class="col-lg-7 col-sm-8 col-xs-8">
					<label>: ${CabRequestDetails.projectName}</label>
				</div>
			</div>
			<div class="form-group row col-sm-12 col-lg-6 col-md-6 ">
				<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Reason 
				</label>
				<div class="col-lg-7 col-sm-8 col-xs-8">
					<label>: ${CabRequestDetails.reason}</label>
				</div>
			</div>
			<div class="form-group row col-sm-12 col-lg-6 col-md-6">
				<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Request 
					Nature </label>
				<div class="col-lg-7 col-sm-8 col-xs-8">
					<label>: ${CabRequestDetails.adhocMonthly}</label>
				</div>
			</div>
			<%-- <div class="form-group row col-lg-6 col-sm-12 col-md-6">
				<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Mobile 
					Number </label>
				<div class="col-lg-7 col-sm-8 col-xs-8 ">
					<label>: ${CabRequestDetails.mobileNumber}</label>
				</div>
			</div> --%>
			<div class="form-group row col-sm-12 col-lg-6  col-md-6">
				<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Created 
					Date </label>
				<div class="col-lg-7 col-sm-8 col-xs-8">
					<label>: ${CabRequestDetails.createdDate}</label>
				</div>
			</div>
			<c:if test="${CabRequestDetails.requestedBy ne null}">
				<div class="form-group row col-lg-6 col-sm-12 col-md-6">
					<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Requested 
						By </label>
					<div class="col-lg-7 col-sm-8 col-xs-8 ">
						<label>: ${CabRequestDetails.requestedBy}</label>
					</div>
				</div>
			</c:if>
			<div class="form-group row col-lg-6 col-sm-12 col-md-6">
				<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">From 
				</label>
				<div class="col-lg-7 col-sm-8 col-xs-8 ">
					<label>: ${CabRequestDetails.fromLocation}</label>
				</div>
			</div>

			<div class="form-group row col-lg-6 col-sm-12 col-md-6">
				<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">To </label>
				<div class="col-lg-7 col-sm-8 col-xs-8 ">
					<label>: ${CabRequestDetails.toLocation}</label>
				</div>
			</div>
			<div class="form-group row col-sm-12 col-lg-6 col-md-6">
				<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">From
					Date </label>
				<div class="col-lg-7 col-sm-8 col-xs-8">
					<label>: ${CabRequestDetails.fromDate}</label>
				</div>
			</div>
			<div class="form-group row col-sm-12 col-lg-6 col-md-6">
				<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">To
					Date </label>
				<div class="col-lg-7 col-sm-8 col-xs-8">
					<label>: ${CabRequestDetails.toDate}</label>
				</div>
			</div>
			<c:if
				test="${CabRequestDetails.pickTime ne null && CabRequestDetails.pickTime ne ''}">
				<div class="form-group pickup row col-lg-6 col-sm-12 col-md-6">
					<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Pickup 
						Time </label>
					<div class="col-lg-7 col-sm-8 col-xs-8 ">
						<label>: ${CabRequestDetails.pickTime}</label>
					</div>
				</div>
			</c:if>
			<c:if
				test="${CabRequestDetails.dropTime ne null && CabRequestDetails.dropTime ne '' }">
				<div class="form-group drop row col-lg-6 col-sm-12 col-md-6">
					<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">Drop
						Time </label>
					<div class="col-lg-7 col-sm-8 col-xs-8 ">
						<label>: ${CabRequestDetails.dropTime}</label>
					</div>
				</div>
			</c:if>
			<c:if
				test="${ExcludedDates ne null }">
			<div class="form-group row col-sm-12 col-lg-6 col-md-6">
				<label class="col-lg-5 col-sm-4 col-xs-4 col-form-label ">
					Canceled Dates </label>
				<div class="col-lg-7 col-sm-8 col-xs-8">:
				 <c:forEach items="${ExcludedDates}" var="dates" >
				 <label>: ${dates}</label>&nbsp;
                  </c:forEach>
				</div>
			</div>
			</c:if>
		</div>
		</div>
	</div>
</body>
</html>