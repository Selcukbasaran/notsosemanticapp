package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class CoordinatesToWeatherServiceConfig extends WsConfigurerAdapter {

    @Bean(name = "coordinatesToWeather")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coordinatesToWeatherSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CoordinatesToWeatherPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.example.org/CoordinatesToWeatherService");
        wsdl11Definition.setSchema(coordinatesToWeatherSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema coordinatesToWeatherSchema() {
        return new SimpleXsdSchema(new org.springframework.core.io.ClassPathResource("wsdl/CoordinatesToWeatherService.xsd"));
    }
}
