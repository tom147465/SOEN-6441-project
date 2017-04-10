/**
 *
 * This class
 * 
 * @author Zhen Du
 * @date Apr 6, 2017
 */
package ddg.strategy;

import java.io.Serializable;

import ddg.model.Fighter;
import ddg.model.entity.Chest;
import ddg.model.entity.Game;

/**
 *
 * This class
 * 
 * @author Zhen Du
 * @date Apr 6, 2017
 */
/**
 *
 * This class
 * 
 * @author Zhen Du
 * @date Apr 6, 2017
 */
public class FriendlyStrategy implements IStrategy, Serializable{
	private static final long serialVersionUID = 1L;
	
	private Game game;
	private int xofcharactor;
	private int yofcharactor;
	/**
	 * Constructors
	 * 
	 */
	public FriendlyStrategy(Game game, int x, int y) {
		this.game = game;
		this.xofcharactor = x;
		this.yofcharactor = y;
	}

	@Override
	public void turn(TurnCallback cb) {
		System.out.println("this is friendly strategy, turn function!!!");
		wander(cb);
	}
	protected void wander(TurnCallback cb) {
		for(int i=3;i>0;){
			findChest();
			int num = ((int) (Math.random()*100)) % 4;
			switch (num+1){
				case 1 :
					if(!(xofcharactor-1==game.getXofplayer()&&yofcharactor==game.getYofplayer())
							&&game.getPlayingmap().npcMove(xofcharactor,yofcharactor,xofcharactor-1,yofcharactor)){
						xofcharactor = xofcharactor-1;
						i--;
					}
					break;
				case 2 :
					if(!(xofcharactor==game.getXofplayer()&&yofcharactor-1==game.getYofplayer())&&
							game.getPlayingmap().npcMove(xofcharactor,yofcharactor,xofcharactor,yofcharactor-1)){
						yofcharactor = yofcharactor-1;
						i--;
					}
					break;
				case 3 :
					if(!(xofcharactor+1==game.getXofplayer()&&yofcharactor==game.getYofplayer())&&
							game.getPlayingmap().npcMove(xofcharactor,yofcharactor,xofcharactor+1,yofcharactor)){
						xofcharactor = xofcharactor+1;
						i--;
					}
					break;
				case 4 :
					if(!(xofcharactor==game.getXofplayer()&&yofcharactor+1==game.getYofplayer())&&
							game.getPlayingmap().npcMove(xofcharactor,yofcharactor,xofcharactor,yofcharactor+1)){
						yofcharactor = yofcharactor+1;
						i--;
					}
					break;
			}
		}
		findChest();
	}

	private void findChest() {
		char[][] location = game.getPlayingmap().getLocation();
		Fighter fighter = (Fighter)game.getPlayingmap().getCellsinthemap()[xofcharactor][yofcharactor].getContent();

		if(xofcharactor-1>=0&&location[xofcharactor-1][yofcharactor]=='c'){
			Chest chest = (Chest)game.getPlayingmap().getCellsinthemap()[xofcharactor-1][yofcharactor].getContent();
			fighter.openChest(chest);
			if(chest.isEmpty()){
				game.getPlayingmap().changeLocation(xofcharactor-1, yofcharactor, 'e');
			}
		}
		else if(xofcharactor+1<game.getPlayingmap().getRow()&&location[xofcharactor+1][yofcharactor]=='c'){
			Chest chest = (Chest)game.getPlayingmap().getCellsinthemap()[xofcharactor+1][yofcharactor].getContent();
			fighter.openChest(chest);
			if(chest.isEmpty()){
				game.getPlayingmap().changeLocation(xofcharactor+1, yofcharactor, 'e');
			}
		}
		else if(yofcharactor-1>=0&&location[xofcharactor][yofcharactor-1]=='c'){
			Chest chest = (Chest)game.getPlayingmap().getCellsinthemap()[xofcharactor][yofcharactor-1].getContent();
			fighter.openChest(chest);
			if(chest.isEmpty()){
				game.getPlayingmap().changeLocation(xofcharactor, yofcharactor-1, 'e');
			}
		}
		else if(yofcharactor+1<game.getPlayingmap().getColumn()&&location[xofcharactor][yofcharactor+1]=='c'){
			Chest chest = (Chest)game.getPlayingmap().getCellsinthemap()[xofcharactor][yofcharactor+1].getContent();
			fighter.openChest(chest);
			if(chest.isEmpty()){
				game.getPlayingmap().changeLocation(xofcharactor, yofcharactor+1, 'e');
			}
		}
	}
	
}
