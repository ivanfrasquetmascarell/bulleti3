package com.ivanfrasquet.tema01;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class Act5 {
    private List<Alumno1> alumnos;

    public Act5(String ruta) {
        try (FileReader reader = new FileReader(ruta)) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Alumno1>>(){}.getType();
            alumnos = gson.fromJson(reader, tipoLista);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    /**
     * muestra el nombre y la fecha de nacimiento de los alumnos
     * @return
     */
    public boolean mostrarInfo() {
        if (alumnos == null || alumnos.isEmpty()) {
            return false;
        }

        System.out.printf("%-20s | %-15s%n", "Nombre", "Fecha de nacimiento");
        System.out.println("-------------------------------------------");
        for (Alumno1 a : alumnos) {
            System.out.printf("%-20s | %-15s%n", a.getNombre(), a.getFechaNacimiento());
        }
        return true;
    }
}

class Alumno1 {
    private String id;
    private String nombre;
    private boolean matriculado;
    private String fechaNacimiento;

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public boolean isMatriculado() { return matriculado; }
    public String getFechaNacimiento() { return fechaNacimiento; }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMatriculado(boolean matriculado) {
        this.matriculado = matriculado;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}