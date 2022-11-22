package com.example.mybmi;

import android.content.Intent;
import android.os.LocaleList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int ACTIVITY_REPORT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Main", "開始事件");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        setListensers();
    }

    private Button button_calc;
    private Spinner num_checkinmon;
    private Spinner num_checkoutmon;
    private Spinner num_checkind;
    private Spinner num_checkoutd;
    private EditText num_price;
    private Spinner str_county;
    private EditText num_person;
    private EditText num_judge;
    private TextView show_result;
    private TextView show_suggest;

    private void initViews()
    {
        button_calc = (Button)findViewById(R.id.button);
        num_checkinmon = (Spinner)findViewById(R.id.checkinmon);
        num_checkind = (Spinner)findViewById(R.id.checkinday);
        num_checkoutmon = (Spinner)findViewById(R.id.checkoutmon);
        num_checkoutd = (Spinner)findViewById(R.id.checkoutday);
        num_price = (EditText)findViewById(R.id.price);
        str_county = (Spinner)findViewById(R.id.county);
        num_person = (EditText)findViewById(R.id.person);
        num_judge = (EditText)findViewById(R.id.judge);
        show_result = (TextView) findViewById(R.id.result);
        show_suggest = (TextView)findViewById(R.id.suggest);
    }

    private void setListensers()
    {
        button_calc.setOnClickListener(calcday);
    }

   private OnClickListener calcday = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ReportActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("KEY_checkinmon", num_checkinmon.getSelectedItem().toString());
            bundle.putString("KEY_checkind", num_checkind.getSelectedItem().toString());
            bundle.putString("KEY_checkoutmon", num_checkoutmon.getSelectedItem().toString());
            bundle.putString("KEY_checkoutd", num_checkoutd.getSelectedItem().toString());
            bundle.putString("KEY_price", num_price.getText().toString());
            bundle.putString("signal_string", str_county.getSelectedItem().toString());
            bundle.putString("KEY_person", num_person.getText().toString());
            bundle.putString("KEY_judge", num_judge.getText().toString());
            intent.putExtras(bundle);
            startActivityForResult(intent, ACTIVITY_REPORT);
        }
    };



    private void openOptionsDialog()
    {
        Toast popup = Toast.makeText(this, "訂房網站", Toast.LENGTH_SHORT);
        popup.show();
    }
}



