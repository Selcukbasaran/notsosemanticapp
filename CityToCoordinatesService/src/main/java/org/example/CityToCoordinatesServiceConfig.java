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
public class CityToCoordinatesServiceConfig extends WsConfigurerAdapter {

    @Bean(name = "cityToCoordinates")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema cityToCoordinatesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CityToCoordinatesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://localhost/CityToCoordinatesService");
        wsdl11Definition.setSchema(cityToCoordinatesSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema cityToCoordinatesSchema() {
        return new SimpleXsdSchema(new org.springframework.core.io.ClassPathResource("wsdl/CityToCoordinatesService.xsd"));
    }
}
