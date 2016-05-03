package x7a.droid.advancedapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DroiD on 29/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Advanced.db";
    public static final String TABLE_NAME_EXPENSES = "expenses";
    public static final String TABLE_NAME_INCOMES = "income";
    public static final String COL_1_EXP = "id";
    public static final String COL_2_EXP = "description_exp";
    public static final String COL_3_EXP = "amount_exp";
    public static final String COL_1_INC = "id";
    public static final String COL_2_INC = "description_inc";
    public static final String COL_3_INC = "amount_inc";
    public static final String TABLE_CREATE_EXPENSES = "CREATE TABLE " + TABLE_NAME_EXPENSES +"("+
            COL_1_EXP + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL_2_EXP + " TEXT, "+
            COL_3_EXP + " INT); ";
//            COL_3_EXP + " TEXT); ";
    public static final String TABLE_CREATE_INCOMES = "CREATE TABLE " + TABLE_NAME_INCOMES +"("+
            COL_1_INC + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL_2_INC + " TEXT, "+
            COL_3_INC + " INT); ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_EXPENSES);
        db.execSQL(TABLE_CREATE_INCOMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" +TABLE_NAME_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS" +TABLE_NAME_INCOMES);
    }

    //Methode SAVE
    public boolean save_expanses(String description_exp, String amount_exp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_EXP, description_exp);
        cv.put(COL_3_EXP, amount_exp);
        long result = db.insert(TABLE_NAME_EXPENSES, null, cv);
        return result != -1;
    }
    public boolean save_income(String description_inc, String amount_inc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_INC, description_inc);
        cv.put(COL_3_INC, amount_inc);
        long result = db.insert(TABLE_NAME_INCOMES, null, cv);
        return result != -1;
    }

    //Method GET
    public Cursor list_expenses(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor expenses = db.rawQuery("SELECT "+COL_1_EXP+" as '_id', "+COL_2_EXP+", "+COL_3_EXP+" FROM " +TABLE_NAME_EXPENSES, null);
//        Cursor expenses = db.rawQuery("SELECT "+COL_1_EXP+" as '_id', "+COL_2_EXP+", "+COL_3_EXP+" FROM " + TABLE_NAME_EXPENSES, null);
        return expenses;
    }
    public Cursor list_incomes(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor incomes = db.rawQuery("SELECT "+COL_1_INC+" as '_id', "+COL_2_INC+", "+COL_3_INC+" FROM " + TABLE_NAME_INCOMES, null);
        return incomes;
    }

    //METHOD UPDATE
    public boolean update_expenses(String id, String description_exp, String amount_exp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1_EXP, id);
        cv.put(COL_2_EXP, description_exp);
        cv.put(COL_3_EXP, amount_exp);
        db.update(TABLE_NAME_EXPENSES, cv, "ID = ?", new String[]{id});
        return true;
    }
    public boolean update_income(String id, String description_inc, String amount_inc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1_INC, id);
        cv.put(COL_2_INC, description_inc);
        cv.put(COL_3_INC, amount_inc);
        db.update(TABLE_NAME_INCOMES, cv, "ID = ?", new String[]{id});
        return true;
    }

    //METHOD DEL
    public Integer delete_expenses(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_EXPENSES, "ID = ?", new String[]{id});
    }
    public Integer delete_income(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_INCOMES, "ID = ?", new String[]{id});
    }

}
