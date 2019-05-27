package neuralNetwork.learning;

import main.Main;
import neuralNetwork.NeuralNetwork;
import words.Collocation;

import java.util.*;

public class GeneticAlgorithm {
    private List<Person> population = new ArrayList<>();
    private int iterationsCount;
    private int totalIterations;
    private int personsCount;
    private int chromosomeLength;
    private double crossingOverProbability;
    private double mutationProbability;
    private double intervalA;
    private double intervalB;
    private List<Collocation> collocations;
    private Person bestPerson;


    public GeneticAlgorithm(List<Collocation> collocations, int iterationsCount, int personsCount, int chromosomeLength, double crossingOverProbability, double mutationProbability, double intervalA, double intervalB) {
        this.collocations = collocations;
        this.iterationsCount = iterationsCount;
        this.personsCount = personsCount;
        this.chromosomeLength = chromosomeLength;
        this.crossingOverProbability = crossingOverProbability;
        this.mutationProbability = mutationProbability;
        this.intervalA = intervalA;
        this.intervalB = intervalB;
    }

    public void mainProcedure() {
        createPopulation();
        int repeatAmount = 0;
        int i = 0;
        double previousValue = Integer.MAX_VALUE;
        totalIterations = 0;
        while (repeatAmount < iterationsCount)
        {
            crossPopulation();
            mutationPopulation();
            selectionPopulation();
            if (previousValue == bestPerson.getNeuralNetwork().getOutput())
            {
                repeatAmount++;
            }
            i++;
            previousValue = bestPerson.getNeuralNetwork().getOutput();
        }
        totalIterations = i;
        Main.getNeuralNetwork().setLayers(bestPerson.getNeuralNetwork().getLayers());
    }
    public void createPopulation() {
        for (int i = 0; i < personsCount; i++) {
            NeuralNetwork neuralNetwork = new NeuralNetwork(Main.getNeuralNetwork().getLayers().size());
            neuralNetwork.setInput(new int[Main.getNeuralNetwork().getInput().length]);
            neuralNetwork.initializeNeurons();
            neuralNetwork.createRandomEdges(chromosomeLength);
            Person person = new Person(neuralNetwork, i);
            population.add(person);
        }
    }
    public void crossPopulation() {
        List<Person> tmpPopulation = new ArrayList<>();
        for (int i = 0; i < population.size() * crossingOverProbability; i++) {
            tmpPopulation.add(population.get(i));
            tmpPopulation.get(i).setPosition(i);
        }
        Map<Integer, Integer> pairs = new LinkedHashMap<>();

        while (tmpPopulation.size() != 0) {
            int first = tmpPopulation.get(0).getPosition();
            int maxSum = 0;
            int second = 0;
            for (int i = 1; i < tmpPopulation.size(); i++) {
                int sum = 0;
                for (int j = 0; j < Main.getNeuralNetwork().getLayers().size(); j++) {
                    for (int k = 0; k < Main.getNeuralNetwork().getLayers().get(j).getNeurons().size(); k++) {
                        for (int s = 0; s < Main.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().size(); s++) {
                            for (int m = 0; m < chromosomeLength; m++)
                            if (tmpPopulation.get(0).getNeuralNetwork().getLayers().get(j).getNeurons()
                                    .get(k).getInputSynapses().get(s).getBinaryWeight().charAt(m) !=
                                    tmpPopulation.get(i).getNeuralNetwork().getLayers().get(j).getNeurons()
                                            .get(k).getInputSynapses().get(s).getBinaryWeight().charAt(m)) sum++;
                        }
                    }
                }
                if (sum > maxSum) {
                    maxSum = sum;
                    second = tmpPopulation.get(i).getPosition();
                }
            }
            tmpPopulation.remove(0);
            for (int i = 0; i < tmpPopulation.size(); i++) {
                if (tmpPopulation.get(i).getPosition() == second) {
                    tmpPopulation.remove(i);
                    break;
                }
            }
            pairs.put(first, second);
        }

        //
        Iterator<Map.Entry<Integer, Integer>> iterator = pairs.entrySet().iterator();
        for (int i = 0; i < pairs.size(); i++) {
            Map.Entry<Integer,Integer> entry = iterator.next();
            int first = entry.getKey();
            int second = entry.getValue();

            Person firstPerson = population.get(first);
            Person secondPerson = population.get(second);

            //
            Person firstPersonNew = new Person(new NeuralNetwork(Main.getNeuralNetwork().getLayers().size()),population.size());
            Person secondPersonNew = new Person(new NeuralNetwork(Main.getNeuralNetwork().getLayers().size()),population.size()+1);

            firstPersonNew.setNeuralNetwork(new NeuralNetwork(Main.getNeuralNetwork().getLayers().size()));
            firstPersonNew.getNeuralNetwork().setInput(new int[Main.getNeuralNetwork().getInput().length]);
            firstPersonNew.getNeuralNetwork().initializeNeurons();
            firstPersonNew.getNeuralNetwork().createRandomEdges(chromosomeLength);

            secondPersonNew.setNeuralNetwork(new NeuralNetwork(Main.getNeuralNetwork().getLayers().size()));
            secondPersonNew.getNeuralNetwork().setInput(new int[Main.getNeuralNetwork().getInput().length]);
            secondPersonNew.getNeuralNetwork().initializeNeurons();
            secondPersonNew.getNeuralNetwork().createRandomEdges(chromosomeLength);

            //

            for (int j = 0; j < Main.getNeuralNetwork().getLayers().size(); j++) {
                for (int k = 0; k < Main.getNeuralNetwork().getLayers().get(j).getNeurons().size(); k++) {
                    for (int s = 0; s < Main.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().size(); s++) {


                        int randomPoint1 = new Random().nextInt(population.size() - 1);
                        int randomPoint2 = new Random().nextInt(population.size() - 1);
                        while (randomPoint1 == randomPoint2) randomPoint2 = new Random().nextInt(population.size() - 1);
                        char[] x1 = new char[chromosomeLength];
                        char[] x2 = new char[chromosomeLength];
                        int index = 0;
                        while (index != chromosomeLength) {
                            if (index <= randomPoint1 || index > randomPoint2) {
                                x1[index] = firstPerson.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().get(s).getBinaryWeight().charAt(index);
                                x2[index] = secondPerson.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().get(s).getBinaryWeight().charAt(index);
                            }
                            else {
                                x2[index] = firstPerson.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().get(s).getBinaryWeight().charAt(index);
                                x1[index] = secondPerson.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().get(s).getBinaryWeight().charAt(index);
                            }
                            index++;
                        }
                        String firstBinaryWeight = new String(x1);
                        String secondBinaryWeight = new String(x2);
                        firstPersonNew.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().get(s).setBinaryWeight(firstBinaryWeight);
                        secondPersonNew.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().get(s).setBinaryWeight(secondBinaryWeight);
                    }

                }
            }

            population.add(firstPersonNew);
            population.add(secondPersonNew);
        }


    }
    public void mutationPopulation() {
        int popCount = population.size();
        for (int i = personsCount; i < (personsCount + (popCount - personsCount) * mutationProbability); i++) {

            Person personNew = new Person(new NeuralNetwork(Main.getNeuralNetwork().getLayers().size()),population.size());

            personNew.setNeuralNetwork(new NeuralNetwork(Main.getNeuralNetwork().getLayers().size()));
            personNew.getNeuralNetwork().setInput(new int[Main.getNeuralNetwork().getInput().length]);
            personNew.getNeuralNetwork().initializeNeurons();
            personNew.getNeuralNetwork().createRandomEdges(chromosomeLength);

            for (int j = 0; j < Main.getNeuralNetwork().getLayers().size(); j++) {
                for (int k = 0; k < Main.getNeuralNetwork().getLayers().get(j).getNeurons().size(); k++) {
                    for (int s = 0; s < Main.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().size(); s++) {

                        int firstRandomPoint = new Random().nextInt(chromosomeLength);
                        int secondRandomPoint = new Random().nextInt(chromosomeLength);
                        while (firstRandomPoint == secondRandomPoint) secondRandomPoint = new Random().nextInt(chromosomeLength);

                        char[] x = personNew.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().get(s).getBinaryWeight().toCharArray();
                        x[firstRandomPoint] = x[firstRandomPoint] == '1' ? '0' : '1';
                        x[secondRandomPoint] = x[secondRandomPoint] == '1' ? '0' : '1';

                        String binaryWeight = new String(x);
                        personNew.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().get(s).setBinaryWeight(binaryWeight);

                    }
                }
            }

            population.add(personNew);

        }

    }
    public void selectionPopulation() {

        for (int i = 0; i < population.size(); i++) {
            population.get(i).getNeuralNetwork().setFirstIteration(false);
            for (int j = 0; j < Main.getNeuralNetwork().getLayers().size(); j++) {
                for (int k = 0; k < Main.getNeuralNetwork().getLayers().get(j).getNeurons().size(); k++) {
                    for (int s = 0; s < Main.getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().size(); s++) {
                        String binaryWeight = population.get(i).getNeuralNetwork().getLayers().get(j).
                                getNeurons().get(k).getInputSynapses().get(s).getBinaryWeight();
                        population.get(i).getNeuralNetwork().getLayers().get(j).getNeurons().get(k).getInputSynapses().get(s).
                                setWeight(decodeBinaryWeight(binaryWeight,chromosomeLength,intervalA,intervalB));
                    }
                }
            }
            double error = 0;
            for (int j = 0; j < collocations.size(); j++) {
                population.get(i).getNeuralNetwork().performCalculation(collocations.get(j));
                double really = collocations.get(j).isCollocationReally() ? 1 : 0;
                double neuralNetworkResult = population.get(i).getNeuralNetwork().getOutput();
                error = error + Math.abs(really-neuralNetworkResult);
            }
            population.get(i).getNeuralNetwork().setError(error);
        }


        while (population.size() != personsCount) {
            double maxError = Double.MIN_VALUE;
            int maxPerson = 0;
            for (int i = 0; i < population.size(); i++) {
                if (population.get(i).getNeuralNetwork().getError() > maxError) {
                    maxError = population.get(i).getNeuralNetwork().getError();
                    maxPerson = i;
                }
            }
            population.remove(maxPerson);
        }
        double minError = Double.MAX_VALUE;
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).getNeuralNetwork().getError() < minError) {
                minError = population.get(i).getNeuralNetwork().getError();
                bestPerson = population.get(i);
            }
        }

        for (int i = 0; i < population.size(); i++) {
            population.get(i).setPosition(i);
            population.get(i).getNeuralNetwork().setError(0);
        }


    }
    public static String randomBinaryLength(int chromosomeLength) {
        StringBuilder binaryLength = new StringBuilder("");
        for (int i = 0; i < chromosomeLength; i++) {
            binaryLength.append(new Random().nextInt(2));
        }
        return binaryLength.toString();
    }
    private double decodeBinaryWeight(String binaryWeight, int chromosomeLength, double intervalA, double intervalB) {
        double decodedBinaryWeight = 0;
        for (int i = 0; i < chromosomeLength; i++) {
            decodedBinaryWeight = decodedBinaryWeight + Math.pow(2, i) * Integer.parseInt(String.valueOf(binaryWeight.charAt(i)));
        }
        return (Math.abs(intervalB - intervalA) / (Math.pow(2, chromosomeLength) - 1)) * decodedBinaryWeight + intervalA;
    }
    public int getTotalIterations() {
        return totalIterations;
    }
}
