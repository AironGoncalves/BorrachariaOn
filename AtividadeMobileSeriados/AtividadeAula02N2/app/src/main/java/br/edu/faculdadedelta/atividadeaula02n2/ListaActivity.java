package br.edu.faculdadedelta.atividadeaula02n2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.edu.faculdadedelta.atividadeaula02n2.adapter.SeriadosAdapter;
import br.edu.faculdadedelta.atividadeaula02n2.dao.SeriadosDAO;
import br.edu.faculdadedelta.atividadeaula02n2.modelo.Seriados;

public class ListaActivity extends AppCompatActivity {

    private ListView lvLista;
    private SeriadosDAO dao = new SeriadosDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lvLista = findViewById(R.id.lvLista);

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Seriados seriadosSelecionado = (Seriados) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("seriadosSelecionado", seriadosSelecionado);
                startActivity(intent);
            }
        });


        lvLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                Seriados seriadosSelecionado = (Seriados) adapterView.getItemAtPosition(i);
                dao.excluir(seriadosSelecionado);
                carregarLista();
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista(){
        SeriadosAdapter adapter = new SeriadosAdapter(dao.listar(), getBaseContext());
        lvLista.setAdapter(adapter);
    }


    public void novo(View view){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }




}
