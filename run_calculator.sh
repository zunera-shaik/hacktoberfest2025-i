#!/bin/bash

# Advanced Java Calculator Demo Script
# Created by: KartikeyaNainkhwal
# For: Hacktoberfest 2025 - Issue #281

echo "╔══════════════════════════════════════════════════════════════╗"
echo "║               Advanced Java Calculator Demo                  ║"
echo "║                by KartikeyaNainkhwal                         ║"
echo "║              Hacktoberfest 2025 - Issue #281                ║"
echo "╚══════════════════════════════════════════════════════════════╝"
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java to run the calculators."
    exit 1
fi

if ! command -v javac &> /dev/null; then
    echo "❌ Java compiler (javac) is not installed. Please install JDK to compile the calculators."
    exit 1
fi

echo "✅ Java found: $(java -version 2>&1 | head -n 1)"
echo

# Function to compile and check for errors
compile_java() {
    local filename=$1
    echo "🔨 Compiling $filename..."
    
    if javac "$filename" 2>/dev/null; then
        echo "✅ $filename compiled successfully!"
        return 0
    else
        echo "❌ Failed to compile $filename"
        javac "$filename"  # Show errors
        return 1
    fi
}

# Function to show menu
show_menu() {
    echo
    echo "🧮 Choose Calculator Version:"
    echo "1. GUI Calculator (Graphical Interface)"
    echo "2. Console Calculator (Command Line)"
    echo "3. Compile Both"
    echo "4. Exit"
    echo
    read -p "Enter your choice (1-4): " choice
}

# Main menu loop
while true; do
    show_menu
    
    case $choice in
        1)
            echo
            echo "🎨 Starting GUI Calculator..."
            if compile_java "JavaCalculator.java"; then
                echo "🚀 Launching GUI Calculator..."
                java JavaCalculator &
                echo "✨ GUI Calculator is now running!"
                echo "📝 Note: The calculator window should appear. Close it when done."
            fi
            ;;
        2)
            echo
            echo "💻 Starting Console Calculator..."
            if compile_java "ConsoleCalculator.java"; then
                echo "🚀 Launching Console Calculator..."
                echo "📝 Note: Type 'help' for commands, 'exit' to quit"
                echo "----------------------------------------"
                java ConsoleCalculator
            fi
            ;;
        3)
            echo
            echo "🔨 Compiling both calculators..."
            compile_java "JavaCalculator.java"
            echo
            compile_java "ConsoleCalculator.java"
            echo
            echo "✅ Compilation complete! You can now run either calculator."
            ;;
        4)
            echo
            echo "👋 Thank you for trying the Advanced Java Calculator!"
            echo "🌟 Don't forget to star the repository if you liked it!"
            echo "🤝 Happy Hacktoberfest 2025!"
            exit 0
            ;;
        *)
            echo
            echo "❌ Invalid choice. Please enter 1, 2, 3, or 4."
            ;;
    esac
    
    echo
    read -p "Press Enter to continue..."
done
