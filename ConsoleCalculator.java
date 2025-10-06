/**
 * Console Calculator with Advanced Features
 * Created by: KartikeyaNainkhwal
 * For: Hacktoberfest 2025 - Issue #281
 */

import java.util.*;
import java.util.regex.*;

public class ConsoleCalculator {
    
    private Scanner scanner;
    private double memory = 0;
    private Map<String, Double> variables = new HashMap<>();
    private List<String> history = new ArrayList<>();
    private boolean isRunning = true;
    private boolean showSteps = true;
    
    public ConsoleCalculator() {
        scanner = new Scanner(System.in);
        initializeVariables();
        showWelcomeMessage();
    }
    
    private void initializeVariables() {
        variables.put("pi", Math.PI);
        variables.put("e", Math.E);
    }
    
    private void showWelcomeMessage() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║               Advanced Console Calculator                    ║");
        System.out.println("║                by KartikeyaNainkhwal                         ║");
        System.out.println("║              Hacktoberfest 2025 - Issue #281                ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║  Basic Operations: +, -, *, /, ^, %                         ║");
        System.out.println("║  Functions: sin, cos, tan, log, ln, sqrt, abs, ceil, floor  ║");
        System.out.println("║  Constants: pi, e                                            ║");
        System.out.println("║  Memory: mem_store <value>, mem_recall, mem_clear, mem_add   ║");
        System.out.println("║  Variables: set <var> <value>, get <var>                     ║");
        System.out.println("║  Commands: history, clear_history, help, show_steps, exit   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    public void start() {
        while (isRunning) {
            System.out.print("Calculator> ");
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) continue;
            
            processInput(input);
        }
        
