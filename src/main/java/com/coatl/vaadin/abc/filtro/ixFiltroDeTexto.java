/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.vaadin.abc.filtro;

import com.coatl.vaadin.abc.ixDefinicionDeColumna;
import com.coatl.vaadin.grids.ixGridTabla;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author matus
 */
public class ixFiltroDeTexto extends HorizontalLayout
{

    private final TextField texto;
    private final ixGridTabla grid;

    private final ixDefinicionDeColumna defCol;

    public ixFiltroDeTexto(
            final ixGridTabla grid,
            ixDefinicionDeColumna defCol
    )
    {
        this.texto = new TextField();
        texto.setValue(defCol.getFiltro());
        this.addComponent(texto);
        this.grid = grid;
        this.defCol = defCol;
        final String nombreCol = defCol.getNombre();

        texto.addStyleName(ValoTheme.TEXTFIELD_TINY);
        texto.setInputPrompt("Filtros");

        texto.addListener(new TextChangeListener()
        {
            @Override
            public void textChange(TextChangeEvent event)
            {
                grid.setActivarFiltro(nombreCol);
                cambioDeTexto(event.getText());
            }
        });
    }

    public void cambioDeTexto(String valor)
    {
        defCol.setFiltro(valor);
        System.out.println("Fijado filtro> " + valor);
        grid.armarTabla();
    }

    /**
     * @return the texto
     */
    public TextField getTexto()
    {
        return texto;
    }

}
