var todoApp = angular.module('todoApp', ['ngResource']);

todoApp.factory('todoResource', ['$resource', function($resource) {
	return $resource( '/rest/todos/:todoId', { todoId: '@todoId' }, {
		archive: {
			url: '/rest/archive',
			method: 'DELETE',
			params: {todoIds: '@todoIds'}
		}
	});
}]);

todoApp.controller('TodoController',['$scope','todoResource', function($scope, todoResource) {
	
	  $scope.todos = todoResource.query();
		
	  $scope.addTodo = function() {
		todoResource.save({}, { "text":$scope.todoText, "done":false }).$promise.then(function() {
			$scope.todos = todoResource.query()
		})
		$scope.todoText = '';
	  };
	 
	  $scope.remaining = function() {
	    var count = 0;
	    angular.forEach($scope.todos, function(todo) {
	      count += todo.done ? false : true;
	    });
	    return count;
	  };
	 
	  $scope.archive = function() {
	    var todoIds = [];
	    var todos = $scope.todos;
	    angular.forEach(todos, function(todo) {
	      if (todo.done) {
	    	todoIds.push(todo.id);
	      }
	    });
	    todoResource.archive({todoIds: todoIds}).$promise.then(function() {
	        $scope.todos = todoResource.query();
	    });
	  };
}]);
