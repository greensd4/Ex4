package database;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.sql.*;
import java.util.GregorianCalendar;

/**
 * Created by green on 6/17/2019.
 */
public class DBClient {
    private Connection conn;
    private Statement statement;
    private static final String username = "eilon";
    private static final String password = "1234";
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/RotEx4";

    //####################### Connections ################################

    public void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        this.conn = DriverManager.getConnection(DB_URL, username, password);
        System.out.println("connection created");
    }

    public void closeConnection() throws SQLException{
        this.statement.close();
        this.conn.close();
        System.out.println("connection and statement are closed");
    }

    //######################  Tables Creations ############################
    public CTImage selectQueryForImg(String table, String imageName) throws SQLException, IOException {
        // All LargeObject API calls must be within a transaction block
        conn.setAutoCommit(false);
        // Get the Large Object Manager to perform operations with
        String sql = String.format(SqlStatements.SELECT_IMAGE_FROM_ALBUM, table);
        Object[] args = {"", imageName};
        ResultSet rs = selectQuery(sql, args);
        if (rs != null) {
            CTImage imageRecord = new CTImage();
            //Gets only the first image.
            while (rs.next()) {
                // Open the large object for reading
                byte buf[] = rs.getBytes("image");
                imageRecord.setImageName(rs.getString("image_name"));
                imageRecord.setImageData(buf);
                imageRecord.setImageSize(rs.getInt("image_size"));
                imageRecord.setTitle(rs.getString("title"));
                imageRecord.setImageHeight(rs.getInt("height"));
                imageRecord.setImageWidth(rs.getInt("width"));
                imageRecord.setUserName(rs.getString("username"));
                imageRecord.setUserName(rs.getString("user_id"));
                GregorianCalendar calendar = new GregorianCalendar();
                imageRecord.setDate(rs.getString("date"));
                imageRecord.setLongitude(rs.getFloat("longitude"));
                imageRecord.setLatitude(rs.getFloat("latitude"));
                // Close the object
            }
            rs.close();
            return imageRecord;
        }
        return null;
    }
    public boolean insertImageRecord(CTImage imageRecord, String album) throws SQLException, IOException {
        // All LargeObject API calls must be within a transaction block
        conn.setAutoCommit(false);
        CTThumbnail thumbnail = createThumbnail(imageRecord);
        String sql = String.format(SqlStatements.INSERT_NEW_IMAGE_TO_ALBUM, album);
        Object[] args = {"", imageRecord.getImageName(), imageRecord.getImageSize(), imageRecord.getImageData(),imageRecord.getTitle(),
                imageRecord.getImageHeight(), imageRecord.getImageWidth(), imageRecord.getUserName(), imageRecord.getDate(), imageRecord.getLongitude(),
                imageRecord.getLatitude() , imageRecord.getAlbumName()};
        boolean res = insertQuery(sql, args);
        logger.info("Result of inserting image " +imageRecord.getImageName()+ " : " + res);
        doCommit();
        return insertThumbnail(thumbnail, album);

    }

    // ############################### Interfaces ####################################
    public ResultSet selectQuery(String sql) throws SQLException {
        return doSqlStatementReturnResultSet(sql);
    }
    public ResultSet selectQuery(String sql, Object[] args) throws SQLException {
        return doSqlStatementReturnResultSet(sql, args);
    }
    public boolean updateQuery(String update) throws SQLException{
        boolean res = doSqlStatementReturnBoolean(update);
        return !res;
    }
    public boolean updateQuery(String update, Object[] args) throws SQLException{
        boolean res = doSqlStatementReturnBoolean(update, args);
        return !res;
    }
    public boolean insertQuery(String insert) throws SQLException{
        boolean res = doSqlStatementReturnBoolean(insert);
        return !res;
    }
    public boolean insertQuery(String insert, Object[] args) throws SQLException{
        boolean res = doSqlStatementReturnBoolean(insert, args);
        return !res;
    }
    public boolean deleteQuery(String delete) throws SQLException{
        boolean res = doSqlStatementReturnBoolean(delete);
        return !res;
    }
    public boolean deleteQuery(String delete, Object[] args) throws SQLException{
        boolean res = doSqlStatementReturnBoolean(delete, args);
        return !res;
    }
    // ############################### SQL ###########################################
    private ResultSet doSqlStatementReturnResultSet(String sql, Object[] args) throws SQLException {
        statement = this.conn.prepareStatement(sql);
        for(int i=1; i < args.length; i++){
            if (args[i] == null)
                ((PreparedStatement)this.statement).setNull(i, Types.NULL);
            else if (args[i] instanceof String)
                ((PreparedStatement)this.statement).setString(i, (String) args[i]);
            else if (args[i] instanceof Integer)
                ((PreparedStatement)this.statement).setInt(i,(Integer) args[i]);
            else if (args[i] instanceof Boolean)
                ((PreparedStatement)this.statement).setBoolean(i,(Boolean) args[i]);
            else if (args[i] instanceof Long)
                ((PreparedStatement)this.statement).setLong(i,(Long) args[i]);
            else if (args[i] instanceof Float)
                ((PreparedStatement)this.statement).setFloat(i,(Float) args[i]);
            else if (args[i].getClass().equals( byte[].class) )
                ((PreparedStatement)this.statement).setBytes(i,(byte[]) args[i]);
            else if (args[i].getClass().equals(XMLGregorianCalendar.class)){
                ((PreparedStatement)this.statement).setDate(i, fromXMLDateToDate((XMLGregorianCalendar) args[i]));
            }
            else if (args[i].getClass().equals(Date.class)){
                ((PreparedStatement)this.statement).setDate(i,(Date) args[i]);
            }
            else if (args[i].getClass().equals(java.util.Date.class)){
                Date date = new Date(((java.util.Date)args[i]).getTime());
                ((PreparedStatement)this.statement).setDate(i, date);
            }
            else
                throw new SQLException("Not implemented");
        }
        logger.info("Executing Query " + sql);
        return ((PreparedStatement)this.statement).executeQuery();
    }
    private ResultSet doSqlStatementReturnResultSet(String sql) throws SQLException{
        this.statement = this.conn.createStatement();
        logger.info("Executing Query " + sql);
        return this.statement.executeQuery(sql);
    }
    private boolean doSqlStatementReturnBoolean(String sql, Object[] args) throws SQLException {
        statement = this.conn.prepareStatement(sql);
        for(int i=1; i < args.length; i++){
            if (args[i] == null)
                ((PreparedStatement)this.statement).setNull(i, Types.NULL);
            else if (args[i] instanceof String)
                ((PreparedStatement)this.statement).setString(i, (String) args[i]);
            else if (args[i] instanceof Integer)
                ((PreparedStatement)this.statement).setInt(i,(Integer) args[i]);
            else if (args[i] instanceof Boolean)
                ((PreparedStatement)this.statement).setBoolean(i,(Boolean) args[i]);
            else if (args[i] instanceof Long)
                ((PreparedStatement)this.statement).setLong(i,(Long) args[i]);
            else if (args[i] instanceof Float)
                ((PreparedStatement)this.statement).setFloat(i,(Float) args[i]);
            else if (args[i].getClass().equals( byte[].class) )
                ((PreparedStatement)this.statement).setBytes(i,(byte[]) args[i]);
            else if (args[i].getClass().equals(XMLGregorianCalendar.class)){
                ((PreparedStatement)this.statement).setDate(i, fromXMLDateToDate((XMLGregorianCalendar) args[i]));
            }
            else if (args[i].getClass().equals(Date.class)){
                ((PreparedStatement)this.statement).setDate(i,(Date) args[i]);
            }
            else if (args[i].getClass().equals(java.util.Date.class)){
                Date date = new Date(((java.util.Date)args[i]).getTime());
                ((PreparedStatement)this.statement).setDate(i, date);
            }
            else
                throw new SQLException("Not implemented");
        }
        logger.info("Executing Query " + sql);
        return ((PreparedStatement)this.statement).execute();
    }
    private boolean doSqlStatementReturnBoolean(String sql) throws SQLException {
        this.statement = this.conn.createStatement();
        logger.info("Executing Query " + sql);
        return this.statement.execute(sql);
    }
    private void doCommit() throws SQLException {
        this.conn.commit();
    }
}

