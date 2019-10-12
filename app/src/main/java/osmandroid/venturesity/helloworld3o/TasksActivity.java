package osmandroid.venturesity.helloworld3o;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TasksActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    RecyclerView recyclerView;

    private List<String> titleList = new ArrayList<>();
    private List<Boolean> checkedList = new ArrayList<>();

    TasksAdapter adapter;

    Button day1,day2,day3,day4,day5,day6,day7;


    FirebaseAuth mAuth;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        recyclerView = findViewById(R.id.recyclerView);

        day1 = findViewById(R.id.button1);
        day2 = findViewById(R.id.button2);
        day3 = findViewById(R.id.button3);
        day4 = findViewById(R.id.button4);
        day5 = findViewById(R.id.button5);
        day6 = findViewById(R.id.button6);
        day7 = findViewById(R.id.button7);

        mAuth = FirebaseAuth.getInstance();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        addTestData();
        adapter = new TasksAdapter(titleList,checkedList);

        recyclerView.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        ref.child("Task").child("day1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    DailyScoreModel  dailyScoreModel = postSnapshot.getValue(DailyScoreModel.class);


                    // here you can access to name property like university.name

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    void addTestData()
    {
        titleList.add("Exercise minimum 30 minutes in morning");
        titleList.add("Spend more time with family and friends");
        titleList.add("Work on your hobbies");
        titleList.add("Fulfill the work you always gave excuse to");
        titleList.add("Music therapy of calm sounds");
        titleList.add("Going to stress management rehabs");
        titleList.add("Listen a motivation videos");
        titleList.add("Self Talk");
        titleList.add("Meet New people");
        titleList.add("Take your Medicine On time");
        titleList.add("Walk For 20 minutes");
        titleList.add("Sleep for minimum 8 Hours");


        checkedList.add(false);
        checkedList.add(false);
        checkedList.add(false);
        checkedList.add(false);
        checkedList.add(false);
        checkedList.add(false);
        checkedList.add(false);
        checkedList.add(false);
        checkedList.add(false);
        checkedList.add(false);
        checkedList.add(false);
        checkedList.add(false);

    }

    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.button1:
                changeColor(view);
                break;
            case R.id.button7:
                changeColor(view);
                break;
            case R.id.button6:
                changeColor(view);
                break;
            case R.id.button5:
                changeColor(view);
                break;
            case R.id.button4:
                changeColor(view);
                break;
            case R.id.button3:
                changeColor(view);
                break;
            case R.id.button2:
                changeColor(view);
                break;

        }



    }

    void changeColor(View button)
    {
        day1.setBackgroundColor(Color.parseColor("#f2fe71"));
        day2.setBackgroundColor(Color.parseColor("#f2fe71"));
        day3.setBackgroundColor(Color.parseColor("#f2fe71"));
        day4.setBackgroundColor(Color.parseColor("#f2fe71"));
        day5.setBackgroundColor(Color.parseColor("#f2fe71"));
        day6.setBackgroundColor(Color.parseColor("#f2fe71"));
        day7.setBackgroundColor(Color.parseColor("#f2fe71"));

        button.setBackgroundColor(Color.parseColor("#ff6861"));
    }
}
