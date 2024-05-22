INSERT INTO account (id, name, email, password, role, locked) VALUES
    (
        '00000000-0000-0000-0000-000000000001',
        'foo',
        'foo@email.com',
        '$2a$10$46q7Jq0QOu97d1YJ8sc1K.x8hHh6MCH.xFZbXJZyWR1jN5mPPNr9a',
        'CLIENT',
        false
    ),
    (
        '00000000-0000-0000-0000-000000000002',
        'bar',
        'bar@email.com',
        '$2a$10$UrnKwxyoOP1k8d8v/ShOneZqlPw6dAPNY.rs0QArojkB1M4ftjFvK',
        'CLIENT',
        false
    ),
    (
        '00000000-0000-0000-0000-000000000003',
        'baz',
        'baz@email.com',
        '$2a$10$TXCkd.Uyf9QeozkZ8Gq1Hepl27moIArAqGLRp/9Pjt1o2RFkBYS/e',
        'CLIENT',
        false
    ),
    (
        '00000000-0000-0000-0000-000000000004',
        'alice',
        'alice@tabi.com',
        '$2a$10$Uv/nTO1kWKnm7sKgU9ATuOY9bUEanzfVNDOABvL3RNEelExwR2VO.',
        'EMPLOYEE',
        false
    ),
    (
        '00000000-0000-0000-0000-000000000005',
        'bob',
        'bob@tabi.com',
        '$2a$10$V6ROft5cWO/Pwk.rlhB37uOIOO7Pp0toNQpDkdwUYH5STI5wd4AqS',
        'EMPLOYEE',
        false
    ),
    (
        '00000000-0000-0000-0000-000000000006',
        'admin',
        'admin@tabi.com',
        '$2a$10$yOMtTkDBhjuNWWtYjzquW.pB9U.TV30Z./7rXeYmsgaFo27f3xG/C',
        'ADMIN',
        false
    );

INSERT INTO client (id, status) VALUES
    ('00000000-0000-0000-0000-000000000001', 'BRONZE'),
    ('00000000-0000-0000-0000-000000000002', 'SILVER'),
    ('00000000-0000-0000-0000-000000000003', 'GOLD');

INSERT INTO employee (id) VALUES
    ('00000000-0000-0000-0000-000000000004'),
    ('00000000-0000-0000-0000-000000000005');

INSERT INTO travel (
    id,
    title,
    description,
    place,
    base_price,
    start_date,
    end_date,
    guest_limit,
    created_by
) VALUES (
    '00000000-0000-0000-0001-000000000001',
    'Temples, shrines, and gardens',
    '...',
    'Kyoto/Japan',
    2044.50,
    '2025-05-07',
    '2025-05-14',
    100,
    '00000000-0000-0000-0000-000000000004'
);

INSERT INTO reservation (
    id,
    client,
    travel,
    price,
    guest_count
) VALUES (
    '00000000-0000-0001-0000-000000000001',
    '00000000-0000-0000-0000-000000000001',
    '00000000-0000-0000-0001-000000000001',
    2089.00,
    2
);

INSERT INTO review (
    id,
    client,
    travel,
    comment,
    rating
) VALUES (
    '00000000-0001-0000-0000-000000000001',
    '00000000-0000-0000-0000-000000000001',
    '00000000-0000-0000-0001-000000000001',
    'I really enjoyed the trip.',
    10
);
