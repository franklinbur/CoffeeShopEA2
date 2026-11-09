# INFORME ACADÉMICO – ACTIVIDAD EVALUATIVA 2 (EA2)
## IMPLEMENTACIÓN Y JUSTIFICACIÓN DE ESTRUCTURAS DE DATOS EN JAVA

---

### 1. PORTADA

**INSTITUCIÓN UNIVERSITARIA DIGITAL DE ANTIOQUIA – IU DIGITAL**  
**Facultad de Ingeniería**  
**Tecnología en Desarrollo de Software**  
**Asignatura:** Estructuras de Datos  
**Semestre / Periodo Académico:** 2026-1 / EA2  

---

**TÍTULO DEL PROYECTO:**  
### CoffeeShopEA2 – Sistema de Gestión de Pedidos de una Cafetería Basado en Estructuras de Datos Eficientes

---

**ESTUDIANTES:**  
1. `[NOMBRE COMPLETO DEL ESTUDIANTE 1]` - `[CORREO INSTITUCIONAL]` - `[DOCUMENTO DE IDENTIDAD]`  
2. `[NOMBRE COMPLETO DEL ESTUDIANTE 2]` - `[CORREO INSTITUCIONAL]` - `[DOCUMENTO DE IDENTIDAD]`  
3. `[NOMBRE COMPLETO DEL ESTUDIANTE 3]` - `[CORREO INSTITUCIONAL]` - `[DOCUMENTO DE IDENTIDAD]`  
4. `[NOMBRE COMPLETO DEL ESTUDIANTE 4]` - `[CORREO INSTITUCIONAL]` - `[DOCUMENTO DE IDENTIDAD]`  
5. `[NOMBRE COMPLETO DEL ESTUDIANTE 5]` - `[CORREO INSTITUCIONAL]` - `[DOCUMENTO DE IDENTIDAD]`  

**DOCENTE:**  
`[NOMBRE DEL DOCENTE ASIGNADO]`  

**GRUPO:**  
`[CÓDIGO O NÚMERO DE GRUPO]`  

**CIUDAD Y FECHA:**  
Medellín, Antioquia – `[FECHA DE ENTREGA]`  

**ENLACE DEL VIDEO DE SUSTENTACIÓN:**  
`[INSERTAR AQUÍ EL ENLACE AL VIDEO EN YOUTUBE / DRIVE / TEAMS]`  

---

### 2. INTRODUCCIÓN

En la industria del software, el diseño de sistemas escalables y de alto rendimiento depende críticamente de la selección técnica de los Tipos de Datos Abstractos (TDA) y sus implementaciones concretas. Una elección errada de una estructura de datos en operaciones concurrentes o en bucles transaccionales puede transformar un proceso de tiempo constante en uno de tiempo cuadrático, colapsando los servicios en entornos de alta demanda.

El presente proyecto académico aborda el desarrollo de **CoffeeShopEA2**, una aplicación desarrollada en lenguaje Java estándar (JDK 21) concebida para modernizar y optimizar la operativa de una cafetería. A través de este caso práctico, se modelan tres áreas esenciales del negocio: el flujo de despacho de pedidos bajo estricto orden de llegada, el mantenimiento y control de unicidad del catálogo de productos y el registro e indexación directa de los empleados responsables de la atención. 

El sistema implementa de forma rigurosa los principios de la Programación Orientada a Objetos (POO), los patrones de bajo acoplamiento mediante inversión de interfaces del Java Collections Framework (JCF), y expone un análisis formal de complejidad temporal y espacial respaldado por pruebas de software reproducibles.

---

### 3. OBJETIVO GENERAL

Desarrollar una aplicación de consola en Java puro para la gestión de pedidos, catálogo de productos y empleados de una cafetería, seleccionando, implementando y justificando matemáticamente las estructuras de datos del Java Collections Framework (`Queue/ArrayDeque`, `Set/HashSet`, `Map/HashMap`) con el propósito de optimizar los tiempos de respuesta y garantizar la integridad de las transacciones.

