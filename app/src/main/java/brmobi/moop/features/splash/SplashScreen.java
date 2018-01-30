package brmobi.moop.features.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import brmobi.moop.features.MoopActivity;
import brmobi.moop.features.login.LoginActivity;
import brmobi.moop.model.singleton.UsuarioSingleton;
import brmobi.moop.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UsuarioSingleton.I.isUsuarioLogado(SplashScreen.this)) {
                    startActivity(new Intent(SplashScreen.this, MoopActivity.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                }
                finish();
            }
        }, 1500);
    }
}
