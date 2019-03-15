"use strict";

angular.module('tmsApp',[]).controller('actionController', [ '$scope','$rootScope', '$http','$q', function($scope, $rootScope, $http,$q) {

//	$scope.user={"name":"Shoban Babu JP","empcode":"6653","email":"shobanbabup@quinnox.com","mobileNumber":"8148977019","project":"Amway Europe-CQ5","manager":"Ashwathy","created":"Oct 25,2017 10.00 AM","scheduled":"Nov 2, 2017 6.30 PM"};

$scope.action=function(id){
	var deferred = $q.defer();
	$scope.actions.cabRequestId=id;
	console.log($scope.actions);
	$http({
        url: "/tmsApp/tms/external/managerRemark",
        method: "POST",
        data: $scope.actions
    }).then(function successCallback(response) {
    	if(response.headers('Log_Out')=="true"){
			window.location='/tmsApp/login';
		}
    	 deferred.resolve(response);
    	 console.log(response.data);
    	 window.location.href="/tmsApp/tms/successPage.jsp";
    }, function errorCallback(response) {
    	bootbox.alert({
            message: "<label>Something went wromg.Inernal error.</label>"
        });

    });
	return deferred.promise;
}

$('input[name="Action"]').off().on('change',(function(){
	var val=$(this).val();
	if($scope.flmActions!=undefined){
	$scope.flmActions.action=val;
	}
	if($scope.actions!=undefined){
		$scope.actions.action=val;
		}
	if(val=="Rejected"){
		$('textarea').attr('required',true);
	}
	else{
		$('textarea').attr('required',false);
	}
}));

$scope.flmAction=function(newId){
	var deferred = $q.defer();
	console.log($scope.flmActions);
	if($scope.flmActions.comment ==undefined){
		$scope.flmActions.comment=null;
	}
	$http({
        url: "/tmsApp/tms/external/ManagerRemarkForAddrChangePost",
        method: "POST",
        params:{
        	//oldId:oldId,
        	newId:newId,
        	action:$scope.flmActions.action,
        	comment:$scope.flmActions.comment      	
        }
    }).then(function successCallback(response) {
    	if(response.headers('Log_Out')=="true"){
			window.location='/tmsApp/login';
		}
    	 deferred.resolve(response);
    	 console.log(response.data);
    	 window.location.href="/tmsApp/tms/successPage.jsp";
    }, function errorCallback(response) {
    	bootbox.alert({
            message: "<label>Something went wromg.Inernal error.</label>"
        });

    });
	return deferred.promise;
}
}]);
