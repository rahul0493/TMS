'use strict';

angular.module('tmsApp').controller('ShiftPlanController', [ '$scope', '$rootScope', '$http','$q','$timeout', function( $scope, $rootScope,$http, $q, $timeout) {

var Shift = [];
var resp ;
var action= ''; 
var track_Id;
var project_id;
var account_id;
var accountName;
var projectName;
var trackName; 
var shiftName;
var trackApproved;
var  spocId;
var recentcall;

$(document).ready(function(){
	//Added by Akshata --URL
	$.urlParam = function(name){
	    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);    
	    if (results==null){
	       return null;
	    }
	    else{
	       return decodeURI(results[1]) || 0;
	    }
	} 
	//console.log($.urlParam('type')); 
	if ($.urlParam('type')=='email'){
		$('.deleteArea').css('display','none'); 
		//$('#acc').append('<option value="'+$.urlParam('accountName')+'">'+$.urlParam('accountName')+'</option>').attr('disabled','true');
		$scope.accountData = [{"id":$.urlParam('accountName'),"accountName":$.urlParam('accountName') }];
		$scope.projData = [{"projId":$.urlParam('projectName'),"proj_nm":$.urlParam('projectName') }];
		$scope.TrackData = [{"trackId":$.urlParam('trackName'),"track_nm":$.urlParam('trackName') }];
		 spocId = $.urlParam('spocId');
		  
		$timeout(function(){
			$scope.SelectedAccount = $.urlParam('accountName');
			$scope.SelectedProj = $.urlParam('projectName');
			$scope.SelectedTrack = $.urlParam('trackName');
			$('#acc').attr('disabled','true');
			$('#proj').attr('disabled','true');
			project_id = $.urlParam('projectId');
			getEmpForProject(project_id);   
			   
		},30);
		   
		$timeout(function(){
			GetShiftPlanMonth(project_id, $.urlParam('trackId'),'email');   
		},100);
		 
		$('#projectsubmitDiv').css('display','none');
		$('.c-previous').css('display','none');
		$('.c-next').css('display','none');
		$('#shiftRow').css('display','none');  
		$('#saveTrackwise').css('display','none');  
		$('#actionItems').css('display','none');  
	}else {
		getAllAcount();
	} 

	//End of URL
	});
  
//Context Menu
// $(function() {
//    $.contextMenu({
//        selector: '.inCalendar', 
//        callback: function(key, options) {
//        	console.log(key); 
//        	console.log(options)
//        	console.log(options.$trigger)
//            var m = "clicked: " + key;
//            window.console && console.log(m) || alert(m); 
//        },
//        items: {
//            "copy": {name: "Copy for next 5 days", icon: "copy"},
//            "quit": {name: "Quit", icon: function(){
//                return 'context-menu-icon context-menu-icon-quit';
//            }}
//        }
//    });
//
//    $('.context-menu-one').on('click', function(e){
//        console.log('clicked', this);
//    })    
//});

//End of Context Menu

  $('[data-toggle="tooltip"]').tooltip();
  $(".deleteArea").droppable({
    accept: '.inCalendar',
    drop: function(event, ui) {
    	var drop = $(this);
     	$(ui.helper).remove();
     	EmployeeCounter();
     	CheckCovered();
    }
});
  
  function getAllAcount(){
	  $http({
	      url: "getAllAcount",
	      method: "GET",
	     
	  }).then(function successCallback(response) {
		  if (response.headers('Log_Out') == "true") {
              window.location = '/tmsApp/login';
          }
		  $scope.accountData = response.data;
		  
	  }, function errorCallback(response) {
	      alert("error");
	
	  }); 
  }
//
  $("#radioTrack").change(function(){
	  $('.outCalendar').alterClass( '*-event', '');     
	  var isDraggable = $(".outCalendar").is(".ui-draggable") ;
	  if (isDraggable){  
    	  $('.outCalendar').draggable('disable');   
      } 
		$('#shift').val(''); 
	  
  if ($(this).is(':checked')){
	  //TrackWise
		  $('#trackRow').css('display','');
		  $('#projectsubmitDiv').css('display','none');
		  $('#shift').attr('disabled','disabled');   
//		  $('#copyPlan').css('display','');
//		  $('#deleteShiftPlan').css('display',''); 
		 // $('#deleteShiftPlan').css('display','display');
		  $('#allowance').css('display','');
	    }
	  else{	 
		  //ProjectWise
			  $('#trackRow').css('display','none');
			  $('#tracksubmitDiv').css('display','none');
			  $('#projectsubmitDiv').css('display','');
			  statusBtnProj(); 
			  var proj=$('#proj option:selected').val();
			  getShiftForProject(proj); 
			  GetShiftPlanMonthByProject(proj);
			  $('#track').val('');  
			  track_Id = 0; 
			  $('#copyPlan').css('display','none');
			 $('#deleteShiftPlan').css('display','none');
			 $('#allowance').css('display','none');
	   }
	  
	});
    
  $("input[type=radio]").change(function(){
	    alert( $("input[type=radio][name="+ this.name + "]").val() );
	});
  

$("#acc").change(function(){
	account_id =$('#acc option:selected').val();
	
	 getProjForAcc(account_id);
	 accountName = $('#acc option:selected').text();
	 $('#proj').val(''); 
});
$("#proj").change(function(){
	var proj=$('#proj option:selected').val();
	GetStatus(proj);
	getTrackForProj(proj);
	 getEmpForProject(proj); 
	 getShiftForProject(proj);  
	 
	 GetShiftPlanMonthByProject(proj);
	
	project_id =proj;
	projectName = $('#proj option:selected').text();
	action="save";
	
	//$('#shift').val(''); 
	$('#track').val(''); 
});

$("#track").change(function(){
	
	$('#actionItems').css('display','block');
	var track=$('#track option:selected').val();
	
	GetShiftPlanMonth(project_id, track);
    getShiftForTrack(track);
   // getEmpForTrack(track);
    statusBtnTrack(track);
    track_Id = track;
    trackName = $('#track option:selected').text(); 
    $('#shift').val(''); 
});
 
$("#shift").change(function(){
var shift = $("#shift option:selected");
$timeout(function() {
	  setDraggable($(".outCalendar"), true);
	  $('.outCalendar').draggable('enable');  
	  //filter_empwise();
  }, 30);         	 
     fillColor(shift.val(),shift.text(),shift.attr('data-shiftid'), shift.attr('data-shift_from_time'), shift.attr('data-shift_to_time'));
               
});


$('#status').click(function(){
	$('#statusDiv').slideToggle('slow');
});  

$('#copyPlan').click(function(){
	
	var track=$('#track option:selected').val();
	if (track == ""){
		bootbox.alert("Please select Track");
	}else { 
	GetShiftPlanMonth(project_id, track,'copy');
	}
});


$('#allowance').on('click', function () { 
	var track=$('#track option:selected').val();
	if (track == ""){
		bootbox.alert("Please select Track");
	}else { 
		window.open('../checkAllowance.jsp?proj_id='+CryptoJS.AES.encrypt($('#proj option:selected').val(), "Secret Passphrase")+'&trackId='+CryptoJS.AES.encrypt($('#track option:selected').val(), "Secret Passphrase")+
				'&monthId='+CryptoJS.AES.encrypt($('.c-month').attr('data-monthId'), "Secret Passphrase")+'&year='+CryptoJS.AES.encrypt($('.c-month').attr('data-year'), "Secret Passphrase"));
	}
	
	
});


$scope.allowanceLoadFunc=function(){
	var params={};
	window.location.search
	  .replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str,key,value) {
		  var decrypted = CryptoJS.AES.decrypt(value, "Secret Passphrase");
	    params[key] = decrypted.toString(CryptoJS.enc.Utf8);
	  }
	);
	$http({
	    url: "/tmsApp/tms/project/CalculateShiftDetails",
	    method: "GET",
	    params: params,
	}).then(function successCallback(response) {
		$scope.allowanceView=response.data;
		
		$timeout(function(){
        	$('#allowanceViewTable').DataTable().destroy();
              	$('#allowanceViewTable').DataTable({
					responsive:true,
					dom: 'Bfrtip',
				        buttons: [{
	                        extend: 'excel',
	                        text: 'Excel',
	                        title:'Shift Allowance Report'
				        }],
					language: {
					      emptyTable: "No Record found."
					    }
				});
				$('#allowanceViewTable').wrap('<div class="dataTables_scroll" />');
				$('th').removeClass('sorting_asc');
				if(response.data.length<=0){
					$('.buttons-excel').hide();
				}
				else{
					$('.buttons-excel').show();
				}
				
					
				},30);
		
					
	}, function errorCallback(response) {
	    alert("error");

	}); 
}

