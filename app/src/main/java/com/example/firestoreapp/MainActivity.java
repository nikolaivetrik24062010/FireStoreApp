package com.example.firestoreapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private Button saveBTN, updateBTN, readBTN, deleteBTN;
    private EditText nameET, emailET;
    private TextView text;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private DocumentReference friendDocumentReference =
            firebaseFirestore.collection("Users").document("wOEOT8fHaTx80YHFO58N");
    private CollectionReference collectionReference = firebaseFirestore.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        saveBTN = findViewById(R.id.SaveBTN);
        updateBTN = findViewById(R.id.updateBTN);
        readBTN = findViewById(R.id.readBTN);
        deleteBTN = findViewById(R.id.deleteBTN);
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        text = findViewById(R.id.text);

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDataToNewDocument();
            }
        });

        readBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAllDocumentsIncludeCollection();
            }
        });

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSpecificDocument();
            }
        });

        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void SaveDataToNewDocument() {
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();

        Friend friend = new Friend(name, email);
        collectionReference.add(friend).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String documentId = documentReference.getId();
            }
        });
    }

    private void GetAllDocumentsIncludeCollection() {
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";
                //This code will be executed when the query is successful
                //The queryDocumentSnapshots object contains all the documents returned by the query and represent a document in the collection
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    Friend friend = snapshot.toObject(Friend.class);
                    data += "Name: " + friend.getName() + "Email: " + friend.getEmail() + "\n";
                }
                text.setText(data);
            }
        });
    }

    private void UpdateSpecificDocument() {
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();

        friendDocumentReference.update("name", name);
        friendDocumentReference.update("email", email);
    }

    private void DeleteAll() {
        friendDocumentReference.delete();
    }
}