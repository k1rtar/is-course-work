CREATE TABLE Role (
 RoleID SERIAL PRIMARY KEY,
 RoleName VARCHAR(50) NOT NULL,
 Description TEXT
 );
 CREATE TABLE UserAccount (
 UserID SERIAL PRIMARY KEY,
 Email VARCHAR(255) NOT NULL UNIQUE,
 PasswordHash VARCHAR(255) NOT NULL,
 Username VARCHAR(50) NOT NULL UNIQUE,
 Bio TEXT,
 ProfilePhoto BYTEA,
 RegistrationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 IsBlocked BOOLEAN NOT NULL DEFAULT FALSE,
 IsDeleted BOOLEAN NOT NULL DEFAULT FALSE,
 RoleID INTEGER NOT NULL REFERENCES Role(RoleID)
 );
 CREATE TABLE Rating (
 UserID INTEGER PRIMARY KEY REFERENCES UserAccount(UserID) ON DELETE CASCADE,
 Points INTEGER NOT NULL DEFAULT 0
 );
 CREATE TABLE Category (
 CategoryID SERIAL PRIMARY KEY,
 CategoryName VARCHAR(100) NOT NULL UNIQUE,
 Description TEXT
 );
 CREATE TABLE Task (
 TaskID SERIAL PRIMARY KEY,
 Title VARCHAR(255) NOT NULL,
 Description TEXT NOT NULL,
 DifficultyLevel INTEGER NOT NULL CHECK (DifficultyLevel BETWEEN 1 AND 10),
 CreationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 IsPublic BOOLEAN NOT NULL DEFAULT TRUE,
 Status VARCHAR(20) NOT NULL CHECK (Status IN ('Approved', 'Pending', 'Rejected')),
 MaxExecutionTime REAL,
 MaxMemoryUsage REAL,
 CreatorUserID INTEGER REFERENCES UserAccount(UserID) ON DELETE SET NULL
 );
 7
