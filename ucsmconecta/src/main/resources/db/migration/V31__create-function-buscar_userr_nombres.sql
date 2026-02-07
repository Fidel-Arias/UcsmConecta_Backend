-- FUNCTION: Para la busqueda de names de colaboradores y admin
CREATE OR REPLACE FUNCTION buscar_user_nombres(p_busqueda TEXT)
    RETURNS TABLE(
                     nombres VARCHAR,
                     a_paterno VARCHAR,
                     a_materno VARCHAR,
                     email VARCHAR,
                     estado BOOL
                 )
AS $$
BEGIN
    RETURN QUERY
        SELECT
            c.nombres,
            c.a_paterno,
            c.a_materno,
            c.email,
            c.estado
        FROM colaborador c
        WHERE
            -- Buscar por names
            LOWER(c.nombres) LIKE LOWER(CONCAT('%', p_busqueda, '%'));
END;
$$ LANGUAGE plpgsql;