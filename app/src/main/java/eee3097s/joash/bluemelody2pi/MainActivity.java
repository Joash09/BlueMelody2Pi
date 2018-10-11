package eee3097s.joash.bluemelody2pi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView songList;
    ArrayAdapter<String> songAdapter;
    int selection=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList = findViewById(R.id.listSong);
        songList.setClickable(true);
        songAdapter = new ArrayAdapter<String>(this, R.layout.song_selection_layout, R.id.txtSong, DataInfo.songNames);
        songList.setAdapter(songAdapter);
        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView lblStatus = findViewById(R.id.lblStatus);
                selection = position;
                lblStatus.setText(getString(R.string.strSelectedSong)+" "+(position+1));
            }
        });
    }

    public void connect(View view){
        String dataInput = DataInfo.songData[selection];
        BluetoothObject bluetoothObject = new BluetoothObject(this);
        bluetoothObject.connect(dataInput);
    }

    public void composeMode(View view){
        this.startActivity(new Intent(MainActivity.this, ComposeModeActivity.class));
    }
}
