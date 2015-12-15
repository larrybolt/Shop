import domain.ShopFacade;

import javax.swing.*;

public class UiController {
    private ShopFacade shopFacade;
    private ScanAdvancedView scanView;
    private CustomerView customerView;

    public UiController() {
        shopFacade = new ShopFacade();
        scanView = new ScanAdvancedView(this);
        customerView = new CustomerView(this);
    }

    public void showScanView() {
        scanView.showView();
        customerView.showView();
        shopFacade.addObserver(scanView);
        shopFacade.addObserver(customerView);
    }

    public void addProduct(String id, String amount) {
        try {
            shopFacade.addProduct(parseProductId(id), parseAmount(amount));
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.out.println("AddProduct error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void applyKorting(String code) {
        try {
            shopFacade.applyKorting(code);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.out.println("AddProduct error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected int parseProductId(String productId) {
        try {
            return Integer.parseInt(productId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Product id should be a number");
        }
    }

    protected int parseAmount(String amount) {
        try {
            int parsedInt = Integer.parseInt(amount);
            return parsedInt;
        } catch (Exception e) {
            throw new IllegalArgumentException("Amount should be a number");
        }
    }

    public ShopFacade getShopFacade() {
        return shopFacade;
    }

    public String formatTotal(double totalCost) {
        return formatPrice(totalCost);
    }

    public String formatPrice(double price) {
        return String.format("%.2f EUR", price);
    }

    public void pay(double amount) {
        shopFacade.pay(amount);
    }
}