window.GetStatus = function (proj){
	/*var data = [{
	              "cabGeneratedStatus": "false",
	                  "approveStatus": "false",
	                    "draftStatus":"false",
	                        "shiftPlanId": "",
	                          "trackId": 1,
	                          "track_nm":".Net"
	            }, {
	                  "cabGeneratedStatus": "true",
	                  "approveStatus": "false",
	                  "shiftPlanId": 1573,
	                    "draftStatus":"true",
	                  "trackId": 2,
	                  "track_nm": "Informatica"
	            }]*/
	$http({
	    url: "getStatus",
	    method: "GET",
	    params: {
	              proj_id:proj,
	              month_id:$('.c-month').attr('data-monthId') ,
	              year:$('.c-month').attr('data-year') ,
	      } ,
	}).then(function successCallback(response) {
		if (response.headers('Log_Out') == "true") {
            window.location = '/tmsApp/login';
        }
	        $scope.statusData = response.data;
	        $timeout(function(){
	                  paintStatus($scope.statusData);
	                  	                 
	            },30);
	}, function errorCallback(response) {
	    alert("error");

	}); 
	} 

function paintStatus(data){ 
	console.log(data.length);   
	for (var i=0; i<=data.length-1;i++){
//		console.log(i);
//		console.log(data);
//		console.log(data[i]);
		var track_id = '#track_'+data[i].trackId;
		if (data[i].draftStatus== "true"){
			$(track_id +'> .draftStatus').removeClass('disabled').addClass('complete');
		}
		if (data[i].cabGeneratedStatus== "true"){
			$(track_id +'>.cabStatus').removeClass('disabled').addClass('complete');
		}
		if (data[i].approveStatus== "true"){
			$(track_id +'>.approvalStatus').removeClass('disabled').addClass('complete');
			trackApproved = data[i].trackId;
		}
	}
	
	$('#status').css('display','');
}



window.statusBtnTrack = function (track){ 
	$('#tracksubmitDiv').css('display','');
		
	$('#cabbtn').css('display','none'); 
	$('#sendApprbtn').css('display','none');
	$('#copyPlan').css('display',''); 
	$('#deleteShiftPlan').css('display','');  
//	$('#message').html('') 

	var data= $scope.statusData;
	for (var i=0; i<=data.length-1;i++){
		if (track == data[i].trackId){
			if (data[i].approveStatus== "true"){
				$('#tracksubmitDiv').css('display','none');   
				$('#message').addClass('alert alert-danger').html('The ShiftPlan for this Track has been sent for Approval.Hence No changes are allowed.');
				$('#copyPlan').css('display','none');
				$('#deleteShiftPlan').css('display','none');
				break;   
			}
			if (data[i].cabGeneratedStatus== "true"){
				$('#cabbtn').css('display',''); 
				$('#sendApprbtn').css('display',''); 
				$('#tracksaveDraftbtn').css('display','none'); 
				break;
				
			}  
			if (data[i].draftStatus== "true"){
				$('#cabbtn').css('display',''); 
				$('#tracksaveDraftbtn').css('display','');    
				break;
				
			}
			$('#tracksaveDraftbtn').css('display',''); 
						 
		}
		
	}
	
}

