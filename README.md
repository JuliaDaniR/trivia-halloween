# 🎃 Trivia de Halloween 👻

¡Bienvenido a la Trivia de Halloween! Este proyecto es una API RESTful en Java que permite a los usuarios jugar un juego de trivia basado en mitos, leyendas y historias de Halloween. Los jugadores pueden responder preguntas de diferentes niveles de dificultad y recibir puntos según sus respuestas.

## 📚 Tabla de Contenidos

- [Características](#características)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Uso](#uso)
- [Endpoints](#endpoints)
- [Desarrollado](#desarrollo)

## 🛠️ Características

- **Preguntas de Trivia**: Preguntas categorizadas en mitos, leyendas e historias. 📜
- **Niveles de Dificultad**: Preguntas clasificadas en tres niveles: fácil, medio y difícil. ⚔️
- **Sistema de Puntuación**: Los usuarios obtienen puntos según sus respuestas correctas. 🏆
- **Registro y Autenticación**: Los usuarios pueden registrarse y autenticar su cuenta. 🔑
- **Estadísticas del Usuario**: Información sobre el rendimiento del usuario en el juego. 📊

## 🖥️ Tecnologías Utilizadas

- **Java**: Lenguaje de programación. ☕
- **Spring Boot**: Framework para crear aplicaciones Java basadas en microservicios. 🚀
- **JPA / Hibernate**: Para la gestión de la base de datos. 🗄️
- **MySQL Database**: Base de datos para desarrollo. 💾
- **Swagger**: Documentación de la API. 📖
- **JWToken**: Para autenticación. 🧪

## 🎮 Uso

Puedes interactuar con la API utilizando herramientas como Postman o mediante Swagger. Aquí hay algunos ejemplos de cómo usar los endpoints.

## 📡 Endpoints

### 🔑 Autenticación

- **Registrar Usuario**: `POST /api/auth/register`
  - Cuerpo: 
    ```json
    { "nombre": "nombre_usuario", "password": "contraseña" }
    ```

- **Iniciar Sesión**: `POST /api/auth/login`
  - Cuerpo: 
    ```json
    { "nombre": "nombre_usuario", "password": "contraseña" }
    ```

### 🎤 Trivia

- **Registrar Pregunta**: `POST /api/preguntas`
  - Cuerpo: 
    ```json
    {
      "pregunta": "¿Cuál es la pregunta?",
      "respuesta": "respuesta correcta",
      "tipo": "mito",
      "dificultad": "facil"
    }
    ```

- **Listar Preguntas**: `GET /api/preguntas`
  
- **Actualizar Pregunta**: `PUT /api/preguntas/{id}`
  - Cuerpo: 
    ```json
    {
      "pregunta": "¿Nueva pregunta?",
      "respuesta": "nueva respuesta",
      "tipo": "mito",
      "dificultad": "dificil"
    }
    ```

- **Eliminar Pregunta**: `DELETE /api/preguntas/{id}`

- **Obtener Pregunta Aleatoria**: `GET /api/preguntas/aleatoria`
  - Parámetros: `dificultad`, `tipo`

- **Responder Pregunta**: `POST /api/responder`
  - Cuerpo: 
    ```json
    {
      "preguntaId": 1,
      "respuestaId": 2
    }
    ```

- **Obtener Estadísticas**: `GET /api/usuarios/{usuarioId}/estadisticas`

### 🏆 Puntajes

- **Obtener Puntajes de Todos los Usuarios**: `GET /api/puntajes`
- **Obtener Puntajes por Usuario**: `GET /api/puntajes/{id}`

### 📊 Otras Consultas

- **Listar Preguntas por Dificultad**: `GET /api/listar/dificultad/{dificultad}`
- **Listar Preguntas por Tipo**: `GET /api/listar/tipo/{tipo}`
- **Obtener Logros**: `GET /api/logros/{usuarioId}`

### 📜 Desarrollo
  **Desarrollado por Julia Daniela Rodriguez**

**¡Diviértete jugando a la trivia de Halloween! 🎉👻**

