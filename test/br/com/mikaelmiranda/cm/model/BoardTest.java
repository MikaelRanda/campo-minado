package br.com.mikaelmiranda.cm.model;

import br.com.mikaelmiranda.cm.exception.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board = new Board(5, 5, 5);

    @BeforeEach
    void iniciarTabuleiro(){
        board = new Board(5, 5, 5);
    }

    @Test
    void testeAbrirCampo(){
        board.abrir(1, 1);
        Field campo = board.getField(1, 1);
        assertTrue(campo.isAberto());
    }

    @Test
    void testeAlternarMarcacaoCampo(){
        board.alternarMarcacao(1, 1);
        Field campo = board.getField(1, 1);
        assertTrue(campo.isMarcado());
    }

    @Test
    void testeGerarCampos(){
        assertEquals(25, board.getFields().size());
    }

    @Test
    void testeAssociarVizinhos(){
        Board board33 = new Board(3, 3, 0);
        Field campo = board33.getField(1, 1);
        assertEquals(8, campo.getVizinhos().size());

        Field campoBorda = board33.getField(0, 0);
        assertEquals(3, campoBorda.getVizinhos().size());
    }

    @Test
    void testeSortearMinas(){
        long camposMinados = board.getFields()
                .stream()
                .filter(Field::isMinado)
                .count();

        assertEquals(5, camposMinados);
    }

    @Test
    void testeObjetivoAlcancadoTabuleiro(){
        Board tabuleiroLimpo = new Board(5, 5, 0);
        tabuleiroLimpo.abrir(1, 1);
        assertTrue(tabuleiroLimpo.objetivoAlcancado());
    }

    @Test
    void testeReiniciarTabuleiro(){
        board.abrir(3, 2);
        board.reiniciar();
        Field campo = board.getField(1, 1);

        assertTrue(campo.isFechado());
    }

    @Test
    void testeToStringTabuleiroInicial(){
        String result =
                " ?  ?  ?  ?  ? \n"
              + " ?  ?  ?  ?  ? \n"
              + " ?  ?  ?  ?  ? \n"
              + " ?  ?  ?  ?  ? \n"
              + " ?  ?  ?  ?  ? \n";

        assertEquals(result, board.toString());
    }

    @Test
    void testeToStringAposAbrirCampo(){
        board.abrir(3, 2);
        String result = board.toString();

        assertTrue(result.contains("  "));
    }

    @Test
    void testeComCampoMarcado(){
        board.alternarMarcacao(2, 2);
        String result = board.toString();

        assertTrue(result.contains(" x "));
    }

    @Test
    void testeComCampoMinado(){
        Field campo = board.getField(0, 0);
        campo.minar();
        try {
            campo.abrir();
        } catch (ExplosaoException e){

        }

        String result = board.toString();
        assertTrue(result.contains(" * "));
    }
}
