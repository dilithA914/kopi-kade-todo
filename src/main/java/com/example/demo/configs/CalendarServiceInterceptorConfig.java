package com.example.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptors.CalendarServiceInterceptor;

@Component
public class CalendarServiceInterceptorConfig implements WebMvcConfigurer {

	@Autowired
	CalendarServiceInterceptor calendarServiceInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(calendarServiceInterceptor);
	}

}
