/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.appengine.datastore;

import com.coatl.ed.TablaIF;
import com.coatl.ed.filtros.ixFiltro;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author matus
 */
public class ixDataStore
{

    public DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public DatastoreService getDS()
    {
        return datastore;
    }

    public void guardar(String kind, Map obj)
    {
        String id = (String) obj.get("id");

        Entity e = null;
        if (id != null)
        {
            e = new Entity(kind, id);
        } else
        {
            e = new Entity(kind);
        }

        Iterator i = obj.keySet().iterator();

        while (i.hasNext())
        {
            String llave = (String) i.next();
            if (!llave.equals("id"))
            {
                Object valor = obj.get(llave);
                e.setProperty(llave, valor);
            }
        }

        datastore.put(e);
    }

    public void borrar(String tabla, String id)
    {
        try
        {
            datastore.delete(KeyFactory.createKey(tabla, id));
        } catch (Exception err)
        {
            err.printStackTrace();
        }
    }

    public void borrar(String tabla, long id)
    {

        try
        {
            datastore.delete(KeyFactory.createKey(tabla, id));
        } catch (Exception err)
        {
            err.printStackTrace();
        }
    }

    public void borrarPorID(String tabla, String id)
    {
        long idl = 0;
        if (esEntero(id, 10) && id.length() > 7)
        {
            idl = Long.parseLong(id);
        }
        if (idl > 0)
        {
            borrar(tabla, idl);
        } else
        {
            borrar(tabla, (String) id);
        }
    }

    public void borrarPorIDS(String tabla, List<String> l)
    {
        List<Key> lista = new ArrayList();
        for (String id : l)
        {
            lista.add(KeyFactory.createKey(tabla, id));
        }
        datastore.delete(lista);
    }

    public void fijarAtributoPorIDs(String tabla,
                                    List<Map> m,
                                    String prop,
                                    String val)
    {
        List<Entity> entidades = new ArrayList();
        for (Map obj : m)
        {
            Object id = (String) obj.get("id");
            Entity e = null;
            if (id instanceof String)
            {
                e = new Entity(tabla, (String) id);
            } else
            {
                e = new Entity(tabla, KeyFactory.createKey(tabla, (Long) id));
            }
            try
            {
                e = datastore.get(e.getKey());
                e.setProperty(prop, val);
                entidades.add(e);
            } catch (Exception ex)
            {

            }
        }
        datastore.put(entidades);
    }

    public static boolean esEntero(String s, int radix)
    {
        if (s.isEmpty())
        {
            return false;
        }
        for (int i = 0; i < s.length(); i++)
        {
            if (i == 0 && s.charAt(i) == '-')
            {
                if (s.length() == 1)
                {
                    return false;
                } else
                {
                    continue;
                }
            }
            if (Character.digit(s.charAt(i), radix) < 0)
            {
                return false;
            }
        }
        return true;
    }

    public Map buscarObjeto(String kind, Object id)
    {
        Key llave = null;

        if (id instanceof Long)
        {
            //System.out.println("Haciendo llave de largo: " + id);
            llave = KeyFactory.createKey(kind, (Long) id);
        } else
        {
            //System.out.println("Haciendo llave de cadena: " + id);
            llave = KeyFactory.createKey(kind, (String) id);
        }

        try
        {

            Entity ee = datastore.get(llave);
            //System.out.println("Objeto cargado: " + ee);
            if (ee == null)
            {
                return null;
            }
            Map<String, Object> rese = ee.getProperties();
            //System.out.println("Propiedades obtenidas");
            Iterator<String> i = rese.keySet().iterator();
            Map res = new HashMap();
            while (i.hasNext())
            {
                String k = i.next();
                Object v = rese.get(k);
                res.put(k, v);
            }

            res.put("id", id);
            return res;

        } catch (Exception ex)
        {
            //ex.printStackTrace();
            System.out.println("buscarObjeto()::Error: " + ex);
        }
        return null;
    }

