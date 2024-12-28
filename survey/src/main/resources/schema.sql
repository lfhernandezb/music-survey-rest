CREATE TABLE survey (
    id_survey IDENTITY NOT NULL PRIMARY KEY,
    email VARCHAR(128) NOT NULL UNIQUE,
    id_music_style INT NOT NULL
);