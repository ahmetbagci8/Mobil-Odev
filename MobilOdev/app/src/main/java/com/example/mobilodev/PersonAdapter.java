package com.example.mobilodev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {
    ArrayList<Person> mPersonList;
    LayoutInflater inflater;


    public PersonAdapter(Context context, ArrayList<Person>persons){
        inflater=LayoutInflater.from(context);
        this.mPersonList=persons;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= inflater.inflate(R.layout.get_person_card,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        final Person selectedPerson= mPersonList.get(position);
        holder.setData(selectedPerson,position);
    }

    @Override
    public int getItemCount(){return mPersonList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName, userPassword;
        ImageView userImage;
        ImageView showPassword;
        boolean isClicked=false;


        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.personUserName);
            userPassword = (TextView) itemView.findViewById(R.id.personPassword);
            userImage = (ImageView) itemView.findViewById(R.id.personImage);
            showPassword = itemView.findViewById(R.id.showPassword_button);
            showPassword.setOnClickListener(this);

        }


        public void setData(final Person selectedPerson, int position) {
            this.userPassword.setText("**********");
            this.userName.setText(selectedPerson.getUserName());
            this.userImage.setImageResource(selectedPerson.getImageID());
            showPassword.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(userPassword.getText().toString().equals("**********")){
                        userPassword.setText(selectedPerson.getUserPassword());
                    }
                    else{
                        userPassword.setText("**********");
                    }

                }
            });
        }


        @Override
        public void onClick(View v) {
            isClicked=!isClicked;
            if(isClicked==false){
                this.userPassword.setText("************");
            }
            else{
                this.userPassword.setText(getLayoutPosition());
            }

        }
    }
}