        System.out.println("Thank you for using Advanced Console Calculator!");
        scanner.close();
    }
    
    private void processInput(String input) {
        try {
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                isRunning = false;
                return;
            }
            
            if (input.equalsIgnoreCase("help")) {
                showHelp();
                return;
            }
            
            if (input.equalsIgnoreCase("history")) {
                showHistory();
                return;
            }
            
            if (input.equalsIgnoreCase("clear_history")) {
                clearHistory();
                return;
            }
            
            if (input.startsWith("mem_")) {
                handleMemoryOperation(input);
                return;
            }
            
            if (input.startsWith("set ")) {
                handleVariableSet(input);
                return;
            }
            
            if (input.startsWith("get ")) {
                handleVariableGet(input);
                return;
            }
            
            if (input.equalsIgnoreCase("vars")) {
                showVariables();
                return;
            }
            
            if (input.equalsIgnoreCase("show_steps") || input.equalsIgnoreCase("toggle_steps")) {
                showSteps = !showSteps;
                System.out.println("Step-by-step calculation display: " + (showSteps ? "ON" : "OFF"));
                return;
            }
            
            if (showSteps) {
                System.out.println("┌─ Calculating: " + input);
                System.out.print("│ ");
            }
            
            double result = evaluateExpressionWithSteps(input);
            String formattedResult = formatNumber(result);
            
            if (showSteps) {
                System.out.println("└─ Final Result: " + formattedResult);
            } else {
                System.out.println("Result: " + formattedResult);
            }
            addToHistory(input + " = " + formattedResult);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            addToHistory("Error in: " + input + " - " + e.getMessage());
        }
    }
    
    private double evaluateExpression(String expression) {
        for (Map.Entry<String, Double> var : variables.entrySet()) {
            expression = expression.replaceAll("\\b" + var.getKey() + "\\b", var.getValue().toString());
        }
        
        expression = handleFunctions(expression);
        return new ExpressionEvaluator().evaluate(expression);
    }
    
    private double evaluateExpressionWithSteps(String expression) {
        for (Map.Entry<String, Double> var : variables.entrySet()) {
            String oldExpression = expression;
            expression = expression.replaceAll("\\b" + var.getKey() + "\\b", var.getValue().toString());
            if (!oldExpression.equals(expression) && showSteps) {
                System.out.println("│ Substituting " + var.getKey() + " = " + formatNumber(var.getValue()) + ": " + expression);
            }
        }
        
        expression = handleFunctionsWithSteps(expression);
        return new ExpressionEvaluator().evaluateWithSteps(expression, showSteps);
    }
    
    private String handleFunctions(String expression) {
        expression = expression.replaceAll("sin\\(([^)]+)\\)", "Math.sin(Math.toRadians($1))");
        expression = expression.replaceAll("cos\\(([^)]+)\\)", "Math.cos(Math.toRadians($1))");
        expression = expression.replaceAll("tan\\(([^)]+)\\)", "Math.tan(Math.toRadians($1))");
        expression = expression.replaceAll("log\\(([^)]+)\\)", "Math.log10($1)");
        expression = expression.replaceAll("ln\\(([^)]+)\\)", "Math.log($1)");
        expression = expression.replaceAll("sqrt\\(([^)]+)\\)", "Math.sqrt($1)");
        expression = expression.replaceAll("abs\\(([^)]+)\\)", "Math.abs($1)");
        expression = expression.replaceAll("ceil\\(([^)]+)\\)", "Math.ceil($1)");
        expression = expression.replaceAll("floor\\(([^)]+)\\)", "Math.floor($1)");
        
        return expression;
    }
    
    private String handleFunctionsWithSteps(String expression) {
        Map<String, String> functionReplacements = new HashMap<>();
        
        expression = handleFunctionWithStepTracking(expression, "sin", functionReplacements);
        expression = handleFunctionWithStepTracking(expression, "cos", functionReplacements);
        expression = handleFunctionWithStepTracking(expression, "tan", functionReplacements);
        expression = handleFunctionWithStepTracking(expression, "log", functionReplacements);
        expression = handleFunctionWithStepTracking(expression, "ln", functionReplacements);
        expression = handleFunctionWithStepTracking(expression, "sqrt", functionReplacements);
        expression = handleFunctionWithStepTracking(expression, "abs", functionReplacements);
        expression = handleFunctionWithStepTracking(expression, "ceil", functionReplacements);
        expression = handleFunctionWithStepTracking(expression, "floor", functionReplacements);
        
        return expression;
    }
    
    private String handleFunctionWithStepTracking(String expression, String function, Map<String, String> replacements) {
        Pattern pattern = Pattern.compile(function + "\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(expression);
        
        while (matcher.find()) {
            String arg = matcher.group(1);
            double argValue = Double.parseDouble(arg);
            double result = 0;
            
            switch (function) {
                case "sin": result = Math.sin(Math.toRadians(argValue)); break;
                case "cos": result = Math.cos(Math.toRadians(argValue)); break;
                case "tan": result = Math.tan(Math.toRadians(argValue)); break;
                case "log": result = Math.log10(argValue); break;
                case "ln": result = Math.log(argValue); break;
                case "sqrt": result = Math.sqrt(argValue); break;
                case "abs": result = Math.abs(argValue); break;
                case "ceil": result = Math.ceil(argValue); break;
                case "floor": result = Math.floor(argValue); break;
            }
            
            if (showSteps) {
                System.out.println("│ Evaluating " + function + "(" + formatNumber(argValue) + ") = " + formatNumber(result));
            }
            
            expression = expression.replace(matcher.group(0), String.valueOf(result));
            matcher = pattern.matcher(expression);
        }
        
        return expression;
    }
    
    private void handleMemoryOperation(String input) {
        String[] parts = input.split(" ");
        String operation = parts[0];
        
        switch (operation.toLowerCase()) {
            case "mem_store":
                if (parts.length > 1) {
                    double value = evaluateExpression(parts[1]);
                    memory = value;
                    System.out.println("Stored " + formatNumber(value) + " in memory");
                    addToHistory("Memory stored: " + formatNumber(value));
                } else {
                    System.out.println("Usage: mem_store <value>");
                }
                break;
                
            case "mem_recall":
                System.out.println("Memory: " + formatNumber(memory));
                break;
                
            case "mem_clear":
                memory = 0;
                System.out.println("Memory cleared");
                addToHistory("Memory cleared");
                break;
                
            case "mem_add":
                if (parts.length > 1) {
                    double value = evaluateExpression(parts[1]);
                    memory += value;
                    System.out.println("Added " + formatNumber(value) + " to memory. New value: " + formatNumber(memory));
                    addToHistory("Memory + " + formatNumber(value) + " = " + formatNumber(memory));
                } else {
                    System.out.println("Usage: mem_add <value>");
                }
                break;
                
            default:
                System.out.println("Unknown memory operation: " + operation);
        }
    }
    
    private void handleVariableSet(String input) {
        String[] parts = input.split(" ");
        if (parts.length >= 3) {
            String varName = parts[1];
            String valueExpr = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
            double value = evaluateExpression(valueExpr);
            variables.put(varName, value);
            System.out.println("Variable '" + varName + "' set to " + formatNumber(value));
            addToHistory("Set " + varName + " = " + formatNumber(value));
        } else {
            System.out.println("Usage: set <variable_name> <value>");
        }
    }
    
    private void handleVariableGet(String input) {
        String[] parts = input.split(" ");
        if (parts.length > 1) {
            String varName = parts[1];
            if (variables.containsKey(varName)) {
                System.out.println(varName + " = " + formatNumber(variables.get(varName)));
            } else {
                System.out.println("Variable '" + varName + "' not found");
            }
        } else {
            System.out.println("Usage: get <variable_name>");
        }
    }
    
    private void showVariables() {
        if (variables.isEmpty()) {
            System.out.println("No variables defined");
        } else {
            System.out.println("Variables:");
            for (Map.Entry<String, Double> var : variables.entrySet()) {
                System.out.println("  " + var.getKey() + " = " + formatNumber(var.getValue()));
            }
        }
    }
    
    private void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No calculation history");
        } else {
            System.out.println("Calculation History:");
            for (int i = 0; i < history.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + history.get(i));
            }
        }
    }
    
    private void clearHistory() {
        history.clear();
        System.out.println("History cleared");
    }
    
    private void addToHistory(String entry) {
        history.add(entry);
        // Keep only last 50 entries
        if (history.size() > 50) {
            history.remove(0);
        }
    }
    
    private void showHelp() {
        System.out.println("\n═══════════════════ HELP ═══════════════════");
        System.out.println("Arithmetic Operations:");
        System.out.println("  +, -, *, /, ^, %");
        System.out.println("  Example: 2 + 3 * 4");
        System.out.println();
        System.out.println("Functions (use parentheses):");
        System.out.println("  sin(x), cos(x), tan(x) - trigonometric (degrees)");
        System.out.println("  log(x) - base 10 logarithm");
        System.out.println("  ln(x) - natural logarithm");
        System.out.println("  sqrt(x) - square root");
        System.out.println("  abs(x) - absolute value");
        System.out.println("  ceil(x), floor(x) - ceiling and floor");
        System.out.println();
        System.out.println("Constants:");
        System.out.println("  pi - π (3.14159...)");
        System.out.println("  e - Euler's number (2.71828...)");
        System.out.println();
        System.out.println("Memory Operations:");
        System.out.println("  mem_store <value> - store value in memory");
        System.out.println("  mem_recall - recall memory value");
        System.out.println("  mem_clear - clear memory");
        System.out.println("  mem_add <value> - add value to memory");
        System.out.println();
        System.out.println("Variable Operations:");
        System.out.println("  set <name> <value> - create/update variable");
        System.out.println("  get <name> - get variable value");
        System.out.println("  vars - list all variables");
        System.out.println();
        System.out.println("Other Commands:");
        System.out.println("  history - show calculation history");
        System.out.println("  clear_history - clear history");
        System.out.println("  show_steps - toggle step-by-step calculation display");
        System.out.println("  help - show this help");
        System.out.println("  exit - quit calculator");
        System.out.println("════════════════════════════════════════════\n");
    }
    
    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.valueOf((long) number);
        } else {
            return String.format("%.10f", number).replaceAll("0+$", "").replaceAll("\\.$", "");
        }
    }
    
    private String formatNumber(Double number) {
        return formatNumber(number.doubleValue());
    }
    
    // Simple expression evaluator
    private class ExpressionEvaluator {
        
        public double evaluate(String expression) {
            expression = expression.replaceAll("\\s", "");
            expression = expression.replaceAll("\\^", "**");
            
            return evaluateExpression(expression);
        }
        
        public double evaluateWithSteps(String expression, boolean showSteps) {
            expression = expression.replaceAll("\\s", "");
            expression = expression.replaceAll("\\^", "**");
            
            if (showSteps) {
                System.out.println("│ Processing: " + expression);
            }
            
            return evaluateExpressionWithSteps(expression, showSteps);
        }
        
        private double evaluateExpression(String expr) {
            while (expr.contains("(")) {
                int start = expr.lastIndexOf('(');
                int end = expr.indexOf(')', start);
                if (end == -1) throw new IllegalArgumentException("Mismatched parentheses");
                
                String subExpr = expr.substring(start + 1, end);
                double result = evaluateExpression(subExpr);
                expr = expr.substring(0, start) + result + expr.substring(end + 1);
            }
            
            expr = evaluateMathFunctions(expr);
            return evaluateArithmetic(expr);
        }
        
        private double evaluateExpressionWithSteps(String expr, boolean showSteps) {
            // Handle parentheses first
            while (expr.contains("(")) {
                int start = expr.lastIndexOf('(');
                int end = expr.indexOf(')', start);
                if (end == -1) throw new IllegalArgumentException("Mismatched parentheses");
                
                String subExpr = expr.substring(start + 1, end);
                if (showSteps) {
                    System.out.println("│ Solving parentheses: (" + subExpr + ")");
                }
                double result = evaluateExpressionWithSteps(subExpr, showSteps);
                if (showSteps) {
                    System.out.println("│ (" + subExpr + ") = " + formatNumber(result));
                }
                expr = expr.substring(0, start) + result + expr.substring(end + 1);
            }
            
            // Handle Math functions
            expr = evaluateMathFunctions(expr);
            return evaluateArithmetic(expr);
        }
        
        private String evaluateMathFunctions(String expr) {
            Pattern pattern = Pattern.compile("Math\\.(\\w+)\\(([^)]+)\\)");
            Matcher matcher = pattern.matcher(expr);
            
            while (matcher.find()) {
                String function = matcher.group(1);
                double arg = Double.parseDouble(matcher.group(2));
                double result = 0;
                
                switch (function) {
                    case "sin": result = Math.sin(arg); break;
                    case "cos": result = Math.cos(arg); break;
                    case "tan": result = Math.tan(arg); break;
                    case "log10": result = Math.log10(arg); break;
                    case "log": result = Math.log(arg); break;
                    case "sqrt": result = Math.sqrt(arg); break;
                    case "abs": result = Math.abs(arg); break;
                    case "ceil": result = Math.ceil(arg); break;
                    case "floor": result = Math.floor(arg); break;
                    default: throw new IllegalArgumentException("Unknown function: " + function);
                }
                
                expr = expr.replace(matcher.group(0), String.valueOf(result));
                matcher = pattern.matcher(expr);
            }
            
            return expr;
        }
        
        private double evaluateArithmetic(String expr) {
            try {
                while (expr.contains("**")) {
                    Pattern pattern = Pattern.compile("([\\d.]+)\\*\\*([\\d.]+)");
                    Matcher matcher = pattern.matcher(expr);
                    if (matcher.find()) {
                        double base = Double.parseDouble(matcher.group(1));
                        double exp = Double.parseDouble(matcher.group(2));
                        double result = Math.pow(base, exp);
                        expr = expr.replace(matcher.group(0), String.valueOf(result));
                    } else {
                        break;
                    }
                }
                
                Pattern mdPattern = Pattern.compile("([\\d.]+)([*/%])([\\d.]+)");
                while (mdPattern.matcher(expr).find()) {
                    Matcher matcher = mdPattern.matcher(expr);
                    if (matcher.find()) {
                        double left = Double.parseDouble(matcher.group(1));
                        String op = matcher.group(2);
                        double right = Double.parseDouble(matcher.group(3));
                        double result = 0;
                        
                        switch (op) {
                            case "*": result = left * right; break;
                            case "/": 
                                if (right == 0) throw new ArithmeticException("Division by zero");
                                result = left / right; 
                                break;
                            case "%": result = left % right; break;
                        }
                        
                        expr = expr.replace(matcher.group(0), String.valueOf(result));
                    }
                }
                
                Pattern asPattern = Pattern.compile("([\\d.]+)([+-])([\\d.]+)");
                while (asPattern.matcher(expr).find()) {
                    Matcher matcher = asPattern.matcher(expr);
                    if (matcher.find()) {
                        double left = Double.parseDouble(matcher.group(1));
                        String op = matcher.group(2);
                        double right = Double.parseDouble(matcher.group(3));
                        double result = op.equals("+") ? left + right : left - right;
                        
                        expr = expr.replace(matcher.group(0), String.valueOf(result));
                    }
                }
                
                return Double.parseDouble(expr);
                
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid expression: " + expr);
            }
        }
    }
    
    public static void main(String[] args) {
        ConsoleCalculator calculator = new ConsoleCalculator();
        calculator.start();
    }
}
