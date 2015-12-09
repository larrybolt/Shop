import javax.swing.JOptionPane;

import domain.ShopFacade;

public class UiController {
	private ShopFacade shopFacade;
	private ScanAdvancedView scanView;
	private CustomerView customerView;

	public UiController(){
		shopFacade = new ShopFacade();
		scanView = new ScanAdvancedView(this);
		customerView = new CustomerView(this);
	}
	public void showScanView(){
		scanView.showView();
		customerView.showView();
		shopFacade.addObserver(scanView);
		shopFacade.addObserver(customerView);
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
	public void applyKorting(String code){
		try {
			shopFacade.applyKorting(code);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: "+e.getMessage());
			System.out.println("AddProduct error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public ShopFacade getShopFacade(){
		return shopFacade;
	}

	public String formatTotal(double totalCost){
		return formatPrice(totalCost);
	}
	public String formatPrice(double price){
		return String.format("%.2f EUR", price);
	}
	public void pay(double amound){
		shopFacade.pay(amound);
	}
}