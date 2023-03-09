package persistence;

import org.json.JSONObject;

/*
 *    Title: JsonSerializationDemo
 *    Author: Paul Carter
 *    Date: 16 Oct 2021
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
