import domain.verkoop.VerkoopObserver;
import domain.verkoop.Subject;
import domain.verkoop.Verkoop;

import javax.swing.*;
import java.awt.*;

public class CustomerView extends JFrame implements VerkoopObserver {
    private UiController controller;

    // we moeten dit bijhouden, want het wordt ge-update in de update() methode
    JTextField payField;

    public CustomerView(UiController uiController) {
        // OOO setup
        this.controller = uiController;

        // Swing setup
        setTitle("");
        setSize(200, 70);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // swing Panels
        JPanel mainPanel = new JPanel();
        JPanel row1Panel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        row1Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        getContentPane().add(mainPanel);
        mainPanel.add(row1Panel);

        // Panel components
        row1Panel.add(new JLabel("To Pay"));
        payField = new JTextField(getController().formatTotal(0));
        payField.setColumns(8);
        payField.setEnabled(false);
        row1Panel.add(payField);
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
    
    public void reset() {
		payField.setText(getController().formatTotal(controller.getTotalCost()));
    }
}