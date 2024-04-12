package com.github.enteraname74.musik.controller.utils;

/**
 * Messages used by controller to add information for returned responses.
 */
public class ControllerMessages {
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

    /********************* GENERAL ****************************/

    public static String SERVICE_UNREACHABLE = "The service is unreachable";

    /********************* COVERS *****************************/

    public static String CANNOT_FIND_COVER = "Cannot find the cover for the given id.";
}
