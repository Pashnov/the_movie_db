# schema

# --- !Ups

CREATE TABLE m_user (
  id bigint(20) NOT NULL,
  password varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE movie (
  id bigint(20) NOT NULL,
  name varchar(255) DEFAULT NULL,
  rating double NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE favorite (
  id bigint(20) NOT NULL,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE favorite_movie (
  favorite_id bigint(20) NOT NULL,
  movies_id bigint(20) NOT NULL,
  KEY p_movie_key (movies_id),
  KEY p_favorite_key (favorite_id),
  CONSTRAINT f_favorite_key FOREIGN KEY (favorite_id) REFERENCES favorite (id),
  CONSTRAINT f_movie_key FOREIGN KEY (movies_id) REFERENCES movie (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE m_user_favorite (
  m_user_id bigint(20) NOT NULL,
  favorites_id bigint(20) NOT NULL,
  UNIQUE KEY p_key (favorites_id),
  KEY p_m_user_key (m_user_id),
  CONSTRAINT f_m_user_key FOREIGN KEY (m_user_id) REFERENCES m_user (id),
  CONSTRAINT f_favorite_fkey FOREIGN KEY (favorites_id) REFERENCES favorite (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# --- !Downs

DROP TABLE m_user_favorite;
DROP TABLE favorite_movie;
DROP TABLE m_user;
DROP TABLE movie;
DROP TABLE favorite;