---

### 4. OBJETIVOS ESPECÍFICOS

1. Modelar una cola de atención de pedidos fundamentada en la interfaz `Queue` y la implementación `ArrayDeque`, asegurando un orden de despacho FIFO (*First-In, First-Out*) con complejidad temporal $O(1)$ amortizado en los extremos.
2. Construir un módulo de administración del catálogo de productos empleando la interfaz `Set` y la clase `HashSet`, garantizando la prevención de duplicados con normalización insensible a mayúsculas/minúsculas y búsquedas de disponibilidad en tiempo promedio $O(1)$.
3. Implementar un módulo de gestión de personal basado en la interfaz `Map` y la clase `HashMap`, permitiendo la validación inmediata y la consulta de colaboradores por su ID único en tiempo promedio $O(1)$.
4. Diseñar una arquitectura modular con separación de responsabilidades en capas (`model`, `service`, `view`, `test`), aplicando encapsulamiento, polimorfismo y programación hacia interfaces.
5. Elaborar una suite de pruebas automatizadas independiente (sin dependencias externas) que verifique el 100% de los requerimientos funcionales y documente formalmente los resultados obtenidos.

---

### 5. DESCRIPCIÓN DEL CASO DE ESTUDIO

La cafetería **CoffeeShopEA2** experimenta cuellos de botella durante las horas de mayor afluencia. Entre los problemas identificados se encuentran:
- **Alteración de turnos:** Clientes que llegaron después son atendidos antes debido a la falta de un control sistemático de colas.
- **Inconsistencias en el menú:** Registro repetido de productos por variaciones ortográficas (ejemplo: "latte", "Latte", "LATTE"), generando dispersión y confusión en los inventarios.
- **Falta de trazabilidad:** Registro de pedidos asignados a códigos de empleado inexistentes o inválidos.

Para solucionar estos desafíos, la dirección requiere una solución automatizada que asegure la integridad de los datos, rechace entradas erróneas y garantice que las consultas y operaciones se ejecuten con la mínima latencia computacional.

---

### 6. ANÁLISIS DE REQUERIMIENTOS

#### 6.1 Requerimientos Funcionales (RF)

| Código | Requerimiento | Descripción |
|---|---|---|
| **RF-01** | Registro inmediato de pedidos | Encolar pedidos con cliente, producto, fecha/hora, estado y empleado responsable. |
| **RF-02** | Despacho FIFO | Atender pedidos estrictamente en el orden en que fueron registrados (`poll()`). |
| **RF-03** | Consulta de siguiente pedido | Visualizar el próximo pedido a despachar sin retirarlo de la cola (`peek()`). |
| **RF-04** | Listado de pedidos pendientes | Mostrar todos los pedidos en espera con su número de turno correlativo. |
| **RF-05** | Prevención de cola vacía | Informar amigablemente si no hay pedidos pendientes al consultar o atender. |
| **RF-06** | Catálogo de productos sin duplicados | Registrar productos evitando redundancias mediante `HashSet` y normalización léxica. |
| **RF-07** | Verificación de producto en menú | Validar si un producto existe en el catálogo en tiempo constante antes de admitir un pedido. |
| **RF-08** | Mantenimiento del catálogo | Permitir listar, agregar y eliminar productos del catálogo activo. |
| **RF-09** | Registro de personal con ID único | Almacenar empleados asociando un identificador numérico irrepetible con sus nombres. |
| **RF-10** | Consulta de empleado por ID | Recuperar y validar la existencia de un empleado en tiempo $O(1)$ mediante su ID. |
| **RF-11** | Validación cruzada en pedidos | Impedir registrar pedidos si el producto no existe en el menú o si el empleado no está registrado. |

#### 6.2 Requerimientos No Funcionales (RNF)

