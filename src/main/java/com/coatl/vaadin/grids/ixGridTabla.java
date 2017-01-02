/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.vaadin.grids;

import com.coatl.ed.TablaIF;
import com.coatl.ed.ixTablaEnMemoria;
import com.coatl.vaadin.abc.filtro.ixCambioDeFiltroGridTabla;
import com.coatl.vaadin.layouts.ixPanelTripleVertical;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author matus
 */
@Theme("tema_iu7")
public class ixGridTabla extends ixPanelTripleVertical
        implements SelectionListener, ClickListener
{

    private TablaIF tabla = null;

    private String columnasVisibles = null;
    Grid grid = null;
    //private List<Object[]> seleccionados = null;

    private String nombreTabla = null;
    private String columnas = null;

    //private FetchOptions fetchOptions;
    long pagina = 0;
    long renglonesPorPagina = 25;
    //private long numRengTotales;

    Button anterior = new Button("<Anterior");
    Button siguiente = new Button("Siguiente>");

    HorizontalLayout paginador = new HorizontalLayout();

    Label infoPaginas = new Label("-");
    Label infoRenglones = new Label("-");
    Label titulo = new Label();
    Label sepFiltro = new Label(" ");

    Label textoFiltro = new Label("Filtro: ");
    private TextField filtro = new TextField();

    private String activarFiltro = null;

    private String prefijoColumnaSeleccion = "ixgt_sel_";

    private HorizontalLayout encabezado = new HorizontalLayout();

    private Map<String, String> titulosColumnas = new HashMap();

    private CheckBox cualquiera = new CheckBox("Cualquiera");

    public ixGridTabla()
    {
        configurar();
    }

    public ixGridTabla(TablaIF t)
    {
        configurar();
    }

    public final void configurar()
    {
        getEncabezado().addComponent(titulo);
        titulo.setCaptionAsHtml(true);
        titulo.setStyleName("titulo2");

        sepFiltro.setCaptionAsHtml(true);
        sepFiltro.setWidth("21px");
        textoFiltro.setHeight("32px");

        getEncabezado().addComponent(sepFiltro);
        getEncabezado().addComponent(textoFiltro);
        textoFiltro.setStyleName("margintop4");

        filtro.addStyleName(ValoTheme.TEXTFIELD_TINY);
        getEncabezado().addComponent(getFiltro());
        getFiltro().addStyleName("margintop4");
        getEncabezado().addComponent(cualquiera);
        cualquiera.addStyleName("margintop6");
        //cualquiera.addStyleName("margin3");
        //cualquiera.addStyleName("margintop6");
        //this.getEncabezado().setMargin(true);

        this.setComponenteSuperior(getEncabezado());

        anterior.setHeight("25px");
        siguiente.setHeight("25px");

        paginador.addComponent(anterior);
        paginador.addComponent(infoPaginas);
        paginador.addComponent(siguiente);
        paginador.addComponent(infoRenglones);

        anterior.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                pagina--;
                if (pagina < 0)
                {
                    pagina = 0;
                }
                armarTabla();
            }
        });

        siguiente.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                pagina++;

                long maxpag = 0;
                if (renglonesPorPagina > 0)
                {
                    long tReng = 0;
                    if (tabla != null)
                    {
                        tReng = tabla.getTotalDeRenglones();
                    }
                    maxpag = tReng / renglonesPorPagina;
                }

                if (pagina > maxpag)
                {
                    pagina = maxpag;
                }
                armarTabla();
            }
        });

        cualquiera.addListener(new Property.ValueChangeListener()
        {
            public void valueChange(ValueChangeEvent event)
            {
                armarTabla();
            }
        });

        this.setComponenteInferior(paginador);

        filtro.addListener(new ixCambioDeFiltroGridTabla(this));

    }

    void configurarQuery()
    {

    }

    public void dobleClick(Map m)
    {
        System.out.println("CLICK> " + m.get("id"));
    }

    public ixTablaEnMemoria getTabla(long pagina, long renglonesPorPagina)
    {
        return null;
    }

    public void armarTabla()
    {

        this.tabla = getTabla(pagina, 25);

        grid = new Grid();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addSelectionListener(this);
        grid.addListener(new ItemClickEvent.ItemClickListener()
        {
            @Override
            public void itemClick(ItemClickEvent item)
            {
                if (item.isDoubleClick())
                {
                    Object[] renglon = tabla.getRenglon((Integer) item.getItemId() - 1);
                    Map<String, Object> m = tabla.getRenglonComoMapa(renglon);

                    dobleClick(m);
                }
            }
        });

        String[] colVis = this.getArregloColumnasVisibles();
        for (String t : colVis)
        {
            Class cl = getTabla().getClaseColumna(t);
            String titulo = getTituloColumna(t);
            grid.addColumn(titulo, cl);
        }

        //this.configurarEncabezados(grid);
        List<Object[]> lista = getTabla().getDatos();
        for (Object[] renglonOriginal : lista)
        {
            Object[] renglonGrid = new Object[colVis.length];
            int nc = 0;
            for (String t : colVis)
            {
                Object o = renglonOriginal[getTabla().getIndiceDeColumna(t)];
                if (o instanceof Boolean)
                {
                    if (((Boolean) o).booleanValue())
                    {
                        renglonGrid[nc] = "◉";
                    } else
                    {
                        //renglonGrid[nc] = "○";
                        renglonGrid[nc] = " ";
                    }

                } else if (o != null)
                {
                    renglonGrid[nc] = o.toString();
                } else
                {
                    renglonGrid[nc] = null;
                }
                nc++;
            }
            grid.addRow(renglonGrid);
        }

        grid.setSizeFull();
        hacerPaginador();

        this.setComponenteMedio(grid);
        seleccionar();
    }

    /*
     public void configurarEncabezados(Grid tabla)
     {

     }
     */
    public String getColumnaSeleccion()
    {
        return this.getPrefijoColumnaSeleccion();
    }

    public void seleccionar()
    {

        List<Object[]> lista = getTabla().getDatos();
        //System.out.println("Iterando para seleccionar ..." + lista.size());
        int n = 1;
        for (Object[] l : lista)
        {
            Map<String, Object> m = getTabla().getRenglonComoMapa(l);
            String v = (String) m.get(getColumnaSeleccion());
            //System.out.println("   +" + getColumnaSeleccion() + "=" + v);
            if (v != null)
            {
                if (v.equals("1"))
                {
                    grid.select(n);
                    //System.out.println("   +Seleccionando " + n);
                }
            }
            n++;
        }
    }

    public void hacerPaginador()
    {
        if (tabla.getTotalDeRenglonesFiltrados() == 0)
        {
            long nPaginas = (tabla.getTotalDeRenglones() / tabla.getRenglonesPorPagina());
            String infoPag = "| Página " + tabla.getPagina() + " de " + nPaginas + " |";
            this.infoPaginas.setValue(infoPag);

            long rengIni = (this.pagina * this.renglonesPorPagina + 1);
            long rengFin = (this.pagina * this.renglonesPorPagina + this.renglonesPorPagina);
            if (rengFin > tabla.getTotalDeRenglones())
            {
                rengFin = tabla.getTotalDeRenglones();
            }
            String infoReng = "| Renglón " + rengIni + " a " + rengFin + " de " + tabla.getTotalDeRenglones() + " |";
            this.infoRenglones.setValue(infoReng);
        } else
        {
            long nPaginas = (tabla.getTotalDeRenglonesFiltrados() / tabla.getRenglonesPorPagina());
            String infoPag = "| Página " + tabla.getPagina() + " de " + nPaginas + " |";
            this.infoPaginas.setValue(infoPag);

            long rengIni = (this.pagina * this.renglonesPorPagina + 1);
            long rengFin = (this.pagina * this.renglonesPorPagina + this.renglonesPorPagina);
            if (rengFin > tabla.getTotalDeRenglonesFiltrados())
            {
                rengFin = tabla.getTotalDeRenglonesFiltrados();
            }
            String infoReng = "| Renglón " + rengIni + " a " + rengFin + " de " + tabla.getTotalDeRenglonesFiltrados() + " filtrados, " + tabla.getTotalDeRenglones() + " totales |";
            this.infoRenglones.setValue(infoReng);
        }

    }

    /**
     * @return the columnasVisibles
     */
    public String getColumnasVisibles()
    {
        return columnasVisibles;
    }

    /**
     * @param columnasVisibles the columnasVisibles to set
     */
    public void setColumnasVisibles(String columnasVisibles)
    {
        this.columnasVisibles = columnasVisibles;
    }

    @Override
    public void select(SelectionEvent event)
    {

        Collection<Object> rows = grid.getSelectedRows();
        Set<Object> sels = event.getAdded();
        System.out.println("Seleccionados: " + sels.size());
        {
            Iterator<Object> i = sels.iterator();
            List<Map> l = new ArrayList();
            while (i.hasNext())
            {
                int n = (Integer) i.next() - 1;
                Object[] reng = getTabla().getRenglon(n);
                Map<String, Object> m = tabla.getRenglonComoMapa(reng);
                //String id = (String) m.get("id");
                //System.out.println("   +" + id);
                l.add(m);
                if (l.size() > 50)
                {
                    marcarSeleccionadosPorID(l);
                }
            }
            if (l.size() > 0)
            {
                marcarSeleccionadosPorID(l);
            }
        }

        Set<Object> dsels = event.getRemoved();
        System.out.println("Liberador    : " + dsels.size());
        {
            Iterator<Object> i = dsels.iterator();
            List l = new ArrayList();
            while (i.hasNext())
            {
                int n = (Integer) i.next() - 1;
                Object[] reng = getTabla().getRenglon(n);
                Map<String, Object> m = tabla.getRenglonComoMapa(reng);
                String id = (String) m.get("id");
                System.out.println("   +" + id);

                l.add(id);
                if (l.size() > 50)
                {
                    marcarDeseleccionadosPorID(l);
                }
            }
            if (l.size() > 0)
            {
                marcarDeseleccionadosPorID(l);
            }

        }
        /*
         Iterator<Object> i = rows.iterator();

         setSeleccionados(new ArrayList());

         while (i.hasNext())
         {
         int n = (int) i.next() - 1;

         Object[] reng = getTabla().getRenglon(n);
         System.out.println("   Seleccionado> " + n + ": " + reng[0]);

         getSeleccionados().add(reng);
         }
         */
    }

    public Map<String, Object> getRenglonComoMapa(Object[] renglon)
    {
        return getTabla().getRenglonComoMapa(renglon);
    }

    /**
     * @return the nombreTabla
     */
    public String getNombreTabla()
    {
        return nombreTabla;
    }

    /**
     * @param nombreTabla the nombreTabla to set
     */
    public void setNombreTabla(String nombreTabla)
    {
        this.nombreTabla = nombreTabla;
        configurarQuery();
    }

    /**
     * @return the columnas
     */
    public String getColumnas()
    {
        return columnas;
    }

    /**
     * @param columnas the columnas to set
     */
    public void setColumnas(String columnas)
    {
        this.columnas = columnas;
    }

    public String[] getArregloColumnasVisibles()
    {
        if (columnasVisibles != null)
        {
            //System.out.println("Las columnas visibles son: " + columnasVisibles);
            return columnasVisibles.split(",");
        }

        String cols[] = new String[getTabla().getColumnas().size()];

        int n = 0;
        for (String c : getTabla().getColumnas())
        {
            System.out.println("      +Columna " + n + ": " + c);
            cols[n] = c;
            n++;
        }

        return cols;
    }

    public void setTitulo(String titulo)
    {
        this.titulo.setValue(titulo);
    }

    /**
     * @return the encabezado
     */
    public HorizontalLayout getEncabezado()
    {
        return encabezado;
    }

    /**
     * @param encabezado the encabezado to set
     */
    public void setEncabezado(HorizontalLayout encabezado)
    {
        this.encabezado = encabezado;
    }

    /**
     * @return the titulosColumnas
     */
    public Map<String, String> getTitulosColumnas()
    {
        return titulosColumnas;
    }

    /**
     * @param titulosColumnas the titulosColumnas to set
     */
    public void setTitulosColumnas(Map<String, String> titulosColumnas)
    {
        this.titulosColumnas = titulosColumnas;
    }

    public String getTituloColumna(String col)
    {
        return this.titulosColumnas.get(col);
    }

    public void setTituloColumna(String n, String titulo)
    {
        this.titulosColumnas.put(n, titulo);
    }

    /**
     * @return the tabla
     */
    public TablaIF getTabla()
    {
        return tabla;
    }

    /**
     * @param tabla the tabla to set
     */
    public void setTabla(TablaIF tabla)
    {
        this.tabla = tabla;
    }

    @Override
    public void click(MouseEvents.ClickEvent event)
    {
        System.out.println("CLICK!");
    }

    public void marcarSeleccionadosPorID(List<Map> m)
    {

    }

    public void marcarDeseleccionadosPorID(List<Map> m)
    {

    }

    /**
     * @return the prefijoColumnaSeleccion
     */
    public String getPrefijoColumnaSeleccion()
    {
        return prefijoColumnaSeleccion;
    }

    /**
     * @param prefijoColumnaSeleccion the prefijoColumnaSeleccion to set
     */
    public void setPrefijoColumnaSeleccion(String prefijoColumnaSeleccion)
    {
        this.prefijoColumnaSeleccion = prefijoColumnaSeleccion;
    }

    /**
     * @return the activarFiltro
     */
    public String getActivarFiltro()
    {
        return activarFiltro;
    }

    /**
     * @param activarFiltro the activarFiltro to set
     */
    public void setActivarFiltro(String activarFiltro)
    {
        this.activarFiltro = activarFiltro;
    }

    /**
     * @return the filtro
     */
    public TextField getFiltro()
    {
        return filtro;
    }

    /**
     * @param filtro the filtro to set
     */
    public void setFiltro(TextField filtro)
    {
        this.filtro = filtro;
    }

    /**
     * @return the cualquiera
     */
    public CheckBox getCualquiera()
    {
        return cualquiera;
    }

    /**
     * @param cualquiera the cualquiera to set
     */
    public void setCualquiera(CheckBox cualquiera)
    {
        this.cualquiera = cualquiera;
    }

}
