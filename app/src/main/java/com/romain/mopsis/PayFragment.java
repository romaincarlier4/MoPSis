package com.romain.mopsis;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class PayFragment extends Fragment{
    Button calc = null;
    EditText montant = null;
    EditText interet = null;
    EditText duree = null;
    Spinner type = null;
    TextView mensualite = null;
    TextView total = null;
    PieChart chart = null;

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
        View rootView = inflater.inflate(R.layout.fragment_pay, container, false);

        //Creation des vues
        montant = rootView.findViewById(R.id.montant_emprunte);
        interet = rootView.findViewById(R.id.taux_nombre);
        duree = rootView.findViewById(R.id.durée_nombre);
        type = rootView.findViewById(R.id.spinner);
        mensualite = rootView.findViewById(R.id.mensualite_result);
        total = rootView.findViewById(R.id.total_result);
        chart = rootView.findViewById(R.id.pieChart);

        //creation des listeners
        montant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String input = charSequence.toString();

                if (!input.isEmpty()) {

                    montant.removeTextChangedListener(this);

                    String new_string = spacer(input);

                    montant.setText(new_string);
                    montant.setSelection(new_string.length());

                    montant.addTextChangedListener(this);

                    dynamicCalcul();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        interet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                dynamicCalcul();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        duree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String input = charSequence.toString();

                if (!input.isEmpty()) {

                    duree.removeTextChangedListener(this);

                    String new_string = spacer(input);

                    duree.setText(new_string);
                    duree.setSelection(new_string.length());

                    duree.addTextChangedListener(this);

                    dynamicCalcul();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        type.setOnItemSelectedListener(spinnerListener);
        return rootView;

    }

    //Calcul dynamique
    public void dynamicCalcul(){
        String amount = montant.getText().toString();
        amount = striper(amount);
        String taux = interet.getText().toString();
        taux = striper(taux);
        String duration = duree.getText().toString();
        duration = striper(duration);
        String type_pret = type.getSelectedItem().toString();
        if (amount.isEmpty()){
            mensualite.setText("");
            total.setText("");
        }

        if (taux.isEmpty()){
            mensualite.setText("");
            total.setText("");
        }

        if (duration.isEmpty()){
            mensualite.setText("");
            total.setText("");
        }

        // Calcul des mensualités et de l'interet total pour l'annuité constante
        if (!amount.isEmpty() && !taux.isEmpty() && !duration.isEmpty() && type_pret.equals("Annuité Constante")) {
            double annual_refund = (Double.parseDouble(amount) * Double.parseDouble(taux) / 100) / (1 - (Math.pow((1 + Double.parseDouble(taux) / 100), -Double.parseDouble(duration))));
            Float amountFloat = Float.parseFloat(amount);
            double monthly_refund = annual_refund/12;
            monthly_refund = (double) Math.round(monthly_refund*100.0)/100;
            String result_month = Double.toString(monthly_refund);
            mensualite.setText(decimal_spacer(result_month)+" €");
            mensualite.setTextColor(getResources().getColor(R.color.black));
            mensualite.setBackground(getResources().getDrawable(R.drawable.border));


            double interest_and_capital = (monthly_refund * 12 * Double.parseDouble(duration));
            interest_and_capital = (double) Math.round(interest_and_capital*100.0)/100;
            String result_total = Double.toString(interest_and_capital);
            total.setText(decimal_spacer(result_total)+ " €");
            total.setTextColor(getResources().getColor(R.color.black));
            total.setBackground(getResources().getDrawable(R.drawable.border));

            montant.setHintTextColor(getResources().getColor(R.color.hint_default));
            interet.setHintTextColor(getResources().getColor(R.color.hint_default));
            duree.setHintTextColor(getResources().getColor(R.color.hint_default));

            List<PieEntry> entries = new ArrayList<PieEntry>();
            entries.add(new PieEntry(amountFloat, "capital"));
            entries.add(new PieEntry((float) (interest_and_capital-amountFloat),"interests"));

            PieDataSet pieDataSet = new PieDataSet(entries, "");
            pieDataSet.setColors(getResources().getColor(R.color.rouge),getResources().getColor(R.color.blue));

            PieData pieData = new PieData(pieDataSet);
            chart.setMaxAngle(180);
            chart.setData(pieData);

        // Calcul des mensualites et de l'interet total pour le capital fixe
        } else if (!amount.isEmpty() && !taux.isEmpty() && !duration.isEmpty() && type_pret.equals("Capital fixe")) {
            double total_refund=0;
            Double amountDouble = Double.parseDouble(amount);
            Float amountFloat = Float.parseFloat(amount);
            Double durationDouble = Double.parseDouble(duration);
            Double tauxDouble = Double.parseDouble(taux);
            for (int i = 1; i < Double.parseDouble(duration)+1; i++) {
                total_refund+= (amountDouble)/(durationDouble)*(1+(tauxDouble/100)*(durationDouble-i+1));
            }
            total.setText(decimal_spacer(Double.toString(total_refund))+" €");
            total.setTextColor(getResources().getColor(R.color.black));
            total.setBackground(getResources().getDrawable(R.drawable.border));

            double mensualiteMoyenne;
            mensualiteMoyenne = total_refund/durationDouble/12;
            mensualite.setText(decimal_spacer(Double.toString(mensualiteMoyenne))+" €");
            mensualite.setTextColor(getResources().getColor(R.color.black));
            mensualite.setBackground(getResources().getDrawable(R.drawable.border));

            List<PieEntry> entries = new ArrayList<PieEntry>();
            entries.add(new PieEntry(amountFloat, "capital"));
            entries.add(new PieEntry((float) (total_refund-amountDouble),"interests"));

            PieDataSet pieDataSet = new PieDataSet(entries, "");
            pieDataSet.setColors(getResources().getColor(R.color.rouge),getResources().getColor(R.color.blue));

            PieData pieData = new PieData(pieDataSet);
            chart.setMaxAngle(180);
            chart.setData(pieData);
        }
        else if(!amount.isEmpty() && !taux.isEmpty() && !duration.isEmpty() && type_pret.equals("Bullet")){
            double total_refund=0;
            Double amountDouble = Double.parseDouble(amount);
            Float amountFloat = Float.parseFloat(amount);
            Double durationDouble = Double.parseDouble(duration);
            Double tauxDouble = Double.parseDouble(taux);
            total_refund = amountDouble + (amountDouble*tauxDouble/100*durationDouble);
            total.setText(decimal_spacer(Double.toString(total_refund))+" €");
            total.setTextColor(getResources().getColor(R.color.black));
            total.setBackground(getResources().getDrawable(R.drawable.border));

            double mensualites;
            mensualites = amountDouble*tauxDouble/100/12;
            mensualite.setText(decimal_spacer(Double.toString(mensualites))+" €");
            mensualite.setTextColor(getResources().getColor(R.color.black));
            mensualite.setBackground(getResources().getDrawable(R.drawable.border));

            List<PieEntry> entries = new ArrayList<PieEntry>();
            entries.add(new PieEntry(amountFloat, "capital"));
            entries.add(new PieEntry((float) (total_refund-amountDouble),"interests"));

            PieDataSet pieDataSet = new PieDataSet(entries, "");
            pieDataSet.setColors(getResources().getColor(R.color.rouge),getResources().getColor(R.color.blue));

            PieData pieData = new PieData(pieDataSet);
            chart.setMaxAngle(180);
            chart.
            chart.setData(pieData);
        }
    }

    //spinner listener
    private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            dynamicCalcul();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
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
        String number3 = number2.replace(decimalSeparator,"");
        return decimalFormat.format(Double.parseDouble(number3));
    }

    //striper
    private static String striper(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String groupingSeparator = String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator());
        String number2 = number.replace(groupingSeparator, "");
        return number2;
    }
}