package org.grails.cxf.client

import grails.core.GrailsApplication
import org.apache.grails.core.cli.ApplicationCommand
import org.apache.grails.core.cli.ExecutionContext
import org.grails.cxf.client.util.WsdlToJavaUtil
import org.springframework.beans.factory.annotation.Autowired

class WsdlToJavaCommand implements ApplicationCommand {

    @Autowired
    GrailsApplication grailsApplication

    boolean handle(ExecutionContext ctx) {
        List<Map> wsdls = [[:]]
        grailsApplication.config.cxf?.client?.each {
            wsdls << [wsdl: it?.value?.wsdl, wsdlArgs: it?.value?.wsdlArgs, namespace: it?.value?.namespace, client: it?.value?.client ?: false, bindingFile: it?.value?.bindingFile, outputDir: it?.value?.outputDir ?: 'src/main/java']
        }

        wsdls.each { endpoint ->
            WsdlToJavaUtil.runWsdlToJava(endpoint)
            return true
        }
    }
}
