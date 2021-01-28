package com.romain.mopsis;

import android.content.Intent;
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

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class PayFragment extends Fragment{

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
    Spinner choiceCalculate;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayFragment() {
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
    public static PayFragment newInstance(String param1, String param2) {
        PayFragment fragment = new PayFragment();
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

        choiceCalculate = rootView.findViewById(R.id.choiceCalculate);
        choiceCalculate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(choiceCalculate.getSelectedItem().toString().equals(getResources().getString(R.string.rate))){
                    Intent intent = new Intent()
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
        //creation des listeners
        amountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (!input.isEmpty()) {

                    amountEdit.removeTextChangedListener(this);

                    String new_string = spacer(input);

                    amountEdit.setText(new_string);
                    amountEdit.setSelection(new_string.length());
                    amountEdit.addTextChangedListener(this);

                    dynamicCalculMonths();
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
                dynamicCalculMonths();
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

                String input = charSequence.toString();

                if (!input.isEmpty()) {

                    durationEdit.removeTextChangedListener(this);

                    String new_string = spacer(input);

                    durationEdit.setText(new_string);
                    durationEdit.setSelection(new_string.length());
                    durationEdit.addTextChangedListener(this);

                    dynamicCalculMonths();
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

        if (amount.isEmpty() || taux.isEmpty() || duration.isEmpty()){
            monthsTxt.setText("");
            totalTxt.setText("");
        }

        // Calcul des mensualités et de l'interet total pour l'annuité constante
        else if (type_pret.equals(getResources().getString(R.string.annuite))) {

            double annual_refund = (Double.parseDouble(amount) * Double.parseDouble(taux) / 100) / (1 - (Math.pow((1 + Double.parseDouble(taux) / 100), -Double.parseDouble(duration))));
            double monthly_refund = annual_refund/12;
            monthly_refund = (double) Math.round(monthly_refund*100.0)/100;
            String result_month = Double.toString(monthly_refund);
            monthsTxt.setText(decimal_spacer(result_month)+" €");
            monthsTxt.setTextColor(getResources().getColor(R.color.black));
            monthsTxt.setBackground(getResources().getDrawable(R.drawable.border));


            double interest_and_capital = (monthly_refund * 12 * Double.parseDouble(duration));
            interest_and_capital = (double) Math.round(interest_and_capital*100.0)/100;
            String result_total = Double.toString(interest_and_capital);
            totalTxt.setText(decimal_spacer(result_total)+ " €");
            totalTxt.setTextColor(getResources().getColor(R.color.black));
            totalTxt.setBackground(getResources().getDrawable(R.drawable.border));

            amountEdit.setHintTextColor(getResources().getColor(R.color.hint_default));
            rateEdit.setHintTextColor(getResources().getColor(R.color.hint_default));
            durationEdit.setHintTextColor(getResources().getColor(R.color.hint_default));


        // Calcul des mensualites et de l'interet total pour le capital fixe
        } else if (type_pret.equals(getResources().getString(R.string.capital))) {
            double total_refund=0;
            Double amountDouble = Double.parseDouble(amount);
            Double durationDouble = Double.parseDouble(duration);
            Double tauxDouble = Double.parseDouble(taux);
            for (int i = 1; i < Double.parseDouble(duration)+1; i++) {
                total_refund+= (amountDouble)/(durationDouble)*(1+(tauxDouble/100)*(durationDouble-i+1));
            }
            totalTxt.setText(decimal_spacer(Double.toString(total_refund))+" €");
            totalTxt.setTextColor(getResources().getColor(R.color.black));
            totalTxt.setBackground(getResources().getDrawable(R.drawable.border));

            double mensualiteMoyenne;
            mensualiteMoyenne = total_refund/durationDouble/12;
            monthsTxt.setText(decimal_spacer(Double.toString(mensualiteMoyenne))+" €");
            monthsTxt.setTextColor(getResources().getColor(R.color.black));
            monthsTxt.setBackground(getResources().getDrawable(R.drawable.border));

        }
        else if(type_pret.equals(getResources().getString(R.string.bullet))){
            double total_refund=0;
            Double amountDouble = Double.parseDouble(amount);
            Double durationDouble = Double.parseDouble(duration);
            Double tauxDouble = Double.parseDouble(taux);
            total_refund = amountDouble + (amountDouble*tauxDouble/100*durationDouble);
            totalTxt.setText(decimal_spacer(Double.toString(total_refund))+" €");
            totalTxt.setTextColor(getResources().getColor(R.color.black));
            totalTxt.setBackground(getResources().getDrawable(R.drawable.border));

            double mensualites;
            mensualites = amountDouble*tauxDouble/100/12;
            monthsTxt.setText(decimal_spacer(Double.toString(mensualites))+" €");
            monthsTxt.setTextColor(getResources().getColor(R.color.black));
            monthsTxt.setBackground(getResources().getDrawable(R.drawable.border));
        }
    }

    //config Type spinner listener
    private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            dynamicCalculMonths();
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
}