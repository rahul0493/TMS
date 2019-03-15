<jsp:include page="header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="/tmsApp/js/app/adminController.js"></script>
<script src="/tmsApp/js/app/adminService.js"></script>
<style>
   table .fa:hover {background-color: rgb(26, 58, 74)  !important;
   color:white;
   border-radius: 5px;
   box-shadow: 4px 4px #999;
   }
   table .fa:active {
   background-color: #3e8e41;
   box-shadow: 2px 2px #666;
   transform: translateY(2px);
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
   font-size: 13px;
   padding: 4px !important;
   }
   th{
   padding-right:15px !important;
   background-color:#b4dbec;
   border-bottom: 1px solid #1a3a4a !important 
   }
   td{
   background-color:#f1f1f1;
   }
   .tooltip1 .tooltiptext{
   padding:2px !important;
   }
   .addSpoc:hover{
   color:#3d6798;
   text-decoration: underline;
   }
   .addSpoc{
   margin-left:15px;
   }
   .vendorEdit,.vendorDelete{
   display:inline;
   }
   .vendorEdit a ,.vendorDelete a{
   color:#1a3a4a;
   text-decoration:none;
   padding:5px;
   }
   .vendorEdit .tooltiptext,.vendorDelete .tooltiptext{
   padding:5px !important;
   left:0% !important;
   margin-left:0px !important;
   }
   .addVendor a{
   text-decoration:none;
   padding:5px;
   color:#1a3a4a;
   }
   .addVendor .fa:active{
   transform: translateY(2px);
   }
   .addVendor .fa:hover{
   background-color: rgb(26, 58, 74) !important;
   color: white;
   border-radius: 5px;
   box-shadow: 4px 4px #999;
   padding:5px;
   }
   #vendorModal .modal-content,#vendorModal .modal-body{
   padding-right:0px;
   }
