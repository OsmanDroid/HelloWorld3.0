package osmandroid.venturesity.helloworld3o;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiveDoctor extends AppCompatActivity {

    private static final String TAG = "TAG";
    RecyclerView recyclerView;
    EditText editText;
    ImageView sendButton;

    FirebaseAuth mAuth;
    DatabaseReference ref;

    CustomAdapter adapter;
    List<ChatModel> chatModelList;

    MyProgressDialog myProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)actionBar.setTitle("Online Live Consultation");

        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.send_btn);

        myProgressDialog = new MyProgressDialog(this);
        myProgressDialog.showPDiialog();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        mAuth = FirebaseAuth.getInstance();

        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child("FrzHCwQxgjZYhTB0dZ5OLd13hf22");
        userRef.child("patientUID").setValue(uid);

        chatModelList = new ArrayList<>();

        adapter = new CustomAdapter(this, chatModelList, Objects.requireNonNull(mAuth.getCurrentUser()).getUid(),2);

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        ref.child("liveD").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatModel model = dataSnapshot.getValue(ChatModel.class);
                Log.d(TAG, "onChildAdded: " + model.msgUser);
                Log.d(TAG, "onChildAdded: " + model.msgText);

                chatModelList.add(model);
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatModelList.size() - 1);

                myProgressDialog.dismissPDialog();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("liveD").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    // The child doesn't exist
                    myProgressDialog.dismissPDialog();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();

                if (!message.equals("")) {

                    ChatModel chatMessage = new ChatModel(message, "user");
                    ref.child("liveD").push().setValue(chatMessage);
                }
                editText.setText("");
            }
        });

        recyclerView.setAdapter(adapter);
    }
}
