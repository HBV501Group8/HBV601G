package com.example.logintest3;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Model.Users;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Hagsparbeta4.db";
    public static final String TABNAME = "users";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    /**
     * Býr til nýtt DB meö töflu Users ásamt Forecast töflum
     * . Keyrist bara þegar nýr DB er búin til
     * @param MyDB er nýr database
     * @return Tafla Users og forecast töflur hefur verið búin til
     */

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT , password TEXT,EmailUser TEXT,Namereal TEXT)");
        MyDB.execSQL("CREATE TABLE FORECAST(ID INT PRIMARY KEY,FORECAST_NAME varchar(255),GENERATED_TIME TIMESTAMP,USER_ID  INT)");
        MyDB.execSQL("CREATE TABLE FORECAST_INPUT(ID INT PRIMARY KEY,FREQUENCY  varchar(255),NAME VARCHAR(255) ,UNIT  VARCHAR(255) ,FORECAST_ID BIGINT)");
        MyDB.execSQL("CREATE TABLE FORECAST_INPUT_SERIES( FORECAST_INPUT_ID INT PRIMARY KEY,SERIES Double,SERIES_ORDER INT Not Null )");
        MyDB.execSQL("CREATE TABLE FORECAST_INPUT_TIME( ID INT PRIMARY KEY,FORECAST_DESCRIPTION VARCHAR(1000),FORECAST_MODEL VARCHAR(255), FREQUENCY VARCHAR(255), NAME VARCHAR(255),UNIT VARCHAR(255),FORECAST_ID INT)");
        MyDB.execSQL("CREATE TABLE FORECAST_RESULT( ID INT PRIMARY KEY,FORECAST_DESCRIPTION VARCHAR(1000), FORECAST_MODEL VARCHAR(255), FREQUENCY VARCHAR(255),NAME VARCHAR(255),UNIT VARCHAR(255), FORECAST_ID INT)");
        MyDB.execSQL("CREATE TABLE FORECAST_RESULT_LOWER( IFORECAST_RESULT_ID INT PRIMARY KEY,LOWER DOUBLE,LOWER_ORDER  int not null);");
        MyDB.execSQL("CREATE TABLE FORECAST_RESULT_SERIES( IFORECAST_RESULT_ID INT PRIMARY KEY,SERIES double,SERIES_ORDER int not null)");
        MyDB.execSQL("CREATE TABLE FORECAST_RESULT_UPPER( IFORECAST_RESULT_ID INT PRIMARY KEY,UPPER double,UPPER_ORDER int not null);");


    }    /**
     * Eyðir töflu users til að geta uppfært nýka töflu
     * @param MyDB DB nafn
     * @return Tafla Users hefur verið eytt
     */
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists FORECAST");
        MyDB.execSQL("drop Table if exists FORECAST_INPUT");
        MyDB.execSQL("drop Table if exists FORECAST_INPUT_SERIES");
        MyDB.execSQL("drop Table if exists FORECAST_INPUT_TIME");
        MyDB.execSQL("drop Table if exists FORECAST_RESULT");
        MyDB.execSQL("drop Table if exists FORECAST_RESULT_LOWER");
        MyDB.execSQL("drop Table if exists FORECAST_RESULT_SERIES");
        MyDB.execSQL("drop Table if exists FORECAST_RESULT_UPPER");

    }

    /**
     * InsertData(username,paswortd,mail,realname
     * Býr til nýja færslu í Users
     * @param username sem er notandanafn
     * @param password sem er lykilorð notanda
     * @param mail sem er netfang notanda
     * @param realname sem er Nafn nootanda
     * @return true eð false eftit því hvort færslan gekk upp
     */

    public Boolean insertData(String username, String password,String mail, String realname){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("EmailUser", mail);
        contentValues.put("Namereal", realname  );
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    /**
     * Boolean updateUser(String username, String password,String mail,String name)
     * Uppfærir eigindi notanda
     * @param username sem er notandanafn
     * @param password sem er lykilorð
     * @param mail sem er Email
     * @param name sem er nafn notanda
     * @return true eð false eftit því hvort notanddi hafi verið úppfærður
     */


    public Boolean updateUser(String username, String password,String mail,String name){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Update Users set password=?,EmailUser=?,nameReal=? where username =? ", new String[] {password,mail,name,username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }


    /**
     * checkisEmpty()
     * Athuar hvort users tafla er tóm
     * @return true eð false eftit því hvort taflan er tóm eða ekki
     */

    public Boolean checkisEmpty() {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users",  new String[]{});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    /**
     * checkusername(String username)
     * Athuagar hvoty notandanafn er til
     * @param username sem er notandanafn
     * @return true eð false eftit því hvort notandi sé til
     */

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    /**
     * List<String> getUser(String username)
     * Nær í eigindi notanda
     * @param username sem er notandanafn
     * @return Sklar lista af stengjum með eigindum notanda
     */

    public List<String> getUser(String username) {
        List<String> returnList = new ArrayList<>();
        String userName="";

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select id,Namereal,emailUser,username,password from users where username = ?", new String[]{username});
        if(cursor.moveToFirst()) {
            int userID= cursor.getInt(0);
            userName = cursor.getString(3);
            String userPassword = cursor.getString(4);
           String Mail = cursor.getString(2);
           String realName = cursor.getString(1);
           String ID= userID+"";
           returnList.add(ID);
           returnList.add(realName);
           returnList.add(Mail);
           returnList.add(username);
           returnList.add(userPassword);
        }
        return returnList;

    }
    /**
     * Boolean checkusernamepassword(String username, String password)
     * Athuagar hvort notandanafn og lykilorð passa
     * @param username sem er notandanafn
     * @param password sem er lykilorð
     * @return true eð false eftit því hvort notandi sé til með lykilorð
     */

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    /**
     * Boolean deleteUser(String username)
     * Eyðir notanda
     * @param username sem er notandanafn
     * @return false eða true eftir því hvort tókst að eyða notanda
     */


    public Boolean deleteUser(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("delete from users where username = ?", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    /**
     *  List<String> getAll()
     *  Nær í allar færslur ú users töflu
     *  @return Skilar lista af strengjum með öllum notendum
     */

    public List<String> getAll() {

        List<String> returnList = new ArrayList<>();
        String queryString = "Select * from Users";
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery(queryString,null);
        if(cursor.moveToFirst()) {
            do {
                int userID= cursor.getInt(0);
                String userName = cursor.getString(1);
                String userPassword = cursor.getString(2);
                String Mail = cursor.getString(3);
                String realName = cursor.getString(4);
                returnList.add(userName);
            } while (cursor.moveToNext());
        } else {
        }
        cursor.close();
        MyDB.close();
        return returnList;

    }


    // Endurstillir DB og töflur þegar þarf

    public void resetDB() {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists FORECAST");
        MyDB.execSQL("drop Table if exists FORECAST_INPUT");
        MyDB.execSQL("drop Table if exists FORECAST_INPUT_SERIES");
        MyDB.execSQL("drop Table if exists FORECAST_INPUT_TIME");
        MyDB.execSQL("drop Table if exists FORECAST_RESULT");
        MyDB.execSQL("drop Table if exists FORECAST_RESULT_LOWER");
        MyDB.execSQL("drop Table if exists FORECAST_RESULT_SERIES");
        MyDB.execSQL("drop Table if exists FORECAST_RESULT_UPPER");
        MyDB.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT , password TEXT,EmailUser TEXT,Namereal TEXT)");
        MyDB.execSQL("CREATE TABLE FORECAST(ID INT PRIMARY KEY,FORECAST_NAME varchar(255),GENERATED_TIME TIMESTAMP,USER_ID  INT)");
        MyDB.execSQL("CREATE TABLE FORECAST_INPUT(ID INT PRIMARY KEY,FREQUENCY  varchar(255),NAME VARCHAR(255) ,UNIT  VARCHAR(255) ,FORECAST_ID BIGINT)");
        MyDB.execSQL("CREATE TABLE FORECAST_INPUT_SERIES( FORECAST_INPUT_ID INT PRIMARY KEY,SERIES Double,SERIES_ORDER INT Not Null )");
        MyDB.execSQL("CREATE TABLE FORECAST_INPUT_TIME( ID INT PRIMARY KEY,FORECAST_DESCRIPTION VARCHAR(1000),FORECAST_MODEL VARCHAR(255), FREQUENCY VARCHAR(255), NAME VARCHAR(255),UNIT VARCHAR(255),FORECAST_ID INT)");
        MyDB.execSQL("CREATE TABLE FORECAST_RESULT( ID INT PRIMARY KEY,FORECAST_DESCRIPTION VARCHAR(1000), FORECAST_MODEL VARCHAR(255), FREQUENCY VARCHAR(255),NAME VARCHAR(255),UNIT VARCHAR(255), FORECAST_ID INT)");
        MyDB.execSQL("CREATE TABLE FORECAST_RESULT_LOWER( IFORECAST_RESULT_ID INT PRIMARY KEY,LOWER DOUBLE,LOWER_ORDER  int not null);");
        MyDB.execSQL("CREATE TABLE FORECAST_RESULT_SERIES( IFORECAST_RESULT_ID INT PRIMARY KEY,SERIES double,SERIES_ORDER int not null)");
        MyDB.execSQL("CREATE TABLE FORECAST_RESULT_UPPER( IFORECAST_RESULT_ID INT PRIMARY KEY,UPPER double,UPPER_ORDER int not null);");
    }

}



