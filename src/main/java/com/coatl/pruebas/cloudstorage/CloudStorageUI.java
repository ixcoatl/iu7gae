/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.pruebas.cloudstorage;

import com.google.api.services.storage.Storage;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
//import com.google.cloud.Page;
//import com.google.cloud.RetryParams;
import com.google.cloud.storage.Blob;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author matus
 */
@Theme("mytheme")
public class CloudStorageUI extends UI
{

    private TextField nombre;
    private VerticalLayout layout;
    private VerticalLayout comandos;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        this.layout = new VerticalLayout();
        layout.addComponent(new Label("Pruebas de DataStore"));

        setContent(layout);
        //this.listarCubeta();
    }
}
