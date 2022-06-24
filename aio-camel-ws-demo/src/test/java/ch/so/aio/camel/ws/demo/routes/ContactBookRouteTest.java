package ch.so.aio.camel.ws.demo.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
@MockEndpoints("direct:out")
class ContactBookRouteTest {

    @Autowired
    private ProducerTemplate template;

    @EndpointInject("mock:direct:out")
    private MockEndpoint mock;

    @Test
    void search_alice() throws InterruptedException {
        mock.expectedBodiesReceived("""
                <html xmlns:ns2="http://example.org/contactbook" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                    <body>
                        <h2>Contact Book</h2>
                        <table border="1">
                            <tr bgcolor="#9acd32">
                                <th>Email</th><th>ID</th><th>Name</th><th>Phone</th>
                            </tr>
                            <tr>
                                <td>alice@example.org</td><td>1</td><td>Alice Smith</td><td>+1 123-456-7890</td>
                            </tr>
                        </table>
                    </body>
                </html>
                """.replace("\n", "\r\n"));

        template.sendBody("direct:in", """
                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                    <soap:Body>
                        <ns2:SearchContactsResponse xmlns:ns2="http://example.org/contactbook">
                            <contacts>
                                <email>alice@example.org</email>
                                <id>1</id>
                                <name>Alice Smith</name>
                                <phone>+1 123-456-7890</phone>
                            </contacts>
                        </ns2:SearchContactsResponse>
                    </soap:Body>
                </soap:Envelope>""");

        mock.assertIsSatisfied();
    }

}
