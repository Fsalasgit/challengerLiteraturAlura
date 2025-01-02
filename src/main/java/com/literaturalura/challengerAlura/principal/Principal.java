package com.literaturalura.challengerAlura.principal;

import com.literaturalura.challengerAlura.model.Datos;
import com.literaturalura.challengerAlura.service.ConsumoAPI;
import com.literaturalura.challengerAlura.service.ConvierteDatos;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/" ;
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraelMenu(){
        var json = consumoApi.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);
    }
}
