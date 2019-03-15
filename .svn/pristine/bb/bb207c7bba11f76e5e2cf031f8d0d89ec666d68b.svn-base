<jsp:include page="header.jsp" />
<script src="/tmsApp/js/app/spocController.js"></script>
<script src="/tmsApp/js/app/spocService.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
   .panel-group .panel+.panel {
   margin-top: 0px !important;
   }
   #track td{
   padding:3px;
   text-align:center;
   }
    #track td input{
    text-align:center;
    }
   .shiftTime .fa-trash-o{
    padding:5px;
    background: #81c8e8;
    border-radius: 5px;
   }
  .shiftTime table .fa:hover {
  background-color: rgb(26, 58, 74)  !important;
   color:white;
   border-radius: 5px;
   box-shadow: 4px 4px #999;
   }
   .shiftTime table .fa:active {
   background-color: #3e8e41;
   box-shadow: 2px 2px #666;
   transform: translateY(2px);
   }
   
   .shiftTime th,accordion th,accordion td,
   .shiftTime td
   {
   background-color: #eaecec;
   text-align: center;
   border:1px solid #bbc1ca;
   }
   .shiftTime td,accordion td,accordion th,
   .shiftTime th {
   text-align: center !important;
   font-size: 14px;
   padding: 4px !important;
   }
   .shiftTime th,accordion th{
   background-color:#b4dbec;
   border-bottom: 1px solid #1a3a4a !important 
   }
   .shiftTime td,accordion td{
   background-color:#f1f1f1;
   }
   
   .panel-heading {
   border-bottom: 1px solid !important;
   border-bottom-width: thin !important;
   border-color: white !important;
   }
   .btn .pickerBtn{
   background:transparent;
   }
   
