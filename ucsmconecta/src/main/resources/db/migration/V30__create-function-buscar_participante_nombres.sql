-- FUNCTION: Para la busqqueda de names de participantes
CREATE OR REPLACE FUNCTION buscar_participante_nombres(p_busqueda TEXT)
    RETURNS TABLE(
                     nombres VARCHAR,
                     apPaterno VARCHAR,
                     apMaterno VARCHAR,
                     numDocumento VARCHAR,
                     estado VARCHAR
                 )
AS $$
BEGIN
    RETURN QUERY
        SELECT
            p.nombres,
            p.ap_paterno,
            p.ap_materno,
            p.num_documento,
            p.estado
        FROM participante p
        WHERE
            -- Buscar por names
            LOWER(p.nombres) LIKE LOWER(CONCAT('%', p_busqueda, '%'));
END;
$$ LANGUAGE plpgsql;