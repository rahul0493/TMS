<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
   <head>
      <title>Quinnox TMS</title>
      <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/images/car_favicon.png" />
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.min.css ">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar-menu.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/datetimepicker.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.timepicker.min.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.dataTable.min.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/buttons.dataTables.min.css">
      <%--   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select2.min.css"> --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sumoselect.min.css"> 
      <style>
         /* Let's get this party started */
         ::-webkit-scrollbar,::-ms-scrollbar {
         width: 10px;
         height: 10px;
         }
         /* Track */
         ::-webkit-scrollbar-track,::-ms-scrollbar-track {
         -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
         -webkit-border-radius: 10px;
         -ms-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
         -ms-border-radius: 10px;
         border-radius: 10px;
         }
         /* Handle */
         ::-webkit-scrollbar-thumb,::-ms-scrollbar-thumb {
         border-radius: 10px;
         background: #ddaf2e !important;
         -webkit-border-radius: 10px;
         -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.5);
         -ms-border-radius: 10px;
         -ms-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.5);
         }
         
          .has-feedback .form-control {
         padding-right: 10px !important;
         }
         
         body {
         padding-left: 0px !imporatnt;
         padding-right: 0px !important;
         }
         .quinnox_logo {
         display: initial;
         }
         .navbar-nav>.role a:hover {
         cursor: default !important;
         color: white !important;
         }
         #ui-datepicker-div {
         z-index: 10000 !important;
         }
         .ui-timepicker-container {
         z-index: 10000 !important;
         }
         .ui-datepicker-calendar td,
         .ui-datepicker-calendar th {
         padding: 3px !important;
         }
         input::-webkit-calendar-picker-indicator {
         opacity: 0;
         }
         a:focus {
         outline: none !important;
         }
         .modal-footer {
         padding: 5px !important;
         }
         .myScroll {
         height: 250px !important;
         margin: 10pt auto;
         border: 1pt solid #CCC;
         }
         
         .tmsBody ::-webkit-scrollbar-track {
     -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
     background-color: white;
}
 .tmsBody ::-webkit-scrollbar {
     width: 7px;
     height:7px;
     background-color: white;
}
 .tmsBody ::-webkit-scrollbar-thumb {
     cursor: pointer;
     background-color: #ddaf2e;
}
      </style>
   </head>
   <body ng-app="tmsApp" class="tmsBody">
      <div class="ajax-loader">
         <img src="/tmsApp/images/loading-large.gif" class="img-responsive" />
      </div>
      <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="margin-bottom: 0">
         <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-menu">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            </button>
            <button type="button" class="toggleSidenav hidden">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            </button>
            
            <span>
               <!--   <i class="fa fa-align-justify fa-2x" ></i> -->
               <a href="http://www.quinnox.com" target="_blank"><img src="${pageContext.request.contextPath}/images/QCSS logo-01.png" style="width:160px" class="quinnox_logo img-responsive"/></a>
               <!-- <a class="navbar-brand" href="dashboard.htm"><b>Q</b>Focus</a> -->
            </span>
         </div>
         <div>
            <ul class="nav navbar-nav navbar-right ">
               <li class="user"><a href="${pageContext.request.contextPath}/tms/employee/addEmployee"><span class="glyphicon glyphicon-user"></span> ${sessionScope.userName}</a></li>
               <li class="logout role">
                  <a href="">
                     |&nbsp;
                     
                     <c:choose>
                     <c:when test="${sessionScope.isSuperAdmin eq 'SUPER_ADMIN'}">
        				SUPER ADMIN
    					</c:when>    
    					<c:otherwise>
				       	 <c:choose>
    					<c:when test="${sessionScope.isFrontDesk eq 'FRONT_DESK'}">
        				FRONT DESK
    					</c:when>    
    					<c:otherwise>
				       	 <c:choose>
                        <c:when test="${sessionScope.isAdmin eq 'ADMIN'}"> FLM</c:when>
                        <c:otherwise>
                           <c:choose>
                              <c:when test="${sessionScope.isManager eq 'SENIOR_MANAGER'}">
                                 SENIOR MANAGER
                              </c:when>
                              <c:otherwise>
                                 <c:choose>
                                    <c:when test="${sessionScope.isSpoc eq 'SPOC'}">
                                       SPOC
                                    </c:when>
                                    <c:otherwise>USER</c:otherwise>
                                 </c:choose>
                              </c:otherwise>
                           </c:choose>
                        </c:otherwise>
                     </c:choose>
                     </c:otherwise>
                     </c:choose>
				    	</c:otherwise>
					</c:choose>
                    
                  </a>
               </li>
               <li class=" logout"><a href="" onclick="return logout();"><span class="glyphicon glyphicon-off "></span> Logout</a></li>
            </ul>
         </div>
         <div class="nav-side-menu ">
         
         ${sessionScope.isFrontDesk}
          
            <ul class="sidebar-menu collapse out ">
            <c:if test="${sessionScope.isFrontDesk ne 'FRONT_DESK' || sessionScope.isAuperAdmin eq 'SUPER_ADMIN'}">
               <li class="dashboardSide">
                  <a href="${pageContext.request.contextPath}/tms/home">
                  <i class="fa fa-dashboard fa-lg"></i> <span>Dashboard</span>
                  </a>
               </li>
               <li class="adhocSide">
                  <a href="#">
                  <i class="fa fa-car fa-lg"></i> <span>Cab Request</span> <i class="fa fa-angle-right pull-right"></i>
                  </a>
                  <ul class="sidebar-submenu">
                     <li class="createList">
                        <a href="${pageContext.request.contextPath}/tms/adhocRequest/adhoc">
                        <i class="fa fa-id-card-o"></i><span> Create Adhoc Request<span>
                        </a>
                     </li>
                     <li class="reportList"><a href="${pageContext.request.contextPath}/tms/adhocRequest/cabRequestHistoryPage">
                        <i class="fa fa-history" ></i><span> Report<span>
                        </a>
                     </li>
                  </ul>
               </li>
                </c:if>
               <c:if test="${sessionScope.isSpoc eq 'SPOC' || sessionScope.isSuperAdmin eq 'SUPER_ADMIN'}">
                  <li class="monthySide">
                     <a href="${pageContext.request.contextPath}/tms/shiftPlanner/home" target="_blank">
                     <i class="fa fa-calendar fa-lg"></i> <span>Shift Planner</span>
                     </a>
                  </li>
               </c:if>
               <c:if test="${sessionScope.isSpoc eq 'SPOC' || sessionScope.isManager eq 'SENIOR_MANAGER' || sessionScope.isSuperAdmin eq 'SUPER_ADMIN'}">
                  <li class="masterSide">
                     <a href="#">
                     <i class="fa fa-object-group fa-lg"></i> <span>Master</span> <i class="fa fa-angle-right pull-right"></i>
                     </a>
                     <ul class="sidebar-submenu">
                        <c:if test="${sessionScope.isManager eq 'SENIOR_MANAGER' || sessionScope.isSuperAdmin eq 'SUPER_ADMIN'}">
                           <li class="trackList">
                              <a href="${pageContext.request.contextPath}/tms/track/track_details">
                              <i class="fa fa-desktop"></i><span> Track Master<span>
                              </a>
                           </li>
                        </c:if>
                        <c:if test="${sessionScope.isSpoc eq 'SPOC' || sessionScope.isSuperAdmin eq 'SUPER_ADMIN'}">
                           <li class="shiftList"><a href="${pageContext.request.contextPath}/tms/project/project_details">
                              <i class="fa fa-cogs" ></i><span> Shift Master<span>
                              </a>
                           </li>
                        </c:if>
                     </ul>
                  </li>
               </c:if>
               <c:if test="${sessionScope.isManager eq 'SENIOR_MANAGER'|| sessionScope.isSuperAdmin eq 'SUPER_ADMIN' || sessionScope.isSpoc eq 'SPOC'}">
                  <li class="trackMappingSide ">
                     <a href="${pageContext.request.contextPath}/tms/project/trackMapping">
                     <i class="fa fa-sitemap fa-lg"></i> <span> Mapping</span>
                     </a>
                  </li>
               </c:if>
               <c:if test="${sessionScope.isAdmin eq 'ADMIN' || sessionScope.isSuperAdmin eq 'SUPER_ADMIN'}">
                  <li class="adminSide">
                     <a href="">
                     <i class="fa fa-cog fa-lg"></i> <span>FLM Admin</span> <i class="fa fa-angle-right pull-right"></i>
                     </a>
                     <ul class="sidebar-submenu">
                        <li class="flmDashSide">
                           <a href="${pageContext.request.contextPath}/tms/flmDashboard/admin">
                           <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                           </a>
                        </li>
                        <li class="vendorDetails">
                           <a href="${pageContext.request.contextPath}/tms/flmDashboard/vendor">
                           <i class="fa fa-taxi "></i> <span> Vendor Details</span>
                           </a>
                        </li>
                        <li class="EmpList">
                           <a href="${pageContext.request.contextPath}/tms/project/viewEmployeeDetails">
                           <i class="fa fa-list"></i> <span>All Emp List</span>
                           </a>
                        </li>
                        
                     </ul>
                  </li>
               </c:if>
               <c:if test="${sessionScope.isFinance eq 'FINANCE' || sessionScope.isSuperAdmin eq 'SUPER_ADMIN'}">
                  <li class="financeSide ">
                     <a href="${pageContext.request.contextPath}/tms/financePage/finance">
                     <i class="fa fa-inr fa-lg"></i> <span> Finance</span>
                     </a>
                  </li>
               </c:if>
               
                <%-- <c:if test="${sessionScope.isFinance ne 'FINANCE' && sessionScope.isSpoc eq 'SPOC'}">
                  <li class="financeSide ">
                     <a href="${pageContext.request.contextPath}/tms/project/finance">
                     <i class="fa fa-inr fa-lg"></i> <span> Shift Allowance</span>
                     </a>
                  </li>
               </c:if> --%>
                <c:if test="${sessionScope.isFrontDesk eq 'FRONT_DESK'}">
                  <li class="flmDashSide">
                           <a href="${pageContext.request.contextPath}/tms/flmDashboard/admin">
                           <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                           </a>
                        </li>
                        <li class="vendorDetails">
                           <a href="${pageContext.request.contextPath}/tms/flmDashboard/vendor">
                           <i class="fa fa-taxi "></i> <span> Vendor Details</span>
                           </a>
                        </li>
               </c:if>
               <c:if test="${sessionScope.isSuperAdmin eq 'SUPER_ADMIN' }">
                <li class="uploadProjectSide">
                           <a href="${pageContext.request.contextPath}/tms/project/createAccountProject">
                           <i class="fa fa-info-circle "></i> <span> Upload Project Info</span>
                           </a>
                        </li>
                        <li class="uploadEmpSide">
                           <a href="${pageContext.request.contextPath}/tms/employee/createEmployeeList">
                           <i class="fa fa-users"></i> <span> Upload Emp Info</span>
                           </a>
                        </li>
                        </c:if>
               <c:if test="${sessionScope.isFrontDesk ne 'FRONT_DESK' || sessionScope.isSuperAdmin eq 'SUPER_ADMIN'}">
               <li class="userSide ">
                  <a href="${pageContext.request.contextPath}/tms/employee/addEmployee">
                  <i class="fa fa-user-plus fa-lg"></i> <span> User Details</span>
                  </a>
               </li>
               </c:if>
            </ul>
         </div>
      </nav>
      <script src="${pageContext.request.contextPath}/js/lib/Jquery.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/angular.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/angular-route.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/angular-ui-router.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/ui-bootstrap-tpls.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/bootstrap.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/localforage.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/ngStorage.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/app/app.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/moment.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/datetimepicker.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/jquery.timepicker.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/sidebar-menu.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/Defiant.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/bootbox.js"></script>
      <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>
         <script src="https://cdn.datatables.net/plug-ins/1.10.19/sorting/datetime-moment.js"></script> -->
      <script src="${pageContext.request.contextPath}/js/lib/jquery.dataTables.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/dataTables.buttons.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/dataTables.bootstrap.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/buttons.html5.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/jszip.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/lib/crypto.js"></script><!-- Encryption and Decryption -->
      <script src="${pageContext.request.contextPath}/js/lib/jquery.sumoselect.min.js"></script>
      <%-- <script src="${pageContext.request.contextPath}/js/lib/select2.min.js"></script>     --%>
      <script>
         $.sidebarMenu($('.sidebar-menu'));
         if("${sessionScope.isFrontDesk}"=="FRONT_DESK"){
        	 $(".user a").removeAttr('href');
        	 }
         function logout() {
             bootbox.confirm({
                 size: "small",
                 message: "<label>Are you sure want to Logout?</label>",
                 callback: function(result) {
                     if (result == true) {
                         window.location.href = "/tmsApp/logout";
                     }
                 }
             });
         }
         
         $(document).ready(function() {
             setTimeout(function() {
                 $('[data-toggle="tooltip"]').tooltip();
                 var height = $(window).height();
                 if (height > 620) {
                     $('.sidebar-menu').css("height", (height - 51));
                 }
                 /* else if(height<620){
         $('.sidebar-menu .out .collapse .in').css("height",(height-200));
         } */
             }, 100);
         
             //console.log("${authorities[0] }");
             if ("${authorities[0] }" == "READ-ONLY") {
                 $('.sidebar-menu .adhocSide,.adhocSide li,.sidebar-menu .updateAddrSide,.sidebar-menu .dashboardSide,.sidebar-menu .financeSide').addClass('hidden');
             } else {
                 $('.sidebar-menu .adhocSide,.adhocSide li,.sidebar-menu .updateAddrSide,.sidebar-menu .dashboardSide,.sidebar-menu .financeSide').removeClass('hidden');
             }
         
             $('input[type="tel"]').keypress(function(e) {
                 if (this.value.length == 0 && e.which == 48) {
                     return false;
                 }
             });
         
             $('input,textarea').keypress(function(e) {
                 if (this.value.length == 0 && e.which == 32) {
                     return false;
                 }
             });
         
             $('input,select,textarea').attr("autocomplete", "off");
             $('.modal-body textarea,.modal-body input,.modal-body select').attr("autocomplete", "off");
         
             $('.bootbox .modal-footer .btn').addClass('btn-sm');
             $('.sidebar-menu a').addClass('hvr-bounce-to-right');
         
             console.log("${authorities}");
         });
      </script>