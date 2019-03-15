'use strict';

angular.module('tmsApp').factory('adminService', ['$localStorage', '$http', '$q', 'urls',
    function($localStorage, $http, $q, urls) {

        var factory = {
        		getProject: getProject,
        		getProjectEmployee:getProjectEmployee,
        		updateEmployeeRoles:updateEmployeeRoles,
        		uploadAccntPrjctExcelFile:uploadAccntPrjctExcelFile,
        		uploadEmpExcel:uploadEmpExcel,
        		saveEmpList:saveEmpList,
        		FlmHistoryData:FlmHistoryData,
        		flmUpcomingReq:flmUpcomingReq,
        		flmDailyReq:flmDailyReq,
        		createTripSheet:createTripSheet,
        		discardTrip:discardTrip,
        		editAndUpdateTripSheet:editAndUpdateTripSheet,
        		updateActioAndStatus:updateActioAndStatus,
        		getSpocOfProject:getSpocOfProject,
        		getEmpOfProjectTrack:getEmpOfProjectTrack,
        		updateTrackSpocRole:updateTrackSpocRole,
        		getTripSheetNumber:getTripSheetNumber,
        		updateTrackEmp:updateTrackEmp,
        		createAndUpdateVendor:createAndUpdateVendor,
        		deleteVendorDetail:deleteVendorDetail,
        		getVendorDetail:getVendorDetail,
        		getEmpList:getEmpList,
        		getAllTripSheetDetail:getAllTripSheetDetail,
        		sendSelcectedProj:sendSelcectedProj
        };

        return factory;

       
        function getProject(Data,role) {
            var deferred = $q.defer();
            var url;
            if(role=='FRONT_DESK'){
                url="/tmsApp/tms/flmDashboard/getProject";
            }
            else{
            	url="/tmsApp/tms/project/getProject";
            }
            $http({
                url: url,
                method: "POST",
                data: Data,
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });

            });
            return deferred.promise;
        }
        function getProjectEmployee(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/project/getEmployeeRoles",
                method: "POST",
                data: Data,
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });

            });
            return deferred.promise;
        }
        
        function getSpocOfProject(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/project/getTrackRoleMapping",
                method: "POST",
                data: Data,
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
                console.log("error");

            });
            return deferred.promise;
        }
        
        function getEmpOfProjectTrack(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/project/getEmployeeTrackRole",
                method: "POST",
                data: Data,
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });


            });
            return deferred.promise;
        }
        function updateEmployeeRoles(Data,page) {
        	var url;
        	/*if(page=="manager"){
        		url="/tmsApp/tms/mappings/updateUserRoleMapping";
        	}
        	else{
        		url="/tmsApp/tms/adminPage/updateUserRoleMapping";
        	}*/
        	console.log(JSON.stringify(Data));
            var deferred = $q.defer();
            $http({
                url:"/tmsApp/tms/project/updateUserRoleMapping",
                method: "POST",
                data: Data,
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });


            });
            return deferred.promise;
        }
        
        
        function FlmHistoryData(Data) {

            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/flmDashboard/flmCabRequestHistory",
                method: "POST",
                data: Data
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });


            });
            return deferred.promise;
        }
        
        function uploadAccntPrjctExcelFile(formData) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/project/uploadExcel",
                method: "POST",
                data: formData,
                headers: {
                    'Content-Type': undefined
                }
            }).then(function successCallback(response) {
                //var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
                //alert(response.status);
                var json = response.status;
                deferred.resolve(json);
               // $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        function uploadEmpExcel(formData) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/uploadEmployeeList ",
                method: "POST",
                data: formData,
                headers: {
                    'Content-Type': undefined
                }
            }).then(function successCallback(response) {
               // var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
                //alert(response.status);
                var json = response.status;
                deferred.resolve(json);
               // $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        function saveEmpList(formData) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/saveEmployeeList",
                method: "POST",
                data: formData
            }).then(function successCallback(response) {
               // var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
                //alert(response.status);
                var json = response.status;
                deferred.resolve(json);
               // $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        saveEmpList
        
        function flmUpcomingReq() {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/flmDashboard/viewUpcomingRequestForFLM",
                method: "POST"
                
            }).then(function successCallback(response) {
                //var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
               // $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        function flmDailyReq(data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/flmDashboard/getDailyReport",
                method: "GET",
                params:{
                	dayWiseList:data
                }
            }).then(function successCallback(response) {
                //var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });

               // $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        function createTripSheet(Data,bool,opt) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/tripSheetDetails/saveTripSheetDetails",
                method: "POST",
                params: {
                	TripSheetDetailsJson: Data,
                	updateTripNumInCabDetails:bool,
                	tripSheetId:opt
                }
                
            }).then(function successCallback(response) {
                //var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });

               // $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        function discardTrip(cabid,tripid) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/tripSheetDetails/removeTripIdInDetails",
                method: "POST",
                params: {
                	detailsId:cabid,
                	tripId:tripid
                }
                
            }).then(function successCallback(response) {
                //var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });

               // $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        function editAndUpdateTripSheet(getDetails,id,cabId,data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/tripSheetDetails/updateTripSheetDetails",
                method: "POST",
                dataType: 'json',
                params: {
                	updateTripSheetDetails: data,
                    getDetails: getDetails,
                    tripSheetId: id,
                    cabRequestId:cabId
                }
                
            }).then(function successCallback(response) {
               // var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });

              //  $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        function getVendorDetail(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/flmDashboard/getVendor?vendorId="+ Data,
                method: "GET",
                //vendorId: Data,
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function createAndUpdateVendor(data,getDetails) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/flmDashboard/saveUpdateVendorDetails",
                method: "POST",
                dataType: 'json',
                params: {
                	updateVendorDetails: data,
                	getDetails:getDetails,
                }
                
            }).then(function successCallback(response) {
                //var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });

              //  $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        function deleteVendorDetail(data,details) {
        	if(details=="spoc"){
        		var spoc=data
        	}
        	else{
        		var vendor=data;
        	}
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/flmDashboard/saveUpdateVendorDetails",
                method: "POST",
                dataType: 'json',
                params: {
                	getDetails:"delete",
                	vendorId:vendor,
                	vendorSpocId:spoc
                }
                
            }).then(function successCallback(response) {
               // var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
              //  $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        function updateActioAndStatus(data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/flmDashboard/ActionTravelStatusUpdateByFLM",
                method: "POST",
                dataType: 'json',
                data:data
                
            }).then(function successCallback(response) {
                //var json = response.data;
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
               // $scope.error = response.statusText;
            });
            return deferred.promise;
        }
        
        function updateTrackSpocRole(Data,id) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/project/updateSpocTrackMapping?projectId="+id,
                method: "POST",
                data:Data,
                dataType: 'json'
                

            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function updateTrackEmp(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/project/updateUserTrackMapping",
                method: "POST",
                data: Data,
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function getTripSheetNumber() {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/tripSheetDetails/getAllTripSheet",
                method: "GET",
            }).then(function successCallback(response) {
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function getAllTripSheetDetail() {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/tripSheetDetails/getAllTripSheetDetails",
                method: "GET",
            }).then(function successCallback(response) {
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function getEmpList() {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/flmDashboard/projectMappingEmpList",
                method: "GET",
            }).then(function successCallback(response) {
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function sendSelcectedProj(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/flmDashboard/updateUserProjMapping",
                method: "POST",
                data: Data,
            }).then(function successCallback(response) {
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
    }
]);