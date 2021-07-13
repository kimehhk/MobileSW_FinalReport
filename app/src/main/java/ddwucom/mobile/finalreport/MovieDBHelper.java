package ddwucom.mobile.finalreport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDBHelper extends SQLiteOpenHelper {
    final static String TAG = "MovieDBHelper";

    final static String DB_NAME = "movies.db";
    public final static String TABLE_NAME = "movie_table";

    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_DIRECTOR = "director";
    public final static String COL_ACTOR = "actor";
    public final static String COL_GENRE = "genre";
    public final static String COL_GRADE = "grade";

    public MovieDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                COL_TITLE + " TEXT, " + COL_DIRECTOR + " TEXT, " + COL_ACTOR + " TEXT, " +
                COL_GENRE + " TEXT, " + COL_GRADE + " REAL)";
        Log.d(TAG, sql);
        db.execSQL(sql);

        db.execSQL("insert into " + TABLE_NAME + " values (null, '위플래쉬', '데이미언 셔젤', '마일스 텔러', '드라마', 4.3);");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '죽은 시인의 사회', '피터 위어', '로빈 윌리엄스', '드라마', 3.9);");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '크루엘라', '크레이그 길레스피', '엠마 스톤', '드라마', 4.1);");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '부산행', '연상호', '공유', '액션', 4.1);");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '리틀 포레스트', '임순례', '김태리', '드라마', 4.7);");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '몬스터 주식회사', '피트 닥터', '존 굿맨', '애니메이션', 3.6);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}


