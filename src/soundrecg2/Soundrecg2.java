/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundrecg2;

import com.sun.media.sound.FFT;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author thegreatz6
 */
public class Soundrecg2 {

    /**
     * @param args the command line arguments
     */
    static OutputStream out;
    static double[] highscores;
    static double[] recordPoints;

    public static void main(String[] args) throws LineUnavailableException, SQLException {
        AudioFormat format = getformat();
        TargetDataLine microphone;
        byte[] data = null;
        try {
            microphone = AudioSystem.getTargetDataLine(format);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);

            out = new ByteArrayOutputStream();
            int numBytesRead;
            data = new byte[microphone.getBufferSize()];
            microphone.start();

            int bytesRead = 0;

            try {
                while (bytesRead < 1000000) { //Just so I can test if recording my mic works...
                    numBytesRead = microphone.read(data, 0, data.length);
//           System.out.println("numbytes read is"+numBytesRead);
                    bytesRead = bytesRead + numBytesRead;
//            System.out.println("bytes read is"+bytesRead);
//            System.out.println(bytesRead);
//              System.out.println(numBytesRead);
//                    out.write(data, 0, numBytesRead);
                }
//        for(int i=0;i<data.length;i++){
//        
//        System.out.print(data[i]+"\t");
//        if(i%5==0){
//        System.out.println();}
//       
//        }
            } catch (Exception e) {
                e.printStackTrace();
            }
            microphone.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
//byte audio[]=data[];

        int totalSize = data.length;
//         System.out.print("this is data length"+data.length);
//	System.out.println(totalSize);
        int amountPossible = totalSize / 1024;
//        System.out.print("this is amount posible value1" + amountPossible);
//        System.out.println();
        //When turning into frequency domain we'll need complex numbers:
        Complex[][] results = new Complex[amountPossible][];

        //For all the chunks:
        int times;
        for (times = 0; times < amountPossible; times++) {
            Complex[] complex = new Complex[1024];
            for (int i = 0; i < 1024; i++) {
                //Put the time domain data into a complex number with imaginary part as 0:
                complex[i] = new Complex(data[(1024 * times) + i], 0);
//            System.out.println(complex[i]);

            }
            FFt_1 ff;
            ff = new FFt_1();

            results[times] = ff.fft(complex);


        }

     
       

         int[] mag = new int[results.length * 1024];
        for (int y = 1; y < results.length; y++) {
            for (int n = 1; n < results[y].length; n++) {
                if (((int) (results[y][n].abs())) > 0) {
                    mag[1024 * y + n] = (int) (results[y][n].abs());

                }

            }

 
        }
        int p = totalSize / 44100;
        int[] hj = new int[44100];
        for (int d = 0; d < p; d++) {

            for (int f = 1; f < 44100; f++) {
                hj[f] = mag[f + 44100 * d];

            }
            Makehashkey mh = new Makehashkey(hj);
            for (int i = 0; i < mh.hashkeys.length; i++) {
                System.out.print("this is hash key"+mh.hashkeys[i]);
                System.out.print("this is ur music"+AvaDB.findMusic(mh.hashkeys[i]));
            }
        }
    }
    public static  int[] RANGE = new int[]{40, 80, 120, 180, 4090 + 1};

    //Find out in which range
    public static int getIndex(int freq) {
        int i = 0;
        while (RANGE[i] < freq) {
            i++;
        }
        return i;
    }

    private static AudioFormat getformat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 1; //mono
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
   
    }
}
