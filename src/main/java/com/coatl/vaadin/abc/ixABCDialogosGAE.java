/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.vaadin.abc;

import com.coatl.appengine.IU7;
import com.coatl.appengine.datastore.ixDataStore;
import com.coatl.ed.ixTablaEnMemoria;
import com.coatl.vaadin.ixUI;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import java.util.Map;

/**
 *
 * @author matus
 */
public class ixABCDialogosGAE extends ixABCDialogos
{

    public ixABCDialogosGAE(ixUI ui)
    {

        super(ui);

    }

    /*
    *
    * CONFIGURAR LAS COLUMNAS DE LOS ENCABEZADOS
    *
     */
    @Override
    public void configurarEncabezados(Grid grid)
    {

        // Create a header row to hold column filters
        HeaderRow filterRow = grid.appendHeaderRow();

        System.out.println("Filtros:");
        // Set up a filter for all columns
        int nCol = 0;
        String[] colVis = this.getArregloColumnasVisibles();
        for (Object pid : grid.getContainerDataSource()
                .getContainerPropertyIds())
        {
            HeaderCell cell = filterRow.getCell(pid);

            // Have an input field to use for filter
            TextField filterField = new TextField();
            cell.setComponent(filterField);

            System.out.println("    +Haciendo filtro para: " + pid + ": " + colVis[nCol]);
            ixDefinicionDeColumna defCol = this.getDefinicionDeColumna(colVis[nCol]);

            filterField.addStyleName(ValoTheme.TEXTFIELD_TINY);
            filterField.setInputPrompt("Filtro");


            /*
            // Update filter When the filter input is changed
            filterField.addTextChangeListener(change ->
                    {
                        // Can't modify filters so need to replace
                        container.removeContainerFilters(pid);

                        // (Re)create the filter if necessary
                        if (!change.getText().isEmpty())
                        {
                            container.addContainerFilter(
                                    new SimpleStringFilter(pid,
                                                           change.getText(), true, false));
                        }
            });
             */
            nCol++;
        }
    }

    /*
    *
    * CONFIGURAR LA SELECCIÓN
    *
     */
    @Override
    public String getColumnaSeleccion()
    {
        return new StringBuilder(this.getPrefijoColumnaSeleccion()).
                append(this.getIxUI().getPropS("usuario", "ninguno")).toString();
    }

    @Override
    public void marcarSeleccionadosPorID(List<String> m)
    {
        //System.out.println("Marcando " + m.size() + " ids seleccionados ");
        IU7.ds.fijarAtributoPorIDS(this.getNombreTabla(), m, getColumnaSeleccion(), "1");
    }

    @Override
    public void marcarDeseleccionadosPorID(List<String> m)
    {
        //System.out.println("Marcando " + m.size() + " ids deseleccionados ");
        IU7.ds.fijarAtributoPorIDS(this.getNombreTabla(), m, getColumnaSeleccion(), "0");
    }

    /*
    * La tabla
     */
    @Override
    public ixTablaEnMemoria getTabla(long pagina, long renglonesPorPagina)
    {
        //DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query(this.getNombreTabla());
        PreparedQuery preparedQuery = IU7.ds.getDS().prepare(query);
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

        long numRengTotales = preparedQuery.countEntities(fetchOptions);

        ixTablaEnMemoria tabla = new ixTablaEnMemoria();
        tabla.setTotalDeRenglones(numRengTotales);
        tabla.setPagina(pagina);
        tabla.setRenglonesPorPagina(renglonesPorPagina);

        String cols = this.getColumnas() + "," + getColumnaSeleccion();
        //System.out.println("Columnas de tabla: " + cols);

        setTabla(new ixTablaEnMemoria());
        IU7.ds.getBuscarEnTabla(
                preparedQuery,
                fetchOptions,
                cols,
                tabla,
                pagina * renglonesPorPagina, // La posicion de inicio
                renglonesPorPagina
        );

        return tabla;
    }

    /*
    * CREAR
     */
    @Override
    public void crear()
    {
        //System.out.println("Creando");
        Map m = defFormaCrear.getMapa();
        if (!this.antesDeCrear(m))
        {
            return;
        }
        ixDataStore datastore = new ixDataStore();
        datastore.guardar(this.getNombreTabla(), m);
        this.armarTabla();
        this.cerrarDialogos();
    }


    /*
    * EDITAR
     */
    @Override
    public void guardar()
    {
        //System.out.println("Guardando");
        Map m = defFormaEditar.getMapa();
        //ixDataStore datastore = new ixDataStore();
        m = IU7.ds.buscarObjeto(this.getNombreTabla(), m.get("id"));
        m = defFormaEditar.getMapa(m);

        IU7.ds.guardar(this.getNombreTabla(), m);
        this.armarTabla();
    }


    /*
    * BORRAR
     */
    @Override
    public void borrar()
    {
        //System.out.println("Borrar");
        Map m = defFormaBorrar.getMapa();
        this.cerrarDialogos();
        //ixDataStore datastore = new ixDataStore();
        IU7.ds.borrar(this.getNombreTabla(), (String) m.get("id"));
        this.armarTabla();
    }

    @Override
    public void dobleClick(Map m)
    {
        Object id = m.get("id");
        //System.out.println("Recargando objeto ...");
        m = IU7.ds.buscarObjeto(this.getNombreTabla(), id);
        if (m == null)
        {
            return;
        }

        System.out.println("CLICK> " + m.get("id"));
        abrirEditar();
        this.defFormaEditar.vaciarMapa(m);
    }
}
