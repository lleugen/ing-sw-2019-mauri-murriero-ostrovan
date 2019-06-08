package it.polimi.se2019;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import javax.swing.*;

public class GUIPlayerViewTest {

    static JFrame dialog;

    @BeforeClass
    public static void setUp(){
        dialog = new JFrame();
    }

    @Test
    public void dialog_setVisible_mustShow(){
        Object[] options = {"Yes", "No"};
        int answer = JOptionPane.showOptionDialog(dialog, "Hello world!","first window", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        Assert.assertEquals(answer, 0);
    }
}
