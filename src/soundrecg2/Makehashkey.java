/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundrecg2;

/**
 *
 * @author thegreatz6
 */
public class Makehashkey {

    int n=44100;
    int[] p1 = new int[3];
    int[] cluster = new int[n];
    int[] maxindex = new int[3];
    String[] hashkeys = new String[9];

    public Makehashkey(int[] t) {
        this.cluster = t;
        findbigest();
    }

    public void findbigest() {
        int first = 0;
        int temp = 99999;
        int q2 = 0;
        for (int j = 0; j < 2; j++) {

            for (int f = 0; f < cluster.length; f++) {
                if (cluster[f] > first && cluster[f] < temp) {

                    first = cluster[f];
                    temp = first;
                    q2 = f;
                }
            }
            maxindex[j] = q2;
            p1[j] = first;

        }
        hashkeys = hash(cluster, maxindex);
    }

    public String[] hash(int a[], int[] b) {

        String[] hashkey = new String[9];
        int counter = 0;
        for (int i = 0; i <= 2; i++) {
            for (int y = 0; y <= 2; y++) {
                hashkey[counter] = a[b[i]] + ":" + (a[b[i]] - a[b[i]+y]) + ":" + y;
                counter++;
            }

        }
        return hashkey;
    }
}
