/*
 * basic_passcheck.c
 *
 * A tiny, basic password-strength checker for Hacktoberfest.
 * - Single-file, small and clear.
 * - Checks: length, lowercase, uppercase, digit, symbol.
 * - Prints score 0-5 and short advice.

 * Usage:
 *   ./basic_passcheck              # reads password from stdin (hidden input not implemented)
 *   ./basic_passcheck "P@ssw0rd!"  # evaluate argument
 *
 * Author: M4ximumpizza
 */

#include <stdio.h>
#include <string.h>
#include <ctype.h>

static int contains_lower(const char *s) {
    while (*s) if (islower((unsigned char)*s++)) return 1;
    return 0;
}
static int contains_upper(const char *s) {
    while (*s) if (isupper((unsigned char)*s++)) return 1;
    return 0;
}
static int contains_digit(const char *s) {
    while (*s) if (isdigit((unsigned char)*s++)) return 1;
    return 0;
}
static int contains_symbol(const char *s) {
    while (*s) {
        unsigned char c = (unsigned char)*s++;
        if (!isalnum(c) && !isspace(c)) return 1;
    }
    return 0;
}

int main(int argc, char **argv) {
    const char *pw;
    char buf[256];

    if (argc >= 2) {
        pw = argv[1];
    } else {
        printf("Enter password (no echo not implemented): ");
        if (!fgets(buf, sizeof(buf), stdin)) return 1;
        /* remove newline */
        size_t ln = strlen(buf);
        if (ln && buf[ln-1] == '\n') buf[ln-1] = '\0';
        pw = buf;
    }

    size_t len = strlen(pw);
    int score = 0;

    if (len >= 8) score++;           /* reasonable minimum length */
    if (len >= 12) score++;          /* stronger length */
    if (contains_lower(pw)) score++;
    if (contains_upper(pw)) score++;
    if (contains_digit(pw)) score++;
    if (contains_symbol(pw)) score++;

    /* Cap score to 5 for simple display (length tiers plus variety) */
    if (score > 5) score = 5;

    printf("\nPassword: %s\n", pw);
    printf("Length: %zu\n", len);
    printf("Contains: %s%s%s%s\n",
           contains_lower(pw) ? "lowercase " : "",
           contains_upper(pw) ? "uppercase " : "",
           contains_digit(pw) ? "digits " : "",
           contains_symbol(pw) ? "symbols " : "");

    printf("\nSimple strength (0-5): %d/5\n", score);

    /* Tiny advice */
    if (score <= 1) {
        puts("Advice: Very weak. Use a longer passphrase (4+ random words) or add mixed-case letters, digits, and symbols.");
    } else if (score == 2) {
        puts("Advice: Weak. Increase length and add more character variety.");
    } else if (score == 3) {
        puts("Advice: Moderate. Consider a longer, unique passphrase and a password manager.");
    } else if (score == 4) {
        puts("Advice: Strong. Good, but do not reuse this password across sites.");
    } else {
        puts("Advice: Very strong. Use a password manager to store it safely.");
    }

    return 0;
}
