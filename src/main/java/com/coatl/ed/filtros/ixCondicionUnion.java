/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matus
 */
public class ixCondicionUnion extends ixCondicionMultiple
{

    @Override
    public boolean cumple()
    {
        boolean algunaCumple = false;
        for (ixCondicionDeTupla c : condiciones)
        {
            if (c.cumple())
            {
                algunaCumple = true;
            }
        }
        return algunaCumple;
    }
}
