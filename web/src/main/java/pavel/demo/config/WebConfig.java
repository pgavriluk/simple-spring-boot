package pavel.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import pavel.demo.interceptor.RequestInterceptor;
import pavel.demo.util.ViewNames;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	// bean methods
	@Bean
	public LocaleResolver localeResolver(){
		return new SessionLocaleResolver();
	}


	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName(ViewNames.HOME);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor((new RequestInterceptor()));

		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang"); // default locale
		registry.addInterceptor(localeChangeInterceptor);
	}
}
