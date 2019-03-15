<jsp:include page="header.jsp" />
<script src="/tmsApp/js/app/shiftUserController.js"></script>
<script src="/tmsApp/js/app/shiftUserService.js"></script>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<script type="text/javascript">
$(document).ready(function () {
	 var pickupDrp=["05:00 AM","05:30 AM","06:00 AM","06:30 AM","07:00 PM","07:30 PM","08:00 PM","08:30 PM","09:00 PM","09:30 PM"];
	    var dropDrp=["06:00 AM","06:30 AM","09:30 PM","10:00 PM","10:30 PM"];
	 var a, b,month,day,year,time,h,m,min,selcDate,today,d,nextDay,fromDay,ToDay,crntTime;
	  d = new Date();
	  today=new Date().get
	  crntTime=d.getHours();
	  if(crntTime>=17){
		  crntTime=1;
	  }
	  else{
		  crntTime=0;
	  }
	   d.setDate(d.getDate()+30);
	   $("#from").datepicker({
           dateFormat: "yy-mm-dd",
           minDate: crntTime,
           maxDate:d,
           autoclose: true,
           onSelect: function (date) {
        	   $('#fromClear').removeClass('hidden');
        	   $('#saturday,#sunday').removeAttr('required');
        	   fromDay=new Date(date);
        	   fromDay=fromDay.getDay();
        	   $('select[name="pickuptime"],select[name="droptime"]').val("");
        	   createDate(date);
        	   if(selcDate==today){
        		   if(h>=17){
        			   bootbox.alert({
                   	    message: "<label>You Can't create request after 5 PM for today and tomorrow till next day 7 AM </label>",
                   	    callback: function () {
                   	     $('#from').val("");
                   	     $('#fromClear').addClass('hidden');
                   	    }
                   	});
            	   }
        	   }
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
               $('#sun,#sat').addClass("hidden");
               a = $("#from").val();
               b = $("#to").val();
               days();
               $('#from').attr("readonly",true);
           }
       });
       $('#to').datepicker({
           dateFormat: "yy-mm-dd",
           minDate: 1,
           maxDate:d,
           autoclose: true,
           onSelect: function (date) {
        	   $('#toClear').removeClass('hidden');
        	   $('#saturday,#sunday').removeAttr('required').prop('checked',false);
        	   toDay=new Date(date);
        	   toDay=toDay.getDay();
        	   if(toDay=="0"){
        		   $('#sunday').attr('required',true);
        	   }
        	   else if(toDay=="6"){
        		   $('#saturday').attr('required',true);
        	   }
        	   if(fromDay=="0"){
        		   $('#sunday').attr('required',true);
        	   }
        	   else if(fromDay=="6"){
        		   $('#saturday').attr('required',true);
        	   }
        	   check(date);
        	   $('#sun,#sat').addClass("hidden");
               a = $("#from").val();
               b = $("#to").val();
               days();
               $('#to').attr("readonly",true);
               
           }
       });
       var value;
       $('input[name="requestType"]').on('change', (function() {
    	   value = $('input[name="requestType"]:checked').val();
    	   //if(value!="both"){
    		   var date=$("#to").val();
    		   setTimeout(function () {
    			   var toDate=$('#to').val();
    			   if(date!=""&&date!=null&&date!=undefined){
    		       check(date);
    			  }
    		   $('#to').val(toDate);
    	   },150);
    	   //}
       }));
       
        $("#toPlaceDrp,#fromPlaceDrp").on('change', (function() {
    	  if(value=="both"){
    		  var date=$("#to").val();
   		   	setTimeout(function () {
   			   var toDate=$('#to').val();
   			   if(date!=""&&date!=null&&date!=undefined){
   		       check(date);
   			  }
   		   	$('#to').val(toDate);
   	   		},150);
    	  }
       }));
       
        $('#fromClear').click(function(){
        	$(this).siblings().val('');
        	 $(this).siblings().attr("readonly",false);
        	 $(this).addClass('hidden');
        });
        $('#toClear').click(function(){
        	$(this).siblings().val('');
        	 $(this).siblings().attr("readonly",false);
        	 $(this).addClass('hidden');
        	 $('#sat,#sun').addClass('hidden');
        });
       function createDate(date){
    	    d = new Date(),
           month = '' + (d.getMonth() + 1),
           day = '' + d.getDate(),
           year = d.getFullYear();
    	   m=d.getMinutes();
    	   h =  d.getHours();
    	   //h=23;
    	   h =  d.getHours();
    	   min=m;
    	   if(h==12){
    		   time=(h + ':' + min +' PM');
    	   }
    	   else if(h>12){
    		   time=(h-12 + ':' + min +' PM')
    	   }
    	   else{
    		   time=(h + ':' + min +' AM')
    	   }
    	   if(month<=9){
    		   month="0"+month;
    	   }
    	   if(day<=9){
    		   day="0"+day;
    	   }
    	   today=year+"-"+month+"-"+day
   		   console.log(today);
    	   selcDate=date;
    	   nextDay = new Date(d);
    	   nextDay.setDate(d.getDate()+1);
    	   var month1 = '' + (nextDay.getMonth() + 1),
           day1 = '' + nextDay.getDate(),
           year1 = nextDay.getFullYear();
    	   if(month1<=9){
    		   month1="0"+month1;
    	   }
    	   if(day1<=9){
    		   day1="0"+day1;
    	   }
    	   nextDay=year1+"-"+month1+"-"+day1;
       }
       
       
       
       function check(date){
    	   createDate(date);
    	   if(selcDate==today){
    		   if(h<=7){
    			   var stt = new Date(selcDate+" "+ "07:00 AM").getTime();
        		   compare(selcDate,pickupDrp,dropDrp,stt);
    		   }
    		   else{
    		   var stt = new Date(selcDate+" "+ time).getTime();
    		   compare(selcDate,pickupDrp,dropDrp,stt);
    		   }
    	   }
    	   else if(selcDate==nextDay){
    		   if(h>=17){
    		   var stt = new Date(selcDate+" "+ "07:00 AM").getTime();
    		   compare(selcDate,pickupDrp,dropDrp,stt);
    	   	}
    		   else{
    			   $('.droptime option').attr('disabled',false).show();
        		   $('.picktime option').attr('disabled',false).show();
    		   }
    	   }
    	   else{
    		   $('.droptime option').attr('disabled',false).show();
    		   $('.picktime option').attr('disabled',false).show();
    	   }
       }
       
       function compare(selcDate,pickupDrp,dropDrp,stt){
    	   for(var i=0;i<pickupDrp.length;i++){
			   var tim=new Date(selcDate+" "+ pickupDrp[i]).getTime();
			   if(stt>tim){
				   $('#adhoc_form #pick'+[i]).attr('disabled',true).hide();
			   }
			   else{
				   $('#adhoc_form #pick'+[i]).attr('disabled',false).show();
			   }
		   }
		   for(var i=0;i<dropDrp.length;i++){
			   var tim=new Date(selcDate+" "+ dropDrp[i]).getTime();
			   if(stt>tim){
				   $('#adhoc_form #drp'+[i]).attr('disabled',true).hide();
			   }
			   else{
				   $('#adhoc_form #drp'+[i]).attr('disabled',false).show();
			   }
		   }
       }
       function days() {
           console.log('on select event');

           var day = 1000 * 60 * 60 * 24;
           var date1 = new Date(a);
           var date2 = new Date(b);
           var diff = (date2.getTime() - date1.getTime()) / day;
           console.log(diff);
           for (var i = 0; i <= diff; i++) {
               var xx = date1.getTime() + day * i;
               var yy = new Date(xx);
               var date = (yy.getFullYear() + "-" + (yy.getMonth() + 1) + "-" + yy.getDate());
               var split=date.split("-");
               var newdate = new Date(
            		    parseInt(split[0]),
            		    parseInt(split[1]) - 1,
            		    parseInt(split[2])
            		);
               newdate=newdate.getDay();
               if (newdate == 0) {
                   $('#sun').removeClass("hidden");
               } else if (newdate == 6) {
                   $('#sat').removeClass("hidden");
               }
           }
       }
       
   });
   
    $(".readonly").keydown(function(e){
        e.preventDefault();
    });
