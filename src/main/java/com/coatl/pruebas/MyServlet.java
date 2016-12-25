/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.pruebas;

import com.vaadin.server.GAEVaadinServlet;
import com.vaadin.server.VaadinServlet;
import javax.servlet.ServletException;

/**
 *
 * @author matus
 */
public class MyServlet extends GAEVaadinServlet
{

    public MyServlet()
    {
        super();
        System.out.println("");
        System.out.println("*************** MyServlet creado");
    }
}
