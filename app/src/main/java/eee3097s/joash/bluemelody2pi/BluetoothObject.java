package eee3097s.joash.bluemelody2pi;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BluetoothObject {

    BluetoothAdapter mBluetoothAdapter;
    Activity activity;

    public BluetoothObject(Activity activity){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.activity = activity;
    }

    private BluetoothDevice findDevice(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                if (deviceName.equals(BluetoothInfo.getNAME())){
                    return device;
                }
            }
        }
        return null;
    }

    public void connect(String dataInput){
        if (!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, BluetoothInfo.REQUEST_ENABLE_BT);
        }

        if (mBluetoothAdapter.isEnabled()){
            ConnectThread thread = new ConnectThread(findDevice(), dataInput);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Data has been sent
            System.out.println("Data has been sent");
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private OutputStream mmOutStream;
        private String dataInput;

        public ConnectThread(BluetoothDevice device, String dataInput) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;
            this.dataInput = dataInput;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = mmDevice.createRfcommSocketToServiceRecord(BluetoothInfo.getMyUuid());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmSocket=tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();

                /*------------------------------------------------------------------*/
                OutputStream tmpOut = null;

                try {
                    tmpOut = mmSocket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mmOutStream = tmpOut;

                try {
                    byte [] temp = dataInput.getBytes();
                    mmOutStream.write(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*------------------------------------------------------------------*/
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    closeException.printStackTrace();
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            //manageMyConnectedSocket(mmSocket);
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
