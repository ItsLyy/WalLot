package com.irlyreza.wallot;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.irlyreza.wallot.activity.MainActivity;

import androidx.annotation.Nullable;

public class Data extends SQLiteOpenHelper {

    Context context;
    Cursor cursor;
    SQLiteDatabase database;

    public static String nama_database = "data";
    public static String nama_table = "userdata";
    public Data(@Nullable Context context) {
        super(context, nama_database, null, 3);
        this.context=context;
        database = getReadableDatabase();
        database = getWritableDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + nama_table + "(id INT(50) PRIMARY KEY AUTOINCREMENT ,username VARCHAR(8),password VARCHAR(12),email VARCHAR(50), phone VARCHAR(20) )";
        db.execSQL(query);
    }

    public void check(String username, String password){
        String pw = null;
        String query = "SELECT password FROM " + nama_table + " WHERE username = ?";

        Cursor cursor = null;
        try {

            cursor = database.rawQuery(query, new String[]{username});

            if (cursor != null && cursor.moveToFirst()) {

                pw = cursor.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        if (pw == password){
            sendDataToMainActivity(context,"True");
        }
        else {
            sendDataToMainActivity(context,"False");
        }

    }

    public void insert_data(String username, String password, String email, String phone){
        database.execSQL("INSERT INTO "+nama_table+"(username,password,email,phone) VALUES ('"+username+"','"+password+", "+email+", "+phone+"')");
        Toast.makeText(context, "Your account has been created", Toast.LENGTH_SHORT).show();
    }

    public void sendDataToMainActivity(Context context, String data) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("condition", data);
        context.startActivity(intent);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
