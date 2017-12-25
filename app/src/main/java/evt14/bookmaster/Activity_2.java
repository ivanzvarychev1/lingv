package evt14.bookmaster;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;

public class Activity_2 extends Activity {

    Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Bundle extras = getIntent().getExtras();
        String Ganrel =  extras.getString("ganre");
        String BookName;
        String BookImage;


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
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainlayout);

        c = myDbHelper.query("books",null,"ganre = ?",new String[]{Ganrel},null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            BookName = c.getString(1);
            BookImage = c.getString(4);
            final String forActivity_3 = BookName;


            int resID = getResources().getIdentifier(BookImage, "drawable", getApplicationContext().getPackageName());
            ImageView image = new ImageView(getApplicationContext());
            image.setImageResource(resID);
            mainLayout.addView(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), Activity_3.class);
                    intent.putExtra("name",forActivity_3);
                    startActivity(intent);

                }
            });

            TextView text = new TextView(getApplicationContext());
            text.setText(BookName);
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            text.setGravity(Gravity.CENTER_HORIZONTAL);

            mainLayout.addView(text);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                 Intent intent = new Intent(getApplicationContext(), Activity_3.class);
                 intent.putExtra("name",forActivity_3);
                 startActivity(intent);

                }
            });

            c.moveToNext();
        }
        c.close();
        myDbHelper.close();
    }
}