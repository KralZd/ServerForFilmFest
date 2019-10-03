/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filmfestivalserver;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import server.util.DBUtil;

/**
 * communication class with statements for database (mySql)
 *
 * @Zdenek
 */
public class ServerDAO {

    private static String dbURL = "jdbc:mysql://localhost:3306/emailAppDB?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
    private static String userName = "root";
    private static String password = "1111";

    /**
     * set up url, name and pass for database
     */
    public void setUpDBConnection() {

        DBUtil.setDbURL(dbURL);
        DBUtil.setUserName(userName);
        DBUtil.setPassword(password);
    }
        public ServerDAO(){
        setUpDBConnection();
        
        }
    /**
     * This method send results from database to client
     *
     * @param os - PrintStream
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void getFestProgram(PrintStream os) throws SQLException, ClassNotFoundException {

        setUpDBConnection();

        String selectStmt = "SELECT*FROM filmfest.festival";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method

            ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
            while (rs.next()) {
                os.println("newmovie");
                os.println(rs.getString("idfestival"));
                os.println(rs.getString("datum"));
                os.println(rs.getString("movie"));
                os.println(rs.getString("director"));
                os.println(rs.getString("cinema"));

            }
            os.println("end");

        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }

    }

    /**
     * Method add new record to database
     *
     * @param date - date in format yyyy-mm-dd
     * @param film - string name of the movie
     * @param director - string with name of director
     * @param cinema - string name of cinema
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void addMovie(String date, String film, String director, String cinema) throws SQLException, ClassNotFoundException {

        setUpDBConnection();
        String selectStmt = "INSERT INTO filmfest.festival (datum,movie,director,cinema) VALUES (?,?,?,?)";

        ArrayList list = new ArrayList();

        list.add(date);
        list.add(film);
        list.add(director);
        list.add(cinema);

        DBUtil.dbExecuteUpdate(selectStmt, list);

    }

    /**
     * update item in database
     *
     * @param id - id of movie we want to update
     * @param date - date in format yyyy-mm-dd
     * @param film - string name of the movie
     * @param director - string with name of director
     * @param cinema - string name of cinema
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void updateMovie(String id, String date, String film, String director, String cinema) throws SQLException, ClassNotFoundException {

        setUpDBConnection();
        
        String selectStmt = "UPDATE filmfest.festival SET datum=?, movie =? ,director = ?, cinema=? WHERE idfestival =?";

        ArrayList list = new ArrayList();

        list.add(date);
        list.add(film);
        list.add(director);
        list.add(cinema);
        list.add(id);

        DBUtil.dbExecuteUpdate(selectStmt, list);

    }

    /**
     * delete movie from database
     *
     * @param id - id of movie we want to delete
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void deleteMovie(String id) throws SQLException, ClassNotFoundException {

        String selectStmt = "DELETE FROM filmfest.festival WHERE idfestival = ?";

        ArrayList list = new ArrayList();

        list.add(id);

        DBUtil.dbExecuteUpdate(selectStmt, list);

    }
}
