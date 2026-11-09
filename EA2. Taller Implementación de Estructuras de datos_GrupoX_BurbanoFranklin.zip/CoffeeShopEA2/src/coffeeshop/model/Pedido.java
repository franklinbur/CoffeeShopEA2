package coffeeshop.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Representa un pedido dentro del sistema de la cafetería.
 * Almacena los datos requeridos por las especificaciones funcionales:
 * identificador único, cliente, producto, fecha y hora de registro, estado y empleado asignado.
 */
public class Pedido {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final int id;
    private final String cliente;
    private final String producto;
    private final LocalDateTime fechaHoraRegistro;
    private EstadoPedido estado;
    private final Empleado empleadoAsignado;

    /**
     * Constructor completo para instanciar un pedido.
     * 
     * @param id               Identificador único del pedido.
     * @param cliente          Nombre del cliente que realiza el pedido.
     * @param producto         Producto del menú solicitado.
     * @param empleadoAsignado Empleado asignado a la atención del pedido.
     */
    public Pedido(int id, String cliente, String producto, Empleado empleadoAsignado) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del pedido debe ser mayor a cero.");
        }
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        if (producto == null || producto.trim().isEmpty()) {
            throw new IllegalArgumentException("El producto no puede estar vacío.");
        }
        if (empleadoAsignado == null) {
            throw new IllegalArgumentException("Todo pedido debe tener un empleado asignado válido.");
        }

        this.id = id;
        this.cliente = cliente.trim();
        this.producto = producto.trim();
        this.fechaHoraRegistro = LocalDateTime.now();
        this.estado = EstadoPedido.PENDIENTE;
        this.empleadoAsignado = empleadoAsignado;
    }

    public int getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getProducto() {
        return producto;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado del pedido no puede ser nulo.");
        }
        this.estado = estado;
    }

    public Empleado getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id == pedido.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
            "Pedido #%-3d | Cliente: %-12s | Producto: %-12s | Registrado: %s | Estado: %-10s | Atendido por: %s (ID: %d)",
            id,
            cliente,
            producto,
            fechaHoraRegistro.format(FORMATTER),
            estado.name(),
            empleadoAsignado.getNombre(),
            empleadoAsignado.getId()
        );
    }
}

