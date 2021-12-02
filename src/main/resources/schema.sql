CREATE TABLE IF NOT EXISTS users
(
    id            uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    first_name    text        NOT NULL,
    last_name     text        NOT NULL,
    email         text UNIQUE NOT NULL,
    password      text        NOT NULL,
    is_specialist boolean     NOT NULL DEFAULT false,
    job_title     text,
    about         text
);

CREATE TABLE IF NOT EXISTS services
(
    id               uuid PRIMARY KEY  DEFAULT uuid_generate_v4(),
    name             text     NOT NULL,
    price            real     NOT NULL CHECK ( price >= 0 ),
    duration         smallint NOT NULL CHECK ( duration > 0 ),
    about            text     NOT NULL,
    approve_required boolean  NOT NULL DEFAULT false,
    specialist_id    uuid     NOT NULL REFERENCES users
);

CREATE TABLE IF NOT EXISTS appointments
(
    id         uuid PRIMARY KEY     DEFAULT uuid_generate_v4(),
    date       timestamptz NOT NULL,
    status     text        NOT NULL DEFAULT 'DRAFT',
    client_id  uuid        NOT NULL REFERENCES users,
    service_id uuid        NOT NULL REFERENCES services
);

CREATE TABLE IF NOT EXISTS review
(
    id            uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    text          text     NOT NULL,
    rating        smallint NOT NULL CHECK ( rating >= 1 AND rating <= 5),
    author_id     uuid     NOT NULL REFERENCES users,
    specialist_id uuid     NOT NULL REFERENCES users,
    created_at    timestamptz      DEFAULT now()
);

CREATE TABLE IF NOT EXISTS schedules
(
    id              uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    day             smallint NOT NULL CHECK ( day >= 0 AND day <= 6),
    work_start_time time     NOT NULL,
    work_end_time   time     NOT NULL,
    start_date      date     NOT NULL,
    end_date        date,
    specialist_id   uuid     NOT NULL REFERENCES users
);
