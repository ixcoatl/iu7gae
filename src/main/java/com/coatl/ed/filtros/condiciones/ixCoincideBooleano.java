/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros.condiciones;

import com.coatl.ed.filtros.ixCondicionDeTupla;
import java.util.Map;

/**
 *
 * @author matus
 */
public class ixCoincideBooleano extends ixCondicionDeTupla
{

    public ixCoincideBooleano(String n, boolean v)
    {
        super(n, v);
    }

    @Override
    public boolean cumple(Map<String, Object> m)
    {
        boolean val = (Boolean) m.get(this.getNombre());
        if (this.getValor() != null && this.getValor() instanceof Boolean)
        {
            Boolean vv = (Boolean) this.getValor();
            if (vv == val)
            {
                return true;
            }
        }
        return false;
    }
}
