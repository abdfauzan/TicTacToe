create table if not exists game_mode(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    mode VARCHAR(64) NOT NULL,
    label VARCHAR(128) NOT NULL,
    created_by VARCHAR(64) NOT NULL DEFAULT '',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_by VARCHAR(64),
    updated_at TIMESTAMP
)