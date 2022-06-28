-- -- Place
CREATE TABLE IF NOT EXISTS PLACE
(
    id   binary(16) not null,
    name varchar(20) not null,
    PRIMARY KEY (id)
);

INSERT IGNORE INTO PLACE (id, name)
VALUES (UNHEX(REPLACE('0b31f723-8ce9-4d51-9715-83aa0032a79a', '-', '')), '피라미드');

INSERT IGNORE INTO PLACE (id, name)
VALUES (UNHEX(REPLACE('3fec5466-630f-4313-bbea-0fff58819836', '-', '')), '타이완');

INSERT IGNORE INTO PLACE (id, name)
VALUES (UNHEX(REPLACE('5b9c0510-5c8f-4817-b387-9df785f13594', '-', '')), '가오슝');

INSERT IGNORE INTO PLACE (id, name)
VALUES (UNHEX(REPLACE('a992488a-a4f5-4d85-afa0-7a93c7a65ecc', '-', '')), '성 패트릭 성당');

INSERT IGNORE INTO PLACE (id, name)
VALUES (UNHEX(REPLACE('dddf5fe9-6fe4-46d3-8ec7-b7b85851370e', '-', '')), '순례길');

-- -- USER
Create TABLE IF NOT EXISTS USER
(
    id    binary(16) not null,
    point INT,
    PRIMARY KEY (ID)
);

INSERT IGNORE INTO USER (id, point)
VALUES (UNHEX(REPLACE('46026146-9cc9-44f0-b62c-e3e833fdc7d4', '-', '')), 0);

INSERT IGNORE INTO USER (id, point)
VALUES (UNHEX(REPLACE('78e74b34-8987-4962-8a31-51efcd15511d', '-', '')), 0);


-- -- REVIEW
CREATE TABLE IF NOT EXISTS REVIEW(
    id           binary(16) not null,
    content      VARCHAR(1000),
    status       VARCHAR(100),
    place_id     binary(16),
    user_id      binary(16),
    first_review boolean NOT NULL default 1,
    FOREIGN KEY (place_id) REFERENCES PLACE (id),
    FOREIGN KEY (user_id) REFERENCES USER (ID),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS PHOTO (
    id        binary(16) not null,
    status    VARCHAR(100),
    review_id binary(16),
    FOREIGN KEY (review_id) REFERENCES REVIEW (ID),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS POINTLOG (
    id           int not null PRIMARY KEY AUTO_INCREMENT ,
    user_id      VARCHAR(100),
    place_id     VARCHAR(100),
    review_id    VARCHAR(100),
    status       VARCHAR(100),
    update_point int,
    last_point   int
);
