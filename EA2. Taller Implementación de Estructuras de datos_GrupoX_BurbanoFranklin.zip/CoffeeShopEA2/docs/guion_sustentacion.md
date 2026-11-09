# GUION TÉCNICO PARA VIDEO DE SUSTENTACIÓN (MÁXIMO 5 MINUTOS)

## Proyecto: CoffeeShopEA2 – Sistema de Gestión de Pedidos de una Cafetería
**Asignatura:** Estructuras de Datos  
**Programa:** Tecnología en Desarrollo de Software  
**Institución Universitaria Digital de Antioquia (IU Digital)**  

---

### DATOS EDITABLES DEL EQUIPO

- **Docente:** `[NOMBRE DEL DOCENTE ASIGNADO]`
- **Grupo:** `[NÚMERO O CÓDIGO DE GRUPO]`
- **Fecha:** `[FECHA DE ENTREGA O GRABACIÓN]`
- **Enlace del Video:** `[INSERTAR ENLACE DE YOUTUBE / DRIVE / STREAMING]`

#### Distribución de Integrantes:
- **Integrante 1:** `[NOMBRE COMPLETO DEL ESTUDIANTE 1]` – *Introducción, Problema y Arquitectura General* (Minuto 0:00 - 1:00)
- **Integrante 2:** `[NOMBRE COMPLETO DEL ESTUDIANTE 2]` – *Módulo de Pedidos: TDA Cola y ArrayDeque* (Minuto 1:00 - 2:00)
- **Integrante 3:** `[NOMBRE COMPLETO DEL ESTUDIANTE 3]` – *Módulo de Menú: TDA Set y HashSet* (Minuto 2:00 - 2:50)
- **Integrante 4:** `[NOMBRE COMPLETO DEL ESTUDIANTE 4]` – *Módulo de Empleados: TDA Map y HashMap* (Minuto 2:50 - 3:45)
- **Integrante 5:** `[NOMBRE COMPLETO DEL ESTUDIANTE 5]` – *Demostración en Vivo, Pruebas y Conclusiones* (Minuto 3:45 - 5:00)

> *Nota de adaptación:* Si el grupo está conformado por 3 o 4 integrantes, el Integrante 1 puede asumir la introducción y menú, y el Integrante 4 puede fusionar la demostración con las conclusiones.

---

## CRONOGRAMA Y GUION DETALLADO PASO A PASO

### BLOQUE 1: INTRODUCCIÓN Y CONTEXTO DEL PROBLEMA (0:00 – 1:00)
**Responsable:** Integrante 1 (`[NOMBRE DEL ESTUDIANTE 1]`)  
**En pantalla:** Portada del proyecto, diapositiva con el problema del negocio y vista general de la arquitectura en Visual Studio Code.

- **Diálogo del expositor:**
  > "Cordial saludo, profesor y compañeros. Mi nombre es **[NOMBRE 1]**, y junto con mis compañeros **[NOMBRES DE LOS INTEGRANTES]**, presentamos el proyecto académico **CoffeeShopEA2** para la asignatura de Estructuras de Datos de la IU Digital de Antioquia.
  >
  > El caso de estudio aborda una problemática crítica en el sector gastronómico: una cafetería experimentaba demoras, desorden en los turnos de entrega y errores al asociar productos y empleados en horas pico.
  >
  > Para resolverlo, desarrollamos una solución en Java puro —sin frameworks ni dependencias externas— aplicando Programación Orientada a Objetos, el Java Collections Framework y arquitectura en capas separadas: Model, Service y View.
  >
  > El objetivo central no fue únicamente programar un menú funcional, sino **demostrar y justificar formalmente** la selección de las estructuras de datos óptimas según la complejidad algorítmica requerida."

---

### BLOQUE 2: MÓDULO DE PEDIDOS Y ESTRUCTURA ARRAYDEQUE (1:00 – 2:00)
**Responsable:** Integrante 2 (`[NOMBRE DEL ESTUDIANTE 2]`)  
**En pantalla:** Código de `PedidoService.java` mostrando `colaPedidos = new ArrayDeque<>()`, `offer()`, `poll()`, `peek()`.

- **Diálogo del expositor:**
  > "Mi nombre es **[NOMBRE 2]** y les explicaré el Módulo de Gestión de Pedidos.
  >
  > El requerimiento del negocio exige atender a los clientes en estricto orden de llegada, lo que computacionalmente se define como una cola FIFO (*First-In, First-Out*).
  >
  > Implementamos la interfaz `Queue` mediante la clase concreta `ArrayDeque`.
  >
  > ¿Por qué elegimos `ArrayDeque` sobre `LinkedList` o `ArrayList`?
  > Primero: frente a `ArrayList`, cuando atendemos un pedido con `poll()`, `ArrayList` requeriría desplazar todos los elementos en memoria, tomando tiempo lineal $O(n)$. `ArrayDeque`, al ser un arreglo circular dinámico, extrae el frente en tiempo **$O(1)$ amortizado**.
  > Segundo: frente a `LinkedList`, `ArrayDeque` no crea objetos `Node` adicionales por cada pedido, lo que reduce drásticamente el consumo de memoria en el *heap* y mejora la localidad espacial en la memoria caché del procesador.
  >
  > Usamos `offer()` para encolar de inmediato, `poll()` para despachar y `peek()` para consultar el siguiente turno sin modificar la cola."

