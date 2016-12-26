/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros.condiciones.multiples;

import com.coatl.ed.filtros.ixCondicionDeFiltro;
import com.coatl.ed.filtros.ixCondicionDeTupla;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matus
 */
public class ixCondicionMultiple extends ixCondicionDeFiltro
{

    protected List<ixCondicionDeTupla> condiciones = new ArrayList();

    public void agregar(ixCondicionDeTupla c)
    {
        condiciones.add(c);
    }
}
