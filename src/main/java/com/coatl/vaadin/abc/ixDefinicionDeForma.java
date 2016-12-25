/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.vaadin.abc;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author matus
 */
public class ixDefinicionDeForma implements Serializable
{

    private Map<String, AbstractComponent> componentes = new HashMap();
    private AbstractComponent componente = null;
    private Window ventana = null;

    public Map getMapa()
    {
        return getMapa(new HashMap());
    }

    public Map getMapa(Map mapa)
    {
        //Map mapa = new HashMap();
        Iterator<String> i = componentes.keySet().iterator();
        while (i.hasNext())
        {
            String nombre = i.next();
            AbstractComponent cValor = componentes.get(nombre);
            Object val = null;

            if (cValor instanceof TextField)
            {
                val = ((TextField) cValor).getValue();
            }

            if (cValor instanceof CheckBox)
            {
                val = ((CheckBox) cValor).getValue();
            }
            mapa.put(nombre, val);
        }
        return mapa;
    }

    public TextField getTextField(String nombre)
    {
        return (TextField) componentes.get(nombre);
    }

    /**
     * @return the componentes
     */
    public Map<String, AbstractComponent> getComponentes()
    {
        return componentes;
    }

    /**
     * @param componentes the componentes to set
     */
    public void setComponentes(Map<String, AbstractComponent> componentes)
    {
        this.componentes = componentes;
    }

    /**
     * @return the componente
     */
    public AbstractComponent getComponente()
    {
        return componente;
    }

    /**
     * @param componente the componente to set
     */
    public void setComponente(AbstractComponent componente)
    {
        this.componente = componente;
    }

    /**
     * @return the ventana
     */
    public Window getVentana()
    {
        return ventana;
    }

    /**
     * @param ventana the ventana to set
     */
    public void setVentana(Window ventana)
    {
        this.ventana = ventana;
    }

    void vaciarMapa(Map m)
    {
        Iterator<String> i = m.keySet().iterator();
        while (i.hasNext())
        {
            String nombre = i.next();
            AbstractComponent cValor = componentes.get(nombre);
            Object valor = m.get(nombre);

            if (cValor instanceof TextField)
            {
                TextField tf = (TextField) cValor;
                boolean ro = tf.isReadOnly();
                tf.setReadOnly(false);
                tf.setValue((String) valor);
                tf.setReadOnly(ro);
            }

            if (cValor instanceof CheckBox)
            {
                CheckBox tf = (CheckBox) cValor;
                boolean ro = tf.isReadOnly();
                tf.setReadOnly(false);
                ((CheckBox) cValor).setValue((Boolean) valor);
                tf.setReadOnly(ro);
            }

        }

    }

}
