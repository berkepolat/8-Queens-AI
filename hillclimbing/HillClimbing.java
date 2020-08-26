/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hillclimbing;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import javax.swing.text.View;

/**
 *
 * @author Polat
 */
public class HillClimbing extends JFrame {

    private static int süre = 0;
    private static int heur = 0;
    private static int randomRes = 0;
    private static int iterasyon = 0;

    public static Queens[] tahta() {
        Queens[] tahta = new Queens[8];
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) {
            tahta[i] = new Queens(rnd.nextInt(8), i);
        }
        return tahta;
    }

    public static int h_Hesap(Queens[] queens) {
        int heur = 0;
        for (int i = 0; i < queens.length; i++) {
            for (int k = i + 1; k < queens.length; k++) {
                if (queens[i].check(queens[k])) {
                    heur++;
                }
            }
        }
        return heur;
    }

    public static Queens[] iterate(Queens[] tahta) {
        Queens[] gecici = new Queens[8];//Her bir iterasyon için bir önceki durumun ile kıyaslama yapmak için önceki tahta durumunu tutuyoruz.
        Queens[] sonraki = new Queens[8];
        int heuristic = h_Hesap(tahta);
        int optimum = heuristic;
        int geciciH;
        for (int i = 0; i < 8; i++) {
            sonraki[i] = new Queens(tahta[i].getSatir(), tahta[i].getSutun());
            gecici[i] = sonraki[i];
        }

        for (int h = 0; h < 8; h++) {
            if (h > 0) {
                gecici[h - 1] = new Queens(tahta[h - 1].getSatir(), tahta[h - 1].getSutun());
            }
            gecici[h] = new Queens(0, gecici[h].getSutun());

            for (int s = 0; s < 8; s++) {
                geciciH = h_Hesap(gecici);
                if (geciciH < optimum) {
                    optimum = geciciH;
                    for (int k = 0; k < 8; k++) {
                        sonraki[k] = new Queens(gecici[k].getSatir(), gecici[k].getSutun());
                    }
                }
                if (gecici[s].getSatir() != 7) {
                    gecici[s].ilerle();
                }
            }

        }

        if (optimum == heuristic) {
            randomRes++;
            sonraki = tahta();
            heur = h_Hesap(sonraki);
        } else {
            heur = optimum;
        }
        iterasyon++;
        return sonraki;
    }

    public static void printBoard(Queens[] q) {
        JFrame j = new JFrame();
        j.setSize(390, 410);
        //JScrollPane j = new JScrollPane();
        //j.setBounds(0, 0, 390, 410);
        JButton[][] butonlar = new JButton[8][8];
        int cx = 5;
        int cy = 5;
        int[] satirlar = new int[8];
        boolean beyaz = true;
        int h = 1;
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                h++;
                if (k == q[i].getSatir()) {
                    butonlar[i][k] = new JButton("");
                    butonlar[i][k].setIcon(new ImageIcon(Class.class.getResource("/resources/img.png")));
                    butonlar[i][k].setBounds(cx, cy, 40, 40);
                    j.add(butonlar[i][k]);
                    cx += 45;

                } else {
                    butonlar[i][k] = new JButton("");
                    butonlar[i][k].setBounds(cx, cy, 40, 40);
                    j.add(butonlar[i][k]);
                    cx += 45;

                }
                if (beyaz) {
                    butonlar[i][k].setBackground(Color.white);
                    beyaz = !beyaz;
                } else {
                    butonlar[i][k].setBackground(Color.black);
                    beyaz = !beyaz;
                }
            }
            beyaz = !beyaz;
            cx = 5;
            cy += 45;
        }
        j.setLayout(null);
        //frame.add(j);
        System.out.println("H=" + h);
        j.setVisible(true);

    }
    static ArrayList<Queens[]> qq = new ArrayList<Queens[]>();
    static ArrayList<Button> butonlar = new ArrayList<Button>();

    public static void main(String[] args) {
        Deneme[] liste = new Deneme[20];
        JFrame jf = new JFrame("8-Queens via Hill Climbing");
        jf.setSize(1000, 660);
        jf.setVisible(true);
        JTable tablo = new JTable();
        int coorx = 900;
        int coory = 20;
        int xsize = 90;
        int ysize = 30;
        String[] columnNames = {"Deney Numarası",
            "Restart Sayısı.",
            "Yer Değişim Sayısı",
            "Süre(saniye)"};
        DefaultTableModel model = new DefaultTableModel();
        tablo.setModel(model);
        model.setColumnIdentifiers(columnNames);
        tablo.setBackground(Color.gray);
        tablo.setForeground(Color.white);
        Font font = new Font("", 1, 22);
        tablo.setFont(font);
        tablo.setRowHeight(30);
        JScrollPane pane = new JScrollPane(tablo);
        pane.setBounds(0, 0, 900, 660);
        jf.setLayout(null);
        jf.add(pane);

        for (int i = 0; i < 20; i++) {
            randomRes=0;
            iterasyon=0;
            System.out.println("Deney" + (i + 1) + "tamamlandı");
            double sure;
            long start = System.currentTimeMillis();
            int h;
            Queens[] queens = tahta();
            h = h_Hesap(queens);
            while (h != 0) {
                queens = iterate(queens);
                h = heur;
            }
            final Queens[] qq = queens.clone();
            long stop = System.currentTimeMillis();
            long time = stop - start;
            sure = (double) time / 1000;
            liste[i] = new Deneme(i + 1, randomRes, sure, iterasyon);
            Object[] obj = new Object[5];
            obj[0] = i + 1;
            obj[1] = randomRes;
            obj[2] = iterasyon;
            obj[3] = sure;
            butonlar.add(new Button("Göster"));
            butonlar.get(i).setBounds(coorx, coory, xsize, ysize);
            butonlar.get(i).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    printBoard(qq);
                }
            });
            jf.add(butonlar.get(i));
            coory += 30;
            model.addRow(obj);
            
        }

        JOptionPane.showMessageDialog(null, "20 Deney Başarıyla Tamamalandı", "Hill Climbing 8 Queens", JOptionPane.INFORMATION_MESSAGE);

    }

    /*@Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == butonlar.get(0)) {
            printBoard(qq.get(0));
        } else if (ae.getSource() == butonlar.get(1)) {
            printBoard(qq.get(1));
            System.out.println("kjahskdjasd");
        } else if (ae.getSource() == butonlar.get(2)) {
            printBoard(qq.get(2));
        } else if (ae.getSource() == butonlar.get(3)) {
            printBoard(qq.get(3));
        } else if (ae.getSource() == butonlar.get(4)) {
            printBoard(qq.get(4));
        } else if (ae.getSource() == butonlar.get(5)) {
            printBoard(qq.get(5));
        } else if (ae.getSource() == butonlar.get(6)) {
            printBoard(qq.get(6));
        } else if (ae.getSource() == butonlar.get(7)) {
            printBoard(qq.get(7));
        } else if (ae.getSource() == butonlar.get(8)) {
            printBoard(qq.get(8));
        } else if (ae.getSource() == butonlar.get(9)) {
            printBoard(qq.get(9));
        } else if (ae.getSource() == butonlar.get(10)) {
            printBoard(qq.get(10));
        } else if (ae.getSource() == butonlar.get(11)) {
            printBoard(qq.get(11));
        } else if (ae.getSource() == butonlar.get(12)) {
            printBoard(qq.get(12));
        } else if (ae.getSource() == butonlar.get(13)) {
            printBoard(qq.get(13));
        } else if (ae.getSource() == butonlar.get(14)) {
            printBoard(qq.get(14));
        } else if (ae.getSource() == butonlar.get(15)) {
            printBoard(qq.get(15));
        } else if (ae.getSource() == butonlar.get(16)) {
            printBoard(qq.get(16));
        } else if (ae.getSource() == butonlar.get(17)) {
            printBoard(qq.get(17));
        } else if (ae.getSource() == butonlar.get(18)) {
            printBoard(qq.get(18));
        } else if (ae.getSource() == butonlar.get(19)) {
            printBoard(qq.get(19));
        }

    }*/
}
