<jsp:include page="header.jsp" />
<script src="/tmsApp/js/app/shiftUserController.js"></script>
<script src="/tmsApp/js/app/shiftUserService.js"></script>
<script type="text/javascript">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
   $(function() {
/*     $('#from').datetimepicker({
    	format : "yyyy-mm-dd",
   		autoclose : true,
   		todayBtn : false,
   	 	minDate: 0 ,
   		ampm : true,	
   		minView:2,
        pickTime: false,
   		showMeridian : true,
   		pickerPosition : "bottom-left",
   	}).on('changeDate', function (selected) {
   	 $('#fromClear').removeClass('hidden');
   		//$('#to').data("DateTimePicker").destroy();
   	    var startDate = new Date(selected.date.valueOf());
   	    $('#to').datetimepicker({
   	    	format : "yyyy-mm-dd",
   	   		autoclose : true,
   	   		todayBtn : false,
   	   		minView:2,
   	        pickTime: false,
   	   		showMeridian : true,
   	   		pickerPosition : "bottom-left",
   	   	    startDate: startDate,
   	    	
   	    }).on('changeDate', function (selected) {
   	    	$('#toClear').removeClass('hidden');
   	    });
   	// $('#to').data("DateTimePicker").minDate(startDate);
   	}).on('clearDate', function (selected) {
   	    $('#to').datetimepicker('setStartDate', null);
   	});  */
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
   	
   	/* $('#picktime,#droptime').datetimepicker({
   		format: 'hh:ii',   		
        autoclose: true,
        ampm : true,	
        todayHighlight: true,
        showMeridian: true,
        startView: 1,
        maxView: 1,
        pickerPosition : "bottom-left"
   	}); */
   	
   	$('#fromClear,#toClear').click(function(){
    	$(this).siblings().val('');
    	$(this).addClass('hidden');
    });
   	$('.adhocSide').addClass('active');
  	$('.adhocSide .reportList').addClass('active');
  	$('.ajax-loader').css("visibility", "visible");
   });
</script>
<style>
     table,
    tr,
    td,
    th {
   background-color: #eaecec;
   text-align: center;
   border:1px solid #bbc1ca;
   }
   #adhocHistryPanel th,#adhocHistryPanel td{
   padding:5px !important;
   }
   #adhocHistryPanel th{
   padding-right:15px !important;
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
    padding-right: 10px !important;
   border-bottom: 1px solid #1a3a4a !important 
   }
   td{
  	background-color:#f1f1f1;
   }
   div .adhocReport{
    display: none;
   }
   /* #adhocViewPanel,#adhocHistryPanel{
       overflow: auto;
       } */
</style>
<script type="text/javascript">
function report(evt, reportType) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    if(reportType=="adhocHistryPanel"){
    	document.getElementById(reportType).style.display = "inline-block";
    }
    else{
    document.getElementById(reportType).style.display = "block";
    }
    evt.currentTarget.className += " active";
    setTimeout(function(){  
   	 $('[data-toggle="tooltip"]').tooltip();  
   	 }, 100);
}
$(document).ready(function(){
document.getElementById("defaultOpen").click(); 
});

</script>

<div class="monthly_panel cont col-xs-12 col-sm-10 col-sm-offset-1 col-md-9 col-md-offset-3 col-lg-10
   col-lg-offset-2" ng-controller="shiftUserController" ng-init="getAdhocCabReqList('${status}','${type}')">
   <div class="tab" >
  <button class="tablinks" onclick="report(event, 'adhocViewPanel')" id="defaultOpen">{{request}}</button>
  <button class="tablinks" onclick="report(event, 'adhocHistryPanel')">History</button>
