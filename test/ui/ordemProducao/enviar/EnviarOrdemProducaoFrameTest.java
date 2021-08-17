/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.ordemProducao.enviar;

import javax.swing.JLabel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ui.principal.GerenteJanelas;

/**
 *
 * @author auxsecinfor1
 */
public class EnviarOrdemProducaoFrameTest {
    
    public EnviarOrdemProducaoFrameTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getTIPO_PROD method, of class EnviarOrdemProducaoFrame.
     */
    @Test
    public void testGetTIPO_PROD() {
        System.out.println("getTIPO_PROD");
        byte expResult = 0;
        byte result = EnviarOrdemProducaoFrame.getTIPO_PROD();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTIPO_PROD method, of class EnviarOrdemProducaoFrame.
     */
    @Test
    public void testSetTIPO_PROD() {
        System.out.println("setTIPO_PROD");
        byte TIPO_PROD = 0;
        EnviarOrdemProducaoFrame.setTIPO_PROD(TIPO_PROD);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVLR_ORC method, of class EnviarOrdemProducaoFrame.
     */
    @Test
    public void testGetVLR_ORC() {
        System.out.println("getVLR_ORC");
        double expResult = 0.0;
        double result = EnviarOrdemProducaoFrame.getVLR_ORC();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVLR_ORC method, of class EnviarOrdemProducaoFrame.
     */
    @Test
    public void testSetVLR_ORC() {
        System.out.println("setVLR_ORC");
        double VLR_ORC = 0.0;
        EnviarOrdemProducaoFrame.setVLR_ORC(VLR_ORC);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCOD_CONTATO method, of class EnviarOrdemProducaoFrame.
     */
    @Test
    public void testGetCOD_CONTATO() {
        System.out.println("getCOD_CONTATO");
        int expResult = 0;
        int result = EnviarOrdemProducaoFrame.getCOD_CONTATO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCOD_CONTATO method, of class EnviarOrdemProducaoFrame.
     */
    @Test
    public void testSetCOD_CONTATO() {
        System.out.println("setCOD_CONTATO");
        int COD_CONTATO = 0;
        EnviarOrdemProducaoFrame.setCOD_CONTATO(COD_CONTATO);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCOD_ENDERECO method, of class EnviarOrdemProducaoFrame.
     */
    @Test
    public void testGetCOD_ENDERECO() {
        System.out.println("getCOD_ENDERECO");
        int expResult = 0;
        int result = EnviarOrdemProducaoFrame.getCOD_ENDERECO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCOD_ENDERECO method, of class EnviarOrdemProducaoFrame.
     */
    @Test
    public void testSetCOD_ENDERECO() {
        System.out.println("setCOD_ENDERECO");
        int COD_ENDERECO = 0;
        EnviarOrdemProducaoFrame.setCOD_ENDERECO(COD_ENDERECO);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstancia method, of class EnviarOrdemProducaoFrame.
     */
    @Test
    public void testGetInstancia() {
        System.out.println("getInstancia");
        JLabel loading = null;
        GerenteJanelas gj = null;
        EnviarOrdemProducaoFrame expResult = null;
        EnviarOrdemProducaoFrame result = EnviarOrdemProducaoFrame.getInstancia(loading, gj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of estadoPe method, of class EnviarOrdemProducaoFrame.
     */
    @Test
    public void testEstadoPe() {
        System.out.println("estadoPe");
        EnviarOrdemProducaoFrame.estadoPe();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
