package common;

/**
 * Created by green on 6/17/2019.
 */
public enum Commands {

    GET_PHOTO_FROM_ALBUM,
    CREATE_NEW_ALBUM,
    ADD_PHOTO_TO_ALBUM;

    public String value() {
        return name();
    }

    public static Commands fromValue(String v) {
        return valueOf(v);
    }

}
