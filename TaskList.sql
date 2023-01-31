CREATE TABLE TaskList (
taskID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
tasktext varchar(200) NOT NULL,
taskduedate varchar (10),
taskimportance varchar(35),
taskstatus varchar(35) NOT NULL,
userID int NOT NULL REFERENCES Users(userid)
);