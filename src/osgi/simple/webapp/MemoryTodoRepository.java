package osgi.simple.webapp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import aQute.bnd.annotation.component.Component;

@Component(immediate = true)
public class MemoryTodoRepository implements TodoRepository {

	AtomicLong counter = new AtomicLong();
	Map<Long, Todo> todoMap = new ConcurrentHashMap<>();

	public void insert(Todo todo) {
		todo.id = counter.incrementAndGet();
		todoMap.put(todo.id, todo);
	}

	public void delete(long id) {
		todoMap.remove(id);
	}

	public Iterable<Todo> findTodos() {
		return todoMap.values();
	}

}
