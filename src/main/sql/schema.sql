CREATE TABLE root (
  id BIGINT PRIMARY KEY,
  name VARCHAR(100),
);
CREATE TABLE child (
  id BIGINT PRIMARY KEY ,
  name VARCHAR(100),
  root_id BIGINT,
  FOREIGN KEY (root_id) REFERENCES root(id)
);

CREATE TABLE salary (
  id BIGINT PRIMARY KEY ,
  amounth DOUBLE NOT NULL
);

CREATE TABLE person (
  id BIGINT PRIMARY KEY,
  name VARCHAR(100),
  salary_id BIGINT,
  FOREIGN KEY (salary_id) REFERENCES salary(id)
);