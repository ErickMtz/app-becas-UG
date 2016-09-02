package com.jamer.jaimeloyola.becas.Registro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jamer.jaimeloyola.becas.R;
import com.jamer.jaimeloyola.becas.UI.MainActivity;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Jaime Loyola on 16/6/2016. :v
 *
 * In this class checkLogin() method verifies the login details on the server
 */
public class LoginActivity extends Activity {
    private Button btnLogin;
    private EditText inputEmail;
    private EditText inputPassword;

    HttpURLConnection con;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            /*
            Comprobar disponibilidad de la red
             */

            String user = inputEmail.getText().toString();
            String dataURL = "http://148.214.194.234:8080/MailServices/webresources/users/findbyuemail/" + user;

            try {
                ConnectivityManager conMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()) {
                    new JsonTask().
                            execute(new URL(dataURL));
                }
                else {
                    Toast.makeText(getBaseContext(), "Error de conexi?n", Toast.LENGTH_LONG).show();
                }
            } catch(MalformedURLException e) {
                e.printStackTrace();
            }
        }
        });
    }

    public class JsonTask extends AsyncTask<URL, Void, List<User>> {
        @Override
        protected List<User> doInBackground(URL... urls) {
            List<User> user = null;

            try {
                //Establecer conexi?n
                con = (HttpURLConnection) urls[0].openConnection();
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);

                //Parsear el flujo con formato JSON
                InputStream in = new BufferedInputStream(con.getInputStream());
                JsonUserParser parser = new JsonUserParser();
                //GsonUserParser parser = new GsonUserPArser();

                user = parser.leerFlujoJson(in);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
            return user;
        }

        @Override
        protected void onPostExecute(List<User> user) {
            /*
           Verificar datos de usuario
             */
            String passHappy = inputPassword.getText().toString();
            String superSecure = user.get(0).getPassword();

            if(user!=null) {
                if(superSecure.equals(md5(passHappy))) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(
                            getBaseContext(),
                            "Datos incorrectos, verifica e intentalo de nuevo.",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }else{
                Toast.makeText(
                        getBaseContext(),
                        "Error de Parsing Json",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public static final String md5(final String pass) {
        final String MD5 = "MD5";

        try {
            //Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(pass.getBytes());
            byte messageDigest[] = digest.digest();

            //Create Hex String
            StringBuilder hexString = new StringBuilder();
            for(byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while(h.length() < 2)
                    h = "0"+ h;
                hexString.append(h);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
