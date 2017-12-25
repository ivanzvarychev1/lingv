package evt14.bookmaster;

import android.app.Activity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.SQLException;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.IOException;

public class Activity_3 extends Activity {

    Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Bundle extras = getIntent().getExtras();
        String name =  extras.getString("name");
        String text;

        DatabaseHelper myDbHelper = new DatabaseHelper(getApplicationContext());
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        ScrollView scrollLayout = (ScrollView) findViewById(R.id.mailayout);

        String nametrue = "";
        int x = 0;
        c = myDbHelper.query("books",null,null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            nametrue = c.getString(1);
            if (name.equals(nametrue)) {
                x = 1;
            }
            c.moveToNext();
        }
        c.close();
        if(x == 1) {
            c = myDbHelper.query("books", null, "name = ?", new String[]{name}, null, null, null);
            c.moveToFirst();
            text = c.getString(3);
            TextView textView = new TextView(getApplicationContext());
            textView.setText(text);
            scrollLayout.addView(textView);
            c.close();
        }
        else{
            TextView textView = new TextView(getApplicationContext());
            textView.setText("Книга не найдена либо неправильно написано название");
            scrollLayout.addView(textView);
        }
        myDbHelper.close();
    }
}
