CREATE TABLE SUBSIDIARY(
  SUBSIDIARY_ID BIGSERIAL PRIMARY KEY,
  INNER_CODE CHAR(3) NOT NULL UNIQUE,
  NAME VARCHAR(30) NOT NULL,
  ADDRESS VARCHAR(64),
  PHONE_NUMBER VARCHAR(24)
);