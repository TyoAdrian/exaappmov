package com.ec.examen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgregarEstudianteActivity extends AppCompatActivity {

    private EditText edtNombre, edtNota1, edtNota2, edtPromedio;
    private Button btnGuardarEstudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_estudiante);

        edtNombre = findViewById(R.id.edtNombre);
        edtNota1 = findViewById(R.id.edtNota1);
        edtNota2 = findViewById(R.id.edtNota2);
        edtPromedio = findViewById(R.id.edtPromedio);
        btnGuardarEstudiante = findViewById(R.id.btnGuardarEstudiante);

        // Hacer que el promedio se calcule automáticamente
        edtNota1.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                calcularPromedio();
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });

        edtNota2.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                calcularPromedio();
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });

        btnGuardarEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEstudiante();
            }
        });
    }

    private void calcularPromedio() {
        try {
            double nota1 = Double.parseDouble(edtNota1.getText().toString());
            double nota2 = Double.parseDouble(edtNota2.getText().toString());
            double promedio = (nota1 + nota2) / 2;
            edtPromedio.setText(String.valueOf(promedio));
        } catch (NumberFormatException e) {
            edtPromedio.setText(""); // En caso de error al convertir las notas
        }
    }

    private void guardarEstudiante() {
        try {
            String nombre = edtNombre.getText().toString();
            double nota1 = Double.parseDouble(edtNota1.getText().toString());
            double nota2 = Double.parseDouble(edtNota2.getText().toString());
            double promedio = (nota1 + nota2) / 2;

            Estudiante estudiante = new Estudiante(nombre, promedio);

            // Guarda el estudiante en Firebase
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("estudiantes");
            databaseRef.push().setValue(estudiante);

            Toast.makeText(AgregarEstudianteActivity.this, "Estudiante guardado correctamente", Toast.LENGTH_SHORT).show();
            finish(); // Regresa a la actividad principal
        } catch (Exception e) {
            Log.e("AgregarEstudiante", "Error al guardar el estudiante", e);
            Toast.makeText(AgregarEstudianteActivity.this, "Error al guardar el estudiante", Toast.LENGTH_SHORT).show();
        }
    }
}
