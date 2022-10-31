import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagerView {
    public JFrame view;

    public JButton btnManageUser = new JButton("Manage Users");

    public StoreManagerView() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Admin");

        title.setFont(title.getFont().deriveFont(24.0f));

        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());

        panelButtons.add(btnManageUser);

        view.getContentPane().add(panelButtons);

        btnManageUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ManageUserView ui = new ManageUserView();
                ui.run();
            }
        });
    }
}
