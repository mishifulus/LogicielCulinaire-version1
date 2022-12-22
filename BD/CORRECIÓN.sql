uSE myspa;
SELECT * FROM myspa.v_empleados;
SELECT * FROM myspa.v_clientes;
SELECT * FROM myspa.sucursal;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarCliente`(   /* Datos Personales */
                                    IN  var_nombre          VARCHAR(64),
                                    IN  var_apellidoPaterno VARCHAR(64),
                                    IN  var_apellidoMaterno VARCHAR(64),
                                    IN  var_genero          VARCHAR(2),
                                    IN  var_domicilio       VARCHAR(200),
                                    IN	var_telefono        VARCHAR(25),
                                    IN	var_rfc             VARCHAR(14),
                                    
                                    /* Datos de Usuario */
                                    IN  var_nombreUsuario   VARCHAR(48),
                                    IN  var_contrasenia     VARCHAR(48),
                                    IN  var_rol             VARCHAR(24),
                                    
                                    /* Datos de Cliente */
                                    IN  var_correo          VARCHAR(200),
                                    OUT  var_numeroUnico     VARCHAR(70),
                                    
                                    /* Valores de Retorno */
                                    OUT var_idPersona       INT,
                                    OUT var_idUsuario       INT,
                                    OUT var_idCliente       INT
                                )
BEGIN
        -- Comenzamos insertando en la tabla Persona:
        INSERT INTO persona ( nombre, apellidoPaterno, apellidoMaterno, genero,
                              domicilio, telefono, rfc) 
                    VALUES( var_nombre, var_apellidoPaterno, var_apellidoMaterno, 
                            var_genero, var_domicilio, var_telefono, var_rfc);
        -- Recuperamos el ID de la Persona que se generÃÂ³:
        SET var_idPersona = LAST_INSERT_ID();


        -- Insertamos en la tabla de Usuario con los datos de seguridad:
        INSERT INTO usuario ( nombreUsuario, contrasenia, rol) 
                    VALUES( var_nombreUsuario, var_contrasenia, var_rol);
        -- Recuperamos el ID del Usuario que se generÃÂ³ de forma automÃÂ¡tica:
        SET var_idUsuario = LAST_INSERT_ID();

		-- Generamos el número de cliente
        SET var_numeroUnico="";
		IF  LENGTH(var_rfc) >= 4 THEN
            SET var_numeroUnico = SUBSTRING(var_rfc, 1, 4);
        END IF;
        SET var_numeroUnico = CONCAT(var_numeroUnico, CAST(UNIX_TIMESTAMP() AS CHAR));
        
        -- Finalmente, insertamos en la tabla cliente:
        INSERT INTO cliente ( correo, numeroUnico, estatus,
                              idPersona, idUsuario) 
                    VALUES( var_correo, var_numeroUnico, 1,
                            var_idPersona, var_idUsuario);
        -- Recuperamos el ID del CLiente que se generÃÂ³ de forma automÃÂ¡tica:
        SET var_idCliente = LAST_INSERT_ID();
    END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarCliente`(     /* Datos Personales */
                                        IN  var_nombre          VARCHAR(64),
                                        IN  var_apellidoPaterno VARCHAR(64),
                                        IN  var_apellidoMaterno VARCHAR(64),
                                        IN  var_genero          VARCHAR(2),
                                        IN  var_domicilio       VARCHAR(200),
                                        IN  var_telefono        VARCHAR(25),
                                        IN  var_rfc             VARCHAR(14),

                                        /* Datos de Usuario */
                                        IN  var_nombreUsuario   VARCHAR(48),
                                        IN  var_contrasenia     VARCHAR(48),
                                        IN  var_rol             VARCHAR(24),

                                        /* Datos de Cliente */
                                        IN  var_correo          VARCHAR(200),
                                        IN  var_numeroUnico           VARCHAR(70),

                                        /* ID's de las tablas afectadas */
                                        IN var_idPersona        INT,
                                        IN var_idUsuario        INT,
                                        IN var_idCliente        INT
                                    )
BEGIN
        -- Comenzamos actualizando los datos personales:
        UPDATE  persona SET     nombre = var_nombre, 
                                apellidoPaterno = var_apellidoPaterno,
                                apellidoMaterno = var_apellidoMaterno,
                                genero = var_genero,
                                domicilio = var_domicilio,
                                telefono = var_telefono, 
                                rfc = var_rfc
                        WHERE   idPersona = var_idPersona;

        -- DespuÃ©s actualizamos los datos de seguridad:
        UPDATE  usuario SET     nombreUsuario = var_nombreUsuario, 
                                contrasenia = var_contrasenia,
                                rol = var_rol 
                        WHERE   idUsuario = var_idUsuario;

        -- Finalmente, actualizamos los datos del cliente:
        UPDATE  cliente SET     correo = var_correo,
                                numeroUnico = var_numeroUnico,
                                estatus = 1
                        WHERE   idCliente = var_idCliente;

    END$$
DELIMITER ;

