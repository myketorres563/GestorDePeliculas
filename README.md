# GestorDePeliculas


## 1º Introducción

Mi idea de proyecto es desarrollar una aplicación para gestionar películas, series e incluso directores favoritos. El objetivo es que el usuario pueda llevar un control de lo que ha visto, lo que está viendo y lo que quiere ver. Además, permitirá guardar información relevante sobre cada título y sobre los directores, ofreciendo una forma sencilla y organizada de mantener actualizada su colección personal de contenido audiovisual.


## 2º Fase de Desarrollo

#### Herramientas Utilizadas

Como lenguaje de programación he utilizado Java, como IDE he utilizado IntelliJ.
De herramientas para la corrección de errores y ayudas con partes del código ChatGPT.


#### Estructura del Proyecto

1. baseDeDatos
   Contiene clases que gestionan la conexión y configuración con la base de datos. Aquí se incluye la apertura y cierre de conexiones, así como la gestión de propiedades desde archivos externos (XML).
2. controller/view
   Agrupa los controladores específicos de cada pantalla de la aplicación desarrollada en JavaFX. Estos controladores gestionan la interacción entre la interfaz gráfica y la lógica del negocio.
3. DAO
   Incluye clases que permiten el acceso y la manipulación de datos en la base de datos. Cada clase DAO maneja operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para su respectiva entidad del sistema.
4. model
   Contiene las clases del modelo de dominio del proyecto, que representan entidades como películas, series, usuarios, directores y estados. Estas clases definen la estructura y las propiedades básicas de los datos manejados en la aplicación.
5. resources
   Incluye los archivos FXML, que definen la estructura visual de cada pantalla de la aplicación. Cada archivo FXML corresponde a una vista específica y define los componentes gráficos de la interfaz.
6. test
   Agrupa las clases dedicadas a realizar pruebas unitarias y de integración para asegurar el correcto funcionamiento de los métodos y las interacciones entre componentes del proyecto.
7. images
   Contiene los recursos gráficos utilizados por la aplicación, como imágenes para fondos, iconos y logotipos.


## 3º Mejoras

* Optimización del Código
* Filtro y búsqueda avanzada
* Mejorar la Inerfaz


## 4º Conclusión

El desarrollo de esta aplicación me ha brindado la oportunidad de aplicar lo que aprendí sobre Java, JavaFX, la gestión de bases de datos, el diseño con el patrón MVC y el uso de herramientas de control de versiones como Git. Durante el proyecto, se ha creado una herramienta efectiva y bien organizada que facilita a los usuarios la administración de películas y series de forma simple y clara.
