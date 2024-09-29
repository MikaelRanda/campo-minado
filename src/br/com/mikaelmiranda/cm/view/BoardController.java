package br.com.mikaelmiranda.cm.view;

import br.com.mikaelmiranda.cm.exception.CloseGameException;
import br.com.mikaelmiranda.cm.exception.ExplosaoException;
import br.com.mikaelmiranda.cm.model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BoardController {

    private Board board;
    private Scanner entrada = new Scanner(System.in);

    public BoardController(Board board){
        this.board = board;

        executarJogo();
    }

    private void executarJogo() {
        try{
            boolean continuar = true;

            while (continuar){
                cicloDoJogo();

                System.out.println("Outra partida? (S/n) ");
                String resposta = entrada.nextLine();

                if ("n".equalsIgnoreCase(resposta)){
                    continuar = false;
                } else {
                    board.reiniciar();
                }
            }
        } catch (CloseGameException e){
            System.out.println("Tchau!!!");
        } finally {
            entrada.close();
        }
    }

    private void cicloDoJogo() {
        try {

            while (!board.objetivoAlcancado()){
                System.out.println(board);

                String digitado = capturarValorDigitado("Digite (x, y): ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");

                if ("1".equalsIgnoreCase(digitado)){
                    board.abrir(xy.next(), xy.next());
                } else if ("2".equalsIgnoreCase(digitado)){
                    board.alternarMarcacao(xy.next(), xy.next());
                }
            }

            System.out.println("Você ganhou!");
        } catch (ExplosaoException e){
            System.out.println(board);
            System.out.println("Você perdeu!");
        }
    }

    private String capturarValorDigitado(String texto){
        System.out.print(texto);
        String digitado = entrada.nextLine();

        if ("sair".equalsIgnoreCase(digitado)){
            throw new CloseGameException();
        }

        return digitado;
    }
}
