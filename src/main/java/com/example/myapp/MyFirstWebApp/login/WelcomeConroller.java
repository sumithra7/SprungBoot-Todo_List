package com.example.myapp.MyFirstWebApp.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("name")
public class WelcomeConroller {
	 
@RequestMapping(value="/",method=RequestMethod.GET)
	
	public String WelcomeJSP(ModelMap model) {
	model.put("name", getLoggedInUserId());
		return "welcome";
	}

private String getLoggedInUserId() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	return authentication.getName();
}
 


}
