package org.example.model;

public class Entity {
    private int id;
    private String name;
    private String description;

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("{\"id\": %d, \"name\": \"%s\", \"description\": \"%s\"}", id, name, description);
    }
}
