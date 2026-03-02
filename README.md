# Numeric Methods App

## Requisitos para desarrollo

- Java JDK 11 o superior
- Maven
- Git (opcional, para clonar el repositorio)

## Instalación de dependencias en Linux (Debian/Ubuntu)

```
sudo apt update
sudo apt install openjdk-11-jdk maven git
```

## Clonar el repositorio

```
git clone https://github.com/marcoBartolo55/numeric-methods-app.git
cd numeric-methods-app
```

## Compilar y ejecutar la aplicación

```
mvn clean package
mvn javafx:run
```

O puedes ejecutar el JAR generado:

```
java -jar target/numeric-methods-app-1.0-SNAPSHOT.jar
```

## Estructura del proyecto

- `src/main/java/com/numeric/methods/` : Código fuente principal
- `src/main/resources/com/numeric/methods/view/` : Archivos FXML (interfaces)
- `src/main/resources/com/numeric/methods/style/` : Archivos CSS (estilos visuales)

## Personalización de estilos

Cada vista FXML tiene su propio archivo CSS en la carpeta `style`. El sistema carga automáticamente el CSS correspondiente al cambiar de pantalla.

## Solución de problemas comunes

- Si ves errores de tipo `LoadException` relacionados con FXML, revisa las importaciones en el archivo FXML.
- Si los estilos no se aplican, asegúrate de que el nombre del CSS coincida con el nombre del FXML (por ejemplo, `first-menu.fxml` usa `first-menu.css`).
- Si usas Java 17+ y tienes problemas con JavaFX, instala los módulos de JavaFX y agrégalos al comando de ejecución.

## Notas
- Si usas Windows, instala Java y Maven desde sus páginas oficiales y agrégalos al PATH.
- Si usas otra distribución de Linux, usa el gestor de paquetes correspondiente.
- El archivo principal es `App.java` en `src/main/java/com/numeric/methods/App.java`.
- Para cambiar el tamaño de la ventana o el comportamiento inicial, edita el método `start` en `App.java`.

## Dependencias principales
- JavaFX
- exp4j (evaluación de expresiones matemáticas)

---
