-- Manual Queries Must Use UUID_TO_BIN() and BIN_TO_UUID()
-- SELECT * FROM publisher
-- WHERE id = UUID_TO_BIN('a2cd4590-7823-4e20-b8c9-6fc028b99313');


-- Insert into publisher
INSERT INTO publisher VALUES (
                                 UUID_TO_BIN(RANDOM_UUID()), 'Acme Corp', '{"industry":"media"}', 'ACTIVE',
                                 CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1
                             );

-- Insert into user (renamed to app_user)
INSERT INTO app_user VALUES (
                                RANDOM_UUID(), 'Arjun', '{"interests":["sports","tech"]}', 'ACTIVE',
                                CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1
                            );

-- Insert into topic
INSERT INTO topic (id, publisher_id, topic_name, topic_desc, status, created_date, updated_date, version)
SELECT RANDOM_UUID(), p.id, 'Daily Updates', 'topic created for daily updates', 'ACTIVE',
       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1
FROM publisher p
    LIMIT 1;

-- Insert into subscriber
INSERT INTO subscriber (id, user_id, topic_id, last_message_delivered_date, status, created_date, updated_date, version)
SELECT RANDOM_UUID(), u.id, t.id, CURRENT_TIMESTAMP, 'ACTIVE',
       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1
FROM app_user u, topic t
    LIMIT 1;

-- Insert into message
INSERT INTO message (id, data, topic_id, status, created_date, updated_date, version)
SELECT RANDOM_UUID(), '{"title":"Hello World","body":"Welcome!"}', t.id, 'SENT',
       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1
FROM topic t
    LIMIT 1;

-- Insert into transaction
INSERT INTO transaction (id, message_id, subscriber_id, status, created_date, updated_date, version)
SELECT RANDOM_UUID(), m.id, s.id, 'DELIVERED',
       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1
FROM message m, subscriber s
    LIMIT 1;
