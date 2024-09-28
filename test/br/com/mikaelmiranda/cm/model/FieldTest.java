package br.com.mikaelmiranda.cm.model;

import br.com.mikaelmiranda.cm.exception.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private Field field = new Field(3, 3);

    @BeforeEach
    void iniciarCampo(){
        field = new Field(3, 3);
    }

    @Test
    void testeVizinhoDistancia1Esquerda(){
        Field vizinho = new Field(3, 2);
        boolean result = field.adicionarVizinhos(vizinho);
        assertTrue(result);
    }

    @Test
    void testeVizinhoDistancia1Direita(){
        Field vizinho = new Field(3, 4);
        boolean result = field.adicionarVizinhos(vizinho);
        assertTrue(result);
    }

    @Test
    void testeVizinhoDistancia1EmCima(){
        Field vizinho = new Field(2, 3);
        boolean result = field.adicionarVizinhos(vizinho);
        assertTrue(result);
    }

    @Test
    void testeVizinhoDistancia1EmBaixo(){
        Field vizinho = new Field(4, 3);
        boolean result = field.adicionarVizinhos(vizinho);
        assertTrue(result);
    }

    @Test
    void testeVizinhoDistancia2(){
        Field vizinho = new Field(2, 2);
        boolean result = field.adicionarVizinhos(vizinho);
        assertTrue(result);
    }

    @Test
    void testeNaoVizinho(){
        Field vizinho = new Field(1, 1);
        boolean result = field.adicionarVizinhos(vizinho);
        assertFalse(result);
    }

    @Test
    void testeValorPadraoAtributoMarcado(){
        assertFalse(field.isMarcado());
    }

    @Test
    void testeAlternarMarcacao(){
        field.alternarMarcacao();
        assertTrue(field.isMarcado());
    }

    @Test
    void testeAlternarMarcacaoDuasChamadas(){
        field.alternarMarcacao();
        field.alternarMarcacao();
        assertFalse(field.isMarcado());
    }

    @Test
    void testeAbrirNaoMinadoNaoMarcado(){
        assertTrue(field.abrir());
    }

    @Test
    void testeAbrirNaoMinadoMarcado(){
        field.alternarMarcacao();
        assertFalse(field.abrir());
    }

    @Test
    void testeAbrirMinadoMarcado(){
        field.alternarMarcacao();
        field.minar();
        assertFalse(field.abrir());
    }

    @Test
    void testeAbrirMinadoNaoMarcado(){
        field.minar();

        assertThrows(ExplosaoException.class, () -> {
            field.abrir();
        });
    }

    @Test
    void testeAbrirComVizinhoDefault(){
        Field campo11 = new Field(1, 1);
        Field campo22 = new Field(2, 2);

        campo22.adicionarVizinhos(campo11);

        field.adicionarVizinhos(campo22);
        field.abrir();

        assertTrue(campo22.isAberto() && campo11.isAberto());
    }

    @Test
    void testeAbrirComVizinhoMinado(){
        Field campo11 = new Field(1, 1);
        Field campo12 = new Field(1, 2);

        campo12.minar();

        Field campo22 = new Field(2, 2);
        campo22.adicionarVizinhos(campo11);
        campo22.adicionarVizinhos(campo12);

        field.adicionarVizinhos(campo22);
        field.abrir();

        assertTrue(campo22.isAberto() && campo12.isFechado());
    }

    @Test
    void testeObjetivoAlcançadoDesvendado(){
        field.abrir();
        assertTrue(field.objetivoAlcancado());
    }

    @Test
    void testeObjetivoAlcancadoProtegido(){
        Field campo11 = new Field(1, 1);

        campo11.minar();

        campo11.alternarMarcacao();

        assertTrue(campo11.objetivoAlcancado());
    }

    @Test
    void testeObjetivoNaoAlcancadoCampoMinado(){
        field.minar();
        assertFalse(field.objetivoAlcancado());
    }

    @Test
    void testeObjetivoNaoAlcancadoCampoSeguroFechado(){
        assertFalse(field.objetivoAlcancado());
    }

    @Test
    void testeMinasNaVizinhancaContador(){
        Field vizinhoMinado1 = new Field(2, 2);
        vizinhoMinado1.minar();

        Field vizinhoMinado2 = new Field(2, 4);
        vizinhoMinado2.minar();

        Field vizinhoMinado3 = new Field(3, 4);
        vizinhoMinado3.minar();

        Field vizinhoMinado4 = new Field(4, 4);
        vizinhoMinado4.minar();

        Field vizinhoNaoMinado1 = new Field(2, 3);
        Field vizinhoNaoMinado2 = new Field(3, 2);
        Field vizinhoNaoMinado3 = new Field(4, 2);
        Field vizinhoNaoMinado4 = new Field(4, 3);

        field.adicionarVizinhos(vizinhoMinado1);
        field.adicionarVizinhos(vizinhoMinado2);
        field.adicionarVizinhos(vizinhoMinado3);
        field.adicionarVizinhos(vizinhoMinado4);
        field.adicionarVizinhos(vizinhoNaoMinado1);
        field.adicionarVizinhos(vizinhoNaoMinado2);
        field.adicionarVizinhos(vizinhoNaoMinado3);
        field.adicionarVizinhos(vizinhoNaoMinado4);

        assertEquals(4, field.minasNaVizinhanca());
    }

    @Test
    void testeReiniciar(){
        field.abrir();
        field.minar();
        field.alternarMarcacao();

        field.reiniciar();

        assertFalse(field.isAberto());
        assertFalse(field.isMinado());
        assertFalse(field.isMarcado());
    }

    @Test
    void testeToStringMarcado(){
        field.alternarMarcacao();
        assertEquals("x", field.toString());
    }

    @Test
    void testeToStringAbertoEMinado(){
        field.minar();
        try {
            field.abrir();
        } catch (ExplosaoException e) {}

        assertEquals("*", field.toString());
    }

    @Test
    void testeToStringContadorMinas(){
        Field campoMinado1 = new Field(2, 2);
        campoMinado1.minar();
        Field campoMinado2 = new Field(2, 4);
        campoMinado2.minar();
        Field campoNaoMinado = new Field(2, 3);

        field.adicionarVizinhos(campoMinado1);
        field.adicionarVizinhos(campoMinado2);
        field.adicionarVizinhos(campoNaoMinado);

        field.abrir();

        assertEquals("2", field.toString());
    }

    @Test
    void testeToStringAberto(){
        field.abrir();
        assertEquals(" ", field.toString());
    }

    @Test
    void testeToStringFechado(){
        assertEquals("?", field.toString());
    }

    @Test
    void testeGetLinha(){
        Field campo53 = new Field(5, 3);
        assertEquals(5, campo53.getLinha());
    }

    @Test
    void testeGetColuna(){
        Field campo53 = new Field(5, 3);
        assertEquals(3, campo53.getColuna());
    }
}
