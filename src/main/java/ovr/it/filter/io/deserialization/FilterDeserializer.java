package ovr.it.filter.io.deserialization;

import ovr.it.filter.api.Filter;
import ovr.it.filter.builder.matcher.Matchers;


public class FilterDeserializer {

    private final String input;
    private int pos = 0;

    public FilterDeserializer(String input) {
        this.input = input;
    }

    public static Filter parse(String input) {
        return new FilterDeserializer(input).parseOr();
    }

    // lowest precedence
    private Filter parseOr() {
        Filter left = parseAnd();
        skipSpaces();
        while (matchKeyword("OR")) {
            Filter right = parseAnd();
            left = Matchers.or(left, right);
        }
        return left;
    }

    private Filter parseAnd() {
        Filter left = parseNot();
        skipSpaces();
        while (matchKeyword("AND")) {
            Filter right = parseNot();
            left = Matchers.and(left, right);
        }
        return left;
    }

    // highest precedence
    private Filter parseNot() {
        skipSpaces();
        if (matchKeyword("NOT")) {
            return Matchers.not(parseOperation());
        }
        return parseOperation();
    }

    private Filter parseOperation() {
        if (isChar('(')) {
            skipChar();
            Filter inner = parseOr();
            skipChar();
            return inner;
        } else if (matchKeyword("TRUE")) {
            return Matchers.isTrue();
        } else if (matchKeyword("FALSE")) {
            return Matchers.isFalse();
        } else if (matchKeyword("EXISTS")) { //EXISTS(prop)
            skipChar();
            String key = parseText();
            skipChar();
            return Matchers.exists(key);
        } else {
            return parseComparisonOperation();
        }
    }

    private Filter parseComparisonOperation() {
        String propLeft = parseText();
        skipSpaces();
        String operator = parseComparator();
        skipSpaces();
        // if quoted, then numeric operation
        if (isChar('"')) {
            skipChar();
            String value = parseText();
            if (!isChar('"')) {
                throw new IllegalArgumentException("Missing closing \" at position: " + pos);
            }
            skipChar();
            return parseValueOperation(propLeft, operator, value);
        } else {
            // else is properties equality
            if (!"=".equals(operator)) {
                throw new IllegalArgumentException("Unsupported operation for properties equality check at position: " + pos);
            }
            String propRight = parseText();
            return Matchers.eqProp(propLeft, propRight);
        }
    }

    private Filter parseValueOperation(String key, String op, String val) {
        switch (op) {
            case "=":
                return Matchers.eq(key, val);
            case ">":
                return Matchers.gt(key, val);
            case ">=":
                return Matchers.gte(key, val);
            case "<":
                return Matchers.lt(key, val);
            case "<=":
                return Matchers.lte(key, val);
            default:
                throw new IllegalArgumentException("Unknown value operator: " + op);
        }
    }

    private String parseText() {
        int current = pos;
        while (isValidChar()) {
            pos++;
        }
        // if a single char is found, then error (eg. ' = "red" ')
        if (current == pos) {
            throw new IllegalArgumentException("Malformed expression at position: " + pos);
        }
        // extract found text
        return input.substring(current, pos);
    }

    private String parseComparator() {
        if (match(">=")) return ">=";
        if (match("<=")) return "<=";
        if (match("=")) return "=";
        if (match(">")) return ">";
        if (match("<")) return "<";
        throw new IllegalArgumentException("Expected operator at position " + pos);
    }

    private boolean match(String expected) {
        skipSpaces();
        if (input.regionMatches(true, pos, expected, 0, expected.length())) {
            pos += expected.length();
            return true;
        }
        return false;
    }

    private boolean matchKeyword(String operator) {
        int start = pos;
        boolean isMatch = match(operator);
        if (isMatch && isValidChar()) {
            pos = start; // Revert pos if next char is valid char (eg. ORDER = ...)
            isMatch = false;
        }
        return isMatch;
    }

    private boolean isValidChar() {
        return pos < input.length() && Character.isLetterOrDigit(input.charAt(pos));
    }

    private boolean isChar(char c) {
        return pos < input.length() && input.charAt(pos) == c;
    }

    private void skipChar() {
        pos++;
    }

    private void skipSpaces() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }
    }
}