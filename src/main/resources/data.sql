INSERT INTO account (id, name, email, password, locked) VALUES
    (
        '00000000-0000-0000-0000-000000000001',
        'Foo',
        'foo@email.com',
        'foopassword',
        false
    ),
    (
        '00000000-0000-0000-0000-000000000002',
        'Bar',
        'bar@email.com',
        'barpassword',
        false
    ),
    (
        '00000000-0000-0000-0000-000000000003',
        'Baz',
        'baz@email.com',
        'bazpassword',
        false
    ),
    (
        '00000000-0000-0000-0000-000000000004',
        'Alice',
        'alice@tabi.com',
        'alicepassword',
        false
    ),
    (
        '00000000-0000-0000-0000-000000000005',
        'Bob',
        'bob@tabi.com',
        'bobpassword',
        true
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
