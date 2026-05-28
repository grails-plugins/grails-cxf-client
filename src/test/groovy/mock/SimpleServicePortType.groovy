package mock

import jakarta.jws.WebMethod
import jakarta.jws.WebParam
import jakarta.jws.WebResult
import jakarta.jws.WebService
import jakarta.xml.ws.RequestWrapper
import jakarta.xml.ws.ResponseWrapper

@WebService(targetNamespace = 'http://mock.client.cxf/', name = 'SimpleServicePortType')
interface SimpleServicePortType {

    @WebResult(name = 'return', targetNamespace = '')
    @RequestWrapper(localName = 'simpleMethod2', targetNamespace = 'http://mock.client.cxf/', className = 'test.mock.SimpleMethod2')
    @WebMethod
    @ResponseWrapper(localName = 'simpleMethod2Response', targetNamespace = 'http://mock.client.cxf/', className = 'test.mock.SimpleMethod2Response')
    SimpleResponse simpleMethod2(
            @WebParam(name = 'request', targetNamespace = '')
            SimpleRequest request
    )

    @WebResult(name = 'return', targetNamespace = '')
    @RequestWrapper(localName = 'simpleMethod1', targetNamespace = 'http://mock.client.cxf/', className = 'test.mock.SimpleMethod1')
    @WebMethod
    @ResponseWrapper(localName = 'simpleMethod1Response', targetNamespace = 'http://mock.client.cxf/', className = 'test.mock.SimpleMethod1Response')
    SimpleResponse simpleMethod1(
            @WebParam(name = 'request', targetNamespace = '')
            SimpleRequest request
    )
}