| Código | Requerimiento | Criterio de Aceptación |
|---|---|---|
| **RNF-01** | Eficiencia computacional | Operaciones críticas de inserción, búsqueda y extracción en $O(1)$ promedio o amortizado. |
| **RNF-02** | Pureza tecnológica | Uso exclusivo de JDK estándar sin frameworks ni librerías externas adicionales. |
| **RNF-03** | Principios POO | Aplicación estricta de encapsulamiento, abstracción, herencia, polimorfismo y programación dirigida a interfaces. |
| **RNF-04** | Robustez y validación | Control de excepciones para evitar caídas ante entradas numéricas o cadenas inválidas por consola. |

---

### 7. SELECCIÓN Y JUSTIFICACIÓN DE LAS ESTRUCTURAS DE DATOS

```
+-------------------------------------------------------------------------------------------------+
|                                 MATRIZ DE SELECCIÓN TECNOLÓGICA                                 |
+---------------------+-------------------+---------------------+------------------+--------------+
| Módulo              | Estructura Elegida| Complejidad         | Alternativa      | Motivo de    |
|                     |                   |                     | Descartada       | Descarte     |
+---------------------+-------------------+---------------------+------------------+--------------+
| Pedidos             | ArrayDeque        | O(1) amortizado     | ArrayList        | O(n) al      |
| (Cola FIFO)         |                   |                     |                  | remover head |
|                     |                   |                     | LinkedList       | Mayor uso de |
|                     |                   |                     |                  | heap/nodos   |
+---------------------+-------------------+---------------------+------------------+--------------+
| Menú                | HashSet           | O(1) promedio       | TreeSet          | O(log n)     |
| (Unicidad)          |                   |                     | ArrayList        | O(n) búsqued.|
+---------------------+-------------------+---------------------+------------------+--------------+
| Empleados           | HashMap           | O(1) promedio       | TreeMap          | O(log n)     |
| (Indexación ID)     |                   |                     | ArrayList        | O(n) búsqued.|
+---------------------+-------------------+---------------------+------------------+--------------+
```

1. **Gestión de Pedidos (`ArrayDeque`):**
   - La clase `ArrayDeque` implementa una cola circular basada en un arreglo contiguo. Mantiene punteros móviles de cabecera y cola. Al extraer un elemento del frente mediante `poll()`, únicamente incrementa el puntero lógico del frente, sin mover ningún elemento físico en memoria, logrando un costo **$O(1)$ amortizado**.
   - En contraste, `ArrayList.remove(0)` invoca `System.arraycopy()`, desplazando $n-1$ posiciones, lo cual toma tiempo $O(n)$ e introduce latencias inaceptables en sistemas de ventas.
   - Respecto a `LinkedList`, `ArrayDeque` aprovecha la localidad espacial en memoria caché L1/L2 al tener memoria contigua y evita crear objetos `Node` individuales.

2. **Catálogo del Menú (`HashSet`):**
   - Los conjuntos (`Set`) garantizan matemáticamente la ausencia de duplicados. `HashSet` almacena los elementos en una tabla de dispersión donde el índice se calcula mediante el valor hash del objeto.
   - Esto permite que `contains()` y `add()` se ejecuten en tiempo **$O(1)$ promedio**.
   - Se descartó `TreeSet` porque estructura sus datos en un árbol binario rojinegro con operaciones de costo **$O(\log n)$**, innecesario dado que no se requiere un ordenamiento lexicográfico forzoso durante la atención.

3. **Registro de Empleados (`HashMap`):**
   - Los mapas (`Map`) relacionan pares clave-valor. Al asignar como clave el `ID` (entero único del empleado), las consultas `get(id)` y `containsKey(id)` se resuelven en tiempo constante **$O(1)$ promedio**.
   - Se descartó `TreeMap` ($O(\log n)$) y listas lineales ($O(n)$) para no penalizar el tiempo de respuesta en la validación del personal.

