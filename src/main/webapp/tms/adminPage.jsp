  
<jsp:include page="header.jsp" />
<script src="${pageContext.request.contextPath}/js/lib/datetimepicker.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/datetimepicker.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTable.checkboxes.css">
<script src="${pageContext.request.contextPath}/js/lib/dataTable.checkboxes.min.js"></script>
<script>
   $(document).ready(function() {
	   $('.toggleSidenav').removeClass('hidden');
	   $('.toggleSidenav').click(function(){
		  $('.nav-side-menu').toggleClass('hidden'); 
		  $('.adminpage').toggleClass('col-xs-12 col-sm-6 col-sm-offset-3 col-md-9 col-md-offset-3 col-lg-10 col-lg-offset-2').toggleClass('col-lg-12');
	   });
	//   $('.js-example-basic-multiple').select2();
   	document.getElementById("defaultOpen").click();
   	
       $(".class").click(function() {
         $('.content').addClass("collapse");
         //$(this).toogleClass("collapse");
           var contentId =this.dataset.href;
         var class1= $(contentId).hasClass("collapse");
           if(class1==true){
           $(contentId).removeClass("collapse");
           }
           else{
           $(contentId).addClass("collapse");
           }
       });
       $('#fromClear,#toClear').click(function(){
       	$(this).siblings().val('');
       	$(this).addClass('hidden');
       });
   });
   
   
   $(function() {
	   var a;
	    $('#from').datepicker({
	    	dateFormat: "yy-mm-dd",
	        autoclose: true,
	        onSelect: function (date) {
	        	$('#fromClear').removeClass('hidden');
	        	var dt2 = $('#to');
	            var startDate = $('#from').datepicker('getDate');
	            var minDate = $('#from').datepicker('getDate');
	            dt2.datepicker('setDate', minDate);
	            startDate.setDate(startDate.getDate() + 30);
	            //sets dt2 maxDate to the last day of 30 days window
	           // dt2.datepicker('option', 'maxDate', startDate);
	            dt2.datepicker('option', 'minDate', minDate);
	            //$(this).datepicker('option', 'minDate', minDate);
	            $('#to').val('');
	            $('#toClear').addClass('hidden');
	            a = $("#from").val();
	        }
	    });
	    $('#to').datepicker({
	        dateFormat: "yy-mm-dd",
	        minDate:a,
	        autoclose: true,
	        onSelect: function (date) {
	        	$('#toClear').removeClass('hidden');
	        }
	    });
	   	
      	
         $('#tripTimePicker').datetimepicker({
      	   format: 'HH:ii P',   
      	   minuteStep: 30,
           autoclose: true,
           ampm : true,	
           todayHighlight: true,
           showMeridian: true,
           startView: 1,
           maxView: 1,
           pickerPosition : "bottom-left"
      	});
      	$('.adminSide').addClass('active');
      	$('.adminSide .flmDashSide').addClass('active');
      });
      
</script>
<script src="/tmsApp/js/app/adminController.js"></script>
<script src="/tmsApp/js/app/adminService.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
body.modal-open {margin-right: 0px} 
   #tripDatePicker span{
     cursor:default !important;
   }
  .ui-datepicker-calendar td {
   padding: 3px !important;
   }
    table,
    tr,
    td,
    th {
   background-color: #eaecec;
   text-align: center;
   border:1px solid #bbc1ca;
   }
     td,
   th {
    text-align: center !important;
    font-size: 13px;
    padding: 4px !important;
   }
   th{
   font-weight:bold;
   background-color:#b4dbec;
   border-bottom: 1px solid #1a3a4a !important ;
   padding-right: 15px !important;
   }
   td{
  	background-color:#f1f1f1;
   }
   #flmDailyTable_filter{
    float:none;
   }
   
   
   .adminpage ::-webkit-scrollbar-track {
     -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
     background-color: white;
}
 .adminpage ::-webkit-scrollbar {
     width: 8px;
     height:8px;
     background-color: white;
}
 .adminpage ::-webkit-scrollbar-thumb {
     cursor: pointer;
     background-color: #31708e;
}
</style>

