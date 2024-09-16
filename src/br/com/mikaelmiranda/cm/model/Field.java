package br.com.mikaelmiranda.cm.model;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int linha;
    private final int coluna;

    private boolean aberto;
    private boolean minado;
    private boolean marcado;

    private List<Field> vizinhos = new ArrayList<>();

    Field(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }
}