</style>
<div  class="Project_shift hidden cont col-xs-12  col-sm-6 col-sm-offset-3 col-md-9 col-md-offset-3 col-lg-8 col-lg-offset-3" ng-controller="spocController">
   <div class="panel-group col-lg-12" id=accordion>
   <input type="hidden" id="rolesList"  value="${authorities}">
      <div class="panel panel-default " id=project_panel>
         <div class="panel-heading" >
            <h4 class="panel-title"><b>Project and Shift Details</b></h4>
         </div>
         <div id=collapse1 class="panel-collapse collapse in">
         <form ng-submit="createProjectShift()">
            <div class="panel-body" ng-init="getProjectShiftDetails()">
               <div class="form-group row col-sm-12 col-lg-6 col-md-6">
                  <label class="col-lg-4 col-form-label">Account Name</label>
                  <div class="col-lg-8">
                     <select ng-model="project.accountName" id="project_accntDrp"
                        name="accountName" class="form-control" required>
                        <option value="" disabled selected>Select Account</option>
                        <c:forEach items="${accountList}" var="account"
                           varStatus="status">
                           <option id="${account.id}" value="${account.accountName}">${account.accountName}</option>
                        </c:forEach>
                     </select>
                  </div>
               </div>
               <div class="form-group row col-sm-12 col-lg-6 col-md-6 projectDetails"
                  ng-show="projectDetails">
                  <label class="col-lg-4 col-form-label">Project Name</label>
                  <div class="col-lg-8">
                     <select ng-model="project.projectName" id="projectname"
                        name="projectname" class="form-control" required>
                        <option value="" disabled selected>Select Project</option>
                        <option ng-repeat="x in projectList" id="{{x.projectId}}"
                           value="{{x.projectName}}">{{x.projectName}}</option>
                     </select>
                  </div>
               </div>
                <div class="form-group row col-lg-6 trackRequires"
                  ng-show="trackRequires">
                  <label class="col-lg-12 col-form-label" style="color:red">* Selected Project's Track List is Empty</label> 
                 <!--  <div class="col-lg-12"> -->
                     <span>&nbsp;<input type="radio" name="trackRequires" value="yes"    ng-model="trackY"   ng-click="checktrack('yes',project.projectName,'${authorities}')" > <b>Add New Track</b></span>
                     <span>&nbsp;<input type="radio"    name="trackRequires" value="no"  ng-model="ytackY"  ng-click="checktrack('no',project.projectName)"> <b>Continue without Track</b></span>
                  <!-- </div> -->
               </div> 
                <div class="form-group row col-sm-12 col-lg-6 col-md-6 trackerDetails"
                  ng-show="trackerDetails">
                  <label class="col-lg-4 col-form-label">Track Name</label>
                  <div class="col-lg-8">
                     <select ng-model="project.trackDetails.trackName" id="trackDrp"
                        name="trackName" class="form-control" required>
                        <option value="" disabled selected>Select Track</option>
                        <option ng-repeat="x in trackList" id="{{x.trackDetailsId}}"
                           value="{{x.trackName}}">{{x.trackName}}</option>
                     </select>
                  </div>
                  <div class="form-group row col-lg-12"
                  ng-show="addTrack">
                      &nbsp;&nbsp;<i><br> <a ng-click="createNewShiftTrack()">If you
                  want to add Track? Click here!</a> </i>
               </div> 
               </div>
               <div class="form-group row col-sm-12 col-lg-6 col-md-6 " ng-show="newTrack==true">
                  <label class="col-lg-4 col-form-label"></label>
                  <div class="col-lg-8">
                  	<table id="track" class="text-center">
                     <tr id="{{$index+1}}" ng-repeat="x in trackProject">
                        <td><label>{{$index+1}}</label></td>
                        <td><input type="text" class="form-control newTrack" autofocus id="trck{{$index}}" maxlength="20" ng-model="trackProject[$index].trackName" placeholder="Enter Track Name"/>
                        </td>
                        <td><i class="fa fa-trash-o fa-lg trackRem{{$index}}" aria-hidden="true" ng-click="removeShiftTrack(trackProject,$index)"></i>
                     	<label id="trckErr{{$index}}" class="trckErr" style="color:red;display:none">{{trckAlert}}</label></td>
                     </tr>
                     <tr>
                        <td></td>
                        <td><span><i class="fa fa-plus-circle fa-md" id="add_proj_btn1" aria-hidden="true" ng-click="addTrackerProject()" style="cursor:pointer;">&nbsp;Add Track</i></span></td>
                     </tr>
                  </table>
                  <div class="col-lg-12 trackBtns text-center">
                     <input type="button" class="btn btn-sm" value="Submit"  ng-click="sub('shiftTrack')"/>
                       <input type="button" class="btn btn-sm" value="Cancel"  ng-click="shiftTrackCan()"/>
                  </div>
                  </div>
               </div> 
               
               <div ng-show="shiftDetail" class="shiftDetail">
                  <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                     <label class="col-lg-4  col-form-label">Shift</label>
                     <div class="col-lg-8">
                        <select ng-model="shiftName" id="prjct_shift" name="prjct_shift"
                           class="form-control" >
                           <option value="" disabled selected>Select Shift</option>
                           <c:forEach items="${shiftList}" var="shift" varStatus="status">
                              <option id="${shift.shiftId}"
                                 value="${shift.shiftName}, ${shift.shiftInitials}">${shift.shiftName}&nbsp;&nbsp;(${shift.shiftInitials})</option>
                           </c:forEach>
                        </select>
                     </div>
                  </div>
                  <div class="shiftTime col-lg-10 col-lg-offset-1" ng-show="shiftTime">
                     <div
                        class="form-group row col-sm-12 col-xs-12 col-lg-offset-1 col-lg-10 col-md-6">
                        <table>
                        <thead>
                           <tr>
                              <th>ID</th>
                              <th>Shift</th>
                              <th>Start Time</th>
                              <th>End Time</th>
                              <th>Pickup</th>
                              <th>Drop</th>
                              <th style="border-style: hidden;background: transparent"></th>
                              <!-- <th>Delete</th> -->
                           </tr>
                           </thead>
                           <tbody>
                           <tr ng-repeat="x in shiftDetails track by $index" >
                              <td>{{x.shiftId}}</td>
                              <td id="shiftInit{{$index}}">{{x.shiftInitials}}</td>
                              <td id=''>
                                 <div class=' date' >
                                    <input type='text' id="startTime_{{$index}}" class="form-control startTime {{x.shiftInitials}}" 
                                       ng-model="shiftDetails[$index].startTime" onkeypress="return false" required datetimepicker autocomplete="off" />
                                 </div>
                              </td>
                              <td id=''>
                                 <div class=' date'>
                                    <input type='text' id="endTime_{{$index}}" class="form-control endTime {{x.shiftInitials}}" 
                                       ng-model="shiftDetails[$index].endTime" onkeypress="return false"  required datetimepicker autocomplete="off" /> 
                                 </div>
                              </td>
                              <td><input type="checkbox" name="pickup" ng-model="shiftDetails[$index].pickup" ></td>
                              <td><input type="checkbox" name="drop" ng-model="shiftDetails[$index].drop" ></td>
                              <td style="border-style: hidden;background: transparent;padding:4px !important"><span><i class="fa fa-trash-o fa-lg hidden" aria-hidden="true" id="del_{{x.shiftId}}"  ng-click="removeShift(shiftDetails,$index,x.shiftDetailsId)"></i></span></td>
                           </tr>
                           </tbody>
                        </table>
                         <div style="padding-top: 10px;color: red;">
                      <span>* To add another shift, please choose from 'Shift' dropdown</span>
                      </div>
                     </div>
                     <div class="text-center col-lg-12">
                      <div class="">
               						<label id="err" style="color:red;display:none">{{alert}}</label>
               				  </div>
                        <input type="submit" class="btn btn-md " value="Submit"
                           id="submit" />
                     </div>
                  </div>
               </div>
            </div>
           </form>
         </div>
      </div>
      <br>
      <div class="projectShiftDetail">
       <accordion close-others="true" >
             <accordion-group  is-open="isOpen" ng-repeat="(key,value) in projectShiftDetails" id="{{key}}"> 
             <accordion-heading> 
             <div>
                     <i class="fa fa-globe"></i>
                     <strong>{{key}}</strong>
                     <i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': isOpen, 'glyphicon-chevron-left': !isOpen}"></i>
                  </div>
               </accordion-heading> 
            <accordion close-others="true">
               <accordion-group is-open="isOpen" ng-repeat="x in value">
                  <accordion-heading>
                     <div>
                        <i class="fa fa-sitemap"></i> <strong>
                        {{x.projectName}} </strong> <i class="pull-right glyphicon"
                           ng-class="{'glyphicon-chevron-down': isOpen, 'glyphicon-chevron-left': !isOpen}"></i>
                     </div>
                  </accordion-heading>
                  <div>
                        <accordion close-others="true">
                           <accordion-group is-open="isOpen" ng-repeat="y in x.trackDetails">
                              <accordion-heading>
                                 <div>
                                    <i class="fa fa-info-circle"></i> <strong>
                                    {{y.trackName}} <input type="text" class="accordionTrack hidden"></strong> <i class="pull-right glyphicon"
                                       ng-class="{'glyphicon-chevron-down': isOpen, 'glyphicon-chevron-left': !isOpen}"></i>
                                 </div>
                              </accordion-heading>
                              <div>
                              <br>
                                 <table>
                                    <tr>
                                       <th>ShiftID</th>
                                       <th>ShiftName</th>
                                       <th>Start Time</th>
                                       <th>End Time</th>
                                       <th>Pickup</th>
                                       <th>Drop</th>
                                    </tr>
                                       <tr id="${x.projectId}" value="{{x.projectName}}" ng-repeat="z in y.shiftDetails">
                                          <td>{{z.shiftId}}</td>
                                          <td>{{z.shiftInitials}}</td>
                                          <td>{{z.startTime}}</td>
                                          <td>{{z.endTime}}</td>
                                          <td><input type="checkbox" name="pickup" ng-model="z.pickup" disabled></td>
                                          <td><input type="checkbox" name="drop" ng-model="z.drop" disabled></td>
                                       </tr>
                                 </table>
                              </div>
                              <div class="text-center">
                                 <input type="button" class="btn btn-sm" value="Edit Shift" ng-click="editProjectShift(x.projectId,x.accountId,y.trackDetailsId,'edit')"/>
                              </div>
                           </accordion-group>
                        </accordion>
                  </div>
               </accordion-group>
            </accordion>
             </accordion-group>
            </accordion>
      
      <div class="modal fade" id="shiftModal" role="dialog">
         <div class="modal-dialog ">
            <div class="modal-content">
              <form ng-submit="updateProjectShift('update')" >
               <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                  <h4 class="modal-title">Shift Details</h4>
               </div>
               <div class="modal-body col-lg-12" id="eidtShiftModal">
                  <div class=" form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                     <label class="col-lg-4  col-form-label">Shift</label>
                     <div class="col-lg-8">
                        <select ng-model="shiftDetails.shiftName" id="prjct_shift1"
                           name="prjct_shift1" class="form-control"
                           ng-change="check_type()" >
                           <option value="" disabled selected>Select Shift</option>
                           <c:forEach items="${shiftList}" var="shift" varStatus="status">
                              <option id="${shift.shiftId}"
                                 value="${shift.shiftName}, ${shift.shiftInitials}">${shift.shiftName}</option>
                           </c:forEach>
                        </select>
                     </div>
                  </div>
                  <div
                     class="shiftTime form-group row col-sm-12 col-xs-12 col-lg-offset-1 col-lg-10  col-md-6">
                     <table>
                     <thead>
                        <tr>
                           <th>ID</th>
                           <th>Shift</th>
                           <th>Start Time</th>
                           <th>End Time</th>
                           <th>Pickup</th>
                           <th>Drop</th>
                           <th style="border-style: hidden;background: transparent"></th>
                           <!-- <th >Delete</th> -->
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="x in shiftDetails track by $index">
                           <td>
                              <!-- {{x.shiftId}} -->
                              <label ng-model="shiftDetails[$index].shiftId">{{x.shiftId}}</label>
                           </td>
                           <td><label ng-model="shiftDetails[$index].shiftInitials">{{x.shiftInitials}}</label></td>
                           <td id=''>
                              <div class='date' >
                                 <input type='text' class="form-control startTime {{x.shiftInitials}}" id="startTime_{{$index}}"  
                                    ng-model="shiftDetails[$index].startTime" onkeypress="return false" required autocomplete="off" datetimepicker  /> 
                              </div>
                           </td>
                           <td id=''>
                              <div class='date' >
                                 <input type='text' class="form-control endTime {{x.shiftInitials}}" id="endTime_{{$index}}" 
                                    ng-model="shiftDetails[$index].endTime" onkeypress="return false" required  autocomplete="off" datetimepicker  /> 
                              </div>
                           </td>
                           <td><input type="checkbox" name="pickup" ng-model="x.pickup" data-value=false> </td>
                           <td><input type="checkbox" name="drop" ng-model="x.drop" data-value=false> </td>
                           <td style="border-style: hidden;background: transparent;padding:4px !important"><span><i class="fa fa-trash-o fa-lg hidden" aria-hidden="true" id="del_{{x.shiftId}}"
                              ng-click="removeShift(shiftDetails,$index,x.shiftDetailsId)"></i></span></td>
                        </tr>
                        </tbody>
                     </table>
                     <div style="padding-top: 10px;color: red;">
                     <span>* To Add Shift please select fron Shift Dropdown</span>
                      </div>
                  </div>
               </div>
               <div class="modal-footer">
               <div class="text-center">
               <label id="err" style="color:red;display:none">{{alert}}</label>
               </div>
                  <input type="submit" class="btn btn-md " value="Update"/>
                  <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
               </div>
                 </form>
            </div>
         </div>
      </div>
      </div>
      <jsp:include page="modalPopup.jsp" />
   </div>
</div>
<script>
   $(document).ready(function(){
	   $('.datetimepicker-hours .hour span').removeClass('active');
   //$('.Project_shift').addClass('hidden');
   $('.ajax-loader').css("visibility", "visible");
   $('.masterSide').addClass('active');
	$('.shiftList').addClass('active');
   });
</script>