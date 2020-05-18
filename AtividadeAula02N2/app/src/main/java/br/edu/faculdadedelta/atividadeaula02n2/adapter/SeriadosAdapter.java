package br.edu.faculdadedelta.atividadeaula02n2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.edu.faculdadedelta.atividadeaula02n2.R;
import br.edu.faculdadedelta.atividadeaula02n2.dao.SeriadosDAO;
import br.edu.faculdadedelta.atividadeaula02n2.modelo.Seriados;

public class SeriadosAdapter extends BaseAdapter {

    private List<Seriados> listaSeriados;
    private Context context;

    public SeriadosAdapter(List<Seriados> listaSeriados, Context context) {
        this.listaSeriados = listaSeriados;
        this.context = context;
    }


    @Override
    public int getCount() {
        return listaSeriados.size();
    }

    @Override
    public Object getItem(int i) {
        return listaSeriados.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Seriados seriados = (Seriados) getItem(i);

        View viewRetorno = view.inflate(context, R.layout.item_lista, null);

        TextView tvId = viewRetorno.findViewById(R.id.tvId);
        tvId.setText("Id: "+seriados.getId());

        TextView tvGenero = viewRetorno.findViewById(R.id.tvGenero);
        tvGenero.setText("Genero: "+seriados.getGenero());

        TextView tvStatus = viewRetorno.findViewById(R.id.tvStatus);
        tvStatus.setText("Status: "+seriados.getStatus());

        TextView tvNome = viewRetorno.findViewById(R.id.tvNome);
        tvNome.setText("Nome: "+seriados.getNome());

        TextView tvComentario = viewRetorno.findViewById(R.id.tvComentario);
        tvComentario.setText("Comentario: "+seriados.getComentario());

        TextView tvNota = viewRetorno.findViewById(R.id.tvNota);
        tvNota.setText("Nota: "+seriados.getNota());

        TextView tvData = viewRetorno.findViewById(R.id.tvData);

        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

        tvData.setText("Data: "+ sf.format(seriados.getData()));


        if (i % 2 == 0) {
            viewRetorno.setBackgroundColor(R.color.colorAccent);
        }

        return viewRetorno;
    }
}
