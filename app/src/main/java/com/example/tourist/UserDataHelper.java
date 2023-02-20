package com.example.tourist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserDataHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DB_NAME = "user.db";

    public UserDataHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS user_list (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, phone TEXT, gender TEXT, password TEXT, point INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS suggest_list (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, url TEXT, thumb TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS hotel_voucher_list (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, discount TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS food_voucher_list (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, discount TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS place_list (ID INTEGER PRIMARY KEY AUTOINCREMENT, image BLOB, name TEXT, description TEXT, category TEXT, address TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS voucher_list (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, discount TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS marker_list (ID INTEGER PRIMARY KEY AUTOINCREMENT, lat REAL, long REAL, type TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user_list");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS suggest_list");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS hotel_voucher_list");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS food_voucher_list");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS place_list");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS voucher_list");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS marker_list");
    }

    public Boolean insertUser(String username, String email, String phone, String gender, String password, int points) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("gender", gender);
        contentValues.put("password", password);
        contentValues.put("point", points);

        Log.d("user", contentValues.toString());
        long result = sqLiteDatabase.insert("user_list", null, contentValues);

        if (result == 1) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user_list where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkUserpassword(String username, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user_list WHERE username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public JSONObject getUserdetails(String usernames) throws JSONException {

        /*String usernames = null;*/
        JSONObject userDetails = new JSONObject();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user_list WHERE username = ?", new String[]{usernames});
        if (cursor.getCount() != 0)
            while (cursor.moveToNext()) {
                Log.d("userdetails", cursor.getInt(6) + " ");
                int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String email = cursor.getString(2);
                String phone = cursor.getString(3);
                String gender = cursor.getString(4);
                String password = cursor.getString(5);
                int point = cursor.getInt(6);

                userDetails.put("id", id);
                userDetails.put("username", username);
                userDetails.put("email", email);
                userDetails.put("phone", phone);
                userDetails.put("gender", gender);
                userDetails.put("password", password);
                userDetails.put("point", point);
            }
        Log.d("userdetails", userDetails.toString());
        return userDetails;
    }

    public Boolean updateUserdetails(int id, String username, String email, String phone, String gender, String password) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user_list WHERE id = ?", new String[]{String.valueOf(id)});
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("gender", gender);
        contentValues.put("password", password);

        Log.d("contentvalues: ", contentValues.toString());

        if (cursor.getCount() > 0) {
            long result = sqLiteDatabase.update("user_list", contentValues, "id=?", new String[]{String.valueOf(id)});
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public ArrayList<Suggestion> getSuggest() {

        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ArrayList<Suggestion> suggestList = new ArrayList<>();

            //Cursor cursor = sqLiteDatabase.rawQuery("select * from learn_info",null);
            Cursor cursor = sqLiteDatabase.rawQuery("select * from suggest_list", null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String title = cursor.getString(1);
                    String url = cursor.getString(2);
                    String thumb = cursor.getString(3);

                    suggestList.add(new Suggestion(title, url, thumb));
                    Log.d("BIT", suggestList.toString());
                }
                return suggestList;
            } else {
                Toast.makeText(context, "No data exist in Database", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<Voucher> getHotelVoucher() {

        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ArrayList<Voucher> voucherList = new ArrayList<>();

            //Cursor cursor = sqLiteDatabase.rawQuery("select * from learn_info",null);
            Cursor cursor = sqLiteDatabase.rawQuery("select * from hotel_voucher_list", null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String discount = cursor.getString(2);

                    voucherList.add(new Voucher(id, title, discount));
                }
                return voucherList;
            } else {
                Toast.makeText(context, "No data exist in Database", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<Voucher> getFoodVoucher() {

        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ArrayList<Voucher> voucherList = new ArrayList<>();

            Cursor cursor = sqLiteDatabase.rawQuery("select * from food_voucher_list", null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String discount = cursor.getString(2);

                    voucherList.add(new Voucher(id, title, discount));
                }
                return voucherList;
            } else {
                Toast.makeText(context, "No data exist in Database", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public boolean updateHotelVoucher(int id){
        SQLiteDatabase sqLiteDatabase1 = this.getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM hotel_voucher_list WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                contentValues.put("title", cursor.getString(1));
                contentValues.put("discount", cursor.getString(2));
            }
        }

        long result = sqLiteDatabase1.insert("voucher_list", null,contentValues);
        sqLiteDatabase.delete("hotel_voucher_list","id = ?", new String[]{String.valueOf(id)});
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateFoodVoucher(int id){
        SQLiteDatabase sqLiteDatabase1 = this.getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM food_voucher_list WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                contentValues.put("title", cursor.getString(1));
                contentValues.put("discount", cursor.getString(2));
            }
        }

        long result = sqLiteDatabase1.insert("voucher_list", null,contentValues);
        sqLiteDatabase.delete("food_voucher_list","id = ?", new String[]{String.valueOf(id)});
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public List<Place> getPlace(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Place> placeList = new ArrayList<>();

        //Cursor cursor = sqLiteDatabase.rawQuery("select * from learn_info",null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM place_list WHERE category = ?", new String[]{"place"});
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
               /* byte [] imageBytes = cursor.getBlob(1);
                Bitmap img = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);*/

                Log.d("img",cursor.getString(1));
                String img = cursor.getString(1);
                String name = cursor.getString(2);
                String description = cursor.getString(3);
                String category = cursor.getString(4);
                String address = cursor.getString(5);
                String video = cursor.getString(6);

                placeList.add(new Place(img, name, description, category, address, video));
                Log.d("PLACE", name);
            }

            return placeList;
        } else {
            Toast.makeText(context, "No data exist in Database", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public List<Place> getFood(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Place> placeList = new ArrayList<>();

        //Cursor cursor = sqLiteDatabase.rawQuery("select * from learn_info",null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM place_list WHERE category = ?", new String[]{"food"});
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
               /* byte [] imageBytes = cursor.getBlob(1);
                Bitmap img = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);*/

                Log.d("img",cursor.getString(1));
                String img = cursor.getString(1);
                String name = cursor.getString(2);
                String description = cursor.getString(3);
                String category = cursor.getString(4);
                String address = cursor.getString(5);

                placeList.add(new Place(img, name, description, category, address, null));
            }

            return placeList;
        } else {
            Toast.makeText(context, "No data exist in Database", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<Voucher> getVoucher() {

        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ArrayList<Voucher> voucherList = new ArrayList<>();

            //Cursor cursor = sqLiteDatabase.rawQuery("select * from learn_info",null);
            Cursor cursor = sqLiteDatabase.rawQuery("select * from voucher_list", null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String discount = cursor.getString(2);

                    voucherList.add(new Voucher(id, title, discount));
                }
                return voucherList;
            } else {
                Toast.makeText(context, "No data exist in Database", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public int getVoucherCount(){
        int count = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM voucher_list", null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
            return count;
        }else{
            return 0;
        }
    }

    public JSONArray getMarker(String type){
        try {
            JSONArray jsonArray = new JSONArray();

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM marker_list WHERE type = ?", new String[]{type});
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("lat",cursor.getDouble(1));
                    jsonObject.put("long",cursor.getDouble(2));
                    jsonArray.put(jsonObject);
                }
                Log.d("jsarraydata",jsonArray.toString());
                return jsonArray;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
