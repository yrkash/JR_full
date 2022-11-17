package com.javarush.task.task33.task3309_DOM;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/*
Комментарий внутри xml
*/

public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment)  {

        try {
            StringWriter writer = new StringWriter();
            convertToXml(writer, obj);
            Document document = getDocument(writer.toString());

            //Non-recursive traversing of Nodes for finding tagname
            NodeList nodeList = document.getElementsByTagName(tagName);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    node.getParentNode().insertBefore(document.createComment(comment),node);
                }
            }
            //Non-recursive traversing of Nodes for finding escape characters in textContent
            NodeList nodeListFull = document.getElementsByTagName("*");
            for (int i = 0; i < nodeListFull.getLength(); i++) {
                Node node = nodeListFull.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (node.getTextContent().matches(".*[<>&'\"].*")) {
                        String textContent = node.getTextContent();
                        node.setTextContent("");
                        node.getParentNode().appendChild(document.createCDATASection(textContent));
                    }
                }
            }
            return toString(document);
        } catch (Exception ignored) {
            ignored.toString();
        }
        return null;
    }

    //Serialization class
    public static void convertToXml(StringWriter writer, Object obj) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, writer);
    }


    // Obtain Document from xml
    private static Document getDocument(String xml) throws Exception {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setCoalescing(true);
        builderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        return documentBuilder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    // Obtain corrected xml from Document with TransformerFactory
    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }


    public static void main(String[] args) throws Exception {
        System.out.println(Solution.toXmlWithComment(new First(), "second", "it's a comment"));
        System.out.println("________________________________________________________");
        /*
        First first = new First();
        StringWriter writer = new StringWriter();
        convertToXml(writer, first);
        System.out.println(writer);
        */

    }

}
