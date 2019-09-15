package com.example.lee.donglend2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class viewItem extends AppCompatActivity implements OnMapReadyCallback {
    String title,price,contents,id,x,y,img;
    String selete;

    TextView selete_l,title_l,price_l,contents_l;
    ImageView imgview;

    private GoogleMap mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        selete=intent.getStringExtra("selete");
        title=intent.getStringExtra("title");
        price=intent.getStringExtra("price");
        contents=intent.getStringExtra("contents");
        img=intent.getStringExtra("img");
        x=intent.getStringExtra("x");
        y=intent.getStringExtra("y");

        selete_l = (TextView) findViewById(R.id.Lselete);
        title_l = (TextView) findViewById(R.id.Ltitle);
        price_l = (TextView) findViewById(R.id.Lprice);
        contents_l = (TextView) findViewById(R.id.Lcontents);
        imgview = (ImageView) findViewById(R.id.image_view2);


        title_l.setText("제목 : "+title+"\n");
        selete_l.setText(selete+"\n");
        price_l.setText(price+"원\n");
        contents_l.setText("내용 : \n"+contents+"\n\n");

        Glide.with(this).asBitmap().load(img).into(this.imgview);









    }

    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng point = new LatLng(Double.parseDouble(x), Double.parseDouble(y));
        // 마커 생성
        mMap.addMarker(new MarkerOptions().position(point).title("거래 장소"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));

    }
}
