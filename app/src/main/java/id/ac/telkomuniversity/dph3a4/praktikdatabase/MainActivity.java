package id.ac.telkomuniversity.dph3a4.praktikdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText nipEdit, nameEdit;
    Button saveButton, showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nipEdit = findViewById(R.id.editNIP);
        nameEdit = findViewById(R.id.editNama);
        saveButton = findViewById(R.id.btnSimpan);
        showButton = findViewById(R.id.btnTampilkan);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                cleanFields();
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printDataToLog();
            }
        });
    }

    private void saveData() {
        Pegawai p = new Pegawai();
        p.setNip(Integer.valueOf(nipEdit.getText().toString()));
        p.setName(nameEdit.getText().toString());

        DatabasePegawai db = new DatabasePegawai(this);
        db.createPegawai(p);
    }

    private void cleanFields(){
        nipEdit.setText("");
        nameEdit.setText("");
    }

    private void printDataToLog() {
        DatabasePegawai db = new DatabasePegawai(this);
        Log.i("DB", "--- DATA PEGAWAI DI DATABASE ---");

        List<Pegawai> pegawaiList = db.getAllPegawai();
        for (Pegawai p : pegawaiList) {
            Log.i("DB", String.format("Nama Pegawai : %s, NIP : %d",
                    p.getName(), p.getNip()));
        }
    }
}
