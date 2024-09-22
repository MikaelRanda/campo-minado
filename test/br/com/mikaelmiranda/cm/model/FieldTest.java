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
}
