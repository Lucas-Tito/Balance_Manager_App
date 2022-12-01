package com.example.mobile_final_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Register() {
        // Required empty public constructor
    }


    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
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
        View v = inflater.inflate(R.layout.register, container, false);

        build_login_btn(v);
        build_register_btn(v);

        return v;
    }

    private void build_login_btn(View v) {

        Button login_btn = v.findViewById(R.id.login_btn);
        System.out.println("sus");
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("sus2");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Login()).commit();
            }
        });

    }


    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    private void build_register_btn(View v) {

        Button register = v.findViewById(R.id.Register_btn);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameInput = v.findViewById(R.id.nameInput);
                emailInput = v.findViewById(R.id.emailInput);
                passwordInput = v.findViewById(R.id.passwordInput);

                mAuth = FirebaseAuth.getInstance();

                createUser(nameInput.getText().toString(), emailInput.getText().toString(),
                        passwordInput.getText().toString());
            }

        });

    }

    private void createUser(String name, String email, String password) {

        if(TextUtils.isEmpty(name)){
            nameInput.setError("field cannot be empty");
            nameInput.requestFocus();
        }
        else if (TextUtils.isEmpty(email)){
            emailInput.setError("field cannot be empty");
            emailInput.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            passwordInput.setError("field cannot be empty");
            passwordInput.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(), "Successfully Registered", Toast.LENGTH_LONG).show();

                        DocumentReference documentReference = fStore.collection("users").document(mAuth.getCurrentUser().getUid());
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("email", email);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                System.out.println("USER: user profile created for " + mAuth.getCurrentUser().getUid());
                            }
                        });

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Login()).commit();
                    }
                    else{
                        try {
                            throw task.getException();

                        }catch(FirebaseAuthWeakPasswordException e) {
                            Toast.makeText(getActivity(), "Weak Password, 6 characters minimum", Toast.LENGTH_SHORT).show();
                        }catch(FirebaseAuthUserCollisionException e) {
                            Toast.makeText(getActivity(), "Email already taken", Toast.LENGTH_SHORT).show();
                        }catch(FirebaseAuthInvalidCredentialsException e){
                            Toast.makeText(getActivity(), "Email invalid", Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            Toast.makeText(getActivity(), "Unable to Register, " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

    }
}