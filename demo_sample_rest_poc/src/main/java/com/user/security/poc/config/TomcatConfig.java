package com.user.security.poc.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class TomcatConfig {

    @Bean
    public ServletWebServerFactory servletContainer(@Value("${server.port.additional:#{null}}") String additionalPort) {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        Connector additionalConnector = tryCreateAdditionalConnector(additionalPort);
        if (additionalConnector != null) {
            tomcat.addAdditionalTomcatConnectors(additionalConnector);
        }
        return tomcat;
    }

    private Connector tryCreateAdditionalConnector(String additionalPort) {
        if (!StringUtils.isEmpty(additionalPort)) {
            return null;
        }
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(Integer.parseInt(additionalPort));
        return connector;
    }
}
