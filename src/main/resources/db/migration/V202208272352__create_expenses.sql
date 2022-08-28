CREATE SEQUENCE expense_id_sequence;

CREATE TABLE IF NOT EXISTS expenses (
    id bigint not null default nextval('expense_id_sequence'),
    description varchar(255),
    total_value decimal(19,2),
    date timestamp,
    category varchar(32) default 'OTHERS',
    primary key (id)
);