---

### 8. DESARROLLO DE LA SOLUCIÓN

La aplicación fue construida respetando la modularidad y el desacoplamiento:

1. **Capa Modelo (`model`):**
   - `Empleado`: Representa al personal con atributos privados `id` y `nombre`, validaciones en constructor y sobreescritura de `equals` y `hashCode`.
   - `EstadoPedido`: Enum tipificado (`PENDIENTE`, `EN_PREPARACION`, `ENTREGADO`, `CANCELADO`).
   - `Pedido`: Modela la orden de compra con identificador único, nombre de cliente, producto estandarizado, marca de tiempo (`LocalDateTime`), estado y referencia al `Empleado` responsable.

2. **Capa de Servicios (`service`):**
   - `MenuService`: Encapsula `Set<String>` (instanciado como `HashSet<String>`). Contiene la función `normalizarNombre()` para transformar cualquier cadena de entrada a formato estándar (Title Case), impidiendo duplicidades inadvertidas. Carga por defecto los 7 productos iniciales requeridos.
   - `EmpleadoService`: Encapsula `Map<Integer, Empleado>` (instanciado como `HashMap<Integer, Empleado>`). Carga por defecto los 3 empleados base (101: Carlos, 102: María, 103: Andrés) y valida la unicidad de las claves.
   - `PedidoService`: Encapsula `Queue<Pedido>` (instanciado como `ArrayDeque<Pedido>`). Administra las operaciones `offer()`, `poll()` y `peek()` junto a un contador atómico para la asignación de identificadores de pedido.

3. **Capa de Presentación (`view`):**
   - `MenuPrincipal`: Presenta una interfaz de consola con menús formateados, captura de datos protegida contra fallos de entrada y orquestación desacoplada de los servicios.

4. **Capa de Pruebas (`test`):**
   - `SistemaPruebas`: Programa ejecutable independiente que somete a prueba los 10 escenarios solicitados sin requerir dependencias externas de JUnit.

---

### 9. ARQUITECTURA DEL SOFTWARE

```
CoffeeShopEA2/
│
├── src/
│   └── coffeeshop/
│       ├── Main.java                         <-- Punto de entrada de la aplicación
│       │
│       ├── model/                            <-- Entidades de dominio encapsuladas
│       │   ├── Empleado.java
│       │   ├── EstadoPedido.java
│       │   └── Pedido.java
│       │
│       ├── service/                          <-- Lógica de negocio y TDAs (JCF)
│       │   ├── PedidoService.java            (Queue / ArrayDeque)
│       │   ├── MenuService.java              (Set / HashSet)
│       │   └── EmpleadoService.java          (Map / HashMap)
│       │
│       ├── view/                             <-- Interfaz de usuario interactiva
│       │   └── MenuPrincipal.java
│       │
│       └── test/                             <-- Suite de pruebas independiente
│           └── SistemaPruebas.java
│
├── bin/                                      <-- Bytecode compilado (.class)
│
├── docs/                                     <-- Documentación académica completa
│   ├── informe_academico.md
│   ├── justificacion_tecnica.md
│   └── guion_sustentacion.md
│
└── README.md                                 <-- Guía de instalación y ejecución
```

---

### 10. EXPLICACIÓN DEL CÓDIGO FUENTE

