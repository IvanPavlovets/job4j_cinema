CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE if not exists sessions (
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE if not exists ticket (
  id SERIAL PRIMARY KEY,
  session_id INT NOT NULL REFERENCES sessions(id),
  row INT NOT NULL,
  cell INT NOT NULL,
  userId INT NOT NULL REFERENCES users(id)
);