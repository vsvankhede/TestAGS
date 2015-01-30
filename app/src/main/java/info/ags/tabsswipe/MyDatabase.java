package info.ags.tabsswipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "db_apnt.sqlite";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public Cursor getApnt() {

        SQLiteDatabase db = getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"rowid AS _id","title", "desc", "date", "time", "pics"};
        String sqlTables = "tbl_apnt";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();
        return c;

    }
    public void setApnt(String dt_title, String dt_desc, String dt_date, String dt_time, String dt_img){

        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

       // String [] sqlSelect = {title,desc,date,time,img};
        String sqlTables = "tbl_apnt";
        //String sql = "INSERT INTO tbl_apnt (title,desc,date,time,img) VALUES( "+dt_title+", "+dt_desc+", "+dt_date+","+dt_time+","+dt_img+")";

        try {
            ContentValues values = new ContentValues();

            //  values.put(KEY_ID, information.getId());
            values.put("title", dt_title);
            values.put("desc", dt_desc);
            values.put("date", dt_date);
            values.put("time", dt_time);
            values.put("pics", dt_img);

            qb.setTables(sqlTables);
            db.insert(sqlTables,null,values);
            System.out.println("Data is stored");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}