package com.example.capstone0.BottomNavigationThings.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.capstone0.D_CurrentUser;
import com.example.capstone0.Login.A_SignIn;
import com.example.capstone0.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class A_AccountSettings extends AppCompatActivity implements View.OnClickListener {

    Button deleteAccount;
    FirebaseAuth firebaseAuth;
     FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__account_settings);

      deleteAccount=findViewById(R.id.Account_Settings_btn_Delete_Account);
      deleteAccount.setOnClickListener(this);

      firebaseAuth=FirebaseAuth.getInstance();
      firebaseUser=firebaseAuth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.Account_Settings_btn_Delete_Account)
        {
           final String uid=firebaseUser.getUid();
           deleteuserData(uid);
           clearAll();

           firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()) {
                       Toast.makeText(getApplicationContext(), "Account deleted successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),A_SignIn.class));
                   }
               }
           });
        }
    }
  public void clearAll()
  {
      D_CurrentUser.Gender=null;
      D_CurrentUser.noOfAddress=0;
      D_CurrentUser.Email=null;
      D_CurrentUser.Phone=null;
      D_CurrentUser.Name=null;
  }

  public void deleteuserData(String uid)
  {
     DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");

     databaseReference.child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful())
                 Toast.makeText(getApplicationContext(),"Your application data deleted successfully",Toast.LENGTH_SHORT).show();
                Log.e("A_Account","Data deleted");
         }
     });
  }
  public void removeCurrentUser()
  {
      firebaseAuth.updateCurrentUser(null);
      Toast.makeText(getApplicationContext(),"Current user set to null",Toast.LENGTH_LONG).show();
      Log.e("A_Account","Current user set to null");
  }



}
