INSERT INTO public.uloga (id_uloga,naziv) VALUES
	 (2,'ROLE_ADMIN'),
	 (1,'ROLE_USER');
	 
INSERT INTO public.lokacija (id_lokacija,duljina,sirina,drzava,naselje,adresa) VALUES
	 (1,17.17239500000000000000000000,45.70221200000000000000000000,'Croatia','Grubišno Polje','M. A. Relkovića 19A'),
	 (2,18.01160800000000000000000000,45.16314300000000000000000000,'Croatia','Slavonski Brod','blabla 123'),
	 (3,15.98191900000000000000000000,45.81501100000000000000000000,'Croatia','Zagreb','blabla 12321'),
	 (4,18.40978200000000000000000000,45.30999700000000000000000000,'Croatia','Đakovo','Đakovačka 123'),
	 (5,16.09097100000000000000000000,45.33826200000000000000000000,'Croatia','Glina','Glinska 12345'),
	 (6,16.44019400000000000000000000,43.50813200000000000000000000,'Croatia','Split','Splitska 145'),
	 (7,-300.00000000000000000000000000,-300.00000000000000000000000000,'global','global','global');
	 
INSERT INTO public.korisnik (id_korisnik,ime,prezime,lozinka,email,aktivan,token,slika,id_lokacija) VALUES
	 (13,'Alen','Bažant','$2a$11$j78gL2GshaiAvv.5diRwiOVQOxLMCXnGbPP0gv5qjdYnyE0As5hDu','alen.bazant@gmail.com',false,NULL,NULL,1),
	 (11,'Marija','Oreč','$2a$11$NhAxTzySeh1.VRCKpi0gvutXaw4jPNsafYbUvM8xfpjcPvaPAcMMG','marija.orec@gmail.com',true,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJtYXJpamEub3JlY0BnbWFpbC5jb20iLCJleHAiOjE2MDk4OTM2NjksImlhdCI6MTYwOTgwNzI2OX0.tfKKc6YY9GroNN4ufBQHTJc_-aB7SQhz7VcFXEcURSUh0GczmrBIYm_C-i5qUFBQ',NULL,6),
	 (8,'Ivan','Jakas','$2a$11$8iGMwdQMAO4u7v4tWAon3.fxVQZaVWJaNQE6gAaRv6RAWSrYBoMRy','ivan.jakas@gmail.com',true,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJpdmFuLmpha2FzQGdtYWlsLmNvbSIsImV4cCI6MTYwOTg5NDAwMywiaWF0IjoxNjA5ODA3NjAzfQ.xvMcbnBqlC7z-ueWtwC8vIx_sQNzH4MGMNpyyoPKRHHiUI0YAUwSEjEPUHEWzQiI',NULL,3),
	 (7,'Iva','Bokšić','$2a$11$i.2gt0JhUkiVdDUr40u3xen3TEVA90zRwomFQkx19k338/zu5It9O','iva.boksic@gmail.com',true,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJpdmEuYm9rc2ljQGdtYWlsLmNvbSIsImV4cCI6MTYwOTg5NDgyMSwiaWF0IjoxNjA5ODA4NDIxfQ.UZcMgXSJSegJ1UteYe3E1XLBPT5xImYTTRnqMpWeIMjFH_DSKKxVHn4Us0o2og-5',NULL,2),
	 (3,'Jan','Roček','$2a$11$/.neaTA/gPK.xvyRdBpb0eDB5mIHSfHMZIxGMsUgpFmNFWfGaoaue','jan.rocek@gmail.com',true,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW4ucm9jZWtAZ21haWwuY29tIiwiZXhwIjoxNjA5ODk0ODMwLCJpYXQiOjE2MDk4MDg0MzB9.Q3OvLRmnZYItt_KrMBbR7M_OI6iJXKzQpWaQOqjzH5DsE-uyAxM7rYWtf6Y6V0un',NULL,1),
	 (9,'Dominik','Ćurić','$2a$11$NqC8EZQX4MGbbc6H.HtJ9u/..PJ0Q3nla0EdTXORbERZAVMO3i9vO','dominik.curic@gmail.com',true,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJkb21pbmlrLmN1cmljQGdtYWlsLmNvbSIsImV4cCI6MTYwOTg5NDgzOCwiaWF0IjoxNjA5ODA4NDM4fQ.yiZJsxfubXOJW09dwTaZXxMefSFoGLMMSTq-uhFCp85ajjDzqn0NIc8iOADPvYHO',NULL,4),
	 (12,'Matea','Lipovac','$2a$11$xFpccfd7N0ZsQqrWBJd6Puh9Mns00tHduP.CNXQBn1go.3c1uUN66','matea.lipovac@gmail.com',true,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJtYXRlYS5saXBvdmFjQGdtYWlsLmNvbSIsImV4cCI6MTYwOTg5NDg0OCwiaWF0IjoxNjA5ODA4NDQ4fQ.3xzefGnAvaxhr3HAGrmmt7-3w6X1PZhnswfy8fRb5MjTkMANM8vREPYuMv7HSFUS',NULL,3),
	 (10,'Robert','Đaković','$2a$11$55SzYYOz7k8mcE2Zw2dM.OXPIIEzhEao2hHoRVM2WNaT03wAc9WSG','robert.dakovic@gmail.com',true,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJyb2JlcnQuZGFrb3ZpY0BnbWFpbC5jb20iLCJleHAiOjE2MDk4OTQ4NTgsImlhdCI6MTYwOTgwODQ1OH0.vpTp2LsYMP9IILR43eCOfLnetI3QbMK1eBxpSAETngCPGodX57vVZenfmFOYn1cH',NULL,5);

INSERT INTO public.imaulogu (id_korisnik,id_uloga) VALUES
	 (13,1),
	 (3,1),
	 (3,2),
	 (7,1),
	 (8,1),
	 (9,1),
	 (10,1),
	 (11,1),
	 (12,1);
	 
INSERT INTO public.kandidatura (id_kandidatura,godina,id_lokacija) VALUES
	 (1,2021,7);
	 
INSERT INTO public.zahtjev (id_zahtjev,opis,tstmp,status,brojmobitela,id_lokacija,id_autor,id_izvrsitelj,primljenanotif,exectstmp) VALUES
	 (18,'Excepturi est ex animi vel repudiandae ut.','2021-04-03 10:08:02','EXECUTING','0981903333',3,7,11,false,NULL),
	 (10,'Suscipit est sint.','2021-04-03 10:08:02','EXECUTING','0981902222',3,11,12,false,NULL),
	 (13,'Aperiam ut dolor.','2021-04-03 10:08:02','EXECUTING','0981902222',6,11,10,false,NULL),
	 (19,'In illo non.','2021-04-03 10:08:02','FINALIZED','0981903333',2,7,11,false,'2021-01-05 01:30:37.57253'),
	 (16,'Sequi dolore aperiam voluptatem exercitationem qui.','2021-04-03 10:08:02','FINALIZED','0981903333',5,7,8,false,'2021-01-05 01:30:41.855142'),
	 (20,'Consequatur quam odio voluptatem.','2021-04-03 10:08:02','FINALIZED','0981903333',1,7,3,false,'2021-01-05 01:30:46.785018'),
	 (2,'Perferendis totam consequatur atque a recusandae omnis sunt delectus eos.','2021-04-03 10:08:02','ACTIVE','0981909999',5,8,NULL,false,NULL),
	 (3,'Omnis cupiditate repellendus nostrum.','2021-04-03 10:08:02','ACTIVE','0981909999',4,8,NULL,false,NULL),
	 (4,'Provident quidem tempore vel quis.','2021-04-03 10:08:02','ACTIVE','0981909999',3,8,NULL,false,NULL),
	 (5,'Minus ut qui quis enim necessitatibus autem et animi.','2021-04-03 10:08:02','ACTIVE','0981909999',2,8,NULL,false,NULL),
	 (6,'Aliquam optio est totam.','2021-04-03 10:08:02','ACTIVE','0981909999',1,8,NULL,false,NULL),
	 (9,'Natus veniam deleniti sed deserunt commodi et.','2021-04-03 10:08:02','ACTIVE','0981902222',2,11,NULL,false,NULL),
	 (11,'Optio nihil magnam.','2021-04-03 10:08:02','ACTIVE','0981902222',4,11,NULL,false,NULL),
	 (12,'Dolore occaecati sit rem quia.','2021-04-03 10:08:02','ACTIVE','0981902222',5,11,NULL,false,NULL),
	 (14,'Doloribus ut molestias aspernatur accusantium.','2021-04-03 10:08:02','ACTIVE','0981902222',6,7,NULL,false,NULL),
	 (21,'Ipsa sit animi consectetur necessitatibus recusandae aut autem iste alias.','2021-04-03 10:08:02','ACTIVE','0981903333',1,3,NULL,false,NULL),
	 (22,'Aut tempore vitae et molestias quis expedita ut repellendus.','2021-04-03 10:08:02','ACTIVE','0981903333',2,3,NULL,false,NULL),
	 (1,'Aut non reiciendis minima minus non aut facilis','2021-04-03 10:08:02','BLOCKED','0981904779',6,8,NULL,false,NULL),
	 (8,'Voluptatibus suscipit sint.','2021-04-03 10:08:02','BLOCKED','0981902222',1,11,NULL,false,NULL),
	 (17,'A molestiae velit.','2021-04-03 10:08:02','BLOCKED','0981903333',4,7,NULL,false,NULL),
	 (25,'Eaque ipsa eaque.','2021-04-03 10:08:02','ACTIVE','0981903333',5,3,NULL,false,NULL),
	 (27,'Eos libero non aliquid excepturi sunt.','2021-04-03 10:08:02','ACTIVE','0981903333',6,9,NULL,false,NULL),
	 (28,'Quasi ut eos est aut vero minima et sint sapiente.','2021-04-03 10:08:02','ACTIVE','0981903333',5,9,NULL,false,NULL),
	 (31,'Ad aut unde aut dignissimos.','2021-04-03 10:08:02','ACTIVE','0981903333',2,9,NULL,false,NULL),
	 (32,'Occaecati maxime voluptas eveniet voluptatem explicabo sapiente perferendis.','2021-04-03 10:08:02','ACTIVE','0981903333',1,9,NULL,false,NULL),
	 (33,'Excepturi non eligendi aut animi sunt eius at.','2021-04-03 10:08:02','ACTIVE','0981903333',1,12,NULL,false,NULL),
	 (35,'Ipsa et est.','2021-04-03 10:08:02','ACTIVE','0981903333',3,12,NULL,false,NULL),
	 (39,'A voluptatum impedit voluptas maiores asperiores quo.','2021-04-03 10:08:02','ACTIVE','0981903333',6,10,NULL,false,NULL),
	 (43,'Est possimus ea deleniti porro amet qui doloremque.','2021-04-03 10:08:02','ACTIVE','0981903333',2,10,NULL,false,NULL),
	 (44,'Possimus quos dolores quia qui consequatur.','2021-04-03 10:08:02','ACTIVE','0981903333',1,10,NULL,false,NULL),
	 (24,'Cumque qui ut.','2021-04-03 10:08:02','BLOCKED','0981903333',4,3,NULL,false,NULL),
	 (29,'Recusandae ad temporibus consequatur.','2021-04-03 10:08:02','BLOCKED','0981903333',4,9,NULL,false,NULL),
	 (37,'Cumque distinctio quibusdam accusamus nam atque et cumque ut.','2021-04-03 10:08:02','BLOCKED','0981903333',5,12,NULL,false,NULL),
	 (41,'Debitis quidem et voluptatem repellat laudantium ab molestiae.','2021-04-03 10:08:02','BLOCKED','0981903333',4,10,NULL,false,NULL),
	 (42,'Facilis officiis esse natus soluta consequatur voluptatem quam.','2021-04-03 10:08:02','EXECUTING','0981903333',3,10,3,false,NULL),
	 (34,'Optio optio eius soluta ullam.','2021-04-03 10:08:02','EXECUTING','0981903333',2,12,8,false,NULL),
	 (36,'Aut nihil rerum saepe eum dignissimos.','2021-04-03 10:08:02','EXECUTING','0981903333',4,12,7,false,NULL),
	 (23,'Quia consequuntur iusto aspernatur illo voluptatem quia.','2021-04-03 10:08:02','EXECUTING','0981903333',3,3,9,false,NULL),
	 (40,'Quod molestiae ipsam nemo id molestiae.','2021-04-03 10:08:02','FINALIZED','0981903333',5,10,9,false,'2021-01-05 01:26:04.228286'),
	 (38,'Maxime quis deleniti eum quam quis consequatur.','2021-04-03 10:08:02','FINALIZED','0981903333',6,12,10,false,'2021-01-05 01:27:12.028119'),
	 (30,'Sit quos error repellendus repellendus.','2021-04-03 10:08:02','EXECUTING','0981903333',3,9,7,false,'2021-01-05 01:27:43.945475'),
	 (26,'Soluta voluptatem quo qui deserunt ut rerum.','2021-04-03 10:08:02','FINALIZED','0981903333',6,3,12,false,'2021-01-05 01:29:24.785815');
	 
INSERT INTO public.ocjenjivanje (id_ocjenjivanje,ocjena,komentar,id_ocjenjivac,id_ocjenjeni,id_zahtjev) VALUES
	 (1,4,'blabla',8,7,16),
	 (2,3,'Dolor voluptatem autem in cupiditate sit necessitatibus aperiam possimus non.',7,8,16),
	 (3,5,'Fuga ut in in.',7,11,19),
	 (4,5,'Inventore quo illo dignissimos voluptas consectetur consequatur.',7,3,20),
	 (5,3,'Repellat dolor quia consequatur iure nihil perspiciatis incidunt.',11,7,19),
	 (6,5,'Natus nesciunt quidem provident ut delectus fuga aut et.',3,7,20),
	 (7,2,'Consequatur molestiae molestiae qui earum ut voluptatem voluptatem.',3,12,26),
	 (8,3,'Ullam eligendi quia eum nihil aspernatur iusto non iste.',12,3,26),
	 (9,3,'Vel non laboriosam consectetur repellat voluptatem qui qui.',12,10,38),
	 (10,4,'Reprehenderit mollitia totam necessitatibus et ducimus ducimus omnis qui.',10,12,38),
	 (11,4,'Fuga voluptates rerum libero.',10,9,40),
	 (12,2,'Perferendis corrupti et aut ad sed corporis.',9,10,40);
