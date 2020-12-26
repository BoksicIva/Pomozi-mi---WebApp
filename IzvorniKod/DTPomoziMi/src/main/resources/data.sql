INSERT INTO public.uloga (id_uloga,naziv) VALUES
	 (1,'ROLE_USER');
	 
INSERT INTO public.lokacija (id_lokacija,duljina,sirina,drzava,naselje,adresa) VALUES
	 (1,17.17239500000000,45.70221200000000,'Croatia','Grubišno Polje','M. A. Relkovića'),
	 (2,18.01160800000000,45.16314300000000,'Croatia','Slavonski Brod','blabla'),
	 (3,15.98191900000000,45.81501100000000,'Croatia','Zagreb','blabla'),
	 (4,18.40978200000000,45.30999700000000,'Croatia','Đakovo','vlavla'),
	 (5,16.09097100000000,45.33826200000000,'Croatia','Glina','vlavla'),
	 (6,16.44019400000000,43.50813200000000,'Croatia','Split','vlavla');
	 
INSERT INTO public.korisnik (id_korisnik,ime,prezime,lozinka,email,aktivan,token,slika,id_lokacija) VALUES
	 (3,'Jan','Roček','$2a$11$/.neaTA/gPK.xvyRdBpb0eDB5mIHSfHMZIxGMsUgpFmNFWfGaoaue','jan.rocek@gmail.com',true,NULL,NULL,1),
	 (7,'Iva','Bokšić','$2a$11$i.2gt0JhUkiVdDUr40u3xen3TEVA90zRwomFQkx19k338/zu5It9O','iva.boksic@gmail.com',true,NULL,NULL,2),
	 (8,'Ivan','Jakas','$2a$11$8iGMwdQMAO4u7v4tWAon3.fxVQZaVWJaNQE6gAaRv6RAWSrYBoMRy','ivan.jakas@gmail.com',true,NULL,NULL,3),
	 (9,'Dominik','Ćurić','$2a$11$NqC8EZQX4MGbbc6H.HtJ9u/..PJ0Q3nla0EdTXORbERZAVMO3i9vO','dominik.curic@gmail.com',true,NULL,NULL,4),
	 (10,'Robert','Đaković','$2a$11$55SzYYOz7k8mcE2Zw2dM.OXPIIEzhEao2hHoRVM2WNaT03wAc9WSG','robert.dakovic@gmail.com',true,NULL,NULL,5),
	 (11,'Marija','Oreč','$2a$11$NhAxTzySeh1.VRCKpi0gvutXaw4jPNsafYbUvM8xfpjcPvaPAcMMG','marija.orec@gmail.com',true,NULL,NULL,6),
	 (12,'Matea','Lipovac','$2a$11$xFpccfd7N0ZsQqrWBJd6Puh9Mns00tHduP.CNXQBn1go.3c1uUN66','matea.lipovac@gmail.com',true,NULL,NULL,3),
	 (13,'Alen','Bažant','$2a$11$j78gL2GshaiAvv.5diRwiOVQOxLMCXnGbPP0gv5qjdYnyE0As5hDu','alen.bazant@gmail.com',true,NULL,NULL,1);
	 
INSERT INTO public.imaulogu (id_korisnik,id_uloga) VALUES
	 (13,1),
	 (3,1),
	 (7,1),
	 (8,1),
	 (9,1),
	 (10,1),
	 (11,1),
	 (12,1);
	 
INSERT INTO public.zahtjev (id_zahtjev,opis,tstmp,status,brojmobitela,id_lokacija,id_autor,id_izvrsitelj,primljenanotif,exectstmp) VALUES
	 (1,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',1,3,NULL,false,NULL),
	 (2,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',3,3,NULL,false,NULL),
	 (3,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',6,3,NULL,false,NULL),
	 (4,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',5,3,NULL,false,NULL),
	 (5,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',4,3,NULL,false,NULL),
	 (6,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',2,3,NULL,false,NULL),
	 (7,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',1,7,NULL,false,NULL),
	 (8,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',3,7,NULL,false,NULL),
	 (9,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',6,7,NULL,false,NULL),
	 (10,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',5,7,NULL,false,NULL),
	 (11,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',4,7,NULL,false,NULL),
	 (12,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',2,7,NULL,false,NULL),
	 (13,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',1,8,NULL,false,NULL),
	 (14,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',3,8,NULL,false,NULL),
	 (15,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',6,8,NULL,false,NULL),
	 (16,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',5,8,NULL,false,NULL),
	 (17,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',4,8,NULL,false,NULL),
	 (18,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',2,8,NULL,false,NULL),
	 (19,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',1,9,NULL,false,NULL),
	 (20,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',3,9,NULL,false,NULL),
	 (21,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',6,9,NULL,false,NULL),
	 (22,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',5,9,NULL,false,NULL),
	 (23,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',4,9,NULL,false,NULL),
	 (24,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',2,9,NULL,false,NULL),
	 (25,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',1,10,NULL,false,NULL),
	 (26,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',3,10,NULL,false,NULL),
	 (27,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',6,10,NULL,false,NULL),
	 (28,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',5,10,NULL,false,NULL),
	 (29,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',4,10,NULL,false,NULL),
	 (30,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',2,10,NULL,false,NULL),
	 (31,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',1,12,NULL,false,NULL),
	 (32,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',3,12,NULL,false,NULL),
	 (33,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',6,12,NULL,false,NULL),
	 (34,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',5,12,NULL,false,NULL),
	 (35,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',4,12,NULL,false,NULL),
	 (36,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',2,12,NULL,false,NULL),
	 (37,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',1,11,NULL,false,NULL),
	 (38,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',3,11,NULL,false,NULL),
	 (39,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',6,11,NULL,false,NULL),
	 (40,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',5,11,NULL,false,NULL),
	 (41,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',4,11,NULL,false,NULL),
	 (42,'masna kobasa','2021-02-03 10:08:02','ACTIVE','0981904779',2,11,NULL,false,NULL);
