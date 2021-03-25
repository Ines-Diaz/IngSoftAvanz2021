package blastGUI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import blast.BlastController;

@SuppressWarnings("serial")
public class BlastGUIMain extends JPanel {

	public BlastGUIMain() {

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;

		JLabel lTipoConsulta = new JLabel("Tipo de consulta: ");
		c.insets = new Insets(30, 20, 30, 20);
		c.gridx = 0;
		c.gridy = 0;
		add(lTipoConsulta, c);
		JRadioButton proteinas = new JRadioButton("Proteínas");
		JRadioButton secnucleotidos = new JRadioButton("Secuencias de nucleótidos");
		ButtonGroup group = new ButtonGroup();
		group.add(proteinas);
		group.add(secnucleotidos);
		c.gridx = 1;
		c.gridy = 0;
		add(proteinas, c);
		c.gridx = 2;
		c.gridy = 0;
		add(secnucleotidos, c);
		proteinas.setSelected(true);

		JComboBox<String> comboOfSec = new JComboBox<String>();
		c.insets = new Insets(30, 20, 60, 20);
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 1;
		add(comboOfSec, c); 
		comboOfSec.setEditable(true);
		ArrayList<String> list = new ArrayList<>();
		comboOfSec.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String item = comboOfSec.getSelectedItem().toString();
					if (!list.contains(item)) {
						list.add(item);
						comboOfSec.insertItemAt(item, 0);
					}
				}
			}
		});

		JPanel pTF = new JPanel();
		JLabel lPorcentaje = new JLabel("Porcentaje de similitud (entre 0.0 y 1.0): ");
		JTextField textField = new JTextField("", 10);
		textField.setPreferredSize(new Dimension(60, 30));
		pTF.add(lPorcentaje);
		pTF.add(textField);
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy = 1;
		add(pTF, c);
		textField.setEditable(true);

		JTextArea textArea = new JTextArea(8, 5);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(200, 150));
		c.insets = new Insets(60, 20, 60, 20);
		c.gridx = 1;
		c.gridwidth = 2;
		c.gridy = 2;
		add(scrollPane, c);

		JButton consulta = new JButton("Consultar");
		JPanel pC = new JPanel();
		consulta.setPreferredSize(new Dimension(120, 30));
		pC.add(consulta);
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 2;
		add(pC, c);
		consulta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String dataBaseFile = new String("resources/yeast.aa");
				String dataBaseIndexes = new String("resources/yeast.aa.indexs");
				BlastController bCnt = new BlastController();
				char c = proteinas.isSelected() ? 'p' : 'n';
				String result;
				if (c == 'p') {
					try {
						String cadena = comboOfSec.getSelectedItem().toString();
						String porcentaje = textField.getText();
						result = bCnt.blastQuery(c, dataBaseFile, dataBaseIndexes, Float.parseFloat(porcentaje),
								cadena);
						textArea.setText(result);
					} catch (Exception exc) {
						textArea.setText("Error en la llamada: " + exc.toString());
					}
				} else {
					JDialog dialog = new JDialog();
					dialog.setVisible(true);
					dialog.setMinimumSize(new Dimension(415, 80));
					dialog.add(new JLabel("La consulta de secuencias de nucleótidos aún no está implementada."));
				}
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("BlastGUI");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				BlastGUIMain newContentPane = new BlastGUIMain();
				newContentPane.setOpaque(true); 
				frame.setContentPane(newContentPane);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}
