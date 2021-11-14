package com.baemin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baemin.Interceptor.OrderInterceptor;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${resource.path}")
	private String resourcePath;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler(uploadPath)
				.addResourceLocations(resourcePath);
	}
	
	
	//Lucy Xss filter 적용	
    @Bean
    public FilterRegistrationBean<XssEscapeServletFilter> getFilterRegistrationBean(){
        FilterRegistrationBean<XssEscapeServletFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new XssEscapeServletFilter());
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
    
    
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new OrderInterceptor())
		.addPathPatterns("/order");
	}
    
//    @Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new AdminInterceptor())
//		.addPathPatterns("/admin/**")
//		;
//	}
    

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new AddressInterceptor())
//		.addPathPatterns("/store/**")
//		;
//	}
    
    
    
    
    

}
