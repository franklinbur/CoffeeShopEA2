# CoffeeShopEA2 – Sistema de Gestión de Pedidos de una Cafetería

**Asignatura:** Estructuras de Datos  
**Programa:** Tecnología en Desarrollo de Software  
**Institución:** Institución Universitaria Digital de Antioquia (IU Digital)  
**Entorno recomendado:** Visual Studio Code con Java Extension Pack  
**Lenguaje:** Java (JDK 17 o superior, verificado en JDK 21) – Sin dependencias ni frameworks externos.

---

## 📋 Descripción del Proyecto

**CoffeeShopEA2** es una aplicación académica orientada a resolver los problemas de gestión de pedidos, catálogo de productos y administración de personal de una cafetería mediante el uso eficiente y fundamentado del **Java Collections Framework (JCF)** y los principios de la **Programación Orientada a Objetos (POO)**.

El sistema demuestra cómo seleccionar, implementar y justificar las estructuras de datos óptimas según los requerimientos de complejidad temporal ($O(1)$ amortizado y promedio), garantizando un diseño limpio en capas, desacoplado y robusto.

---

## ⚙️ Módulos y Estructuras de Datos Implementadas

| Módulo | Tipo de Dato Abstracto | Interfaz JCF | Implementación Concreta | Complejidad Clave | Justificación Resumida |
|---|---|---|---|---|---|
| **1. Gestión de Pedidos** | Cola FIFO | `Queue<Pedido>` | `ArrayDeque<Pedido>` | Inserción: **$O(1)$ amortizado**<br>Extracción: **$O(1)$ amortizado**<br>Consulta: **$O(1)$** | Arreglo circular dinámico. Despacha en estricto orden de llegada (`poll`), encola de inmediato (`offer`) y consulta turno (`peek`). Supera a `ArrayList` (que toma $O(n)$ al remover el frente) y a `LinkedList` (menor uso de memoria y mejor localidad de caché). |
| **2. Catálogo del Menú** | Conjunto (*Set*) | `Set<String>` | `HashSet<String>` | Búsqueda: **$O(1)$ promedio**<br>Inserción: **$O(1)$ promedio** | Garantiza unicidad matemática (sin duplicados). Incorpora normalización insensible a mayúsculas/minúsculas. Supera a `TreeSet` ($O(\log n)$) y a `ArrayList` ($O(n)$). |
| **3. Gestión de Personal** | Diccionario Clave-Valor | `Map<Integer, Empleado>` | `HashMap<Integer, Empleado>` | Búsqueda: **$O(1)$ promedio**<br>Validación: **$O(1)$ promedio** | Asocia el ID único con la entidad del empleado. Permite validar en tiempo constante si un empleado existe antes de asignarlo a un pedido. Supera a `TreeMap` ($O(\log n)$) y a listas lineales ($O(n)$). |

---

## 📂 Arquitectura del Proyecto

```
CoffeeShopEA2/
│
├── src/
│   └── coffeeshop/
│       ├── Main.java                         # Punto de entrada de la aplicación interactiva
│       │
│       ├── model/                            # Modelos de dominio encapsulados
│       │   ├── Empleado.java                 # Entidad Empleado con ID y nombre
│       │   ├── EstadoPedido.java             # Enum con estados del pedido
│       │   └── Pedido.java                   # Entidad Pedido con cliente, producto y empleado
│       │
│       ├── service/                          # Lógica de negocio y TDAs (JCF)
│       │   ├── PedidoService.java            # Cola FIFO con ArrayDeque
│       │   ├── MenuService.java              # Conjunto de productos con HashSet
│       │   └── EmpleadoService.java          # Mapa de empleados con HashMap
│       │
│       ├── view/                             # Capa de presentación por consola
│       │   └── MenuPrincipal.java            # Menú interactivo con 11 opciones y validaciones
│       │
│       └── test/                             # Suite de pruebas automatizada e independiente
│           └── SistemaPruebas.java           # 10 pruebas unitarias reproducibles
│
├── bin/                                      # Directorio de clases compiladas (.class)
│
├── docs/                                     # Documentación académica formal
│   ├── informe_academico.md                  # Informe completo con portada y marco teórico
│   ├── justificacion_tecnica.md              # Comparativa de complejidades y diagramas
│   └── guion_sustentacion.md                 # Guion para video de sustentación (máx. 5 min)
│
└── README.md                                 # Guía de uso y ejecución del proyecto
```

---

## 🚀 Instrucciones de Compilación y Ejecución

### Requisitos Previos:
- JDK 17 o superior instalado (`javac -version` y `java -version`).
- Terminal PowerShell, CMD o Bash en Visual Studio Code.