CREATE TABLE TaskCategory (
 TaskID INTEGER NOT NULL REFERENCES Task(TaskID) ON DELETE CASCADE,
 CategoryID INTEGER NOT NULL REFERENCES Category(CategoryID) ON DELETE CASCADE,
 PRIMARY KEY (TaskID, CategoryID)
 );
 CREATE TABLE TestCase (
 TestCaseID SERIAL PRIMARY KEY,
 InputData TEXT NOT NULL,
 ExpectedOutputData TEXT NOT NULL,
 IsSample BOOLEAN NOT NULL DEFAULT FALSE,
 TaskID INTEGER NOT NULL REFERENCES Task(TaskID) ON DELETE CASCADE
 );
 CREATE TABLE ProgrammingLanguage (
 LanguageID SERIAL PRIMARY KEY,
 LanguageName VARCHAR(50) NOT NULL,
 Version VARCHAR(20)
 );
 CREATE TABLE Solution (
 SolutionID SERIAL PRIMARY KEY,
 SubmissionDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 Code TEXT NOTNULL,
 ExecutionTime REAL,
 MemoryUsage REAL,
 IsAccepted BOOLEAN NOT NULL DEFAULT FALSE,
 Feedback TEXT,
 TaskID INTEGER NOT NULL REFERENCES Task(TaskID) ON DELETE CASCADE,
 UserID INTEGER NOT NULL REFERENCES UserAccount(UserID) ON DELETE CASCADE,
 LanguageID INTEGER NOT NULL REFERENCES ProgrammingLanguage(LanguageID)
 );
 CREATE TABLE Comment (
 CommentID SERIAL PRIMARY KEY,
 CommentText TEXT NOT NULL,
 CreationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 IsDeleted BOOLEAN NOT NULL DEFAULT FALSE,
 UserID INTEGER REFERENCES UserAccount(UserID) ON DELETE SET NULL,
 TaskID INTEGER REFERENCES Task(TaskID) ON DELETE CASCADE,
 ParentCommentID INTEGER REFERENCES Comment(CommentID) ON DELETE CASCADE
 );
 CREATE TABLE Complaint (
 ComplaintID SERIAL PRIMARY KEY,
 8
ComplaintText TEXT NOT NULL,
 CreationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 Status VARCHAR(20) NOT NULL CHECK (Status IN ('New', 'InReview', 'Resolved')),
 ResolutionDate TIMESTAMP,
 ReporterUserID INTEGER REFERENCES UserAccount(UserID) ON DELETE SET NULL,
 TargetUserID INTEGER REFERENCES UserAccount(UserID) ON DELETE SET NULL,
 TargetCommentID INTEGER REFERENCES Comment(CommentID) ON DELETE SET NULL,
 HandlerUserID INTEGER REFERENCES UserAccount(UserID) ON DELETE SET NULL
 );
 CREATE TABLE ActionType (
 ActionTypeID SERIAL PRIMARY KEY,
 ActionName VARCHAR(50),
 RequiredRoleID INTEGER REFERENCES Role(RoleID) ON DELETE CASCADE
 );
 CREATE TABLE ModerationAction (
 ActionID SERIAL PRIMARY KEY,
 ActionDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 Details TEXT,
 ActionTypeID INTEGER REFERENCES ActionType(ActionTypeID) ON DELETE SET NULL,
 ModeratorUserID INTEGER REFERENCES UserAccount(UserID) ON DELETE SET NULL,
 TargetTaskID INTEGER REFERENCES Task(TaskID) ON DELETE SET NULL,
 TargetCommentID INTEGER REFERENCES Comment(CommentID) ON DELETE SET NULL,
 TargetUserID INTEGER REFERENCES UserAccount(UserID) ON DELETE SET NULL
 );
 CREATE TABLE Contest (
 ContestID SERIAL PRIMARY KEY,
 Title VARCHAR(255) NOT NULL,
 Description TEXT,
 StartTime TIMESTAMP NOT NULL,
 EndTime TIMESTAMP NOT NULL,

Кирилл Таранов, [16.01.2025 16:51]
IsPublic BOOLEAN NOT NULL DEFAULT TRUE,
 CreatorUserID INTEGER REFERENCES UserAccount(UserID) ON DELETE SET NULL
 );
 CREATE TABLE ContestTask (
 ContestID INTEGER NOT NULL REFERENCES Contest(ContestID) ON DELETE CASCADE,
 TaskID INTEGER NOT NULL REFERENCES Task(TaskID) ON DELETE CASCADE,
 PRIMARY KEY (ContestID, TaskID)
 );
 CREATE TABLE ContestParticipant (
 ContestID INTEGER NOT NULL REFERENCES Contest(ContestID) ON DELETE CASCADE,
 UserID INTEGER NOT NULL REFERENCES UserAccount(UserID) ON DELETE CASCADE,
 RegistrationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 9
PRIMARY KEY (ContestID, UserID)
 );
 3.
 Реализовать целостность данных при помощи триггеров в
 реляционной СУБД PostgreSQL.-- Триггеры-- Автоматическое Создание Записи в Таблице Rating при Добавлении Нового
 Пользователя
 CREATE ORREPLACE FUNCTION create_rating_for_new_user()
 RETURNS TRIGGER AS $$
 BEGIN
 INSERT INTO Rating (UserID, Points)
 VALUES (NEW.UserID, 0);
 RETURN NEW;
 END;
 $$ LANGUAGEplpgsql;
 CREATE TRIGGER trg_create_rating
 AFTER INSERT ON UserAccount
 FOR EACHROW
 EXECUTE PROCEDURE create_rating_for_new_user();-- Предотвращение Циклических Ссылок в Комментариях (Comment)
 CREATE ORREPLACE FUNCTION prevent_comment_cycle()
 RETURNS TRIGGER AS $$
 DECLARE
 parent_id INTEGER;
 BEGIN
 IF NEW.ParentCommentID IS NOT NULL THEN
 parent_id := NEW.ParentCommentID;
 WHILE parent_id IS NOT NULL LOOP
 IF parent_id = NEW.CommentID THEN
 RAISE EXCEPTION 'Циклическая ссылка обнаружена в комментариях';
 ENDIF;
 SELECT ParentCommentID INTO parent_id FROM Comment WHERE CommentID =
 parent_id;
 ENDLOOP;
 ENDIF;
 RETURN NEW;
 END;
 $$ LANGUAGEplpgsql;
 10
