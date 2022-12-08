package com.example.mobile_final_project;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }


    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login, container, false);

        build_register_btn(v);
        build_SignIn_btn(v);
        return v;
    }


    public void build_register_btn(View v){
        Button register_btn = v.findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Register()).commit();
            }
        });
    }

    public void build_SignIn_btn(View v){

        Button SignIn_btn = v.findViewById(R.id.SignIn_btn);
        EditText emailField = v.findViewById(R.id.emailInput);
        EditText passwordField = v.findViewById(R.id.passwordInput);

        SignIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                if(TextUtils.isEmpty(email)){
                    emailField.setError("Field cannot be empty");
                    emailField.requestFocus();
                }
                else if(TextUtils.isEmpty(password)){
                    passwordField.setError("Field cannot be empty");
                    passwordField.requestFocus();
                }
                else{
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.d(TAG, "login result"+task.getResult().getUser().getEmail().toString());
                                Toast.makeText(getActivity(), "Logged in successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                        }
                    });
                }
            }
        });

        //login and save session of user

    }
}