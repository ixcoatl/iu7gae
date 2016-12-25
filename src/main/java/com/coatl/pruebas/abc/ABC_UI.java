/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.pruebas.abc;

import com.coatl.vaadin.abc.ixABCDialogos;
import com.coatl.vaadin.ixUI;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;

/**
 *
 * @author matus
 */
@Theme("tema_iu7")
public class ABC_UI extends ixUI
{

    @Override
    protected void init(VaadinRequest request)
    {
        this.setContent(new ABCUsuariosAdmin(this));
    }

}
