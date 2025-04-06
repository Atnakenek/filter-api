package ovr.it.filter;

import ovr.it.filter.api.Filter;
import ovr.it.filter.builder.FilterBuilder;
import ovr.it.filter.io.deserialization.FilterDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ovr.it.filter.builder.matcher.Matchers.*;

public class FilterApi {

    public static void main(String[] args) {
        // Create an asset
        Map<String, String> asset = new HashMap<>();
        asset.put("brand", "Alfa Romeo");
        asset.put("color", "red");
        asset.put("year", "2020");
        // Create a filter programmatically
        Filter filter = FilterBuilder
                .builder()
                .and(eq("color", "red"), gt("year", "2010"))
                .build();
        System.out.println("Asset is: " + asset);
        System.out.println("Filter is: " + filter);
        System.out.println("Filter should match: " + filter.matches(asset));
        // Change year
        asset.put("year", "2009");
        System.out.println("Year changed to 2009. Filter should no longer match: " + filter.matches(asset));
        // Asset change
        asset.put("brand", "Alfa Romeo");
        asset.put("color", "red");
        asset.put("year", "2020");
        asset.put("price", "15000");
        asset.put("country", "IT");
        asset.put("origin", "IT");
        // Arbitrary filter
        // NOT((color == "red" AND year >= "2020") OR (price < "20000" AND country == origin))
        filter = FilterBuilder.builder()
                .not(
                        or(
                                and(eq("color", "red"), gte("year", "2020")),
                                and(lt("price", "20000"), eqProp("country", "origin"))
                        )
                )
                .build();
        System.out.println("Asset is: " + asset);
        System.out.println("Filter is: " + filter);
        System.out.println("Filter should NOT match: " + filter.matches(asset));
        asset.put("price", "25000");
        System.out.println("Price changed to 25000. Filter should NOT match: " + filter.matches(asset));
        asset.put("color", "blue");
        System.out.println("Color = blue. Filter should match: " + filter.matches(asset));
        // Reading filter from input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert your custom filter: ");
        String input = scanner.nextLine();
        try {
            Filter parsedFilter = FilterDeserializer.parse(input);
            System.out.println("Parsed as: " + parsedFilter);
            System.out.println("Asset is: " + asset);
            System.out.println("Matches? " + parsedFilter.matches(asset));
        } catch (Exception e) {
            System.out.println("Failed to parse filter: " + e.getMessage());
        }

    }
}