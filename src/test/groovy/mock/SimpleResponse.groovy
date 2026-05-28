package mock

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = 'simpleResponse', propOrder = ['isOld', 'status'])
class SimpleResponse {

    Boolean isOld
    String status
}
