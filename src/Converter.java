import model.Experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Converter {
    Scanner scanner;
    File file;

    public Converter() {
        try {
            File file = new File(getClass().getResource("result.csv").toURI());
            scanner = new Scanner(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Experiment> generateList() {
        List<Experiment> list = new ArrayList<>();
        scanner.nextLine();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            line = line.trim();
            String[] tokens = line.split(",");

            Experiment experiment = new Experiment();
            if (!tokens[0].equals("")) {
                experiment.setParticipantId(tokens[0]);
                Map<String, List<Integer>> trainingPathOrder = new HashMap<>();
                for (int i = 0; i < 4; i++) {
                    List<Integer> pathIds = new ArrayList<>();
                    for (int tokenPosition = 3; tokenPosition < 13; tokenPosition++) {
                        pathIds.add(Integer.parseInt(tokens[tokenPosition]));
                    }
                    trainingPathOrder.put(tokens[2], pathIds);
                    line = scanner.nextLine().trim();
                    tokens = line.split(",");
                }
                experiment.setTrainingPathOrder(trainingPathOrder);

                Map<String, List<Integer>> testingPathOrder = new HashMap<>();
                for (int i = 0; i < 4; i++) {
                    List<Integer> pathIds = new ArrayList<>();
                    for (int tokenPosition = 3; tokenPosition < 13; tokenPosition++) {
                        pathIds.add(Integer.parseInt(tokens[tokenPosition]));
                    }
                    testingPathOrder.put(tokens[2], pathIds);
                    if (i != 3) {
                        tokens = scanner.nextLine().trim().split(",");
                    }
                }
                experiment.setTestingPathOrder(testingPathOrder);

            }
            list.add(experiment);
        }
        System.out.println("Generated List");
        System.out.println(list);
        return list;
    }

    public static void main(String[] args) {
        Converter converter = new Converter();
        converter.generateList();
    }


}
