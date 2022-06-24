<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
                xmlns:ns2="http://example.org/contactbook">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Contact Book</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Email</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Phone</th>
                    </tr>
                    <xsl:for-each select="soap:Envelope/soap:Body/ns2:SearchContactsResponse/contacts">
                        <tr>
                            <td>
                                <xsl:value-of select="email"/>
                            </td>
                            <td>
                                <xsl:value-of select="id"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                            <td>
                                <xsl:value-of select="phone"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
