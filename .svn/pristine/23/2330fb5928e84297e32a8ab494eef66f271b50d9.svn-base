<jsp:include page="header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
/* Let's get this party started */
::-webkit-scrollbar {
    width: 5px;
    height:5px;
}
 
/* Track */
::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
    -webkit-border-radius: 10px;
    border-radius: 10px;
}
 
/* Handle */
::-webkit-scrollbar-thumb {
    -webkit-border-radius: 10px;
    border-radius: 10px;
    background: #ddd; 
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.5); 
}
 ::-webkit-scrollbar-thumb:window-inactive {
	background: #ddd; 
} 
   td{
   font-size:13px;
   border:1px solid #1a3a4a;
   }
   th{
   background:#1a3a4a82;
   }
   table{
   margin-top:5px !important;
   }
   label{
   font-size:15px !important
   }
   marquee{
   marquee-speed: fast;
   }
   #upcomingTable th,#upcomingTable td,#upcomingTable{
   border:none !important;
   }
   .ajax-loader{
   visibility:visible;
   }
   /* */
   .homepage .carousel a{
   color:white !important;
   /* text-decoration:underline; */
   }
   .homepage .carousel a:hover{
   font-size:29px !important;
   }
   .dynamicTile label{
   font-size:18px !important;
   }
   .dynamicTile  {
   padding: 5px;
   text-align:center;
   }
   .bigicon {
   font-size: 35px;
   color: white;
   margin-top: 10px;
   margin-bottom: 10px;
   }
   .icontext {
   color: white;
   font-size: 25px;
   }
   .bigicondark {
   font-size: 67px;
   color: black;
   margin-top: 20px;
   margin-bottom: 10px;
   }
   .icontextdark {
   color: black;
   font-size: 27px;
   }
   #tile1 {
   background: #31708e;
   }
   #tile2 {
   background: #31708e;
   }
   #tile3{
   background: #31708e;
   }
   .tilecaption {
   position: relative;
   top: 50%;
   transform: translateY(-50%);
   -webkit-transform: translateY(-50%);
   -ms-transform: translateY(-50%);
   margin: 0!important;
   text-align: center;
   color: white;
   font-family: Segoe UI;
   font-weight: lighter;
   }
   td, th {
   text-align: center !important; 
   font-size: 14px; 
   padding: 6px !important; 
   }
   .icontext label{
   cursor:not-allowed;
   font-size:25px !important;
   }
   .carousel-control .glyphicon {
    color:transparent !important;
    font-size:25px !important;
   }
