import model.Book;
import model.Experiment;
import model.PickPath;

import java.io.File;
import java.util.*;

public class Converter {
    Scanner scanner;
    File file;
    public final static int NUMBER_OF_BOOKS_PER_PATH_ID = 10;

    public Converter() {

    }

    private void openFile(String filePath) {
        try {
            File file = new File(getClass().getResource(filePath).toURI());
            scanner = new Scanner(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Experiment> parseExperiments() {
        openFile("experiment.csv");

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

    public List<PickPath> parsePickPaths() {
        openFile("pick-paths.csv");
        scanner.nextLine();

        List<PickPath> list = new ArrayList<>();

        while (scanner.hasNext()) {
//            System.out.println("list so far");
//            System.out.println(list);
            String line = scanner.nextLine();
            line = line.trim();
            String[] tokens = line.split(",");
            PickPath pickPath = new PickPath();

            if (!tokens[0].equals("")) {
                String pathId = tokens[0];
                pickPath.setPathId(pathId);

                String type = tokens[1];
                pickPath.setType(type);

//                System.out.println("PathId, Type: " + pathId + ", " + type);
//                Token[13, 14, 15] -> book details
                List<Book> booksInPath = new ArrayList<>();
                while (!tokens[2].equals("orderedBooksAndLocations")) {
                    line = scanner.nextLine();
                    tokens = line.split(",");
                }
                tokens = line.split(",");

                for (int i = 0; i < NUMBER_OF_BOOKS_PER_PATH_ID; i++) {
                    Book book = new Book();
                    book.setName(tokens[13]);
                    book.setAuthor(tokens[14]);
                    book.setLocationTag(tokens[15]);
                    booksInPath.add(book);

                    line = scanner.nextLine();
                    tokens = line.split(",");
                }
//                System.out.println(booksInPath);
                pickPath.setBooksInPath(booksInPath);
                list.add(pickPath);
            }
        }

        System.out.println("Generated List");
        System.out.println(list);

        return list;
    }




    public static void main(String[] args) {
        Converter converter = new Converter();
//        converter.parseExperiments();
        converter.parsePickPaths();
    }


}
