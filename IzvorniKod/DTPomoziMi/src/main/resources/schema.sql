CREATE TABLE Lokacija
(
  duljina NUMERIC(17,14) NOT NULL,
  sirina NUMERIC(17,14) NOT NULL,
  drzava VARCHAR(50) NOT NULL,
  naselje VARCHAR(60) NOT NULL,
  adresa VARCHAR(80) NOT NULL,
  PRIMARY KEY (duljina, sirina)
);

CREATE TABLE Kandidatura
(
  godina INT NOT NULL,
  duljina NUMERIC(17,14) NOT NULL,
  sirina NUMERIC(17,14) NOT NULL,
  PRIMARY KEY (godina, duljina, sirina),
  FOREIGN KEY (duljina, sirina) REFERENCES Lokacija(duljina, sirina)
);

CREATE TABLE Korisnik
(
  ID_Korisnik AUTO_INCREMENT NOT NULL,
  ime VARCHAR(30) NOT NULL,
  prezime VARCHAR(30) NOT NULL,
  lozinka VARCHAR(70) NOT NULL,
  email VARCHAR(250) NOT NULL,
  uloga VARCHAR(15) NOT NULL,
  aktivan BOOLEAN NOT NULL,
  token VARCHAR(500) NOT NULL,
  duljina NUMERIC(17,14),
  sirina NUMERIC(17,14),
  PRIMARY KEY (ID_Korisnik),
  FOREIGN KEY (duljina, sirina) REFERENCES Lokacija(duljina, sirina),
  UNIQUE (email)
);

CREATE TABLE Zahtjev
(
  ID_Zahtjev AUTO_INCREMENT NOT NULL,
  opis VARCHAR(250) NOT NULL,
  datum DATE,
  vrijeme TIME,
  status VARCHAR(15) NOT NULL,
  brojMobitela VARCHAR(15),
  duljina NUMERIC(17,14),
  sirina NUMERIC(17,14),
  ID_Autor INT NOT NULL,
  PRIMARY KEY (ID_Zahtjev),
  FOREIGN KEY (duljina, sirina) REFERENCES Lokacija(duljina, sirina),
  FOREIGN KEY (ID_Autor) REFERENCES Korisnik(ID_Korisnik)
);

CREATE TABLE Ocijenjivanje
(
  ocijena INT NOT NULL,
  komentar VARCHAR(150),
  ID_Ocijenjeni INT NOT NULL,
  ID_Ocijenjivac INT NOT NULL,
  PRIMARY KEY (ID_Ocijenjeni, ID_Ocijenjivac),
  FOREIGN KEY (ID_Ocijenjeni) REFERENCES Korisnik(ID_Korisnik),
  FOREIGN KEY (ID_Ocijenjivac) REFERENCES Korisnik(ID_Korisnik)
);

CREATE TABLE Izvrsavanje
(
  primljeno BOOLEAN NOT NULL,
  ocijena INT,
  komentar VARCHAR(150),
  ID_Zahtjev INT NOT NULL,
  ID_Izvrsitelj INT NOT NULL,
  PRIMARY KEY (ID_Zahtjev, ID_Izvrsitelj),
  FOREIGN KEY (ID_Zahtjev) REFERENCES Zahtjev(ID_Zahtjev),
  FOREIGN KEY (ID_Izvrsitelj) REFERENCES Korisnik(ID_Korisnik),
  CONSTRAINT CHK_OCIJENA CHECK (ocijena BETWEEN 1 AND 5)
  /*check ocijena not null when status izvrsen...*/
);

CREATE TABLE Kandidiranje
(
  godina INT NOT NULL,
  duljina NUMERIC(17,14) NOT NULL,
  sirina NUMERIC(17,14) NOT NULL,
  ID_Korisnik INT NOT NULL,
  PRIMARY KEY (godina, duljina, sirina, ID_Korisnik),
  FOREIGN KEY (godina, duljina, sirina) REFERENCES Kandidatura(godina, duljina, sirina),
  FOREIGN KEY (ID_Korisnik) REFERENCES Korisnik(ID_Korisnik)
);
