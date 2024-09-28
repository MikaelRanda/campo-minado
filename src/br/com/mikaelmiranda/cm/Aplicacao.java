package br.com.mikaelmiranda.cm;

import br.com.mikaelmiranda.cm.model.Board;

public class Aplicacao {

    public static void main(String[] args){

        Board board = new Board(6, 6, 6);

        board.alternarMarcacao(4, 4);
        board.alternarMarcacao(4, 5);
        board.abrir(3, 3);

        System.out.println(board);
    }
}
