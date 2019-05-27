package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BD {
    public static void connectToBD() {
        /*
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/test_base"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        String user = "root";
        String password = "admin";
        String query = "select count(*) from collocations";
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                 int count = rs.getInt(1);
                 System.out.println("Total number of collocations in the table : " + count);
             }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }*/
    }

    public void disconnectFromBD() {

    }

    public void readFromBD() {

    }
    public void writeToBD() {

    }
    public static Map<Integer, String> getProjectsFromDB() {
        Map<Integer, String> projects = new HashMap<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/collocations_search"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC" +
                "&allowPublicKeyRetrieval=true" +
                "&useSSL=false";
        String user = "root";
        String password = "admin";
        String query = "select * from project";
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String projectName = rs.getString("project_name");
                projects.put(projectId, projectName);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
        return projects;
    }
    public static int addProjectToDB(String projectName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/collocations_search"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC" +
                "&allowPublicKeyRetrieval=true" +
                "&useSSL=false";
        String user = "root";
        String password = "admin";
        String query = "insert into project(project_name) values (?);";
        try {
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, projectName);
            preparedStatement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
        }
        query = "select * from project";
        int projectId = 0;
        int maxProjectId = Integer.MIN_VALUE;
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                projectId = rs.getInt("project_id");
                if (maxProjectId < projectId) maxProjectId = projectId;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
        return maxProjectId;
    }
    public static boolean projectRepeatCheckDB(String inputProjectName) {
        boolean repeat = false;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/collocations_search"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC" +
                "&allowPublicKeyRetrieval=true" +
                "&useSSL=false";
        String user = "root";
        String password = "admin";
        String query = "select * from project";
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String projectName = rs.getString("project_name");
                if (projectName.equals(inputProjectName)) repeat = true;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
        return repeat;
    }
    public static void deleteProjectFromDBById(int projectId) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/collocations_search"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC" +
                "&allowPublicKeyRetrieval=true" +
                "&useSSL=false";
        String user = "root";
        String password = "admin";
        String query = "delete from project where project_id = ?;";
        try {
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            preparedStatement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
        }
    }
}
