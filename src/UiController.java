import javax.swing.JOptionPane;

import domain.ShopFacade;

public class UiController {
	private ShopFacade shopFacade;
	private ScanAdvancedView scanView;
	private CustomerView customerView;

	public UiController(){
		shopFacade = new ShopFacade();
		// TODO: vragen of dit de beste methode is
		scanView = new ScanAdvancedView(this);
		customerView = new CustomerView(this);
	}
	public void showScanView(){
		scanView.showView();
		customerView.showView();
		// TODO: vragen of dit zo moet
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

	// @TODO: dit is slecht, niet? Maar dan herhalen we praktisch hetzelfde vele maal
	/*
	bv om een lijst van producten op te halen uit domain:
	view.displayProducts ->
         controller.getProducts -> (deze stap lijkt overbodig)
         shopFacade.getProducts ->
	productsService.getProducts ->
	<interface>productsRepository.getProducts -> (productsDBRepostiry|productsMapRepository).getProducts

	nogmaals shopFacade.getProducts copieren naar controller lijkt overbodig, tenzij deze methode op vele plaatsen
	opgeroepen wordt, maar als dezelfde dev beide de facade en controller maakt.. en de shopFacade is soort van een
	contract om te verzekeren dat getProducts steeds hetzelfde zal teruggeven
	 */
	public ShopFacade getShopFacade(){
		return shopFacade;
	}

	public String formatTotal(double totalCost){
		return formatPrice(totalCost);
	}
	public String formatPrice(double price){
		return String.format("%.2f €", price);
	}
}
