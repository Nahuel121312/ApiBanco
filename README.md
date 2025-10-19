# 💳 CRUD Banco

Este proyecto es una **API REST** desarrollada con **Spring Boot** que simula las operaciones básicas de un sistema bancario.  
Permite gestionar **clientes, cuentas y transacciones** mediante operaciones CRUD (crear, leer, actualizar y eliminar).

---

## 🚀 Tecnologías utilizadas
- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySQL** (base de datos)
- **Maven**
- **Lombok**
- **Postman** (para pruebas de endpoints)

---

## 🧩 Estructura principal

### Entidades:
- **Cliente** → contiene información personal (nombre, apellido, DNI, etc.)
- **Cuenta** → representa una cuenta bancaria con número de cuenta, saldo y tipo.
- **Transacción** → registra operaciones como depósitos, retiros o transferencias entre cuentas.

### Capas del proyecto:
- **Controller:** maneja las peticiones HTTP.
- **Service:** contiene la lógica de negocio.
- **Repository:** maneja la comunicación con la base de datos.
- **Model:** define las entidades del sistema.

---

## ⚙️ Endpoints principales

## Cliente Controller
| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| GET | `/clientes` | Listar todos los clientes |
| POST | `/clientes` | Crear un nuevo cliente |
| PUT | `/clientes/{id}` | Actualizar un cliente existente |
| DELETE | `/clientes/{id}` | Eliminar un cliente |

## 🧠 Ejemplo de uso

### Crear cliente
**POST** `/clientes`

```json
{
  "nombre": "Nahuel",
  "apellido": "Conde",
  "dni": "12345678"
}
```
## Cuenta Controller

| Método | Endpoint | Descripción |
| -------| ---------| ------------|
| GET | `/cuentas` | Listar todas las cuentas |
| GET | `/cuentas/{id}` | Buscar una cuenta por su ID  |
| GET | `/cuentas/cliente/{idCliente}` | Listar todas las cuentas de un cliente |
| POST | `/cuentas` | Crear una nueva cuenta para un cliente |
| PUT | `/cuentas/{id}` | Actualizar datos de una cuenta (por ejemplo: tipo, estado) |
| DELETE | `/cuentas/{id}` | Eliminar una cuenta |

## 🧠 Ejemplo de uso

### Crear cuenta
**POST** `/cuentas`

```json
{
  "numeroCuenta": "1234567890",
  "saldo": 1000,
  "tipoCuenta": "AHORRO",
  "cliente": {
    "clienteId": 1
  }
}
```
## Prestamo Controller

| Método | Endpoint |	Descripción |
|--------|----------|-------------|
| GET	| `/prestamos`	| Listar todos los préstamos |
| GET |	`/prestamos/{id}` |	Buscar un préstamo por su ID |
| POST |	`/prestamos `|	Crear un nuevo préstamo asociado a un cliente |
| PUT | `/prestamos/{id}` |	Actualizar el monto o estado de un préstamo |
| DELETE |	`/prestamos/{id}` |	Eliminar un préstamo |

## 🧠 Ejemplo de uso

### Crear prestamo
**POST** `/prestamos`
```json
{
  "monto": 50000,
  "tipoEstado": "APROBADO",
  "cliente": {
    "clienteId": 1
  }
}
```
## Tarjeta Controller

| Método | Endpoint | Descripción |
| ------ | ---------| ------------|
| GET | `/tarjetas` | Listar todas las tarjetas |
| GET | `/tarjetas/{id}` | Buscar una tarjeta por su ID |
| POST | `/tarjetas` | Crear una nueva tarjeta para un cliente |
| PUT | `/tarjetas/{id}` | Actualizar el límite de crédito de una tarjeta |
| DELETE | `/tarjetas/{id}` | Eliminar una tarjeta |

## 🧠 Ejemplo de uso

### Crear tarjeta
**POST** `/tarjetas`
```json
{
  "numeroTarjeta": "1234-5678-9012-3456",
  "limiteCredito": 100000,
  "cliente": {
    "clienteId": 1
  }
}
```
## Transaccion Controller

| Método | Endpoint | Descripción |
| ------ | ---------| -----------|
| GET    | `/transacciones` | Listar todas las transacciones |
| GET    | `/transacciones/{id}`  | Buscar una transacción por su ID |
| GET    | `/transacciones/cuenta/{idCuenta}` | Listar transacciones de una cuenta |
| POST   | `/transacciones` | Crear una nueva transacción (depósito, retiro o transferencia entre cuentas) |
| DELETE | `/transacciones/{id}` | Eliminar una transacción |

## 🧠 Ejemplo de uso

### Crear tarjeta
**POST** `/transacciones`
```json
{
  "monto": 1500,
  "tipoTransaccion": "TRANSFERENCIA",
  "cuentaOrigen": {
    "idCuenta": 1
  },
  "cuentaDestino": {
    "idCuenta": 2
  }
}
```

---



aplication.properties
spring.datasource.url=jdbc:mysql://localhost:3306/banco_db
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update