CREATE TRIGGER trg_prevent_comment_cycle
 BEFORE INSERT OR UPDATE ON Comment
 FOR EACHROW
 EXECUTE PROCEDURE prevent_comment_cycle();-- Автоматическое Обновление Рейтинга Пользователя при Принятии Решения
 (Solution)
 CREATE ORREPLACE FUNCTION update_rating_on_solution_accept()
 RETURNS TRIGGER AS $$
 BEGIN
 IF (TG_OP = 'INSERT' AND NEW.IsAccepted) OR
 (TG_OP = 'UPDATE' AND NEW.IsAccepted AND (OLD.IsAccepted IS DISTINCT FROM
 NEW.IsAccepted)) THEN
 UPDATE Rating
 SET Points = Points + 10-- Предполагаем, что за принятие решения начисляется 10
 очков
 WHEREUserID = NEW.UserID;
 ENDIF;
 RETURN NEW;
 END;
 $$ LANGUAGEplpgsql;
 CREATE TRIGGER trg_update_rating
 AFTER INSERT OR UPDATE ON Solution
 FOR EACHROW
 EXECUTE PROCEDURE update_rating_on_solution_accept();-- Установка Даты Разрешения Жалобы (Complaint) при Изменении Статуса на
 'Resolved'
 CREATE ORREPLACE FUNCTION set_resolution_date()
 RETURNS TRIGGER AS $$
 BEGIN
 IF NEW.Status = 'Resolved' AND (OLD.Status IS DISTINCT FROM 'Resolved') THEN
 NEW.ResolutionDate := CURRENT_TIMESTAMP;
 ENDIF;
 RETURN NEW;
 END;
 $$ LANGUAGEplpgsql;
 CREATE TRIGGER trg_set_resolution_date
 BEFORE UPDATE ONComplaint
 FOR EACHROW
 11
EXECUTE PROCEDURE set_resolution_date();-- Проверка, что Пользователь Не Заблокирован при Создании Задачи (Task)
 CREATE ORREPLACE FUNCTION check_creator_not_blocked()
 RETURNS TRIGGER AS $$
 DECLARE
 is_blocked BOOLEAN;
 BEGIN
 SELECT IsBlocked INTO is_blocked FROM UserAccount WHERE UserID =
 NEW.CreatorUserID;
 IF is_blocked THEN
 RAISE EXCEPTION 'Заблокированные пользователи не могут создавать задачи';
 ENDIF;
 RETURN NEW;
 END;
 $$ LANGUAGEplpgsql;
 CREATE TRIGGER trg_check_creator_not_blocked
 BEFORE INSERT ON Task
 FOR EACHROW
 EXECUTE PROCEDURE check_creator_not_blocked();-- Проверка Корректности Времени Начала и Окончания Контеста (Contest)
 CREATE ORREPLACE FUNCTION check_contest_time()
 RETURNS TRIGGER AS $$
 BEGIN
 IF NEW.StartTime >= NEW.EndTime THEN
 RAISE EXCEPTION 'Время начала контеста должно быть раньше времени
 окончания';
 ENDIF;
 RETURN NEW;
 END;
 $$ LANGUAGEplpgsql;
 CREATE TRIGGER trg_check_contest_time
 BEFORE INSERT OR UPDATE ON Contest
 FOR EACHROW
 EXECUTE PROCEDURE check_contest_time();-
12
4.
 Написать скрипт для заполнения базы данных тестовыми
 данными.-- Вставка данных в таблицу Role
 INSERT INTO Role (RoleName, Description) VALUES
 ('User', 'Обычный пользователь'),
 ('Moderator', 'Модератор платформы'),

