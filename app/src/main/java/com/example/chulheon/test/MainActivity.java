package com.example.chulheon.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private final String TAG = "Connect FTP";
    private String directoryPath;
    private Editable ip1;
    private Editable ip2;
    private Editable ip3;
    private Editable ip4;
    private int port;
    private ConnectFTP ConnectFTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edittext = (EditText) findViewById(R.id.editText2);
        setOnKeyListener(edittext);
        setOnFocusChangeListener(edittext);

        final EditText edittext3 = (EditText) findViewById(R.id.editText3);
        setOnKeyListener(edittext3);
        setOnFocusChangeListener(edittext3);

        final EditText edittext4 = (EditText) findViewById(R.id.editText4);
        setOnKeyListener(edittext4);
        setOnFocusChangeListener(edittext4);

        final EditText edittext5 = (EditText) findViewById(R.id.editText5);
        setOnKeyListener(edittext5);
        setOnFocusChangeListener(edittext5);

        final EditText edittext6 = (EditText) findViewById(R.id.editText6);
        setOnKeyListener(edittext6);
        setOnFocusChangeListener(edittext5);

         /*
        new Thread(new Runnable() {
            public void run() {
                String currentPath = ConnectFTP.ftpGetDirectory();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {

                String newFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FTPTest";
                File file = new File(newFilePath);
                file.mkdirs();
                newFilePath += "/image2.jpg";
                try {
                    file = new File(newFilePath);
                    file.createNewFile();
                }catch (Exception e){}

                //Fix
                //ConnectFTP.ftpDownloadFile( + "image2.jpg", newFilePath);
            }
        }).start();
        */
    }

    public void setOnKeyListener(final EditText edittext){
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Toast.makeText(MainActivity.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    if (edittext.equals((EditText) findViewById(R.id.editText2)))
                        ip1 = edittext.getText();
                    else if (edittext.equals((EditText) findViewById(R.id.editText3)))
                        ip2 = edittext.getText();
                    else if (edittext.equals((EditText) findViewById(R.id.editText4)))
                        ip3 = edittext.getText();
                    else if (edittext.equals((EditText) findViewById(R.id.editText5)))
                        ip4 = edittext.getText();
                    else if (edittext.equals((EditText) findViewById(R.id.editText6)))
                        port = Integer.parseInt(edittext.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    public void setOnFocusChangeListener(final EditText edittext){
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(getApplicationContext(), "Got the focus", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    if (edittext.equals((EditText) findViewById(R.id.editText2)))
                        ip1 = edittext.getText();
                    else if (edittext.equals((EditText) findViewById(R.id.editText3)))
                        ip2 = edittext.getText();
                    else if (edittext.equals((EditText) findViewById(R.id.editText4)))
                        ip3 = edittext.getText();
                    else if (edittext.equals((EditText) findViewById(R.id.editText5)))
                        ip4 = edittext.getText();
                    else if (edittext.equals((EditText) findViewById(R.id.editText6)))
                        port = Integer.parseInt(edittext.getText().toString());
                }
            }
        });
    }

    /** Called when the user taps the Send button */
    public void connect(View view) {
        //TEST
        if (ConnectFTP == null)
            ConnectFTP = new ConnectFTP();;

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean status = false;
                final String serverIP;
                serverIP = ip1.toString() + "." + ip2.toString() + "." + ip3.toString() + "." + ip4.toString();
                Log.d(TAG, serverIP);

                //status = ConnectFTP.ftpConnect("211.214.97.80", "jch", "9545", 3721);
                status = ConnectFTP.ftpConnect(serverIP, "jch", "9545", port);
                if(status == true) {
                    Log.d(TAG, "Connection Success");
                    //
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TextView textView = findViewById(R.id.textView2);
                            textView.setText("Connection Success");
                        }
                    });
                    //

                }
                else {
                    Log.d(TAG, "Connection failed");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TextView textView = findViewById(R.id.textView2);
                            textView.setText("Connection failed");
                        }
                    });
                }
            }
        }).start();
        /*
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        */
    }
    public void disconnect(View view) {
        new Thread(new Runnable() {
            public void run() {
                boolean result = ConnectFTP.ftpDisconnect();
                if(result == true){
                    Log.d(TAG, "DisConnection Success");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TextView textView = findViewById(R.id.textView2);
                            textView.setText("DisConnection Success");
                        }
                    });
                }
                else{
                    Log.d(TAG, "DisConnection failed");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TextView textView = findViewById(R.id.textView2);
                            textView.setText("DisConnection failed");
                        }
                    });
                }
            }
        }).start();
    }
    public void getDirectory(View view) {
        new Thread(new Runnable() {
            public void run() {
                directoryPath = ConnectFTP.ftpGetDirectory();
                runOnUiThread(new Runnable() {
                    public void run() {
                        TextView textView = findViewById(R.id.textView2);
                        textView.setText(directoryPath);
                    }
                });
            }
        }).start();
    }
}
