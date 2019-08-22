package com.isansc.retrofitpoc.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.isansc.retrofitpoc.R;


public class AboutActivity extends BaseActivity {

    @Override
    protected TransitionType getTransitionType() {
        return TransitionType.FADE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupComponents();
    }

    private void setupComponents(){
        ImageButton btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        ViewGroup grpDatabox = findViewById(R.id.grp_databox);
//        grpDatabox.setClickable(false);
//        grpDatabox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        TextView txtEmail = findViewById(R.id.txt_email);
        txtEmail.setText(Html.fromHtml(getString(R.string.about_email)));
        txtEmail.setMovementMethod(LinkMovementMethod.getInstance());

        final TextView txtPhone = findViewById(R.id.txt_phone);
        txtPhone.setClickable(true);
        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber= txtPhone.getText().toString().replace("-", "").replace("(", "").replace(")", "");
                String uri = "tel:" + phoneNumber ;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });
    }
}
