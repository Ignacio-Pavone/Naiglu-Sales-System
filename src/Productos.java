import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class Productos extends JDialog {

    private JPanel productos1;
    private JTabbedPane tabbedPane1;
    private JButton SALIRButton;
    private JButton button2;
    private JTable tablaProductos;

    public Productos(JFrame parent) {
        super(parent);
        crearTabla();
        setMinimumSize(new Dimension(400, 450));
        setContentPane(productos1);
        setModal(true);
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        SALIRButton.addActionListener(new ActionListener() {
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
