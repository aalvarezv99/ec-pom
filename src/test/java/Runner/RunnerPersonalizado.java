package Runner;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import CommonFuntions.BeforeSuite;
import cucumber.api.junit.Cucumber;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Personalización del Runner con el cual se puede determinar que busque y
 * modifique los .feature antes de ser ejecutados
 */

public class RunnerPersonalizado extends Runner {

    /*
     * private Class<Cucumber> classValue; private Cucumber cucumber;
     */

    private final Class<Cucumber> classValue;
    private Cucumber Cucumber;

    public RunnerPersonalizado(Class<Cucumber> classValue) throws Exception {
        this.classValue = classValue;
        Cucumber = new Cucumber(classValue);
    }

    @Override
    public Description getDescription() {
        return Cucumber.getDescription();
    }

    private void runAnnotatedMethods(Class<?> annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methods = this.classValue.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    method.invoke(null);
                    break;
                }
            }
        }
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            runAnnotatedMethods(BeforeSuite.class);
            Cucumber = new Cucumber(classValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cucumber.run(notifier);
    }
}
