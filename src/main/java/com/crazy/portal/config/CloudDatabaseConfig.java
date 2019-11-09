package com.crazy.portal.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:30 2019-11-08
 * @Modified by:
 */
@Configuration
@Profile("sit")
@Slf4j
public class CloudDatabaseConfig {

    @Bean
    public DataSource dataSource(@Value("${hana.url}") final String url,
                                 @Value("${hana.user}")final String user,
                                 @Value("${hana.password}")final String password) {


        log.info("initial database "+ url);
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName(com.sap.db.jdbc.Driver.class.getName())
                .url(url)
                .username(user)
                .password(password)
                .build();

    }

}
