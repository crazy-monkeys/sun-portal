package com.crazy.portal;

import com.crazy.portal.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@SpringBootApplication(exclude = GsonAutoConfiguration.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class PortalApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}

	@Resource
	private MessageSource messageSource;

	@PostConstruct
	public void onStartup(){
		log.info("========================================【AppContext Start】");
		MessageUtils.messageSource = messageSource;
		log.info("========================================【AppContext End】");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PortalApplication.class);
	}

}

