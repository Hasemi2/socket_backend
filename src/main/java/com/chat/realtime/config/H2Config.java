package com.chat.realtime.config;

import com.zaxxer.hikari.HikariDataSource;
//import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class H2Config {

//    @Bean
//    @ConfigurationProperties("spring.datasource.hikari")
//    public DataSource dataSource() throws SQLException {
//        Server server = defaultRun();
//        return new HikariDataSource();
//    }
//
//    private Server defaultRun() throws SQLException {
//        return Server.createTcpServer(
//                "-tcp",
//                "-tcpAllowOthers",
//                "-ifNotExists",
//                "-tcpPort", 9092 + "").start();
//    }
}