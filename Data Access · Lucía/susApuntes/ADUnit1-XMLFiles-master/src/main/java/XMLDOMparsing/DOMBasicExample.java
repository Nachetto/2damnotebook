/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLDOMparsing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import config.Configuration;
import model.Client;
import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author Lucia
 */
public class DOMBasicExample {

    /**
     * This example reads the information from an XML file and then adds a new element to the file
     */
    
    
    public static void main(String[] args) throws Exception {
       
        List<Client> listCli = new ArrayList<>();

         //Get the DOM Builder Factory
        DocumentBuilderFactory factory = 
            DocumentBuilderFactory.newInstance();

        //Get the DOM Builder
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Load and Parse the XML document
        //document contains the complete XML as a Tree.
        Document document = builder.parse(Files.newInputStream(Paths
                                            .get(Configuration.getInstance().getProperty("xmlClientPath"))));

        // Shows the number of clients in the document. 
        int nrClients= document.getElementsByTagName("client").getLength();
                            System.out.println("number of clients: " + nrClients);

     

        for (int i=0; i<nrClients; i++) {
            Client cli = new Client();
            cli.setFirstName(document.getElementsByTagName("firstName").item(i).getFirstChild().getNodeValue());
            cli.setLastName(document.getElementsByTagName("lastName").item(i).getFirstChild().getNodeValue());
            listCli.add(cli);
        }

     //Printing the clients list populated.
        for (Client e : listCli) {
          System.out.println(e);
        }
        
       // Adding a new client
       
       Client newCli = new Client("Mary","Johnson");
       Element root = document.getDocumentElement();

        //Client element
	Element itemNode = document.createElement("client");
	
        // FirstName Node
	Element fNameNode = document.createElement("firstName");
	Text nodeFNameValue = document.createTextNode(newCli.getFirstName());
	fNameNode.appendChild(nodeFNameValue);
        
        itemNode.appendChild(fNameNode);
	// append itemNode to root
	root.appendChild(itemNode);
        
        //Writing to the file
        // Generates the DOM document and saves it into an XML file
	Source source = new DOMSource(document);
			
	Result result = new StreamResult(Files.newOutputStream(Paths
            .get(Configuration.getInstance().getProperty("xmlClientPath")))); // name of the file
																				
	Transformer transformer = TransformerFactory.newInstance().newTransformer();
	transformer.transform(source, result);
    }
    
}
    
