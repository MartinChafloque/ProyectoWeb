drop table if exists editorial;
create table editorial (
    id int primary key auto_increment,
    name varchar(200) not null,
    web_site varchar(500)
);
insert into editorial
(id,    name,                           web_site) values
(1,     'Ballard & Tighe, Publishers',  'https://ballard-tighe.com/home/'),
(2,     'Bilingual Review Press',       'https://bilingualpress.clas.asu.edu/'),
(3,     'Ediciones Baquiana',           'https://baquiana.com/'),
(4,     'Unknown',                      null);