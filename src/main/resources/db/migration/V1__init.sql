
create table users (
     id bigint primary key not null,
     username varchar (36) not null unique,
     password varchar(80) not null,
     email varchar(50) not null unique,
     created_at timestamp default current_timestamp
);

create table payments (
     id bigint primary key not null,
     amount decimal not null,
     payment_date date default current_timestamp,
     is_monthly bit not null default 0,
     description varchar (255),
     user_id bigint,
     foreign key (user_id) references users(id)
);



create table roles (
     id bigint primary key not null,
     name varchar(50) not null
);

create table users_roles (
    user_id    bigint not null references users (id),
    role_id    bigint not null references roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (user_id, role_id)
);


insert into payments (id, amount, is_monthly, description)
values
(1, 265.5, 0, 'sklavenitis'),
(2, 265.5, 0, 'masoutis');

insert into roles (id, name)
values
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

insert into users (id, username, password, email)
values
(1, 'user1', '123', '124@123'),
(2, 'user2', '321', '125@123'),
(3, 'user3', '333', '126@123'),
(4, 'admin', '111', '127@123');

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 1),
(3, 1),
(4, 2);

