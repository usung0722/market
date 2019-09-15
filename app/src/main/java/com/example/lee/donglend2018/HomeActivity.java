package com.example.lee.donglend2018;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Button seaschbt,re;
    RecyclerView recyclerView;
    String id;
    Toolbar tool_bar;
    private static final int MY_PERMISSION_STORAGE = 1111;
    public static final int sub = 1001; //regist 요청코드 INTENT
    TabPagerAdapter pagerAdapter;

    public String seasch;

    public static Context CONTEXT;

    LinearLayout sec;

    TextView seashText;



    private static String IP_ADDRESS = "49.170.233.37";
    private static String TAG = "selltest";


    private ArrayList<PersonalData> mArrayList;
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;
    String imggg,imgadress;


    String imgUrl = "http://49.170.233.37/";





    // 뒤로가기 금지
    public void onBackPressed() { //super.onBackPressed();
    }

    //메뉴 등록
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    //메뉴 선택
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.action_settings:
               // Intent intent = new Intent(this,RegistActivity.class);
//                startActivities(new Intent[]{new  Intent(getApplicationContext(), RegistActivity.class)});
               // intent.putExtra("act",HomeActivity.class);
             //   startActivityForResult(intent,sub);


                Intent intent = new Intent(this, RegistActivity.class);
                intent.putExtra("id", String.valueOf(id));
                startActivity(intent);



                return true;
            default: //로그아웃 클릭
//                Toast.makeText(getApplicationContext(),"나가기 클릭",Toast.LENGTH_SHORT).show();
                showMessage();
//                return super.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);


    }

    //로그아웃 클릭시
        private void showMessage(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("안내");
            builder.setMessage("로그아웃 하시겠습니까?");
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
    }

    //퍼미션 체크
    private void checkPermission2(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 다시 보지 않기 버튼을 만드려면 이 부분에 바로 요청을 하도록 하면 됨 (아래 else{..} 부분 제거)
            // ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);

            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
            }
        }
    }

    //퍼미션 검사2
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
        case MY_PERMISSION_STORAGE:
            for (int i = 0; i < grantResults.length; i++) {
                // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                if (grantResults[i] < 0) {
                    Toast.makeText(HomeActivity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            // 허용했다면 이 부분에서..

            break;
    }
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);



        seashText = (TextView) findViewById(R.id.seashText);



        sec = (LinearLayout) findViewById(R.id.liner);
        CONTEXT = this;

        checkPermission2();

        re = (Button) findViewById(R.id.re);

        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });

        tool_bar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(tool_bar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        seaschbt = (Button) findViewById(R.id.button2);
        final Intent intent = new Intent(this.getIntent());
        id = intent.getStringExtra("text");
        Toast.makeText(HomeActivity.this, id+"로그인 되었습니다.", Toast.LENGTH_SHORT).show();
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("팝니다"));
        tabLayout.addTab(tabLayout.newTab().setText("삽니다"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(HomeActivity.this,DividerItemDecoration.VERTICAL));



        mArrayList = new ArrayList<>();

        mAdapter = new UsersAdapter(HomeActivity.this,mArrayList);
        mRecyclerView.setAdapter(mAdapter);



        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setVisibility(View.VISIBLE);
                sec.setVisibility(View.INVISIBLE);
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setVisibility(View.VISIBLE);
                sec.setVisibility(View.INVISIBLE);
                viewPager.setCurrentItem(tab.getPosition());

            }
        });






        seaschbt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                sec.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.INVISIBLE);

                seasch = seashText.getText().toString();

                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/query.php", seasch);

            }


        });
    }




    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(HomeActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null){

            }
            else {

                mJsonString = result;
                Log.d("kkkk",result);
                showResult();

            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "key="+params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }



    private void showResult(){

        String TAG_JSON="root";
        String TAG_ID = "id";
        String TAG_SELETE = "selete";
        String TAG_TITLE ="title";
        String TAG_PRICE ="price";
        String TAG_CONTENTS = "contents";
        String TAG_IMG= "img";
        String TAG_X="x";
        String TAG_Y="y";



        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String selete = item.getString(TAG_SELETE);
                String title = item.getString(TAG_TITLE);
                String price = item.getString(TAG_PRICE);
                String contents = item.getString(TAG_CONTENTS);
                String img = item.getString(TAG_IMG);
                String x = item.getString(TAG_X);
                String y = item.getString(TAG_Y);

                PersonalData personalData = new PersonalData();

                personalData.setMember_id(id);
                personalData.setMember_selete(selete);
                personalData.setMember_title(title);
                personalData.setMember_price(price);
                personalData.setMember_contents(contents);
                personalData.setMember_img(img);
                personalData.setMember_x(x);
                personalData.setMember_y(y);

                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }






}