---

### BLOQUE 3: MÓDULO DEL MENÚ Y ESTRUCTURA HASHSET (2:00 – 2:50)
**Responsable:** Integrante 3 (`[NOMBRE DEL ESTUDIANTE 3]`)  
**En pantalla:** Código de `MenuService.java` mostrando `Set<String> productos = new HashSet<>()` y la función `normalizarNombre()`.

- **Diálogo del expositor:**
  > "Buenas tardes, mi nombre es **[NOMBRE 3]**. Me corresponde la gestión del Menú de productos.
  >
  > Los requerimientos exigían dos condiciones clave: evitar productos duplicados y realizar búsquedas instantáneas antes de autorizar un pedido.
  >
  > Seleccionamos la interfaz `Set` con la implementación `HashSet`.
  >
  > `HashSet` calcula el código de dispersión mediante `hashCode()`, logrando inserción y búsqueda con el método `contains()` en tiempo **$O(1)$ promedio**.
  >
  > ¿Por qué no usamos `TreeSet`? Porque `TreeSet` organiza los elementos en un árbol rojinegro balanceado, lo que eleva el costo de búsqueda a $O(\log n)$. Como nuestra prioridad es la velocidad de consulta instantánea y no mantener el menú ordenado alfabéticamente en todo momento, `HashSet` es la estructura matemáticamente superior.
  >
  > Además, desarrollamos el método `normalizarNombre()` para convertir las entradas a formato estándar, garantizando que variaciones como 'latte', 'LATTE' o ' Latte ' no generen duplicados."

---

### BLOQUE 4: MÓDULO DE EMPLEADOS Y ESTRUCTURA HASHMAP (2:50 – 3:45)
**Responsable:** Integrante 4 (`[NOMBRE DEL ESTUDIANTE 4]`)  
**En pantalla:** Código de `EmpleadoService.java` mostrando `Map<Integer, Empleado> empleados = new HashMap<>()`, `containsKey()` y `get()`.

- **Diálogo del expositor:**
  > "Mi nombre es **[NOMBRE 4]** y voy a explicar la Gestión del Personal.
  >
  > La cafetería requiere autenticar al barista o mesero responsable de cada pedido mediante un ID único. Para este comportamiento de diccionario utilizamos la interfaz `Map` implementada con `HashMap`.
  >
  > La clave es el número entero del `ID` y el valor es el objeto `Empleado`.
  >
  > Gracias a la indexación por tablas hash, los métodos `containsKey(id)` y `get(id)` operan en tiempo constante **$O(1)$ promedio**.
  >
  > Descartamos `TreeMap` porque incurre en $O(\log n)$ y un `ArrayList` porque nos obligaría a hacer una búsqueda lineal de costo $O(n)$ en cada pedido. Con `HashMap`, la validación de personal es prácticamente instantánea sin importar si la empresa tiene 10 o 10.000 colaboradores."

---

### BLOQUE 5: PRUEBAS EN VIVO, CONCLUSIONES Y CIERRE (3:45 – 5:00)
**Responsable:** Integrante 5 (`[NOMBRE DEL ESTUDIANTE 5]`)  
**En pantalla:** Terminal de comandos ejecutando `SistemaPruebas` (10/10 PASS) y demostración rápida del menú interactivo registrando un pedido válido, uno inválido y atendiendo.

- **Diálogo del expositor:**
  > "Para finalizar, mi nombre es **[NOMBRE 5]**. Procederemos con la demostración en vivo.
  >
  > Primero ejecutamos nuestra suite automatizada `SistemaPruebas`. Como observan en la consola, se ejecutan las 10 pruebas unitarias requeridas:
  > - Verificación del orden FIFO estricto.
  > - Rechazo de productos duplicados.
  > - Búsqueda en menú con $O(1)$.
  > - Validación cruzada que bloquea pedidos con productos o empleados inexistentes.
  > Obteniendo una tasa de éxito del 100%.
  >
  > Ahora abrimos el menú interactivo con `Main`:
  > Al intentar registrar un pedido con un producto que no está en el menú, el sistema rechaza la operación informando al usuario. Cuando ingresamos un producto válido y un empleado registrado, el pedido se encola inmediatamente. Al pulsar la opción 2, el pedido es despachado en estricto orden de llegada.
  >
  > En conclusión:
  > 1. El uso de interfaces garantiza polimorfismo y bajo acoplamiento.
  > 2. `ArrayDeque`, `HashSet` y `HashMap` ofrecen las mejores complejidades temporales para las operaciones críticas del negocio ($O(1)$ amortizado y promedio).
  >
  > Muchas gracias por su atención."

