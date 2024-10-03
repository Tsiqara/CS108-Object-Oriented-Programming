import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MetropolisesController implements ActionListener {
    private MetropolisesView view;
    private MetropolisesTableModel tableModel;

    public MetropolisesController(MetropolisesView view, MetropolisesTableModel tableModel){
        this.view = view;
        this.tableModel = tableModel;

        view.addButton.addActionListener(this);
        view.searchButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(view.addButton)){
            tableModel.add(view.metropolisField.getText(), view.continentField.getText(), view.populationField.getText());
        }

        if(e.getSource().equals(view.searchButton)){
            boolean greater = view.populationPulldown.getSelectedItem().equals(view.populationPulldownStrings[0]);
            boolean exact = view.matchTypePulldown.getSelectedItem().equals(view.matchTypePulldownStrings[0]);
            tableModel.search(view.metropolisField.getText(), view.continentField.getText(), view.populationField.getText(), greater, exact);
        }
    }
}
