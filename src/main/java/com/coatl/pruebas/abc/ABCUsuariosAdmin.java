/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coatl.pruebas.abc;

import com.coatl.vaadin.abc.ixABCDialogosGAE;
import com.coatl.vaadin.ixUI;

/**
 *
 * @author matus
 */
public class ABCUsuariosAdmin extends ixABCDialogosGAE
{

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ABCUsuariosAdmin(ixUI ui)
    {
        super(ui);

        this.setNombreTabla("sis_usuarios");
        //this.setColumnas("id,clave,nombre,ap1,ap2,activo,tel_fijo,tel_celular");
        //this.setColumnasVisibles("activo,id,clave,nombre,ap1,ap2,tel_celular");
        this.setTipoBusqueda("agrupar");
        this.setColumnas("id,nombre");
        this.setColumnasVisibles("nombre");
        this.setTitulo("Usuarios Administradores");

        this.agregarColumna("id", "Usuario").setRequerido(true).requiereFiltro().TextField();
        this.agregarColumna("clave", "Clave de acceso").setRequerido(true).TextField();
        this.agregarColumna("nombre", "Nombre").requiereFiltro().TextField();
        this.agregarColumna("ap1", "Apellido paterno").TextField();
        this.agregarColumna("ap2", "Apellido materno").TextField();
        this.agregarColumna("activo", "Activo").CheckBox();

        this.agregarColumna("tel_fijo", "Teléfono fijo").TextField();
        this.agregarColumna("tel_celular", "Teléfono celular").TextField();

        this.setFormaCreacion("Vm 'Información~de~Usuario H F id clave nombre ap1 ap2 bCrear .");
        this.setFormaEdicion("Vm 'Información~de~Usuario H F id clave nombre ap1 ap2 . Fm tel_fijo tel_celular  activo bGuardar bBorrar .");
        this.setFormaBorrado("Hm F id nombre ap1 ap2 bConfBorrar . .");

        this.armarTabla();
    }

}
