import domain.product.Product;
import domain.verkoop.VerkoopObserver;
import domain.verkoop.Subject;
import domain.verkoop.Verkoop;
import domain.verkoop.VerkoopEntry;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class CashierView extends JFrame implements VerkoopObserver {
    private UiController controller;

    // we moeten dit bijhouden, want het wordt ge-update in de update() methode
    JTextField payField;
    JTextField productTextField;
    JTextField quantityTextField;
    JTextField kortingField;
    JTable entriesTable;

    ArrayList<VerkoopEntry> entries;
    String columnNames[] = {"Description", "Quantity", "Unit price", "Total Price", ""};
    String rows[][];
    EntriesTableModel entriesModel;

    public CashierView(UiController uiController) {
        // OOO setup
        this.controller = uiController;

        // Swing setup
        setTitle("Shop Manager beter");
        setSize(500, 600);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // swing Panels
        JPanel mainPanel = new JPanel();
        JPanel row1Panel = new JPanel();
        JPanel row2Panel = new JPanel();
        JPanel row3Panel = new JPanel();
        JPanel row4Panel = new JPanel();
        JPanel row5Panel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        row1Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        row2Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        row3Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        row4Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        row5Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        getContentPane().add(mainPanel);
        mainPanel.add(row1Panel);
        mainPanel.add(row2Panel);
        mainPanel.add(row3Panel);
        mainPanel.add(row4Panel);
        mainPanel.add(row5Panel);

        // Panel components
        row1Panel.add(new JLabel("Product:"));
        productTextField = new JTextField();
        productTextField.setColumns(8);
        row1Panel.add(productTextField);

        row1Panel.add(new JLabel("Quantity:"));
        quantityTextField = new JTextField("1");
        quantityTextField.setColumns(3);
        row1Panel.add(quantityTextField);

        JButton addbutton = new JButton("Add");
        addbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().addProduct(productTextField.getText(), quantityTextField.getText());
                productTextField.setText("");
            }
        });
        row1Panel.add(addbutton);

        // Set table column names and data manually
        entries = getController().getShopFacade().getVerkoopProducts();
        ArrayList<Product> products = getController().getShopFacade().getProducts();
        //entriesTable = new JTable(rows, columnNames);
        entriesModel = new EntriesTableModel();
        entriesTable = new JTable(entriesModel);
        ButtonColumn buttonColumn = new ButtonColumn(entriesTable, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowToDelete = Integer.valueOf(e.getActionCommand());
				getController().deleteProductEntry(rowToDelete);
			}
		}, 4);
        //entriesTable.setAutoResizeMode(JTable.);

        JScrollPane entriesTableScrollable = new JScrollPane(entriesTable);
        row2Panel.add(entriesTableScrollable);

        row3Panel.add(new JLabel("To Pay:"));
        payField = new JTextField(getController().formatTotal(0));
        payField.setColumns(8);
        payField.setEnabled(false);
        row3Panel.add(payField);

        row4Panel.add(new JLabel("Korting code:"));
        kortingField = new JTextField();
        kortingField.setColumns(8);
        row4Panel.add(kortingField);
        JButton applyKortingButton = new JButton("Apply");
        applyKortingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().applyKorting(kortingField.getText());
            }
        });
        row4Panel.add(applyKortingButton);

        JButton payButton = new JButton("pay");
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getController().pay();
            }
        });
        row5Panel.add(payButton);
    }

    public class EntriesTableModel extends AbstractTableModel implements TableModel {
    	
    	static final int AMOUNTCOLUMN = 1;

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == AMOUNTCOLUMN || columnIndex == getColumnCount()-1;
        }

        @Override
        public int getRowCount() {
            return entries.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return rows[rowIndex][columnIndex];
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        	if (columnIndex == AMOUNTCOLUMN) {
				getController().updateProductAmount(rowIndex, aValue + "");
        	}
        }
    }

    private void refreshEntries() {
        entries = getController().getShopFacade().getVerkoopProducts();
        rows = new String[entries.size()][columnNames.length];
        for (int i = 0; i < rows.length; i++) {
            int j = 0;
            rows[i][j++] = entries.get(i).getProduct().getName();
            rows[i][j++] = "" + entries.get(i).getCount();
            rows[i][j++] = controller.formatPrice(entries.get(i).getProduct().getPrice());
            rows[i][j++] = controller.formatPrice(entries.get(i).getEntryPrice());
            rows[i][j++] = "delete";
        }
        entriesModel.fireTableDataChanged();
    }

    public void showView() {
        setVisible(true);
    }

    public void close() {
        setVisible(false);
    }

    private UiController getController() {
        return controller;
    }
    
    @Override
    public void update(Subject subject) {
		Verkoop verkoop = (Verkoop) subject;
		payField.setText(getController().formatTotal(verkoop.getTotalcost()));
		refreshEntries();
		entriesModel.fireTableDataChanged();
    }

	public void reset() {
		this.kortingField.setText("");
    	refreshEntries();
		payField.setText(getController().formatTotal(getController().getTotalCost()));
	}
}