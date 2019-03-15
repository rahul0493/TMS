<jsp:include page="header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<script src="/tmsApp/js/app/spocController.js"></script>
<script src="/tmsApp/js/app/spocService.js"></script>
<style>

/* #trackEditDiv input {
    
    padding: 12px 20px;
    margin: 4px 0;
   	border:none !important;
    border-bottom: 1px solid black !important;
    border-radius: 1px !important;
    outline:none !important;
} */

.fa:hover{
	cursor:pointer;
}
   .panel-group .panel+.panel{
   margin-top:0px !important;
   }
   table td input{
   text-align:center;
   }
 /*   .trackEditDiv .form-control{
    background: #ececec;
    pointer-events: none;
   } */
   .panel-heading{
   border-bottom: 1px solid !important;
   border-bottom-width: thin !important;
   border-color: white !important;
   }
   .disabled{
    pointer-events:none;
    opacity:0.4;
}
</style>
 <div class="user_detail hidden cont  col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-md-9 col-md-offset-3 col-lg-8
   col-lg-offset-3" ng-controller="spocController" >
 <%--<div class="panel-group col-lg-12"  id=accordion >
   <div class=" panel panel-default ">
      <div class="panel-heading "><b>Create Project Tracker</b></div>
      <div class="panel-body" id="">
      <form ng-submit="sub('createTrack')">
      <div class="col-lg-6 col-md-12 col-sm-12">
         <div class="form-group row col-sm-12 col-xs-12 col-lg-12 col-md-12">
            <label class="col-lg-4  col-form-label">Account</label>
            <div class="col-lg-8">
               <select ng-model="tracker.accountName" 
                  class="form-control" required  id="trackAccntDrp">
                     <option value=""  selected>Please Select</option>
                     <!--   <option value="1"  selected>Amway</option>
                         <option value="2"  selected>WM</option> -->
                  <c:forEach items="${accountList}" var="account" >
                  <option id="${account.id}" value="${account.accountName}">${account.accountName}</option>
                  </c:forEach>
               </select>
            </div>
         </div>
         <div class="form-group row col-sm-12 col-xs-12 col-lg-12 col-md-12">
            <label class="col-lg-4  col-form-label">Project</label>
            <div class="col-lg-8">
               <select ng-model="tracker.projectName" 
                  class="form-control" required id="trackPrjctDrp" disabled>
                  <option value=""  selected>Please Select</option>
                 <!--  <option value="1"  selected>Amway-CQ5</option>
                         <option value="2"  selected>Amway-Europe</option> -->
                 <option ng-repeat="x in project" value={{x.projectName}} id="{{x.projectId}}">{{x.projectName}}</option> 
               </select>
            </div>
            &nbsp;&nbsp;
            <i><br> <a href="/tmsApp/tms/project/project_details"><b>Click Here!</b> to go back to Shift Master</a> </i>
         </div>
          </div>
      <div class="col-lg-4 col-lg-offset-1 col-md-12 col-sm-12">
      <table id="track" hidden>
     	<tr id="{{$index+1}}" ng-repeat="x in track">
     	<td><label>{{$index+1}}</label></td>
     	<td><input type="text" class="form-control" autofocus id="trck{{$index}}" ng-model="track[$index].trackName" placeholder="Enter Track Name" autocomplete="off"  maxlength="25" required /></td>
     	<td><i class="fa fa-trash-o fa-lg trackRem{{$index}}" aria-hidden="true" ng-click="removeTrack(track,$index)"></i></td>
     	</tr>
     	<tr><td></td><td><span><i class="fa fa-plus-circle fa-md" id="add_proj_btn1" aria-hidden="true" ng-click="addTracker(track)" style="cursor:pointer;">Add Tracker</i></span></td></tr>
      </table><br> 
     <div class="col-lg-12 trackBtns hidden text-center">
       <input type="submit" class="btn btn-sm" value="Submit" />
       <input type="button" class="btn btn-sm" value="Cancel" ng-click="trckCancel()" />
       </div>
      </div>
      </form>
      </div>
   </div>  --%>
   <br>
    <div class="trackEditDiv" data-ng-init="getTrackOnLoad()">
         <%--   <c:forEach items="${projectBeanList}" var="project" varStatus="status"> --%>
         <accordion close-others="true" >
             <accordion-group  is-open="isOpen" ng-repeat="(key,value) in tracker1" id="{{key}}"> 
             <accordion-heading> 
             <div>
                     <i class="fa fa-globe"></i>
                     <strong>{{key}}</strong>
                     <i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': isOpen, 'glyphicon-chevron-left': !isOpen}"></i>
                  </div>
               </accordion-heading> 
             <div>
           <accordion close-others="true" >
             <accordion-group  is-open="isOpen" ng-repeat="x in value" id="accor{{$index}}"> 
             <form ng-submit="trackSave($index,key)">
             <accordion-heading> 
                  <div>
                     <i class="fa fa-info-circle "></i>
                     <strong>{{x.projectName}}</strong>
                     <i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': isOpen, 'glyphicon-chevron-left': !isOpen}"></i>
                  </div>
               </accordion-heading> 
          <div class="col-lg-offset-1 col-lg-11">
       <div class="form-group row col-sm-12 col-lg-6 col-md-6 " id="track" ng-repeat="track in x.trackDetails">
            <label  class="col-lg-2 col-form-label">{{$index+1}}</label>
            <div class="col-lg-8" style="display:-webkit-inline-box">
               <input type="text" class="form-control trckInput" ng-model="track.trackName" placeholder="Enter Track Name" autocomplete="off" required />&nbsp;
               <i class="fa fa-trash-o fa-lg" aria-hidden="true"  ng-click=removeTrack1(x.trackDetails,track.trackDetailsId,$index,key,x)></i>
              </div>
         </div>
         <div class="col-lg-12">
         <span><i class="fa fa-plus-circle fa-md"  id="add_proj_btn1" aria-hidden="true" ng-click="addTracker1($index,key)" style="cursor:pointer;"> Add Track</i></span>
     </div>
     <div class="col-lg-12">
     <div class="text-center trackAlert {{class}} col-lg-offset-4 col-lg-4" style="display:none"><b>{{trackAlertStatus}}</b></div>
     </div>
      </div>
               <div class="text-right col-lg-12" >
                <!-- <input type="button" class="btn btn-sm trackEdit" value="Edit" ng-click="trackEdit($index)"/> -->
                  <input type="submit" class="btn btn-sm trackSave "   value="Save"/>
                     <input type="button" class="btn btn-sm trackCancel" style="display:none" value="Cancel" ng-click="trackEditCancel($index,key)"/>
               </div>
                
            </form>
             </accordion-group> 
  </accordion>
  </div>
   </accordion-group> 
  </accordion>
         <%--  </c:forEach> --%> 
      </div>
   </div>
      <jsp:include page="modalPopup.jsp" />
   </div>
   <script>
   $(document).ready(function(){
	$('.masterSide').addClass('active');
	$('.trackList').addClass('active');
});
</script>