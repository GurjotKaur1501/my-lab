package se.yrgo.schedule;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

public class JsonFormatter implements Formatter {
    @Override
    public String format(List<Assignment> assignments) {
        if (assignments.isEmpty()) {
            return "[]";
        }

        JSONArray jsonArray = new JSONArray();
        for (Assignment assignment : assignments) {
            jsonArray.put(createJsonAssignment(assignment));
        }
        return jsonArray.toString(2);
    }

    private JSONObject createJsonAssignment(Assignment assignment) {
        JSONObject jsonAssignment = new JSONObject();
        jsonAssignment.put("date", assignment.date());

        JSONObject school = new JSONObject();
        school.put("school_name", assignment.school().name());
        school.put("address", assignment.school().address());
        jsonAssignment.put("school", school);

        JSONObject substitute = new JSONObject();
        substitute.put("name", assignment.teacher().name());
        jsonAssignment.put("substitute", substitute);

        return jsonAssignment;
    }
}