    public Map<String, Object> entidadAMapa(Entity ee)
    {
        Map<String, Object> rese = ee.getProperties();
        Key llave = ee.getKey();
        //System.out.println("LLave: " + llave + ", " + llave.isComplete() + ", +" + llave.getId());

        //System.out.println("Propiedades obtenidas");
        Iterator<String> i = rese.keySet().iterator();
        Map res = new HashMap();
        if (llave.getId() == 0)
        {
            res.put("id", limpiarLlave(llave.toString()));
        } else
        {
            res.put("id", llave.getId());
        }
        while (i.hasNext())
        {
            String k = i.next();
            Object v = rese.get(k);
            res.put(k, v);
        }
        return res;
    }

    /*
     * BUSQUEDAS EN MAPAS
     */
    public List<Map> getBuscarListaDeMapas(PreparedQuery pq, FetchOptions fo)
    //(Query q, FetchOptions fo)
    {
        List l = new ArrayList();

        //PreparedQuery pq = datastore.prepare(q);
        List<Entity> lres = pq.asList(fo);

        int n = 0;
        for (Entity ee : lres)
        {
            Map<String, Object> rese = ee.getProperties();
            Iterator<String> i = rese.keySet().iterator();
            Map res = new HashMap();
            Key llave = ee.getKey();
            //System.out.println("LLave: " + llave + ", " + llave.isComplete() + ", +" + llave.getId());
            if (llave.getId() == 0)
            {
                res.put("id", limpiarLlave(llave.toString()));
            } else
            {
                res.put("id", llave.getId());
            }
            while (i.hasNext())
            {
                String k = i.next();
                Object v = rese.get(k);
                res.put(k, v);
            }

            l.add(res);
            n++;
        }
        //System.out.println("Leidos " + n + " objetos de la tabla " + pq.);

        return l;
    }

    public void getBuscarEnTabla(
            PreparedQuery pq, FetchOptions fo,
            String columnas,
            TablaIF tabla
    )
    {
        getBuscarEnTabla(pq, fo, columnas, tabla, 0, 9999999999l);
    }

    public void getBuscarEnTabla(
            PreparedQuery pq,
            FetchOptions fo,
            String columnas,
            TablaIF tabla,
            long inicio,
            long tamPagina
    )
    {
        String[] aCols = columnas.split(",");
        tabla.agregarColumna("id");
        int n = 0;
        for (String col : aCols)
        {
            aCols[n] = col.toLowerCase();
            if (!col.equals("id"))
            {
                tabla.agregarColumna(col);
            }
            n++;
        }

        //List<Entity> lres = pq.asList(fo);
        Iterator<Entity> iter = pq.asIterable().iterator();

        boolean primero = true;
        long num = 0l;
        long numPag = 0l;
        //for (Entity ee : lres)

        while (iter.hasNext())
        {
            Entity ee = iter.next();
            //Aqui extraeremos las classes de los objetos
            if (num >= inicio && numPag < tamPagina)
            {
                Object reng[] = new Object[aCols.length];
                for (int i = 0; i < aCols.length; i++)
                {
                    if (primero)
                    {
                        tabla.agregarClaseColumna(aCols[i].getClass());
                        //System.out.println("   +Clase [" + i + "]> " + aCols[i].getClass());
                    }
                    if (aCols[i].equals("id"))
                    {
                        Key llave = ee.getKey();
                        Object id = null;

                        //System.out.println("  +LLave: " + llave + ", " + llave.isComplete() + ", +" + llave.getId());
                        if (llave.getId() == 0)
                        {
                            id = limpiarLlave(llave.toString());
                        } else
                        {
                            id = llave.getId();
                        }

                        reng[i] = id;
                    } else
                    {
                        reng[i] = ee.getProperties().get(aCols[i]);
                        //System.out.println("    +++" + aCols[i] + "=" + reng[i]);
                    }
                }
                primero = false;
                tabla.agregarRegnglon(reng);
                numPag++;

                if (numPag >= tamPagina)
                {
                    return;
                }
            }
            num++;
        }

    }

