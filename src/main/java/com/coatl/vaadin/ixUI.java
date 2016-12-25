/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.vaadin;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author matus
 */
public class ixUI extends UI
{

    Map<String, Object> propiedades = new HashMap();

    @Override
    protected void init(VaadinRequest request)
    {
        //propiedades = new HashMap();
    }

    public void setProp(String nombre, Object valor)
    {
        propiedades.put(nombre, valor);
    }

    public String getPropS(String nombre)
    {
        return (String) propiedades.get(nombre);
    }

    public String getPropS(String nombre, String pred)
    {
        String v = (String) propiedades.get(nombre);
        if (v == null)
        {
            setProp(nombre, pred);
            return pred;
        }
        return v;
    }
}
