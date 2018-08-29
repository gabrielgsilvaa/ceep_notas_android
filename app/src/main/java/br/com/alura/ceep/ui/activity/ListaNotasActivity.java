package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private List<Nota> todasNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        todasNotas = notasExemplo();
        configuraRecyclerView(todasNotas);

        TextView campoInsereNota = findViewById(R.id.lista_notas_insere_nota);

        campoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 1 && resultCode == 2 && data.hasExtra("nota")){
            Nota notaRecebida = (Nota) data.getSerializableExtra("nota");
            new NotaDAO().insere(notaRecebida);
            adapter.adiciona(notaRecebida);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<Nota> notasExemplo() {
        NotaDAO notaDAO = new NotaDAO();

        //for(int i = 0; i <= 10000; i++) {
        //    notaDAO.insere(new Nota("Título " + i, "Descrição " + i));
        //}
        notaDAO.insere(new Nota("Primeira Nota", "Descrição 1"));
        notaDAO.insere(new Nota("Segunda Nota", "Descrição 2"));

        return notaDAO.todos();
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
    }
}
