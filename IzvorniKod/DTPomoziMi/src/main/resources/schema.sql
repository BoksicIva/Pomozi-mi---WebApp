CREATE TABLE Lokacija
(
  ID_Lokacija BIGINT AUTO_INCREMENT NOT NULL,
  duljina NUMERIC(30,26) NOT NULL,
  sirina NUMERIC(30,26) NOT NULL,
  drzava VARCHAR(50) NOT NULL,
  naselje VARCHAR(60) NOT NULL,
  adresa VARCHAR(80) NOT NULL,
  PRIMARY KEY (ID_Lokacija),
  UNIQUE (duljina, sirina)
);

CREATE TABLE Kandidatura
( 
  ID_Kandidatura BIGINT AUTO_INCREMENT NOT NULL,
  godina INT NOT NULL,
  ID_Lokacija BIGINT NOT NULL,
  PRIMARY KEY (ID_Kandidatura),
  FOREIGN KEY (ID_Lokacija) REFERENCES Lokacija(ID_Lokacija),
  UNIQUE (ID_Lokacija, godina)
);

CREATE TABLE Uloga
(
  ID_Uloga BIGINT AUTO_INCREMENT NOT NULL,
  naziv VARCHAR(15) NOT NULL,
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
  slika VARCHAR(500),
  ID_Lokacija BIGINT,
  PRIMARY KEY (ID_Korisnik),
  FOREIGN KEY (ID_Lokacija) REFERENCES Lokacija(ID_Lokacija) ON DELETE CASCADE,
  UNIQUE (email)
);

CREATE TABLE Zahtjev
(
  ID_Zahtjev BIGINT AUTO_INCREMENT NOT NULL,
  opis VARCHAR(500) NOT NULL,
  tstmp TIMESTAMP,
  status VARCHAR(15) NOT NULL,
  brojMobitela VARCHAR(15),
  ID_Lokacija BIGINT,
  ID_Autor BIGINT NOT NULL,
  ID_Izvrsitelj BIGINT, -- ako nije null, a izvrsen false, onda odabran...
  primljenaNotif BOOLEAN NOT NULL,
  execTstmp TIMESTAMP,
  PRIMARY KEY (ID_Zahtjev),
  FOREIGN KEY (ID_Autor) REFERENCES Korisnik(ID_Korisnik) ON DELETE CASCADE,
  FOREIGN KEY (ID_Izvrsitelj) REFERENCES Korisnik(ID_Korisnik) ON DELETE SET NULL,
  FOREIGN KEY (ID_Lokacija) REFERENCES Lokacija(ID_Lokacija) ON DELETE SET NULL
);

CREATE TABLE Ocjenjivanje
(
  ID_Ocjenjivanje BIGINT AUTO_INCREMENT NOT NULL,
  ocjena INT NOT NULL,
  komentar VARCHAR(250),
  ID_Ocjenjivac BIGINT NOT NULL,
  ID_Ocjenjeni BIGINT NOT NULL,
  ID_Zahtjev BIGINT,
  PRIMARY KEY (ID_Ocjenjivanje),
  FOREIGN KEY (ID_Ocjenjivac) REFERENCES Korisnik(ID_Korisnik) ON DELETE CASCADE,
  FOREIGN KEY (ID_Ocjenjeni) REFERENCES Korisnik(ID_Korisnik) ON DELETE CASCADE,
  FOREIGN KEY (ID_Zahtjev) REFERENCES Zahtjev(ID_Zahtjev) ON DELETE CASCADE,
  CONSTRAINT CHK_OCJENA CHECK (OCJENA BETWEEN 1 AND 5)
);

CREATE TABLE Kandidiranje
(
  ID_Korisnik BIGINT NOT NULL,
  ID_Kandidatura BIGINT NOT NULL,
  PRIMARY KEY (ID_Korisnik, ID_Kandidatura),
  FOREIGN KEY (ID_Korisnik) REFERENCES Korisnik(ID_Korisnik) ON DELETE CASCADE,
  FOREIGN KEY (ID_Kandidatura) REFERENCES Kandidatura(ID_Kandidatura) ON DELETE CASCADE
);

CREATE TABLE ImaUlogu
(
  ID_Korisnik BIGINT NOT NULL,
  ID_Uloga BIGINT NOT NULL,
  PRIMARY KEY (ID_Korisnik, ID_Uloga),
  FOREIGN KEY (ID_Korisnik) REFERENCES Korisnik(ID_Korisnik) ON DELETE CASCADE,
  FOREIGN KEY (ID_Uloga) REFERENCES Uloga(ID_Uloga) ON DELETE CASCADE
);