Кирилл Таранов, [16.01.2025 16:51]
('Administrator', 'Администратор платформы');-- Вставка данных в таблицу ActionType
 INSERT INTO ActionType (ActionName, RequiredRoleID) VALUES
 ('EditTask', 2),-- Требуется роль Moderator
 ('DeleteComment', 2),-- Требуется роль Moderator
 ('BlockUser', 3),-- Требуется роль Administrator
 ('CreateContest', 3),-- Требуется роль Administrator
 ('ResolveComplaint', 2);-- Требуется роль Moderator-- Вставка данных в таблицу UserAccount
 INSERT INTO UserAccount (Email, PasswordHash, Username, Bio, RoleID) VALUES
 ('user1@example.com', 'hashed_password1', 'user1', 'Bio of user1', 1),
 ('user2@example.com', 'hashed_password2', 'user2', 'Bio of user2', 1),
 ('mod1@example.com', 'hashed_password3', 'mod1', 'Bio of moderator1', 2),
 ('admin1@example.com', 'hashed_password4', 'admin1', 'Bio of admin1', 3);-- Таблица Rating будет заполнена автоматически через триггер-- Вставка данных в таблицу Category
 INSERT INTO Category (CategoryName, Description) VALUES
 ('Strings', 'Задачи, связанные со строками'),
 ('Arrays', 'Задачи, связанные с массивами'),
 ('Graphs', 'Задачи, связанные с графами'),
 ('Dynamic Programming', 'Задачи, связанные с динамическим программированием'),
 ('Recursion', 'Задачи, связанные с рекурсией');-- Вставка данных в таблицу ProgrammingLanguage
 INSERT INTO ProgrammingLanguage (LanguageName, Version) VALUES
 ('Python', '3.9'),
 ('C++', '17'),
 ('Java', '11'),
 ('JavaScript', 'ES6');-- Вставка данных в таблицу Task
 INSERT INTO Task (Title, Description, DifficultyLevel, Status, MaxExecutionTime,
 MaxMemoryUsage, CreatorUserID) VALUES
 13
