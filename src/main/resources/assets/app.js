(function() {
    var app = angular.module('myApp', []);
    var notebookBase = "/api";
    app.factory("Post", function($resource) {
	return $resource(notebookBase);
    });
    /**
	 * $scope.notes = array of notes (without text) shown in the table
	 * $scope.note = one note (with text) shown in the form (can be edited)
	 */
    app.controller('NotebookController', function($scope, $http) {
        $scope.notes = {};
    	$scope.reload = function() {
            $http.get(notebookBase)
            .success(function (response) {
            	$scope.notes = response;
        	});
         };
    
         // creates a new note
         $scope.create = function() {
        	var toSend = $scope.note;
            $http.post(notebookBase, toSend)
            .success(function (response) {$scope.reload();});
            this.note = {};
        };

        // deletes a note
        $scope.delete = function(id) {
            $http.delete(notebookBase + "/"+ id)
            .success(function (response) {$scope.reload();});
            $scope.note = {};
        }

        // updates a note
        $scope.update = function(id) {
        	var toSend = $scope.note;
            $http.put(notebookBase + "/"+ id, toSend)
            .success(function (response) {$scope.reload();});
            $scope.note = {};
        }

        // selects a note for editing
        $scope.select = function(id) {
            $scope.note = $scope.notes[id];
        }

        // selects a note for editing
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
