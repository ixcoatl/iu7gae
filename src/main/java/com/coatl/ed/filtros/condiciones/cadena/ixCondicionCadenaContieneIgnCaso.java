/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros.condiciones.cadena;

import com.coatl.ed.filtros.ixCondicionDeTupla;
import java.util.Map;

/**
 *
 * @author matus
 */
public class ixCondicionCadenaContieneIgnCaso extends ixCondicionDeTupla
{

    public ixCondicionCadenaContieneIgnCaso()
    {
    }

    public ixCondicionCadenaContieneIgnCaso(String n, String v)
    {
        super(n, v);
    }

    @Override
    public boolean cumple(Map<String, Object> m)
    {
        String val = (String) m.get(this.getNombre());
        if (val != null && this.getValor() != null)
        {
            return val.toLowerCase().contains(this.getValor().toLowerCase());
        }
        return false;
    }
}
