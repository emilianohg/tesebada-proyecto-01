CREATE DATABASE tesebada;

use tesebada;

CREATE TABLE cheques (
                         nocuenta varchar(50) not null,
                         importe money not null,
                         estatus char(1) not null,
                         primary key (nocuenta)
);

USE tesebada;
GO
DROP PROCEDURE IF EXISTS withdraw;
GO
CREATE PROCEDURE withdraw
    @nocuenta VARCHAR(50),
    @amount MONEY,
    @importe MONEY OUTPUT,
    @estatus CHAR(1) OUTPUT
AS
BEGIN
BEGIN TRANSACTION;
SELECT @importe = importe, @estatus = estatus FROM tesebada.dbo.cheques WITH (UPDLOCK) WHERE nocuenta = @nocuenta;
WAITFOR DELAY '00:00:01.000';
        SET @importe = @importe - @amount;
UPDATE tesebada.dbo.cheques SET importe = @importe WHERE nocuenta = @nocuenta;
COMMIT;
END;

GO
USE tesebada;
DECLARE @importe_total MONEY;
DECLARE @estatus CHAR(1);
EXEC withdraw "00001", 5, @importe_total OUTPUT, @estatus OUTPUT;
SELECT @importe_total AS importe, @estatus AS estatus;

SELECT * FROM tesebada.dbo.cheques;




USE tesebada;
GO

DROP TABLE IF EXISTS usuarios;
create table usuarios (
                          id INT,
                          nombre VARCHAR(50),
                          primary key (id)
);

DROP TABLE IF EXISTS retiros;
GO
CREATE TABLE retiros (
                         idretiro int identity(1,1),
                         nocuenta varchar(50) not null,
                         cantidad money not null,
                         id_usuario int not null,
                         created_at datetime not null default current_timestamp,
                         primary key (idretiro),
                         foreign key (nocuenta) references cheques(nocuenta),
                         foreign key (id_usuario) references usuarios(id)
);


DROP PROCEDURE IF EXISTS reg_withdrawal;
GO
CREATE PROCEDURE reg_withdrawal
    @responsable INT,
    @nocuenta VARCHAR(50),
    @amount MONEY,
    @importe MONEY OUTPUT,
    @estatus CHAR(1) OUTPUT,
    @hasError BIT OUTPUT
AS
BEGIN
BEGIN TRANSACTION;
        SET @hasError = 0;
SELECT @importe = importe, @estatus = estatus FROM tesebada.dbo.cheques WITH (UPDLOCK) WHERE nocuenta = @nocuenta;
WAITFOR DELAY '00:00:01.000';

        if (@importe - @amount < 0) BEGIN
            SET @hasError = 1;
ROLLBACK TRAN;
RETURN;
END;

        SET @importe = @importe - @amount;

UPDATE tesebada.dbo.cheques SET importe = @importe WHERE nocuenta = @nocuenta;
insert into tesebada.dbo.retiros values (@nocuenta, @amount, @responsable, default);
COMMIT;
END;
GO

    DECLARE @importe_total MONEY;
DECLARE @estatus CHAR(1);
EXEC reg_withdrawal "Efrain","00001", 55, @importe_total OUTPUT, @estatus OUTPUT;
SELECT @importe_total AS importe, @estatus AS estatus;

USE tesebada;

SELECT * FROM usuarios;
GO

INSERT INTO tesebada.dbo.usuarios (id, nombre) VALUES (1, "Emiliano");
INSERT INTO tesebada.dbo.usuarios VALUES ("Gerardo");
INSERT INTO tesebada.dbo.usuarios VALUES ("Efrain");