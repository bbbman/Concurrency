package threadTest.sample;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
class Board{
	private int maxX=100;
	private int maxY=100;
	public Board getSubBoard(int count,int i){
		return new Board();
	}
	public void commitNewValues(){}
	public boolean hasConverged(){return false;}
	public int getMaxX(){ return maxX;}
	public int getMaxY(){return maxY;}
	public void setNewValue(int x,int y,boolean isBig){}
	public void waitForConvergence(){}
}
public class CellularAutomata {
	private final Board mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;
	public CellularAutomata(Board board){
		this.mainBoard = board;
		int count = Runtime.getRuntime().availableProcessors();
		this.barrier = new CyclicBarrier(count,new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mainBoard.commitNewValues();
			}			
		});
		this.workers = new Worker[count];
		for(int i=0; i<count;i++)
			workers[i] = new Worker(mainBoard.getSubBoard(count,i));
	}
	private class Worker implements Runnable{
		private final Board board;
		public Worker(Board board){this.board = board;}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!board.hasConverged()){
				for(int x=0; x<board.getMaxX();x++)
					for(int y=0; y<board.getMaxY();y++)
						board.setNewValue(x,y,computeValue(x,y));
				try{
					barrier.await();//每次调用 await() 都将返回能到达屏障处的线程的索引
				}catch(InterruptedException ex){
					return;
				}catch (BrokenBarrierException ex){
					return;
				}
			}
		}
		public void start(){
			for(int i=0; i<workers.length;i++){
				new Thread(workers[i]).start();
				mainBoard.waitForConvergence();
			}
		}
		private boolean computeValue(int x,int y){return x>=y;}		
	}
}
