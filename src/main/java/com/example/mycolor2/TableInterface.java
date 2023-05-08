package com.example.mycolor2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface TableInterface {
    Connection getConnection (String url, String username, String password);
    static void createdSchema(Connection connection, String nameSchema)throws SQLException {
        PreparedStatement createTable = connection.prepareStatement("CREATE SCHEMA " + nameSchema);
        try{ createTable.executeUpdate();} catch (SQLException e){System.out.println(e.getMessage());}
    }

    static void dropScheme(Connection connection, String nameSchema) throws  SQLException {
        PreparedStatement psDropTable = connection.prepareStatement("DROP SCHEMA IF EXIST " + nameSchema );
        try {psDropTable.executeUpdate();} catch(SQLException e) {System.out.println(e);}
    }

    static void dropTable(Connection connection, String nameTable) throws SQLException {
        PreparedStatement psDropTable = connection.prepareStatement("DROP TABLE IF EXISTS " + nameTable);
        try{psDropTable.executeUpdate();} catch(SQLException e){System.out.println(e);}
    }
    static void createTable(Connection connection, String ddlCreateTable) throws SQLException{
        PreparedStatement createTable = connection.prepareStatement(ddlCreateTable);
        try{createTable.executeUpdate();} catch(SQLException e){System.out.println(e.getMessage());}
    }

    static void updateField(Connection connection,String ddlUpdateField) throws SQLException{
        PreparedStatement updateField = connection.prepareStatement(ddlUpdateField);
        try{updateField.executeUpdate();} catch(SQLException e){System.out.println(e.getMessage());}
    }

    static void setLocalInFileLoading(Connection connection) throws SQLException {
        PreparedStatement psSetLocalInFileLoading = connection.prepareStatement("SET GLOBAL local_infile = 1");
        try { psSetLocalInFileLoading.executeUpdate();
            System.out.println("\nGlobal local-infile set successfully");}
        catch (SQLException e){ System.out.println(e);}
    }

    static String loadDataInFileTable(String nameFile, String nameTable){
        return "LOAD DATA LOCAL INFILE '" + nameFile + "' INTO TABLE " + nameTable +
                " COLUMNS TERMINATED BY '\t'" +
                " LINES TERMINATED BY '\n'" +
                " IGNORE 1 LINES";
    }

    static void populateTable(Connection connection,String ddlPopulateTable) throws SQLException{
        PreparedStatement populateTable = connection.prepareStatement(ddlPopulateTable);
        //PreparedStatement populateTable = connection.prepareStatement(ddlPopulateTable);
        try{populateTable.executeUpdate();} catch(SQLException e){System.out.println(e);}
    }

    static void insertFromSelect(Connection connection, String nameToTable, String nameFromTable)throws SQLException{
        PreparedStatement psInsertFromSelect = connection.prepareStatement("INSERT INTO " + nameToTable + " SELECT * FROM " + nameFromTable);
        try {psInsertFromSelect.executeUpdate();} catch (SQLException e) {System.out.println(e);}
    }

    static void insertFromSelect(Connection connection, String ddlInsertFromSelect) throws SQLException{
        PreparedStatement psInsertFromSelect = connection.prepareStatement(ddlInsertFromSelect);

        try{psInsertFromSelect.executeUpdate();} catch (SQLException e) {System.out.println(e);}
    }

    static void insertRecord (Connection connection, String ddlInsertRecord) throws  SQLException {
        PreparedStatement psInsertRecord = connection.prepareStatement(ddlInsertRecord);
        try{psInsertRecord.executeUpdate();} catch(SQLException e){System.out.println(e);}
    }
    static void deleteRecord(Connection connection,String ddlDeleteRecord) throws SQLException{
        PreparedStatement deleteRecord = connection.prepareStatement(ddlDeleteRecord);
        try{deleteRecord.executeUpdate();} catch(SQLException e){System.out.println(e.getMessage());}
    }

    static ResultSet getTable(Connection connection,String nameTable) throws SQLException{
        ResultSet rs = null;
        PreparedStatement getTable = connection.prepareStatement("SELECT * FROM " + nameTable);

        try { rs = getTable.executeQuery();} catch(SQLException e){System.out.println(e.getMessage());}
        return rs;
    }
}
