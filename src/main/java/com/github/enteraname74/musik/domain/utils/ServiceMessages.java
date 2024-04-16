package com.github.enteraname74.musik.domain.utils;

/**
 * Messages used by the service to add information for returned responses.
 */
public class ServiceMessages {
    /********************** MUSICS MESSAGES ******************/
    public static String WRONG_MUSIC_ID = "The given id is not bound to a music.";
    public static String MUSIC_CANNOT_BE_SAVED = "The given music cannot be saved.";
    public static String MUSIC_DELETED = "The music has been deleted.";
    public static String FILE_IS_NOT_A_MUSIC = "The given file is not a music file.";

    /*********************** PLAYLISTS MESSAGES *******************/

    public static String WRONG_PLAYLIST_ID = "The given id is not bound to a playlist.";
    public static String PLAYLIST_CANNOT_BE_SAVED = "The given playlist cannot be saved.";
    public static String PLAYLIST_DELETED = "The playlist has been deleted.";
    public static String MUSIC_ALREADY_IN_PLAYLIST = "The music is already in the playlist.";
    public static String MUSIC_NOT_FOUND_IN_PLAYLIST = "The music is not in the playlist.";
    public static String MUSIC_ADDED_IN_PLAYLIST = "The music was added in the playlist.";
    public static String MUSIC_REMOVED_FROM_PLAYLIST = "The music was removed from the playlist.";

    /********************* USERS MESSAGES ***************************************/

    public static String WRONG_USER_ID = "The given information is not bound to a user.";
    public static String USER_NAME_ALREADY_TAKEN = "The given name is already used by another user.";

    public static String USER_CANNOT_BE_SAVED = "The user cannot be saved.";
    public static String USER_CREATED = "The user has been created.";
    public static String USER_DELETED = "The user has been deleted.";
    public static String MISSING_PERMISSION = "The user is missing the permission to do the requested action.";
    public static String CANNOT_AUTHENTICATE_USER = "Cannot authenticate the user.";
    public static String USER_NOT_AUTHENTICATED = "Cannot authenticate the user.";


    /********************* GENERAL ****************************/

    public static String SERVICE_UNREACHABLE = "The service is unreachable";

    /********************* COVERS *****************************/

    public static String CANNOT_FIND_COVER = "Cannot find the cover for the given id.";
}
