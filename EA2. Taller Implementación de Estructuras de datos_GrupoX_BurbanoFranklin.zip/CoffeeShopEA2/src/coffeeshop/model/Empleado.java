package coffeeshop.model;

import java.util.Objects;

/**
 * Representa a un empleado de la cafetería.
 * Aplica principios de encapsulamiento con atributos privados y métodos de acceso.
 */
public class Empleado {
    private final int id;
    private String nombre;

    /**
     * Constructor para inicializar un empleado con su identificador y nombre.
     * 
     * @param id     Identificador único del empleado (debe ser positivo).
     * @param nombre Nombre del empleado (no nulo ni vacío).
     * @throws IllegalArgumentException si los datos no son válidos.
     */
    public Empleado(int id, String nombre) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del empleado debe ser un número positivo.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del empleado no puede estar vacío.");
        }
        this.id = id;
        this.nombre = nombre.trim();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del empleado no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return id == empleado.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("[ID: %-4d | Nombre: %-15s]", id, nombre);
    }
}

