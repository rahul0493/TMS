<jsp:include page="header.jsp" />
<script src="/tmsApp/js/app/shiftUserController.js"></script>
<script src="/tmsApp/js/app/shiftUserService.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.ui-datepicker-calendar td {
   padding: 3px !important;
   }
.form-control[readonly]{
	background-color: #eee !important;
    opacity: 1;
}
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}
table .fa:hover {
background-color: rgb(26, 58, 74)  !important;
color:white;
border-radius: 5px;
box-shadow: 4px 4px #999;
}
#DefaultWorkLocation th,#DefaultWorkLocation td{
  text-align:left !important;
}

table .fa:active {
  background-color: #3e8e41;
  box-shadow: 2px 2px #666;
  transform: translateY(2px);
}
    th {
   border: 1px solid #d4d8da !important;
    font-size:small;
   }
    table,
    tr,
    td,
    th {
   background-color: #eaecec;
   text-align: center;
  /*  border: 1px solid #1a3a4a !important */
   }
    td,
   th {
   text-align: center !important;
   font-size: 13px;
   padding: 4px !important;
   }
    .tooltip1 .tooltiptext{
    padding: 5px 10px !important;
    left:100% !important;
    }
    .addrEdit a{
    text-decoration:none ;
    color:#1a3a4a;
    padding: 5px;
    }
    th{
     border-bottom: 1px solid #1a3a4a !important;
   background-color:#b4dbec;
   }
   td{
  	background-color:#f1f1f1;
   }
   #DefaultWorkLocation th{
   border-top: 1px solid #1a3a4a !important;
   background-color:#e6e9eb;
   }
   /* This is to remove the arrow of select element in IE */
#empDetail select::-ms-expand {	display: none; }


 .addAddress a{
   text-decoration:none;
   padding:5px;
   color:#1a3a4a;
   }
   .addAddress .fa:active{
   transform: translateY(2px);
   }
   .addAddress .fa:hover{
   background-color: rgb(26, 58, 74) !important;
   color: white;
   border-radius: 5px;
   box-shadow: 4px 4px #999;
   padding:5px;
   }
   #addressModal .modal-content,#addressModal .modal-body{
   padding-right:0px;
   }
</style>

