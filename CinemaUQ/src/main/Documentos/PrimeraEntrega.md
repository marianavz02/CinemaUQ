# Documento de Análisis y Diseño - Proyecto CinemaUQ

## 1. Actores del Sistema
Para este proyecto, identifiqué dos tipos de usuarios principales que interactúan con la plataforma:
* **Cliente:** Es el usuario final que se registra en la aplicación. Su rol principal es consultar la cartelera, elegir qué película ver, seleccionar sus asientos y comprar entradas junto con productos de confitería. Además, es quien administra su propia tarjeta virtual, acumula y redime puntos, y gestiona el historial o las cancelaciones de sus compras.
* **Administrador:** Es el usuario encargado de la gestión integral del cine. Tiene privilegios para crear y modificar películas, configurar las salas y funciones, gestionar el inventario de confitería y promociones, recargar las tarjetas de los clientes y generar los reportes operativos y visuales del negocio.

## 2. Requisitos Funcionales (RF)
He codificado los requisitos funcionales mínimos que la plataforma debe cumplir para operar correctamente:
* **RF-001:** Permitir el registro de usuarios y el inicio de sesión para diferenciar si quien ingresa es un Cliente o el Administrador.
* **RF-002:** Permitir al administrador registrar, editar y configurar las películas, las salas de cine y la distribución física de sus asientos.
* **RF-003:** Programar nuevas funciones vinculando una película específica con una sala, en una fecha y hora determinadas.
* **RF-004:** Permitir al cliente explorar la cartelera y filtrar las funciones disponibles por fecha, hora o sala.
* **RF-005:** Mostrar una interfaz gráfica con la disponibilidad en tiempo real de los asientos (disponibles, ocupados, seleccionados, accesibles) para evitar vender el mismo asiento dos veces.
* **RF-006:** Permitir al cliente agregar productos individuales y combos de confitería a su carrito de compras.
* **RF-007:** Procesar los pagos exclusivamente a través de la tarjeta virtual del cliente, validando primero que esté activa y que el saldo sea suficiente para cubrir el total.
* **RF-008:** Permitir al administrador realizar recargas de saldo en las tarjetas virtuales de los clientes, guardando el registro de la transacción.
* **RF-009:** Gestionar cancelaciones de compras y aplicar reembolsos automáticos a la tarjeta según las políticas de tiempo (devolución del 100% si faltan más de 24h, 80% si faltan entre 2h y 24h, y sin reembolso si faltan menos de 2 horas).
* **RF-010:** Calcular y asignar "Puntos CinemaUQ" por cada compra, controlando que tengan exactamente un año de vigencia y que, al usarse, se descuenten primero los más antiguos.
* **RF-011:** Permitir que el cliente use sus puntos acumulados para pagar o canjear entradas y productos de confitería.
* **RF-012:** Generar reportes para el administrador, tanto exportables (PDF o TXT) como gráficos (diagramas de barras, pastel y líneas usando JavaFX) para analizar ventas, ocupación y recargas.

## 3. Reglas de Negocio Clave
Para garantizar que la lógica de la aplicación sea consistente, establecí las siguientes reglas:
* **RN-001 (Control de saldo):** No se puede concretar ninguna transacción si el costo total (entradas + comida + tarifa de servicio) supera el saldo de la tarjeta virtual, o si esta se encuentra inactiva.
* **RN-002 (Bloqueo de asientos):** En el instante en que se confirma y paga una compra, los asientos reservados pasan a estado *Ocupado* y desaparecen de la disponibilidad para el resto de los usuarios en esa función.
* **RN-003 (Política de reembolsos):** El sistema bloquea cualquier intento de cancelación si faltan menos de 2 horas para la función. Si la cancelación procede, el dinero solo se devuelve a la tarjeta virtual de origen y la compra se marca para que no pueda ser reembolsada dos veces.
* **RN-004 (Dinámica de puntos):** Por cada 10.000 COP gastados, el cliente recibe 10 puntos. Estos vencen a los 365 días. Para redimirlos, programé un comportamiento tipo *FIFO* (First In, First Out), asegurando que el cliente gaste primero los puntos que están más próximos a vencer.
* **RN-005 (Control de salas):** El sistema impide que se programen dos funciones en la misma sala si sus horarios se superponen.

