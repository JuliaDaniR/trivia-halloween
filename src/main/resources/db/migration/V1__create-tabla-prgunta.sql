-- V1__Create_tables.sql

CREATE TABLE IF NOT EXISTS preguntas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    texto LONGTEXT NOT NULL,                
    dificultad ENUM('FACIL', 'MEDIO', 'DIFICIL') NOT NULL,
    tipo ENUM('MITO', 'LEYENDA', 'HISTORIA') NOT NULL,
    breve_historia LONGTEXT
);

CREATE TABLE IF NOT EXISTS respuestas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    texto LONGTEXT NOT NULL,                
    es_correcta BOOLEAN NOT NULL,           
    pregunta_id BIGINT,                     
    FOREIGN KEY (pregunta_id) REFERENCES preguntas(id) ON DELETE CASCADE  
);
