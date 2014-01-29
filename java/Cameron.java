import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;

public class Cameron {
   public static void main(String[] args) throws Exception {
      System.out.println("herp");
      
      DataSource source = new DataSource("C:/Program Files/Weka-3-6/data/cameron.csv");
      Instances data = source.getDataSet();

      // set class attribute
      data.setClassIndex(data.numAttributes() - 1);
      
      String[] options = new String[0];
      MultilayerPerceptron tree = new MultilayerPerceptron();
      tree.setOptions(options);     // set the options
      tree.buildClassifier(data);   // build classifier
    
      double d1 = tree.classifyInstance(data.firstInstance());
      double d2 = tree.classifyInstance(data.instance(1));
      double d3 = tree.classifyInstance(data.instance(2));
      double d4 = tree.classifyInstance(data.instance(3));
      double d5 = tree.classifyInstance(data.instance(4));
      double d6 = tree.classifyInstance(data.instance(5));
      double d7 = tree.classifyInstance(data.instance(6));
      double d8 = tree.classifyInstance(data.instance(7));
      double d9 = tree.classifyInstance(data.instance(8));

      System.out.println("d1: <" + d1 +"> d2: <" + d2 +"> d3: <" + d3 +"> d4: <" + d4 +">" +
               " d5: <" + d5 +"> d6: <" + d6 +"> d7: <" + d7 +"> d8: <" + d8 +">" +
               " d9: <" + d9 +">");

      
      Evaluation eval = new Evaluation(data);
      eval.evaluateModel(tree, data);
      System.out.println(eval.toSummaryString("\nResults\n======\n\n\n\n\n", false) + "\n\n\n\n\n" + eval.toMatrixString());
      
      
   }
}
