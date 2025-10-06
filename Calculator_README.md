# 🧮 Advanced Java Calculator

**Created by: KartikeyaNainkhwal**  
**For: Hacktoberfest 2025 - Issue #281**

## 📋 Overview

This project provides **two comprehensive calculator implementations** in Java:
1. **GUI Calculator** - Beautiful Swing-based graphical interface
2. **Console Calculator** - Powerful command-line calculator with advanced features

Both calculators offer far more than basic arithmetic operations, including scientific functions, memory operations, and calculation history.

## ✨ Features

### 🎨 GUI Calculator (`JavaCalculator.java`)

- **Beautiful Modern Interface**: Dark theme with professional styling
- **Visual Operation Display**: Shows current operation above main display
- **Basic Operations**: Addition, subtraction, multiplication, division
- **Scientific Functions**: 
  - Trigonometric: sin, cos, tan (in degrees)
  - Logarithmic: log (base 10), ln (natural log)
  - Mathematical: square root, square, power
  - Constants: π (pi), e (Euler's number)
- **Memory Operations**: MC (clear), MR (recall), M+ (add), M- (subtract)
- **Advanced Features**:
  - **Step-by-Step Display**: Shows what's being calculated in real-time
  - **Progress Indicators**: Visual feedback for function evaluations
  - Calculation history panel with scrolling
  - Keyboard support for all operations
  - Error handling and validation
  - Hover effects on buttons
  - Custom calculator icon
- **User Experience**:
  - **Real-time Operation Feedback**: See calculations as they happen
  - **Auto-clearing Progress Display**: Shows results then clears automatically
  - Responsive button interactions
  - Clear visual feedback
  - Professional color scheme
  - Enhanced history with complete expressions

### 💻 Console Calculator (`ConsoleCalculator.java`)

- **Interactive Command Line**: Easy-to-use prompt interface
- **Step-by-Step Calculation Display**: Shows detailed breakdown of each calculation
- **Expression Evaluation**: Parse and calculate complex mathematical expressions
- **Scientific Functions**: All trigonometric, logarithmic, and mathematical functions
- **Advanced Features**:
  - **Variable Substitution Tracking**: See how variables are replaced in real-time
  - **Function Evaluation Display**: Watch functions being calculated step-by-step
  - **Toggle Step Display**: Turn detailed breakdown on/off with `show_steps`
  - Variable storage and management
  - Memory operations (store, recall, clear, add)
  - Calculation history with 50-entry buffer
  - Built-in constants (pi, e)
  - Comprehensive help system
- **Smart Parsing**: 
  - **Visual Parentheses Resolution**: See how nested expressions are solved
  - **Order of Operations Display**: Shows PEMDAS in action
  - Handles parentheses correctly
  - Function composition
  - Variable substitution with feedback

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- A Java IDE or text editor
- Terminal/Command Prompt access

### Installation & Running

#### GUI Calculator
```bash
# Compile the GUI calculator
javac JavaCalculator.java

# Run the GUI calculator
java JavaCalculator
```

#### Console Calculator
```bash
# Compile the console calculator
javac ConsoleCalculator.java

# Run the console calculator
java ConsoleCalculator
```

## 📖 Usage Guide

### GUI Calculator Usage

1. **Basic Operations**: Click number buttons and operators
2. **Scientific Functions**: Use the scientific panel on the left
3. **Memory**: Store values using memory buttons (MC, MR, M+, M-)
4. **Keyboard Shortcuts**:
   - Numbers and operators work as expected
   - `Enter` or `=` to calculate
   - `Escape` to clear all
   - `Backspace` to delete last digit
5. **History**: View all calculations in the right panel

### Console Calculator Usage

#### Basic Calculations
```
Calculator> 2 + 3 * 4
Result: 14

Calculator> sqrt(16) + 5
Result: 9

Calculator> sin(30) + cos(60)
Result: 1
```

#### Variable Operations
```
Calculator> set x 10
Variable 'x' set to 10

Calculator> set y x * 2 + 5
Variable 'y' set to 25

Calculator> x + y
Result: 35
```

#### Memory Operations
```
Calculator> mem_store 42
Stored 42 in memory

Calculator> mem_recall
Memory: 42

Calculator> mem_add 8
Added 8 to memory. New value: 50
```

#### Available Commands
- `help` - Show detailed help
- `history` - View calculation history
- `clear_history` - Clear calculation history
- `vars` - List all variables
- `exit` or `quit` - Exit calculator

## 🧪 Examples

### Complex Expressions
```
Calculator> 2^3 + sqrt(25) * sin(90)
Result: 13

Calculator> log(100) + ln(e) + pi
Result: 6.14159

Calculator> (5 + 3) * 2 - 4 / 2
Result: 14
```

### Scientific Calculations
```
Calculator> sin(45) + cos(45)
Result: 1.41421

Calculator> log(1000) + ln(e^2)
Result: 5

Calculator> sqrt(2^2 + 3^2)
Result: 3.60555
```

## 🎯 Key Highlights

### What Makes This Special

1. **Dual Interface**: Both GUI and console versions for different use cases
2. **Professional Quality**: Production-ready code with proper error handling
3. **Advanced Features**: Goes beyond basic calculators with variables, memory, and history
4. **Beautiful Design**: Modern, dark-themed GUI with excellent UX
5. **Comprehensive**: Supports complex mathematical expressions and scientific functions
6. **Educational**: Well-commented code perfect for learning Java Swing and console applications

### Technical Excellence

- **Clean Architecture**: Well-structured, maintainable code
- **Error Handling**: Robust exception handling for all edge cases
- **User Experience**: Intuitive interfaces in both GUI and console versions
- **Performance**: Efficient parsing and calculation algorithms
- **Extensibility**: Easy to add new functions and features

## 🤝 Contributing

This project is part of Hacktoberfest 2025! Feel free to:
- Report bugs or suggest improvements
- Add new mathematical functions
- Enhance the UI/UX
- Improve documentation
- Add unit tests

## 📝 License

This project is open source and available under the MIT License.

## 🙏 Acknowledgments

- Created for Hacktoberfest 2025 - Issue #281
- Thanks to the open-source community for inspiration
- Built with Java Swing and love ❤️

---

**Happy Calculating! 🧮✨**

*Made with ❤️ by KartikeyaNainkhwal for the Hacktoberfest community*
