package se.yrgo.schedule;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

/**
 * Formats a list of assignments as JSON.
 */
public class JsonFormatter implements Formatter {

    /**
     * Formats a list of assignments as JSON string.
     * @param assignments The list of assignments to format
     * @return JSON string representation of the assignments
     */
    @Override
    public String format(List<Assignment> assignments) {
        if (assignments == null || assignments.isEmpty()) {
            return "[]";
        }

        try {
            JSONArray jsonArray = new JSONArray();

            for (Assignment assignment : assignments) {
                if (assignment != null) {
                    jsonArray.put(createAssignmentObject(assignment));
                }
            }

            return jsonArray.toString(2); // Indent with 2 spaces for pretty printing
        } catch (Exception e) {
            // Fallback to empty array if something goes wrong
            return "[]";
        }
    }

    /**
     * Creates a JSON object for a single assignment.
     * @param assignment The assignment to convert to JSON
     * @return JSONObject representing the assignment
     */
    private JSONObject createAssignmentObject(Assignment assignment) {
        JSONObject jsonAssignment = new JSONObject();

        // Add date
        jsonAssignment.put("date", assignment.date() != null ? assignment.date() : "");

        // Add school object
        JSONObject school = new JSONObject();
        if (assignment.school() != null) {
            school.put("school_name", assignment.school().name() != null ? assignment.school().name() : "");
            school.put("address", assignment.school().address() != null ? assignment.school().address() : "");
        } else {
            school.put("school_name", "");
            school.put("address", "");
        }
        jsonAssignment.put("school", school);

        // Add substitute object
        JSONObject substitute = new JSONObject();
        if (assignment.teacher() != null) {
            substitute.put("name", assignment.teacher().name() != null ? assignment.teacher().name() : "");
        } else {
            substitute.put("name", "");
        }
        jsonAssignment.put("substitute", substitute);

        return jsonAssignment;
    }
}