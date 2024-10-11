import subprocess
import platform

def open_application(app_name):
    try:
        if platform.system() == "Windows":
            if app_name == "notepad":
                subprocess.Popen(["notepad.exe"])
            elif app_name == "calculator":
                subprocess.Popen(["calc.exe"])
            elif app_name == "chrome":
                subprocess.Popen(["C:/Program Files/Google/Chrome/Application/chrome.exe"])  # Chrome path
            else:
                print("Application not configured.")
        elif platform.system() == "Linux":
            if app_name == "gedit":
                subprocess.Popen(["gedit"])
            elif app_name == "firefox":
                subprocess.Popen(["firefox"])
            else:
                print("Application not configured.")
        elif platform.system() == "Darwin":  # For macOS
            if app_name == "safari":
                subprocess.Popen(["/Applications/Safari.app/Contents/MacOS/Safari"])
            elif app_name == "textedit":
                subprocess.Popen(["open", "-a", "TextEdit"])
            else:
                print("Application not configured.")
        else:
            print("Unsupported OS")
    except Exception as e:
        print(f"Failed to open {app_name}: {e}")

def main():
    print("Select an application to open:")
    print("1. Notepad (Windows) / Gedit (Linux) / TextEdit (macOS)")
    print("2. Calculator (Windows)")
    print("3. Chrome (Windows) / Firefox (Linux)")
    print("4. Safari (macOS)")

    choice = input("Enter the number of the application you want to open: ")

    if choice == "1":
        open_application("notepad" if platform.system() == "Windows" else "gedit" if platform.system() == "Linux" else "textedit")
    elif choice == "2" and platform.system() == "Windows":
        open_application("calculator")
    elif choice == "3":
        open_application("chrome" if platform.system() == "Windows" else "firefox")
    elif choice == "4" and platform.system() == "Darwin":
        open_application("safari")
    else:
        print("Invalid choice or application not supported on your OS.")

if __name__ == "__main__":
    main()
  
