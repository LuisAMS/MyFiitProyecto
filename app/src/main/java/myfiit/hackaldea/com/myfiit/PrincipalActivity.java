package myfiit.hackaldea.com.myfiit;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PrincipalActivity extends AppCompatActivity {

    public static final String TAG = "LEDv0";
    public static final boolean D = true;

    // Tipos de mensaje enviados y recibidos desde el Handler de ClaseBluetooth
    public static final int Mensaje_Estado_Cambiado = 1;
    public static final int Mensaje_Leido = 2;
    public static final int Mensaje_Escrito = 3;
    public static final int Mensaje_Nombre_Dispositivo = 4;
    public static final int Mensaje_TOAST = 5;
    public static final int MESSAGE_Desconectado = 6;
    public static final int REQUEST_ENABLE_BT = 7;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    //Nombre del dispositivo conectado
    private String mConnectedDeviceName = null;

    // Adaptador local Bluetooth
    private BluetoothAdapter AdaptadorBT = null;

    //Objeto miembro para el servicio de ClaseBluetooth
    private ClaseBluetooth Servicio_BT = null;

    //Vibrador
    private Vibrator vibrador;

    //variables para el Menu de conexion
    private boolean seleccionador=false;

    TextView tv,mensajemonito;
    ImageView monitoimage,globito;
    AnimationDrawable happyAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        tv = (TextView)findViewById(R.id.tvPrincipal);
        mensajemonito = (TextView)findViewById(R.id.txv_char_msj);

        monitoimage = (ImageView) findViewById(R.id.imageView2);
        globito  = (ImageView) findViewById(R.id.imageView3);
        globito.setVisibility(View.INVISIBLE);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("MYCHARACTER");

            if(j.equals("monito")) {
                monitoimage.setImageResource(R.drawable.personaje1);
            }else{
                monitoimage.setImageResource(R.drawable.personaje2);

            }
        }


        // final ToggleButton BotonOnOffBluetooth = (ToggleButton)findViewById(R.id.toggleButton1); //nuestroboton
        final ImageButton BotonOnOffBluetooth = (ImageButton)findViewById(R.id.imageButton3);
        //******* para encender bluetooth *******//
        BotonOnOffBluetooth.setOnClickListener(new View.OnClickListener() {

            public void onClick(View vv) {

               // if(BotonOnOffBluetooth.is())     {

                    String address = "98:D3:31:80:3F:EE"; //Direccion Mac del  modulo bluetooth
                    BluetoothDevice device = AdaptadorBT.getRemoteDevice(address);
                    Servicio_BT.connect(device);
                //}
               /* else {
                    if (Servicio_BT != null) Servicio_BT.stop();//Detenemos servicio
                }*/
            }
        }); //fin de metodo de BotonOnOff

        final ImageButton botonStats = (ImageButton)findViewById(R.id.btnStats);
        botonStats.setOnClickListener(new View.OnClickListener() {

            public void onClick(View vv) {

                Intent i = new Intent(getBaseContext(), StatsActivity.class);
                startActivity(i);
            }
        }); //fin de metodo de BotonOnOff

    }

    public  void onStart() {
        super.onStart();
        ConfigBT();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (Servicio_BT != null) Servicio_BT.stop();//Detenemos servicio
    }

    public void ConfigBT(){
        // Obtenemos el adaptador de bluetooth
        AdaptadorBT = BluetoothAdapter.getDefaultAdapter();
        if (AdaptadorBT.isEnabled()) {//Si el BT esta encendido,
            if (Servicio_BT == null) {//y el Servicio_BT es nulo, invocamos el Servicio_BT
                Servicio_BT = new ClaseBluetooth(this, mHandler);
            }
        }
        else{ if(D) Log.e("Setup", "Bluetooth apagado...");
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Una vez que se ha realizado una actividad regresa un "resultado"...
        switch (requestCode) {

            case REQUEST_ENABLE_BT://Respuesta de intento de encendido de BT
                if (resultCode == Activity.RESULT_OK) {//BT esta activado,iniciamos servicio
                    ConfigBT();
                } else {//No se activo BT, salimos de la app
                    finish();
                }
        }//fin de switch case
    }//fin de onActivityResult

    public  void sendMessage(String message) {
        if (Servicio_BT.getState() == ClaseBluetooth.STATE_CONNECTED) {//checa si estamos conectados a BT
            if (message.length() > 0) {   // checa si hay algo que enviar
                byte[] send = message.getBytes();//Obtenemos bytes del mensaje
                if(D) Log.e(TAG, "Mensaje enviado:"+ message);
                Servicio_BT.write(send);     //Mandamos a escribir el mensaje
            }
        } else Toast.makeText(this, "No conectado", Toast.LENGTH_SHORT).show();
    }//fin de sendMessage

    final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Mensaje_Escrito:
                    byte[] writeBuf = (byte[]) msg.obj; //buffer de escritura...
                    // Construye un String del Buffer
                    String writeMessage = new String(writeBuf);
                    if(D) Log.e(TAG, "Message_write  =w= "+ writeMessage);
                    break;

                case Mensaje_Leido:
                    byte[] readBuf = (byte[]) msg.obj;//buffer de lectura...
                    //Construye un String de los bytes validos en el buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    if(D) tv.setText("Data recibido: "+readMessage); Log.e(TAG, "Message_read   =w= "+ readMessage);

                    if (globito.getVisibility() == View.INVISIBLE) {
                        globito.setVisibility(View.VISIBLE);
                    }

                    if (readMessage.equals("a")){
                        mensajemonito.setText(":c");
                    }else if (readMessage.equals("b")){
                        mensajemonito.setText(":/");
                    }else if (readMessage.equals("c")){
                        mensajemonito.setText(":)");
                    }

                    break;

                case Mensaje_Nombre_Dispositivo:
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME); //Guardamos nombre del dispositivo
                    Toast.makeText(getApplicationContext(), "Conectado con "+ mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    seleccionador=true;
                    break;

                case Mensaje_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;

                case MESSAGE_Desconectado:
                    if(D) Log.e("Conexion","DESConectados");
                    seleccionador=false;
                    break;

            }//FIN DE SWITCH CASE DEL HANDLER
        }//FIN DE METODO INTERNO handleMessage
    };//Fin de Handler

}
