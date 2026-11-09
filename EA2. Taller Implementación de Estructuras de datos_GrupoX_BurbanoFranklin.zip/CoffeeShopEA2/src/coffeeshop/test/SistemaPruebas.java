package coffeeshop.test;

import coffeeshop.model.Empleado;
import coffeeshop.model.EstadoPedido;
import coffeeshop.model.Pedido;
import coffeeshop.service.EmpleadoService;
import coffeeshop.service.MenuService;
import coffeeshop.service.PedidoService;

import java.util.List;
import java.util.Set;

/**
 * Suite de pruebas ejecutables automatizadas e independientes (sin dependencias externas).
 * 
 * Verifica el 100% de los requerimientos funcionales y no funcionales del proyecto CoffeeShopEA2:
 * 1. Registro de varios pedidos en la cola.
 * 2. Atención de pedidos en estricto orden FIFO (poll).
 * 3. Consulta de cola vacía (peek y poll retornan null con reporte adecuado).
 * 4. Prevención de productos duplicados en el menú (HashSet e indiferencia a mayúsculas/minúsculas).
 * 5. Búsqueda de productos existentes e inexistentes en el menú (contains O(1)).
 * 6. Registro de empleados con ID único.
 * 7. Búsqueda de empleados por ID (get O(1)).
 * 8. Consulta de empleados inexistentes (retorno seguro de null).
 * 9. Validación de productos antes de registrar un pedido.
 * 10. Validación de empleados antes de asignar a un pedido.
 */
public class SistemaPruebas {

    private static int totalPruebas = 0;
    private static int pruebasExitosas = 0;
    private static int pruebasFallidas = 0;

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("       EJECUCIÓN DE PRUEBAS AUTOMATIZADAS - SISTEMA COFFEESHOP EA2              ");
        System.out.println("   Tecnología en Desarrollo de Software - IU Digital de Antioquia               ");
        System.out.println("================================================================================");

        ejecutarTodasLasPruebas();

        System.out.println("\n================================================================================");
        System.out.println("                         RESUMEN DE RESULTADOS                                  ");
        System.out.println("================================================================================");
        System.out.printf("Total pruebas ejecutadas : %d%n", totalPruebas);
        System.out.printf("Pruebas exitosas (PASS)  : %d%n", pruebasExitosas);
        System.out.printf("Pruebas fallidas (FAIL)  : %d%n", pruebasFallidas);
        double tasaExito = (totalPruebas > 0) ? ((double) pruebasExitosas / totalPruebas) * 100.0 : 0.0;
        System.out.printf("Tasa de éxito            : %.1f%%%n", tasaExito);
        System.out.println("================================================================================");

