package it.polimi.se2019;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import javax.swing.*;

public class GUIPlayerViewTest {

    private JFrame dialog;

    @Before
    void setUp(){
        dialog = new JFrame();
    }

    @Test
    void dialog_setVisible_mustShow(){
        Object[] options = {"Yes", "No"};
        int answer = JOptionPane.showOptionDialog(dialog, "Hello world!","first window", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        Assert.assertEquals(answer, 0);
    }
}
