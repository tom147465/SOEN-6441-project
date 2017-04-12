package ddg.ui;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import ddg.model.Fighter;
import ddg.strategy.HumanStrategy;
import ddg.strategy.IStrategy.TurnCallback;
import ddg.utils.Dice;

/**
 *
 * This class
 * 
 * @author Zhen Du
 * @date Apr 6, 2017
 */
public class TurnDriven {

	private ArrayList<Fighter> fighters = new ArrayList<Fighter>();
	ArrayList<Integer> maporderrecode;
	ArrayList<Integer> orderarr;
//	private boolean stop = false;
	public TurnDriven() {
		
	}

	public ArrayList<Fighter> getFighters() {
		return fighters;
	}
	
	public void addFighter(Fighter fighter) {
		if(!this.fighters.contains(fighter)) {
			this.fighters.add(fighter);
		}
	}
	
	public void startTurn() {
		this.maporderrecode = new ArrayList<>(this.fighters.size()); 
		System.out.println("TurnDriven start");
		for(int i=0;i<this.fighters.size();i++){
			maporderrecode.add((Integer)(Dice.d20Roll()+fighters.get(i).getDexModifier()));
		}
		this.orderarr = (ArrayList<Integer>) maporderrecode.clone();
//		if(!stop){
//			next();
//		}
//		if(!stop) {
//			int sum = this.fighters.size();
//			if(sum == 0)
//				return;
//			int order[] = new int[sum];
//			for(int i=0;i<sum;i++) {
//				order[i] = i;
//			}
//			next(order, sum);
//		}
	}

	public void next() {
		
//		Fighter f = null;
//		boolean newTurn = false;
//		while(true) {
//			int d = Dice.d20Roll()%sum;//sum should < 20
//			if(order[d] != -1) {
//				order[d] = -1;
//				f = fighters.get(d);
//				break;
//			}
//			int total = 0;
//			for(int i=0;i<sum;i++) {
//				total +=order[i];
//			}
//			if(total == -sum) {
//				newTurn = true;
//				break;
//			}
//		}
		
		int maxloca = 0;
		for(int i=1;i<this.fighters.size();i++){
			if(orderarr.get(i)>orderarr.get(maxloca)){
				maxloca = i;
			}
		}
		
		if(orderarr.get(maxloca)==-1) {
			this.orderarr = (ArrayList<Integer>) maporderrecode.clone();
			next();
			return;
		}
		
		if(fighters.get(maxloca).isAlive()) {
			orderarr.set(maxloca, -1);
			fighters.get(maxloca).turn();
			
		}
		else{
			fighters.remove(maxloca);
			orderarr.remove(maxloca);
			next();
		}
		
		
	}
//	
//	public void finishTurn() {
//		System.out.println("TurnDriven finish");
//		stop = true;
//	}
}
