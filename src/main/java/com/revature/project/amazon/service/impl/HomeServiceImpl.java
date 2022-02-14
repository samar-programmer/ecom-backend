package com.revature.project.amazon.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project.amazon.model.User;
import com.revature.project.amazon.repository.HomeRepository;
import com.revature.project.amazon.service.HomeService;

import org.springframework.stereotype.Service;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

@Service
@Transactional
public class HomeServiceImpl implements HomeService{
	
	@Autowired
	HomeRepository homeRepository;

	@Override
	public void signupUser(User user) {
		homeRepository.saveAndFlush(user);
	}

	@Override
	public User verifyUser(String email, String password) {
		User user = homeRepository.findByEmail(email);
		return user;
	}

	@Override
	public boolean checkUserExists(String email) {
		boolean  userExists = false;
		User user = homeRepository.findByEmail(email);
		if(null != user) {
			userExists = true;
		}
		return userExists;
	}

	@Override
	public boolean sendEmail(String subject, String message, String to) {
		boolean f=false;
		
		String from="samarsamar1023@gmail.com";
		
		String host="smtp.gmail.com";
		
		Properties properties=System.getProperties();
		
		
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		Session session=Session.getInstance(properties, new Authenticator()
				{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication("samarsamar1023@gmail.com","xfwysblsmxiqjljk");
			}
				});
		
		session.setDebug(true);
		
		MimeMessage mimemessage=new MimeMessage(session);
		
		try
		{
			mimemessage.setFrom(from);
			mimemessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimemessage.setSubject(subject);
			mimemessage.setText(message);
			Transport.send(mimemessage);
			
			System.out.println("Sent Success.............");
			
			f=true;
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return f;
	}

	@Override
	public int updateUserByOtp(String to, long otp) {
		 homeRepository.updateUserByOtp(to, otp);
				
		 return	1;
	}

	@Override
	public User fetchByOtp(long tempOtp) {
		return homeRepository.findByOtp(tempOtp);
	}

	@Override
	public boolean sendPassword(String subject, String tempPassword, String tempEmail) {
		boolean f=false;
		
		String from="samarsamar1023@gmail.com";
		
		String host="smtp.gmail.com";
		
		Properties properties=System.getProperties();
		
		
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		Session session=Session.getInstance(properties, new Authenticator()
				{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication("samarsamar1023@gmail.com","xfwysblsmxiqjljk");
			}
				});
		
		session.setDebug(true);
		
		MimeMessage mimemessage=new MimeMessage(session);
		
		try
		{
			mimemessage.setFrom(from);
			mimemessage.addRecipient(Message.RecipientType.TO, new InternetAddress(tempEmail));
			mimemessage.setSubject(subject);
			mimemessage.setText("Your PassWord is  "+tempPassword);
			Transport.send(mimemessage);
			
			System.out.println("Sent Success.............");
			
			f=true;
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return f;
	}

	@Override
	public User getProfile(String email) {
		return homeRepository.findByEmail(email);
	}

	

}
