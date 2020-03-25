package com.sikiedu.smslogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	//接收并处理index请求
	@RequestMapping("/index")
	public String index()
	{
		return "index.html";
	}
	
	//显示login页面
	@RequestMapping("/login")
	public String login()
	{
		return "login.html";
	}
	
	//显示register页面
	@RequestMapping("/register")
	public String register()
	{
		return "register.html";
	}
	
	
	
	
	
	
	
	
	
	
	
}
