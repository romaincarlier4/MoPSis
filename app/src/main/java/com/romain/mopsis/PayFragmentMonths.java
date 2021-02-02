package com.romain.mopsis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayFragmentMonths#newInstance} factory method to
 * create an instance of this fragment.
 */

public class PayFragmentMonths extends Fragment{

    TextView amountTxt;
    EditText amountEdit;
    TextView rateTxt;
    EditText rateEdit;
    TextView durationTxt;
    EditText durationEdit;
    Spinner typeEdit;
    TextView monthsTitle;
    TextView monthsTxt;
    TextView totalTitle;
    TextView totalTxt;
    int id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayFragmentMonths() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayFragmentMonths newInstance(String param1, String param2) {
        PayFragmentMonths fragment = new PayFragmentMonths();
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
        View rootView = inflater.inflate(R.layout.fragment_pay_months, container, false);

        amountTxt = rootView.findViewById(R.id.montant);
        amountEdit = rootView.findViewById(R.id.montant_emprunte);
        rateTxt = rootView.findViewById(R.id.taux);
        rateEdit = rootView.findViewById(R.id.taux_nombre);
        durationTxt = rootView.findViewById(R.id.durée);
        durationEdit = rootView.findViewById(R.id.durée_nombre);
        typeEdit = rootView.findViewById(R.id.spinner);
        monthsTitle = rootView.findViewById(R.id.mensualité);
        monthsTxt = rootView.findViewById(R.id.mensualite_result);
        totalTitle = rootView.findViewById(R.id.total);
        totalTxt = rootView.findViewById(R.id.total_result);
        id = ((MainActivity)getActivity()).id;

        amountEdit.setText("");
        rateEdit.setText("");
        durationEdit.setText("");

        if (id != -1){
            loadData(getContext(),id);
        }
        //creation des listeners
        amountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                id = ((MainActivity)getActivity()).id;
                String input = charSequence.toString();

                if (!input.isEmpty()) {

                    amountEdit.removeTextChangedListener(this);

                    String new_string = spacer(input);

                    amountEdit.setText(new_string);
                    amountEdit.setSelection(new_string.length());
                    amountEdit.addTextChangedListener(this);

                    dynamicCalculMonths();
                    if(id!=-1) {
                        saveData(getContext(), id);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        rateEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                id = ((MainActivity)getActivity()).id;
                dynamicCalculMonths();
                if(id!=-1) {
                    saveData(getContext(), id);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        durationEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                id = ((MainActivity)getActivity()).id;

                String input = charSequence.toString();

                if (!input.isEmpty()) {

                    durationEdit.removeTextChangedListener(this);

                    String new_string = spacer(input);

                    durationEdit.setText(new_string);
                    durationEdit.setSelection(new_string.length());
                    durationEdit.addTextChangedListener(this);

                    dynamicCalculMonths();
                    if(id!=-1) {
                        saveData(getContext(), id);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        typeEdit.setOnItemSelectedListener(spinnerListener);

        return rootView;
    }

    // METHODE AUXILLIAIRES
    //Calcul dynamique
    public void dynamicCalculMonths(){
        String amount = amountEdit.getText().toString();
        String taux = rateEdit.getText().toString();
        String duration = durationEdit.getText().toString();
        String type_pret = typeEdit.getSelectedItem().toString();
        amount = strip(amount);
        taux = strip(taux);
        duration = strip(duration);
        double capital = 0;
        double rate = 0;
        double years = 0;
        Boolean empty;

        if (amount.isEmpty() || taux.isEmpty() || duration.isEmpty()){
            monthsTxt.setText("");
            totalTxt.setText("");
            empty = true;
        } else {
            capital = Double.parseDouble(amount);
            rate = Double.parseDouble(taux);
            years = Double.parseDouble(duration);
            empty  = false;
        }

        // Calcul des mensualités et de l'interet total pour l'annuité constante
        if (type_pret.equals(getResources().getString(R.string.annuite)) && !empty) {

            double annualRefund = (capital * rate / 100) / (1 - (Math.pow((1 + rate / 100), -years)));
            double monthlyRefund = (double)Math.round(annualRefund/12*100)/100;

            String result_month = Double.toString(monthlyRefund);
            monthsTxt.setText(decimal_spacer(result_month)+" €");
            monthsTxt.setTextColor(getResources().getColor(R.color.black));
            monthsTxt.setBackground(getResources().getDrawable(R.drawable.border));


            double interestAndCapital = (double)Math.round((monthlyRefund * 12 * years)*100)/100;

            String result_total = Double.toString(interestAndCapital);
            totalTxt.setText(decimal_spacer(result_total)+ " €");
            totalTxt.setTextColor(getResources().getColor(R.color.black));
            totalTxt.setBackground(getResources().getDrawable(R.drawable.border));

            amountEdit.setHintTextColor(getResources().getColor(R.color.hint_default));
            rateEdit.setHintTextColor(getResources().getColor(R.color.hint_default));
            durationEdit.setHintTextColor(getResources().getColor(R.color.hint_default));

        // Calcul des mensualites et de l'interet total pour le capital fixe
        } else if (type_pret.equals(getResources().getString(R.string.capital)) && !empty) {

            double total_refund=0;

            for (int i = 1; i < Double.parseDouble(duration)+1; i++) {
                total_refund+= (capital)/(years)*(1+(rate/100)*(years-i+1));
            }
            totalTxt.setText(decimal_spacer(Double.toString(total_refund))+" €");
            totalTxt.setTextColor(getResources().getColor(R.color.black));
            totalTxt.setBackground(getResources().getDrawable(R.drawable.border));

            double mensualiteMoyenne;
            mensualiteMoyenne = total_refund/years/12;
            monthsTxt.setText(decimal_spacer(Double.toString(mensualiteMoyenne))+" €");
            monthsTxt.setTextColor(getResources().getColor(R.color.black));
            monthsTxt.setBackground(getResources().getDrawable(R.drawable.border));

        }
        else if(type_pret.equals(getResources().getString(R.string.bullet)) && !empty){
            double total_refund=0;
            total_refund = capital + (capital*rate/100*years);
            totalTxt.setText(decimal_spacer(Double.toString(total_refund))+" €");
            totalTxt.setTextColor(getResources().getColor(R.color.black));
            totalTxt.setBackground(getResources().getDrawable(R.drawable.border));

            double mensualites;
            mensualites = capital*rate/100/12;
            monthsTxt.setText(decimal_spacer(Double.toString(mensualites))+" €");
            monthsTxt.setTextColor(getResources().getColor(R.color.black));
            monthsTxt.setBackground(getResources().getDrawable(R.drawable.border));
        }
    }

    //config Type spinner listener
    private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            id = ((MainActivity)getActivity()).id;
            dynamicCalculMonths();
            if(id!=-1) {
                saveData(getContext(), id);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    };


    //creer espace entre chiffres
    private static String spacer(String number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String groupingSeparator = String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator());
        String number2 = number.replace(groupingSeparator, "");
        return decimalFormat.format(Double.parseDouble(number2));
    }

    //creer espace entre chiffres avec decimales
    private static String decimal_spacer(String number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");
        String groupingSeparator = String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator());
        String decimalSeparator = String.valueOf(decimalFormat.getDecimalFormatSymbols().getDecimalSeparator());
        String number2 = number.replace(groupingSeparator, "");
        String number3 = number2.replace(decimalSeparator,".");
        return decimalFormat.format(Double.parseDouble(number3));
    }

    //striper
    private static String strip(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String groupingSeparator = String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator());
        return number.replace(groupingSeparator, "");
    }

    private void saveData(Context context, int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.projects),Context.MODE_PRIVATE);
        String projectsArray = sharedPreferences.getString(context.getResources().getString(R.string.projects),"");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Type cls = new TypeToken<ArrayList<Project>>(){}.getType();
        ArrayList<Project> projects = gson.fromJson(projectsArray, cls);
        Project projectToSave = projects.get(id);
        projectToSave.setStatus("months");
        projectToSave.setType(typeEdit.getSelectedItemPosition());
        projectToSave.setAmount(amountEdit.getText().toString().equals("") ? 0 : Double.parseDouble(strip(amountEdit.getText().toString())));
        projectToSave.setRate(rateEdit.getText().toString().equals("") ? 0 : Double.parseDouble(strip(rateEdit.getText().toString())));
        projectToSave.setDuration(durationEdit.getText().toString().equals("") ? 0 : Double.parseDouble(strip(durationEdit.getText().toString())));
        projects.set(id,projectToSave);
        String pro = gson.toJson(projects);
        editor.putString(context.getResources().getString(R.string.projects),pro);
        editor.apply();
    }

    public void loadData(Context context, int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.projects),Context.MODE_PRIVATE);
        String projectsArray = sharedPreferences.getString(context.getResources().getString(R.string.projects),"");
        Gson gson = new Gson();
        Type cls = new TypeToken<ArrayList<Project>>(){}.getType();
        ArrayList<Project> projects = gson.fromJson(projectsArray, cls);
        Project toShow = projects.get(id);
        String status = toShow.getStatus();
        if(status == null){
            status = "months";
        }
        if(status.equals("months")){
            String amount = String.valueOf(toShow.getAmount());
            String rate = String.valueOf(toShow.getRate());
            String duration = String.valueOf(toShow.getDuration());
            int type = toShow.getType();
            System.out.println(amount);
            amountEdit.setText(spacer(amount));
            rateEdit.setText(rate);
            durationEdit.setText(spacer(duration));
            typeEdit.setSelection(type);
            dynamicCalculMonths();
        }
    }
}