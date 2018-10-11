package eee3097s.joash.bluemelody2pi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class ComposeModeActivity extends AppCompatActivity {

    static char [] notes = {'C', 'c', 'D', 'd', 'E', 'F', 'f', 'G', 'g', 'A', 'a','B'};

    private class SkBarListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            switch (seekBar.getId()){
                case 2131165312://Note
                    TextView lblNotes = findViewById(R.id.lblNotes);
                    lblNotes.setText(getString(R.string.strNotes)+"\t\t"+notes[i]);
                    break;
                case 2131165313://Octave
                    TextView lblOctave = findViewById(R.id.lblOctave);
                    lblOctave.setText(getString(R.string.strOctave)+"\t\t"+i);
                    break;
                case 2131165311://Duration
                    TextView lblDuration = findViewById(R.id.lblDuration);
                    lblDuration.setText(getString(R.string.strDuration)+"\t\t"+i);
                    break;
                case 2131165314://Volume
                    TextView lblVolume = findViewById(R.id.lblVolume);
                    lblVolume.setText(getString(R.string.strVolume)+"\t\t"+i);
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mode);

        SeekBar skbarNotes = findViewById(R.id.skbarNotes);
        SeekBar skbarOctave = findViewById(R.id.skbarOctave);
        SeekBar skbarDuration = findViewById(R.id.skbarDuration);
        SeekBar skbarVolume = findViewById(R.id.skbarVolume);

        skbarNotes.setOnSeekBarChangeListener(new SkBarListener());
        skbarOctave.setOnSeekBarChangeListener(new SkBarListener());
        skbarDuration.setOnSeekBarChangeListener(new SkBarListener());
        skbarVolume.setOnSeekBarChangeListener(new SkBarListener());
    }

    public void connect(View view){
        TextView txtInput = findViewById(R.id.txtInput);
        String dataInput = txtInput.getText().toString();
        if (!dataInput.equals("")){
            BluetoothObject bluetoothObject = new BluetoothObject(this);
            bluetoothObject.connect(dataInput);
        }
    }

    public void clearText(View view){
        TextView txtInput = findViewById(R.id.txtInput);
        txtInput.setText("");
    }

    public void addNote(View view){
        //Get the values on screen
        SeekBar skbarNotes = findViewById(R.id.skbarNotes);
        SeekBar skbarOctave = findViewById(R.id.skbarOctave);
        SeekBar skbarDuration = findViewById(R.id.skbarDuration);
        SeekBar skbarVolume = findViewById(R.id.skbarVolume);

        String note = ""+notes[skbarNotes.getProgress()];
        String octave = ""+skbarOctave.getProgress();
        String duration = ""+skbarDuration.getProgress();
        String volume = ""+skbarVolume.getProgress();
        String newNote = note+octave+duration+volume;

        //Set values
        TextView txtInput = findViewById(R.id.txtInput);
        txtInput.setText(txtInput.getText().toString()+newNote);
    }
}
