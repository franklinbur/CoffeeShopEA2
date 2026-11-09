package coffeeshop.view;

import coffeeshop.model.Empleado;
import coffeeshop.model.Pedido;
import coffeeshop.service.EmpleadoService;
import coffeeshop.service.MenuService;
import coffeeshop.service.PedidoService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Interfaz de usuario por consola para el sistema CoffeeShopEA2.
 * Gestiona el menú interactivo, la captura y validación de datos de entrada
 * y la comunicación desacoplada con las capas de servicio.
 */
public class MenuPrincipal {
    private final PedidoService pedidoService;
    private final MenuService menuService;
    private final EmpleadoService empleadoService;
    private final Scanner scanner;

    public MenuPrincipal() {
        this.pedidoService = new PedidoService();
        this.menuService = new MenuService();
        this.empleadoService = new EmpleadoService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Constructor con inyección de dependencias (útil para pruebas o configuración personalizada).
     */
    public MenuPrincipal(PedidoService pedidoService, MenuService menuService, EmpleadoService empleadoService, Scanner scanner) {
        this.pedidoService = pedidoService;
        this.menuService = menuService;
        this.empleadoService = empleadoService;
        this.scanner = scanner;
    }

    /**
     * Bucle principal de ejecución del menú interactivo.
     */
    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            mostrarEncabezadoMenu();
            int opcion = leerEntero("Seleccione una opción (1-11): ");

            System.out.println();
            switch (opcion) {
                case 1 -> registrarPedido();
                case 2 -> atenderSiguientePedido();
                case 3 -> consultarSiguientePedido();
                case 4 -> mostrarPedidosPendientes();
                case 5 -> agregarProducto();
                case 6 -> consultarProducto();
                case 7 -> mostrarMenuProductos();
                case 8 -> registrarEmpleado();
                case 9 -> consultarEmpleadoPorId();
                case 10 -> mostrarEmpleados();
                case 11 -> {
                    System.out.println("======================================================================");
                    System.out.println(" ¡Gracias por utilizar CoffeeShopEA2! Cerrando el sistema con éxito...");
                    System.out.println("======================================================================");
                    salir = true;
                }
                default -> System.out.println("[ERROR] Opción no válida. Por favor elija un número entre 1 y 11.");
            }

            if (!salir) {
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }

    private void mostrarEncabezadoMenu() {
        System.out.println("\n+--------------------------------------------------------------------+");
        System.out.println("|                COFFEESHOP EA2 - SISTEMA DE GESTION                 |");
        System.out.println("|             Institucion Universitaria Digital de Antioquia         |");
        System.out.println("+--------------------------------------------------------------------+");
        System.out.println("|  MODULO DE PEDIDOS (Queue / ArrayDeque - FIFO O(1) amortizado)      |");
        System.out.println("|    1. Registrar pedido                                             |");
        System.out.println("|    2. Atender siguiente pedido                                     |");
        System.out.println("|    3. Consultar siguiente pedido (peek)                            |");
        System.out.println("|    4. Mostrar pedidos pendientes                                   |");
        System.out.println("|                                                                    |");
        System.out.println("|  MODULO DE MENU (Set / HashSet - Unicidad O(1) promedio)           |");
        System.out.println("|    5. Agregar producto al menu                                     |");
        System.out.println("|    6. Consultar si un producto existe                              |");
        System.out.println("|    7. Mostrar catalogo de productos                                |");
        System.out.println("|                                                                    |");
        System.out.println("|  MODULO DE EMPLEADOS (Map / HashMap - Busqueda por ID O(1) prom.)   |");
        System.out.println("|    8. Registrar empleado                                           |");
        System.out.println("|    9. Consultar empleado por ID                                    |");
        System.out.println("|   10. Mostrar todos los empleados                                  |");
        System.out.println("|                                                                    |");
        System.out.println("|  11. Salir del sistema                                             |");
        System.out.println("+--------------------------------------------------------------------+");
    }

    // =========================================================================
    // ACCIONES DEL MÓDULO DE PEDIDOS
    // =========================================================================

    private void registrarPedido() {
        System.out.println("--- REGISTRO DE NUEVO PEDIDO ---");
        String cliente = leerCadena("Nombre del cliente: ");
        if (cliente.isEmpty()) {
            System.out.println("[ERROR] El nombre del cliente no puede estar vacío.");
            return;
        }

        String nombreProducto = leerCadena("Producto solicitado: ");
        if (!menuService.existeProducto(nombreProducto)) {
            System.out.println("[ERROR] El producto '" + nombreProducto + "' no existe en el menú.");
            System.out.println("[AYUDA] Consulte la opción 7 para ver los productos disponibles o agregue uno nuevo con la opción 5.");
            return;
        }
        String productoEstandarizado = menuService.normalizarNombre(nombreProducto);

        int idEmpleado = leerEntero("ID del empleado que atiende el pedido: ");
        if (!empleadoService.existeEmpleado(idEmpleado)) {
            System.out.println("[ERROR] No existe ningún empleado registrado con el ID " + idEmpleado + ".");
            System.out.println("[AYUDA] Consulte la opción 10 para ver la lista de personal autorizado.");
            return;
        }
        Empleado empleado = empleadoService.consultarEmpleado(idEmpleado);

        Pedido nuevo = pedidoService.registrarPedido(cliente, productoEstandarizado, empleado);
        System.out.println("\n[ÉXITO] ¡Pedido registrado satisfactoriamente en la cola!");
        System.out.println(">> " + nuevo);
        System.out.println(">> Total pedidos pendientes en cola: " + pedidoService.totalPedidosPendientes());
    }

    private void atenderSiguientePedido() {
        System.out.println("--- ATENCIÓN DE PEDIDO (FIFO - poll()) ---");
        if (pedidoService.estaVacia()) {
            System.out.println("[INFORMACIÓN] No hay pedidos pendientes por atender en este momento.");
            return;
        }

        Pedido atendido = pedidoService.atenderSiguientePedido();
        System.out.println("[ÉXITO] Se ha atendido y despachado el pedido:");
        System.out.println(">> " + atendido);
        System.out.println(">> Pedidos restantes en espera: " + pedidoService.totalPedidosPendientes());
    }

    private void consultarSiguientePedido() {
        System.out.println("--- CONSULTAR SIGUIENTE PEDIDO EN TURNO (peek()) ---");
        if (pedidoService.estaVacia()) {
            System.out.println("[INFORMACIÓN] La cola está vacía. No hay pedidos en espera.");
            return;
        }

        Pedido siguiente = pedidoService.consultarSiguientePedido();
        System.out.println("[INFORMACIÓN] El siguiente pedido en turno para preparación es:");
        System.out.println(">> " + siguiente);
        System.out.println("*(Nota: El pedido sigue en la cola sin modificaciones)*");
    }

    private void mostrarPedidosPendientes() {
        System.out.println("--- LISTA DE PEDIDOS PENDIENTES EN COLA ---");
        if (pedidoService.estaVacia()) {
            System.out.println("[INFORMACIÓN] No existen pedidos pendientes actualmente.");
            return;
        }

        List<Pedido> pendientes = pedidoService.obtenerPedidosPendientes();
        System.out.println("Total en espera: " + pendientes.size());
        System.out.println("---------------------------------------------------------------------------------------------------");
        int turno = 1;
        for (Pedido p : pendientes) {
            System.out.printf("Turno %-2d -> %s%n", turno++, p);
        }
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    // =========================================================================
    // ACCIONES DEL MÓDULO DE MENÚ (PRODUCTOS)
    // =========================================================================

    private void agregarProducto() {
        System.out.println("--- AGREGAR PRODUCTO AL MENÚ ---");
        String nombre = leerCadena("Nombre del nuevo producto: ");
        if (nombre.isEmpty()) {
            System.out.println("[ERROR] El nombre del producto no puede estar vacío.");
            return;
        }

        boolean agregado = menuService.agregarProducto(nombre);
        if (agregado) {
            System.out.println("[ÉXITO] Producto '" + menuService.normalizarNombre(nombre) + "' agregado al menú correctamente.");
        } else {
            System.out.println("[AVISO] El producto '" + menuService.normalizarNombre(nombre) + "' ya existe en el menú (se previno duplicado).");
        }
    }

    private void consultarProducto() {
        System.out.println("--- CONSULTAR DISPONIBILIDAD DE PRODUCTO ---");
        String nombre = leerCadena("Ingrese el nombre del producto a buscar: ");
        if (nombre.isEmpty()) {
            System.out.println("[ERROR] Debe ingresar un término de búsqueda válido.");
            return;
        }

        boolean existe = menuService.existeProducto(nombre);
        if (existe) {
            System.out.println("[DISPONIBLE] El producto '" + menuService.normalizarNombre(nombre) + "' se encuentra activo en el menú.");
        } else {
            System.out.println("[NO ENCONTRADO] El producto '" + nombre + "' no forma parte del menú actual.");
        }
    }

    private void mostrarMenuProductos() {
        System.out.println("--- CATÁLOGO DE PRODUCTOS DISPONIBLES (HashSet) ---");
        Set<String> items = menuService.obtenerProductos();
        if (items.isEmpty()) {
            System.out.println("[INFORMACIÓN] El menú está vacío.");
            return;
        }

        System.out.println("Total de productos en catálogo: " + menuService.totalProductos());
        System.out.println("--------------------------------------------------");
        int index = 1;
        for (String p : items) {
            System.out.printf("  %2d. %s%n", index++, p);
        }
        System.out.println("--------------------------------------------------");
    }

    // =========================================================================
    // ACCIONES DEL MÓDULO DE EMPLEADOS
    // =========================================================================

    private void registrarEmpleado() {
        System.out.println("--- REGISTRO DE EMPLEADO ---");
        int id = leerEntero("Ingrese el ID del empleado (entero positivo): ");
        if (id <= 0) {
            System.out.println("[ERROR] El ID debe ser un número entero positivo mayor a cero.");
            return;
        }

        String nombre = leerCadena("Nombre completo del empleado: ");
        if (nombre.isEmpty()) {
            System.out.println("[ERROR] El nombre no puede estar vacío.");
            return;
        }

        boolean registrado = empleadoService.registrarEmpleado(id, nombre);
        if (registrado) {
            System.out.println("[ÉXITO] Empleado registrado: " + empleadoService.consultarEmpleado(id));
        } else {
            System.out.println("[ERROR] Ya existe un empleado con el ID " + id + ". Las claves deben ser únicas.");
        }
    }

    private void consultarEmpleadoPorId() {
        System.out.println("--- CONSULTA DE EMPLEADO POR ID ---");
        int id = leerEntero("Ingrese el ID del empleado: ");
        Empleado emp = empleadoService.consultarEmpleado(id);
        if (emp != null) {
            System.out.println("[ENCONTRADO] Datos del empleado:");
            System.out.println(">> " + emp);
        } else {
            System.out.println("[NO ENCONTRADO] No existe ningún empleado registrado con el ID " + id + ".");
        }
    }

    private void mostrarEmpleados() {
        System.out.println("--- LISTA DE EMPLEADOS REGISTRADOS (HashMap) ---");
        Map<Integer, Empleado> empleados = empleadoService.obtenerTodos();
        if (empleados.isEmpty()) {
            System.out.println("[INFORMACIÓN] No hay empleados registrados en el sistema.");
            return;
        }

        System.out.println("Total de empleados activos: " + empleadoService.totalEmpleados());
        System.out.println("--------------------------------------------------");
        for (Map.Entry<Integer, Empleado> entry : empleados.entrySet()) {
            System.out.println("  Clave [ID " + entry.getKey() + "] => " + entry.getValue());
        }
        System.out.println("--------------------------------------------------");
    }

    // =========================================================================
    // MÉTODOS AUXILIARES DE ENTRADA Y VALIDACIÓN
    // =========================================================================

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Entrada inválida. Ingrese un número entero válido.");
            }
        }
    }

    private String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}
