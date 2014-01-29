import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class HelloWorld extends AbstractHandler {
   MultilayerPerceptron tree;
   // credit to stack overflow user Pavel Repin
   // java sucks at this particular task.
   static String convertStreamToString(java.io.InputStream is) {
      java.util.Scanner s = new java.util.Scanner(is, "UTF-8")
         .useDelimiter("\\A");
      return s.hasNext() ? s.next() : "";
   }

   void setupAnazlyzer() throws Exception {
      System.out.println("herp");

      DataSource source = new DataSource(
         "C:/Program Files/Weka-3-6/data/cameron.arff");
      Instances data = source.getDataSet();

      // set class attribute
      data.setClassIndex(data.numAttributes() - 1);

      String[] options = new String[0];
       tree = new MultilayerPerceptron();
      tree.setOptions(options); // set the options
      tree.buildClassifier(data); // build classifier

      double d1 = tree.classifyInstance(data.firstInstance());
      double d2 = tree.classifyInstance(data.instance(1));
      double d3 = tree.classifyInstance(data.instance(2));
      double d4 = tree.classifyInstance(data.instance(3));
      double d5 = tree.classifyInstance(data.instance(4));
      double d6 = tree.classifyInstance(data.instance(5));
      double d7 = tree.classifyInstance(data.instance(6));
      double d8 = tree.classifyInstance(data.instance(7));
      double d9 = tree.classifyInstance(data.instance(8));

      System.out.println("d1: <" + d1 + "> d2: <" + d2
         + "> d3: <" + d3 + "> d4: <" + d4 + ">" + " d5: <" + d5
         + "> d6: <" + d6 + "> d7: <" + d7 + "> d8: <" + d8
         + ">" + " d9: <" + d9 + ">");

      Evaluation eval = new Evaluation(data);
      eval.evaluateModel(tree, data);
      System.out.println(eval.toSummaryString(
         "\nResults\n======\n\n\n\n\n", false)
         + "\n\n\n\n\n"
         + eval.toMatrixString());
   }

   public HelloWorld() throws Exception {
      setupAnazlyzer();
   }

   public void handle(String target, Request baseRequest,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
      System.out.println("calling handle");
      InputStream inData = request.getInputStream();
      /*OutputStream outputStream = 
               new FileOutputStream(new File("C:/Program Files/Weka-3-6/data/cameron2.csv"));
      System.out.println("setup streams");

      //String input = convertStreamToString(inData);
      int read;
      byte[] bytes = new byte[1024];
      while ((read = inData.read(bytes)) != -1) {
         outputStream.write(bytes, 0, read);
      }
      System.out.println("wrote files");
      

      
      inData.close();
      outputStream.close();
      System.out.println("closed up shop"); */
      

      response.setContentType("text/html;charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);
      baseRequest.setHandled(true);

      DataSource source;
      try {
         source = new DataSource(inData);//"C:/Program Files/Weka-3-6/data/cameron2.csv");

         System.out.println("opened data source");

      }
      catch (Exception e1) {
         e1.printStackTrace();
         response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         return;
      }
      double d;
      try {
         System.out.println("classifying");
         Instances data = source.getDataSet();
         data.setClassIndex(data.numAttributes() - 1);

         Attribute att = data.firstInstance().attribute(0);
         
         System.out.println("ATTRIBUTE: " + att.toString() + ", index: " + data.firstInstance().classIndex());
         d = tree.classifyInstance(data.firstInstance());
      }
      catch (Exception e) {
         e.printStackTrace();
         response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         return;
      }
      System.out.println("responding");

      
      response.getWriter().println(
         "<h1>Hello World :" + d + ":</h1>");
      
      
   }

   public static void main(String[] args) throws Exception {
      Server server = new Server(8080);
      server.setHandler(new HelloWorld());
      server.start();
      server.join();
   }
}
