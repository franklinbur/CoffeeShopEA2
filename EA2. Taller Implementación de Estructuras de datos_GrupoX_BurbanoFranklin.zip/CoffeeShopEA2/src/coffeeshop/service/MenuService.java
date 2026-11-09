package coffeeshop.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Servicio encargado de la administración del catálogo de productos (Menú).
 * 
 * Justificación de Estructura de Datos:
 * - Interfaz: Set
 * - Implementación: HashSet
 * 
 * Razones técnicas:
 * 1. Garantiza por definición matemática la unicidad de los elementos (no admite duplicados).
 * 2. Ofrece una complejidad temporal de O(1) en promedio para operaciones de inserción (add),
 *    búsqueda (contains) y eliminación (remove), gracias al cálculo directo de la función de dispersión (hash).
 * 3. Es significativamente más rápido que estructuras basadas en árboles (TreeSet: O(log n))
 *    o listas (ArrayList: O(n) en búsquedas no ordenadas).
 */
public class MenuService {
    // Polimorfismo e interfaces: El tipo de referencia es la interfaz Set y la instancia concreta es HashSet.
    private final Set<String> productos;

    /**
     * Constructor por defecto. Inicializa el conjunto e inserta los productos iniciales.
     */
    public MenuService() {
        this.productos = new HashSet<>();
        cargarProductosIniciales();
    }

    /**
     * Carga el menú base definido en los requerimientos del proyecto.
     */
    private void cargarProductosIniciales() {
        agregarProducto("Latte");
        agregarProducto("Cappuccino");
        agregarProducto("Espresso");
        agregarProducto("Americano");
        agregarProducto("Mocaccino");
        agregarProducto("Muffin");
        agregarProducto("Croissant");
    }

    /**
     * Normaliza una cadena de texto para evitar duplicados por diferencias de espacios,
     * mayúsculas o minúsculas (formato Nombre Propio / Title Case).
     * 
     * Ejemplo: "  lAtTe " -> "Latte", "café con leche" -> "Café Con Leche"
     *
     * @param nombre Cadena original.
     * @return Cadena normalizada, o null si la entrada es inválida.
     */
    public String normalizarNombre(String nombre) {
        if (nombre == null) {
            return null;
        }
        String limpio = nombre.trim();
        if (limpio.isEmpty()) {
            return "";
        }

        String[] palabras = limpio.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < palabras.length; i++) {
            String p = palabras[i];
            if (!p.isEmpty()) {
                sb.append(Character.toUpperCase(p.charAt(0)));
                if (p.length() > 1) {
                    sb.append(p.substring(1).toLowerCase());
                }
                if (i < palabras.length - 1) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    /**
     * Registra un nuevo producto en el menú si no existe previamente.
     * 
     * Complejidad: O(1) promedio.
     * 
     * @param nombreProducto Nombre del producto a registrar.
     * @return true si el producto fue agregado con éxito; false si ya existía o el nombre es inválido.
     */
    public boolean agregarProducto(String nombreProducto) {
        String normalizado = normalizarNombre(nombreProducto);
        if (normalizado == null || normalizado.isEmpty()) {
            return false;
        }
        return productos.add(normalizado);
    }

    /**
     * Comprueba si un producto existe en el menú.
     * 
     * Complejidad: O(1) promedio.
     * 
     * @param nombreProducto Nombre del producto a consultar.
     * @return true si el producto está en el menú, false en caso contrario.
     */
    public boolean existeProducto(String nombreProducto) {
        String normalizado = normalizarNombre(nombreProducto);
        if (normalizado == null || normalizado.isEmpty()) {
            return false;
        }
        return productos.contains(normalizado);
    }

    /**
     * Elimina un producto del menú.
     * 
     * Complejidad: O(1) promedio.
     * 
     * @param nombreProducto Nombre del producto a remover.
     * @return true si el producto fue eliminado; false si no existía.
     */
    public boolean eliminarProducto(String nombreProducto) {
        String normalizado = normalizarNombre(nombreProducto);
        if (normalizado == null || normalizado.isEmpty()) {
            return false;
        }
        return productos.remove(normalizado);
    }

    /**
     * Retorna una vista inmutable del conjunto de productos disponibles.
     * 
     * @return Set inmutable de nombres de productos.
     */
    public Set<String> obtenerProductos() {
        return Collections.unmodifiableSet(productos);
    }

    /**
     * Obtiene el número total de productos disponibles en el menú.
     * 
     * @return Cantidad de elementos en el Set.
     */
    public int totalProductos() {
        return productos.size();
    }
}

