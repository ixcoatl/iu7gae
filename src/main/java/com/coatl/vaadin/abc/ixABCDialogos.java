/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.vaadin.abc;

import com.coatl.vaadin.grids.ixGridTabla;
import com.coatl.vaadin.ixUI;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author matus
 */
public class ixABCDialogos extends ixGridTabla
{

    private String formaCreacion = null;
    private String formaEdicion = null;
    private String formaBorrado = null;

    Map<String, ixDefinicionDeColumna> columnas = new HashMap<String, ixDefinicionDeColumna>();

    Button preguntarCrear = new Button("\u2605 Crear");
    Button recargar = new Button("\uD83D\uDD0D Buscar");

    Button bCrear = new Button("\u2605 Crear");
    Button bGuardar = new Button("\uD83D\uDCBE Guardar");
    Button bBorrar = new Button("\u232B Borrar");
    Button bConfBorrar = new Button("\u232B Confirmar borrar");

    Label espaciador1 = new Label();
    Label lBusqueda = new Label();
    TextField buqueda = new TextField();

    private ixUI ixUI;

    public ixABCDialogos(ixUI ui)
    {
        this.ixUI = ui;
        configurarInterfaz();
    }

    ixDefinicionDeForma getForma(String forma, String tipo)
    {
        Stack<AbstractComponent> pila = new Stack();
        Map<String, AbstractComponent> componentes = new HashMap();
        AbstractComponent ultimo = null;

        String[] cmds = forma.split(" ");

        for (String cmd : cmds)
        {
            cmd = cmd.trim();
            if (!cmd.equals(""))
            {
                System.out.println("CMD> [" + cmd + "]");
                if (cmd.equals("bConfBorrar"))
                {
                    agregar(pila, bConfBorrar);

                } else if (cmd.equals("bBorrar"))
                {
                    agregar(pila, bBorrar);

                } else if (cmd.equals("bGuardar"))
                {
                    agregar(pila, bGuardar);

                } else if (cmd.equals("bCrear"))
                {
                    agregar(pila, bCrear);
                } else if (cmd.startsWith("H"))
                {
                    HorizontalLayout hl = new HorizontalLayout();
                    if (cmd.contains("m"))
                    {
                        hl.setMargin(true);
                    }
                    agregar(pila, hl);
                } else if (cmd.startsWith("V"))
                {
                    VerticalLayout vl = new VerticalLayout();
                    if (cmd.contains("m"))
                    {
                        vl.setMargin(true);
                    }
                    agregar(pila, vl);
                } else if (cmd.startsWith("F"))
                {
                    FormLayout fl = new FormLayout();
                    if (cmd.contains("m"))
                    {
                        fl.setMargin(true);
                    }
                    agregar(pila, fl);
                } else if (cmd.equals("."))
                {
                    ultimo = pila.pop();
                    System.out.println("Sacando de pila a " + ultimo);
                } else if (cmd.startsWith("'"))
                {
                    String txt = cmd.substring(1);
                    while (txt.contains("~"))
                    {
                        txt = txt.replace("~", " ");
                    }
                    Label l = new Label(txt);

                    agregar(pila, l);
                } else
                {
                    System.out.println("Buscando columna [" + cmd + "]");
                    ixDefinicionDeColumna columna = columnas.get(cmd);
                    String claseControl = columna.getClaseControl();
                    if (claseControl == null)
                    {
                        claseControl = "com.vaadin.ui.TextField";
                    }

                    AbstractComponent con = null;

                    try
                    {
                        con = (AbstractComponent) this.getClass().
                                getClassLoader().
                                loadClass(claseControl).
                                newInstance();
                    } catch (Exception ex)
                    {
                        System.out.println("ERROR INSTANCIANDO> [" + claseControl + "], " + ex);
                    }
                    con.setCaption(this.getTituloColumna(cmd));
                    if (con != null)
                    {
                        agregar(pila, con);
                        componentes.put(cmd, con);
                    }

                    if (tipo != null)
                    {
                        if (tipo.toLowerCase().equals("c"))
                        {
                            if (columna.isSoloLecturaCrear() || columna.isSoloLectura())
                            {
                                con.setReadOnly(true);
                            }
                        }
                        if (tipo.toLowerCase().equals("e") || tipo.toLowerCase().equals("g"))
                        {
                            if (columna.isSoloLecturaGuardar() || columna.isSoloLectura())
                            {
                                con.setReadOnly(true);
                            }
                        }
                        if (tipo.toLowerCase().equals("b"))
                        {

                            con.setReadOnly(true);
                        }
                    }

                }
            }
        }

        while (pila.size() > 0)
        {
            ultimo = pila.pop();
        }

        ixDefinicionDeForma res = new ixDefinicionDeForma();
        res.setComponente(ultimo);
        res.setComponentes(componentes);

        return res;
    }

