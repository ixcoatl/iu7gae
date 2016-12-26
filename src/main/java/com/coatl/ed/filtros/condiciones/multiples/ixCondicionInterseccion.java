/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros.condiciones.multiples;

import com.coatl.ed.filtros.ixCondicionDeTupla;
import java.util.Map;

/**
 *
 * @author matus
 */
public class ixCondicionInterseccion extends ixCondicionMultiple
{

    @Override
    public boolean cumple(Map m)
    {

        for (ixCondicionDeTupla c : condiciones)
        {
            if (!c.cumple(m))
            {
                return false;
            }
        }
        return true;
    }
}
