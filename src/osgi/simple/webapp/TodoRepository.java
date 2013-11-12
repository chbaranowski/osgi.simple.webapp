package osgi.simple.webapp;

public interface TodoRepository {
	void insert(Todo todo);
	void delete(long id);
	Iterable<Todo> findTodos();
}
