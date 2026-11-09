package coffeeshop.model;

/**
 * Enumeración que representa los estados posibles de un pedido.
 * Facilita el ciclo de vida del pedido cumpliendo con buenas prácticas de POO.
 */
public enum EstadoPedido {
    PENDIENTE("Pendiente de preparación"),
    EN_PREPARACION("En preparación"),
    ENTREGADO("Entregado al cliente"),
    CANCELADO("Cancelado");

    private final String descripcion;

    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}

