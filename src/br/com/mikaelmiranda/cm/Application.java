package br.com.mikaelmiranda.cm;

import br.com.mikaelmiranda.cm.model.Board;
import br.com.mikaelmiranda.cm.view.BoardController;

public class Application {

    public static void main(String[] args){

        Board board = new Board(6, 6, 6);

        new BoardController(board);
    }
}
