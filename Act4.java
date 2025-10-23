package com.ivanfrasquet.tema01;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class Act4 {
    private List<Pedido> pedidos = new ArrayList<>();

    public Act4(String ruta) {
        try {
            File xmlFile = new File(ruta);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList lista = doc.getElementsByTagName("pedido");

            for (int i = 0; i < lista.getLength(); i++) {
                Element pedidoElem = (Element) lista.item(i);

                String id = pedidoElem.getAttribute("id");
                String cliente = pedidoElem.getElementsByTagName("nombre").item(0).getTextContent();
                double totalXML = Double.parseDouble(pedidoElem.getElementsByTagName("total").item(0).getTextContent());

                NodeList itemsNodos = ((Element) pedidoElem.getElementsByTagName("items").item(0))
                        .getElementsByTagName("item");

                List<Item> items = new ArrayList<>();

                for (int j = 0; j < itemsNodos.getLength(); j++) {
                    Element itemElem = (Element) itemsNodos.item(j);

                    String sku = itemElem.getAttribute("sku");
                    String descripcion = itemElem.getElementsByTagName("descripcion").item(0).getTextContent();
                    int cantidad = Integer.parseInt(itemElem.getElementsByTagName("cantidad").item(0).getTextContent());
                    double precio = Double.parseDouble(itemElem.getElementsByTagName("precioUnitario").item(0).getTextContent());

                    items.add(new Item(sku, descripcion, cantidad, precio));
                }

                pedidos.add(new Pedido(id, cliente, items, totalXML));
            }

        } catch (Exception e) {
            System.err.println("Error al leer el XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * muestra los items del pedido pasado y el calcula el precio
     * @param idPedido
     * @return
     */
    public boolean mostrarItemsDePedido(String idPedido) {
        Optional<Pedido> pedidoOpt = pedidos.stream()
                .filter(p -> p.getId().equalsIgnoreCase(idPedido))
                .findFirst();

        if (pedidoOpt.isEmpty()) {
            return false;
        }

        Pedido pedido = pedidoOpt.get();
        System.out.println("\n" + pedido);
        System.out.println("Items del pedido:");
        pedido.getItems().forEach(item -> System.out.println(" - " + item));

        double recalculado = pedido.calcularTotal();
        System.out.printf("%n Total calculado: %.2f€%n", recalculado);
        System.out.printf("Total XML: %.2f€%n", pedido.getTotalXML());

        if (Math.abs(recalculado - pedido.getTotalXML()) < 0.01)
            return true;
        else
            return false;
    }
}

class Pedido {
    private String id;
    private String cliente;
    private List<Item> items;
    private double totalXML;

    public Pedido(String id, String cliente, List<Item> items, double totalXML) {
        this.id = id;
        this.cliente = cliente;
        this.items = items;
        this.totalXML = totalXML;
    }

    public String getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotalXML() {
        return totalXML;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setTotalXML(double totalXML) {
        this.totalXML = totalXML;
    }

    /**
     * calcula el total de un pedido
     * @return
     */
    public double calcularTotal() {
        return items.stream()
                .mapToDouble(i -> i.getCantidad() * i.getPrecioUnitario())
                .sum();
    }

    @Override
    public String toString() {
        return String.format("Pedido %s (%s) - Total declarado: %.2f€", id, cliente, totalXML);
    }
}

class Item {
    private String sku;
    private String descripcion;
    private int cantidad;
    private double precioUnitario;

    public Item(String sku, String descripcion, int cantidad, double precioUnitario) {
        this.sku = sku;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Cantidad: %d - Precio: %.2f€",
                descripcion, sku, cantidad, precioUnitario);
    }
}