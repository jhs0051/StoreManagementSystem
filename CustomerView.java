import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerView {
    public CustomerModel customer = null;

    public JFrame view;

    public JButton btnEditInfo = new JButton("Edit Customer Info");
    public JButton btnMakeOrder = new JButton("Make an Order");
    public JButton btnUpdateOrder = new JButton("Update an Order");
    public JButton btnCancelOrder = new JButton("Cancel an Order");
    public JButton btnViewOrders = new JButton("View Order History");
    public JButton btnSearchProduct = new JButton("Search Products");

    public CustomerView(final UserModel user) {
        customer = Client.getInstance().getDataAdapter().loadCustomerByUser(user.mUserID);

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Customer View");
        view.setSize(500, 150);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Client View");
        view.getContentPane().add(title);


        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnEditInfo);
        panelButtons.add(btnSearchProduct);
        panelButtons.add(btnMakeOrder);
        panelButtons.add(btnUpdateOrder);
        panelButtons.add(btnCancelOrder);
        panelButtons.add(btnViewOrders);

        view.getContentPane().add(panelButtons);

        btnEditInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateClientInfoView ui = new UpdateClientInfoView(user, customer);
                ui.run();
            }
        });

        btnViewOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                OrderHistoryView ui = new OrderHistoryView(customer);
                ui.run();
            }
        });

        btnMakeOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                CreateNewOrderView ui = new CreateNewOrderView(customer);
                ui.run();
            }
        });

        btnUpdateOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                UpdateOrderView ui = new UpdateOrderView();
                ui.run();
            }
        });

        btnSearchProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ProductSearchView ui = new ProductSearchView();
                ui.view.setVisible(true);
            }
        });

        btnCancelOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                RemoveOrderView ui = new RemoveOrderView(customer);
                ui.run();
            }
        });
    }
}
