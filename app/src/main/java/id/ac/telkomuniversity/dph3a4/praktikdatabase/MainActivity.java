package id.ac.telkomuniversity.dph3a4.praktikdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText nipEdit, nameEdit;
    Button saveButton, showButton;
    TextView dateText;
    ImageButton btnDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nipEdit = findViewById(R.id.editNIP);
        nameEdit = findViewById(R.id.editNama);
        saveButton = findViewById(R.id.btnSimpan);
        showButton = findViewById(R.id.btnTampilkan);

        dateText = findViewById(R.id.date_text);
        btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

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

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month+1) + "/" + year;
        dateText.setText(date);
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
