"""
text_utils.py

A collection of text and string utility functions.
Great for contributing to Hacktoberfest 2025.

Author: Soumya
"""

from typing import List


def is_palindrome(text: str) -> bool:
    """
    Check if a string is a palindrome (ignores case and spaces).

    Args:
        text (str): Input string.

    Returns:
        bool: True if palindrome, False otherwise.
    """
    cleaned = ''.join(c.lower() for c in text if c.isalnum())
    return cleaned == cleaned[::-1]


def word_count(text: str) -> int:
    """
    Count the number of words in a string.

    Args:
        text (str): Input string.

    Returns:
        int: Word count.
    """
    return len(text.split())


def char_frequency(text: str) -> dict:
    """
    Count the frequency of each character in a string.

    Args:
        text (str): Input string.

    Returns:
        dict: Character frequencies.
    """
    freq = {}
    for char in text:
        freq[char] = freq.get(char, 0) + 1
    return freq


def reverse_string(text: str) -> str:
    """
    Reverse a string.

    Args:
        text (str): Input string.

    Returns:
        str: Reversed string.
    """
    return text[::-1]


def unique_words(text: str) -> List[str]:
    """
    Get a list of unique words from a string.

    Args:
        text (str): Input string.

    Returns:
        List[str]: Unique words.
    """
    return list(set(text.lower().split()))


if __name__ == "__main__":
    # Example usage
    sample = "Madam Arora teaches malayalam"
    print("Palindrome check:", is_palindrome(sample))
    print("Word count:", word_count(sample))
    print("Char frequency:", char_frequency(sample))
    print("Reversed string:", reverse_string(sample))
    print("Unique words:", unique_words(sample))