</script>
<style>
#fromClear,#toClear{
	float:right;
}
</style>
<div class="Adhoc_panel cont col-xs-12 col-sm-10 col-sm-offset-1 col-md-9 col-md-offset-3 col-lg-8
   col-lg-offset-3" ng-controller="shiftUserController">
     
   <div class=" panel panel-default ">
      <div class="panel-heading "><b>Adhoc Request</b></div>
      <div class=" panel-body " id="adhoc_form" ng-init="loadAddr()">
     	 <div class="text-left" style="color:red;padding:0px 15px 15px "><!-- <span>Note: *All fields are mandatory</span> -->
     	 <span>Note:<br>
     	 1. You can't create request after 5 PM<br>
     	   &nbsp;&nbsp;&nbsp;&nbsp;a. Pickup/Drop for the same day.</br>
     	   &nbsp;&nbsp;&nbsp;&nbsp;b. Pickup/Drop for the next morning.<br>
     	 2. All fields are mandatory</span>
     	 </div>
         <form ng-submit="submit_adhoc()" id="create_form">
            <div class="form-group row col-sm-12 col-lg-6 col-md-6 reqForRadioDiv" >
               <label class="col-lg-4 col-form-label">Request Cab for</label>
               <div class="col-lg-8" style="margin-top:5px">
                  <span>&nbsp;<input type="radio" name="requestedFor" ng-model="adhoc.requestedFor"  value="self" ng-required="!requestedFor" ng-checked="true" ng-click="requestfor('self')"> <b>Self</b></span>
                  <span>&nbsp;<input type="radio" name="requestedFor" ng-model="adhoc.requestedFor" id="employee"  value="employee" ng-required="!requestedFor" ng-click="requestfor('employee')"> <b>Employee</b></span>
                  <!-- <span>&nbsp;<input type="radio" name="requestedFor" ng-model="adhoc.requestedFor" value="guest" ng-required="!requestedFor"> <b>Guest</b></span> -->
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6 reqForInpDiv" hidden>
               <div class="empDiv" hidden>
                  <label class="col-lg-4 col-form-label">Employee Name</label>
                  <div class="col-lg-8 has-clear has-feedback">
                   <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden" id="empClear"></span>
                    <input  list="adhocEmployeesList"  name="requestedEmpName"  class="form-control" placeholder="Select Employee"/>
                     <datalist id="adhocEmployeesList">
                        <c:forEach items="${employeesList}" var="employee" varStatus="status">
                        <option value="" disabled selected>Please Select</option>
                           <option id="${employee.id }" value="${employee.name}">${employee.name}</option>
                        </c:forEach>
                     </datalist> 
                     
                  </div>
               </div>
               <div class="guestDiv" hidden>
                  <label class="col-lg-4 col-form-label">Guest Name</label>
                  <div class="col-lg-8">
                     <input type="text" name="requestedGuestName"  class="form-control" />
                  </div>
               </div>
            </div>
            <div class="form-group row col-sm-12 project  col-lg-6  col-md-6">
               <label class="col-lg-4 col-form-label">Project</label>
               <div class="col-lg-8 has-clear has-feedback">
                  <input list="adhocProjectList" value="${sessionScope.user.projectName}" required name="requestedProjectId" placeholder="Select Project" class="form-control" disabled  />
                  <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden" id="projectClear"></span>
                  <datalist id="adhocProjectList">
                     <c:forEach items="${projectList}" var="project" varStatus="status">
                        <option id="${project.id }" value="${project.projectName}"></option>
                     </c:forEach>
                  </datalist>
                  <div class="col-lg-12" style="padding:5px">
                  <span id="othrPrjct"><a>Click here to change project</a></span>
                  </div>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6" x>
               <label for="example-email-input" class="col-lg-4 col-form-label ">From Date</label>
               <div class="form-group  col-lg-8">
                     <input type='text' class="form-control " onkeypress="return false" name="from" id="from"
                        required ng-model="adhoc.from"/> 
                        <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden" id="fromClear"></span>
                        <!-- <span id="fromClear" class="hidden"><a href="#">Clear</a></span> -->
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label for="example-email-input" class="col-lg-4 col-form-label">To Date</label>
               <div class="form-group  col-lg-8">
                     <input type='text' class="form-control" onkeypress="return false"   id="to" name="to"  ng-model="adhoc.to" required/>
                  <span id="sat" class="hidden"><label>&nbsp;<input type="checkbox" id="saturday" ng-model="adhoc.saturday" name="saturday" value="saturday"> Sat</label></span>
                  <span id="sun" class="hidden">&nbsp;<label><input type="checkbox" id="sunday" ng-model="adhoc.sunday" name="sunday" value="sunday" > Sun</label></span>
              	 <!--  <span id="toClear" class="hidden"><a href="#">Clear</a></span> -->
              	 <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden" id="toClear"></span>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6" >
               <label class="col-lg-4 col-form-label">Reason</label>
               <div class="col-lg-8">
                  <input class="form-control" type="text" name="reason"  ng-model=adhoc.reason  required>
            </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-4 col-form-label">Mobile</label>
               <div class="col-lg-8">
                  <input class="form-control" type="tel" name="mobileNumber" ng-model="adhoc.mobileNumber" onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" maxlength="10" minlength="10" pattern="[0-9]{10}" title="Please Enter 10 digit Mobile Number" required>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6 requestType" >
               <label for="example-email-input" class="col-lg-4 col-form-label">Request Type</label>
               <div class="form-group  col-lg-8" style="margin-top:5px">
      			  <span class="reqTip">&nbsp;<input type="radio" name="requestType" ng-model="adhoc.requestType"  value="pickup" ng-required="!adhoc.requestType"> <b>Pickup</b><span class="tooltiptext">Pickup from Home</span></span>
                  <span class="reqTip">&nbsp;<input type="radio" name="requestType" ng-model="adhoc.requestType"  value="drop" ng-required="!adhoc.requestType"> <b>Drop</b>&nbsp; <span class="tooltiptext">Drop to Home</span></span>
                  <span class="reqTip">&nbsp;<input type="radio" name="requestType" ng-model="adhoc.requestType" value="both" ng-required="!adhoc.requestType"> <b>Round Trip</b><span class="tooltiptext">Round Trip</span></span>
               </div>
            </div>
            <div   class="row hidden places" >
            <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
               <label class="col-lg-4  col-form-label">From Place</label>
               <div class="col-lg-8">
                  <select ng-model="adhoc.fromAliasName" 
                     class="form-control" id="fromPlaceDrp" name="fromAddress">
                     <option value=""  selected>Please Select</option>
                     <option ng-repeat="x in adhocAliasList" class="{{x.alias}}" value="{{x.alias}}" id="{{x.employeeId}}" >{{x.alias}}</option>
                  </select>
                  <div class="tooltip1 col-lg-12" style="padding:5px" hidden><a>Hover here to view Address</a>
                     <span class="tooltiptext" ng-bind="fromAddressHover">	</span>
                  </div>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6 toPlace">
               <label class="col-lg-4  col-form-label">To Place</label>
               <div class="col-lg-8">
                  <select ng-model="adhoc.toAliasName" 
                     class="form-control" name="toAddress" id="toPlaceDrp">
                     <option value=""  selected>Please Select</option>
                       <option ng-repeat="x in adhocAliasList" class="{{x.alias}}" value="{{x.alias}}" id="{{x.employeeId}}" >{{x.alias}}</option>
                  </select>
                  <div class="tooltip2 col-lg-12" style="padding:5px" hidden><a>Hover here to view Address</a>
                     <span class="tooltiptext" ng-bind="toAddressHover"></span>
                  </div>
               </div>
            </div>
            </div>
             <div id="bth" style="display:inline">
             </div>
            <div class="text-center col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <input type="submit" class="btn btn-sm" value="Submit">
            </div>
            <div class="text-center alert {{class}} col-lg-offset-4 col-lg-4" ><b>{{status}}</b></div> 
         </form>
      </div>
      <jsp:include page="modalPopup.jsp" />
   </div>
