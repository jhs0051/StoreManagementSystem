import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveOrderView {
    public JFrame view;

    public JButton btnDelete = new JButton("Remove");

    public JTextField txtOrderID = new JTextField(10);

    OrderModel order;
    CustomerModel customer;

    public RemoveOrderView(CustomerModel currentCustomer) {
        this.view = new JFrame();
        customer = currentCustomer;

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.setTitle("Remove Order");
        view.setSize(200, 100);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("OrderID: "));
        line.add(txtOrderID);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(btnDelete);
        view.getContentPane().add(line);

        btnDelete.addActionListener(new DeleteButtonListener());
    }

    public void run() {
        view.setVisible(true);
    }

    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            String id = txtOrderID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "OrderID cannot be null!");
                return;
            }

            order = Client.getInstance().getDataAdapter().loadOrder(Integer.parseInt(id));

            if (order.mCustomerID != customer.mCustomerID) {
                JOptionPane.showMessageDialog(null, "Not your OrderID " +
                        "Please enter a valid Order ID.");
            }

            int res = Client.getInstance().getDataAdapter().deleteOrder(order.mOrderID);
            if (res == SQLiteDataAdapter.ORDER_SAVE_FAIL) {
                JOptionPane.showMessageDialog(null, "Order was not deleted");
            }
            else {
                JOptionPane.showMessageDialog(null, "Order deleted");
            }
        }
    }
}
