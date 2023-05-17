package com.example.mycolor2;

import javafx.scene.chart.PieChart;

import java.sql. DriverManager;
import java. sql. Connection;
import java.sql.ResultSet;
import java.sql. SQLException;

import java.util. Map;
import java.util. HashMap;
public class MyDatabase implements TableInterface, DatabaseInterface {
    String url, username, password;
    Connection connection;

    MyDatabase(String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = getConnection(url, username, password);
        TableInterface.dropSchema(connection, "csc221Final");
        TableInterface.createSchema(connection, "csc221Final");
        TableInterface.selectDatabase(connection, "csc221Final"); //ughahaha
        System.out.println("Database Selected");
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
            this.ddlCreateTable = DatabaseInterface.ddlCreateTableSchedule;
            this.ddlPopulateTable = TableInterface.loadDataInFileTable(filename, nameTable);
//            TableInterface.dropTable(connection, nameTable);  // deletes old instance -> no longer needed
            TableInterface.createTable(connection, ddlCreateTableSchedule);
            System.out.println("\nTable 'Schedule' created successfully");

            TableInterface.setLocalInFileLoading(connection);
            TableInterface.populateTable(connection, ddlPopulateTable);
            System.out.println("\nTable 'Schedule' populated successfully");
            ResultSet RS = TableInterface.getTable(connection, nameTable);
            System.out.println("\nQuery on 'Schedule' executed successfully");
        }

        public void upDateCourseInstructor(String courseID, String sectionNumber, String nameInstructor) throws SQLException {
            this.ddlUpDateCourseInstructor = DatabaseInterface.ddlUpdateCourseInstructor(courseID, sectionNumber, nameInstructor);
            TableInterface.updateField(connection, ddlUpDateCourseInstructor);
        }

        public void upDateInstructor(String nameInstructor, String nameNewInstructor) throws SQLException {
            this.ddlUpDateInstructor = DatabaseInterface.ddlUpdateInstructor(nameInstructor, nameNewInstructor);
            TableInterface.updateField(connection, ddlUpDateInstructor);
        }
    }
    class Courses {
        String ddlCreateTable, ddlPopulateTable;
        String nameToTable, nameFromTable;

        Courses(String nameToTable, String nameFromTable) throws SQLException {
            this.nameToTable = nameToTable;
            this.nameFromTable = nameFromTable;
            this.ddlCreateTable = DatabaseInterface.ddlCreateTableCourses;
            String populateClasses = DatabaseInterface.ddlInsertTableCourses(nameToTable, nameFromTable);

//            TableInterface.dropTable(connection, nameToTable); //deletes old instance of courses
            TableInterface.createTable(connection, ddlCreateTableCourses); //professor uses ddlCreateTable
            System.out.println("\nTable 'Courses' created successfully");

            TableInterface.insertFromSelect(connection, populateClasses);
            System.out.println("Table 'Courses' populated successfully");
            ResultSet RS = TableInterface.getTable(connection, nameToTable);
            System.out.println("\nQuery on 'Courses' executed successfully");
        }
    }

    class Students{
        String ddlCreateTable, ddlPopulateTable, nameTable;

        Students(String nameTable) throws SQLException{
            this.nameTable = nameTable;
            this.ddlCreateTable = DatabaseInterface.ddlCreateTableStudents;
            this.ddlPopulateTable = DatabaseInterface.ddlInsertTableStudents;
            //change student
//            System.out.println(ddlPopulateTable);

//            TableInterface.dropTable(connection, nameTable);
            TableInterface.createTable (connection, ddlCreateTable);
            System.out.println("Table 'Students' created successfully");

            TableInterface.populateTable (connection, ddlPopulateTable);
            System.out.println("Table 'Students' populated Successfully");
            ResultSet RS = TableInterface.getTable(connection, nameTable);
            System.out.println("Query on 'Students' executed successfully");
        }
    }
    class Classes {
        String ddlCreateTable, ddlPopulateTable, nameTable;

        Classes(String nameTable) throws SQLException {
            this.nameTable = nameTable;
            this.ddlCreateTable = DatabaseInterface.ddlCreateTableClasses;
            this.ddlPopulateTable = DatabaseInterface.ddlInsertTableClasses;
//            System.out.println(ddlPopulateTable);

//            TableInterface.dropTable(connection, nameTable);
            TableInterface.createTable(connection, ddlCreateTable);
            System.out.println("\nTable 'Classes' created successfully");

            TableInterface.populateTable(connection, ddlPopulateTable);
            System.out.println("InTable 'Classes' populated successfully");
            ResultSet RS = TableInterface.getTable(connection, nameTable);
            System.out.println("\nQuery on 'Classes' executed successfully");
        }
    }

    class AggregateGrades{
        String ddlCreateTable, ddlPopulateTable, nameToTable, nameFromTable;

        AggregateGrades (String nameToTable, String nameFromTable) throws SQLException {
            this.nameToTable = nameToTable;
            this.nameFromTable = nameFromTable;
            this.ddlCreateTable = DatabaseInterface.ddlCreateTableAggregateGrades;
            this.ddlPopulateTable = DatabaseInterface.ddlInsertTableAggregateGrades(nameToTable, nameFromTable);

//            TableInterface.dropTable(connection, nameToTable);
            TableInterface.createTable(connection, ddlCreateTable);
            System.out.println("Table 'AggregateGrades' created successfully");

            TableInterface.insertFromSelect(connection, ddlPopulateTable);
            System.out.println("Table 'AggregateGrades' populated successfully");
            ResultSet RS = TableInterface.getTable(connection, nameToTable);
            System.out.println("Queury on 'AggregateGrades' executed successfully");
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
