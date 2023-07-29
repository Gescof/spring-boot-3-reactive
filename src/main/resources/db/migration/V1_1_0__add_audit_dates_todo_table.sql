ALTER TABLE todos
    ADD ts_create TIMESTAMP DEFAULT current_timestamp NOT NULL,
    ADD ts_update TIMESTAMP DEFAULT current_timestamp NOT NULL ON UPDATE current_timestamp;
