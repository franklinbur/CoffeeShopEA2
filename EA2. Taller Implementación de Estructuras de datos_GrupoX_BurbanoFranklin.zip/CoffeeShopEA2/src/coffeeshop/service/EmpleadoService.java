package coffeeshop.service;

import coffeeshop.model.Empleado;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio encargado de la gestión de empleados de la cafetería.
 * 
 * Justificación de Estructura de Datos:
 * - Interfaz: Map
 * - Implementación: HashMap
 * 
 * Razones técnicas:
 * 1. Asocia una clave única primaria (ID entero del empleado) con su respectiva entidad o valor.
 * 2. Ofrece una complejidad temporal de O(1) promedio para búsqueda (get), inserción (put) y
 *    verificación de existencia (containsKey) mediante la tabla de dispersión interna.
 * 3. Supera a TreeMap (O(log n)) en rendimiento cuando no se requiere un ordenamiento estricto
 *    de las claves, y a un ArrayList (O(n) en búsquedas secuenciales por ID).
 */
public class EmpleadoService {
    // Polimorfismo e interfaces: Tipo de referencia Map, implementación concreta HashMap.
    private final Map<Integer, Empleado> empleados;

    /**
     * Constructor. Inicializa el mapa y carga los empleados iniciales requeridos.
     */
    public EmpleadoService() {
        this.empleados = new HashMap<>();
        cargarEmpleadosIniciales();
    }

    /**
     * Carga el personal base especificado en los requerimientos del proyecto.
     */
    private void cargarEmpleadosIniciales() {
        registrarEmpleado(101, "Carlos");
        registrarEmpleado(102, "María");
        registrarEmpleado(103, "Andrés");
    }

    /**
     * Registra un nuevo empleado validando que no exista duplicidad de ID y datos válidos.
     * 
     * Complejidad: O(1) promedio.
     * 
     * @param id     Identificador único mayor a cero.
     * @param nombre Nombre del empleado.
     * @return true si el registro fue exitoso; false si el ID ya existe o los datos son inválidos.
     */
    public boolean registrarEmpleado(int id, String nombre) {
        if (id <= 0 || nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        // Validación de clave existente mediante containsKey() O(1)
        if (empleados.containsKey(id)) {
            return false;
        }

        Empleado nuevoEmpleado = new Empleado(id, nombre);
        empleados.put(id, nuevoEmpleado);
        return true;
    }

    /**
     * Consulta un empleado a partir de su ID utilizando el método get().
     * 
     * Complejidad: O(1) promedio.
     * 
     * @param id Identificador a buscar.
     * @return Objeto Empleado encontrado, o null si no existe.
     */
    public Empleado consultarEmpleado(int id) {
        return empleados.get(id);
    }

    /**
     * Verifica si existe un empleado registrado con el ID suministrado.
     * 
     * Complejidad: O(1) promedio.
     * 
     * @param id Identificador a verificar.
     * @return true si existe la clave; false en caso contrario.
     */
    public boolean existeEmpleado(int id) {
        return empleados.containsKey(id);
    }

    /**
     * Retorna una vista inmutable de todos los empleados registrados.
     * 
     * @return Map inmutable de ID -> Empleado.
     */
    public Map<Integer, Empleado> obtenerTodos() {
        return Collections.unmodifiableMap(empleados);
    }

    /**
     * Obtiene la cantidad total de empleados registrados.
     * 
     * @return Número de empleados en el sistema.
     */
    public int totalEmpleados() {
        return empleados.size();
    }
}

