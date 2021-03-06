package com.spring.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.beans.Login;
import com.spring.beans.User;
import com.spring.service.UserService;

@Controller
public class LoginController 
{

	@Autowired
	public UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request,HttpServletResponse response) 
	{
		ModelAndView mav=new ModelAndView("login");
		mav.addObject("login", new Login());
		return mav;
	}
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	  public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
	  @ModelAttribute("login") Login login)
	  {
		 ModelAndView mav = null;
		 User user = userService.validateUser(login);
		 
		  if(null!=user)
		  {
			  mav = new ModelAndView("welcome");
			    mav.addObject("firstname", user.getFirstname());
		  }
		  else
		  {
			  mav = new ModelAndView("login");
			  mav.addObject("message", "Enter a Valid Username or Password");
		  }
		  return mav;
	  }
}
