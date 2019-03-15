<jsp:include page="header.jsp" />

 		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>

 		  <link rel="stylesheet" href="/tmsApp/css/jquery.e-calendar.css"/>
      <!-- <link rel="stylesheet" href="/tmsApp/css/pop.css"/>-->
//    <link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
 <script src="/tmsApp/js/lib/Jquery.js"></script> 
   <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/tmsApp/js/app/shiftPlanner.js"></script> 
    <script type="text/javascript" src="/tmsApp/js/app/jquery.e-calendar.js"></script>
//    <!-- <script type="text/javascript" src="/tmsApp/js/lib/confirm-bootstrap.js"></script> -->

    <script type="text/javascript" src="/tmsApp/js/lib/jquery-alter.js"></script>
  <link href="/tmsApp/css/jquery.contextMenu.css?v=1" rel = "stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.0/jquery.contextMenu.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.7.0/jquery.ui.position.js"></script>
     <script src = "https://code.jquery.com/ui/1.11.2/jquery-ui.min.js"></script>
         <script type="text/javascript" src="/tmsApp/js/lib/qtip.js"></script>
         
	<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
	  <link href = "http://code.jquery.com/ui/jquery-ui-git.css"  rel = "stylesheet">
	   <link rel="stylesheet" href="/tmsApp/css/qtip.css"/>
 <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
 <style>
 .mainDiv ::-webkit-scrollbar-track {
     -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
     background-color: white;
}
.mainDiv ::-webkit-scrollbar { 
     width: 8px;
     height:8px;
     background-color: white;
}
.mainDiv ::-webkit-scrollbar-thumb {
     cursor: pointer;
     background-color: #b9cedf; 
} 
 </style>
        <div class="container-fluid mainDiv" ng-controller="ShiftPlanController">
      
            <div class="col-lg-12 no-padding">
        <div class="breadcrumbShift" style="">
        
		        <ol class="breadcrumb">
			        <li class="breadcrumb-item"><a href="/tmsApp/tms/home">Home</a></li>
			        <li class="breadcrumb-item active">Shift Planner</li>
				 
		      </ol>
		      <a href="#" id="allowance" style="display:none">Check Allowance</a>
          <a href="#" style="display:none" id="status">Check Status</a>
			
			
        </div>
       
      </div>
   

