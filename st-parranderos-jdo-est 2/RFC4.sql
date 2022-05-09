/* ------------- */
/*      RFC 4    */
/* ------------- */
SELECT
    CONSUMOS.*,
    EMPLEADOS_GENERAL.nombre
FROM
    CONSUMOS
    INNER JOIN EMPLEADOS_GENERAL ON (
        CONSUMOS.empleado_num_doc = EMPLEADOS_GENERAL.num_doc
    )
WHERE
    EMPLEADO_NUM_DOC = 1234;