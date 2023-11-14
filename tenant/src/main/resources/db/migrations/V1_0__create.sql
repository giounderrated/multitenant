CREATE TABLE student (
	id serial NOT NULL,
	enrollment_id varchar(255) NULL,
	user_id int8 NOT NULL,
	CONSTRAINT student_pkey PRIMARY KEY (id)
);

CREATE TABLE account (
	id serial NOT NULL,
	firstname varchar(255) NULL,
	lastname varchar(255) NULL,
	email varchar(255) NOT NULL,
	"password" varchar(255) NULL,
	role int2 NULL,
	CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT tenant_status_check CHECK (((role >= 0) AND (role <= 2)))
);

