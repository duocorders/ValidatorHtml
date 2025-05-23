package view;

import javax.swing.*;

import model.tag.TagInfo;
import validator.Validator;
import java.awt.*;

public class App extends JFrame {
    private final JTextField txtCaminho = new JTextField(30);
    private final JTextArea txtResultado = new JTextArea(6, 50);
    private final JTextArea txtTags = new JTextArea(10, 50);

    public App() {
        setTitle("Validador HTML");
        setSize(650, 550);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel topo = new JPanel();
        JButton btnEscolher = new JButton("Procurar");
        JButton btnValidar = new JButton("Validar");

        topo.add(new JLabel("Arquivo:"));
        topo.add(txtCaminho);
        topo.add(btnEscolher);
        topo.add(btnValidar);

        txtResultado.setEditable(false);
        txtTags.setEditable(false);

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
                    StringBuilder sb = new StringBuilder("Tags encontradas:\n");
                    for (TagInfo tag : validator.getContadorTag().getTagsOrdenadas()) {
                        sb.append(tag.getNome()).append(": ").append(tag.getContador()).append("\n");
                    }
                    txtTags.setText(sb.toString());
                } else {
                    txtTags.setText("");
                }
            }
        });

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(txtResultado), BorderLayout.CENTER);
        add(new JScrollPane(txtTags), BorderLayout.SOUTH);
        setVisible(true);
    }
}