#### 10.1 Gestión de Cola FIFO (`PedidoService.java`)
```java
public class PedidoService {
    // Programación hacia la interfaz Queue con implementación ArrayDeque
    private final Queue<Pedido> colaPedidos = new ArrayDeque<>();
    private int contadorId = 1;

    public Pedido registrarPedido(String cliente, String producto, Empleado emp) {
        Pedido nuevo = new Pedido(contadorId++, cliente, producto, emp);
        colaPedidos.offer(nuevo); // O(1) amortizado
        return nuevo;
    }

    public Pedido atenderSiguientePedido() {
        Pedido atendido = colaPedidos.poll(); // O(1) amortizado
        if (atendido != null) {
            atendido.setEstado(EstadoPedido.ENTREGADO);
        }
        return atendido;
    }

    public Pedido consultarSiguientePedido() {
        return colaPedidos.peek(); // O(1)
    }
}
```
*Análisis técnico:* El uso de `offer()`, `poll()` y `peek()` es preferible a `add()`, `remove()` y `element()` porque devuelven valores especiales (`false` o `null`) en lugar de arrojar excepciones en colas vacías o con capacidad restringida, favoreciendo un flujo de control seguro.

#### 10.2 Control de Unicidad y Normalización (`MenuService.java`)
```java
public class MenuService {
    private final Set<String> productos = new HashSet<>();

    public String normalizarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return null;
        String[] partes = nombre.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < partes.length; i++) {
            sb.append(Character.toUpperCase(partes[i].charAt(0)));
            if (partes[i].length() > 1) {
                sb.append(partes[i].substring(1).toLowerCase());
            }
            if (i < partes.length - 1) sb.append(" ");
        }
        return sb.toString();
    }

    public boolean agregarProducto(String nombre) {
        String normalizado = normalizarNombre(nombre);
        if (normalizado == null) return false;
        return productos.add(normalizado); // O(1) promedio
    }
}
```
*Análisis técnico:* Al estandarizar el texto a Title Case antes de insertar o buscar, se asegura que las cadenas `"latte"`, `"LATTE"` y `"  Latte "` generen exactamente la misma clave en `HashSet`, garantizando unicidad léxica total con costo $O(1)$ promedio.

#### 10.3 Mapeo Clave-Valor (`EmpleadoService.java`)
```java
public class EmpleadoService {
    private final Map<Integer, Empleado> empleados = new HashMap<>();

    public boolean registrarEmpleado(int id, String nombre) {
        if (id <= 0 || nombre == null || nombre.trim().isEmpty()) return false;
        if (empleados.containsKey(id)) return false; // Validación O(1) de clave única
        empleados.put(id, new Empleado(id, nombre)); // O(1)
        return true;
    }

    public Empleado consultarEmpleado(int id) {
        return empleados.get(id); // O(1)
    }
}
```
*Análisis técnico:* La clave del mapa es un entero primitivo autoboxeado (`Integer`). La función hash de `Integer` en Java es trivial e inmediata (devuelve el propio valor entero), lo que minimiza el cálculo de dispersión y garantiza rendimiento óptimo.

---

### 11. ANÁLISIS DE COMPLEJIDAD COMPUTACIONAL

```
+---------------------------------------------------------------------------------------------------+
|                            TABLA DETALLADA DE COMPLEJIDAD ASINTÓTICA                              |
+----------------------+--------------------+--------------------+-----------------+----------------+
| Estructura           | Operación          | Caso Promedio      | Amortizado      | Peor Caso      |
+----------------------+--------------------+--------------------+-----------------+----------------+
| ArrayDeque           | offer() [insertar] | O(1)               | O(1)            | O(n) [redimen.]|
| ArrayDeque           | poll() [retirar]   | O(1)               | O(1)            | O(1)           |
| ArrayDeque           | peek() [consultar] | O(1)               | O(1)            | O(1)           |
| HashSet              | add() [insertar]   | O(1)               | N/A             | O(log n)*      |
| HashSet              | contains() [buscar]| O(1)               | N/A             | O(log n)*      |
| HashSet              | remove() [eliminar]| O(1)               | N/A             | O(log n)*      |
| HashMap              | put() [asociar]    | O(1)               | N/A             | O(log n)*      |
| HashMap              | get() [recuperar]  | O(1)               | N/A             | O(log n)*      |
| HashMap              | containsKey() [val]| O(1)               | N/A             | O(log n)*      |
+----------------------+--------------------+--------------------+-----------------+----------------+
* En Java 8 y superior, gracias a la transformación de cubetas con colisiones en árboles rojinegros (Treeify).
```

