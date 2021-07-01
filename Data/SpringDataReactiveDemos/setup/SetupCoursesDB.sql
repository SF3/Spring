
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Deliveries;
DROP TABLE IF EXISTS RecommendedReading;
DROP TABLE IF EXISTS Instructors;
DROP TABLE IF EXISTS FeedbackOnDelivery;
DROP TABLE IF EXISTS TrainingPrograms;

CREATE TABLE Courses(CourseNum VARCHAR(10) PRIMARY KEY, CourseTitle VARCHAR(30), CourseType VARCHAR(15), MachineSpec VARCHAR(200), OperatingSystemSpec VARCHAR(200), SoftwareSpec VARCHAR(200));
CREATE TABLE Deliveries(DeliveryNum BIGINT AUTO_INCREMENT PRIMARY KEY, TrainingProgramNum BIGINT DEFAULT NULL, StartDate DATE, EndDate DATE, CourseNum VARCHAR(10));
CREATE TABLE RecommendedReading(Course_CourseNum VARCHAR(10), Description VARCHAR(100));
CREATE TABLE Instructors(Course_CourseNum VARCHAR(10), Position INT, Name VARCHAR(100));
CREATE TABLE FeedbackOnDelivery(Delivery_DeliveryNum BIGINT, DelegateName VARCHAR(100), Feedback VARCHAR(200));
CREATE TABLE TrainingPrograms(TrainingProgramNum BIGINT AUTO_INCREMENT PRIMARY KEY, Name VARCHAR(100));


INSERT INTO Courses (CourseNum, CourseTitle, CourseType) VALUES ('AB12', 'Intro to C++', 'Beginners');
INSERT INTO Courses (CourseNum, CourseTitle, CourseType) VALUES ('CD34', 'Intro to C#', 'Beginners');
INSERT INTO Courses (CourseNum, CourseTitle, CourseType, MachineSpec, OperatingSystemSpec, SoftwareSpec) VALUES ('EF56', 'Intro To Java', 'Beginners','500 MB on hard drive, 1GB RAM','Any','JDK1.5 and Eclipse or NetBeans');
INSERT INTO Courses (CourseNum, CourseTitle, CourseType) VALUES ('GH78', 'Programming in IL', 'Intermediate');
INSERT INTO Courses (CourseNum, CourseTitle, CourseType) VALUES ('IJ90', 'XPath and XSLT', 'Intermediate');
INSERT INTO Courses (CourseNum, CourseTitle, CourseType) VALUES ('KL12', 'Enterprise JavaBeans', 'Advanced');
INSERT INTO Courses (CourseNum, CourseTitle, CourseType) VALUES ('MN34', 'Designing .NET Apps', 'Advanced');

INSERT INTO TrainingPrograms (TrainingProgramNum, Name) VALUES (10,'Learning Java');
INSERT INTO TrainingPrograms (TrainingProgramNum, Name) VALUES (11,'Learning .NET');

INSERT INTO Deliveries (DeliveryNum, StartDate, EndDate, CourseNum) VALUES (1001,'2006-01-01','2006-01-04','AB12');
INSERT INTO Deliveries (DeliveryNum, StartDate, EndDate, CourseNum) VALUES (1002,'2006-01-01','2006-01-04','CD34');
INSERT INTO Deliveries (DeliveryNum, StartDate, EndDate, CourseNum) VALUES (1003,'2006-01-01','2006-01-04','AB12');
INSERT INTO Deliveries (DeliveryNum, TrainingProgramNum, StartDate, EndDate, CourseNum) VALUES (1004,10,'2006-01-01','2006-01-04','EF56');
INSERT INTO Deliveries (DeliveryNum, TrainingProgramNum, StartDate, EndDate, CourseNum) VALUES (1005,10,'2006-01-07','2006-01-11','KL12');
INSERT INTO Deliveries (DeliveryNum, StartDate, EndDate, CourseNum) VALUES (1006,'2006/02/01','2006/02/04','AB12');
INSERT INTO Deliveries (DeliveryNum, TrainingProgramNum, StartDate, EndDate, CourseNum) VALUES (1007,11,'2006-02-01','2006-02-04','CD34');
INSERT INTO Deliveries (DeliveryNum, StartDate, EndDate, CourseNum) VALUES (1008,'2006-02-01','2006-02-04','AB12');
INSERT INTO Deliveries (DeliveryNum, StartDate, EndDate, CourseNum) VALUES (1009,'2006-02-01','2006-02-04','EF56');
INSERT INTO Deliveries (DeliveryNum, TrainingProgramNum, StartDate, EndDate, CourseNum) VALUES (1010,11,'2006-02-07','2006-02-11','MN34');

INSERT INTO RecommendedReading (Course_CourseNum, Description) VALUES ('AB12','C++ Primer by Stanley Lippman');
INSERT INTO RecommendedReading (Course_CourseNum, Description) VALUES ('AB12','The C++ Standard by British Standards Institute');
INSERT INTO RecommendedReading (Course_CourseNum, Description) VALUES ('AB12','The C++ Programming Language by Bjarne Stroustrup');

INSERT INTO Instructors (Course_CourseNum, Position, Name) VALUES ('AB12',0,'Joe Bloggs');
INSERT INTO Instructors (Course_CourseNum, Position, Name) VALUES ('AB12',1,'Jane Smith');
INSERT INTO Instructors (Course_CourseNum, Position, Name) VALUES ('AB12',2,'Fred Hayes');

INSERT INTO FeedbackOnDelivery (Delivery_DeliveryNum, DelegateName, Feedback) VALUES (1001,'Homer','Great course');
INSERT INTO FeedbackOnDelivery (Delivery_DeliveryNum, DelegateName, Feedback) VALUES (1001,'Marge','Good course');
INSERT INTO FeedbackOnDelivery (Delivery_DeliveryNum, DelegateName, Feedback) VALUES (1001,'Bart','OK course');
INSERT INTO FeedbackOnDelivery (Delivery_DeliveryNum, DelegateName, Feedback) VALUES (1001,'Liza','Poor course');