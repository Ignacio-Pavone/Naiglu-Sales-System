package DatabaseRelated;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Product extends JDialog {

    private JPanel products1;
    private JTabbedPane tabbedPane1;
    private JButton exitButton;
    private JButton button2;
    private JTable tablaProductos;

    public Product(JFrame parent) {
        super(parent);
        crearTabla();
        setMinimumSize(new Dimension(400, 450));
        setContentPane(products1);
        setModal(true);
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    private void crearTabla (){
        tablaProductos.setModel(new DefaultTableModel(
                null,
                new String [] {"ID", "NOMBRE", "STOCK","PRECIO"}

        ));
    };
}
