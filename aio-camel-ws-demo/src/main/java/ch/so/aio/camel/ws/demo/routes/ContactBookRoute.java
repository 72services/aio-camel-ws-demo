package ch.so.aio.camel.ws.demo.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.stereotype.Component;

@Component
public class ContactBookRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("timer://contactBookTimer?repeatCount=1")
                .setHeader(CxfConstants.OPERATION_NAME, constant("SearchContacts"))
                .setBody(constant("""
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://example.org/contactbook">
                            <soapenv:Header/>
                            <soapenv:Body>
                                <con:SearchContacts>
                                    <name>Alice</name>
                                </con:SearchContacts>
                            </soapenv:Body>
                        </soapenv:Envelope>
                        """))
                .log("Request: ${body}")
                .to("cxf://https://contactbook-uudvvca52q-oa.a.run.app/contactbook" +
                        "?serviceClass=org.example.contactbook.ContactBook" +
                        "&wsdlURL=https://contactbook-uudvvca52q-oa.a.run.app/contactbook?wsdl" +
                        "&dataFormat=RAW")
                .log("Response: ${body}")
                .to("xslt:xslt/contactbook_to_html.xslt")
                .to("file:c:\\Users\\simon\\Downloads?fileName=contactbook.html");
    }
}
