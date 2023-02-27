package com.example.myapp.MyFirstWebApp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

//@Controller
@SessionAttributes("name")
public class TodoController {
	
	private TodoService todoService;
	
	
	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}
	
	


	@RequestMapping("todo-list")
	public String ListAllTodos(ModelMap model){
		String username= getLoggedInUserId(model);
		List<Todo> todos= todoService.findByuserName(username);
		model.addAttribute("todos",todos);
		
		return "listTodos";
		
	}




	private String getLoggedInUserId(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id){
		todoService.deleteById(id);
		return "redirect:todo-list";
		
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String ShowNewTodo(ModelMap model){
		String username= getLoggedInUserId(model);
		Todo todo=new Todo(0,username,"",LocalDate.now().plusYears(1),false);
		model.put("todo", todo);
		return "todo";
		
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String AddNewTodo(ModelMap model,@Valid Todo todo, BindingResult result){
		
		if(result.hasErrors()) {
			return "todo";
		}
		String username= getLoggedInUserId(model);
			todoService.addTodo(username, todo.getDescription(), todo.getTargetdate(), false);
		return "redirect:todo-list";	
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.GET)
	public String ShowUpdateTodo(@RequestParam int id,ModelMap model){
		Todo todo= todoService.findById(id);
		model.addAttribute("todo", todo);
		return "todo";
		
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String UpdarteTodo(ModelMap model,@Valid Todo todo, BindingResult result){
		
		if(result.hasErrors()) {
			return "todo";
		}
		String username= getLoggedInUserId(model);
		todo.setUsername(username);
		todoService.updateTodo(todo);
		return "redirect:todo-list";	
	}

}