<div class="user_detail hidden cont col-xs-12 col-sm-6 col-sm-offset-3 col-md-9 col-md-offset-3 col-lg-8
   col-lg-offset-3" ng-controller="shiftUserController" ng-init="getAddressDetail()">
   <div class=" panel panel-default personal">
    <div class="panel-heading faq-links"><b>Personal Information</b>
      </div>
      <div class="panel-body collapse in" id="empDetail" ng-init="getDefaultProject()">
      	 <!-- <div class="text-left" style="color:red;padding:5px 10px 15px "><span>Note: *All fields are mandatory</span></div> -->
         <form id="userform" action="/tmsApp/tms/employee/saveEmployeeDetails" method="post" autocomplete="off">
             <div class="form-group row col-lg-6 col-sm-12 col-md-6">
               <label  class="col-lg-4  col-form-label">Name</label>
               <div class="col-lg-8">
                  <input class="form-control" readonly name="name" type="text" value="${sessionScope.userName}"  required>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6 ">
               <label  class="col-lg-4 col-form-label">Emp Id</label>
               <div class="col-lg-8">
                  <input class="form-control" readonly name="empcode" type="text" value="${sessionScope.empId}"  required>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-4 col-form-label">Email</label>
               <div class="col-lg-8">
                  <input class="form-control" readonly name="email" type="email" value="${sessionScope.userEmail}"  required>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-4 col-form-label">Designation</label>
               <div class="col-lg-8">
                  <input class="form-control" readonly name="designationName" type="text" value="${sessionScope.title}"  required>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-4 col-form-label">Gender</label>
               <div class="col-lg-8">
              <select name="gender" 
                  class="form-control enableField"  required id="genderId">
                  <option value="" disabled selected>Please Select</option>
                  <option value="Male">Male</option>
                  <option value="Female">Female</option>
               </select>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-4 col-form-label">Mobile</label>
               <div class="col-lg-8">
               	  <!-- <input class="form-control" type="tel" name="phoneNumber" pattern="[0-9]{10}" title="Please Enter 10 digit Mobile NO" required> -->
                  <input class="form-control enableField" type="tel" name="phoneNumber" maxlength="10"    pattern="[0-9]{10,10}" title="Please Enter 10 digit Mobile Number"  value="${employee.phoneNumber}"  required>
               </div>
            </div>
          <div class="form-group row col-sm-12  col-lg-6 col-md-6">
            <label class="col-lg-4  col-form-label">Account</label>
            <div class="col-lg-8">
               <select name="accountId"
                  class="form-control enableField" required  id="empAccntDrp">
                  <option value="" disabled selected>Please Select</option>
                  <c:forEach items="${accountList}" var="account" >
                  <option value="${account.id}">${account.accountName}</option>
                  </c:forEach>
               </select>
            </div>
         </div>
         <div class="form-group row col-sm-12  col-lg-6 col-md-6">
            <label class="col-lg-4  col-form-label">Project</label>
            <div class="col-lg-8">
            <input class="form-control enableField exitProject" type="text" name="projectId" value="${employee.projectName}"  required>
               <select name="projectId" 
                  class="form-control enableField newProject"  required id="projectId">
                  <option value="" disabled selected>Please Select</option>
                  <option ng-repeat="x in project"value={{x.projectId}}>{{x.projectName}}</option>
               </select>
            </div>
         </div>
          <div class="form-group row col-sm-12  col-lg-6 col-md-6">
            <label class="col-lg-4  col-form-label">Manager Email</label>
            <div class="col-lg-8">
				<input type="email" name="managerEmail" value="${employee.managerEmail}" maxlength="100"  class="form-control enableField"  required/>
            </div>
         </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6 addressDiv" >
               <label class="col-lg-4 col-form-label" id="address_label">Address</label>
               <div class="col-lg-8">
                  <textarea class="form-control" rows="3" cols="30" name="address" maxlength="100"   required>${employee.address} </textarea>
                  <!-- <input class="form-control" type="text" /> -->
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6 addressDiv">
               <label  class="col-lg-4 col-form-label">Landmark</label>
               <div class="col-lg-8">
                  <input class="form-control" type="text" name="landmark" maxlength="30" placeholder="Near By Landmark"  value="${employee.landmark}" required/>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6 addressDiv" >
               <label  class="col-lg-4 col-form-label">Location</label>
               <div class="col-lg-8">
                  <input class="form-control" type="text" name="location" maxlength="30"   placeholder="Ex: BTM,JP Nagar,..etc" value="${employee.location}" required/>
               </div>
            </div>
            
            <div class="form-group row col-sm-12  col-lg-6 col-md-6 addressDiv ">
               <label class="col-lg-4  col-form-label">City</label>
               <input class="form-control" type="hidden" name="city" id="registerCity"  value="" /> 
               <div class="col-lg-8">
                  <select name="locationId" 
                  class="form-control" id="locationId" required>
                <option  disabled selected>Please Select</option>
                      <c:forEach items="${location}" var="location">
                           <option id="${location.locationName }" value="${location.locationId}">${location.locationName}</option>
                        </c:forEach>
                  </select> 
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6 addressDiv">
               <label  class="col-lg-4 col-form-label">Pincode</label>
               <div class="col-lg-8">
                  <input class="form-control" type="text" name="pincode" maxlength="6" onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" pattern="[0-9]{6,6}" title="Please Enter 6 digit Pincode" value="${employee.pincode}" required />
               </div>
            </div>
             <div class="form-group row col-sm-12  col-lg-6 col-md-6" id="cabRequires" >
               <label class="col-lg-4  col-form-label">Cab Required?</label>
               <div class="col-lg-8">
                <span>&nbsp;<input type="radio" name="isCabRequired"   value=true   > <b>Yes</b></span>
                     <span>&nbsp;<input type="radio" name="isCabRequired"   value=false checked="checked" > <b>No</b></span>
               </div>
            </div>
            <div class="text-center btns userSubmitBtn col-lg-12 col-sm-12 col-md-12">
            <input type="hidden"  value="${employee.id}"  name="id" />
                  <input type="submit" class="btn btn-md userSubmit"  value="Submit"   />
            </div>

         </form>
      </div>
   </div>
   <div id="updatAddressDiv" style="margin-bottom:50px">
     <div id="addressTable col-xs-12">
     <div >
              <table id="DefaultWorkLocation" style="text-align: left!important;"> 
              <tr ng-repeat="z in address_data" ng-if="z.alias=='Client'">
              <th>Default Work Location</th>
              <th ng-repeat="y in address_data" ng-if="y.alias!='Home'"><input type="radio" name="defaultWorkPlace"  ng-model="y.defaultAddress" ng-value=true   ng-click="defaultWorkPlace(y.alias,address_data)">&nbsp;<b>{{y.alias}}</b></th>
              </tr>
              <tr ng-repeat="t in address_data" ng-if="t.alias=='Home'">
             <th>Cab Required</th>
              <th ><input type="radio" name="cabrequired"  ng-model="t.isCabRequired" ng-value=true   ng-change="CabRequired(empId,true)">&nbsp;<b>Yes</b></th>
              <th ><input type="radio" name="cabrequired"  ng-model="t.isCabRequired" ng-value=false   ng-change="CabRequired(empId,false)">&nbsp;<b>No</b></th>
              </tr>
              </table>
       </div> 
       <br>
        <div class=" panel panel-default">
      <div class="panel-heading faq-links"><b>Address Details</b>
      </div>
      <div class=" panel-body vendorBody">
       
      <table id="addressTableId">
      <thead>
         <tr>
            <!-- <th>Work Location</th> -->
            <th>Address Type</th>
            <th>Address</th>
            <th>Landmark</th>
            <th>Location</th>
            <th>Pincode</th>
            <th>Status</th>
            <th>Action</th>
         </tr>
         </thead>
         <tbody>
         <tr ng-repeat="x in address_data" id="{{x.empAddressBeanId}}">
            <td id="alias">{{x.alias}} </td>
           <!--  <td id="address{{$index+1}}">{{x.address}},{{x.landmark}},{{x.location}},{{x.pincode}}</td> -->
           <td id="address{{$index+1}}">{{x.address}}</td>
            <td id="landmark{{$index+1}}">{{x.landmark}}</td>
            <td id="location{{$index+1}}">{{x.location}}</td>
            <td id="pincode{{$index+1}}">{{x.pincode}}</td>
            <td>
            <span data-ng-if="x.alias=='Office'">
               </span>
               <span data-ng-if="x.alias!='Office'">
            {{x.statusValue}}
            </span>
            
            </td>
             <td id="AddrAction{{x.empAddressBeanId}}"  class="addrEdit" style="text-align:left !important" >
             <span data-ng-if="x.alias=='Office'">
               </span>
               <span data-ng-if="x.alias!='Office'">
               <span >
               <a class="fa fa-pencil-square-o fa-lg edit_address" data-toggle="tooltip" title="Edit" id="edit_address{{$index+1}}" aria-hidden="true" ng-click="edit_address(x.empAddressBeanId,x.alias)" ></a>
            </span>
            <span data-ng-if="x.statusValue=='Pending'">
            <a class="fa fa-arrow-circle-right fa-lg" data-toggle="tooltip" title="Send Approval to Secondary Approver" ng-click="secondaryApproval(x.employeeId,x)" aria-hidden="true"></a>
            </span>
              <span data-ng-if="x.statusValue!='Pending'">
              </span>
            </span>
            </td> 
         </tr>
       </tbody>   
      </table>
      </div>
      </div>
      <!--  <button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal">Open Modal</button> -->
   </div> 
   <br>
   
   <div class="modal fade" id="addressModal" role="dialog">
      <div class="modal-dialog modal-lg">
         <div class="modal-content col-lg-12">
            <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal">&times;</button>
               <h4 class="modal-title">Address Details</h4>
            </div>
            <div class="modal-body" >
            	<form id="address_form" ng-submit="createAddress()" autocomplete="new-password">
        <div class="" >
        <span style="color:red">   * Note:All Fields are Mandatory</span>
        </div>
        <br>
        <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6 addressAlias">
               <label class="col-lg-4  col-form-label">Address Alias</label>
               <div class="col-lg-8">
                  <select name="alias"  
                     class="form-control" ng-model="address.alias" required>
                     <option value="" disabled selected>Please Select</option>
                     <option name="alias" value="Home">Home</option>
                     <option name="alias"  value="Client">Client</option>
                  </select>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label class="col-lg-4 col-form-label" id="address_label">Address</label>
               <div class="col-lg-8">
                  <textarea class="form-control" rows="3" cols="30" maxlength="100"  ng-model="address.address"  required> </textarea>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-4 col-form-label">Landmark</label>
               <div class="col-lg-8">
                  <input class="form-control" type="text" ng-model="address.landmark"   maxlength="30" required/>
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-4 col-form-label">Area/ Location</label>
               <div class="col-lg-8">
                  <input class="form-control" type="text" placeholder="BTM, Marthahalli,..etc"  maxlength="30"  ng-model="address.location" required/>
               </div>
            </div> 
            <div class="form-group row col-sm-12  col-lg-6 col-md-6">
               <label class="col-lg-4  col-form-label">City</label>
               <div class="col-lg-8">
                  <select name="locationId" 
                  class="form-control" id="locationId" ng-model="address.city" required>
                    <option  disabled value="" selected>Please Select</option>
                      <c:forEach items="${location}" var="location">
                           <option id="${location.locationName}" value="${location.locationName}">${location.locationName}</option>
                        </c:forEach>
                  </select> 
               </div>
            </div>
            <div class="form-group row col-sm-12 col-lg-6 col-md-6">
               <label  class="col-lg-4 col-form-label">Pincode</label>
               <div class="col-lg-8">
                  <input class="form-control" type="text" ng-model="address.pincode" onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;"   maxlength="6"  pattern="[0-9]{6,6}" title="Please Enter 6 digit Pincode" required />
               </div>
            </div>
             <div class="form-group reflectFrom row col-sm-12 col-lg-6 col-md-6 hidden" >
               <label for="example-email-input" class="col-lg-4 col-form-label ">Effective From</label>
               <div class="form-group  col-lg-8">
                     <input type='text' class="form-control" ng-model="address.effectiveDate" onkeypress="return false" name="effectiveDate" id="reflectFrom"
                         /> 
               </div>
            </div>
            <div class="text-center addressBtns btns col-lg-12 col-sm-12 col-md-12"  style="padding-bottom:10px">
               <input type="submit" class="btn btn-md homeBtn" disabled value="Create"   />
               <input type="reset" class="btn btn-md" value="Reset" disabled ng-click="addressReset()"   />
            </div>
         </form>
            </div>
            </div>
            </div>
            </div>
   </div>
    <jsp:include page="modalPopup.jsp" />
