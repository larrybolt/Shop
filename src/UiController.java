import javax.swing.JOptionPane;

import domain.ShopFacade;

public class UiController {
	private ShopFacade shopFacade;
	private ScanView scanView;
	
	public UiController(){
		shopFacade = new ShopFacade();
		// TODO: vragen of dit de beste methode is
		scanView = new ScanView(this);
	}
	public void showScanView(){
		scanView.showView();
		// TODO: vragen of dit zo moet
		shopFacade.addObserver(scanView);
	}
	public void addProduct(String id, String amount){
		try {
			shopFacade.addProduct(Integer.parseInt(id), Integer.parseInt(amount));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: "+e.getMessage());
			System.out.println("AddProduct error: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
