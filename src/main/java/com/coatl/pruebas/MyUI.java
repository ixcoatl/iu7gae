/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.pruebas;

import com.coatl.appengine.datastore.ixDataStore;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
public class MyUI extends UI
{

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        final VerticalLayout layout = new VerticalLayout();

        final TextField name = new TextField();
        name.setCaption("Escribe algo aquí:");

        Button button = new Button("Dime que escribí");

        button.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                System.out.println("Click!");
                layout.addComponent(new Label("Usted escribió> " + name.getValue()));
            }
        });

        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }
}
