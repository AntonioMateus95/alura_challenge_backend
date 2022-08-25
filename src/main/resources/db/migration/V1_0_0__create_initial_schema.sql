CREATE TABLE IF NOT EXISTS expense (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    description varchar(255),
    value decimal(19,2),
    date timestamp,
    category varchar(32) default 'OTHERS'
);

CREATE TABLE IF NOT EXISTS revenue (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    description varchar(255),
    value decimal(19,2),
    date timestamp
);