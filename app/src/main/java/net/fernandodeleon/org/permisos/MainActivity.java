package net.fernandodeleon.org.permisos;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_PERMISO = 1;
    private static final int CODIGO_SOLICITAR_PERMISO_BLUETOOTH = 0;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = this;
    }

    public void HabilitarBluetooth(View v){
        solicitarPermiso();
        BluetoothAdapter adapterBluetooth = BluetoothAdapter.getDefaultAdapter();
        if(adapterBluetooth  == null){
            Toast.makeText(MainActivity.this, "Este celular no tiene bluetooth", Toast.LENGTH_SHORT).show();
        }

        if(!adapterBluetooth.isEnabled()){
            Intent habilitarBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(habilitarBluetoothIntent, CODIGO_SOLICITAR_PERMISO_BLUETOOTH);
        }
    }

    public boolean revisarPermiso(){
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        if(resultado == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    public void solicitarPermiso(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)){
            Toast.makeText(MainActivity.this, "Acceso permitido", Toast.LENGTH_LONG).show();
        }else{
            //****aca puedo enviar un array de permisos, y gestionarlos por medio de la variable CODIGO_PERMISO
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH}, CODIGO_PERMISO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CODIGO_PERMISO:
                if(revisarPermiso()){
                    Toast.makeText(MainActivity.this, "Ya esta listo el permiso para bluetooth", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "NO esta listo el permiso para bluetooth", Toast.LENGTH_SHORT).show();
                }
            break;
            //***otros case
        }
    }
}
