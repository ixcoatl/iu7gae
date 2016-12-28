/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros.condiciones.cadena;

import com.coatl.ed.filtros.ixCondicionDeFiltro;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author matus
 */
public class ixCuaquierCampoContieneCadenaIgnCaso extends ixCondicionDeFiltro
{

    String v = null;

    public ixCuaquierCampoContieneCadenaIgnCaso()
    {
    }

    public ixCuaquierCampoContieneCadenaIgnCaso(String v)
    {
        if (v != null)
        {
            v = v.toLowerCase();
        }
        this.v = v;
    }

    @Override
    public boolean cumple(Map<String, Object> m)
    {
        if (v == null)
        {
            return false;
        }
        Iterator<String> i = m.keySet().iterator();
        while (i.hasNext())
        {
            Object obj = m.get(i.next());
            if (obj instanceof String)
            {
                String val = (String) obj;
                if (val != null)
                {
                    if (val.toLowerCase().contains(v))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
