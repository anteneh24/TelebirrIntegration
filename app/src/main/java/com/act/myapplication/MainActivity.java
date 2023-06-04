package com.act.myapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.act.myapplication.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import android.view.Menu;
import android.view.MenuItem;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;

import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);

        Ussd ussd = new Ussd();
        ussd.setAppId("");
        ussd.setNonce("");
        ussd.setNotifyUrl("");
        ussd.setOutTradeNo("");
        ussd.setReceiveName("");
        ussd.setReturnUrl("");
        ussd.setTimestamp((new Date().getTime())+"");
        ussd.setTotalAmount("123.21");
        Request request = new Request();
        request.setAppid(ussd.getAppId());
        request.setSign("");
        String ussdRequest = new Gson().toJson(ussd);
        String publicKeyValue = "";
        byte[] encodedPublicKey = publicKeyValue.getBytes();
        byte[] plaintext = new byte[0];
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            Key publicKey = keyFactory.generatePublic(publicKeySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            plaintext = ussdRequest.getBytes("UTF-8");
            byte[] ciphertext = cipher.doFinal(plaintext);
            request.setUssd(ciphertext.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        String toBeSend = new Gson().toJson(request);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}