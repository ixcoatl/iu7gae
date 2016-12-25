/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.vaadin.abc;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import java.io.Serializable;

/**
 *
 * @author matus
 */
public class ixDefinicionDeColumna implements Serializable
{

    private String nombre = null;
    private String claseControl = null;
    private boolean requerido = false;
    private boolean requeridoCrear = false;
    private boolean requeridoGuardar = false;
    private boolean soloLectura = false;
    private boolean soloLecturaGuardar = false;
    private boolean soloLecturaCrear = false;
    private String titulo = null;

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public ixDefinicionDeColumna setNombre(String nombre)
    {
        this.nombre = nombre;

        return this;
    }

    public ixDefinicionDeColumna CheckBox()
    {
        this.claseControl = CheckBox.class.getName();
        return this;
    }

    public ixDefinicionDeColumna TextField()
    {
        this.claseControl = TextField.class.getName();
        return this;
    }

    /**
     * @return the control
     */
    public String getClaseControl()
    {
        return claseControl;
    }

    /**
     * @param control the control to set
     */
    public ixDefinicionDeColumna setClaseControl(String control)
    {
        this.claseControl = control;
        return this;
    }

    /**
     * @return the requerido
     */
    public boolean isRequerido()
    {
        return requerido;
    }

    /**
     * @param requerido the requerido to set
     */
    public ixDefinicionDeColumna setRequerido(boolean requerido)
    {
        this.requerido = requerido;
        return this;
    }

    /**
     * @return the requeridoCrear
     */
    public boolean isRequeridoCrear()
    {
        return requeridoCrear;
    }

    /**
     * @param requeridoCrear the requeridoCrear to set
     */
    public ixDefinicionDeColumna setRequeridoCrear(boolean requeridoCrear)
    {
        this.requeridoCrear = requeridoCrear;
        return this;
    }

    /**
     * @return the requeridoGuardar
     */
    public boolean isRequeridoGuardar()
    {
        return requeridoGuardar;
    }

    /**
     * @param requeridoGuardar the requeridoGuardar to set
     */
    public ixDefinicionDeColumna setRequeridoGuardar(boolean requeridoGuardar)
    {
        this.requeridoGuardar = requeridoGuardar;
        return this;
    }

    /**
     * @return the titulo
     */
    public String getTitulo()
    {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public ixDefinicionDeColumna setTitulo(String titulo)
    {
        this.titulo = titulo;
        return this;
    }

    /**
     * @return the soloLecturaGuardar
     */
    public boolean isSoloLecturaGuardar()
    {
        return soloLecturaGuardar;
    }

    /**
     * @param soloLecturaGuardar the soloLecturaGuardar to set
     */
    public ixDefinicionDeColumna setSoloLecturaGuardar(boolean soloLecturaGuardar)
    {
        this.soloLecturaGuardar = soloLecturaGuardar;
        return this;
    }

    /**
     * @return the soloLecturaCrear
     */
    public boolean isSoloLecturaCrear()
    {
        return soloLecturaCrear;
    }

    /**
     * @param soloLecturaCrear the soloLecturaCrear to set
     */
    public ixDefinicionDeColumna setSoloLecturaCrear(boolean soloLecturaCrear)
    {
        this.soloLecturaCrear = soloLecturaCrear;
        return this;
    }

    /**
     * @return the soloLectura
     */
    public boolean isSoloLectura()
    {
        return soloLectura;
    }

    /**
     * @param soloLectura the soloLectura to set
     */
    public ixDefinicionDeColumna setSoloLectura(boolean soloLectura)
    {
        this.soloLectura = soloLectura;
        return this;
    }

}
