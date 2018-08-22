package com.example.theanh.exe_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ShowActivity extends AppCompatActivity {
    private TextView txt_ht, txt_gt, txt_ns, txt_em;
    private ImageView img_v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String ho_ten1= "";
        String email1= "";
        String ngaysinh1= "";
        if(bundle != null){
            ho_ten1 = bundle.getString("ho_ten");
            email1 = bundle.getString("email");
            ngaysinh1 = bundle.getString("ngaysinh");
        }
        txt_ht = (TextView) findViewById(R.id.txt_hoten1);
        txt_ht.setText(ho_ten1);

        txt_gt = (TextView) findViewById(R.id.txt_gt);
        String v = getIntent().getStringExtra("getData");
        txt_gt.setText(v);

        txt_ns = (TextView) findViewById(R.id.txt_ns);
        txt_ns.setText(ngaysinh1);

        txt_em = (TextView) findViewById(R.id.txt_EM);
        txt_em.setText(email1);

        img_v = (ImageView) findViewById(R.id.img_V);
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("Bitmap");
        img_v.setImageBitmap(bitmap);

    }
}
