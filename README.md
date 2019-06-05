 JavaHomeWork10
输入输出
===============
任务与整体解决方案：
------
* 编写程序，将键盘输入的若干整数相加，并把它们全部写入给定文件中。

* java.io包中定义的PipedInputStream和PipedOutputStream类是专门针对线程数据I/O的管道流。请查阅相关API文档，了解它们的使用方式，并修改第9章的习题9，让两个线程通过管道互通信息，实现交替打印功能。

### 编写程序，将键盘输入的若干整数相加，并把它们全部写入给定文件中。

从标准输入流获取数字并相加并不难，新知识是使用输出流将一个数字输出至文件。

### java.io包中定义的PipedInputStream和PipedOutputStream类是专门针对线程数据I/O的管道流。请查阅相关API文档，了解它们的使用方式，并修改第9章的习题9，让两个线程通过管道互通信息，实现交替打印功能。

难点。使用线程间通信的管道流，定义线程时的构造方法内含一个布尔型信号量，在run方法中，当信号量为false，则持续等待来自另一个线程的通信；若信号量为true，则打印名称，将信号量变为false，之后向另一个线程通信。

领悟：
------
### 向文件的写入操作
首先需要new一个FileOutputStream类的实例，之后用PrintStream进行包装。如下：
```java
FileOutputStream fout =  new FileOutputStream("e:/test.txt");
PrintStream ps = new PrintStream(fout);
ps.println(c);
```
同时需要进行IOException的异常捕捉。使用try-catch块就行了

### 对pip管道通信流的理解
非常有意思的一个东西。我的理解是，就像是一组插销。一组插销有插销头和插销板，插上才能用。PipedInputStream就像是插销板，PipedOutputStream就像是插销头。想要输入或输出操作必须声明这俩。由于本题是线程间的相互通信，所以定义类的时候需要把俩都定义上。也就是：
```
 public PipedOutputStream getpipedoutputstream(){
        out = new PipedOutputStream();
        return out;
    }
    public PipedInputStream getPipedInputputStream(){
        in = new PipedInputStream();
        return in;
      }
```
有了插销板和插销头了，还得把这俩插上。所以在main程序中：
```
        A.getPipedInputputStream().connect(B.getpipedoutputstream());
        B.getPipedInputputStream().connect(A.getpipedoutputstream());
```
这就算是把俩线程连上了。如果是单向传输只需要连一个就行。<br>
那怎么让这俩相互通信呢？首先要把一个字符串放到管道流里，这里用到的就是：
```
out.write("1".getBytes());
```
这样就是把“1”写入了管道流。提取时使用的就是
```
byte[] bys = new byte[1024];
in.read(bys);
String receive = new String(bys).trim();
```
这样就可以把之前输入的string在另一个线程这里用receive获取了。

### 关于是否需要flag信号量的讨论
最初设计线程类的时候，原计划是使用flag信号量进行控制的。当flag为true的时候打印，当flag为false时等待。整体流程是<br>
获取管道流内信息 -> flag变成true -> 打印线程名 -> 修改自身flag为false -> 传递信息至管道流。<br>
而在理解管道流之后，似乎可以这么干：<br>
判定管道流内是否有信息 -> 打印线程名 ->传递信息至管道流。<br>
首先，这样看起来就降低了每个线程的空间复杂性，毕竟少了个变量flag。但问题就又出现了。在线程的构造函数中，flag是变量之一，初始化flag的用途是激活循环。因为如果俩线程flag同时都是0，那就锁住了。如果去掉了flag，俩线程同时都是等待状态的话，就需要再想其他办法去激活这个循环。现在的想法是再定义一个线程，而且是个单向的，比如一个线程C只单向连接A，也就是只向A传递但不接收。然后用C去激活这个循环。这样的坏处是，空间复杂度反而又升高了，毕竟多了个线程...

<br>*Signed-off-by: 遥梦幽兰kzx <kjx336@163.com>*