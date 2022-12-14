package edu.itstep.hw20221205;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {

    // змінні екземпляра класу - активні елементи Актівіті
    private ListView lvContacts;

    private SimpleCursorAdapter adapter; // адаптер для роботи з БД
    private SQLiteDatabase database; // БД для отримання даних

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Ініціалізація даних
        lvContacts = findViewById(R.id.lvContacts);

        // Ініціалізація БД через DatabaseHelper
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase(); // метод дозволяє записувати та читати інф

        // Метод, що виконує створення курсора (запит до БД), створення Адаптера на основі курсора, підключення Адаптера до списка
        databaseRequestMaker();

        // Підключення до списку слухача по кліку на елемент
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(ContactsActivity.this, "" + position + " " + id, Toast.LENGTH_SHORT).show();
            }
        });

        // Підключення слухача - видалення по ДОВГОМУ кліку на елемент
        lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(ContactsActivity.this, "LONG", Toast.LENGTH_SHORT).show();

                // Запит до БД на видалення рядка за id
                database.execSQL("DELETE FROM contacts WHERE _ID = " + id);

                // Метод, що виконує створення курсора (запит до БД),
                // створення Адаптера на основі курсора, підключення Адаптера до списка
                databaseRequestMaker();

                return false;
            }
        });

    }

    // Метод, що виконує створення курсора (запит до БД),
    // створення Адаптера на основі курсора, підключення Адаптера до списка
    private void databaseRequestMaker() {
        // Створення курсора - міститиме результат виборки з БД (метод rawQuery повертає результат) - дані у вигляді масива, дозволено цикли
        // в параметрах SQL запит та null - потрібен щоб у вигляді масива можна було передати різні фільтрації для запиту (зазвичай ставлять null, прописуючи запит SQL), щоб позбавитись від SQL створюють власний ORM
        Cursor cursor = database.rawQuery("SELECT * FROM contacts", null);

        // Створення Адаптера
        adapter = new SimpleCursorAdapter(
                this, // контекст
                android.R.layout.two_line_list_item, // вбудований лейаут, що містить два текстових поля для виведення даних
                cursor, // курсор (зазвичай масив даних або колекція) - результат виборки з БД
                new String[]{"name", "phone"}, // параметр from - масив рядків, де вказуються назви полів, з яких отримується інф
                new int[]{android.R.id.text1, android.R.id.text2}, // цілочисельний масив, що містить id текстових полів у лейауті до яких потрібно помістити вміст відповідних полів БД (для перегляду вмісту стандартного лейаута Ctrl + ЛКМ на ім'я лейаута)
                0 // параметр відповідає за те, в якому потоці відбуватиметься запит
        );

        // Встановлення адаптера до списка
        lvContacts.setAdapter(adapter);
    }
}
