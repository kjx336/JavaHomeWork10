/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HomeWork10;
import java.io.*;

class FileStream{
    public static void start(String c){
        try {
            FileOutputStream fout =  new FileOutputStream("e:/test.txt");
            PrintStream ps = new PrintStream(fout);
            ps.println(c);
        } catch (IOException e) {
            System.out.print("Exception: " + e);
        }
    }
}

public class ADD {

    public static void main(String[] args)throws IOException {
        int c,total;
        total = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输入字符, 按下 '0' 键退出。");
        do {
            String str = br.readLine();
            c = Integer.parseInt(str);
            System.out.println("c = " + c);
            total = total +c;
            System.out.println("total = " + total);
        } while (c != 0);
        System.out.println("和为" + total);
        FileStream.start("和为" + total);
    }
    
}

/*
import java.io.*;
import java.util.*;

public class NumberPlus {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入两个正整数");
        int a = in.nextInt();
        int b = in.nextInt();
        int c = a+b;
        try{
            FileOutputStream fout =  new FileOutputStream("d:/test.txt");
            PrintStream ps = new PrintStream(fout);
            ps.println(a+"和"+b+"相加的结果是："+c);     
        }catch(IOException ioe){
            System.out.println(ioe);
        }
        
    }
}
*/