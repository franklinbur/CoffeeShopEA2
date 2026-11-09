package coffeeshop.service;

import coffeeshop.model.Empleado;
import coffeeshop.model.EstadoPedido;
import coffeeshop.model.Pedido;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * Servicio encargado de la gestión de pedidos de la cafetería bajo el principio FIFO.
 * 
 * Justificación de Estructura de Datos:
 * - Interfaz: Queue
 * - Implementación: ArrayDeque
 * 
 * Razones técnicas:
 * 1. Implementa una cola circular basada en un arreglo dinámico redimensionable.
 * 2. Operaciones en los extremos con complejidad O(1) amortizado para inserción (offer)
 *    y extracción (poll).
 * 3. Es más eficiente en memoria y en rendimiento que LinkedList al no requerir la creación
 *    adicional de objetos nodo (Node) por cada elemento insertado, favoreciendo la localidad espacial del CPU.
 * 4. A diferencia de ArrayList, ArrayDeque no incurre en un costo O(n) al remover el primer elemento.
 */
public class PedidoService {
    // Polimorfismo e interfaces: Tipo de referencia Queue, implementación ArrayDeque.
    private final Queue<Pedido> colaPedidos;
    private int contadorId;

    /**
     * Constructor del servicio de pedidos. Inicializa la cola y el contador de identificadores.
     */
    public PedidoService() {
        this.colaPedidos = new ArrayDeque<>();
        this.contadorId = 1;
    }

    /**
     * Registra un nuevo pedido en el sistema agregándolo al final de la cola mediante offer().
     * 
     * Complejidad: O(1) amortizado.
     * 
     * @param cliente          Nombre del cliente.
     * @param producto         Producto solicitado (validado previamente con MenuService).
     * @param empleadoAsignado Empleado responsable (validado previamente con EmpleadoService).
     * @return El objeto Pedido creado e insertado en la cola.
     */
    public Pedido registrarPedido(String cliente, String producto, Empleado empleadoAsignado) {
        Pedido nuevoPedido = new Pedido(contadorId++, cliente, producto, empleadoAsignado);
        boolean insertado = colaPedidos.offer(nuevoPedido);
        if (!insertado) {
            throw new IllegalStateException("No fue posible encolar el pedido en este momento.");
        }
        return nuevoPedido;
    }

    /**
     * Atiende y retira el siguiente pedido de la cola según el orden FIFO mediante poll().
     * 
     * Complejidad: O(1) amortizado.
     * 
     * @return El pedido atendido, o null si la cola está vacía.
     */
    public Pedido atenderSiguientePedido() {
        Pedido pedidoAtendido = colaPedidos.poll();
        if (pedidoAtendido != null) {
            pedidoAtendido.setEstado(EstadoPedido.ENTREGADO);
        }
        return pedidoAtendido;
    }

    /**
     * Consulta el siguiente pedido en la cola sin removerlo, mediante peek().
     * 
     * Complejidad: O(1).
     * 
     * @return El siguiente pedido por atender, o null si la cola está vacía.
     */
    public Pedido consultarSiguientePedido() {
        return colaPedidos.peek();
    }

    /**
     * Retorna una lista con los pedidos actualmente pendientes sin alterar la estructura de la cola.
     * 
     * @return Lista no modificable con los pedidos pendientes.
     */
    public List<Pedido> obtenerPedidosPendientes() {
        return Collections.unmodifiableList(new ArrayList<>(colaPedidos));
    }

    /**
     * Verifica si la cola no contiene pedidos pendientes.
     * 
     * @return true si la cola está vacía, false si tiene pedidos por atender.
     */
    public boolean estaVacia() {
        return colaPedidos.isEmpty();
    }

    /**
     * Retorna el número de pedidos en espera de ser atendidos.
     * 
     * @return Cantidad de pedidos pendientes.
     */
    public int totalPedidosPendientes() {
        return colaPedidos.size();
    }
}

