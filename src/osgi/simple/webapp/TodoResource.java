package osgi.simple.webapp;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import aQute.service.rest.Options;
import aQute.service.rest.ResourceManager;

@Component
public class TodoResource implements ResourceManager {
	
	interface TodoOptions extends Options {
	}
	
	TodoRepository todoRepository;

	@Reference
	public void setTodoRepository(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}
	
}