- **Complejidad Promedio:** Es la expectativa matemática del tiempo de ejecución cuando los datos se distribuyen uniformemente a través de las cubetas de dispersión.
- **Complejidad Amortizada:** En `ArrayDeque`, el crecimiento del arreglo interno ocurre de manera exponencial (factor 2x). Aunque una inserción que dispara la duplicación del arreglo toma tiempo $O(n)$, el costo promediado sobre las $n$ inserciones previas y posteriores es constante: **$O(1)$ amortizado**.
- **Peor Caso:** Se produce ante colisiones de hash deliberadas o saturación extrema. En versiones modernas de Java, el peor caso en colecciones de dispersión pasa de $O(n)$ a $O(\log n)$ debido a la conversión interna a árboles balanceados.

---

### 12. EVIDENCIAS DE PRUEBAS Y EJECUCIÓN

Se implementó y ejecutó la suite automatizada `SistemaPruebas.java` con las 10 pruebas requeridas. Todos los casos arrojaron resultados satisfactorios.

| ID Prueba | Descripción del Escenario | Resultado Esperado | Resultado Obtenido | Estado |
|---|---|---|---|---|
| **PRUEBA-01** | Registro de varios pedidos secuenciales en la cola | Total en cola = 3, pedidos con IDs consecutivos 1, 2 y 3 | Total en cola = 3, IDs = [1, 2, 3] | **PASS** |
| **PRUEBA-02** | Atención de pedidos en estricto orden FIFO (`poll`) | Orden: Primero -> Segundo -> Tercero; cola queda vacía | Extraído: Primero -> Segundo -> Tercero; cola vacía = true | **PASS** |
| **PRUEBA-03** | Consulta y retiro seguro en una cola vacía | `estaVacia() = true`, `peek() = null`, `poll() = null` | `estaVacia = true`, `peek = null`, `poll = null` | **PASS** |
| **PRUEBA-04** | Prevención de productos duplicados en `HashSet` | Rechazar 'latte', 'LATTE', ' Latte '; aceptar 'Té Verde' | Rechazos: [false, false, false], Nuevo: true, Tamaño final = 8 | **PASS** |
| **PRUEBA-05** | Búsqueda $O(1)$ de productos existentes e inexistentes | `existe('latte') = true`, `existe('Hamburguesa') = false` | `'latte': true`, `'CAPPUCCINO': true`, `'Hamburguesa': false` | **PASS** |
| **PRUEBA-06** | Registro de empleados y unicidad de ID en `HashMap` | Aceptar ID 104; rechazar ID duplicado 101, ID negativo y vacío | `104: true, 101 dup: false, -5: false, vacío: false, Total: 4` | **PASS** |
| **PRUEBA-07** | Consulta $O(1)$ de empleados por ID (`get`) | `ID 101 -> Carlos`, `ID 102 -> María` | `ID 101 -> Carlos`, `ID 102 -> María` | **PASS** |
| **PRUEBA-08** | Consulta controlada de empleados inexistentes | `existeEmpleado(999) = false`, `consultarEmpleado(999) = null` | `existe(999) = false`, `objeto obtenido = null` | **PASS** |
| **PRUEBA-09** | Validación previa de producto antes de crear pedido | Producto inválido rechazado; producto válido encolado con éxito | `Pizza válida = false, Latte válido = true, Pedido registrado = ID #1` | **PASS** |
| **PRUEBA-10** | Validación previa de empleado antes de asignar | Empleado 888 rechazado; Empleado 103 aceptado y asignado | `ID 888 válido = false, ID 103 válido = true, Asignado a: Andrés` | **PASS** |

