package osgi.simple.webapp;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;

@Component(immediate = true)
public class MemoryTodoRepository implements TodoRepository {

	AtomicLong counter = new AtomicLong();
	Map<Long, Todo> todoMap = new ConcurrentHashMap<>();
	
	@Activate
	public void start() throws Exception {
		File todoXmlFile = new File("C:\\tmp\\todos.xml");
		if(todoXmlFile.exists()) {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(todoXmlFile);
			NodeList todoList = doc.getElementsByTagName("todos");
			for (int temp = 0; temp < todoList.getLength(); temp++) {
				Node todoNode = todoList.item(temp);
				if (todoNode.getNodeType() == Node.ELEMENT_NODE) {
					Element todoElement = (Element) todoNode;
					Todo todo = new Todo();
					todo.id = Long.valueOf(todoElement.getElementsByTagName("id").item(0).getTextContent());
					todo.text = todoElement.getElementsByTagName("text").item(0).getTextContent();
					todo.done = Boolean.valueOf(todoElement.getElementsByTagName("done").item(0).getTextContent());
					insert(todo);
				}
			}
		}
	}
	
	@Deactivate
	public void stop() throws Exception {
		DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = instance.newDocumentBuilder();
		Document doc = documentBuilder.newDocument();
		Element root = doc.createElement("todos");
		doc.appendChild(root);
		for (Map.Entry<Long, Todo> element : todoMap.entrySet() ) {
		    Element todo = doc.createElement("todo");
		    Element todoId = doc.createElement("id");
		    todoId.setTextContent(String.valueOf(element.getValue().id));
		    Element todoText = doc.createElement("text");
		    todoText.setTextContent(element.getValue().text);
		    Element todoDone = doc.createElement("done");
		    todoDone.setTextContent(String.valueOf(element.getValue().done));
		    todo.appendChild(todoId);
		    todo.appendChild(todoText);
		    todo.appendChild(todoDone);
		    root.appendChild(todo);
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("C:\\tmp\\todos.xml"));
		transformer.transform(source, result);
	}

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
