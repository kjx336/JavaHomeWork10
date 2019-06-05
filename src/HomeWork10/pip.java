/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
/**
 *
 * @author kjx33
 */
class Demo extends Thread {
    private boolean flag;
    private Thread t;
    private String threadName;
    byte[] bys = new byte[1024];
    PipedOutputStream out = null;
    PipedInputStream in = null;
    Demo(String name,boolean Flag) {
        threadName = name;
        this.flag = Flag;
    }
    public PipedOutputStream getpipedoutputstream(){
        out = new PipedOutputStream();
        return out;
    }
    public PipedInputStream getPipedInputputStream(){
        in = new PipedInputStream();
        return in;
      }
    public void run() {
      System.out.println("Thread进程" + this.threadName + "正在运行");
      for(;;){
          if(flag==false){
            try{
                in.read(bys);
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
            String receive = new String(bys).trim();
            if(receive.equals("1")){
                this.flag = true;
            }
          }
          if(flag==true){
              System.out.println(this.threadName);
              this.flag = false;
              try{
                  out.write("1".getBytes());
              }catch(IOException e){
                  System.out.println(e.getMessage());
              }
              
          }
      }
    }
    public void start () {
       if (t == null) {
          t = new Thread (this, threadName);
          t.start ();
       }
    }
}
public class pip {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Demo A = new Demo("A",true);
        Demo B = new Demo("B",false);
        A.getPipedInputputStream().connect(B.getpipedoutputstream());
        B.getPipedInputputStream().connect(A.getpipedoutputstream());
        A.start();
        B.start();
        
    }
    
}