</div>
<script>
var val="${employee.isCabRequired}";
$(document).ready(function(){
	if("${authorities[0] }"=="READ-ONLY"){
		$('#empDetail').removeClass('collapse');
		$('.addressDiv').removeClass('hidden');
		$('#updatAddressDiv').addClass('hidden');
		$('#cabRequires').show();
	}
	else{
		$('.addressDiv').addClass('hidden');
		$('#updatAddressDiv').removeClass('hidden');
		$('#cabRequires').hide();
	}
	
	
	if("${employee.gender}"!=""){
		$("#genderId").val("${employee.gender}");
	}
	if("${employee.accountId}"!=""){
		$("#empAccntDrp").val("${employee.accountId}");
	}
	//setTimeout(function(){ 
	if("${employee.projectId}"!=""){
		$("#projectId").val("${employee.projectId}");
		$('.newProject').remove();
	 }
	else{
		$('.exitProject').remove();
	}
	//}, 50);
	if("${employee.city}"!=""){
		$("#cityId").val("${employee.city}");
	}
	if("${employee.state}"!=""){
		$("#stateId").val("${employee.state}");
	}
	if("${employee.phoneNumber}"!=""){
	$(".userSubmit").addClass("hidden");
	}
	else{
	$(".userSubmit").val("Submit");
	} 
	setTimeout(function(){ 
		$('#projectId').val("${employee.projectId}");
	}, 150);
if("${employee.phoneNumber}"!=""){
	$('#empDetail .form-control').attr({'readonly':true});
	$('#empDetail .enableField').attr({'readonly':true});
	$('#empDetail input,#empDetail .form-control[readonly]').attr('disabled',true);
	$('#empDetail select').css({"-webkit-appearance":"none","-moz-appearance":"none","appearance":" none;"});
	
	}
else{
	$('#empDetail select').css('display','block');
}

if("${employee.isCabRequired}"=="true"){
	$('#projectType').val('shift');
	$("input[name='isCabRequired'][value='false']").removeAttr('checked');
	$("input[name='isCabRequired'][value='true']").prop('checked',true);
}	
else{
	$('#projectType').val('nonShift');
}

$('#locationId').on('change',function(){
	var id = $(this).children(":selected").attr("id");
	$('#registerCity').val(id);
});
console.log("${location[0].locationName}")
$('.userSide').addClass('active');
	$('.userSide').addClass('active');
	$('.userSide .updateAddrSide').addClass('active');
		 var a, b;
		 var d = new Date();
		   d.setDate(d.getDate()+07);
		   $("#reflectFrom,#myModal #reflectFrom").datepicker({
	           dateFormat: "yy-mm-dd",
	           minDate: 0,
	           maxDate:d,
	           autoclose: true,
	           onSelect: function (date) {
	               $('#reflectFrom').attr("readonly",true);
	           }
	       });
});
</script>
