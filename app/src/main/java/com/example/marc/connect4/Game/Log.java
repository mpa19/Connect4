package com.example.marc.connect4.Game;

import android.os.Parcel;
import android.os.Parcelable;

public class Log implements Parcelable {

    String jugador1;
    String jugador2;
    String data;
    String graella;
    String tiempo;
    String resultado;
    String movimientos;


    public Log(String jugador1, String jugador2, String data, String graella, String tiempo, String resultado, String movimientos) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.data = data;
        this.graella = graella;
        this.tiempo = tiempo;
        this.resultado = resultado;
        this.movimientos = movimientos;
    }

    protected Log(Parcel in) {
        jugador1 = in.readString();
        jugador2 = in.readString();
        data = in.readString();
        graella = in.readString();
        tiempo = in.readString();
        resultado = in.readString();
        movimientos = in.readString();
    }

    public String getJugador1(){
        return this.jugador1;
    }

    public String getJugador2(){
        return this.jugador2;
    }

    public String getData(){
        return this.data;
    }

    public String getGraella(){
        return this.graella;
    }

    public String getTiempo(){
        return this.tiempo;
    }

    public String getResultado(){
        return this.resultado;
    }

    public String getMovimientos(){
        return this.movimientos;
    }

    public static final Creator<Log> CREATOR = new Creator<Log>() {
        @Override
        public Log createFromParcel(Parcel in) {
            return new Log(in);
        }

        @Override
        public Log[] newArray(int size) {
            return new Log[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jugador1);
        dest.writeString(jugador2);
        dest.writeString(data);
        dest.writeString(graella);
        dest.writeString(tiempo);
        dest.writeString(resultado);
        dest.writeString(movimientos);
    }

}
