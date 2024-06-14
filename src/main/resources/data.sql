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
    'Cultural Tour of Japan',
    'Experience the rich cultural heritage of Japan, from the bustling streets of Tokyo to the tranquil temples of Kyoto.',
    'Tokyo/Japan, Kyoto/Japan',
    2044.50,
    '2025-05-07',
    '2025-05-14',
    100,
    '00000000-0000-0000-0000-000000000004'
),
(
    '00000000-0000-0000-0002-000000000002',
    'Mediterranean Cruise Adventure',
    'Explore the stunning coastlines of the Mediterranean Sea with stops in Italy, Greece, and Spain.',
    'Mediterranean Sea',
    3499.99,
    '2025-06-15',
    '2025-06-25',
    45,
    '00000000-0000-0000-0000-000000000004'
),
(
    '00000000-0000-0000-0003-000000000003',
    'Safari in the Serengeti',
    'Experience the thrill of a safari in the Serengeti, witnessing the great migration and diverse wildlife.',
    'Serengeti/Tanzania',
    4599.00,
    '2025-07-01',
    '2025-07-10',
    50,
    '00000000-0000-0000-0000-000000000004'
),
(
    '00000000-0000-0000-0004-000000000004',
    'Northern Lights Expedition',
    'Join us for a once-in-a-lifetime experience to see the Northern Lights in the Arctic Circle.',
    'Arctic Circle/Norway',
    2999.50,
    '2025-12-01',
    '2025-12-07',
    30,
    '00000000-0000-0000-0000-000000000005'
),
(
    '00000000-0000-0000-0005-000000000005',
    'Cultural Tour of Egypt',
    'Discover the ancient wonders of Egypt, including the Pyramids, the Sphinx, and the Nile River.',
    'Cairo/Egypt',
    2750.75,
    '2025-10-10',
    '2025-10-20',
    80,
    '00000000-0000-0000-0000-000000000005'
),
(
    '00000000-0000-0000-0006-000000000006',
    'Alaskan Wilderness Adventure',
    'Embark on an adventure through the rugged Alaskan wilderness, including glacier hikes and wildlife viewing.',
    'Alaska/USA',
    3250.00,
    '2025-08-05',
    '2025-08-15',
    60,
    '00000000-0000-0000-0000-000000000005'
),
(
    '00000000-0000-0000-0007-000000000007',
    'Australian Outback Experience',
    'Explore the unique landscapes of the Australian Outback, including Uluru, wildlife, and Aboriginal culture.',
    'Outback/Australia',
    3999.99,
    '2025-09-01',
    '2025-09-10',
    70,
    '00000000-0000-0000-0000-000000000005'
),
(
    '00000000-0000-0000-0008-000000000008',
    'European Christmas Markets',
    'Enjoy the festive atmosphere of European Christmas markets in Germany, Austria, and Switzerland.',
    'Europe',
    2399.75,
    '2025-12-15',
    '2025-12-22',
    120,
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