window.statusBtnProj = function (){
	var data= $scope.statusData;
	for (var i=0; i<=data.length-1;i++){
			
			if (data[i].approveStatus== "true"){
				$('#projectsaveDraftbtn').css('display','none');
				$('#message').addClass('alert alert-danger').html('Please Save the Plan TrackWise.');
				break;
			}
			   
			if (data[i].cabGeneratedStatus== "true"){
				$('#projectsaveDraftbtn').css('display','none')
				$('#message').addClass('alert alert-danger').html('Cab Generation is done for a certain Track.Please Save the Plan Trackwise.');
				break;
			}
			
			$('#projectsaveDraftbtn').css('display','');  
			
		
		
	}
}

//$('[data-popup-close]').click(function()  {
//		$('[data-popup="popup-1"]').fadeOut(1000);
//	});
//
//$('#btn-apprv').click(function(){
//  $('#shift-left-panel').css('display','none');
//  $('#cab-left-panel').css('display','block');
//  $('#ShiftCalendar .div-event-drop').draggable('disable');
//  $('#cab-manage').addClass('active');
//    $('#shift-plan').removeClass('active');
//  $('#ShiftCalendar .div-event-drop').click(function(){
//     $(this).toggleClass('cab-border');
//  });
//  $('[data-popup="popup-1"]').fadeOut(1000);
//});
//
//$('#btn-rej').click(function(){
//  $('[data-popup="popup-1"]').fadeOut(1000);
//});

function getProjForAcc(acc){
	  //alert($scope.SelectedAccount);
	  $http({
	      url: "getProjectByAccount",
	      method: "GET",
	      params: {
	                acc_id: acc         
	        } ,
	  }).then(function successCallback(response) {
		  if (response.headers('Log_Out') == "true") {
              window.location = '/tmsApp/login';
          }
		  $scope.projData = response.data;
		   $('#proj').prop('disabled', false);
		
	  }, function errorCallback(response) {
	      alert("error");

	  }); 
	  
}
function getTrackForProj(proj){
	  
	  $http({
	      url: "getTrackByProject",
	      method: "GET",
	      params: {
            proj_id: proj,
                    
        } ,
	  }).then(function successCallback(response) {
		  if (response.headers('Log_Out') == "true") {
              window.location = '/tmsApp/login';
          }
		  $scope.TrackData = response.data;
		 // console.log($scope.TrackData);
		   $('#track').prop('disabled', false);
		
	  }, function errorCallback(response) {
	      alert("error");

	  }); 
	  
}

function getShiftForTrack(track){
	
	 $http({
	      url: "getShiftByTrack",
	      method: "GET",
	      params: {
	    	  track_id:track 
          } ,
	  }).then(function successCallback(response) {
		  if (response.headers('Log_Out') == "true") {
              window.location = '/tmsApp/login';
          }
		  $scope.ShiftData = response.data;
		  $('#shift').prop('disabled', false);
		 
		  resp = response.data;
		  CheckCovered();
	  }, function errorCallback(response) {
	      alert("error");

	  }); 

}

function getShiftForProject(proj){
	
	 $http({
	      url: "getShiftForProject",
	      method: "GET",
	      params: {
	    	  proj_id:proj 
         } ,
	  }).then(function successCallback(response) {
		  if (response.headers('Log_Out') == "true") {
              window.location = '/tmsApp/login';
          }
		  $scope.ShiftData = response.data;
		  $('#shift').prop('disabled', false);
		 
		  resp = response.data;
		  CheckCovered();
	  }, function errorCallback(response) {
	      alert("error");

	  }); 

}


function getEmpForProject(proj){
	  $('#tableEmp').css('display','');
	
	 $http({ 
	      url: "getEmployeeByProject", 
	      method: "GET",
	      params: {
	    	  proj_id: proj,
	    	  spocId:spocId
          } ,
	  }).then(function successCallback(response) {
		  if (response.headers('Log_Out') == "true") {
              window.location = '/tmsApp/login';
          }
		  $scope.EmpData =response.data;
		  $('#tableEmp').css('display','');
		  $timeout(function() {
			 // setDraggable($(".div-event-drop"), true);
			  filter_empwise();
			  filter_trackwise();
      }, 30);      
		  
		 
	  }, function errorCallback(response) {
	      alert("error");    

	  }); 

}


$('#deleteShiftPlan').click(function(){
	
	if ($('#track option:selected').val() == ""){
		bootbox.alert({
            message: "<label>Please select Track.</label>"
        });
	} else {  
   
	 bootbox.confirm({    
		    
		    message: "Are you sure to delete the shiftplan for ",
		    buttons: {
		        cancel: {
		            label: '<i class="fa fa-times"></i> Cancel'
		        },
		        confirm: {
		            label: '<i class="fa fa-check"></i> Confirm'
		        }
		    },
		    callback: function (result) {
		    	if (result == true){
		    		//Send Approval
		    		deleteShiftPlan(); 
		    		
		    	} else {
		    		//Dont send for Approval  
		    	}
		    }
		});
	}
});    


