DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'sneaklab') THEN
      EXECUTE 'CREATE DATABASE sneaklab';
   END IF;
END
$$;