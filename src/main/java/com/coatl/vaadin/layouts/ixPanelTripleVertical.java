/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.vaadin.layouts;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalSplitPanel;

/**
 *
 * @author matus
 */
public class ixPanelTripleVertical extends VerticalSplitPanel
{

    VerticalSplitPanel interior = null;

    public ixPanelTripleVertical()
    {
        this.setSizeFull();
        this.setSplitPosition(52, Sizeable.UNITS_PIXELS, false);

        interior = new VerticalSplitPanel();
        interior.setSplitPosition(45, Sizeable.UNITS_PIXELS, true);
        interior.setSizeFull();
        this.setSecondComponent(interior);
        //System.out.println("Armando panel triple vertical");

        /*
         this.setComponenteSuperior(new Label("primer panel"));
         this.setComponenteMedio(new Label("segundo panel"));
         this.setComponenteInferior(new Label("tercer panel"));
         */
    }

    public void setComponenteSuperior(AbstractComponent com)
    {
        this.setFirstComponent(com);
    }

    public void setComponenteMedio(AbstractComponent com)
    {
        interior.setFirstComponent(com);
    }

    public void setComponenteInferior(AbstractComponent com)
    {
        interior.setSecondComponent(com);
    }

    public void setSplitPosition1(int pos)
    {
        this.setSplitPosition(pos, Sizeable.UNITS_PIXELS, false);
    }

    public void setSplitPosition2(int pos)
    {
        interior.setSplitPosition(pos, Sizeable.UNITS_PIXELS, true);
    }

}
