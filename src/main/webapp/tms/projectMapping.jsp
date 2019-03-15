<jsp:include page="header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="/tmsApp/js/app/adminController.js"></script>
<script src="/tmsApp/js/app/adminService.js"></script>
<script src="${pageContext.request.contextPath}/js/lib/jquery.sumoselect.min.js"></script>
<link href="${pageContext.request.contextPath}/css/sumoselect.min.css" rel="stylesheet" />
<script type="text/javascript">
   $(document).ready(function () {
   	$('.adminSide').addClass('active');
    	$('.adminSide .projMapping').addClass('active');
       window.sb = $('.SlectBox-grp-src').SumoSelect({ csvDispCount: 3, search: true, searchText:'Enter here.', selectAll:false });
       $('.SlectBox').on('sumo:opened', function(o) {
         console.log("dropdown opened", o)
       });
       //$('.SumoSelect').addClass('form-control');
   });
</script>
<style>
  /*  .SumoSelect .form-control{
   padding:0px !important;
   line-height:21px !important;
   }
   */
</style>
<div class="user_detail projMapCont    col-xs-12 col-sm-10 col-sm-offset-1 col-md-9 col-md-offset-3 col-lg-8
   col-lg-offset-3" ng-controller="adminController">
   <div class=" panel panel-default" ng-init="getEmpList()">
      <div class="panel-heading faq-links"><b>Employee Project Mapping</b>
      </div>
      <div class=" panel-body">
         <div class="form-group row col-sm-12 col-lg-6  col-md-6">
            <label class="col-lg-4 col-form-label">Employee Name</label>
            <div class="col-lg-8 has-clear has-feedback">
               <span class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden" id="empClear"></span>
               <input  list="projectEmployeesList"  name="requestedEmpList"  class="form-control" />
               <datalist id="projectEmployeesList">
                  <option ng-repeat="x in projEmpList" id="{{x.id}}" value="{{x.name}}" ng-model="employeID"></option>
               </datalist>
            </div>
         </div>
         <div class="form-group row col-sm-12 project col-lg-6  col-md-6 hidden">
            <label class="col-lg-4 col-form-label">Project</label>
            <div class="form-group  col-lg-8">
               <select multiple="multiple" placeholder="Select Project" class="SlectBox-grp-src form-control projectSelect">
                  <c:forEach items="${projectList}" var="account" varStatus="status">
                     <optgroup label="${account.key}">
                        <c:forEach items="${account.value}" var="project" varStatus="status">
                           <option id="${project.projectId }" value="${project.projectId}">${project.projectName}</option>
                        </c:forEach>
                     </optgroup>
                  </c:forEach>
               </select>
            </div>
         </div>
         <div class="text-center projMapAlert {{class}} col-lg-offset-4 col-lg-4" style="display:none"><b>{{projMapAlertStatus}}</b></div>
         <div class="text-center saveBtn col-lg-12" hidden>
         <input type="button" class="btn" ng-click="saveSelectedProject()" value="Save">
         </div>
      </div>
   </div>
</div>
</body>
</html>