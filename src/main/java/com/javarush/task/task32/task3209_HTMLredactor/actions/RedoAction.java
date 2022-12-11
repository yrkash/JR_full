package com.javarush.task.task32.task3209_HTMLredactor.actions;

import com.javarush.task.task32.task3209_HTMLredactor.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction {

    private View view;

    public RedoAction(View view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.redo();
    }

}
