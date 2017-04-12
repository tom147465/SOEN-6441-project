package ddg.ui.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ddg.Config;
import ddg.model.FighterModel;
import ddg.model.ItemEditorModel;
import ddg.model.item.BaseItem;
import ddg.model.item.Item;
import ddg.ui.view.component.ListEntryCellRenderer;
import ddg.utils.Utils;

/**
 * @author Bo
 * dialog for selecting a item put in the chest
 *
 */
public class PopUpForItem extends JDialog implements ActionListener{
//	private JFrame owner;
	private ItemEditorModel itemsmodel;
	private JList itemslist;
	private JTextArea detailofitem;
	private Item selecteditem;
	
	/**
     * @param owner	the owner frame of this dialog
     * @param title	the title of this dialog
     */
	public PopUpForItem(JFrame owner, String title) {
		super(owner,title);
//		this.owner = owner;
//		Container contofframe = this.getContentPane();
		selecteditem = null;
		initData();
		initView();
	}

	private void initData() {
//		String g = Utils.readFile(Config.ITEM_FILE);
//		this.itemsmodel = Utils.fromJson(g, ItemEditorModel.class);
		this.itemsmodel = Utils.readObject(Config.ITEM_FILE, ItemEditorModel.class);
		if (this.itemsmodel == null) {
			this.itemsmodel = new ItemEditorModel();
		}
		BaseItem key = new BaseItem("key", 1, "wisdom");
		itemsmodel.addItem(key);
	}
	
	ListSelectionListener slsnr = new ListSelectionListener() {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			if (e.getValueIsAdjusting() == false) {
				int index = itemslist.getSelectedIndex();
				if(index >= 0) {
					System.out.println("list select:"+index);
					Item item = itemsmodel.getItemByIndex(index);
					detailofitem.setText(item.getId() + "\n\n +" + item.getBonus() +" "+item.getIncrease());
				}
			}
		}
	};
	
	private void initView() {
		setSize(500,500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
        
		addListView();
		addContentView();
		
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	private void addListView(){
		JPanel listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(Config.OPTION_WIDTH, Config.OPTION_HEIGHT));
		itemslist = new JList(itemsmodel.getListModel());
		itemslist.setCellRenderer(new ListEntryCellRenderer());
		itemslist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemslist.addListSelectionListener(slsnr);
        JScrollPane listScrollPane = new JScrollPane(itemslist);
        listScrollPane.setPreferredSize(new Dimension(Config.OPTION_WIDTH, 400));
        listPanel.add(listScrollPane);
        
        getContentPane().add(listPanel, BorderLayout.WEST);
	}
	
	private void addContentView(){
		JPanel contentPanel = new JPanel();
		detailofitem = new JTextArea(5,10);
		detailofitem.setEditable(false);
		
		JButton bensure = new JButton("Ensure");
		bensure.addActionListener(this);
		
		contentPanel.add(detailofitem);
		contentPanel.add(bensure);
		
		getContentPane().add(contentPanel);
	}
	
	/**
	 * @return the item which selected by user, then put into the chest
	 */
	public Item getSelecteditem() {
		return selecteditem;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Ensure")){
			int index = itemslist.getSelectedIndex();
			if(index>=0){
				selecteditem = itemsmodel.getItemByIndex(itemslist.getSelectedIndex());
				System.out.println(selecteditem.getId());
			}
			JButton button = (JButton)e.getSource();
            SwingUtilities.getWindowAncestor(button).dispose();
		}
	}
}
