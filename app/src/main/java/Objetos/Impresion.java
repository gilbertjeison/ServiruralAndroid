package Objetos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import Session.ManejadorSession;

/**
 * Created by Desarrollo on 14/12/2015.
 */
public class Impresion {

    Context mCOntext;
    //ELEMENTOS IMPRESION
    //BLUETOOTH CONTROLS
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    String IMPRESORA_MAC;
    Thread tr;
    Activity activity_ppl;
    String data_;


    //LECTURA DE ARCHIVOS
    static OutputStream mOutPutStream;
    InputStream mInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;



    public Impresion(Context _context,Activity activity)
    {
        mCOntext = _context;
        activity_ppl = activity;
    }

    public void Imprimir(String data) throws IOException {
        try {
            ManejadorSession session = new ManejadorSession(mCOntext);
            IMPRESORA_MAC = session.GetPrinterMac();

            //PROCESO PARA IMPRIMIR
            EncontrarImpresora();
            AbrirBluetooth();
            data_ = data;
            Log.e("Factura a imprimir", "" + data);
            mOutPutStream.write(data.getBytes());
            closeBT();
        }catch (Exception e){e.printStackTrace();}
    }

    //METODO PARA ENCONTRAR LA IMPRESORA
    //EN LA LISTA DE DISPOSITIVOS GUARDADOS
    private void EncontrarImpresora()
    {
        try
        {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null)
            {
                Toast.makeText(mCOntext, "No hay adaptador de bluetooth disponible", Toast.LENGTH_LONG).show();
            }
            if(!mBluetoothAdapter.isEnabled())
            {
                Toast.makeText(mCOntext, "Por favor encienda el bluetooth para poder imprimir", Toast.LENGTH_LONG).show();
            }

            Set<BluetoothDevice> pariedDevices = mBluetoothAdapter.getBondedDevices();
            if (pariedDevices.size()>0)
            {
                for (BluetoothDevice device : pariedDevices)
                {
                    if (device.getAddress().equals(IMPRESORA_MAC))
                    {
//                       Log.i("Tamaño: ",""+device.getName());
                        mBluetoothDevice = device;
                        break;
                    }
                }
            }
        }
        catch (NullPointerException np) {
            np.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //ABRIR COMUNICACIÓN CON IMPRESORA SELECCIONADA
    private void AbrirBluetooth()
    {
        try
        {
            // Standard SerialPortService ID
            UUID uuid =   UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mBluetoothSocket = mBluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);
//            if (!mBluetoothSocket.isConnected())
//            {
//                mBluetoothSocket.connect();
//            }



            try {
//                mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            } catch (Exception e) {
                Log.e("", "Error creating socket");}

            try {
                mBluetoothSocket.connect();
                Log.e("","Connected");
            } catch (IOException e) {
                Log.e("",e.getMessage());
                try {
                    Log.e("","trying fallback...");

                    mBluetoothSocket =(BluetoothSocket) mBluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(mBluetoothDevice,1);
                    mBluetoothSocket.connect();

                    Log.e("","Connected");
                }
                catch (Exception e2) {
                    Log.e("", "Couldn't establish Bluetooth connection!");
                }
            }


            mOutPutStream = mBluetoothSocket.getOutputStream();
            mInputStream = mBluetoothSocket.getInputStream();

            BeginListenForData();

            Toast.makeText(mCOntext,"Conexión abierta con dispositivo !",Toast.LENGTH_LONG).show();
        }
        catch (NullPointerException np) {
            np.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //ENVIAR COMANDO DE DATOS A LA IMPRESORA
    //(IMPRESION DEL DOCUMENTO)
    private void BeginListenForData() throws InterruptedException {
        final Handler handler = new Handler();
        final ProgressDialog pDialog = new ProgressDialog(activity_ppl);
        try
        {
            //CODIGO ASCCI PARA NUEVA LINEA
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition =0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            pDialog.setMessage("Imprimiendo...");
                            pDialog.setCancelable(true);
                            pDialog.show();
                         }
                     });


                    while (!Thread.currentThread().isInterrupted() && stopWorker)
                    {
                        try
                        {
                            int bytesAvailable = mInputStream.available();
                            if (bytesAvailable>0)
                            {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mInputStream.read(packetBytes);

                                for(int i =0;i < bytesAvailable; i++)
                                {
                                    Log.i("Camellando con: ","..."+i);
                                    byte b = packetBytes[i];
                                    if (b== delimiter)
                                    {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer,0,encodedBytes,0,encodedBytes.length);
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(mCOntext,data,Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                    else
                                    {
                                        readBuffer[readBufferPosition++] = b;
                                    }

                                }
                            }
                        }catch (IOException ex)
                        {
                            stopWorker = true;
                        }
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            pDialog.dismiss();
                        }
                    });
                }
            });
        }
        catch (NullPointerException np) {
            np.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


        workerThread.start();
    }

    //CERRAR CONEXIÓN CON DISPOSITIVO
    void closeBT() throws IOException {
        try {
            stopWorker = true;
            mOutPutStream.close();
            mInputStream.close();
            mBluetoothSocket.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
