package com.aashika.gotourtoday;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.firebase.ui.database.FirebaseRecyclerOptions;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class Gallery_image_completeView extends AppCompatActivity {
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference ref;
    DatabaseReference mDatabaseReference;
    FirebaseRecyclerAdapter<Member, ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Member> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_complete_view);
        mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView = findViewById(R.id.recycler_view);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Gallery");
        String Imageid = getIntent().getStringExtra("Imageid");
        int id= Integer.parseInt(Imageid);
        ref.child(Imageid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    switch(id){
                        case 1:
                            mDatabaseReference = mFirebaseDatabase.getReference("spring");
                            System.out.println(mDatabaseReference);
                            break;
                        case 2:
                            mDatabaseReference = mFirebaseDatabase.getReference("summer");
                            System.out.println(mDatabaseReference);
                            break;
                        case 3:
                            mDatabaseReference = mFirebaseDatabase.getReference("monsoon");
                            System.out.println(mDatabaseReference);
                            break;
                        case 4:
                            mDatabaseReference = mFirebaseDatabase.getReference("autumn");
                            System.out.println(mDatabaseReference);
                            break;
                        case 5:
                            mDatabaseReference = mFirebaseDatabase.getReference("prewinter");
                            System.out.println(mDatabaseReference);
                            break;

                        case 6:
                            mDatabaseReference = mFirebaseDatabase.getReference("winter");
                            System.out.println(mDatabaseReference);
                            break;
                    }
                    showData();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showData() {
        options = new FirebaseRecyclerOptions.Builder<Member>().setQuery(mDatabaseReference, Member.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Member, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Member model) {
                holder.setDetails(getApplicationContext(), model.getTitle(), model.getImage(),  model.getStory(), mDatabaseReference.getKey(), getRef(position).getKey());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.placedetails,parent,false);
                ViewHolder viewHolder=new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent=new Intent(Gallery_image_completeView.this, Place_details_page.class);
                        intent.putExtra("ref", mDatabaseReference.getKey());
                        intent.putExtra("Imageid", getRef(position).getKey());
                        startActivity(intent);
                        Toast.makeText(Gallery_image_completeView.this,"hello",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(Gallery_image_completeView.this,"Long Click",Toast.LENGTH_SHORT);
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