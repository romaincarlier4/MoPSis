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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

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

        createProfiles();

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
                SharedPreferences user1Prefs = getActivity().getSharedPreferences("user1", Context.MODE_PRIVATE);
                SharedPreferences user2Prefs = getActivity().getSharedPreferences("user2", Context.MODE_PRIVATE);
                SharedPreferences user3Prefs = getActivity().getSharedPreferences("user3", Context.MODE_PRIVATE);
                SharedPreferences.Editor user1Edit = user1Prefs.edit();
                SharedPreferences.Editor user2Edit = user2Prefs.edit();
                SharedPreferences.Editor user3Edit = user3Prefs.edit();
                if(user3Prefs.getString("username",null)!=null && user2Prefs.getString("username",null)!=null){
                    copy(user2Prefs, user1Prefs);
                    copy(user3Prefs, user2Prefs);
                    user3Edit.clear();
                    user3Edit.apply();
                }
                else if (user2Prefs.getString("username",null)!=null){
                    copy(user2Prefs, user1Prefs);
                    user2Edit.clear();
                    user2Edit.apply();
                }
                else{
                    user1Edit.clear();
                    user1Edit.apply();
                }
                refresh(getActivity());
            }
        });

        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences user2Prefs = getActivity().getSharedPreferences("user2", Context.MODE_PRIVATE);
                SharedPreferences user3Prefs = getActivity().getSharedPreferences("user3",Context.MODE_PRIVATE);
                SharedPreferences.Editor editorUser2 = user2Prefs.edit();
                SharedPreferences.Editor editorUser3 = user3Prefs.edit();

                if(user3Prefs.getString("username", null)!=null){
                    copy(user3Prefs, user2Prefs);
                    editorUser3.clear();
                    editorUser3.apply();

                } else{
                    editorUser2.clear();
                    editorUser2.apply();
                }
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
        Intent first = activity.getIntent();
        int i = first.getIntExtra("UserID", -1);
        Intent userFragment = new Intent(getContext(), MainActivity.class);
        userFragment.putExtra("UserID", i-1);
        startActivity(userFragment);
        activity.overridePendingTransition(0,0);
    }

    private void createProfiles() {
        SharedPreferences user1SharedPrefs = getActivity().getSharedPreferences("user1",getContext().MODE_PRIVATE);
        SharedPreferences user2SharedPrefs = getActivity().getSharedPreferences("user2",getContext().MODE_PRIVATE);
        SharedPreferences user3SharedPrefs = getActivity().getSharedPreferences("user3",getContext().MODE_PRIVATE);

        String user1DefaultMessage = getResources().getString(R.string.userName1);
        String user2DefaultMessage = getResources().getString(R.string.userName2);
        String user3DefaultMessage = getResources().getString(R.string.userName3);

        username1 = user1SharedPrefs.getString("username", null);
        username2 = user2SharedPrefs.getString("username", null);
        username3 = user3SharedPrefs.getString("username", null);

        String[] usernames = new String[]{username1, username2, username3};
        for (int i = 0; i < usernames.length ; i++) {
            if(usernames[i]!=null){
                if(i==0){
                    firstUser.setVisibility(View.VISIBLE);
                    firstUser.setEnabled(false);
                    firstUser.setText("Your first profile : "+ usernames[i]);
                    modify1.setVisibility(View.VISIBLE);
                    delete1.setVisibility(View.VISIBLE);
                }
                else if (i==1){
                    secondUser.setVisibility(View.VISIBLE);
                    secondUser.setEnabled(false);
                    secondUser.setText("Your second profile : "+ usernames[i]);
                    modify2.setVisibility(View.VISIBLE);
                    delete2.setVisibility(View.VISIBLE);
                }
                else {
                    thirdUser.setVisibility(View.VISIBLE);
                    thirdUser.setEnabled(false);
                    thirdUser.setText("Your third profile : " + usernames[i]);
                    modify3.setVisibility(View.VISIBLE);
                    delete3.setVisibility(View.VISIBLE);
                }
            } else {
                if(i==0){
                    firstUser.setVisibility(View.VISIBLE);
                    firstUser.setEnabled(true);
                    firstUser.setText(user1DefaultMessage);
                    modify1.setVisibility(View.INVISIBLE);
                    delete1.setVisibility(View.INVISIBLE);
                    break;
                }
                else if (i==1){
                    secondUser.setVisibility(View.VISIBLE);
                    secondUser.setEnabled(true);
                    secondUser.setText(user2DefaultMessage);
                    modify2.setVisibility(View.INVISIBLE);
                    delete2.setVisibility(View.INVISIBLE);
                    break;
                }
                else {
                    thirdUser.setVisibility(View.VISIBLE);
                    thirdUser.setEnabled(true);
                    thirdUser.setText(user3DefaultMessage);
                    modify3.setVisibility(View.INVISIBLE);
                    delete3.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void copy(SharedPreferences fromU1, SharedPreferences toU2) {
        SharedPreferences.Editor editorUser2 = toU2.edit();
        editorUser2.clear();

        for (Map.Entry<String, ?> entry : fromU1.getAll().entrySet()) {
            Object v = entry.getValue();
            String key = entry.getKey();

            if (v instanceof Boolean)
                editorUser2.putBoolean(key, (Boolean) v);
            else if (v instanceof Float)
                editorUser2.putFloat(key, (Float) v);
            else if (v instanceof Integer)
                editorUser2.putInt(key, (Integer) v);
            else if (v instanceof Long)
                editorUser2.putLong(key, (Long) v);
            else if (v instanceof String)
                editorUser2.putString(key, ((String) v));
        }
        editorUser2.apply();
    }
}