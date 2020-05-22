package br.edu.faculdadedelta.atividadeaula02n2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.faculdadedelta.atividadeaula02n2.dao.SeriadosDAO;
import br.edu.faculdadedelta.atividadeaula02n2.modelo.Seriados;

public class MainActivity extends AppCompatActivity {

    private EditText etGenero;
    private EditText etStatus;
    private EditText etNome;
    private EditText etComentario;
    private EditText etNota;
    private EditText etData;

    private Seriados seriados = new Seriados();
    private SeriadosDAO dao = new SeriadosDAO();

    SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    Date dataHoje = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etGenero = findViewById(R.id.etGenero);
        etStatus = findViewById(R.id.etStatus);
        etNome = findViewById(R.id.etNome);
        etComentario = findViewById(R.id.etComentario);
        etNota = findViewById(R.id.etNota);
        etData = findViewById(R.id.etData);


        Intent intent = getIntent();




        if(intent != null){
            Seriados seriadosSelecionado = (Seriados) intent.getSerializableExtra("seriadosSelecionado");

            if(seriadosSelecionado != null) {
                popularFormulario(seriadosSelecionado);
            }
        }

    }


    private void popularFormulario(Seriados seriadosSelecionado){
        etGenero.setText(seriadosSelecionado.getGenero());
        etStatus.setText(seriadosSelecionado.getStatus());
        etNome.setText(seriadosSelecionado.getNome());
        etComentario.setText(seriadosSelecionado.getComentario());

        etNota.setText(String.valueOf(seriadosSelecionado.getNota()));

        etData.setText(sf.format(seriadosSelecionado.getData()));
        seriados.setId(seriadosSelecionado.getId());

    }

    private void popularModelo(){
        seriados.setGenero(etGenero.getText().toString());
        seriados.setStatus(etStatus.getText().toString());
        seriados.setNome(etNome.getText().toString());
        seriados.setComentario(etComentario.getText().toString());
        seriados.setNota(Integer.parseInt(etNota.getText().toString()));


        Date data = null;

        try {
            data = sf.parse(etData.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        seriados.setData(data);

    }




    public void salvar(View view) throws ParseException {


        String genero = etGenero.getText().toString();
        String status = etStatus.getText().toString();
        String nome = etNome.getText().toString();
        String comentario = etComentario.getText().toString();
        String nota = etNota.getText().toString();
        String dataTexto = etData.getText().toString();

        Date dataDigitada = null;

        try {
            dataDigitada = sf.parse(etData.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (genero.isEmpty()) {
            Toast.makeText(getBaseContext(), "O campo Gênero é obrigatório !",
                    Toast.LENGTH_LONG).show();
            etGenero.requestFocus();
        } else if (status.isEmpty()) {
            Toast.makeText(getBaseContext(), "O campo Status é obrigatório !",
                    Toast.LENGTH_LONG).show();
            etStatus.requestFocus();
        } else if (nome.isEmpty()) {
            Toast.makeText(getBaseContext(), "O campo nome é obrigatório !",
                    Toast.LENGTH_LONG).show();
            etNome.requestFocus();
        } else if (comentario.isEmpty()) {
            Toast.makeText(getBaseContext(), "O campo Comentário é obrigatório !",
                    Toast.LENGTH_LONG).show();
            etComentario.requestFocus();
        } else if (nota.isEmpty()) {
            Toast.makeText(getBaseContext(), "O campo Nota é obrigatório !",
                    Toast.LENGTH_LONG).show();
            etNota.requestFocus();
        } else if (dataTexto.isEmpty()) {
            Toast.makeText(getBaseContext(), "O campo Data é obrigatório !",
                    Toast.LENGTH_LONG).show();
            etData.requestFocus();
        }else if (dataDigitada.after(dataHoje)) {
                Toast.makeText(getBaseContext(), "A data não pode ser maior que a data de hoje!",
                        Toast.LENGTH_LONG).show();
                etData.requestFocus();


        }else {

                 popularModelo();


                 if(seriados.getId() == null) {
                    dao.incluir(seriados);
                    limparCampos();
                    Toast.makeText(getBaseContext(), "Inclusão realizada com sucesso!", Toast.LENGTH_LONG).show();

                  }else {

                         dao.alterar(seriados);
                         limparCampos();
                         Toast.makeText(getBaseContext(), "Alteração realizada com sucesso!", Toast.LENGTH_LONG).show();
                        }
              }
        }


    public void listar(View view){
        Intent intent = new Intent(getBaseContext(), ListaActivity.class);
        startActivity(intent);
    }


    public void limpar(View view){
        limparCampos();
    }

    private void limparCampos(){
        etGenero.setText("");
        etStatus.setText("");
        etNome.setText("");
        etComentario.setText("");
        etNota.setText("");
        etData.setText("");
        seriados = new Seriados();
    }


}