</style>
<div class="user_detail vendorCont hidden   col-xs-12 col-sm-10 col-sm-offset-1 col-md-9 col-md-offset-3 col-lg-8
   col-lg-offset-3" ng-controller="adminController">
   <div class=" panel panel-default" ng-init="getAllVendor()">
      <div class="panel-heading faq-links"><b>Vendor Details</b>
      </div>
      <div class=" panel-body vendorBody">
         <div class="vendorDiv">
            <table id="vendorTable" class="table table-responsive display ">
               <thead>
                  <tr>
                     <th>Doc.No</th>
                     <th>Travels Name</th>
                     <th>Vendor Name</th>
                     <th>Vendor Number</th>
                     <th>Travels Address</th>
                     <th>Charge with Escort</th>
                     <th>Charge without Escort</th>
                     <th>Action</th>
                  </tr>
               </thead>
               <tbody>
                  <tr ng-repeat="x in allVendorData">
                     <td class="details-control"  id="{{$index}}">
                        <label><a data-toggle="tooltip" title="Click here to see Supervisor detail's">{{x.vendorId}}</a></label>
                     </td>
                     <td>{{x.travelsName}}</td>
                     <td>{{x.ownerName}}</td>
                     <td>{{x.officeNumber}}</td>
                     <td>{{x.officeAddress}}</td>
                     <td>{{x.rateWithEscort}}</td>
                     <td>{{x.rateWithoutEscort}}</td>
                     <td>
                        <div class=" vendorEdit" >
                           <a class="fa fa-pencil-square-o fa-lg" id="" data-toggle="tooltip" title="Edit" aria-hidden="true" ng-click="editVendor(x.vendorId)" ></a>
                        </div>
                        <!-- <div class=" vendorDelete" >
                           <a class="fa fa-trash fa-lg" id="" data-toggle="tooltip" title="Delete" aria-hidden="true" ng-click="deleteVendor(x.vendorId)" ></a>
                        </div> -->
                  </tr>
               </tbody>
            </table>
         </div>
         <div class="text-center vendorAlert {{class}} col-lg-offset-4 col-lg-4 hidden" ><b>{{vendorAlertStatus}}</b></div>
      </div>
   </div>
   <div class="modal fade" id="vendorModal" role="dialog">
      <div class="modal-dialog modal-lg">
         <div class="modal-content col-lg-12">
            <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal">&times;</button>
               <h4 class="modal-title">Vendor Detail</h4>
            </div>
            <div class="modal-body" >
               <form id="" ng-submit="createVendor()">
                  <div class="" >
                     <span style="color:red">   * Note:All Fields are Mandatory</span>
                  </div>
                  <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6 addressAlias">
                     <label class="col-lg-4  col-form-label">Travels Name</label>
                     <div class="col-lg-8">
                        <input class="form-control" type="text" ng-model="vendor.travelsName" maxlength="30" required/>
                     </div>
                  </div>
                  <div class="form-group row col-sm-12 col-lg-6 col-md-6">
                     <label class="col-lg-4 col-form-label" id="address_label">Travels Address</label>
                     <div class="col-lg-8">
                        <textarea class="form-control" rows="3" cols="30" ng-model="vendor.officeAddress" maxlength="100"  required> </textarea>
                     </div>
                  </div>
                  <div class="form-group row col-sm-12 col-lg-6 col-md-6">
                     <label  class="col-lg-4 col-form-label">Vendor Name</label>
                     <div class="col-lg-8">
                        <input class="form-control" type="text" ng-model="vendor.ownerName" maxlength="30" required/>
                     </div>
                  </div>
                  <div class="form-group row col-sm-12 col-lg-6 col-md-6">
                     <label  class="col-lg-4 col-form-label">Vendor Number</label>
                     <div class="col-lg-8">
                        <input class="form-control" type="tel"  ng-model="vendor.officeNumber" onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" maxlength="10" pattern="[0-9]{10}" title="Please Enter 10 digit Mobile Number" required/>
                     </div>
                  </div>
                  <div class="form-group row col-sm-12 col-lg-6 col-md-6">
                     <label  class="col-lg-4 col-form-label">Trip charge with Escort</label>
                     <div class="col-lg-8">
                        <input class="form-control" type="tel"  ng-model="vendor.rateWithEscort" maxlength="10" onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" required/>
                     </div>
                  </div>
                  <div class="form-group row col-sm-12 col-lg-6 col-md-6">
                     <label  class="col-lg-4 col-form-label">Trip charge without Escort</label>
                     <div class="col-lg-8">
                        <input class="form-control" type="tel" ng-model="vendor.rateWithoutEscort" maxlength="10" onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" required/>
                     </div>
                  </div>
                  <div ng-repeat="x in vendorSpocs" class="spocDiv" id="div{{$index}}">
                     <div class="form-group row col-sm-12 col-lg-6 col-md-6">
                        <label  class="col-lg-4 col-form-label">Supervisor {{$index+1}} Name</label>
                        <div class="col-lg-8">
                           <input class="form-control" type="text" ng-model="x.spocName" maxlength="30"  required />
                        </div>
                     </div>
                     <div class="form-group row col-sm-12 col-xs-12 col-lg-6 col-md-6">
                        <label class="col-lg-4  col-form-label">Supervisor {{$index+1}} Number</label>
                        <div class="col-lg-8">
                           <input class="form-control" type="tel" ng-model="x.spocMobileNumber" onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" maxlength="10" pattern="[0-9]{10}" title="Please Enter 10 digit Mobile Number" required />
                           &nbsp;<i class="fa fa-trash fa-lg delSpoc hidden" id="del{{$index+1}}" ng-click="removeSpoc(x.spocId,$index)" style="padding-top:10px">&nbsp;Delete Supervisor{{$index+1}}</i>
                        </div>
                     </div>
                  </div>
                  <i class="fa fa-plus-square fa-lg addSpoc" ng-click="addSPOC()">&nbsp;Add Supervisor</i>
                  <br/>
                  <div class="text-center  col-lg-12 col-sm-12 col-md-12" style="padding-bottom:10px">
                     <input type="submit" class="btn btn-md vendorSubmit"   value="Submit" />
                     <input type="reset" class="btn btn-md vendorReset" value="Reset" ng-click="vendorReset()"/>
                  </div>
               </form>
            </div>
         </div>
      </div>
   </div>
</div>
<script>
   $(document).ready(function(){
   	$('.vendorSubmit').val('Submit');
    	$('.adminSide').addClass('active');
     	$('.adminSide .vendorDetails').addClass('active');
     	$('#div0 .form-control').keyup(function() {
           if(($('#div0  input[type="text"]').val() != '')&&($('#div0  input[type="tel"]').val() != '')) {
              $('.addSpoc').removeClass('hidden');
           }
        });
   	
   });
</script>