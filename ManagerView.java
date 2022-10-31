import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerView {
    public JFrame view;

    public JButton btnManageProduct = new JButton("Manage Products");

    public ManagerView() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Manager View");
        view.setSize(400, 100);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Manager System View");
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnManageProduct);

        view.getContentPane().add(panelButtons);

        btnManageProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ManageProductView ui = new ManageProductView();
                ui.run();
            }
        });
    }
}
