package ddwucom.mobile.finalreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MovieDBManager {

    MovieDBHelper movieDBHelper = null;
    Cursor cursor = null;

    public MovieDBManager(Context context) {
        movieDBHelper = new MovieDBHelper(context);
    }

    //    DB의 모든 movie를 반환
    public ArrayList<Movie> getAllMovie() {
        ArrayList movieList = new ArrayList();
        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MovieDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_TITLE));
            String director = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
            String actor = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_ACTOR));
            String genre = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_GENRE));
            double grade = cursor.getDouble(cursor.getColumnIndex(MovieDBHelper.COL_GRADE));

            movieList.add ( new Movie(id, title, director, actor, genre, grade) );
        }

        cursor.close();
        movieDBHelper.close();
        return movieList;
    }

    //    DB 에 새로운 movie 추가
    public boolean addNewMovie(Movie newMovie) {
        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(MovieDBHelper.COL_TITLE, newMovie.getTitle());
        value.put(MovieDBHelper.COL_DIRECTOR, newMovie.getDirector());
        value.put(MovieDBHelper.COL_ACTOR, newMovie.getActor());
        value.put(MovieDBHelper.COL_GENRE, newMovie.getGenre());
        value.put(MovieDBHelper.COL_GRADE, newMovie.getGrade());

        long count = db.insert(MovieDBHelper.TABLE_NAME, null, value);
        movieDBHelper.close();
        if (count > 0) return true;
        return false;
    }

    public boolean modifyMovie(Movie movie) {
        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(MovieDBHelper.COL_TITLE, movie.getTitle());
        row.put(MovieDBHelper.COL_DIRECTOR, movie.getDirector());
        row.put(MovieDBHelper.COL_ACTOR, movie.getActor());
        row.put(MovieDBHelper.COL_GENRE, movie.getGenre());
        row.put(MovieDBHelper.COL_GRADE, movie.getGrade());

        String whereClause = MovieDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(movie.get_id()) };
        int result = sqLiteDatabase.update(MovieDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        movieDBHelper.close();
        if (result > 0) return true;
        return false;
    }

    //    _id 를 기준으로 DB에서 movie 삭제
    public boolean removeMovie(long id) {
        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();
        String whereClause = MovieDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        int result = sqLiteDatabase.delete(MovieDBHelper.TABLE_NAME, whereClause,whereArgs);
        movieDBHelper.close();
        if (result > 0) return true;
        return false;
    }

    //    영화 제목으로 DB 검색
    public int getMovieByTitle(String title) {
        SQLiteDatabase db = movieDBHelper.getReadableDatabase();

        String[] columns = null;
        String selection = "title=?";
        String[] selectArgs = new String[] { title };

        Cursor cursor = db.query(MovieDBHelper.TABLE_NAME, columns, selection, selectArgs, null, null, null, null);

        long id = 0;
        while(cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
        }

        cursor.close();
        movieDBHelper.close();
        return (int) id;
    }

    //    출연 배우로 DB 검색
    public ArrayList getMovieByActor(String actor) {
        ArrayList movieList = new ArrayList();
        SQLiteDatabase db = movieDBHelper.getReadableDatabase();

        String[] columns = null;
        String selection = "actor=?";
        String[] selectArgs = new String[] { actor };

        Cursor cursor = db.query(MovieDBHelper.TABLE_NAME, columns, selection, selectArgs, null, null, null, null);

        String searchTitle = null;
        long id = 0;
        while(cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
            searchTitle = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_TITLE));

            movieList.add ((int) id);
            movieList.add (searchTitle);
        }

        cursor.close();
        movieDBHelper.close();
        return movieList;
    }


    public void close() {
        if (movieDBHelper != null) movieDBHelper.close();
        if (cursor != null) cursor.close();
    };
}
