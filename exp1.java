import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;
public class exp1 {

final static int MaxMemory = 128; //总内存
static int proCount = 0; //进程计数器
static int cnt = 0;

static class Pro{
	public int memory;
	public Pro() {
		proCount++;
		this.memory = (int)(Math.random()*50+1);
	}
}

static Queue<Pro> NewQueue = new LinkedList<Pro>();
static Queue<Pro> ReadyQueue = new LinkedList<Pro>();
static Queue<Pro> RunQueue = new LinkedList<Pro>();
static Queue<Pro> BlockQueue = new LinkedList<Pro>();
public static  void Dispath() {
	if(RunQueue.size()<1)
	{
		if(ReadyQueue.size()>0)
		{
			RunQueue.offer(ReadyQueue.poll());
		}
	}
}
public static void Timeout() {
	if(RunQueue.size()==1)
	{
		ReadyQueue.offer(RunQueue.poll());
		Dispath();
	}

}
public static void EventWait() {
	if(RunQueue.size()==1)
	{
		BlockQueue.offer(RunQueue.poll());
		Dispath();
	}
}
public static void EvenOccur() {
	if(BlockQueue.isEmpty()==false)
	{
		ReadyQueue.offer(BlockQueue.poll());
		Dispath();
	}
}
public static int getMemory(Queue<Pro> q)
{
	int len = q.size();
	int sum = 0;
	for(int i=0;i<len;i++)
	{
		Pro tmp = q.poll();
		sum += tmp.memory;
		q.offer(tmp);
	}
	return sum;
}
public static int Admit() {
	if(NewQueue.isEmpty())
		return 0;
	int sum = 0;
	sum += getMemory(ReadyQueue);
	sum += getMemory(RunQueue);
	sum += getMemory(BlockQueue);
	int len = NewQueue.size();
	Pro front = NewQueue.poll();
	sum += front.memory;
	
	if(sum<=MaxMemory)
	{
		ReadyQueue.offer(front);
		Dispath();
	}
	else {
		NewQueue.offer(front);
		for(int i=0;i<len-1;i++) {
			NewQueue.offer(NewQueue.poll());
			}
		}
	return 1;
}

public static void Create() {
	Pro tmp = new Pro();
	NewQueue.offer(tmp);
	Admit();
}

public static void Relase() {
	if(RunQueue.size()==1)
	{
		RunQueue.poll();
		Admit();
		Dispath();
	}
	
}

public static void drawTextField(Queue<Pro> p,JTextField tf){
	int len = p.size();
	tf.setText("");
	for(int i=0;i<len;i++)
	{
		Pro tmp = p.poll();
		String memory = tmp.memory+"";
		String number = proCount+"";
		String str = number+","+memory;
		tf.setText(tf.getText()+" "+str);
		p.offer(tmp);
		
	}
}
public static void showQueue(Queue<Pro> p)
{
	int len = p.size();
	for(int i=0;i<len;i++)
	{
		Pro tmp = p.poll();
		String memory = tmp.memory+"";
		String number = proCount+"";
		String str = number+","+memory;
		System.out.print(str+"  ");
		p.offer(tmp);
	}
	System.out.println();
}
public static void drawNew(JTextField tf) {
	System.out.println(cnt++);
	drawTextField(NewQueue, tf);
	System.out.print("new:");
	showQueue(NewQueue);
}
public static void drawReady(JTextField tf) {
	drawTextField(ReadyQueue, tf);
	System.out.print("ready:");
	showQueue(ReadyQueue);
}
public static void drawRun(JTextField tf) {
	drawTextField(RunQueue, tf);
	System.out.print("run:");
	showQueue(RunQueue);
}
public static void drawBlock(JTextField tf) {
	drawTextField(BlockQueue, tf);
	System.out.print("block:");
	showQueue(BlockQueue);
}

	public static void main(String[] args) {
		JFrame MainWindow = new JFrame("实验一");
		MainWindow.setLayout(new FlowLayout(FlowLayout.LEFT));
		MainWindow.setSize(1700, 600);
		MainWindow.setVisible(true);
		JButton DispathBtn = new JButton("Dispath");
		JButton TimeoutBtn = new JButton("Timeout");
		JButton EventWaitBtn = new JButton("EventWait");
		JButton EventOccurBtn = new JButton("EventOccur");
		JButton CreateBtn = new JButton("Create");
		JButton AdmitBtn = new JButton("Admit");
		JButton ReleaseBtn = new JButton("Release");
		MainWindow.add(DispathBtn);
		MainWindow.add(TimeoutBtn);
		MainWindow.add(EventWaitBtn);
		MainWindow.add(EventOccurBtn);
		MainWindow.add(CreateBtn);
		MainWindow.add(AdmitBtn);
		MainWindow.add(ReleaseBtn);
		JLabel New =new JLabel("New");
		JTextField NewText = new JTextField(20);
		MainWindow.add(New);
		MainWindow.add(NewText);
		JLabel Ready =new JLabel("Ready");
		MainWindow.add(Ready);
		JTextField ReadyText = new JTextField(20);
		MainWindow.add(ReadyText);
		JLabel Running =new JLabel("Running");
		MainWindow.add(Running);
		JTextField RunningText = new JTextField(20);
		MainWindow.add(RunningText);
		JLabel Block =new JLabel("Block");
		MainWindow.add(Block);
		JTextField BlockText = new JTextField(20);
		MainWindow.add(BlockText);
		DispathBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Dispath();
				drawNew(NewText);
				drawBlock(BlockText);
				drawReady(ReadyText);
				drawRun(RunningText);

			}
		});
		TimeoutBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Timeout();
				drawNew(NewText);
				drawBlock(BlockText);
				drawReady(ReadyText);
				drawRun(RunningText);
				
			}
		});
		EventOccurBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EvenOccur();
				drawNew(NewText);
				drawBlock(BlockText);
				drawReady(ReadyText);
				drawRun(RunningText);
			}
		});
		EventWaitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EventWait();
				drawNew(NewText);
				drawBlock(BlockText);
				drawReady(ReadyText);
				drawRun(RunningText);
			}
		});
		CreateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Create();
				drawNew(NewText);
				drawBlock(BlockText);
				drawReady(ReadyText);
				drawRun(RunningText);
				
			}
		});
		AdmitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Admit();
				drawNew(NewText);
				drawBlock(BlockText);
				drawReady(ReadyText);
				drawRun(RunningText);
				
			}
		});
		ReleaseBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Relase();
				drawNew(NewText);
				drawBlock(BlockText);
				drawReady(ReadyText);
				drawRun(RunningText);
			}
		});
		MainWindow.validate();
	}
}
