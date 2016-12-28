/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.vaadin.abc;

import com.coatl.appengine.IU7;
import com.coatl.appengine.datastore.ixDataStore;
import com.coatl.ed.filtros.ixFiltro;
import com.coatl.ed.ixTablaEnMemoria;
import com.coatl.vaadin.abc.filtro.ixFiltroDeTexto;
import com.coatl.vaadin.ixUI;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
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
 /*
    @Override
    public void configurarEncabezados(Grid grid)
    {
        HeaderRow rengFiltros = null;

        int nCol = 0;
        String[] colVis = this.getArregloColumnasVisibles();
        for (Object pid
             : grid.getContainerDataSource()
                .getContainerPropertyIds())
        {
            ixDefinicionDeColumna defCol = this.getDefinicionDeColumna(colVis[nCol]);

            if (defCol.tieneFiltro())
            {
                //System.out.println("Se requiere filtro para: " + defCol.getNombre());
                if (rengFiltros == null)
                {
                    //System.out.println("... haciendo renglon de filtros");
                    rengFiltros = grid.appendHeaderRow();
                }
                HeaderCell cell = rengFiltros.getCell(pid);
                ixFiltroDeTexto filtro = new ixFiltroDeTexto(this, defCol);
                cell.setComponent(filtro);
                if (this.getActivarFiltro() != null)
                {
                    if (defCol.getNombre().equals(this.getActivarFiltro()))
                    {
                        filtro.getTexto().focus();
                        filtro.getTexto().setCursorPosition(10000);
                    }
                }
            }

            nCol++;
        }
        //System.out.println("Activar filtro: " + this.getActivarFiltro());
    }

    private void configurarFiltros(Query query)
    {
        String[] colVis = this.getArregloColumnasVisibles();
        int nCol = 0;
        List<Filter> filtros = new ArrayList();
        for (String col : colVis)
        {
            ixDefinicionDeColumna defCol = this.getDefinicionDeColumna(colVis[nCol]);

            if (defCol.tieneFiltro())
            {
                String cadf = defCol.getFiltro();
                if (cadf != null && cadf.length() > 0)
                {

                    if (colVis[nCol].equals("id"))
                    {

                        Key llave = KeyFactory.createKey(this.getNombreTabla(), cadf);
                        Filter propertyFilter
                               = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
                                                     FilterOperator.GREATER_THAN_OR_EQUAL,
                                                     llave);
                        filtros.add(propertyFilter);

                        llave = KeyFactory.createKey(this.getNombreTabla(), cadf + "\ufffd");
                        propertyFilter
                        = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
                                              FilterOperator.LESS_THAN,
                                              llave);
                        filtros.add(propertyFilter);
                        System.out.println("  Filtrando ID " + colVis[nCol] + " >= " + cadf);
                    } else
                    {

                        Filter propertyFilter
                               = new FilterPredicate(colVis[nCol],
                                                     FilterOperator.GREATER_THAN_OR_EQUAL,
                                                     cadf);
                        filtros.add(propertyFilter);
                        propertyFilter
                        = new FilterPredicate(colVis[nCol],
                                              FilterOperator.LESS_THAN,
                                              cadf + "\ufffd");
                        filtros.add(propertyFilter);
                        //filtros.add(propertyFilter);
                        System.out.println("  Filtrando propiedad " + colVis[nCol] + " >= " + cadf);
                    }
                }
            }
            nCol++;
        }
        if (filtros.size() > 1)
        {
            CompositeFilter cf = new CompositeFilter(CompositeFilterOperator.AND, filtros);
            query.setFilter(cf);
        }
        if (filtros.size() == 1)
        {

            query.setFilter(filtros.get(0));
        }

    }
     */
    public ixFiltro getFiltros()
    {
        String v = this.getFiltro().getValue().trim();
        if (v.equals(""))
        {
            return new ixFiltro();
        }

        ixFiltro f = new ixFiltro();
        f.iniciarY();
        f.agregarCuaquierCampoContieneCadenaIgnCaso(v);
        f.terminarTodo();
        return f;
    }

    /*
    * La tabla
     */
    @Override
    public ixTablaEnMemoria getTabla(long pagina, long renglonesPorPagina)
    {
        //DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query(this.getNombreTabla());

        //configurarFiltros(query);
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
                renglonesPorPagina,
                getFiltros()
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

}
