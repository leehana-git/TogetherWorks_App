package kr.co.TogetherWorks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText com_id, mem_id, mem_pw;
    TextView sign_up_btn,id_search_btn, pw_search_btn;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //에디트텍스트, 버튼, 텍스트뷰 읽어 오기
        com_id = (EditText)findViewById(R.id.com_id);
        mem_id = (EditText)findViewById(R.id.mem_id);
        mem_pw = (EditText)findViewById(R.id.mem_pw);
        login_btn = (Button)findViewById(R.id.login_btn);
        sign_up_btn = (TextView)findViewById(R.id.sign_up_btn);
        id_search_btn = (TextView)findViewById(R.id.id_search_btn);
        pw_search_btn = (TextView)findViewById(R.id.pw_search_btn);

        login_btn.setOnClickListener(this);
        sign_up_btn.setOnClickListener(this);
        id_search_btn.setOnClickListener(this);
        pw_search_btn.setOnClickListener(this);


        /*login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            try{
                    String result;
                    IdPwCheck check = new IdPwCheck();
                    result = check.execute(com_id.getText().toString(),mem_id.getText().toString(),mem_pw.getText().toString()).get();
                    Log.i("리턴 값",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        Intent sign_up_intent, id_search_intent, pw_search_intent;
        switch (v.getId()) {
            case R.id.login_btn:
                try{
                    String result;
                    IdPwCheck check = new IdPwCheck();
                    result = check.execute(com_id.getText().toString(),mem_id.getText().toString(),mem_pw.getText().toString()).get();
                    Log.i("리턴 값",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.sign_up_btn:
                sign_up_intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        Join_agree_form.class); // 다음 넘어갈 클래스 지정
                startActivity(sign_up_intent); // 다음 화면으로 넘어간다
                break;
            case R.id.id_search_btn:
                id_search_intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        Member_id_search_form.class); // 다음 넘어갈 클래스 지정
                startActivity(id_search_intent); // 다음 화면으로 넘어간다
                break;
            case R.id.pw_search_btn:
                pw_search_intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        Member_pw_search_form.class); // 다음 넘어갈 클래스 지정
                startActivity(pw_search_intent); // 다음 화면으로 넘어간다
                break;
        }
    }

    class IdPwCheck extends AsyncTask<String, Void, String>{
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {

            try{
                String str;
                URL url = new URL("http://192.168.40.23:8080/www/android/A_login.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "com_id="+strings[0]+"&mem_id="+strings[1]+"&mem_pw="+strings[2];
                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }


            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
}
