package osmandroid.venturesity.helloworld3o;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import spencerstudios.com.bungeelib.Bungee;

public class ChatActivity extends AppCompatActivity {


    private static final String TAG = "TAG";
    RecyclerView recyclerView;
    EditText editText;
    ImageView sendButton;

    FirebaseAuth mAuth;
    DatabaseReference ref;

    CustomAdapter adapter;
    List<ChatModel> chatModelList;

    MyProgressDialog myProgressDialog;

    @NonNull
    protected static final Query sChatQuery =
            FirebaseDatabase.getInstance().getReference().child("chats").limitToLast(50);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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

        chatModelList = new ArrayList<>();

        adapter = new CustomAdapter(this, chatModelList, Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        //ref.keepSynced(true);

        ref.child("chat").addChildEventListener(new ChildEventListener() {
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

        final AIConfiguration config = new AIConfiguration("7597677cd3eb4de789abb14778816d2d",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        //aiService = AIService.getService(this, config);
        //aiService.setListener(this);

        final AIDataService aiDataService = new AIDataService(this, config);

        final AIRequest aiRequest = new AIRequest();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                String message = editText.getText().toString().trim();

                if (!message.equals("")) {

                    ChatModel chatMessage = new ChatModel(message, "user");
                    ref.child("chat").push().setValue(chatMessage);

                    aiRequest.setQuery(message);
                    new AsyncTask<AIRequest, Void, AIResponse>() {

                        @Override
                        protected AIResponse doInBackground(AIRequest... aiRequests) {
                            final AIRequest request = aiRequests[0];
                            try {
                                return aiDataService.request(aiRequest);
                            } catch (AIServiceException e) {
                                Log.d(TAG, "doInBackground: Error:" + e.toString());
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(AIResponse response) {
                            if (response != null) {

                                Result result = response.getResult();
                                String reply = result.getFulfillment().getSpeech();
                                ChatModel chatMessage = new ChatModel(reply, "doctor");
                                Log.d(TAG, "onPostExecute: " + chatMessage);
                                ref.child("chat").push().setValue(chatMessage);
                            }
                        }
                    }.execute(aiRequest);
                }


                editText.setText("");


            }
        });


        recyclerView.setAdapter(adapter);


    }


    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(Objects.requireNonNull(this.getCurrentFocus()).getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bungee.fade(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}





