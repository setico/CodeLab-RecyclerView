
package org.gdg_lome.codelab_recyclerview.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by setico on 10/10/15.
 */
public class ProgrammeContract {


    public static final String CONTENT_AUTHORITY = "org.gdg_lome.codelab_recyclerview";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_MATIERE="matiere";

    public static final class MatiereEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MATIERE).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"+CONTENT_AUTHORITY+"/"+PATH_MATIERE;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/"+CONTENT_AUTHORITY+"/"+PATH_MATIERE;

        public static String TABLE_NAME = "matiere";
        public static String COLUMN_MATIERE_LOGO = "logo";
        public static String COLUMN_MATIERE_NOM = "nom";
        public static String COLUMN_MATIERE_DESCRIPTION = "description";

        public static Uri buildMatiereUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
