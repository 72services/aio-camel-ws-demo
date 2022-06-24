package ch.so.aio.camel.ws.demo.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.stereotype.Component;

@Component
public class ContactBookRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("timer://contactBookTimer?fixedRate=true&period={{timer.period}}")
                .id("contact-book-timer")
                .to("direct:start");

        from("direct:start")
                .id("contact-book-ws-call")
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
                .to("""
                        cxf://https://contactbook-uudvvca52q-oa.a.run.app/contactbook\
                        ?serviceClass=org.example.contactbook.ContactBook\
                        &wsdlURL=https://contactbook-uudvvca52q-oa.a.run.app/contactbook?wsdl\
                        &dataFormat=RAW""")
                .to("direct:in");

        from("direct:in")
                .id("contact-book-transformation")
                .to("xslt:xslt/contactbook_to_html.xslt")
                .to("direct:out");

        from("direct:out")
                .id("contact-book-output-file")
                .to("file:{{output.directory}}?fileName=contactbook_${date:now:yyyyMMdd_HHmmss}.html");
    }
}
