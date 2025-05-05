package se.yrgo.schedule;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.StringWriter;
import java.util.List;

public class XmlFormatter implements Formatter {
    @Override
    public String format(List<Assignment> assignments) {
        if (assignments.isEmpty()) {
            return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<schedules></schedules>";
        }

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("schedules");
            doc.appendChild(rootElement);

            for (Assignment assignment : assignments) {
                Element scheduleElement = doc.createElement("schedule");
                scheduleElement.setAttribute("date", assignment.date());

                Element schoolElement = doc.createElement("school");

                Element schoolName = doc.createElement("school_name");
                schoolName.appendChild(doc.createTextNode(assignment.school().name()));
                schoolElement.appendChild(schoolName);

                Element address = doc.createElement("address");
                address.appendChild(doc.createTextNode(assignment.school().address()));
                schoolElement.appendChild(address);

                scheduleElement.appendChild(schoolElement);

                Element substituteElement = doc.createElement("substitute");

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(assignment.teacher().name()));
                substituteElement.appendChild(name);

                scheduleElement.appendChild(substituteElement);
                rootElement.appendChild(scheduleElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.toString();
        } catch (ParserConfigurationException | TransformerException e) {
            return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<error>Failed to generate XML</error>";
        }
    }
}