package eee3097s.joash.bluemelody2pi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ComposeModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mode);
    }

    public void connect(View view){
        EditText txtInput = findViewById(R.id.txtInput);
        String dataInput = txtInput.getText().toString();
        if (dataInput != ""){
            BluetoothObject bluetoothObject = new BluetoothObject(this);
            bluetoothObject.connect(dataInput);
        }
    }

    public void clearText(View view){
        EditText txtInput = findViewById(R.id.txtInput);
        txtInput.setText("");
    }
}
