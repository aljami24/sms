-- ১. সব টেবিলের ডাটা একসাথে মুছে ফেলা এবং আইডি রিসেট করা
-- CASCADE ব্যবহার করলে ফরেন-কি থাকা সত্ত্বেও ডাটা মুছে যাবে
TRUNCATE TABLE classroom_version_section, class_room, version, section
RESTART IDENTITY CASCADE;

-- ২. প্রাথমিক ডাটা ইনসার্ট (ভার্সন এবং সেকশন)
INSERT INTO version (id, name)
VALUES (1, 'Bangla'),
       (2, 'English');
INSERT INTO section (id, name)
VALUES (1, 'A'),
       (2, 'B');

-- ৩. ক্লাস ১ থেকে ৮ পর্যন্ত তৈরি করা
INSERT INTO class_room (id, name)
VALUES
        (1, 'Class 1'),
        (2, 'Class 2'),
        (3, 'Class 3'),
        (4, 'Class 4'),
        (5, 'Class 5'),
        (6, 'Class 6'),
        (7, 'Class 7'),
        (8, 'Class 8');

-- ৪. ক্লাস ১-৩: ২ ভার্সন, ২ সেকশন (নাম ধরে ম্যাপিং)
INSERT INTO classroom_version_section (class_id, version_id, section_id)
SELECT c.id, v.id, s.id
FROM class_room c, version v, section s
WHERE c.name IN ('Class 1', 'Class 2', 'Class 3');

-- ৫. ক্লাস ৪-৫: ২ ভার্সন, কিন্তু সেকশন NULL
INSERT INTO classroom_version_section (class_id, version_id, section_id)
SELECT c.id, v.id, NULL
FROM class_room c, version v
WHERE c.name IN ('Class 4', 'Class 5');

-- ৬. ক্লাস ৬-৮: শুধু বাংলা ভার্সন, সেকশন NULL
INSERT INTO classroom_version_section (class_id, version_id, section_id)
SELECT c.id, v.id, NULL
FROM class_room c, version v
WHERE c.name IN ('Class 6', 'Class 7', 'Class 8')
  AND v.name = 'Bangla';