package id.ac.telkomuniversity.dph3a4.praktikdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabasePegawai extends SQLiteOpenHelper {
    public static String DB_NAME = "dbpegawai";
    public static int DB_VERSION = 1;

    public DatabasePegawai(Context context){
        super(context, DB_NAME,
                null, DB_VERSION);
    }

    public DatabasePegawai(Context context, String name,
                           SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    public DatabasePegawai(Context context, String name,
                           SQLiteDatabase.CursorFactory factory, int version,
                           DatabaseErrorHandler errorHandler){
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE pegawai(" +
                "id INTEGER PRIMARY KEY," +
                "nip INTEGER UNIQUE," +
                "nama TEXT," +
                "tglLahir TEXT);";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1 == 2 && i == 1) {
            String sql = "ALTER TABLE pegawai ADD COLUMN jenis_kelamin INTEGER;";
            sqLiteDatabase.execSQL(sql);
        }
    }

    public void createPegawai(Pegawai pegawai){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nip", pegawai.getNip());
        cv.put("nama", pegawai.getName());
        cv.put("tglLahir", pegawai.getDate());
        db.insert("pegawai", null, cv);
        db.close();
    }

    public void deletePegawai(int nip){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("pegawai", "nip = " + String.valueOf(nip),
                null);
        db.close();
    }

    public void updatePegawai(Pegawai pegawai){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nip", pegawai.getNip());
        cv.put("nama", pegawai.getName());
        cv.put("tglLahir", pegawai.getDate());
        db.update("pegawai", cv,
                "nip = " + String.valueOf(pegawai.getNip()),
                null);
    }

    public List<Pegawai> getAllPegawai() {
        SQLiteDatabase db = getReadableDatabase();
        List<Pegawai> employees = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM pegawai", null);

        if (cursor.moveToFirst()) {
            do {
                // ubah pegawai
                Pegawai pegawai = new Pegawai();
                pegawai.setId(cursor.getInt(0));
                pegawai.setNip(cursor.getInt(1));
                pegawai.setName(cursor.getString(2));
                pegawai.setDate(cursor.getString(3));
                employees.add(pegawai);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employees;
    }
}
