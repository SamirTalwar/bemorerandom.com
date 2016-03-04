CREATE TABLE dnd_races (
    name VARCHAR(255) NOT NULL PRIMARY KEY
);

INSERT INTO dnd_races VALUES
    ('human'),
    ('dragonborn'),
    ('dwarf'),
    ('eladrin'),
    ('elf'),
    ('halfling'),
    ('tiefling');

CREATE TABLE dnd_npc_first_names (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    sex VARCHAR(6) NOT NULL,
    race VARCHAR(255) NOT NULL REFERENCES dnd_races (name)
);

CREATE TABLE dnd_npc_last_names (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    race VARCHAR(255) NOT NULL REFERENCES dnd_races (name)
);
