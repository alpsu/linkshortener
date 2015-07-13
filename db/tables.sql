SET search_path TO 'test';

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
  code            CHARACTER VARYING(11)  NOT NULL,
  quantity        INTEGER DEFAULT 0      NOT NULL,
  description     CHARACTER VARYING(500),
  tagone_id       INTEGER DEFAULT 1,
  tagtwo_id       INTEGER DEFAULT 1,
  tagthree_id     INTEGER DEFAULT 1,
  tagfour_id      INTEGER DEFAULT 1,
  tagfive_id      INTEGER DEFAULT 1,
  CONSTRAINT link_pkey PRIMARY KEY (id),
  CONSTRAINT link_code_key UNIQUE (code),
  CONSTRAINT link_user_account_id_fkey FOREIGN KEY (user_account_id)
  REFERENCES user_account (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tagone_id_fkey FOREIGN KEY (tagone_id)
  REFERENCES teg (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tagtwo_id_fkey FOREIGN KEY (tagtwo_id)
  REFERENCES teg (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tagthree_id_fkey FOREIGN KEY (tagthree_id)
  REFERENCES teg (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tagfour_id_fkey FOREIGN KEY (tagfour_id)
  REFERENCES teg (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tagfive_id_fkey FOREIGN KEY (tagfive_id)
  REFERENCES teg (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);
COMMENT ON TABLE link IS 'Таблица ссылок';