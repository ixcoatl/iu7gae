/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.pruebas.datastore;

import com.coatl.appengine.datastore.ixDataStore;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import com.coatl.appengine.IU7;
import com.coatl.ed.TablaIF;
import com.coatl.ed.ixTablaEnMemoria;
import com.coatl.vaadin.grids.ixGridTabla;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author matus
 */
@Theme("tema_iu7")
public class DataStoreUI extends UI
{

    ixGridTabla grid = new ixGridTabla();
    private TextField tabla;
    private VerticalLayout principal;
    private VerticalLayout lista;
    private VerticalLayout comandos;
    private TextField nombre;
    private FormLayout forma;
    private TextField ap1;
    private TextField ap2;
    private Label renglones;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        this.principal = new VerticalLayout();
        {
            principal.addComponent(new Label("Pruebas de DataStore"));
            HorizontalLayout superior = new HorizontalLayout();
            principal.addComponent(superior);

            this.forma = new FormLayout();
            superior.addComponent(forma);
            {
                this.tabla = new TextField("Tabla");
                tabla.setValue("iq3_usuarios");
                forma.addComponents(tabla);

                this.nombre = new TextField("Nombre");
                forma.addComponents(nombre);
                this.ap1 = new TextField("Apellido Paterno");
                forma.addComponents(ap1);
                this.ap2 = new TextField("Apellido Materno");
                forma.addComponents(ap2);
                this.renglones = new Label("Renglones");
                forma.addComponents(renglones);

            }

            FormLayout botones = new FormLayout();
            /*
            botones.addComponent(new Label(" "));
            botones.addComponent(new Label("Acciones:"));
             */
            superior.addComponent(botones);

            Button recargar = new Button("Recargar");
            botones.addComponent(recargar);
            recargar.addClickListener(new Button.ClickListener()
            {
                @Override
                public void buttonClick(Button.ClickEvent event)
                {
                    listarObjetos();
                }
            });

            Button guardar = new Button("Guardar");
            botones.addComponent(guardar);
            guardar.addClickListener(new Button.ClickListener()
            {
                @Override
                public void buttonClick(Button.ClickEvent event)
                {
                    Map m = new HashMap();
                    m.put("nombre", nombre.getValue());
                    m.put("ap1", ap1.getValue());
                    m.put("ap2", ap2.getValue());
                    m.put("id", nombre.getValue());

                    IU7.ds.guardar(tabla.getValue(), m);
                    System.out.println("Objeto guardado");
                    listarObjetos();
                }
            });

            Button borrar = new Button("Borrar");
            botones.addComponent(borrar);
            borrar.addClickListener(new Button.ClickListener()
            {
                @Override
                public void buttonClick(Button.ClickEvent event)
                {
                    borrar();
                }
            });

            Button mil = new Button("Hacer 1,000");
            botones.addComponent(mil);
            mil.addClickListener(new Button.ClickListener()
            {
                @Override
                public void buttonClick(Button.ClickEvent event)
                {
                    for (int i = 0; i < 1000; i++)
                    {
                        Map m = new HashMap();
                        String id = "ID_" + i + "_" + Math.random();
                        m.put("id", id);
                        m.put("nombre", "Nombre_" + id + "_" + Math.random());
                        m.put("ap1", "Ap1_" + id + "_" + Math.random());
                        m.put("ap2", "Ap2_" + id + "_" + Math.random());
                        IU7.ds.guardar(tabla.getValue(), m);
                        System.out.println(" >" + id);
                    }
                }
            });

        }

        VerticalSplitPanel vsl = new VerticalSplitPanel();
        setContent(vsl);
        vsl.setFirstComponent(principal);

        grid.setSizeFull();
        grid.setComponenteSuperior(new Label("primer panel"));
        //grid.setComponenteMedio(new Label("segundo panel"));
        //grid.setComponenteInferior(new Label("tercer panel"));
        grid.setNombreTabla(tabla.getValue());
        grid.setColumnas("id,nombre,ap1,ap2");
        grid.setColumnasVisibles("nombre,ap1,ap2");

        vsl.setSecondComponent(grid);
        //grid.setComponenteMedio(grid);
        this.listarObjetos();
    }

    public void listarObjetos()
    {
        if (true)
        {
            grid.armarTabla();
            return;
        }

        System.out.println("Listando objetos");
        try
        {
            Thread.sleep(400);
        } catch (Exception e)
        {

        }

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query q = new Query(tabla.getValue());
        PreparedQuery pq = datastore.prepare(q);
        FetchOptions fo = FetchOptions.Builder.withDefaults();

        TablaIF tabla = new ixTablaEnMemoria();
        IU7.ds.getBuscarEnTabla(pq, fo, "id,nombre,ap1,ap2", tabla, 0, 10);

        grid.setTituloColumna("id", "ID");
        grid.setTituloColumna("nombre", "Nombre");
        grid.setTituloColumna("ap1", "Apellido Paterno");
        grid.setTituloColumna("ap2", "Apellido Materno");

        grid.setColumnasVisibles("nombre,ap1,ap2");

        //grid.setTabla(tabla);
        renglones.setValue("" + pq.countEntities(fo));

    }

    public void borrar()
    {
        /*
        List<Object[]> sel = grid.getSeleccionados();

        for (Object[] reng : sel)
        {
            String id = (String) grid.getRenglonComoMapa(reng).get("id");
            System.out.println("Borrando> " + id);
            IU7.ds.borrar(tabla.getValue(), id);
        }

        listarObjetos();
         */
    }
}
