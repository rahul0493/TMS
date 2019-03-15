<jsp:include page="header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="/tmsApp/js/app/adminController.js"></script>
<script src="/tmsApp/js/app/adminService.js"></script>
<style>
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
$('.adminSide').addClass('active');
$('.adminSide .EmpList').addClass('active');
$('#allEmpTable').DataTable({
	"pageLength": 25
});
});

</script>

<div class="user_detail   col-xs-12 col-sm-6 col-sm-offset-3 col-md-9 col-md-offset-3 col-lg-8
   col-lg-offset-3" ng-controller="adminController">
   <div>
   <h3><b>Employee List</b></h3>
   </div>
      <div id="allEmp" >
      <table id="allEmpTable" class="table table-responsive">
      <thead>
         <tr >
            <th>Emp ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Gender</th>
            <th>Designation</th>
            <th>Mobile</th>
            <th>Manager Email</th>
         </tr>
         </thead>
         <tbody>
         <c:forEach items="${empList}" var="employee" varStatus="status">
         <tr>
           <td>${employee.empcode}</td>
           <td>${employee.name}</td>
           <td>${employee.email}</td>
           <td>${employee.gender}</td>
           <td>${employee.empDesignation}</td>
           <td>${employee.phoneNumber}</td>
           <td>${employee.managerEmail}</td>         
          </c:forEach>
         </tbody>
      </table>      
   </div>
   </div>