package com.aashika.gotourtoday;
       import android.content.Context;
        import android.view.View;
       import android.widget.Button;
       import android.widget.ImageView;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import com.bumptech.glide.Glide;
       import com.google.firebase.database.DataSnapshot;
       import com.google.firebase.database.DatabaseError;
       import com.google.firebase.database.DatabaseReference;
       import com.google.firebase.database.FirebaseDatabase;
       import com.google.firebase.database.ValueEventListener;
       import com.like.LikeButton;
       import com.like.OnLikeListener;


public class ViewHolder extends RecyclerView.ViewHolder {
    View mview;
    DatabaseReference ref;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mview = itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return false;
            }
        });
    }
    public void setDetails(Context ctx, String title, String image, String story, String reference, String position) {
        TextView mTitle=mview.findViewById(R.id.titleView);
        ImageView mImage=mview.findViewById(R.id.imageView);
        TextView mStory=mview.findViewById(R.id.storyView);
        LikeButton x=mview.findViewById(R.id.like);
        x.setEnabled(true);
        ref= FirebaseDatabase.getInstance().getReference().child(reference).child(position);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if(snapshot.child("like").getValue().toString().equals("false")){
                        x.setLiked(false);
                    }
                    else if(snapshot.child("like").getValue().toString().equals("true")){
                        x.setLiked(true);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        x.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                ref.child("like").setValue(true);
                System.out.println("liked");
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                ref.child("like").setValue(false);
                System.out.println("unliked");
            }
        });

        mTitle.setText(title);
        mStory.setText(story);
        Glide.with(ctx)
                .load(image).placeholder(R.mipmap.ic_launcher).fitCenter().centerCrop()
                .into(mImage);
    }
    private ViewHolder.ClickListener mClickListener;
    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener=clickListener;
    }
}

