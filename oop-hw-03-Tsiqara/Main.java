import org.apache.commons.dbcp2.BasicDataSource;

public class Main {
    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/hw_3");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        MetropolisesTableModel model = new MetropolisesTableModel(dataSource);
        MetropolisesView view = new MetropolisesView(dataSource, model);
        MetropolisesController controller = new MetropolisesController(view, model);
    }
}
