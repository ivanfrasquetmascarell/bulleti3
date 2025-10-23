package com.ivanfrasquet.tema01;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Act3 {
    private List<Libro> libros = new ArrayList<>();

    public Act3(String ruta) {
        try {
            File xmlFile = new File(ruta);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList lista = doc.getElementsByTagName("libro");

            for (int i = 0; i < lista.getLength(); i++) {
                Node nodo = lista.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nodo;

                    String isbn = elem.getAttribute("isbn");
                    String titulo = elem.getElementsByTagName("titulo").item(0).getTextContent();

                    NodeList generosNodos = ((Element) elem.getElementsByTagName("generos").item(0))
                            .getElementsByTagName("genero");

                    List<String> generos = new ArrayList<>();
                    for (int j = 0; j < generosNodos.getLength(); j++) {
                        generos.add(generosNodos.item(j).getTextContent());
                    }

                    libros.add(new Libro(isbn, titulo, generos));
                }
            }

        } catch (Exception e) {
            System.err.println("Error al leer el XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * devuelve true o false si hay o no libros y muestra por pantalla los titulos de estos
     * @return
     */
    public Boolean mostrarTitulos() {
        if (libros.isEmpty()) {
            return false;
        }

        System.out.println("Títulos de los libros:");
        for (Libro libro : libros) {
            System.out.println("- " + libro.getTitulo());
        }
        return true;
    }

    /**
     * devuelve true o false ademas de la cantidad de libros que hay por genero
     * @return
     */
    public boolean mostrarConteoPorGenero() {
        if (libros.isEmpty()) {
            return false;
        }

        Map<String, Long> conteo = libros.stream()
                .flatMap(libro -> libro.getGeneros().stream())
                .collect(Collectors.groupingBy(g -> g, Collectors.counting()));

        System.out.println("\nLibros por género:");
        System.out.printf("%-15s | %-10s%n", "Género", "Cantidad");
        System.out.println("--------------------------------");
        conteo.forEach((genero, cantidad) ->
                System.out.printf("%-15s | %-10d%n", genero, cantidad)
        );
        return true;
    }
}

class Libro {
    private String isbn;
    private String titulo;
    private List<String> generos;

    public Libro(String isbn, String titulo, List<String> generos) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.generos = generos;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", titulo, String.join(", ", generos));
    }
}