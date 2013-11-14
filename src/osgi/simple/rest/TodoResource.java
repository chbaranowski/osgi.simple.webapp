package osgi.simple.rest;

import osgi.simple.todo.Todo;
import osgi.simple.todo.TodoRepository;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import aQute.service.rest.Options;
import aQute.service.rest.ResourceManager;

@Component
public class TodoResource implements ResourceManager {
	
	interface TodoOptions extends Options {
		Long[] todoIds();
		Todo _();
	}
	
	TodoRepository todoRepository;
	
	public Iterable<Todo> getTodos(TodoOptions opts){
		return todoRepository.findTodos();
	}
	
	public void postTodos(TodoOptions opts) {
		Todo todo = opts._();
		if(todo.id == null) {
			todoRepository.insert(todo);
		} else {
			todoRepository.update(todo);
		}
	}
	
	public void deleteTodos(TodoOptions opts, long id) {
		todoRepository.delete(id);
	}
	
	public void deleteArchive(TodoOptions opts) {
		Long[] todoIds = opts.todoIds();
		for (Long todoId : todoIds) {
			todoRepository.delete(todoId);
		}
	}

	@Reference
	public void setTodoRepository(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}
	
}
