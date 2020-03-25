package com.sikiedu.smslogin.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.sikiedu.smslogin.entity.User;
import com.sikiedu.smslogin.service.UserService;

@Controller
public class UserController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserService userService;
	
	
	//用户登录
	@RequestMapping("/userLogin")
	public String userLogin(User user,Model model)
	{
		System.out.println(user);
		//select * from user where phone = ? and password = ?
		boolean success = userService.findUserByPhoneAndPassword(user);
		//登录成功，在数据库中找到了对应的数据
		if(success)
		{
			session.setAttribute("user", user);
			//返回首页
			return "redirect:/index";			
		}
		//登录失败
		else
		{
			//给一点错误信息
			model.addAttribute("error", "用户名或者密码错误！！");
			return "login.html";
		}
		
	}
	
	
	//用户注册
	@RequestMapping("/userRegister")
	public String userResigter(User user)
	{
		System.out.println(user);
		
		userService.save(user);
		
		
		return "redirect:/login";
	}
	
	
	
	@RequestMapping("/sms")
	@ResponseBody
	public String smsCode(String phone)
	{
		System.out.println(phone);
		
		//保护机制，如果用户已经注册过了，我们就不用给他发短信了
		//通过phone查找用户，看其是否注册过，如果注册过了，我们就不用给他发短信
		//如果没有注册，我们才需要给他发短信
		//select * from user where phone = ? 13477296494
		//表示该用户存不存在
		boolean success = userService.findUserByPhone(phone);
		String json = null;
		//该用户没有注册
		if(success)
		{
			//发送短信
			String sms = SMS(phone);
			
			json = "{\"message\":"+true+",\"sms\":\""+sms+"\"}";	
		}
		//用户以及注册过了
		else
		{
			json = "{\"message\":"+false+"}";
		}
		
		return json;
	}
	
	
	
	
	//发送短信
	private String SMS(String phone)
	{
		//手机
		String phoneNumber = phone;
		//短信的内容
		int templateId = 200461;
		//参数
		String[] params = new String[1];
		//验证码
		String code = "";
		Random r = new Random();
		for(int i = 0;i<4;i++)
		{
			code += r.nextInt(10);
		}
		//将Code放入Session中
		session.setAttribute("sms", code);
		params[0] = code;
		System.out.println(code);
		//签名
		String sign = "tLain公众号";
		
		
		//【tLain公众号】该项目的验证码为：1234，如非本人操作，请忽略本短信。

		
		//拿到发送短信的核心类
		SmsSingleSender ssender = new SmsSingleSender(1400142856, "21306d751cfdf61ed433e506da242522");
		//发送短信
		try {
			SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber, templateId, params, sign, "", "");
			System.out.println(result);
		} catch (JSONException | HTTPException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return code;
	}
	
	
	
	
}
