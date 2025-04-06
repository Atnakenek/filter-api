
# Filter API

This project implements arbitrary domain-specific-language (DSL) filtering in Java 8 for assets represented as `Map<String, String>`.

### Features

- ✔️ Supports AND, OR, NOT logical operators
- ✔️ Value-based conditions (`prop = "val"`, `>`, `>=`, `<`, `<=`)
- ✔️ Property-to-property comparison (`propA = propB`)
- ✔️ Boolean literals (`TRUE`, `FALSE`)
- ✔️ Property presence check via `EXISTS(prop)`
- ✔️ Case-insensitive value matching
- ✔️ Parser with `NOT` `AND` `OR` precedence
- ✔️ Visitor pattern for serialization
- ✔️ FilterBuilder for fluent creation

### Example

```
NOT(color = "red" AND year > "2010") OR (brand = "BMW" AND year <= "2020")
```

---

### 🛠️ How to use

#### Programmatic usage

```java
Filter filter = FilterBuilder.builder()
    .and(eq("color", "green"), gt("year", "2015"))
    .or(eqProp("brand", "origin"))
    .build();

or

Filter filter = FilterBuilder.builder(
        or(
                and(
                        eq("color", "green"), 
                        gt("year", "2015")
                ),
                eqProp("brand", "origin")
        ))
        .build();


boolean match = filter.matches(asset);
```

#### Input reading

```java
String input = "brand = \"BMW\" AND price < \"20000\"";
Filter parsed = FilterDeserializer.parse(input);
```

---

### ⚙️ Build & Run

```bash
mvn clean compile exec:java
```


---

### 🚀 Technologies

- Java 8
- Maven
- JUnit 5
- Lombok
- Apache Commons Lang
- Visitor & Interpreter Patterns

---

### 🤖 Author's note

I kept code readable and modular, with a clear separation between parsing, filter evaluation, and filter construction. 
The design makes it easy to extend, to add new filter types or enhance the language in the future.

---

### 📄 License

GPL-3.0
