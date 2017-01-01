/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.ed;

import java.util.List;
import java.util.Map;

/**
 *
 * @author matus
 */
public interface TablaIF
{

    public void reiniciarColumnas();

    public List<String> getColumnas();

    public void agregarColumna(String nombre);

    public int getIndiceDeColumna(String nombre);

    public void agregarClaseColumna(Class clase);

    public void agregarColumna(String nombre, String titulo);

    public void agregarRegnglon(Object[] reng);

    public Object[] getRenglon(int n);

    public Object getCol(int n, int pos);

    public Object getCol(int n, String nombre);

    public long getTam();

    public Object[][] getVector();

    public List<Object[]> getDatos();

    public Class getClaseColumna(int col);

    public Class getClaseColumna(String c);

    public Map<String, Object> getRenglonComoMapa(Object[] renglon);

    public long getTotalDeRenglones();

    public void setTotalDeRenglones(long t);

    public long getTotalDeRenglonesFiltrados();

    public void setTotalDeRenglonesFiltrados(long t);

    public long getPagina();

    public void setPagina(long t);

    public long getRenglonesPorPagina();

    public void setRenglonesPorPagina(long t);

}
