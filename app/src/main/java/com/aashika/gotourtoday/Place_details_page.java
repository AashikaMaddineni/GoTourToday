package com.aashika.gotourtoday;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

public class Place_details_page extends AppCompatActivity {
    DatabaseReference ref;
    FirebaseRecyclerAdapter<Member, PlaceViewHolder> firebaseRecyclerAdapter;
    LinearLayoutManager mLinearLayoutManager;
    FirebaseRecyclerOptions<Member> options;
    DatabaseReference mDatabaseReference;
    private static ViewPager mPager;
    TextView Title;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    RecyclerView mRecyclerView;
    private String[] urls = new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_info_display);

        mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView = findViewById(R.id.recycler_view);

        Title=findViewById(R.id.titleView);
        mRecyclerView = findViewById(R.id.recycler_view);
        init();
        String Imageid=getIntent().getStringExtra("Imageid");
        String reference=getIntent().getStringExtra("ref");
        ref= FirebaseDatabase.getInstance().getReference().child(reference);

        mDatabaseReference = ref.child(Imageid).child("places");

        ref.child(Imageid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String title=snapshot.child("maintitle").getValue().toString();
                    System.out.println(title);
                    Title.setText(title);
                    urls[0]=snapshot.child("image1").getValue().toString();
                    urls[1]=snapshot.child("image2").getValue().toString();
                    urls[2]=snapshot.child("image3").getValue().toString();
                    urls[3]=snapshot.child("image4").getValue().toString();
                    urls[4]=snapshot.child("image5").getValue().toString();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        showData();
    }
    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new sliding_image_adapter(Place_details_page.this, urls));
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES = urls.length;
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });

    }
    private void showData() {

        options = new FirebaseRecyclerOptions.Builder<Member>().setQuery(mDatabaseReference, Member.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Member, PlaceViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PlaceViewHolder holder, int position, @NonNull Member model) {
                holder.setDetails(getApplicationContext(), model.getTitle(), model.getImage(),  model.getStory());
            }

            @NonNull
            @Override
            public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.complete_place_details,parent,false);
                PlaceViewHolder viewHolder=new PlaceViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(Place_details_page.this,"hello",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(Place_details_page.this,"Long Click",Toast.LENGTH_SHORT);
                    }
                });
                return viewHolder;
            }
        };
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    protected void onStart() {

        super.onStart();
        if(firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.startListening();
        }
    }
}
