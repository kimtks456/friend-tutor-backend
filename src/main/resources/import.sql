--insert into `user` (`email`, `password`, `user_id`, `user_name`) values ('admin@admin.com', '$2a$10$dyiw3YfbmBkHlUdbgWhsYesqniOsXo71KBzF75le5.YtS/rsrsM22', 'admin', '관리자');
--insert into `user` (`email`, `password`, `user_id`, `user_name`) values ('test@test.com', '$2a$10$PMc5QFOUViVsGmsNMQqUzOuQG2rZ2wUJjSTRGFkBo9jZR/CSnQ03K', 'test', '테스트');
----https://haservi.github.io/posts/spring/hibernate-ddl-auto/
--insert into member (username, password, name, nick_name, grade, email) values ('gdsc', 'gdsc1004', '김산', '산이', 0, 'gdsc@gmail.com');

-- Insert a user with an encoded password
-- INSERT INTO 하고 VALUES 줄바꾸면 Mysql error 발생.
INSERT INTO member (username, password, name, nick_name, grade, email, score) VALUES ('gdsc1', '{bcrypt}$2a$10$oY5Q9UgKEYFTpPfECqom4eqwN3q7YV4lmYv56RdX4dLBh8BHUX6YW', '김산', 'mt_kim', 6, 'gdsc@gmail.com', 0);
INSERT INTO member (username, password, name, nick_name, grade, email, score) VALUES ('gdsc2', '{bcrypt}$2a$10$oY5Q9UgKEYFTpPfECqom4eqwN3q7YV4lmYv56RdX4dLBh8BHUX6YW', '안하늘', 'sky', 6, 'gdsc@gmail.com', 0);
INSERT INTO member (username, password, name, nick_name, grade, email, score) VALUES ('gdsc3', '{bcrypt}$2a$10$oY5Q9UgKEYFTpPfECqom4eqwN3q7YV4lmYv56RdX4dLBh8BHUX6YW', '서호동', '호똥', 6, 'gdsc@gmail.com', 0);

INSERT INTO member_roles (member_id, roles) VALUES (1, 'USER');
INSERT INTO member_roles (member_id, roles) VALUES (2, 'USER');
INSERT INTO member_roles (member_id, roles) VALUES (3, 'USER');

INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2023-03-08T12:36:06.702025', '원의 넓이 5분정리', '원의 넓이 어렵지만 잘 해보면 쉬워요', 1, 6, "math", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "xYxTzitqJ2g", 5);
INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2022-03-09T12:36:06.702025', '약수와 배수', '약수와 배수를 잘 알아보세요', 1, 5, "math", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "OZ0jvrmSrWs", 0);
INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2023-03-31T12:36:06.702025', '합동과 대칭', 'SAS, SSA 합동 기억이 나나요?', 1, 5, "math", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "IIWugmb1Ncg", 76);

INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2022-05-30T12:36:06.702025', '명사의 복수형', 's만 붙인다고 되는게 아니야!', 2, 4, "english", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "DSXVTqW7axg", 10);
INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2022-06-30T12:36:06.702025', 'Be 동사와 일반동사', 'are is well', 2, 4, "english", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "1k-bc2rg9y8", 1);
INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2022-07-30T12:36:06.702025', '외국인 친구에게 추석을 영어로 소개하기', 'Thanks giving day !!', 2, 4, "english", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "BmMLmQyYmJI", 23);
INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2022-08-30T12:36:06.702025', '소유격 대명사가 뭐냐면!', 'my냐? mine이냐?.. mine이 소유격 아니야?', 2, 4, "english", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "TXSv3Yb5Psw", 12);
INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2022-09-30T12:36:06.702025', '조동사 - must', '무조건 해야한다, 강한추측', 2, 4, "english", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "YmQmkBW5V70", 54);
INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2022-10-30T12:36:06.702025', '3인칭 단수, 대방출!', '동사에 es를 잘 붙여봐요', 2, 4, "english", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "xW-_t1QkGQA", 4);

INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2023-03-07T12:36:06.702025', '국어의 음운 체계', '혀의 위치를 조심해라', 3, 7, "korean", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "trMdK89WamQ", 6);
INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2022-11-30T12:36:06.702025', '맞춤법', '표준 발음법, 겹받침 아시나요?', 3, 8, "korean", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "vQNmhLhzIDw", 112);
INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2022-12-31T12:36:06.702025', '9품사 국어 문법', '어렵다 어려워...', 3, 7, "korean", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "osvZvF5k2Ic", 62);
INSERT INTO course_info (created_date, title, description, writer_id, grade, subject, drive_link, video_id, likes) VALUES ('2023-04-28T12:36:06.702025', '공무도하가', '남편이 물에 떠내려가서 슬퍼하는 그런 내용인가?', 3, 7, "korean", "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing", "NXx6dCHbe1A", 19);