</div>
   <div id="adhocHistryPanel" class="tabcontent col-lg-12 col-sm-12 col-xs-12"> 
   <div class="col-lg-offset-1 col-lg-10">
         <form id="histrySearchForm">
         <br>
            <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6" >
               <label class="col-lg-4  col-form-label">Project</label>
               <div class="col-lg-8">
                  <select ng-model="history.projectId" 
                     class="form-control" >
                     <option value=""  selected>Please Select</option>
                     <%-- <option name="" value="${sessionScope.user.projectId}">${sessionScope.user.projectName}</option> --%>
                      <c:forEach items="${projList}" var="project" >
                  <option value=${project.id}>${project.projectName}</option>
                  </c:forEach>
                   </select>
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
            <div class="" >
               <div class="form-group row col-sm-12 col-lg-6 col-md-6">
                  <label for="example-email-input" class="col-lg-4 col-form-label">From</label>
                  <div class="form-group  col-lg-8">
                        <input type='text' class="form-control" name="from" id="from" readonly ng-model="history.fromDate"/>
                        <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden" id="fromClear"></span> 
                  </div>
               </div>
               <div class="form-group row col-sm-12 col-lg-6 col-md-6">
                  <label for="example-email-input" class="col-lg-4 col-form-label">To</label>
                  <div class="form-group  col-lg-8" >
                        <input type='text' class="form-control" id="to" name="to" readonly ng-model="history.toDate" /> 
                        <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden" id="toClear"></span>
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
                  <input type="submit" class="btn btn-md " value="Search" ng-click="Adhoc_History()" id="submit" />
                  <input type="reset" class="btn btn-md" value="Reset" ng-click="resetHistorySearch()"   />
                  <div class="text-center alert {{class}} col-lg-offset-4 col-lg-4" ><b>{{status}}</b></div> 
               </div>
            </div>
         </form>
         </div>
         <br>
   <div class="adhoc_histry_table" hidden>
      <table  id="adhoc_histry_table" class="table table-responsive">
         <thead>
            <tr>
           			 <th>Doc.No</th>
                     <th>Request Type</th>
                     <th>Schedule Date</th>
                     <th>Request Time</th>
                     <th>From Address</th>
                     <th>To Address</th>
                     <th>Requested Date</th>
                     <th>Project</th>
                     <th>Mobile Number</th>
                     <th>Request Status</th>
            </tr>
         </thead>
         <tbody>
            <tr ng-repeat="x in data" >
             	<td class="details-control"  id={{$index}}>
      					<label><a data-toggle="tooltip" title="Click here to see Request details">{{x.id}}</a></label>
             			</td>
               <td style="text-transform:capitalize;">{{x.requestType}}</td>
               <td>{{x.scheduleDate |date:'dd-MMM-yyyy'}}</td>
               <td>{{x.reqTime}}</td>
                <td>{{x.fromLocation}}</td>
               <td>{{x.toLocation}}</td>
                <td >{{x.requestDate |date:'dd-MMM-yyyy'}}</td>
                <td>{{x.projectName}}</td>
               <td>{{x.mobileNumber}}</td>
               <td>{{x.travelStatus}}</td>
            </tr>
         </tbody>
      </table>
   </div>
   </div>
   <div id="adhocViewPanel" class="tabcontent col-lg-12">
     <div class="col-lg-12 adhocViewPanelBtns" style="font-size: 15px;margin: 10px">
      <span>&nbsp;<input type="radio" name="adhocView" value="self"  ng-checked="true" ng-click="getAdhocCabReqList('','')"> <b>Self</b></span>
      <span>&nbsp;<input type="radio" name="adhocView"  value="others" ng-click="getAdhocOtherCabReqList()" > <b>Others</b></span>
     </div>
     <br>
         <div class="adhocviewTable" >
            <table border="1" id="adhocview"  class="table table-responsive table-bordered">
               <thead>
                  <tr>
                     <th>Doc.No</th>
                     <th>Request Type</th>
                     <th>Schedule Date</th>
                     <th>Request Time</th>
                     <th>From Address</th>
                     <th>To Address</th>
                     <th>Requested Date</th>
                     <th>Project</th>
                     <th>Request Status</th>
                     <th class="cancel">Cancel</th>
                  </tr>
               </thead>
               <tbody id="">
                   <tr id="datano{{$index}}" ng-repeat="x in adhocViewData" style="display:table-row">
             			<td class="details-control"  id={{$index}}>
      					<label><a data-toggle="tooltip" title="Click here to see Request details">{{x.id}}</a></label>
             			</td>
                        <td id="reqtype{{$index}}" style="text-transform:capitalize;">{{x.requestType}}</td>
                        <td id="sdate{{$index}}">{{x.scheduleDate |date:'dd-MMM-yyyy'}}</td>
                        <td>{{x.reqTime}}</td>
                        <td>{{x.fromLocation}}</td>
                        <td>{{x.toLocation}}</td>
                        <td >{{x.requestDate |date:'dd-MMM-yyyy'}}</td>
                        <td>{{x.projectName}}</td>
                        <td id="reqStatus{{$index}}">{{x.travelStatus}}</td>
                        <td class="cancel">
                           <i class="fa fa-times fa-lg cancel" data-toggle="tooltip" title="Click here to Cancel Request" aria-hidden="true" ng-click="cancel_request(x.id,x.scheduleDate,'${status}','${type}')"></i>
                        </td>
                     </tr>
               </tbody>
            </table>
         </div>
         <div class="adhocOtherViewTable" hidden>
            <table border="1" id="adhocOtherView"  class="table table-responsive table-bordered">
               <thead>
                  <tr>
                     <th>Doc.No</th>
                     <th>Request Type</th>
                     <th>Requested For</th>
                     <th>Schedule Date</th>
                     <th>Request Time</th>
                     <th>From Address</th>
                     <th>To Address</th>
                      <th>Requested Date</th>
                      <th>Project</th>
                     <th>Request Status</th>
                     <th>Cancel</th>
                  </tr>
               </thead>
               <tbody id="monthly_tbody">
                   <tr id="datano{{$index}}" ng-repeat="x in adhocOtherViewData" style="display:table-row">
             			<td class="details-control"  id={{$index}}>
      					<label><a data-toggle="tooltip" title="Click here to see Request details">{{x.id}}</a></label>
             			</td>
                        <td id="reqtype{{$index}}" style="text-transform:capitalize;">{{x.requestType}}</td>
                        <td>{{x.requestedFor}}</td>
                        <td id="sdate{{$index}}">{{x.scheduleDate|date:'dd-MMM-yyyy'}}</td>
                        <td>{{x.reqTime}}</td>
                        <td>{{x.fromLocation}}</td>
                        <td>{{x.toLocation}}</td>
                        <td >{{x.requestDate |date:'dd-MMM-yyyy'}}</td>
                        <td>{{x.projectName}}</td>
                        <td id="reqStatus{{$index}}">{{x.travelStatus}}</td>
                        <td>
                           <i class="fa fa-times fa-lg cancel" data-toggle="tooltip" title="Click here to Cancel Request" aria-hidden="true" ng-click="cancel_request(x.id,x.scheduleDate)"></i>
                        </td>
                     </tr>
                  
                  
               </tbody>
            </table>
         </div>
      </div>
      <jsp:include page="modalPopup.jsp" />
</div>