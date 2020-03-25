package com.sikiedu.smslogin.service;

import com.sikiedu.smslogin.entity.User;

public interface UserService {

	void save(User user);

	boolean findUserByPhoneAndPassword(User user);

	boolean findUserByPhone(String phone);

}
