/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hillclimbing;

import java.awt.Button;

    public class Deneme{
        int deneyNo;
        int resSayi;
        double sure;
        int iterasyon;
        Button button;
        public Deneme(int deneyNo,int resSayi,double sure,int iterasyon){
            this.resSayi=resSayi;
            this.sure=0;
            this.iterasyon=0;
            this.button=new Button("Yerleşim");
        }
    }
