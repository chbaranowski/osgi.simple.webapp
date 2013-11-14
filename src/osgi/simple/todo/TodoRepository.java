package osgi.simple.todo;

import aQute.bnd.annotation.ProviderType;

@ProviderType
public interface TodoRepository {
	void insert(Todo todo);
	void delete(long id);
	Iterable<Todo> findTodos();
	void update(Todo todo);
}
