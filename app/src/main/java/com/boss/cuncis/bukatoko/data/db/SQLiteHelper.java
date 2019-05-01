package com.boss.cuncis.bukatoko.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.boss.cuncis.bukatoko.data.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BukaToko.db";
    public static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "cart";
    private static final String CART_ID = "cart_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT = "product";
    private static final String IMAGE_URL = "image";
    private static final String PRICE = "price";
    private static final String CURRENT_DATE = "current_date";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                PRODUCT_ID + " INTEGER, " +
                PRODUCT + " TEXT, " +
                IMAGE_URL + " TEXT, " +
                PRICE + " INTEGER, " +
                CURRENT_DATE + "DATE DEFAULT CURRENT_DATE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Long addToCart(int product_id, String product, String image, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_ID, product_id);
        values.put(PRODUCT, product);
        values.put(IMAGE_URL, image);
        values.put(PRICE, price);

        long cart_id = db.insert(TABLE_NAME, null, values);

        return cart_id;
    }

    public Integer checkExists(int product_id) {
        String sql = "SELECT " + PRODUCT_ID + " FROM " + TABLE_NAME + " WHERE " + PRODUCT_ID + "='" + String.valueOf(product_id) + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        int count = cursor.getCount();
        return count;
    }

    public List<Cart> myCart() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + CART_ID + " DESC";

        ArrayList<Cart> carts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            Cart cart = new Cart();
            cart.setCart_id(cursor.getInt(cursor.getColumnIndex(CART_ID)));
            cart.setProduct_id(cursor.getInt(cursor.getColumnIndex(PRODUCT_ID)));
            cart.setProduct(cursor.getString(cursor.getColumnIndex(PRODUCT)));
            cart.setImage(cursor.getString(cursor.getColumnIndex(IMAGE_URL)));
            cart.setPrice(cursor.getInt(cursor.getColumnIndex(PRICE)));
//            cart.setCurr_date(cursor.getString(cursor.getColumnIndex(CURRENT_DATE)));
            cart.setCurr_date(cursor.getString(5));

            Log.d("_logProductName", "myCart: " + cursor.getString(cursor.getColumnIndex(PRODUCT)));
            carts.add(cart);
        }

        return carts;
    }

    public void removeItem(String cart_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, CART_ID + "=?", new String[]{cart_id});
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}
















