'use strict';

angular.module('tmsApp').factory('spocService', ['$localStorage', '$http', '$q', 'urls',
    function($localStorage, $http, $q, urls) {

        var factory = {
        		ProjectShiftData:ProjectShiftData,
        		getShiftProjectList:getShiftProjectList,
        		getTrackProjectList:getTrackProjectList,
        		getExistTrackListByProject:getExistTrackListByProject,
        		getTrackList:getTrackList,
        		getShiftDetailsByTrack:getShiftDetailsByTrack,
        		ProjectShiftEditUpdateDelete:ProjectShiftEditUpdateDelete,
        		trackerCreation:trackerCreation,
        		trackerUpdate:trackerUpdate,
        		getProjectShiftDetails:getProjectShiftDetails,
        		deleteShift:deleteShift
        }
        return factory;
        
        function ProjectShiftData(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/project/save_project_details",
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
        
        function deleteShift(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/shift/deleteShiftDetails",
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
        
        
        function ProjectShiftEditUpdateDelete(projectId,accountId,trackId,action,data) {
       	 var deferred = $q.defer();
            var getDetails;
            $http({
                url: "/tmsApp/tms/project/updateProjectShiftDetails",
                method: "POST",
                dataType: 'json',
                params: {
               	 updateProjectShiftDetails: data,
                    getDetails: action,
                    trackId:trackId,
                    projectId: projectId,
                    accountId:accountId
                    
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
        
        function getShiftProjectList(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/project/getProject",
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
        
        
        function getShiftDetailsByTrack(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/project/getShiftListByTrack",
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
        
        function getTrackProjectList(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/track/getProjectTrackerList",
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
        
        function getExistTrackListByProject() {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/track/getExistProjectTrackerList",
                method: "POST"
                
            }).then(function successCallback(response) {

                deferred.resolve(response);
            }, function errorCallback(response) {
            	bootbox.alert({
                    message: "<label>Something went wrong.Inernal error.</label>"
                });
            });
            return deferred.promise;
        }
        
        function getTrackList(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/track/getTrackList",
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
        
        function trackerCreation(Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/track/save_track_details",
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
        
        function trackerUpdate(no,getDetails,Data) {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/track/updateTrackDetails",
                method: "POST",
                params: {
                	updateTrackDetails: Data,
                    getDetails: getDetails,
                    trackId:no
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
        
        function getProjectShiftDetails() {
            var deferred = $q.defer();
            $http({
                url: "/tmsApp/tms/project/getProjectShiftDetails",
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

}]);