function deleteShiftPlan(){
    
	
	 $http({ 
	      url: "deleteShiftPlan", 
	      method: "GET",      
	      params: {
				proj_id: $('#proj option:selected').val(),
				track: $('#track option:selected').val(),  
				month_id:$('.c-month').attr('data-monthId') ,
				year:$('.c-month').attr('data-year') } ,
	  }).then(function successCallback(response) { 
		  if (response.headers('Log_Out') == "true") {
              window.location = '/tmsApp/login';
          }
		  $('#message').removeClass().addClass('alert alert-danger').html('Deleted Successfully');
				 
	  }, function errorCallback(response) {
	      alert("error");

	  }); 

}


function filter_empwise(){
  $(".filter_emp").click(function () {
	  var isDraggable = $(".outCalendar").is(".ui-draggable") ;
	 
    var showAll = true;
      $('#ShiftCalendar .inCalendar').addClass('blurgrayscale');
      $('.inCalendar').draggable('disable');
      if (isDraggable){  
    	  $('.outCalendar').draggable('disable');   
      } 
      $('.inCalendar .trackCheck').attr('disabled','disabled');
  	  
     $('.filter_emp').each(function () {
         if ($(this)[0].checked) {
            showAll = false;
             var value = $(this).val();
             $('#ShiftCalendar .inCalendar[data-empid="' + value + '"]').removeClass('blurgrayscale').show();
         	
         } 

     });
     if(showAll){
         $('#ShiftCalendar .inCalendar').removeClass('blurgrayscale');
         $('.inCalendar').draggable('enable');
         if (isDraggable){  
        	 $('.outCalendar').draggable('enable');
         }
         $('.inCalendar .trackCheck').removeAttr('disabled','disabled');
     	
     }  
 });
}
   
function filter_trackwise(){
	  $(".filter_track").click(function () {
		  var isDraggable = $(".outCalendar").is(".ui-draggable") ;
	    var showAll = true;
	      $('#ShiftCalendar .inCalendar').addClass('blurgrayscale');
	      $('.inCalendar').draggable('disable');
	      if (isDraggable){  
	    	  $('.outCalendar').draggable('disable');
	      }	
	      $('.inCalendar .select-value').popover('disable');
	     $('.filter_track').each(function () {
	         if ($(this)[0].checked) {
	            showAll = false;
	           // $('.select-value').off('click');
	          
	             var value = $(this).val();
	             $('#ShiftCalendar .inCalendar[data-trackid="' + value + '"]').removeClass('blurgrayscale').show();
	             //  $('#ShiftCalendar .trackCheck')
	            // console.log($('#ShiftCalendar .trackCheck[value='+value+']').is(':checked'));
	          $('#ShiftCalendar .inCalendar .popover_select_values .trackCheck[value='+value+']').each(function(){
	            	// $(this).removeClass('blurgrayscale').show();
	        	  // console.log($(this).closest('.w-emp'));
	        	 if ($(this).is(':checked')){
	        		 console.log($(this).closest('.inCalendar'));
	        		 $(this).closest('.inCalendar').removeClass('blurgrayscale').show();
	        		  
	        	 }
	             });
	         }

	     });
	     if(showAll){  
	         $('#ShiftCalendar .inCalendar').removeClass('blurgrayscale');
	         $('.inCalendar').draggable('enable');
	         if (isDraggable){  
	        	 $('.outCalendar').draggable('enable');
	         }
	         $('.inCalendar .select-value').popover('enable');
	     }
	 });
	}


//-----------------
  function fillColor(shift_master_nm,shift_name,shift_id, from_time, to_time ){
	  var shared_class = '';
	  var EmpEvent = $('#tableEmp .outCalendar');
	   EmpEvent.removeAttr('data-shift');
	   EmpEvent.attr('data-shift',shift_master_nm);
	   
	   EmpEvent.removeAttr('data-shiftid');
	   EmpEvent.attr('data-shiftid',shift_id);
	   
	  EmpEvent.removeAttr('title');
	  EmpEvent.attr('title',shift_name);
	  
	  $('#shift_details').html('<span class="glyphicon glyphicon-time"></span>  '+from_time+' - '+to_time);
    switch(shift_master_nm) {
      case '1S':
    	  EmpEvent.alterClass( '*-event', 'first-event');
    	  shared_class = 'shared-first-event';
          break;
      case '2S':
            // $('#tableEmp tbody').removeClass().addClass('w-shift');
            // $('#tableEmp .div-event-drop').removeClass('second-event night-event').addClass('w-event');
          EmpEvent.alterClass( '*-event','second-event');
          shared_class = 'shared-second-event';
          break;

      case 'RS':
          EmpEvent.alterClass( '*-event','regular-event');
          shared_class = 'shared-regular-event';
          break;

      case 'NS':
          EmpEvent.alterClass( '*-event','night-event');
          shared_class = 'shared-night-event';
          break;
      case 'W':
          EmpEvent.alterClass( '*-event','weekoff-event');
          shared_class = 'shared-weekoff-event';
          break;
      case 'L':
          EmpEvent.alterClass( '*-event','leave-event');
          shared_class = 'shared-leave-event';
          break;
      case 'PH':
          EmpEvent.alterClass( '*-event','publicHol-event');
          shared_class = 'shared-publicHol-event';
          break;
      case 'H':
          EmpEvent.alterClass( '*-event','usHol-event');
          shared_class = 'shared-usHol-event';
          break;
      case 'C':
          EmpEvent.alterClass( '*-event','compoff-event ');
          shared_class = 'shared-compoff-event';
          break;
      case 'OS':
          EmpEvent.alterClass( '*-event','night-event');
          shared_class = 'shared-night-event';
          break;
}
    
    
     EmpEvent.removeAttr('data-shared-class');
	  EmpEvent.attr('data-shared-class',shared_class);
  }

  var jsonObj;
