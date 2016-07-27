INSERT INTO root (id, name) VALUES (1, 'Name 1');
INSERT INTO root (id, name) VALUES (2, 'Name 2');

INSERT INTO child (id, name, root_id) VALUES (1, 'Child 1 of root 1', 1);
INSERT INTO child (id, name, root_id) VALUES (2, 'Child 2 of root 1', 1);
INSERT INTO child (id, name, root_id) VALUES (3, 'Child 3 of root 1', 1);
INSERT INTO child (id, name, root_id) VALUES (4, 'Child 4 of root 1', 1);
INSERT INTO child (id, name, root_id) VALUES (5, 'Child 5 of root 1', 1);

INSERT INTO child (id, name, root_id) VALUES (6, 'Child 1 of root 2', 2);
INSERT INTO child (id, name, root_id) VALUES (7, 'Child 2 of root 2', 2);
INSERT INTO child (id, name, root_id) VALUES (8, 'Child 3 of root 2', 2);
INSERT INTO child (id, name, root_id) VALUES (9, 'Child 4 of root 2', 2);
INSERT INTO child (id, name, root_id) VALUES (10, 'Child 5 of root 2', 2);

INSERT INTO salary (id, amounth) VALUES (1, 1.1);
INSERT INTO salary (id, amounth) VALUES (2, 2.2);
INSERT INTO salary (id, amounth) VALUES (3, 3.3);
INSERT INTO salary (id, amounth) VALUES (4, 4.4);

INSERT INTO person (id, name, salary_id) VALUES (1, 'name 1', 1);
INSERT INTO person (id, name, salary_id) VALUES (2, 'name 2', 2);
INSERT INTO person (id, name, salary_id) VALUES (3, 'name 3', 3);
INSERT INTO person (id, name, salary_id) VALUES (4, 'name 4', 4);