package com.google.todoappfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class AddTaskActivity extends AppCompatActivity {
    EditText title, description;
    TextView start, end;
    Button btnStart, btnEnd, btnSave;

    Integer num = new Random().nextInt();
    String keyNew = Integer.toString(num);

    DatabaseReference reference;
    boolean isUpdate;
    String keyTask;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Calendar calendar = Calendar.getInstance();
        int selectedYear = calendar.get(Calendar.YEAR);
        int selectedMonth = calendar.get(Calendar.MONTH);
        int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        Intent intent = getIntent();
        isUpdate = intent.getBooleanExtra("isUpdate", false);

        title = (EditText) findViewById(R.id.editTextTitle);
        start = (TextView) findViewById(R.id.editTextStart);
        end = (TextView) findViewById(R.id.editTextEnd);
        description = (EditText) findViewById(R.id.editTextDescription);
        btnStart = (Button) findViewById(R.id.buttonStart);
        btnEnd = (Button) findViewById(R.id.buttonEnd);
        btnSave = (Button) findViewById(R.id.buttonSave);

        if (isUpdate) {
            keyTask = intent.getStringExtra("keyTask");

            task = new Task(intent.getStringExtra("titleTask"),
                    intent.getStringExtra("startTask"),
                    intent.getStringExtra("endTask"),
                    intent.getStringExtra("descriptionTask"));

            title.setText(task.getTitle());
            start.setText(task.getStart());
            end.setText(task.getEnd());
            description.setText(task.getDescription());
        } else findViewById(R.id.buttonDelete).setVisibility(View.GONE);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        start.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                        dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
                datePickerDialog.show();
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        end.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                        dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
                datePickerDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().equals("") || start.getText().toString().equals("") || end.getText().toString().equals(""))
                    Toast.makeText(AddTaskActivity.this, "Please fill in the blanks", Toast.LENGTH_SHORT).show();
                else {
                    if (isUpdate) {
                        reference = FirebaseDatabase.getInstance().getReference().child("Todo Tasks").child("Task" + keyTask);
                        reference.child("Title").setValue(title.getText().toString());
                        reference.child("Start").setValue(start.getText().toString());
                        reference.child("End").setValue(end.getText().toString());
                        reference.child("Description").setValue(description.getText().toString());
                        reference.child("Key").setValue(keyTask);
                        Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Todo Tasks").child("Task" + keyNew);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.getRef().child("Title").setValue(title.getText().toString());
                                snapshot.getRef().child("Start").setValue(start.getText().toString());
                                snapshot.getRef().child("End").setValue(end.getText().toString());
                                snapshot.getRef().child("Description").setValue(description.getText().toString());
                                snapshot.getRef().child("Key").setValue(keyNew);
                                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });

        findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference().child("Todo Tasks").child("Task" + keyTask);
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}