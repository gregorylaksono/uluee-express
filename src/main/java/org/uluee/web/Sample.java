package org.uluee.web;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class Sample {
//	public static void main(String args[]) throws Exception {
//        // Create SOAP Connection
//        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
//        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
//
//        // Send SOAP Message to SOAP Server
//        String url = "http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx";
//        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
//
//        // print SOAP Response
//        System.out.print("Response SOAP Message:");
//        soapResponse.writeTo(System.out);
//
//        soapConnection.close();
//    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://ws.cdyne.com/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", serverURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("VerifyEmail", "example");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("email", "example");
        soapBodyElem1.addTextNode("mutantninja@gmail.com");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("LicenseKey", "example");
        soapBodyElem2.addTextNode("123");

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "VerifyEmail");

        soapMessage.saveChanges();

        /* Print the request message */
//        System.out.print("Request SOAP Message:");
        soapMessage.writeTo(System.out);
//        System.out.println();

        return soapMessage;
    }

    
}
