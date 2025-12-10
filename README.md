# Proyecto Juguetería ToyHub

<img width="500" height="500" alt="toyhub" src="https://github.com/user-attachments/assets/3816e0cd-fc1d-4fd5-b396-faa895b6b01b" />

## Integrantes del Equipo

* **Lee Medrano Kioga** (@K10ga)
* **Reyes García Uziel** (@UzielRey15)
* **Soriano Cortés Carlos Daniel** (@DanielSoriano1807)

---

## Descripción

Nuestro proyecto es una **juguetería en línea** que simula un _marketplace_.

En ToyHub, un usuario puede elegir entre **vender un juguete o comprarlo**.

### Funcionalidades para Vendedores

Los **dueños** de los juguetes (vendedores) tienen la capacidad de **editar** su producto para:

* Cambiar el **precio**.
* Actualizar las **imágenes** del juguete.
* Modificar el **tipo** o categoría del juguete.

### Funcionalidades para Compradores

Por el lado de los **compradores**, en caso de que el vendedor edite la información de un juguete, se les mostrará una **alerta** para que refresquen la página y puedan ver el producto actualizado.

---

## Stack Tecnológico y Características

Este proyecto ha sido desarrollado siguiendo estrictamente los lineamientos de la materia:

* **Lenguaje:** Kotlin 100%.
* **Interfaz de Usuario:** Jetpack Compose.
* **Arquitectura:** MVVM (Model-View-ViewModel).
* **Base de Datos (API):** SQLite con SQLAlchemy (servidor).

### Conectividad (API REST con Retrofit)

La aplicación utiliza la librería **Retrofit** para interactuar con la API REST, implementando las siguientes operaciones:

* **GET (Obtener):**
    * `GET /juguetes`: Obtiene el listado completo de todos los juguetes disponibles.
    * `GET /juguetes/<id>`: Obtiene los detalles específicos de un juguete por su ID.
    * `GET /uploads_juguetes/<filename>`: Obtiene la imagen física de un juguete (usada para cargar las imágenes en la UI).

* **POST (Crear/Enviar):**
    * `POST /register`: Crea un nuevo **Usuario** (Registro).
    * `POST /login`: Verifica las credenciales de un **Usuario** (Inicio de Sesión).
    * `POST /juguetes`: **Crea un nuevo juguete** subiendo sus detalles y un máximo de **3 imágenes**.
    * `POST /juguetes/<id>/comprar`: Marca un juguete como **vendido** y asigna el ID del comprador.

* **PUT (Actualizar):**
    * `PUT /juguetes/<id>`: **Actualiza** el nombre, tipo y/o precio de un juguete. Puede reemplazar las imágenes existentes si se envían nuevas.

* **DELETE (Borrar):**
    * `DELETE /juguetes/<id>`: **Elimina** un juguete permanentemente del sistema.

### Sensor Integrado: Cámara

* **Uso:** La cámara del dispositivo se utiliza para permitir a los vendedores tomar y subir **fotos de los juguetes** al momento de la publicación o edición, con un límite de **tres fotos** por juguete.

---

### Estructura del Proyecto (MVVM con Compose)

La aplicación sigue la arquitectura MVVM, organizada para mantener la separación de responsabilidades:

* `data/`: Contiene los modelos de datos (`Juguete`, `User`).
* `network/`: Maneja la comunicación con la API (clases de **Retrofit** como `ApiService` y `RetrofitInstance`).
* `repository/`: Abstrae la fuente de datos (`JugueteRepository`).
* `ui/`: La capa de interfaz de usuario con **Jetpack Compose**.
    * `components/`: Elementos reutilizables de la UI (`buttons`, `texts`, `JugueteCard`, `ToyCategorySelector.kt`).
    * `screens/`: Pantallas principales de la aplicación (`LoginScreen`, `IndexJuguetesScreen`, `CreateJugueteScreen`, `EditJugueteScreen`, `ForgotPasswordScreen`, `RegistroScreen`).
* `utils/`: Clases de utilidad, como el manejo de la sesión del usuario (`UserSession`).
* `viewmodel/`: La capa de lógica de negocio y estado de la UI (`JugueteViewModel`, `LoginViewModel`, `RegisterViewModel`).
