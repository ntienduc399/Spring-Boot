package com.tiennguyen.learning_spring_boot.clientproxy;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ClienProxyConfig {
    @Value("{users.api.url.v1}")
    private String usersEndpointUrl;
    @Bean
    public UserResourceV1 getUserResourceV1() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newBuilder().build();
        ResteasyWebTarget target = client.target(usersEndpointUrl);
        UserResourceV1 proxy = target.proxy(UserResourceV1.class);
        return proxy;
    }
}

