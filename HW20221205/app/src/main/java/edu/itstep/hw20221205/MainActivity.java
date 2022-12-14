package edu.itstep.hw20221205;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Змінні екземпляра класу, що відповідають активним елеменнтам Актівіті
    private EditText etName;
    private EditText etPhone;
    private Button btnSave;
    private Button btnAll;

    // Змінна для збереження об'єкта sqLiteDatabase - використовується для здійснення маніпуляцій з БД
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ініціалізація (отримання за id) змінних екземпляра класу
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);
        btnAll = findViewById(R.id.btnAll);

        // Підключення слухачів
        btnSave.setOnClickListener(this::save);
        btnAll.setOnClickListener(this::showAll);

        // Ініціалізація змінної SQLiteDatabase через DatabaseHelper
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        // Методи, що доступні у об'єкта DatabaseHelper
        database = databaseHelper.getWritableDatabase(); // метод дозволяє записувати та читати інф
    }

    // Збереження введених даних до БД
    private void save(View view) {
        // Отримання даних з елементів Актівіті
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();

        // Збереження даних до БД
        ContentValues contentValues = new ContentValues(); // створення контейнера для передачі даних
        contentValues.put("name", name); // наповнення контейнера
        contentValues.put("phone", phone); // наповнення контейнера
        database.insert(
                "contacts", // назва таблиці в БД до якої зберігаються дані
                null, // використовується при необхідності залишення рядка пустим
                contentValues // контейнер, де вказано що і куди покласти
        );

        // очищення текстових полів після збереження даних до БД
        etName.setText("");
        etPhone.setText("");

        // Тостове повідомлення для користовача, про те,що дані збережено
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();

        // Код для програмного закритті клавіатури (після збереження даних залишається відкритою)
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    // Виведення вмісту БД - перехід до Актівіті, де відображатиметься список
    private void showAll(View view) {

        // Запуск Актівіті, в якому відображатиметься список
        startActivity(new Intent(this, ContactsActivity.class));
    }

}