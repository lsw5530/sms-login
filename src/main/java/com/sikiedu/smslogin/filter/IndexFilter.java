package com.sikiedu.smslogin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IndexFilter implements Filter{

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private HttpSession session;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		
		//System.out.println("拦截！！"+httpServletRequest.getServletPath());
		if(httpServletRequest.getServletPath().equals("/index"))
		{
			//如果登录了，直接放行
			if(session.getAttribute("user") != null)
			{
				chain.doFilter(request, response);				
			}
			//如果没有登录，我们就不让访问首页
			else
			{
				//跳转到login
				request.getRequestDispatcher("/login").forward(request, response);
			}
			System.out.println("首页");
		}
		//处理其他请求
		else
		{
			//放行
			chain.doFilter(request, response);	
			
		}
		
	}

}
