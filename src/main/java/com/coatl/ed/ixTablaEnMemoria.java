/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author matus
 */
public class ixTablaEnMemoria implements TablaIF, Serializable
{

    List<String> columnas = new ArrayList();

    private List<Class> clasesColumnas = new ArrayList();
    List<Object[]> datos = new ArrayList();
    Map<String, Integer> mColumnas = new HashMap();

    private long totalDeRenglones = 0;
    private long pagina = 0;
    private long renglonesPorPagina = 0;

    @Override
    public void agregarRegnglon(Object[] reng)
    {
        datos.add(reng);
    }

    @Override
    public Object[] getRenglon(int n)
    {
        return datos.get(n);
    }

    @Override
    public Object getCol(int n, int col)
    {
        return datos.get(n)[col];
    }

    @Override
    public Object getCol(int n, String col)
    {
        return datos.get(n)[mColumnas.get(col)];
    }

    public void agregarColumna(String nombre)
    {
        //System.out.println("               +Agregando: " + nombre + "> " + columnas.size());

        mColumnas.put(nombre, columnas.size());
        columnas.add(nombre);

    }

    public void agregarColumna(String nombre, String titulo)
    {
        mColumnas.put(nombre, columnas.size());
        columnas.add(nombre);

    }

    @Override
    public long getTam()
    {
        return datos.size();
    }

    @Override
    public void reiniciarColumnas()
    {
        columnas = new ArrayList();
        mColumnas = new HashMap();
        //titulosColumnas = new ArrayList();
        clasesColumnas = new ArrayList();
        datos = new ArrayList();
    }

    @Override
    public Object[][] getVector()
    {
        return (Object[][]) datos.toArray();
    }

    @Override
    public List<Object[]> getDatos()
    {
        return datos;
    }

    @Override
    public List<String> getColumnas()
    {
        return columnas;
    }

    @Override
    public int getIndiceDeColumna(String nombre)
    {
        return mColumnas.get(nombre);
    }

    @Override
    public void agregarClaseColumna(Class clase)
    {
        this.clasesColumnas.add(clase);
    }

    @Override
    public Class getClaseColumna(int col)
    {
        Class cl = null;
        try
        {
            cl = this.clasesColumnas.get(col);

        } catch (Exception e)
        {
            return String.class;
        }

        return cl;
    }

    @Override
    public Class getClaseColumna(String col)
    {
        Class cl = null;
        try
        {
            cl = this.clasesColumnas.get(mColumnas.get(col));

        } catch (Exception e)
        {
            return String.class;
        }

        return cl;
    }

    @Override
    public Map<String, Object> getRenglonComoMapa(Object[] renglon)
    {
        Map res = new HashMap();
        for (String col : this.columnas)
        {
            res.put(col, renglon[this.getIndiceDeColumna(col)]);
        }
        return res;
    }

    /**
     * @return the totalDeRenglones
     */
    public long getTotalDeRenglones()
    {
        return totalDeRenglones;
    }

    /**
     * @param totalDeRenglones the totalDeRenglones to set
     */
    public void setTotalDeRenglones(long totalDeRenglones)
    {
        this.totalDeRenglones = totalDeRenglones;
    }

    /**
     * @return the pagina
     */
    public long getPagina()
    {
        return pagina;
    }

    /**
     * @param pagina the pagina to set
     */
    public void setPagina(long pagina)
    {
        this.pagina = pagina;
    }

    /**
     * @return the renglonesPorPagina
     */
    public long getRenglonesPorPagina()
    {
        return renglonesPorPagina;
    }

    /**
     * @param renglonesPorPagina the renglonesPorPagina to set
     */
    public void setRenglonesPorPagina(long renglonesPorPagina)
    {
        this.renglonesPorPagina = renglonesPorPagina;
    }

}
