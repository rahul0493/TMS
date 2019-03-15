
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
    <div class="monthly_panel col-xs-12 col-sm-10 col-sm-offset-1 col-md-9 col-md-offset-3 col-lg-8
  col-lg-offset-3" ng-controller="adminController">
        <div class="panel panel-default">
            <div class="panel-heading "><b>Upload Account and Project Details</b> <a href="/tmsApp/tms/project/writeExcel?excelType=AccProjectExcel" class="fa fa-download pull-right"> Download Excel</a></div>
            <div class=" panel-body">
                <div class="excelupload  col-lg-offset-4 col-lg-4 " >

                    <h3>Upload an Excel file </h3>
                    <p id="support-notice">Application supports only Excel file</p>
                    <!-- The form starts -->
                    <form method="post" id="fromFileUpload" enctype="multipart/form-data" name="myForm">
                              <label class="control-label" for="file">Please upload the file : <span class="required">*</span> </label>
                                <input id="browseId" class=" " type="file" name="file" multiple data-rule-required="true"  accept=".csv,.xlsx">
                                 <div class="text-center">
                                  <input type="button" class="btn btn-sm"  ng-click="uploadAccntPrjctExcelFile()" value="Upload" />
                            </div>
                    </form> 
                </div>
                 <div class="text-center excelAlert {{class}} col-lg-offset-4 col-lg-4" hidden ><b>{{excelStatus}}</b></div>
            </div>
        </div>

              <div class="accntDiv" hidden>
                <table id="accntTable"   class="table table-bordered" >
                    <thead>
                        <tr>
                            <th>S.No</th>
                            <th>Account</th>
                            <th>Project</th>
                            
                        </tr>
                    </thead>
                    <tbody >
                  
   					
                        <tr ng-repeat="x in accountProjectList track by $index" id=data{{no+$index+1}} style="display:table-row">
                            <td>{{ no+$index+1 }}</td>
                            <td>{{x.accountName}}</td>
                            <td>{{x.projectName}}</td>
                        </tr>
                    </tbody>
                </table>
                </div>
    </div>
      <jsp:include page="modalPopup.jsp" />
<script>
$(document).ready(function(){
	
	$('.uploadProjectSide').addClass('active');
});
</script>