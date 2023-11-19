import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {
    private static final int INITIAL_X = 1000;
    private static final int INITIAL_Y = 200;
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 550;

    private static final int FRAME_PADDING = 20;

    private JTextField textFieldEquation;

    private JTextField textFieldAnswer;


    Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(INITIAL_X, INITIAL_Y, FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(FRAME_PADDING, FRAME_PADDING, FRAME_PADDING, FRAME_PADDING));
        setContentPane(contentPanel);

        add(new DisplayPanel());
        add(new ButtonPanel());

    }


    private class DisplayPanel extends JPanel {
        private static final int DISPLAY_HEIGHT = 90;
        private static final int DISPLAY_WIDTH = FRAME_WIDTH - 40;

        DisplayPanel() {
            setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            textFieldEquation = createTextField();
            textFieldAnswer = createTextField();

            add(textFieldEquation);
            add(textFieldAnswer);
        }

        private JTextField createTextField() {
            JTextField textField = new JTextField();
            textField.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
//            textField.setEditable(false);
            return textField;
        }
    }

    private class ButtonPanel extends JPanel {
        private static final int BUTTON_PANEL_WIDTH = FRAME_WIDTH - 40;
        private static final int BUTTON_PANEL_HEIGHT = FRAME_HEIGHT - 140;

        ButtonPanel() {
            setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH, BUTTON_PANEL_HEIGHT));
            setLayout(new GridLayout(5, 4, 15, 12));
            createButtons();
        }

        private void createButtons() {
            String[] buttonLabels = {
                    "(", ")", "DEL", "AC",
                    "7", "8", "9", "X",
                    "4", "5", "6", "/",
                    "1", "2", "3", "+",
                    "0", ".", "=", "-"
            };

            for (String label : buttonLabels) {
                Button btn = new Button(label);
                add(btn);
            }
        }
    }

    private class Button extends JButton {
        Button(String label) {
            setText(label);
            assignColor(label);
            addActionListener(e -> handleButtonClick(this));
        }

        private void assignColor(String label) {
            if ("DEL".equalsIgnoreCase(label)) {
                setBackground(new Color(245, 0, 0));
            } else if ("1234567890.=".contains(label)) {
                setBackground(new Color(220, 23, 230));
            } else if ("X/+-()".contains((label))) {
                setBackground(new Color(120, 20, 100));
            } else {
                setBackground(new Color(0, 0, 0));
            }
        }

        private void handleButtonClick(Button btn) {
            String label = btn.getText();
            if ("0123456789().".contains(label)) {
                writeNumber(label);
            } else if ("X-+/".contains(label)) {
                writeOperator(label);
            } else if ("AC".equalsIgnoreCase(label)) {
                textFieldEquation.setText("");
                textFieldEquation.setText("");
            } else if ("DEL".equalsIgnoreCase(label)) {
                deleteText();
            } else {
                calculate();
            }
        }

        private void writeNumber(String number) {
            String currentText = textFieldEquation.getText();
            String newText = currentText + number;
            textFieldEquation.setText(newText);
        }

        private void writeOperator(String operator) {
            String currentText = textFieldEquation.getText().trim();
            String newText = currentText + " " + operator + " ";
            textFieldEquation.setText(newText);
        }

        private void deleteText() {
            String currentText = textFieldEquation.getText().trim();
            int length = currentText.length();
            String newText = "";
            if (length > 0)
                newText = currentText.substring(0, length - 1);
            textFieldEquation.setText(newText);
        }

        private void calculate() {
            String equation = textFieldEquation.getText();
            String ans = "";
            textFieldAnswer.setText(ans);

        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
    }
}