function createMonthlyJson(month_nm, month_id, year){
	 var other_trck;
var empObj;
var trckObj;
 jsonObj = [];

    for(var i=0; i<=31; i++){
     
      var empEvent = $('#'+i).find('.inCalendar').length;
    
      if (empEvent > 0) {
    	 var shiftPartition = $('#'+i).children().length;
    	 var shiftDiv = $('#'+i).find('.empEvent');
	    	 shiftDiv.each(function () {
	    		 if (shiftDiv.children().length > 0) {
	    			 $(this).find(".inCalendar").each(function () {
	    				 other_trck = [];
//							console.log($(this).text());
//							console.log($(this).attr('data-shift'));
	    				// console.log($(this).find('a').text());
	    				
	    				    empObj = {};
							empObj.date = i;
							empObj.emp_init = $(this).find('a').text();
							empObj.shift_id = $(this).attr('data-shiftid');
							empObj.track_id = $(this).attr('data-trackid');
							empObj.emp_id = $(this).attr('data-empId');
							empObj.month = month_nm;
							empObj.month_id = month_id;
							empObj.year = year;
							empObj.other_trck = [];
							
						
							var a = $(this).find('.popover_select_values');
							if($(a).find('.trackCheck').length > 0 ){
		    					
								$(a).find('.trackCheck').each(function(){
		    						trckObj = {};
			    					 if(($(this).prop('checked'))){
			    						trckObj.trck_id = $(this).val();
			    						trckObj.trck_nm = $(this).data('track');
			    						 empObj.other_trck.push(trckObj);
			    					 }
			    					        
			    					 
			    				 });
		    					
		    					
		    				 }
							jsonObj.push(empObj);
		    				 
							
		            });
	    		 }
	    	 });

         }
     // jsonObj.push(dateObj);
      }
   console.log(jsonObj);
   
 
       
}

function SaveShiftPlan(track, gen_cab_req){
	
	console.log(jsonObj);
	
	track = (track==undefined || track ==null)? 0 : track;
	console.log(track);   
	var deferred = $q.defer();
	$http({
	    url: "saveShiftDetailsProject",
	    method: "POST",
	    data:jsonObj,
	    params: {
	    	  action:action,
	    	  acc_id : account_id,
	    	  month_id:$('.c-month').attr('data-monthId') ,
	          track_id: track,
	          proj_id:project_id,
	          year:$('.c-month').attr('data-year'), 
	          cab_req: gen_cab_req
        } ,
	}).then(function successCallback(response) {
		if (response.headers('Log_Out') == "true") {
            window.location = '/tmsApp/login';
        }
		  deferred.resolve(response);
		  if (gen_cab_req == false){
			  $('#message').removeClass('alert alert-success alert-danger').addClass('alert alert-success').html('Saved Successfully');
		  } else {
			  $('#message').removeClass('alert alert-success alert-danger').addClass('alert alert-success').html('Cab Generated Successfully');
		  }
//		$('#cabbtn').css('display','');      
//		$('#tracksaveDraftbtn').css('display','none');   
		GetStatus(project_id);
		$timeout(function(){
			if (track == 0){
				statusBtnProj(); 
			}else {
				statusBtnTrack(track);
			}  
		},100);

	}, function errorCallback(response) {
		$('#message').removeClass().addClass('alert alert-danger').html('Error in Saving');
	}); 
	 return deferred.promise;
	 }  


	$('#ShiftCalendar').eCalendar({
        weekDays: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'], 
        months: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        textArrows: {previous: '<', next: '>'},
        eventTitle: 'Events',
        url: '',
        events: '',
        firstDayOfWeek: 0
      });

	//Get Plan by Proj
	 window.GetShiftPlanMonthByProject = function (proj){
		 $('.inCalendar').remove();
		 $('#message').removeClass('alert alert-success alert-danger').html('');
      	 $http({   
      	      url: "getShiftDetailsByProject",
      	      method: "GET",
      	      params: {
              month_id:$('.c-month').attr('data-monthId') ,
              proj_id: proj,
              year:$('.c-month').attr('data-year') ,
              
      	      } , 
      	  }).then(function successCallback(response) {
      		 // $('#message').css('display','none').html('');          	
      		if (response.headers('Log_Out') == "true") {
                window.location = '/tmsApp/login';
            }
      		  if (response.data.length > 0){
      			  LoadShiftPlan(response.data[0]);
      			  action = 'update';
      			$timeout(function() {
      	   		 setDraggable($(".cal"), false);
      	   		 EmployeeCounter();
      	   		 CheckCovered();
      	   		 statusBtnProj();
      		     }, 100); 
      		  } else{
      			 action = 'save';
      		  }
      	
      		  
      	  }, function errorCallback(response) {
       	      alert("error");

       	  }); 
 
        }
  
	 
	 //Get Plan by TRAck 
    window.GetShiftPlanMonth = function (project_id, track,type){
    	var month_id= $('.c-month').attr('data-monthId');
    	
    		if (type =="copy"){
    			month_id =  parseInt($('.c-month').attr('data-monthId')) - 1;
    		}
    		
    	$('.inCalendar').remove();
    	$('#message').removeClass('alert alert-success alert-danger').html('');
    	
   	 $http({
   	      url: "getShiftDetails",
   	      method: "GET",
   	      params: {
           month_id: month_id ,
           track_id: track,
           year:$('.c-month').attr('data-year') ,
           proj_id:project_id,
           
   	      } , 
   	  }).then(function successCallback(response) {
   		if (response.headers('Log_Out') == "true") {
            window.location = '/tmsApp/login';
        }
   		  if (response.data.length > 0){
   			  LoadShiftPlan(response.data[0]);
   			  action = 'update';
   			$timeout(function() {
   	   		 setDraggable($(".cal"), false);
   	   		 if (type =="email"){
   	   			 $('.inCalendar').draggable('disable');
   	   		 }
   	   		 CheckCovered();
   		     }, 100); 
   		  } else{
   			 action = 'save';
   		  }
   	
   		 EmployeeCounter();
   	  }, function errorCallback(response) {
   	      alert("error");

   	  }); 

    }
      
    window.AttachTrack = function(){
    	$('.inCalendar .select-value').popover({
    	    html: true,
    	    content: function () {
    	       // return $('.popover_select_values').html();
//    	    	console.log($(this));
//    	    	console.log($(this).parents());
//    	    	console.log($(this).parent().parent());
//    	    	console.log($(this).parent().parent().find('.popover_select_values'));
//    	    	console.log($(this).parent().parent().find('.popover_select_values').html());
    	    	return $(this).parent().parent().find('.popover_select_values').html();
    	    	
    	    	//return $(this).next().html();
    	    }
    	
    	});         	
    }
    
    window.CheckTrack = function(cloned){
    	var track=$('#track option:selected').val();
    	  
    	if (track !=null || track !=undefined || track != ""){
			var div = $(cloned).find('.popover_select_values');
			var checkbox = $(div).find("input[type=checkbox][value='"+track+"']") ;
			
			if($(checkbox).length > 0){
				checkbox.prop('checked',true).prop('disabled',true);
				checkbox.attr('checked',true).prop('disabled',true);     
				var class_nm = $(cloned).data('shared-class');
				$(cloned).addClass(class_nm);
			}
		}
  	          
    }
     
