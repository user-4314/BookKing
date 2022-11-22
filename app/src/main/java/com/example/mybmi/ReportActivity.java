package com.example.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    ListView suggest_hotel;
    ArrayList<String> hotel_name;
    ArrayList<String> hotel_price;
    ArrayList<String> hotel_address;
    String result;
    String strjson;
    private double price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initViews();
        showResults();
        setListensers();
        setListenser();

        Thread thread = new Thread(mutiThread);
        thread.start(); // 開始執行

    }


    private Button button_back;
    private Button button_next;
    private TextView show_result;
    private TextView show_suggest;

    private void initViews()
    {
        button_back = (Button)findViewById(R.id.button);
        button_next = (Button)findViewById(R.id.button1);
        show_result = (TextView)findViewById(R.id.result);
        show_suggest = (TextView) findViewById(R.id.suggest);
        //hotel_name=new ArrayList<>();
        //hotel_price=new ArrayList<>();
        //hotel_address=new ArrayList<>();
    }
    private void showResults()
    {
        try
        {
            DecimalFormat nf = new DecimalFormat("0");
            Bundle bundle = this.getIntent().getExtras();

            double checkinmon = Double.parseDouble(bundle.getString("KEY_checkinmon"));
            double checkind = Double.parseDouble(bundle.getString("KEY_checkind"));
            double checkoutmon = Double.parseDouble(bundle.getString("KEY_checkoutmon"));
            double checkoutd = Double.parseDouble(bundle.getString("KEY_checkoutd"));
            double price = Double.parseDouble(bundle.getString("KEY_price"));
            double person = Double.parseDouble(bundle.getString("KEY_person"));
            double judge = Double.parseDouble(bundle.getString("KEY_judge"));
            double day = checkoutd - checkind + (checkoutmon-checkinmon)*30;
            String county = bundle.getString("signal_string");


            show_result.setText(getText(R.string.dayf_result) + nf.format(day)+getText(R.string.dayb_result)
                                +getText(R.string.location_result) +county
                                +getText(R.string.roomf_result) +nf.format(person)+getText(R.string.roomb_result)
                                +getText(R.string.judgef_result) +judge+getText(R.string.judgeb_result)
                                +getText(R.string.hotel_result)
            );

            if(price >= day*person*1500) {

                show_result.setText(R.string.advice_high);
            }
            else {//剛剛好

                show_result.setText(R.string.advice_low);
            }
        }
        catch(Exception obj) {
            Toast.makeText(this, "要先輸入完整資料喔!", Toast.LENGTH_SHORT).show();
        }
    }


    private Runnable mutiThread = new Runnable(){
        public void run()
        {

            try {
                URL url = new URL("http://140.136.151.144/GetData.php");
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                int responseCode =
                        connection.getResponseCode();
                // 建立取得回應的物件
                if(responseCode ==
                        HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream =
                            connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String box = ""; // 宣告存放用字串
                    String line = null; // 宣告讀取用的字串
                    while((line = bufReader.readLine()) != null) {

                        box += line + "\n";
                        // 每當讀取出一列，就加到存放字串後面
                    }
                    inputStream.close(); // 關閉輸入串流
                    result = box; // 把存放用字串放到全域變數
                    /*JSONArray array = new JSONArray(strjson);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);

                        String hname = jsonObject.getString("name");
                        String hprice = jsonObject.getString("price");
                        String hadd = jsonObject.getString("address");

                        hotel_name.add(hname);
                        hotel_price.add(hprice);
                        hotel_address.add(hadd);
                    }*/

                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) {
                result = e.toString(); // 如果出事，回傳錯誤訊息
            }


            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    show_suggest.setText(result); // 更改顯示文字
                }
            });
        }
    };

    /*public class listlayoutadapter extends BaseAdapter {

        private LayoutInflater listlayoutInflater;

        public listlayoutadapter(Context c){
            listlayoutInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            //取得ArrayList的總數 (要注意，跟array不同之處)
            return hotel_name.size();
        }

        @Override
        public Object getItem(int position) {
            //要用get(position)取得資料 (要注意，跟array不同之處)
            return  hotel_name.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = listlayoutInflater.inflate(R.layout.listlayout,null);

            //設定自訂樣板上物件對應的資料。

            TextView lbl_name = (TextView) convertView.findViewById(R.id.hotelname);
            TextView lbl_price = (TextView) convertView.findViewById(R.id.hotelprice);
            TextView lbl_address = (TextView) convertView.findViewById(R.id.hoteladdress);
            //要用get(position)取得資料 (要注意，跟array不同之處)

            lbl_name.setText(hotel_name.get(position));
            lbl_price.setText(hotel_price.get(position));
            lbl_address.setText(hotel_address.get(position));

            return convertView;
        }
    }*/
    private void setListensers()
    {
        button_back.setOnClickListener(backtoMain);
    }

    private Button.OnClickListener backtoMain = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent intent = new Intent();
            intent.setClass(ReportActivity.this, MainActivity.class);
            DecimalFormat nf = new DecimalFormat("0");

            Bundle bundle = new Bundle();
            bundle.putString("預算", nf.format(price));

            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            ReportActivity.this.finish();
            startActivity(intent);
        }
    };
    private void setListenser()
    {
        button_next.setOnClickListener(gotonext);
    }
    private View.OnClickListener gotonext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ReportActivity.this, bookhotel.class);
            startActivity(intent);
        }
    };
}