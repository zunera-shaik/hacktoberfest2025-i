#!/bin/bash

# Enhanced Calculator Demo - Shows Step-by-Step Calculations
# Created by: KartikeyaNainkhwal
# For: Hacktoberfest 2025 - Issue #281

echo "🧮✨ ENHANCED CALCULATOR DEMO - Now with Step-by-Step Display! ✨🧮"
echo "=================================================================="
echo

# Compile both calculators
echo "🔨 Compiling enhanced calculators..."
if javac JavaCalculator.java && javac ConsoleCalculator.java; then
    echo "✅ Both calculators compiled successfully!"
else
    echo "❌ Compilation failed!"
    exit 1
fi

echo
echo "🎯 NEW FEATURES DEMONSTRATED:"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "📱 GUI Calculator:"
echo "  • Shows current operation above the display"
echo "  • Visual feedback for each calculation step"
echo "  • Operation history with complete expressions"
echo "  • Scientific function progress indicators"
echo
echo "💻 Console Calculator:"
echo "  • Step-by-step calculation breakdown"
echo "  • Variable substitution tracking"
echo "  • Function evaluation display"
echo "  • Toggle step display on/off with 'show_steps'"
echo

echo "🚀 Starting Console Calculator Demo with Sample Calculations..."
echo "═════════════════════════════════════════════════════════════════"
echo

# Demonstrate step-by-step calculations
cat << 'EOF' | java ConsoleCalculator
2 + 3 * 4
sqrt(16) + sin(30)
set x 10
set y 5
x * y + sqrt(x^2 + y^2)
show_steps
2 + 3 * 4
sqrt(16) + sin(30)
log(100) + ln(e) * pi
(5 + 3) * 2 - sqrt(25)
show_steps
exit
EOF

echo
echo "🎨 The GUI Calculator also has enhanced visual feedback!"
echo "   • Run './run_calculator.sh' and select option 1 to see:"
echo "     - Current operation display above main screen"
echo "     - Visual calculation progress"
echo "     - Enhanced history panel"
echo
echo "✨ Key Improvements Made:"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "1. 📺 Visual Operation Display - See what's being calculated"
echo "2. 🔍 Step-by-Step Breakdown - Understand each calculation"
echo "3. 🎯 Progress Indicators - Know when functions are being evaluated"
echo "4. 📊 Enhanced History - Complete calculation records"
echo "5. ⚡ Real-time Feedback - Immediate visual responses"
echo "6. 🎛️  Toggle Controls - Turn step display on/off as needed"
echo
echo "🌟 Perfect for Hacktoberfest 2025! 🌟"
echo "Made with ❤️  by KartikeyaNainkhwal"
