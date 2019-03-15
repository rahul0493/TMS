'use strict';

angular.module('tmsApp').controller('spocController', ['spocService', '$scope', '$rootScope', '$timeout', function(spocService, $scope, $rootScope, $timeout) {
    $scope.projectShiftDetail = true;
    var projectDetail = [];
    
    //--------------convert 24 hrs to 12 hrs--------------------
    var startTime,endTime;
    $scope.convertTimeTo12hrs=function(time,type,index){    	
    	var hrs = Number(time.match(/^(\d+)/)[1]);
    	var mnts = Number(time.match(/:(\d+)/)[1]);
    	var format = time.match(/\s(.*)$/)[1];
    	if (format == "PM" && hrs < 12) hrs = hrs + 12;
    	if (format == "AM" && hrs == 12) hrs = hrs - 12;
    	var hours = hrs.toString();
    	var minutes = mnts.toString();
    	if (hrs < 10) hours = "0" + hours;
    	if (mnts < 10) minutes = "0" + minutes;
    	var timeString=hours + ":" + minutes;
        if(type=="start"){
        	startTime=timeString;
        }
        else{
        	endTime=timeString;
        }
       }
    //-------------------------------getProjectShiftDetails-----------------------------------
    
    $scope.getProjectShiftDetails = function() {
        //$('.Project_shift').addClass('hidden');
        $('.ajax-loader').css("visibility", "visible");
        spocService.getProjectShiftDetails()
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    response.data = JSON.parse(response.data);
                    for (var i in response.data) {
                        for (var j in response.data[i]) {
                            response.data[i][j].trackDetails = response.data[i][j].trackDetailsList;
                            for (var q in response.data[i][j].trackDetails) {
                                response.data[i][j].trackDetails[q].shiftDetails = response.data[i][j].trackDetails[q].shiftDetailsList;
                                for (var k in response.data[i][j].trackDetails[q].shiftDetails) {
                                	$scope.convertTimeTo12hrs(response.data[i][j].trackDetails[q].shiftDetails[k].startTime,"start",k);
                                	response.data[i][j].trackDetails[q].shiftDetails[k].startTime=startTime;
                                	$scope.convertTimeTo12hrs(response.data[i][j].trackDetails[q].shiftDetails[k].endTime,"end",k);
                                	response.data[i][j].trackDetails[q].shiftDetails[k].endTime=endTime;
                                }
                            }
                        }
                    }
                    $scope.projectShiftDetails = response.data;
                    console.log(response.data);
                    // $scope.$apply();
                    $timeout(function() {
                        $('.ajax-loader').css("visibility", "hidden");
                        $('.Project_shift').removeClass('hidden');
                        $('.fa-trash-o').addClass('hidden');
                    }, 100);
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }
    //----------------------createNewShiftTrack---------------------------------
    $scope.createNewShiftTrack = function() {
        $scope.chkTrckLength($scope.trackProject);
        $scope.newTrack = true;
        $scope.shiftDetail = false;
        $scope.shiftTime = false;
        $('#trackDrp').val('');
        $('.newTrack').attr('ng-required', true);
    }
    //----------convert 24 hrs time to 12 hrs format------------------
    $scope.convertTime=function(hrs,type,index){
    var timeString = hrs;
    var hourEnd = timeString.indexOf(":");
    var H = +timeString.substr(0, hourEnd);
    var h = H % 12 || 12;
    if(h<10){
    	h="0"+h;
    }
    var ampm = H < 12 ? "AM" : "PM";
    timeString = h + timeString.substr(hourEnd, 3) +" "+ampm;
    if(type=="start"){
    	$scope.shiftDetails[index].startTime=timeString;
    	//$scope.$apply();
    }
    else{
    	$scope.shiftDetails[index].endTime=timeString;
    	//$scope.$apply();
    }
    }
    //----------------------Create project shift--------------------------------------------------------
    $scope.createProjectShift = function() {
        var trckName = $scope.project.trackDetails.trackName;
        $scope.project.trackDetails = {
            "trackName": trckName
        };
        $scope.project.accountId = $('#project_accntDrp option:selected').attr('id');
        $scope.project.projectId = $('#projectname option:selected').attr('id');
        $scope.project.trackDetails.trackDetailsId = $('#trackDrp option:selected').attr('id');
        for(var i=0;i<($scope.shiftDetails).length;i++){
        	$scope.convertTime($scope.shiftDetails[i].startTime,"start",i);
        	$scope.convertTime($scope.shiftDetails[i].endTime,"end",i);
        }
        $scope.project.trackDetails.shiftDetails = $scope.shiftDetails;
        
        $scope.project.trackDetails = [$scope.project.trackDetails];
        console.log(JSON.stringify($scope.project));
        spocService.ProjectShiftEditUpdateDelete($scope.project.projectId, $scope.project.accountId, $scope.project.trackDetails.trackDetailsId, 'update', $scope.project)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(response.data);
                    //$scope.projectDetails = false;
                    //$scope.trackerDetails = false;
                    // $scope.projectDetails = false;
                    //$scope.shiftDetail = false;
                    for(var i=0;i<($scope.shiftDetails).length;i++){
                    	$scope.convertTimeTo12hrs($scope.shiftDetails[i].startTime,"start",i);
                    	$scope.shiftDetails[i].startTime=startTime;
                    	$scope.convertTimeTo12hrs($scope.shiftDetails[i].endTime,"end",i);
                    	$scope.shiftDetails[i].endTime=endTime;
                    }
                    var response = "<label>" + response.data.data + "</label>";
                    $('#Adhoc_popup').html(response);
                    $('#myModal').modal('show');
                    $scope.getProjectShiftDetails();
                    $('#trackDrp').val(trckName);
                    $scope.project.trackDetails.trackName = trckName;
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    //-----------------------------------add project shift account dropdwn------------------------------------------------
    $('#project_accntDrp').on('change', (function() {
        var value = $('#project_accntDrp option:selected').attr('id');
        $scope.getShiftProjList(value);
        $('#projectname').val("");
        if ($scope.project != undefined) {
            $scope.project.projectName = "";
        }
        $scope.newTrack = false;
        $scope.shiftDetail = false;
        $scope.trackRequires = false;
        $scope.trackerDetails = false;
    }));


    $scope.getShiftProjList = function(value) {
        spocService.getShiftProjectList(value)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(response);
                    for (var i in response.data) {
                        response.data[i].trackDetails = response.data[i].trackDetailsList;
                    }
                    $rootScope.projectList = response.data;
                    $scope.projectDetails = true;
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }
    $('#projectname').on('change', (function() {
        var roles = $('#rolesList').val();
        $scope.checkRoles(roles);
        $scope.trackerDetails = false;
        $scope.shiftDetail = false;
        $scope.newTrack = false;
        $scope.shiftDetails = [];
        $('#prjct_shift').val('');
        var id = $('#projectname option:selected').attr('id');
        $scope.selectShiftProject(id);
        $('.newTrack').attr('ng-required', false);
    }));
    var str;
    $scope.checkRoles = function(roles) {
        str = roles;
        str = str.replace('[', '');
        str = str.replace(']', '');
        str = str.split(",");
        for (var i = 0; i < str.length; i++) {
            str[i] = str[i].trim();
            if (str[i] == "SENIOR_MANAGER") {
                str = true;
                break;
            }
        }
    }

    $scope.selectShiftProject = function(id) {
        var res = JSON.search($rootScope.projectList, '//*[projectId="' + id + '"]');
        $scope.trackList = res[0].trackDetails;
        if (res[0].trackDetails.length > 0) {
            $scope.trackRequires = false;
            $scope.trackerDetails = true;
            $timeout(function() {
                $('#trackDrp').val('');
                if ($scope.project.trackDetails != undefined) {
                    $scope.project.trackDetails.trackName = "";
                }
            }, 50);
            if (str == true) {
                $scope.addTrack = true;
            }
        } else {
            $scope.trackRequires = true;
            $('[name="trackRequires"]').prop('checked', false);
        }
    }
    $scope.checktrack = function(val, projectName, roles) {
        if (val == "yes") {
            //$scope.newTrack=true;
            $scope.shiftDetail = false;
            // $('.newTrack').attr('ng-required',true);
            // $scope.chkTrckLength($scope.trackProject);
            console.log(JSON.stringify(roles));
            $scope.checkRoles(roles);
            if (str == true) {
                //window.location.href = "/tmsApp/tms/track/track_details";
                $scope.newTrack = true;
                $scope.shiftDetail = false;
                $scope.shiftTime = false;
                $scope.addTrack = true;
                $('.newTrack').attr('ng-required', true);
            } else {
                bootbox.alert({
                    message: "<label>Please contact senior Manager to add Track.</label>",
                    callback: function() {
                        $('[name="trackRequires"]').prop('checked', false);
                    }
                });
            }
        } else {


            bootbox.confirm("<label>Default Track is created with name '" + projectName + "'</label>", function(result) {
                if (result == true) {
                    var prjctName = $('#projectname option:selected').val();
                    $scope.project.trackDetails = {
                        "trackName": prjctName,
                        "shiftDetails": ""
                    };
                    console.log($scope.project);
                    $scope.newTrack = false;
                    $scope.shiftDetail = true;
                    $scope.shiftTime = false;
                    $('.newTrack').attr('ng-required', false);
                    $scope.trackRequires = false;
                } else {
                    $scope.trackRequires = true;
                    $('[name="trackRequires"]').prop('checked', false);
                }
                $scope.$apply();
            });
        }
    }
    $('#trackDrp').on('change', (function() {
        $scope.shiftName = "";
        var id = $('#trackDrp option:selected').attr('id');
        spocService.getShiftDetailsByTrack(id)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    if (response.data.length > 0) {
                    	for(var i=0;i<response.data.length;i++){
                        	$scope.convertTimeTo12hrs(response.data[i].startTime,"start",i);
                        	response.data[i].startTime=startTime;
                        	$scope.convertTimeTo12hrs(response.data[i].endTime,"end",i);
                        	response.data[i].endTime=endTime;
                        }  
                        $scope.shiftDetails = response.data;
                        $scope.shiftDetail = true;
                        $scope.shiftTime = true;
                    } else {
                        $scope.shiftDetails = [];
                        $scope.shiftTime = false;
                        $('.newTrack').attr('ng-required', true);
                    }
                    $scope.shiftDetail = true;
                    $scope.newTrack = false;
                    $('.newTrack').attr('ng-required', false);

                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }));

    //remove shift
    $scope.removeShift = function(collection, no, shiftId) {
        /*if(shiftId!=undefined&&shiftId!=null){
	        		spocService.deleteShift(shiftId)
		             .then(
		                 function(response) {
		                	 console.log(response);
		                	 $scope.getProjectShiftDetails();
		                 },
		                 function(errResponse) {
		                     console.error('Error while creating User');
		                     self.errorMessage = 'Error while creating User: ' + errResponse.data.errorMessage;
		                     self.successMessage = '';
		                 }
		             );
	        	}*/
        collection.splice(no, 1);
        console.log(collection);
        if (collection.length < 1) {
            $scope.shiftTime = false;
        }
        $scope.shiftName = null;
        $('#eidtShiftModal #prjct_shift1').val('');
    }
    //Edit project and shift Details

    $scope.editProjectShift = function(projectId, accountId, trackId, action) {
        $scope.newTrack = false;
        $scope.shiftDetail = false;
        $scope.trackRequires = false;
        $scope.trackerDetails = false;
        $scope.projectDetails = false;
        $('#project_accntDrp').val('');
        spocService.ProjectShiftEditUpdateDelete(projectId, accountId, trackId, action)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    $scope.response = response.data;
                    var data = {
                        "projectId": projectId,
                        "accountId": accountId,
                        "trackDetailsId": trackId,
                        "shiftDetails": response.data
                    };
                    $rootScope.ShiftData = data;
                    for(var i=0;i<data.shiftDetails.length;i++){
                    	$scope.convertTimeTo12hrs(data.shiftDetails[i].startTime,"start",i);
                    	data.shiftDetails[i].startTime=startTime;
                    	$scope.convertTimeTo12hrs(data.shiftDetails[i].endTime,"end",i);
                    	data.shiftDetails[i].endTime=endTime;
                    }                   
                    $scope.shiftDetails = data.shiftDetails;
                    $rootScope.dupShift = data.shiftDetails;
                    console.log()
                    $timeout(function() {
                        $('#shiftModal').modal('show');
                        $('#shiftModal .fa-trash-o').addClass('hidden');
                    }, 20);
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }
    //update project and shift details
    $scope.updateProjectShift = function(action) {
    	for(var i=0;i<($scope.shiftDetails).length;i++){
        	$scope.convertTime($scope.shiftDetails[i].startTime,"start",i);
        	$scope.convertTime($scope.shiftDetails[i].endTime,"end",i);
        }
    	
        $scope.trackDetails = [{
            "trackDetailsId": $rootScope.ShiftData.trackDetailsId,
            "trackName": $scope.shiftDetails[0].trackName,
            "shiftDetails": $scope.shiftDetails
        }];
        $scope.response = [{
            "accountId": $rootScope.ShiftData.accountId,
            "projectId": $rootScope.ShiftData.projectId,
            "trackDetails": $scope.trackDetails
        }];
        spocService.ProjectShiftEditUpdateDelete($rootScope.ShiftData.projectId, $rootScope.ShiftData.accountId, $rootScope.ShiftData.trackDetailsId, action, $scope.response)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    $('#shiftModal').modal('hide');
                    var response = "<label>" + response.data.data + "</label>";
                    $('#Adhoc_popup').html(response);
                    $('#myModal').modal('show');
                    $scope.getProjectShiftDetails();
                    //$('#project_accntDrp').val('');
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    // var arr = [];
    var shftName, shftId, shftInit;
    $('#prjct_shift').off().on('change', (function() {
        shftName = $('#prjct_shift option:selected').val();
        shftInit = shftName.split(",");
        shftId = $('#prjct_shift option:selected').attr('id');
        if ($scope.shiftDetails != undefined) {
            var res = JSON.search($scope.shiftDetails, '//*[shiftId=' + shftId + ']');
            if (res.length >= 1) {
                $(".shiftDetail #err").css("display", "block");
                $scope.alert = "* " + shftName + " is already exists.";
                $("#err").delay(3000).fadeOut();
                $timeout(function() {
                    $scope.alert = "";
                }, 3000);
            } else {
                pushShiftData();
            }
        } else {
            pushShiftData();
        }
    }));
    if ($scope.shiftDetails == undefined) {
        $scope.shiftDetails = [];
    }

    function pushShiftData() {
        var data = {
            "shiftName": shftInit[0],
            "shiftId": shftId,
            "shiftInitials": shftInit[1],
            "pickup": false,
            "drop": false,
            "startTime": "",
            "endTime": ""

        };
        $scope.shiftDetails.push(data);
        $scope.shiftTime = true;
        $timeout(function() {
            $('#del_' + shftId).removeClass('hidden');
        }, 30);
    }

    //--------------------------------------------------shift Timing Validation----------------------------------------------------------------
    $scope.convertMillisecond = function(start, end, timeClass) {

        start = start.split(" ");
        start = start[1];
        end = end.split(" ");
        end = end[1];

        $rootScope.startTime = start;
        $rootScope.endTime = end;
        if (start == end) {
            bootbox.alert({
                message: "<label>Please select 8 Hrs range between Pickup and Drop Time.</label>",
                callback: function() {

                }
            });
        }
    }


    $('#eidtShiftModal #prjct_shift1 ').off().on('change', (function(e) {
        shftName = $(' #prjct_shift1 option:selected').val();
        shftId = $(' #prjct_shift1 option:selected').attr('id');
        shftInit = shftName.split(",");
        if ($scope.shiftDetails != undefined) {
            var res = JSON.search($scope.shiftDetails, '//*[shiftId=' + shftId + ']');
            if (res.length >= 1) {
                $("#shiftModal #err").css("display", "block");
                $scope.alert = "* " + shftName + " is already exists.";
                $("#shiftModal #err").delay(3000).fadeOut();
                $timeout(function() {
                    $scope.alert = "";
                }, 3000);
            } else {
                pushUpdateShiftData();
            }
        } else {
            pushUpdateShiftData();
        }

    }));

    function pushUpdateShiftData() {
        var data = {
            "shiftName": shftInit[0],
            "shiftId": shftId,
            "shiftInitials": shftInit[1],
            "pickup": false,
            "drop": false,
            "startTime": "",
            "endTime": ""
        };
        var arr = $rootScope.dupShift;
        arr.push(data);
        $scope.shiftDetails = arr;
        $timeout(function() {
            $('#eidtShiftModal #del_' + shftId).removeClass('hidden');
        }, 30);
    }
    //=========================================================================Tracker==================== ==========================================


    //--------------------------------------------get track list on pageload--------------------------
    var accountId;
    $scope.getTrackOnLoad = function() {
        $('.ajax-loader').css("visibility", "visible");
        //accountId=data;
        spocService.getExistTrackListByProject()
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    $rootScope.tracker1 = response.data;
                    $(".trackAlert").css("display", "none");
                    $timeout(function() {
                        for (var i in response.data) {
                        	var id=i.replace(/\./g,'\\.')
                            for (var j in response.data[i]) {
                                response.data[i][j].trackDetails = response.data[i][j].trackDetailsList;
                                if (response.data[i][j].trackDetails.length <= 0) {
                                    $('.trackEditDiv #'+ id +' #accor'+ j +' .trackSave').hide();
                                } else {
                                    $('.trackEditDiv #'+ id +' #accor'+ j +' .trackSave').show();
                                }
                            }
                        }

                    }, 30);
                    console.log(response);
                    $('.user_detail').removeClass('hidden');
                    $('.ajax-loader').css("visibility", "hidden");
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }
    $scope.trackProject = [{
        "trackName": "",
        "trackDetailsId": ""
    }];


    $scope.addTrackerProject = function() {
        $scope.trackProject.push({
            "trackName": "",
            "trackDetailsId": ""
        });
        var res = JSON.search($scope.trackProject, '//*[trackDetailsId=""]');
        if (res.length <= 0) {
            $('.trackBtns').addClass('hidden');
        } else {
            $('.trackBtns').removeClass('hidden');
        }
    }


    $scope.addTracker = function(collection) {
        $scope.track.push({
            "trackName": "",
            "trackDetailsId": ""
        });
        var res = JSON.search(collection, '//*[trackDetailsId=""]');
        if (res.length <= 0) {
            $('.trackBtns').addClass('hidden');
        } else {
            $('.trackBtns').removeClass('hidden');
        }
    }


    $scope.addTracker1 = function(no, account) {
        $scope.tracker1[account][no].trackDetails.push({
            "trackName": ""
        });
        $timeout(function() {
            $('.trackEditDiv #' + account + ' #accor' + no + ' .trackSave').show();

            /*$('.trackEditDiv #accor'+no+' .form-control').css({"background":"white","pointer-events":"initial"});
             $('.trackEditDiv #accor'+no+' .fa-trash-o').show();*/
        }, 30);
    }

    $scope.removeTrack = function(collection, no) {
        collection.splice(no, 1);
        var res = JSON.search(collection, '//*[trackDetailsId=""]');
        if (res.length <= 0) {
            $('.trackBtns').addClass('hidden');
        } else {
            $('.trackBtns').removeClass('hidden');
        }
    }
    $scope.chkTrckLength = function(data) {
        if (data.length <= 0) {
            $('#track .fa-trash-o').hide();
            $('.trackBtns').addClass('hidden');
        } else {
            $('#track .fa-trash-o').show();
            $('.trackBtns').removeClass('hidden');
        }

    }
    $scope.removeShiftTrack = function(collection, no, shiftId) {
        if (shiftId != undefined && shiftId != null) {
            spocService.deleteShift(shiftId)
                .then(
                    function(response) {
                        console.log(response);
                        $scope.getProjectShiftDetails();
                    },
                    function(errResponse) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    }
                );
        }
        collection.splice(no, 1);
        $scope.chkTrckLength(collection);
        if (collection.length < 1) {
            $scope.shiftTime = false;
        }
        $scope.shiftName = null;
        $('#prjct_shift').val('');
    }

    $scope.shiftTrackCan = function() {
        $scope.newTrack = false;
        $scope.trackProject = [{
            "trackName": "",
            "trackDetailsId": ""
        }];
        $scope.trackY = null;
        $scope.project.trackDetails.trackName = "";
        $('.newTrack').attr('ng-required', false);
    }

    $scope.trckCancel = function() {
        $scope.track = [{
            "trackName": "",
            "trackDetailsId": ""
        }];
        $('#track').hide();
        $('.trackBtns').addClass('hidden');
        $scope.tracker = {};
        $scope.project = {};
        $('#trackPrjctDrp').attr('disabled', true);
    }
    $('#trackAccntDrp').on('change', (function() {
        if ($(this).val() == "") {
            $('#trackAccntDrp').val("");
            $scope.project = {};
            $('#trackPrjctDrp').val("");
            $scope.tracker.projectName = "";
            $('#track').hide();
        } else {
            var value = $('#trackAccntDrp option:selected').attr('id');
            spocService.getTrackProjectList(value)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        $('#trackPrjctDrp').attr('disabled', false);
                        $scope.project = response.data;
                        console.log(response);
                    },
                    function(errResponse) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    }
                );
        }
    }));

    $scope.getTrackerByProjectId = function(val) {
        spocService.getTrackList(val)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    var length = response.data.length;
                    if (length <= 0) {
                        var track = [];
                        track.push({
                            "trackName": "",
                            "trackDetailsId": ""
                        });
                        $scope.track = track;
                        $('#track').show();
                        $('.trackBtns').removeClass('hidden');
                    } else {
                        $timeout(function() {
                            for (var i = 0; i < length; i++) {
                                $('#trck' + i).attr('disabled', true);
                                $('.trackRem' + i).hide();
                            }
                        }, 10);
                        $scope.track = response.data;
                        $scope.track.push({
                            "trackName": "",
                            "trackDetailsId": ""
                        });
                        $('#track').show();
                        $('.trackBtns').removeClass('hidden');
                    }
                    console.log(response);
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }
    var projectId;
    $('#trackPrjctDrp').on('change', (function() {
        if ($(this).val() == "") {
            $('#trackPrjctDrp').val("");
            $scope.tracker.projectName = "";
            $('#track').hide();
        } else {
            projectId = $('#trackPrjctDrp option:selected').attr('id');
            $scope.getTrackerByProjectId(projectId);
        }
    }));
    //-------------------------create tracker--------------------------
    $scope.sub = function(track) {
        var data;
        if (track == "createTrack") {
            $scope.tracker.accountId = $(' #trackAccntDrp option:selected').attr('id');
            $scope.tracker.projectId = $(' #trackPrjctDrp option:selected').attr('id');
            console.log($scope.trackProject);
            var res = JSON.search($scope.track, '//*[trackDetailsId=""]');
            $scope.tracker.trackDetails = res;
            data = $scope.tracker;
            console.log(JSON.stringify($scope.tracker));
            $scope.createTrack(data);
        } else if (track == "shiftTrack") {
            var data = [];
            for (var i = 0; i < $scope.trackProject.length; i++) {
                if ($scope.trackProject[i].trackName == "") {
                    data.push("false");
                    $('#trckErr' + [i]).css('display', 'block');
                    $scope.trckAlert = "*Required";
                }
            }
            if (data.length >= 1) {
                $(".trckErr").delay(3000).fadeOut();
                $timeout(function() {
                    $scope.trckAlert = "";
                    $('#trckErr' + [i]).css('display', 'none');
                }, 3000);
            } else {
                $scope.project.accountId = $(' #project_accntDrp option:selected').attr('id');
                $scope.project.projectId = $(' #projectname option:selected').attr('id');
                console.log($scope.track);
                var res = JSON.search($scope.trackProject, '//*[trackDetailsId=""]');
                $scope.project.trackDetails = res;
                data = $scope.project;
                console.log(JSON.stringify($scope.project));
                $scope.createTrack(data, track);
            }
        }

    }

    $scope.createTrack = function(data, track) {
        spocService.trackerCreation(data)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(response);
                    var response = "<label>" + response.data.data + "</label>";
                    $('#Adhoc_popup').html(response);
                    $('#myModal').modal('show');
                    if (track == "shiftTrack") {
                        $scope.getShiftProjList($scope.project.accountId);
                        $timeout(function() {
                            $scope.selectShiftProject($scope.project.projectId);
                        }, 60);
                        $scope.newTrack = false;
                        $scope.trackProject = [{
                            "trackName": "",
                            "trackDetailsId": ""
                        }];
                    } else {
                        var response = "<label>Track details have been updated successfully.</label>";
                        /*$('#track input').attr('disabled',true);
                        $('.trcakBtns').addClass('hidden');
                        $('#track .fa-trash-o').hide();*/
                        $scope.getTrackerByProjectId(projectId);
                        $('#Adhoc_popup').html(response);
                        $('#myModal').modal('show');
                        $scope.getTrackOnLoad();
                        // window.location.reload();
                    }
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }


    //------------------------------Edit track--------------------------------------			 
    $scope.trackEdit = function(no) {
        $('.trackEditDiv #accor' + no + ' .form-control').css({
            "background": "white",
            "pointer-events": "initial"
        });
        $('.trackEditDiv #accor' + no + ' .trackEdit').hide();
        $('.trackEditDiv #accor' + no + ' .trackSave,.trackEditDiv #accor' + no + ' .trackCancel,.trackEditDiv #accor' + no + ' .fa-trash-o').show();
        $('.trackEditDiv #accor' + no + ' #add_proj_btn1').removeClass('disabled');
    }
    //-------------------------update track------------------------------
    $scope.trackSave = function(no, account) {
        $(".trackAlert").css("display", "none");
        var arr = [];
        for (var i in $scope.tracker1[account][no].trackDetails) {
            if ($scope.tracker1[account][no].trackDetails[0].trackName == $scope.tracker1[account][no].trackDetails[i].trackName) {
                arr.push($scope.tracker1[account][no].trackDetails[i].trackName);
            }

        }
        if (arr.length > 1) {
            $scope.class = "alert-danger";
            $scope.trackAlertStatus = 'Duplicate Track name not allowed.';
            $(".trackAlert").css("display", "block");
            $(".trackAlert").delay(3000).fadeOut();
            $timeout(function() {
                $scope.trackAlertStatus = "";
            }, 2500);
        } else {
            $('.trackEditDiv #accor' + no + ' .trackEdit').show();
            $('.trackEditDiv #accor' + no + ' .trackSave,.trackEditDiv #accor' + no + ' .trackCancel,.trackEditDiv #accor' + no + ' .fa-trash-o').hide();
            $('.trackEditDiv #accor' + no + ' #add_proj_btn1').addClass('disabled');
            $('.trackEditDiv #accor' + no + ' .form-control').css({
                "background": "#ececec",
                "pointer-events": "none"
            });
            console.log($scope.tracker1[no]);
            spocService.trackerUpdate("", "update", $scope.tracker1[account][no])
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        console.log(response);
                        var response = "<label>" + response.data.data + "</label>";
                        $('#Adhoc_popup').html(response);
                        $('#myModal').modal('show');
                        $scope.getTrackOnLoad();
                    },
                    function(errResponse) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    }
                );
        }
    }

    //------------------------------cancel track edit-------------------------------------

    $scope.trackEditCancel = function(no) {
        $('.trackEditDiv #accor' + no + ' .trackEdit').show();
        $('.trackEditDiv #accor' + no + ' .trackSave,.trackEditDiv #accor' + no + ' .trackCancel,.trackEditDiv #accor' + no + ' .fa-trash-o').hide();
        $('.trackEditDiv #accor' + no + ' #add_proj_btn1').addClass('disabled');
        $('.trackEditDiv #accor' + no + ' .form-control').css({
            "background": "#ececec",
            "pointer-events": "none"
        });
        // $scope.getTrackOnLoad(accountId);
    }
    //-------------------------------Delete individaual tracker by id------------------------
    $scope.removeTrack1 = function(collection, no, id, account, projectId) {
        if (no == undefined) {
            collection.splice(id, 1);
        } else {
            var res = JSON.search(collection, '//*[trackDetailsId="' + no + '"]');
            /*if(res[0].shiftDetailsExits==true){
						 var msg="<label>This Track has Shift Details ! <br>It May delete your Existing Shift Details of this Track <br>Are you sure want to delete Track?</label>";
					 }
					 else{*/
            var msg = "<label>Are you sure that you want to delete this Track?</label>";
            /*}*/
            bootbox.confirm(msg, function(result) {
                if (result == true) {
                    spocService.trackerUpdate(no, "delete", "")
                        .then(
                            function(response) {
                                if (response.headers('Log_Out') == "true") {
                                    window.location = '/tmsApp/login';
                                }
                                console.log(response)
                                if (response.data.data == "success") {
                                    bootbox.alert({
                                        message: "<label>Deleted successfully.</label>",
                                    });
                                    collection.splice(id, 1);
                                } else {
                                    bootbox.alert({
                                        message: "<label>Cannot delete as it is mapped.</label>",
                                    });
                                }
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
    }


    $scope.startTimeAlert = function(element, id, label) {
        bootbox.alert({
            message: label,
            callback: function() {
                element.trigger('input');
                $('#startTime_' + id).val('');
                $scope.shiftDetails[id].startTime = "";
                $scope.$apply();
            }
        });
    }

    $scope.endTimeAlert = function(element, id, label) {
        bootbox.alert({
            message: label,
            callback: function() {
                element.trigger('input');
                $('#endTime_' + id).val('');
                $scope.shiftDetails[id].endTime = "";
                $scope.$apply();
            }
        });
    }
    var msg="<label>Tap gap between Start time and End time should be minimum of 7 hours and maximum of 9 hours.</label>";
    $scope.shiftTimePicker = function(element, scope, attrs, ngModel) {
        element.timepicker({
            timeFormat: 'HH:mm',
            interval: 30,
            minTime: '00:00',
            maxTime: '23:59',
            dynamic: false,
            dropdown: true,
            scrollbar: false,
            change: function(time) {
                $scope.time = time;
                scope.$apply(function(scope) {
                    var selectTime = moment(new Date($scope.time), "HH:mm a");
                    var className = (attrs.class).split(" ");
                    className = className[1];
                    var minute=selectTime.minute();
                    /*if(selectTime.minute()>0&&className!="endTime"){
                    	selectTime = (selectTime.hour());
                    }
                    else{*/
                    	selectTime = selectTime.hour();
                    /*}*/
                    var id = (attrs.id).split("_");
                    id = id[1];
                    var initial = $('#shiftInit' + id).html();
                    initial = initial.trim();
                    if (className == "startTime") {
                        var endTime = $('#endTime_' + id).val();
                        if (endTime != "") {
                            endTime = moment(endTime, "HH:mm a");
                            endTime = endTime.hour();
                            if (endTime == selectTime) {
                                $scope.startTimeAlert(element, id, "<label>Start time and End time should not be same.</label>");
                            } else {
                                if (selectTime < endTime) {
                                    if ((endTime - 7) < selectTime) {
                                        $scope.startTimeAlert(element, id,msg);
                                    } else if (selectTime < (endTime - 9)) {
                                        $scope.startTimeAlert(element, id,msg);
                                    } else {
                                        element.trigger('input');
                                    }
                                } else {
                                    if (initial == "NS") {
                                        endTime = endTime + 24;
                                        var time = endTime - selectTime;
                                        if (time <= 9 && time >= 7) {
                                        	if(time==9&&minute>0){
                                        		 $scope.startTimeAlert(element, id,msg);
                                        	}
                                        	else{
                                        		element.trigger('input');
                                        	}
                                        } else {
                                            $scope.startTimeAlert(element, id,msg);
                                        }
                                    } else {
                                        $scope.startTimeAlert(element, id, "<label>Start time should not be greater than End time.</label>");
                                    }
                                }
                            }
                        } else {
                            if (initial == "NS") {
                                if (selectTime>=21 && selectTime<=23) {
                                    if(selectTime==23&&minute>0){
                                    	$scope.startTimeAlert(element, id, "<label>Night shift Start time should be between 9 PM to 11 PM</label>");
                                    }
                                    else{
                                    	element.trigger('input');
                                    }
                                } else {
                                    $scope.startTimeAlert(element, id, "<label>Night shift Start time should be between 9 PM to 11 PM</label>");
                                }
                            } else {
                                element.trigger('input');
                            }
                        }
                    } else {
                        var startTime = $('#startTime_' + id).val();
                        if (startTime != "") {
                            startTime = moment(startTime, "HH:mm a");
                            startTime = startTime.hour();
                            if (selectTime == startTime) {
                                $scope.endTimeAlert(element, id, "<label>Start time and End time should not be same.</label>");
                            } else {
                                if (selectTime > startTime) {
                                    if ((selectTime - 7) < startTime) {
                                        $scope.endTimeAlert(element, id,msg);
                                    } else if (selectTime > (startTime + 9)) {
                                        $scope.endTimeAlert(element, id,msg);
                                    } else {
                                        element.trigger('input');
                                    }
                                } else {
                                    if (initial == "NS") {
                                        selectTime = selectTime + 24;
                                        var time = selectTime - startTime;
                                        if (time <= 9 && time >=7) {
                                        	if(time==9&&minute>0){
                                        		$scope.endTimeAlert(element, id,msg);
                                        	}
                                        	else{
                                        		 element.trigger('input');
                                        	}
                                        } else {
                                            $scope.endTimeAlert(element, id,msg);
                                        }
                                    } else {
                                        $scope.endTimeAlert(element, id, "<label>End time should not be lesser than Start time.</label>");
                                    }
                                }
                            }
                        } else {
                            if (initial == "NS") {
                                if (selectTime <=7 && selectTime>=5) {
                                    
                                    if(selectTime==7&&minute>0){
                                    	$scope.endTimeAlert(element, id, "<label>Night shift End time should be between 5 AM to 8AM</label>");
                                    }
                                    else{
                                    	element.trigger('input');
                                    }
                                } else {
                                    $scope.endTimeAlert(element, id, "<label>Night shift End time should be between 5 AM to 8AM</label>");
                                }
                            } else {
                                element.trigger('input');
                            }
                        }
                    }


                });

            }
        });
    }
}]);

app.directive('datetimepicker', function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {

            if (!ngModel) return; // do nothing if no ng-model
            scope.shiftTimePicker(element, scope, attrs, ngModel);
        }
    }
});