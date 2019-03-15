<jsp:include page="header.jsp" />
<script src="/tmsApp/js/app/financeController.js"></script>
<script src="/tmsApp/js/app/financeService.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<style>

.ajax-loader {
     visibility: visible;
     }
.ui-datepicker-calendar td {
   padding: 2px !important;
   }
   tr,
   td,
   th {
   background-color: #eaecec;
   text-align: center;
   border:1px solid #bbc1ca;
   }
   td input{
   text-align:center !important;
   }
   td,
   th {
   text-align: center !important;
   font-size: 14px;
   padding: 4px !important;
   }
   th{
   background-color:#b4dbec;
   border-bottom: 1px solid #1a3a4a !important 
   }
   td{
   background-color:#f1f1f1;
   }
   .addHoliday a,.deleteHoliday {
   text-decoration:none;
   padding:5px;
   color:#1a3a4a;
   }
   .addHoliday .fa:active,.deleteHoliday:active{
   transform: translateY(2px);
   }
   .addHoliday .fa:hover,.deleteHoliday:hover{
   background-color: rgb(26, 58, 74) !important;
   color: white;
   border-radius: 5px;
   box-shadow: 4px 4px #999;
   padding:5px;
   }
   
   a:hover, a:focus {
  color: #2a6496;
  text-decoration: none;
}
   .square-service-block{
	position:relative;
	overflow:hidden;
	margin:15px auto;
	box-shadow: 5px 5px #999999a6;
	 border-radius: 5px;
	}
.square-service-block a {
 background-color:#31708e;
  border-radius: 5px;
  display: block;
  padding: 40px 20px;
  text-align: center;
  width: 100%;
  
}
.square-service-block a:hover{
  background-color: #3c8dbc;
  color:black;
  border-radius: 5px;
}
.square-service-block:hover{
 border-radius: 5px;
  box-shadow: 8px 8px #999;
}

.square-service-block a:active{
 box-shadow: 2px 2px #666;
 transform: translateY(2px);
}
.ssb-icon {
  color: #fff;
  display: inline-block;
  font-size: 28px;
  margin: 0 0 20px;
}

h2.ssb-title {
  color: #fff;
  font-size: 20px;
  font-weight: 200;
  margin:0;
  padding:0;
}


</style>
<div class="user_detail  col-xs-12 col-sm-6 col-sm-offset-3 col-md-9 col-md-offset-3 col-lg-8
   col-lg-offset-3" ng-controller="financeController" >
   <div class="tab" >
 <button class="tablinks holidayTab" ng-click="financePanel($event, 'holidayListDiv')" >Holidays</button> 
  <button class="tablinks allowanceTab" ng-click="financePanel($event, 'shiftAllowanceDiv')" >Shift Allowance</button>
  <button class="tablinks shiftAllowanceTab" ng-click="financePanel($event, 'allowanceViewDiv')">Allowance View</button>
  <button class="tablinks allowanceExcelTab" ng-click="financePanel($event, 'allowanceExcelDiv')">Finance Excel</button>
