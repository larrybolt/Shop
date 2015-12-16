import domain.ShopFacade;
import domain.verkoop.state.InsuffientPaymentException;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/**
 * 
 * @author larrybolt, Annelore
 *
 */

public class UiController {
    private ShopFacade shopFacade;
    private CashierView scanView;
    private CustomerView customerView;

    public UiController() {
        try {
            URL location = UiController.class.getProtectionDomain().getCodeSource().getLocation();
            System.out.println(location.getFile());
            InputStream configFile = new FileInputStream(location.getFile() + "../config.xml");
            shopFacade = new ShopFacade(configFile);
        } catch (FileNotFoundException e) {
            shopFacade = new ShopFacade();
        } catch (Exception e) {
            shopFacade = new ShopFacade();
        }
        scanView = new CashierView(this);
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

    public void updateProductAmount(int index, String newAmount) {
        try {
            int amount = parseAmount(newAmount);
            if (amount == 0) {
                shopFacade.removeEntry(index);
            } else {
                shopFacade.updateEntryAmount(index, amount);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.out.println("updateamount error: " + e.getMessage());
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

    public void pay() {
        try {
            String enteredAmount = JOptionPane.showInputDialog("Amount paid by customer:");
            if (enteredAmount == null) {
                return;
            }
            double amount = Double.parseDouble(enteredAmount);
            shopFacade.pay(amount);
            shopFacade.removeObserver(customerView);
            shopFacade.removeObserver(scanView);
            shopFacade.startNewSale();
            shopFacade.addObserver(scanView);
            shopFacade.addObserver(customerView);
            customerView.reset();
            scanView.reset();
        } catch (InsuffientPaymentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + ". Missing: " + e.getDifference());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount");
        } catch (NullPointerException e) {

        }
    }

    public double getTotalCost() {
        return shopFacade.getTotalCost();
    }

    public void deleteProductEntry(int rowToDelete) {
        shopFacade.deleteProductEntry(rowToDelete);
    }
}