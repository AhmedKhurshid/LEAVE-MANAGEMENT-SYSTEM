package com.example.lms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABSE_NAME = "lms_db";
    private static final String TABLE_EMPLOYEE = "tbl_employee";
    private static final String TABLE_DEPARTMENT ="tbl_department" ;
    private static final String TABLE_MANAGER ="tbl_manager" ;
    private static final String TABLE_LEAVECATEGORY = "tbl_leavecategory" ;
    private static final String TABLE_LEAVE = "tbl_leave";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABSE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        create_table_admin(db);
        create_table_manager(db);
        create_table_employee(db);
        create_table_leavecategory(db);
        create_table_leavedetaile(db);
        create_table_leave(db);
        create_table_department(db);
    }

    private void create_table_leave(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_LEAVE+"("+
                "le_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "le_title VARCHAR (100) NOT NULL,"+
                "le_lcid INTEGER NOT NULL,"+
                "le_did INTEGER NOT NULL,"+
                "le_purpose VARCHAR (500) NOT NULL,"+
                "le_email VARCHAR (100) NOT NULL,"+
                "le_number INTEGER NOT NULL,"+
                "le_address VARCHAR (300) NOT NULL,"+
                "le_nodays INTEGER NOT NULL,"+
                "le_startle VARCHAR (100) NOT NULL,"+
                "le_endle VARCHAR (100) NOT NULL,"+
                "FOREIGN KEY (le_lcid) references "+TABLE_LEAVECATEGORY+"(lc_id),"+
                "FOREIGN KEY (le_did) references "+TABLE_DEPARTMENT+"(de_id))"
        );
    }

    private void create_table_department(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_DEPARTMENT+"("+
                "de_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "de_name VARCHAR (50) NOT NULL)");
    }

    private void create_table_leavedetaile(SQLiteDatabase db) {
    }

    private void create_table_leavecategory(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_LEAVECATEGORY+"("+
                "lc_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "lc_name VARCHAR (100) NOT NULL)");
    }

    private void create_table_employee(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_EMPLOYEE+"("+
                "e_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "e_img BLOB,"+
                "e_name VARCHAR (100) NOT NULL,"+
                "e_cnic VARCHAR (100) NOT NULL,"+
                "e_jdate VARCHAR (100) NOT NULL,"+
                "e_address VARCHAR (300) NOT NULL,"+
                "e_salary INTEGER NOT NULL,"+
                "e_leave INTEGER NOT NULL,"+
                "e_email VARCHAR (100) NOT NULL,"+
                "e_password VARCHAR (100) NOT NULL,"+
                "de_did INTEGER NOT NULL,"+
                "me_mid INTEGER NOT NULL,"+
                "FOREIGN KEY (de_did) references "+TABLE_DEPARTMENT+"(de_id),"+
                "FOREIGN KEY (me_mid) references "+TABLE_MANAGER+"(m_id))"
        );
    }

    private void create_table_manager(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_MANAGER+"("+
                "m_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "m_name VARCHAR NOT NULL," +
                "m_img BLOB,"+
                "m_cnic VARCHAR (100) NOT NULL,"+
                "m_address VARCHAR (300) NOT NULL,"+
                "m_salary INTEGER NOT NULL,"+
                "m_leave INTEGER NOT NULL,"+
                "m_email VARCHAR (100) NOT NULL,"+
                "m_password Varchar NOT NULL," +
                "dm_did INTEGER NOT NULL," +
                "FOREIGN KEY (dm_did) references "+TABLE_DEPARTMENT+"(de_id))"
        );
    }

    private void create_table_admin(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TABLE_ADMIN(" +
                "a_id INTEGER NOT NULL CONSTRAINT a_idPK PRIMARY KEY AUTOINCREMENT," +
                "a_name VARCHAR NOT NULL," +
                "a_img BLOB,"+
                "a_password Varchar NOT NULL)");
    }

    public String getDeIDByName(String category) {
        String ID=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from tbl_department where de_name = ? ",new String[]{category});
        if (c.moveToFirst()){
            ID = c.getString(0);
        }
        return ID;
    }
    public String getMeIDByName(String category) {
        String ID=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from tbl_manager where m_name = ? ",new String[]{category});
        if (c.moveToFirst()){
            ID = c.getString(0);
        }
        return ID;
    }
    public String getLeIDByName(String category2) {
        String LID=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from tbl_leavecategory where lc_name = ?",new String[]{category2});
        if (c.moveToFirst()){
            LID=c.getString(0);
        }
        return LID;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Cursor dbLog(String Username, String Password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from TABLE_ADMIN where a_name = ? and a_password = ? ",new String[]{Username,Password});
        return c;
    }
    public Cursor dbLogUser(String Username, String Password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c2 = db.rawQuery("select * from tbl_employee where e_name = ? and e_password = ? ",new String[]{Username,Password});
        return c2;
    }
    public Cursor dbLogUser2(String Username, String Password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c3 = db.rawQuery("select * from tbl_manager where m_name = ? and m_password = ? ",new String[]{Username,Password});
        return c3;
    }

    public void insert_initials(){
        SQLiteDatabase db = this.getWritableDatabase();
        String r1 = "INSERT INTO TABLE_ADMIN (a_name,a_password) values ('admin','123') ";
        String r2 = "INSERT INTO tbl_Leavecategory (lc_name) values ('Adoption leave'),('Annual leave'),('Career break'),('Carer’s leave')," +
                "('Leave for a medical appointment'),('Study leave'),('Sick leave'),('Training'),('TOIL')";
        String r3 = "INSERT INTO tbl_department (de_name) values ('Accounts'),('HR'),('Admin'),('IT'),('Complain'),('Food'),('Marketing'),('Security')";

        Cursor cursor = db.rawQuery("select * from TABLE_ADMIN",null);

        if (!cursor.moveToFirst()){
            db.execSQL(r1);
            db.execSQL(r2);
            db.execSQL(r3);
        }

    }

    public Cursor fetch_all(String table){
        SQLiteDatabase db =getReadableDatabase();
        Cursor c = db.rawQuery("select * from "+table,null);
        return c;
    }

    public boolean insert_data(String table, ContentValues cv){
        SQLiteDatabase db =getWritableDatabase();
        long res = db.insert(table,null,cv);
        if (res != -1){
            return true;
        }
        return false;
    }

}
