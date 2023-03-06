package com.cokroktosmok.beersandmealsappfront.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class DialogWindow {

    private Dialog dialog;
    private Button closeButton;
    private Span messageSpan1;

    private Span messageSpan2;

    public DialogWindow(String title, String message) {
        dialog=new Dialog();
        messageSpan1 =new Span();
        messageSpan1.setText(message);
        closeButton=new Button("ok",e->dialog.close());
        dialog.setHeaderTitle(title);
        dialog.add(messageSpan1);
        dialog.getFooter().add(closeButton);
    }

    public DialogWindow(String title, String message1,String message2) {
        dialog=new Dialog();
        messageSpan1 =new Span();
        messageSpan2=new Span();
        messageSpan1.setText(message1);
        messageSpan2.setText(message2);
        closeButton=new Button("ok",e->dialog.close());
        dialog.setHeaderTitle(title);
        dialog.add(new VerticalLayout(messageSpan1,messageSpan2));
        dialog.getFooter().add(closeButton);
    }


    public Dialog getDialog() {
        return dialog;
    }
}
