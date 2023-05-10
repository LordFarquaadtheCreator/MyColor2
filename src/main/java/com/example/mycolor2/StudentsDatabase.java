package com.example.mycolor2;
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

    class Schedule{
        String ddlCreateTable, ddlPopulateTable;
        String ddlUpDateCourseInstructor, ddlUpDateInstructor;
        String filename, nameTable;

        Schedule (String filename, String nameTable) throws SQLException {
            this.filename = filename; this.nameTable = nameTable;
            this.ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableSchedule;
            this.ddlPopulateTable = TableInterface.loadDataInFileTable(filename, nameTable);
            TableInterface.dropTable (connection, nameTable);
            TableInterface.createTable(connection, ddlCreateTable);
            System.out.println("\nTable Schedule created successfully");

            TableInterface.setLocalInFileLoading(connection);
            TableInterface.populateTable(connection, ddlPopulateTable);
            System.out.println("\nTable Schedule populated successfully");
            ResultSet RS = TableInterface.getTable(connection, nameTable);
            System.out.println("\nQuery on Schedule executed successfully");
        }
    }
}
