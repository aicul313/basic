import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lotto extends JFrame {

    private List<Integer> numbers = IntStream.rangeClosed(1, 45).boxed().collect(Collectors.toList());

    public Lotto() {

        setTitle("Lotto - 현지 >_<");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Container c = getContentPane();
        JLabel num1 = generateLabel();
        JLabel num2 = generateLabel();
        JLabel num3 = generateLabel();
        JLabel num4 = generateLabel();
        JLabel num5 = generateLabel();
        JLabel num6 = generateLabel();
        JPanel numberPanel = new JPanel(new GridLayout(3, 2));
        JButton button = new JButton();

        numberPanel.add(num1);
        numberPanel.add(num2);
        numberPanel.add(num3);
        numberPanel.add(num4);
        numberPanel.add(num5);
        numberPanel.add(num6);

        c.add(numberPanel, BorderLayout.CENTER);
        setSize(350, 200);

        button.setText("click");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.shuffle(numbers);
                System.out.println("섞은 결과: " + numbers);
                num1.setText(String.valueOf(numbers.get(0)));
                num2.setText(String.valueOf(numbers.get(1)));
                num3.setText(String.valueOf(numbers.get(2)));
                num4.setText(String.valueOf(numbers.get(3)));
                num5.setText(String.valueOf(numbers.get(4)));
                num6.setText(String.valueOf(numbers.get(5)));
                repaint();
            }
        });

        c.add(button, BorderLayout.NORTH);
        setVisible(true);

    }

    public static void main(String[] args) {

        new Lotto();

    }

    private JLabel generateLabel() {

        return new JLabel("", JLabel.CENTER);

    }
}