### 1. Compilación del Código Fuente
Desde la carpeta raíz del proyecto (`CoffeeShopEA2`):

**En Windows (PowerShell):**
```powershell
# Crear carpeta de binarios si no existe
New-Item -ItemType Directory -Path bin -Force

# Compilar todos los archivos Java con codificación UTF-8
$archivos = (Get-ChildItem -Path src -Filter *.java -Recurse).FullName
javac -encoding UTF-8 -d bin $archivos
```

**En Linux / macOS / Git Bash:**
```bash
mkdir -p bin
javac -encoding UTF-8 -d bin $(find src -name "*.java")
```

---

### 2. Ejecutar la Aplicación Interactiva (`Main`)
Para iniciar el menú de la cafetería con las 11 opciones interactivas:

**En Windows / Linux / macOS:**
```bash
java -cp bin coffeeshop.Main
```

#### Menú Interactivo de la Aplicación:
```text
+--------------------------------------------------------------------+
|                COFFEESHOP EA2 - SISTEMA DE GESTION                 |
|             Institucion Universitaria Digital de Antioquia         |
+--------------------------------------------------------------------+
|  MODULO DE PEDIDOS (Queue / ArrayDeque - FIFO O(1) amortizado)      |
|    1. Registrar pedido                                             |
|    2. Atender siguiente pedido                                     |
|    3. Consultar siguiente pedido (peek)                            |
|    4. Mostrar pedidos pendientes                                   |
|                                                                    |
|  MODULO DE MENU (Set / HashSet - Unicidad O(1) promedio)           |
|    5. Agregar producto al menu                                     |
|    6. Consultar si un producto existe                              |
|    7. Mostrar catalogo de productos                                |
|                                                                    |
|  MODULO DE EMPLEADOS (Map / HashMap - Busqueda por ID O(1) prom.)   |
|    8. Registrar empleado                                           |
|    9. Consultar empleado por ID                                    |
|   10. Mostrar todos los empleados                                  |
|                                                                    |
|  11. Salir del sistema                                             |
+--------------------------------------------------------------------+
```

---

### 3. Ejecutar la Suite de Pruebas Automatizadas (`SistemaPruebas`)
La suite ejecuta de manera autónoma las 10 pruebas requeridas por la guía académica, reportando los valores esperados, obtenidos y el veredicto final:

```bash
java -cp bin coffeeshop.test.SistemaPruebas
```

#### Cobertura de las Pruebas:
1. `PRUEBA-01`: Registro de varios pedidos secuenciales en la cola (`ArrayDeque`).
2. `PRUEBA-02`: Atención de pedidos en estricto orden FIFO (`poll`).
3. `PRUEBA-03`: Consulta y retiro seguro en una cola vacía (`peek` y `poll`).
4. `PRUEBA-04`: Prevención de productos duplicados en `HashSet` con normalización.
5. `PRUEBA-05`: Búsqueda $O(1)$ de productos existentes e inexistentes (`contains`).
6. `PRUEBA-06`: Registro de empleados y validación de unicidad de ID en `HashMap`.
7. `PRUEBA-07`: Consulta $O(1)$ de empleados por ID (`get`).
8. `PRUEBA-08`: Consulta controlada de empleados inexistentes (`null` seguro).
9. `PRUEBA-09`: Validación de producto en menú antes de permitir encolar un pedido.
10. `PRUEBA-10`: Validación de empleado en sistema antes de asignarlo al pedido.

---

## 📚 Documentación Académica Incluida

En la carpeta [`docs/`](./docs/) encontrará los documentos exigidos para la entrega:

1. **[Informe Académico Completo (`docs/informe_academico.md`)](./docs/informe_academico.md):**  
   Contiene portada con campos editables, objetivos, análisis de requerimientos, marco conceptual, evidencias de pruebas, principios POO, conclusiones, referencias en formato formal y espacio para el enlace del video.
2. **[Justificación Técnica y Diagramas (`docs/justificacion_tecnica.md`)](./docs/justificacion_tecnica.md):**  
   Comparativas profundas entre `ArrayDeque` vs `ArrayList`/`LinkedList`, `HashSet` vs `TreeSet`, `HashMap` vs `TreeMap`, diferenciación de casos promedio/amortizado/peor caso, y diagramas en formato Mermaid (UML de clases, flujo de pedido y estructuras).
3. **[Guion para Video de Sustentación (`docs/guion_sustentacion.md`)](./docs/guion_sustentacion.md):**  
   Guion estructurado paso a paso para un video de máximo 5 minutos, con tiempos precisos y distribución de roles entre 3 a 5 estudiantes.

