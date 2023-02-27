package com.example.myapp.MyFirstWebApp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import jakarta.validation.Valid;


@Service
public class TodoService {
	
	private static int todoCount=0;
	
	private static List<Todo> todos=new ArrayList<>();
	static {
		todos.add(new Todo(++todoCount,"in28Minutes","Learn AWS", LocalDate.now().plusYears(1),false));
		todos.add(new Todo(++todoCount,"in28minutes","Learn DevOps",LocalDate.now(),false));
		todos.add(new Todo(++todoCount,"in28minutes","Learn SpringBoot",LocalDate.now(),false));
	}
	
	public void addTodo(String username, String description, LocalDate targetdate, boolean done) {
		Todo todo= new Todo(++todoCount,username,description,targetdate,done);
		todos.add(todo);
		
	}
	public List<Todo> findByuserName(String username){
		Predicate<? super Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
	}
	public void deleteById(int id) {
		
		Predicate<? super Todo> predicate = todo -> todo.getId() ==  id;
		todos.removeIf(predicate);
	}
	public Todo findById(int id) {
		// TODO Auto-generated method stub
		Predicate<? super Todo> predicate = todo -> todo.getId() ==  id;
		Todo todo=todos.stream().filter(predicate).findFirst().get();
		return todo;
	}
	public void updateTodo(@Valid Todo todo) {
		// TODO Auto-generated method stub
		deleteById(todo.getId());
		
		todos.add(todo);
		
		
	}
	
	
}
