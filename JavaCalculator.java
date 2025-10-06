/**
 * Advanced Java Calculator with GUI and Scientific Operations
 * Created by: KartikeyaNainkhwal
 * For: Hacktoberfest 2025 - Issue #281
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.regex.*;

public class JavaCalculator extends JFrame implements ActionListener {
    
    private JTextField display;
    private JTextField operationDisplay;
    private JTextArea historyArea;
    private JScrollPane historyScrollPane;
    
    private double num1 = 0;
    private double num2 = 0;
    private double result = 0;
    private String operator = "";
    private String currentInput = "";
    private boolean isNewCalculation = true;
    
    private double memory = 0;
    private List<String> calculationHistory = new ArrayList<>();
    
    private String[] basicButtons = {
        "C", "CE", "⌫", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "−",
        "1", "2", "3", "+",
        "±", "0", ".", "="
    };
    
    private String[] scientificButtons = {
        "sin", "cos", "tan", "log",
        "ln", "√", "x²", "xʸ",
        "π", "e", "(", ")",
        "MC", "MR", "M+", "M-"
    };
    
    private final Color BACKGROUND_COLOR = new Color(32, 32, 32);
    private final Color BUTTON_COLOR = new Color(64, 64, 64);
    private final Color OPERATOR_COLOR = new Color(255, 149, 0);
    private final Color NUMBER_COLOR = new Color(96, 96, 96);
    private final Color DISPLAY_COLOR = new Color(16, 16, 16);
    private final Color TEXT_COLOR = Color.WHITE;
    
    public JavaCalculator() {
        initializeGUI();
        setupKeyboardListener();
    }
    
    private void initializeGUI() {
        setTitle("Advanced Java Calculator - by KartikeyaNainkhwal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        createDisplayPanel();
        createButtonPanel();
        createHistoryPanel();
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        
        try {
            setIconImage(createCalculatorIcon());
        } catch (Exception e) {
            System.out.println("Could not load icon");
        }
    }
    
    private void createDisplayPanel() {
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBackground(BACKGROUND_COLOR);
        displayPanel.setBorder(new EmptyBorder(15, 15, 10, 15));
        
        operationDisplay = new JTextField("");
        operationDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        operationDisplay.setHorizontalAlignment(JTextField.RIGHT);
        operationDisplay.setEditable(false);
        operationDisplay.setBackground(DISPLAY_COLOR);
        operationDisplay.setForeground(Color.LIGHT_GRAY);
        operationDisplay.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        operationDisplay.setPreferredSize(new Dimension(400, 30));
        
        display = new JTextField("0");
        display.setFont(new Font("Segoe UI", Font.BOLD, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(DISPLAY_COLOR);
        display.setForeground(TEXT_COLOR);
        display.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        display.setPreferredSize(new Dimension(400, 80));
        
        displayPanel.add(operationDisplay, BorderLayout.NORTH);
        displayPanel.add(display, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.NORTH);
    }
    
    private void createButtonPanel() {
        JPanel mainButtonPanel = new JPanel(new BorderLayout());
        mainButtonPanel.setBackground(BACKGROUND_COLOR);
        mainButtonPanel.setBorder(new EmptyBorder(0, 15, 15, 15));
        
        JPanel basicPanel = createBasicButtonPanel();
        JPanel scientificPanel = createScientificButtonPanel();
        
        mainButtonPanel.add(scientificPanel, BorderLayout.WEST);
        mainButtonPanel.add(basicPanel, BorderLayout.CENTER);
        
        add(mainButtonPanel, BorderLayout.CENTER);
    }
    
    private JPanel createBasicButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 5, 5));
        panel.setBackground(BACKGROUND_COLOR);
        
        for (String buttonText : basicButtons) {
            JButton button = createStyledButton(buttonText);
            panel.add(button);
        }
        
        return panel;
    }
    
    private JPanel createScientificButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(0, 0, 0, 10));
        
        for (String buttonText : scientificButtons) {
            JButton button = createStyledButton(buttonText);
            button.setFont(new Font("Segoe UI", Font.BOLD, 12));
            panel.add(button);
        }
        
        return panel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(TEXT_COLOR);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        button.setFocusPainted(false);
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(70, 60));
        
        if (isOperator(text)) {
            button.setBackground(OPERATOR_COLOR);
        } else if (isNumber(text) || text.equals(".")) {
            button.setBackground(NUMBER_COLOR);
        } else {
            button.setBackground(BUTTON_COLOR);
        }
        
        addHoverEffect(button);
        
        return button;
    }
    
    private void addHoverEffect(JButton button) {
        Color originalColor = button.getBackground();
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
    }
    
    private void createHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(BACKGROUND_COLOR);
        historyPanel.setBorder(new EmptyBorder(0, 15, 15, 15));
        
        JLabel historyLabel = new JLabel("History");
        historyLabel.setForeground(TEXT_COLOR);
        historyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        historyArea = new JTextArea(8, 20);
        historyArea.setEditable(false);
        historyArea.setBackground(DISPLAY_COLOR);
        historyArea.setForeground(TEXT_COLOR);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        
        historyScrollPane = new JScrollPane(historyArea);
        historyScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JButton clearHistoryBtn = new JButton("Clear History");
        clearHistoryBtn.setBackground(BUTTON_COLOR);
        clearHistoryBtn.setForeground(TEXT_COLOR);
        clearHistoryBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        clearHistoryBtn.setFocusPainted(false);
        clearHistoryBtn.addActionListener(e -> clearHistory());
        
        historyPanel.add(historyLabel, BorderLayout.NORTH);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);
        historyPanel.add(clearHistoryBtn, BorderLayout.SOUTH);
        
        add(historyPanel, BorderLayout.EAST);
    }
    
    private void setupKeyboardListener() {
        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyInput(e);
            }
        });
        
        // Make sure the display can receive focus
        display.setFocusable(true);
        display.requestFocus();
    }
    
    private void handleKeyInput(KeyEvent e) {
        char keyChar = e.getKeyChar();
        int keyCode = e.getKeyCode();
        
        if (Character.isDigit(keyChar) || keyChar == '.') {
            handleNumberInput(String.valueOf(keyChar));
        } else if (keyChar == '+' || keyChar == '-' || keyChar == '*' || keyChar == '/') {
            String operator = keyChar == '*' ? "×" : keyChar == '/' ? "÷" : keyChar == '-' ? "−" : "+";
            handleOperator(operator);
        } else if (keyChar == '=' || keyCode == KeyEvent.VK_ENTER) {
            calculateResult();
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            clearAll();
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            backspace();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        try {
            if (isNumber(command) || command.equals(".")) {
                handleNumberInput(command);
            } else if (isBasicOperator(command)) {
                handleOperator(command);
            } else if (command.equals("=")) {
                calculateResult();
            } else if (command.equals("C")) {
                clearAll();
            } else if (command.equals("CE")) {
                clearEntry();
            } else if (command.equals("⌫")) {
                backspace();
            } else if (command.equals("±")) {
                toggleSign();
            } else if (isScientificFunction(command)) {
                handleScientificFunction(command);
            } else if (isMemoryFunction(command)) {
                handleMemoryFunction(command);
            }
        } catch (Exception ex) {
            display.setText("Error");
            addToHistory("Error: " + ex.getMessage());
        }
    }
    
    private void handleNumberInput(String number) {
        if (isNewCalculation || display.getText().equals("0")) {
            display.setText(number);
            isNewCalculation = false;
        } else {
            String currentText = display.getText();
            if (number.equals(".") && currentText.contains(".")) {
                return; // Prevent multiple decimal points
            }
            display.setText(currentText + number);
        }
        currentInput = display.getText();
    }
    
    private void handleOperator(String op) {
        if (!operator.isEmpty() && !isNewCalculation) {
            calculateResult();
        }
        
        num1 = Double.parseDouble(display.getText());
        operator = op;
        isNewCalculation = true;
        
        operationDisplay.setText(formatResult(num1) + " " + op + " ");
    }
    
    private void calculateResult() {
        if (operator.isEmpty()) return;
        
        num2 = Double.parseDouble(display.getText());
        String expression = formatResult(num1) + " " + operator + " " + formatResult(num2);
        
        operationDisplay.setText(expression + " =");
        
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "−":
                result = num1 - num2;
                break;
            case "×":
                result = num1 * num2;
                break;
            case "÷":
                if (num2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                result = num1 / num2;
                break;
        }
        
        display.setText(formatResult(result));
        addToHistory(expression + " = " + formatResult(result));
        
        num1 = result;
        operator = "";
        isNewCalculation = true;
        
        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> operationDisplay.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
    
    private void handleScientificFunction(String function) {
        double value = Double.parseDouble(display.getText());
        String expression = function + "(" + formatResult(value) + ")";
        
        operationDisplay.setText("Calculating: " + expression);
        
        switch (function) {
            case "sin":
                result = Math.sin(Math.toRadians(value));
                break;
            case "cos":
                result = Math.cos(Math.toRadians(value));
                break;
            case "tan":
                result = Math.tan(Math.toRadians(value));
                break;
            case "log":
                if (value <= 0) throw new ArithmeticException("Invalid input for log");
                result = Math.log10(value);
                break;
            case "ln":
                if (value <= 0) throw new ArithmeticException("Invalid input for ln");
                result = Math.log(value);
                break;
            case "√":
                if (value < 0) throw new ArithmeticException("Invalid input for square root");
                result = Math.sqrt(value);
                expression = "√(" + formatResult(value) + ")";
                break;
            case "x²":
                result = value * value;
                expression = "(" + formatResult(value) + ")²";
                break;
            case "π":
                result = Math.PI;
                expression = "π";
                operationDisplay.setText("Using constant: π");
                break;
            case "e":
                result = Math.E;
                expression = "e";
                operationDisplay.setText("Using constant: e");
                break;
        }
        
        operationDisplay.setText(expression + " = " + formatResult(result));
        display.setText(formatResult(result));
        addToHistory(expression + " = " + formatResult(result));
        isNewCalculation = true;
        
        javax.swing.Timer timer = new javax.swing.Timer(3000, e -> operationDisplay.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
    
    private void handleMemoryFunction(String function) {
        double value = Double.parseDouble(display.getText());
        
        switch (function) {
            case "MC":
                memory = 0;
                addToHistory("Memory cleared");
                break;
            case "MR":
                display.setText(formatResult(memory));
                addToHistory("Memory recalled: " + formatResult(memory));
                isNewCalculation = true;
                break;
            case "M+":
                memory += value;
                addToHistory("Memory + " + formatResult(value) + " = " + formatResult(memory));
                break;
            case "M-":
                memory -= value;
                addToHistory("Memory - " + formatResult(value) + " = " + formatResult(memory));
                break;
        }
    }
    
    private void clearAll() {
        display.setText("0");
        operationDisplay.setText("");
        num1 = 0;
        num2 = 0;
        result = 0;
        operator = "";
        currentInput = "";
        isNewCalculation = true;
    }
    
    private void clearEntry() {
        display.setText("0");
        isNewCalculation = true;
    }
    
    private void backspace() {
        String current = display.getText();
        if (current.length() > 1) {
            display.setText(current.substring(0, current.length() - 1));
        } else {
            display.setText("0");
            isNewCalculation = true;
        }
    }
    
    private void toggleSign() {
        double value = Double.parseDouble(display.getText());
        value = -value;
        display.setText(formatResult(value));
    }
    
    private void addToHistory(String calculation) {
        calculationHistory.add(calculation);
        updateHistoryDisplay();
    }
    
    private void updateHistoryDisplay() {
        StringBuilder sb = new StringBuilder();
        int start = Math.max(0, calculationHistory.size() - 20);
        
        for (int i = start; i < calculationHistory.size(); i++) {
            sb.append(calculationHistory.get(i)).append("\n");
        }
        
        historyArea.setText(sb.toString());
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }
    
    private void clearHistory() {
        calculationHistory.clear();
        historyArea.setText("");
    }
    
    private String formatResult(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        } else {
            return String.format("%.10f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
        }
    }
    
    private boolean isNumber(String text) {
        return text.matches("[0-9]");
    }
    
    private boolean isOperator(String text) {
        return text.equals("+") || text.equals("−") || text.equals("×") || text.equals("÷") || text.equals("=");
    }
    
    private boolean isBasicOperator(String text) {
        return text.equals("+") || text.equals("−") || text.equals("×") || text.equals("÷");
    }
    
    private boolean isScientificFunction(String text) {
        String[] functions = {"sin", "cos", "tan", "log", "ln", "√", "x²", "π", "e"};
        return Arrays.asList(functions).contains(text);
    }
    
    private boolean isMemoryFunction(String text) {
        return text.equals("MC") || text.equals("MR") || text.equals("M+") || text.equals("M-");
    }
    
    private Image createCalculatorIcon() {
        BufferedImage icon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = icon.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRoundRect(2, 2, 28, 28, 6, 6);
        
        g2d.setColor(DISPLAY_COLOR);
        g2d.fillRoundRect(4, 4, 24, 8, 2, 2);
        
        g2d.setColor(BUTTON_COLOR);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g2d.fillRoundRect(4 + j * 6, 14 + i * 4, 4, 3, 1, 1);
            }
        }
        
        g2d.dispose();
        return icon;
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set system look and feel");
        }
        
        SwingUtilities.invokeLater(() -> {
            new JavaCalculator().setVisible(true);
        });
    }
}
