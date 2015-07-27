SET search_path TO 'public';

DELETE FROM link;
DELETE FROM teg;
DELETE FROM user_account;

INSERT INTO user_account (name, login, password) VALUES
  ('Alex', 'Alex', 'Alex'), ('Andrew', 'Andrew', 'Andrew'), ('Vano', 'Vano', 'Vano');