INSERT INTO account (id, name, email, password) VALUES
    (
        '00000000-0000-0000-0000-000000000001',
        'Foo',
        'foo@email.com',
        'foopassword'
    ),
    (
        '00000000-0000-0000-0000-000000000002',
        'Bar',
        'bar@email.com',
        'barpassword'
    ),
    (
        '00000000-0000-0000-0000-000000000003',
        'Baz',
        'baz@email.com',
        'bazpassword'
    ),
    (
        '00000000-0000-0000-0000-000000000004',
        'Alice',
        'alice@tabi.com',
        'alicepassword'
    ),
    (
        '00000000-0000-0000-0000-000000000005',
        'Bob',
        'bob@tabi.com',
        'bobpassword'
    );

INSERT INTO client (id, status) VALUES
    ('00000000-0000-0000-0000-000000000001', 'BRONZE'),
    ('00000000-0000-0000-0000-000000000002', 'SILVER'),
    ('00000000-0000-0000-0000-000000000003', 'GOLD');

INSERT INTO employee (id) VALUES
    ('00000000-0000-0000-0000-000000000004'),
    ('00000000-0000-0000-0000-000000000005');
