package com.sikiedu.smslogin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sikiedu.smslogin.dao.UserMapper;
import com.sikiedu.smslogin.entity.User;
import com.sikiedu.smslogin.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	private UserMapper userMapper;
	
	
	@Override
	public void save(User user) {
		userMapper.save(user);
		
	}

	//在数据库中查询是否有数据
	@Override
	public boolean findUserByPhoneAndPassword(User user) {
		//select * from user where phone = ? and password = ?
		User temp = userMapper.findUserByPhoneAndPassword(user);
		return temp == null?false:true;
	}

	@Override
	public boolean findUserByPhone(String phone) {
		//select * from user where phone = 13477296494
		User temp = userMapper.findUserByPhone(phone);
		//如果temp==null这个是成立的，我们就return true（第一个）。
		//如果不成立，则return false （第二个）
		return temp == null?true:false;
	}

}
