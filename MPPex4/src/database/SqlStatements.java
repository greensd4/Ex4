package database;

/**
 * Created by green on 6/17/2019.
 */
public final class SqlStatements {

    private static final String albumsTable = "albums";
    private static final String rulesTable = "rules";
    private static final String usersTable = "users";

    //GENERAL
    public static final String REMOVE_TABLE= "DROP %s;";
    public static final String UPDATE_TABLE_RECORD = "UPDATE %s SET %s WHERE %s;";
    public static final String DELETE_TABLE_RECORD = "DELETE FROM %s WHERE %s;";
    public static final String SELECT_ALL_RECORDS = "SELECT * FROM %s;";

    //CREATE
    public static final String[] INIT_BASIC_TABLES = {
            "CREATE TABLE "+ albumsTable +" (" +
                    "album_name   text  NOT NULL PRIMARY KEY, " +
                    "creator    text   NOT NULL, " +
                    "participants text, " +
                    "description text," +
                    "creation text, " +
                    "expiration text" +
                    ");",

            "CREATE TABLE "+rulesTable+" (" +
                    "album_name  text NOT NULL   PRIMARY KEY, " +
                    "location   boolean NOT NULL, " +
                    "longitude float(24), " +
                    "latitude  float(24), " +
                    "radius  integer," +
                    "time   boolean NOT NULL, " +
                    "start_date   text, " +
                    "end_date   text " +
                    ");",

            "CREATE TABLE "+ usersTable +" (" +
                    "username    text NOT NULL PRIMARY KEY," +
                    "password  text," +
                    "birthday  text," +
                    "joined  text," +
                    "profile_img  bytea," +
                    "email text," +
                    "friends text,"+
                    "info   text" +
                    ");"
    };
    public static final String NEW_ALBUM_CREATION = "CREATE TABLE %s (" +
            "image_name text, " +
            "image_size integer, " +
            "image bytea, " +
            "title  text, " +
            "height integer, " +
            "width integer," +
            "username  text," +
            "date  text," +
            "longitude  numeric," +
            "latitude  numeric, " +
            "albumName text" +
            ");";

    public static final String NEW_ALBUM_THUMBNAIL_CREATION = "CREATE TABLE %s (" +
            "thumb_name text, " +
            "height integer, " +
            "width integer, "+
            "thumbnail bytea" +
            ");";

    //INSERT
    public static final String INSERT_NEW_THUMBNAIL_TO_ALBUM = "INSERT INTO %s_thumbs VALUES (?, ?, ?, ?);";
    public static final String INSERT_NEW_ALBUM_TO_ALBUMS_TABLE = "INSERT INTO albums VALUES (?, ?, ?, ?, ?, ?);";
    public static final String INSERT_NEW_USER_TO_USERS_TABLE = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String INSERT_NEW_RULES_TO_RULES_TABLE = "INSERT INTO rules VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String INSERT_NEW_IMAGE_TO_ALBUM = "INSERT INTO %s_img VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";


    //SELECT
    public static final String SELECT_FROM_ALBUMS = "SELECT %s FROM " + albumsTable + " %s;";
    public static final String SELECT_ALL_THUMBNAILS_FROM_ALBUM = "SELECT * FROM %s_thumbs;";
    public static final String SELECT_IMAGE_FROM_ALBUM = "SELECT * FROM %s_img WHERE image_name = ?;";
    public static final String SELECT_RULES_FOR_ALBUM = "SELECT * FROM "+rulesTable+" WHERE album_name = '%s';";
    public static final String SELECT_USER_FROM_USERS = "SELECT * FROM "+usersTable+" WHERE username = ?;";
    public static final String SELECT_KEY_FROM_USERS = "SELECT %s FROM "+usersTable+" WHERE %s = '%s'";
    public static final String SELECT_ALBUM_FROM_ALBUMS = "SELECT * FROM " + albumsTable + " WHERE album_name = '%s';";
    //UPDATE
    public static final String UPDATE_TABLE = "UPDATE %s SET %s = '%s' WHERE %s;";

    public static final String UPDATE_RULES_FOR_ALBUM = "UPDATE "+rulesTable+" SET (location, longitude, latitude, radius, time," +
            " start_date, end_date) = (?, ?, ?, ?, ?, ?, ?) WHERE album = '%s';";

    public static final String UPDATE_USER_PROFILE = "UPDATE "+usersTable+" SET (username, password, birthday, joined, profile_img," +
            " email, friends, info) = (?, ?, ?, ?, ?, ?, ?, ?) WHERE username = '%s';";

    public static final String[] newAlbumCreationSQLs(String album){
        String album_thumbnail = album + "_thumbs";
        String album_reg = album + "_img";
        return new String[]{String.format(NEW_ALBUM_CREATION, album_reg),
                String.format(NEW_ALBUM_THUMBNAIL_CREATION, album_thumbnail)};
    }

}
