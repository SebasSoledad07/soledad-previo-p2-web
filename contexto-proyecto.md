````markdown
# Instrucciones de Contexto: Backend Restaurante (Previo P2)

## 1. Estructura y Configuración del Proyecto

- **Nombre del Artefacto:** `apellido-previo-p2-web`
- **Paquetes Obligatorios:** `model`, `dao`, `service`, `controller`
- **Dependencia Principal:** Spring Boot Web (Spring Initializr)

---

## 2. Modelos de Datos (JavaBeans)

### Clase `Mesa`

| Campo       | Tipo    | Descripción                                  |
|-------------|---------|----------------------------------------------|
| id          | int     | Identificador único                          |
| numero      | int     | Número de mesa                               |
| capacidad   | int     | Capacidad máxima de personas                 |
| zona        | String  | interior, terraza, barra, privado            |
| activa      | boolean | Estado de disponibilidad de la mesa          |

### Clase `Reserva`

| Campo           | Tipo   | Descripción                          |
|-----------------|--------|--------------------------------------|
| id              | int    | Identificador único                  |
| mesaId          | int    | ID de la mesa reservada              |
| clienteNombre   | String | Nombre del cliente                   |
| fecha           | String | Formato `yyyy-MM-dd`                 |
| horaInicio      | String | Formato `HH:mm`                      |
| horaFin         | String | Formato `HH:mm`                      |
| numPersonas     | int    | Número de personas                   |

---

## 3. Capa de Persistencia (DAOs)

- Usar la anotación `@Repository`
- El almacenamiento debe realizarse en memoria usando:
  - `List<T>` privada
  - contador estático para IDs autoincrementales

### Reglas Específicas

#### `ReservaDAO`

Debe implementar:

```java
findByMesa(int mesaId)
````

Utilizando **Java Streams** con:

```java
.filter()
.collect()
```

### Consideraciones

* Los métodos `findById` deben retornar:

    * `null` si el ID no existe
    * No lanzar excepciones

---

## 4. Capa de Servicio y Reglas de Negocio

* Usar la anotación `@Service`

## Validaciones en `ReservaService`

### `mesaId`

* Debe existir en `MesaDAO`
* La mesa debe estar:

  ```java
  activa == true
  ```

### `clienteNombre`

* No puede ser nulo
* Debe tener mínimo 3 caracteres después de aplicar:

  ```java
  .trim()
  ```

### Horario

* `horaFin` debe ser posterior a `horaInicio`
* Comparación lexicográfica

### `numPersonas`

Debe cumplir:

```text
> 0
<= capacidad real de la mesa
```

La capacidad debe consultarse en el DAO correspondiente.

### Retorno del Método `validar`

Debe retornar:

```java
Map<String, String>
```

Incluyendo todos los errores encontrados.

---

# 5. Controladores y Seguridad

## AuthController (`/auth`)

### Usuarios Hardcodeados

| Usuario    | Contraseña |
| ---------- | ---------- |
| mesero1    | mes123     |
| mesero2    | mes456     |
| supervisor | sup789     |

### Manejo de Sesión

Usar:

```java
request.getSession(true)
```

Configurar:

```java
setMaxInactiveInterval(1800)
```

---

## ReservaController (`/reservas`)

### Seguridad

Implementar método:

```java
verificarSesion
```

Usando:

```java
getSession(false)
```

En cada endpoint.

### Manejo de Cookies

#### POST (guardar reserva)

Crear cookie:

```java
ultimaMesa
```

Configurar:

```java
setMaxAge(604800)
setHttpOnly(true)
```

#### GET

* Leer la cookie desde el request
* Incluir su valor en el header:

```text
X-Ultima-Mesa
```

---

## MesaController (`/mesas`)

### Acceso

* Público
* No requiere sesión

### Operaciones

CRUD completo con códigos HTTP:

| Código | Significado |
| ------ | ----------- |
| 200    | OK          |
| 201    | Created     |
| 204    | No Content  |
| 404    | Not Found   |

---

## Nota para el Agente

* Seguir estrictamente estas reglas de validación
* Mantener exactamente los nombres de paquetes indicados
* No utilizar bases de datos externas
* Todo el almacenamiento debe realizarse en memoria

```
```
