package com.rmproduct.calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBack extends AppCompatActivity {

    private EditText name, text;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        if (!isConnected()) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_warning)
                    .setTitle("No Internet Connection!")
                    .setMessage("Please Check Your Internet Connection and Try Again!")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }

        name=findViewById(R.id.name);
        text=findViewById(R.id.text);
        send=findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String strName=name.getText().toString().trim();
                    String strText=text.getText().toString().trim();

                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/email");

                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"rmmostak@yahoo.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from All Calendar Mobile App");
                    intent.putExtra(Intent.EXTRA_TEXT, "Name: "+strName+"\nMessage: "+strText);

                    startActivity(Intent.createChooser(intent, "Send feedback using..."));

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Exception: "+e, Toast.LENGTH_SHORT).show();
                }

                name.setText("");
                text.setText("");
            }
        });
    }

    public boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
