package br.com.webflux.starwarsapiclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app-config.client.swapi")
public class ConfigProperties {

    private String people;
    private String planets;
    private String films;
    private String species;
    private String vehicles;
    private String starships;
}
