CREATE DATABASE Chord DEFAULT CHARACTER SET utf8;

CREATE USER 'Chord'@'localhost' IDENTIFIED BY 'admin';
CREATE USER 'Chord'@'%' IDENTIFIED BY 'admin';

GRANT ALL PRIVILEGES ON Chord.* TO 'Chord'@'localhost';
GRANT ALL PRIVILEGES ON Chord.* TO 'Chord'@'%';

CREATE TABLE Chord.member
(
    user_id         VARCHAR(15)  NOT NULL PRIMARY KEY,
    password        VARCHAR(255) NOT NULL,
    name            VARCHAR(15)  NOT NULL,
    status_message  VARCHAR(15),
    register_time   TIMESTAMP    NOT NULL,
    last_login_time TIMESTAMP    NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE Chord.friend
(
    user_id   VARCHAR(15) NOT NULL,
    friend_id VARCHAR(15) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE Chord.chat_room
(
    room_id      INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    room_type    VARCHAR(10) NOT NULL,
    name         VARCHAR(15) NOT NULL,
    headcount    INTEGER     NOT NULL,
    last_message TEXT,
    create_time  TIMESTAMP   NOT NULL,
    last_time    TIMESTAMP   NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE Chord.chat_member
(
    user_id              VARCHAR(15) NOT NULL,
    room_id              INTEGER     NOT NULL,
    invite_time          TIMESTAMP   NOT NULL,
    last_read_message_id INT         NOT NULL,
    hidden               BOOLEAN     NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE Chord.message
(
    message_id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id      VARCHAR(15) NOT NULL,
    room_id      INT         NOT NULL,
    message_type VARCHAR(15) NOT NULL,
    message      TEXT        NOT NULL,
    send_time    TIMESTAMP   NOT NULL
)



