package com.example.mycolor2;
public interface DatabaseInterface {

    String ddlCreateTableSchedule = "CREATE TABLE Schedule("+
            "courseId CHAR(12) NOT NULL UNIQUE, " +
            "sectionNumber VARCHAR(8) NOT NULL UNIQUE, " +
            "title VARCHAR(64), " +
            "department CHAR(16), " +
            "program VARCHAR(48), " +
            "year INT, " +
            "semester CHAR(6), " +
            "instructor VARCHAR(24), " +
            "PRIMARY KEY(courseId, sectionNumber));";

    String ddlCreateTableStudents = "CREATE TABLE Students (" +
            "emplId INT PRIMARY KEY, " +
            "firstName VARCHAR(32) NOT NULL, " +
            "lastName VARCHAR(32) NOT NULL, "+
            "email VARCHAR(50), "+
            "gender CHAR CHECK (gender = 'F' OR gender = 'M' OR gender = 'U'))";

    String ddlCreateTableCourses = "CREATE TABLE Courses(" +
            "courseId CHAR(12) PRIMARY KEY, " +
            "CourseTitle VARCHAR(64), " +
            "department CHAR(16)," +
            "program VARCHAR(48));";

    String ddlCreateTableClasses = "CREATE TABLE Classes(" +
            "courseID  CHAR(12) REFERENCES Schedule(courseId), " +
            "emplId INT REFERENCES Student(emplId), " +
            "sectionNo VARCHAR(8) REFERENCES Schedule(sectionNo), " +
            "year INT, " +
            "semester CHAR(6), " +
            "grade CHAR CHECK(grade = 'A' OR grade = 'B' OR grade = 'C' OR grade = 'D' OR grade = 'F' OR grade = 'W'), "+
            "PRIMARY KEY(emplId, courseId, sectionNo))";
    String ddlInsertTableClasses = "INSERT INTO Classes VALUES " +
        "('10000 PP', 23530615,'34143',2021,'Spring','C'), "+
        "('10200 CC1',23530616,'32118',2021,'Spring','D'), "+
        "('10200 CC2',23530617,'32119',2021,'Spring','B'), "+
        "('10200 CC3',23530618,'32139',2021,'Spring','C'), "+
        "('10200 MM1',23530619,'32140',2021,'Spring','A'), "+
        "('10200 MM2',23530620,'32141',2021,'Spring','C'), "+
        "('10200 MM3',23530621,'32155',2021,'Spring','B'), "+
        "('10300 CC1',23530622,'32120',2021,'Spring','D'), "+
        "('10200 CC2',23530623,'32121',2021,'Spring','W'), "+
        "('10300 MM1',23530624,'32122',2021,'Spring','B'), "+
        "('10300 MM2',23530625,'32123',2021,'Spring','C'), "+
        "('10300 EF1',23530626,'32124','2021','Spring','D'), "+
        "('10400 PR1',23530627,'32125',2021,'Spring','F'), "+
        "('10400 2L', 23530628,'32142',2021,'Spring','B'), "+
        "('11300 2N', 23530629,'32126',2021,'Spring','C'), "+
        "('22100 F',23530630, '32131', 2021, 'Spring', 'D'), "+
        "('22100 P',23530631, '32132', 2021, 'Spring', 'C'), "+
        "('22100 R',23530632, '32150',2021, 'Spring', 'A'), "+
        "('22100 F',23530633, '32131', 2021, 'Spring', 'B'), "+
        "('22100 R',23530634, '32150', 2021, 'Spring', 'D'), "+
        "('22100 R',23530635, '32150', 2021, 'Spring', 'C'), "+
        "('22100 P',23530636, '32132', 2021, 'Spring', 'F'), "+
        "('22100 R',23530637, '32150', 2021, 'Spring', 'D'), "+
        "('22100 P',23530638, '32132', 2021, 'Spring', 'F'), "+
        "('22100 P',23530639, '32132', 2021, 'Spring', 'D'), "+
        "('22100 P',23530640, '32132', 2021, 'Spring', 'C'), "+
        "('22100 P',23530641, '32132', 2021, 'Spring', 'F'), "+
        "('22100 R',23530642, '32150', 2021, 'Spring', 'B')";
    String sqlAggregateGrades = "SELECT grade, count(grade) FROM Classes GROUP by grade";

