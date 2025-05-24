package view;

import javax.swing.*;
import java.awt.*;

import model.tag.TagInfo;
import validator.Validator;

public class App extends JFrame {
    private final JTextField txtCaminho = new JTextField(30);
    private final JTextArea txtResultado = new JTextArea(6, 50);
    private final JTextArea txtTags = new JTextArea(15, 50); 

    public App() {
        setTitle("Validador HTML");
        setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/resources/html_icon.png")));
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        txtTags.setEditable(false);
        txtTags.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtTags.setBackground(new Color(30, 30, 30));
        txtTags.setForeground(new Color(212, 212, 212));
        txtTags.setCaretColor(Color.WHITE);
        txtResultado.setBackground(new Color(158, 158, 160));

        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel topo = new JPanel();
        JButton btnEscolher = new JButton("Procurar");
        btnEscolher.setBackground(new Color(0,122,204));
        btnEscolher.setForeground(Color.WHITE);
        JButton btnValidar = new JButton("Validar");
        btnValidar.setBackground(new Color(255, 128, 64));
        btnValidar.setForeground(Color.WHITE);

        topo.add(new JLabel("Arquivo:"));
        topo.add(txtCaminho);
        topo.add(btnEscolher);
        topo.add(btnValidar);

        btnEscolher.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                txtCaminho.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        btnValidar.addActionListener(e -> {
            String path = txtCaminho.getText();
            if (!path.isBlank()) {
                Validator validator = new Validator();
                boolean ok = validator.validar(path);
                txtResultado.setText(validator.getReport());

                if (ok) {
                    StringBuilder sb = new StringBuilder("Tags encontradas:\n\n");
                    for (TagInfo tag : validator.getContadorTag().getTagsOrdenadas()) {
                        sb.append("<").append(tag.getNome()).append(">: ")
                          .append(tag.getContador()).append("\n");
                    }
                    txtTags.setText(sb.toString());
                } else {
                    txtTags.setText("");
                }
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new JScrollPane(txtResultado), new JScrollPane(txtTags));
        splitPane.setResizeWeight(0.5);

        getContentPane().add(topo, BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
