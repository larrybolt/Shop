import domain.ShopFacade;

public class UiController {
	private ShopFacade shopFacade;
	private ScanView scanView;
	
	public UiController(){
		shopFacade = new ShopFacade();
		scanView = new ScanView();
	}
	public void showScanView(){
		scanView.showView();
	}
}
