package com.example.pencatatanpenduduk.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public  static final String database_name = "db_penduduk";
    public  static final String table_name = "tb_penduduk";
    public  static final String row_id = "_id";
    public  static final String row_namaLengkap = "nama_lengkap";
    public  static final String row_alamat = "alamat";
    public  static final String row_tanggaLahir = "tangal_lahir";
    public static final String row_tanggalTercatat = "tanggal_tercatat";
    public  static final String row_jenisKelamin = "jenis_kelamin";
    public  static final String row_nomorTelepon = "nomor_telepon";
    public  static final String row_gaji = "gaji";
    public  static final String row_agama= "agama";
    public  static final String row_hobi= "hobi";
    public static final String row_foto = "foto";


    private SQLiteDatabase db;

    public DBHelper(Context context){
        super(context, database_name, null, 2);
        db = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_namaLengkap + " TEXT, " + row_alamat + " TEXT, " + row_tanggaLahir + " TEXT,  " + row_tanggalTercatat +" TEXT, "
                + row_jenisKelamin + " TEXT, " + row_nomorTelepon + " TEXT, " + row_gaji + " TEXT, " + row_agama + " TEXT, " + row_hobi + " TEXT, " + row_foto + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    //Get All SQLite Data
    public Cursor allData(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY _id DESC", null);
        return cursor;
    }

    //Get 1 Data By ID
    public Cursor oneData(Long id){
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_id + "=" + id, null);
        return cursor;
    }

    //Insert Data to Database
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db.update(table_name, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(table_name, row_id + "=" + id, null);
    }
}
