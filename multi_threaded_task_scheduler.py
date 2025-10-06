import threading
import time
import heapq
from typing import Callable, Optional

class Task:
    def __init__(self, func: Callable, delay: float = 0, interval: Optional[float] = None):
        """
        Initialize a Task.
        :param func: Callable task function.
        :param delay: Delay in seconds before first execution.
        :param interval: If set, task will repeat every 'interval' seconds.
        """
        self.func = func
        self.delay = delay
        self.interval = interval
        self.next_run = time.time() + delay
        self.cancelled = False

    def __lt__(self, other):
        return self.next_run < other.next_run

    def run(self):
        if not self.cancelled:
            try:
                self.func()
            except Exception as e:
                print(f"[Error] Task raised an exception: {e}")

class TaskScheduler:
    def __init__(self):
        self.tasks = []
        self.lock = threading.Lock()
        self.running = False
        self.thread = threading.Thread(target=self._run)
        self.thread.daemon = True

    def add_task(self, task: Task):
        with self.lock:
            heapq.heappush(self.tasks, task)

    def start(self):
        self.running = True
        self.thread.start()

    def stop(self):
        self.running = False
        self.thread.join()

    def _run(self):
        while self.running:
            with self.lock:
                if not self.tasks:
                    next_sleep = 0.1
                else:
                    task = self.tasks[0]
                    now = time.time()
                    if task.next_run <= now:
                        heapq.heappop(self.tasks)
                        threading.Thread(target=task.run).start()
                        if task.interval:
                            task.next_run = now + task.interval
                            heapq.heappush(self.tasks, task)
                        continue
                    else:
                        next_sleep = task.next_run - now
            time.sleep(min(next_sleep, 0.1))

# Example usage
if __name__ == "__main__":
    scheduler = TaskScheduler()

    def task1():
        print(f"[{time.strftime('%H:%M:%S')}] Task 1 executed")

    def task2():
        print(f"[{time.strftime('%H:%M:%S')}] Task 2 executed")

    # Add tasks: one-time and periodic
    scheduler.add_task(Task(task1, delay=2))
    scheduler.add_task(Task(task2, delay=1, interval=3))

    print("Starting scheduler...")
    scheduler.start()

    # Run the scheduler for 10 seconds for demo
    time.sleep(10)
    scheduler.stop()
    print("Scheduler stopped.")
