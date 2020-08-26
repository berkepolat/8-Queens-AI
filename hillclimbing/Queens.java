/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hillclimbing;

/**
 *
 * @author Polat
 */
public class Queens {
    private int satir;
    private int sutun;
    public Queens(int satir,int sutun){
        this.satir=satir;
        this.sutun=sutun;
    }
    public void ilerle(){
        satir++;
    }
    public int getSatir() {
        return satir;
    }
    public int getSutun() {
        return sutun;
    }
    public Boolean check(Queens q){
        if(q.getSatir()==satir || q.getSutun()==sutun){//Aynı satır veya sutunda vezir varsa true
            return true;
        }
        else if(Math.abs(sutun-q.getSutun())==Math.abs(satir-q.getSatir())){//Çapraz olarak vezir varsa true
            return true;
        }
        return false;
    }
    
}
