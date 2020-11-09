package com.romain.mopsis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment {

    Button firstUser;
    Button secondUser;
    Button thirdUser;
    ImageButton modify1;
    ImageButton delete1;
    ImageButton modify2;
    ImageButton delete2;
    ImageButton modify3;
    ImageButton delete3;

    private String username1;
    private String username2;
    private String username3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersFragment newInstance(String param1, String param2) {
        UsersFragment fragment = new UsersFragment();
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
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_users, container, false);

        firstUser = rootView.findViewById(R.id.user1);
        secondUser = rootView.findViewById(R.id.user2);
        thirdUser = rootView.findViewById(R.id.user3);
        modify1 = rootView.findViewById(R.id.modify1);
        delete1 = rootView.findViewById(R.id.delete1);
        modify2 = rootView.findViewById(R.id.modify2);
        delete2 = rootView.findViewById(R.id.delete2);
        modify3 = rootView.findViewById(R.id.modify3);
        delete3 = rootView.findViewById(R.id.delete3);

        SharedPreferences user1SharedPrefs = getActivity().getSharedPreferences("user1",getContext().MODE_PRIVATE);
        SharedPreferences user2SharedPrefs = getActivity().getSharedPreferences("user2",getContext().MODE_PRIVATE);
        SharedPreferences user3SharedPrefs = getActivity().getSharedPreferences("user3",getContext().MODE_PRIVATE);

        String user1DefaultMessage = getResources().getString(R.string.userName1);
        String user2DefaultMessage = getResources().getString(R.string.userName2);
        String user3DefaultMessage = getResources().getString(R.string.userName3);

        username1 = user1SharedPrefs.getString("username", user1DefaultMessage);
        username2 = user2SharedPrefs.getString("username", user2DefaultMessage);
        username3 = user3SharedPrefs.getString("username", user3DefaultMessage);
        if (username1 != user1DefaultMessage) {
            firstUser.setText("Your first profile : " + username1);
            modify1.setVisibility(View.VISIBLE);
            delete1.setVisibility(View.VISIBLE);
            firstUser.setEnabled(false);
        }
        else {
            firstUser.setText(user1DefaultMessage);
            modify1.setVisibility(View.INVISIBLE);
            delete1.setVisibility(View.INVISIBLE);
            firstUser.setEnabled(true);
        }
        if(username2 != user2DefaultMessage) {
            secondUser.setText("Your second profile : " + username2);
            modify2.setVisibility(View.VISIBLE);
            delete2.setVisibility(View.VISIBLE);
            secondUser.setEnabled(false);
        }
        else {
            secondUser.setText(user2DefaultMessage);
            modify2.setVisibility(View.INVISIBLE);
            delete2.setVisibility(View.INVISIBLE);
            secondUser.setEnabled(true);
        }
        if(username3!=user3DefaultMessage){
            thirdUser.setText("Your third profile : " + username3);
            modify3.setVisibility(View.VISIBLE);
            delete3.setVisibility(View.VISIBLE);
            thirdUser.setEnabled(false);
        }
        else{
            thirdUser.setText(user3DefaultMessage);
            modify3.setVisibility(View.INVISIBLE);
            delete3.setVisibility(View.INVISIBLE);
            thirdUser.setEnabled(true);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        firstUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(1);
            }
        });
        secondUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(2);
            }
        });
        thirdUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(3);
            }
        });

        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user1", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                refresh(getActivity());
            }
        });

        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user2", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                refresh(getActivity());
        }
        });

        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user3", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                refresh(getActivity());
            }
        });

        modify1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("OK");
                updateUser(0);
            }
        });

        modify2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(1);
            }
        });

        modify3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(2);
            }
        });
    }

    public void createUser(int personIndex){
        Intent createUser1Activity = new Intent(getActivity().getApplicationContext(), CreateUser1Activity.class);
        Intent createUser2Activity = new Intent(getActivity().getApplicationContext(), CreateUser2Activity.class);
        Intent createUser3Activity = new Intent(getActivity().getApplicationContext(), CreateUser3Activity.class);
        if (personIndex==1){
            startActivity(createUser1Activity);
        }
        else if (personIndex==2){
            startActivity(createUser2Activity);
        }
        else{
            startActivity(createUser3Activity);
        }

    }

    public void updateUser(int personIndex){
        Intent updateUser1 = new Intent(getActivity().getApplicationContext(), UpdateUser1.class);
        Intent updateUser2 = new Intent(getActivity().getApplicationContext(), UpdateUser2.class);
        Intent updateUser3 = new Intent(getActivity().getApplicationContext(), UpdateUser3.class);
        if (personIndex==0){
            startActivity(updateUser1);
        }
        else if (personIndex==1){
            startActivity(updateUser2);
        }
        else{
            startActivity(updateUser3);
        }
    }

    public void refresh(Activity activity){
        activity.finish();
        activity.overridePendingTransition(0,0);
        startActivity(activity.getIntent());
        activity.overridePendingTransition(0,0);
    }

}