        if (pruebasFallidas > 0) {
            System.err.println("¡ADVERTENCIA! Se encontraron pruebas fallidas. Revise los detalles anteriores.");
            System.exit(1);
        } else {
            System.out.println("¡TODAS LAS PRUEBAS FINALIZARON SATISFACTORIAMENTE CON ÉXITO!");
        }
    }

    public static void ejecutarTodasLasPruebas() {
        prueba1_RegistroVariosPedidos();
        prueba2_AtencionPedidosOrdenFIFO();
        prueba3_ConsultaColaVacia();
        prueba4_PrevencionProductosDuplicados();
        prueba5_BusquedaProductosExistentesEInexistentes();
        prueba6_RegistroEmpleadosIdUnico();
        prueba7_BusquedaEmpleadoPorId();
        prueba8_ConsultaEmpleadoInexistente();
        prueba9_ValidacionProductoAntesDePedido();
        prueba10_ValidacionEmpleadoAntesDeAsignar();
    }

    private static void registrarResultado(String idPrueba, String descripcion, boolean condicion, String esperado, String obtenido) {
        totalPruebas++;
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.printf("[%s] %s%n", idPrueba, descripcion);
        System.out.printf("  Resultado Esperado : %s%n", esperado);
        System.out.printf("  Resultado Obtenido : %s%n", obtenido);
        if (condicion) {
            pruebasExitosas++;
            System.out.println("  ESTADO             : [PASS] - PRUEBA EXITOSA");
        } else {
            pruebasFallidas++;
            System.out.println("  ESTADO             : [FAIL] - FALLO EN LA PRUEBA");
        }
    }

    // -------------------------------------------------------------------------
    // PRUEBA 1: Registro de varios pedidos en la cola
    // -------------------------------------------------------------------------
    private static void prueba1_RegistroVariosPedidos() {
        PedidoService pedidoService = new PedidoService();
        Empleado emp = new Empleado(101, "Carlos");

        Pedido p1 = pedidoService.registrarPedido("Ana Gómez", "Latte", emp);
        Pedido p2 = pedidoService.registrarPedido("Juan Pérez", "Cappuccino", emp);
        Pedido p3 = pedidoService.registrarPedido("Sofía Castro", "Croissant", emp);

        int total = pedidoService.totalPedidosPendientes();
        boolean ok = (total == 3) && (p1.getId() == 1) && (p2.getId() == 2) && (p3.getId() == 3);

        registrarResultado(
            "PRUEBA-01",
            "Registro de varios pedidos secuenciales en la cola (ArrayDeque)",
            ok,
            "Total en cola = 3, pedidos con IDs consecutivos 1, 2 y 3",
            String.format("Total en cola = %d, IDs = [%d, %d, %d]", total, p1.getId(), p2.getId(), p3.getId())
        );
    }

    // -------------------------------------------------------------------------
    // PRUEBA 2: Atención de pedidos en estricto orden FIFO (poll)
    // -------------------------------------------------------------------------
    private static void prueba2_AtencionPedidosOrdenFIFO() {
        PedidoService pedidoService = new PedidoService();
        Empleado emp = new Empleado(102, "María");

        pedidoService.registrarPedido("Primero", "Latte", emp);
        pedidoService.registrarPedido("Segundo", "Espresso", emp);
        pedidoService.registrarPedido("Tercero", "Muffin", emp);

        Pedido at1 = pedidoService.atenderSiguientePedido();
        Pedido at2 = pedidoService.atenderSiguientePedido();
        Pedido at3 = pedidoService.atenderSiguientePedido();

        boolean ok = "Primero".equals(at1.getCliente())
                  && "Segundo".equals(at2.getCliente())
                  && "Tercero".equals(at3.getCliente())
                  && at1.getEstado() == EstadoPedido.ENTREGADO
                  && pedidoService.estaVacia();

        registrarResultado(
            "PRUEBA-02",
            "Atención de pedidos en estricto orden FIFO utilizando poll()",
            ok,
            "Orden de extracción: Primero -> Segundo -> Tercero; cola queda vacía",
            String.format("Orden extraído: %s -> %s -> %s; cola vacía = %b",
                at1 != null ? at1.getCliente() : "null",
                at2 != null ? at2.getCliente() : "null",
                at3 != null ? at3.getCliente() : "null",
                pedidoService.estaVacia())
        );
    }

    // -------------------------------------------------------------------------
    // PRUEBA 3: Consulta de una cola vacía
    // -------------------------------------------------------------------------
    private static void prueba3_ConsultaColaVacia() {
        PedidoService pedidoService = new PedidoService();

        boolean estaVaciaInicial = pedidoService.estaVacia();
        Pedido peekVacio = pedidoService.consultarSiguientePedido();
        Pedido pollVacio = pedidoService.atenderSiguientePedido();

        boolean ok = estaVaciaInicial && (peekVacio == null) && (pollVacio == null);

        registrarResultado(
            "PRUEBA-03",
            "Consulta y retiro seguro en una cola vacía (peek y poll)",
            ok,
            "estaVacia() = true, peek() = null, poll() = null sin lanzar excepciones",
            String.format("estaVacia = %b, peek = %s, poll = %s", estaVaciaInicial, peekVacio, pollVacio)
        );
    }

    // -------------------------------------------------------------------------
    // PRUEBA 4: Prevención de productos duplicados en el menú (HashSet)
    // -------------------------------------------------------------------------
    private static void prueba4_PrevencionProductosDuplicados() {
        MenuService menu = new MenuService();
        int inicial = menu.totalProductos(); // 7 iniciales

        // Intento de agregar productos duplicados con variaciones de mayúsculas/minúsculas y espacios
        boolean r1 = menu.agregarProducto("latte");
        boolean r2 = menu.agregarProducto("LATTE");
        boolean r3 = menu.agregarProducto("  Latte  ");
        boolean r4 = menu.agregarProducto("Té Verde"); // nuevo válido

        int finalTotal = menu.totalProductos();
        boolean ok = !r1 && !r2 && !r3 && r4 && (finalTotal == inicial + 1);

        registrarResultado(
            "PRUEBA-04",
            "Prevención de productos duplicados en HashSet con normalización",
            ok,
            "Rechazar 'latte', 'LATTE' y '  Latte  ' (false); aceptar 'Té Verde' (true); tamaño pasa de 7 a 8",
            String.format("Rechazos: [%b, %b, %b], Nuevo: %b, Tamaño final = %d", r1, r2, r3, r4, finalTotal)
        );
    }

    // -------------------------------------------------------------------------
    // PRUEBA 5: Búsqueda de productos existentes e inexistentes (contains)
    // -------------------------------------------------------------------------
    private static void prueba5_BusquedaProductosExistentesEInexistentes() {
        MenuService menu = new MenuService();

        boolean existeLatte = menu.existeProducto("latte");
        boolean existeCapuchino = menu.existeProducto("CAPPUCCINO");
        boolean existeInexistente = menu.existeProducto("Hamburguesa Triple");

        boolean ok = existeLatte && existeCapuchino && !existeInexistente;

        registrarResultado(
            "PRUEBA-05",
            "Búsqueda O(1) de productos existentes e inexistentes mediante contains()",
            ok,
            "existe('latte') = true, existe('CAPPUCCINO') = true, existe('Hamburguesa Triple') = false",
            String.format("'latte': %b, 'CAPPUCCINO': %b, 'Hamburguesa': %b", existeLatte, existeCapuchino, existeInexistente)
        );
    }

    // -------------------------------------------------------------------------
    // PRUEBA 6: Registro de empleados con ID único en HashMap
    // -------------------------------------------------------------------------
    private static void prueba6_RegistroEmpleadosIdUnico() {
        EmpleadoService empService = new EmpleadoService();
        int totalInicial = empService.totalEmpleados(); // 3 iniciales (101, 102, 103)

        boolean regNuevo = empService.registrarEmpleado(104, "Laura Gómez");
        boolean regDuplicado = empService.registrarEmpleado(101, "Carlos Intruso"); // ID 101 ya existe
        boolean regInvalidoId = empService.registrarEmpleado(-5, "ID Negativo");
        boolean regInvalidoNombre = empService.registrarEmpleado(105, "   ");

        boolean ok = regNuevo && !regDuplicado && !regInvalidoId && !regInvalidoNombre
                  && empService.totalEmpleados() == totalInicial + 1;

        registrarResultado(
            "PRUEBA-06",
            "Registro de empleados y validación de unicidad de ID en HashMap",
            ok,
            "Nuevo empleado (104) = true, ID duplicado (101) = false, ID negativo = false, Nombre vacío = false",
            String.format("104: %b, 101 dup: %b, -5: %b, vacío: %b, Total: %d",
                regNuevo, regDuplicado, regInvalidoId, regInvalidoNombre, empService.totalEmpleados())
        );
    }

    // -------------------------------------------------------------------------
    // PRUEBA 7: Búsqueda de empleados por ID (get)
    // -------------------------------------------------------------------------
    private static void prueba7_BusquedaEmpleadoPorId() {
        EmpleadoService empService = new EmpleadoService();

        Empleado e101 = empService.consultarEmpleado(101);
        Empleado e102 = empService.consultarEmpleado(102);

        boolean ok = (e101 != null && "Carlos".equals(e101.getNombre()))
                  && (e102 != null && "María".equals(e102.getNombre()));

        registrarResultado(
            "PRUEBA-07",
            "Consulta O(1) de empleados por ID mediante HashMap.get()",
            ok,
            "ID 101 -> Carlos, ID 102 -> María",
            String.format("ID 101 -> %s, ID 102 -> %s",
                e101 != null ? e101.getNombre() : "null",
                e102 != null ? e102.getNombre() : "null")
        );
    }

    // -------------------------------------------------------------------------
    // PRUEBA 8: Consulta de empleados inexistentes
    // -------------------------------------------------------------------------
    private static void prueba8_ConsultaEmpleadoInexistente() {
        EmpleadoService empService = new EmpleadoService();

        boolean existe999 = empService.existeEmpleado(999);
        Empleado emp999 = empService.consultarEmpleado(999);

        boolean ok = !existe999 && (emp999 == null);

        registrarResultado(
            "PRUEBA-08",
            "Consulta controlada de empleados inexistentes",
            ok,
            "existeEmpleado(999) = false, consultarEmpleado(999) = null sin error",
            String.format("existe(999) = %b, objeto obtenido = %s", existe999, emp999)
        );
    }

    // -------------------------------------------------------------------------
    // PRUEBA 9: Validación de productos antes de registrar pedidos
    // -------------------------------------------------------------------------
    private static void prueba9_ValidacionProductoAntesDePedido() {
        MenuService menu = new MenuService();
        EmpleadoService empService = new EmpleadoService();
        PedidoService pedidoService = new PedidoService();

        Empleado emp = empService.consultarEmpleado(101);
        String prodInvalido = "Pizza Hawaiana";
        String prodValido = "Latte";

        // Lógica de validación antes de inserción
        boolean puedeRegistrarInvalido = menu.existeProducto(prodInvalido);
        boolean puedeRegistrarValido = menu.existeProducto(prodValido);

        Pedido pedidoCreado = null;
        if (puedeRegistrarValido) {
            pedidoCreado = pedidoService.registrarPedido("Cliente Validador", prodValido, emp);
        }

        boolean ok = !puedeRegistrarInvalido && puedeRegistrarValido && (pedidoCreado != null);

        registrarResultado(
            "PRUEBA-09",
            "Validación previa de existencia del producto en el menú antes de crear pedido",
            ok,
            "Producto inválido rechazado (false), producto válido aceptado y pedido encolado exitosamente",
            String.format("Pizza válida = %b, Latte válido = %b, Pedido registrado = %s",
                puedeRegistrarInvalido, puedeRegistrarValido, pedidoCreado != null ? "ID #" + pedidoCreado.getId() : "null")
        );
    }

    // -------------------------------------------------------------------------
    // PRUEBA 10: Validación de empleados antes de asignarlos a un pedido
    // -------------------------------------------------------------------------
    private static void prueba10_ValidacionEmpleadoAntesDeAsignar() {
        MenuService menu = new MenuService();
        EmpleadoService empService = new EmpleadoService();
        PedidoService pedidoService = new PedidoService();

        int idEmpleadoInexistente = 888;
        int idEmpleadoExistente = 103;

        boolean existeInexistente = empService.existeEmpleado(idEmpleadoInexistente);
        boolean existeValido = empService.existeEmpleado(idEmpleadoExistente);

        Pedido pedidoAsignado = null;
        if (existeValido) {
            Empleado emp = empService.consultarEmpleado(idEmpleadoExistente);
            pedidoAsignado = pedidoService.registrarPedido("Cliente Andrés", "Americano", emp);
        }

        boolean ok = !existeInexistente && existeValido && (pedidoAsignado != null)
                  && (pedidoAsignado.getEmpleadoAsignado().getId() == idEmpleadoExistente);

        registrarResultado(
            "PRUEBA-10",
            "Validación previa de existencia del empleado antes de asignación al pedido",
            ok,
            "Empleado 888 rechazado (false), Empleado 103 aceptado (true) y pedido asignado a Andrés",
            String.format("ID 888 válido = %b, ID 103 válido = %b, Asignado a: %s",
                existeInexistente, existeValido,
                pedidoAsignado != null ? pedidoAsignado.getEmpleadoAsignado().getNombre() : "null")
        );
    }
}

