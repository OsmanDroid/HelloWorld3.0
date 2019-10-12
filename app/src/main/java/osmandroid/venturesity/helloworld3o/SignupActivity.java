package osmandroid.venturesity.helloworld3o;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText nameET, emailET, passwordET, ageEt, weightET, otherdetailsET;
    Button signup,signin,forgot;

    FirebaseAuth mAuth;

    MyProgressDialog progressDialog;

    private final static String TAG = "TAG";

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();



        nameET = findViewById(R.id.pname_et);
        emailET = findViewById(R.id.emailet);
        passwordET = findViewById(R.id.passwordEt);
        ageEt = findViewById(R.id.age_et);
        weightET = findViewById(R.id.weight_et);
        otherdetailsET = findViewById(R.id.otherDetails_et);

        signup = findViewById(R.id.sign_up_button);
        signin = findViewById(R.id.alreadyReg);
        forgot = findViewById(R.id.btn_reset_password);

        progressDialog = new MyProgressDialog(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.name = nameET.getText().toString().trim();
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                user.age = ageEt.getText().toString().trim();
                user.weight = weightET.getText().toString().trim();
                user.otherd = otherdetailsET.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    emailET.setError("Enter Email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordET.setError("Enter Password");
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                setSignup(email,password,user);

            }
        });

    }

    void setSignup(String email, String password, final User user)
    {
        progressDialog.showPDiialog();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            uploadDetails(user);
                            progressDialog.dismissPDialog();
                            updateUI(firebaseUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed: "+ task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            progressDialog.dismissPDialog();
                        }

                        // ...
                    }
                });
    }

    private void uploadDetails(User user) {
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = myRef.child("Users").child(uid);
        userRef.child("name").setValue(user.name);
        userRef.child("age").setValue(user.age);
        userRef.child("weight").setValue(user.weight);
        userRef.child("otherd").setValue(user.otherd);

        userRef.child("chat").child("initial").child("msgUser").setValue("doctor");
        userRef.child("chat").child("initial").child("msgText").setValue("Hi, I'm your Health Assistant.\nHow may I help you?");

    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

}
