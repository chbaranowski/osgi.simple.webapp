var todoApp = angular.module('todoApp', ['ngResource']);

todoApp.factory('Todos', ['$resource', function($resource) {
	return $resource( '/rest/todos/:todoId', { todoId: '@todoId' } );
}]);

todoApp.controller('TodoController',['$scope','Todos', function($scope, Todos) {
	
	  $scope.todos = Todos.query();
		
	  $scope.addTodo = function() {
		Todos.save({}, { "text":$scope.todoText, "done":false });
		$scope.todos = Todos.query();
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
	    var todos = $scope.todos;
	    $scope.todos = [];
	    angular.forEach(todos, function(todo) {
	      if (todo.done) {
	    	  Todos.delete({todoId: todo.id});
	      }
	    });
	    $scope.todos = Todos.query();
	  };
}]);
