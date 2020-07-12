create table m_users
(
    id              bigserial                                              not null
        constraint m_users_pk
            primary key,
    user_name       varchar(100) default 'DEFAULT_NAME'::character varying not null,
    user_surname    varchar(100) default 'DEFAULT_NAME'::character varying not null,
    birth_date      date                                                   not null,
    user_login      varchar(100)                                           not null,
    user_password   varchar(100)                                           not null,
    passport_number varchar(100)                                           not null,
    created         timestamp(6),
    changed         timestamp(6),
    is_deleted      boolean      default false

);

alter table m_users
    owner to admin;

create unique index m_users_id_uindex
    on m_users (id);

create unique index m_users_login_uindex
    on m_users (login);

create unique index m_users_password_uindex
    on m_users (password);

create unique index m_users_passport_number_uindex
    on m_users (passport_number);

create table m_banks
(
    id           bigserial                                              not null
        constraint m_bank_pk
            primary key,
    bank_name    varchar(100) default 'DEFAULT_NAME'::character varying not null,
    phone_number varchar(100)                                           not null,
    bank_code    varchar(100)                                           not null,
    created      timestamp(6),
    changed      timestamp(6),
    is_deleted   boolean      default false
);

alter table m_banks
    owner to admin;

create table m_bank_account
(
    id         bigserial         not null
        constraint m_bank_account_pk
            primary key,
    iban       varchar(100)      not null,
    amount     bigint  default 0 not null,
    id_user    bigint            not null
        constraint m_bank_account_m_users_id_fk
            references m_users
            on update cascade on delete cascade,
    id_bank    bigint            not null
        constraint m_bank_account_m_banks_id_fk
            references m_banks
            on update cascade on delete cascade,
    created    timestamp(6),
    changed    timestamp(6),
    is_deleted boolean default false

);

alter table m_bank_account
    owner to admin;

create unique index m_bank_account_iban_uindex
    on m_bank_account (iban);

create unique index m_bank_account_id_uindex
    on m_bank_account (id);

create unique index m_bank_id_uindex
    on m_banks (id);

create unique index m_banks_bank_code_uindex
    on m_banks (bank_code);

create table m_roles
(
    id         bigserial                                           not null
        constraint m_roles_pk
            primary key,
    user_id    bigint                                              not null
        constraint m_roles_m_users_id_fk
            references m_users
            on update cascade on delete cascade,
    user_role  varchar(100) default 'ROLE_USER'::character varying not null,
    created    timestamp(6),
    changed    timestamp(6),
    is_deleted boolean      default false
);

alter table m_roles
    owner to admin;

create unique index m_roles_id_uindex
    on m_roles (id);

create unique index m_roles_user_id_user_role_uindex
    on m_roles (user_id, user_role);

create table m_cards
(
    id                bigserial                                          not null
        constraint m_cards_pk
            primary key,
    card_number       varchar(100)                                       not null,
    id_bank_account   bigint                                             not null
        constraint m_cards_m_bank_account_id_fk
            references m_bank_account
            on update cascade on delete cascade,
    "expiration date" date                                               not null,

        card_type         varchar(100) default 'STANDART'::character varying not null,
    created           timestamp(6),
    changed           timestamp(6),
    is_deleted        boolean      default false
);

alter table m_cards
    owner to admin;

create unique index m_cards_id_uindex
    on m_cards (id);

create unique index m_cards_card_number_uindex
    on m_cards (card_number);

create table m_transactions
(
    id                  bigserial                                            not null
        constraint m_transactions_pk
            primary key,
    type_of_transaction varchar(100) default 'REMITTANCE'::character varying not null,
    count               bigint       default 0                               not null,
    id_bank_account     bigint                                               not null
        constraint m_transactions_m_bank_account_id_fk
            references m_bank_account
            on update cascade on delete cascade,
    transaction_time    timestamp(6)                                         not null,
    created             timestamp(6),
    changed             timestamp(6),
    is_deleted          boolean      default false
);

alter table m_transactions
    owner to admin;

create unique index m_transactions_id_uindex
    on m_transactions (id);

