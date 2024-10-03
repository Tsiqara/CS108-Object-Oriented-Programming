import org.apache.commons.dbcp2.BasicDataSource;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class MetropolisesView extends JFrame {
    JTextField metropolisField, continentField, populationField;
    JTable table;
    MetropolisesTableModel tableModel;
    JButton addButton, searchButton;
    JComboBox populationPulldown, matchTypePulldown;

    String[] populationPulldownStrings = {"Population larger than", "Population smaller than or equal to"};
    String[] matchTypePulldownStrings = {"Exact Match", "Partial match"};

    private final int textFieldSize = 10;

    public MetropolisesView(BasicDataSource dataSource,MetropolisesTableModel model){
        super("Metropolis Viewer");
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.add(new JLabel("Metropolis:"));
        metropolisField = new JTextField(textFieldSize);
        top.add(metropolisField);
        top.add(new JLabel("Continent:"));
        continentField = new JTextField(textFieldSize);
        top.add(continentField);
        top.add(new JLabel("Population:"));
        populationField = new JTextField(textFieldSize);
        top.add(populationField);
        add(top, BorderLayout.NORTH);

        tableModel = model;
        table = new JTable();
        table.setModel(tableModel);
        JScrollPane scrollTable = new JScrollPane(table);
        add(scrollTable, BorderLayout.CENTER);

        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        addButton = new JButton("Add");
        searchButton = new JButton("Search");
        east.add(addButton);
        east.add(searchButton);
        Box box = Box.createVerticalBox();
        box.setBorder(new TitledBorder("Search Options"));
        populationPulldown = new JComboBox(populationPulldownStrings);
        populationPulldown.setMaximumSize(new Dimension(populationPulldown.getMaximumSize().width,populationPulldown.getMinimumSize().height));
        matchTypePulldown = new JComboBox(matchTypePulldownStrings);
        matchTypePulldown.setMaximumSize(new Dimension(populationPulldown.getMaximumSize().width,populationPulldown.getMinimumSize().height));
        box.add(populationPulldown);
        box.add(matchTypePulldown);
        box.add(Box.createVerticalGlue());
        box.setMaximumSize(new Dimension(box.getMaximumSize().width, box.getMinimumSize().height));
        east.add(box);
        east.add(Box.createVerticalGlue());
        add(east, BorderLayout.EAST);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