</div>
<script>
   $(document).ready(function(){
	   
   	 $('input:radio[name="requestedFor"]').on('change',(function(){
   		$('.project .form-control-clear').addClass('hidden');
   	    	var val=$(this).val();
   	    	$('[name="pickuptime"],[name="droptime"],#fromPlaceDrp,#toPlaceDrp').removeAttr('required');
   	    	if(val=="employee"){
   	    		$('.requestType').hide();
   	    		$('.requestType input,#sunday,#saturday').removeAttr('required ng-required');
   	    		$('input[name="requestedProjectId"]').removeAttr("disabled",false);
   	        	$('input[name="requestedProjectId"]').val("");
   	    		$('.reqForInpDiv').show();
   	    		$('.empDiv').show();
   	    		$('.guestDiv').hide();
   	    		$('#othrPrjct').hide();
   	    		$('.picktime,.droptime').hide();
   	    		$('.reqForRadioDiv').css('margin-right','100%');
   	    		$('input[name="requestedEmpName"]').attr('required',true).val('');
   	    		$('#from,#to').removeAttr('readonly').val('');
   	    		$('.places,#empClear,#fromClear,#toClear').addClass('hidden');
   	    	}
   	    	else if(val=="self"){
   	    		$('.requestType').show();
   	    		$('.requestType input').attr({'ng-required':'!adhoc.requestType','required':true});
   	    		$('input[name="requestedProjectId"]').attr("disabled",true);
   	        	$('input[name="requestedProjectId"]').val("${sessionScope.user.projectName}");
   	        	$('#sunday,#saturday').removeAttr('required ng-required');
   	    			$('.reqForInpDiv').hide();
   	    			$('.empDiv').hide();
   	        		$('.guestDiv').hide();
   	        		$('#othrPrjct').show();
   	        		$('.picktime,.droptime').hide();
   	        		$('.reqForRadioDiv').css('margin-right','');
   	        		$('#from,#to').removeAttr('readonly').val('');
   	        		$('input[name="requestedEmpName"]').removeAttr('required').val("");
   	        		$('.places,#empClear,#fromClear,#toClear').addClass('hidden');
   	    	}
   	    }));
   	 
/*      $("input[name='requestedProjectId']").bind('input', function () {
    	 var reqFor=$('input[name="requestedFor"]:checked').val();
    	 var val = this.value;
    	    if($('#adhocProjectList option').filter(function(){
    	        return this.value === val;        
    	    }).length) {
    	        //send ajax request
    	    	 if($(this).val()!="${sessionScope.user.projectName}"){
    	    		 if(reqFor=="self"){
    	         	 bootbox.confirm("<label> You have selected "+$(this).val()+" project, which is not alloted project.<br> Please Confirm to proceed</label>", function(result) {
    	         		 if (result == false) {
    	         			if(reqFor=="self"){
    	         			$('input[name="requestedProjectId"]').attr("disabled",true);
    	       	        	$('input[name="requestedProjectId"]').val("${sessionScope.user.projectName}");
    	       	    			$('.reqForInpDiv').hide();
    	       	    			$('.empDiv').hide();
    	       	        		$('.guestDiv').hide();
    	       	        		$('#othrPrjct').show();
    	       	        		$('.reqForRadioDiv').css('margin-right','');
    	         			}
    	         			else{
    	         				$('input[name="requestedProjectId"]').val("");
    	         			}
    	         			$('.project .form-control-clear').addClass('hidden');
    	         		 }
    	         	 });
    	    		 }
    	        	 }
    	    }
     }); */
  	$('.adhocSide').addClass('active');
  	$('.adhocSide .createList').addClass('active');
   });
</script>