create table author
(
    id serial
        constraint author_pk
            primary key,
    name varchar(50)
);


create table publisher
(
    id serial
        constraint publisher_pk
            primary key,
    name varchar(50)
);


create table book
(
    id serial
        constraint book_pk
            primary key,
    name varchar(50),
    author_id int,
    publisher_id int,
    title varchar(50),
    price numeric,
    number_of_pages int,
    CONSTRAINT fk_author
        FOREIGN KEY(author_id)
            REFERENCES author(id),
    CONSTRAINT fk_publisher
        FOREIGN KEY(publisher_id)
            REFERENCES publisher(id)
);

-- SEEDING
INSERT INTO author(name) values ('author-one');
INSERT INTO author(name) values ('author-two');
INSERT INTO author(name) values ('author-three');

INSERT INTO publisher(name) values ('publisher-one');
INSERT INTO publisher(name) values ('publisher-two');
INSERT INTO publisher(name) values ('publisher-three');

INSERT INTO book(author_id, publisher_id, title, price, number_of_pages) VALUES
(1, 1, 'book-one', 50000, 100);
INSERT INTO book(author_id, publisher_id, title, price, number_of_pages) VALUES
(1, 2, 'book-two', 250000, 130);
INSERT INTO book(author_id, publisher_id, title, price, number_of_pages) VALUES
(1, 3, 'book-three', 330000, 330);
INSERT INTO book(author_id, publisher_id, title, price, number_of_pages) VALUES
(2, 1, 'book-four', 440000, 440);
INSERT INTO book(author_id, publisher_id, title, price, number_of_pages) VALUES
(2, 2, 'book-five', 550000, 550);
INSERT INTO book(author_id, publisher_id, title, price, number_of_pages) VALUES
(2, 3, 'book-six', 660000, 660);
INSERT INTO book(author_id, publisher_id, title, price, number_of_pages) VALUES
(3, 1, 'book-seven', 770000, 770);
INSERT INTO book(author_id, publisher_id, title, price, number_of_pages) VALUES
(3, 2, 'book-eight', 880000, 880);
INSERT INTO book(author_id, publisher_id, title, price, number_of_pages) VALUES
(3, 3, 'book-nine', 990000, 990);
