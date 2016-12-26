/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros.condiciones.multiples;

import com.coatl.ed.filtros.condiciones.multiples.ixCondicionMultiple;
import com.coatl.ed.filtros.ixCondicionDeTupla;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author matus
 */
public class ixCondicionUnion extends ixCondicionMultiple
{

    @Override
    public boolean cumple(Map m)
    {
        boolean algunaCumple = false;
        for (ixCondicionDeTupla c : condiciones)
        {
            if (c.cumple(m))
            {
                algunaCumple = true;
            }
        }
        return algunaCumple;
    }
}
