package com.example.govert.nutriconscious;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class InputActivity extends AppCompatActivity {
TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        text = (TextView) findViewById(R.id.text1);

        Button button = (Button) findViewById(R.id.but1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new doit().execute();
            }
        });
    }

    public class doit extends AsyncTask<Void, Void, Void> {
        String words;

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                Document doc = Jsoup.connect("https://ndb.nal.usda.gov/ndb/foods/show/01009" +
                        "?fgcd=&manu=&format=Full&count=&max=25&offset=&sort=default&order=asc&q" +
                        "lookup=01009&ds=&qt=&qp=&qa=&qn=&q=&ing=").get();
                words = doc.text();

            }catch(Exception e){e.printStackTrace();}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            text.setText(words);
        }
    }

}
