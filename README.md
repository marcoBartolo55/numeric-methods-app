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
mvn clean install
mvn javafx:run
```

## Notas
- Si usas Windows, instala Java y Maven desde sus páginas oficiales y asegúrate de agregarlos al PATH.
- Si usas otra distribución de Linux, usa el gestor de paquetes correspondiente.
- El archivo principal es `App.java` en `src/main/java/com/numeric/methods/App.java`.
- Para cambiar el tamaño de la ventana o el comportamiento inicial, edita el método `start` en `App.java`.

## Dependencias principales
- JavaFX
- exp4j (evaluación de expresiones matemáticas)

---
