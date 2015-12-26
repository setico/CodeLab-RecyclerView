
package org.gdg_lome.codelab_recyclerview.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.gdg_lome.codelab_recyclerview.data.ProgrammeContract.MatiereEntry;

/**
 * Created by setico on 10/10/15.
 */
public class ProgrammeDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "programme.db";
    public static final int DB_VERSION = 1;
    public static final String[] COLUMNS = {
            MatiereEntry._ID,
            MatiereEntry.COLUMN_MATIERE_LOGO,
            MatiereEntry.COLUMN_MATIERE_NOM,
            MatiereEntry.COLUMN_MATIERE_DESCRIPTION
    };

    public ProgrammeDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    // matiere table
        final String SQL_CREATE_MATIERE_TABLE = "CREATE TABLE "+
                MatiereEntry.TABLE_NAME + " ("+
                MatiereEntry._ID+" INTEGER PRIMARY KEY,"+
                MatiereEntry.COLUMN_MATIERE_LOGO +" TEXT NOT NULL, "+
                MatiereEntry.COLUMN_MATIERE_NOM+" TEXT UNIQUE NOT NULL, "+
                MatiereEntry.COLUMN_MATIERE_DESCRIPTION+" TEXT NOT NULL, "+
                "UNIQUE ("+MatiereEntry.COLUMN_MATIERE_NOM+") ON CONFLICT IGNORE"+
                " );";


        db.execSQL(SQL_CREATE_MATIERE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+MatiereEntry.TABLE_NAME);
        onCreate(db);
    }

}
