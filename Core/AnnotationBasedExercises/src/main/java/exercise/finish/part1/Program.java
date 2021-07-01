package exercise.finish.part1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.swing.*;

public class Program {
    private static AbstractApplicationContext buildContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(Program.class.getPackage().getName());
        context.refresh();
        return context;
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = buildContext();
        JFrame gui = context.getBean("gui", JFrame.class);
        gui.setVisible(true);
    }
}
