import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class UpdateOrderView {
    public JFrame view;

    public JButton btnUpdate = new JButton("Update Order Info");

    public JTextField txtOrderID = new JTextField(10);
    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtProductID = new JTextField(10);
    public JTextField txtQuantity = new JTextField(10);

    public JLabel labPrice = new JLabel("Product Price: ");
    public JLabel labDate = new JLabel("Order Date: ");

    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductName = new JLabel("Product Name: ");

    public JLabel labCost = new JLabel("Cost: ");
    public JLabel labTax = new JLabel("Tax: ");
    public JLabel labTotalCost = new JLabel("Total Cost: ");

    ProductModel product;
    CustomerModel customer;
    OrderModel order;

    public UpdateOrderView() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.setTitle("Update Order Info");
        view.setSize(400, 225);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("OrderID: "));
        line.add(txtOrderID);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("CustomerID: "));
        line.add(txtCustomerID);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("ProductID: "));
        line.add(txtProductID);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Quantity: "));
        line.add(txtQuantity);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(btnUpdate);
        view.getContentPane().add(line);

        txtProductID.addFocusListener(new ProductIDFocusListener());
        txtCustomerID.addFocusListener(new CustomerIDFocusListener());
        txtQuantity.getDocument().addDocumentListener(new QuantityChangeListener());

        btnUpdate.addActionListener(new UpdateButtonListener());
    }

    public void run() {
        order = new OrderModel();
        order.mDate = Calendar.getInstance().getTime().toString();
        labDate.setText("Order Date: " + order.mDate);
        view.setVisible(true);
    }

    private class ProductIDFocusListener implements FocusListener {
        public void focusGained(FocusEvent focusEvent) {
        }

        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = txtProductID.getText();

            if (s.length() == 0) {
                labProductName.setText("Product Name: [not specified!]");
                return;
            }

            System.out.println("ProductID = " + s);

            try {
                order.mProductID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid ProductID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            product = Client.getInstance().getDataAdapter().loadProduct(order.mProductID);

            if (product == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No product with id = " + order.mProductID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labProductName.setText("Product Name: ");

                return;
            }

            labProductName.setText("Product Name: " + product.mName);
            order.mPrice = product.mPrice;
            labPrice.setText("Product Price: " + product.mPrice);
        }
    }

    private class CustomerIDFocusListener implements FocusListener {
        public void focusGained(FocusEvent focusEvent) {
        }

        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }

            System.out.println("CustomerID = " + s);

            try {
                order.mCustomerID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid CustomerID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            customer = Client.getInstance().getDataAdapter().loadCustomer(order.mCustomerID);

            if (customer == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No customer with id = " + order.mCustomerID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labCustomerName.setText("Customer Name: ");
                return;
            }

            labCustomerName.setText("Customer Name: " + customer.mName);
        }
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
            String s = txtQuantity.getText();

            if (s.length() == 0) {
                return;
            }

            System.out.println("Quantity = " + s);

            try {
                order.mQuantity = Double.parseDouble(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter an invalid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (order.mQuantity <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter an invalid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (order.mQuantity > product.mQuantity) {
                JOptionPane.showMessageDialog(null,
                        "Not enough available products!", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            order.mCost = order.mQuantity * product.mPrice;
            order.mTax = order.mCost * 0.09;
            order.mTotal = order.mCost + order.mTax;

            labCost.setText("Cost: $" + String.format("%8.2f", order.mCost).trim());
            labTax.setText("Tax: $" + String.format("%8.2f", order.mTax).trim());
            labTotalCost.setText("Total: $" + String.format("%8.2f", order.mTotal).trim());
        }
    }

    class UpdateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            String id = txtOrderID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "OrderID cannot be null");
                return;
            }

            try {
                order.mOrderID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "OrderID is invalid");
                return;
            }

            order.mDate = Calendar.getInstance().getTime().toString();

            int res = Client.getInstance().getDataAdapter().updateOrder(order);
            if (res == SQLiteDataAdapter.ORDER_SAVE_FAIL) {
                JOptionPane.showMessageDialog(null, "Order not updated");
            }
            else {
                JOptionPane.showMessageDialog(null, "Order updated");
            }
        }
    }
}
