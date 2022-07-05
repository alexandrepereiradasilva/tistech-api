CREATE TABLE availability
(
    id INT IDENTITY NOT NULL,
    name VARCHAR(30) NOT NULL,
    CONSTRAINT availability_pkey PRIMARY KEY (id),
    CONSTRAINT uk_availability_name UNIQUE (name)
);

CREATE TABLE candidate
(
    id INT IDENTITY NOT NULL,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(255),
    CONSTRAINT candidate_pkey PRIMARY KEY (id),
    CONSTRAINT uk_candidate_name UNIQUE (name)
);

CREATE TABLE room
(
    id INT IDENTITY NOT NULL,
    name VARCHAR(30) NOT NULL,
    CONSTRAINT room_pkey PRIMARY KEY (id),
    CONSTRAINT uk_room_name UNIQUE (name)
);

CREATE TABLE exam
(
    id INT IDENTITY NOT NULL,
    name VARCHAR(30) NOT NULL,
    availability_id INT,
    candidate_id INT,
    CONSTRAINT exam_pkey PRIMARY KEY (id),
    CONSTRAINT uk_exam_name UNIQUE (name),
    CONSTRAINT availability_id FOREIGN KEY (availability_id)
        REFERENCES availability (id),
    CONSTRAINT candidate_id FOREIGN KEY (candidate_id)
        REFERENCES candidate (id)
);

