package br.com.mikaelmiranda.cm.model;

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
}