//  
    $('html').on('click', function(e) {
		  if (typeof $(e.target).data('original-title') == 'undefined' &&
		     !$(e.target).parents().is('.popover.in')) {
		    $('[data-original-title]').popover('hide');
		  }
		});
    
    $(document).on('change', '.popover-content input[type=checkbox]', function() {
    	var id = $(this).val();
    	 var other_track  = [];
    	
//    	var mainDiv = $(this).parentsUntil('.w-emp');
//    	console.log(mainDiv);
    	 var class_nm = $(this).closest('.w-emp').data('shared-class');
    	 var main_div = $(this).closest('.w-emp');
    	 var classFlag = false;
    	var div = $(this).parentsUntil('.empEvent').find('.popover_select_values');
    	console.log("main"+ $(main_div));
    	console.log("div"+div);  
    	
      	    if ($(this).prop('checked')){    
	    		$(div).find("input[type=checkbox][value='"+id+"']").prop('checked',true);
	    		$(div).find("input[type=checkbox][value='"+id+"']").attr('checked',true);
	    		    other_track.push(id);    		 
	    	} else {
	    		$(div).find("input[type=checkbox][value='"+id+"']").prop('checked',false); 
	    		$(div).find("input[type=checkbox][value='"+id+"']").attr('checked',false);
	    			    		
	    	}
      	       
      	 var grp =  $(div).find("input[type=checkbox]");  
      	 
      	grp.each(function(){
    		//console.log($(this));    
    		if ($(this).prop('checked')){
    			classFlag = true;  
    		} 
    	});   
      	 
      	if (classFlag == true){ 
    		$(main_div).addClass(class_nm);
    	}else{
    		$(main_div).removeClass(class_nm);
    	}
      	
    	    
 });  
    
    window.EmployeeCounter = function(){
    	//console.log($scope.EmpData.length);
    	
    	//console.log($scope.EmpData.length);
    	
    	for (var m=0; m < $scope.EmpData.length;m++){
    		
    		//console.log($scope.EmpData[m]);
    		for (var a=0; a< $scope.EmpData[m].emp.length;a++){
    		//	console.log($scope.EmpData[m].emp[a].empid);
    			var len = $("#ShiftCalendar").find("[data-empid='"+$scope.EmpData[m].emp[a].empid+"']").length
    			console.log(len);
    			$('#tableEmp').find('span.'+$scope.EmpData[m].emp[a].empid).html(len);
    		}
    		
    		//$("#ShiftCalendar").find("[data-empid='1']").length;
    	}
    	
    }
    

    function LoadShiftPlan(data){
    	
    	var shift_class = '';
    	var shared_class = '';

    	var track=$('#track option:selected').val();
    	
    	      for (var c=0; c<=data["list"].length - 1 ;c++){
    	        var date = (data["list"][c].date);
    	      
    	        for (var m=0; m <= (data["list"][c].shiftPlans.length - 1);m++){
    	        	 var cnt = 0;
					 var shift_per_date = '';
					 var other_track = '';
					 var final_class = '';
					 var shiftPlan = data["list"][c].shiftPlans[m];
					  var shift_nm = data["list"][c].shiftPlans[m].shift_nm;
    	          switch(data["list"][c].shiftPlans[m].shift_nm) {
    	            case '1S':
    	                  shift_class = 'first-event';
    	                  shared_class = 'shared-first-event';
    	                  break;
    	            case '2S':
    	                  shift_class = 'second-event';
    	                  shared_class = 'shared-second-event';
    	                  break;
    	            case 'RS':
    	                    shift_class = 'regular-event';
    	                    shared_class = 'shared-regular-event';
    	                    break;
    	            case 'NS':
		                    shift_class = 'night-event';
		                    shared_class = 'shared-night-event';
		                    break;
    	            case 'W':
		                    shift_class = 'weekoff-event';
		                    shared_class = 'shared-weekoff-event';
		                    break;
    	            case 'L':
							shift_class = 'leave-event';
							 shared_class = 'shared-leave-event';
							break;
    	            case 'PH':
		                    shift_class = 'publicHol-event';
		                    shared_class = 'shared-publicHol-event';
		                    break;
    	            case 'H':
		                    shift_class = 'usHol-event';
		                    shared_class = 'shared-usHol-event';
		                    break;
    	            case 'C':
		                    shift_class = 'compoff-event';
		                    shared_class = 'shared-compoff-event';
		                    break;
    	            case 'OS':
		                    shift_class = 'night-event';
		                    shared_class = 'shared-night-event';
		                    break;
	                  
	                
    	                    
    	                  
    	          }
    	        
    	         // console.log(data["list"][c].shiftPlans[m].other_trck);
    	          
    	          other_track += '<div style="display:none" id="hi" class="popover_select_values"> <ul style="list-style-type:none;margin: 0;padding: 0;">';
 				 for(var b=0; b<= (shiftPlan.other_trck.length - 1) ;b++){
 					 
 					 console.log(shiftPlan.other_trck[b].trackAssignedTo);
 				var assigned_trck = '';
 				var disable_trck = '';
 				 if(shiftPlan.other_trck[b].trackAssignedTo == "true"){
 					assigned_trck = 'checked';
 					cnt++;
 				 } 
 				 
 				 if(shiftPlan.other_trck[b].trck_id == track || shiftPlan.other_trck[b].trck_id == trackApproved){
 					 disable_trck = 'disabled';
 					 $('.outCalendar').find("input[type=checkbox][value='"+trackApproved+"']").attr('disabled','disabled');
 				 }
 				   				 
 				other_track += '<li><label class="chkLabel">'+shiftPlan.other_trck[b].trck_nm+'</label>';
 				other_track += '<input class="trackCheck" data-track="'+shiftPlan.other_trck[b].trck_nm+'"  type="checkbox"  value="'+shiftPlan.other_trck[b].trck_id+'"'+ disable_trck+' '+assigned_trck+' /></li>'
 				 }    
 				other_track += '</ul></div>';
 				 
 				if (cnt > 0){
 					final_class  = shift_class + "  "+ shared_class;
 					   
 				} else{   
 					final_class = shift_class;
 				}
				  shift_per_date += '<div class="inCalendar w-emp cal '+final_class+'" data-shift="'+shift_nm+'" data-shared-class="'+shared_class+'" title ="'+shift_nm+'" data-shiftid="'+data["list"][c].shiftPlans[m].shift_id+'" data-trackid="'+data["list"][c].shiftPlans[m].track_id+'" data-toggle="tooltip"  data-empid="'+data["list"][c].shiftPlans[m].emp_id+'"><div class=""><a href="#" data-toggle="dropdown" class="dropdown-toggle select-value ng-binding" data-placement="bottom" data-original-title="" title="">'+data["list"][c].shiftPlans[m].emp_init+' </a></div>';
				  shift_per_date +=other_track;       
					  $('#'+date).find('div.'+shift_nm).append(shift_per_date);
    	      }      
    	        
    	       // $('#'+date).find('div.'+shift_nm).append(shift_per_date);
    	    }
    	      AttachTrack();

    	}

    var	slots_data = {};
	function initShift(){
		console.log('in init');
		var month_id = $('.c-month').attr('data-monthid');
		var no_of_days = $('.c-month').attr('data-noOfDays');
		
		var days_in_month = 0; 
		
		for (var m = 1 ;m <= no_of_days ;m++){
			slots_data[m]= {};
		
		   for(var a=0 ;a<resp.length;a++){    
			   var len = $('#'+m+'>.'+resp[a].shift_master_nm).children().length;
			   if ((resp[a].shift_type).toLowerCase() == 'normal'){
				   if (len > 0){        
						 slots_data[m]['slot_'+resp[a].shift_master_nm] = true;
					 }
				   else {
					   slots_data[m]['slot_'+resp[a].shift_master_nm] = false;
					 }
		   }
			} 
				
		}
		console.log(slots_data);
	}
	
		
 window.CheckCovered = function (){
	 initShift();
	 var no_of_days = $('.c-month').attr('data-noOfDays');
	 var date = missing_slots_in_month();
	 console.log(date);
	 for (var m = 1 ;m<=no_of_days ;m++){
		 if(jQuery.inArray(m, date) == -1){
			 $('[data-date="'+m+'"]').find('span.glyphicon').removeClass('fail').addClass('success').css('display','inline-block').attr('title','All Shifts Covered');
		 } else{
			 $('[data-date="'+m+'"]').find('span.glyphicon').removeClass('success').addClass('fail').css('display','inline-block');
		 }
	 }
	 
		
	 
 }
 
 

 function missing_slots_in_month() {
		var dates_where_slots_are_missing = []

		var month_data = slots_data;
		var date_already_added;
	//	console.log("month"+month_data);

		Object.keys(month_data).forEach(function (day_of_month) { 
		//	console.log(day_of_month);

		    var day_slots_data = month_data[day_of_month];
			
			var date_already_added = false;
			Object.keys(day_slots_data).forEach(function(slot_name) { 
  	
			    	if (day_slots_data[slot_name] == false && date_already_added == false) {
			    		day_of_month = parseInt(day_of_month);
			    		dates_where_slots_are_missing = dates_where_slots_are_missing.concat([day_of_month]);
			    		date_already_added = true;
			    	}
			    	
			});
						
		});
//console.log("dates" + dates_where_slots_are_missing)
		return dates_where_slots_are_missing;
	}
				