</div>
   
      <div class="tabcontent col-lg-12" id="holidayListDiv">
 	<br>
         <form ng-submit="createHolidayList()">
            <div class="col-lg-12">
               <div class="form-group row col-sm-12  col-lg-6 col-md-6 ">
                  <label class="col-lg-4  col-form-label">Location</label>
                  <div class="col-lg-8">
                     <select name="city" 
                        class="form-control" id="holidayLocation" ng-model="holiday.Location.locationId" required>
                        <option value="" disabled selected>Please Select</option>
                        <c:forEach items="${location}" var="location">
                           <option id="${location.locationId }" value="${location.locationId}">${location.locationName}</option>
                        </c:forEach>
                        <!-- <option name="city" value="bangalore">Bangalore</option>
                        <option name="city" value="mumbai">Mumbai</option> -->
                     </select>
                  </div>
               </div>
               <div class="form-group row col-sm-12  col-lg-6 col-md-6 ">
                  <label class="col-lg-4  col-form-label">Year</label>
                  <div class="col-lg-8">
                     <select name="city" 
                        class="form-control" id="holidayYear" ng-model="holiday.Year" disabled required>
                        <option value="" disabled selected>Please Select</option>
                     </select>
                  </div>
               </div>
            </div>
            <div class="col-lg-12  holidayDiv hidden">
               <table id="createHoliday">
                  <thead>
                     <tr>
                        <th>S.No</th>
                        <th>Date</th>
                        <th>Description</th>
                        <th>National Holiday</th>
                        <th>Public Holiday</th>
                        <th>Delete</th>
                     </tr>
                  </thead>
                  <tbody>
                     <tr ng-repeat="x in holidayList" id=holiday{{$index+1}}>
                     <td>{{$index+1}}</td>
                     <td data-search={{holidayList[$index].holidayDate}}><input type='text' class="form-control holidayDate" value={{holidayList[$index].holidayDate}} ng-model="holidayList[$index].holidayDate"  datetimepicker onkeypress="return false"  required/></td>
                     <td data-search={{holidayList[$index].holidayName}}><input class="form-control" type="text" value={{holidayList[$index].holidayName}} ng-model="holidayList[$index].holidayName" maxlength="30" required/></td>
                     <td><input type="checkbox" class="holidayType nationalHoliday {{$index}}" ng-model="holidayList[$index].isNationalHoliday" ng-change="holidayChange(this)"/></td>
                     <td><input type="checkbox" class="holidayType publicHoliday {{$index}}" ng-model="holidayList[$index].isPublicHoliday" ng-change="holidayChange(this)"/></td>
                     <td><a class="fa fa-trash fa-lg deleteHoliday" id="" data-toggle="tooltip" title="Delete" aria-hidden="true" ng-click="deleteHoliday($index,x.holidayId,x.location.locationId,x.year)" ></a></td>
                     </tr>
                  </tbody>
               </table>
              <!--  <div class="text-center noRecord" style="padding:3px;border-bottom:1px solid" hidden><label>No Record Found</label></div> -->
               <div style='padding-top: 10px; padding-right: 15px;'  class='row addHoliday'>
                  <a class='fa fa-plus-circle fa-lg addHoliday' data-toggle='tooltip' title='Add New Holiday' ng-click="addHoliday($inde+1)" style='float: right;'> Add Holiday</a>
                  <div class="text-center  col-lg-12 col-sm-12 col-md-12" style="padding-bottom:10px">
                     <input type="submit" class="btn btn-md submitBtn"   />
                  </div>
                  <div>
                     <div class="text-center holidayAlert {{class}} col-lg-offset-4 col-lg-4" hidden ><b>{{holidayAlertStatus}}</b></div>
                  </div>
               </div>
            </div>
         </form>
         </div>
         <div class="tabcontent allowanceDiv col-lg-12" ng-init="getShift()" id="shiftAllowanceDiv">
 <br>
            <form ng-submit="createAllowance()">
               <table id="allowanceShiftTable">
                  <thead>
                     <tr>
                        <th>S.No</th>
                        <th>Shift Name</th>
                        <th>Shift Initials</th>
                        <th>Allowance</th>
                     </tr>
                  </thead>
                  <tbody>
                     <tr ng-repeat="x in shiftAllowance" id="holiday"+{{$index+1}}>
                     <td>{{$index+1}}</td>
                     <td><label>{{x.shiftName}}</label></td>
                     <td><label>{{x.shiftInitials}}</label></td>
                     <td><input type="text" class="form-control allowance" ng-model="shiftAllowance[$index].allowance" onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" ng-value="00.00"/></td>
                     </tr>
                  </tbody>
               </table>
               <div class="text-center  col-lg-12 col-sm-12 col-md-12" style="padding:10px 10px">
                  <input type="submit" class="btn btn-md submitBtn" value="Save"/>
               </div>
               <div>
                  <div class="text-center allowanceAlert {{class}} col-lg-offset-4 col-lg-4" hidden ><b>{{allowanceAlertStatus}}</b></div>
               </div>
            </form>
            </div>
            
            
            <div class="tabcontent allowanceDiv col-lg-12"  id="allowanceViewDiv">
                <div class="col-lg-12">
                   <br>
      <form id="histrySearchForm">
      <div class=" panel panel-default ">
      <div class="panel-heading faq-links"><b>Search</b>
      </div>
      <div class="panel-body collapse in" id="search" >
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
                     <input type='text' class="form-control" id="to" readonly name="to" ng-change="checkErr(history.fromDate,history.toDate)" ng-model="history.toDate" /> 
                       <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden" id="toClear"></span>
                  
               </div>
               <span>{{errMessage}}</span>
            </div>
            
           
           
            <div class="text-center">
               <input type="submit" class="btn btn-md " value="Search" ng-click="Allowance_History()" id="submit" />
               <input type="button" class="btn btn-md" value="Reset" ng-click="resetAllowance()"/>
            </div>
             <div class="text-center flmHistAlert {{class}} col-lg-offset-4 col-lg-4" hidden ><b>{{flmHistStatus}}</b></div> 
            </div>
            </div>
         </div>
      </form>
      </div>
      <!-- <br> -->
      <div class="allowanceViewTable" hidden>
      <hr>
         <table  id="allowanceViewTable" class="table table-responsive display" cellspacing="0" width="100%">
            <thead>
               <tr>
           	   <th>S.No</th>
               <th>Employee Number</th>
               <th>Employee Name</th>
               <th>Month</th>
               <th>EarningComponentAffected</th>
               <th>Amount</th>
               <th>Remarks</th>
               <th>Project Name</th>
               <th>Spoc</th>
               
              
            </tr>
            </thead>
            <tbody>
               <tr ng-repeat="x in allowanceView"  id={{x.id}}>
                             
               <td id="{{$index}}" class="details-control" >{{$index+1}}</td>
               <td>{{x.empId}}</td>
               <td>{{x.empName}}</td>
               <td>{{x.month}}</td>
               <td>{{x.earningComponentAffected}}</td>
               <td>{{x.allowanceAmount}}</td>
               <td>{{x.remark}}</td>
               <td>{{x.projectName}}</td>
               <td>{{x.spocName}}</td>
               
            
             
               </tr>
            </tbody>
         </table>
      </div>
            </div>
              <div class="tabcontent col-lg-12"  id="allowanceExcelDiv">
 <br>
            <div class="form-group row col-sm-12 col-xs-12 col-lg-5 col-lg-offset-1" >
            <label class="col-lg-4  col-form-label">Select Month</label>
            <div class="col-lg-8">
               <input type='text' class="form-control" id="excelCal" onfocus="this.blur()" ng-model="excelCal"  onkeypress="return false"  required/>
            </div>
           
         </div>
            <div class=" col-sm-12 col-xs-12 col-lg-offset-1 col-lg-10">
            
          <div class="col-md-6">
            <div class="square-service-block" ng-click="financeExcel('FinalExcelReport')">
               <a href="#">
                 <div class="ssb-icon"><i class="fa fa-file-excel-o" aria-hidden="true"></i></div>
                 <h2 class="ssb-title">Finance Excel</h2>  
               </a>
            </div>
          </div>
          
          <div class="col-md-6">
            <div class="square-service-block" ng-click="financeExcel('SAPExcelReport')">
               <a href="#">
                 <div class="ssb-icon"> <i class="fa fa-file-excel-o" aria-hidden="true"></i> </div>
                 <h2 class="ssb-title">SAP Excel</h2>  
               </a>
            </div>
            </div>
           
          </div>
           <div class="text-center financeExcelAlert {{class}} col-lg-offset-4 col-lg-4" hidden ><b>{{financeExcStatus}}</b></div>
            </div>
         </div>
<script>
$(function() {
	  $('#excelCal').datetimepicker({
    	format : "mm-yyyy",
   		autoclose : true,
   		todayBtn : false,
   		startView: "year",
   		minView: 3,
   		maxView: 4,
    	pickTime: false,
        showMeridian : false,
   		pickerPosition : "bottom-left",
   	}) 
   	

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
	
});


   $(document).ready(function(){
	   $('.holidayTab').attr('id','defaultOpen');
	document.getElementById("defaultOpen").click();
    $('.financeSide').addClass('active');
     var i=new Date().getFullYear();
         i=i-5;
     for (var j=i; j<(i+8); j++)
    {
        $('#holidayYear').append($('<option />').val(j).html(j));
    }
     $('.ajax-loader').css("visibility", "hidden");
      
   });
</script>