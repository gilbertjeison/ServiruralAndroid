package com.meysi.desarrollo.servirural_android;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.LinkedList;
import java.util.prefs.PreferenceChangeEvent;

import ObjetosAccesoDatos.DaoUsuario;
import Session.ManejadorSession;

public class LoginActivity extends AppCompatActivity implements Animation.AnimationListener {

    ImageView img;
    Animation slideDown;
    RelativeLayout layoutLogin;
    EditText txtUsuario, txtContraseña;
    Button btnLogin;
    private static Context mContext;

    DaoUsuario daoUsuario = new DaoUsuario();

    ManejadorSession session;
    String Nombre;
    int codigo_cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();

        codigo_cliente = 0;
        Nombre = "";
        img = (ImageView) findViewById(R.id.imageView);
        img.setBackgroundResource(R.drawable.loading);
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        frameAnimation.start();
        frameAnimation.stop();

        //ANIMACIÓN DE LAYOUT
        layoutLogin = (RelativeLayout)findViewById(R.id.loginLayout);
        slideDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animacion);
        slideDown.setAnimationListener(this);
        layoutLogin.setAnimation(slideDown);

        // ESCONDER TECLADO AL INICIAR ACTIVIDAD
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //INICIALIZACIÓN DE CONTROLES
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContraseña = (EditText) findViewById(R.id.txtContraseña);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = txtUsuario.getText().toString().trim();
                String contrasena = txtContraseña.getText().toString().trim();

                if (CheckDatosLogin(usuario,contrasena))
                {
                    new asyncLogin(LoginActivity.this).execute(usuario,contrasena);
                }
                else
                {
                    ErrorLogin(LoginActivity.this);
                }
            }
        });
    }

    public boolean CheckDatosLogin(String usuario, String contrasena)
    {
        if(usuario.equals("") || contrasena.equals(""))
        {
            Log.e("Error login","Campos vacios, por favor verificar...!");
            return false;
        }
        else
        {
            return true;
        }
    }

    public void ErrorLogin(Context con)
    {
        //Vibrator vibrator = (Vibrator)getSystemService(con.VIBRATOR_SERVICE);
        //vibrator.vibrate(500);
        Toast.makeText(con,"Error iniciando sesión..!",Toast.LENGTH_LONG).show();
    }

    public boolean EstadoLogin(String usuario, String contrasena)
    {
        int estado_login = 0;
        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();

        parametros.add(new BasicNameValuePair("usuario",usuario));
        parametros.add(new BasicNameValuePair("contrasena",contrasena));

        //REALIZAMOS UNA PROPUESTA PARA OBTENER UN JSON
        JSONObject jData = daoUsuario.ObtenerDatosServidor(parametros,DaoUsuario.UrlServer);
        if (jData != null)
        Log.i(LoginActivity.class.getSimpleName(),"Los datos retornados por el servidor: "+jData.toString());

        SystemClock.sleep(500);

        if (jData != null && jData.length() >0 ) {

            JSONObject jsonObject2;
            JSONArray jA;
            try
            {
                jA = jData.getJSONArray("Usuarios");

                jsonObject2 = jA.getJSONObject(0);

                estado_login = jData.getInt("correcto");
                Nombre = jsonObject2.getString("nombre");
                codigo_cliente = Integer.parseInt(jsonObject2.getString("codigo"));

                Log.i(LoginActivity.class.getSimpleName(),"Nombre"+Nombre);
                Log.i(LoginActivity.class.getSimpleName(),"Código: "+codigo_cliente);


                ManejadorSession session = new ManejadorSession(mContext);
                session.LoginSet(codigo_cliente,Nombre);

                if (!daoUsuario.CheckSession(codigo_cliente)) {

                    daoUsuario.CreateSession(codigo_cliente);
                }

                Log.e("estado_login", ""+estado_login);
            }catch (JSONException je)
            {
                je.printStackTrace();
            }

            if (estado_login == 0)
            {
                Log.e("estado_login","Invalido...!");
                return false;
            }
            else
            {
                Log.e("estado_login","Valido...!"+estado_login);
                return true;
            }
        }
        else
        {
            Log.e(LoginActivity.class.getSimpleName(),"Error, el servidor ha retornado NULL...!");
            return false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == slideDown)
        {
            Toast.makeText(getApplicationContext(),
                    "Inicie sesión con su usario y contraseña asignados...!"
                        ,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    class asyncLogin extends AsyncTask<String,String,String>
    {
        String user,pass;
        public ProgressDialog pDialog;
        private LoginActivity logA = new LoginActivity();
        private Context mContext;
        public asyncLogin (Context context){
            mContext = context;
        }
        //DIALOGO
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Autenticando con servidor...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            //OBTENEMOS USUARIO Y CONTRASEÑA
            user = params[0];
            pass = params[1];

            //ENVIAMOS, RECIBIMOS Y ANALIZAMOS LOS DATOS EN UN SEGUNDO CLIENTE
            if (logA.EstadoLogin(user, pass))
            {
                return "OK";
            }
            else
            {
                return "NO";
            }
        }

        protected void onPostExecute(String resultado)
        {
            pDialog.dismiss();
            Log.e("onPostExecute",""+resultado);

            if(resultado.equals("OK"))
            {
                //GUARDAR USUARIO EN PREFERENCIAS
                Intent principal = new Intent(mContext,MenuActivity.class);
                principal.putExtra("usuario",user);
                mContext.startActivity(principal);
                finish();
            }
            else
            {
                logA.ErrorLogin(mContext);
            }
        }
    }
}
