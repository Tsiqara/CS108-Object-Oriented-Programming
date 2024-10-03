public class Metropolis {
    private String name, continent;
    private int population;

    public Metropolis(String name, String continent, int population){
        this.name = name;
        this.continent = continent;
        this.population = population;
    }

    public Object get(int ind){
        if(ind == 0){
            return name;
        }
        if(ind == 1){
            return continent;
        }
        return population;
    }
}
