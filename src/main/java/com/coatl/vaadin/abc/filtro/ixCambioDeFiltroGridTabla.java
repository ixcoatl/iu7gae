/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.vaadin.abc.filtro;

import com.coatl.vaadin.grids.ixGridTabla;
import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.Component;

/**
 *
 * @author matus
 */
public class ixCambioDeFiltroGridTabla implements Property.ValueChangeListener
{

    private final ixGridTabla t;

    public ixCambioDeFiltroGridTabla(ixGridTabla t)
    {
        this.t = t;
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event)
    {
        System.out.println("Hay cambio de texto en filtro");
        t.armarTabla();
    }

}
