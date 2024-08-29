insert into USUARIOS (id, username, password, role)
values (100, 'ana@email.com', '$2a$12$e2ZD4keT5f5Yomn5xFnwTuE1Fr7Iahz2geZpXhLavRzqQ1JpL19GG', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role)
values (101, 'joao@email.com', '$2a$12$e2ZD4keT5f5Yomn5xFnwTuE1Fr7Iahz2geZpXhLavRzqQ1JpL19GG', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
values (102, 'caca@email.com', '$2a$12$e2ZD4keT5f5Yomn5xFnwTuE1Fr7Iahz2geZpXhLavRzqQ1JpL19GG', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
values (103, 'luis@email.com', '$2a$12$e2ZD4keT5f5Yomn5xFnwTuE1Fr7Iahz2geZpXhLavRzqQ1JpL19GG', 'ROLE_CLIENTE');

insert into CLIENTES (id, nome, cpf, id_usuario)
values (11, 'João Gomes', '64750770035', 101);
insert into CLIENTES (id, nome, cpf, id_usuario)
values (12, 'Carlos Alberto', '01278358064', 102);