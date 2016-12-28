/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros.condiciones.multiples;

import com.coatl.ed.filtros.ixCondicionDeFiltro;
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

        for (ixCondicionDeFiltro c : condiciones)
        {
            if (!c.cumple(m))
            {
                return false;
            }
        }
        return true;
    }
}
