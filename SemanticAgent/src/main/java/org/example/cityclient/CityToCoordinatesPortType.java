package org.example.cityclient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://localhost/CityToCoordinatesService", name = "CityToCoordinatesPortType")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CityToCoordinatesPortType {

    @WebMethod
    @WebResult(name = "GetCoordinatesResponse", targetNamespace = "http://localhost/CityToCoordinatesService", partName = "parameters")
    GetCoordinatesResponse getCoordinates(
            @WebParam(name = "GetCoordinatesRequest", targetNamespace = "http://localhost/CityToCoordinatesService", partName = "parameters")
            GetCoordinatesRequest parameters
    );
}
