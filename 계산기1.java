import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame{

    private JTextField inputSpace;
    private String num = "";
    private String prev_operation = "";
    private ArrayList<String> equation = new ArrayList<String>();

    public Calculator(){
        setLayout(null);

        inputSpace = new JTextField();
        inputSpace.setEditable(false);//편집 가능 여부 불가능(버튼으로만 활용)
        inputSpace.setBackground(Color.WHITE);//배경색
        inputSpace.setHorizontalAlignment(JTextField.RIGHT);//정렬위치
        inputSpace.setFont(new Font("Arial", Font.BOLD, 50));//글꼴
        inputSpace.setBounds(8, 10, 270, 70);//x:8, y:10 위치에 270x70의 크기

        JPanel buttonPanel = new JPanel(); //버튼을 담을 패널
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10)); //차례대로 가로 칸수, 세로 칸수, 좌우 간격, 상하 간격
        buttonPanel.setBounds(8, 90, 270, 235);

        String button_names[] = { "C", "/", "*", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0" };
        JButton buttons[] = new JButton[button_names.length];

        for(int i=0; i<button_names.length; i++){
            buttons[i] = new JButton(button_names[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            if(button_names[i] == "C")
                buttons[i].setBackground(Color.RED);
            else if((i >= 4 && i<= 6) || (i >= 8 && i<= 10) || (i >= 12 && i <= 14))
                buttons[i].setBackground(Color.BLACK);
            else buttons[i].setBackground(Color.WHITE);
            buttons[i].setForeground(Color.GRAY);
            buttons[i].setBorderPainted(false);
            buttons[i].addActionListener(new PadActionListener());
            buttonPanel.add(buttons[i]);
        }

        add(inputSpace);
        add(buttonPanel);

        setTitle("계산기"); //창의 제목
        setVisible(true); //보이기 여부
        setSize(300, 370); //사이즈
        setLocationRelativeTo(null); //null 값으로 띄울 때 화면 가운데에 띄움
        setResizable(false); //사이즈 조절 불가능
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//창 닫을 때 실행 중인 프로그램도 같이 종료
    }

    class PadActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
                String operation = e.getActionCommand();

                if(operation.equals("C")){
                    inputSpace.setText("");
                }else if(operation.contentEquals("=")){
                    String result = Double.toString(calculate(inputSpace.getText()));
                    inputSpace.setText("" + result);
                    num = "";
                }else if(operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/")){
                    if(inputSpace.getText().equals("") && operation.equals("-")){
                        inputSpace.setText(inputSpace.getText()+e.getActionCommand());
                    }else if(!inputSpace.getText().equals("") && !prev_operation.equals("+") && !prev_operation.equals("-") && !prev_operation.equals("*") && !prev_operation.equals("/")){
                        inputSpace.setText(inputSpace.getText()+e.getActionCommand());
                    }
                }else{
                    inputSpace.setText(inputSpace.getText()+e.getActionCommand());
                }
                prev_operation = e.getActionCommand();
        }
    }

    private void fullTextParsing(String inputText){
        equation.clear();

        for(int i=0; i<inputText.length(); i++){
            char ch = inputText.charAt(i);

            if(ch == '-' || ch == '+' || ch == '*' || ch == '/'){
                equation.add(num);
                num = "";
                equation.add(ch + "");
            } else {
                num = num + ch;
            }
        }
        equation.add(num);
        equation.remove("");
    }

    public double calculate(String inputText){
        fullTextParsing(inputText);

        double prev = 0;
        double current = 0;
        String mode = "";

        for(int i=0; i<equation.size(); i++){
            String s = equation.get(i);

            if(s.equals("+")){
                mode = "add";
            }else if(s.equals("-")){
                mode = "sub";
            }else if(s.equals("*")){
                mode = "mul";
            }else if(s.equals("/")){
                mode = "div";
            }else{
                if((mode.equals("mul") || mode.equals("div") && !s.equals("+") && !s.equals("-") && !s.equals("*") && !s.equals("/"))){
                    Double one = Double.parseDouble(equation.get(i - 2));
                    Double two = Double.parseDouble(equation.get(i));
                    Double result = 0.0;

                    if(mode.equals("mul")) {
                        result = one * two;
                    }else if(mode.equals("div")){
                        result = one / two;
                    }

                    equation.add(i+1, Double.toString(result));

                    for(int j=0; j<3; j++){
                        equation.remove(i - 2);
                    }

                    i -= 2; //결과값이 생긴 인덱스로 이동
                }
            }
        } // 곱셈 나눗셈을 먼저 계산한다

        for(String s : equation) {
            if(s.equals("+")){
                mode = "add";
            }else if(s.equals("-")){
                mode = "sub";
            }else{
                current = Double.parseDouble(s);
                if (mode.equals("add")){
                    prev += current;
                }else if(mode.equals("sub")){
                    prev -= current;
                }else{
                    prev = current;
                }
            }

            prev = Math.round(prev * 100000) / 100000.0;
        }

        return prev;
    }

    public static void main(String[] args) throws Exception {
        new Calculator();
    }
}
