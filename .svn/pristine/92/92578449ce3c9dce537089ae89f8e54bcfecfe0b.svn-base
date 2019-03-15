"use strict";

angular.module("tmsApp").controller("financeController", ["financeService","$scope", "$rootScope", "$timeout", "$filter", "$http", function(financeService,$scope, $rootScope, $timeout, $filter, $http) {

	
	$scope.financePanel=function(evt, view) {
	       var i, tabcontent, tablinks;
	       tabcontent = document.getElementsByClassName("tabcontent");
	       for (i = 0; i < tabcontent.length; i++) {
	           tabcontent[i].style.display = "none";
	       }
	       tablinks = document.getElementsByClassName("tablinks");
	       for (i = 0; i < tablinks.length; i++) {
	           tablinks[i].className = tablinks[i].className.replace(" active", "");
	       }
	       document.getElementById(view).style.display = "block";
	       evt.currentTarget.className += " active";
	       }

    //-----------------------------------------Holiday------------------------------------------------------
    var year, location;
    $('#holidayLocation').on('change', function() {
        $('#holidayYear').attr('disabled', false);
    });
    $('#holidayYear').on('change', function() {
        year = $(this).val();
        location = $('#holidayLocation').val();
        $scope.getHolidayList(year, location);
    });
    $scope.holidayDataTable=function(){
    	 $timeout(function () {
 			$('#createHoliday').DataTable({
 				responsive:true,
 				ordering: false,
 				language: {
 				      emptyTable: "No Data available."
 				    },
 			});
 			$('#createHoliday').wrap('<div class="dataTables_scroll" />');
 			$('th').removeClass('sorting_asc');
 			$timeout(function(){
 				$('.ajax-loader').css("visibility", "hidden");
 			},50);
 			
 			$scope.holidayChange=function($event){
 				console.log($scope.holidayList);
           	 var el = event.target
           	    console.log(el);
           	console.log(event.target.value);
           	var id=$(el).closest('tr').attr('id');
           	var cls=$(el).attr('class');
           	cls=cls.split(' ');
           	var index=cls[2];
           	cls=cls[1];
           	 if(el.checked){
               $('#createHoliday #'+id+' .holidayType').not(el).prop('checked', false);
           	 }
           	 if(cls=="nationalHoliday"){
           		 $scope.holidayList[index].isNationalHoliday=true;
           		 $scope.holidayList[index].isPublicHoliday=false;
           	 }
           	 else{
           		 $scope.holidayList[index].isNationalHoliday=false;
          		 $scope.holidayList[index].isPublicHoliday=true;
           	 }
           	console.log($scope.holidayList[index]);
           }
 			
 			
 		}, 30);
    }

    $scope.addHoliday = function(row) {
    	$('.ajax-loader').css("visibility", "visible");
    	$('#createHoliday').DataTable().destroy();
        $('.noRecord').css('display', 'none');
        $scope.holidayList.push({
            "holidayDate": "",
            "holidayName": "",
            "isPublicHoliday": false,
            "isNationalHoliday": false,
            "location": {"locationId":location},
            "year": year,
            "holidayId": null
        });
        $scope.holidayDataTable();
        console.log($scope.holidayList);
    }

    $scope.getHolidayList = function(year, location) {
    	location=location.toString();
        $http({
            url: "/tmsApp/tms/financePage/getHolidays",
            method: "GET",
            params: {
                location:location,
                year: year
            }
        }).then(function successCallback(response) {
        	if(response.headers('Log_Out')=="true"){
				window.location='/tmsApp/login';
			}
        	$('#createHoliday').DataTable().destroy();
            if (response.data != "No Data") {
                $('.noRecord').css('display', 'none');
                console.log(JSON.stringify(response.data));
                response.data.sort(function(a,b) {
              	 return a.holidayDate-b.holidayDate;
              	  // return a.localeCompare(b);         // <-- alternative 
              	});
              
                for (var i = 0; i < response.data.length; i++) {
                    var date = moment(response.data[i].holidayDate); //Get the current date
                    response.data[i].holidayDate = date.format("YYYY-MM-DD");
                }
                
                $scope.holidayList = response.data;
                $('.submitBtn').val('Update');
            } else {
                $('.submitBtn').val('Submit');
                $('.noRecord').css('display', 'block');
                $scope.holidayList = [];
            }
            $scope.holidayDataTable();
            $('.holidayDiv').removeClass('hidden');
            console.log(response);

        }, function errorCallback(response) {
        	bootbox.alert({
                message: "<label>Something went wrong.Inernal error.</label>"
            });
        });
    }
    
    $scope.createHolidayList = function() {
    	var checkHol;
    	
    	for(var i=0;i<$scope.holidayList.length;i++){
    		if($scope.holidayList[i].isNationalHoliday==false&&$scope.holidayList[i].isPublicHoliday==false){
    			checkHol=false;
    		}
    		if($scope.holidayList[i].location!=undefined){
    		$scope.holidayList[i].location.locationId=parseInt($scope.holidayList[i].location.locationId);
    		}
    		else{
    			$scope.holidayList[i].location={"locationId":parseInt(location)};
    		}
    	}
    	
    	if(checkHol==false){
    		$scope.class = "alert-danger";
            $(".holidayAlert").css("display", "block");
            $scope.holidayAlertStatus = 'Please select any holiday type.';
            $(".holidayAlert").delay(3000).fadeOut();
            $timeout(function() {
                $scope.holidayAlertStatus = "";
            }, 3000);
    	}
    	else{
        $scope.holiday.holidayList = $scope.holidayList;
        
        for (var i = 0; i < $scope.holidayList.length; i++) {
            var date = moment($scope.holidayList[i].holidayDate); //Get the current date
            $scope.holidayList[i].holidayDate = date.format("YYYY-MM-DD");
        }
        console.log($scope.holidayList);

        $http({
            url: "/tmsApp/tms/financePage/saveOrUpdateHolidays",
            method: "POST",
            data: $scope.holidayList
        }).then(function successCallback(response) {
        	if(response.headers('Log_Out')=="true"){
				window.location='/tmsApp/login';
			}
        	$('#createHoliday').DataTable().destroy();
            console.log(response);
            $scope.getHolidayList($scope.holidayList[0].year, $scope.holidayList[0].location.locationId);
            $scope.class = "alert-success";
            $(".holidayAlert").css("display", "block");
            $scope.holidayAlertStatus = 'Saved successfully.';
            $(".holidayAlert").delay(3000).fadeOut();
            $timeout(function() {
                $scope.holidayAlertStatus = "";
            }, 3000);

        }, function errorCallback(response) {
        	bootbox.alert({
                message: "<label>Something went wrong.Inernal error.</label>"
            });
        });
    	}
    }

    $scope.deleteHoliday = function(id, holidayId, location, year) {
        bootbox.confirm("<label>Are you sure want to Delete?</label>", function(result) {
            if (result == true) {
            	$('.ajax-loader').css("visibility", "visible");
            	$('#createHoliday').DataTable().destroy();
                if (holidayId == null) {
                    $scope.holidayList.splice(id, 1);
                    $scope.$apply();
                    $scope.holidayDataTable();
                } else if (holidayId != null) {
                    $http({
                        url: "/tmsApp/tms/financePage/deleteHolidays",
                        method: "POST",
                        params: {
                            holidayId: holidayId
                        }
                    }).then(function successCallback(response) {
                    	if(response.headers('Log_Out')=="true"){
							window.location='/tmsApp/login';
						}
                    	
                        console.log(response);
                        $scope.holidayList.splice(id, 1);
                        //$scope.holidayDataTable();
                        $scope.holidayList=$scope.holidayList;
                        $scope.class = "alert-success";
                        $(".holidayAlert").css("display", "block");
                        $scope.holidayAlertStatus = 'Deleted successfully.';
                        $(".holidayAlert").delay(3000).fadeOut();
                        $timeout(function() {
                            $scope.holidayAlertStatus = "";
                        }, 3000);
                        $scope.holidayDataTable();
                    }, function errorCallback(response) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    });
                }
            }
        });
    }

    //-------------------------------------------------------Allowance-------------------------------------------------------------

    $scope.getShift = function() {
        $('.ajax-loader').css("visibility", "visible");
        $http({
            url: "/tmsApp/tms/financePage/getShifts",
            method: "GET",
        }).then(function successCallback(response) {
        	if(response.headers('Log_Out')=="true"){
				window.location='/tmsApp/login';
			}
            $scope.shiftAllowance = response.data;
            $timeout(function () {
				$('#allowanceShiftTable').DataTable({
					"responsive":true,					
					"paging":false,
                    "ordering":false,
                    "info":false,
                    "searching":false,
					"language": {
					      "emptyTable": "No Data available."
					    },
				});
				$('#allowanceShiftTable').wrap('<div class="dataTables_scroll" />');
				$('th').removeClass('sorting_asc');
			}, 30);
            console.log(response);
            $('.shiftAllowanceDiv').removeClass('hidden');
            $('.ajax-loader').css("visibility", "hidden");
            $timeout(function() {
                $(".allowance").blur(function() {
                    if ($.trim($(this).val()) === "") {
                        $(this).val("00");
                    }
                });
            }, 30);
        }, function errorCallback(response) {
        	bootbox.alert({
                message: "<label>Something went wrong.Inernal error.</label>"
            });
        });
    }

    $scope.createAllowance = function() {
        console.log($scope.shiftAllowance);
        $http({
            url: "/tmsApp/tms/financePage/updateAllowance",
            method: "POST",
            data: $scope.shiftAllowance
        }).then(function successCallback(response) {
        	if(response.headers('Log_Out')=="true"){
				window.location='/tmsApp/login';
			}
        	$('#allowanceShiftTable').DataTable().destroy();
            $scope.getShift();
            $scope.class = "alert-success";
            $(".allowanceAlert").css("display", "block");
            $scope.allowanceAlertStatus = 'Saved successfully.';
            $(".allowanceAlert").delay(3000).fadeOut();
            $timeout(function() {
                $scope.holidayAlertStatus = "";
            }, 3000);
            console.log(response);
        }, function errorCallback(response) {
        	bootbox.alert({
                message: "<label>Something went wrong.Inernal error.</label>"
            });
        });
    }

    
    $scope.holidayDatepicker = function(element){
    	var strtDate=new Date(year,'00','01');
        var lastDate = new Date(year, '11', '31');
        console.log(element);
        element.datepicker({
            dateFormat: "yy-mm-dd",
            autoclose: true,
            minDate: strtDate,
            yearRange: '-0:+1',
            maxDate: lastDate,
            hideIfNoPrevNext: true,
           /* onSelect: function() {
                alert($(this).val());
                var date=moment($(this).val());
                $(this).val(date.format("DD-MMM-YYYY"));
               element.
              }*/
        });
    }
    //-------------------------------------------------------Allowance View-----------------------------------------------------------
    
  //---------------------------ALLOWNACE History------------------------------------
	$scope.Allowance_History = function () {
		$scope.history.fromDate=$('#from').val();
    	$scope.history.toDate=$('#to').val();
		$scope.history.fromDate=$('#from').val();
    	$scope.history.toDate=$('#to').val(); 
		if($scope.history!=undefined){
			$.each($scope.history, function(key,value){
 			    if(value==""||value==null){
 			        delete $scope.history[key];
 			    }
 			});
		}
		$('.allowanceViewTable').hide();
		console.log($scope.history);
		if ($scope.history == undefined||Object.keys($scope.history).length <= 0) {
			$scope.class="alert-danger";
   		 	$(".flmHistAlert").css("display","block");
   		 	$scope.flmHistStatus = 'Please provide input.';
   		 	$(".flmHistAlert").delay(3000).fadeOut();
   		 	$timeout(function(){
   			 $scope.flmHistStatus="";
   		 	},3000);
		} else {
			$rootScope.historyData=$scope.history;
			$scope.getALLowanceHistory($scope.history);
		}
	}
	//-------------------------------Reset Allowance History-------------------
	$scope.resetAllowance=function(){
		  $scope.history = {};
		  $('#histrySearchForm')[0].reset();
		  $('.allowanceViewTable').hide();
	      $('#histrySearchForm .glyphicon-remove').addClass('hidden');
	}
	
	
	function SortByName(a, b){
		  var aName = a.empName.toLowerCase();
		  var bName = b.empName.toLowerCase(); 
		  return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
		}
	
	//-------------------getALLOWANCE History----------------------
	$scope.getALLowanceHistory=function(data){
		//$scope.allowanceView = allow;
		financeService.getAllowanceView(data)
		.then(
			function (response) {
				console.log(response);
				var data=response.data;
				data.sort(SortByName);
				$scope.allowanceView = data;
				$('#allowanceViewTable').DataTable().destroy();
				$timeout(function () {
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
				}, 30);
				$('.allowanceViewTable').show();
			},
			function (errResponse) {  
				bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
			}
		);
		
		}
	
	
	  
	$('#allowanceViewDiv #accntDrp').on('change', (function () {
		var value = $(this).val();
		if(value!=""){
			financeService.getProject(value)
				.then(
						function (response) {
							console.log(response);
							$scope.project = response.data;
						},
						
						function (errResponse) {  
							bootbox.alert({
			                    message: "<label>Something went wrong.Inernal error.</label>"
			                });
						}
					);
		}
	}));
	
	$scope.checkErr = function(startDate,endDate) {
	    $scope.errMessage = '';
	    var startYear=startDate.substring(startDate.indexOf('-')+1);
	    var endYear=endDate.substring(endDate.indexOf('-')+1);
	    var startmonth=startDate.substring(0,startDate.indexOf('-'));
	    var endmonth=endDate.substring(0,endDate.indexOf('-'));
	    if(startYear)
       
	    if(startYear > endYear){
	    	$(".flmHistAlert").css("display","block");
   		 	$scope.flmHistStatus = 'End Date should be greater than Start date.';
   		 	$(".flmHistAlert").delay(3000).fadeOut();
	    
	      return false;
	    }else if(startYear <= endYear){
	    	if(startmonth>endmonth){
	    		$(".flmHistAlert").css("display","block");
	   		 	$scope.flmHistStatus = 'End Date should be greater than Start date.';
	   		 	$(".flmHistAlert").delay(3000).fadeOut();
	   	      return false;
	    	}     
	    }
	    
	};
	//--------------------------------------Finance Excel--------------------------------------------------------------
	
	$scope.financeExcel=function(type){
		if($scope.excelCal!=undefined){
			var data=$scope.excelCal.split('-');
			var month=data[0];
			var year=data[1];
			
			$http({
	            url: "/tmsApp/tms/financePage/writeExcel",
	            method: "GET",
	            params: {
	            	excelType:type,
	            	month: month,
	            	year:year
	            	
	            }
	        }).then(function successCallback(response) {
	        	if(response.headers('Log_Out')=="true"){
					window.location='/tmsApp/login';
				}
	        	if(response.data=="NoData"){
	        		$scope.class="alert-danger";
	       		 	$(".financeExcelAlert").css("display","block");
	       		 	$scope.financeExcStatus = 'No Record found.';
	       		 	$(".financeExcelAlert").delay(2000).fadeOut();
	       		 	$timeout(function(){
	       			 $scope.financeExcStatus="";
	       		 	},2000);
	        	}
	        	else{
	        		window.location="/tmsApp/tms/financePage/writeExcel?excelType="+type+"&month="+month+"&year="+year;
	        	}
	        	console.log(response);
	        }, function errorCallback(response) {
	        	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
	        });
		}
		else{
			$scope.class="alert-danger";
   		 	$(".financeExcelAlert").css("display","block");
   		 	$scope.financeExcStatus = 'Please select month.';
   		 	$(".financeExcelAlert").delay(2000).fadeOut();
   		 	$timeout(function(){
   			 $scope.financeExcStatus="";
   		 	},2000);
		}
	}
	
	
	  $('#fromClear,#toClear').click(function(){
	    	$(this).siblings().val('');
	    	 $(this).addClass('hidden');
	    	 
	    });
}]);

angular.module('tmsApp').directive("datetimepicker", function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {
        	scope.holidayDatepicker(element);    		 
        }
    }
});