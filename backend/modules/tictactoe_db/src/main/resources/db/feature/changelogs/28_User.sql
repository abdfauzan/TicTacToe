create table if not exists "user"(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    created_by VARCHAR(64) NOT NULL DEFAULT '',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_by VARCHAR(64),
    updated_at TIMESTAMP
)