    private void agregar(Stack<AbstractComponent> pila, AbstractComponent comp)
    {

        try
        {
            if (pila.size() == 0)
            {
                System.out.println("   +Agregando " + comp + " a pila");
                pila.add(comp);
                return;
            }

            System.out.println("   +Agregando " + comp + " a " + pila);
            Layout lo = (Layout) pila.peek();
            lo.addComponent(comp);

            if (comp instanceof Layout)
            {
                pila.add(comp);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return;
    }

    @Override
    public void dobleClick(Map m)
    {

    }

    /*
    * CREAR
     */
    public ixDefinicionDeForma defFormaCrear = null;

    public void abrirCrear()
    {
        cerrarDialogos();
        defFormaCrear = getFormaCrear();

        Window subWindow = new Window("Crear");

        subWindow.setContent(defFormaCrear.getComponente());
        getIxUI().addWindow(subWindow);
        subWindow.center();

        this.defFormaCrear.setVentana(subWindow);
    }

    public void crear()
    {

    }

    public ixDefinicionDeForma getFormaCrear()
    {

        ixDefinicionDeForma forma = this.getForma(this.getFormaCreacion(), "c");

        return forma;
    }

    public boolean antesDeCrear(Map m)
    {
        return true;
    }

    /*
    * EDITAR
     */
    ixDefinicionDeForma defFormaEditar = null;

    public void abrirEditar()
    {
        cerrarDialogos();
        defFormaEditar = getFormaEditar();

        Window subWindow = new Window("Editar");

        subWindow.setContent(defFormaEditar.getComponente());
        getIxUI().addWindow(subWindow);
        subWindow.center();

        this.defFormaEditar.setVentana(subWindow);
    }

    public void guardar()
    {

    }

    public ixDefinicionDeForma getFormaEditar()
    {

        ixDefinicionDeForma forma = this.getForma(this.getFormaEdicion(), "e");

        return forma;
    }


    /*
    * BORRAR
     */
    ixDefinicionDeForma defFormaBorrar = null;

    public void abrirBorrar()
    {
        if (defFormaEditar == null)
        {
            return;
        }

        Map m = defFormaEditar.getMapa();
        cerrarDialogos();

        defFormaBorrar = getFormaBorrar();
        defFormaBorrar.vaciarMapa(m);

        Window subWindow = new Window("Borrar");
        subWindow.setContent(defFormaBorrar.getComponente());

        getIxUI().addWindow(subWindow);
        subWindow.center();

        this.defFormaBorrar.setVentana(subWindow);
    }

    public void borrar()
    {

    }

    public ixDefinicionDeForma getFormaBorrar()
    {

        ixDefinicionDeForma forma = this.getForma(this.getFormaBorrado(), "b");

        return forma;
    }

    public boolean antesDeBorrar(Map m)
    {
        return true;
    }

    public boolean antesDeGuardar(Map m)
    {
        return true;
    }

    public void configurarInterfaz()
    {
        preguntarCrear.setHeight("25px");
        recargar.setHeight("25px");
        espaciador1.setCaption("&nbsp;");
        espaciador1.setCaptionAsHtml(true);
        espaciador1.setWidth("40px");

        preguntarCrear.setStyleName("margen1");
        recargar.setStyleName("margen1");

        this.getEncabezado().addComponent(espaciador1);
        this.getEncabezado().addComponent(recargar);
        this.getEncabezado().addComponent(preguntarCrear);

        //this.getEncabezado().setMargin(true);
        preguntarCrear.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                abrirCrear();
            }

        });

        recargar.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                armarTabla();
            }

        });

        bCrear.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                crear();
            }

        });

        bGuardar.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                guardar();
            }

        });

        bBorrar.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                abrirBorrar();
            }

        });

        bConfBorrar.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                borrar();
            }

        });
    }

    public void cerrarDialogos()
    {
        if (this.defFormaEditar != null)
        {
            //this.defFormaEditar.getComponente();
            this.defFormaEditar.getVentana().close();
            this.defFormaEditar = null;
        }
        if (this.defFormaCrear != null)
        {
            //this.defFormaEditar.getComponente();
            this.defFormaCrear.getVentana().close();
            this.defFormaCrear = null;
        }
        if (this.defFormaBorrar != null)
        {
            //this.defFormaEditar.getComponente();
            this.defFormaBorrar.getVentana().close();
            this.defFormaBorrar = null;
        }
    }

    /*
    *
    *
    *
    *
    *
    *
     */
    public ixDefinicionDeColumna agregarColumna(String nombre, String titulo)
    {
        ixDefinicionDeColumna col = new ixDefinicionDeColumna();
        col.setNombre(nombre);
        col.setTitulo(titulo);
        columnas.put(nombre, col);
        this.getTitulosColumnas().put(nombre, titulo);

        return col;
    }

    public ixDefinicionDeColumna getDefinicionDeColumna(String col)
    {
        return columnas.get(col);
    }

    public void inicializar(String tabla, String columnas)
    {
        this.setNombreTabla(tabla);
        this.setColumnas(columnas);
        this.armarTabla();
    }

    /**
     * @return the columnasCaptura
     */
    public String getFormaCreacion()
    {
        return formaCreacion;
    }

    /**
     * @param columnasCaptura the columnasCaptura to set
     */
    public void setFormaCreacion(String columnasCaptura)
    {
        this.formaCreacion = columnasCaptura;
    }

    /**
     * @return the formaEdicion
     */
    public String getFormaEdicion()
    {
        return formaEdicion;
    }

    /**
     * @param formaEdicion the formaEdicion to set
     */
    public void setFormaEdicion(String formaEdicion)
    {
        this.formaEdicion = formaEdicion;
    }

    /**
     * @return the formaBorrado
     */
    public String getFormaBorrado()
    {
        return formaBorrado;
    }

    /**
     * @param formaBorrado the formaBorrado to set
     */
    public void setFormaBorrado(String formaBorrado)
    {
        this.formaBorrado = formaBorrado;
    }

    /**
     * @return the ixUI
     */
    public ixUI getIxUI()
    {
        return ixUI;
    }

    /**
     * @param ixUI the ixUI to set
     */
    public void setIxUI(ixUI ixUI)
    {
        this.ixUI = ixUI;
    }
}
