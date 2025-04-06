package ovr.it.filter.io.deserialization;

import org.junit.jupiter.api.Test;
import ovr.it.filter.api.Filter;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FilterDeserializerTest {

    private Filter parse(String input) {
        return FilterDeserializer.parse(input);
    }

    private Map<String, String> asset(String... keyValues) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            map.put(keyValues[i], keyValues[i + 1]);
        }
        return map;
    }

    @Test
    public void testSimpleAnd() {
        Filter f = parse("color = \"red\" AND year > \"2015\"");
        assertTrue(f.matches(asset("color", "red", "year", "2016")));
        assertFalse(f.matches(asset("color", "red", "year", "2014")));
    }

    @Test
    public void testSimpleOr() {
        Filter f = parse("brand = \"BMW\" OR brand = \"Audi\"");
        assertTrue(f.matches(asset("brand", "BMW")));
        assertTrue(f.matches(asset("brand", "Audi")));
        assertFalse(f.matches(asset("brand", "Toyota")));
    }

    @Test
    public void testSimpleNot() {
        Filter f = parse("NOT(color = \"red\")");
        assertFalse(f.matches(asset("color", "red")));
        assertTrue(f.matches(asset("color", "blue")));
    }

    @Test
    public void testNestedAndOr() {
        Filter f = parse("(color = \"red\" AND year > \"2015\") OR (brand = \"BMW\" AND year < \"2012\")");
        assertTrue(f.matches(asset("color", "red", "year", "2020")));
        assertTrue(f.matches(asset("brand", "BMW", "year", "2010")));
        assertFalse(f.matches(asset("brand", "BMW", "year", "2020")));
    }

    @Test
    public void testComplexNested() {
        Filter f = parse("NOT((color = \"red\" AND year > \"2015\") OR (brand = \"BMW\" AND price < \"20000\"))");
        assertFalse(f.matches(asset("color", "red", "year", "2016")));
        assertFalse(f.matches(asset("brand", "BMW", "price", "15000")));
        assertTrue(f.matches(asset("brand", "Toyota", "price", "25000", "year", "2010")));
    }

    @Test
    public void testMixedOperators() {
        Filter f = parse("color = \"blue\" AND (year >= \"2010\" OR year <= \"2000\")");
        assertTrue(f.matches(asset("color", "blue", "year", "2010")));
        assertTrue(f.matches(asset("color", "blue", "year", "2000")));
        assertFalse(f.matches(asset("color", "blue", "year", "2005")));
        assertFalse(f.matches(asset("color", "green", "year", "2010")));
    }

    @Test
    public void testChainedAndOrNot() {
        Filter f = parse("NOT(color = \"red\") AND brand = \"Toyota\" OR year = \"2020\"");
        assertTrue(f.matches(asset("color", "blue", "brand", "Toyota")));
        assertTrue(f.matches(asset("year", "2020")));
        assertFalse(f.matches(asset("color", "red", "brand", "Toyota")));
    }

    @Test
    public void testEqualsProperty() {
        Filter f = parse("country = origin");
        assertTrue(f.matches(asset("country", "IT", "origin", "IT")));
        assertFalse(f.matches(asset("country", "IT", "origin", "DE")));
    }

    @Test
    public void testExists() {
        Filter f = parse("EXISTS(model)");
        assertTrue(f.matches(asset("model", "Yaris")));
        assertFalse(f.matches(asset("brand", "Toyota")));
    }

    @Test
    public void testCaseInsensitiveValues() {
        Filter f = parse("color = \"RED\"");
        assertTrue(f.matches(asset("color", "red")));
        assertTrue(f.matches(asset("color", "ReD")));
    }

    @Test
    public void testOrWithExtraWhitespaceBetween() {
        Filter f = parse("color = \"red\"     OR    brand = \"BMW\"");
        assertTrue(f.matches(asset("color", "red")));
        assertTrue(f.matches(asset("brand", "BMW")));
        assertFalse(f.matches(asset("brand", "Toyota")));
    }

    @Test
    public void testAndWithExtraWhitespaceBetween() {
        Filter f = parse("color = \"red\"        AND     year >= \"2010\"");
        assertTrue(f.matches(asset("color", "red", "year", "2020")));
        assertFalse(f.matches(asset("color", "red", "year", "2000")));
    }

    @Test
    public void testComplexWhitespaceAroundOperators() {
        Filter f = parse(" (  color = \"red\"  AND   year >= \"2020\" )   OR  ( brand = \"BMW\"   AND  price < \"20000\" ) ");
        assertTrue(f.matches(asset("color", "red", "year", "2022")));
        assertTrue(f.matches(asset("brand", "BMW", "price", "15000")));
        assertFalse(f.matches(asset("brand", "Toyota", "price", "25000")));
    }

    @Test
    public void testBooleanTrue() {
        Filter f = parse("TRUE");
        assertTrue(f.matches(asset()));
        assertTrue(f.matches(asset("anything", "whatever")));
    }

    @Test
    public void testBooleanFalse() {
        Filter f = parse("FALSE");
        assertFalse(f.matches(asset()));
        assertFalse(f.matches(asset("x", "y")));
    }

    @Test
    public void testPropertyEqualsAnother() {
        Filter f = parse("a = b");
        assertTrue(f.matches(asset("a", "val", "b", "val")));
        assertFalse(f.matches(asset("a", "x", "b", "y")));
    }

    @Test
    public void testPropertyEqualsNull() {
        Filter f = parse("a = b");
        assertTrue(f.matches(asset())); // both null is true
        assertFalse(f.matches(asset("a", "x"))); // b null
    }

    @Test
    public void testInvalidNumericComparison() {
        Filter f = parse("year > \"abc\"");
        assertFalse(f.matches(asset("year", "2020")));
    }

    @Test
    public void testInvalidAssetValue() {
        Filter f = parse("year < \"2025\"");
        assertFalse(f.matches(asset("year", "twenty")));
    }

    @Test
    public void testMissingIdentifierBeforeOperator() {
        assertThrows(IllegalArgumentException.class, () -> parse(" = \"red\""));
    }

    @Test
    public void testUnterminatedQuotedValue() {
        assertThrows(IllegalArgumentException.class, () -> parse("brand = \"Alfa"));
    }
}
