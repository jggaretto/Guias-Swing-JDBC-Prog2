# Prácticos Java - Swing y JDBC

Proyecto integrador para VSCode con todos los ejercicios de las guías de **Java Swing** y **Base de Datos con JDBC**.

## Estructura del proyecto

```
java-practicos/
├── lib/
│   └── mysql-connector-j-X.X.X.jar   ← (descargar, ver paso 1, yo usé la version 9.7.0)
├── swing/
│   ├── ejercicio1/   FormularioBasico.java
│   ├── ejercicio2/   MiniEditor.java
│   ├── ejercicio3/   PanelPreferencias.java
│   └── ejercicio4/   PantallaLogin.java
├── jdbc/
│   ├── ejercicio5_basico/   ConexionDB.java, OperacionesDB.java
│   ├── ejercicio5_mvc/      Empleado.java, OperacionesDAO.java, ConexionDB.java, EmpleadoDAO.java, Main.java
│   ├── ejercicio6/          EmpleadosVista.java
│   └── ejercicio7/          EmpleadosAvanzadoVista.java
├── setup.sql
└── README.md
```

---

## Paso 1: Descargar el conector MySQL

Descargá el **MySQL Connector/J** desde:  
https://dev.mysql.com/downloads/connector/j/

Elegí la opción **Platform Independent (.zip)**, descomprimí y copiá el archivo `.jar` a la carpeta `lib/`:

```
java-practicos/lib/mysql-connector-j-X.X.X.jar
```

---

## Paso 2: Configurar la base de datos

Abrí **MySQL Workbench** (o la terminal MySQL) y ejecutá:

```bash
mysql -u root -p < setup.sql
```

O copiá y pegá el contenido de `setup.sql` en MySQL Workbench y ejecutalo.

> ⚠️ **Importante:** En todos los archivos `ConexionDB.java` reemplazá `"root123"` por tu contraseña real de MySQL.

---

## Ejercicios Swing (sin base de datos)

Estos ejercicios no requieren MySQL. Se compilan y ejecutan directamente.

### Ejercicio 1 — Formulario básico

```bash
cd swing/ejercicio1
javac FormularioBasico.java
java FormularioBasico
```

**Qué hace:** Ventana con un campo de texto y botón que muestra un saludo personalizado.

---

### Ejercicio 2 — Mini Editor de Texto

```bash
cd swing/ejercicio2
javac MiniEditor.java
java MiniEditor
```

**Qué hace:** Editor de texto con barra de menús (Limpiar / Salir).

---

### Ejercicio 3 — Panel de Preferencias

```bash
cd swing/ejercicio3
javac PanelPreferencias.java
java PanelPreferencias
```

**Qué hace:** Ventana principal con botón que abre un JDialog modal con checkboxes. Al aceptar, muestra las opciones seleccionadas en la ventana principal.

---

### Ejercicio 4 — Pantalla de Login

```bash
cd swing/ejercicio4
javac PantallaLogin.java
java PantallaLogin
```

**Credenciales de prueba:**
- Usuario: `admin`
- Contraseña: `1234`

**Qué hace:** Formulario de login con JPasswordField que oculta caracteres. Muestra diálogo de éxito o error.

---

## Ejercicios JDBC (requieren MySQL)

> Reemplazá `mysql-connector-j-X.X.X.jar` por el nombre exacto del archivo que descargaste.

### Ejercicio 5 Básico — CRUD directo

```bash
cd jdbc/ejercicio5_basico
javac -cp "../../lib/mysql-connector-j-X.X.X.jar:." *.java
java -cp "../../lib/mysql-connector-j-X.X.X.jar:." OperacionesDB
```

**En Windows** reemplazá `:` por `;` en el classpath:
```bash
javac -cp "..\..\lib\mysql-connector-j-X.X.X.jar;." *.java
java -cp "..\..\lib\mysql-connector-j-X.X.X.jar;." OperacionesDB
```

**Qué hace:** Ejecuta un INSERT, UPDATE y DELETE sobre la tabla `empleados` e imprime el resultado en consola.

---

### Ejercicio 5 MVC — Patrón DAO

```bash
cd jdbc/ejercicio5_mvc
javac -cp "../../lib/mysql-connector-j-X.X.X.jar:." *.java
java -cp "../../lib/mysql-connector-j-X.X.X.jar:." Main
```

**Qué hace:** Mismas operaciones pero separadas en capas: modelo `Empleado`, interfaz `OperacionesDAO`, implementación `EmpleadoDAO`, y clase `Main` para probar.

---

### Ejercicio 6 — Vista JTable con MouseListener

```bash
cd jdbc/ejercicio6
javac -cp "../../lib/mysql-connector-j-X.X.X.jar:." EmpleadosVista.java
java -cp "../../lib/mysql-connector-j-X.X.X.jar:." EmpleadosVista
```

**Qué hace:** Interfaz gráfica con tabla que carga empleados desde MySQL. Al hacer clic en una fila, llena el formulario lateral para poder modificar o eliminar el registro.

> Requiere ejecutar **primero** el bloque de `ejercicios 5 y 6` del `setup.sql` (tabla `empleados` con columna `departamento` como texto).

---

### Ejercicio 7 — CRUD Avanzado con JComboBox y JFileChooser

```bash
cd jdbc/ejercicio7
javac -cp "../../lib/mysql-connector-j-X.X.X.jar:." EmpleadosAvanzadoVista.java
java -cp "../../lib/mysql-connector-j-X.X.X.jar:." EmpleadosAvanzadoVista
```

**Qué hace:** CRUD completo con:
- **JComboBox** cargado desde la tabla `departamentos` (clave foránea)
- **JFileChooser** para seleccionar y previsualizar foto del empleado
- Tabla interactiva igual al ejercicio 6

> Requiere ejecutar **todo** el `setup.sql` incluyendo el bloque del ejercicio 7 (recrea la tabla `empleados` con `id_departamento` y `ruta_foto`).

---
