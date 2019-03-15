<jsp:include page="header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="/tmsApp/js/app/adminController.js"></script>
<script src="/tmsApp/js/app/adminService.js"></script>
<style>
.ajax-loader {
     visibility: visible;
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
</style>
<script type="text/javascript">
$(document).ready(function(){
//$('.adminSide').addClass('active');
$('.trackMappingSide').addClass('active');
console.log("${authorities}");
/* if("${sessionScope.isAdmin}"=="ADMIN"||"${sessionScope.isManager}"=="SENIOR_MANAGER"){
	
} */
if("${sessionScope.isSuperAdmin}"!="SUPER_ADMIN"){
	if("${sessionScope.isManager}"!="SENIOR_MANAGER"){
		$('.userRoleTab,#userRoleDiv').remove();
		$('.spocTrackMappingTab').attr('id','defaultOpen');
	}

	 if("${sessionScope.isManager}"!="SENIOR_MANAGER"&&"${sessionScope.isSpoc}"=="SPOC"){
		$('.userRoleTab,#userRoleDiv,.spocTrackMappingTab,#spocTrackMappingDiv').remove();
		$('.empTrackMappingTab').attr('id','defaultOpen');
	}
	else if("${sessionScope.isManager}"!="SENIOR_MANAGER"){
		$('.spocTrackMappingTab,#spocTrackMappingDiv').remove();
	}
	else if("${sessionScope.isManager}"=="SENIOR_MANAGER"&&"${sessionScope.isSpoc}"!="SPOC"){
		$('.empTrackMappingTab,#empTrackMappingDiv').remove();
	}
	else if("${sessionScope.isManager}"=="SENIOR_MANAGER"&&"${sessionScope.isSpoc}"!="SPOC"){
		$('.empTrackMappingTab,#empTrackMappingDiv').remove();
	}
	 if("${sessionScope.isManager}"!="SENIOR_MANAGER"&&"${sessionScope.isSpoc}"!="SPOC"){
		$('.empTrackMappingTab,#empTrackMappingDiv,.spocTrackMappingTab,#spocTrackMappingDiv').remove();
	}
	}
	else{
		$('.userRoleTab').attr('id','defaultOpen');
	}
	document.getElementById("defaultOpen").click();
	 $('.ajax-loader').css("visibility", "hidden");
	});
</script>

<div class="user_detail cont  col-xs-12 col-sm-6 col-sm-offset-3 col-md-9 col-md-offset-3 col-lg-8
   col-lg-offset-3" ng-controller="adminController">
  
   <div class="tab" >
 <button class="tablinks userRoleTab" ng-click="MappingPanel($event, 'userRoleDiv')" id="defaultOpen">User Role Mapping</button> 
  <button class="tablinks spocTrackMappingTab" ng-click="MappingPanel($event, 'spocTrackMappingDiv')" >SPOC Track Mapping</button>
  <button class="tablinks empTrackMappingTab" ng-click="MappingPanel($event, 'empTrackMappingDiv')">Employee Track Mapping</button>
</div>

 <div class="tabcontent col-lg-12" id="userRoleDiv">
 <br>
      <div class="col-lg-12 roleDiv">
         <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
            <label class="col-lg-4  col-form-label">Account</label>
            <div class="col-lg-8" id="userRoleAcct">
               <select ng-model="user.accountName" 
                  class="form-control " required  id="accntDrp">
                  <option value=""  selected>Please Select</option>
                  <c:forEach items="${accountList}" var="account" >
                  <option value=${account.id}>${account.accountName}</option>
                  </c:forEach>
               </select>
            </div>
         </div>
         <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
            <label class="col-lg-4  col-form-label">Project</label>
            <div class="col-lg-8">
               <select ng-model="user.project" 
                  class="form-control" required id="accnt_prjct" ng-change="getEmployee(user.project,'${sessionScope.isManager}','${sessionScope.isSuperAdmin}')">
                  <option value=""  selected>Please Select</option>
                  <option  ng-repeat="x in userProject"value={{x.projectId}}>{{x.projectName}}</option>
               </select>
            </div>
         </div>
   </div>
   <div id="emp_table" style="display:none">
   <div style="display:inline-block">
   <span style="color:red">* Note: Please select checkbox to edit and update.</span>
   </div>
      <table id="userEmpRole" class="adminUserEmpRole table table-responsive">
      <thead>
         <tr >
            <th>Select</th>
            <th>Emp ID</th>
            <th>Name</th>
            <th>ADMIN</th>
            <th>SPOC</th>
            <th>FINANCE</th>
            <th>USER</th>
         </tr>
         </thead>
         <tbody>
         <tr ng-repeat="x in empData" id="row{{$index+1}}">
         	<td id={{$index+1}}><input type="checkbox" class="enableRole" name="enableRole" id={{x.empCode}} ng-click="roleCheck($index+1)"   ></td>
            <!-- <td>{{$index+1}}<label hidden>{{x.empId}}</label></td> -->
            <td>{{x.empCode}}</td>
            <td>{{x.empName}}</td>
            <td><input type="checkbox" class="roleCheck admin" ng-model="x.isAdmin" disabled="disabled"></td>
            <td><input type="checkbox" class="roleCheck"ng-model="x.isSpoc" disabled="disabled"></td>
            <td><input type="checkbox" class="roleCheck admin" ng-model="x.isFinance" disabled="disabled" ></td>
             <td><input type="checkbox" class="admin" ng-model="x.isUser" disabled="disabled" ></td>
         </tr>
         </tbody>
      </table>
      
       <table id="userEmpRole" class="managerUserEmpRole table table-responsive">
      <thead>
         <tr >
            <th>Select</th>
            <th>Emp ID</th>
            <th>Name</th>
            <th>SPOC</th>
         </tr>
         </thead>
         <tbody>
         <tr ng-repeat="x in empData" id="row{{$index+1}}">
         	<td id={{$index+1}}><input type="checkbox" class="enableRole" name="enableRole" id={{x.empCode}} ng-click="roleCheck($index+1)"   ></td>
            <!-- <td>{{$index+1}}<label hidden>{{x.empId}}</label></td> -->
            <td>{{x.empCode}}</td>
            <td>{{x.empName}}</td>
            <td><input type="checkbox" class="roleCheck"ng-model="x.isSpoc" disabled="disabled"></td>
         </tr>
         </tbody>
      </table>
      
      <div class="text-center btns col-lg-12 col-sm-12 col-md-12" id="submitRole"  >
         <input type="submit" class="btn btn-md hidden"   value="Update" disabled ng-click="updateUserRole('${sessionScope.isManager}','${sessionScope.isSuperAdmin }')"  />
      </div>
       <div class="text-center roleAlert {{class}} col-lg-offset-4 col-lg-4" ><b>{{roleAlertStatus}}</b></div>
   </div>
   </div>


   <div class="tabcontent col-lg-12" id="spocTrackMappingDiv">
   <br>
      <div class="col-lg-12 roleDiv">
         <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
            <label class="col-lg-4  col-form-label">Account</label>
            <div class="col-lg-8" id="spocTrackAcct">
               <select ng-model="spocTrack.accountName" 
                  class="form-control " required  id="accntDrp">
                  <option value=""  selected>Please Select</option>
                  <c:forEach items="${accountList}" var="account" >
                  <option value=${account.id}>${account.accountName}</option>
                  </c:forEach>
               </select>
            </div>
         </div>
         <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
            <label class="col-lg-4  col-form-label">Project</label>
            <div class="col-lg-8">
               <select ng-model="spocTrack.project"  
                  class="form-control" required id="accnt_prjct">
                  <option value="" selected >Please Select</option>
                  <option ng-repeat="x in spocTrackProject"value={{x.projectId}}>{{x.projectName}}</option>
 
               </select>
            </div>
      </div>
      </div>
   
      <div id="spocTrackTable" style="display:none">
      <div style="display:inline-block">
   <span style="color:red">* Note: Please select checkbox to map Track to SPOC</span>
   </div>
      <table id="trackSpocMapping" class="table table-responsive">
      <thead>
         <tr >
           <th>Select</th>
           <th>Emp ID</th>
           <th>Name</th>
         </tr>
         </thead>
         <tbody>
         <tr ng-repeat="x in spocData" id="row{{$index+1}}">
         	<td id={{$index+1}}><input type="checkbox" class="enableSpoc" name="enableSpoc" id={{x.empCode}} ng-click="spocCheck($index+1)"   ></td>
            <!-- <td>{{$index+1}}<label hidden>{{x.empId}}</label></td> -->
            <td>{{x.empCode}}</td>
            <td>{{x.empName}}</td>
            <!-- <td ng-repeat="(key, value) in x.trackDetails">{{key}}</td> -->
            <td ng-repeat="(key, value) in x.isEmpMapToTrack"><input type="checkbox" class="spocCheck" ng-model="x.isEmpMapToTrack[key]" disabled="disabled"></td>
         </tr>
         </tbody>
      </table>
       
      <div class="text-center btns col-lg-12 col-sm-12 col-md-12" id="submitSpoc" >
         <input type="submit" class="btn btn-md hidden"  value="Update" disabled ng-click="updatespocTrack()"  />
      </div>
      <div class="text-center roleAlert {{class}} col-lg-offset-4 col-lg-4" ><b>{{roleAlertStatus}}</b></div>
   </div>
   </div>
    <div class="tabcontent col-lg-12" id="empTrackMappingDiv">
   <br>
      <div class="col-lg-12 roleDiv">
         <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
            <label class="col-lg-4  col-form-label">Account</label>
            <div class="col-lg-8" id="empTrackAcct">
               <select ng-model="empTrack.accountName" 
                  class="form-control " required  id="accntDrp">
                  <option value=""  selected>Please Select</option>
                  <c:forEach items="${accountList}" var="account" >
                  <option value=${account.id}>${account.accountName}</option>
                  </c:forEach>
               </select>
            </div>
         </div>
         <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
            <label class="col-lg-4  col-form-label">Project</label>
            <div class="col-lg-8">
               <select ng-model="empTrack.project" 
                  class="form-control" required id="accnt_prjct">
                  <option value=""  selected>Please Select</option>
                  <option ng-repeat="x in empTrackProject"value={{x.projectId}}>{{x.projectName}}</option>
 
               </select>
            </div>
         </div>
      </div>
   
      <div id="empTrackTable" style="display:none">
      <div style="display:inline-block">
   <span style="color:red">* Note: Please select checkbox to map Employee to Track</span>
   </div>
      <table id="empTrackMapping" class="table table-responsive">
     <thead>
     	 <tr >
           <th>Select</th>
           <th>Emp ID</th>
           <th>Name</th>
         </tr>
        </thead>
         <tbody>
         <tr ng-repeat="x in empTrackData" id="row{{$index+1}}" class="{{$index}}">
         	<td id={{$index+1}}><input type="checkbox" class="enableUser" name="enableUser" id={{x.empCode}} ng-click="empTrackChk($index+1)"   ></td>
            <td>{{x.empCode}}</td>
            <td>{{x.empName}}</td>
            <td ng-repeat="(key, value) in x.isEmpMapToTrack"><input type="checkbox" class="empTrackChk" value={{key}} ng-model="x.isEmpMapToTrack[key]" ng-change="empTrackChange(this)" disabled="disabled"></td> 
          <!--  <td ng-repeat="(key, value) in x.isEmpMapToTrack"><input type="checkbox" class="empTrackChk" ng-model="x.isEmpMapToTrack[key]" disabled="disabled"></td> -->
         <!-- <td ng-repeat="(key, value) in x.isEmpMapToTrack"><input type="radio" name="isEmpMapToTrack{{x.empCode}}" class="empTrackChk" value="{{key}}" ng-model="x.trackName" disabled="disabled"></td> -->
         </tr>
         </tbody>
      </table>
      
      <div class="text-center btns col-lg-12 col-sm-12 col-md-12" id="submitTrackEmp" >
         <input type="submit" class="btn btn-md hidden"  value="Update" disabled ng-click="updateEmpTrack()"  />
      </div>
       <div class="text-center roleAlert {{class}} col-lg-offset-4 col-lg-4" ><b>{{roleAlertStatus}}</b></div>
   </div>
   </div>
   <jsp:include page="modalPopup.jsp" />
   </div>	
