package com.example.mycolor2;
import javafx.scene.control.Tab;

import java.sql. DriverManager;
import java. sql. Connection;
import java.sql.ResultSet;
import java.sql. SQLException;

import java.util. Map;
import java.util. HashMap;
public class StudentsDatabase implements TableInterface, StudentsDatabaseInterface{
        String url, username, password;
        Connection connection;

        StudentsDatabase (String url, String username, String password) {
            this.url = url;
            this.username = username;
            this.password = password;
            this.connection = getConnection(url, username, password);
        }

    public Connection getConnection (String url, String username, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("\nConnection to the database server successful!");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return connection;
    }

    class Schedule {
        String ddlCreateTable, ddlPopulateTable;
        String ddlUpDateCourseInstructor, ddlUpDateInstructor;
        String filename, nameTable;

        Schedule(String filename, String nameTable) throws SQLException {
            this.filename = filename;
            this.nameTable = nameTable;
            this.ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableSchedule;
            this.ddlPopulateTable = TableInterface.loadDataInFileTable(filename, nameTable);
            TableInterface.dropTable(connection, nameTable);
            TableInterface.createTable(connection, ddlCreateTable);
            System.out.println("\nTable Schedule created successfully");

            TableInterface.setLocalInFileLoading(connection);
            TableInterface.populateTable(connection, ddlPopulateTable);
            System.out.println("\nTable Schedule populated successfully");
            ResultSet RS = TableInterface.getTable(connection, nameTable);
            System.out.println("\nQuery on Schedule executed successfully");
        }

        public void upDateCourseInstructor(String courseID, String sectionNumber, String nameInstructor) throws SQLException {
            this.ddlUpDateCourseInstructor = StudentsDatabaseInterface.ddlUpdateCourseInstructor(courseID, sectionNumber, nameInstructor);
            TableInterface.updateField(connection, ddlUpDateCourseInstructor);
        }

        public void upDateInstructor(String nameInstructor, String nameNewInstructor) throws SQLException {
            this.ddlUpDateInstructor = StudentsDatabaseInterface.ddlUpdateInstructor(nameInstructor, nameNewInstructor);
            TableInterface.updateField(connection, ddlUpDateInstructor);
        }
    }
    class Courses {
        String ddlCreateTable, ddlPopulateTable;
        String nameToTable, nameFromTable;

        Courses(String nameToTable, String nameFromTable) throws SQLException {
            this.nameToTable = nameToTable;
            this.nameFromTable = nameFromTable;
            this.ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableCourses;

            TableInterface.dropTable(connection, nameToTable);
            TableInterface.createTable(connection, ddlCreateTable);
            System.out.println("\nTable Courses created successfully");

            TableInterface.insertFromSelect(connection, ddlPopulateTable);
            System.out.println("InTable Courses populated successfully");
            ResultSet R$ = TableInterface.getTable(connection, nameToTable);
            System.out.println("\nQuery on Courses executed successfully");
        }
    }

    class Students{
        String ddlCreateTable, ddlPopulateTable, nameTable;

        Students(String nameTable) throws SQLException{
            this.nameTable = nameTable;
            this.ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableStudents;
            this.ddlPopulateTable = StudentsDatabaseInterface.ddlInsertTableStudents;
            System.out.println(ddlPopulateTable);

            TableInterface.dropTable(connection, nameTable);
            TableInterface.createTable (connection, ddlCreateTable);
            System.out.println("'nTable Students created successfully");

            TableInterface.populateTable (connection, ddlPopulateTable);
            System.out.println("\nTable Students populated Successfully");
            ResultSet RS = TableInterface.getTable(connection, nameTable);
            System.out.println("\nQuery on Students executed successfully");
        }
    }
    class Classes {
        String ddlCreateTable, ddlPopulateTable, nameTable;

        Classes(String nameTable) throws SQLException {
            this.nameTable = nameTable;
            this.ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableClasses;
            this.ddlPopulateTable = StudentsDatabaseInterface.ddlInsertTableClasses;
            System.out.println(ddlPopulateTable);

            TableInterface.dropTable(connection, nameTable);
            TableInterface.createTable(connection, ddlCreateTable);
            System.out.println("\nTable Classes created successfully");

            TableInterface.populateTable(connection, ddlPopulateTable);
            System.out.println("InTable Classes populated successfully");
            ResultSet RS = TableInterface.getTable(connection, nameTable);
            System.out.println("\nQuery on Classes executed successfully");
        }
    }

    class AggregateGrades{
        String ddlCreateTable, ddlPopulateTable, nameToTable, nameFromTable;

        AggregateGrades (String nameToTable, String nameFromTable) throws SQLException {
            this.nameToTable = nameToTable;
            this.nameFromTable = nameFromTable;
            this.ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableAggregateGrades;
            this.ddlPopulateTable = StudentsDatabaseInterface.ddlInsertTableAggregateGrades(nameToTable, nameFromTable);

            TableInterface.dropTable(connection, nameToTable);
            TableInterface.createTable(connection, ddlCreateTable);
            System.out.println("\nTable courses created successfully");

            TableInterface.insertFromSelect(connection, ddlPopulateTable);
            System.out.println("\nTable AggregateGrades populated successfully");
            ResultSet RS = TableInterface.getTable(connection, nameToTable);
            System.out.println("\nQueury on AggregateGrades executed successfully");
        }
        public Map<Character, Integer> getAggregateGrades (String nameTable) {
            Map<Character, Integer> mapAggregateGrades = new HashMap();
            try {
                ResultSet RS = TableInterface.getTable(connection, nameTable);
                while (RS.next()) {
                    mapAggregateGrades.put(RS.getString("grade").charAt(0), RS.getInt("numberStudents"));
                }
            }
            catch (SQLException e){ System.out.println(e);}
            return mapAggregateGrades;
        }
    }
}