// $("#saveDraft").click(function(){
// 	if($('.inCalendar').length == 0) {
// 		$('#message').addClass('alert alert-danger').html('* Please Enter Data in the Calendar');
// 		return 0;
// 	} else{
// 		var month_nm = $('.c-month').attr('data-monthname');
// 		var month_id = $('.c-month').attr('data-monthid');
// 		var year = $(this).attr('data-year');
// 		$.when(createMonthlyJson(month_nm, month_id, year)).done(SaveShiftPlan(track_Id));
// 	}
// 	
// 	//functionOne().done( functionTwo() );
//  //  $('[data-popup="popup-1"]').fadeIn(1000);
//    
//  
// });


 //generate cab req and  send Email 


// $("#submit").click(function(){
//	 $('.inCalendar').css('z-index','0');
//	
//	 if($('.inCalendar').length == 0) {
//	 		$('#message').addClass('alert alert-danger').html('* Please Enter Data in the Calendar');
//	 		 $('.inCalendar').css('z-index','99');
//	 		return 0;
//	 	} else{
//		 bootbox.confirm({
//			    
//			    message: "Do you want to Generate Cab Requests?",
//			    buttons: {
//			        cancel: {
//			            label: '<i class="fa fa-floppy-o"></i> Save as Draft'
//			        },
//			        confirm: {
//			            label: '<i class="fa fa-taxi"></i> Generate Cab Requests'
//			        }
//			    },
//			    callback: function (result) {
//			    	if (result == true){
//			    		// Save and generate cab requests
//			    		var month_nm = $('.c-month').attr('data-monthname');
//			     		var month_id = $('.c-month').attr('data-monthid');
//			     		var year = $('.c-month').attr('data-year');
//			     		var gen_cab_req = true;
//			     		$.when(createMonthlyJson(month_nm, month_id, year)).done(SaveShiftPlan(track_Id, gen_cab_req));
//			    	} else {
//			    		// Save as a Draft
//			    		var month_nm = $('.c-month').attr('data-monthname');
//			     		var month_id = $('.c-month').attr('data-monthid');
//			     		var year = $('.c-month').attr('data-year');
//			     		var gen_cab_req = false;
//			     		$.when(createMonthlyJson(month_nm, month_id, year)).done(SaveShiftPlan(track_Id, gen_cab_req));
//			    	}
//			    }
//			});
//	 	}
//  
// });
//
// 
// $('#sendAppr').click(function(){
//	 bootbox.confirm({
//		    
//		    message: "You cannot change the ShiftPlan after Sending for Approval.Do you want to continue ?",
//		    buttons: {
//		        cancel: {
//		            label: '<i class="fa fa-times"></i> Cancel'
//		        },
//		        confirm: {
//		            label: '<i class="fa fa-check"></i> Confirm'
//		        }
//		    },
//		    callback: function (result) {
//		    	if (result == true){
//		    		// Lock shift plan and send for Approval
//		    	} else {
//		    		//Dont send for Approval  
//		    	}
//		    }
//		});
//	 
//}); 
// 
// 
 
 //Save As Draft
 $("#projectsaveDraftbtn, #tracksaveDraftbtn").click(function(){
	 $('.inCalendar').css('z-index','0');
	
	// Save as Draft
	var month_nm = $('.c-month').attr('data-monthname');
	var month_id = $('.c-month').attr('data-monthid');
	var year = $('.c-month').attr('data-year');
	var gen_cab_req = false;
	$.when(createMonthlyJson(month_nm, month_id, year)).done(SaveShiftPlan(track_Id, gen_cab_req));
	
  
 });
 
 //Generate Cab Requests
 $("#cabbtn").click(function(){
	 $('.inCalendar').css('z-index','0');
	
	// Save as Draft
	var month_nm = $('.c-month').attr('data-monthname');
	var month_id = $('.c-month').attr('data-monthid');
	var year = $('.c-month').attr('data-year');
	var gen_cab_req = true;
	$.when(createMonthlyJson(month_nm, month_id, year)).done(SaveShiftPlan(track_Id, gen_cab_req));
	email();
  
 });
 
 //Send for Approval
 
 $('#sendApprbtn').click(function(){
	 bootbox.confirm({
		    
		    message: "ShiftPlan cannot be edited once 'Sent for Approval'.Do you want to continue ?",
		    buttons: {
		        cancel: {
		            label: '<i class="fa fa-times"></i> Cancel'
		        },
		        confirm: {
		            label: '<i class="fa fa-check"></i> Confirm'
		        }
		    },
		    callback: function (result) {
		    	if (result == true){
		    		//Send Approval
		    		sendApproval();
		    		
		    	} else {
		    		//Dont send for Approval  
		    	}
		    }
		});
	 
}); 
 
 
 function sendApproval(){
		$http({
		    url: "ApproveShiftDetails",
		    method: "POST",
		    data:jsonObj,
		    params: {
		    	  monthId:$('.c-month').attr('data-monthId') ,
		    	  trackId: track_Id,  
		          proj_id:project_id,
		          year:$('.c-month').attr('data-year')
		          
	        } ,
		}).then(function successCallback(response) {
			if (response.headers('Log_Out') == "true") {
                window.location = '/tmsApp/login';
            }
			$('#message').removeClass().addClass('alert alert-success').html('Sent For Approval Successfully');
			GetStatus(project_id);
			$timeout(function(){
				if (track == 0){
					statusBtnProj(); 
				}else {
					statusBtnTrack(track);
				}
			},100);
		
		}, function errorCallback(response) {
			$('#message').removeClass().addClass('alert alert-danger').html('Error in Saving'); 
		}); 
 }

function email(){
	 $http({
	      url: "sendShiftPlannerEmailToEmployee",
	      method: "POST",
	      params: {
	    	  month_id:$('.c-month').attr('data-monthId') ,
	           trackName: trackName,
	          year:$('.c-month').attr('data-year'),
	          projectName:projectName,
	          accountName:accountName,
	          trackId:track_Id,
	          projectId:project_id,
	        } ,
	  }).then(function successCallback(response) {
		  if (response.headers('Log_Out') == "true") {
              window.location = '/tmsApp/login';
          }
		  $scope.projData = response.data;
		   $('#proj').prop('disabled', false);
		
	  }, function errorCallback(response) {
	      alert("error");

	  }); 
 }

}]);