    public void getBuscarEnTabla(
            PreparedQuery pq, FetchOptions fo,
            String columnas,
            TablaIF tabla,
            long inicio,
            long tamPagina,
            ixFiltro filtro
    )
    {
        String[] aCols = columnas.split(",");
        tabla.agregarColumna("id");
        int n = 0;
        for (String col : aCols)
        {
            aCols[n] = col.toLowerCase();
            if (!col.equals("id"))
            {
                tabla.agregarColumna(col);
            }
            n++;
        }

        //List<Entity> lres = pq.asList(fo);
        Iterator<Entity> iter = pq.asIterable().iterator();

        boolean primero = true;
        long totalRenglones = 0l;
        long totalRenglonesFiltrados = 0l;
        long numPag = 0l;
        //for (Entity ee : lres)

        while (iter.hasNext())
        {
            Entity ee = iter.next();

            Map m = this.entidadAMapa(ee);

            if (filtro != null && filtro.cumple(m))
            //Aqui extraeremos las classes de los objetos
            {
                totalRenglonesFiltrados++;
                if (totalRenglones >= inicio && numPag < tamPagina)
                {
                    Object reng[] = new Object[aCols.length];
                    for (int i = 0; i < aCols.length; i++)
                    {
                        if (primero)
                        {
                            tabla.agregarClaseColumna(aCols[i].getClass());
                            //System.out.println("   +Clase [" + i + "]> " + aCols[i].getClass());
                        }
                        if (aCols[i].equals("id"))
                        {
                            Key llave = ee.getKey();
                            Object id = null;

                            //System.out.println("  +LLave: " + llave + ", " + llave.isComplete() + ", +" + llave.getId());
                            if (llave.getId() == 0)
                            {
                                id = limpiarLlave(llave.toString());
                            } else
                            {
                                id = llave.getId();
                            }

                            reng[i] = id;
                        } else
                        {
                            reng[i] = ee.getProperties().get(aCols[i]);
                            //System.out.println("    +++" + aCols[i] + "=" + reng[i]);
                        }
                    }
                    primero = false;
                    tabla.agregarRegnglon(reng);
                    numPag++;
                }
            }
            totalRenglones++;
        }
        tabla.setTotalDeRenglones(totalRenglones);
        tabla.setTotalDeRenglonesFiltrados(totalRenglonesFiltrados);
        //System.out.println("Leidos " + num + " objetos de la tabla " + q.getKind());
    }

    /*
     *
     */
    public void getBuscarEnTablaAgrupando(
            PreparedQuery pq, FetchOptions fo,
            String columnas,
            String columnasLLave,
            TablaIF tabla,
            long inicio,
            long tamPagina,
            ixFiltro filtro
    )
    {
        Map diferentes = new HashMap();

        String[] aCols = columnas.split(",");
        tabla.agregarColumna("id");
        int n = 0;
        for (String col : aCols)
        {
            aCols[n] = col.toLowerCase();
            if (!col.equals("id"))
            {
                tabla.agregarColumna(col);
            }
            n++;
        }

        Iterator<Entity> iter = pq.asIterable().iterator();

        boolean primero = true;
        long totalRenglones = 0l;
        long totalRenglonesFiltrados = 0l;
        long numPag = 0l;

        while (iter.hasNext())
        {
            Entity ee = iter.next();

            Map m = this.entidadAMapa(ee);

            String llaveAgr = hacerLlaveSinID(m, columnasLLave);
            if (!diferentes.containsKey(llaveAgr))
            {
                if (filtro != null && filtro.cumple(m))
                {
                    totalRenglonesFiltrados++;
                    if (totalRenglones >= inicio && numPag < tamPagina)
                    {
                        Object reng[] = new Object[aCols.length];
                        for (int i = 0; i < aCols.length; i++)
                        {
                            if (primero)
                            {
                                tabla.agregarClaseColumna(aCols[i].getClass());
                            }
                            if (aCols[i].equals("id"))
                            {
                                reng[i] = llaveAgr;
                            } else
                            {
                                reng[i] = ee.getProperties().get(aCols[i]);
                            }
                        }
                        primero = false;
                        tabla.agregarRegnglon(reng);
                        diferentes.put(llaveAgr, "1");
                        //System.out.println("LLave agr> " + llaveAgr);
                        numPag++;
                    }
                }
            }
            totalRenglones++;
        }
        tabla.setTotalDeRenglones(totalRenglones);
        tabla.setTotalDeRenglonesFiltrados(totalRenglonesFiltrados);
    }

