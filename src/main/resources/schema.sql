
-- Создаем таблицу с жанрами
CREATE TABLE IF NOT EXISTS genre (
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(48)
);

-- Создаем таблицу с рейтингами
CREATE TABLE IF NOT EXISTS mpa (
    mpa_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(48)
);


--Создаем таблицу с фильмами с внешним ключем genre_id которая ссылается на поле id
--таблицы genre
    CREATE TABLE IF NOT EXISTS films (
    film_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(48),
    release_date date,
    duration INTEGER,
    description varchar(200),
    rate INTEGER(10),
    mpa_id INTEGER(10) REFERENCES mpa(mpa_id)
    );

-- Создаем таблицу пользователей
    CREATE TABLE IF NOT EXISTS users (
      	user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
      	name varchar(48),
      	login varchar(48),
      	email varchar(48),
      	birthday date
    );

--Создаем таблицу добавления в друзья.
    CREATE TABLE  IF NOT EXISTS adding_friends(
        adding_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        from_user_id INTEGER REFERENCES users(user_id),
        to_user_id INTEGER REFERENCES users(user_id)
    );

--Создае таблицу лайков фильма
CREATE TABLE IF NOT EXISTS likes(
   like_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
   film_id INTEGER REFERENCES films(film_id),
   user_id INTEGER REFERENCES users(user_id)

);
-- Создаем связующую таблицу через отношение многие ко многим. Связываются таблицы: Films, Genre
CREATE TABLE IF NOT EXISTS film_genre (
   id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
   film_id INTEGER REFERENCES films(film_id) ON DELETE CASCADE,
   genre_id INTEGER REFERENCES genre(genre_id) ON DELETE CASCADE

);
--Вставляем данные по умолчанию в таблицу
INSERT INTO mpa (name) SELECT * FROM (VALUES ('G'), ('PG'), ('PG-13'), ('R'), ('NC-17')) AS x (name) WHERE NOT EXISTS (SELECT 1 FROM mpa);
-- Вставляем данные по умолчанию в таблицу
INSERT INTO genre (name) SELECT * FROM (VALUES ('Комедия'), ('Драма'), ('Мультфильм'), ('Триллер'), ('Документальный'), ('Боевик')) AS x (name) WHERE NOT EXISTS (SELECT 1 FROM genre);


