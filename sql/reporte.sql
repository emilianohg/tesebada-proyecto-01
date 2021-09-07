USE tesebada;
GO

DROP TABLE IF EXISTS retiros;
GO
CREATE TABLE retiros (
	 idretiro int identity(1,1),
     nocuenta varchar(50) not null,
     cantidad money not null,
     responsable varchar(50) not null,
	 created_at datetime not null default current_timestamp,
     primary key (idretiro),
	 foreign key (nocuenta) references cheques(nocuenta)
);


DROP PROCEDURE IF EXISTS reg_withdrawal;
GO
CREATE PROCEDURE reg_withdrawal
	@responsable VARCHAR(50),
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

        if (@importe < 0) return;

        UPDATE tesebada.dbo.cheques SET importe = @importe WHERE nocuenta = @nocuenta;
		insert into tesebada.dbo.retiros values (@nocuenta, @amount, @responsable, default);
    COMMIT;
END;





DECLARE @importe_total MONEY;
DECLARE @estatus CHAR(1);
EXEC reg_withdrawal "Efrain","00001", 55, @importe_total OUTPUT, @estatus OUTPUT;
SELECT @importe_total AS importe, @estatus AS estatus;

SELECT * FROM tesebada.dbo.cheques;


insert into tesebada.dbo.cheques values ('00002',50000,1)

select * from retiros;