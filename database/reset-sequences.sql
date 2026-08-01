-- ============================================================
-- Resynchronise toutes les séquences SERIAL avec le MAX(id) réel
-- de chaque table. A exécuter à chaque fois qu'on a inséré des
-- lignes avec des id explicites (ex: test.sql, data.sql).
-- ============================================================

DO $$
DECLARE
    r RECORD;
    seq_name TEXT;
    max_id BIGINT;
BEGIN
    FOR r IN
        SELECT c.relname AS table_name
        FROM pg_class c
        JOIN pg_namespace n ON n.oid = c.relnamespace
        WHERE c.relkind = 'r' AND n.nspname = 'public'
    LOOP
        -- Cherche la séquence liée à la colonne "id" de chaque table (si elle existe)
        seq_name := pg_get_serial_sequence('public.' || quote_ident(r.table_name), 'id');

        IF seq_name IS NOT NULL THEN
            EXECUTE format('SELECT COALESCE(MAX(id), 0) FROM %I', r.table_name) INTO max_id;
            PERFORM setval(seq_name, GREATEST(max_id, 1), max_id > 0);
        END IF;
    END LOOP;
END $$;