('Reverse a String', 'Напишите функцию, которая разворачивает строку.', 1, 'Approved',
 1.0, 256.0, 1),
 ('Find Maximum Subarray', 'Найдите подмассив с максимальной суммой.', 5, 'Approved',
 2.0, 512.0, 1),
 ('Dijkstra\'s Algorithm', 'Реализуйте алгоритм Дейкстры для поиска кратчайшего пути.', 7,
 'Approved', 3.0, 1024.0, 3),
 ('Recursive Factorial', 'Напишите рекурсивную функцию для вычисления факториала
 числа.', 2, 'Approved', 1.0, 256.0, 1),
 ('Graph Connectivity', 'Проверьте связность графа.', 6, 'Approved', 2.5, 768.0, 3);-- Вставка данных в таблицу TaskCategory
 INSERT INTO TaskCategory (TaskID, CategoryID) VALUES
 (1, 1),-- Reverse a String-> Strings
 (2, 4),-- Find Maximum Subarray-> Dynamic Programming
 (3, 3),-- Dijkstra's Algorithm-> Graphs
 (4, 5),-- Recursive Factorial-> Recursion
 (5, 3);-- Graph Connectivity-> Graphs-- Вставка данных в таблицу TestCase
 INSERT INTO TestCase (InputData, ExpectedOutputData, IsSample, TaskID) VALUES
 ('"hello"', '"olleh"', TRUE, 1),
 ('"world"', '"dlrow"', TRUE, 1),
 ('[-2,1,-3,4,-1,2,1,-5,4]', '6', TRUE, 2),
 ('[1,2,3,4,5]', '15', FALSE, 2),
 ('[0, 1, 4, 6, 8]', '0,1,3,6,8', TRUE, 4),
 ('[10, 20, 30]', '3628800', FALSE, 4),
 ('{"A", "B", "C"}', '{"A", "B", "C"}', TRUE, 3),
 ('[1, 2, 3]', '{"1","2","3"}', FALSE, 3),
 ('{"A", "B"}', '{"A","B"}', TRUE, 5),
 ('{"A"}', '{"A"}', FALSE, 5);-- Вставка данных в таблицу Solution
 INSERT INTO Solution (Code, ExecutionTime, MemoryUsage, IsAccepted, TaskID, UserID,
 LanguageID) VALUES
 ('def reverse_string(s): return s[::-1]', 0.5, 128.0, TRUE, 1, 1, 1),
 ('def max_subarray(arr): ...', 1.2, 256.0, FALSE, 2, 2, 1),
 ('public class Dijkstra { ... }', 2.8, 512.0, TRUE, 3, 3, 2),
 ('def factorial(n): return 1 if n == 0 else n * factorial(n-1)', 0.3, 64.0, TRUE, 4, 1, 1),
 ('public class GraphConnectivity { ... }', 1.5, 300.0, TRUE, 5, 3, 2);-- Вставка данных в таблицу Comment
 INSERT INTO Comment (CommentText, UserID, TaskID) VALUES
 ('Отличная задача!', 2, 1),
 ('Можете предоставить больше примеров?', 1, 2),
 ('Нужна помощь с тестами.', 2, 3),
 ('Хорошая реализация.', 3, 4);
 14
-- Вставка данных в таблицу Complaint
 INSERT INTO Complaint (ComplaintText, Status, ReporterUserID, TargetUserID,
 TargetCommentID) VALUES
 ('Спам в комментариях', 'New', 2, 3, 3),
 ('Некорректная задача', 'InReview', 1, NULL, NULL);-- Вставка данных в таблицу ModerationAction
 INSERT INTO ModerationAction (ActionTypeID, ModeratorUserID, TargetCommentID,
 Details) VALUES
 (1, 3, 3, 'Удаление спама в комментарии'),

Кирилл Таранов, [16.01.2025 16:51]
(2, 3, NULL, 'Редактирование описания задачи');-- Вставка данных в таблицу Contest
 INSERT INTO Contest (Title, Description, StartTime, EndTime, CreatorUserID) VALUES
 ('Weekly Challenge', 'Еженедельные задачи для участников.', '2024-05-01 10:00:00', '2024
05-07 18:00:00', 4),
 ('Monthly Marathon', 'Месячные соревнования с наградами.', '2024-05-10 09:00:00', '2024
05-31 23:59:59', 4);-- Вставка данных в таблицу ContestTask
 INSERT INTO ContestTask (ContestID, TaskID) VALUES
 (1, 1),
 (1, 2),
 (1, 3),
 (2, 2),
 (2, 3),
 (2, 4),
 (2, 5);-- Вставка данных в таблицу ContestParticipant
 INSERT INTO ContestParticipant (ContestID, UserID) VALUES
 (1, 1),
 (1, 2),
 (1, 3),
 (2, 1),
 (2, 2),
 (2, 3),
 (2, 4);-- Пользователь с ID 4 — администратор, участвует в контестах-- Вставка данных в таблицу Comment с ParentCommentID
 INSERT INTO Comment (CommentText, UserID, TaskID, ParentCommentID) VALUES
 ('Спасибо за ответ!', 1, 1, 1),-- Ответ на первый комментарий
 ('Рад помочь!', 2, 1, 5);-- Ответ на второй комментарий-- Вставка дополнительных данных для полноты тестирования
 15
-- Добавление пользователей, заблокированных для проверки триггера
 INSERT INTO UserAccount (Email, PasswordHash, Username, Bio, RoleID, IsBlocked) VALUES
 ('blocked_user@example.com', 'hashed_password5', 'blocked_user', 'Bio of blocked user', 1,
 TRUE);-- Попытка создать задачу от заблокированного пользователя (должна вызвать
 ошибку)-- Этот запрос будет отклонён триггером trg_check_creator_not_blocked-- INSERT INTO Task (Title, Description, DifficultyLevel, Status, CreatorUserID) VALUES-- ('Blocked Task', 'Задача от заблокированного пользователя.', 3, 'Pending', 5);
 5.
 Написать функции для важных прецедентов.-- Создаёт нового пользователя, добавляет запись в таблицы UserAccount и
 автоматически инициализирует запись в таблице Rating через триггер.
 CREATE ORREPLACE FUNCTION register_user(
 p_email VARCHAR,
 p_password_hash VARCHAR,
 p_username VARCHAR,
 p_bio TEXT,
 p_role_id INTEGER DEFAULT 1-- По умолчанию роль 'User'
 )
 RETURNS INTEGER AS $$
 DECLARE
 new_user_id INTEGER;
 BEGIN
 INSERT INTO UserAccount (Email, PasswordHash, Username, Bio, RoleID)
 VALUES (p_email, p_password_hash, p_username, p_bio, p_role_id)
 RETURNING UserID INTO new_user_id;
 RETURN new_user_id;
 EXCEPTION
 WHENunique_violation THEN
 RAISE EXCEPTION 'Email или Username уже используются.';
 END;
 $$ LANGUAGEplpgsql;-- Позволяет пользователю отправить решение задачи.
 CREATE ORREPLACE PROCEDURE submit_solution(
 p_task_id INTEGER,
 p_user_id INTEGER,
 p_language_id INTEGER,
 p_code TEXT
 16
)
 LANGUAGEplpgsql
 AS $$
 BEGIN-- Проверка существования задачи
 IF NOT EXISTS (SELECT 1 FROM Task WHERE TaskID = p_task_id AND IsPublic = TRUE) THEN
 RAISE EXCEPTION 'Задача с ID % не существует или не является публичной.',
 p_task_id;
 ENDIF;-- Вставка решения
 INSERT INTO Solution (TaskID, UserID, LanguageID, Code)
 VALUES (p_task_id, p_user_id, p_language_id, p_code);
 RAISE NOTICE 'Решение успешно отправлено.';
 END;
 $$;-- Возвращает список задач с возможностью фильтрации по категориям, уровню
 сложности и статусу.
 CREATE ORREPLACE FUNCTION get_tasks(
 p_category_ids INTEGER[] DEFAULT NULL,
 p_min_difficulty INTEGER DEFAULT NULL,
 p_max_difficulty INTEGER DEFAULT NULL,
 p_status VARCHAR DEFAULT NULL,
 p_limit INTEGER DEFAULT 100,
 p_offset INTEGER DEFAULT 0
 )
 RETURNS TABLE (
 TaskID INTEGER,
 Title VARCHAR,
 Description TEXT,
 DifficultyLevel INTEGER,
 Status VARCHAR,
 CreatorUserID INTEGER,
 CreationDate TIMESTAMP
 ) AS $$
 BEGIN
 RETURN QUERY
 SELECT t.TaskID, t.Title, t.Description, t.DifficultyLevel, t.Status, t.CreatorUserID,
 t.CreationDate
 FROMTask t
 WHERE
 (p_category_ids IS NULL OR EXISTS (
 SELECT 1 FROMTaskCategory tc
 WHEREtc.TaskID = t.TaskID AND tc.CategoryID = ANY(p_category_ids)
 ))
 AND(p_min_difficulty IS NULL OR t.DifficultyLevel >= p_min_difficulty)
 AND(p_max_difficulty IS NULL OR t.DifficultyLevel <= p_max_difficulty)
 17
AND(p_status IS NULL OR t.Status = p_status)
 ORDERBYt.CreationDate DESC
 LIMIT p_limit OFFSET p_offset;
 END;

Кирилл Таранов, [16.01.2025 16:51]
$$ LANGUAGEplpgsql;-- Позволяет пользователю отправить жалобу на другого пользователя или
 комментарий.
 CREATE ORREPLACE PROCEDURE submit_complaint(
 p_complaint_text TEXT,
 p_reporter_user_id INTEGER,
 p_target_user_id INTEGER DEFAULT NULL,
 p_target_comment_id INTEGER DEFAULT NULL
 )
 LANGUAGEplpgsql
 AS $$
 BEGIN
 INSERT INTO Complaint (
 ComplaintText,
 Status,
 ReporterUserID,
 TargetUserID,
 TargetCommentID
 )
 VALUES (
 p_complaint_text,
 'New',
 p_reporter_user_id,
 p_target_user_id,
 p_target_comment_id
 );
 RAISE NOTICE 'Жалоба успешно отправлена.';
 END;
 $$;