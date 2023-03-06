--insert into `user` (`email`, `password`, `user_id`, `user_name`) values ('admin@admin.com', '$2a$10$dyiw3YfbmBkHlUdbgWhsYesqniOsXo71KBzF75le5.YtS/rsrsM22', 'admin', '관리자');
--insert into `user` (`email`, `password`, `user_id`, `user_name`) values ('test@test.com', '$2a$10$PMc5QFOUViVsGmsNMQqUzOuQG2rZ2wUJjSTRGFkBo9jZR/CSnQ03K', 'test', '테스트');
----https://haservi.github.io/posts/spring/hibernate-ddl-auto/
--insert into member (username, password, name, nick_name, grade, email) values ('gdsc', 'gdsc1004', '김산', '산이', 0, 'gdsc@gmail.com');

-- Insert a user with an encoded password
-- INSERT INTO 하고 VALUES 줄바꾸면 Mysql error 발생.
INSERT INTO member (username, password, name, nick_name, grade, email, score) VALUES ('gdsc', '{bcrypt}$2a$10$oY5Q9UgKEYFTpPfECqom4eqwN3q7YV4lmYv56RdX4dLBh8BHUX6YW', '김산', '산이', 6, 'gdsc@gmail.com', 0);
INSERT INTO member_roles (member_id, roles) VALUES (1, 'USER');
