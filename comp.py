import asyncio
import contextlib
import time
from functools import wraps
from typing import Any, Dict, List, Optional

# Advanced Decorator with arguments
def retry(max_attempts: int = 3, delay: float = 1.0):
    def decorator(func):
        @wraps(func)
        async def async_wrapper(*args, **kwargs):
            last_exception = None
            for attempt in range(max_attempts):
                try:
                    return await func(*args, **kwargs)
                except Exception as e:
                    last_exception = e
                    if attempt < max_attempts - 1:
                        await asyncio.sleep(delay)
            raise last_exception
        return async_wrapper
    return decorator

# Metaclass for advanced class control
class MetaClass(type):
    def __new__(cls, name, bases, attrs):
        # Add class creation timestamp
        attrs['_created_at'] = time.time()
        return super().__new__(cls, name, bases, attrs)

# Descriptor for attribute validation
class ValidatedAttribute:
    def __init__(self, min_val: float, max_val: float):
        self.min_val = min_val
        self.max_val = max_val
        self._name = None

    def __set_name__(self, owner, name):
        self._name = name

    def __get__(self, instance, owner):
        if instance is None:
            return self
        return instance.__dict__[self._name]

    def __set__(self, instance, value):
        if not (self.min_val <= value <= self.max_val):
            raise ValueError(f"Value must be between {self.min_val} and {self.max_val}")
        instance.__dict__[self._name] = value

# Context manager for resource management
@contextlib.contextmanager
def database_connection(connection_string: str):
    connection = None
    try:
        print(f"Connecting to {connection_string}")
        connection = {"connected": True, "string": connection_string}
        yield connection
    except Exception as e:
        print(f"Database error: {e}")
        raise
    finally:
        if connection:
            print("Closing connection")
            connection["connected"] = False

# Advanced class using multiple concepts
class ComplexSystem(metaclass=MetaClass):
    version = "1.0"
    
    # Using descriptor
    temperature = ValidatedAttribute(-273.15, 1000.0)

    def __init__(self, name: str, temp: float = 0.0):
        self.name = name
        self.temperature = temp
        self._data: List[Dict[str, Any]] = []
        self._lock = asyncio.Lock()

    async def __aenter__(self):
        await self.initialize()
        return self

    async def __aexit__(self, exc_type, exc_val, exc_tb):
        await self.cleanup()

    @retry(max_attempts=5, delay=0.1)
    async def initialize(self):
        async with self._lock:
            print(f"Initializing {self.name}")
            await asyncio.sleep(0.05)  # Simulate async work
            self._data.append({"status": "initialized", "timestamp": time.time()})

    async def process_data(self, data: List[float]):
        async with self._lock:
            processed = [x * 2 for x in data if x > 0]
            self._data.append({"original": data, "processed": processed})
            return processed

    @classmethod
    def get_version(cls):
        return f"ComplexSystem v{cls.version}"

    def __repr__(self):
        return f"ComplexSystem(name='{self.name}', temp={self.temperature})"

# Async generator and comprehensions
async def data_stream(limit: int = 10):
    for i in range(limit):
        yield {"id": i, "value": i ** 2}
        await asyncio.sleep(0.01)

# Main async function demonstrating complex workflow
async def main():
    system = ComplexSystem("MainSystem", 25.5)
    
    async with system:
        # Using context manager for database
        with database_connection("postgresql://localhost:5432/mydb") as db:
            print(f"Database connected: {db['connected']}")

        # Process streamed data
        async for data in data_stream(5):
            result = await system.process_data([data["value"]])
            print(f"Processed {data} -> {result}")

        # Demonstrate async comprehensions
        temperatures = [temp async for temp in generate_temperatures(3)]
        print(f"Sampled temperatures: {temperatures}")

        # Parallel tasks
        tasks = [simulate_work(i) for i in range(3)]
        results = await asyncio.gather(*tasks, return_exceptions=True)
        print(f"Parallel results: {results}")

async def generate_temperatures(count: int):
    for i in range(count):
        yield 20 + i * 5
        await asyncio.sleep(0.01)

async def simulate_work(task_id: int):
    await asyncio.sleep(0.05)
    return f"Task {task_id} completed"

# Property with caching
class CachedProperty:
    def __init__(self, func):
        self.func = func
        self._value = None
        self._computed = False

    def __get__(self, instance, owner):
        if not self._computed:
            self._value = self.func(instance)
            self._computed = True
        return self._value

class DataProcessor:
    def __init__(self, data: List[float]):
        self.data = data

    @CachedProperty
    def statistics(self) -> Dict[str, float]:
        print("Computing expensive statistics...")
        time.sleep(0.1)  # Simulate expensive computation
        return {
            "mean": sum(self.data) / len(self.data),
            "max": max(self.data),
            "min": min(self.data)
        }

# Run the complex system
if __name__ == "__main__":
    # Demonstrate cached property
    processor = DataProcessor([1, 2, 3, 4, 5])
    print("First access (computing):", processor.statistics)
    print("Second access (cached):", processor.statistics)

    # Run async main
    asyncio.run(main())

    # Demonstrate error handling
    try:
        invalid_system = ComplexSystem("Invalid", -300)  # Should raise ValueError
    except ValueError as e:
        print(f"Caught expected error: {e}")