import domain.verkoop.VerkoopObserver;
import domain.verkoop.Subject;
import domain.verkoop.Verkoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScanView extends JFrame implements VerkoopObserver {
    private UiController controller;

    // we moeten dit bijhouden, want het wordt ge-update in de update() methode
    JTextField payField;

    public ScanView(UiController uiController) {
        // OOO setup
        this.controller = uiController;

        // Swing setup
        setTitle("Shop Manager");
        setSize(400, 100);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // swing Panels
        JPanel mainPanel = new JPanel();
        JPanel row1Panel = new JPanel();
        JPanel row2Panel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        row1Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        row2Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        getContentPane().add(mainPanel);
        mainPanel.add(row1Panel);
        mainPanel.add(row2Panel);

        // Panel components
        row1Panel.add(new JLabel("Product"));
        final JTextField productTextField = new JTextField();
        productTextField.setColumns(8);
        row1Panel.add(productTextField);

        row1Panel.add(new JLabel("Quantity"));
        final JTextField quantityTextField = new JTextField("1");
        quantityTextField.setColumns(1);
        row1Panel.add(quantityTextField);

        JButton addbutton = new JButton("Add");
        addbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().addProduct(productTextField.getText(), quantityTextField.getText());
            }
        });
        row1Panel.add(addbutton);

        row2Panel.add(new JLabel("To Pay"));
        payField = new JTextField(getController().formatTotal(0));
        payField.setColumns(8);
        payField.setEnabled(false);
        row2Panel.add(payField);
    }

    public void showView() {
        setVisible(true);
    }

    private UiController getController() {
        return controller;
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof Verkoop) {
            Verkoop verkoop = (Verkoop) subject;
            payField.setText(getController().formatTotal(verkoop.getTotalcost()));
        }
    }
}