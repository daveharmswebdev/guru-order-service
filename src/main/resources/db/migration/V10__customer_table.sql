create table customer (
                          id bigint not null auto_increment primary key,
                          name varchar(255),
                          address varchar(255),
                          city varchar(255),
                          state varchar(255),
                          phone varchar(255),
                          email varchar(255),
                          zip_code varchar(255),
                          created_date timestamp,
                          last_modified_date timestamp
) engine =InnoDB;

alter table order_header
    add column customer_id bigint;

alter table order_header
    add constraint order_header_customer_fk
        foreign key (customer_id) references customer(id);

alter table order_header
    drop column customer;

insert into customer
    (name, address, city, state, zip_code, created_date, last_modified_date)
    VALUES ('customer', '123 anywher street', 'New York', 'New York', '12345', now(), now());

update order_header set order_header.customer_id = (select id from customer limit 1);