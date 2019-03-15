'use strict';

angular.module('tmsApp').controller('shiftUserController', ['shiftUserService', '$scope', '$rootScope', '$timeout', function(userService, $scope, $rootScope, $timeout) {

    var self = this;

    self.successMessage = '';
    self.errorMessage = '';
    self.done = false;
    self.onlyIntegers = /^\d+$/;
    self.onlyNumbers = /^\d+([,.]\d+)?$/;

    function format(d) {
        var sDate = moment(d.requestDate); //Get the current date
        sDate = sDate.format("DD/MM/YYYY");

        return '<div class="form-group row col-sm-12 col-lg-6 col-md-6 text-left">' +
            '<label  class="col-lg-4 col-form-label">From Address </label><label  class="col-lg-8 col-form-label text-left">: ' + d.fromAddress + ',' + d.fromLandmark + ',' + d.fromLocation + ',' + d.fromCity + ',' + d.fromPincode + '</label>' +
            '</div>' +
            '<div class="form-group row col-sm-12 col-lg-6 col-md-6 text-left">' +
            '<label  class="col-lg-4 col-form-label">To Address </label><label  class="col-lg-8 col-form-label text-left">: ' + d.toAddress + ',' + d.toLandmark + ',' + d.toLocation + ',' + d.toCity + ',' + d.toPincode + '</label>' +
            '</div>'


    }

    function adhocSelfformat(d) {
        return '<div class="form-group row col-sm-12 col-lg-6 col-md-6 ">' +
            '<label  class="col-lg-3 col-form-label text-right">Mobile Number  </label><label  class="col-lg-3 col-form-label text-left">: ' + d.mobileNumber + '</label>' +
            '</div>' +
            '<div class="form-group row col-sm-12 col-lg-6 col-md-6 ">' +
            '<label  class="col-lg-3 col-form-label text-right">Location  </label><label  class="col-lg-3 col-form-label text-left">: ' + d.fromCity + '</label>' +
            '</div>'
    }

    function adhocOtherFormat(d) {
        return '<div class="form-group row col-sm-12 col-lg-6 col-md-6 ">' +
            '<label  class="col-lg-3 col-form-label text-right">Mobile Number </label><label  class="col-lg-3 col-form-label text-left" >: ' + d.mobileNumber + '</label>' +
            '</div>' +
            '<div class="form-group row col-sm-12 col-lg-6 col-md-6 ">' +
            '<label  class="col-lg-3 col-form-label text-right">Location </label><label  class="col-lg-3 col-form-label text-left">: ' + d.fromCity + '</label>' +
            '</div>'
    }

    //--------------------------------------------------Project Type selection-------------------------------------
    $('#projectType').change(function() {
        var val = $("#projectType option:selected").val();
        if (val == "shift") {
            $("#cabRequires").show();
        } else {
            $("#cabRequires").hide();
            $("input[name='isCabRequired']").val(false);
        }

    });
    //---------------Create Adhoc ---------
    $scope.submit_adhoc = function() {
        $scope.adhoc
        $('.panel').addClass('hidden');
        $('.ajax-loader').css("visibility", "visible");
        console.log($scope.adhoc);
        if ($scope.adhoc.saturday == null && $scope.adhoc.sunday == null) {
            $scope.adhoc.sunday = false;
            $scope.adhoc.saturday = false;
        } else if ($scope.adhoc.saturday == null) {
            $scope.adhoc.saturday = false;
        } else if ($scope.adhoc.sunday == null) {
            $scope.adhoc.sunday = false;
        }
        var val = $('input:radio[name="requestedFor"]:checked').val();
        $scope.adhoc.requestedFor = val;
        if (val == "employee") {
            var dataEmpVal = $('input[name="requestedEmpName"]').val();
            $scope.adhoc.requestedEmpId = $('#adhocEmployeesList option[value="' + dataEmpVal + '"]').attr('id');
        } else if (val == "guest") {
            $scope.adhoc.guestUserName = $('input[name="requestedGuestName"]').val();
        }
        var dataPrjVal = $('input[name="requestedProjectId"]').val();
        $scope.adhoc.projectName = dataPrjVal;
        $scope.adhoc.projectId = $('#adhocProjectList option[value="' + dataPrjVal + '"]').attr('id');
        if ($scope.adhoc.projectId == undefined) {
            $('.ajax-loader').css("visibility", "hidden");
            bootbox.alert({
                message: "<label>Select valid project</label>",
            });
        } else {
            console.log($scope.adhoc);
            $scope.adhoc.from = $('#from').val();
            $scope.adhoc.to = $('#to').val();
            $scope.adhoc.pickTime = $('select[name="pickuptime"]').val();
            $scope.adhoc.dropTime = $('select[name="droptime"]').val();
            $scope.adhoc.fromAliasName = $('#fromPlaceDrp').val();
            $scope.adhoc.toAliasName = $('#toPlaceDrp').val();
            console.log(JSON.stringify($scope.adhoc));

            userService.SendAdhocData($scope.adhoc)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        console.log(response.data.data);
                        $('#create_form').trigger("reset");
                        $('.panel').removeClass('hidden');
                        window.location.href = "/tmsApp/tms/adhocRequest/cabRequestHistoryPage";

                    },
                    function(errResponse) {
                        $scope.class = "alert-danger";
                        $(".alert").css("display", "block");
                        $scope.status = 'Something went wrong. Please try after sometime.';
                        $(".alert").delay(3000).fadeOut();
                        $timeout(function() {
                            $scope.status = "";
                        }, 3000);

                    }
                );
        }
    }
    //------------------------------Enable if other Project---------------------------------->

    $('#othrPrjct').click(function() {
        $('input[name="requestedProjectId"]').removeAttr("disabled", false);
        $('input[name="requestedProjectId"]').val("");

    });

    //--------------------other project selection alert----------------------
    //-------------------------------- other address city selection---------------------
    var fromcity, tocity, selected;
    $("select[name='fromCity'],select[name='toCity']").on('change', function() {
        var selected = $(this).val();
        fromcity = $("select[name='fromCity']").val();
        tocity = $("select[name='toCity']").val();
        $scope.adhoc.fromCity = selected;
        $scope.adhoc.toCity = selected;

    });
    //----------------------get address of other employee------------------------------>
    var data;
    $("input[name='requestedEmpName']").bind('input', function() {
        if (checkExists($("input[name='requestedEmpName']").val()) === true) {
            $scope.adhocAliasList = [];
            $scope.adhoc.toAliasName = null;
            $scope.adhoc.fromAliasName = null;
            $('#toPlaceDrp').val('');
            $('#fromPlaceDrp').val('');
            $('.tooltip2,.tooltip1').css("display", "none");
            var opt = $('option[value="' + $(this).val() + '"]').attr('id');
            if (opt != undefined) {
                var dataEmpVal = $('input[name="requestedEmpName"]').val();
                dataEmpVal = $('#adhocEmployeesList option[value="' + dataEmpVal + '"]').attr('id');
                getAddress(opt);
            } else {
                $timeout(function() {
                    $scope.adhocAliasList = [];
                    $scope.adhoc.toAliasName = null;
                    $scope.adhoc.fromAliasName = null;
                    $('#toPlaceDrp').val('');
                    $('#fromPlaceDrp').val('');
                    $('.tooltip2,.tooltip1').css("display", "none");
                }, 0);
            }
            console.log($(this).val() + ", " + opt);
        }
    });

    //-----------------------  Remove text from select employee if not selected from option  --------------------------------

    $("input[name='requestedEmpName']").focusout(function() {
        var val = $("input[name='requestedEmpName']").val();
        if ($('#adhocEmployeesList option').filter(function() {
                return this.value === val;
            }).length) {

        } else {
            $("input[name='requestedEmpName']").val('');
            $('.empDiv .form-control-clear').addClass('hidden');
            $('.places').addClass('hidden');
            $('.requestType').css('display', 'none');
            $('.droptime,.picktime').remove();
            $('.places select,.requestType input').attr('required', false);
            $('.places select,.droptime select,.picktime select').val('');
            //$(".requestType input:radio").removeAttr("checked");
            $scope.adhoc.requestType = "";
        }
    });
    //-----------------------  Remove text from select project if not selected from option  --------------------------------

    $("input[name='requestedProjectId']").focusout(function() {
        var val = $("input[name='requestedProjectId']").val();
        if ($('#adhocProjectList option').filter(function() {
                return this.value === val;
            }).length) {

        } else {
            $("input[name='requestedProjectId']").val('');
            $('.project .form-control-clear').addClass('hidden');
        }
    });
    $scope.loadAddr = function() {
        $scope.adhoc = {};
        $scope.adhoc.requestedFor = "self";
        getAddress("0");
        $('.form-control-clear').addClass('hidden');
    }


    $('.has-clear input').on('input propertychange', function() {
        var $this = $(this);
        var visible = Boolean($this.val());
        $this.siblings('.form-control-clear').toggleClass('hidden', !visible);
    }).trigger('propertychange');

    $('#empClear').click(function() {
        $(this).siblings('input').val('')
            .trigger('propertychange').focus();
        $('#toPlaceDrp').val('');
        $('#fromPlaceDrp').val('');
        $scope.adhocAliasList = [];
        $scope.$apply();
        $scope.adhoc.toAliasName = null;
        $scope.adhoc.fromAliasName = null;
        $('.tooltip2,.tooltip1').css("display", "none");
        $('.places').addClass('hidden');
        $('.requestType').css('display', 'none');
        $('.droptime,.picktime').remove();
        $('.places select,.requestType input').attr('required', false);
        $('.places select,.droptime select,.picktime select').val('');
        //$(".requestType input:radio").removeAttr("checked");
        $scope.adhoc.requestType = "";
    });

    $('#projectClear').click(function() {
        $(this).siblings('input').val('')
            .trigger('propertychange').focus();
    });

    //-------------------------------------Adhoc Project DropDown--------------------------
    var project = $('input[name="requestedProjectId"]').val();
    $("input[name='requestedProjectId']").bind('input', function() {
        var reqFor = $('input[name="requestedFor"]:checked').val();
        var val = this.value;
        if ($('#adhocProjectList option').filter(function() {
                return this.value === val;
            }).length) {
            //send ajax request
            var arr = [];
            for (var i = 0; i < $rootScope.empProjectList.length; i++) {
                if ($rootScope.empProjectList[i].projectName == val) {
                    arr.push(val);
                }
            }
            if (arr.length == 0) {
                bootbox.confirm("<label> You have selected '" + $(this).val() + "', which is not the allocated project to employee.<br> Please confirm to proceed.</label>", function(result) {
                    if (result == false) {
                        if (reqFor == "self") {
                            $('input[name="requestedProjectId"]').attr("disabled", true);
                            $('input[name="requestedProjectId"]').val(project);
                            $('.reqForInpDiv').hide();
                            $('.empDiv').hide();
                            $('.guestDiv').hide();
                            $('#othrPrjct').show();
                            $('.reqForRadioDiv').css('margin-right', '');
                        } else {
                            $('input[name="requestedProjectId"]').val("");
                        }
                        $('.project .form-control-clear').addClass('hidden');
                    }
                });
            }
        }
    });


    $scope.requestfor = function(val) {
        if (val == "self") {
            getAddress("0");
            $("input[name='requestedEmpName']").removeAttr('required');
        } else {
            $("input[name='requestedEmpName']").attr('required', true);
        }
        $scope.adhoc = {};
        $scope.adhoc.toAliasName = null;
        $scope.adhoc.fromAliasName = null;
        $('#toPlaceDrp,#fromPlaceDrp,#from,#to').val('');
        $('.tooltip2,.tooltip1').css("display", "none");
        $('#sat,#sun').addClass('hidden');
        $scope.adhoc = {
            "requestedFor": val,
            "mobileNumber": ""
        };
    }

    function getAddress(opt) {
        userService.getAdhocAddress(opt).then(
            function(response) {
                if (response.headers('Log_Out') == "true") {
                    window.location = '/tmsApp/login';
                }
                $rootScope.empProjectList = response.data.projectList;
                if (opt == "0") {
                    if (response.data.employeeAddress.length <= 1) {

                        bootbox.alert({
                            message: "<label>Can't process your request because of any of the below reasons:<br>&nbsp;&nbsp;1. Employee address details is empty. <br>&nbsp;&nbsp;2. Request for address updation is not approved by the respective mangaer.<br> &nbsp;&nbsp;3. Office address details is empty. Contact Admin.</label>",
                            callback: function() {
                                window.location.href = "/tmsApp/tms/employee/addEmployee";
                            }
                        });

                    } else {
                        var res = JSON.search(response.data.employeeAddress, '//*[alias="Home"]');
                        if (res.length <= 0) {
                            bootbox.alert({
                                message: "<label>Can't process your request because of any of the below reasons:<br>&nbsp;&nbsp;1. Employee address details is empty. <br>&nbsp;&nbsp;2. Request for address updation is not approved by the respective mangaer.<br> &nbsp;&nbsp;3. Office address details is empty. Contact Admin.</label>",
                                callback: function() {
                                    window.location.href = "/tmsApp/tms/employee/addEmployee";
                                }
                            });
                        } else {
                            $rootScope.adhocAddress = response.data.employeeAddress;
                            $scope.adhocAliasList = response.data.employeeAddress;
                            console.log(response.data.employeeAddress);
                        }
                    }
                } else {
                    //var res = JSON.search(response.data, '//*[employeeId="' +opt+ '"]');
                    if (response.data.employeeAddress.length <= 1) {
                        bootbox.alert({
                            message: "<label>Can't process your request because of any of the below reasons:<br>&nbsp;&nbsp;1. Employee address details is empty. <br>&nbsp;&nbsp;2. Request for address updation is not approved by the respective mangaer.<br> &nbsp;&nbsp;3. Office address details is empty. Contact Admin.</label>",
                        });
                        $('.form-control-clear').addClass('hidden');
                        $scope.adhocAliasList = [];
                        $('input[name="requestedEmpName"]').val('');
                    } else {
                        var res = JSON.search(response.data.employeeAddress, '//*[alias="Home"]');
                        if (res.length <= 0) {
                            bootbox.alert({
                                message: "<label>Can't process your request because of any of the below reasons:<br>&nbsp;&nbsp;1. Employee address details is empty. <br>&nbsp;&nbsp;2. Request for address updation is not approved by the respective mangaer.<br> &nbsp;&nbsp;3. Office address details is empty. Contact Admin.</label>",
                            });
                            $('.form-control-clear').addClass('hidden');
                            $scope.adhocAliasList = [];
                            $('input[name="requestedEmpName"]').val('');
                        } else {
                            $('.requestType').show();
                            $rootScope.adhocAddress = response.data.employeeAddress;
                            $scope.adhocAliasList = response.data.employeeAddress;
                            console.log(response.data.employeeAddress);
                            $('.requestType input').attr({
                                'ng-required': '!adhoc.requestType',
                                'required': true
                            });
                        }
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


    function checkExists(inputValue) {
        var x = document.getElementById("adhocEmployeesList");
        var i;
        var flag;
        for (i = 0; i < x.options.length; i++) {
            if (inputValue == x.options[i].value) {
                flag = true;
            }
        }
        return flag;
    }
    //-------------------------------add alias address in adhoc----------------------->
    var res;
    $scope.fromAddressHover = "Please Select Address";
    $scope.toAddressHover = "Please Select Address";

    $("#fromPlaceDrp").off().on('change', (function() {

        if ($(this).val() == "") {
            $('.tooltip1').css("display", "none");
        } else {
            var prevsVal = $scope.adhoc.fromAliasName;
            var fromAlias = $('#fromPlaceDrp').val();
            $('#toPlaceDrp').val('');
            $('.tooltip2').css("display", "none");
            if (fromAlias == "Home") {
                $('#toPlaceDrp .Home').hide().attr('disabled',true);
                $('#toPlaceDrp .Client,#toPlaceDrp .Office').show().attr('disabled',false);;
                var res = JSON.search($rootScope.adhocAddress, '//*[alias="Office"]');
                if (res.length > 0) {
                    $scope.toAlias('Office');
                    $('#toPlaceDrp').val('Office');
                } else {
                    $scope.toAlias('Client');
                    $('#toPlaceDrp').val('Client');
                }
            } else {
                $('#toPlaceDrp .Home').show().attr('disabled',false);;
                $('#toPlaceDrp .Client,#toPlaceDrp .Office').hide().attr('disabled',true);

                $scope.toAlias('Home');
                $('#toPlaceDrp').val('Home');
            }
            //$('#toPlaceDrp').attr('disabled', false);
            var toAlias = $('#toPlaceDrp').val();
            var alias_type = $(this).attr('name');
            var alias_val = $(this).val();
            if($scope.adhoc.requestType=="both"){
            	$('#toPlaceDrp').attr('disabled', false);
            if ($(this).val() == "Home") {
                $("#bth").html('');
                $("#bth").append(pickTimeEle);
                $("#bth").append(dropTimeEle);
            } else {
                $("#bth").html('');
                $("#bth").append(dropTimeEle);
                $("#bth").append(pickTimeEle);
            }
            }
            $scope.fromAlias(alias_val);
            $timeout(function() {
                $scope.checkTime();
            }, 100);
        }
    }));

    $("#toPlaceDrp").off().on('change', (function() {
        if ($(this).val() == "") {
            $('.tooltip2').css("display", "none");
        } else {
            var prevsVal = $scope.adhoc.toAliasName;
            var fromAlias = $('#fromPlaceDrp').val();
            var toAlias = $('#toPlaceDrp').val();
            var alias_type = $(this).attr('name');
            var alias_val = $(this).val();
            if($scope.adhoc.requestType=="both"){
           if ($(this).val() == "Home") {
                $("#bth").html('');
                $("#bth").append(dropTimeEle);
                $("#bth").append(pickTimeEle);
            } else {
                $("#bth").html('');
                $("#bth").append(pickTimeEle);
                $("#bth").append(dropTimeEle);
            }
            }
            $scope.toAlias(alias_val);
            $timeout(function() {
                $scope.checkTime();
            }, 100);
        }
    }));

    $scope.fromAlias = function(alias_val) {
        $('.tooltip1').css("display", "inline-block");
        $('.fromAddress').hide();
        res = JSON.search($rootScope.adhocAddress, '//*[alias="' + alias_val + '"]');
        //res = Object.assign([], res);
        console.log(res);
        $scope.adhoc.fromAddress = res[0].address;
        $scope.adhoc.fromLandmark = res[0].landmark;
        $scope.adhoc.fromLocation = res[0].location;
        $scope.adhoc.fromPincode = res[0].pincode;
        $scope.adhoc.fromCity = res[0].city;
        $scope.fromAddressHover = res[0].address + ',' + res[0].landmark + ',' + res[0].location + ',' + res[0].city + ',' + res[0].pincode;
        console.log($scope.fromAddressHover);
        $scope.$apply();
        if ((res[0].empcode) == null) {
            $('.fromAddrDiv .form-control').attr('disabled', 'disabled');
        } else {
            $('.fromAddrDiv .form-control').removeAttr("disabled");
        }
    }

    $scope.toAlias = function(alias_val) {
        $('.tooltip2').css("display", "inline-block");
        $('.toAddress').hide();
        res = JSON.search($rootScope.adhocAddress, '//*[alias="' + alias_val + '"]');
        console.log(res);
        $scope.adhoc.toAddress = res[0].address;
        $scope.adhoc.toLandmark = res[0].landmark;
        $scope.adhoc.toLocation = res[0].location;
        $scope.adhoc.toPincode = res[0].pincode;
        $scope.adhoc.toCity = res[0].city;
        $scope.toAddressHover = res[0].address + ',' + res[0].landmark + ',' + res[0].location + ',' + res[0].city + ',' + res[0].pincode;
        console.log($scope.toAddressHover);
        $scope.$apply();
        if ((res[0].empcode) == null) {
            $('.toAddrDiv .form-control').attr('disabled', 'disabled');
        } else {
            $('.toAddrDiv .form-control').removeAttr("disabled");
        }
    }


    //-------------------------check pick and drop(AM and PM) Time-------------------

    $scope.checkTime = function() {
        $('select[name="pickuptime"],select[name="droptime"]').off().on('change', (function() {
            var type = $(this).attr('name');
            var drpTime = $('select[name="droptime"]').val();
            var pickTime = $('select[name="pickuptime"]').val();
            if (pickTime != null && drpTime != null) {
                drpTime = drpTime.split(" ");
                pickTime = pickTime.split(" ");
                if ((drpTime[1] == pickTime[1])) {
                    if (drpTime[0] != "") {
                        bootbox.alert({
                            message: "<label>Pickup and Drop Meridiem should not be same (AM or PM).</label>",
                            callback: function() {
                                if (type == "droptime") {
                                    $('select[name="droptime"]').val("");
                                } else {
                                    $('select[name="pickuptime"]').val("");
                                }
                            }
                        });
                    }
                }
            }
        }));
    }

    $scope.pickupDrp = ["05:00 AM", "05:30 AM", "06:00 AM", "06:30 AM", "07:00 PM",
        "07:30 PM", "08:00 PM", "08:30 PM", "09:00 PM", "09:30 PM"
    ];
    $scope.dropDrp = ["06:00 AM", "06:30 AM", "09:30 PM", "10:00 PM", "10:30 PM"];

    var pickOpt = "";
    var drpOpt = "";
    for (var i = 0; i < $scope.pickupDrp.length; i++) {
        pickOpt += "<option id='pick" + [i] + "' value='" + $scope.pickupDrp[i] + "'>";
        pickOpt += $scope.pickupDrp[i];
        pickOpt += "</option>";
    }

    for (var i = 0; i < $scope.dropDrp.length; i++) {
        drpOpt += "<option id='drp" + [i] + "' value='" + $scope.dropDrp[i] + "'>";
        drpOpt += $scope.dropDrp[i];
        drpOpt += "</option>";
    }

    console.log(pickOpt);
    console.log(drpOpt);
    //-----------adhoc pikup or drop function---------------

    $('input[name="requestType"]').on('change', (function() {
        $scope.adhoc.pickTime = "";
        $scope.adhoc.dropTime = "";
        var value = $('input[name="requestType"]:checked').val();
        request_type(value);
    }));



    var pickTimeEle, dropTimeEle;
    pickTimeEle = '<div class="form-group picktime row col-sm-12 col-lg-6 col-md-6" >' +
        '<label for="example-email-input" class="col-lg-4 col-form-label">Pickup Time</label>' +
        '<div class="form-group  col-lg-8">' +
        '<select ng-model="adhoc.pickTime" class="form-control " name="pickuptime" required>' +
        '<option value=""  selected>Please Select</option>' + pickOpt + '</select></div></div>';

    //'<option ng-repeat="x in pickupDrp" id="pick{{$index}}" value="{{x}}"  >{{x}}</option></select></div></div>';


    dropTimeEle = '<div class="form-group droptime row col-sm-12 col-lg-6 col-md-6" >' +
        '<label for="example-email-input" class="col-lg-4 col-form-label">Drop Time</label>' +
        '<div class="form-group  col-lg-8">' +
        '<select ng-model="adhoc.dropTime" class="form-control " name="droptime" required>' +
        '<option value=""  selected>Please Select</option>' + drpOpt + '</select></div></div>';
    //'<option ng-repeat="x in dropDrp" id="drp{{$index}}" value="{{x}}"  >{{x}}</option></select></div></div>';

    function request_type(value) {
        if (value == "pickup") {
            $('.tooltip2 ,.tooltip1,#bth').css('display', 'none');
            $('.requestType').css("margin-right", "");
            $('#bth').html('');
            $(pickTimeEle).insertAfter(".requestType");
            $(".droptime").remove();
            $("select[name='droptime']").removeAttr('required').val("");
            $("select[name='pickuptime']").attr('required', true).val("");
            //$scope.adhoc.fromAliasName="Home";
            $('#fromPlaceDrp').val('Home').attr('disabled', true).css('-webkit-appearance', ' none');
            $scope.fromAlias('Home');
            var res = JSON.search($rootScope.adhocAddress, '//*[alias="Office"]');
            if (res.length > 0) {
                $('#toPlaceDrp').val('Office').attr('disabled', false).css('-webkit-appearance', ' menulist');
                $scope.toAlias('Office');
            } else {
                $('#toPlaceDrp').val('Client').attr('disabled', false).css('-webkit-appearance', ' menulist');
                $scope.toAlias('Client');
            }
            $('#toPlaceDrp .Home').addClass('hidden').attr('disabled',true);
            $('#toPlaceDrp .Office,#toPlaceDrp .Client').show();
            $('#fromPlaceDrp .Home').removeClass('hidden').attr('disabled',false);
            $('.places').removeClass('hidden').css('display', 'inline');
        }
        if (value == "drop") {
            $('.tooltip2 ,.tooltip1,#bth').css('display', 'none');
            $(".picktime").remove();
            $('#bth').html('');
            $('.requestType').css("margin-right", "");
            $(dropTimeEle).insertAfter(".requestType");
            $("select[name='droptime']").attr('required', true).val("");
            $("select[name='pickuptime']").removeAttr('required').val("");
            //$scope.adhoc.toAliasName="Home";
            var res = JSON.search($rootScope.adhocAddress, '//*[alias="Office"]');
            if (res.length > 0) {
                $('#fromPlaceDrp').val('Office').attr('disabled', false).css('-webkit-appearance', ' menulist');
                $scope.fromAlias('Office');
            } else {
                $('#fromPlaceDrp').val('Client').attr('disabled', false).css('-webkit-appearance', ' menulist');
                $scope.fromAlias('Client');
            }
            $('#toPlaceDrp').val('Home').attr('disabled', true).css('-webkit-appearance', ' none');
            $scope.toAlias('Home');
            $('#toPlaceDrp .Home').removeClass('hidden').attr('disabled',false);
            $('#fromPlaceDrp .Home').addClass('hidden').attr('disabled',true);
            $('.places').removeClass('hidden').css('display', 'inline');
        }
        if (value == "both") {
            $('.tooltip2 ,.tooltip1').css('display', 'none');
            $('#bth').css('display', 'block');
            $('#fromPlaceDrp').val('').attr('disabled', false).css('-webkit-appearance', ' menulist');
            $('#toPlaceDrp').val('').attr('disabled', true).css('-webkit-appearance', ' menulist');
            $('#toPlaceDrp .Home').removeClass('hidden').attr('disabled',false);
            $('#fromPlaceDrp .Home').removeClass('hidden').attr('disabled',false);
            $(".droptime,.picktime").remove();
            if ($(window).width() > 1000) {
                $('.requestType').css("margin-right", "100%");
            }
            //$(".picktime,.droptime").show().css("margin-right","");
            $("select[name='droptime']").attr('required', true).val("");
            $("select[name='pickuptime']").attr('required', true).val("");
            $('.places').removeClass('hidden').css('display', 'inline');

            $("select[name='pickuptime']").on('change', (function() {
                var pick = $("select[name='pickuptime']").val();
                var drop = $("select[name='droptime']").val();
            }));

            $("select[name='droptime']").on('change', (function() {
                var pick = $("select[name='pickuptime']").val();
                var drop = $("select[name='droptime']").val();
            }));
        }
        $('#fromPlaceDrp,#toPlaceDrp').attr('required', true);
    }

    //-------edit--------------
    $scope.edit = function(no) {

        userService.SendAdhocUpdateData(no, null)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(response);
                    $scope.adhoc = response.data;
                    console.log($scope.adhoc);
                    $('#adhoc_model').modal('show');

                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    $scope.update_adhoc = function(no) {
        $scope.adhoc = JSON.stringify($scope.adhoc);
        userService.SendAdhocUpdateData(no, $scope.adhoc)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(response);
                    var response = "<label>" + response.data + "</label>";
                    $('#adhoc_model').modal('hide');
                    document.getElementById('Adhoc_popup').innerHTML = response;
                    $('#Response_model').modal('show');
                    window.location.reload();
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }
    //--------------------------------------------------------------------------------------------------

    $('.Adhoc_panel #fromClear').click(function() {
        $(this).siblings().val('');
        $(this).siblings().attr("readonly", false);
        $(this).addClass('hidden');
        $scope.adhoc.from = "";
        $scope.$apply();
    });
    $('.Adhoc_panel #toClear').click(function() {
        $(this).siblings().val('');
        $(this).siblings().attr("readonly", false);
        $(this).addClass('hidden');
        $('#sat,#sun').addClass('hidden');
        $scope.adhoc.to = "";
        $scope.$apply();
    });

    $('.monthly_panel #fromClear').click(function() {
        $(this).siblings().val('');
        $(this).addClass('hidden');
        $scope.history.fromDate = "";
        $scope.$apply();
    });
    $('.monthly_panel #toClear').click(function() {
        $(this).siblings().val('');
        $(this).addClass('hidden');
        $scope.history.toDate = "";
        $scope.$apply();
    });


    $scope.history={};
    //<----------Adhoc History----------->
    $scope.Adhoc_History = function() {
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
            $('.adhoc_histry_table').hide();
            $(".alert").css("display", "block");
            $scope.status = 'Please provide input.';
            $(".alert").delay(3000).fadeOut();
            $timeout(function() {
                $scope.status = "";
            }, 3000);
        } else {
            userService.UserHistoryData($scope.history)
                .then(
                    function(response) {
                        if (response.headers('Log_Out') == "true") {
                            window.location = '/tmsApp/login';
                        }
                        console.log(JSON.stringify(response.data));
                        console.log(response);
                        $rootScope.data = response.data;
                        $('#adhoc_histry_table').DataTable().destroy();
                        $timeout(function() {
                            var table = $('#adhoc_histry_table').DataTable({
                                "responsive": true,
                                "dom": 'lBfrtip',
                                "columnDefs": [{
                                    "targets": [2, 6],
                                    "orderable": false
                                }],
                                //"ordering":false,
                                order: [],
                                "buttons": [{
                                    extend: 'excel',
                                    text: 'Excel',
                                    title: 'Report'
                                }]
                            });
                            $('#adhoc_histry_table').wrap('<div class="dataTables_scroll" />');
                            $('th').removeClass('sorting_asc');
                            // Add event listener for opening and closing details


                            if (response.data.length == 0) {
                                $timeout(function() {
                                    $('.adhoc_histry_table button').attr('disabled', true).css('cursor', 'not-allowed');
                                }, 30);
                            } else {
                                $timeout(function() {
                                    $('.adhoc_histry_table button').attr('disabled', false).css('cursor', 'pointer');
                                }, 30);
                            }
                            $timeout(function() {
                                $('[data-toggle="tooltip"]').tooltip();
                                // $('tr').removeClass('openChildRow');
                                //$('tr').removeClass('shown');
                                $('#adhoc_histry_table tbody').off().on('click', 'td.details-control', function() {
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
                                        console.log($rootScope.tripViewData);
                                        row.child(format($rootScope.data[id])).show();
                                        tr.addClass('shown');
                                        $('.openChildRow').remove();
                                        tr.next('tr').addClass('openChildRow');
                                    }
                                });
                            }, 50);
                        }, 30);
                        $('.adhoc_histry_table').show();
                    },
                    function(errResponse) {
                    	bootbox.alert({
                            message: "<label>Something went wrong.Inernal error.</label>"
                        });
                    }
                );
        }
    }

    //---------------------------------------adhoc self view---------------------------------
    var adhocTable;
    $scope.checkReqType = function(status, type) {
        $scope.request = type + " " + status + " Request";
        $('.adhocViewPanelBtns').hide();
        var rows = adhocTable.rows({
            'search': 'applied'
        }).nodes();
        if ((type == "Monthly" && status == "Unused") || (type == "Adhoc" && (status == "Approved" || status == "Pending"))) {
            $('.cancel', rows).show();
            $('.cancel').show();
        } else {
            $('.cancel').hide();
            $('.cancel', rows).hide();
        }
    }


    var status1, type1;
    $scope.getAdhocCabReqList = function(status, type) {
        status1 = status;
        type1 = type;
        if ((status == "" || status == undefined) && (type == "" || type == undefined)) {
            $scope.request = "Upcoming Request";
            $('.adhocViewPanelBtns').show();
            $('.cancel').show();
            status = "";
            type = "";
        } else {
            //$scope.checkReqType(status,type);
        }
        userService.getAdhocCabReqList(status, type).then(
            function(response) {
                if (response.headers('Log_Out') == "true") {
                    window.location = '/tmsApp/login';
                }
                $rootScope.adhocViewData = response.data;
                $('.adhocOtherViewTable').hide();
                $('.adhocviewTable').show();
                $('#adhocview').DataTable().destroy();
                console.log(response);
                $timeout(function() {
                    adhocTable = $('#adhocview').DataTable({
                        "responsive": true,
                        //"ordering":false,
                        order: [],
                        "dom": 'lBfrtip',
                        "columnDefs": [{
                            "targets": [2, 6],
                            "orderable": false
                        }],
                        "buttons": [{
                            extend: 'excel',
                            text: 'Excel',
                            title: 'Report',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6]
                            }
                        }]
                    });
                    $('#adhocview').wrap('<div class="dataTables_scroll" />');
                    $('th').removeClass('sorting_asc');
                    // Add event listener for opening and closing details
                    $('#adhocview tbody').off().on('click', 'td.details-control', function() {
                        var id = $(this).attr('id');
                        var tr = $(this).closest('tr');
                        var row = adhocTable.row(tr);
                        console.log(row);
                        if (row.child.isShown()) {
                            // This row is already open - close it
                            $('.openChildRow').remove();
                            row.child.hide();
                            tr.removeClass('shown');
                        } else {
                            // Open this row
                            $('tr').removeClass('shown');
                            console.log($rootScope.tripViewData);
                            row.child(adhocSelfformat($rootScope.adhocViewData[id])).show();
                            tr.addClass('shown');
                            $('.openChildRow').remove();
                            tr.next('tr').addClass('openChildRow');
                        }
                    });
                    if (response.data.length == 0) {
                        $timeout(function() {
                            $('.adhocviewTable button').attr('disabled', true).css('cursor', 'not-allowed');
                        }, 30);
                    } else {
                        $timeout(function() {
                            $('.adhocviewTable button').attr('disabled', false).css('cursor', 'pointer');
                        }, 30);
                    }
                    if ((status != "" && status != undefined) && (type != "" && type != undefined)) {
                        $scope.checkReqType(status, type);
                    }
                    $timeout(function() {
                        $('.ajax-loader').css("visibility", "hidden");
                    }, 30);
                }, 30);
            },
            function(errResponse) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            }
        );
    }
    //---------------------------------------adhoc others view---------------------------------   
    $scope.getAdhocOtherCabReqList = function() {
        userService.getAdhocOtherCabReqList().then(
            function(response) {
                if (response.headers('Log_Out') == "true") {
                    window.location = '/tmsApp/login';
                }
                $rootScope.adhocOtherViewData = response.data;
                $('.adhocOtherViewTable').show();
                $('.adhocviewTable').hide();
                $('#adhocOtherView').DataTable().destroy();
                console.log(response);
                $timeout(function() {
                    var table = $('#adhocOtherView').DataTable({
                        "responsive": true,
                        //"ordering":false,
                        order: [],
                        "dom": 'lBfrtip',
                        "columnDefs": [{
                            "targets": [3, 7],
                            "orderable": false
                        }],
                        "buttons": [{
                            extend: 'excel',
                            text: 'Excel',
                            title: 'Report',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
                            }
                        }]
                    });
                    $('#adhocOtherView').wrap('<div class="dataTables_scroll" />');
                    $('th').removeClass('sorting_asc');
                    // Add event listener for opening and closing details
                    $('#adhocOtherView tbody').off().on('click', 'td.details-control', function() {
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
                            row.child(adhocOtherFormat($rootScope.adhocOtherViewData[id])).show();
                            tr.addClass('shown');
                            $('.openChildRow').remove();
                            tr.next('tr').addClass('openChildRow');
                        }
                    });

                    if (response.data.length == 0) {
                        $timeout(function() {
                            $('.adhocOtherViewTable button').attr('disabled', true).css('cursor', 'not-allowed');
                        }, 30);
                    } else {
                        $timeout(function() {
                            $('.adhocOtherViewTable button').attr('disabled', false).css('cursor', 'pointer');
                        }, 30);
                    }

                }, 30);
            },
            function(errResponse) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            }
        );
    }

    //Reset History Search 
    $scope.resetHistorySearch = function() {
        $('.adhoc_histry_table').hide();
        $('#histrySearchForm')[0].reset();
        $scope.history = {};
        $('#histrySearchForm .glyphicon-remove').addClass('hidden');
        // $scope.$apply();

    }

    //Reset adhoc Search 
    $scope.adhocReset = function() {
        $('input[name="requestedProjectId"]').attr("disabled", true);
        $('#othrPrjct').show();
        $scope.adhoc = {};
        $('.tooltip2,.tooltip1').css("display", "none");
    }

    //Reset address form
    $scope.addressReset = function() {
        $scope.address = {};
        $scope.address.alias = "";
        $scope.address.city = "";
        $('.form-control').removeAttr('style');
        $('.addressAlias select').attr('disabled', false);
        $('.reflectFrom').addClass('hidden');
        $('#reflectFrom').attr({
            'required': false,
            'readonly': false
        });
        $('.homeBtn').val("Create");
    };
    //<---------- Adhoc Cancel Request---------->
    var viewType;
    $scope.cancel_request = function(no, selcDate, status, type,reqTime) {
        var today = new Date().toJSON().slice(0, 10).replace(/-/g, '/');
        var h = new Date().getHours();
        var nextDay = new Date();
        nextDay.setDate(nextDay.getDate() + 1);
        nextDay = new Date(nextDay).toJSON().slice(0, 10).replace(/-/g, '/');
        selcDate = new Date(selcDate).toJSON().slice(0, 10).replace(/-/g, '/');
        console.log(no);
        viewType = $("input[name='adhocView']:checked").val();
        //h=6;
        reqTime=reqTime.split(':');
        var Hr=reqTime[1].split(" ");
        if(Hr[1]=="PM"){
        	reqTime[0]=(+reqTime[0])+(+12);
        }
        if (selcDate == today) {
            if (h >= 17) {
                bootbox.alert({
                    message: "<label> You can't cancel request after 5 PM <br>&nbsp;&nbsp;a. Pickup/Drop scheduled for the same day.<br>&nbsp;&nbsp;b. Pickup/Drop scheduled for the next morning.</label>",
                });
            	}
            else if(h<=7){
            	if(reqTime[0]>=8){
            		$scope.cancelRequestFunc(no);
            	}
            	else{
            		bootbox.alert({
                        message: "<label> You can't cancel request after 5 PM <br>&nbsp;&nbsp;a. Pickup/Drop scheduled for the same day.<br>&nbsp;&nbsp;b. Pickup/Drop scheduled for the next morning.</label>",
                    });
            	}
            }
            else {
                $scope.cancelRequestFunc(no);
            }
        } else if (selcDate == nextDay) {
            if (h>=17) {
            	if(reqTime[0]<=8){
                bootbox.alert({
                    message: "<label> You can't cancel request after 5 PM <br>&nbsp;&nbsp;a. Pickup/Drop scheduled for the same day.<br>&nbsp;&nbsp;b. Pickup/Drop scheduled for the next morning.</label>",
                });
            	}
            	else{
            		$scope.cancelRequestFunc(no);
            	}
            } else {
                $scope.cancelRequestFunc(no);
            }
        } else {
            $scope.cancelRequestFunc(no);
        }

    }

    $scope.cancelRequestFunc = function(no) {
        bootbox.confirm("<label>Are you sure want to Cancel Request?</label>", function(result) {
            if (result == true) {
                userService.CancelRequest(no)
                    .then(
                        function(response) {
                            if (response.headers('Log_Out') == "true") {
                                window.location = '/tmsApp/login';
                            }
                            var response = "<label>" + response.data.data + "</label>";
                            $('#Adhoc_popup').html(response);
                            $('#myModal').modal('show');
                            if (viewType == 'self') {
                                $scope.getAdhocCabReqList(status1, type1);
                            } else {
                                $scope.getAdhocOtherCabReqList();
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




    $scope.convertMillisecond = function(pick, drop, type) {

        pick = pick.split(" ");
        pick = pick[1];
        drop = drop.split(" ");
        drop = drop[1];

        $rootScope.pickTime = pick;
        $rootScope.dropTime = drop;
        if (pick == drop) {
            bootbox.alert({
                message: "<label>Please select 8 Hrs range between Pickup and Drop time.</label>",
                callback: function() {
                    if (type == "pickup") {
                        var pick = $("select[name='pickuptime']").val("");
                    } else {
                        var drop = $("select[name='droptime']").val("");
                    }
                }
            });
        }
    }




    //====================================================================
    //<----------excel download-->

    $("#btnExport").click(function(e) {
        e.preventDefault();
        //getting data from our table
        var data_type = 'data:application/vnd.ms-excel';
        var table_div = document.getElementById('adhocview');
        var table_html = table_div.outerHTML.replace(/ /g, '%20');
        var a = document.createElement('a');
        a.href = data_type + ', ' + table_html;
        a.download = 'exported_table_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
        a.click();
    });

    //---------------------get project list on load(user detail page)---------------------
    $scope.getProject = function(value) {
        userService.getEmpProjectList(value)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(response);
                    $scope.project = response.data;
                    $('#projectId').val("");


                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    $scope.getDefaultProject = function() {
        $scope.getProject(0);
    }

    //-----------------------------------add employee account dropdwn------------------------------------------------
    $('#empAccntDrp').on('change', (function() {
        var value = $('#empAccntDrp option:selected').val();
        $scope.getProject(value);
    }));

    //<---------------------------------------------Address update------------------------------------------->
    $scope.getAddressDetail = function() {
        $('.ajax-loader').css("visibility", "visible");
        userService.getAddressDetail()
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    if (response.data.data == "Employee Address is Empty") {
                        $('#addressTableId').DataTable().destroy();
                        $rootScope.addressData = [];
                        $scope.address_data = [];
                        $timeout(function() {
                            var table = $('#addressTableId').DataTable({
                                "responsive": true,
                                "paging": false,
                                "ordering": false,
                                "info": false,
                                "searching": false
                            });
                            $('#addressTableId').wrap('<div class="dataTables_scroll" />');
                            $('th').removeClass('sorting_asc');
                            $('.ajax-loader').css("visibility", "hidden");
                            $('.user_detail').removeClass('hidden');
                            if (response.data.length > 2) {
                                $('.addAddress').hide();
                            } else {
                                $("<div style='padding-top: 10px; padding-right: 15px;'  class='row addAddress'><a class='fa fa-plus-circle fa-lg' data-toggle='tooltip' title='Add New Address' style='float: right;'> Add Address</a>").insertAfter("#addressTableId");
                            }

                            $timeout(function() {

                                $('.addAddress').click(function() {
                                    $('#addressModal').modal('show');
                                    $scope.addressReset();
                                    document.getElementById("address_form").reset();
                                });
                            }, 60);
                        }, 30);
                    } else {
                        $rootScope.addressData = response.data;
                        $('#addressTableId').DataTable().destroy();
                        //$scope.address_data = response.data;
                        var json = JSON.search(response.data, '//*[alias="Client"]');
                        if (json.length > 0) {
                            var client = JSON.search(response.data, '//*[alias="Client"]');
                            var office = JSON.search(response.data, '//*[alias="Office"]');

                            if (client[0].defaultAddress == true) {
                                office[0].defaultAddress = false;
                            } else {
                                if (office.length > 0) {
                                    office[0].defaultAddress = true;
                                }
                            }
                            //                    	var home=JSON.search(response.data, '//*[alias="Home"]');
                            //                    	//home[0].isCabRequired=false;
                            //                    	$scope.isCabRequired=home[0].isCabRequired;
                            //                    	$scope.empId=home[0].employeeId;
                        }
                        var home = JSON.search(response.data, '//*[alias="Home"]');
                        //home[0].isCabRequired=false;
                        if (home.length > 0) {
                            $scope.isCabRequired = home[0].isCabRequired;
                            $scope.empId = home[0].employeeId;
                        }
                        $scope.address_data = response.data;

                        console.log(JSON.stringify(response.data));
                        $timeout(function() {
                            var table = $('#addressTableId').DataTable({
                                "responsive": true,
                                "paging": false,
                                "ordering": false,
                                "searching": false,
                                "info": false
                            });
                            $('#addressTableId').wrap('<div class="dataTables_scroll" />');
                            $('th').removeClass('sorting_asc');
                            if (response.data.length > 2) {
                                $('.addAddress').hide();
                            } else {
                                $("<div style='padding-top: 10px; padding-right: 15px;'  class='row addAddress'><a class='fa fa-plus-circle fa-lg' data-toggle='tooltip' title='Add New Address' style='float: right;'> Add Address</a>").insertAfter("#addressTableId");
                            }
                            $timeout(function() {
                                $('.addAddress').click(function() {
                                    $('#addressModal').modal('show');
                                    $scope.addressReset();
                                    document.getElementById("address_form").reset();
                                });
                            }, 60);
                        }, 30);
                        var resAlias1 = JSON.search($rootScope.addressData, '//*[alias="Home"]');
                        var resAlias2 = JSON.search($rootScope.addressData, '//*[alias="Client"]');
                        if (resAlias1.length > 1) {
                            var res1 = JSON.search(resAlias1, '//*[statusValue="Approved"]');
                            $timeout(function() {
                                $('#AddrAction' + res1[0].empAddressBeanId + ' .fa').hide();
                            }, 40);
                        }
                        if (resAlias2.length > 1) {
                            var res2 = JSON.search(resAlias2, '//*[statusValue="Approved"]');
                            $timeout(function() {
                                $('#AddrAction' + res2[0].empAddressBeanId + ' .fa').hide();
                            }, 40);
                        }
                        $('.ajax-loader').css("visibility", "hidden");
                        $('.user_detail').removeClass('hidden');
                    }
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    $scope.createAddress = function() {

        $scope.address.effectiveDate = $('#reflectFrom').val();
        //$scope.address.city=$("#addressModal #locationId option:selected").attr("id");
        var addr = $scope.address.alias;
        var addr1 = JSON.search($rootScope.addressData, '//*[alias="' + addr + '"]');
        if (addr1.length >= 1) {
            bootbox.confirm("<label>Address will be updated once manager approves your request.</label>", function(result) {
                if (result == true) {
                    $scope.craeteAddrFunction($scope.address);
                    $('.addressAlias select').attr('disabled', false);
                    $('#addressModal').modal('hide');
                } else {
                    $scope.getAddressDetail();
                }
            });
        } else {
            $scope.craeteAddrFunction($scope.address);
            $('.addressAlias select').attr('disabled', false);
            $('#addressModal').modal('hide');
        }
    }
    //------------------create address function--------------------

    $scope.craeteAddrFunction = function(data) {
        userService.CreateAddressData(data)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(JSON.stringify(response.data));
                    console.log(response);
                    $('#address_form .form-control').removeAttr('style');
                    $('.addressBtns .btn').attr('disabled', true);
                    $scope.address = {};
                    $('.reflectFrom').addClass('hidden');
                    $('#reflectFrom').attr({
                        'required': false,
                        'readonly': false
                    });
                    $scope.getAddressDetail();
                    $('#empDetail').addClass('collapse in').attr('aria-expanded', true).css("height", "");
                    $('#addrDetail').removeClass('in').attr('aria-expanded', true);
                    $('.personal .faq-links i').removeClass('fa-plus').addClass('fa-minus');
                    $('.address .faq-links i').removeClass('fa-minus').addClass('fa-plus');
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }
    //-------------------------Edit Address-------------------------------
    $scope.edit_address = function(id, alias) {
        $('#address_form .form-control').css({
            "border-color": " #66afe9",
            "box-shadow": "inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102,175,233,.6)"
        });
        $('select[name="alias"]').removeAttr('style');
        $scope.getAddressById(id);
        //$('.addressAlias select').attr('disabled',true);
        $('.addressBtns .btn').attr('disabled', false);
        $('.reflectFrom').removeClass('hidden');
        $('#reflectFrom').attr('required', true);
        $('.homeBtn').val("Update");
    }
    $scope.getAddressById = function(id) {
        userService.getAddressByAddressId(id)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(JSON.stringify(response.data));
                    if (response.data.status == true) {
                        response.data.effectiveDate = null;
                    }
                    $scope.address = response.data;
                    if ($scope.address.effectiveDate != null) {
                        $scope.address.effectiveDate = $.datepicker.formatDate('yy-mm-dd', new Date($scope.address.effectiveDate));
                    }
                    /*$('#addrDetail').addClass('collapse in').attr('aria-expanded',true).css("height", "");
                    $('#empDetail').removeClass('in').attr('aria-expanded',true);
                    $('.address .faq-links i').removeClass('fa-plus').addClass('fa-minus');
                    $('.personal .faq-links i').removeClass('fa-minus').addClass('fa-plus');*/
                    $('#addressModal').modal('show');
                    /*$('.panel-heading .faq-links').trigger( "click" );*/
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    $scope.getSecondryApprover = function(id, data) {
        //    	id=1;
        userService.getSecondryApprover(id)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(JSON.stringify(response.data));
                    bootbox.confirm("<label>An approval e-mail will be sent to " + response.data.data + ". Click OK to confirm.</label>", function(result) {
                        if (result == true) {
                            data.managerEmail = response.data.data;
                            $scope.sendApproval(data);
                        }
                    });
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    $scope.sendApproval = function(data) {
        userService.sendApproval(data)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(JSON.stringify(response.data));
                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    $scope.secondaryApproval = function(id, data) {
        $scope.getSecondryApprover(id, data);
    }
    //------------------default Work Location Change---------------
    $scope.defaultWorkPlace = function(val,json) {
        // alert(val);
        if (val == "Client") {
            var id = JSON.search(json, '//*[alias="Client"]');
            if (id[0].statusValue == "Pending") {
                bootbox.alert({
                    message: "<label>Request for Client address updation is pending for Manager's approval.<br> Please try after it is approved.</label>",
                    callback: function() {
                        $scope.getAddressDetail();
                    }
                });
            } else {
                $scope.changeWorkLocation(val,json);

            }
        } else {
            $scope.changeWorkLocation(val,json);
        }
    }
    $scope.changeWorkLocation = function(val,json) {
        bootbox.confirm("<label>Do you really want to change the work location?</label>", function(result) {
            if (result == true) {
                var data, id;
                id = JSON.search(json, '//*[alias="Client"]');
                id = id[0].empAddressBeanId;
                if (val == "Client") {
                    data = true;
                } else {
                    data = false;
                }
                userService.defaultWorkPlace(data, id)
                    .then(
                        function(response) {
                            if (response.headers('Log_Out') == "true") {
                                window.location = '/tmsApp/login';
                            }
                            console.log(JSON.stringify(response.data));

                        },
                        function(errResponse) {
                        	bootbox.alert({
                                message: "<label>Something went wrong.Inernal error.</label>"
                            });
                        }
                    );
            } else {
                $scope.getAddressDetail();
            }
        });

    }


    //-------------------isCabRequired--------------------------

    $scope.CabRequired = function(id, val) {
        if (val == true) {
            bootbox.confirm("<label>It will be effective from the upcoming cab request.<br>Click OK to confirm.", function(result) {
                if (result == true) {
                    $scope.iSCabRequired(id, val);
                } else {
                    $scope.getAddressDetail();
                }

            });
        } else {
            bootbox.confirm("<label>All the raised requests will be cancelled.<br>Click OK to confirm.</label>", function(result) {
                if (result == true) {
                    $scope.iSCabRequired(id, val);
                } else {
                    $scope.getAddressDetail();
                }

            });
        }

    }
    $scope.iSCabRequired = function(id, val) {
        userService.isCabRequired(id, val)
            .then(
                function(response) {
                    if (response.headers('Log_Out') == "true") {
                        window.location = '/tmsApp/login';
                    }
                    console.log(JSON.stringify(response.data));

                },
                function(errResponse) {
                	bootbox.alert({
                        message: "<label>Something went wrong.Inernal error.</label>"
                    });
                }
            );
    }

    //----------------Address Alias Selection----------------
    $('select[name="alias"]').off().on('change', (function() {
        var val = $(this).val();
        if ($rootScope.addressData.length <= 0) {
            $('.addressBtns .btn').attr('disabled', false);
        } else {
            var res = JSON.search($rootScope.addressData, '//*[alias="' + val + '"]');
            if (res.length > 0) {
                /*bootbox.alert({
        	    message: "<label>You will not be able to enter existing Alias again. <br>If you want to edit the address, please click on Edit icon</label>",
        	    callback: function () {
        	    	$('select[name="alias"]').val("");
        	    	$scope.address.alias="";
        	    	$('.addressBtns .btn').attr('disabled',true);
        	    }
        	});*/
                if (res[0].status == true) {
                    res[0].effectiveDate = null;
                }
                $scope.address = res[0];
                if ($scope.address.effectiveDate != null) {
                    $scope.address.effectiveDate = $.datepicker.formatDate('yy-mm-dd', new Date($scope.address.effectiveDate));
                }
                $('.reflectFrom').removeClass('hidden');
                $('#reflectFrom').attr('required', true);
                $('.homeBtn').val("Update");
            } else {
                $scope.address = {};
                $scope.address.alias = val;
                $('.addressBtns .btn').attr('disabled', false);
                $('.reflectFrom').addClass('hidden');
                $('#reflectFrom').attr('required', false);
                $('.homeBtn').val("Create");
            }
        }
    }));


    $('.faq-links').click(function() {
        var collapsed = $(this).find('i').hasClass('fa-plus');

        $(this).find('i').removeClass('fa-minus');

        $(this).find('i').addClass('fa-plus');
        if (collapsed)
            $(this).find('i').toggleClass('fa-plus  fa-minus');
    });

}]);