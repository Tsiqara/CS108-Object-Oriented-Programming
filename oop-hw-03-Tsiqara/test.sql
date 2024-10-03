CREATE DATABASE test_3;
USE test_3;


DROP TABLE IF EXISTS metropolises;
-- remove table if it already exists and start from scratch

CREATE TABLE metropolises (
                              metropolis CHAR(64),
                              continent CHAR(64),
                              population BIGINT
);

Delete from metropolises