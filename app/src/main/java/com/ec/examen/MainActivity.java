package com.ec.examen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private DatabaseReference databaseRef;
    private Button btnAgregarEstudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout);
        btnAgregarEstudiante = findViewById(R.id.btnAgregarEstudiante);
        databaseRef = FirebaseDatabase.getInstance().getReference("estudiantes");

        // Cargar los estudiantes en la tabla
        cargarEstudiantes();

        // Ir a la pantalla de agregar estudiante
        btnAgregarEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica si el contexto de la actividad es correcto
                try {
                    // Abre la segunda actividad para agregar un estudiante
                    startActivity(new Intent(MainActivity.this, AgregarEstudianteActivity.class));
                } catch (Exception e) {
                    // Mostrar un mensaje en Logcat en caso de error
                    Log.e("MainActivity", "Error al abrir la pantalla de agregar estudiante", e);
                }
            }
        });

    }

    private void cargarEstudiantes() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Limpiar la tabla antes de cargar los nuevos datos
                tableLayout.removeViewsInLayout(1, tableLayout.getChildCount() - 1);

                // Recorrer los estudiantes en Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child("nombre").getValue(String.class);
                    Double promedio = snapshot.child("promedio").getValue(Double.class);

                    // Si el nombre o el promedio no son null, agregar una fila a la tabla
                    if (nombre != null && promedio != null) {
                        TableRow row = new TableRow(MainActivity.this);
                        TextView nombreText = new TextView(MainActivity.this);
                        nombreText.setText(nombre);
                        nombreText.setPadding(16, 16, 16, 16);
                        row.addView(nombreText);

                        TextView promedioText = new TextView(MainActivity.this);
                        promedioText.setText(String.format("%.2f", promedio));
                        promedioText.setPadding(16, 16, 16, 16);
                        row.addView(promedioText);

                        tableLayout.addView(row);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejo de error
            }
        });
    }
}
