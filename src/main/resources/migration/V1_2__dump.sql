INSERT INTO public.m_banks (id, bank_name, phone_number, bank_code, created, changed)
VALUES (11, 'BelBank', '375 29 84 21 782', '427', '2020-07-27 23:04:25.627000', '2020-07-27 23:04:25.627000');
INSERT INTO public.m_banks (id, bank_name, phone_number, bank_code, created, changed)
VALUES (12, 'HtpBank', '375 29 14 31 787', '192', '2020-07-27 23:05:04.648000', '2020-07-27 23:05:04.648000');
INSERT INTO public.m_banks (id, bank_name, phone_number, bank_code, created, changed)
VALUES (13, 'JavaBank', '375 29 24 51 815', '777', '2020-07-27 23:05:52.866000', '2020-07-27 23:05:52.866000');

INSERT INTO public.m_users (id, firstname, lastname, birth_date, login, password, passport_number, created, changed,
                            is_deleted)
VALUES (47, 'Mark', 'Zuckerberg', '1984-05-15', 'facebook', 'facebook', 'KH2847184', '2020-07-27 23:12:02.217000',
        '2020-07-27 23:12:02.243000', false);
INSERT INTO public.m_users (id, firstname, lastname, birth_date, login, password, passport_number, created, changed,
                            is_deleted)
VALUES (1, 'Sergei', 'Poviraev', '1996-02-23', 'admin', 'admin', 'KH2739164', '2020-07-08 00:12:38.000000',
        '2020-07-25 12:04:39.600000', false);

INSERT INTO public.m_roles (id, user_id, user_role)
VALUES (20, 1, 'ROLE_ADMIN');
INSERT INTO public.m_roles (id, user_id, user_role)
VALUES (23, 47, 'ROLE_USER');

INSERT INTO public.m_bank_account (id, iban, amount, id_user, id_bank, created, changed)
VALUES (31, 'BY20 427 3153 7519 2534 3199 6034', 999700, 47, 11, '2020-07-27 23:12:02.242000', null);
INSERT INTO public.m_bank_account (id, iban, amount, id_user, id_bank, created, changed)
VALUES (30, 'BY20 777 2046 5688 3425 2027 5360', 770, 1, 13, '2020-07-27 23:06:49.831000',
        '2020-07-27 23:06:49.839000');

INSERT INTO public.m_cards (id, card_number, id_bank_account, expiration_date, card_type, created, changed, is_blocked)
VALUES (19, '5839 3316 3377 5770', 30, '2025-07-26', 'DEBET', '2020-07-27 23:07:04.370000',
        '2020-07-27 23:07:04.371000', false);
INSERT INTO public.m_cards (id, card_number, id_bank_account, expiration_date, card_type, created, changed, is_blocked)
VALUES (20, '2596 9384 4332 1086', 31, '2025-07-26', 'DEBET', '2020-07-27 23:13:32.867000',
        '2020-07-27 23:13:32.867000', false);

INSERT INTO public.m_transactions (id, type_of_transaction, count, id_bank_account, transaction_time, card_number)
VALUES (18, 'Internet payment', 30, 30, '2020-07-27 23:08:37.758000', '5839 3316 3377 5770');
INSERT INTO public.m_transactions (id, type_of_transaction, count, id_bank_account, transaction_time, card_number)
VALUES (19, 'Transfer to 5839 3316 3377 5770. Tuition fees', 300, 31, '2020-07-27 23:16:17.273000',
        '2596 9384 4332 1086');