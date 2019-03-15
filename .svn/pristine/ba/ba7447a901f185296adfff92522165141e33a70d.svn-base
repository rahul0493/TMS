
'use strict';

angular.module('tmsApp').controller('adminController', ['adminService', '$scope', '$rootScope', '$timeout', '$filter', function(adminService, $scope, $rootScope, $timeout, $filter) {

    //$('.ajax-loader').css("visibility", "visible");

    $scope.flmPanel = function(evt, cityName) {
        $timeout(function() {
            $('[data-toggle="tooltip"]').tooltip();
        }, 100);
        if (cityName == "flmDailyPanel") {
            $scope.flmDailyRequest('all');
        } else if (cityName == "flmUpcomingPanel") {
            $scope.flmUpcomingRequest();
        } else if (cityName == "flmTripPanel") {
            $scope.getTripSheetView();
        }
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        if (cityName == "flmHistoryPanel") {
            document.getElementById(cityName).style.display = "inline-block";
            //	$('.dailyExcelBtn').hide();
            /* document.getElementById(cityName).style.display = "grid"; */
        } else {
            document.getElementById(cityName).style.display = "block";
        }
        evt.currentTarget.className += " active";
    }

    $scope.MappingPanel = function(evt, mappingType) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        document.getElementById(mappingType).style.display = "block";
        //document.getElementById(mappingType).style.display = "-ms-grid";
        evt.currentTarget.className += " active";
    }



    $('.faq-links').click(function() {
        var collapsed = $(this).find('i').hasClass('fa-plus');
        $('.faq-links').find('i').removeClass('fa-minus');
        $('.faq-links').find('i').addClass('fa-plus');
        if (collapsed) {
            $(this).find('i').toggleClass('fa-plus  fa-minus')
        }
    });
    $('.has-clear input').on('input propertychange', function() {
        var $this = $(this);
        var visible = Boolean($this.val());
        $this.siblings('.form-control-clear').toggleClass('hidden', !visible);
    }).trigger('propertychange');


    $('.form-control-clear').click(function() {
        var tripDate = $('input[name="tripDate"]').val();
        $('#tripSheetModel .form-control,#tripSheetModel span').attr('disabled', false);
        $('.date span').css('display', '');
        $('#tripSheetModel input[name="tripSheetNumber"],.createTripSht,.updateTripSht,#escortfor,#escortName').removeAttr('disabled');
        $scope.trip = {
            "isEscort": false
        };
        $('#isEscortDiv').show();
        $('#escortName').hide();
        $rootScope.escort = "";
        $scope.trip.tripDate = tripDate;
        $(this).siblings('input').val('')
            .trigger('propertychange').focus();
        $scope.$apply();
    });



    $('#spocTrackMappingDiv #accntDrp,#userRoleDiv #accntDrp,#empTrackMappingDiv #accntDrp,#flmHistoryPanel #accntDrp').on('change', (function() {
        var value = $(this).val();
        //var id=$(this).attr('class');
        var id = $(this).parent("div").attr('id');
        if (value != "") {
        	
            adminService.getProject(value,$scope.role)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        $('#accnt_prjct').attr('disabled',false);
                        console.log(response);
                        if(id=="spocTrackAcct"){
                        $scope.spocTrackProject = response.data;
                        }
                        else if(id=="empTrackAcct"){
                        $scope.empTrackProject = response.data;
                        }
                        else if(id=="userRoleAcct"){
                        $scope.userProject = response.data;
                        }
                        else{
                        $scope.project = response.data;
                        }
                        $('#emp_table').hide();
                        $('#spocTrackTable').hide();
                        $('#empTrackTable').hide();
                        //$timeout(function(){
                        $scope.spocTrack = {
                            "project": "",
                            "accountName": ""
                        };
                        $scope.user = {
                            "project": "",
                            "accountName": ""
                        };
                        $scope.empTrack = {
                            "project": "",
                            "accountName": ""
                        };
                        if (id == "spocTrackAcct") {
                            $scope.spocTrack = {
                                "project": "",
                                "accountName": value
                            };
                        }
                        if (id == "empTrackAcct") {
                            $scope.empTrack = {
                                "project": "",
                                "accountName": value
                            };
                        }
                        if (id == "userRoleAcct") {
                            $scope.user = {
                                "project": "",
                                "accountName": value
                            };
                        }
                        $(this).val(value);
                        //},30);
                    },
                    function(errResponse) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    }
                );
        } else {

            $scope.spocTrackProject = [];
            $scope.empTrackProject = [];
            $scope.userProject = [];
            $scope.project = [];
            $timeout(function() {
                $scope.spocTrack = {
                    "project": "",
                    "accountName": value
                };
                $scope.user = {
                    "project": "",
                    "accountName": value
                };
                $scope.empTrack = {
                    "project": "",
                    "accountName": value
                };
                //$('#accnt_prjct').val("");
                //$scope.empTrack.project="";
            }, 50);

            $('#emp_table').hide();
            $('#spocTrackTable').hide();
            $('#empTrackTable').hide();

        }
    }));

    $scope.getEmpOfProject = function(value, manager, admin) {
        /*var role="";
        page=JSON.parse(JSON.stringify(page));
        for(var i=0;i<page.length;i++){
        	role+=page[i]+"_";
        }*/
        if (value != ""&&value!=undefined) {
            adminService.getProjectEmployee(value)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        if (manager == "SENIOR_MANAGER" && admin != "SUPER_ADMIN") {
                        	$('.adminUserEmpRole').remove();
                        }
                        else if(admin == "SUPER_ADMIN"){
                        	$('.managerUserEmpRole').remove();
                        }
                        var res = response.data;
                        $('#userEmpRole').DataTable().destroy();
                        if (res.data == "No User Found") {
                            $scope.empData = null;
                            $('#submitRole input').hide();
                        } else {
                            $scope.empData = response.data;
                            $('#submitRole input').show();
                        }
                        console.log(response.data);
                        $('#emp_table').show();
                        $timeout(function() {
                            $('#userEmpRole').DataTable({
                                responsive: true,
                                ordering: false,
                                language: {
                                    emptyTable: "No Employee available."
                                },
                                //bRetrieve: true,
                                //bSortable:false
                            });
                            $('#userEmpRole').wrap('<div class="dataTables_scroll" />');
                            $('th').removeClass('sorting_asc');
                           /* if (manager == "SENIOR_MANAGER" && admin != "ADMIN") {
                                $('td:nth-child(4),th:nth-child(4),td:nth-child(6),th:nth-child(6),td:nth-child(7),th:nth-child(7)').remove();
                            }*/
                        }, 30);
                        /*$timeout(function () {
                        	$('.roleCheck').on('change', (function () {
                        		$('#submitRole input').removeAttr('disabled');
                        	}));
                        }, 60);*/
                        $scope.roleCheck = function(id) {
                            if ($(".enableRole").is(':checked')) {
                                $('#submitRole input').removeClass('hidden');
                                $('#submitRole input').attr('disabled', false);
                            } else {
                                $('#submitRole input').addClass('hidden');
                                $('#submitRole input').attr('disabled', true);
                            }

                            var disable = $('#userEmpRole #row' + id + ' .roleCheck').attr('disabled');
                            if (disable == 'disabled') {
                                $('#userEmpRole #row' + id + ' .roleCheck').attr("disabled", false);
                                //$('#submitRole input').attr('disabled',false);
                            } else {
                                $('#userEmpRole #row' + id + ' .roleCheck').attr("disabled", true);
                                //$('#submitRole input').attr('disabled',true);
                            }
                        }
                    },
                    function(errResponse) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    }
                );
        } else {
            $('#emp_table').hide();
        }
    }

    $scope.getSpocOfProject = function(value) {
    	if (value != ""&&value!=undefined) {
            adminService.getSpocOfProject(value)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        $('#trackSpocMapping').DataTable().destroy();
                        console.log(response.data);
                        var res = response.data;
                        if (res.data == "No User Found") {
                            $("#trackSpocMapping thead tr").html("<th>Select</th><th>Emp ID</th><th>Emp Name</th>");
                            $scope.spocData = null;
                            $('#submitSpoc input').hide();
                        } else {
                            $scope.dummy1 = ["Select", "Emp ID", "Name"];
                            $.each(response.data[0].isEmpMapToTrack, function(key, value) {
                                $scope.dummy1.push(key);
                            });
                            var header = '';
                            $.each($scope.dummy1, function(key, value) {
                                header = header + "<th>" + value + "</th>";
                            });
                            $("#trackSpocMapping thead tr").html(header);
                            $scope.spocData = response.data;
                            $('#submitSpoc input').show();
                        }

                        $('#spocTrackTable').show();
                        $timeout(function() {
                            $('#trackSpocMapping').DataTable({
                                responsive: true,
                                ordering: false,
                                //bRetrieve: true,
                                //bSortable:false,
                                language: {
                                    emptyTable: "No Track or Employee available."
                                }
                            });
                            $('#trackSpocMapping').wrap('<div class="dataTables_scroll" />');
                            $('th').removeClass('sorting_asc');
                        }, 30);
                        /*$timeout(function () {
                        	$('.spocCheck').on('change', (function () {
                        		$('#submitSpoc input').removeAttr('disabled');
                        	}));
                        }, 60);*/
                        
                        $scope.spocTrackChange=function($event){
                       	 var el = event.target
                       	    console.log(el);
                       	console.log(event.target.value);
                       	var id=$(el).closest('tr').attr('id');
                       	var cls=$(el).closest('tr').attr('class');
                       	cls=cls.split(' ');
                       	 if(el.checked){                            
                       	$scope.spocData[cls[0]].trackName=el.value;
                       	if (!$scope.$$phase) {
                       	$scope.$apply();
                       	}
                       	 }
                       	 else{
                       		 $scope.empTrackData[cls[0]].trackName="";
                       		 console.log($scope.spocData[cls[0]].trackName);
                       		 if (!$scope.$$phase) {
                       			 $scope.$apply();
                       		 }
                       	 }
                       }
                        
                        $scope.spocCheck = function(id) {

                            if ($(".enableSpoc").is(':checked')) {
                                $('#submitSpoc input').removeClass('hidden');
                                $('#submitSpoc input').attr('disabled', false);
                            } else {
                                $('#submitSpoc input').addClass('hidden');
                                $('#submitSpoc input').attr('disabled', true);
                            }
                            var disable = $('#trackSpocMapping #row' + id + ' .spocCheck').attr('disabled');
                            if (disable == 'disabled') {
                                $('#trackSpocMapping #row' + id + ' .spocCheck').attr("disabled", false);
                            } else {
                                $('#trackSpocMapping #row' + id + ' .spocCheck').attr("disabled", true);
                                //$('#submitSpoc input').attr('disabled',true);
                            }
                        }
                    },
                    function(errResponse) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    }
                );
        } else {
            $('#spocTrackTable').hide();
        }
    }

    $scope.getEmpOfProjectTrack = function(value) {
    	if (value != ""&&value!=undefined) {
            adminService.getEmpOfProjectTrack(value)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        $('#empTrackMapping').DataTable().destroy();
                        console.log(response.data);
                        var res = response.data;
                        if (res.data == "No User Found") {
                            $("#empTrackMapping thead tr").html("<th>Select</th><th>Emp ID</th><th>Emp Name</th>");
                            $scope.empTrackData = null;
                            $('#submitTrackEmp input').hide();
                        } else {
                            $scope.dummy1 = ["Select", "Emp ID", "Name"];
                            $.each(response.data[0].isEmpMapToTrack, function(key, value) {
                                $scope.dummy1.push(key);
                            });
                            var header = '';
                            $.each($scope.dummy1, function(key, value) {
                                header = header + "<th>" + value + "</th>";
                            });
                            $("#empTrackMapping thead tr").html(header);
                            $scope.empTrackData = response.data;
                            $('#submitTrackEmp input').show();
                        }

                        $('#empTrackTable').show();
                        $timeout(function() {
                            $('#empTrackMapping').DataTable({
                                responsive: true,
                                ordering: false,
                                //bRetrieve: true,
                                //bSortable:false,
                                language: {
                                    emptyTable: "No Track or Employee available."
                                }
                            });
                            $('#empTrackMapping').wrap('<div class="dataTables_scroll" />');
                            $('th').removeClass('sorting_asc');
                            
                            
                            //$('#empTrackMapping .empTrackChk').on('change', function() {
                            $scope.empTrackChange=function($event){
                            	 var el = event.target
                            	    console.log(el);
                            	console.log(event.target.value);
                            	var id=$(el).closest('tr').attr('id');
                            	var cls=$(el).closest('tr').attr('class');
                            	cls=cls.split(' ');
                            	 if(el.checked){
                                $('#empTrackMapping #'+id+' .empTrackChk').not(el).prop('checked', false); 
                            	$scope.empTrackData[cls[0]].trackName=el.value;
                            	if (!$scope.$$phase) {
                            	$scope.$apply();
                            	}
                            	 }
                            	 else{
                            		 $scope.empTrackData[cls[0]].trackName="";
                            		 console.log($scope.empTrackData[cls[0]].trackName);
                            		 if (!$scope.$$phase) {
                            			 $scope.$apply();
                            		 }
                            	 }
                            }
                        //});
                        }, 30);



                        $scope.empTrackChk = function(id) {
                            if ($(".enableUser").is(':checked')) {
                                $('#submitTrackEmp input').removeClass('hidden');
                                $('#submitTrackEmp input').attr('disabled', false);
                            } else {
                                $('#submitTrackEmp input').addClass('hidden');
                                $('#submitTrackEmp input').attr('disabled', true);
                            }

                            var disable = $('#empTrackMapping #row' + id + ' .empTrackChk').attr('disabled');
                            if (disable == 'disabled') {
                                $('#empTrackMapping #row' + id + ' .empTrackChk').attr("disabled", false);
                            } else {
                                $('#empTrackMapping #row' + id + ' .empTrackChk').attr("disabled", true);
                                //$('#submitTrackEmp input').attr('disabled',true);
                            }
                        }
                        
                        
                    },
                    function(errResponse) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    }
                );
        } else {

            $('#empTrackTable').hide();
        }
    }

    var selectedProjct;
    /*$('#userRoleDiv #accnt_prjct').on('change', (function () {
		selectedProjct = $(this).val();
		$scope.getEmpOfProject(selectedProjct);
	}));
*/



    $scope.getEmployee = function(option, manager, admin) {
        selectedProjct = option;
        $scope.getEmpOfProject(option, manager, admin);
    }

    $('#spocTrackMappingDiv #accnt_prjct').on('change', (function() {
        selectedProjct = $(this).val();
        $scope.getSpocOfProject(selectedProjct);
    }));

    $('#empTrackMappingDiv #accnt_prjct').on('change', (function() {
        selectedProjct = $(this).val();
        $scope.getEmpOfProjectTrack(selectedProjct);
    }));


    //-------------------update user role------------------------------------
    $scope.updateUserRole = function(manager, admin) {
        if ($("input[name='enableRole']").is(':checked')) {
            var arrr = [];
            $("input[name=enableRole]:checked").each(function() {
                var id = $(this).attr("id");
                var res = JSON.search($scope.empData, '//*[empCode=' + id + ']');
                res = Object.assign({}, res);
                arrr.push(res[0]);
            });
            adminService.updateEmployeeRoles(arrr, manager, admin)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        //var response = "<label>" + response.data.data + "</label>";
                        //$('#Adhoc_popup').html(response);
                        //$('#myModal').modal('show');
                        $('#submitRole input').attr('disabled', true);
                        $scope.class = "alert-success";
                        $(".roleAlert").css("display", "block");
                        $scope.roleAlertStatus = 'Saved successfully.';
                        $(".roleAlert").delay(3000).fadeOut();
                        $timeout(function() {
                            $scope.roleAlertStatus = "";
                        }, 3000);
                        $scope.getEmpOfProject(selectedProjct, manager, admin);
                       // $scope.getSpocOfProject(selectedProjct);
                    },
                    function(errResponse) {
                        $scope.class = "alert-danger";
                        $(".roleAlert").css("display", "block");
                        $scope.roleAlertStatus = 'Something went wrong. Please try after sometime.';
                        $(".roleAlert").delay(3000).fadeOut();
                        $timeout(function() {
                            $scope.roleAlertStatus = "";
                        }, 3000);
                    }
                );
        } else {
            bootbox.alert({
                message: "<label>Please select a checkbox.</label>"
            });
        }
    }

    $("input[type='radio']").on('change', (function() {
        $(this).attr('id');
    }));
    //----------------------update spoc of track---------------------------

    $scope.updatespocTrack = function() {
        $('.ajax-loader').css("visibility", "visible");
        if ($("input[name='enableSpoc']").is(':checked')) {
            var arrr = [];
            $("input[name=enableSpoc]:checked").each(function() {
                var id = $(this).attr("id");
                var res = JSON.search($scope.spocData, '//*[empCode=' + id + ']');
                var dat = res[0].isEmpMapToTrack;
                var keys = Object.keys(dat);
                var filtered = keys.filter(function(key) {
                    return dat[key]
                });
                res[0].projectId=$scope.spocTrack.project;
                res[0].isEmpMapToTrack = null;
                res[0].trackDetails = filtered;
                delete res[0].isEmpMapToTrack;
                console.log(filtered);
                arrr.push(res[0]);
            });

            console.log(arrr);
            
            adminService.updateTrackSpocRole(arrr,selectedProjct)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        //var response = "<label>" + response.data.data + "</label>";
                        $scope.class = "alert-success";
                        $(".roleAlert").css("display", "block");
                        $scope.roleAlertStatus = 'Saved successfully.';
                        $(".roleAlert").delay(3000).fadeOut();
                        $timeout(function() {
                            $scope.roleAlertStatus = "";
                        }, 3000);
                        //$('#Adhoc_popup').html(response);
                        //$('#myModal').modal('show');
                        $('#submitSpoc input').attr('disabled', true);
                        $scope.getSpocOfProject(selectedProjct);
                        $('.ajax-loader').css("visibility", "hidden");
                    },
                    function(errResponse) {
                        $scope.class = "alert-danger";
                        $(".roleAlert").css("display", "block");
                        $scope.roleAlertStatus = 'Something went wrong. Please try after sometime.';
                        $(".roleAlert").delay(3000).fadeOut();
                        $timeout(function() {
                            $scope.roleAlertStatus = "";
                        }, 3000);
                    }
                );
        } else {
            bootbox.alert({
                message: "<label>Please select a checkbox.</label>"
            });
        }
    }

    //-------------------------------update emp of track-----------------------

    $scope.updateEmpTrack = function() {
        $('.ajax-loader').css("visibility", "visible");
        if ($("input[name='enableUser']").is(':checked')) {
            var arrr = [];
            $("input[name=enableUser]:checked").each(function() {
                var id = $(this).attr("id");
                var res = JSON.search($scope.empTrackData, '//*[empCode=' + id + ']');
                /*var dat = res[0].isEmpMapToTrack;
                var keys = Object.keys(dat);
                var filtered = keys.filter(function(key) {

                });*/
       
                if(res[0].trackName!=null){

                res[0].isEmpMapToTrack = null;
                //res[0].trackDetails = filtered;
                res[0].trackDetails = [res[0].trackName];
                delete res[0].isEmpMapToTrack;
                //console.log(filtered);
                arrr.push(res[0]);
                }
            });

            console.log(arrr);
            if(arrr.length>0){
            adminService.updateTrackEmp(arrr)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        //var response = "<label>" + response.data.data + "</label>";
                        $scope.class = "alert-success";
                        $(".roleAlert").css("display", "block");
                        $scope.roleAlertStatus = 'Saved successfully.';
                        $(".roleAlert").delay(3000).fadeOut();
                        $timeout(function() {
                            $scope.roleAlertStatus = "";
                        }, 3000);
                        //$('#Adhoc_popup').html(response);
                        //$('#myModal').modal('show');
                        $('#submitTrackEmp input').attr('disabled', true);
                        $scope.getEmpOfProjectTrack(selectedProjct);
                        $('.ajax-loader').css("visibility", "hidden");
                    },
                    function(errResponse) {
                    	$('.ajax-loader').css("visibility", "hidden");
                        $scope.class = "alert-danger";
                        $(".roleAlert").css("display", "block");
                        $scope.roleAlertStatus = 'Something went wrong. Please try after sometime.';
                        $(".roleAlert").delay(3000).fadeOut();
                        $timeout(function() {
                            $scope.roleAlertStatus = "";
                        }, 3000);
                    }
                );
            }
            else{
            	$('.ajax-loader').css("visibility", "hidden");
            	 $scope.class = "alert-danger";
                 $(".roleAlert").css("display", "block");
                 $scope.roleAlertStatus = 'No details were changed to update.';
                 $(".roleAlert").delay(3000).fadeOut();
                 $timeout(function() {
                     $scope.roleAlertStatus = "";
                 }, 3000);
            }
        } else {
            bootbox.alert({
                message: "<label>Please select a checkbox.</label>"
            });
        }
    }

    //---------------------------------------Upload Account and Project as excel------------------------
    $scope.uploadAccntPrjctExcelFile = function() {
        $('.ajax-loader').css("visibility", "visible");
        console.log('About to Upload excel');
        var formdata = new FormData();
        var fileInput = document.getElementById('browseId');
        var file = fileInput.files[0];
        if(file==undefined||file==""){
      	  $('.ajax-loader').css("visibility", "hidden");
      	  $scope.class = "alert-danger";
          $(".excelAlert").css("display", "block");
          $scope.excelStatus = 'Please choose Excel file';
          $(".excelAlert").delay(3000).fadeOut();
          $timeout(function() {
              $scope.excelStatus = "";
          }, 3000);
      }
      else{        
        formdata.append("file", file);
        adminService.uploadAccntPrjctExcelFile(formdata)
            .then(
                function(response) {
                	if (response == "400") {
                        $('.ajax-loader').css("visibility", "hidden");
                        var response = "<label>Please check your Excel file. <br> It is not uploaded.</label>";
                        $('#browseId').val("");
                        $('#Adhoc_popup').html(response);
                        $('#myModal').modal('show');
                    } else {
                    	 if (response.headers('Log_Out') == "true") {
                             window.location = '/tmsApp/login';
                         }
                    	 else{
                        $scope.createAccntPrjtTable(response.data);
                        $('#browseId').val("");
                    	 }
                    }
                   
                    
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
      }
    }

    $scope.createAccntPrjtTable = function(response) {
        $('#accntTable').DataTable().destroy();
        $scope.accountProjectList = response;
        console.log(response);
        console.log('Excel Uploaded successfully');
        $timeout(function() {
            var table = $('#accntTable').DataTable({
                responsive: true,
            });
            $('#accntTable').wrap('<div class="dataTables_scroll" />');
            $('.ajax-loader').css("visibility", "hidden");
            $('.accntDiv').show();
        }, 30);
    }

    //---------------------------------------Upload Employee List Excel------------------------
    var AllEmployeeData;
    $scope.uploadEmpExcel = function() {
        $('.ajax-loader').css("visibility", "visible");
        console.log('About to Upload excel');
        var formdata = new FormData();
        var fileInput = document.getElementById('browseId');
        var file = fileInput.files[0];
        if(file==undefined){
        	  $('.ajax-loader').css("visibility", "hidden");
        	$scope.class = "alert-danger";
            $(".excelAlert").css("display", "block");
            $scope.excelStatus = 'Please choose Excel file';
            $(".excelAlert").delay(3000).fadeOut();
            $timeout(function() {
                $scope.excelStatus = "";
            }, 3000);
        }
        else{        
        formdata.append("file", file);
        adminService.uploadEmpExcel(formdata)
            .then(
                function(response) {
                	if (response == "400") {
                        $('.ajax-loader').css("visibility", "hidden");
                        var response = "<label>Please check your Excel file.<br> It is not uploaded.</label>";
                        $('#browseId').val("");
                        $('#Adhoc_popup').html(response);
                        $('#myModal').modal('show');
                    } else {
                    	if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                    	else{
                        $scope.empCreateTable(response.data);
                        $('#browseId').val("");
                    	}
                    }
                	
                    
                    
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
        }
    }


    $scope.empCreateTable = function(response, load) {
        $('#empTable').DataTable().destroy();
        $scope.employeeList = response;
        console.log(response);
        if($scope.employeeList.length<=0){
        	$('#submitEmpList').hide();
        }
        else{
        	$('#submitEmpList').show();	
        }
        console.log('Excel Uploaded successfully');
        $timeout(function() {
            var table = $('#empTable').DataTable({
                responsive: true,
            });
            $('#empTable').wrap('<div class="dataTables_scroll" />');
            
            	$scope.check=function(className){
            	if($('.td_'+className).is(':checked')){
            		$('#empTable').DataTable().$('.td_'+className).prop('checked',true).attr('checked',true);
                } else {
                	$('#empTable').DataTable().$('.td_'+className).prop('checked',false).attr('checked',false);
                }
            	}

            $('.empTableDiv .dataTables_scroll #example-select-all').on('click', function() {
                $('.dataTables_scrollBody thead tr').css({
                    visibility: 'collapse'
                });
                // Check/uncheck all checkboxes in the table
                var rows = table.rows({
                    'search': 'applied'
                }).nodes();
                $('input[type="checkbox"]', rows).prop('checked', this.checked);
            });
            $('.ajax-loader').css("visibility", "hidden");
            $('.empTableDiv').show();
        }, 30);

    }


    //--------------------------save Emp List----------------------

    $scope.saveEmp = function() {
        //adminService.saveEmpList(formdata)
        if ($(".empRow").is(':checked')) {
            $('.empTableDiv').hide();
            $('.ajax-loader').css("visibility", "visible");
            var arrr = [];
            var idArr = [];
            $(".empRow:checked").each(function() {
                var id = $(this).attr("id");
                idArr.push(id);
                var returnedData = $.grep($scope.employeeList, function(element, index) {
                    return index == id;
                });
                //var res = JSON.search($scope.employeeList, '//*[empcode=' + id + ']');
                arrr.push(returnedData[0]);
            });

            console.log(arrr);
            for (var i = 0; i < arrr.length; i++) {
                var res = JSON.search($scope.employeeList, '//*[empcode=' + arrr[i].empcode + ']');
                var projectList = [];
                for (var j = 0; j < res.length; j++) {
                    var data = {
                        "projectName": res[j].projectName,
                        "accountName": res[j].accountName
                    };
                    projectList.push(data);
                }
                arrr[i].projectList = projectList;
            }


            adminService.saveEmpList(arrr)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        console.log(response.data);
                        if (response.data == "Error") {
                            $scope.class = "alert-danger";
                            $scope.roleAlertStatus = 'Something went wrong. Tryagain.';
                        } else {
                            $scope.class = "alert-success";
                            $scope.roleAlertStatus = 'Saved successfully';
                            for (var j = (idArr.length - 1); j >= 0; j--) {
                                $scope.employeeList.splice(idArr[j], 1);
                            }
                            $('#empTable').DataTable().$('input[type="checkbox"]').prop('checked', false);
                            if($scope.employeeList.length<=0){
                            	$('#submitEmpList').hide();
                            }
                            else{
                            	$('#submitEmpList').show();	
                            }
                        }
                        $scope.empCreateTable($scope.employeeList, "reload");
                        $('.ajax-loader').css("visibility", "hidden");
                        $('.empTableDiv').show();
                        $(".roleAlert").css("display", "block");
                        $(".roleAlert").delay(3000).fadeOut();
                        $timeout(function() {
                            $scope.roleAlertStatus = "";
                        }, 3000);
                        $timeout(function() {
                            $(".empRow,#example-select-all").attr("checked", false);
                        }, 30);
                    },
                    function(errResponse) {
                        $scope.class = "alert-danger";
                        $('.ajax-loader').css("visibility", "hidden");
                        $('.empTableDiv').show();
                        $(".roleAlert").css("display", "block");
                        $scope.roleAlertStatus = 'Something went wrong. Please try after sometime.';
                        $(".roleAlert").delay(3000).fadeOut();
                        $timeout(function() {
                            $scope.roleAlertStatus = "";
                        }, 3000);
                    }
                );
        } else {
            $scope.class = "alert-danger";
            $(".roleAlert").css("display", "block");
            $scope.roleAlertStatus = 'Please select a checkbox';
            $(".roleAlert").delay(3000).fadeOut();
            $timeout(function() {
                $scope.roleAlertStatus = "";
            }, 3000);
        }



    }

    //---------------------------flm dashboard upcoming request---------------------------------------
    $scope.flmFunction = function(dash, viewData, reportType) {
        $('#flm' + dash + 'Table').DataTable().destroy();
        $timeout(function() {
            table = $('#flm' + dash + 'Table').DataTable({
                "responsive": true,
                "initComplete": function() {
                    this.api().columns().every(function() {
                        var column = this;
                        var select = $('<select><option value=""></option></select>')
                            .appendTo($(column.footer()).empty())
                            .on('change', function() {
                                var val = $.fn.dataTable.util.escapeRegex(
                                    $(this).val()
                                );

                                column
                                    .search(val ? '^' + val + '$' : '', true, false)
                                    .draw();
                            });

                        column.data().unique().sort().each(function(d, j) {
                            select.append('<option value="' + d + '">' + d + '</option>')
                        });
                    });
                },
                "dom": 'lB<"#' + dash + '">frtip',
                'className': 'dt-body-center',
                'render': function(data, type, full, meta) {
                    return '<input type="checkbox" name="id[]" value="' +
                        $('<div/>').text(data).html() + '">';
                },
                "columnDefs": [ {
       	    	  "targets": [0,3,9],
       	    	  "orderable": false
       	    	  } ],
                "buttons": [{
                        extend: 'excel',
                        text: 'Excel',
                        title: dash + ' Report',
                        exportOptions: {
                            columns: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
                        }
                    },
                    /*{
		                text: 'Excel',
		                className: 'dailyExcelBtn',
		                action: function (e, dt, node, config) {
		                	window.location.href="/tmsApp/tms/excel/writeExcel";
		                }
		            },*/
                    {
                        text: 'Generate TripSheet',
                        className:'tripSheetBtn',
                        action: function(e, dt, node, config) {
                            $scope.tripType = "create";
                            var arr = [];
                            $('#escortName').hide();
                            $('#escortfor').val([]);
                            //							$('.js-example-basic-multiple').select2({
                            //								  placeholder: 'Select an option'
                            //								});
                            $scope.trip = {};
                            if ($('input[type="checkbox"]').is(':checked')) {

                                //-------------------get Trip sheet number----------------
                                $('#tripSheetModel input,#tripSheetModel span').attr('disabled', false);
                                $('.date span').css('display', '');
                                $('#tripSheetModel input[name="tripSheetNumber"],.createTripSht,.updateTripSht').removeAttr('disabled');

                                $(table.$('input[type="checkbox"]:checked').each(function() {
                                    var id = $(this).closest('tr').attr('id');
                                    arr.push(id);
                                }));
                                console.log(arr);
                                var trpId = JSON.search(viewData, '//*[id="' + arr[0] + '"]');
                                if (trpId[0].tripSheetId != null || trpId[0].tripSheetId != undefined) {
                                    bootbox.alert({
                                        message: "<label>You are not able to select existing TripSheet ID.<br>If you want to edit, click on TripSheet ID.</label>"
                                    });
                                } else {
                                    var res = JSON.search(viewData, '//*[id="' + arr[0] + '"]');
                                    var sDate = moment(res[0].scheduleDate);
                                    sDate = sDate.format("DD-MM-YYYY");
                                    var reqType = res[0].requestType;
                                    var tripId = res[0].tripSheetId;
                                    console.log(res);
                                    var arr1 = [];
                                    var arr2 = [];
                                    var arr3 = [];
                                    var arr4 = [];
                                    for (var i = 0; i < arr.length; i++) {
                                        var res1 = JSON.search(viewData, '//*[id="' + arr[i] + '"]');
                                        var sDate1 = moment(res1[0].scheduleDate);
                                        sDate1 = sDate1.format("DD-MM-YYYY");
                                        var reqType1 = res1[0].requestType;
                                        var tripId1 = res1[0].tripSheetId;
                                        var travelStatus1 = res1[0].travelStatus;
                                        if (sDate == sDate1) {
                                            arr1.push({
                                                "data": true
                                            });
                                        } else {
                                            arr1.push({
                                                "data": false
                                            });
                                        }
                                        if (reqType == reqType1) {
                                            arr2.push({
                                                "data": true
                                            });
                                        } else {
                                            arr2.push({
                                                "data": false
                                            });
                                        }
                                        if (tripId == tripId1) {
                                            arr3.push({
                                                "data": true
                                            });
                                        } else {
                                            arr3.push({
                                                "data": false
                                            });
                                        }
                                        if (travelStatus1 == "Completed"||travelStatus1=="No show by User") {
                                            arr4.push({
                                                "data": true
                                            });
                                        } else {
                                            arr4.push({
                                                "data": false
                                            });
                                        }
                                    }
                                    var var2 = JSON.search(arr1, '//*[data="false"]');
                                    var var3 = JSON.search(arr2, '//*[data="false"]');
                                    var var4 = JSON.search(arr3, '//*[data="false"]');
                                    var var5 = JSON.search(arr4, '//*[data="false"]');
                                    if (var2.length >= 1) {
                                        bootbox.alert({
                                            message: "<label>Please select the requests with same Schedule Date to create the TripSheet.</label>"
                                        });
                                    } else if (var3.length >= 1) {
                                        bootbox.alert({
                                            message: "<label>Please select the requests with same Request Type to create the TripSheet.</label>"
                                        });
                                    } else if (var4.length >= 1) {
                                        bootbox.alert({
                                            message: "<label>You are not able to select the existing TripSheet ID.<br>If you want to edit, click on TripSheet ID.</label>"
                                        });
                                    } else if (var5.length >= 1) {
                                        bootbox.alert({
                                            message: "<label>You can create the TripSheet only when the travel status is completed (or) No show by User.</label>"
                                        });
                                    } else {
                                        var res = JSON.search(viewData, '//*[id="' + arr[0] + '"]');
                                        console.log(arr);
                                        var trvldName = [];
                                        for (var i = 0; i < arr.length; i++) {
                                            var var2 = JSON.search(viewData, '//*[id="' + arr[i] + '"]');
                                            trvldName.push(var2[0]);

                                        }
                                        console.log(trvldName);
                                        var femaleTrvlr = JSON.search(trvldName, '//*[empGender="F"]');
                                        console.log(femaleTrvlr);
                                        $rootScope.femaleTraveler = femaleTrvlr;
                                        $('#tripSheetModel').modal('show');
                                        $rootScope.escort = "";
                                        $('.createTripSht').val('Create');
                                        $('.updateTripSht').addClass('hidden');
                                        $('.createTripSht').show();
                                        /*var date = moment(sDate); //Get the current date
                                        sDate = date.format("DD-MM-YYYY");*/
                                        $scope.getTripSheetNumber(sDate,reqType);
                                        $timeout(function() {
                                            sDate = {
                                                "tripDate": sDate,
                                                "isEscort": false
                                            };
                                            $scope.trip = sDate;
                                        }, 30);
                                    }
                                }
                            } else {
                                bootbox.alert({
                                    message: "<label>Please select a checkbox.</label>"
                                });
                            }
                        }
                    }
                ]
            });

            $('<label/>').text('  FLM Status  ').appendTo('#' + dash).append(" ").prepend(" ");
            var select = $('<select/>').addClass("form-control input-sm").attr("id", "flm" + dash + "ActionChkd").appendTo('#' + dash);
            $('<option/>').val("Please Select").text('Please Select').attr({

                "selected": "selected"
            }).appendTo(select);
            $('<option/>').val('Approved').text('Approve').appendTo(select);
            $('<option/>').val('Rejected').text('Reject').appendTo(select);

            $('<label/>').text('  Travel Status  ').addClass('statusLabel').appendTo('#' + dash).append(" ").prepend(" ");
            var select = $('<select/>').addClass("form-control input-sm").attr({
                "id": "flm" + dash + "StatusChkd",
                "disabled": true
            }).appendTo('#' + dash);
            $('<option/>').val("Please Select").text('Please Select').attr({

                "selected": "selected"
            }).appendTo(select);
            //$('<option/>').val('Pending').text('Pending').appendTo(select);
           // $('<option/>').val('Processed').text('Processed').appendTo(select);
            $('<option/>').val('Completed').text('Completed').appendTo(select);
            $('<option/>').val('No show by User').text('No show by User').appendTo(select);
            $('<option/>').val('No show by Vendor').text('No show by Vendor').appendTo(select);
            $('<option/>').val('No show by FLM').text('No show by FLM').appendTo(select);
            var btn = ' <input type="button" class="btn flm' + dash + 'SubmitBtn" id="submit' + dash + 'Update" value="Submit" disabled>';
            $('#' + dash).append(btn);


            $('#flm' + dash + 'Table').wrap('<div class="dataTables_scroll" />');
            $('#flm' + dash + 'Table th').removeClass('sorting_asc');
            $('.dataTables_scroll').css('overflow-x','auto');
            $('.adminpage .dataTables_scroll #example-select-all').on('click', function() {
                $('.dataTables_scrollBody thead tr').css({
                    visibility: 'collapse'
                });
                // Check/uncheck all checkboxes in the table
                var rows = table.rows({
                    'search': 'applied'
                }).nodes();
                $('input[type="checkbox"]:not(:disabled)', rows).prop('checked', this.checked);
                /*$('#flm' + dash + 'Table input[type="checkbox"]:not(:disabled)', '#flm' + dash + 'Table input[type="checkbox"]').prop('checked', true);*/
                //$('input[type="checkbox"] disabled', rows).prop('checked', false);
            });


            $('#flm' + dash + 'Table').on('change', 'input[type="checkbox"]', function() {
                var arr = [];
                var hold=[];
                if ($('input[type="checkbox"]').is(':checked')) {
                    var val = $(this).val();
                    $(table.$('input[type="checkbox"]:checked').each(function() {
                        var id = $(this).closest('tr').attr('id');
                        var tr = JSON.search(viewData, '//*[id="' + id + '"]');
                        if (tr[0].action != "Approved") {
                            arr.push(tr[0]);
                        }
                        var str = tr[0].travelStatus;
                        if(str.replace("hold", "") !== str){
                        	hold.push(tr[0]);
                        }
                    }));
                    
                    if(hold.length>0){
                    	 bootbox.alert({
                             message: "<label>Request for address updation is pending for Manager's Approval. You cannot make any changes.</label>",

                             callback: function() {
                            	 $('input[type="checkbox"]').prop("checked", false);
                             }
                         });
                    }
                    else{
                    if (arr.length > 0) {
                        $('#flm' + dash + 'StatusChkd').attr('disabled', true);
                    } else {
                        $('#flm' + dash + 'StatusChkd').attr('disabled', false);
                    }
                    }
                }
                var a = $("input[type='checkbox']");
                if(a.filter(":checked").length<1){
                	$('#flm' + dash + 'StatusChkd,.flm' + dash + 'SubmitBtn').attr('disabled', true);
                	$('#flm' + dash + 'StatusChkd').val('Please Select');
                }
            });



            $('#flm' + dash + 'ActionChkd').off().on('change', (function() {
                var action = $(this).val();
                if(action=="Please Select"){
                	 $('#flm' + dash + 'StatusChkd').attr("disabled", true).val('Please Select');
                	 $('.flm' + dash + 'SubmitBtn').attr('disabled',true);
                }
                else{
                if (action == "Rejected") {
                    $('#flm' + dash + 'StatusChkd').attr("disabled", true);
                } else {
                    $('#flm' + dash + 'StatusChkd').attr("disabled", false);
                }
                if ($('input[type="checkbox"]').is(':checked')) {
                	//var val = $(this).val();
                	 $scope.checkBox(action);
                } else {
                    bootbox.alert({
                        message: "<label>Please select a checkbox.</label>",
                    });
                    $(this).val('Please Select');
                    $('#flm' + dash + 'StatusChkd').attr('disabled', true);

                }
                }
            }));

            $scope.checkBox=function(action){
            	  var arr = [];
                  var arr1 = [];
                var val =action;
               
                $(table.$('input[type="checkbox"]:checked').each(function() {
                    var id = $(this).closest('tr').attr('id');
                    arr.push(id);
                }));
                if (action == "Rejected") {
                    for (var i = 0; i < arr.length; i++) {
                        var res1 = JSON.search(viewData, '//*[id="' + arr[i] + '"]');
                        if ((res1[0].travelStatus == "Completed"||res1[0].travelStatus == "No show by User"||res1[0].travelStatus == "No show by Vendor"||res1[0].travelStatus == "No show by FLM")) {
                            arr1.push({
                                "data": true
                            });
                        } else {
                            arr1.push({
                                "data": false
                            });
                        }
                    }
                    var var1 = JSON.search(arr1, '//*[data="true"]');
                    if (var1.length >= 1) {
                        bootbox.alert({
                            message: "<label>You cannot Reject the below requests:<br>&nbsp;&nbsp;1. Travel status is Completed.<br>&nbsp;&nbsp;2. No show by User/Vendor/FLM.</label>",

                            callback: function() {
                                $('#flm' + dash + 'ActionChkd,#flm' + dash + 'StatusChkd').val("Please Select");
                                $('.flm' + dash + 'SubmitBtnv', '#flm' + dash + 'StatusChkd').attr('disabled', false);

                            }
                        });
                    } else {
                    	bootbox.confirm("<label>Please ensure the request cannot be modified once rejected.</label>", function(result) {
                            if (result == true) {
                        $(table.$('input[type="checkbox"]:checked').each(function() {
                            var id = $(this).closest('tr').attr('id');
                            var tr = JSON.search(viewData, '//*[id="' + id + '"]');
                            if (val == "Approved") {
                                tr[0].action = "Approved";
                                if(tr[0].travelStatus=="Pending with FLM"||tr[0].travelStatus=="Pending with Manager"){
                                	 tr[0].travelStatus = "Processing";
                                }
                                else{
                                	tr[0].travelStatus = tr[0].travelStatus;
                                }
                            } else if (val == "Rejected") {
                                tr[0].action = "Rejected";
                                tr[0].travelStatus = "Rejected By FLM";
                            }
                            $('.flm' + dash + 'SubmitBtn', '#flm' + dash + 'StatusChkd').attr('disabled', false);
                        }));
                        $('.flm' + dash + 'SubmitBtn').attr('disabled',false);
                            }
                            else{
                            	$('#flm' + dash + 'ActionChkd,#flm' + dash + 'StatusChkd').val("Please Select");
                            }
                    	 });
                    }
                        
                   
                } else {
                    $(table.$('input[type="checkbox"]:checked').each(function() {
                        var id = $(this).closest('tr').attr('id');
                        var tr = JSON.search(viewData, '//*[id="' + id + '"]');
                        if (val == "Approved") {
                            tr[0].action = "Approved";
                            if(tr[0].travelStatus=="Pending with FLM"||tr[0].travelStatus=="Pending with Manager"){
                           	 tr[0].travelStatus = "Processing";
                           }
                           else{
                           	tr[0].travelStatus = tr[0].travelStatus;
                           }
                        } else if (val == "Rejected") {
                            tr[0].action = "Rejected";
                            tr[0].travelStatus = "Rejected By FLM";
                        }
                        $('.flm' + dash + 'SubmitBtn').attr('disabled', false);
                    }));

                }
            	
            }
            
            
            $('#flm' + dash + 'StatusChkd').off().on('change', (function() {
                console.log(viewData);
                var dateArr = [];
                if ($('input[type="checkbox"]').is(':checked')) {
                    var val = $(this).val();
                    $(table.$('input[type="checkbox"]:checked').each(function() {
                        var id = $(this).closest('tr').attr('id');
                        var tr = JSON.search(viewData, '//*[id="' + id + '"]');
                        //tr[0].travelStatus=val;
                        if(tr[0].travelStatus=="Completed"||tr[0].travelStatus=="No show by User"){
                        	if(tr[0].tripSheetNumber!=null){
                        		if(val!="Completed"&&val!="No show by User"){
                        			bootbox.alert({
                                        message: "<label>If you want to make any changes, discard the generated Tripsheet.</label>",
                                        callback: function() {
                                        	$('#flm' + dash + 'StatusChkd').val('Please Select');
                                        }
                        			
                        			});
                        			 return false;
                        		}
                        	}
                        }

                        var mydate = moment(tr[0].scheduleDate);
                        mydate = mydate.format("YYYY-MM-DD");
                        
                        
                        var zero="00";
                        var q = new Date();
                        var m = q.getMonth();
                        var d = q.getDate();
                        var y = q.getFullYear();
                        q.setHours(zero);
                        q.setMinutes(zero);
                        q.setSeconds(zero);
                        
                        var myDateFormat=mydate;
                        var qDateFormat=moment(q).format("YYYY-MM-DD");
                        mydate = new Date(mydate);
                        mydate.setHours(zero);
                        mydate.setMinutes(zero);
                        mydate.setSeconds(zero);
                        console.log(q);
                        console.log(mydate);

                        if (q < mydate) {
                            dateArr.push(tr[0]);
                        } 
                        else if (qDateFormat == myDateFormat) {
                            var crntTime = moment(new Date(), "HH:mm a");
                            var reqTime = tr[0].reqTime;
                            reqTime = moment(reqTime, "HH:mm a");

                            if ((crntTime.hour() < reqTime.hour())) {
                            	dateArr.push(tr[0]);
                            } 
                            else if(crntTime==reqTime) {
                            	if ((crntTime.minute() < reqTime.minute())) {
                            		dateArr.push(tr[0]);
                            	}
                            }
                        }

                    }));

                    if (dateArr.length > 0) {
                        if ($(this).val() != "Processing") {
                            bootbox.alert({
                                message: "<label>You cannot change the status until the Trip is completed.</label>",
                            });
                            $('#flm' + dash + 'StatusChkd').val('Please Select');
                        } else {
                            /*if ($(this).val() != "Processing") {
                                bootbox.confirm("<label>Once you update with 'Completed or No Show' status, cannot be changed again</label>", function(result) {
                                    if (result == true) {
                                        $('.flm' + dash + 'SubmitBtn').attr('disabled', false);
                                    } else {
                                        $('#flm' + dash + 'StatusChkd').val('Please Select');
                                    }
                                });
                            } else {*/
                                $('.flm' + dash + 'SubmitBtn').attr('disabled', false);
                           /* }*/
                        }
                    } else {
                        /*if ($(this).val() != "Processing") {
                            bootbox.confirm("<label>Once you update with 'Completed or No Show' status, cannot be changed again</label>", function(result) {
                                if (result == true) {
                                    $('.flm' + dash + 'SubmitBtn').attr('disabled', false);
                                } else {
                                    $('#flm' + dash + 'StatusChkd').val('Please Select');
                                }
                            });
                        } else {*/
                            $('.flm' + dash + 'SubmitBtn').attr('disabled', false);
                        /*}*/
                    }
                } else {
                    bootbox.alert({
                        message: "<label>Please select a checkbox.</label>",
                    });
                    $(this).val('Please Select');
                }
            }));

            // Add event listener for opening and closing details 
            $('#submit' + dash + 'Update').click(function() {
                var flmData = [];
                $(table.$('input[type="checkbox"]:checked').each(function() {
                    var id = $(this).closest('tr').attr('id');
                    var trId = JSON.search(viewData, '//*[id="' + id + '"]');
                    var action, status;
                    action = $("#flm" + dash + "ActionChkd").val();
                    status = $("#flm" + dash + "StatusChkd").val();
                    //action=trId[0].action;
                    //status=trId[0].travelStatus;
                    
                    if(trId[0].travelStatus=="Pending with FLM"||trId[0].travelStatus=="Pending with Manager"){
                    	status = "Processing";
                   }
                   else{
                	   if(status=="Please Select"){
                		   status= trId[0].travelStatus;
                	   }
                   }
                    var flmJSON = {
                        "cabDetailsId": id,
                        "action": action,
                        "travelStatus": status
                    };
                    flmData.push(flmJSON);
                }));

                adminService.updateActioAndStatus(flmData)
                    .then(
                        function(response) {
                            if (response.headers('Log_Out') == "true") {
                                window.location = '/tmsApp/login';
                            }
                            console.log(response.data);
                            var response = "<label>" + response.data.data + "</label>";
                            $('#Adhoc_popup').html(response);
                            $('#myModal').modal('show');
                            //$('#flmUpcomingTable').DataTable().destroy();
                            if (dash == "Daily") {
                                $scope.flmDailyRequest('all');
                            } else if (dash == "Upcoming") {
                                $scope.flmUpcomingRequest();
                            } else if (dash == "History") {
                                $scope.getFLMHistory($rootScope.historyData);
                            }
                            //window.location.refresh();
                        },
                        function(errResponse) {
                            console.error('Error while creating User');
                            self.errorMessage = 'Error while creating User: ' + errResponse.data.errorMessage;
                            self.successMessage = '';
                        }
                    );
                console.log(flmData);
            });

            $('#flmUpcomingPanel .tripSheetBtn,#flmUpcomingPanel .statusLabel,#flmUpcomingStatusChkd').hide();
            

            $('.ajax-loader').css("visibility", "hidden");
            $timeout(function() {
                $('[data-toggle="tooltip"]').tooltip();
                if (dash == "Daily") {
                    $('#flmDailyTable_filter').append('<div id="filter" style="float:left"><input type="radio" value="all"  name="report"/>&nbsp;<label>All</label><input type="radio" value="today" name="report"/>&nbsp;<label>Today</label><input type="radio" value="tomorrow" name="report"/>&nbsp;<label>Tomorrow</label>');

                    $timeout(function() {
                        $("input[value='" + reportType + "']").prop("checked", true);

                        $('input[name="report"]').on('change', function() {
                            var today, tomorrow;
                            if ($(this).val() == "today") {
                                $scope.flmDailyRequest('today');
                            } else if ($(this).val() == "tomorrow") {
                                $scope.flmDailyRequest('tomorrow');
                            } else {
                                $scope.flmDailyRequest('all');
                            }
                            /*$timeout(function(){
                            $("input[value='"+$(this).val()+"']").prop("checked",true);
                            },50);*/
                        });
                    }, 30);


                } else {
                    $('#filter').remove();
                }
            }, 100);
        }, 30);
    }
    //-----------------------------Open Sub Rows flm Upcoming-------------------------------
    $('#flmUpcomingTable tbody').off().on('click', 'td.details-control', function() {
        var table = $('#flmUpcomingTable').DataTable();
        var id = $(this).attr('id');
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            $('.openChildRow').remove();
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row

            $('tr').removeClass('shown');
            /*if(dash=="Daily"){
            	row.child(SubRow($rootScope.flmDailyViewData[id])).show();
            }
            else if(dash=="Upcoming"){*/
            row.child(SubRow($rootScope.flmUpcomingViewData[id])).show();
            /*}
            else if(dash=="History"){
            	row.child(SubRow($rootScope.flmHistoryViewData[id])).show();
            }*/

            tr.addClass('shown');
            $('.openChildRow').remove();
            tr.next('tr').addClass('openChildRow');
        }
    });

    //-----------------------------Open Sub Rows flm Daily-------------------------------
    $('#flmDailyTable tbody').off().on('click', 'td.details-control', function() {
        var table = $('#flmDailyTable').DataTable();
        var id = $(this).attr('id');
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            $('.openChildRow').remove();
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row

            $('tr').removeClass('shown');
            /*if(dash=="Daily"){*/
            row.child(SubRow($rootScope.flmDailyViewData[id])).show();
            /*}*/

            /*else if(dash=="Upcoming"){
            	row.child(SubRow($rootScope.flmUpcomingViewData[id])).show();
            }
            else if(dash=="History"){
            	row.child(SubRow($rootScope.flmHistoryViewData[id])).show();
            }*/

            tr.addClass('shown');
            $('.openChildRow').remove();
            tr.next('tr').addClass('openChildRow');
        }
    });

    //-----------------------------Open Sub Rows flm History-------------------------------
    $('#flmHistoryTable tbody').off().on('click', 'td.details-control', function() {
        var table = $('#flmHistoryTable').DataTable();
        var id = $(this).attr('id');
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            $('.openChildRow').remove();
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row

            $('tr').removeClass('shown');
            row.child(SubRow($rootScope.flmHistoryViewData[id])).show();
            tr.addClass('shown');
            $('.openChildRow').remove();
            tr.next('tr').addClass('openChildRow');
        }
    });

    var table;
    //----------------------------FLM Daily--------------------------------
    $scope.flmDailyRequest = function(data) {
    	 $('.ajax-loader').css("visibility", "visible");
        // $('input[name="report"]').val(data);
        adminService.flmDailyReq(data)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    $rootScope.flmDailyViewData = response.data;
                    console.log(response);
                    $('#flmDailyTable').DataTable().destroy();
                    $scope.flmFunction("Daily", $rootScope.flmDailyViewData, data);
                    if (response.data.length <= 0) {
                        $timeout(function() {
                            $('#flmDailyPanel button').attr('disabled', true).css('cursor', 'not-allowed');
                            $('#flmDailyActionChkd').attr('disabled', true);
                        }, 30);
                    }
                    else{
                    	 $timeout(function() {
                             $('#flmDailyActionChkd').attr('disabled', false);
                         }, 30);
                    }
                    
                    $('#flmDailyTable #example-select-all').prop('checked',false);
                    $('.ajax-loader').css("visibility", "hidden");
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                    $('.ajax-loader').css("visibility", "hidden");
                }
            );
    }

    //---------------------------FLM Upcoming---------------------------------------
    $scope.flmUpcomingRequest = function() {
    	 $('.ajax-loader').css("visibility", "visible");
        adminService.flmUpcomingReq()
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    $rootScope.flmUpcomingViewData = response.data;
                    console.log(response.data);
                    $('#flmUpcomingTable').DataTable().destroy();
                    $scope.flmFunction("Upcoming", $rootScope.flmUpcomingViewData);
                    if (response.data.length <= 0) {
                        $timeout(function() {
                            $('#flmUpcomingPanel button').attr('disabled', true).css('cursor', 'not-allowed');
                            $('#flmUpcomingActionChkd').attr('disabled', true);
                        }, 30);
                    }
                    else{
                    	 $timeout(function() {
                             $('#flmUpcomingActionChkd').attr('disabled', false);
                         }, 30);
                    }
                    $('#flmUpcomingTable #example-select-all').prop('checked',false);
                    $('.ajax-loader').css("visibility", "hidden");
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                    $('.ajax-loader').css("visibility", "hidden");
                }
            );
    }
    $scope.history={};
    //---------------------------FLM History------------------------------------
    $scope.Flm_History = function() {
    	$scope.history.fromDate=$('#from').val();
    	$scope.history.toDate=$('#to').val();
        if ($scope.history != undefined) {
            //Object.keys($scope.history).forEach((key) => ($scope.history[key] =="") && delete $scope.history[key]);
            $.each($scope.history, function(key, value) {
                if (value == "" || value == null) {
                    delete $scope.history[key];
                }
            });
        }
        console.log($scope.history);
        if ($scope.history == undefined || Object.keys($scope.history).length <= 0) {
            $scope.class = "alert-danger";
            $('.flmHistoryTable').hide();
            $(".flmHistAlert").css("display", "block");
            $scope.flmHistStatus = 'Please provide input';
            $(".flmHistAlert").delay(3000).fadeOut();
            $timeout(function() {
                $scope.flmHistStatus = "";
            }, 3000);
        } else {
            $rootScope.historyData = $scope.history;
            $scope.getFLMHistory($scope.history);
            $('#flmHistoryTable #example-select-all').prop('checked',false);
        }
    }

    //-------------------getFLM History----------------------
    $scope.getFLMHistory = function(data) {
    	 $('.ajax-loader').css("visibility", "visible");
        adminService.FlmHistoryData(data)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    $('#flmHistoryPanel .panel-body').removeClass('collapse in');
                    $('.dt-button').removeAttr('disabled');
                    console.log(JSON.stringify(response.data));
                    console.log(response.data);
                    $('#flmHistoryTable').DataTable().destroy();
                    $rootScope.flmHistoryViewData = response.data;
                    $('#flmHistoryPanel .panel-body').addClass('collapse');
                    $scope.flmFunction("History", $rootScope.flmHistoryViewData);
                    //$('.dailyExcelBtn').addClass('hidden');
                    $('.flmHistoryTable').show();
                    if (response.data.length == 0) {
                        $timeout(function() {
                            //$('.dailyExcelBtn').addClass('hidden');
                            $('#flmHistoryPanel button').attr('disabled', true).css('cursor', 'not-allowed');
                            $('#flmHistoryActionChkd').attr('disabled', true);
                        }, 30);
                        $('#flmHistoryPanel .panel-body').removeClass('collapse');
                        $('#flmHistoryPanel .panel-body').addClass('collapse in');
                    } else {
                        $timeout(function() {
                            //$('.dailyExcelBtn').addClass('hidden');
                            $('#flmHistoryPanel button').attr('disabled', false).css('cursor', 'pointer');
                            $('#flmHistoryActionChkd').attr('disabled', false);
                            $timeout(function() {
                            for(var i=0;i<response.data.length;i++){
                            	if(response.data[i].activeRequest==false){
                            		var id=response.data[i].id;
                            		$('#flmHistoryTable').DataTable().$('.td'+id+' input[type="checkbox"]').prop('disabled', true);
                            		//$('.td'+id+' input[type="checkbox"]').attr('disabled',true);
                            	}
                            }
                            $('input[type="checkbox"]').prop('checked',false);
                            },60);
                            $('.ajax-loader').css("visibility", "hidden");
                        }, 30);
                    }
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                    $('.ajax-loader').css("visibility", "hidden");
                }
            );
    }

    //-----------------------history rest button------------------
    $scope.resetHistorySearch = function() {
    	$('#histrySearchForm')[0].reset();
        $scope.history = "";
        $('.flmHistoryTable').hide();
        $('#histrySearchForm .glyphicon-remove').addClass('hidden');
    }
    // ------------------------------show trip sheet popup-----------------------
    var newCabId;
    $scope.addTripSheet = function(id, dash) {
        $scope.trip = "";
        if (dash == "Upcoming") {
            var res1 = JSON.search($rootScope.flmUpcomingViewData, '//*[id="' + id + '"]');
        } else {
            var res1 = JSON.search($rootScope.flmHistoryViewData, '//*[id="' + id + '"]');
        }
        res1 = res1[0].scheduleDate;
        $('#tripSheetModel input,#tripSheetModel span').attr('disabled', false);
        $('.date span').css('display', '');
        $('#tripSheetModel input[name="tripSheetNumber"],.createTripSht,.updateTripSht,#escortfor,#escortName').removeAttr('disabled');
        $('#tripSheetModel').modal('show');
        $('.createTripSht').val('Create');
        $('.updateTripSht').addClass('hidden');
        $('.createTripSht').show();
        newCabId = id;
        var date = moment(res1); //Get the current date
        res1 = date.format("DD-MM-YYYY");
        $scope.getTripSheetNumber(res1);
        $timeout(function() {
            res1 = {
                "tripDate": res1
            };
            $scope.trip = res1;
        }, 30);
    }

    //----------------------------Create Trip Sheet-------------------------------------------------
    $("input[name='tripSheetNumber']").on('input', function() {
        var tripDate = $('input[name="tripDate"]').val();
        $('#tripSheetModel .form-control,#tripSheetModel span').attr('disabled', false);
        $('.date span').css('display', '');
        $('#tripSheetModel input[name="tripSheetNumber"],.createTripSht,.updateTripSht,#escortfor,#escortName').removeAttr('disabled');
        $scope.trip = {
            "isEscort": false
        };
        $rootScope.escort = "";
        $('#isEscortDiv').show();
        $('#escortName').hide();
        $scope.trip.tripDate = tripDate;
        //$scope.trip.isEscort=false;
        var val = this.value;
        console.log(val);
        if ($("#tripSheet option").filter(function() {
                return this.value === val;
            }).length) {
            var opt = $('option[value="' + $(this).val() + '"]').attr('id');
            var res = JSON.search($rootScope.tripSheetList, '//*[tripId="' + opt + '"]');
            $scope.trip = res[0];
            if ($scope.trip.escortName == null || $scope.trip.escortName == undefined) {
                $('#escortName').hide();
            } else {
                $('#escortName').show();
                $rootScope.escort = $scope.trip.escortName;
            }
            $('#tripSheetModel .form-control,#tripSheetModel span').attr('disabled', 'disabled');
            $('.date span').css('display', 'none');
            $('#tripSheetModel input[name="tripSheetNumber"],.createTripSht,.updateTripSht,#escortfor,#escortName').removeAttr('disabled');
        }
    });
    var opt, bool;

    $scope.createTripSheet = function(type, id, cabid) {

        if (type == "create") {
            console.log($scope.trip);
            var cabDetails = [];
            $(table.$('input[type="checkbox"]:checked').each(function() {
                var id = $(this).closest('tr').attr('id');
                var x = parseInt(id);
                cabDetails.push(x);
            }));
            var cabDetail = [];
            console.log($scope.trip.escortFor);
            for (var i = 0; i < cabDetails.length; i++) {
                if ($scope.trip.isEscort == false || $scope.trip.isEscort == undefined) {
                    var x = {
                        "cabDetailsId": cabDetails[i],
                        "isEscort": false
                    };
                } else {
                    var flmReq = JSON.search($rootScope.femaleTraveler, '//*[id="' + cabDetails[i] + '"]');
                    if (flmReq.length > 0) {
                        var x = {
                            "cabDetailsId": flmReq[0].id,
                            "isEscort": true,
                            "escortName": $scope.trip.escortName
                        }
                    } else {
                        var x = {
                            "cabDetailsId": cabDetails[i],
                            "isEscort": false
                        };
                    }
                }
                cabDetail.push(x);
            }
            console.log(cabDetail);
            if (cabDetails.length <= 0) {
                cabDetails = [newCabId];
            }
            opt = $('input[name="tripSheetNumber"]').val();
            opt = $('option[value="' + opt + '"]').attr('id');
            $scope.tripSheetId = opt;
            var bool;
            if (opt != undefined) {
                bool = true;
            } else {
                bool = false;
            }
            console.log($scope.trip);
            $scope.trip.vendorId = $('select[name="vendorName"] option:selected').attr('id');
            $scope.trip.tripSheetNumber = parseInt($scope.trip.tripSheetNumber);
            $scope.trip.empCabRequestDetails = cabDetail;
            delete $scope.trip.escortFor;
            adminService.createTripSheet($scope.trip, bool, $scope.tripSheetId)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        console.log(response);
                        if (response.data.data == "false") {
                            response.data.data = "Trip SheetNo already exists";
                        } else {
                            $('#tripSheetModel').modal('hide');
                            $scope.flmUpcomingRequest();
                            $scope.flmDailyRequest('all');
                            if ($rootScope.historyData != null && $rootScope.historyData != undefined) {
                                $scope.getFLMHistory($rootScope.historyData);
                            }
                            //$scope.Flm_History();
                        }
                        var response = "<label>" + response.data.data + "</label>";
                        $('#Adhoc_popup').html(response);;
                        $('#myModal').modal('show');
                    },
                    function(errResponse) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    }
                );
        } else if (type == "update") {

            $scope.updateTripSheet(id, cabid);
        }
    }
    //-----------------update Trip Sheet-----------------
    $scope.updateTripSheet = function(id, cabid) {
        if ($scope.trip.isEscort == false || $scope.trip.isEscort == undefined) {
            var x = {
                "cabDetailsId": cabid,
                "isEscort": false
            };
            
        } else {
            var x = {
                "cabDetailsId": cabid,
                "isEscort": true,
                "escortName": $scope.trip.escortName
            };
        }
        $scope.trip.empCabRequestDetails = [x];
        delete $scope.trip.reqId;
        delete $scope.trip.escortFor;
        console.log($scope.trip);
        $scope.trip.vendorId = $('select[name="vendorName"] option:selected').attr('id');
        adminService.editAndUpdateTripSheet(false, id, cabid, $scope.trip)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(response);
                    $('#tripSheetModel').modal('hide');
                    var response = "<label>" + response.data.data + "</label>";
                    $('#Adhoc_popup').html(response);
                    $('#myModal').modal('show');
                    $scope.flmUpcomingRequest();
                    $scope.flmDailyRequest('all');
                    if ($rootScope.historyData != null && $rootScope.historyData != undefined) {
                        $scope.getFLMHistory($rootScope.historyData);
                    }
                    //$scope.Flm_History();
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }
    //--------------------------Discard employee from tripsheet-------------------
    $scope.discardTrip = function(cabid, tripid) {
    	if(tripid==undefined){
    		 bootbox.alert({
                 message: "<label>You cannot discard a Tripsheet without Tripsheet No.</label>"
             });
    	}
    	else{
        bootbox.confirm("<label>Are you sure want to discard the request?</label>", function(result) {
            if (result == true) {
                adminService.discardTrip(cabid, tripid)
                    .then(
                        function(response) {
                            if (response.headers('Log_Out') == "true") {
                                window.location = '/tmsApp/login';
                            }
                            $('#tripSheetModel').modal('hide');
                            $scope.flmUpcomingRequest();
                            $scope.flmDailyRequest('all');
                            if ($rootScope.historyData != null && $rootScope.historyData != undefined) {
                                $scope.getFLMHistory($rootScope.historyData);
                            }
                            console.log(response);
                        },
                        function(errResponse) {
                            console.error('Error while creating User');
                            self.errorMessage = 'Error while creating User: ' + errResponse.data.errorMessage;
                            self.successMessage = '';
                        }
                    );
            }
        });
    	}
    }

    //-----------------Escort Name-----------------------
    $('input[name="isEscort"]').off().on('change', (function() {
        var val = $(this).val();
        if (val == "true") {
            if ($rootScope.escort != "" | $rootScope.escort != undefined) {
                $scope.trip.escortName = $rootScope.escort;
                $scope.$apply();
                console.log($rootScope.escort);
            }
            $('#escortName').show();

        } else {
            $('#escortName').hide();
            $scope.trip.escortName = "";
            $('input[name="escortName"]').val("");
        }
    }));

    //------------------------Edit Trip Sheet-------------------------------
    $scope.EditTripSheet = function(id, cabid, data, sDate,reqType) {
        $('#tripSheetModel input,#tripSheetModel span').attr('disabled', false);
        $('.date span').css('display', '');
        $('#tripSheetModel input[name="tripSheetNumber"],.createTripSht').removeAttr('disabled');
        if (id == null) {
            $scope.trip = "";
            $('#tripSheetModel').modal('show');
            $scope.tripType = "create";
            $('.createTripSht').val('Create');
            $('.discardTripBtn').addClass('hidden');
            $scope.getTripSheetNumber();
            $rootScope.escortName = "";
        } else {
            $('.createTripSht').val('Update');
            $('.discardTripBtn').removeClass('hidden');
            $scope.tripType = "update";
            //$('.updateTripSht').removeClass('hidden');
            $scope.getAndUpdateTripData(true, id, cabid, null, data);

        }
        var date = moment(sDate); //Get the current date
        date = date.format("DD-MM-YYYY");
        $scope.getTripSheetNumber(date,reqType);
    }
    $scope.getTripSheetNumber = function(sDate,reqType) {
        adminService.getTripSheetNumber()
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(response);
                    var shdlDateTripList = JSON.search(response.data, '//*[tripDate="' + sDate + '"]');
                    var reqList= JSON.search(response.data, '//*[requestType="' + reqType + '"]');
                    $rootScope.tripSheetList = reqList;
                    //$rootScope.tripSheetList = response.data;
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }
    $scope.getAndUpdateTripData = function(getDetails, id, cabid, data, viewData) {
        adminService.editAndUpdateTripSheet(getDetails, id, cabid, data)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(response);
                    var escortFor = JSON.search(viewData, '//*[id="' + cabid + '"]');
                    $rootScope.femaleTraveler = escortFor;
                    $scope.trip = response.data;
                    $rootScope.escort = $scope.trip.escortName;
                    if ($scope.trip.isEscort == true) {
                        $timeout(function() {
                            $('#escortfor').val([escortFor[0].userId]).trigger('change');;
                        }, 30);
                        $('#isEscortDiv').show();
                        $('#escortName').show();
                    } else {
                        $('#isEscortDiv').show();
                        $('#escortName').hide();
                    }
                    $scope.reqId = cabid;
                    var val = $scope.trip.isEscort;
                    /*if (viewData.empGender=="M") {
                    	$('#escortName').hide();
                    	$('#isEscortDiv').hide();
                    }*/
                    $('#tripSheetModel').modal('show');
                    $('.createTripSht').val('Update');
                    $('#tripForm').attr('ng-submit', 'updateTripSheet(trip.TripId)');
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    //------------------------------trip view page--------------------------------

    $scope.getTripSheetView = function() {
    	 $('.ajax-loader').css("visibility", "visible");
        adminService.getAllTripSheetDetail()
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    $('#flmTripTable').DataTable().destroy();
                    console.log(response.data);
                    $rootScope.tripViewData = response.data;
                    $timeout(function() {
                        var tab = $('#flmTripTable').DataTable({
                            responsive: true,
                        });

                        $('#flmTripTable tbody').off().on('click', 'td.details-control', function() {
                            var id = $(this).attr('id');
                            var tr = $(this).closest('tr');
                            var row = tab.row(tr);

                            if (row.child.isShown()) {
                                $('.openChildRow').remove();
                                row.child.hide();
                                tr.removeClass('shown');
                            } else {
                                // Open this row
                                $('tr').removeClass('shown');
                                console.log($rootScope.tripViewData);
                                row.child(tripViewSubRow($rootScope.tripViewData[id].empCabRequestDetails)).show();
                                tr.addClass('shown');
                                $('.openChildRow').remove();
                                tr.next('tr').addClass('openChildRow');
                            }


                        });
                        $('#flmTripTable').wrap('<div class="dataTables_scroll" />');
                        $('.ajax-loader').css("visibility", "hidden");
                    }, 30);
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                    $('.ajax-loader').css("visibility", "hidden");
                }
            );
    }
    //------------------------------vendor cretion----------------------------------------------
    var vendorSpocs = [{
        "spocId": "",
        "spocName": "",
        "spocMobileNumber": ""
    }];
    $scope.vendorSpocs = vendorSpocs;
    $scope.addSPOC = function() {
        var spoc = {
            "spocId": "",
            "spocName": "",
            "spocMobileNumber": ""
        };
        $scope.vendorSpocs.push(spoc);
        $timeout(function() {
            $scope.checkLength();
        }, 20);
        console.log($scope.vendorSpocs.length);
    }
    $scope.checkLength = function() {
        var len = $scope.vendorSpocs.length
        if (len > 1) {
            $('.delSpoc').removeClass('hidden');
        } else {
            $('.delSpoc').addClass('hidden');
        }
        if (len >= 5) {
            $('.addSpoc').addClass('hidden');
        } else {
            $('.addSpoc').removeClass('hidden');
        }
    }
    $scope.removeSpoc = function(id, row) {
        console.log(id);
        if (id != "") {
        	 bootbox.confirm("<label>Are you sure want to delete?</label>", function(result) {
                 if (result == true) {
            adminService.deleteVendorDetail(id, "spoc")
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        console.log(response);
                        $scope.getAllVendor();
                        $scope.checkLength();
                        var removeIndex = $scope.vendorSpocs.map(function(item) {
                            return item.spocId;
                        }).indexOf(id);
                        $scope.vendorSpocs.splice(removeIndex, 1);
                    },
                    function(errResponse) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    }
                );
                 }
                 });

        } else {
            $scope.vendorSpocs.splice(row, 1);
            console.log($scope.vendorSpocs);
            $scope.checkLength();
        }
    }

    $scope.deleteVendor = function(id) {
        console.log(id);
        bootbox.confirm("<label>Are you sure want to delete?</label>", function(result) {
            if (result == true) {
                adminService.deleteVendorDetail(id, "vendor")
                    .then(
                        function(response) {
                            if (response.headers('Log_Out') == "true") {
                                window.location = '/tmsApp/login';
                            }
                            console.log(response);
                            $scope.getAllVendor();
                            $scope.vendorReset();
                        },
                        function(errResponse) {
                        	bootbox.alert({
                                message: "<label>Something went wrong.Inernal error.</label>"
                            });
                        }
                    );
            }
        });
    }
    $scope.vendorReset = function() {
        $scope.vendor = {};
        $scope.vendorSpocs = [{
            "spocId": "",
            "spocName": "",
            "spocMobileNumber": ""
        }];
        $('.vendorSubmit').val('Submit');
        $timeout(function() {
            $scope.checkLength();
            $('.vendorBody .form-control').removeAttr('style');
        }, 15);
        // $('.addSpoc').addClass('hidden');
    }
    $scope.getAllVendor = function() {
        $('.ajax-loader').css("visibility", "visible");
        adminService.getVendorDetail(null)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(JSON.stringify(response.data));
                    $scope.allVendorData = response.data;
                    $scope.vendorDrop = response.data;
                    $('#vendorTable').DataTable().destroy();
                    $timeout(function() {
                        var table = $('#vendorTable').DataTable({
                            responsive: true,
                        });
                        $('#vendorTable tbody').off().on('click', 'td.details-control', function() {
                            var id = $(this).attr('id');
                            var tr = $(this).closest('tr');
                            var row = table.row(tr);
                            if (row.child.isShown()) {
                                $('.openChildRow').remove();
                                row.child.hide();
                                tr.removeClass('shown');
                            } else {

                                $('tr').removeClass('shown');
                                console.log($rootScope.tripViewData);
                                row.child(vendorTable(response.data[id].vendorSpocs)).show();
                                tr.addClass('shown');
                                $('.openChildRow').remove();
                                tr.next('tr').addClass('openChildRow');
                            }
                        });
                        $('#vendorTable').wrap('<div class="dataTables_scroll" />');
                        $scope.checkLength();
                        $('.user_detail').removeClass('hidden');
                        $("<div style='padding-top: 10px; padding-right: 15px;'  class='row addVendor'><a class='fa fa-plus-circle fa-lg' data-toggle='tooltip' title='Add New Vendor' style='float: right;'> Add Vendor</a>").insertBefore(".vendorCont .dataTables_paginate ");
                        $timeout(function() {
                            $('.addVendor').click(function() {
                            	$scope.vendorReset();
                                $('#vendorModal').modal('show');
                                $('.vendorReset').show();
                            });
                        }, 60);
                        $('.ajax-loader').css("visibility", "hidden");
                    }, 30);
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }


    $scope.createVendor = function() {
        $scope.vendor.vendorSpocs = $scope.vendorSpocs;
        console.log($scope.vendor);
        adminService.createAndUpdateVendor($scope.vendor, "saveUpdate")
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    $('#vendorModal').modal('hide');
                    console.log(response);
                    $scope.vendorReset();
                    //var response = "<label>" + response.data + "</label>";
                    //$('#Adhoc_popup').html(response);
                    //$('#myModal').modal('show');
                    $scope.class = "alert-success";
                    $(".vendorAlert").css("display", "block");
                    $scope.vendorAlertStatus = 'Saved successfully.';
                    $(".vendorAlert").delay(3000).fadeOut();
                    $timeout(function() {
                        $scope.vendorAlertStatus = "";
                    }, 3000);
                    $('#vendorBody').removeClass('in').attr('aria-expanded', true).css("height", "");
                    $('.vendor .faq-links i').removeClass('fa-minus').addClass('fa-plus');
                    //$('.vendor .faq-links').addClass();
                    $scope.getAllVendor();

                    location.reload();
                },
                function(errResponse) {
                    $scope.class = "alert-danger";
                    $(".vendorAlert").css("display", "block");
                    $scope.vendorAlertStatus = 'Something went wrong. Please try after sometime.';
                    $(".vendorAlert").delay(3000).fadeOut();
                    $timeout(function() {
                        $scope.vendorAlertStatus = "";
                    }, 3000);
                }
            );
    }
    
    
    $('.adminpage #fromClear').click(function(){
    	$(this).siblings().val('');
    	$(this).addClass('hidden');
    	$scope.history.fromDate="";
   	    $scope.$apply();
    });
    $('.adminpage #toClear').click(function(){
    	$(this).siblings().val('');
    	$(this).addClass('hidden');
    	$scope.history.toDate="";
    	$scope.$apply();
    });
    
    $scope.editVendor = function(vendorId) {
        console.log(vendorId);
        adminService.getVendorDetail(parseInt(vendorId))
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(response.data);
                    $rootScope.editData = response.data[0];
                    $scope.vendor = response.data[0];
                    $('.vendorSubmit').val('Update');
                    $('.vendorReset').hide();
                    $scope.vendorSpocs = response.data[0].vendorSpocs;
                    $('#vendorModal').modal('show');
                    $timeout(function() {
                        $scope.checkLength();
                        $('.vendorBody .form-control').css({
                            "border-color": " #66afe9",
                            "box-shadow": "inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102,175,233,.6)"
                        });
                    }, 30);
                    $('#vendorBody').addClass('collapse in').attr('aria-expanded', true).css("height", "");
                    $('.vendor .faq-links i').removeClass('fa-plus').addClass('fa-minus');
                    //$('.addSpoc').removeClass('hidden');
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    //-----------------------------------------Employee Project Mapping----------------------------------------------
    
    $scope.getEmpList=function(){
    	 adminService.getEmpList()
         .then(
             function(response) {
                 if (response.headers('Log_Out') == "true") {
                     window.location = '/tmsApp/login';
                 }
                 console.log(response);
                 $rootScope.projEmpList=response.data;
             },
             function(errResponse) {
            	 bootbox.alert({
                     message: "<label>Something went wrong.Inernal error.</label>"
                 });
             }
         );
    }
    
    $("input[name='requestedEmpList']").bind('input', function() {
        if (checkExists($("input[name='requestedEmpList']").val()) === true) {
            var opt = $('option[value="' + $(this).val() + '"]').attr('id');
            if (opt != undefined) {
            	$('.project').removeClass('hidden');
            	$('.saveBtn').removeAttr('hidden');
            	 $scope.clearSelected();
            	var res = JSON.search($rootScope.projEmpList, '//*[id="' + opt + '"]');
            	 res = JSON.search(res, '//*[name="' +$(this).val()+ '"]');
            	for(var i=0;i<res[0].empProjBean.length;i++){
            		opt=res[0].empProjBean[i].projectId;
            		if(res[0].empProjBean[i].id!=null){           		            		
            		//$('.'+opt).click();            		
            		$('.'+opt).css({"opacity":"0.6","cursor":"not-allowed"}).attr('disabled',true).addClass('default');
            		$('.'+opt+' label').css("cursor","not-allowed");
            		}
            		else{
            			$('.'+opt).click();
            		}
            	}
            	//$('.opt .selected').click();
            } else {
            	$('.project').addClass('hidden');
            	$('.saveBtn').attr('hidden',true);
            	 $scope.clearSelected();
            }
        }
    });
    
 $("input[name='requestedEmpList']").focusout(function() {
        var val = $("input[name='requestedEmpList']").val();
        if ($('#projectEmployeesList option').filter(function() {
                return this.value === val;
            }).length) {

        } else {
            $("input[name='requestedEmpList']").val('');
            $('.project .form-control-clear').addClass('hidden');
        }
    });
    
    $('#empClear').click(function() {
        $(this).siblings('input').val('')
            .trigger('propertychange').focus();
       $('.project ').addClass('hidden');
       $('.saveBtn').attr('hidden',true);
       $scope.clearSelected();
    });
    
 $('.SlectBox').SumoSelect({  okCancelInMulti: true });
 
 $scope.clearSelected=function(){
	 	var obj = [];
	 	$('.options .opt').removeClass('default');
	    $('option:selected').each(function () {
	        obj.push($(this).index());
	    });

	    for (var i = 0; i < obj.length; i++) {
	        $('.projectSelect')[0].sumo.unSelectItem(obj[i]);
	    }
	/* var num = $('.projectSelect option').length;
	    for(var i=1; i<num; i++){
	    $('.projectSelect')[0].sumo.unSelectAll();
	    } */
	    $('.options ul li').css({"pointer-events":"","opacity":""}).attr('disabled',false).removeClass('selected');
 }
    
    
    $scope.saveSelectedProject=function(){
    	var obj = [];
    	$('.projectSelect option:selected').each(function(i) {
    	obj.push($(this).val());
    	
    	});
    	var userId = $('input[name="requestedEmpList"]').val();
           	userId= $('#projectEmployeesList option[value="' + userId + '"]').attr('id');
    	
    	var data={"userId":parseInt(userId),"projects":obj};
    	//alert(JSON.stringify(data));
    	console.log(data);
    	if(obj.length>0){
    	adminService.sendSelcectedProj(data)
        .then(
            function(response) {
                if (response.headers('Log_Out') == "true") {
                    window.location = '/tmsApp/login';
                }
                $scope.getEmpList();
                $scope.class = "alert-success";
                $(".projMapAlert").css("display", "block");
                $scope.projMapAlertStatus =response.data.data;
                $(".projMapAlert").delay(3000).fadeOut();
                $timeout(function() {
                    $scope.projMapAlertStatus = "";
                }, 3000);
            },
            function(errResponse) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            }
        );
    	}
    	else{
    		 $scope.class = "alert-danger";
             $(".projMapAlert").css("display", "block");
             $scope.projMapAlertStatus = 'Please select project.';
             $(".projMapAlert").delay(3000).fadeOut();
             $timeout(function() {
                 $scope.projMapAlertStatus = "";
             }, 3000);
    		
    		/*bootbox.alert({
                message: "<label>Please select project.</label>"
            });*/
    	}
    }
    function checkExists(inputValue) {
        var x = document.getElementById("projectEmployeesList");
        var i;
        var flag;
        for (i = 0; i < x.options.length; i++) {
            if (inputValue == x.options[i].value) {
                flag = true;
            }
        }
        return flag;
    }
    
    //-----------------------------------------------------------------------------------------------------------
    
    
    $('#fromClear,#toClear').click(function(){
    	$(this).siblings().val('');
    	 $(this).addClass('hidden');
    });

    function SubRow(d) {
        var sDate = moment(d.requestDate); //Get the current date
        sDate = sDate.format("DD/MM/YYYY");

        return '<div class="col-sm-12 col-lg-10 col-lg-offset-1">' +
        '<div class="form-group row col-sm-12 col-lg-6 col-md-6 text-left">' +
            '<label  class="col-lg-4 col-form-label">From Address </label><label  class="col-lg-8 col-form-label">: ' + d.fromAddress + ', ' + d.fromLandmark + ', ' + d.fromLocation + ', ' + d.fromCity + ', ' + d.fromPincode + '</label>' +
            '</div>' +
            '<div class="form-group row col-sm-12 col-lg-6 col-md-6 text-left">' +
            '<label  class="col-lg-4 col-form-label">To Address </label><label  class="col-lg-8 col-form-label">: ' + d.toAddress + ', ' + d.toLandmark + ', ' + d.toLocation + ', ' + d.toCity + ', ' + d.toPincode + '</label>' +
            '</div></div>'
    }


    function tripViewSubRow(d) {
        //console.log(d[0].mobile );
        var final = '<table><thead><tr><th>Doc No</th><th>Emp Name</th><th>Mobile</th><th>Project</th><th>From</th><th>To</th><th>Travel Status</th></tr></thead><tbody>';
        if (d.length > 0) {
            for (var i = 0; i < d.length; i++) {
                var html = '<tr><td>' + d[i].id + '</td><td>' + d[i].empName + '</td><td>' + d[i].mobileNumber + '</td><td>' + d[i].projectName + '</td><td>' + d[i].fromLocation + '</td><td>' + d[i].toLocation + '</td><td>' + d[i].travelStatus + '</td></tr>';
                final = final + html;
            }
            final = final + '</tbody></table>';
        } else {
            var html = "</tbody></table><div class='text-center' style='padding: 5px;border-bottom: 1px solid;'><b>No Data Available</b></div>";
            final = final + html;
        }
        return final;
    }

    function vendorTable(d) {
        //console.log(d[0].mobile );
        var final = '<table><thead><tr><th>Doc No</th><th>SPOC Name</th><th>Mobile Number</th></tr></thead><tbody>';
        for (var i = 0; i < d.length; i++) {
            var html = '<tr><td>' + d[i].spocId + '</td><td>' + d[i].spocName + '</td><td>' + d[i].spocMobileNumber + '</td></tr>';
            final = final + html;
        }
        return final + '</tbody></table>';
    }
}]);