## 4. Casos de Uso Principales
Definí cuatro flujos centrales que demuestran el funcionamiento del cine:
1. **Comprar Entradas y Confitería (Cliente):** El usuario navega la cartelera, elige una función, selecciona sus asientos de forma gráfica, añade un combo de palomitas y finaliza el flujo pagando con el saldo de su tarjeta virtual.
2. **Recargar Tarjeta Virtual (Administrador):** Un cliente se da cuenta de que no tiene saldo y solicita una recarga. El administrador busca la tarjeta en el sistema, ingresa el monto y la plataforma actualiza el saldo registrando el movimiento.
3. **Cancelar Compra (Cliente/Administrador):** Un usuario decide que no puede asistir. El sistema revisa la hora actual contra la hora de la función, calcula si le corresponde el 100% o el 80% de devolución, y retorna ese dinero a su tarjeta.
4. **Redimir Puntos (Cliente):** Al momento de pagar, el cliente decide usar sus puntos acumulados. El sistema valida su saldo de puntos vigentes, descuenta los necesarios para cubrir el producto y elimina los lotes de puntos más antiguos.

## 5. Modelo del Dominio (Entidades y Relaciones)
Tomando como base el diagrama UML que diseñé para la implementación, las clases principales y cómo se relacionan quedaron definidas de la siguiente manera:
* Tengo una clase abstracta `Usuario` de la cual heredan `Cliente` y `Administrador`.
* Cada `Cliente` tiene una relación de 1 a 1 con su `TarjetaVirtual`, y esta a su vez tiene muchos `MovimientoTarjeta` (1 a muchos).
* El cine se organiza así: una `Pelicula` tiene muchas `Funcion`es (1 a muchos), y una `Sala` también tiene muchas `Funcion`es asignadas (1 a muchos).
* A nivel físico, una `Sala` está compuesta por muchos `Asiento`s (1 a muchos).
* Cuando un cliente compra, se genera una `Compra`. Esta clase central agrupa al `Cliente`, la `Funcion`, múltiples `Boleta`s (que reservan los asientos) y los `DetalleConfiteria`.
* Para los productos, usé una clase abstracta `ProductoConfiteria` que se divide en `ProductoIndividual` y `Combo`.
* Finalmente, para la fidelización, el cliente tiene un registro de `PuntosLealtad` compuesto por múltiples `LotePuntos` para poder controlar las fechas de vencimiento de cada ganancia.

## 6. Patrones de Diseño y SOLID
Me aseguré de que el código respete los principios SOLID (por ejemplo, separando la lógica de la interfaz gráfica y usando abstracciones para los reportes). Además, implementé estos **tres patrones creacionales** para resolver problemas específicos que encontré durante el desarrollo:

### 1. Patrón Factory Method (Para la confitería)
* **Problema:** Crear productos es complejo porque un "Snack" individual no se inicializa igual que un "Combo" (que incluye varios productos y aplica un porcentaje de descuento). Llenar el código de *if/else* para instanciar esto iba a ser un problema a futuro.
* **Solución:** Implementé la clase `ProductoConfiteriaFactory`.
* **Justificación:** Me permite centralizar la creación de los productos. Si mañana el cine inventa un nuevo tipo de producto, solo agrego el método a la fábrica sin romper la lógica del carrito de compras.

### 2. Patrón Builder (Para armar las compras)
* **Problema:** Construir un objeto `Compra` requería pasarle demasiados parámetros al constructor: el cliente, la función, la lista de boletas, la lista de confitería, etc. Esto genera un código sucio y propenso a errores.
* **Solución:** Creé la clase `CompraBuilder`.
* **Justificación:** Me permite armar la compra paso a paso (ej. `builder.conAsientos(...).conConfiteria(...)`) de forma súper legible. Además, el método final `build()` me sirve como filtro para validar que la compra tenga sentido antes de registrarla en el sistema.

### 3. Patrón Singleton (Para la sesión del sistema)
* **Problema:** Necesitaba una forma de saber globalmente quién es el usuario que tiene la sesión iniciada en ese momento, sin tener que estar pasando el objeto "Usuario" de ventana en ventana por todo JavaFX.
* **Solución:** Implementé la clase `CinemaUQContext` con el patrón Singleton.
* **Justificación:** Al tener una única instancia compartida (`getInstance()`), cualquier controlador de la interfaz puede consultar fácilmente `getCurrentUser()` y saber si debe mostrar la vista de administrador o la del cliente, garantizando que los datos de la sesión no se dupliquen ni se pierdan.