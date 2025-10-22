package com.ivanfrasquet.tema01;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import org.w3c.dom.Element;
import java.io.IOException;

public class Act1 {

    public static Empleats[] parse(String path) throws IOException, ParserConfigurationException, SAXException {
        /* Inicializamos a null el array de países */
        Empleats[] empleados = null;
        try (FileInputStream fis = new FileInputStream(path)) {
            /* Obtenemos el DocumentBuilderFactory necesario para parsear mediante DOM */
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            /* Obtenemos el DocumentBuilder necesario para parsear mediante DOM */
            DocumentBuilder builder = factory.newDocumentBuilder();
            /* Obtenemos el Document parseando mediante DOM */
            Document dom = builder.parse(fis);
            /* Eliminamos nodos de texto redundantes (como saltos de línea o espacios) */
            dom.getDocumentElement().normalize();
            /* Obtenemos la lista de nodos con el tag "country" */
            NodeList items = dom.getElementsByTagName("empleado");
            /* Array de countries con tamaño igual al número de nodos de tipo country */
            empleados = new Empleats[items.getLength()];
            /* Recorremos cada uno de los nodos */
            for (int i = 0; i < items.getLength(); i++) {
                /* Obtenemos el nodo de la posición i */
                Node item = items.item(i);
                /* Obtenemos los atributos necesarios para construir cada objeto Country */

                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) item;

                    String id = item.getAttributes().getNamedItem("id").getNodeValue();

                    String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();

                    double salario = Double.parseDouble(elemento.getElementsByTagName("salario").item(0).getTextContent());

                    String currency = elemento.getElementsByTagName("salario").item(0).getAttributes().getNamedItem("moneda").getNodeValue();

                    /* Creamos el objeto Country en la posición i del array */
                    empleados[i] = new Empleats(id, nombre, salario, currency);
                }
            }
        }

        return empleados;
    }

}

class Empleats {
    private String id;
    private String nombre;
    private double salario;
    private String currency;

    public Empleats(String id, String nombre, double salario, String currency) {
        this.id = id;
        this.nombre = nombre;
        this.salario = salario;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSalario() {
        return salario;
    }

    public String getCurrency() {
        return currency;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return ("ID: " + id + " " + "NOMBRE: " + nombre + " " + "SALARIO: " + Double.toString(salario) + " " + currency + "\n");
    }
}
