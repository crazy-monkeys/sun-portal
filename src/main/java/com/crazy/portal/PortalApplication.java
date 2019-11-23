package com.crazy.portal;

import com.crazy.portal.util.I18NUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@SpringBootApplication(exclude = GsonAutoConfiguration.class)
public class PortalApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}

	@Resource
	private MessageSource messageSource;

	@PostConstruct
	public void onStartup(){
		log.info("========================================【AppContext Start】");
		I18NUtils.messageSource = messageSource;
		log.info("========================================【AppContext End】");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PortalApplication.class);
	}

}