    /*
     * SERVICIOS PRIVADOS
     */
    private String limpiarLlave(String llave)
    {
        int pos = llave.indexOf("(");
        return llave.substring(pos + 2, llave.length() - 2);
    }

    /*
     public void borrarTabla(String kind)
     {
     //Entity e = new Entity(kind, id);

     try
     {

     MapReduceSettings settings = new MapReduceSettings.Builder()
     .setWorkerQueueName("mrworker").build();


     String jobId = MapReduceJob.start(
     MapReduceSpecification.of(
     "Clean Accounts",
     // shard count 50
     new DatastoreInput(kind, 50),
     new BorradorDeKind(kind),
     Marshallers.getVoidMarshaller(),
     Marshallers.getVoidMarshaller(),
     NoReducer.<Void, Void, Void>create(),
     NoOutput.<Void, Void>create(1)),
     settings);

     } catch (Exception err)
     {
     err.printStackTrace();
     }

     }
     */

    /*
     public class BorradorDeKind extends Mapper<Entity, Void, Void>
     {

     String nombreEntidad = null;

     public BorradorDeKind(String nombreEntidad)
     {
     this.nombreEntidad = nombreEntidad;
     }

     //private transient DatastoreMutationPool pool;
     @Override
     public void beginShard()
     {
     // Ikai gives examples of using the pool for
     // better parallel datastore operations
     //this.pool = DatastoreMutationPool.forWorker(this);

     // You could optionally use the normal datastore like this
     //this.datastore =
     //     DatastoreServiceFactory.getDatastoreService();
     }

     @Override
     public void map(Entity value)
     {

     // During my testing, some of our workers
     // died with NullPointer's on this field,
     // as if in some circumstances it goes away.
     // This ensures we always have it.

     //if (this.pool == null)
     //{
     //    this.pool
     //    = DatastoreMutationPool.forWorker(this);

     //}

     // Slightly paranoid check we have an account
     if (value.getKind().equals("Account"))
     {
     // Logic goes here to determine
     // if the account should be deleted
     //if (itShouldBeDeleted)
     {
     datastore.delete(value.getKey());
     }
     // You could create/update/count here too

     }
     }
     }*/

    /*
     public class BorradorDeLLave extends MapOnlyMapper<Key, Void>
     {

     private static final long serialVersionUID = -6485226450501339416L;

     // [START datastoreMutationPool]
     private transient DatastoreMutationPool batcher;
     // [END datastoreMutationPool]

     // [START begin_and_endSlice]
     @Override
     public void beginSlice()
     {
     batcher = DatastoreMutationPool.create();
     }

     @Override
     public void endSlice()
     {
     batcher.flush();
     }
     // [END begin_and_endSlice]

     @Override
     public void map(Key key)
     {
     batcher.delete(key);
     }
     }
     */
    private String hacerLlaveSinID(Map m, String columnas)
    {
        StringBuilder llave = new StringBuilder();
        StringBuilder colss = new StringBuilder();

        String[] cols = columnas.split(",");
        int n = 0;
        for (String col : cols)
        {
            col = col.trim();

            if (!col.equals(" ") && !col.equals("id"))
            {
                colss.append(col).append(" ");
                if (n > 0)
                {
                    llave.append("-");
                }
                llave.append(m.get(col));
                n++;
            }
        }
        //System.out.println("CLlaves> " + colss.toString());

        return llave.toString();
    }
}
