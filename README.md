# Proyecto ChatBot con Ollama API y Java Swing

Este proyecto consiste en un **ChatBot** que utiliza la **API de Ollama** (versión 3.2) y está implementado con una interfaz gráfica utilizando **Java Swing**. El ChatBot se conecta a la API de Ollama para generar respuestas a partir de la entrada del usuario.

## Índice
1. [Instalación](#instalación)
    - [Instalación de Ollama API](#instalación-de-ollama-api)
    - [Instalación del entorno de desarrollo](#instalación-del-entorno-de-desarrollo)
    - [Instalación de librerías necesarias](#instalación-de-librerías-necesarias)
2. [Configuración y uso](#configuración-y-uso)
    - [Conexión con la API](#conexión-con-la-api)
    - [Librerías necesarias](#librerías-necesarias)
3. [Autores](#autores)

---

## 1. Instalación

### Instalación de Ollama API

1. Dirígete al siguiente enlace para descargar la **API de Ollama** versión 3.2:  
   [Ollama API - GitHub](https://github.com/ollama/ollama)  
   
2. Sigue las instrucciones de instalación en el repositorio para configurar la API en tu máquina local.

### Instalación del entorno de desarrollo

Para desarrollar y ejecutar este proyecto, necesitarás las siguientes herramientas:

- **NetBeans IDE (Versión 15)**: Un entorno de desarrollo integrado para proyectos Java.  
  [Descargar NetBeans 15](https://netbeans.apache.org/download/index.html)
  
- **Java 8**: Es necesario tener instalado **Java 8** en tu dispositivo
  [Descargar Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)

### Instalación de librerías necesarias

Este proyecto requiere de algunas librerías adicionales para funcionar correctamente. Debes descargar los siguientes archivos `.jar`:

- **Json.JAR**: Necesaria para manejar datos en formato JSON.  
  [Descargar Json.JAR](https://mvnrepository.com/artifact/org.json/json)  
 
- **FlatLaf.JAR**: Para aplicar un tema visual moderno a la interfaz gráfica con Java Swing.  
  [Descargar FlatLaf.JAR](https://github.com/JFormDesigner/FlatLaf)

Coloca estos archivos `.jar` en el directorio adecuado de tu proyecto y asegúrate de añadirlos al classpath en NetBeans.

---

## 2. Configuración y uso

### Conexión con la API

Para establecer la conexión entre tu aplicación y la API de Ollama, deberás activar la API en el dispositivo, para que así se puede iniciar la conexión

### Librerías necesarias

Asegúrate de importar las siguientes librerías en tu proyecto para interactuar correctamente con la API y crear la interfaz gráfica:

```java
import com.formdev.flatlaf.FlatLightLaf;  // Tema visual
import java.io.IOException;  // Manejo de entradas y salidas
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;  // Manejo de Jlist
import javax.swing.JOptionPane;  // Ventanas emergentes
import javax.swing.UIManager;  // Configuración de la interfaz gráfica
import org.json.JSONArray;  // Manejo de arrays JSON
import org.json.JSONObject;  // Manejo de objetos JSON2
```

## Autores

- [@Juan Camilo Lazala Hurtado](https://github.com/JLazalaHurtado)
- [José Samur](https://github.com/Samur1x)
