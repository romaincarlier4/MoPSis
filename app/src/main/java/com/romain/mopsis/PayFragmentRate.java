package com.romain.mopsis;

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
 * Use the {@link PayFragmentRate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayFragmentRate extends Fragment {

    EditText amountEdit;
    EditText monthsEdit;
    EditText durationEdit;
    Spinner type;
    TextView rateResult;
    TextView totalResult;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayFragmentRate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayFragmentRate.
     */
    // TODO: Rename and change types and number of parameters
    public static PayFragmentRate newInstance(String param1, String param2) {
        PayFragmentRate fragment = new PayFragmentRate();
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
        View rootView =  inflater.inflate(R.layout.fragment_pay_rate, container, false);
        amountEdit = rootView.findViewById(R.id.montant_emprunte);
        monthsEdit = rootView.findViewById(R.id.mensualitéEdit);
        durationEdit = rootView.findViewById(R.id.durée_nombre);
        type = rootView.findViewById(R.id.spinner);
        rateResult = rootView.findViewById(R.id.taux_nombre);
        totalResult = rootView.findViewById(R.id.total_result);

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

                    dynamicCalculRate();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        monthsEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (!input.isEmpty()) {

                    monthsEdit.removeTextChangedListener(this);

                    String new_string = spacer(input);

                    monthsEdit.setText(new_string);
                    monthsEdit.setSelection(new_string.length());
                    monthsEdit.addTextChangedListener(this);

                    dynamicCalculRate();
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

                String input = charSequence.toString();

                if (!input.isEmpty()) {

                    durationEdit.removeTextChangedListener(this);

                    String new_string = spacer(input);

                    durationEdit.setText(new_string);
                    durationEdit.setSelection(new_string.length());
                    durationEdit.addTextChangedListener(this);

                    dynamicCalculRate();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        type.setOnItemSelectedListener(spinnerListener);
        return rootView;
    }

    private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            dynamicCalculRate();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    };

    public void dynamicCalculRate(){
        String amount = amountEdit.getText().toString();
        String months = monthsEdit.getText().toString();
        String duration = durationEdit.getText().toString();
        String type_pret = type.getSelectedItem().toString();
        amount = strip(amount);
        months = strip(months);
        duration = strip(duration);
        double capital = 0;
        double monthly = 0;
        double years = 0;
        Boolean empty;

        if (amount.isEmpty() || months.isEmpty() || duration.isEmpty()){
            rateResult.setText("");
            totalResult.setText("");
            empty = true;
        } else {
            capital = Double.parseDouble(amount);
            monthly = Double.parseDouble(months);
            years = Double.parseDouble(duration);
            empty  = false;
        }

        // Calcul des mensualités et de l'interet total pour l'annuité constante
        if (type_pret.equals(getResources().getString(R.string.annuite)) && !empty) {

            String resultRate = "hello";
            double currentError = 1;

            for (double i = 0; i < 1; i+=0.001) {
                double currentMonthly = (capital * i ) / (1 - (Math.pow((1 + i), -years)))/12;
                if(Math.abs(currentMonthly-monthly)<1){
                    if(Math.abs(currentMonthly-monthly)<currentError)
                        resultRate = Double.toString(i);
                }
            }

            /*rateResult.setText(decimal_spacer(resultRate)+" %");
            rateResult.setTextColor(getResources().getColor(R.color.black));
            rateResult.setBackground(getResources().getDrawable(R.drawable.border));*/


            double interestAndCapital = (double)Math.round((monthly * 12 * years)*100)/100;

            String result_total = Double.toString(interestAndCapital);
//            totalResult.setText(decimal_spacer(result_total)+ " €");
//            totalResult.setTextColor(getResources().getColor(R.color.black));
//            totalResult.setBackground(getResources().getDrawable(R.drawable.border));
//
//            amountEdit.setHintTextColor(getResources().getColor(R.color.hint_default));
//            monthsEdit.setHintTextColor(getResources().getColor(R.color.hint_default));
//            durationEdit.setHintTextColor(getResources().getColor(R.color.hint_default));

            // Calcul des mensualites et de l'interet total pour le capital fixe
        } /*else if (type_pret.equals(getResources().getString(R.string.capital))) {

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
        else if(type_pret.equals(getResources().getString(R.string.bullet))){
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
        }*/
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