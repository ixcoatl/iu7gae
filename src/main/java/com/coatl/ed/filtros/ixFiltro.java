/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros;

import com.coatl.ed.filtros.condiciones.cadena.ixCondicionCadenaContieneIgnCaso;
import com.coatl.ed.filtros.condiciones.multiples.ixCondicionInterseccion;
import com.coatl.ed.filtros.condiciones.multiples.ixCondicionMultiple;
import com.coatl.ed.filtros.condiciones.multiples.ixCondicionUnion;
import java.util.Stack;

/**
 *
 * @author matus
 */
public class ixFiltro
{

    Stack<ixCondicionDeFiltro> pila = new Stack();
    private ixCondicionDeFiltro ultimo;

    public ixFiltro iniciarO()
    {
        pila.push(new ixCondicionUnion());
        return this;
    }

    public ixFiltro iniciarY()
    {
        pila.push(new ixCondicionInterseccion());
        return this;
    }

    public void terminar()
    {
        this.ultimo = pila.pop();
    }

    public void terminarTodo()
    {
        while (pila.size() > 0)
        {
            terminar();
        }
    }

    public ixFiltro contieneCadena(String nombre, String v)
    {
        ixCondicionMultiple cm = (ixCondicionMultiple) pila.peek();
        cm.agregar(new ixCondicionCadenaContieneIgnCaso(nombre, v));
        return this;
    }
}
