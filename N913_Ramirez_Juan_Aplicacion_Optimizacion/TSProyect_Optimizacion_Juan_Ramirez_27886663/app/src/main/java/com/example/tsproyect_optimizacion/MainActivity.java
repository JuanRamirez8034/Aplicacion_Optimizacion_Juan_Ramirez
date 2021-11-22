package com.example.tsproyect_optimizacion;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtmsjPrincipal, txtmsjRuta, txtRuta, txtmsjDioxido, txtDioxido, txtmsjRutaCorta;
    private Spinner spinner;
    private Button btn;

    String ciudadzero = "PAN";
    ArrayList<Rutas> rutas = new ArrayList<>();
    ArrayList<String> ciudades = new ArrayList<>();
    ArrayList<String> puertos = new ArrayList<>();
    String[] puertos2 = {"PAN", "AMS", "CAS", "NY", "HEL"};
    ArrayList<String> ciudades2 = new ArrayList<>();
    int[][] distancia = {
            {0, 8943, 8019, 3652, 10545},{8943, 0, 2619, 6317, 2078},{8019, 2619, 0, 5836, 4939},{3652, 6317, 5836, 0, 7825},{10545, 2078, 4939, 7825, 0}};
    int[] distanciaTotal = new int[24];
    double[] dioxido = new double [24];

    String mensaje, mensaje2, mensaje3;
    int aux, aux2=24193;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcularRutas();
        calcularDistancias();
        calcularDioxido();
        rutaMasCorta();

        //Conectamos la parte lógica con la parte gráfica
        this.txtmsjPrincipal = (TextView) findViewById(R.id.txtPrincipal);
        this.txtmsjRuta = (TextView) findViewById(R.id.txtMsjRuta);
        this.txtRuta = (TextView) findViewById(R.id.txtRuta);
        this.txtmsjDioxido = (TextView) findViewById(R.id.txtMsjDioxido);
        this.txtDioxido = (TextView) findViewById(R.id.txtDioxido);
        this.txtmsjRutaCorta = (TextView) findViewById(R.id.txtRutaCorta);
        this.spinner = (Spinner) findViewById(R.id.idspinner);
        this.btn = (Button) findViewById(R.id.btn);


        //Mostramos el array de artículos dentro del Spinner
        ArrayAdapter<Rutas> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner__item,rutas);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_iten_dropdown);
        spinner.setAdapter(spinnerAdapter);

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                aux = (int) spinner.getSelectedItemId();
                Toast.makeText(MainActivity.this, "El Id seleccionado es: " + aux, Toast.LENGTH_LONG).show();
                for(int i=0; i<distanciaTotal.length; i++){

                    if(aux==i){

                        mensaje = String.valueOf(distanciaTotal[i]);
                        txtRuta.setText(mensaje);
                        txtRuta.setVisibility(View.VISIBLE);

                        mensaje2 = String.valueOf(dioxido[i]);
                        txtDioxido.setText(mensaje2);
                        txtDioxido.setVisibility(View.VISIBLE);

                        int aux3 = distanciaTotal[i];

                        if(aux2==aux3){
                            mensaje3 = "¡Esta es la ruta más corta con "+aux3+" km de distancia!";
                            txtmsjRutaCorta.setText(mensaje3);
                            txtmsjRutaCorta.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }
        });

    }
    public void calcularRutas() {
        ciudades.add("AMS");
        ciudades.add("CAS");
        ciudades.add("NY");
        ciudades.add("HEL");

        puertos.add("AMS");//0
        puertos.add("CAS");//1
        puertos.add("NY");//2
        puertos.add("HEL");//3

        int contador = 3;
        do {
            String ciudad1, ciudad2, ciudad3, ciudad4;

            //PRIMER PUERTO
            if (puertos.get(contador).equals("HEL")) {
                ciudad1 = puertos.get(contador); //Helsinky
                ciudades.remove(contador);

                for (int i = 0; i < puertos.size() - 1; i++) {
                    //Primera Opción
                    if (ciudades.get(i).equals("AMS")) {
                        ciudad2 = ciudades.get(i);//Amsterdam
                        Rutas r111 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r112 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r111);
                        rutas.add(r112);
                        for (int j = 1; j < ciudades.size(); j++) {
                            if (j == 1) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 1);
                                r111.setCiudad4(ciudad3);
                                r111.setCiudad5(ciudad4);
                            }
                            if (j == 2) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 1);
                                r112.setCiudad4(ciudad3);
                                r112.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-AMSTERDAM CIERRE
                    //Segunda Opción
                    if (ciudades.get(i).equals("CAS")) {
                        ciudad2 = ciudades.get(i);//Casablanca
                        Rutas r113 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r114 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r113);
                        rutas.add(r114);
                        for (int j = 0; j < ciudades.size(); j++) {
                            if (j == 0) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 2);
                                r113.setCiudad4(ciudad3);
                                r113.setCiudad5(ciudad4);
                            }
                            if (j == 2) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 2);
                                r114.setCiudad4(ciudad3);
                                r114.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-CASABLANCA CIERRE
                    //Tercera Opción
                    if (ciudades.get(i).equals("NY")) {
                        ciudad2 = ciudades.get(i);//New York
                        Rutas r115 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r116 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r115);
                        rutas.add(r116);
                        //ciudades.remove(i);
                        for (int j = 0; j < ciudades.size() - 1; j++) {
                            if (j == 0) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 1);
                                r115.setCiudad4(ciudad3);
                                r115.setCiudad5(ciudad4);
                            }
                            if (j == 1) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 1);
                                r116.setCiudad4(ciudad3);
                                r116.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-NEW YORK CIERRE
                }//FOR CIERRE
            } //SEGUNDO PUERTO
            else if (puertos.get(contador).equals("NY")) {
                ciudad1 = puertos.get(contador); //New York
                String puerto = puertos.get(contador + 1);
                ciudades.set(contador, puerto);

                for (int i = 0; i < puertos.size() - 1; i++) {
                    //Primera Opción
                    if (ciudades.get(i).equals("AMS")) {
                        ciudad2 = ciudades.get(i);//Amsterdam
                        Rutas r211 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r212 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r211);
                        rutas.add(r212);
                        //ciudades.remove(i);
                        for (int j = 1; j < ciudades.size(); j++) {
                            if (j == 1) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 1);
                                r211.setCiudad4(ciudad3);
                                r211.setCiudad5(ciudad4);
                            }
                            if (j == 2) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 1);
                                r212.setCiudad4(ciudad3);
                                r212.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-AMSTERDAM CIERRE
                    //Segunda Opción
                    if (ciudades.get(i).equals("CAS")) {
                        ciudad2 = ciudades.get(i);//Casablanca
                        Rutas r213 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r214 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r213);
                        rutas.add(r214);
                        //ciudades.remove(i);
                        for (int j = 0; j < ciudades.size(); j++) {
                            if (j == 0) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 2);
                                r213.setCiudad4(ciudad3);
                                r213.setCiudad5(ciudad4);
                            }
                            if (j == 2) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 2);
                                r214.setCiudad4(ciudad3);
                                r214.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-CASABLANCA CIERRE
                    //Tercera Opción
                    if (ciudades.get(i).equals("HEL")) {
                        ciudad2 = ciudades.get(i);//New York
                        Rutas r215 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r216 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r215);
                        rutas.add(r216);
                        for (int j = 0; j < ciudades.size() - 1; j++) {
                            if (j == 0) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 1);
                                r215.setCiudad4(ciudad3);
                                r215.setCiudad5(ciudad4);
                            }
                            if (j == 1) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 1);
                                r216.setCiudad4(ciudad3);
                                r216.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-HELSINKY CIERRE
                }//FOR CIERRE
            } //TERCER PUERTO
            else if (puertos.get(contador).equals("CAS")) {
                ciudad1 = puertos.get(contador); //Casablanca
                String puerto = puertos.get(contador + 1);
                ciudades.set(contador, puerto);
                for (int i = 0; i < puertos.size() - 1; i++) {
                    //Primera Opción
                    if (ciudades.get(i).equals("AMS")) {
                        ciudad2 = ciudades.get(i);//Amsterdam
                        Rutas r311 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r312 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r311);
                        rutas.add(r312);
                        for (int j = 1; j < ciudades.size(); j++) {
                            if (j == 1) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 1);
                                r311.setCiudad4(ciudad3);
                                r311.setCiudad5(ciudad4);
                            }
                            if (j == 2) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 1);
                                r312.setCiudad4(ciudad3);
                                r312.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-AMSTERDAM CIERRE
                    //Segunda Opción
                    if (ciudades.get(i).equals("NY")) {
                        ciudad2 = ciudades.get(i);//Casablanca
                        Rutas r313 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r314 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r313);
                        rutas.add(r314);
                        for (int j = 0; j < ciudades.size(); j++) {
                            if (j == 0) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 2);
                                r313.setCiudad4(ciudad3);
                                r313.setCiudad5(ciudad4);
                            }
                            if (j == 2) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 2);
                                r314.setCiudad4(ciudad3);
                                r314.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-NEW YORK CIERRE
                    //Tercera Opción
                    if (ciudades.get(i).equals("HEL")) {
                        ciudad2 = ciudades.get(i);//New York
                        Rutas r315 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r316 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r315);
                        rutas.add(r316);
                        for (int j = 0; j < ciudades.size() - 1; j++) {
                            if (j == 0) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 1);
                                r315.setCiudad4(ciudad3);
                                r315.setCiudad5(ciudad4);
                            }
                            if (j == 1) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 1);
                                r316.setCiudad4(ciudad3);
                                r316.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-HELSINKY CIERRE
                }//FOR CIERRE
            } //CUARTO PUERTO
            else if (puertos.get(contador).equals("AMS")) {
                ciudad1 = puertos.get(contador); //Amsterdam
                String puerto = puertos.get(contador + 1);
                ciudades.set(contador, puerto);
                for (int i = 0; i < puertos.size() - 1; i++) {
                    //Primera Opción
                    if (ciudades.get(i).equals("CAS")) {
                        ciudad2 = ciudades.get(i);//Amsterdam
                        Rutas r411 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r412 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r411);
                        rutas.add(r412);
                        //ciudades.remove(i);
                        for (int j = 1; j < ciudades.size(); j++) {
                            if (j == 1) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 1);
                                r411.setCiudad4(ciudad3);
                                r411.setCiudad5(ciudad4);
                            }
                            if (j == 2) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 1);
                                r412.setCiudad4(ciudad3);
                                r412.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-AMSTERDAM CIERRE
                    //Segunda Opción
                    if (ciudades.get(i).equals("NY")) {
                        ciudad2 = ciudades.get(i);//Casablanca
                        Rutas r413 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r414 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r413);
                        rutas.add(r414);
                        for (int j = 0; j < ciudades.size(); j++) {
                            if (j == 0) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 2);
                                r413.setCiudad4(ciudad3);
                                r413.setCiudad5(ciudad4);
                            }
                            if (j == 2) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 2);
                                r414.setCiudad4(ciudad3);
                                r414.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-NEW YORK CIERRE
                    //Tercera Opción
                    if (ciudades.get(i).equals("HEL")) {
                        ciudad2 = ciudades.get(i);//New York
                        Rutas r415 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        Rutas r416 = new Rutas(ciudadzero, ciudad1, ciudad2, ciudad1, ciudad1, ciudadzero);
                        rutas.add(r415);
                        rutas.add(r416);
                        for (int j = 0; j < ciudades.size() - 1; j++) {
                            if (j == 0) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j + 1);
                                r415.setCiudad4(ciudad3);
                                r415.setCiudad5(ciudad4);
                            }
                            if (j == 1) {
                                ciudad3 = ciudades.get(j);
                                ciudad4 = ciudades.get(j - 1);
                                r416.setCiudad4(ciudad3);
                                r416.setCiudad5(ciudad4);
                            }
                        }
                    }//IF-HELSINKY CIERRE
                }//FOR CIERRE
            }
            //System.out.println();
            contador--;
        } while (contador > -1);
    }
    public void calcularDistancias() {
        ciudades2.add("AMS");
        ciudades2.add("CAS");
        ciudades2.add("NY");
        ciudades2.add("HEL");
        int pos1 = 0, pos2, pos3, pos4, pos5;
        int cuenta;
        //IMPRIMIR LAS 24 RUTAS
        for (int i = 0; i < rutas.size(); i++) {

            //IMPRIMIR LOS CUATRO PUERTOS PRINCIPALES -AMSTERDAM -CASABLANCA -NEW YORK -HELSINKY
            for (int j = puertos.size(); j > 0; j--) {

                //PUERTO 1-------------------PUERTO 1----------------------PUERTO 1
                if (rutas.get(i).getCiudad2().equals("HEL") && j == 4) {
                    pos2 = j;

                    //IMPRIMIR LAS TRES CIUDADES SECUNDARIAS
                    for (int k = 1; k < puertos2.length; k++) {

                        //CIUDAD SECUNDARIA 1
                        if (rutas.get(i).getCiudad3().equals("AMS") && k == 1) {
                            pos3 = k;

                            //CIUDAD TERCIARIA 1
                            if (i == 0) {
                                pos4 = k + 1; pos5 = k + 2;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 1){
                                pos4 = k + 2; pos5 = k + 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                        //CIUDAD SECUNDARIA 2
                        if (rutas.get(i).getCiudad3().equals("CAS") && k == 2) {
                            pos3 = k;

                            //CIUDAD TERCIARIA 1
                            if (i == 2) {
                                pos4 = k - 1; pos5 = k + 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 3){
                                pos4 = k + 1;
                                pos5 = k - 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                        //CIUDAD SECUNDARIA 3
                        if (rutas.get(i).getCiudad3().equals("NY") && k == 3) {
                            pos3 = k;


                            //CIUDAD TERCIARIA 1
                            if (i == 4) {
                                pos4 = k - 2;
                                pos5 = k - 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 5){
                                pos4 = k - 1;
                                pos5 = k - 2;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                    }
                }//FIN PUERTO 1

                //PUERTO 2-------------------PUERTO 2----------------------PUERTO 2
                if (rutas.get(i).getCiudad2().equals("NY") && j == 3) {
                    pos2 = j;

                    //IMPRIMIR LAS TRES CIUDADES SECUNDARIAS
                    for (int k = 1; k < puertos2.length; k++) {
                        //CIUDAD SECUNDARIA 1
                        if (rutas.get(i).getCiudad3().equals("AMS") && k == 1) {
                            pos3 = k;

                            //CIUDAD TERCIARIA 1
                            if (i == 6) {
                                pos4 = k + 1;
                                pos5 = k + 3;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 7){
                                pos4 = k + 3;
                                pos5 = k + 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                        //CIUDAD SECUNDARIA 2
                        if (rutas.get(i).getCiudad3().equals("CAS") && k == 2) {
                            pos3 = k;
                            //CIUDAD TERCIARIA 1
                            if (i == 8) {
                                pos4 = k - 1;
                                pos5 = k + 2;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 9){
                                pos4 = k + 2;
                                pos5 = k - 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }

                        }
                        //CIUDAD SECUNDARIA 3
                        if (rutas.get(i).getCiudad3().equals("HEL") && k == 4) {
                            pos3 = k;
                            //CIUDAD TERCIARIA 1
                            if (i == 10) {
                                pos4 = k - 3;
                                pos5 = k - 2;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 11){
                                pos4 = k - 2;
                                pos5 = k - 3;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                    }
                }//FIN PUERTO 2

                //PUERTO 3-------------------PUERTO 3----------------------PUERTO 3
                if (rutas.get(i).getCiudad2().equals("CAS") && j == 2) {
                    pos2 = j;
                    //IMPRIMIR LAS TRES CIUDADES SECUNDARIAS
                    for (int k = 1; k < puertos2.length; k++) {
                        //CIUDAD SECUNDARIA 1
                        if (rutas.get(i).getCiudad3().equals("AMS") && k == 1) {
                            pos3 = k;
                            //CIUDAD TERCIARIA 1
                            if (i == 12) {
                                pos4 = k + 2;
                                pos5 = k + 3;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 13){
                                pos4 = k + 3;
                                pos5 = k + 2;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                        //CIUDAD SECUNDARIA 2
                        if (rutas.get(i).getCiudad3().equals("NY") && k == 3) {
                            pos3 = k;
                            //CIUDAD TERCIARIA 1
                            if (i == 14) {
                                pos4 = k - 2;
                                pos5 = k + 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 15){
                                pos4 = k + 1;
                                pos5 = k - 2;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                        //CIUDAD SECUNDARIA 3
                        if (rutas.get(i).getCiudad3().equals("HEL") && k == 4) {
                            pos3 = k;
                            //CIUDAD TERCIARIA 1
                            if (i == 16) {
                                pos4 = k - 3;
                                pos5 = k - 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 17){
                                pos4 = k - 1;
                                pos5 = k - 3;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                    }
                }//FIN PUERTO 3

                //PUERTO 4-------------------PUERTO 4----------------------PUERTO 4
                if (rutas.get(i).getCiudad2().equals("AMS") && j == 1) {
                    pos2 = j;

                    //IMPRIMIR LAS TRES CIUDADES SECUNDARIAS
                    for (int k = 1; k < puertos2.length; k++) {
                        //CIUDAD SECUNDARIA 1
                        if (rutas.get(i).getCiudad3().equals("CAS") && k == 2) {
                            pos3 = k;

                            //CIUDAD TERCIARIA 1
                            if (i == 18) {
                                pos4 = k + 1;
                                pos5 = k + 2;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 19){
                                pos4 = k + 2;
                                pos5 = k + 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                        //CIUDAD SECUNDARIA 2
                        if (rutas.get(i).getCiudad3().equals("NY") && k == 3) {
                            pos3 = k;
                            //CIUDAD TERCIARIA 1
                            if (i == 20) {
                                pos4 = k - 1;
                                pos5 = k + 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 21){
                                pos4 = k + 1;
                                pos5 = k - 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                        //CIUDAD SECUNDARIA 3
                        if (rutas.get(i).getCiudad3().equals("HEL") && k == 4) {
                            pos3 = k;

                            //CIUDAD TERCIARIA 1
                            if (i == 22) {
                                pos4 = k - 2;
                                pos5 = k - 1;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                            //CIUDAD TERCIARIA 2
                            if(i == 23){
                                pos4 = k - 1;
                                pos5 = k - 2;
                                cuenta = distancia[pos1][pos2] + distancia[pos2][pos3] + distancia[pos3][pos4]
                                        + distancia[pos4][pos5] + distancia[pos5][pos1];
                                distanciaTotal[i] = cuenta;
                            }
                        }
                    }

                }//FIN PUERTO 4

            }// FIN 4 CIUDADES PRINCIPALES

        }// FIN 24 RUTAS
    }
    public void calcularDioxido(){
        int dist;
        double diox;
        for(int i=0; i<distanciaTotal.length; i++){
            dist = distanciaTotal[i];
            diox = Math.round(dist*0.02);
            dioxido[i] = diox;
        }
    }
    public void rutaMasCorta(){
        int menor;
        menor = distanciaTotal[0];
        for(int i=0; i<distanciaTotal.length; i++){
            if(distanciaTotal[i]<menor){
                menor = distanciaTotal[i];
            }
        }
        System.out.println("La ruta más corta es: "+menor);
    }
}
class Rutas {

    private String ciudad1, ciudad2, ciudad3, ciudad4, ciudad5, ciudad6;

    public Rutas(String ciudad1, String ciudad2, String ciudad3, String ciudad4, String ciudad5, String ciudad6) {
        this.ciudad1 = ciudad1;
        this.ciudad2 = ciudad2;
        this.ciudad3 = ciudad3;
        this.ciudad4 = ciudad4;
        this.ciudad5 = ciudad5;
        this.ciudad6 = ciudad6;
    }

    public String getRuta() {
        return ciudad1 + " " + ciudad2 + " " + ciudad3 + " " + ciudad4 + " " + ciudad5 + " " + ciudad6;
    }

    @Override
    public String toString() {
        return  ciudad1+" - "+ciudad2+" - "+ciudad3+" - "+ciudad4+" - "+ciudad5+" - "+ciudad6;
    }

    public String getCiudad2() {
        return ciudad2;
    }

    public String getCiudad3() {
        return ciudad3;
    }

    public void setCiudad4(String ciudad4) {
        this.ciudad4 = ciudad4;
    }

    public void setCiudad5(String ciudad5) {
        this.ciudad5 = ciudad5;
    }

}