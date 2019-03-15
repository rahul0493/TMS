'use strict';

angular.module('tmsApp').factory('shiftUserService', ['$localStorage', '$http', '$q', 'urls',
    function($localStorage, $http, $q, urls) {

        var factory = {
            SendAdhocData: SendAdhocData,
            getAdhocAddress:getAdhocAddress,
            SendAdhocUpdateData: SendAdhocUpdateData,
            getAdhocCabReqList:getAdhocCabReqList,
            getAdhocOtherCabReqList:getAdhocOtherCabReqList,
            UserHistoryData: UserHistoryData,
            CancelRequest: CancelRequest,
            getEmpProjectList:getEmpProjectList,
            UpdateAddressData: UpdateAddressData,
            DefaultAddress:DefaultAddress,
            DeleteAddress:DeleteAddress,
            CreateAddressData:CreateAddressData,
            getAddressDetail:getAddressDetail,
            getAddressByAddressId:getAddressByAddressId,
            getSecondryApprover:getSecondryApprover,
            sendApproval:sendApproval,
            defaultWorkPlace:defaultWorkPlace,
            isCabRequired:isCabRequired
        };

        return factory;

        /*function loadAllUsers() {
            console.log('Fetching all users');
            var deferred = $q.defer();
            $http.get(urls.USER_SERVICE_API)
                .then(
                    function(response) {
                        console.log('Fetched successfully all users');
                        $localStorage.users = response.data;
                        deferred.resolve(response);
                    },
                    function(errResponse) {
                        console.error('Error while loading users');
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function getAllUsers() {
            return $localStorage.users;
        }

        function getUser(id) {
            console.log('Fetching User with id :' + id);
            var deferred = $q.defer();
            $http.get(urls.USER_SERVICE_API + id)
                .then(
                    function(response) {
                        console.log('Fetched successfully User with id :' + id);
                        deferred.resolve(response.data);
                    },
                    function(errResponse) {
                        console.error('Error while loading user with id :' + id);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function createUser(user) {
            console.log('Creating User');
            var deferred = $q.defer();
            $http.post(urls.USER_SERVICE_API, user)
                .then(
                    function(response) {
                        loadAllUsers();
                        deferred.resolve(response.data);
                    },
                    function(errResponse) {
                        console.error('Error while creating User : ' + errResponse.data.errorMessage);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function updateUser(user, id) {
            console.log('Updating User with id ' + id);
            var deferred = $q.defer();
            $http.put(urls.USER_SERVICE_API + id, user)
                .then(
                    function(response) {
                        loadAllUsers();
                        deferred.resolve(response.data);
                    },
                    function(errResponse) {
                        console.error('Error while updating User with id :' + id);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }

        function removeUser(id) {
            console.log('Removing User with id ' + id);
            var deferred = $q.defer();
            $http.delete(urls.USER_SERVICE_API + id)
                .then(
                    function(response) {
                        loadAllUsers();
                        deferred.resolve(response.data);
                    },
                    function(errResponse) {
                        console.error('Error while removing User with id :' + id);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }*/

        function SendAdhocData(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/adhocRequest/saveAdhocRequest",
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
        
        function getAdhocAddress(data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/adhocRequest/getAddress",
                method: "POST",
                params: {
                	empId:data
                },
                
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }

        function SendAdhocUpdateData(requestId, updateCabDetails) {
            var deferred = $q.defer();
            var getDetails;
            //console.log(Data+no);
            if (updateCabDetails == null || updateCabDetails == undefined) {
                getDetails = true;
            } else {
                getDetails = false;
            }

            $http({
                url: "/tmsApp/tms/adhocRequest/updateRequest",
                method: "POST",
                dataType: 'json',
                params: {
                    updateCabDetails: updateCabDetails,
                    getDetails: getDetails,
                    requestId: requestId
                }


            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function getAdhocCabReqList(status,type) {
        	var url,deferred;
        	if(type=="Adhoc"){
        		url="/tmsApp/tms/adhocRequest/getAdhocCabRequestList";
        	}
        	else if(type=="Monthly"){
        		url="/tmsApp/tms/adhocRequest/monthlyReqByUserId";
        	}
        	else{
        		url="/tmsApp/tms/adhocRequest/getAdhocCabRequestList";
        	}
            deferred = $q.defer();
            $http({
                url:url,
                method: "GET",
                params: {
                	status:status
                }
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function getAdhocOtherCabReqList() {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/adhocRequest/viewOtherCabDetails",
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
        
        function UserHistoryData(Data) {

            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/adhocRequest/cabRequestHistory",
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
        
        function CancelRequest(Data) {


            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/adhocRequest/deleteIndividualRequest",
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
        function getEmpProjectList(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/getProjectList",
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

        //address update
       
        
        function CreateAddressData(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/save_employee_address",
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
        function getAddressDetail() {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/getEmployeeAddress",
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
        function UpdateAddressData(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/update_employee_address",
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
        function DefaultAddress(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/SwapEmpDefaultAddress",
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
        
        function DeleteAddress(Data,id) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/delete_employee_address",
                method: "POST",
                params: {
                	employeeDetailsJSON: Data,
                	requestId: id,
                }
            }).then(function successCallback(response) {
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function getAddressByAddressId(id) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/getAddressByAddressId",
                method: "POST",
                data:id
            }).then(function successCallback(response) {
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function getSecondryApprover(id) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/getSecondaryApproverMail",
                method: "GET",
                params: {
                	userId: id
                }
            }).then(function successCallback(response) {
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function sendApproval(data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/sendMailSecondaryAppr",
                method: "POST",
                data:data
            }).then(function successCallback(response) {
                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function defaultWorkPlace(data,id){
        	 var deferred = $q.defer();
             $http({
                 url: "/tmsApp/tms/employee/setDefaultAddress",
                 method: "POST",
                 params:{
                	 value:data,
                	 addressId:id
                 }
             }).then(function successCallback(response) {
                 deferred.resolve(response);
             }, function errorCallback(response) {
            	 bootbox.alert({
                     message: "<label>Something went wrong.Inernal error.</label>"
                 });
             });
             return deferred.promise;
        }
        
        function isCabRequired(id,val){
       	 var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/employee/setCabRequiredForEmp",
                method: "POST",
                params:{
                	userId :id,
                	isCabRequired :val
                }
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