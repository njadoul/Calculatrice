package com.example.user.calculatrice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class CalAdapter extends ArrayAdapter<Donnee>{

    private Context mcontext;
    private int mresource;


    static class ViewHolder{
        TextView calcul;

        TextView resultat;
    }

    CalAdapter(Context context, int resource, ArrayList<Donnee> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String calcul = Objects.requireNonNull(getItem(position)).getCalcul();
        String result = Objects.requireNonNull(getItem(position)).getResult();

        Donnee donnee = new Donnee(calcul,result);

        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
            convertView = layoutInflater.inflate(mresource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.calcul = convertView.findViewById(R.id.calcul);
            viewHolder.resultat = convertView.findViewById(R.id.result);




            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.resultat.setText(donnee.getResult());
        viewHolder.calcul.setText(donnee.getCalcul());

        return convertView;
    }

}
