package osgi.simple.webapp;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import aQute.service.rest.Options;
import aQute.service.rest.ResourceManager;

@Component
public class TodoResource implements ResourceManager {
	
	interface TodoOptions extends Options {
		Todo _();
	}
	
	TodoRepository todoRepository;
	
	public Iterable<Todo> getTodos(TodoOptions opts){
		return todoRepository.findTodos();
	}
	
	public void postTodos(TodoOptions opts) {
		todoRepository.insert(opts._());
	}
	
	public void deleteTodos(TodoOptions opts, long id) {
		todoRepository.delete(id);
	}

	@Reference
	public void setTodoRepository(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}
	
}
