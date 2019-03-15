'use strict';

angular.module('tmsApp').factory('financeService', ['$localStorage', '$http', '$q', 'urls',
    function($localStorage, $http, $q, urls) {

        var factory = {
        		getAllowanceView: getAllowanceView,
        		getProject:getProject
        		
        };

        return factory;
        
        function getAllowanceView(data) {
     	   var deferred = $q.defer();
         $http({
             url: "/tmsApp/tms/financePage/getAllShiftAllowance",
             method: "GET",
             params: {
             	accountId:data.accountId,
             	projectId:data.projectId,
             	fromDate:data.fromDate,
             	toDate:data.toDate
                
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
     
     
     function getProject(Data) {
         var deferred = $q.defer();
          $http({
             url: "/tmsApp/tms/financePage/getProjectList",
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
   
}]);