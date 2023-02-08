create table if not exists role
(
    id
    bigint
    NOT
    NULL
    AUTO_INCREMENT,
    name
    varchar
(
    255
) NOT NULL UNIQUE,
    PRIMARY KEY
(
    id
)
    );
create table if not exists users
(
    id
    bigint
    NOT
    NULL
    AUTO_INCREMENT,
    user_name
    varchar
(
    255
) NOT NULL,
    password varchar
(
    255
) NOT NULL,
    email varchar
(
    255
) NOT NULL,
    is_verified bit,
    role_id bigint,
    PRIMARY KEY
(
    id
),
    FOREIGN KEY
(
    role_id
) REFERENCES role
(
    id
)
    );
create table if not exists verification_token
(
    id
    bigint
    NOT
    NULL
    AUTO_INCREMENT,
    token
    varchar
(
    255
) NOT NULL UNIQUE,
    created_at TIMESTAMP,
    expires_at TIMESTAMP,
    usert_id bigint,
    PRIMARY KEY
(
    id
),
    FOREIGN KEY
(
    usert_id
) REFERENCES users
(
    id
)
    );