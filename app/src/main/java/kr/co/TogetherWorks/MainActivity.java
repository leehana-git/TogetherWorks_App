package kr.co.TogetherWorks;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText com_id, mem_id, mem_pw;
    TextView sign_up_btn, id_search_btn, pw_search_btn, terms_of_service, privacy, information_communication_network_act, personal_information_protection_act;
    Button login_btn;
    String url_str = "http://192.168.40.23:8080/www/android/";
    //String url_str = "http://togetherworks.co.kr/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //에디트텍스트, 버튼, 텍스트뷰 읽어 오기
        com_id = (EditText) findViewById(R.id.com_id);
        mem_id = (EditText) findViewById(R.id.mem_id);
        mem_pw = (EditText) findViewById(R.id.mem_pw);
        login_btn = (Button) findViewById(R.id.login_btn);
        sign_up_btn = (TextView) findViewById(R.id.sign_up_btn);
        id_search_btn = (TextView) findViewById(R.id.id_search_btn);
        pw_search_btn = (TextView) findViewById(R.id.pw_search_btn);
        terms_of_service = (TextView) findViewById(R.id.terms_of_service);
        privacy = (TextView) findViewById(R.id.privacy);
        information_communication_network_act = (TextView) findViewById(R.id.information_communication_network_act);
        personal_information_protection_act = (TextView) findViewById(R.id.personal_information_protection_act);

        login_btn.setOnClickListener(this);
        sign_up_btn.setOnClickListener(this);
        id_search_btn.setOnClickListener(this);
        pw_search_btn.setOnClickListener(this);
        terms_of_service.setOnClickListener(this);
        privacy.setOnClickListener(this);
        information_communication_network_act.setOnClickListener(this);
        personal_information_protection_act.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                boolean is_empty = Is_Empty(); //아이디 비번 공백 확인

                if (is_empty) {
                    try {
                        String com_id_str, mem_id_str, mem_pw_str;
                        com_id_str = com_id.getText().toString().replace(" ", "");
                        mem_id_str = mem_id.getText().toString().replace(" ", "");
                        mem_pw_str = mem_pw.getText().toString().replace(" ", "");

                        IdPwCheck check = new IdPwCheck();
                        //리턴값에 공백하나 같이 출력되어 trim()사용
                        String result = check.execute(com_id_str, mem_id_str, mem_pw_str).get().trim();
                        Log.i("리턴 값", result);

                        if (result.equals("로그인")) {
                            Class Member_main = Member_main.class;
                            change_screen(Member_main);
                        } else {
                            new SweetAlertDialog(MainActivity.this)
                                    .setTitleText(result)
                                    .setConfirmText("확인")
                                    .show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
                break;

            case R.id.sign_up_btn:
                Class Join_agree = Join_agree_form.class;
                change_screen(Join_agree);  //클래스명으로 화면 전환
                break;

            case R.id.id_search_btn:
                Class Member_id_search = Member_id_search_form.class;
                change_screen(Member_id_search);  //클래스명으로 화면 전환
                break;

            case R.id.pw_search_btn:
                Class Member_pw_search = Member_pw_search_form.class;
                change_screen(Member_pw_search);  //클래스명으로 화면 전환
                break;

            case R.id.terms_of_service:
                String terms_of_service_url = url_str + "A_index_terms_of_service.jsp";
                change_screen(terms_of_service_url);  //URL을 웹뷰로 화면전환
                break;

            case R.id.privacy:
                String privacy_url = url_str + "A_index_privacy.jsp";
                change_screen(privacy_url);  //URL을 웹뷰로 화면전환
                break;

            case R.id.information_communication_network_act:
                String web_URL1 = "http://www.law.go.kr/lsInfoP.do?lsiSeq=167388&efYd=20150421#0000";
                change_screen(web_URL1);  //URL을 웹뷰로 화면전환
                break;

            case R.id.personal_information_protection_act:
                String web_URL2 = "http://www.law.go.kr/lsInfoP.do?lsiSeq=173223&efYd=20150724#0000";
                change_screen(web_URL2);  //URL을 웹뷰로 화면전환
                break;
        }
    }

    //웹뷰로 URL주소 내용 화면전환
    public void change_screen(String URL){
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                Web_view.class); // 다음 넘어갈 클래스 지정
        intent.putExtra("URL", URL); //데이터 송신
        startActivity(intent); // 다음 화면으로 넘어간다
    }

    // 클래스명으로 화면전환
    public void change_screen(Class name){
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                name); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다
    }

    //아이디 비번 공백 확인
    public boolean Is_Empty() {
        String title, sub_text;
        if (TextUtils.isEmpty(com_id.getText().toString())) {
            title = "회사 아이디";
            sub_text = "회사 아이디를 입력해 주세요!";
            return sweet_alert(title, sub_text, com_id);
        }
        if (TextUtils.isEmpty(com_id.getText().toString().replace(" ", ""))) {
            title = "회사 아이디";
            sub_text = "공백을 입력하지 마세요!";
            return sweet_alert(title, sub_text, com_id);
        }
        if (TextUtils.isEmpty(mem_id.getText().toString())) {
            title = "회원 아이디";
            sub_text = "회원 아이디를 입력해 주세요!";
            return sweet_alert(title, sub_text, mem_id);
        }
        if (TextUtils.isEmpty(mem_id.getText().toString().replace(" ", ""))) {
            title = "회원 아이디";
            sub_text = "공백을 입력하지 마세요!";
            return sweet_alert(title, sub_text, mem_id);
        }
        if (TextUtils.isEmpty(mem_pw.getText().toString())) {
            title = "회원 비밀번호";
            sub_text = "회원 비밀번호를 입력해 주세요!";
            return sweet_alert(title, sub_text, mem_pw);
        }
        if (TextUtils.isEmpty(mem_pw.getText().toString().replace(" ", ""))) {
            title = "회원 비밀번호";
            sub_text = "공백을 입력하지 마세요!";
            return sweet_alert(title, sub_text, mem_pw);
        }
        return true;
        //
    }

    // 알림창 및 입력창 초기화, 포커스
    public boolean sweet_alert(String title, String sub_text, EditText name){
        new SweetAlertDialog(MainActivity.this)
                .setTitleText(title)
                .setContentText(sub_text)
                .setConfirmText("확인")
                .show();
        name.setText(null);
        name.requestFocus();
        return false;
    }

    class IdPwCheck extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {

            try {
                String str;
                URL url = new URL(url_str + "A_login.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "com_id=" + strings[0] + "&mem_id=" + strings[1] + "&mem_pw=" + strings[2];
                osw.write(sendMsg);
                osw.flush();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }


}
