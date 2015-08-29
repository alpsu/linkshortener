SET search_path TO 'test';

DROP TABLE IF EXISTS link_2_teg;
DROP TABLE IF EXISTS link;
DROP TABLE IF EXISTS user_account;
DROP TABLE IF EXISTS teg;

CREATE TABLE teg (
  id   SERIAL                NOT NULL,
  name CHARACTER VARYING(50) NOT NULL,
  CONSTRAINT teg_pkey PRIMARY KEY (id),
  CONSTRAINT name_key UNIQUE (name)
);
COMMENT ON TABLE teg IS 'Справочник тэгов';

CREATE TABLE user_account (
  id       SERIAL                 NOT NULL,
  name     CHARACTER VARYING(100) NOT NULL,
  login    CHARACTER VARYING(60)  NOT NULL,
  password CHARACTER VARYING(35)  NOT NULL,
  CONSTRAINT user_account_pkey PRIMARY KEY (id),
  CONSTRAINT user_account_login_key UNIQUE (login)

);
COMMENT ON TABLE user_account IS 'Таблица пользователей';

CREATE TABLE link (
  id              SERIAL                 NOT NULL,
  user_account_id INTEGER                NOT NULL,
  url             CHARACTER VARYING(500) NOT NULL,
  code CHARACTER VARYING(20) NOT NULL,
  quantity        INTEGER DEFAULT 0      NOT NULL,
  description     CHARACTER VARYING(500),
  CONSTRAINT link_pkey PRIMARY KEY (id),
  CONSTRAINT link_code_key UNIQUE (code),
  CONSTRAINT link_user_account_id_fkey FOREIGN KEY (user_account_id)
  REFERENCES user_account (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);
COMMENT ON TABLE link IS 'Таблица ссылок';

CREATE TABLE link_2_teg
(
  link_id INTEGER NOT NULL,
  teg_id  INTEGER NOT NULL,
  CONSTRAINT link_2_teg_teg_id_fkey FOREIGN KEY (teg_id)
  REFERENCES teg (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT link_2_teg_link_id_fkey FOREIGN KEY (link_id)
  REFERENCES link (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uc_link_teg UNIQUE (link_id, teg_id)
);
COMMENT ON TABLE link_2_teg IS 'Промежуточная таблица для реализации связи многие-ко-многим';