create extension if not exists "uuid-ossp";

create table if not exists "countries"
(
    id               UUID unique  not null default uuid_generate_v1() primary key,
    name      varchar(225) not null,
    code CHAR(2) UNIQUE NOT NULL
);

alter table "countries"
    owner to postgres;

delete
from "countries";
INSERT INTO countries (name, code)
VALUES ('Fiji','FJ');
INSERT INTO countries (name, code)
VALUES ('Tanzania','TZ');
INSERT INTO countries (name, code)
VALUES ('Western Sahara','EH');