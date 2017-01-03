/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed.filtros;

/**
 *
 * @author matus
 */
public class ixCondicionDeTupla extends ixCondicionDeFiltro
{

    private String nombre = null;
    private Object valor = null;

    public ixCondicionDeTupla()
    {
    }

    public ixCondicionDeTupla(String n, Object v)
    {
        this.nombre = n;
        this.valor = v;
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the valor
     */
    public Object getValor()
    {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Object valor)
    {
        this.valor = valor;
    }

}
