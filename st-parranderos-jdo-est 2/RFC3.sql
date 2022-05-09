/* ------------- */
/*      RFC 3    */
/* ------------- */
WITH DIAS_OCUPADOS AS (
    SELECT
        RESERVAS_HABITACIONES.numero_habitacion AS NUM_HABITACION,
        SUM(
            EXTRACT(
                DAY
                FROM
                    (
                        RESERVAS_HABITACIONES.fecha_salida - RESERVAS_HABITACIONES.fecha_entrada
                    )
            )
        ) AS DIAS
    FROM
        RESERVAS_HABITACIONES
    GROUP BY
        RESERVAS_HABITACIONES.numero_habitacion
)
SELECT
    DIAS_OCUPADOS.NUM_HABITACION,
    ROUND(
        DIAS_OCUPADOS.DIAS / EXTRACT(
            DAY
            FROM
                (
                    TO_TIMESTAMP('31-12-2022 12:00:00', 'DD-MM-YYYY HH:MI:SS') - TO_TIMESTAMP('01-01-2022 12:00:00', 'DD-MM-YYYY HH:MI:SS')
                )
        ),
        3
    ) AS INDICE_OCUPACION
FROM
    DIAS_OCUPADOS;