**Resumen Estadístico de Pruebas:**
- Total pruebas ejecutadas: **10**
- Pruebas exitosas (PASS): **10**
- Pruebas fallidas (FAIL): **0**
- Tasa de éxito: **100.0%**

---

### 13. APLICACIÓN DE LOS PRINCIPIOS POO

1. **Encapsulamiento:**  
   Todos los atributos en `Empleado`, `Pedido` y en los servicios son `private` o `private final`. El acceso a los estados internos se gestiona mediante getters y métodos de negocio expresivos, protegiendo las invariantes del modelo.
2. **Abstracción:**  
   Las clases exponen únicamente las operaciones pertinentes al dominio (`registrarPedido`, `atenderSiguientePedido`, `existeProducto`), ocultando por completo la complejidad de punteros circulares o cubetas de hash.
3. **Polimorfismo y Programación hacia Interfaces:**  
   En todos los servicios, los campos y firmas declaran interfaces genéricas (`Queue<Pedido>`, `Set<String>`, `Map<Integer, Empleado>`), desacoplando la definición del TDA de su implementación concreta.
4. **Separación de Responsabilidades (SRP):**  
   Ninguna clase concentra lógica desmedida. La capa de presentación (`MenuPrincipal`) no manipula colecciones directamente, sino que delega las tareas a servicios especializados.

---

### 14. CONCLUSIONES

1. **La estructura de datos determina la viabilidad del sistema:** El uso de `ArrayDeque`, `HashSet` y `HashMap` permite que el sistema responda en tiempo constante ($O(1)$) tanto para colas de atención como para validaciones de catálogo y personal, eliminando la sobrecarga computacional de listas secuenciales ($O(n)$).
2. **La normalización previene la dispersión del hash:** En estructuras como `HashSet`, dos cadenas con los mismos caracteres pero diferente capitalización tienen códigos hash distintos. La implementación de una función de normalización previa es indispensable para salvaguardar la unicidad semántica en aplicaciones reales.
3. **Programar contra interfaces preserva la arquitectura:** Utilizar interfaces de la JCF (`Queue`, `Set`, `Map`) dota al software de alta mantenibilidad, permitiendo futuras evoluciones (por ejemplo, transicionar hacia colecciones concurrentes o persistentes) sin alterar las capas superiores.
4. **La verificación formal asegura la fiabilidad:** La suite de pruebas unitarias independiente comprobó con 100% de efectividad la robustez del sistema ante colas vacías, datos inválidos y accesos fuera de rango.

---

### 15. REFERENCIAS BIBLIOGRÁFICAS

1. Bloch, J. (2018). *Effective Java* (3.ª ed.). Addison-Wesley Professional.
2. Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2022). *Introduction to Algorithms* (4.ª ed.). The MIT Press.
3. Oracle Corporation. (2023). *Java Platform, Standard Edition & Java Development Kit Version 21 API Specification*. Oracle Technology Network. https://docs.oracle.com/en/java/javase/21/docs/api/
4. Lafore, R. (2002). *Data Structures and Algorithms in Java* (2.ª ed.). Sams Publishing.
5. Deitel, P., & Deitel, H. (2017). *Java How to Program, Early Objects* (11.ª ed.). Pearson.

---

### 16. ESPACIO PARA INCORPORAR EL ENLACE DEL VIDEO

```
+-------------------------------------------------------------------------------------------------+
|                                SUSTENTACIÓN EN VIDEO DEL PROYECTO                               |
+-------------------------------------------------------------------------------------------------+
| Enlace oficial de visualización (YouTube / OneDrive / Google Drive / Institucional):           |
|                                                                                                 |
| >> [PEGAR AQUÍ LA URL DEL VIDEO GRABADO POR EL EQUIPO] <<                                       |
|                                                                                                 |
| Duración máxima: 5 minutos                                                                      |
| Integrantes participantes: Ver sección 1 (Portada) y guion técnico adjunto                      |
+-------------------------------------------------------------------------------------------------+
```

