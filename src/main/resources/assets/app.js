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
            	for(var i = 0, size = response.notes.length; i < size ; i++){
            		var note = response.notes[i];
            		$scope.notes[note.id] = note;
            	}
        	});
         };
    
         $scope.create = function() {
        	var toSend = $scope.note;
            $http.post(notebookBase, toSend)
            .success(function (response) {$scope.reload();});
            this.note = {};
        };

        // not working!
        $scope.delete = function(id) {
            $http.delete(notebookBase + "/"+ id)
            .success(function (response) {$scope.reload();});
            $scope.note = {};
        }

        $scope.select = function(id) {
            $scope.note = $scope.notes[id];
        }

        $scope.newNote = function() {
            $scope.note = {};
        }
        $scope.reload();
    });
})();