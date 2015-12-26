package org.gdg_lome.codelab_recyclerview.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


/**
 * Created by setico on 22/12/15.
 */
public class ProgrammeProvider extends ContentProvider {

    private static final int MATIERE= 100;
    private static final int MATIERE_ID= 101;
    private static final UriMatcher sURI_MATCHER = buildUriMatcher();

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ProgrammeContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ProgrammeContract.PATH_MATIERE,MATIERE);
        matcher.addURI(authority, ProgrammeContract.PATH_MATIERE+"/#",MATIERE_ID);
        return matcher;
    }

    private ProgrammeDbHelper dbHelper;
    @Override
    public boolean onCreate() {
        dbHelper = new ProgrammeDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOder) {

        Cursor retCursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        switch (sURI_MATCHER.match(uri)){
            case MATIERE:
                retCursor = db.query(ProgrammeContract.MatiereEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOder
                );
                break;
            case MATIERE_ID:
                retCursor = db.query(ProgrammeContract.MatiereEntry.TABLE_NAME,
                        projection,
                        ProgrammeContract.MatiereEntry._ID+" =?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        null
                );
                break;

            default:
                throw new UnsupportedOperationException("unknow uri:"+uri);

        }

        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sURI_MATCHER.match(uri);
        switch (match){
            case MATIERE:
                return ProgrammeContract.MatiereEntry.CONTENT_TYPE;
            case MATIERE_ID:
                return ProgrammeContract.MatiereEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("unknow uri:"+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;

        switch (sURI_MATCHER.match(uri)){
            case MATIERE:
                long id = db.insert(ProgrammeContract.MatiereEntry.TABLE_NAME,
                        null,
                        contentValues);
                if(id>0)
                    returnUri = ProgrammeContract.MatiereEntry.buildMatiereUri(id);
                else
                throw new SQLException("Failed to insert :"+uri);
                break;

            default:
                throw new UnsupportedOperationException("unknow uri:"+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowDeleted;

        switch (sURI_MATCHER.match(uri)){
            case MATIERE:
                rowDeleted = db.delete(ProgrammeContract.MatiereEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;

            default:
                throw new UnsupportedOperationException("unknow uri:"+uri);
        }

        if(rowDeleted!=0)
            getContext().getContentResolver().notifyChange(uri,null);

        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowUpdated;

        switch (sURI_MATCHER.match(uri)){
            case MATIERE:
                rowUpdated = db.update(ProgrammeContract.MatiereEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("unknow uri:"+uri);
        }

        if(rowUpdated!=0)
            getContext().getContentResolver().notifyChange(uri,null);

        return rowUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int returnCount =0;


        switch (sURI_MATCHER.match(uri)){
            case MATIERE:
                db.beginTransaction();
                try {
                    for (int i = 0; i < values.length; i++) {
                        long id = db.insert(ProgrammeContract.MatiereEntry.TABLE_NAME,
                                null,
                                values[i]);
                        if(id!=-1)
                            returnCount++;
                    }

                    db.setTransactionSuccessful();

                }finally {
                    db.endTransaction();
                    db.close();
                }
                getContext().getContentResolver().notifyChange(uri, null);
               return returnCount;


            default:
                return super.bulkInsert(uri, values);

        }
    }
}
