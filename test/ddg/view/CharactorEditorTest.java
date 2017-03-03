/**
 * 
 */
package ddg.view;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ddg.Config;
import ddg.item.entity.Ability;
import ddg.item.entity.BaseItem;
import ddg.model.Fighter;
import ddg.model.FighterModel;
import ddg.utils.Utils;

/**
 * This class is test character edit
 * 
 * @author Fei Yu
 * @author Zhen Du
 * @date Mar 3, 2017
 */
public class CharactorEditorTest {

	private Fighter fighter;
	/**
	 * This method is set environment
	 * 
	 */
	@Before
	public void setUpBefore() {
		String g = Utils.readFile(Config.CHARACTOR_FILE);
		FighterModel fighterModel = Utils.fromJson(g, FighterModel.class);
		if(fighterModel != null){        			
    		try{
        		if( null!=fighterModel.getFighters() ){
        			HashMap<String, Fighter> map = fighterModel.getFighters();
                    Set<String> keySet1 = map.keySet();
                    Iterator<String> it1 = keySet1.iterator();
                    
                    if (it1.hasNext()){
                    	String keyName = it1.next();
                    	fighter = map.get(keyName);
                    }
        		}
    		}
    		catch (NullPointerException ex){
    			System.out.println("there is a NullPointerException");
    		}        			
		}
	}

	@Test
	public void testAbility() {
		BaseItem i = new BaseItem(BaseItem.RING, 3, Ability.STRENGTH);
		int init = fighter.getStrength();

		fighter.wearItem(i);
		
		int after = fighter.getStrength();
		assertTrue(after-init==3);
	}

	@Test
	public void testWearingItem() {
		int n = fighter.getWorn().size();
		BaseItem i = new BaseItem(BaseItem.RING, 3, Ability.STRENGTH);
		i.setBonus(3);

		fighter.wearItem(i);
		fighter.wearItem(i);
		fighter.wearItem(i);
		assertTrue(fighter.getWorn().size()-n==1);
	}
}
