# GamingHub
A Gaming Hub platform

## Model
Usuario
---------
- id : UUID
- nombre_usuario : String
- email : String
- telefono : String
- google_id : String
- rol : ENUM(USUARIO, ADMIN, BOT)
- fecha_registro : DateTime

Juego
---------
- id : UUID
- nombre : String
- descripcion : String
- reglas : Text? (opcional)

Partida
---------
- id : UUID
- anfitrion_id : UUID → Usuario
- juego_id : UUID → Juego
- estado : ENUM(ESPERANDO, ACTIVA, FINALIZADA, CANCELADA)
- fecha_creacion : DateTime
- duracion_estimada : Int? (opcional en minutos)
- config_extra : JSON? (opcional si los juegos tienen settings específicos)

Participante
---------
- id : UUID
- usuario_id : UUID → Usuario
- partida_id : UUID → Partida
- estado : ENUM(PENDIENTE, ACEPTADO, RECHAZADO, EXPULSADO)
- rol_en_juego : String? (si aplica para juegos tipo “impostor”)
- puntuacion : Int? (si aplica)

Amistad
---------
- id : UUID
- usuario_id : UUID → Usuario (el que envía la solicitud)
- amigo_id : UUID → Usuario (el que la recibe)
- estado : ENUM(PENDIENTE, ACEPTADA, RECHAZADA, BLOQUEADA)
- fecha_solicitud : DateTime
- fecha_aceptacion : DateTime?

Chat
---------
- id : UUID
- tipo : ENUM(PRIVADO, PARTIDA)
- partida_id : UUID? (si es de tipo PARTIDA)

Mensaje
---------
- id : UUID
- chat_id : UUID → Chat
- autor_id : UUID → Usuario
- contenido : TEXT
- fecha_envio : DateTime
- es_sistema : BOOLEAN