</style>
<div class="homepage panel panel-default col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-md-9 col-md-offset-3 col-lg-8
   col-lg-offset-3">
   <div class="title">
      <!-- <strong>Welcome</strong> to the Employee Self Service (Cab Booking). -->
      <strong>Welcome to Quinnox Cab & Shift Scheduling</strong> 
   </div>
   <div class="dynamicTile userDashboard">
      <div class="">
         <div class="col-sm-12 col-xs-12 col-lg-3">
            <div style="background-color: #1a3a4a;color: white;padding:5px">
               <label>Monthly Request</label>
            </div>
            <div id="tile1" class="tile">
               <div class="carousel slide" data-ride="carousel" id="cabReqCarousel">
                  <!-- Wrapper for slides -->
                 <!--  <ol class="carousel-indicators">
    					<li data-target="#cabReqCarousel" data-slide-to="0" class="active"></li>
    					<li data-target="#cabReqCarousel" data-slide-to="1"></li>
    					<li data-target="#cabReqCarousel" data-slide-to="2"></li>
  				  </ol> -->
                  
                  
                  <div class="carousel-inner">
                     <div class="item active text-center" >
                        <div class="icontext">
                           <span class="fa fa-thumbs-up bigicon"></span>
                        </div>
                        <div class="icontext">
                           Used
                        </div>
                        <div class="icontext">
                        <c:if test="${monthlyUsed eq 0}">
 							<label>${monthlyUsed}</label>
						</c:if>
						<c:if test="${monthlyUsed ne 0}">
  							<a href="/tmsApp/tms/adhocRequest/cabRequestHistoryPage?status=Used&type=Monthly">${monthlyUsed}</a>
						</c:if>
                        </div>
                     </div>
                     <div class="item text-center">
                        <div class="icontext">
                           <span class="fa fa-thumbs-down bigicon"></span>
                        </div>
                        <div class="icontext">
                           Unused
                        </div>
                        <div class="icontext">
                        <c:if test="${monthlyUnused eq 0}">
 							<label>${monthlyUnused}</label>
						</c:if>
						<c:if test="${monthlyUnused ne 0}">
  						<a href="/tmsApp/tms/adhocRequest/cabRequestHistoryPage?status=Unused&type=Monthly">${monthlyUnused}</a>
						</c:if>
                        </div>
                     </div>
                     <div class="item  text-center">
                        <div class="icontext">
                           <span class="fa fa-eye-slash bigicon"></span>
                        </div>
                        <div class="icontext">
                           No Show
                        </div>
                        <div class="icontext">
                        <c:if test="${monthlyNoshow eq 0}">
 							<label>${monthlyNoshow}</label>
						</c:if>
						<c:if test="${monthlyNoshow ne 0}">
  						  <a href="/tmsApp/tms/adhocRequest/cabRequestHistoryPage?status=NoShow&type=Monthly">${monthlyNoshow}</a>
						</c:if>
                        </div>
                     </div>
                  </div>
                  <a class="left carousel-control" href="#cabReqCarousel" data-slide="prev">
                  <span class="glyphicon glyphicon-chevron-left hidden"></span>
                  <span class="sr-only">Previous</span>
                  </a>
                  <a class="right carousel-control" href="#cabReqCarousel" data-slide="next">
                  <span class="glyphicon glyphicon-chevron-right hidden"></span>
                  <span class="sr-only">Next</span>
                  </a> 
               </div>
            </div>
         </div>
         <div class="col-sm-12 col-xs-12 col-lg-3">
            <div style="background-color: #1a3a4a;color: white;padding:5px">
               <label>Adhoc Request</label>
            </div>
            <div id="tile2" class="tile">
               <div class="carousel slide" data-ride="carousel" id="adhocCarousel">
                  <!-- Wrapper for slides -->
                  <!-- <ol class="carousel-indicators">
    					<li data-target="#adhocCarousel" data-slide-to="0" class="active"></li>
    					<li data-target="#adhocCarousel" data-slide-to="1"></li>
    					<li data-target="#adhocCarousel" data-slide-to="2"></li>
    					<li data-target="#adhocCarousel" data-slide-to="2"></li>
  				  </ol> -->
                  <div class="carousel-inner">
                     <!--  <div class="item active text-center" href="#adhocCarousel" data-slide="next"> -->
                     <div class="item active text-center" >
                        <div class="icontext">
                           <span class="fa fa-check bigicon"></span>
                        </div>
                        <div class="icontext">
                           Approved
                        </div>
                        <div class="icontext">
                         <c:if test="${adhocApproved eq 0}">
 							<label>${adhocApproved}</label>
						</c:if>
						<c:if test="${adhocApproved ne 0}">
  						   <a href="/tmsApp/tms/adhocRequest/cabRequestHistoryPage?status=Approved&type=Adhoc">${adhocApproved}</a>
						</c:if>
                        </div>
                     </div>
                     <!-- <div class="item text-center" href="#adhocCarousel" data-slide="next"> -->
                     <div class="item text-center" >
                        <div class="icontext">
                           <span class="fa fa-pause bigicon"></span>
                        </div>
                        <div class="icontext">
                           Pending
                        </div>
                        <div class="icontext">
                          <c:if test="${adhocPending eq 0}">
 							<label>${adhocPending}</label>
						</c:if>
						<c:if test="${adhocPending ne 0}">
  						   <a href="/tmsApp/tms/adhocRequest/cabRequestHistoryPage?status=Pending&type=Adhoc">${adhocPending}</a>
						</c:if>
                        </div>
                     </div>
                     <div class="item  text-center" >
                        <div class="icontext">
                           <span class="fa fa-ban bigicon"></span>
                        </div>
                        <div class="icontext">
                           Rejected
                        </div>
                        <div class="icontext">
                           <c:if test="${adhocRejected eq 0}">
 							<label>${adhocRejected}</label>
						</c:if>
						<c:if test="${adhocRejected ne 0}">
  						   <a href="/tmsApp/tms/adhocRequest/cabRequestHistoryPage?status=Rejected&type=Adhoc">${adhocRejected}</a>
						</c:if>
                        </div>
                     </div>
                     <div class="item text-center" >
                        <div class="icontext">
                           <span class="fa fa-trash bigicon"></span>
                        </div>
                        <div class="icontext">
                           Cancelled
                        </div>
                        <div class="icontext">
                           <c:if test="${adhocCancelled eq 0}">
 							<label>${adhocCancelled}</label>
						</c:if>
						<c:if test="${adhocCancelled ne 0}">
  						  <a href="/tmsApp/tms/adhocRequest/cabRequestHistoryPage?status=Canceled&type=Adhoc">${adhocCancelled}</a>
						</c:if>
                        </div>
                     </div>
                    <%--  <div class="item  text-center" >
                        <div class="icontext">
                           <span class="fa fa-eye-slash bigicon"></span>
                        </div>
                        <div class="icontext">
                           No Show
                        </div>
                        <div class="icontext">
                           <a href="/tmsApp/tms/adhocRequest/cabRequestHistoryPage?status=NoShow&type=Adhoc">${adhocNoShow}</a>
                        </div>
                     </div> --%>
                  </div>
                  <a class="left carousel-control" href="#adhocCarousel" data-slide="prev">
                  <span class="glyphicon glyphicon-chevron-left hidden"></span>
                  <span class="sr-only">Previous</span>
                  </a>
                  <a class="right carousel-control" href="#adhocCarousel" data-slide="next">
                  <span class="glyphicon glyphicon-chevron-right hidden"></span>
                  <span class="sr-only">Next</span>
                  </a>
               </div>
            </div>
         </div>
         <div class="col-sm-12 col-xs-12 col-lg-6">
            <div style="background-color: #1a3a4a;color: white;padding:5px">
               <label>Upcoming Requests</label>
            </div>
            <div id="tile1" class="tile">
               <div class="carousel slide" data-ride="carousel">
                  <!-- Wrapper for slides -->
                  <div class="carousel-inner">
                     <div class="item active text-center">
                        <div class="icontext" style="padding:10px;padding-right:0px;padding-top:5px">
                           <c:if test="${empty upcomingReq}">
                              <div style="margin-top:50px">
                                 No Upcoming Request !!!
                              </div>
                           </c:if>
                           <c:if test="${not empty upcomingReq}">
                              <!-- <Marquee direction="up" scrollamount="3" height="140" onmouseover="this.stop();" onmouseout="this.start();"> --> 
                              <div style="overflow: auto;width:97%;height:150px">
                              <table id="" style="font-size:22px;">
                                 <tr>
                                    <th>Type</th>
                                    <th>Date</th>
                                    <th>TravelType</th>
                                    <th>Time</th>
                                    <th>Location</th>
                                 </tr>
                                 <c:forEach items="${upcomingReq}" var="cab" >
                                    <tr>
                                       <td style="text-transform:capitalize;">${cab.requestType}</td>
                                       <td>
                                          <fmt:formatDate pattern="dd MMM, yyyy" value="${cab.scheduleDate}" />
                                       </td>
                                       <td>${cab.adhocMonthly}</td>
                                       <td>${cab.reqTime}</td>
                                       <c:if test="${cab.requestType eq 'pickup'}">
                                          <td>${cab.fromLocation}</td>
                                       </c:if>
                                       <c:if test="${cab.requestType eq 'drop'}">
                                          <td>${cab.toLocation}</td>
                                       </c:if>
                                      <!--  <td>Shoban BTM 2nd satage babu bangalore BTM 2nd satage</td> -->
                                    </tr>
                                 </c:forEach>
                              </table>
                              </div>
                           </c:if>
                           <!-- </Marquee> -->
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
</div>
<div class=" col-md-12 col-xs-12  col-sm-12 col-lg-8 col-lg-offset-4">
   <jsp:include page="footer.jsp" />
</div>
 </div>
    </div>

<script type="text/javascript">
   jQuery(function ($) {
    //$('.ajax-loader').css("visibility", "visible");
    $('.dashboardSide').addClass('active');
       $(".tile").height($("#tile1").width()-40);
       $(".carousel").height($("#tile2").width()-40);
       $(".item").height($("#tile1").width()-40);
       
       $(window).on('resize', function () {
           if (this.resizeTO) {
               clearTimeout(this.resizeTO);
           }
           this.resizeTO = setTimeout(function () {
               $(this).trigger('resizeEnd');
           }, 10);
       });
   
        $(window).on('resizeEnd', function () {
           $(".tile").height($("#tile1").width()-40);
           $(".carousel").height($("#tile2").width()-40);
           $(".item").height($("#tile1").width()-40);
       });
       setTimeout(function () {
      	 $('.ajax-loader').css("visibility", "hidden");
      	$('.dynamicTile').removeAttr('hidden');
       }, 150);
   });
</script>