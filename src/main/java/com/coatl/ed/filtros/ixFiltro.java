/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros;

import com.coatl.ed.filtros.condiciones.cadena.ixCondicionCadenaContieneIgnCaso;
import com.coatl.ed.filtros.condiciones.cadena.ixCuaquierCampoContieneCadenaIgnCaso;
import com.coatl.ed.filtros.condiciones.ixCoincideBooleano;
import com.coatl.ed.filtros.condiciones.multiples.ixCondicionInterseccion;
import com.coatl.ed.filtros.condiciones.multiples.ixCondicionMultiple;
import com.coatl.ed.filtros.condiciones.multiples.ixCondicionUnion;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author matus
 */
public class ixFiltro
{

    Stack<ixCondicionDeFiltro> pila = new Stack();
    private ixCondicionDeFiltro ultimo;

    public ixCondicionDeFiltro getCondicionRaiz()
    {
        return ultimo;
    }

    public boolean cumple(Map m)
    {
        if (ultimo == null)
        {
            return true;
        }

        return ultimo.cumple(m);
    }

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

    public ixFiltro agregarCoincideBooleano(String nombre, boolean v)
    {
        ixCondicionMultiple cm = (ixCondicionMultiple) pila.peek();
        cm.agregar(new ixCoincideBooleano(nombre, v));
        return this;
    }

    public ixFiltro agregarCoincideCadenaIgnCaso(String nombre, String v)
    {
        ixCondicionMultiple cm = (ixCondicionMultiple) pila.peek();
        cm.agregar(new ixCondicionCadenaContieneIgnCaso(nombre, v));
        return this;
    }

    public ixFiltro agregarCuaquierCampoContieneCadenaIgnCaso(String v)
    {
        ixCondicionMultiple cm = (ixCondicionMultiple) pila.peek();
        cm.agregar(new ixCuaquierCampoContieneCadenaIgnCaso(v));
        return this;
    }

}
