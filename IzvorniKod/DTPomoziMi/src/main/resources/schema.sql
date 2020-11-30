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

CREATE TABLE Uloga
(
  naziv VARCHAR(15) NOT NULL,
  ID_Uloga BIGINT AUTO_INCREMENT NOT NULL,
  PRIMARY KEY (ID_Uloga),
  UNIQUE (naziv)
);

CREATE TABLE Korisnik
(
  ID_Korisnik BIGINT AUTO_INCREMENT NOT NULL,
  ime VARCHAR(50) NOT NULL,
  prezime VARCHAR(50) NOT NULL,
  lozinka VARCHAR(70) NOT NULL,
  email VARCHAR(250) NOT NULL,
  aktivan BOOLEAN NOT NULL,
  token VARCHAR(500),
  duljina NUMERIC(17,14),
  sirina NUMERIC(17,14),
  PRIMARY KEY (ID_Korisnik),
  FOREIGN KEY (duljina, sirina) REFERENCES Lokacija(duljina, sirina),
  UNIQUE (email)
);

CREATE TABLE Zahtjev
(
  ID_Zahtjev BIGINT AUTO_INCREMENT NOT NULL,
  opis VARCHAR(500) NOT NULL,
  datum DATE,
  vrijeme TIME,
  status VARCHAR(15) NOT NULL,
  brojMobitela VARCHAR(15),
  duljina NUMERIC(17,14),
  sirina NUMERIC(17,14),
  ID_Autor INT NOT NULL,
  ID_Izvrsitelj INT,
  PRIMARY KEY (ID_Zahtjev),
  FOREIGN KEY (duljina, sirina) REFERENCES Lokacija(duljina, sirina),
  FOREIGN KEY (ID_Autor) REFERENCES Korisnik(ID_Korisnik),
  FOREIGN KEY (ID_Izvrsitelj) REFERENCES Korisnik(ID_Korisnik)
);

CREATE TABLE Izvrsavanje
(
  primljenNotif BOOLEAN NOT NULL,
  ID_Zahtjev INT NOT NULL,
  PRIMARY KEY (ID_Zahtjev),
  FOREIGN KEY (ID_Zahtjev) REFERENCES Zahtjev(ID_Zahtjev)
);

CREATE TABLE Ocjenjivanje
(
  ocjena INT NOT NULL,
  komentar VARCHAR(250),
  ID_Ocjenjivanje INT NOT NULL,
  ID_Ocjenjivac INT NOT NULL,
  ID_Ocjenjeni INT NOT NULL,
  ID_Zahtjev INT,
  PRIMARY KEY (ID_Ocjenjivanje),
  FOREIGN KEY (ID_Ocjenjivac) REFERENCES Korisnik(ID_Korisnik),
  FOREIGN KEY (ID_Ocjenjeni) REFERENCES Korisnik(ID_Korisnik),
  FOREIGN KEY (ID_Zahtjev) REFERENCES Izvrsavanje(ID_Zahtjev),
  CONSTRAINT CHK_OCJENA CHECK (OCJENA BETWEEN 1 AND 5)
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

CREATE TABLE ImaUlogu
(
  ID_Korisnik BIGINT NOT NULL,
  ID_Uloga BIGINT NOT NULL,
  PRIMARY KEY (ID_Korisnik, ID_Uloga),
  FOREIGN KEY (ID_Korisnik) REFERENCES Korisnik(ID_Korisnik),
  FOREIGN KEY (ID_Uloga) REFERENCES Uloga(ID_Uloga)
);

