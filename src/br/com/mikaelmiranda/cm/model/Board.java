package br.com.mikaelmiranda.cm.model;

import br.com.mikaelmiranda.cm.exception.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Field> fields = new ArrayList<>();

    public Board (int linhas, int colunas, int minas){
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        
        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public List<Field> getFields(){
        return fields;
    }

    public Field getField(int linha, int coluna){
        return fields.stream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .orElse(null);
    }

    public void abrir(int linha, int coluna){
        try {
            fields.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.abrir());
        } catch (ExplosaoException e){
            fields.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void alternarMarcacao(int linha, int coluna){
        fields.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao());
    }

    private void gerarCampos() {
        for (int linha = 0; linha < linhas; linha++){
            for (int coluna = 0; coluna < colunas; coluna++){
                fields.add(new Field(linha, coluna));
            }
        }
    }

    private void associarVizinhos() {
        for(Field c1: fields){
            for (Field c2: fields){
                c1.adicionarVizinhos(c2);
            }
        }
    }

    private void sortearMinas() {
        Predicate<Field> minado = Field::isMinado;

        while (fields.stream().filter(minado).count() < minas){
            int aleatorio = (int) (Math.random() * fields.size());
            Field campo = fields.get(aleatorio);

            if (!campo.isMinado()){
                campo.minar();
            }
        }
    }

    public boolean objetivoAlcancado(){
        return fields.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar(){
        fields.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        //ANSI Codes to Change Colors
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_CYAN = "\u001B[36m";

        sb.append("  ");
        for (int c = 0; c < colunas; c++){
            sb.append(" ")
                    .append(ANSI_CYAN)
                    .append(c)
                    .append(ANSI_RESET)
                    .append(" ");
        }

        sb.append("\n");

        int i = 0;
        for (int l = 0; l < linhas; l++){
            sb.append(ANSI_CYAN)
                    .append(l)
                    .append(ANSI_RESET)
                    .append(" ");

            for (int c = 0; c < colunas; c++){
                sb.append(" ");
                sb.append(fields.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
