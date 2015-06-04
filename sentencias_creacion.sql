
CREATE TABLE IF NOT EXISTS usuario (
	mail varchar(200) NOT NULL,
	nombre varchar(50) NOT NULL,
	contrasegna varchar(50) NOT NULL,
	apellidos varchar(100) NOT NULL,
	nick varchar(50) NOT NULL UNIQUE,
	PRIMARY KEY(mail)
);

CREATE TABLE IF NOT EXISTS evento (
	id int(10) NOT NULL AUTO_INCREMENT,
	titulo varchar(200) NOT NULL,
	lugar varchar(100) NOT NULL,
	fecha DATETIME NOT NULL,
	descripcion varchar(2000),
	mailCreador varchar(200) NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (mailCreador) REFERENCES usuario(mail)

);

CREATE TABLE IF NOT EXISTS invitacion(
	id int NOT NULL AUTO_INCREMENT,
	mailCreador varchar(200) NOT NULL,
	idEvento int(10) NOT NULL,
	estado varchar(20) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE (mailCreador,idEvento),
	FOREIGN KEY(mailCreador) REFERENCES usuario(mail) ON DELETE CASCADE,
	FOREIGN KEY(idEvento) REFERENCES evento(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS mensaje(
	idEvento int NOT NULL,
	idMensaje int NOT NULL AUTO_INCREMENT,
	creador varchar(50) NOT NULL,
	contenido varchar(250) NOT NULL,
	fecha DATETIME,
	PRIMARY KEY(idMensaje),
	FOREIGN KEY (idEvento) REFERENCES evento(id) ON DELETE CASCADE,
	FOREIGN KEY (creador) REFERENCES usuario(nick) ON DELETE CASCADE

);