    String ddlCreateTableAggregateGrades = "CREATE TABLE AggregateGrades(grade CHAR, numberStudents INT)";

    String ddlInsertTableStudents = "INSERT INTO Students VALUES " +
            "(23530615, 'Jeremiah','Petion','jcrew@gmail.com','M'), "+
            "(23530616, 'Aarav', 'Sharma', 'aarav123@gmail.com','M'), "+
            "(23530617, 'Anaya', 'Patel', 'anaya123@gmail.com', 'F'), "+
            "(23530618, 'Arjun', 'Gupta', 'arjun123@gmail.com', 'F'), "+
            "(23530619, 'Avni', 'Verma', 'avni123@gmail.com', 'U'), "+
            "(23530620, 'Ayush', 'Singh', 'ayush123@gmail.com','U'), "+
            "(23530621, 'Devika', 'Malhotra', 'devika123@gmail.com', 'M'), "+
            "(23530622, 'Ishaan', 'Chopra', 'ishaan123@gmail.com', 'U'), "+
            "(23530623, 'Kavya', 'Rao', 'kavya123@hotmail.com','F'), "+
            "(23530624, 'Krishna', 'Srivastava', 'krishna123@gmail.com','M'), "+
            "(23530625, 'Myra', 'Joshi', 'myra123@gmail.com','F'), "+
            "(23530626, 'Neha', 'Pandey', 'neha123@gmail.com','M'), "+
            "(23530627, 'Prisha', 'Deshmukh', 'prisha123@gmail.com','F'), "+
            "(23530628, 'Rehaan', 'Iyer', 'rehaan123@gmail.com','M'), "+
            "(23530629, 'Riya', 'Mukherjee', 'riya123@gmail.com','U'), "+
            "(23530630, 'Samir', 'Choudhury', 'samir123@gmail.com','U'), "+
            "(23530631, 'Sara', 'Rajput', 'sara123@gmail.com','M'), "+
            "(23530632, 'Shreya', 'Banerjee', 'shreya123@gmail.com','M'), "+
            "(23530633, 'Fahad', 'Faruqi', 'fahadfaruqi1@gmail.com','U'), "+
            "(23530634, 'Vedant', 'Nair', 'vedant123@gmail.com','M'), "+
            "(23530635, 'Vanya', 'Gupta', 'vanya123@gmail.com','F'), "+
            "(23530636, 'Vihaan', 'Biswas', 'vihaan123@gmail.com','M'), "+
            "(23530637, 'Yashvi', 'Mehta', 'yashvi123@gmail.com','U'), "+
            "(23530638, 'Zara', 'Shah', 'zara123@gmail.com','U'), "+
            "(23530639, 'Aanya', 'Kumar', 'aanya123@gmail.com','M'), "+
            "(23530640, 'Aaradhya', 'Pandit', 'aaradhya123@gmail.com','F')";

    static String ddlUpdateCourseInstructor(String courseId, String sectionNumber, String nameInstructor) {
        return "UPDATE Schedule" +
                " SET instructor = " + nameInstructor +
                " WHERE courseId = " + courseId + " AND + " + "sectionNumber = " + sectionNumber;
    }

    static String ddlUpdateInstructor(String nameInstructor, String nameNewInstructor){
        return "UPDATE Schedule " +
                " SET instructor = " + nameInstructor +
                " WHERE instructor = " + nameNewInstructor;
    }

    static String ddlInsertTableCourses(String nameToTable, String nameFromTable) {
        return "INSERT INTO " + nameToTable +
                " SELECT courseId, title, department, program" +
                " FROM " + nameFromTable;
    }

    static String ddlInsertTableAggregateGrades(String nameToTable, String nameFromTable) {
        return "INSERT INTO " + nameToTable +
                " SELECT grade, count(grade) FROM " + nameFromTable +
                " Group By grade ORDER BY grade";
    }
}
