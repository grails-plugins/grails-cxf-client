package mock

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = 'simpleRequest', propOrder = ['name', 'age'])
class SimpleRequest {

    String name
    Integer age
}
