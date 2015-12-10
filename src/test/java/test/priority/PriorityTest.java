package test.priority;

import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

import test.InvokedMethodNameListener;
import test.SimpleBaseTest;

public class PriorityTest extends SimpleBaseTest {

  private void runTest(Class<?> cls, boolean parallel, String... methods) {
    TestNG tng = create(cls);
    InvokedMethodNameListener listener = new InvokedMethodNameListener();
    tng.addListener(listener);
    if (parallel) {
      tng.setParallel(XmlSuite.ParallelMode.METHODS);
    }
    tng.run();
    for (int i=0; i<methods.length; i++) {
      Assert.assertEquals(listener.getInvokedMethodNames().get(i), methods[i]);
    }
  }

  @Test(enabled = false, description = "Make sure priorities work in parallel mode")
  public void priorityInParallel1() {
    runTest(WithPrioritySampleTest.class, true /* parallel */, "first", "second");
  }

  @Test(enabled = false, description = "Make sure priorities work in parallel mode")
  public void priorityInParallel2() {
    runTest(WithPrioritySample2Test.class, true /* parallel */, "second", "first");
  }

  @Test(description = "Make sure priorities work in sequential mode")
  public void priorityInSequential1() {
    runTest(WithPrioritySampleTest.class, false /* sequential */, "first", "second");
  }

  @Test(description = "Make sure priorities work in sequential mode")
  public void priorityInSequential2() {
    runTest(WithPrioritySample2Test.class, false /* sequential */, "second", "first");
  }
}
