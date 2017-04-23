(function() {
    var app = angular.module('myApp', []);
    var notebookBase = "/api";
    app.factory("Post", function($resource) {
	return $resource(notebookBase);
    });
    app.controller('NotebookController', function($scope, $http) {
        $scope.notes = {};
    	$scope.reload = function() {
            $http.get(notebookBase)
            .success(function (response) {
            	$scope.notes = response;
        	});
         };
    
         $scope.create = function() {
        	var toSend = $scope.note;
            $http.post(notebookBase, toSend)
            .success(function (response) {$scope.reload();});
            this.note = {};
        };

        $scope.delete = function(id) {
            $http.delete(notebookBase + "/"+ id)
            .success(function (response) {$scope.reload();});
            $scope.note = {};
        }

        $scope.update = function(id) {
        	var toSend = $scope.note;
            $http.put(notebookBase + "/"+ id, toSend)
            .success(function (response) {$scope.reload();});
            $scope.note = {};
        }

        $scope.select = function(id) {
            $scope.note = $scope.notes[id];
        }

        $scope.edit = function(id) {
        	$http.get(notebookBase + "/"+ id)
        	.success(function (response) {$scope.note = response});
        }

        $scope.newNote = function() {
            $scope.note = {};
        }
        $scope.reload();
    });
})();