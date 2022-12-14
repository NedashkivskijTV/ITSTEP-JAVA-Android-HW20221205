package edu.itstep.hw20221205;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Зкорегований конструктор
    public DatabaseHelper(Context context) {
        super(context, "PhoneBook", null, 1);
        //super(context, "PhoneBook", null, 2); // конструкція для емуляції оновлення застосунку - спрацьовує метод onUpgrade !!!

        // Створення логів - при виклику конструктора у консоль виведеться повідомлення
        Log.d("dbExec", "DatabaseHelper");
    }

    // Спрацьовує 1 раз при першому запуску мобільного додатку на телефоні
    // відповідає за створення структури БД
    // метод потребує реалізації, а викликається системою
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Створення логів - при виклику метода onCreate у консоль виведеться повідомлення
        Log.d("dbExec", "onCreate");

        // Створення таблиці contacts, метод execSQL виконує запит, але нічого не повертає !!!
        sqLiteDatabase.execSQL("CREATE TABLE contacts(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)");

        // Наповнення створеної таблиці тестовими даними
        sqLiteDatabase.execSQL("INSERT INTO contacts(name, phone) VALUES('Ivan Ivanenko', '+380-50-111-22-33')");
        sqLiteDatabase.execSQL("INSERT INTO contacts(name, phone) VALUES('Petro', '+380-77-112-22-32')");
    }

    // Викликається при оновленні додатку
    @Override
    //public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Створення логів - при виклику метода onUpgrade у консоль виведеться повідомлення
        Log.d("dbExec", "onUpgrade");
    }
}
