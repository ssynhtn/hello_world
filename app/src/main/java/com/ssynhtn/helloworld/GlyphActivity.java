package com.ssynhtn.helloworld;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GlyphActivity extends AppCompatActivity {

    private static final String TAG = GlyphActivity.class.getSimpleName();
    @BindView(R.id.text_view)
    TextView text_view;

    @BindView(R.id.text_view_two)
    TextView text_view_two;

    @BindView(R.id.text_view_center)
    TextView text_view_center;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glyph);


        ButterKnife.bind(this);

        boolean work = false;
        if (work) {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    return new Glyph().work();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Toast.makeText(GlyphActivity.this, "work finished " + s, Toast.LENGTH_SHORT).show();

                    text_view.setText(text_view.getText() + " " + s);
                }
            }.execute();

        }

        String music = "\uD834\uDD1F";
        addToClipBoard("\uD834\uDD1F");
        text_view_two.setText("\uD834\uDD1F");
        text_view_center.setText(getString(R.string.music));
        Toast.makeText(this, getString(R.string.music), Toast.LENGTH_SHORT).show();

        measureText();

        getTypeFace();

    }

    private void getTypeFace() {

        Map<String, Typeface> typefaces = getSSystemFontMap();
        for (String key : typefaces.keySet()) {
            Log.d(TAG, "typeface name " + key);
        }
    }

    protected Map<String, Typeface> getSSystemFontMap() {
        Map<String, Typeface> sSystemFontMap = null;
        try {
            //Typeface typeface = Typeface.class.newInstance();
            Typeface typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
            Field f = Typeface.class.getDeclaredField("sSystemFontMap");
            f.setAccessible(true);
            sSystemFontMap = (Map<String, Typeface>) f.get(typeface);
            for (Map.Entry<String, Typeface> entry : sSystemFontMap.entrySet()) {
                Log.d("FontMap", entry.getKey() + " ---> " + entry.getValue() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sSystemFontMap;
    }

    private void measureText() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(50);

        Rect rect = new Rect();
        paint.getTextBounds("h", 0, 1, rect);
        Log.d(TAG, "h top " + rect.top + ", " + rect);

        paint.getTextBounds("g", 0, 1, rect);
        Log.d(TAG, "g bottom " + rect.bottom + ", " + rect);

        paint.getTextBounds("hg", 0, 2, rect);
        Log.d(TAG, "hg top bottom " + rect.top + ", " + rect.bottom + ", " + rect);
    }

    private void addToClipBoard(String str) {
        ClipData clipData = ClipData.newPlainText("hello", str);
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
    }
}
