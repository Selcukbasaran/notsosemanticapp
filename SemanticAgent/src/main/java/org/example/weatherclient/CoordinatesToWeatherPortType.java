package org.example.weatherclient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://localhost/CoordinatesToWeatherService", name = "CoordinatesToWeatherPortType")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CoordinatesToWeatherPortType {

    @WebMethod
    @WebResult(name = "GetWeatherResponse", targetNamespace = "http://localhost/CoordinatesToWeatherService", partName = "parameters")
    GetWeatherResponse getWeather(
            @WebParam(name = "GetWeatherRequest", targetNamespace = "http://localhost/CoordinatesToWeatherService", partName = "parameters")
            GetWeatherRequest parameters
    );
}
