package com.cos.instagram.config;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.auth.dto.LoginUser;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor

public class WebMvcConfig implements WebMvcConfigurer {

	private final HttpSession httpSession;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new HandlerMethodArgumentResolver() {

			// 1. supportsParameter() 에서 true가 리턴되면!
			@Override
			public boolean supportsParameter(MethodParameter parameter) {
				boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUserAnnotation.class) != null;
				boolean isUserClass = LoginUser.class.equals(parameter.getParameterType());
				return isLoginUserAnnotation && isUserClass;
			}

			// 2. 세션을 만들어서 @LoginUserAnnotation에 주입한다.
			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				return httpSession.getAttribute("loginUser");
			}
		});

		// TEST 
		resolvers.add(new HandlerMethodArgumentResolver() {
			
			@Override
			public boolean supportsParameter(MethodParameter parameter) {
				return true;
			}
			
			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				return "cos";
			}
		});
	}
	
	

}
