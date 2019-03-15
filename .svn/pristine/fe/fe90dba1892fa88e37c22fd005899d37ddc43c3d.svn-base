
    <jsp:include page="header.jsp" />
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
   font-size: 12px;
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
    <div class="monthly_panel col-xs-12  col-sm-10 col-sm-offset-1 col-md-9 col-md-offset-3 col-lg-10
  col-lg-offset-2" ng-controller="adminController">
  <div class="col-lg-offset-1 col-lg-10">
        <div class="monthly panel panel-default">
            <div class="panel-heading "><b>Upload Employee List</b><!-- <a href="/tmsApp/tms/project/writeExcel?excelType=EmployeeExcel" class="fa fa-download pull-right"> Download Excel</a> --></div>
            <div class=" panel-body">
                <div class="excelupload  col-lg-offset-4 col-lg-4 " >

                    <h3>Upload an Excel file </h3>
                    <p id="support-notice">Application supports only Excel file</p>
                    <!-- The form starts -->
                    <form method="post" id="fromFileUpload" enctype="multipart/form-data" name="myForm">
                              <label class="control-label" for="file">Please upload the file : <span class="required">*</span> </label>
                                <input id="browseId" class=" " type="file" name="file" multiple data-rule-required="true"  accept=".csv,.xlsx">
                                 <div class="text-center">
                                  <input type="button" class="btn btn-sm"  ng-click="uploadEmpExcel()" value="Upload" />
                            </div>
                    </form> 
                </div>
                  <div class="text-center excelAlert {{class}} col-lg-offset-4 col-lg-4" hidden ><b>{{excelStatus}}</b></div>
            </div>
        </div>
</div>
              <div class="empTableDiv" hidden>
                <table id="empTable"   class="table table-bordered" >
                    <thead>
                        <tr>
                        	<th><input name="select_all" value="1" id="example-select-all" type="checkbox" /></th>
                            <th>Emp Code</th>
                            <th>Employee Name</th>
                            <th>EmailId</th>
                            <th>Designation</th>
                            <th>Mobile Number</th>
                            <th>Gender</th>
                            <th>Project Name</th>
                            <th>Project Allocation</th>
                            <th>Customer Name</th>
                            <th>Manager Email</th>
                        </tr>
                    </thead>
                    <tbody id="">
                  
   					
                        <tr ng-repeat="x in employeeList track by $index" id=data{{no+$index+1}} style="display:table-row">
                            <td><input type="checkbox" class="empRow td_{{x.empcode}}" id="{{$index}}" ng-click="check(x.empcode)"></td>
                            <td>{{x.empcode}}</td>
                            <td>{{x.name}}</td>
                            <td>{{x.email}}</td>
                            <td>{{x.designationName}}</td>
                            <td>{{x.phoneNumber}}</td>
                            <td>{{x.gender}}</td>
                            <td>{{x.projectName}}</td>
                            <td>{{x.projectAllocation}}</td>
                            <td>{{x.accountName}}</td>
                             <td>{{x.managerEmail}}</td>
                        </tr>
                    </tbody>
                </table>
          <div class="text-center btns col-lg-12 col-sm-12 col-md-12" id="submitEmpList"  style="padding-bottom: 20px;">
       	<input type="submit" class="btn btn-md "  value="Submit"  ng-click="saveEmp()"  />
       	<br>
      </div>
       <div class="text-center roleAlert {{class}} col-lg-offset-4 col-lg-4" hidden><b>{{roleAlertStatus}}</b></div>
                
                </div>
        </div>
      <jsp:include page="modalPopup.jsp" />
<script>
$(document).ready(function(){
	$('.uploadEmpSide').addClass('active');
});
</script>