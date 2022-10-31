import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateNewOrderView {
    public JFrame view;

    public JButton btnSave = new JButton("Add Order");

    public JTextField txtProductName = new JTextField(10);
    public JTextField txtQuantity = new JTextField(10);

    public JLabel labPrice = new JLabel("Price: ");
    public JLabel labDate = new JLabel("Order Date: ");

    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labCost = new JLabel("Cost:" );
    public JLabel labTax = new JLabel("Tax: ");
    public JLabel labTotal = new JLabel("Total Cost: ");

    ProductModel product;
    CustomerModel customer;
    OrderModel order;

    public CreateNewOrderView(CustomerModel currentCustomer) {
        this.view = new JFrame();
        customer = currentCustomer;

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Create new orders");
        view.setSize(400, 150);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("Product Name: "));
        line.add(txtProductName);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Quantity: "));
        line.add(txtQuantity);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(btnSave);
        view.getContentPane().add(line);

        txtQuantity.getDocument().addDocumentListener(new QuantityChangeListener());

        btnSave.addActionListener(new SaveButtonListener());
    }

    public void run() {
        order = new OrderModel();
        order.mDate = Calendar.getInstance().getTime().toString();
        view.setVisible(true);
    }

    private class QuantityChangeListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            process();
        }

        public void removeUpdate(DocumentEvent e) {
            process();
        }

        public void insertUpdate(DocumentEvent e) {
            process();
        }

        private void process() {
            ArrayList<ProductModel> productList = Client.getInstance().getDataAdapter().searchProductByName(txtProductName.getText());
            ProductModel loadedProduct = productList.get(0);

            labPrice.setText("Product's Price: " + loadedProduct.mPrice);

            String s = txtQuantity.getText();

            if (s.length() == 0) {
                return;
            }

            System.out.println("Quantity = " + s);

            try {
                order.mQuantity = Double.parseDouble(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter a valid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (order.mQuantity <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter a valid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (order.mQuantity > loadedProduct.mQuantity) {
                JOptionPane.showMessageDialog(null,
                        "Not enough available products!", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            order.mCost = order.mQuantity * loadedProduct.mPrice;
            order.mTax = order.mCost * 0.09;
            order.mTotal = order.mCost + order.mTax;

            labCost.setText("Cost: $" + String.format("%8.2f", order.mCost).trim());
            labTax.setText("Tax: $" + String.format("%8.2f", order.mTax).trim());
            labTotal.setText("Total: $" + String.format("%8.2f", order.mTotal).trim());
        }
    }

    class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            ArrayList<Integer> orderCountList = Client.getInstance().getDataAdapter().getOrderRowCount();
            int realOrderCount = orderCountList.get(0);
            realOrderCount = realOrderCount + 1;
            order.mOrderID = realOrderCount;

            ArrayList<ProductModel> productList = Client.getInstance().getDataAdapter().searchProductByName(txtProductName.getText());
            ProductModel product = productList.get(0);
            order.mProductID = product.mProductID;

            order.mCustomerID = customer.mCustomerID;

            order.mDate = Calendar.getInstance().getTime().toString();

            int res = Client.getInstance().getDataAdapter().saveOrder(order);

            if (res == SQLiteDataAdapter.ORDER_SAVE_FAIL) {
                JOptionPane.showMessageDialog(null, "Order not saved; please try again.");
            }
            else {
                JOptionPane.showMessageDialog(null, "Order saved");
            }
        }
    }
}
