CREATE TABLE accounts (
	id serial NOT NULL,
	firstname varchar(255) NULL,
	lastname varchar(255) NULL,
	email varchar(255) NOT NULL,
	"password" varchar(255) NULL,
	profile_picture varchar(255) NULL,
	role int2 NULL,
	is_enabled bool null,
	is_active bool null,
	CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT tenant_status_check CHECK (((role >= 0) AND (role <= 2)))
);

CREATE TABLE students (
	id serial NOT NULL,
	enrollment_id varchar(255) NULL,
	user_id int8 NOT NULL,
	CONSTRAINT student_pkey PRIMARY KEY (id),
    CONSTRAINT fk_student
      FOREIGN KEY(user_id)
	  REFERENCES accounts(id)
	  ON DELETE CASCADE
);

create table program_types(
    id serial NOT NULL,
	name varchar(255) NULL,
	CONSTRAINT program_types_pkey PRIMARY KEY (id)
);

create table programs(
    id serial NOT NULL,
    name varchar(255) NULL,
	credits int2 NULL,
	cycles int2 NULL,
	type_id int2 not null,
	CONSTRAINT program_pkey PRIMARY KEY (id),
    CONSTRAINT fk_program
      FOREIGN KEY(type_id)
	  REFERENCES program_types(id)
	  ON DELETE CASCADE
);

create table courses(
    id serial NOT NULL,
    name varchar(255),
    description text,
    credits int2 NULL,
	program_id int2 NOT NULL,
	CONSTRAINT course_pkey PRIMARY KEY (id),
	CONSTRAINT fk_course
      FOREIGN KEY(program_id)
	  REFERENCES program_types(id)
	  ON DELETE CASCADE
);



