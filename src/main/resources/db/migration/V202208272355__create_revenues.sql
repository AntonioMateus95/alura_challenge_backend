CREATE SEQUENCE revenue_id_sequence;

CREATE TABLE IF NOT EXISTS revenues (
    id bigint not null default nextval('revenue_id_sequence'),
    description varchar(255),
    total_value decimal(19,2),
    date timestamp,
    primary key (id)
);