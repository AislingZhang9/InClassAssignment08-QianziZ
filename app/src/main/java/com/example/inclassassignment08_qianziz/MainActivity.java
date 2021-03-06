package com.example.inclassassignment08_qianziz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText keyField;
    private EditText valueField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("FirebasePractice01");

        keyField = (EditText) findViewById(R.id.key_edit_field);
        valueField = (EditText) findViewById(R.id.value_edit_field);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Messages");
    }

    public void writeToCloud(View view) {
        myRef.child(keyField.getText().toString()).setValue(valueField.getText().toString());
    }

    public void readFromCloud(View view) {
        DatabaseReference childRef = myRef.child(keyField.getText().toString());
        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String loadedData = dataSnapshot.getValue(String.class);
                    valueField.setText(loadedData);
                } else {
                    valueField.setText("");
                    Toast.makeText(MainActivity.this, "Cannot find key", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
