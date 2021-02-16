package com.example.elshare;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class edit_listing_frag extends Fragment
{
    Spinner spinner2,spinner3,spinner4,spinner5,spinner6,spinner7;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_edit_listing, container, false);

        String[] charegerType_a = {"Charger Type","     Monday", "     Tuesday", "     Wednesday", "     Thursday", "     Friday", "     Saturday", "Sunday"};
        //String[] receptabelType_a = {"Receptable Type"};
        String[] voltage_a = {"Voltage","     110", "     220", "     420"};
        String[] ampere_a = {"Ampere","     220", "     321", "     ?"};
        String[] electric_a = {"Electric Utility Company","     ABC", "     XYZ"};
        String[] company_a = {"Rate structure","     Standared", "     Ordinary"};

        Spinner spinner2= rootView.findViewById(R.id.spinner2);
       // Spinner spinner3= rootView.findViewById(R.id.spinner3);
        Spinner spinner4= rootView.findViewById(R.id.spinner4);
        Spinner spinner5= rootView.findViewById(R.id.spinner5);
        Spinner spinner6= rootView.findViewById(R.id.spinner6);
        Spinner spinner7= rootView.findViewById(R.id.spinner7);

        spinner2.setOnItemSelectedListener( new  chargerType());
      //  spinner3.setOnItemSelectedListener(new receptableType());
        spinner4.setOnItemSelectedListener(new voltege());
        spinner5.setOnItemSelectedListener(new ampere());
        spinner6.setOnItemSelectedListener(new electricUtilityCompany());
        spinner7.setOnItemSelectedListener(new rateStructure());

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, charegerType_a);
        spinner2.setAdapter(adapter2);

//        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, receptabelType_a);
//        spinner3.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, voltage_a);
        spinner4.setAdapter(adapter4);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, ampere_a);
        spinner5.setAdapter(adapter5);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, electric_a);
        spinner6.setAdapter(adapter6);

        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, company_a);
        spinner7.setAdapter(adapter7);

        return rootView;
    }

    class chargerType implements AdapterView.OnItemSelectedListener
    {
        public  void  onItemSelected(AdapterView<?> parent, View view,int pos, long id)
        {
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
//    class receptableType implements AdapterView.OnItemSelectedListener
//    {
//        public  void  onItemSelected(AdapterView<?> parent, View view,int pos, long id)
//        {
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//    }
    static class voltege implements AdapterView.OnItemSelectedListener
    {
        public  void  onItemSelected(AdapterView<?> parent, View view,int pos, long id)
        {
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    static class ampere implements AdapterView.OnItemSelectedListener
    {
        public  void  onItemSelected(AdapterView<?> parent, View view,int pos, long id)
        {
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    static class electricUtilityCompany implements AdapterView.OnItemSelectedListener
    {
        public  void  onItemSelected(AdapterView<?> parent, View view,int pos, long id)
        {
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    class rateStructure implements AdapterView.OnItemSelectedListener
    {
        public  void  onItemSelected(AdapterView<?> parent, View view,int pos, long id)
        {
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