<div class="adminpage  col-xs-12 col-sm-6 col-sm-offset-3 col-md-9 col-md-offset-3 col-lg-10 
   col-lg-offset-2" ng-controller="adminController" >
   <br>
   <div class="tab" >
    	<button class="tablinks" ng-click="flmPanel($event, 'flmDailyPanel')" id="defaultOpen">Daily Request</button>
      <button class="tablinks" ng-click="flmPanel($event, 'flmUpcomingPanel')">Upcoming Request</button>
      <button class="tablinks" ng-click="flmPanel($event, 'flmHistoryPanel')">History</button>
      <button class="tablinks" ng-click="flmPanel($event, 'flmTripPanel')">Trip View</button>
   </div>
    <div id="flmDailyPanel" class="tabcontent col-lg-12">
     <table id="flmDailyTable" class="table table-responsive display"  width="100%">
      <thead>
            <tr>
           	    <th><input name="select_all" value="1" id="example-select-all" type="checkbox" /></th>
               <th>Doc No</th>
               <th>Emp Name</th>
               <th>Schedule Date</th>
               <th>Request Type</th>
               <th>Request Time</th>
               <th>Request Nature</th>
               <th>From Address</th>
               <th>To Address</th>
               <th>Requested Date</th>
               <th>Mobile Number</th>
               <th>Project</th>
               <th>Manager Status</th>
               <th>FLM Status</th>
               <th>Travel Status</th>
               <th>Trip Sheet</th>
            </tr>
         </thead>
         <tbody>
            <tr ng-repeat="x in flmDailyViewData" id={{x.id}}>
               <td><input  type="checkbox" /></td>
               <!-- <td id="{{$index}}" class="details-control">
               <div class="tooltip1 col-lg-12" >
      		<label><a>{{x.id}}</a></label>
               <span class="tooltiptext">Click here to see Request details</span>
             </div></td> -->
             <td id="{{$index}}" class="details-control" >
      		<label><a data-toggle="tooltip" title="Click here to see Request details">{{x.id}}</a></label>
             </td>
               <td>{{x.empName}}</td>
               <td><span style="display:none">{{x.scheduleDate}}</span><span>{{x.scheduleDate |date:'dd-MMM-yyyy'}}</span></td>
               <td style="text-transform:capitalize;">{{x.requestType}}</td>
               <td>{{x.reqTime}}</td>
               <td>{{x.adhocMonthly}}</td>
               <td>{{x.fromLocation}}</td>
               <td>{{x.toLocation}}</td>
               <td>{{x.requestDate |date:'dd-MMM-yyyy'}}</td>
               <td>{{x.mobileNumber}}</td>
               <td>{{x.projectName}}</td>
               <td>{{x.requestStatus}}</td>
               <td>{{x.action}}</td>
               <td>{{x.travelStatus}}</td>
               <td>
               <span data-ng-if="x.tripSheetId==null">
               -
               </span>
               <span data-ng-if="x.tripSheetId!='null'">
               <a ng-click="EditTripSheet(x.tripSheetId,x.id,x,x.scheduleDate,x.requestType)">{{x.tripSheetNumber}}</a>
               </span>
               </td>
            </tr>
         </tbody>
     </table>
    </div>
   
   
   <div id="flmUpcomingPanel" class="tabcontent col-lg-12" >
      <table id="flmUpcomingTable" class="table table-responsive display" cellspacing="0" width="100%">
         <thead>
            <tr>
           	   <th><input name="select_all" value="1" id="example-select-all" type="checkbox" /></th>
               <th>Doc No</th>
               <th>Emp Name</th>
               <th>Schedule Date</th>
               <th>Request Type</th>
               <th>Request Time</th>
               <th>Request Nature</th>
               <th>From Address</th>
               <th>To Address</th>
               <th>Requested Date</th>
               <th>Mobile Number</th>
               <th>Project</th>
               <th>Manager Status</th>
               <th>FLM Status</th>
               <th>Travel Status</th>
               <th>Trip Sheet</th>
            </tr>
         </thead>
         <tbody>
            <tr ng-repeat="x in flmUpcomingViewData " id={{x.id}}>
               <td><input  type="checkbox" /></td>
               <!-- <td id="{{$index}}" class="details-control">
               <div class="tooltip1 col-lg-12" >
      		<label><a>{{x.id}}</a></label>
               <span class="tooltiptext">Click here to see Request details</span>
             </div></td> -->
             <td id="{{$index}}" class="details-control" >
      		<label><a data-toggle="tooltip" title="Click here to see Request details">{{x.id}}</a></label>
             </td>
               
               <td>{{x.empName}}</td>
               <td>{{x.scheduleDate |date:'dd-MMM-yyyy'}}</td>
               <td style="text-transform:capitalize;">{{x.requestType}}</td>
               <td>{{x.reqTime}}</td>
                <td>{{x.adhocMonthly}}</td>
               <td>{{x.fromLocation}}</td>
               <td>{{x.toLocation}}</td>
               <td>{{x.requestDate |date:'dd-MMM-yyyy'}}</td>
               <td>{{x.mobileNumber}}</td>
               <td>{{x.projectName}}</td>
               <td>{{x.requestStatus}}</td>
               <td>{{x.action}}</td>
               <td>{{x.travelStatus}}</td>
               <td>
               <span data-ng-if="x.tripSheetId==null">
               -
               </span>
               <span data-ng-if="x.tripSheetId!='null'">
               <a ng-click="EditTripSheet(x.tripSheetId,x.id,x,x.scheduleDate)">{{x.tripSheetNumber}}</a>
               </span>
               </td>
            </tr>
         </tbody>
      </table>
   </div>
   <div id="flmHistoryPanel" class="tabcontent col-lg-12 col-sm-12 col-xs-12" ng-init="role='${sessionScope.isFrontDesk}'">
      <br>
      <div class="col-lg-offset-1 col-lg-10">
      <form id="histrySearchForm">
      <div class=" panel panel-default ">
      <div class="panel-heading faq-links" data-toggle="collapse" data-target="#search"><b>Search</b><i class="fa fa-minus  pull-right"  aria-hidden="true"></i>
      </div>
      <div class=" panel-body collapse in" id="search" >
         <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6" >
            <label class="col-lg-4  col-form-label">Account</label>
            <div class="col-lg-8">
               <select ng-model="history.accountId" 
                  class="form-control" id="accntDrp" >
                  <option value=""  selected>Please Select</option>
                  <c:forEach items="${accountList}" var="account" >
                     <option value="${account.id}">${account.accountName}</option>
                  </c:forEach>
               </select>
            </div>
         </div>
         <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6" >
            <label class="col-lg-4  col-form-label">Project</label>
            <div class="col-lg-8">
               <select name="projectId" ng-model="history.projectId"
                  class="form-control" id="projectId">
                  <option value=""  selected>Please Select</option>
                  <option ng-repeat="x in project" value={{x.projectId}}>{{x.projectName}}</option>
               </select>
            </div>
         </div>
         <div class="" >
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label for="example-email-input" class="col-lg-4 col-form-label">From</label>
               <div class="form-group  col-lg-8">
                     <input type='text' class="form-control" name="from" readonly id="from" ng-model="history.fromDate"/> 
                      <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden" id="fromClear"></span>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label for="example-email-input" class="col-lg-4 col-form-label">To</label>
               <div class="form-group  col-lg-8">
                     <input type='text' class="form-control" id="to" readonly name="to" ng-model="history.toDate" /> 
                      <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden" id="toClear"></span>
                  </div>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6" >
               <label class="col-lg-4  col-form-label">Request Nature</label>
               <div class="col-lg-8">
                  <select ng-model="history.requestNature" 
                     class="form-control" >
                     <option value=""  selected>Please Select</option>
                     <option name="" value="Adhoc">Adhoc</option>
                     <option name="" value="Monthly">Monthly</option>
                  </select>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6" >
               <label class="col-lg-4  col-form-label">Request Type</label>
               <div class="col-lg-8">
                  <select ng-model="history.requestType" 
                     class="form-control" >
                     <option value=""  selected>Please Select</option>
                     <option name="" value="Pickup">Pickup</option>
                     <option name="" value="Drop">Drop</option>
                  </select>
               </div>
            </div>
            <div class="text-center">
               <input type="submit" class="btn btn-md " value="Search" ng-click="Flm_History()" id="submit" />
               <input type="reset" class="btn btn-md" value="Reset" ng-click="resetHistorySearch()"   />
            </div>
             <div class="text-center flmHistAlert {{class}} col-lg-offset-4 col-lg-4" hidden ><b>{{flmHistStatus}}</b></div> 
            </div>
            </div>
         </div>
      </form>
      <!-- <br> -->
      <div class="flmHistoryTable" hidden>
      <hr>
         <table  id="flmHistoryTable" class="table table-responsive display" cellspacing="0" width="100%">
            <thead>
               <tr>
           	   <th><input name="select_all" value="1" id="example-select-all" type="checkbox" /></th>
               <th>Doc No</th>
               <th>Emp Name</th>
               <th>Schedule Date</th>
               <th>Request Type</th>
               <th>Request Time</th>
               <th>Request Nature</th>
               <th>From Address</th>
               <th>To Address</th>
               <th>Requested Date</th>
               <th>Mobile Number</th>
               <th>Project</th>
               <th>Manager Status</th>
               <th>FLM Status</th>
               <th>Travel Status</th>
               <th>Trip Sheet</th>
            </tr>
            </thead>
            <tbody>
               <tr ng-repeat="x in flmHistoryViewData"  id={{x.id}}>
               <td class="td{{x.id}}"><input  type="checkbox" /></td>
               <td id="{{$index}}" class="details-control" >
      		<label><a data-toggle="tooltip" title="Click here to see Request details">{{x.id}}</a></label>
             </td>
               <td>{{x.empName}}</td>
               <td>{{x.scheduleDate |date:'dd-MMM-yyyy'}}</td>
               <td style="text-transform:capitalize;">{{x.requestType}}</td>
               <td>{{x.reqTime}}</td>
               <td>{{x.adhocMonthly}}</td>
               <td>{{x.fromLocation}}</td>
               <td>{{x.toLocation}}</td>
               <td>{{x.requestDate |date:'dd-MMM-yyyy'}}</td>
               <td>{{x.mobileNumber}}</td>
               <td>{{x.projectName}}</td>
               <td>{{x.requestStatus}}</td>
               <td>{{x.action}}</td>
               <td>{{x.travelStatus}}</td>
               <td id="Trip{{x.id}}">
               <span data-ng-if="x.tripSheetId==null">
              -
               </span>
               <span data-ng-if="x.tripSheetId!='null'">
               <a ng-click="EditTripSheet(x.tripSheetId,x.id,x,x.scheduleDate)">{{x.tripSheetNumber}}</a>
               </span>
               </td>
               </tr>
            </tbody>
         </table>
      </div>
   </div>
    <div id="flmTripPanel" class="tabcontent col-lg-12">
      <table id="flmTripTable" class="table table-responsive display" cellspacing="0" width="100%">
         <thead>
            <tr>
            <th>Doc No</th>
            <th>Trip Sheet</th>
            <th>Trip Date</th>
            <th>Trip Time</th>
            <th>Place</th>
            <th>Driver Name</th>
            <th>Driver No</th>
            <th>Vehicle No</th>
            <th>Vendor</th>
            <th>Vehicle Type</th>
            </tr>
         </thead>
         <tbody>
            <tr ng-repeat="x in tripViewData">
             <td id="{{$index}}" class="details-control" >
      		<label><a data-toggle="tooltip" title="Click here to see Emp details">{{x.tripId}}</a></label>
             </td>
            <td>{{x.tripSheetNumber}}</td>
            <td>{{x.tripDate}}</td>
            <td>{{x.reportingTime}}</td>
            <td>{{x.reportingPlace}}</td>
            <td>{{x.driverName}}</td>
            <td>{{x.driverMobileNumber}}</td>
            <td>{{x.vehicleNumber}}</td>
            <td>{{x.vendorName}}</td>
            <td>{{x.vehicleType}}</td>
            </tr>
         </tbody>
      </table>
   </div>
   <div class="modal fade" id="tripSheetModel" role="dialog" ng-init="getAllVendor()">
      <div class="modal-dialog modal-lg">
         <div class="modal-content">
            <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal">&times;</button>
               <h4 class="modal-title">Details</h4>
            </div>
              <form id="tripForm" ng-submit="createTripSheet(tripType,trip.tripId,reqId)">
            <div class="modal-body col-lg-10 col-lg-offset-1" id="adhoc_body">
               <div class="form-group row col-sm-12 col-lg-6 col-md-6" >
                  <label class="col-lg-5 col-form-label">TripSheet No</label>
                  <div class="col-lg-7 has-clear has-feedback">
                   <input list="tripSheet"  name="tripSheetNumber" ng-model="trip.tripSheetNumber" onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" maxlength="8"  class="form-control" required />
                    <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden"></span>
                     <datalist id="tripSheet">
                           <option ng-repeat="x in tripSheetList" id={{x.tripId}} value="{{x.tripSheetNumber}}">{{x.tripSheetNumber}}</option>
                     </datalist>
                  </div>
               </div>
                 <div class="form-group row col-sm-12 col-lg-6 col-md-6" >
                  <label class="col-lg-5 col-form-label">Vendor Name</label>
                  <div class="col-lg-7">
                      <select ng-model="trip.vendorName"  name="vendorName" required
                     class="form-control" >
                     <option value="" selected disabled>Select Vendor</option>
                     <option ng-repeat="x in vendorDrop" value="{{x.travelsName}}" id="{{x.vendorId}}">{{x.travelsName}}</option>
                     <!-- <option  value="Dharma Travels">Dharma Travels</option>
                     <option  value="zzzz Travels">zzzz Travels</option> -->
                  </select>
                  </div>
               </div>
               <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                  <label class="col-lg-5  col-form-label">Trip Date</label>
                  <div class="col-lg-7">
                    <div class='date' id='tripDatePicker'>
                     <input type='text' class="form-control" name="tripDate"  ng-model="trip.tripDate" readonly
                       required /> <!-- <span class="input-group-addon"> <span
                        class="glyphicon glyphicon-calendar"></span> -->
                     </span>
                  </div>
                  </div>
               </div>
               <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                  <label class="col-lg-5  col-form-label">Reporting Time</label>
                  <div class="col-lg-7">
                   <div class='input-group date' id='tripTimePicker'>
                        <input type='text' class="form-control" name="reportingTime"
                           ng-model="trip.reportingTime" onkeypress="return false"  required/> <span class="input-group-addon"> <span
                           class="glyphicon glyphicon-time"></span>
                        </span>
                     </div>
                  </div>
               </div>
                <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                  <label class="col-lg-5  col-form-label">Driver Name</label>
                  <div class="col-lg-7">
                     <input class="form-control" type="text" name="driverName" maxlength="50" ng-model="trip.driverName" required>
                  </div>
               </div>
               <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                  <label class="col-lg-5  col-form-label">Driver Number</label>
                  <div class="col-lg-7">
                     <input class="form-control" type="text" name="driverMobileNumber" onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" minlength="10" maxlength="10" ng-model="trip.driverMobileNumber" required>
                  </div>
               </div>
               <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                  <label class="col-lg-5  col-form-label">Vehicle No</label>
                  <div class="col-lg-7">
                     <input class="form-control" type="text" name="vehicleNumber" maxlength="15" ng-model="trip.vehicleNumber" placeholder="Eg: KA 01 AB 1234" required>
                  </div>
               </div>
               <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                  <label class="col-lg-5  col-form-label">Vehicle Type</label>
                  <div class="col-lg-7">
                     <!-- <input class="form-control" type="text" name="vehicleType" maxlength="20" ng-model="trip.vehicleType" required> -->
                  	<select ng-model="trip.vehicleType"  name="vehicleType" required
                     class="form-control" >
                     <option value="" selected disabled>Select Vendor</option>
                     <option  value="Swift">Swift</option>
                     <option  value="Indica">Indica</option>
                     <option  value="Etios">Etios</option>                     
                  </select>
                  </div>
               </div>
               <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                  <label class="col-lg-5  col-form-label">Reporting Place</label>
                  <div class="col-lg-7">
                     <input class="form-control" type="text" name="reportingPlace" maxlength="30" ng-model="trip.reportingPlace" required>
                  </div>
               </div>
               <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6" id="isEscortDiv">
                  <label class="col-lg-5  col-form-label">Is Escort?</label>
                  <div class="col-lg-7">
                  	 <span>&nbsp;<input type="radio" name="isEscort" ng-model="trip.isEscort" ng-required="!trip.isEscort"  ng-value="true" > <b>Yes</b></span>
                     <span>&nbsp;<input type="radio" name="isEscort" ng-model="trip.isEscort" ng-required="!trip.isEscort"  ng-value="false" > <b>No</b></span>
                     <!-- <input class="form-control" type="text" name="isEscort" ng-model="trip.isEscort" required> -->
                  </div>
               </div>
               <div id="escortName" class="col-lg-12" hidden>
               
                <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                  <label class="col-lg-5  col-form-label">Escort Name</label>
                  <div class="col-lg-7">
                     <input class="form-control" type="text" name="escortName" id="escortName" value="" ng-model="trip.escortName">
                  </div>
                  </div>
                </div> 
              
            </div>
            <div class="modal-footer">
               <input type="submit" class="btn btn-primary createTripSht" >
                <!-- <input type="button" class="btn btn-primary updateTripSht hidden" ng-click="updateTripSheet(trip.tripId,reqId)" value="Update"> -->
                <input type="button" class="btn btn-primary discardTripBtn hidden" ng-click="discardTrip(reqId,trip.tripId)" value="Discard">
               <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
            </div>
             </form>
         </div>
      </div>
   </div>      
    <jsp:include page="modalPopup.jsp" />
</div>
