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

