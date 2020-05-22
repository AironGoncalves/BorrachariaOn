package br.edu.faculdadedelta.atividadeaula02n2.dao;

import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.atividadeaula02n2.modelo.Seriados;

public class SeriadosDAO {

    private static List<Seriados> listaSeriados = new ArrayList<>();
    private static Long idGerador = 1L;

    public void incluir(Seriados seriados){
        seriados.setId(idGerador++);
        listaSeriados.add(seriados);
    }


    public void excluir(Seriados seriados){
        listaSeriados.remove(seriados);
    }


    public List<Seriados> listar(){
        return listaSeriados;
    }


    public void alterar(Seriados seriados){
        for (Seriados seriadosAux : listaSeriados){
            long idSeriados = seriados.getId();
            long idSeriadosAux = seriadosAux.getId();

            if(idSeriados == idSeriadosAux){
                listaSeriados.remove(seriadosAux);
                listaSeriados.add(seriados);
                break;
            }
        }
    }
}
