package com.fazendaurbana.alfacemania;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Alfacemania.DB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ORDERS = "Ordens";
    private static final String COLUMN_ID = "IdOrdens";
    private static final String COLUMN_METHOD = "method";
    private static final String COLUMN_DATE = "date";

    // Para a tabela de usuários (se você for implementar)
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean createOrder(int userId, String orderNumber, ArrayList<String> products, double total, String paymentMethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("order_number", orderNumber);
        values.put("total", total);
        values.put("payment_method", paymentMethod);
        values.put("products", new Gson().toJson(products)); // Converter a lista para JSON

        long result = db.insert("orders", null, values);
        return result != -1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação da tabela de pedidos
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_METHOD + " TEXT,"
                + COLUMN_DATE + " TEXT" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);

        // Criação da tabela de usuários (se você precisar)
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_METHOD, order.getMethod());
        values.put(COLUMN_DATE, order.getDate());

        db.insert(TABLE_ORDERS, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ORDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                order.setMethod(cursor.getString(cursor.getColumnIndex(COLUMN_METHOD)));
                order.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                orderList.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return orderList;
    }

    // Método para buscar um usuário
    public Cursor getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE "
                + COLUMN_USERNAME + " = ? AND "
                + COLUMN_PASSWORD + " = ?";

        // Retorna o cursor com os resultados da consulta
        return db.rawQuery(selectQuery, new String[]{username, password});
    }
}