<div id="statusDiv" style="display:none" class="container" >
		        
            <div  ng-repeat="option in statusData"  > 
           
                <div id="track_{{option.trackId}}" class=" col-lg-4 col-xs-12row bs-wizard" style="border-bottom:0;">
                 <div class="trackHeading">{{option.trackName}}</div>
                <div  class="draftStatus  col-lg-4 bs-wizard-step disabled ">
                   <div class="text-center bs-wizard-stepnum">Saved As Draft.</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="#" class="bs-wizard-dot"></a>
                  
                </div>
                
                <div  class=" cabStatus col-xs-3 col-lg-4 bs-wizard-step disabled"><!-- complete -->
                <div class="text-center bs-wizard-stepnum">Cab Requests Generated</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="#" class="bs-wizard-dot"></a>
                
                </div> 
                
                <div class="approvalStatus col-xs-3 col-lg-4  bs-wizard-step disabled"><!-- complete -->
                  <div class="text-center bs-wizard-stepnum">Sent For Approval</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="#" class="bs-wizard-dot"></a>
                 
                </div>
                
                  
            </div>
            </div>
              
        
        
        
	</div>
    	<div id="shift-left-panel" class="col-lg-2 no-padding">
    		<div>
            <table class="table table-responsive">
              <thead style="background-color:rgb(112, 145, 183)">
                <tr>
                  <td style="color:white;">
                    <div class="form-group">
                      <label>Account</label>
                      <select class="form-control" id="acc" ng-model="SelectedAccount">
                      	<option value="" disabled selected>Please Select</option>
                      	<option ng-repeat="option in accountData" value="{{option.id}}">{{option.accountName}}</option>
                      </select>
                    </div>
                  </td>
                </tr>
              <tr>
                <td style="color:white;">
                  <div class="form-group">
                    <label>Project</label>
                    <select class="form-control" id="proj" disabled="true" ng-model="SelectedProj">
                	<option value=""  disabled selected>Please Select</option>
                  	<option ng-repeat="option in projData" value="{{option.projId}}">{{option.proj_nm}}</option>
                    </select>
                  </div>
                </td>
              </tr>
              <tr>
              <td id="saveTrackwise" style="color:white;">
                <div class="form-group">
               		 <input type="checkbox" id="radioTrack">
                    <label>Save TrackWise</label>
              
                  </td>
              </tr>
            
              <tr id="trackRow" style="display:none" >
                <td style="color:white;">
                  <div class="form-group">
                    <label>Track</label>
                    <select class="form-control" id="track" disabled="true"  ng-model="SelectedTrack" >
	                <option value="" disabled selected>Please Select</option>
                  	<option ng-repeat="option in TrackData" value="{{option.trackId}}">{{option.track_nm}}</option>	
                    </select>
                  
                  </div>
                </td>
              </tr>
              <tr id="shiftRow">
                <td style="color:white;">
                  <div class="form-group">
                    <label>Shift</label>
                    <select class="form-control" id="shift" disabled="true">
                    <option value="" disabled selected>Please Select</option>
                  	<option ng-repeat="option in ShiftData" value="{{option.shift_master_nm}}" data-shiftid="{{option.shift_id}}" data-shift_from_time="{{option.shift_from_time}}" data-shift_to_time="{{option.shift_to_time}}">{{option.shift_display_nm}}</option>
                    </select>
                  </div>
                </td>
              </tr>
              <tr>
             
                <td>
               <div id="actionItems"  class="btn-group">  
            <button id="trashOne" type="button" class="deleteArea btn btn-primary"><span class=" glyphicon glyphicon-trash">  </span><p><span style="font-size:10px;">Remove</span></p></button>
			 <button id="copyPlan" style="display:none" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-copy"></span><p><span style="font-size:10px;">COPY</span></p></button>
			 <button id="deleteShiftPlan" style="display:none" type="button"  class="btn btn-primary"><span class="glyphicon glyphicon-remove-circle"></span><p><span style="font-size:10px;">Delete Plan</span></p></button>
					
			</div>    
			        
                </td>
              </tr>
            </thead>
            </table>

            </div>
              <div style="margin-top:10px;">
                <table id="tableEmp" class="table table-responsive w-shift" style="display:none;">
                  <tbody>

                  <tr>
                    <td>
                      <div class="form-group " id="empDiv" style="overflow:auto;height:150px">
                        <label>My Team</label>
                        <div id="empList" style="clear:both">
                          <div id="shift_details"></div>
                          <hr/>
                          <div  ng-repeat="option in EmpData">
                          
	                          <div>
	                         		 <p class="heading"><input class="filter_track" type="checkbox" value="{{option.emp_trackid}}"/>{{option.emp_trcknm}}</p>
	                         	 <div style="border:red;" ng-repeat="x in option.emp">
		                          	<input class="filter_emp" type="checkbox" value="{{x.empid}}"/>
		                        	<div class="name" data-toggle="tooltip" title="{{x.empnm}}">{{x.empnm.split(' ')[0]}}</div>
		                        	<div class="outCalendar w-emp " data-empid="{{x.empid}}"  data-trackid="{{x.emp_trackid}}" >
		                        	<div class="" ><a href="#" data-toggle="dropdown" class="dropdown-toggle select-value" data-placement="bottom">{{x.emp_init}}</a></div>
			                        	 <div style="display:none" id="emp_{{x.empid}}" class="popover_select_values">
				                        	<ul style="list-style-type:none;margin: 0;padding: 0;">
				                        		<li ng-repeat="y in x.other_track">
				                        			<label class="chkLabel">{{y.track_nm}}</label><input class="trackCheck" data-track="{{y.track_nm}}"  type="checkbox"  value="{{y.trackId}}" />
				                        		</li>
				                        		 
           
				                        	</ul>
			                        	</div>
			                        	</div>
		                        	
		                          	<span id="counter" class="{{x.empid}}" display="inline-block"></span>
		                          	</div>
		                          	
	                          </div>
	                         </div>
                          </div>
                      </div>
                    </td>
                  </tr>
                </tbody>
                </table>
              </div>

    	</div>
     
    	<div class="col-lg-10 no-padding">
    		<div id="ShiftCalendar"></div>
    	<div id="message"></div> 
    	
    		<!--  <div class="submitDiv">
    			 <button id="saveDraft" style="width:12rem;" class="submit btn btn-success" >Save as Draft</button>
    			<button id="submit" style="width:14rem;" class="submit btn btn-success">Submit</button>
    			<button style="display:none; width:14rem" id="sendAppr" class="submit btn btn-success">Send For Approval</button>
    		</div>  -->
    		<div id="projectsubmitDiv" class="submitDiv">
    			 <button id="projectsaveDraftbtn" style="width:12rem;" class="submit btn btn-success" >Save as Draft</button>
    			
    		</div>
    		<div id="tracksubmitDiv"  class="submitDiv" style="display:none">
    			 <button id="tracksaveDraftbtn" style="width:12rem;" class="submit btn btn-success" >Save as Draft</button>
    			<button id="cabbtn" style="width:17rem;" class="submit btn btn-success">Generate Cab Requests</button>
    			<button style="display:none; width:17rem" id="sendApprbtn" class="submit btn btn-success">Send For Approval</button>
    		</div>
    		
    	</div>
    	

	</div>

  <script>
  $(function() {


          // $('.div-event-drop').draggable({
          //   cursor: "pointer",
          //   helper: 'clone',
          //   revert: 'invalid',
          //   snap:'false',
          //   stack:'.div-event-drop'
          // });

        // $(".c-drop").droppable({
        //   accept:".div-event-drop",
        //   tolerance: "pointer",
        //     drop: function(event,ui) {
        //         var appendedtd = ($(this).find('td'));
        //         ui.draggable.appendTo(appendedtd);
        //       // console.log($(this).css('width'));
        //         $(ui.draggable).removeClass('w-emp').addClass("cal");
        //       //alert("hi");
        //     }
        // });

    })

    function setDraggable(el, doClone) {
    el.draggable({
      helper: doClone ? 'clone' : 'original',
      revert: true,
      stack:'.div-event-drop',
      scroll:'false'

    });
  }
  
 

  </script>



