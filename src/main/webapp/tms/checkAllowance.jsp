<jsp:include page="header.jsp" />
<style>

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
   
   .shiftAllow .tabcontent{
   	display:block;
   }
</style>
 <script type="text/javascript" src="/tmsApp/js/app/shiftPlanner.js"></script> 
  <script type="text/javascript" src="/tmsApp/js/app/jquery.e-calendar.js"></script>
  <div class="Adhoc_panel shiftAllow cont col-xs-12 col-sm-10 col-sm-offset-1 col-md-9 col-md-offset-3 col-lg-10
   col-lg-offset-2" ng-controller="ShiftPlanController">
     <div class="tab" >
 <button class="tablinks userRoleTab" >Shift Allowance</button> 
 </div>
 <div id="allow" class="tabcontent col-lg-12" ng-init="allowanceLoadFunc()"> 
       <table id="allowanceViewTable" class="table table-responsive display no-footer dataTable" cellspacing="0" width="100%" role="grid" aria-describedby="allowanceViewTable_info" style="width: 100%;">
            <thead>
               <tr role="row">
               <th class="sorting_disabled" tabindex="0" aria-controls="allowanceViewTable" rowspan="1" colspan="1" aria-sort="ascending" aria-label="S.No: activate to sort column descending" style="width: 32px;">S.No</th>
               <th class="sorting_disabled" tabindex="0" aria-controls="allowanceViewTable" rowspan="1" colspan="1" aria-label="Employee Number: activate to sort column ascending" style="width: 96px;">Employee Number</th>
               <th class="sorting_disabled" tabindex="0" aria-controls="allowanceViewTable" rowspan="1" colspan="1" aria-label="Employee Name: activate to sort column ascending" style="width: 104px;">Employee Name</th>
               <th class="sorting_disabled" tabindex="0" aria-controls="allowanceViewTable" rowspan="1" colspan="1" aria-label="Month: activate to sort column ascending" style="width: 50px;">Month</th>
               <th class="sorting_disabled" tabindex="0" aria-controls="allowanceViewTable" rowspan="1" colspan="1" aria-label="EarningComponentAffected: activate to sort column ascending" style="width: 186px;">Earning Component Affected</th>
               <th class="sorting_disabled" tabindex="0" aria-controls="allowanceViewTable" rowspan="1" colspan="1" aria-label="Amount: activate to sort column ascending" style="width: 53px;">Amount</th>
               <th class="sorting_disabled" tabindex="0" aria-controls="allowanceViewTable" rowspan="1" colspan="1" aria-label="Amount: activate to sort column ascending" style="width: 53px;">No. of Days</th>
               <th class="sorting_disabled" tabindex="0" aria-controls="allowanceViewTable" rowspan="1" colspan="1" aria-label="Remarks: activate to sort column ascending" style="width: 89px;">Remarks</th>
               <th class="sorting_disabled" tabindex="0" aria-controls="allowanceViewTable" rowspan="1" colspan="1" aria-label="Project Name: activate to sort column ascending" style="width: 54px;">Project Name</th>
               <th class="sorting_disabled" tabindex="0" aria-controls="allowanceViewTable" rowspan="1" colspan="1" aria-label="Spoc: activate to sort column ascending" style="width: 83px;">Spoc</th></tr>
            </thead>
            <tbody>
               <!-- ngRepeat: x in allowanceView --><!-- end ngRepeat: x in allowanceView --><!-- end ngRepeat: x in allowanceView -->
            <tr ng-repeat="x in allowanceView"  id={{x.id}}>
                             
               <td id="{{$index}}" class="details-control" >{{$index+1}}</td>
               <td>{{x.empId}}</td>
               <td>{{x.empName}}</td>
               <td>{{x.month}}</td>
               <td>{{x.earningComponentAffected}}</td>
               <td>{{x.allowanceAmount}}</td>
                <td>{{x.noOfDays}}</td>
               <td>{{x.remark}}</td>
               <td>{{x.projectName}}</td>
               <td>{{x.spocName}}</td>
               </tr></tbody>
         </table>
         </div>
         </div>
