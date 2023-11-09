package main.components;

import org.w3c.dom.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.*;

public class MyXMLTreeViewer extends JPanel {
    private JTree tree;

    public MyXMLTreeViewer() {
        // Configura el panel para que esté inicialmente vacío
        setLayout(new BorderLayout());
        tree = new JTree(new DefaultMutableTreeNode("XML Document"));
        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setPreferredSize(new Dimension(200, scrollPane.getPreferredSize().height));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTree(Document doc) {
        // Limpia el árbol actual y construye uno nuevo con el Document proporcionado
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("XML Document");

        try {
            // Obtener el elemento raíz del documento XML
            Element rootElement = doc.getDocumentElement();

            // Crear un nodo raíz del árbol basado en el elemento raíz y actualizar el modelo del árbol
            DefaultMutableTreeNode newRootNode = createTreeNode(rootElement);
            rootNode.add(newRootNode);

            // Actualizar el JTree con el nuevo modelo
            tree.setModel(new DefaultTreeModel(rootNode));

        } catch (Exception e) {
            System.out.println("Error al actualizar el árbol: " + e.getMessage());
        }
    }

    private DefaultMutableTreeNode createTreeNode(Element element) {
        // Este método crea un nodo del árbol a partir de un elemento XML
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(element.getTagName());

        // Obtener los atributos del elemento y agregarlos como nodos hijos
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            DefaultMutableTreeNode attributeNode =
                    new DefaultMutableTreeNode(attribute.getNodeName() + "=" + attribute.getNodeValue());
            node.add(attributeNode);
        }

        // Obtener los elementos hijos y agregarlos como nodos hijos recursivamente
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element) {
                DefaultMutableTreeNode childNode = createTreeNode((Element) child);
                node.add(childNode);
            }
        }
        return node;
    }
}
