create table if not exists board_size_configuration(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    "value" INTEGER NOT NULL,
    label VARCHAR(128) NOT NULL,
    created_by VARCHAR(64) NOT NULL DEFAULT '',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_by VARCHAR(64),
    updated_at TIMESTAMP
)